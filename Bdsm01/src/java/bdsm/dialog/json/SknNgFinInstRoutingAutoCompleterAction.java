package bdsm.dialog.json;


import java.util.List;
import java.util.Map;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgFinInstRoutingAutoCompleterAction extends BaseDialogAction {
	private String BIC;
	private String mode = "1"; // 1=Sector or Region; 2=City
	private String term; // for autocompleter
	
	
    /**
     * 
     */
    public SknNgFinInstRoutingAutoCompleterAction() {
		this.setRows(5);
		this.setPage(1);
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	@SuppressWarnings("unchecked")
	public java.util.List<? extends Object> doList() throws Exception {
		java.util.Map<String, String> params = new java.util.HashMap<String, String>();
		
		if (org.apache.commons.lang.StringUtils.isBlank(this.BIC))
			this.BIC = "";
		
		if (org.apache.commons.lang.StringUtils.isBlank(this.mode))
			this.mode = "1";
		
		params.put("BIC", this.BIC);
		params.put("mode", this.mode);
		params.put("term", this.term);
		params.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		
		String result = bdsm.util.HttpUtil.request(this.getBdsmHost() + "SknNgFinInstRouting_list", params);
		
		Map<String, ? extends Object> resultMap = (Map<String, ? extends Object>) org.apache.struts2.json.JSONUtil.deserialize(result);
		List<Map<String, String>> routingList = (List<Map<String, String>>) resultMap.get("routingList");
		
		return routingList;
	}
	
	
    /**
     * 
     * @param BIC
     */
    public void setBIC(String BIC) {
		this.BIC = BIC;
	}
	
    /**
     * 
     * @param mode
     */
    public void setMode(String mode) {
		this.mode = mode;
	}
	
    /**
     * 
     * @param term
     */
    public void setTerm(String term) {
		this.term = term;
	}
	
}