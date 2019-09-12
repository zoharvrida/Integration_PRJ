package bdsm.scheduler.processor;


import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import bdsm.model.SknNgWSAuditTrailBatch;
import bdsm.model.SknNgWSAuditTrailDetail;
import bdsm.model.SknNgWSAuditTrailHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.service.SknNgReturInwardDebitService;
import bdsm.service.SknNgService;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.SknNgWSAuditTrailBatchDAO;
import bdsmhost.dao.SknNgWSAuditTrailDetailDAO;
import bdsmhost.dao.SknNgWSAuditTrailHeaderDAO;


/**
 * @author v00019372
 */
public class SknNgReturInwardDebitWSWorker extends SknNgReturInwardDebitWorker {
	public static final String PREFIX = "SKNRID";
	private String batchNo;
	

	public SknNgReturInwardDebitWSWorker(Map<? extends String, ? extends Object> context) throws Exception {
		super(context);
	}
	
	
	@Override
	protected boolean doExecute() throws Exception {
		String typeFix = (String) this.context.get(MapKey.typeFix);
		
		if (StatusDefinition.XTRACT.equals(typeFix)) {
			boolean result = super.doExecute();
			
			if (result) {
				String newFilename = PREFIX + "_" + super.batchNo + "_" + this.cdBranch + "_" + this.context.get(MapKey.reportFileName).toString();
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
			SknNgWSAuditTrailBatchDAO batchDAO = new SknNgWSAuditTrailBatchDAO(this.session);
			SknNgWSAuditTrailHeaderDAO headerDAO = new SknNgWSAuditTrailHeaderDAO(this.session);
			SknNgWSAuditTrailDetailDAO detailDAO = new SknNgWSAuditTrailDetailDAO(this.session);
			SknNgReturInwardDebitService service = new SknNgReturInwardDebitService();
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
						case '0': {
									isHeader = true;
									headerRowNo = ctrRow;
									
									/* Read Header */
									header = service.parseToWSAuditTrailHeader(line, this.batchNo, Integer.valueOf(ctrRow));
									sb.append(line.substring(0, service.getLenG2Header())).append(SknNgService.newLine());
							}
							break;
						case '1': {
									if (isHeader == false)
										throwFIXException("Header is Missing", ctrRow);
									
									isDetail = true;
									
									/* Read Detail */
									detail = service.parseToWSAuditTrailDetail(line, this.batchNo, Integer.valueOf(ctrRow), header);
									detailDAO.insert(detail);
									sb.append(line.substring(0, service.getLenG2Detail())).append(SknNgService.newLine());
							}
							break;
						case '3': {
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
	
	
	protected void commitTransaction() {
		this.tx.commit();
		this.tx = this.session.beginTransaction();
	}
	
	protected static void throwFIXException(String message, int line) throws Exception {
		if (line == -1)
			throw new FIXException(message);
		else
			throw new FIXException(message + " (Line " + line + ")");
	}
	
}
