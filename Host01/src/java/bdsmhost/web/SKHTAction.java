package bdsmhost.web;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.fcr.model.BaBankMast;
import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.model.CiCustMastPK;
import bdsm.fcr.service.CustomerService;
import bdsm.fcr.service.DataMasterService;
import bdsm.model.Parameter;
import bdsm.model.SkhtBranchRegionMapping;
import bdsm.model.SkhtDepositReq;
import bdsm.model.SkhtDepositResp;
import bdsm.model.SkhtPaidOffInqReq;
import bdsm.model.SkhtPaidOffInqResp;
import bdsm.model.SkhtPaidOffReq;
import bdsm.model.SkhtPaidOffResp;
import bdsm.model.SkhtPrintDep;
import bdsm.model.SkhtPrintPaid;
import bdsm.model.SkhtPrintReq;
import bdsm.model.SkhtViewData;
import bdsm.model.SkhtViewDataPelunasan;
import bdsm.scheduler.PropertyPersister;
import bdsm.service.SiskohatService;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsmhost.dao.ParameterDao;
import bdsmhost.dao.SkhtBranchRegionMappingDAO;
import bdsmhost.dao.SkhtDepositReqDAO;
import bdsmhost.dao.SkhtDepositRespDAO;
import bdsmhost.dao.SkhtPaidOffReqDAO;
import bdsmhost.dao.SkhtPaidOffRespDAO;
import bdsmhost.dao.SkhtPrintDepDAO;
import bdsmhost.dao.SkhtPrintPaidDAO;
import bdsmhost.dao.SkhtPrintReqDAO;
import bdsmhost.dao.TransactionParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.CiCustMastDao;
import bdsmhost.fcr.dao.DataMasterDAO;
import freemarker.core.ParseException;


/**
 *
 * @author v00022309
 */
@SuppressWarnings("serial")
public class SKHTAction extends ModelDrivenBaseHostAction<Object> {
	private final SimpleDateFormat DATE_SKHT_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
	private final SimpleDateFormat DATE_INDO_FORMATTER = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
	private ChAcctMast chAcctMast;
	private CiCustMast ciCustMast;
	private String acctNo;
	private String validationNo;
	private Date datBirthCust;
	private Integer codOrgBrn;
	private Integer codCust;
	private Integer cifNo;
	private Boolean sPrintDep;
	private String birthPlace;
	private String hajiType;
	private SkhtViewData svd;
	private SkhtViewDataPelunasan svdp;
	private SkhtPaidOffInqResp sktResp;
	private SkhtPaidOffInqReq sktPad;
	private SkhtPaidOffReq paidreq;
	private SkhtPaidOffResp paidresp;
	private SkhtPrintPaid skpPrint;
	private SkhtDepositResp skhtDepResp;
	private SkhtPrintDep skpDep;
	private SkhtPrintReq skPrintReq;
	private String balance;
	private String noPorsi;
	private String keys;
	
	
	public String checkCutOff() {
		ParameterDao paramDAO = new ParameterDao(this.getHSession());
		
		Calendar now = Calendar.getInstance();
		Calendar cutOff = Calendar.getInstance();
		String[] strCutOff = paramDAO.get("SISKOHAT.CUTOFF").getStrVal().split(":");
		cutOff.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strCutOff[0]));
		cutOff.set(Calendar.MINUTE, Integer.parseInt(strCutOff[1]));
		
		if (now.after(cutOff))
			this.putToObjData("cutoff", Boolean.TRUE);
		
		return SUCCESS;
	}
	
	public String retrieveDepositViewData() throws Exception {
		ParameterDao paramDAO = new ParameterDao(this.getHSession());
		SkhtDepositReqDAO sdrDAO = new SkhtDepositReqDAO(this.getHSession());
		SkhtDepositRespDAO sdrsDAO = new SkhtDepositRespDAO(this.getHSession());
		
		SkhtDepositReq sdr = null;
		SkhtDepositResp sdrs = null;
		String branchStateCompareErrorCode = this.compareValueDataSiskohatByCodeBranch(this.codOrgBrn);
		
		if (branchStateCompareErrorCode != null)
			return quitOfError(branchStateCompareErrorCode);
		
		sdr = sdrDAO.getByAcctNo(this.acctNo);
		if (sdr != null)
			sdrs = sdrsDAO.get(sdr.getRefNo());
		
		if ("add".equals(this.getStrData().get("mode"))) {
			if ((sdr != null) && (sdrs !=null) && (Integer.valueOf(sdrs.getResponseCode()) == 0))
				return quitOfError("Already requested");
			
			this.retrieveValueDataSiskohatByAcctNo();
			
			String allowedProductCode = paramDAO.get("SISKOHAT.Haji.ProductCode").getStrVal().replace(" ", "");
			
			if ((";" + allowedProductCode + ";").contains(";" + this.chAcctMast.getCodProd().toString() + ";") == false)
				return quitOfError("error.account.invalid");
			else if (this.svd == null)
				return quitOfError("error.1");
			/*else if (this.svd.getTxtOccuptnCatCore() == null)
				return quitOfError("skht.occupation.empty");*/
			else {
				this.putToObjData("hajiId", "");
				this.putToObjData("ciCustMast", this.ciCustMast);
				this.putToStrData("cifNo", this.cifNo.toString());
			}
			
			BaBankMastDAO baBankMastDAO = new BaBankMastDAO(this.getHSession());
			Date dat1 = baBankMastDAO.get().getDatProcess();
			int age = CustomerService.getAge((CiCustMast) this.getObjData().remove("ciCustMast"), dat1);
			
			this.getLogger().info("Age: " + age);
			try {
				if (age < Integer.parseInt(PropertyPersister.sKHTAge.get("AWAL")))
					throw new Exception("skht.underage");
			}
			catch (Exception e) {
				this.getLogger().error(e, e);
				return quitOfError("skht.underage");
			}
		}
		else if("inquiry".equals(this.getStrData().get("mode"))) {
			if (sdr == null)
				return quitOfError("Have not requested yet");
			else {
				SkhtPrintReqDAO sprDAO = new SkhtPrintReqDAO(this.getHSession());
				SkhtPrintReq spr = sprDAO.getByAcctNo(this.acctNo);
				
				Parameter param = paramDAO.get("SISKOHAT.Haji.Regular");
				
				this.svd = new SkhtViewData();
				this.svd.setNamCustFull(sdr.getJamaahName());
				this.svd.setCodNatId(sdr.getNIK());
				this.svd.setBirthPlace(sdr.getBirthPlace());
				this.svd.setDatBirthCust(DATE_SKHT_FORMATTER.format(sdr.getBirthDate()));
				this.svd.setTxtCustAdrAdd1(sdr.getAddress());
				this.svd.setTxtCustAdrZip(sdr.getPostalCode().toString());
				this.svd.setDesaName(sdr.getVillageName());
				this.svd.setKecamatanName(sdr.getDistrictName());
				this.svd.setNamPermadrCity(sdr.getCityName());
				this.svd.setNamPermadrState(this.pickListDesc("siskohatState", sdr.getProvinceCode()));
				this.svd.setSetoranAwal((param!=null)? param.getValue().toString(): "");
				this.svd.setTerbilang((param!=null)? param.getStrVal(): "");
				this.svd.setCodCustSex(sdr.getGender());
				this.svd.setTxtCustSex(this.pickListDesc("siskohatGender", sdr.getGender()));
				this.svd.setAcctNoInq(sdr.getAcctNo());
				this.svd.setCurrencyCode(sdr.getCurrencyCode().toString());
				this.svd.setTxtProfessCat(sdr.getOccupationCode());
				this.svd.setOccupationdesc(this.pickListDesc("siskohatOccupation", sdr.getOccupationCode()));
				this.svd.setMarstatdesc(this.pickListDesc("siskohatMaritalStatus", sdr.getMaritalStatus()));
				this.svd.setCodCustEducn(sdr.getEducationCode().toString());
				this.svd.setTxtCustEducn(this.pickListDesc("siskohatEducation", sdr.getEducationCode()));
				this.svd.setCodCustMarstat(sdr.getMaritalStatus());
				this.svd.setFatherName(sdr.getFatherName());
				
				if ((spr != null) && (spr.getValidationNo() != null)) {
					SkhtPrintDepDAO spdDAO = new SkhtPrintDepDAO(this.getHSession());
					SkhtPrintDep spd = spdDAO.get(spr.getRefNo());
					
					this.svd.setStatusInq(spd.getResponseCode() + "-" + spd.getResponseDesc());
				}
				else
					this.svd.setStatusInq(sdrs.getResponseCode() + "-" + sdrs.getResponseDesc());
				
				this.putToObjData("hajiId", this.pickListDesc("siskohatHajiType", sdr.getHajiType()));
			}
		}
		this.svd.setTerbilang(this.svd.getTerbilang() + " RUPIAH");
		this.putToStrData("datBirthCust", DATE_INDO_FORMATTER.format(DATE_SKHT_FORMATTER.parse(this.svd.getDatBirthCust())));
		this.putToStrData("setoranAwal", this.svd.getSetoranAwal());
		
		this.chAcctMast = null;
		this.ciCustMast = null;
		this.cifNo = null;
		
		return SUCCESS;
	}
	
	private void retrieveValueDataSiskohatByAcctNo() throws Exception {
		ChAcctMastDAO acctDao = new ChAcctMastDAO(this.getHSession());
		this.chAcctMast = acctDao.getActiveAccount(this.acctNo);
		
		if (this.chAcctMast != null) {
			CiCustMastDao custDAO = new CiCustMastDao(this.getHSession());
			CiCustMastPK custPk = new CiCustMastPK(this.chAcctMast.getCodCust(), "A", 11);
			this.ciCustMast = custDAO.get(custPk);
			this.cifNo = this.ciCustMast.getCiCustMastPK().getCodCustId();
			
			SiskohatDAO skhtDAO = new SiskohatDAO(this.getHSession());
			Map<String, Object> viewData = skhtDAO.getViewDataScreen(this.acctNo, this.cifNo);
			
			if (viewData != null) {
				this.svd = new SkhtViewData();
				this.svd.setAcctNoInq(this.acctNo);
				ClassConverterUtil.MapToClass(viewData, this.svd);
				this.getLogger().debug("modelList: " + viewData);
				this.getLogger().debug("svdsct1: " + this.svd);
			}
		}
	}
	
	public String postingInitialDeposit() throws Exception {
		BaBankMast baBankMast = new BaBankMastDAO(this.getHSession()).get();
		SkhtDepositReq sdr = new SkhtDepositReq();
		SkhtDepositReqDAO sdrDAO = new SkhtDepositReqDAO(this.getHSession());
		SkhtDepositRespDAO sdrsDAO = new SkhtDepositRespDAO(this.getHSession());
		SiskohatService siskohatService = new SiskohatService(this.getHSession());
		
		this.getLogger().info("account: " + this.svd.getAcctNoInq());
		this.getLogger().info("name full: " + this.svd.getNamCustFull());
		this.getLogger().info("birth place: " + this.svd.getBirthPlace());
		this.getLogger().info("birth date: " + this.svd.getDatBirthCust());
		this.getLogger().info("currency code: " + this.svd.getCurrencyCode());
		this.getLogger().info("Haji Type: " + this.getHajiType());
		this.getLogger().info("codcustid: " + this.getFromStrData("cifNo"));
		this.getLogger().debug("setoran awal: " + this.svd.getSetoranAwal());
		ParameterDao pfDao = new ParameterDao(getHSession());
		
		BigDecimal initDeposit = new BigDecimal(pfDao.get("SISKOHAT.Haji.Regular").getValue());
		this.getLogger().debug("setoran: " + initDeposit);
		
		//insert to deposit request
		sdr.setHajiType(this.getHajiType());
		sdr.setTrxDate(baBankMast.getBusinessDate());
		sdr.setJamaahName(this.svd.getNamCustFull());
		sdr.setNIK(this.svd.getCodNatId());
		sdr.setBirthPlace(this.svd.getBirthPlace());
		sdr.setBirthDate(DATE_SKHT_FORMATTER.parse(this.svd.getDatBirthCust()));
		sdr.setAddress(this.svd.getTxtCustAdrAdd1());
		sdr.setPostalCode(Integer.valueOf(this.svd.getTxtCustAdrZip()));
		sdr.setVillageName(this.svd.getDesaName());
		sdr.setDistrictName(this.svd.getKecamatanName());
		sdr.setCityName(this.svd.getNamPermadrCity());
		sdr.setGender(this.svd.getCodCustSex());
		sdr.setAcctNo(this.svd.getAcctNoInq());
		sdr.setCurrencyCode(Integer.valueOf(this.svd.getCurrencyCode()));
		sdr.setInitialDeposit(initDeposit);
		sdr.setAcctVA("");
		sdr.setBpsBranchCode(this.codOrgBrn);
		sdr.setProvinceCode(Integer.parseInt(this.svd.getCodPermadrState()));
		sdr.setCityCode(Integer.parseInt(this.svd.getCodPermadrCity().toString()));
		sdr.setOccupationCode(this.svd.getTxtProfessCat());
		sdr.setEducationCode(Integer.valueOf(this.svd.getCodCustEducn()));
		sdr.setMaritalStatus(this.svd.getCodCustMarstat());
		sdr.setFatherName(this.svd.getFatherName());
		sdr.setCifNumber(Integer.parseInt(this.getFromStrData("cifNo")));
		sdr.setRefNo(siskohatService.generateRefNo(sdr.getAcctNo()));
		sdr.setIdMaintainedBy(this.getFromStrData("idMaintainedBy"));
		sdr.setIdMaintainedSpv(this.getFromStrData("idMaintainedSpv"));
		
		this.getLogger().info("[Begin] Insert table Deposit Request");
		sdrDAO.insert(sdr);
		
		this.tx.commit();
		this.tx = this.getHSession().beginTransaction();
		this.getLogger().info("[Finish] Insert table Deposit Request");
		
		try {
			this.skhtDepResp = siskohatService.depositRequest(sdr);
			if (this.skhtDepResp.getRefNo() != null) {
				this.skhtDepResp.setIdMaintainedBy(sdr.getIdMaintainedBy());
				this.skhtDepResp.setIdMaintainedSpv(sdr.getIdMaintainedSpv());
				sdrsDAO.insert(this.skhtDepResp);
			}
		}
		catch (Exception ex) {
			this.getLogger().info("Exception: " + ex, ex);
			return this.quitOfError(this.getErrorMessageFromException(ex));
		}
		finally {
			this.svd = null;
			super.putToObjData("hajiId", this.pickListDesc("siskohatHajiType", sdr.getHajiType()));
		}
		
		this.getLogger().info("SKHT Response: " + this.skhtDepResp);
		return SUCCESS;
	}
	
	public String printInitialDeposit() throws Exception {
		BaBankMast baBankMast = new BaBankMastDAO(this.getHSession()).get();
		SkhtPrintReq sPrintReq = new SkhtPrintReq();
		SkhtPrintReqDAO sprDAO = new SkhtPrintReqDAO(this.getHSession());
		SkhtPrintDepDAO spdDAO = new SkhtPrintDepDAO(this.getHSession());
		SiskohatService siskohatService = new SiskohatService(this.getHSession());
		
		//insert
		sPrintReq.setAcctNo(this.acctNo);
		sPrintReq.setValidationNo(this.validationNo);
		sPrintReq.setBpsBranchCode(this.codOrgBrn);
		sPrintReq.setHajiType(this.hajiType);
		sPrintReq.setCifNumber(Integer.parseInt(this.getFromStrData("cifNo")));
		sPrintReq.setRefNo(siskohatService.generateRefNo(sPrintReq.getAcctNo()));
		sPrintReq.setIdMaintainedBy(this.getFromStrData("idMaintainedBy"));
		
		this.getLogger().info("Reff No: " + sPrintReq.getRefNo());
		this.getLogger().info("Account: " + sPrintReq.getAcctNo());
		this.getLogger().info("ValidationNo: " + sPrintReq.getValidationNo());
		this.getLogger().info("BpsBranchCode: " + sPrintReq.getBpsBranchCode());
		this.getLogger().info("getHajiType: " + this.hajiType);
		this.getLogger().debug("cifNo: " + sPrintReq.getCifNumber());
		
		this.getLogger().info("[START] Insert table PRINT Deposit");
		this.setsPrintDep((Boolean) sprDAO.insert(sPrintReq));
		
		this.tx.commit();
		this.tx = this.getHSession().beginTransaction();
		this.getLogger().info("[FINISH] Insert table PRINT Deposit");
		
		try {
			this.skpDep = siskohatService.depositPrintRequest(sPrintReq);
			this.skpDep.setTrxDate(baBankMast.getBusinessDate());
			
			if (this.skpDep.getRefNo() != null) {
				this.skpDep.setIdMaintainedBy(sPrintReq.getIdMaintainedBy());
				spdDAO.insert(this.skpDep);
				this.tx.commit();
				this.tx = this.getHSession().beginTransaction();
				
				spdDAO.evictObjectFromPersistenceContext(this.skpDep);
				this.skpDep.setGender(this.pickListDesc("siskohatGender", this.skpDep.getGender()));
			}
		}
		catch (Exception ex) {
			this.getLogger().info("Exception: " + ex, ex);
			return this.quitOfError(this.getErrorMessageFromException(ex));
		}
		
		this.getLogger().info("skpDep: " + this.skpDep);
		return SUCCESS;
	}
	
	public String retrievePaidOffViewData() throws Exception {
		SkhtPaidOffReqDAO sprDAO = new SkhtPaidOffReqDAO(this.getHSession());
		SkhtPaidOffRespDAO sprsDAO = new SkhtPaidOffRespDAO(this.getHSession());
		SiskohatService siskohatService = new SiskohatService(this.getHSession());
		SkhtDepositReqDAO sdrDAO = new SkhtDepositReqDAO(this.getHSession());
		ParameterDao paramDAO = new ParameterDao(this.getHSession());
		Parameter param = paramDAO.get("SISKOHAT.Haji.Regular");
		SkhtDepositReq sdr = null;
		this.svdp = new SkhtViewDataPelunasan();
		
		this.getLogger().debug("skhtpaidoffinqreq: " + this.sktPad);
		
		this.paidreq = sprDAO.getByAcctNoAndPortionNo(this.sktPad.getAcctNo(), this.sktPad.getPortionNo());
		if (this.paidreq != null)
			this.paidresp = sprsDAO.get(this.paidreq.getRefNo());
		
		this.retrieveAccountData();
		
		if (this.chAcctMast == null)
			return quitOfError("error.account.invalid");
		
		this.getLogger().debug("mode: " + this.getStrData().get("mode"));
		if ("add".equals(this.getStrData().get("mode"))) {
			if ((this.paidreq != null) && (this.paidresp != null) && (Integer.valueOf(this.paidresp.getResponseCode()) == 0))
				return quitOfError("Already requested");
			
			SkhtDepositReq depreq = sdrDAO.getByAcctNo(this.sktResp.getAcctNo());
			if (depreq == null) {
				this.svdp.setErrorCode("Have not requested initial deposit yet!!!");
				return ERROR;
			}
			
			BaBankMastDAO babank = new BaBankMastDAO(getHSession());
			BaBankMast baBankMast = babank.get();
			int age = CustomerService.getAge(this.ciCustMast, baBankMast.getDatProcess());
			
			this.getLogger().info("Check age: " + age);
			try {
				if (age < Integer.parseInt(PropertyPersister.sKHTAge.get("PELUNASAN")))
					return quitOfError("skht.underage");
			}
			catch (Exception e) {
				return quitOfError("Invalid age in BDSM configuration");
			}
			
			this.getLogger().debug("codOrgBrn: " + this.sktPad.getBpsBranchCode());
			String branchStateCompareErrorCode = this.compareValueDataSiskohatByCodeBranch(this.sktPad.getBpsBranchCode());
			this.getLogger().debug("Branch state compare error code: " + branchStateCompareErrorCode);
			if (branchStateCompareErrorCode != null) {
				this.svdp.setErrorCode(branchStateCompareErrorCode);
				return quitOfError(branchStateCompareErrorCode);
			}
			else {
				try {
					this.sktPad.setRefNo(siskohatService.generateRefNo(this.sktPad.getAcctNo()));
					this.sktPad.setTrxDate(baBankMast.getBusinessDate());
					this.getLogger().debug("Request WS3: " + this.sktPad.toString());
					
					SkhtPaidOffReqDAO reqDAO = new SkhtPaidOffReqDAO(this.getHSession());
					reqDAO.insert(this.sktPad);
					this.tx.commit();
					this.tx = this.getHSession().beginTransaction();
					
					this.sktResp = siskohatService.paidOffInquiryRequest(this.sktPad);
					this.sktResp.setTrxDate(this.sktPad.getTrxDate());
					
					SkhtPaidOffRespDAO respDAO = new SkhtPaidOffRespDAO(this.getHSession());
					respDAO.insert(this.sktResp);
					this.getLogger().debug("sktResponse: " + this.sktResp);
					
					if (0 != Integer.parseInt(this.sktResp.getResponseCode()))
						return quitOfError(this.sktResp.getResponseCode() + "-" + this.sktResp.getResponseDesc());
					
					sdr = sdrDAO.getByAcctNo(this.sktPad.getAcctNo());
					this.svdp = (SkhtViewDataPelunasan) ClassConverterUtil.ReferenceClass(this.sktResp, this.svdp);
					TransactionParameterDao trxParamDAO = new TransactionParameterDao(getHSession());
					String acctNoTo = trxParamDAO.getByModuleName("SISKOHAT").getAccountNo().split(";")[1];
					this.svdp.setAcctNoTo(acctNoTo);
					this.getLogger().debug("acctNoTo: " + acctNoTo.toString());
					
					// ClassConverterUtil.MapToClass(lunasModel, svdp);
					this.getLogger().debug("cek Deposit Req: " + sdr);
					this.getLogger().debug("WS3 before svdp: " + this.svdp);
					this.getLogger().debug("Haji Type Description: "+ this.svdp.getHajiTypeDesc());
					
					this.svdp.setAcctNoInq(sdr.getAcctNo());
					this.svdp.setBirthPlace(sdr.getBirthPlace());
					this.svdp.setDatBirthCust(DATE_SKHT_FORMATTER.format(sdr.getBirthDate()));
					this.svdp.setHajiTypeDesc(this.pickListDesc("siskohatHajiType", this.svdp.getHajiType()));
					this.svdp.setCodNatId(sdr.getNIK());
					this.svdp.setTxtCustAdrAdd1(sdr.getAddress());
					this.svdp.setFatherName(sdr.getFatherName());
					this.svdp.setCodCustEducn(this.pickListDesc("siskohatEducation", sdr.getEducationCode()));
					this.svdp.setOccupation(this.pickListDesc("siskohatOccupation", sdr.getOccupationCode()));
					this.svdp.setMaritalStatus(this.pickListDesc("siskohatMaritalStatus", sdr.getMaritalStatus()));
					//this.svdp.setSetoranAwal(sdr.getInitialDeposit().toString());
					this.svdp.setSetoranAwal(this.sktResp.getInitialDeposit().toString());
					this.svdp.setBalance(this.chAcctMast.getBalAvailable().toString());
					this.svdp.setSisaLunas(this.sktResp.getResidualCost().toString());
					this.svdp.setCurrencyCode(this.sktResp.getCurrencyCode().toString());
					this.svdp.setResponseCode(sktResp.getResponseCode());
					this.svdp.setKloter(this.sktResp.getKloter());
					this.svdp.setEmbarkasi(this.sktResp.getEmbarkasi());
					this.svdp.setPortionNo(this.sktResp.getPortionNo().toString());
					this.svdp.setTerbilang((param!=null)? param.getValue().toString(): "");
					this.svdp.setPIHKName(this.sktResp.getPIHKName());
					this.svdp.setPIHKCode(this.sktResp.getPIHKCode().toString());
					this.svdp.setBpsBranchCode(this.sktPad.getBpsBranchCode().toString());
					this.svdp.setFlgPaidOff(this.sktResp.getFlgPaidOff());
					this.svdp.setDelayYear(this.sktResp.getDelayYear());
					this.svdp.setBpihInIDR(this.sktResp.getBpihInIDR());
					
					this.putToStrData("cifNo", this.cifNo.toString());
					
					this.getLogger().debug("WS3 after svdp: " + this.svdp);
					this.getLogger().debug("WS3 after sktResp: " + this.sktResp);
				}
				catch (Exception e) {
					this.getLogger().error("exception add for setData: " + e, e);
					return this.quitOfError(this.getErrorMessageFromException(e));
				}
			}
		}
		else if ("inquiry".equals(this.getStrData().get("mode"))) {
			if ((this.paidreq == null) || (this.paidresp == null))
				return quitOfError("Have not requested yet");
			else {
				sdr = sdrDAO.getByAcctNo(this.sktResp.getAcctNo());
				this.getLogger().debug("Inquiry paidresp: " + this.paidresp);
				this.getLogger().debug("Inquiry paidreq: " + this.paidreq);
				this.getLogger().debug("Inquiry chAcctMast:" + this.chAcctMast);
				this.getLogger().debug("Inquiry Deposit Req:" + sdr);
				
				SkhtPrintReqDAO skhtPrintReqDAO = new SkhtPrintReqDAO(this.getHSession());
				SkhtPrintReq spr = skhtPrintReqDAO.getByAcctNo(this.sktPad.getAcctNo());
				
				try {
					this.svdp.setAcctNo(this.paidreq.getAcctNo());
					this.svdp.setJamaahName(this.paidreq.getJamaahName());
					this.svdp.setBirthPlace((sdr.getBirthPlace()==null)? "": sdr.getBirthPlace());
					this.svdp.setDatBirthCust(DATE_SKHT_FORMATTER.format((sdr.getBirthDate() == null)? "": sdr.getBirthDate()));
					this.svdp.setHajiType(this.paidreq.getHajiType());
					this.svdp.setCodNatId(sdr.getNIK());
					this.svdp.setTxtCustAdrAdd1(sdr.getAddress());
					this.svdp.setFatherName(sdr.getFatherName());
					this.svdp.setCodCustEducn(this.pickListDesc("siskohatEducation", sdr.getEducationCode()));
					this.svdp.setOccupation(this.pickListDesc("siskohatOccupation", sdr.getOccupationCode()));
					this.svdp.setMaritalStatus(this.pickListDesc("siskohatMaritalStatus", sdr.getMaritalStatus()));
					this.svdp.setSetoranAwal(this.paidreq.getInitialDeposit().toString());
					this.svdp.setBalance(this.chAcctMast.getBalAvailable().toString());
					this.svdp.setSisaLunas(this.paidreq.getResidualCost().toString());
					this.svdp.setCurrencyCode(this.paidreq.getCurrencyCode().toString());
					this.svdp.setPortionNo(this.paidreq.getPortionNo().toString());
					this.svdp.setTerbilang((param != null)? param.getValue().toString(): "");
					// this.svdp.setPIHKName(this.paidreq.getPIHKName());
					this.svdp.setAcctNoInq(this.paidreq.getAcctNo());
					this.svdp.setAcctNoTo("-");
					this.svdp.setUsdExchange(this.paidreq.getUSDExchange().toString());
					this.svdp.setBpihCost(this.paidreq.getBPIHCost().toString());
					this.svdp.setBpihInIDR(this.paidreq.getBPIHInIDR().toString());
					
					if ((spr != null) && (spr.getPortionNo() != null)) {
						SkhtPrintPaidDAO sppDAO = new SkhtPrintPaidDAO(this.getHSession());
						SkhtPrintPaid spp = sppDAO.get(spr.getRefNo());
						
						this.svdp.setStatusInq(spp.getResponseCode() + "-" + spp.getResponseDesc());
					}
					else
						this.svdp.setStatusInq(this.paidresp.getResponseCode() + "-" + this.paidresp.getResponseDesc());
				}
				catch (Exception e) {
					// this.svdp.setErrorCode("0001");
					this.getLogger().error("exception inquiry for setData: " + e, e);
					return this.quitOfError(this.getErrorMessageFromException(e));
				}
			}
			this.getLogger().debug("Inquiry svdp after: " + this.svdp);
		}
		this.putToStrData("datBirthCust", DATE_INDO_FORMATTER.format(sdr.getBirthDate()));
		this.putToStrData("hajiType", this.pickListDesc("siskohatHajiType", this.svdp.getHajiType()));
		this.putToStrData("setoranAwal", this.svdp.getSetoranAwal());
		this.putToStrData("balance", this.svdp.getBalance());
		this.putToStrData("bpihCost", this.svdp.getBpihCost());
		this.putToStrData("usdExchange", this.svdp.getUsdExchange());
		this.putToStrData("bpihInIDR", this.svdp.getBpihInIDR());
		this.putToStrData("sisaLunas", this.svdp.getSisaLunas());
		
		return SUCCESS;
	}
	
	private void retrieveAccountData() throws Exception {
		ChAcctMastDAO dao = new ChAcctMastDAO(getHSession());
		this.chAcctMast = dao.getActiveAccount(this.sktPad.getAcctNo());
		this.getLogger().info("accountNo: " + this.sktPad.getAcctNo());
		this.getLogger().info("noPorsi: " + this.sktPad.getPortionNo());
		this.getLogger().info("ch_acct_mast exist: " + (this.chAcctMast != null));
		
		if (this.chAcctMast != null) {
			CiCustMastDao custDAO = new CiCustMastDao(getHSession());
			this.ciCustMast = custDAO.get(new CiCustMastPK(this.chAcctMast.getCodCust(), "A", 11));
			this.getLogger().info("CIF: " + this.ciCustMast.getCiCustMastPK().getCodCustId());
			this.cifNo = this.chAcctMast.getCodCust();
		}
	}
	
	public String postingPaidOff() throws Exception {
		BaBankMast baBankMast = new BaBankMastDAO(this.getHSession()).get();
		ParameterDao paramDAO = new ParameterDao(this.getHSession());
		SkhtPaidOffReq spor = new SkhtPaidOffReq();
		SkhtPaidOffReqDAO sprDAO = new SkhtPaidOffReqDAO(this.getHSession());
		SkhtPaidOffRespDAO sprsDAO = new SkhtPaidOffRespDAO(this.getHSession());
		SiskohatService siskohatService = new SiskohatService(this.getHSession());
		
		this.getLogger().info("Before Posting pelunass: " + this.svdp);
		this.getLogger().info("trxDate: " + baBankMast.getBusinessDate());
		this.getLogger().info("sktResp: " + this.sktResp);
		this.getLogger().info("[Begin] insert to table paidoff req");
		
		
		BigDecimal initialDeposit = new BigDecimal(paramDAO.get("SISKOHAT.Haji.Regular").getValue());
		BigDecimal SisaLunas = new BigDecimal(this.svdp.getSisaLunas());
		BigDecimal BPIHCost = new BigDecimal(this.svdp.getBpihCost());
		BigDecimal USDExchange = new BigDecimal(this.svdp.getUsdExchange());
		BigDecimal BPIHInIDR = new BigDecimal(this.svdp.getBpihInIDR());
		BigDecimal ResidualCost = new BigDecimal(this.svdp.getSisaLunas());
		
		this.getLogger().debug("setoran pelunasan: " + initialDeposit);
		this.getLogger().debug("SisaLunas: " + this.svdp.getSisaLunas());
		this.getLogger().debug("BPIHCost: " + this.svdp.getBpihCost());
		this.getLogger().debug("USDExchange: " + this.svdp.getUsdExchange());
		this.getLogger().debug("BPIHInIDR: " + this.svdp.getBpihInIDR());
		
		//insert to deposit request
		try {
			spor.setHajiType(this.svdp.getHajiType());
			spor.setJamaahName(this.svdp.getJamaahName());
			spor.setAcctNo(this.svdp.getAcctNo());
			spor.setCurrencyCode(Integer.valueOf(this.svdp.getCurrencyCode()));
			this.getLogger().debug("Currency Code: " + this.svdp.getCurrencyCode());
			spor.setFinalDeposit(SisaLunas);
			this.getLogger().debug("SisaLunas: " + SisaLunas);
			spor.setBpsBranchCode(Integer.valueOf(this.svdp.getBpsBranchCode()));
			this.getLogger().debug("BpsBranchCode: " + this.svdp.getBpsBranchCode());
			spor.setValidationNo(this.svdp.getValidationNo());
			spor.setEmbarkasi(this.svdp.getEmbarkasi());
			spor.setKloter(this.svdp.getKloter());
			spor.setPortionNo(Long.valueOf(this.svdp.getPortionNo()));
			spor.setBPIHCost(BPIHCost);
			this.getLogger().debug("BPIHCost: " + BPIHCost);
			spor.setUSDExchange(USDExchange);
			this.getLogger().debug("USDExchange: " + USDExchange);
			spor.setBPIHInIDR(BPIHInIDR);
			this.getLogger().debug("BPIHInIDRr: " + BPIHInIDR);
			spor.setResidualCost(ResidualCost);
			this.getLogger().debug("ResidualCost: " + this.svdp.getResidualCost());
			spor.setFlgPaidOff(this.svdp.getFlgPaidOff());
			spor.setDelayYear(this.svdp.getDelayYear());
			spor.setPIHKCode(Integer.valueOf(this.svdp.getPIHKCode()));
			spor.setPIHKName(this.svdp.getPIHKName());
			spor.setInitialDeposit(initialDeposit);
			spor.setFlgPaidOff(this.svdp.getFlgPaidOff());
			spor.setDelayYear(this.svdp.getDelayYear());
			this.getLogger().debug("InitialDeposit: " + initialDeposit);
			spor.setTrxDate(baBankMast.getBusinessDate());
			spor.setRefNo(siskohatService.generateRefNo(spor.getAcctNo()));
			spor.setIdMaintainedBy(this.getFromStrData("idMaintainedBy"));
			spor.setIdMaintainedSpv(this.getFromStrData("idMaintainedSpv"));
			this.getLogger().debug("After Get Data1: " + spor);
			this.getLogger().debug("After Get Data2: " + this.svdp);
			
			sprDAO.insert(spor);
			this.tx.commit();
			this.tx = this.getHSession().beginTransaction();
		}
		catch (Exception e) {
			this.getLogger().debug("error: " + e, e);
			return this.quitOfError(this.getErrorMessageFromException(e));
		}
		
		this.tx.commit();
		this.tx = this.getHSession().beginTransaction();
		
		try {
			this.paidresp = siskohatService.paidOffRequest(spor);
			this.paidresp.setTrxDate(spor.getTrxDate());
			
			/* response from siskohat gender is empty string */
			if (this.paidresp.getGender().trim().equals(""))
				this.paidresp.setGender(null);
			sprsDAO.insert(this.paidresp);
			
			this.tx.commit();
			this.tx = this.getHSession().beginTransaction();
			this.getLogger().debug("paidresp11: " + spor);
		}
		catch (Exception ex) {
			this.getLogger().error("Exception22: " + ex, ex);
			return this.quitOfError(this.getErrorMessageFromException(ex));
		}
		
		return SUCCESS;
	}

	public String printPaidOff() throws Exception {
		BaBankMast baBankMast = new BaBankMastDAO(this.getHSession()).get();
		SkhtPrintReq sPrintReq = new SkhtPrintReq();
		SkhtPrintReqDAO sprDAO = new SkhtPrintReqDAO(getHSession());
		SkhtPrintPaidDAO sppDAO = new SkhtPrintPaidDAO(this.getHSession());
		SiskohatService siskohatService = new SiskohatService(getHSession());
		
		this.getLogger().info("[START] Insert table PRINT");
		this.getLogger().info("Account: " + this.acctNo);
		this.getLogger().info("No. Porsi: " + this.noPorsi);
		this.getLogger().info("hajiType: " + this.getHajiType());
		
		//insert
		sPrintReq.setAcctNo(this.acctNo);
		try {
			sPrintReq.setPortionNo(Integer.parseInt(this.noPorsi));
		}
		catch (Exception e) {
			this.getLogger().info("FAILED PORSI " + e, e);
			return ERROR;
		}
		sPrintReq.setBpsBranchCode(this.codOrgBrn);
		sPrintReq.setHajiType(this.hajiType);
		sPrintReq.setCifNumber(Integer.parseInt(this.getFromStrData("cifNo")));
		sPrintReq.setRefNo(siskohatService.generateRefNo(sPrintReq.getAcctNo()));
		sPrintReq.setIdMaintainedBy(this.getFromStrData("idMaintainedBy"));
		this.setsPrintDep((Boolean) sprDAO.insert(sPrintReq));
		this.tx.commit();
		this.tx = this.getHSession().beginTransaction();
		
		try {
			this.skpPrint = siskohatService.paidOffPrintRequest(sPrintReq);
			this.getLogger().debug("skpPrint: " + this.skpPrint);
			
			if (this.skpPrint != null) {
				this.skpPrint.setTrxDate(baBankMast.getBusinessDate());
				this.skpPrint.setIdMaintainedBy(sPrintReq.getIdMaintainedBy());
				sppDAO.insert(this.skpPrint);
				this.tx.commit();
				this.tx = this.getHSession().beginTransaction();
				
				if (StringUtils.isBlank(this.skpPrint.getGender()) == false) {
					String genderDesc = this.pickListDesc("siskohatGender", this.skpPrint.getGender());
					if (genderDesc != null) {
						genderDesc = Character.toUpperCase(genderDesc.charAt(0)) + genderDesc.substring(1).toLowerCase();
						this.putToStrData("gender", this.pickListDesc("siskohatGender", this.skpPrint.getGender()));
					}
				}
				if (this.skpPrint.getBirthDate() != null)
					this.putToStrData("birthDate", DATE_INDO_FORMATTER.format(this.skpPrint.getBirthDate()));
				
				if (this.skpPrint.getResidualCost() != null)
					this.putToStrData("residualCostSpellingOut", BdsmUtil.angkaToTerbilang(this.skpPrint.getResidualCost().longValueExact()));
			}
		}
		catch(Exception ex) {
			this.getLogger().error(ex, ex);
			return this.quitOfError(this.getErrorMessageFromException(ex));
		}
		
		return SUCCESS;
	}
	
	
	public String compareValueDataSiskohatByCodeBranch(Integer codOrgBrn) throws ParseException {
		ChAcctMastDAO acctDAO = new ChAcctMastDAO(this.getHSession());
		CiCustMastDao custDAO = new CiCustMastDao(this.getHSession());
		
		if (this.chAcctMast == null)
			this.chAcctMast = acctDAO.getActiveAccount(this.acctNo);
		
		CiCustMastPK custPK = new CiCustMastPK(this.chAcctMast.getCodCust(), "A", 11);
		CiCustMast cust = custDAO.get(custPK);
		String namPermadrState = cust.getNamPermadrState();
		this.getLogger().info("Account No: " + this.acctNo);
		this.getLogger().info("CIF: " + this.chAcctMast.getCodCust());
		this.getLogger().info("Permanent address state: " + namPermadrState);
		
		SkhtBranchRegionMappingDAO sbrmDAO = new SkhtBranchRegionMappingDAO(this.getHSession());
		SkhtBranchRegionMapping brmap = sbrmDAO.getByBranchCode(codOrgBrn);
		this.getLogger().info("Branch region mapping state: " + brmap.getCoreProvName());
		
		if (namPermadrState == null)
			return "skht.address.state.empty";
		else if (namPermadrState.equalsIgnoreCase(brmap.getCoreProvName()) == false)
			return "skht.invalid.branch";
		else
			return null;
	}

	public String compareBalance() throws Exception {
		ChAcctMastDAO dao = new ChAcctMastDAO(getHSession());
		this.chAcctMast = dao.getActiveAccount(this.acctNo);
		
		try {
			this.getLogger().info("compare balance: " + this.balance);
			BigDecimal bigDecimal = new BigDecimal(this.balance);
			BigDecimal accountBalance = this.chAcctMast.getBalAvailable().subtract(this.chAcctMast.getBalAcctMinReqd().add(this.chAcctMast.getAmtHld()));
			this.getLogger().info("account Balance: " + accountBalance);
			
			if (accountBalance.compareTo(bigDecimal) >= 0)
				this.keys = "VALIDATE";
			else
				this.keys = "NOT VALIDATED";
		}
		catch (Exception e) {
			this.getLogger().info("Exception : " + e, e);
			this.keys = "FAILED";
		}
		
		this.getLogger().info("compareBalance result: " + this.keys);
		this.chAcctMast = null;
		
		return SUCCESS;
	}
	
	
	private String getErrorMessageFromException(Exception ex) {
		Throwable t = ex;
		String errorMessage = "";
		while ((errorMessage = t.getMessage()) == null) {
			t = t.getCause();
			if (t == null)
				break;
		}
		
		return errorMessage;
	}
	
	private String quitOfError(String errorCode) {
		this.setErrorCode(errorCode);
		return ERROR;
	}
	
	
	@Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public String doGet() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	

	public String pickListDesc(String masterType, Object id) throws Exception {
		DataMasterService dmService = new DataMasterService();
		dmService.setDataMasterDAO(new DataMasterDAO(this.getHSession()));
		
		return (String) dmService.getDataById(masterType, id)[1];
	}
	
	
	
	public String getAcctNo() {
		return this.acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	public Date getDatBirthCust() {
		return this.datBirthCust;
	}
	public void setDatBirthCust(Date datBirthCust) {
		this.datBirthCust = datBirthCust;
	}
	
	public Integer getCodOrgBrn() {
		return this.codOrgBrn;
	}
	public void setCodOrgBrn(Integer codOrgBrn) {
		this.codOrgBrn = codOrgBrn;
	}
	
	public Integer getCodCust() {
		return this.codCust;
	}
	public void setCodCust(Integer codCust) {
		this.codCust = codCust;
	}
	
	public SkhtViewData getSvd() {
		return this.svd;
	}
	public void setSvd(SkhtViewData svd) {
		this.svd = svd;
	}
	
	public SkhtViewDataPelunasan getSvdp() {
		return this.svdp;
	}
	public void setSvdp(SkhtViewDataPelunasan svdp) {
		this.svdp = svdp;
	}
	
	public String getBirthPlace() {
		return this.birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	public String getHajiType() {
		return this.hajiType;
	}
	public void setHajiType(String hajiType) {
		this.hajiType = hajiType;
	}
	
	public SkhtDepositResp getSkhtDepResp() {
		return this.skhtDepResp;
	}
	public void setSkhtDepResp(SkhtDepositResp skhtDepResp) {
		this.skhtDepResp = skhtDepResp;
	}
	
	public SkhtPaidOffInqResp getSktResp() {
		return this.sktResp;
	}
	public void setSktResp(SkhtPaidOffInqResp sktResp) {
		this.sktResp = sktResp;
	}
	
	public SkhtPaidOffInqReq getSktPad() {
		return this.sktPad;
	}
	public void setSktPad(SkhtPaidOffInqReq sktPad) {
		this.sktPad = sktPad;
	}
	
	public SkhtPrintDep getSkpDep() {
		return this.skpDep;
	}
	public void setSkpDep(SkhtPrintDep skpDep) {
		this.skpDep = skpDep;
	}
	
	public Boolean getsPrintDep() {
		return this.sPrintDep;
	}
	public void setsPrintDep(Boolean sPrintDep) {
		this.sPrintDep = sPrintDep;
	}
	
	public String getValidationNo() {
		return this.validationNo;
	}
	public void setValidationNo(String validationNo) {
		this.validationNo = validationNo;
	}
	
	public SkhtPrintReq getSkPrintReq() {
		return this.skPrintReq;
	}
	public void setSkPrintReq(SkhtPrintReq skPrintReq) {
		this.skPrintReq = skPrintReq;
	}
	
	public String getBalance() {
		return this.balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getNoPorsi() {
		return this.noPorsi;
	}
	public void setNoPorsi(String noPorsi) {
		this.noPorsi = noPorsi;
	}
	
	public String getKeys() {
		return this.keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	public SkhtPaidOffReq getPaidreq() {
		return this.paidreq;
	}
	public void setPaidreq(SkhtPaidOffReq paidreq) {
		this.paidreq = paidreq;
	}
	
	public SkhtPaidOffResp getPaidresp() {
		return this.paidresp;
	}
	public void setPaidresp(SkhtPaidOffResp paidresp) {
		this.paidresp = paidresp;
	}
	
	public SkhtPrintPaid getSkpPrint() {
		return this.skpPrint;
	}
	public void setSkpPrint(SkhtPrintPaid skpPrint) {
		this.skpPrint = skpPrint;
	}
	
	
	public ChAcctMast getChAcctMast() {
		return this.chAcctMast;
	}
	
	public CiCustMast getCiCustMast() {
		return this.ciCustMast;
	}
	
	
	@Override
	public Object getModel() {
		return new Object();
	}
	
}


class SiskohatDAO extends bdsmhost.dao.BaseAdapterDAO {
	private static Logger LOGGER = Logger.getLogger(SiskohatDAO.class);
	
	public SiskohatDAO(Session session) {
		super(session);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getViewDataScreen(String acctNo, Integer cifNo) {
		List<Map<String, Object>> lm = null;
		String cifProxy = null;
		
		try {
			if (cifNo != null)
				cifProxy = cifNo.toString();
			
			LOGGER.info(acctNo);
			LOGGER.info(cifProxy);
			
			Query query = this.getSession().getNamedQuery("Siskohat#ViewDataScreen");
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.setString("acctNo", acctNo);
			query.setString("cifNost", cifProxy);
			query.setInteger("cifNo", cifNo);
			
			LOGGER.debug(query.getQueryString());
			
			lm = query.list();
			LOGGER.info("map query " + lm);
		}
		catch (Exception hibernateException) {
			LOGGER.error("HIBERNATE: " + hibernateException, hibernateException);
		}
		
		if ((lm != null) && (lm.size() > 0))
			return lm.get(0);
		else
			return null;
	}
}