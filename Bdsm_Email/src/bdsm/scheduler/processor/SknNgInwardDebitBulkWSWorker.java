package bdsm.scheduler.processor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import bdsm.model.SknNgInOutDebitBulkDKE;
import bdsm.model.SknNgInOutDebitBulkFooter;
import bdsm.model.SknNgInOutDebitBulkHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.service.SknNgInwardDebitBulkService;
import bdsm.service.SknNgSPKWebService;
import bdsm.util.CRC16;
import bdsmhost.dao.SknNgInOutDebitBulkDAO;


/**
 * @author v00019372
 */
public class SknNgInwardDebitBulkWSWorker extends BaseProcessor {
	private StringWriter buffer = new StringWriter();
	private int totalDKE;
	private BigDecimal totalNominal;
	private boolean isHeader, isDKE, isAnyValidDKE;


	public SknNgInwardDebitBulkWSWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}

	protected String getBIC() {
		return SknNgSPKWebService.CONVENT_BIC;
	}


	@Override
	protected boolean doExecute() throws Exception {
		SknNgInwardDebitBulkService service = new SknNgInwardDebitBulkService();
		File generatedFile;
		
		if (this.context.get(MapKey.reportFileName) != null) // From Download Report
			generatedFile = new File(PropertyPersister.dirFixOut, this.context.get(MapKey.reportFileName).toString());
		else { // From BDSM Fix Scheduler
			FixSchedulerXtractDao xtractDAO = new FixSchedulerXtractDao(this.session);
			FixSchedulerXtract fsx = xtractDAO.get(((FixQXtract) this.context.get(MapKey.fixQXtract)).getIdScheduler());
			generatedFile = new File(PropertyPersister.dirFixOut, FileUtil.getDateTimeFormatedString(fsx.getFilePattern()) + "." + fsx.getFileFormat());
		}
		
		/* Call Web Service to SPK */
		String result = service.getResultOfRequestInwardWSToSPK(this.getBIC());
		
		BufferedReader reader = new BufferedReader(new StringReader(result));
		BufferedWriter buffWriter = new BufferedWriter(this.buffer);
		BufferedWriter writer = new BufferedWriter(new FileWriter(generatedFile));
		SknNgInOutDebitBulkDAO DAO = new SknNgInOutDebitBulkDAO(this.session);
		SknNgInOutDebitBulkHeader modelHeader = null;
		String line, lineHeader;
		boolean isBlank = true;
		
		
		this.resetState();
		try {
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0)
					continue;
				
				switch (line.charAt(0)) {
					case '0' : {
							modelHeader = service.parseToHeader(line, null, null);
							this.isHeader = true;
						}
						break;
					case '1' : {
							SknNgInOutDebitBulkDKE modelDKE = service.parseToDKE(line, null, null);
							if (DAO.isExistBatchDKE(modelHeader.getTanggalBatch(), modelDKE.getSOR()) == false) {
								if (this.isAnyValidDKE == false) {
									if (isBlank == true)
										isBlank = false;
									
									this.totalDKE++;
									this.totalNominal = this.totalNominal.add(
										new BigDecimal(SknNgInwardDebitBulkService.parseNominal(modelDKE.getTotalNominalRincian().trim()))
									);
									this.isAnyValidDKE = true;
								}
								
								writeln(buffWriter, line);
								this.isDKE = true;
							}
							else
								this.isDKE = false;
						}
						break;
					case '2' : {
							if (this.isHeader && this.isDKE)
								writeln(buffWriter, line);
						}
						break;
					case '3' : {
							if (this.isAnyValidDKE) {
								buffWriter.flush();
								
								modelHeader.setJumlahRecordsDKE(String.valueOf(this.totalDKE));
								modelHeader.setTotalNominalDKE(SknNgInwardDebitBulkService.formatNominal(this.totalNominal.toString()));
								lineHeader = service.formatFromHeader(modelHeader);

								SknNgInOutDebitBulkFooter modelFooter = service.parseToFooter(line, null, null);
								int CRC = CRC16.CRC16(this.buffer.toString(), 0);
								modelFooter.setCRC(String.valueOf(CRC));
								
								writeln(writer, lineHeader);// header
								writer.write(this.buffer.toString()); // (dke and details)'s
								writeln(writer, service.formatFromFooter(modelFooter)); // footer
							}
							
							this.resetState();
						}
						break;
				}
			}
			
			reader.close(); reader = null;
			writer.close(); writer = null;
			
			if (this.context.get(MapKey.reportFileName) == null) { // From BDSM Fix Scheduler
				if (isBlank) {
					this.getLogger().info("delete file: " + generatedFile.getPath());
					FileUtils.deleteQuietly(generatedFile);
				}
				else {
					this.getLogger().info("move file: " + generatedFile.getPath());
					FileUtils.moveFileToDirectory(generatedFile, new File(PropertyPersister.dirFixIn, "SKNNG"), true);
				}
			}
			else
				FileUtils.copyFileToDirectory(generatedFile, new File(PropertyPersister.dirFixIn, "SKNNG"), true);
		}
		finally {
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
		
		return true;
	}

	private void resetState() {
		StringBuffer bf = this.buffer.getBuffer(); 
		bf.delete(0, bf.length());
		
		this.totalDKE = 0;
		this.totalNominal = BigDecimal.ZERO;
		this.isHeader = this.isDKE = this.isAnyValidDKE = false;
	}

	private static void writeln(BufferedWriter writer, String data) throws Exception {
		writer.append(data);
		writer.newLine();
	}

}
