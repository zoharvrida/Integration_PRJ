package bdsm.scheduler.processor;


import static bdsm.service.SknNgBulkService.newLine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bdsm.model.SknNgInOutReturBulkDKE;
import bdsm.model.SknNgInOutReturBulkDetail;
import bdsm.model.SknNgInOutReturBulkFooter;
import bdsm.model.SknNgInOutReturBulkHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.service.SknNgReturInwardDebitBulkService;
import bdsmhost.dao.SknNgInOutReturBulkDKEDAO;
import bdsmhost.dao.SknNgInOutReturBulkDetailDAO;
import bdsmhost.dao.SknNgInOutReturBulkFooterDAO;
import bdsmhost.dao.SknNgInOutReturBulkHeaderDAO;


/**
 * @author v00019372
 */
public class SknNgReturInwardDebitBulkWorker extends BaseProcessor {
	protected String batchNo, userId, cdBranch;


	public SknNgReturInwardDebitBulkWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}


	@Override
	protected boolean doExecute() throws Exception {
		this.batchNo = context.get(MapKey.param6).toString();
		this.userId = context.get(MapKey.hdUserid).toString();
		this.cdBranch = context.get(MapKey.hdcdBranch).toString();
		String reportFilename = context.get(MapKey.reportFileName).toString();
		
		int crcValue = 0;
		StringBuilder sbCRC = new StringBuilder();
		
		// DAO's
		SknNgInOutReturBulkHeaderDAO headerDAO = new SknNgInOutReturBulkHeaderDAO(this.session); 
		SknNgInOutReturBulkDKEDAO DKEDAO = new SknNgInOutReturBulkDKEDAO(this.session);
		SknNgInOutReturBulkDetailDAO detailDAO = new SknNgInOutReturBulkDetailDAO(this.session);
		SknNgInOutReturBulkFooterDAO footerDAO = new SknNgInOutReturBulkFooterDAO(this.session);
		
		// Extract Data Retur
		(new WorkerDAO()).extractData();
		
		
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(PropertyPersister.dirFixOut, reportFilename)));
		List<SknNgInOutReturBulkHeader> listHeader = headerDAO.listByBatchNo(this.batchNo);
		List<SknNgInOutReturBulkDKE> listDKE;
		List<SknNgInOutReturBulkDetail> listDetail;
		SknNgInOutReturBulkHeader modelHeader;
		SknNgInOutReturBulkDKE modelDKE;
		SknNgInOutReturBulkDetail modelDetail;
		SknNgInOutReturBulkFooter modelFooter;

		if (listHeader.size() == 0) {
			out.write("");
			out.close();
			return true;
		}
		
		
		SknNgReturInwardDebitBulkService service = new SknNgReturInwardDebitBulkService();
		
		// header
		for (int h=0; h<listHeader.size(); h++) {
			modelHeader = listHeader.get(h);
			
			/* Reformatting & Write to Fix Length Buffer */
			out.write(service.formatFromHeader(modelHeader));
			out.write(newLine());

			
			listDKE = DKEDAO.listByBatchNoAndParentRecordId(this.batchNo, modelHeader.getCompositeId().getRecordId());
			// DKE
			for (int dk=0; dk<listDKE.size(); dk++) {
				modelDKE = listDKE.get(dk);
				
				/* Reformatting & Write to Fix Length Buffer */
				String lineDKE = service.formatFromDKE(modelDKE); 
				out.write(lineDKE);
				out.write(newLine());
				sbCRC.append(lineDKE);
				
				
				listDetail = detailDAO.listByBatchNoAndParentRecordId(this.batchNo, modelDKE.getCompositeId().getRecordId());
				// detail
				for (int d=0; d<listDetail.size(); d++) {
					modelDetail = listDetail.get(d);
					
					/* Reformatting & Write to Fix Length Buffer */
					String lineDetail = service.formatFromDetail(modelDetail); 
					out.write(lineDetail);
					out.write(newLine());
					sbCRC.append(lineDetail);
				}
			}
			
			
			// the footer
			/* Reformatting & Write to Fix Length Buffer */
			modelFooter = footerDAO.listByBatchNoAndParentRecordId(this.batchNo, modelHeader.getCompositeId().getRecordId()).get(0);
			crcValue = bdsm.util.CRC16.CRC16(sbCRC.toString(), 0);
			
			// update CRC to footer value
			modelFooter.setCRC(String.valueOf(crcValue));
			out.write(service.formatFromFooter(modelFooter));
			out.write(newLine());
			
			sbCRC.delete(0, sbCRC.length());
		}
		
		out.close();
		
		return true;
	}
	
	
	/* Inner Class */
	private class WorkerDAO implements org.hibernate.jdbc.Work {
		private String sql = "{ CALL PKG_SKNNG_BULK.extract_retur_inward_debit(?, ?, ?) }";
		
		public void extractData() {
			session.doWork(this);
		}

		@Override
		public void execute(Connection connection) throws SQLException {
			CallableStatement cs = connection.prepareCall(this.sql);
			cs.setString(1, batchNo);
			cs.setInt(2, Integer.parseInt(cdBranch));
			cs.setString(3, userId);
			
			cs.execute();
		}
	}

}
