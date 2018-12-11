package bdsm.scheduler.processor;


import java.io.BufferedReader;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;

import bdsm.model.SknNgWSAuditTrailBatch;
import bdsm.model.SknNgWSAuditTrailDetail;
import bdsm.model.SknNgWSAuditTrailHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.exception.SkipProcessException;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.service.SknNgOutwardDebitService;
import bdsm.service.SknNgSPKWebService;
import bdsm.service.SknNgService;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.SknNgWSAuditTrailBatchDAO;
import bdsmhost.dao.SknNgWSAuditTrailDetailDAO;
import bdsmhost.dao.SknNgWSAuditTrailHeaderDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.BaSiteDAO;


/**
 * @author v00019372
 */
public class SknNgOutwardDebitWSWorker extends SknNgOutwardDebitWorker {
    /**
     * 
     */
    public static final String PREFIX = "SKNOUD";
	private String batchNo;
	

    /**
     * 
     * @param context
     * @throws Exception
     */
    public SknNgOutwardDebitWSWorker(Map<? extends String, ? extends Object> context) throws Exception {
		super(context);
	}
	
	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String typeFix = (String) this.context.get(MapKey.typeFix);
		
		if (StatusDefinition.XTRACT.equals(typeFix)) {
			boolean result = super.doExecute();
			
			if (result) {
				String newFilename = PREFIX + "_" + this.context.get(MapKey.param6) + "_" + this.context.get(MapKey.param3) + "_" + this.context.get(MapKey.reportFileName).toString();
				File oldFile = new File(PropertyPersister.dirFixOut, this.context.get(MapKey.reportFileName).toString());
				File newFile = new File(PropertyPersister.dirFixIn + PropertyPersister.SKNNG_FIXIN_FOLDER, newFilename);
				File tmpFile;
				
				FixSchedulerXtractDao fixSchedulerXtractDAO = new FixSchedulerXtractDao(this.session);
				FixSchedulerXtract fixSchedulerXtract = fixSchedulerXtractDAO.get((Integer) this.context.get(MapKey.idScheduler));
				
				if (StatusDefinition.YES.equals(fixSchedulerXtract.getFlgEncrypt())) {
					tmpFile = new File(oldFile.getPath() + ".sknng");
					FileUtils.copyFile(oldFile, tmpFile);
					FileUtil.encrypt(tmpFile.getPath(), fixSchedulerXtract.getModEncrypt(), fixSchedulerXtract.getKeyEncrypt());
					FileUtils.moveFile(tmpFile, newFile);
				}
				else {
					tmpFile = oldFile;
					FileUtils.copyFile(tmpFile, newFile);
				}
			}
		}
		else if ((StatusDefinition.IN.equals(typeFix)) && (StatusDefinition.UNAUTHORIZED.equals(this.context.get(MapKey.status)))) {
			BaSiteDAO baSiteDAO = new BaSiteDAO(session);
			SknNgWSAuditTrailBatchDAO batchDAO = new SknNgWSAuditTrailBatchDAO(this.session);
			SknNgWSAuditTrailHeaderDAO headerDAO = new SknNgWSAuditTrailHeaderDAO(this.session);
			SknNgWSAuditTrailDetailDAO detailDAO = new SknNgWSAuditTrailDetailDAO(this.session);
			SknNgOutwardDebitService service = new SknNgOutwardDebitService();
			SknNgWSAuditTrailBatch batch = null;
			SknNgWSAuditTrailHeader header = null;
			SknNgWSAuditTrailDetail detail = null;

			
			
			/* === Batch [BEGIN] === */
			String filename = this.context.get(MapKey.fileName).toString();
			String[] arrFilename = filename.split("_", 4);
			String batchNoOriginal = arrFilename[1];
			this.batchNo = arrFilename[2] + "-" + SchedulerUtil.generateUUID2(); // branch code + generated id
			
			batch = service.createWSAuditTrailBatch(this.batchNo, filename, batchNoOriginal, arrFilename[3]);
			batchDAO.insert(batch);
			/* === Batch [END] === */
			
			
			BufferedReader br = new BufferedReader(new java.io.FileReader(this.context.get(MapKey.param5).toString()));
			String line;
			StringBuffer sb = new StringBuffer();
			List<Integer> listRowNo = new java.util.ArrayList<Integer>();
			int ctrRow, headerRowNo;
			boolean isHeader, isDetail;
			
			try {
				ctrRow = headerRowNo = 1;
				isHeader = isDetail = false;
				
				while ((line = br.readLine()) != null) {
					if (line.trim().length() == 0)
						continue;
					
					listRowNo.add(Integer.valueOf(ctrRow));
					
					switch (line.charAt(0)) {
						case '0': 
								{
									isHeader = true;
									headerRowNo = ctrRow;
									
									/* Read Header */
									header = service.parseToWSAuditTrailHeader(line, this.batchNo, Integer.valueOf(ctrRow));
									sb.append(line.substring(0, service.getLenG2Header())).append(SknNgService.newLine());
									
									
									// validate against SPK Date
									if (ctrRow == 1) {
										Date datProcess = (new BaBankMastDAO(session)).get().getBusinessDate();
										synchronized(DateUtility.DATE_FORMAT_YYYYMMDD) {
											bdsm.fcr.model.BaSite baSite = 
																baSiteDAO.getByMacroName(
																	SknNgSPKWebService.CONVENT_BIC.equals(header.getIdentitasPesertaPengirim())?
																			"ALLOW_SKN_DEBET_CONVENT": 
																			"ALLOW_SKN_DEBET_SYARIA"
																);
											
											if (baSite != null) {
												Date SPKDate = DateUtils.truncate(
																	DateUtility.DATE_FORMAT_YYYYMMDD.parse(baSite.getCompositeId().getMacroValue()), 
																	java.util.Calendar.DATE
																);
												
												if (DateUtils.isSameDay(SPKDate, datProcess) == false)
													throw new SkipProcessException("SKIP FOR REPEAT");
											}
										}
									}
								}
								break;
						case '1': 
								{
									if (isHeader == false)
										throwFIXException("Header is Missing", ctrRow);
									
									isDetail = true;
									
									/* Read Detail */
									detail = service.parseToWSAuditTrailDetail(line, this.batchNo, Integer.valueOf(ctrRow), header);
									detailDAO.insert(detail);
									sb.append(line.substring(0, service.getLenG2Detail())).append(SknNgService.newLine());
								}
								break;
						case '3': 
								{
									if (isHeader == false)
										throwFIXException("Header is Missing", ctrRow);
									
									if (isDetail == false)
										throwFIXException("Detail is Missing", ctrRow);
									
									
									/* Read Footer */
									header.setCRC(service.parseToTheCRCValue(line).trim());
									headerDAO.insert(header);
									sb.append(line.substring(0, service.getLenG2Footer()));
									
									
									/* Commit */
									this.commitTransaction();
									
									
									/* Request to SPK Web Service in a new thread */
									service.createProcessAndRun(
											this.batchNo, 
											headerRowNo, 
											sb.toString(), 
											listRowNo
									);
									
									/* Reset */
									sb.delete(0, sb.length());
									listRowNo.clear();
									isHeader = isDetail = false;
									
									Thread.sleep(PropertyPersister.SKNNG_SPK_WS_DELAY_BETWEEN_THREAD);
								}
								break;
						default:
							throwFIXException("FIRST CHARACTER MUST BE 0(HEADER), 1(DETAIL) OR 3(FOOTER)", ctrRow);
					}
					ctrRow++;
				}
			}
			finally {
				br.close();
			}
		}
		
		return true;
	}
	
	
    /**
     * 
     */
    protected void commitTransaction() {
		this.tx.commit();
		this.tx = this.session.beginTransaction();
	}
	
    /**
     * 
     * @param message
     * @param line
     * @throws Exception
     */
    protected static void throwFIXException(String message, int line) throws Exception {
		if (line == -1)
			throw new FIXException(message);
		else
			throw new FIXException(message + " (Line " + line + ")");
	}
	
}
