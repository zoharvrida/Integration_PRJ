/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu25501;

import bdsm.model.AmortizeCustomOpening;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.math.BigDecimal;
import java.util.*;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00019722
 */
public class Menu25501Action extends BaseContentAction {

	private String ACCOUNT_ACTION = "AmortOpenReg_get.action";
	private String TERM_ACTION = "AmortOpenReg_list.action";
	private String ADD_ACTION = "AmortOpenReg_insert.action";
	private String DELETE_ACTION = "AmortOpenReg_delete.action";
	private AmortizeCustomOpening custom;
	//25501- ScreenField
	private String AccountNo;
	private String codProds;
	private String giftCode;
	private String giftCodes;
	private String programIds;
	private String programName;
	private String Term;
	private String Status;
	private String Opendate;
	private String Canceldate;
	private String giftPrice;
	private String holdAmount;
	private String MinAmount;
	private String MaxAmount;
	private String idSequence;
	private String idProgram_details;
	private String taxPct;
	private String type;
	private String idMaintainedBy;
	private String idMaintainedSpv;
	private String accrualID;
	private String voucherType;
	private String giftGross;
	private String taxAmount;
	//Value on Account Validation
	private String acctSearch;
	private String codprod;
	private String prodDesc;
	private String reason;
	private String datesIf;
	// changing Mode
	private String mode;
	private String gefuStat;
	private String modes;
	private String state;
	private Date tempDate;

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap = null;
		HashMap resultProd = null;
		String reqResult = null;
		String errorCode = null;

		getLogger().info("MODE :" + mode);
		getLogger().info("STATE :" + state);
		if (state == null) {
			state = "VALIDATE";
		}
		//DateUtility.DATE_FORMAT_YYYYMMDD

		getLogger().info("user : " + getIdMaintainedBy());

		// selain 1 dan 5 Account

		if (state.equalsIgnoreCase("TERM")) {
			String Account = BdsmUtil.rightPad(AccountNo, 16);
			getLogger().info("Account : " + Account);
			map.put("custom.acctNo", Account);
			map.put("custom.Term", Term);
			map.put("custom.Mode", mode);
			map.put("custom.codProd", codProds);
			map.put("custom.giftCode", giftCodes.toUpperCase());
			map.put("custom.state", state);
			map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
			result = HttpUtil.request(getBdsmHost() + TERM_ACTION, map);
		} else {
			String Account = BdsmUtil.rightPad(getAcctSearch(), 16);
			getLogger().info("Account : " + Account);
			map.put("custom.instrMode", "VALIDATE");
			map.put("chMastRef.codAcctNo", Account);
			map.put("chMastRef.flgMntStatus", "A");
			map.put("chMastRef.codEntityVpd", "11");
			map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
			result = HttpUtil.request(getBdsmHost() + ACCOUNT_ACTION, map);
		}
		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");

		} catch (JSONException ex) {
			getLogger().fatal(ex, ex);
		}
		if (state.equalsIgnoreCase("TERM")) {
			List resultModel = new ArrayList();
			//resultMap = (HashMap) resultMap.get("modelLIst");
			resultModel = (List) resultMap.get("modelLIst");
			resultMap = (HashMap) resultModel.get(0);

			giftPrice = resultMap.get("GIFT_PRICE").toString();
			MinAmount = resultMap.get("MINIMUM_HOLD").toString();
			MaxAmount = resultMap.get("MAXIMUM_HOLD").toString();
			idSequence = resultMap.get("ID").toString();
			try {
				getLogger().info("DATE NOW :" + resultMap.get("DATEEFF").toString());
			} catch (Exception e) {
				getLogger().info("NULL LHO!!" + e, e);
			}
			try {
				holdAmount = resultMap.get("HOLD_AMOUNT").toString();
				getLogger().info("BEFORE :" + holdAmount);
				BigDecimal holdTemp = new BigDecimal(holdAmount);
				holdAmount = holdTemp.setScale(2).toPlainString();
				getLogger().info("AFTER :" + holdAmount);
			} catch (Exception e) {
				holdAmount = "";
			}
			Status = resultMap.get("STATUS").toString();
			idProgram_details = resultMap.get("PROGRAM_DETAIL_ID").toString();
			Term = resultMap.get("TERM").toString();
			try {
				Opendate = resultMap.get("OPEN_DATE").toString();
				if (Opendate.equalsIgnoreCase("0")) {
					Opendate = resultMap.get("DATEEFF").toString();
				}
			} catch (Exception e) {
				Opendate = resultMap.get("DATEEFF").toString();
			}
			List dateToken = new ArrayList();
			StringTokenizer opendateToken = new StringTokenizer(Opendate, "T");
			while (opendateToken.hasMoreTokens()) {
				dateToken.add(opendateToken.nextToken());
			}
			Opendate = dateToken.get(0).toString();
			getLogger().info(Opendate);
			try {
				Canceldate = resultMap.get("CANCEL_DATE").toString();
				if (Canceldate.equalsIgnoreCase("0")) {
					Canceldate = resultMap.get("DATEEFF").toString();
				}
			} catch (Exception e) {
				Canceldate = resultMap.get("DATEEFF").toString();
			}
			dateToken = new ArrayList();
			opendateToken = new StringTokenizer(Canceldate, "T");
			while (opendateToken.hasMoreTokens()) {
				dateToken.add(opendateToken.nextToken());
			}
			Canceldate = dateToken.get(0).toString();
			getLogger().info("CANCEL DATE :" + Canceldate);
			giftGross = resultMap.get("GIFT_PRICE_GROSS").toString();
			taxAmount = resultMap.get("TAX_AMOUNT").toString();
			return "term";
		} else {
			resultMap = (HashMap) resultMap.get("custom");

			//if (StringUtils.isBlank(errorCode)) {
			setProdDesc(resultMap.get("namProduct").toString());
			setCodprod(resultMap.get("codProd").toString());
			//setDatesIf(mode);
			//}
			setReason(getText(errorCode));
			return "validation";
		}
	}

    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap = null;
		HashMap getID = null;
		String reqResult = null;
		String errorCode = null;

		this.getLogger().info("Acct No :" + AccountNo);
		this.getLogger().info("cod Prod :" + codProds);
		this.getLogger().info("gift Code :" + giftCodes.toUpperCase());
		this.getLogger().info("Program Id :" + programIds);
		this.getLogger().info("Program Name :" + programName);
		this.getLogger().info("Term :" + Term);
		this.getLogger().info("Status :" + Status);
		this.getLogger().info("OpenCancelDate :" + Opendate);
		this.getLogger().info("giftPrice :" + giftPrice);
		this.getLogger().info("holdAmount :" + holdAmount);
		this.getLogger().info("minHoldAmount :" + MinAmount);
		this.getLogger().info("maxHoldAmount :" + MaxAmount);
		this.getLogger().info("sequenceID :" + idSequence);
		this.getLogger().info("id_programDetails :" + idProgram_details);
		this.getLogger().info("tax :" + taxPct);
		this.getLogger().info("amortType :" + type);
		this.getLogger().info("accrualID :" + accrualID);
		this.getLogger().info("voucherType :" + voucherType);

		getLogger().info("user : " + getIdMaintainedBy());
		getLogger().info("userSPV : " + getIdMaintainedSpv());
		String Account = BdsmUtil.rightPad(AccountNo, 16);

		//voucherType = "1"; // temporary
		map.put("custom.acctNo", Account);
		map.put("custom.codProd", codProds);
		map.put("custom.giftCode", giftCodes.toUpperCase());
		map.put("custom.programID", programIds);
		map.put("custom.programName", programName);
		map.put("custom.Term", Term);
		map.put("custom.Status", Status);
		map.put("custom.OpenCancelDate", Opendate);
		map.put("custom.giftPrice", giftPrice);
		String HoldAmounts = holdAmount.replace(",", "");
		map.put("custom.holdAmount", HoldAmounts);

		map.put("custom.minHoldAmount", MinAmount);
		map.put("custom.maxHoldAmount", MaxAmount);
		map.put("custom.sequenceID", idSequence);
		map.put("custom.id_programDetails", idProgram_details);
		map.put("custom.tax", taxPct);
		map.put("custom.amortType", type);
		map.put("custom.accrualID", accrualID);
		map.put("custom.voucherType", voucherType);
		map.put("chMastRef.flgMntStatus", "A");
		map.put("chMastRef.codEntityVpd", "11");
		map.put("idUser", getIdMaintainedBy());
		map.put("spvUser", getIdMaintainedSpv());


		map.put("custom.instrMode", "GLCHECKING");
		map.put("chMastRef.codAcctNo", Account);
		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
		result = HttpUtil.request(getBdsmHost() + ACCOUNT_ACTION, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
			getLogger().info("ERRORCODE :" + errorCode);
		} catch (JSONException ex) {
			getLogger().fatal(ex, ex);
		}

		if (reqResult.equals(ActionSupport.SUCCESS)) {
			map.put("StatusProc", "1");
			map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

			result = HttpUtil.request(getBdsmHost() + ADD_ACTION, map);

			try {
				resultMap = (HashMap) JSONUtil.deserialize(result);
				reqResult = (String) resultMap.get("jsonStatus");
				errorCode = (String) resultMap.get("errorCode");
			} catch (JSONException ex) {
				getLogger().fatal(ex, ex);
			}

			map.put("profileName", resultMap.get("profileName").toString());
			map.put("batchTXN", resultMap.get("batchTXN").toString());

			map.put("StatusProc", "2");
			map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
			result = HttpUtil.request(getBdsmHost() + ADD_ACTION, map);

			try {
				resultMap = (HashMap) JSONUtil.deserialize(result);
				reqResult = (String) resultMap.get("jsonStatus");
				errorCode = (String) resultMap.get("errorCode");
			} catch (JSONException ex) {
				getLogger().fatal(ex, ex);
			}
		}
		getID = (HashMap) resultMap.get("custom");
		String getGL = (String) getID.get("glCode");
		String getBranch = (String) getID.get("branch");
		String getLOB = (String) getID.get("LOBS");

		if (reqResult.equals(ActionSupport.ERROR)) {
			addActionError("Fail To Create Amort Account : " + getAccountNo() + " REASON :" + getText(errorCode)
					+ "For GL_NO :" + getGL + ",Branch :" + getBranch + ",LOB :" + getLOB);
		} else if (reqResult.equals(ActionSupport.SUCCESS)) {
			addActionMessage("New Amort Account :" + getAccountNo() + " Created Successfully");
		}
		return ActionSupport.SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap = null;
		String reqResult = null;
		String errorCode = null;

		//DateUtility.DATE_FORMAT_YYYYMMDD
		HashMap<String, String> params = new HashMap<String, String>();
		StringBuilder batchNos = new StringBuilder();

		this.getLogger().info("TRUE BATCH :" + batchNos);

		getLogger().info("user : " + getIdMaintainedBy());

		//voucherType = "1"; // temporary
		this.getLogger().info("Acct No :" + AccountNo);
		this.getLogger().info("cod Prod :" + codProds);
		this.getLogger().info("gift Code :" + giftCodes.toUpperCase());
		this.getLogger().info("Program Id :" + programIds);
		this.getLogger().info("Program Name :" + programName);
		this.getLogger().info("Term :" + Term);
		this.getLogger().info("Status :" + Status);
		this.getLogger().info("OpenCancelDate :" + Opendate);
		this.getLogger().info("giftPrice :" + giftPrice);
		this.getLogger().info("holdAmount :" + holdAmount);
		this.getLogger().info("minHoldAmount :" + MinAmount);
		this.getLogger().info("maxHoldAmount :" + MaxAmount);
		this.getLogger().info("sequenceID :" + idSequence);
		this.getLogger().info("id_programDetails :" + idProgram_details);
		this.getLogger().info("tax :" + taxPct);
		this.getLogger().info("amortType :" + type);
		this.getLogger().info("giftGross :" + giftGross);
		this.getLogger().info("taxAmount :" + taxAmount);
		this.getLogger().info("voucherType :" + voucherType);

		String Account = BdsmUtil.rightPad(AccountNo, 16);
		map.put("custom.acctNo", Account);
		map.put("custom.codProd", codProds);
		map.put("custom.giftCode", giftCodes.toUpperCase());
		map.put("custom.programID", programIds);
		map.put("custom.programName", programName);
		map.put("custom.Term", Term);
		map.put("custom.Status", Status);
		map.put("custom.OpenCancelDate", Canceldate);
		map.put("custom.giftPrice", giftPrice);
		map.put("custom.holdAmount", holdAmount);
		map.put("custom.minHoldAmount", MinAmount);
		map.put("custom.maxHoldAmount", MaxAmount);
		map.put("custom.sequenceID", idSequence);
		map.put("custom.id_programDetails", idProgram_details);
		map.put("custom.tax", taxPct);
		map.put("custom.amortType", type);
		map.put("custom.accrualID", accrualID);
		map.put("custom.giftGrossPrice", giftGross);
		map.put("custom.taxAmount", taxAmount);
		map.put("custom.voucherType", voucherType);
		map.put("chMastRef.flgMntStatus", "A");
		map.put("chMastRef.codEntityVpd", "11");
		map.put("idUser", getIdMaintainedBy());
		map.put("spvUser", getIdMaintainedSpv());

		map.put("custom.instrMode", "RECORDCHECKING");
		map.put("chMastRef.codAcctNo", Account);
		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
		result = HttpUtil.request(getBdsmHost() + ACCOUNT_ACTION, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
			getLogger().info("ERRORCODE :" + errorCode);
		} catch (JSONException ex) {
			getLogger().fatal(ex, ex);
		}

		if (reqResult.equals(ActionSupport.SUCCESS)) {
			map.put("StatusProc", "1");
			map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

			result = HttpUtil.request(getBdsmHost() + DELETE_ACTION, map);

			try {
				resultMap = (HashMap) JSONUtil.deserialize(result);
				reqResult = (String) resultMap.get("jsonStatus");
				errorCode = (String) resultMap.get("errorCode");
			} catch (JSONException ex) {
				getLogger().fatal(ex, ex);
			}
			if (reqResult.equals(ActionSupport.SUCCESS)) {
				map.put("profileName", resultMap.get("profileName").toString());
				map.put("batchTXN", resultMap.get("batchTXN").toString());
				map.put("StatusProc", "2");
				map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
				result = HttpUtil.request(getBdsmHost() + DELETE_ACTION, map);

				try {
					resultMap = (HashMap) JSONUtil.deserialize(result);
					reqResult = (String) resultMap.get("jsonStatus");
					errorCode = (String) resultMap.get("errorCode");
				} catch (JSONException ex) {
					getLogger().fatal(ex, ex);
				}
			}
		}

		if (reqResult.equals(ActionSupport.ERROR)) {
			addActionError("Fail To Cancel Amort Account :" + getAccountNo() + "Reason :" + getText(errorCode));
		} else if (reqResult.equals(ActionSupport.SUCCESS)) {
			addActionMessage("Amort Account:" + getAccountNo() + " Cancelled Successfully");
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * @return the gefuStat
	 */
	public String getGefuStat() {
		return gefuStat;
	}

	/**
	 * @param gefuStat the gefuStat to set
	 */
	public void setGefuStat(String gefuStat) {
		this.gefuStat = gefuStat;
	}

	/**
	 * @return the acctSearch
	 */
	public String getAcctSearch() {
		return acctSearch;
	}

	/**
	 * @param acctSearch the acctSearch to set
	 */
	public void setAcctSearch(String acctSearch) {
		this.acctSearch = acctSearch;
	}

	/**
	 * @return the codprod
	 */
	public String getCodprod() {
		return codprod;
	}

	/**
	 * @param codprod the codprod to set
	 */
	public void setCodprod(String codprod) {
		this.codprod = codprod;
	}

	/**
	 * @return the prodDesc
	 */
	public String getProdDesc() {
		return prodDesc;
	}

	/**
	 * @param prodDesc the prodDesc to set
	 */
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the datesIf
	 */
	public String getDatesIf() {
		return datesIf;
	}

	/**
	 * @param datesIf the datesIf to set
	 */
	public void setDatesIf(String datesIf) {
		this.datesIf = datesIf;
	}

	/**
	 * @return the modes
	 */
	public String getModes() {
		return modes;
	}

	/**
	 * @param modes the modes to set
	 */
	public void setModes(String modes) {
		this.modes = modes;
	}

	/**
	 * @return the AccountNo
	 */
	public String getAccountNo() {
		return AccountNo;
	}

	/**
	 * @param AccountNo the AccountNo to set
	 */
	public void setAccountNo(String AccountNo) {
		this.AccountNo = AccountNo;
	}

	/**
	 * @return the codProds
	 */
	public String getCodProds() {
		return codProds;
	}

	/**
	 * @param codProds the codProds to set
	 */
	public void setCodProds(String codProds) {
		this.codProds = codProds;
	}

	/**
	 * @return the giftCode
	 */
	public String getGiftCode() {
		return giftCode;
	}

	/**
	 * @param giftCode the giftCode to set
	 */
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}

	/**
	 * @return the programIds
	 */
	public String getProgramIds() {
		return programIds;
	}

	/**
	 * @param programIds the programIds to set
	 */
	public void setProgramIds(String programIds) {
		this.programIds = programIds;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @return the Term
	 */
	public String getTerm() {
		return Term;
	}

	/**
	 * @param Term the Term to set
	 */
	public void setTerm(String Term) {
		this.Term = Term;
	}

	/**
	 * @return the Status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * @param Status the Status to set
	 */
	public void setStatus(String Status) {
		this.Status = Status;
	}

	/**
	 * @return the Opendate
	 */
	public String getOpendate() {
		return Opendate;
	}

	/**
	 * @param Opendate the Opendate to set
	 */
	public void setOpendate(String Opendate) {
		this.Opendate = Opendate;
	}

	/**
	 * @return the Canceldate
	 */
	public String getCanceldate() {
		return Canceldate;
	}

	/**
	 * @param Canceldate the Canceldate to set
	 */
	public void setCanceldate(String Canceldate) {
		this.Canceldate = Canceldate;
	}

	/**
	 * @return the giftPrice
	 */
	public String getGiftPrice() {
		return giftPrice;
	}

	/**
	 * @param giftPrice the giftPrice to set
	 */
	public void setGiftPrice(String giftPrice) {
		this.giftPrice = giftPrice;
	}

	/**
	 * @return the holdAmount
	 */
	public String getHoldAmount() {
		return holdAmount;
	}

	/**
	 * @param holdAmount the holdAmount to set
	 */
	public void setHoldAmount(String holdAmount) {
		this.holdAmount = holdAmount;
	}

	/**
	 * @return the MinAmount
	 */
	public String getMinAmount() {
		return MinAmount;
	}

	/**
	 * @param MinAmount the MinAmount to set
	 */
	public void setMinAmount(String MinAmount) {
		this.MinAmount = MinAmount;
	}

	/**
	 * @return the MaxAmount
	 */
	public String getMaxAmount() {
		return MaxAmount;
	}

	/**
	 * @param MaxAmount the MaxAmount to set
	 */
	public void setMaxAmount(String MaxAmount) {
		this.MaxAmount = MaxAmount;
	}

	/**
	 * @return the idSequence
	 */
	public String getIdSequence() {
		return idSequence;
	}

	/**
	 * @param idSequence the idSequence to set
	 */
	public void setIdSequence(String idSequence) {
		this.idSequence = idSequence;
	}

	/**
	 * @return the idProgram_details
	 */
	public String getIdProgram_details() {
		return idProgram_details;
	}

	/**
	 * @param idProgram_details the idProgram_details to set
	 */
	public void setIdProgram_details(String idProgram_details) {
		this.idProgram_details = idProgram_details;
	}

	/**
	 * @return the taxPct
	 */
	public String getTaxPct() {
		return taxPct;
	}

	/**
	 * @param taxPct the taxPct to set
	 */
	public void setTaxPct(String taxPct) {
		this.taxPct = taxPct;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the idMaintainedBy
	 */
	public String getIdMaintainedBy() {
		return idMaintainedBy;
	}

	/**
	 * @param idMaintainedBy the idMaintainedBy to set
	 */
	public void setIdMaintainedBy(String idMaintainedBy) {
		this.idMaintainedBy = idMaintainedBy;
	}

	/**
	 * @return the idMaintainedSpv
	 */
	public String getIdMaintainedSpv() {
		return idMaintainedSpv;
	}

	/**
	 * @param idMaintainedSpv the idMaintainedSpv to set
	 */
	public void setIdMaintainedSpv(String idMaintainedSpv) {
		this.idMaintainedSpv = idMaintainedSpv;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the giftCodes
	 */
	public String getGiftCodes() {
		return giftCodes;
	}

	/**
	 * @param giftCodes the giftCodes to set
	 */
	public void setGiftCodes(String giftCodes) {
		this.giftCodes = giftCodes;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the accrualID
	 */
	public String getAccrualID() {
		return accrualID;
	}

	/**
	 * @param accrualID the accrualID to set
	 */
	public void setAccrualID(String accrualID) {
		this.accrualID = accrualID;
	}

	/**
	 * @return the voucherType
	 */
	public String getVoucherType() {
		return voucherType;
	}

	/**
	 * @param voucherType the voucherType to set
	 */
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	/**
	 * @return the giftGross
	 */
	public String getGiftGross() {
		return giftGross;
	}

	/**
	 * @param giftGross the giftGross to set
	 */
	public void setGiftGross(String giftGross) {
		this.giftGross = giftGross;
	}

	/**
	 * @return the taxAmount
	 */
	public String getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
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
     * 
     * @return
     */
    public AmortizeCustomOpening getModel() {
		return this.custom;
	}

	/**
	 * @return the tempDate
	 */
	public Date getTempDate() {
		return tempDate;
	}

	/**
	 * @param tempDate the tempDate to set
	 */
	public void setTempDate(Date tempDate) {
		this.tempDate = tempDate;
	}
}
