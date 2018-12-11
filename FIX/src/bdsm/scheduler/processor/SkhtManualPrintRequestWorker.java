package bdsm.scheduler.processor;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.service.AccountService;
import bdsm.model.SkhtDepositReq;
import bdsm.model.SkhtDepositResp;
import bdsm.model.SkhtPaidOffReq;
import bdsm.model.SkhtPaidOffResp;
import bdsm.model.SkhtPrintDep;
import bdsm.model.SkhtPrintPaid;
import bdsm.model.SkhtPrintReq;
import bdsm.rpt.dao.FixMasterReportDao;
import bdsm.rpt.model.FixMasterReport;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.FixTemplateMasterDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.FixTemplateMaster;
import bdsm.scheduler.model.FixTemplateMasterPK;
import bdsm.service.SiskohatService;
import bdsmhost.dao.SkhtDepositReqDAO;
import bdsmhost.dao.SkhtDepositRespDAO;
import bdsmhost.dao.SkhtPaidOffReqDAO;
import bdsmhost.dao.SkhtPaidOffRespDAO;
import bdsmhost.dao.SkhtPrintDepDAO;
import bdsmhost.dao.SkhtPrintPaidDAO;
import bdsmhost.dao.SkhtPrintReqDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import java.math.BigDecimal;


/**
 * @author bdsm
 */
public class SkhtManualPrintRequestWorker extends BaseProcessor {
	
	public SkhtManualPrintRequestWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean doExecute() throws Exception {
		String typeFix = (String) this.context.get(MapKey.typeFix);
		
		if (StatusDefinition.XTRACT.equals(typeFix)) {
			this.getLogger().debug("CONTEXT SKHT: " + this.context);
			String reportId = (String) this.context.get(MapKey.reportId);
			String accountNo = this.context.get(MapKey.param1).toString();
			String keyNo = this.context.get(MapKey.param2).toString();
			String cdBranch = this.context.get(MapKey.hdcdBranch).toString(); // branch code of userid
			
			SkhtPrintPaid skpPaid = new SkhtPrintPaid();
			SkhtPrintDep skpDep = new SkhtPrintDep();
			int type = 0; // 1 = initial deposit; 2 = paidoff
			
			BaBankMastDAO baBankMastDAO = new BaBankMastDAO(this.session);
			FixMasterReportDao fmrDAO = new FixMasterReportDao(this.session);
			FixSchedulerXtractDao fsxDAO = new FixSchedulerXtractDao(this.session);
			FixTemplateMasterDao ftmDAO = new FixTemplateMasterDao(this.session); 
			ChAcctMastDAO acctDAO = new ChAcctMastDAO(this.session);
			SkhtDepositReqDAO sdrDAO = new SkhtDepositReqDAO(this.session);
			SkhtDepositRespDAO sdrsDAO = new SkhtDepositRespDAO(this.session);
			SkhtPaidOffReqDAO sporDAO = new SkhtPaidOffReqDAO(this.session);
			SkhtPaidOffRespDAO sporsDAO = new SkhtPaidOffRespDAO(this.session);
			SkhtPrintReqDAO sprDAO = new SkhtPrintReqDAO(this.session);
			SkhtPrintDepDAO spdDAO = new SkhtPrintDepDAO(this.session);
			SkhtPrintPaidDAO sppDAO = new SkhtPrintPaidDAO(this.session);
			SiskohatService siskohatService = new SiskohatService(this.session);
			
			DateFormat formatref = new SimpleDateFormat("ddMMyyyyHHmmss");
			FixMasterReport report = fmrDAO.get(reportId);
			AccountService acctService = new AccountService();
			acctService.setFCRChAcctMastDAO(acctDAO);
			ChAcctMast account = acctService.getByAccountNo(accountNo);
			String hajiType = "";
			
			SkhtDepositReq depReq = sdrDAO.getByAcctNo(accountNo);  
            SkhtPaidOffReq paidOffReq = sporDAO.getByAcctNoAndPortionNo(accountNo, Long.parseLong(keyNo));
            SkhtDepositResp depResp = null;
            SkhtPaidOffResp paidOffResp = null;
			if (report.getNamScheduler().toUpperCase().indexOf("AWAL") > 0) {
				type = 1;
				
				if (depReq == null)
					throw new FIXException("Never requested before!!!");
				
                depResp = sdrsDAO.get(depReq.getRefNo());
				if ((depResp == null) && ((Integer.valueOf(depResp.getResponseCode()) != 0) && !depResp.getResponseDesc().toUpperCase().contains("TIMEOUT")))
					throw new FIXException("Please request Initial Deposit first!!!");
				
				if (Integer.valueOf(cdBranch).equals(depReq.getBpsBranchCode()) == false)
					throw new FIXException("Request Print must be in the same branch with Initial Deposit!!!");
				
				hajiType = depReq.getHajiType();
			}
			else if (report.getNamScheduler().toUpperCase().indexOf("PELUNASAN") > 0) {
				type = 2;
				
				if (paidOffReq == null)
					throw new FIXException("Never requested before!!!");
                
				paidOffResp = sporsDAO.get(paidOffReq.getRefNo());
				if ((paidOffResp == null) && ((Integer.valueOf(paidOffResp.getResponseCode()) != 0) && !paidOffResp.getResponseDesc().toUpperCase().contains("TIMEOUT")))
					throw new FIXException("Please request Posting PaidOff first!!!");
				
				if (Integer.valueOf(cdBranch).equals(paidOffReq.getBpsBranchCode()) == false)
					throw new FIXException("Request Print must be in the same branch with Posting PaidOff!!!");
				
				hajiType = paidOffReq.getHajiType();
			}
			
			
			SkhtPrintReq sPrintReq = new SkhtPrintReq();
			sPrintReq.setRefNo(accountNo + formatref.format(new Date()));
			sPrintReq.setAcctNo(accountNo);
			sPrintReq.setBpsBranchCode(Integer.parseInt(cdBranch));
			sPrintReq.setHajiType(hajiType);
			sPrintReq.setCifNumber(account.getCodCust());
			sPrintReq.setIdMaintainedBy((String) this.context.get(MapKey.hdUserid));
			sprDAO.insert(sPrintReq);
			
			if (type == 1)
				sPrintReq.setValidationNo(keyNo);
			else
				sPrintReq.setPortionNo(Integer.valueOf(keyNo));
			
			
			this.tx.commit();
			this.tx = this.session.beginTransaction();
			
			Date trxDate = baBankMastDAO.get().getBusinessDate();
			if (type == 1) {
				skpDep = siskohatService.depositPrintRequest(sPrintReq);
				if (skpDep.getRefNo() != null) {
					if (depReq.getBirthDate().compareTo(skpDep.getBirthDate()) != 0 && !(paidOffReq.getJamaahName().equalsIgnoreCase(skpDep.getJamaahName()))) {
						throw new FIXException("Data Not Valid!!!");
					}
					
					if ((Integer.valueOf(skpDep.getResponseCode().trim()) == 0) && depResp.getResponseDesc().toUpperCase().contains("TIMEOUT")) {
                        depResp.setAcctNo(depReq.getAcctNo());
                        depResp.setAcctVA(depReq.getAcctVA());
                        depResp.setAddress(depReq.getAddress());
                        depResp.setBirthDate(depReq.getBirthDate());
                        depResp.setBirthPlace(depReq.getBirthPlace());
                        depResp.setBpsBranchCode(depReq.getBpsBranchCode());
                        depResp.setCityCode(depReq.getCityCode());
                        depResp.setCityName(depReq.getCityName());
                        depResp.setCurrencyCode(depReq.getCurrencyCode());
                        depResp.setDistrictName(depReq.getDistrictName());
                        depResp.setEducationCode(depReq.getEducationCode());
                        depResp.setFatherName(depReq.getFatherName());
                        depResp.setGender(depReq.getGender());
                        depResp.setHajiType(depReq.getHajiType());
                        depResp.setInitialDeposit(depReq.getInitialDeposit());
                        depResp.setJamaahName(depReq.getJamaahName());
                        depResp.setMaritalStatus(depReq.getMaritalStatus());
                        depResp.setNIK(depReq.getNIK());
                        depResp.setOccupationCode(depReq.getOccupationCode());
                        depResp.setPostalCode(depReq.getPostalCode());
                        depResp.setProvinceCode(depReq.getProvinceCode());
                        depResp.setResponseCode("000000");
                        depResp.setResponseDesc("SUCCESS");
                        depResp.setValidationNo(skpDep.getValidationNo());
                        depResp.setVillageName(depReq.getVillageName());
                        depResp.setRefNo(depResp.getRefNo());

                        sdrsDAO.update(depResp);
                        this.tx.commit();
                        this.tx = this.session.beginTransaction();
                        this.session.close();
                    }

                    skpDep.setTrxDate(trxDate);
                    skpDep.setIdMaintainedBy(sPrintReq.getIdMaintainedBy());
                    spdDAO.insert(skpDep);

                    this.tx.commit();
                    this.tx = this.session.beginTransaction();
                    

                    if (Integer.parseInt(skpDep.getResponseCode().trim()) != 0)
                        throw new FIXException(skpDep.getResponseCode() + " - " + skpDep.getResponseDesc());
				}
                else
                    throw new FIXException(skpDep.getResponseCode() + " - " + skpDep.getResponseDesc());
			}
			else if (type == 2) {
				skpPaid = siskohatService.paidOffPrintRequest(sPrintReq);
				if (skpPaid.getRefNo() != null) {
					
					if (depReq.getBirthDate().compareTo(skpPaid.getBirthDate()) != 0 && !(depReq.getJamaahName().equalsIgnoreCase(skpPaid.getJamaahName()))){
						throw new FIXException("Data Not Valid!!!");
					}
                    
					if ((Integer.valueOf(skpPaid.getResponseCode().trim()) == 0) && paidOffResp.getResponseDesc().toUpperCase().contains("TIMEOUT")) {
                        paidOffResp.setAcctNo(depReq.getAcctNo());
                        paidOffResp.setAcctVA(depReq.getAcctVA());
                        paidOffResp.setAddress(depReq.getAddress());
                        paidOffResp.setBirthDate(depReq.getBirthDate());
                        paidOffResp.setBirthPlace(depReq.getBirthPlace());
                        paidOffResp.setBpsBranchCode(depReq.getBpsBranchCode());
                        paidOffResp.setCityName(depReq.getCityName());
                        paidOffResp.setCurrencyCode(depReq.getCurrencyCode());
                        paidOffResp.setDistrictName(depReq.getDistrictName());
                        paidOffResp.setBranchName(skpPaid.getBranchName());
                        paidOffResp.setFatherName(depReq.getFatherName());
                        paidOffResp.setGender(depReq.getGender());
                        paidOffResp.setHajiType(depReq.getHajiType());
                        paidOffResp.setInitialDeposit(depReq.getInitialDeposit());
                        paidOffResp.setJamaahName(depReq.getJamaahName());
                        paidOffResp.setBranchAddress(skpPaid.getBranchAddress());
                        paidOffResp.setEmbarkasi(skpPaid.getEmbarkasi());
                        paidOffResp.setPortionNo(skpPaid.getPortionNo().longValue());
                        paidOffResp.setPostalCode(depReq.getPostalCode());
                        paidOffResp.setAgeInMonth(skpPaid.getAgeInMonth());
                        paidOffResp.setAgeInYear(skpPaid.getAgeInYear());
                        paidOffResp.setResponseCode("000000");
                        paidOffResp.setResponseDesc("SUCCESS");
                        paidOffResp.setVillageName(depReq.getVillageName());
                        paidOffResp.setRefNo(paidOffReq.getRefNo());

                        sdrsDAO.update(paidOffResp);
                        this.tx.commit();
                        this.tx = this.session.beginTransaction();
                        this.session.close();
					}
                    skpPaid.setTrxDate(trxDate);
                    skpPaid.setIdMaintainedBy(sPrintReq.getIdMaintainedBy());
                    sppDAO.insert(skpPaid);

                    this.tx.commit();
                    this.tx = this.session.beginTransaction();

                    if (Integer.parseInt(skpPaid.getResponseCode().trim()) != 0)
                        throw new FIXException(skpPaid.getResponseCode() + " - " + skpPaid.getResponseDesc());
                    }
                else
                    throw new FIXException(skpPaid.getResponseCode() + " - " + skpPaid.getResponseDesc());
			}
			
			FixSchedulerXtract fsx = fsxDAO.get((type == 1)? "RPT_SKHT_SETORAN_AWAL": "RPT_SKHT_PELUNASAN");
			FixTemplateMasterPK pk = new FixTemplateMasterPK();
			pk.setIdTemplate(fsx.getFixSchedulerPK().getIdTemplate());
			FixTemplateMaster ftm = ftmDAO.get(pk);
			
			Map<String, Object> mapReport = new HashMap<String, Object>(0);
			Map<String, Object> mapReportParam = new HashMap<String, Object>(this.context);
			
			mapReport.put(MapKey.reportId, ftm.getRptFileTemplate());
			mapReport.put(MapKey.reportFileName, mapReportParam.remove(MapKey.reportFileName));
			mapReport.put(MapKey.reportFormat, mapReportParam.remove(MapKey.reportFormat));
			mapReport.put(MapKey.session, mapReportParam.remove(MapKey.session));
			mapReportParam.put(MapKey.param1, accountNo);
			mapReportParam.put(MapKey.param2, keyNo);
			mapReportParam.put(MapKey.param3, hajiType);
			mapReportParam.put(MapKey.param4, sPrintReq.getRefNo());
			mapReportParam.remove(MapKey.idScheduler);
			mapReportParam.remove(MapKey.javaClass);
			mapReportParam.remove(MapKey.typeFix);
			mapReport.put(MapKey.reportParam, mapReportParam);
			
			this.getLogger().debug("mapReportParam: " + mapReportParam);
			
			JasperGenRpt jgr = new JasperGenRpt(mapReport);
			jgr.generateReport();
		}
		
		return true;
	}

}
