package bdsm.dialog.json;


import static bdsm.web.Constant.C_WEB_SESSION_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.annotations.JSON;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.Constant;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class Dialog25301GridAction extends BaseDialogAction {
	private static final String AMORTIZE_GIFT_PROGRAM_DETAIL_LIST = "AmortizeGiftProgramDetail_list";
	private static final String AMORTIZE_GIFT_PROGRAM_DETAIL_INSERT = "AmortizeGiftProgramDetail_insert";
	private static final String AMORTIZE_GIFT_PROGRAM_DETAIL_DELETE = "AmortizeGiftProgramDetail_delete";
	
	private String id;
	private String giftCode;
	private String productCode;
	private String effectiveDate;
	private String term;
	private String giftPrice;
	private String minimumHold;
	private String maximumHold;
	private String oper;
	

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List doList() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		String HTTPRequest = AMORTIZE_GIFT_PROGRAM_DETAIL_LIST;
		
		params.put("giftCode", this.giftCode);
		params.put("productCode", this.productCode);
		params.put(C_WEB_SESSION_ID, this.getSessionId());
		params.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		String result = HttpUtil.request(this.getBdsmHost() + HTTPRequest, params);
		this.getLogger().debug(result);

		HashMap ret = (HashMap) JSONUtil.deserialize(result);
		List listRet = (List<? extends Object>) ret.get("modelList");
		String status = (String) ret.get("jsonStatus");
		addActionError(status);
		
		return listRet;
	}
	
    /**
     * 
     * @return
     */
    public String doChange() {
		this.getLogger().info("[Begin] doChange()");
		try {
			if (isValidSession()) {
				this.getLogger().info("operation: " + this.oper);
				
				if ("add".equalsIgnoreCase(this.oper)) {
					Map<String, ? extends Object> resultMap;
					Map<String, String> requestMap = new java.util.HashMap<String, String>();
					String reqResult = null;
					
					this.getLogger().info("giftCode: " + this.giftCode);
					this.getLogger().info("productCode: " + this.productCode);
					this.getLogger().info("effectiveDate: " + this.effectiveDate);
					this.getLogger().info("term: " + this.term);
					this.getLogger().info("giftPrice: " + this.giftPrice);
					this.getLogger().info("minimumHold: " + this.minimumHold);
					this.getLogger().info("maximumHold: " + this.maximumHold);
					
					requestMap.put("giftCode", this.giftCode);
					requestMap.put("productCode", this.productCode);
					requestMap.put("effectiveDate", this.effectiveDate);
					requestMap.put("term", this.term);
					requestMap.put("giftPrice", this.giftPrice);
					requestMap.put("minimumHold", this.minimumHold);
					requestMap.put("maximumHold", this.maximumHold);
					requestMap.put("idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
					requestMap.put(C_WEB_SESSION_ID, this.getSessionId());
					requestMap.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
					
					String result = HttpUtil.request(this.getBdsmHost() + AMORTIZE_GIFT_PROGRAM_DETAIL_INSERT, requestMap);
					
					try {
						resultMap = (java.util.HashMap<String, ? extends Object>) JSONUtil.deserialize(result);
						reqResult = (String) resultMap.get("jsonStatus");
						
						if ("error".equalsIgnoreCase(reqResult))
							this.setErrorMessage(this.getText("25301.error.programdetail.exist"));
					}
					catch (JSONException ex) {
						this.getLogger().fatal(ex, ex);
						this.setErrorMessage(ex.getMessage());
					}
				}
				return SUCCESS;
			}
			else
				return logout();
		}
		finally {
			this.getLogger().info("[ End ] doChange()");
		}
	}
	
    /**
     * 
     * @return
     */
    public String doDelete() {
		this.getLogger().info("[Begin] doDelete()");
		try {
			if (isValidSession()) {
				Map<String, ? extends Object> resultMap;
				Map<String, String> requestMap = new java.util.HashMap<String, String>();
				String reqResult = null;
				
				this.getLogger().info("id: " + this.id);
				requestMap.put("id", this.id);
				requestMap.put("idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
				requestMap.put(C_WEB_SESSION_ID, this.getSessionId());
				requestMap.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
				
				String result = HttpUtil.request(this.getBdsmHost() + AMORTIZE_GIFT_PROGRAM_DETAIL_DELETE, requestMap);
				
				try {
					resultMap = (java.util.HashMap<String, ? extends Object>) JSONUtil.deserialize(result);
					reqResult = (String) resultMap.get("jsonStatus");
					
					if ("error".equalsIgnoreCase(reqResult))
						this.setErrorMessage(this.getText("25301.error.programdetail.used"));
				}
				catch (JSONException ex) {
					this.getLogger().fatal(ex, ex);
					this.setErrorMessage(ex.getMessage());
				}
				
				return SUCCESS;
			}
			else {
				return logout();
			}
		}
		finally {
			this.getLogger().info("[ End ] doDelete()");
		}

	}
	
	
    /**
     * 
     * @return
     */
    @JSON(serialize=false)
	protected String getSessionId() {
		String sessionId = (String) this.session.get(C_WEB_SESSION_ID);
		
		if (sessionId == null) {
			sessionId = ServletActionContext.getRequest().getSession().getId();
			this.session.put(C_WEB_SESSION_ID, sessionId);
		}
		
		return sessionId;
	}
	
	
    /**
     * 
     * @param id
     */
    public void setId(String id) {
		this.id = id;
	}
	
    /**
     * 
     * @param giftCode
     */
    public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	
    /**
     * 
     * @param productCode
     */
    public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
    /**
     * 
     * @param effectiveDate
     */
    public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
    /**
     * 
     * @param term
     */
    public void setTerm(String term) {
		this.term = term;
	}
	
    /**
     * 
     * @param giftPrice
     */
    public void setGiftPrice(String giftPrice) {
		this.giftPrice = giftPrice;
	}
	
    /**
     * 
     * @param minimumHold
     */
    public void setMinimumHold(String minimumHold) {
		this.minimumHold = minimumHold;
	}
	
    /**
     * 
     * @param maximumHold
     */
    public void setMaximumHold(String maximumHold) {
		this.maximumHold = maximumHold;
	}
	
    /**
     * 
     * @param oper
     */
    public void setOper(String oper) {
		this.oper = oper;
	}
	
}
