package bdsm.web.menu28302;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.model.ScreenMsg;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import bdsm.web.ScreenMsgService;
import bdsm.web.request.JSONService;


/**
 * @author v00020800
 */
@SuppressWarnings("serial")
public class Menu28302Action extends BaseContentAction {
	private static final String ACTION_INSERT = "mfcUdMasterLLD_insert.action";
	private static final String ACTION_EDIT = "mfcUdMasterLLD_update.action";
	private static final String ACTION_DELETE = "mfcUdMasterLLD_delete.action";
	private static final String ACTION_GET_FCYIDR_RATE = "BICloseRate_callMethod.action";
	private static final String NAM_MENU = "Underlying Document Maintenance";
	private static final String messageAction = "TXT_LLD_AUTH";
	
	private Integer noCif;
	private String namCustFull;
	private String noUd;
	private String catUD;
	private String txtCatUD;
	private String typeUD;
	private String txtTypeUD;
	private String payeeName;
	private String payeeCountry;
	private String tradingProduct;
	private String dtIssued;
	private String dtExpiry;
	private String ccyUd;
	private BigDecimal amtLimit;
	private String remarks;
	private Integer cdBranch;
	private String status;
	private BigDecimal ratUsdIdr;
	private BigDecimal ratFcyIdr;
	private BigDecimal usdIdrRate;
	private BigDecimal amtLimitUSD;
    private String amtLimitScrUSD;
	private String idMaintainedBy;
	private String idMaintainedSpv;
	private ScreenMsg scrMsg;
	private String msgString;
    private JSONService JSONUtil = new JSONService();

    /**
     * 
     * @return
     */
    @SkipValidation
	@SuppressWarnings("rawtypes")
	public String getInqRatUsdIdr() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap;
		HashMap mapModel;
		
		map.put("methodName", "getLastEOMRate");
		map.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		result = HttpUtil.request(this.getBdsmHost() + ACTION_GET_FCYIDR_RATE, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			mapModel = (HashMap) resultMap.get("model");
			
			if (mapModel != null)
				this.setRatUsdIdr(new BigDecimal(((Double) mapModel.get("midRate")).toString()));
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		return "inqRatUsdIdr";
	}

    /**
     * 
     * @return
     */
    @SkipValidation
	@SuppressWarnings("rawtypes")
	public String getInqRatFcyIdr() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap;
		HashMap mapModel;

		map.put("methodName", "getLastEOMRate");
		map.put("namCcy", this.ccyUd);
		map.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		result = HttpUtil.request(this.getBdsmHost() + ACTION_GET_FCYIDR_RATE, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			mapModel = (HashMap) resultMap.get("model");
			
			if (mapModel != null)
				this.setRatFcyIdr(new BigDecimal(((Double) mapModel.get("midRate")).toString()));
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		return "inqRatFcyIdr";
	}

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
	@SuppressWarnings("rawtypes")
	public String doAdd() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap;
		String reqResult = null;
		String errorCode = null;

		this.getLogger().debug("noCif: " + this.noCif);
		this.getLogger().debug("noUd: " + this.noUd);
		this.getLogger().debug("catUD: " + this.catUD);
		this.getLogger().debug("txtCatUD: " + this.txtCatUD);
		this.getLogger().debug("typeUD: " + this.typeUD);
		this.getLogger().debug("txtTypeUD: " + this.txtTypeUD);
		this.getLogger().debug("payeeName: " + this.payeeName);
		this.getLogger().debug("payeeCountry: " + this.payeeCountry);
		this.getLogger().debug("tradingProduct: " + this.tradingProduct);
		this.getLogger().debug("dtIssued: " + this.dtIssued);
		this.getLogger().debug("dtExpiry: " + this.dtExpiry);
		this.getLogger().debug("ccyUd: " + this.ccyUd);
		this.getLogger().debug("amtLimit: " + this.amtLimit);
		this.getLogger().debug("amtAvail: " + this.amtLimit);
		this.getLogger().debug("remarks: " + this.remarks);
		this.getLogger().debug("cdBranch: " + this.cdBranch);
		this.getLogger().debug("amtLimitUSD: " + this.amtLimitUSD);
		this.getLogger().debug("usdIdrRate: " + this.usdIdrRate);
		this.getLogger().debug("ratFcyIdr: " + this.ratFcyIdr);
		this.getLogger().debug("idMaintainedBy: " + this.idMaintainedBy);
		this.getLogger().debug("idMaintainedSpv: " + this.idMaintainedSpv);

		map.put("model.compositeId.noCif", String.valueOf(this.noCif));
		map.put("model.compositeId.noUd", this.noUd);
		map.put("model.udTypeCategory", this.catUD);
		map.put("model.udTypeValue", this.typeUD);
		map.put("model.payeeName", this.payeeName);
		map.put("model.payeeCountry", this.payeeCountry);
		map.put("model.tradingProduct", this.tradingProduct);
		map.put("model.dtIssued", this.dtIssued);
		map.put("model.dtExpiry", this.dtExpiry);
		map.put("model.ccyUd", this.ccyUd);
		map.put("model.amtLimit", String.valueOf(this.amtLimit));
		map.put("model.amtAvail", String.valueOf(this.amtLimit));
		map.put("model.remarks", this.remarks);
		map.put("model.cdBranch", String.valueOf(this.cdBranch));
		map.put("model.amtLimitUsd", String.valueOf(this.amtLimitUSD));
		map.put("model.amtAvailUsd", String.valueOf(this.amtLimitUSD));
		map.put("model.ratUsdLim", String.valueOf(this.usdIdrRate));
		map.put("model.ratFcyLim", String.valueOf(this.ratFcyIdr));
		map.put("model.status", "A");
		map.put("model.idMaintainedBy", this.idMaintainedBy);
		map.put("model.idMaintainedSpv", this.idMaintainedSpv);
		map.put("namMenu", NAM_MENU);
		map.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));

		result = HttpUtil.request(this.getBdsmHost() + ACTION_INSERT, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		if (reqResult.equals(ERROR))
			this.addActionError(this.getText(errorCode));
		else if (reqResult.equals(SUCCESS))
			this.addActionMessage(this.getText(errorCode));

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("rawtypes")
	public String doEdit() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap;
		String reqResult = null;
		String errorCode = null;

		this.getLogger().debug("noCif : " + this.noCif);
		this.getLogger().debug("noUd : " + this.noUd);
		this.getLogger().debug("catUD: " + this.catUD);
		this.getLogger().debug("txtCatUD: " + this.txtCatUD);
		this.getLogger().debug("payeeName :" + this.payeeName);
		this.getLogger().debug("payeeCountry :" + this.payeeCountry);
		this.getLogger().debug("tradingProduct :" + this.tradingProduct);
		this.getLogger().debug("dtIssued :" + this.dtIssued);
		this.getLogger().debug("dtExpiry : " + this.dtExpiry);
		this.getLogger().debug("ccyUd : " + this.ccyUd);
		this.getLogger().debug("amtLimit : " + this.amtLimit);
		this.getLogger().debug("amtAvail : " + this.amtLimit);
		this.getLogger().debug("remarks : " + this.remarks);
		this.getLogger().debug("cdBranch : " + this.cdBranch);
		this.getLogger().debug("amtLimitUSD : " + this.amtLimitUSD);
		this.getLogger().debug("amtAvailUsd : " + this.amtLimitUSD);
		this.getLogger().debug("usdIdrRate : " + this.usdIdrRate);
		this.getLogger().debug("ratFcyIdr : " + this.ratFcyIdr);
		this.getLogger().debug("status : " + this.status);
		this.getLogger().debug("idMaintainedBy : " + this.idMaintainedBy);
		this.getLogger().debug("idMaintainedSpv : " + this.idMaintainedSpv);

		map.put("model.compositeId.noCif", String.valueOf(this.noCif));
		map.put("model.compositeId.noUd", this.noUd);
		map.put("model.udTypeCategory", this.catUD);
		map.put("model.udTypeValue", this.typeUD);
		map.put("model.payeeName", this.payeeName);
		map.put("model.payeeCountry", this.payeeCountry);
		map.put("model.tradingProduct", this.tradingProduct);
		map.put("model.dtIssued", this.dtIssued);
		map.put("model.dtExpiry", this.dtExpiry);
		map.put("model.ccyUd", this.ccyUd);
		map.put("model.amtLimit", String.valueOf(this.amtLimit));
		map.put("model.amtAvail", String.valueOf(this.amtLimit));
		map.put("model.remarks", this.remarks);
		map.put("model.cdBranch", String.valueOf(this.cdBranch));
		map.put("model.amtLimitUsd", String.valueOf(this.amtLimitUSD));
		map.put("model.amtAvailUsd", String.valueOf(this.amtLimitUSD));
		map.put("model.ratUsdLim", String.valueOf(this.usdIdrRate));
		map.put("model.ratFcyLim", String.valueOf(this.ratFcyIdr));
		map.put("model.status", this.status);
		map.put("model.idMaintainedBy", this.idMaintainedBy);
		map.put("model.idMaintainedSpv", this.idMaintainedSpv);
		map.put("namMenu", NAM_MENU);
		map.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));

		result = HttpUtil.request(this.getBdsmHost() + ACTION_EDIT, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		if (reqResult.equals(ERROR))
			this.addActionError(this.getText(errorCode));
		else if (reqResult.equals(SUCCESS))
			this.addActionMessage(this.getText(errorCode));

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("rawtypes")
	public String doDelete() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap;
		String reqResult = null;
		String errorCode = null;

		this.getLogger().debug("noCif : " + this.noCif);
		this.getLogger().debug("noUd : " + this.noUd);
		this.getLogger().debug("ccyUd : " + this.ccyUd);
		this.getLogger().debug("idMaintainedBy : " + this.idMaintainedBy);
		this.getLogger().debug("idMaintainedSpv : " + this.idMaintainedSpv);

		map.put("model.compositeId.noCif", String.valueOf(this.noCif));
		map.put("model.compositeId.noUd", this.noUd);
		map.put("model.status", this.status);
		map.put("model.idMaintainedBy", this.idMaintainedBy);
		map.put("model.idMaintainedSpv", this.idMaintainedSpv);
		map.put("namMenu", NAM_MENU);
		map.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));

		result = HttpUtil.request(this.getBdsmHost() + ACTION_DELETE, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		if (reqResult.equals(ERROR))
			this.addActionError(this.getText(errorCode));
		else if (reqResult.equals(SUCCESS))
			this.addActionMessage(this.getText(errorCode));

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @SkipValidation
	public String getMessage() {
		try {
			this.scrMsg = this.getScreenMsgresp(messageAction);
			this.msgString = JSONUtil.serialize(scrMsg);
		}
		catch (JSONException jSONException) {
			this.getLogger().debug("FAILED json convert: " + jSONException, jSONException);
		}
		
		return "mess";
	}


    /**
     * 
     * @param tag
     * @return
     */
    @SkipValidation
	@SuppressWarnings("rawtypes")
	public ScreenMsg getScreenMsgresp(String tag) {
		Map mapScreen = new HashMap();
		Map msgMap = new HashMap();
		ScreenMsg msg = new ScreenMsg();

		ScreenMsgService sms = new ScreenMsgService(this.getServletRequest(), session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
		this.getLogger().info("[Begin] Detect LLD Screen Message MIDRATE-SERVICE()");
		
		mapScreen = sms.getMessage(tag);
		if (mapScreen != null) {
			this.getLogger().debug("LLD MAP: " + mapScreen);
			try {
				msgMap = (HashMap) mapScreen.get("model");
				ClassConverterUtil.MapToClass(msgMap, msg);
			}
			catch (Exception e) {
				this.getLogger().debug(e, e);
			}
		}
		
		return msg;
	}



    /**
     * 
     * @return
     */
    public Integer getNoCif() {
		return this.noCif;
	}
    /**
     * 
     * @param noCif
     */
    public void setNoCif(Integer noCif) {
		this.noCif = noCif;
	}

    /**
     * 
     * @return
     */
    public String getNamCustFull() {
		return this.namCustFull;
	}
    /**
     * 
     * @param namCustFull
     */
    public void setNamCustFull(String namCustFull) {
		this.namCustFull = namCustFull;
	}

    /**
     * 
     * @return
     */
    public String getNoUd() {
		return this.noUd;
	}
    /**
     * 
     * @param noUd
     */
    public void setNoUd(String noUd) {
		this.noUd = noUd;
	}

    /**
     * 
     * @return
     */
    public String getCatUD() {
		return this.catUD;
	}
    /**
     * 
     * @param catUD
     */
    public void setCatUD(String catUD) {
		this.catUD = catUD;
	}

    /**
     * 
     * @return
     */
    public String getTxtCatUD() {
		return this.txtCatUD;
	}
    /**
     * 
     * @param txtCatUD
     */
    public void setTxtCatUD(String txtCatUD) {
		this.txtCatUD = txtCatUD;
	}

    /**
     * 
     * @return
     */
    public String getTypeUD() {
		return this.typeUD;
	}
    /**
     * 
     * @param typeUD
     */
    public void setTypeUD(String typeUD) {
		this.typeUD = typeUD;
	}

    /**
     * 
     * @return
     */
    public String getTxtTypeUD() {
		return this.txtTypeUD;
	}
    /**
     * 
     * @param txtTypeUD
     */
    public void setTxtTypeUD(String txtTypeUD) {
		this.txtTypeUD = txtTypeUD;
	}

    /**
     * 
     * @return
     */
    public String getPayeeName() {
		return this.payeeName;
	}
    /**
     * 
     * @param payeeName
     */
    public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

    /**
     * 
     * @return
     */
    public String getPayeeCountry() {
		return this.payeeCountry;
	}
    /**
     * 
     * @param payeeCountry
     */
    public void setPayeeCountry(String payeeCountry) {
		this.payeeCountry = payeeCountry;
	}

    /**
     * 
     * @return
     */
    public String getTradingProduct() {
		return this.tradingProduct;
	}
    /**
     * 
     * @param tradingProduct
     */
    public void setTradingProduct(String tradingProduct) {
		this.tradingProduct = tradingProduct;
	}

    /**
     * 
     * @return
     */
    public String getDtIssued() {
		return this.dtIssued;
	}
    /**
     * 
     * @param dtIssued
     */
    public void setDtIssued(String dtIssued) {
		this.dtIssued = dtIssued;
	}

    /**
     * 
     * @return
     */
    public String getDtExpiry() {
		return this.dtExpiry;
	}
    /**
     * 
     * @param dtExpiry
     */
    public void setDtExpiry(String dtExpiry) {
		this.dtExpiry = dtExpiry;
	}

    /**
     * 
     * @return
     */
    public String getCcyUd() {
		return this.ccyUd;
	}
    /**
     * 
     * @param ccyUd
     */
    public void setCcyUd(String ccyUd) {
		this.ccyUd = ccyUd;
	}

    /**
     * 
     * @return
     */
    public BigDecimal getAmtLimit() {
		return this.amtLimit;
	}
    /**
     * 
     * @param amtLimit
     */
    public void setAmtLimit(BigDecimal amtLimit) {
		this.amtLimit = amtLimit;
	}

    /**
     * 
     * @return
     */
    public String getRemarks() {
		return this.remarks;
	}
    /**
     * 
     * @param remarks
     */
    public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    /**
     * 
     * @return
     */
    public Integer getCdBranch() {
		return this.cdBranch;
	}
    /**
     * 
     * @param cdBranch
     */
    public void setCdBranch(Integer cdBranch) {
		this.cdBranch = cdBranch;
	}

    /**
     * 
     * @return
     */
    public String getStatus() {
		return this.status;
	}
    /**
     * 
     * @param status
     */
    public void setStatus(String status) {
		this.status = status;
	}

    /**
     * 
     * @return
     */
    public String getIdMaintainedBy() {
		return this.idMaintainedBy;
	}
    /**
     * 
     * @param idMaintainedBy
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
		this.idMaintainedBy = idMaintainedBy;
	}

    /**
     * 
     * @return
     */
    public String getIdMaintainedSpv() {
		return this.idMaintainedSpv;
	}
    /**
     * 
     * @param idMaintainedSpv
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
		this.idMaintainedSpv = idMaintainedSpv;
	}

    /**
     * 
     * @return
     */
    public BigDecimal getRatUsdIdr() {
		return this.ratUsdIdr;
	}
    /**
     * 
     * @param ratUsdIdr
     */
    public void setRatUsdIdr(BigDecimal ratUsdIdr) {
		this.ratUsdIdr = ratUsdIdr;
	}

    /**
     * 
     * @return
     */
    public BigDecimal getRatFcyIdr() {
		return this.ratFcyIdr;
	}
    /**
     * 
     * @param ratFcyIdr
     */
    public void setRatFcyIdr(BigDecimal ratFcyIdr) {
		this.ratFcyIdr = ratFcyIdr;
	}

    /**
     * 
     * @return
     */
    public BigDecimal getUsdIdrRate() {
		return this.usdIdrRate;
	}
    /**
     * 
     * @param usdIdrRate
     */
    public void setUsdIdrRate(BigDecimal usdIdrRate) {
		this.usdIdrRate = usdIdrRate;
	}

    /**
     * 
     * @return
     */
    public BigDecimal getAmtLimitUSD() {
		return this.amtLimitUSD;
	}
    /**
     * 
     * @param amtLimitUSD
     */
    public void setAmtLimitUSD(BigDecimal amtLimitUSD) {
		this.amtLimitUSD = amtLimitUSD;
	}

    /**
     * 
     * @return
     */
    public String getAmtLimitScrUSD() {
		return this.amtLimitScrUSD;
	}
    /**
     * 
     * @param amtLimitScrUSD
     */
    public void setAmtLimitScrUSD(String amtLimitScrUSD) {
		this.amtLimitScrUSD = amtLimitScrUSD;
	}

    /**
     * 
     * @return
     */
    public ScreenMsg getScrMsg() {
		return this.scrMsg;
	}

    /**
     * 
     * @return
     */
    public String getMsgString() {
		return this.msgString;
	}

}
