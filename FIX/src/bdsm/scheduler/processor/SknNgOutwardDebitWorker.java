/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.SknNgInOutDebitDetailDAO;
import bdsmhost.dao.SknNgInOutDebitFooterDAO;
import bdsmhost.dao.SknNgInOutDebitHeaderDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsm.scheduler.dao.SknNgOutwardDebitWorkerDAO;
import bdsm.scheduler.exception.FIXException;
import bdsm.service.SknNgOutwardDebitService;
import bdsm.service.SknNgService;
import bdsm.model.SknNgInOutDebitDetail;
import bdsm.model.SknNgInOutDebitFooter;
import bdsm.model.SknNgInOutDebitHeader;
import bdsm.util.CRC16;


/**
 * @author v00019372
 */
public class SknNgOutwardDebitWorker extends BaseProcessor {
	private static Map<String, String> sLockMap = new java.util.HashMap<String, String>();
	
    /**
     * 
     * @param context
     * @throws Exception
     */
    public SknNgOutwardDebitWorker(Map<? extends String, ? extends Object> context) throws Exception {
		super(context);
	}
	
	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		int crcValue = 0;
		StringBuilder sbCRC = new StringBuilder();
		
		String param1 = context.get(MapKey.param1).toString(); // batchScreen
		String param2 = context.get(MapKey.param2).toString(); // clgType
		String param3 = context.get(MapKey.param3).toString(); // branch
		//String param4 = context.get(MapKey.param4).toString(); // date
		String param6 = context.get(MapKey.param6).toString(); // batchProcess
		String idUser = context.get(MapKey.hdUserid).toString(); // userid
		String cdBranch = context.get(MapKey.hdcdBranch).toString(); // branch code of userid 
		String reportFilename = context.get(MapKey.reportFileName).toString();
		String outputFile = reportFilename;
		
		// DAO's
		SknNgInOutDebitHeaderDAO headerDAO = new SknNgInOutDebitHeaderDAO(session);
		SknNgInOutDebitDetailDAO detailDAO = new SknNgInOutDebitDetailDAO(session);
		SknNgInOutDebitFooterDAO footerDAO = new SknNgInOutDebitFooterDAO(session);
		SknNgOutwardDebitWorkerDAO workerDao = new SknNgOutwardDebitWorkerDAO(session);
		
		if (Integer.parseInt(cdBranch) != Integer.parseInt(param3))
			throw new FIXException("It is forbidden to extract other branch data!!!");
		
		java.sql.Date dt = null;
		Date datProcess = (new BaBankMastDAO(session)).get().getBusinessDate();
		
		/*
		// Date Validation
		try {
			Date dtmp = DateUtility.DATE_FORMAT_DDMMYYYY.parse(param4.replace("/", ""));
			dt = new java.sql.Date(dtmp.getTime());
			
			if (org.apache.commons.lang.time.DateUtils.isSameDay(dt, datProcess) == false)
				throw new FIXException("Invalid Date!!!");
		}
		catch (java.text.ParseException pe) {
			throw new FIXException("Invalid Date Format, must be DD/MM/YYYY !!!");
		}
		*/
		dt = new java.sql.Date(datProcess.getTime());
		
		
		/* [Begin] Locking Thread based on branch code */
			if (sLockMap.containsKey(cdBranch) == false)
				sLockMap.put(cdBranch, new String(cdBranch));
			
			String lockObject = sLockMap.get(cdBranch);
			synchronized (lockObject) {
				if (sLockMap.containsKey(cdBranch) == false)
					sLockMap.put(cdBranch, lockObject);
				
				// get data from FCR
				workerDao.getDownloadData(param6, idUser, dt, Float.parseFloat(param2), param3, param1);
				
				sLockMap.remove(cdBranch);
			}
		/* [ End ] Locking Thread based on branch code */
		

		Writer out = new OutputStreamWriter(new FileOutputStream(new File(PropertyPersister.dirFixOut, outputFile)));
		List<SknNgInOutDebitHeader> listHeader = headerDAO.list(param6);
		List<SknNgInOutDebitDetail> listDetail;
		SknNgInOutDebitHeader modelHeader;
		SknNgInOutDebitDetail modelDetail;
		SknNgInOutDebitFooter modelFooter;

		if (listHeader.size() == 0) {
			out.write("");
			out.close();
			return true;
		}
		
		SknNgOutwardDebitService service = new SknNgOutwardDebitService();
		
		// the header
		for (int h=0; h<listHeader.size(); h++) {
			modelHeader = listHeader.get(h);
			
			/* Reformatting & Write to Fix Length Buffer */
			out.write(service.formatFromInOutHeader(modelHeader));
			out.write(SknNgService.newLine());
			modelHeader.setExtractStatus("Y"); // update extract status
			
			
			listDetail = detailDAO.listByParentRecordNo(param6, modelHeader.getCompositeId().getRecordId());
			// write detail
			for (int i=0; i<listDetail.size(); i++) {
				modelDetail = listDetail.get(i);
				
				/* Reformatting & Write to Fix Length Buffer */
				String lineDetail = service.formatFromInOutDetail(modelDetail);
				out.write(lineDetail);
				out.write(SknNgService.newLine());
				modelDetail.setExtractStatus("Y"); // update extract status
				sbCRC.append(lineDetail);
			}
			
			
			// the footer
			modelFooter = footerDAO.get(param6, modelHeader.getCompositeId().getRecordId());
			crcValue = CRC16.CRC16(sbCRC.toString(), 0); // CRC for footer
			
			/* Reformatting & Write to Fix Length Buffer */
			out.write(service.formatFromInOutFooter(modelFooter, crcValue));
			out.write(SknNgService.newLine());
			modelFooter.setExtractStatus("Y"); // update extract status
			
			sbCRC.delete(0, sbCRC.length());
		}
		
		out.close();
		return true;
	}
	
}
