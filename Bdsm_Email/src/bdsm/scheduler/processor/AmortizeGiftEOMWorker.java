package bdsm.scheduler.processor;


import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import bdsm.scheduler.MapKey;
import bdsm.service.AmortizeGiftService;
import bdsm.util.oracle.DateUtility;
import bdsmhost.fcr.dao.BaBankMastDAO;


public class AmortizeGiftEOMWorker extends BaseProcessor {
	
	public AmortizeGiftEOMWorker(Map<String, ? extends Object> context) {
		super(context);
	}

	
	@Override
	protected boolean doExecute() throws Exception {
		BaBankMastDAO bankMastDAO = new BaBankMastDAO(this.session);
		Date businessDate, processDate, lastDateOfTheMonth;
		String accountNo;
		
		businessDate = bankMastDAO.get().getBusinessDate();
		
		if (this.context.get(MapKey.param1) != null)
			processDate = DateUtility.DATE_FORMAT_DDMMYYYY.parse(this.context.get(MapKey.param1).toString().replaceAll("[/-]", ""));
		else
			processDate = businessDate;
		
		accountNo = (String) this.context.get(MapKey.param2);
		
		lastDateOfTheMonth = DateUtility.getLastDayOfTheMonth(processDate);
		
		this.getLogger().info("processDate: " + processDate);
		this.getLogger().info("lastDateOfTheMonth: " + lastDateOfTheMonth);
		this.getLogger().info("businessDate: " + businessDate);
		
		if (DateUtils.isSameDay(processDate, lastDateOfTheMonth)) {
			this.getLogger().info("processDate: " + DateUtility.DATE_FORMAT_DDMMMYYYY.format(processDate));
			
			AmortizeGiftService srvc = new AmortizeGiftService();
			srvc.setSession(this.session);
			srvc.setBaCcyCodeDAO(new bdsmhost.fcr.dao.BaCcyCodeDAO(null));
			srvc.setChAcctMastDAO(new bdsmhost.fcr.dao.ChAcctMastDAO(null));
			srvc.setChProdMastDAO(new bdsmhost.fcr.dao.ChProdMastDAO(null));
			srvc.setAmortizeGLNoDAO(new bdsmhost.dao.AmortizeGLNoDAO(null));
			srvc.setAmortizeProgramMasterDAO(new bdsmhost.dao.AmortizeProgramMasterDAO(null));
			srvc.setAmortizeProgramDetailDAO(new bdsmhost.dao.AmortizeProgramDetailDAO(null));
			srvc.setAmortizeAccountDAO(new bdsmhost.dao.AmortizeAccountDAO(null));
			srvc.setAmortizeRecordDAO(new bdsmhost.dao.AmortizeRecordDAO(null));
			srvc.setTmpTxnUploadHeaderDAO(new bdsm.scheduler.dao.TmpTxnUploadHeaderDAO(null));
			srvc.setTmpTxnUploadDetailDAO(new bdsm.scheduler.dao.TmpTxnUploadDetailDAO(null));
			srvc.setTmpTxnUploadFooterDAO(new bdsm.scheduler.dao.TmpTxnUploadFooterDAO(null));
			srvc.setTmpGefuResponsDAO(new bdsm.scheduler.dao.TmpGefuResponsDao(null));
			
			srvc.doAmortizeEOM(processDate, businessDate, accountNo);
			
			return true;
		}
		else {
			this.getLogger().info(DateUtility.DATE_FORMAT_DDMMMYYYY.format(processDate) + " is not EOM");
		}
		
		return false;
	}
	
}
