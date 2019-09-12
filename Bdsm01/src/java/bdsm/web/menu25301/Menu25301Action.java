package bdsm.web.menu25301;


import static bdsm.web.Constant.C_WEB_SESSION_ID;

import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.model.AmortizeProgramMaster;
import bdsm.model.AmortizeProgramMasterPK;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.Constant;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "serial", "unchecked" })
public class Menu25301Action extends bdsm.web.BaseContentAction implements com.opensymphony.xwork2.ModelDriven<AmortizeProgramMaster> {
	private static String AMORTIZE_GIFT_PROGRAM_MASTER_INSERT = "AmortizeGiftProgramMaster_insert";
	private static String AMORTIZE_GIFT_PROGRAM_MASTER_UPDATE = "AmortizeGiftProgramMaster_update";
	
	private AmortizeProgramMaster amortizeProgramMaster;
	private String productCode_widget;
	
	
    /**
     * 
     */
    public Menu25301Action() {
		this.amortizeProgramMaster = new AmortizeProgramMaster();
		this.amortizeProgramMaster.setCompositeId(new AmortizeProgramMasterPK());
	}
	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		return null;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		return this.process("add");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		return this.process("edit");
	}
	
    /**
     * 
     * @param mode
     * @return
     */
    protected String process(String mode) {
		Map<String, ? extends Object> resultMap;
		Map<String, String> requestMap;
		String reqResult = null;
		
		this.getLogger().info("amortizeProgramMaster: " + this.amortizeProgramMaster);
		requestMap = this.createMapParamFromHTTPRequest();
		requestMap.put("status", ("1".equals(requestMap.get("status"))? "true": "false"));
		requestMap.put("voucher", requestMap.containsKey("voucher")? "true": "false");
		requestMap.put("idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
		requestMap.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		
		if ("edit".equals(mode))
			requestMap.put(C_WEB_SESSION_ID, this.getSessionId());
		
		String result = HttpUtil.request(
							this.getBdsmHost() + (("add".equals(mode))? AMORTIZE_GIFT_PROGRAM_MASTER_INSERT: AMORTIZE_GIFT_PROGRAM_MASTER_UPDATE), 
							requestMap
						);
		
		try {
			resultMap = (java.util.HashMap<String, ? extends Object>) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
			reqResult = ERROR;
		}
		
		if (SUCCESS.equals(reqResult))
			addActionMessage("Data " + ("add".equals(mode)? "Insert": "Updat") + "ed Successfully");
		else
			addActionError("Failed When " + ("add".equals(mode)? "Insert": "Update") + " Data");
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}
	
    /**
     * 
     * @return
     */
    protected Map<String, String> createMapParamFromHTTPRequest() {
		Map<String, String> ret = new java.util.HashMap<String, String>();
		Map<String, String[]> params = this.getServletRequest().getParameterMap();
		Iterator<String> it = params.keySet().iterator();
		
		while (it.hasNext()) {
			String key = it.next();
			ret.put(key, params.get(key)[0]);
		}
		
		return ret;
	}
	
	
    /**
     * 
     * @return
     */
    public AmortizeProgramMaster getModel() {
		return this.amortizeProgramMaster;
	}

    /**
     * 
     * @return
     */
    public String getProductCode_widget() {
		return productCode_widget;
	}
    /**
     * 
     * @param productCode_widget
     */
    public void setProductCode_widget(String productCode_widget) {
		this.productCode_widget = productCode_widget;
	}

}
