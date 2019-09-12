package bdsm.scheduler.processor;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import bdsm.model.SknNgReturInDebitDetail;
import bdsm.model.SknNgReturInDebitFooter;
import bdsm.model.SknNgReturInDebitHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.exception.FIXException;
import bdsm.service.SknNgReturInwardDebitService;
import bdsm.service.SknNgService;
import bdsm.util.CRC16;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.SknNgReturInDebitDetailDAO;
import bdsmhost.dao.SknNgReturInDebitFooterDAO;
import bdsmhost.dao.SknNgReturInDebitHeaderDAO;


/**
 * 
 * @author v00019372
 */
public class SknNgReturInwardDebitWorker extends BaseProcessor {
	protected String batchNo, userId, cdBranch;
	protected Date runDate;
	
	
	public SknNgReturInwardDebitWorker(Map<? extends String, ? extends Object> context) throws Exception {
		super(context);
	}

	
	@Override
	protected boolean doExecute() throws Exception {
		int crcValue = 0;
		StringBuilder sbCRC = new StringBuilder();
		
		String param1 = context.get(MapKey.param1).toString(); // date
		batchNo = context.get(MapKey.param6).toString().replace("-", "").toUpperCase();
		userId = context.get(MapKey.hdUserid).toString();
		cdBranch = context.get(MapKey.hdcdBranch).toString();
		String reportFilename = context.get(MapKey.reportFileName).toString();
		
		// DAO's
		SknNgReturInDebitHeaderDAO headerDAO = new SknNgReturInDebitHeaderDAO(session);
		SknNgReturInDebitDetailDAO detailDAO = new SknNgReturInDebitDetailDAO(session);
		SknNgReturInDebitFooterDAO footerDAO = new SknNgReturInDebitFooterDAO(session);
		
		
		// Date Validation
		try {
			Date dtmp = DateUtility.DATE_FORMAT_DDMMYYYY.parse(param1.replace("/", ""));
			runDate = new java.sql.Date(dtmp.getTime());
			/*
			Date datProcess = baBankMastDAO.get().getDatProcess();
			if (org.apache.commons.lang.time.DateUtils.isSameDay(dt, datProcess) == false)
				throw new FIXException("Invalid Date!!!");
			*/
		}
		catch (java.text.ParseException pe) {
			throw new FIXException("Invalid Date Format, must be DD/MM/YYYY !!!");
		}
		
		
		// Running FCR function to get extracted data
		this.getLogger().info("userId: " + this.userId);
		this.getLogger().info("batchNo: " + this.batchNo);
		this.getLogger().info("runDate: " + param1);
		this.getLogger().info("cdBranch: " + this.cdBranch);
		new WorkerDAO().getDownloadData();
		
		
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(PropertyPersister.dirFixOut, reportFilename)));
		List<SknNgReturInDebitHeader> listHeader = headerDAO.list(batchNo);
		List<SknNgReturInDebitDetail> listDetail;
		SknNgReturInDebitHeader modelHeader;
		SknNgReturInDebitDetail modelDetail;
		SknNgReturInDebitFooter modelFooter;

		if (listHeader.size() == 0) {
			out.write("");
			out.close();
			return true;
		}
		
		
		SknNgReturInwardDebitService service = new SknNgReturInwardDebitService();
		
		// the header
		for (int h=0; h<listHeader.size(); h++) {
			modelHeader = listHeader.get(h);
			
			/* Reformatting & Write to Fix Length Buffer */
			out.write(service.formatFromReturInHeader(modelHeader));
			out.write(SknNgService.newLine());
			modelHeader.setExtractStatus("Y"); // update extract status
			
			
			listDetail = detailDAO.listByParentRecordId(batchNo, modelHeader.getCompositeId().getRecordId());
			// write detail
			for (int i=0; i<listDetail.size(); i++) {
				modelDetail = listDetail.get(i);
				
				/* Reformatting & Write to Fix Length Buffer */
				String lineDetail = service.formatFromReturInDetail(modelDetail); 
				out.write(lineDetail);
				out.write(SknNgService.newLine());
				modelDetail.setExtractStatus("Y"); // update extract status
				sbCRC.append(lineDetail);
			}
			
			// the footer
			/* Reformatting & Write to Fix Length Buffer */
			modelFooter = footerDAO.getByParentRecordId(batchNo, modelHeader.getCompositeId().getRecordId());
			crcValue = CRC16.CRC16(sbCRC.toString(), 0);
			
			// update CRC to footer value
			out.write(service.formatFromReturInFooter(modelFooter, crcValue));
			out.write(SknNgService.newLine());
			modelFooter.setExtractStatus("Y"); // update extract status
			
			sbCRC.delete(0, sbCRC.length());
		}
		
		out.close();
		return true;
	}
	
	
	
	/* Inner Class */
	private class WorkerDAO {
		private String sql = "{ CALL PKG_SKNNG.getRetIncomingDebit(?, ?, ?, ?) }";
		
		public void getDownloadData() {
			Query q = session.createSQLQuery(sql);
			q.setParameter(0, userId);
			q.setParameter(1, batchNo);
			q.setParameter(2, runDate);
			q.setParameter(3, cdBranch);
			
			q.executeUpdate();
		}
	}

}

