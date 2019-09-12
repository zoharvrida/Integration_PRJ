package bdsm.scheduler.processor;


import java.util.List;
import java.util.Map;

import bdsm.model.SknNgInOutCreditDetail;
import bdsm.model.SknNgInOutCreditFooter;
import bdsm.model.SknNgInOutCreditHeader;
import bdsm.model.SknNgWSAuditTrailBatch;
import bdsm.model.SknNgWSAuditTrailDetail;
import bdsm.model.SknNgWSAuditTrailHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.service.SknNgOutwardCreditService;
import bdsm.service.SknNgService;
import bdsm.util.CRC16;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.SknNgWSAuditTrailBatchDAO;
import bdsmhost.dao.SknNgWSAuditTrailDetailDAO;
import bdsmhost.dao.SknNgWSAuditTrailHeaderDAO;


/**
 * @author v00019372
 */
public class SknNgOutwardCreditWSWorker extends SknNgOutwardCreditWorker {
    /**
     * 
     */
    public static final String PREFIX = "SKNOUC"; 

    /**
     * 
     * @param context
     * @throws Exception
     */
    public SknNgOutwardCreditWSWorker(Map<? extends String, ? extends Object> context) throws Exception {
		super(context);
	}
	
	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		super.doExecute();
		
		if ((StatusDefinition.IN.equals(this.context.get(MapKey.typeFix))) && (StatusDefinition.AUTHORIZED.equals(this.context.get(MapKey.status)))) {
			SknNgWSAuditTrailBatchDAO batchDAO = new SknNgWSAuditTrailBatchDAO(this.session);
			SknNgWSAuditTrailHeaderDAO headerDAO = new SknNgWSAuditTrailHeaderDAO(this.session);
			SknNgWSAuditTrailDetailDAO detailDAO = new SknNgWSAuditTrailDetailDAO(this.session);
			SknNgOutwardCreditService service = new SknNgOutwardCreditService();
			SknNgWSAuditTrailBatch batch = null;
			SknNgWSAuditTrailHeader header = null;
			SknNgWSAuditTrailDetail detail = null;

			
			this.commitTransaction();
			
			/* === Batch [BEGIN] === */
			String batchNo = SchedulerUtil.generateUUID2();
			String batchNoOriginal = this.context.get(MapKey.batchNo).toString();
			String filename = this.context.get(MapKey.fileName).toString();
			StringBuilder filenameOriginal = new StringBuilder(filename.substring(this.context.get(MapKey.templateName).toString().length()));
			if ((Character.isLetter(filenameOriginal.charAt(0))) || (Character.isDigit(filenameOriginal.charAt(0))))
				;
			else
				filenameOriginal.deleteCharAt(0);
			
			batch = service.createWSAuditTrailBatch(batchNo, filename, batchNoOriginal, filenameOriginal.toString());
			batchDAO.insert(batch);
			/* === Batch [END] === */
			
			
			List<SknNgInOutCreditHeader> headers = this.headerInOutDAO.getByBatchNo(batchNoOriginal);
			List<Integer> listRowNo = new java.util.ArrayList<Integer>();
			
			
			StringBuilder sb = new StringBuilder();
			StringBuilder sbCRC = new StringBuilder();
			
			for (SknNgInOutCreditHeader h : headers) {
				/* Header */
				header = service.formatToWSAuditTrailHeader(h, batchNo, sb);
				
				sb.append(SknNgService.newLine());
				listRowNo.add(h.getCompositeId().getRecordId());
				
				
				/* Detail */
				List<SknNgInOutCreditDetail> details = this.detailInOutDAO.listByBatchNoAndParentRecordId(batchNoOriginal, h.getCompositeId().getRecordId());
				for (SknNgInOutCreditDetail d : details) {
					detail = service.formatToWSAuditTrailDetail(h, d, batchNo, sbCRC);
					detailDAO.insert(detail);
					
					sbCRC.append(SknNgService.newLine());
					listRowNo.add(d.getCompositeId().getRecordId());
					
				}
				int CRC = CRC16.CRC16(sbCRC.toString(), 0);
				sb.append(sbCRC.toString());
				
				
				/* Footer */
				SknNgInOutCreditFooter f = this.footerInOutDAO.getByBatchNoAndParentRecordId(batchNoOriginal, h.getCompositeId().getRecordId());
				header.setCRC(service.formatToCRCValue(f, CRC, sb));
				headerDAO.insert(header);
				
				sb.append(SknNgService.newLine());
				listRowNo.add(f.getCompositeId().getRecordId());
				
				
				/* Commit */
				this.commitTransaction();
				
				
				/* Request to SPK Web Service in a new thread */
				service.createProcessAndRun(
						batchNo, 
						header.getCompositeId().getRecordId(), 
						sb.toString(), 
						new java.util.ArrayList<Integer>(listRowNo)
				);
				
				/* Reset */ 
				sb.delete(0, sb.length());
				sbCRC.delete(0, sbCRC.length());
				listRowNo.clear();
				
				
				Thread.sleep(PropertyPersister.SKNNG_SPK_WS_DELAY_BETWEEN_THREAD);
			}
		}
		
		return true;
	}
	
	
	private void commitTransaction() {
		this.tx.commit();
		this.tx = this.session.beginTransaction();
	}
	
}
