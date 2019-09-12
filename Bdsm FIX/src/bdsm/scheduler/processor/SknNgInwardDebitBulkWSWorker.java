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
import org.apache.commons.io.FilenameUtils;

import bdsm.model.SknNgInOutDebitBulkDKE;
import bdsm.model.SknNgInOutDebitBulkFooter;
import bdsm.model.SknNgInOutDebitBulkHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.service.SknNgInwardDebitBulkService;
import bdsm.service.SknNgSPKWebService;
import bdsm.service.SknNgService;
import bdsm.util.BdsmUtil;
import bdsm.util.CRC16;
import bdsmhost.dao.SknNgInOutDebitBulkDAO;


/**
 * @author v00019372
 */
public class SknNgInwardDebitBulkWSWorker extends BaseProcessor {
    /**
     * 
     */
    protected String BIC;
	private StringWriter buffer = new StringWriter();
	private int totalDKE;
	private BigDecimal totalNominal;
	private boolean isHeader, isDKE, isAnyValidDKE;


    /**
     * 
     * @param context
     */
    public SknNgInwardDebitBulkWSWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}

    /**
     * 
     * @return
     */
    protected String getBIC() {
		return ((this.BIC != null)? this.BIC: SknNgService.BIC_CONVENT);
	}


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		SknNgInwardDebitBulkService service = new SknNgInwardDebitBulkService();
		FixSchedulerXtractDao xtractDAO;
		FixSchedulerXtract fsx = null;
		File generatedFile;
		
		xtractDAO = new FixSchedulerXtractDao(this.session);
		if (this.context.get(MapKey.reportFileName) != null) { // From Download Report
			fsx = xtractDAO.get((Integer) this.context.get(MapKey.idScheduler));
			String branchCode = this.context.get(MapKey.hdcdBranch).toString();
			this.BIC = new SknNgInwardDebitBulkWorkerDAO(this.session).getBICFromBranchCode(Integer.valueOf(branchCode));
			
			generatedFile = new File(PropertyPersister.dirFixOut, this.context.get(MapKey.reportFileName).toString());
		}
		else { // From BDSM Fix Scheduler
			fsx = xtractDAO.get(((FixQXtract) this.context.get(MapKey.fixQXtract)).getIdScheduler());
			
			Integer SKNCentralBranchCode = SknNgService.BIC_CONVENT.equalsIgnoreCase(this.getBIC())? 
					PropertyPersister.SKN_CENTER_BRANCH_CODE_CONVENT: PropertyPersister.SKN_CENTER_BRANCH_CODE_SYARIAH; 
			
			StringBuilder filename = new StringBuilder(FileUtil.getDateTimeFormatedString(fsx.getFilePattern()) + "." + fsx.getFileFormat());
			filename.insert(filename.indexOf("_"), "_" + BdsmUtil.leftPad(SKNCentralBranchCode.toString(), 5, '0'));
			
			generatedFile = new File(PropertyPersister.dirFixOut, filename.toString());
		}
		
		/* Call Web Service to SPK */
		String result = service.getResultOfRequestInwardWSToSPK(this.session, SknNgService.BIC_CONVENT.equalsIgnoreCase(this.getBIC())? 
					SknNgSPKWebService.CONVENT_BIC: SknNgSPKWebService.SYARIAH_BIC
				);
		
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
			
			// Encryption
			if (StatusDefinition.YES.equals(fsx.getFlgEncrypt())) {
				String newFilename = FilenameUtils.getBaseName(generatedFile.getPath()) + "_enc." + FilenameUtils.getExtension(generatedFile.getPath());
				File newFile = new File(generatedFile.getParent(), newFilename);
				FileUtils.copyFile(generatedFile, newFile);
				generatedFile = newFile;
				FileUtil.encrypt(generatedFile.getPath(), fsx.getModEncrypt(), fsx.getKeyEncrypt(), false);
			}
			
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
