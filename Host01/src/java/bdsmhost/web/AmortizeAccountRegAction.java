/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.fcr.model.*;
import bdsm.model.*;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.dao.TmpTxnUploadDetailDAO;
import bdsm.scheduler.dao.TmpTxnUploadFooterDAO;
import bdsm.scheduler.dao.TmpTxnUploadHeaderDAO;
import bdsm.scheduler.model.*;
import bdsm.util.BdsmUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.*;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.ChProdMastDAO;
import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.ChAcctMastPK;
import bdsmhost.fcr.dao.BaCcyCodeDAO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 *
 * @author v00019722
 */
public class AmortizeAccountRegAction extends BaseHostAction {

	private AmortizeAccount amortAccount;
	private AmortizeCustomOpening custom;
	private String AccountNo;
	private Integer totalTime;
	private String codProd;
	private List modelLIst;
	private String batchTXN;
	private List<ChAcctMast> modelListFCR;
	private ChProdMast modelProd;
	private ChAcctMast modelFCR;
	private ChAcctMastPK modelPKfcr;
	private ChAcctMastReflection chMastRef;
	private Transaction tx;
	private String StatusProc;
	private String profileName;
	private String idUser;
	private String spvUser;

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.patternddMMyyyy);
		AmortizeProgramDetailDAO dao = new AmortizeProgramDetailDAO(getHSession());
		AmortizeAccountDAO accDao = new AmortizeAccountDAO(getHSession());
		FcrBaBankMastDao bankMast = new FcrBaBankMastDao(getHSession());
		getLogger().info("CUSTOM TRANSFER :" + this.custom.getMode());
		getLogger().info("ACCOUNT before CHECK :" + this.custom.getAcctNo());
		// check Account already existing on Amort Period
		List check = accDao.checkExistingAccount(this.custom.getAcctNo());
		//getLogger().info("EXISTING ACCOUNT :" + check);
		getLogger().info("GIFTCODE:" + custom.getGiftCode());
		getLogger().info("STATE:" + custom.getState());
		String AccToCompare = null;
		if (check.isEmpty()) {
			AccToCompare = "";
		} else {
			AccToCompare = BdsmUtil.rightPad(custom.getAcctNo(), 16);
		}
		getLogger().info("ACCT :" + AccToCompare);
		if (getCustom().getMode().equalsIgnoreCase("1")) { // Add
			//HashMap resultdate = null;
			getLogger().info("CODPROD :" + custom.getCodProd());

			if (getCustom().getState().equalsIgnoreCase("Gift")) {
				getLogger().info("GIFT :" + getCustom().getState());
				setModelLIst(dao.listGift(getCustom().getGiftCode(), Integer.parseInt(getCustom().getCodProd()), "Add", AccToCompare));
			} else {
				getLogger().info("TERM :" + getCustom().getState());
				setModelLIst(dao.listTerm(custom.getEffectiveDate(), getCustom().getGiftCode(), Integer.parseInt(getCustom().getCodProd()), "Add", AccToCompare, 0));
			}
		} else if (getCustom().getMode().equalsIgnoreCase("2")) { //delete
			if (getCustom().getEffectiveDate() != null) {
				custom.setEffectiveDate(sdf.format(getCustom().getEffectiveDate()));
			} else {
				custom.setEffectiveDate("");
			}
			if (getCustom().getState().equalsIgnoreCase("Gift")) {
				getLogger().info("EFFECTIVE DATE : " + getCustom().getEffectiveDate());
				setModelLIst(dao.listGift(getCustom().getGiftCode(), Integer.parseInt(getCustom().getCodProd()), "Delete", AccToCompare));
			} else if (getCustom().getState().equalsIgnoreCase("Term")) {
				setModelLIst(dao.listTerm(custom.getEffectiveDate(), getCustom().getGiftCode(), Integer.parseInt(getCustom().getCodProd()), "Delete", AccToCompare, 0));
			} else {
				setModelLIst(dao.listAcct(getCustom().getAcctNo(), "Delete"));
			}
		} else if (getCustom().getMode().equalsIgnoreCase("3")) { // inquiry
			if (getCustom().getState().equalsIgnoreCase("Gift")) {
				getLogger().info("CHECK GIFT :" + AccToCompare);
				setModelLIst(dao.listGift(getCustom().getGiftCode(), Integer.parseInt(getCustom().getCodProd()), "Inquiry", getCustom().getAcctNo()));
			} else if (getCustom().getState().equalsIgnoreCase("Term")) {
				setModelLIst(dao.listTerm(custom.getEffectiveDate(), getCustom().getGiftCode(), Integer.parseInt(getCustom().getCodProd()), "Inquiry", getCustom().getAcctNo(), 0));
			} else {
				setModelLIst(dao.listAcct(getCustom().getAcctNo(), "Inquiry"));
			}
		} else {
			getLogger().info("CUSTOM ACCOUNT " + custom.getAcctNo());
			getLogger().info("CUSTOM TERM " + custom.getTerm());
			getLogger().info("CUSTOM CODEPORD " + custom.getCodProd());
			getLogger().info("CUSTOM GIFTCODE " + custom.getGiftCode());
			getLogger().info("CUSTOM MODE " + custom.getMode());
			List moded = new ArrayList();
			StringTokenizer modeToken = new StringTokenizer(custom.getMode(), ":");
			while (modeToken.hasMoreTokens()) {
				moded.add(modeToken.nextToken());
			}
			String AcctNo = AccToCompare.trim();
			List<Date> effectiveDate = new ArrayList();
			effectiveDate = dao.listDate(Integer.parseInt(getCustom().getCodProd()), getCustom().getGiftCode(), sdf.format(bankMast.get().getDatProcess()), Integer.parseInt(custom.getTerm()));
			getLogger().info("EFFECTIVE DATE : " + effectiveDate);
			Date actual = getNearestDate(effectiveDate, bankMast.get().getDatProcess());

			getLogger().info("DATE : " + actual);
			setModelLIst(dao.listTerm(sdf.format(actual).toString(), getCustom().getGiftCode(), Integer.parseInt(getCustom().getCodProd()), "TermInquiry:" + moded.get(1).toString(), AcctNo, Integer.parseInt(custom.getTerm())));
		}
		getLogger().info("LIST :" + getModelLIst());
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		getLogger().info("COD_ACCT_NO :" + getChMastRef().getCodAcctNo());
		getLogger().info("FLG_MNT :" + getChMastRef().getFlgMntStatus());
		getLogger().info("COD_ENTITY :" + getChMastRef().getCodEntityVpd());
		getLogger().info("ACCT_STAT : " + StatusDefinition.AmortNegateAcctStat);
		GLCostCentreXrefDao xrefDao = new GLCostCentreXrefDao(getHSession());
		GLCostCentreXrefPK xrefPK = new GLCostCentreXrefPK();

		String AcctNo = BdsmUtil.rightPad(getChMastRef().getCodAcctNo(), 16, ' ');
		getLogger().info("aCCT :" + AcctNo);
		//return SUCCESS;
		modelPKfcr = new ChAcctMastPK();
		modelListFCR = new ArrayList<ChAcctMast>();

		try {
			modelPKfcr.setCodAcctNo(AcctNo);
			modelPKfcr.setCodEntityVpd(Integer.parseInt(getChMastRef().getCodEntityVpd()));
			modelPKfcr.setFlgMntStatus(getChMastRef().getFlgMntStatus());
		} catch (Exception exception) {
			getLogger().info("ACCOUNT NOT VALID" + exception, exception);
		}

		List<Integer> AcctStat = new ArrayList();

		StringTokenizer tokenAcct = new StringTokenizer(StatusDefinition.AmortNegateAcctStat, ",");
		while (tokenAcct.hasMoreTokens()) {
			AcctStat.add(Integer.parseInt(tokenAcct.nextToken()));
		}
		ChAcctMastDAO dao = new ChAcctMastDAO(getHSession());

		modelListFCR = dao.listbyAcctStat(modelPKfcr, AcctStat);
		getLogger().info("MODEL ACCT :" + modelListFCR);
		if (modelListFCR.isEmpty()) {
			try {
				modelFCR = dao.get(modelPKfcr);
				getLogger().info("FCRmodel :" + modelFCR);
				if (modelFCR == null) {
					setErrorCode("25501.error.AccountNo.Invalid");
					custom.setCodProd("");
					custom.setNamProduct("");
				} else {
					setErrorCode("25501.error.AccountNo.Closed");
					custom.setCodProd("");
					custom.setNamProduct("");
				}
			} catch (Exception e) {
				getLogger().info("ACCOUNT :" + getChMastRef().getCodAcctNo() + " NOT FOUND");
				setErrorCode("25501.error.AccountNo.Invalid");
				custom.setCodProd("");
				custom.setNamProduct("");
			}
			return SUCCESS;
		} else {
			if (custom.getInstrMode().equalsIgnoreCase("GLCHECKING")) {
				modelFCR = dao.get(modelPKfcr);
				AmortizeGLNoDAO amortDao = new AmortizeGLNoDAO(getHSession());
				AmortizeGLNoPK pkAmort = null;
				AmortizeGLNo Amortizes = new AmortizeGLNo();

				pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 1, "D", 1);
				Amortizes = amortDao.get(pkAmort);

				xrefPK.setCodCCBrn(modelFCR.getCodCcBrn());
				xrefPK.setCodLob(0);
				//xrefPK.setCodLob(Integer.parseInt(dao.getCod_LOB(modelListFCR.get(0).getCompositeId().getCodAcctNo())));
				xrefPK.setCodGLAcct(Amortizes.getGLNo());
				try {
					GLCostCentreXref xrefGl = xrefDao.get(xrefPK);
				} catch (Exception e) {
					getLogger().info("GL NOT FOUND FOR COMBINATION :" + xrefPK.getCodCCBrn() + "|" + xrefPK.getCodLob() + "|" + xrefPK.getCodGLAcct());
					setErrorCode("25501.error.GL.Validation");
					custom.setLOBS(xrefPK.getCodLob().toString());
					custom.setBranch(xrefPK.getCodCCBrn().toString());
					custom.setGlCode(Amortizes.getGLNo());
					return ERROR;
				}
			} else if (getCustom().getInstrMode().equalsIgnoreCase("RECORDCHECKING")) {
				// check into amortize_record
				getLogger().info("BEGIN RECORD CHECKING :" + custom.getSequenceID());
				AmortizeRecordDAO recDao = new AmortizeRecordDAO(getHSession());
				try {
					List<AmortizeRecord> amortRec = recDao.getDistinct(Integer.parseInt(getCustom().getSequenceID()));
					if (amortRec.isEmpty()) {
						getLogger().info("END RECORD CHECKING :" + custom.getSequenceID());
						return SUCCESS;
					} else {
						getLogger().info("EXISTING ON RECORD");
						setErrorCode("25501.error.AccountNo.existing");
						return ERROR;
					}
					//
				} catch (Exception e) {
					getLogger().info("EXCEPTION :" + e);
					return SUCCESS;
				}
			} else {
				ChProdMastDAO prodDao = new ChProdMastDAO(getHSession());
				ChProdMastPK pk = new ChProdMastPK();
				pk.setCodEntityVpd(Integer.parseInt(getChMastRef().getCodEntityVpd()));
				pk.setFlgMntStatus(getChMastRef().getFlgMntStatus());
				pk.setCodProd(modelListFCR.get(0).getCodProd());
				getLogger().info("CODE PRODUCT : " + modelListFCR.get(0).getCodProd().toString());
				modelProd = prodDao.get(pk);
				getLogger().info("MODEL PROD :" + modelProd);
				if (modelProd == null) {
					custom = new AmortizeCustomOpening();
					setErrorCode("25501.error.Product.Closed");
					custom.setAcctNo(getChMastRef().getCodAcctNo());
					custom.setCodProd("");
					custom.setNamProduct("");
					return SUCCESS;
				} else {
					custom = new AmortizeCustomOpening();
					custom.setAcctNo(getChMastRef().getCodAcctNo());
					custom.setCodProd(modelProd.getCompositeId().getCodProd().toString());
					custom.setNamProduct(modelProd.getNamProduct());
					getLogger().info("CUSTOM : " + custom);
					return SUCCESS;
				}
			}
			return SUCCESS;
		}
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		String Status;
		if (StatusProc.equalsIgnoreCase("1")) {
			tx = getHSession().beginTransaction();

			amortAccount = new AmortizeAccount();
			modelPKfcr = new ChAcctMastPK();

			amortAccount.setAccountNo(getCustom().getAcctNo());
			modelPKfcr.setCodEntityVpd(Integer.parseInt(getChMastRef().getCodEntityVpd()));
			modelPKfcr.setFlgMntStatus(getChMastRef().getFlgMntStatus());
			modelPKfcr.setCodAcctNo(getCustom().getAcctNo());

			FcrBaBankMastDao bankDao = new FcrBaBankMastDao(getHSession());
			String HoldAmt = getCustom().getHoldAmount();

			amortAccount.setHoldAmount(new BigDecimal(HoldAmt).setScale(2)); // Buat apa dibagi 100?
			amortAccount.setOpenDate(bankDao.get().getDatProcess());
			amortAccount.setProgramName(getCustom().getProgramName());

			getLogger().info("HOLD :" + amortAccount.getHoldAmount());

			getLogger().info("BASEPRICE :" + getCustom().getGiftPrice());

			BigDecimal netgiftPrice = new BigDecimal(getCustom().getGiftPrice());
			BigDecimal percentage = new BigDecimal(100.0);
			BigDecimal taxEntity = new BigDecimal(getCustom().getTax());

			taxEntity = taxEntity.divide(percentage);
			getLogger().info("TAX :" + taxEntity.toPlainString());
			BigDecimal baseEntity = new BigDecimal(1.0);
			BigDecimal Price = new BigDecimal(getCustom().getGiftPrice()); // Price == netgiftPrice
			getLogger().info("PRICE DESICMAL : " + Price);

			baseEntity = baseEntity.subtract(taxEntity); // baseEntity = baseEntity - taxEntity
			getLogger().info("BASE TAX :" + baseEntity.toPlainString());
			try {
				Price = Price.divide(baseEntity, 2, BigDecimal.ROUND_HALF_UP); // Price = Price / baseEntity
			} catch (Exception e) {
				getLogger().info("failed to Divide : " + e, e);
			}
			getLogger().info("PRICE :" + Price);
			getLogger().info("NOMINAL PAJAK :" + Price.subtract(netgiftPrice));

			amortAccount.setGiftPriceGross(Price.setScale(2));
			amortAccount.setTaxAmount(Price.subtract(netgiftPrice).setScale(2));
			amortAccount.setTaxPercent(new BigDecimal(getCustom().getTax()).setScale(2));
			amortAccount.setGiftPrice(netgiftPrice.setScale(2));
			amortAccount.setProgramDetailId(Integer.parseInt(custom.getId_programDetails()));
			amortAccount.setStatus(1);
			amortAccount.setType(Integer.parseInt(custom.getAmortType()));
			amortAccount.setProgramId(custom.getProgramID());

			amortAccount.setId(AccountJournal());
			getLogger().info("FINISH INSERT JOURNAL");

			String type = "TXN";
			processAmort(type, "OPENING");
			getLogger().info("COMMIT");
			Status = "SUCCESS";
		} else {
			try {
				Status = gefuRespondProcessingTXN();
			} catch (Exception e) {
				getLogger().info("FAILED INSERT GEFU " + e, e);
				Status = "ERROR";
			}
			//Status = "SUCCESS";
			getLogger().info("FINISH INSERT AMORT : " + Status);
		}
		//uditlogDao auditdao = new AuditlogDao(getHSession());

		try {
			if (Status.equalsIgnoreCase("SUCCESS")) {

				super.setErrorCode("success.0");
				getLogger().info("BEFORE_RETURN....");
				return SUCCESS;
			} else {
				super.setErrorCode("error.0");
				return ERROR;
			}
		} catch (Exception e) {
			getLogger().info("FAILED SETTING ERRORCODE");
			return SUCCESS;
		}
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		Transaction tx = getHSession().beginTransaction();
		String Status = null;
		if (StatusProc.equalsIgnoreCase("1")) {
			FcrBaBankMastDao bankDao = new FcrBaBankMastDao(getHSession());
			amortAccount = new AmortizeAccount();
			modelPKfcr = new ChAcctMastPK();

			amortAccount.setAccountNo(getCustom().getAcctNo());
			modelPKfcr.setCodEntityVpd(Integer.parseInt(getChMastRef().getCodEntityVpd()));
			modelPKfcr.setFlgMntStatus(getChMastRef().getFlgMntStatus());
			modelPKfcr.setCodAcctNo(getCustom().getAcctNo());
			String HoldAmt = getCustom().getHoldAmount();
			getLogger().info("HOLD :" + HoldAmt);

			amortAccount.setHoldAmount(BigDecimal.valueOf(Double.parseDouble(HoldAmt)));
			amortAccount.setOpenDate(bankDao.get().getDatProcess());
			amortAccount.setProgramName(getCustom().getProgramName());
			BigDecimal netgiftPrice = new BigDecimal(1.0);
			BigDecimal brutoGiftPrice = new BigDecimal(1.0);
			BigDecimal taxEntity = new BigDecimal(1.0);
			BigDecimal taxPercentage = new BigDecimal(getCustom().getTax());
			BigDecimal giftGross = new BigDecimal(getCustom().getGiftGrossPrice());
			BigDecimal TaxAmount = new BigDecimal(getCustom().getTaxAmount());

			netgiftPrice = netgiftPrice.multiply(BigDecimal.valueOf(Double.parseDouble(getCustom().getGiftPrice())));
			brutoGiftPrice = brutoGiftPrice.multiply(giftGross);
			taxEntity = taxEntity.multiply(TaxAmount);

			amortAccount.setGiftPriceGross(brutoGiftPrice);
			amortAccount.setTaxAmount(taxEntity);
			amortAccount.setTaxPercent(taxPercentage);
			amortAccount.setGiftPrice(netgiftPrice);
			amortAccount.setId(AccountJournal());
			getLogger().info("TAX AMOUNT :" + amortAccount.getTaxAmount());

			if (amortAccount.getId().compareTo(0) >= 0) {
				String type = "TXN";
				processAmort(type, "CANCEL");
				Status = "SUCCESS";
			} else {
				Status = "ERROR";
			}
		} else {
			try {
				Status = gefuRespondProcessingTXN(); // insert to TMP gefu respond (HOLD)
			} catch (Exception e) {
				getLogger().info("FAILED INSERT GEFU " + e, e);
			}
			AuditlogDao auditdao = new AuditlogDao(getHSession());
		}
		//type = "HOLD";
		//processAmort(type, "CANCEL");
		if (Status.equalsIgnoreCase("SUCCESS")) {
			super.setErrorCode("success.2");
			return SUCCESS;
		} else {
			super.setErrorCode("error.0");
			return ERROR;
		}
	}

	private void processAmort(String type, String status) {
		String BatchNo = SchedulerUtil.generateUUID();
		ChAcctMastDAO accDao = new ChAcctMastDAO(getHSession());
		ChAcctMast chMast = new ChAcctMast();
		chMast = accDao.get(modelPKfcr);
		FcrBaBankMastDao bankDao = new FcrBaBankMastDao(getHSession());
		FcrBaBankMast bankMast = bankDao.get();
		AmortizeXref xref = new AmortizeXref();
		AmortizeXrefDAO xrefDao = new AmortizeXrefDAO(getHSession());
		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.AmortTxnDate);
		//SimpleDateFormat amortSDF = new SimpleDateFormat(StatusDefinition.AmortTxnDate);
		tx = getHSession().beginTransaction();

		xref.setId(amortAccount.getId());
		xref.setFileId(BatchNo);

		getLogger().info("BEGIN PROCESSING TXN");
		//getLogger().info("getSession :" + getHSession().getTransaction());
		if (type.equalsIgnoreCase("TXN")) {
			setBatchTXN(BatchNo);
			int generalRecid = 1;
			getLogger().debug("BATCH :" + BatchNo);
			TmpTxnUploadHeaderDAO txnHeaderDao = new TmpTxnUploadHeaderDAO(getHSession());
			TmpTxnUploadDetailDAO txnDetailDao = new TmpTxnUploadDetailDAO(getHSession());
			TmpTxnUploadFooterDAO txnFooterDao = new TmpTxnUploadFooterDAO(getHSession());
			TransactionParameterDao transactionDao = new TransactionParameterDao(getHSession());
			TransactionParameter param = new TransactionParameter();
			AmortizeGLNoDAO glDao = new AmortizeGLNoDAO(getHSession());
			AmortizeGLNoPK glPk = new AmortizeGLNoPK();

			Integer LOB = null;
			try {
				LOB = Integer.parseInt(accDao.getCod_LOB(custom.getAcctNo()));
			} catch (Exception ex) {
				LOB = 00;
			}
			//TmpTxnUploadPK txnPk = new TmpTxnUploadPK();
			//txnPk.setRecordId(generalRecid+1);
			//txnPk.setFileId(BatchNo);
			//parent.setCompositeId(txnPk);
			if (status.equalsIgnoreCase("OPENING")) {
				profileName = "AMORTOPENING";

			} else {
				profileName = "AMORTCANCELLING";
			}

			TmpTxnUploadHeader txnHeader = new TmpTxnUploadHeader(BatchNo, getProfileName());

			String taxCostCenter = "0";
			String taxLOB = "0";

			//txnHeader.setRecordStatus("1");
			//txnHeader.setRecordName("HEADER");
			setProfileName(txnHeader.getModuleName());
			txnHeader.setDateCreate(sdf.format(bankMast.getDatProcess()));
			BaCcyCodeDAO baCcyCodeDAO = new BaCcyCodeDAO(getHSession());
			//getLogger().info("CHECK NEW "+ chMast.getCodCcy());
			BaCcyCode ccyCode = baCcyCodeDAO.get(new BaCcyCodePK(chMast.getCodCcy(), "A", PropertyPersister.codEntityVPD));
			String currency = "IDR";//ccyCode.getNamCcyShort();
			BigDecimal multiplier = new BigDecimal(100.00);
			getLogger().info("CHECK NEW 2 " + ccyCode.getCtrCcyDec());
			/*
			 * multiplier = (ccyCode.getCtrCcyDec() > 0) ? new
			 * BigDecimal(Double.toString(Math.pow(10.0,
			 * ccyCode.getCtrCcyDec()))) : BigDecimal.ONE;
			 */
			BigDecimal rate = new BigDecimal(1.0);
			try {
				txnHeader.generateLength();
				txnHeaderDao.insert(txnHeader);

			} catch (Exception e) {
				getLogger().info("FAILED INSERT HEADER :" + e, e);
			}


			AmortizeGLNoDAO amortDao = new AmortizeGLNoDAO(getHSession());
			AmortizeGLNoPK pkAmort = null;
			AmortizeGLNo Amortizes = new AmortizeGLNo();
			if (getCustom().getAmortType().equalsIgnoreCase("1")) {
				getLogger().info("ADVANCED");
				for (int i = 1; i < 4; i++) {
					generalRecid++;
					//getLogger().info("RECID :" + generalRecid);
					TmpTxnUploadDetail txnDetails = new TmpTxnUploadDetail(txnHeader, Integer.valueOf(generalRecid));
					transactionDao = new TransactionParameterDao(getHSession());
					txnDetailDao = new TmpTxnUploadDetailDAO(getHSession());
					txnDetails.setIdCreatedBy(idUser);
					//txnDetails.getCompositeId().setRecordId(i + 1); //--> need record id
					if (i == 1) {
						//getLogger().info("1ST ROW");
						txnDetails.setAmtTxnLcy(amortAccount.getGiftPriceGross().multiply(multiplier));
						txnDetails.setAmtTxnTcy(txnDetails.getAmtTxnLcy().multiply(rate));

						if (status.equalsIgnoreCase("OPENING")) {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_DEBIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_DEBIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 1, "D", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						} else {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_CREDIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_CREDIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 2, "C", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						}
						txnDetails.setCodLob(0);
						txnDetails.setProdCod(0);
						txnDetails.setCodCcBrn(chMast.getCodCcBrn());
						//getLogger().info("FINISH 1STROW");
					} else if (i == 2) {
						//getLogger().info("2ND ROW");
						txnDetails.setAmtTxnLcy(amortAccount.getGiftPrice().multiply(multiplier));
						txnDetails.setAmtTxnTcy(txnDetails.getAmtTxnLcy().multiply(rate));
						if (status.equalsIgnoreCase("OPENING")) {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_CREDIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_CREDIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 1, "C", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						} else {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_DEBIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_DEBIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 2, "D", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						}
						List LOBparameter = new ArrayList();
						List Branchparameter = new ArrayList();
						StringTokenizer voucherBranch = new StringTokenizer(Amortizes.getCodCCBrn(), ";");
						while (voucherBranch.hasMoreTokens()) {
							Branchparameter.add(voucherBranch.nextToken());
						}
						StringTokenizer voucherLOB = new StringTokenizer(Amortizes.getCodLOB(), ";");
						while (voucherLOB.hasMoreTokens()) {
							LOBparameter.add(voucherLOB.nextToken());
						}
						if (custom.getVoucherType().equalsIgnoreCase("1")) {
							txnDetails.setCodLob(Integer.parseInt(LOBparameter.get(0).toString()));
							txnDetails.setCodCcBrn(Integer.parseInt(Branchparameter.get(0).toString()));
						} else {
							txnDetails.setCodLob(Integer.parseInt(LOBparameter.get(1).toString()));
							txnDetails.setCodCcBrn(Integer.parseInt(Branchparameter.get(1).toString()));
						}
						txnDetails.setProdCod(Amortizes.getCodProduct());
						//getLogger().info("FINISH 2NDROW");

					} else if (i == 3) {
						List costCenter = new ArrayList();
						List LOBparameter = new ArrayList();
						//getLogger().info("3RD ROW");

						txnDetails.setAmtTxnLcy(amortAccount.getTaxAmount().multiply(multiplier));
						txnDetails.setAmtTxnTcy(txnDetails.getAmtTxnLcy().multiply(rate));

						getLogger().info("MODEL AMORT :" + amortAccount);
						if (status.equalsIgnoreCase("OPENING")) {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_CREDIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_CREDIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 1, "C", 2);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						} else {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_DEBIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_DEBIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 2, "D", 2);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						}
						getLogger().info("BRANCH :" + Amortizes.getCodCCBrn());
						StringTokenizer tokenBranch = new StringTokenizer(Amortizes.getCodCCBrn(), ";");
						while (tokenBranch.hasMoreTokens()) {
							costCenter.add(tokenBranch.nextToken());
						}
						getLogger().info("LOB :" + Amortizes.getCodLOB());
						StringTokenizer tokenLOB = new StringTokenizer(Amortizes.getCodLOB(), ";");
						while (tokenLOB.hasMoreTokens()) {
							LOBparameter.add(tokenLOB.nextToken());
						}
						getLogger().info("LOBPARAMETER :" + LOBparameter);
						getLogger().info("LOB :" + LOB);

						taxLOB = (String) LOBparameter.get(0);
						taxCostCenter = (String) costCenter.get(0);
						for (int z = 0; z < LOBparameter.size(); z++) {
							int l_lob = 0;
							try {
								l_lob = Integer.parseInt((String) LOBparameter.get(z));
							} catch (Exception e) {
							}
							if (LOB == l_lob) {
								taxLOB = (String) LOBparameter.get(z);
								taxCostCenter = (String) costCenter.get(z);
								break;
							}
						}
						txnDetails.setCodCcBrn(Integer.parseInt(taxCostCenter));
						txnDetails.setCodLob(Integer.parseInt(taxLOB));
						txnDetails.setProdCod(0);
						getLogger().info("FINISH 3RDROW");
					}
					StringBuilder TxnBuild = new StringBuilder();
					txnDetails.setCodUserId("INTFCAW");
					txnDetails.setTypTxn(3);
					//txnDetails.setTarget("EXT_TXNUPLOAD_DETAILRECDDTO");
					//txnDetails.setCompositeId(txnPk);
					txnDetails.setTxnCurrency(currency);
					txnDetails.setConvRate(rate);
					txnDetails.setReferenceNo("1");
					txnDetails.setRefDocNo(BatchNo);
					txnDetails.setTxtTxnDesc(getCustom().getAccrualID().concat(" ").concat(getCustom().getAcctNo()));
					TxnBuild.append(txnDetails.getRecType()).append(":").append(txnDetails.getTypTxn()).append(":").append(txnDetails.getCodAcctNo().trim()).append(":");
					TxnBuild.append(txnDetails.getCodCcBrn()).append(":").append(txnDetails.getCodLob()).append(":").append("INTFCAW").append(":");
					TxnBuild.append(txnDetails.getTxnType()).append(":").append(txnDetails.getCodTxn()).append(":").append(txnDetails.getProdCod()).append(":");
					TxnBuild.append(txnDetails.getDatTxn()).append(":").append(txnDetails.getCodDrCr()).append(":").append(txnDetails.getDatValue()).append(":");
					TxnBuild.append(txnDetails.getTxnCurrency()).append(":").append(txnDetails.getAmtTxnLcy()).append(":").append(txnDetails.getAmtTxnTcy()).append(":");
					TxnBuild.append(txnDetails.getConvRate()).append(":").append(txnDetails.getReferenceNo()).append(":").append(txnDetails.getRefDocNo()).append(":");
					TxnBuild.append(txnDetails.getTxtTxnDesc());
					txnDetails.setData(TxnBuild.toString());
					try {
						txnDetails.generateLength();
						txnDetailDao.insert(txnDetails);

						getLogger().info("GETiNSER : " + generalRecid);
					} catch (Exception e) {
						getLogger().info("INSERT FAILED " + e, e);
					}
					TxnBuild.delete(0, TxnBuild.length());
					getLogger().info("FINISH ALL ROW");
				}
			} else {
				getLogger().info("ARREARS");
				for (int i = 1; i < 3; i++) {
					generalRecid++;

					TmpTxnUploadDetail txnDetails = new TmpTxnUploadDetail(txnHeader, generalRecid);
					transactionDao = new TransactionParameterDao(getHSession());
					txnDetailDao = new TmpTxnUploadDetailDAO(getHSession());
					//txnPk.setRecordId(i + 1);
					txnDetails.setIdCreatedBy(idUser);
					txnDetails.getCompositeId().setRecordId(i + 1);
					if (i == 1) {
						txnDetails.setAmtTxnLcy(amortAccount.getGiftPriceGross().multiply(multiplier));
						txnDetails.setAmtTxnTcy(txnDetails.getAmtTxnLcy().multiply(rate));
						if (status.equalsIgnoreCase("OPENING")) {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_DEBIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_DEBIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 1, "D", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						} else {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_CREDIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_CREDIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 2, "C", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						}
						txnDetails.setCodCcBrn(Integer.parseInt(Amortizes.getCodCCBrn()));
					} else if (i == 2) {
						txnDetails.setAmtTxnLcy(amortAccount.getGiftPriceGross().multiply(multiplier));
						txnDetails.setAmtTxnTcy(txnDetails.getAmtTxnLcy().multiply(rate));
						if (status.equalsIgnoreCase("OPENING")) {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_CREDIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_CREDIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 1, "C", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						} else {
							txnDetails.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_DEBIT);
							txnDetails.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_DEBIT);
							pkAmort = new AmortizeGLNoPK(Integer.parseInt(custom.getAmortType()), 2, "D", 1);
							Amortizes = amortDao.get(pkAmort);
							txnDetails.setCodAcctNo(Amortizes.getGLNo());
						}
						txnDetails.setCodCcBrn(Integer.parseInt(Amortizes.getCodCCBrn()));
					}
					txnDetails.setCodLob(0);
					txnDetails.setProdCod(0);
					txnDetails.setCodUserId("INTFCAW");
					txnDetails.setTypTxn(3);

					StringBuilder TxnBuild = new StringBuilder();
					//txnDetails.setTarget("EXT_TXNUPLOAD_DETAILRECDDTO");
					//txnDetails.setCompositeId(txnPk);
					txnDetails.setTxnCurrency(currency);
					txnDetails.setConvRate(rate);
					txnDetails.setReferenceNo("1");
					txnDetails.setRefDocNo(BatchNo);
					txnDetails.setTxtTxnDesc(getCustom().getAccrualID().concat(" ").concat(getCustom().getAcctNo()));
					TxnBuild.append(txnDetails.getRecType()).append(":").append(txnDetails.getTypTxn()).append(":").append(txnDetails.getCodAcctNo().trim()).append(":");
					TxnBuild.append(txnDetails.getCodCcBrn()).append(":").append(txnDetails.getCodLob()).append(":").append("INTFCAW").append(":");
					TxnBuild.append(txnDetails.getTxnType()).append(":").append(txnDetails.getCodTxn()).append(":").append(txnDetails.getProdCod()).append(":");
					TxnBuild.append(txnDetails.getDatTxn()).append(":").append(txnDetails.getCodDrCr()).append(":").append(txnDetails.getDatValue()).append(":");
					TxnBuild.append(txnDetails.getTxnCurrency()).append(":").append(txnDetails.getAmtTxnLcy()).append(":").append(txnDetails.getAmtTxnTcy()).append(":");
					TxnBuild.append(txnDetails.getConvRate()).append(":").append(txnDetails.getReferenceNo()).append(":").append(txnDetails.getRefDocNo()).append(":");
					TxnBuild.append(txnDetails.getTxtTxnDesc());
					txnDetails.setData(TxnBuild.toString());
					try {
						txnDetails.generateLength();
						txnDetailDao.insert(txnDetails);

						getLogger().info("GETiNSER : " + generalRecid);
					} catch (Exception e) {
						getLogger().info("INSERT FAILED ARREARS" + e, e);
					}
					TxnBuild.delete(0, TxnBuild.length());
				}
			}

			getLogger().info("START GENERAL DETAIL");
			generalRecid++;
			TmpTxnUploadFooter txnFooter = new TmpTxnUploadFooter(txnHeader, generalRecid);
			//txnFooter.setTarget("EXT_TXNUPLOAD_FOOTERRECDDTO");
			BigDecimal footerAmt = new BigDecimal(0.0);
			footerAmt = footerAmt.add(amortAccount.getGiftPriceGross().multiply(multiplier));
			getLogger().info("footerAmount :" + footerAmt);
			txnFooter.setAmtCr(footerAmt);
			txnFooter.setAmtDr(footerAmt);
			if (getCustom().getAmortType().equalsIgnoreCase("1")) {
				if (status.equalsIgnoreCase("OPENING")) {
					txnFooter.setNoOfCr(2);
					txnFooter.setNoOfDr(1);
				} else {
					txnFooter.setNoOfCr(1);
					txnFooter.setNoOfDr(2);
				}
			} else {
				txnFooter.setNoOfCr(1);
				txnFooter.setNoOfDr(1);
			}
			//txnFooter.setCompositeId(txnPk);
			try {
				txnFooter.generateLength();
				txnFooterDao.insert(txnFooter);
			} catch (Exception e) {
				getLogger().info("INSERT FAILED TXNheaderFooter" + e, e);
			}
			//idBatch = BatchNo;
			// txnupload
			xref.setAmount(amortAccount.getHoldAmount().toString());
			xref.setExtType("TXNUPLOAD");
		}
		try {
			xrefDao.insert(xref);
		} catch (Exception e) {
			getLogger().info("INSERT FAILED XREF" + e, e);
		}
	}

	private Date getNearestDate(List<Date> dates, Date currentDate) {
		long minDiff = -1, currentTime = currentDate.getTime();
		Date minDate = null;
		for (Date date : dates) {
			long diff = Math.abs(currentTime - date.getTime());
			if ((minDiff == -1) || (diff < minDiff)) {
				minDiff = diff;
				minDate = date;
			}
		}
		getLogger().info("GETDATE : " + minDate);
		return minDate;
	}

	private String gefuRespondProcessingTXN() {
		String Status = "SUCCESS";
		SimpleDateFormat dateFormat = new SimpleDateFormat(StatusDefinition.patternJoin);
		TmpTxnUploadHeaderDAO txnHeaderDao = new TmpTxnUploadHeaderDAO(getHSession());
		// get gefu parameter from table parameter
		String AmTxn = PropertyPersister.AMORTTXNPARAM;
		String yyhhMMS = dateFormat.format(SchedulerUtil.getTime());

		List Parameter = new ArrayList();
		Parameter.add(getBatchTXN());
		StringTokenizer tokens = new StringTokenizer(AmTxn, ";");
		while (tokens.hasMoreTokens()) {
			Parameter.add(tokens.nextElement());
		}
		Parameter.set(Parameter.size() - 1, Parameter.get(Parameter.size() - 1).toString().concat(yyhhMMS));
		Parameter.add(getProfileName());
		getLogger().info("BEFORE RUN TXNUPLOAD :" + Parameter);
		Integer run = null;
		try {
			run = txnHeaderDao.runTxn(Parameter);
			getLogger().info("FINISH TXNUPLOAD :" + run);
		} catch (Exception e) {
			getLogger().info("FAILED TO GEFU :" + e, e);
			run = 0;
		}
		try {
			if (run.compareTo(1) == 0) {
				getLogger().info("RUN TXN_GEFU :" + run);
				TmpGefuResponsDao gefuDao = new TmpGefuResponsDao(getHSession());
				TmpGefuRespons tmpGefu = new TmpGefuRespons();
				TmpGefuResponsPK gefuPK = new TmpGefuResponsPK();
				gefuPK.setBatchNo(getBatchTXN());
				gefuPK.setOcpackage("PK_GEFU");
				gefuPK.setOcfunction("responsTxnUpload");
				tmpGefu.setCompositeId(gefuPK);
				tmpGefu.setIdScheduler(0);
				tmpGefu.setModuleDesc("AMORTIZE TXN");
				gefuDao.insert(tmpGefu);
			} else {
				Status = "FAILED";
			}
			getLogger().info("FINISH SETTING TMPGEFU");
		} catch (Exception e) {
			getLogger().info("EXCEPTION :" + e, e);
		}
		return Status;
	}

	private Integer AccountJournal() {
		ChAcctMastDAO accDao = new ChAcctMastDAO(getHSession());
		AmortizeAccount persistence = new AmortizeAccount();
		AmortizeAccountDAO dao = new AmortizeAccountDAO(getHSession());
		int flag = 0;
		Integer idAccount;
		ChAcctMastPK cpk = new ChAcctMastPK();
		cpk.setCodAcctNo(BdsmUtil.rightPad(amortAccount.getAccountNo(), 16));
		cpk.setCodEntityVpd(modelPKfcr.getCodEntityVpd());
		cpk.setFlgMntStatus(modelPKfcr.getFlgMntStatus());

		try {
			persistence = dao.get(Integer.parseInt(custom.getSequenceID()));
			if (custom.getSequenceID().equalsIgnoreCase("0")) {
				flag = 0;
			} else {
				flag = 2;
			}
		} catch (NumberFormatException numberFormatException) {
			getLogger().info("EXCEPTION: " + numberFormatException);
			getLogger().info("FAILED TO PARSE INT : " + custom.getSequenceID());
			flag = 1;
		} catch (HibernateException noExtc) {
			getLogger().info("EXCEPTION HIBERNATE: " + noExtc);
			getLogger().info("FAILED TO PARSE INT : " + custom.getSequenceID());
			flag = 2;
		}
		if (flag == 0) {
			
			modelFCR = accDao.get(cpk);
			persistence = new AmortizeAccount();
			persistence.setAccountNo(amortAccount.getAccountNo());
			persistence.setHoldAmount(amortAccount.getHoldAmount());
			persistence.setOpenDate(amortAccount.getOpenDate());
			persistence.setProgramDetailId(amortAccount.getProgramDetailId());
			persistence.setProgramId(amortAccount.getProgramId());
			persistence.setProgramName(amortAccount.getProgramName());
			persistence.setStatus(AmortizeAccount.STATUS_OPENED);
			persistence.setIdMaintainedBy(idUser);
			persistence.setIdMaintainedSpv(spvUser);
			persistence.setDtmCreated(SchedulerUtil.getTime());
			persistence.setDtmCreatedSpv(SchedulerUtil.getTime());
			persistence.setGiftPrice(amortAccount.getGiftPrice());
			persistence.setGiftPriceGross(amortAccount.getGiftPriceGross());
			persistence.setTaxAmount(amortAccount.getTaxAmount());
			persistence.setTaxPercent(amortAccount.getTaxPercent());
			persistence.setType(amortAccount.getType());
			dao.insert(persistence);
			idAccount = persistence.getId();
		} else if (flag == 2) {
			persistence.setCancelDate(SchedulerUtil.getTime());
			persistence.setStatus(AmortizeAccount.STATUS_CANCELED);
			persistence.setIdMaintainedBy(idUser);
			persistence.setIdMaintainedSpv(spvUser);
			dao.update(persistence);
			idAccount = Integer.parseInt(custom.getSequenceID());
		} else {
			getLogger().info("FAILED TO PROCESS");
			idAccount = 0;
		}
		getLogger().info("ID :" + idAccount);
		return idAccount;
	}

	/**
	 * @return the chMastRef
	 */
	public ChAcctMastReflection getChMastRef() {
		return chMastRef;
	}

	/**
	 * @param chMastRef the chMastRef to set
	 */
	public void setChMastRef(ChAcctMastReflection chMastRef) {
		this.chMastRef = chMastRef;
	}

	/**
	 * @return the custom
	 */
	public AmortizeCustomOpening getCustom() {
		return custom;
	}

	/**
	 * @param custom the custom to set
	 */
	public void setCustom(AmortizeCustomOpening custom) {
		this.custom = custom;
	}

	/**
	 * @return the modelLIst
	 */
	public List getModelLIst() {
		return modelLIst;
	}

	/**
	 * @param modelLIst the modelLIst to set
	 */
	public void setModelLIst(List modelLIst) {
		this.modelLIst = modelLIst;
	}

	/**
	 * @return the StatusProc
	 */
	public String getStatusProc() {
		return StatusProc;
	}

	/**
	 * @param StatusProc the StatusProc to set
	 */
	public void setStatusProc(String StatusProc) {
		this.StatusProc = StatusProc;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the batchTXN
	 */
	public String getBatchTXN() {
		return batchTXN;
	}

	/**
	 * @param batchTXN the batchTXN to set
	 */
	public void setBatchTXN(String batchTXN) {
		this.batchTXN = batchTXN;
	}

	/**
	 * @return the idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the spvUser
	 */
	public String getSpvUser() {
		return spvUser;
	}

	/**
	 * @param spvUser the spvUser to set
	 */
	public void setSpvUser(String spvUser) {
		this.spvUser = spvUser;
	}
}
