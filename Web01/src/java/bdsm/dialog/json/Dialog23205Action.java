package bdsm.dialog.json;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.JSONUtil;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.Constant;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class Dialog23205Action extends BaseDialogAction {
	private static final String MODULE_NAME = "SKNNGINCDB";
        private static final String NAMMENU = "23205";
	private static final String SKN_NG_RETRIEVE_BATCHNO = "SknNgInwardDebit_get.action";
	private static final String SKN_NG_RETRIEVE_INCOMING = "SknNgInwardDebit_list.action";
	private static final List<String> modeList = java.util.Arrays.asList(new String[] {"1", "2"});
	private String action;
	private String codeNo;
	private String mode;

	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List doList() throws Exception {
		Date bdt = (Date) this.session.get(Constant.C_DATEBUSINESS);
		String batchNo = BdsmUtil.leftPad(this.session.get(Constant.C_CODEBRANCH).toString(), 5, '0');
		String HTTPAction = "";
		HashMap<String, String> params = new HashMap<String, String>();
		List<? extends Object> listRet = new ArrayList<Object>();
		String status = SUCCESS;
		
		this.getLogger().debug("action: " + this.action);
		this.getLogger().debug("mode: " + this.mode);
		
		if (!(StringUtils.isBlank(this.action)) && !(StringUtils.isBlank(this.mode))) {
			if (this.action.equalsIgnoreCase("select")) {
				HTTPAction = SKN_NG_RETRIEVE_BATCHNO;
				params.put("cdBranch", batchNo);
			}
			else if (this.action.equalsIgnoreCase("grid")) {
				batchNo += "-" + DateUtility.DATE_FORMAT_YYYYMMDD.format(bdt) + "-" + this.codeNo + "-" + MODULE_NAME;
				this.getLogger().debug("codeNo: " + this.codeNo);
				
				HTTPAction = SKN_NG_RETRIEVE_INCOMING;
				params.put("modelPk.batchNo", batchNo);
			}
			
			this.getLogger().debug("batchNo: " + batchNo);
			
                        params.put("nameMenu",NAMMENU);
			params.put("mode", this.getMode());
			params.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
	
			String result = HttpUtil.request(this.getBdsmHost() + HTTPAction, params);
			this.getLogger().debug(result);
	
			HashMap ret = (HashMap) JSONUtil.deserialize(result);
			listRet = (List<? extends Object>) ret.get("modelList");
			status = (String) ret.get("jsonStatus");
			
		}
		addActionError(status);
		
		return listRet;
	}
	
	
    /**
     * 
     * @return
     */
    public List<String> getModeList() {
		return modeList;
	}
	
    /**
     * 
     * @return
     */
    public String getAction() {
		return this.action;
	}
    /**
     * 
     * @param action
     */
    public void setAction(String action) {
		this.action = action;
	}
	
    /**
     * 
     * @return
     */
    public String getCodeNo() {
		return this.codeNo;
	}
    /**
     * 
     * @param codeNo
     */
    public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

    /**
     * 
     * @return
     */
    public String getMode() {
		return this.mode;
	}
    /**
     * 
     * @param mode
     */
    public void setMode(String mode) {
		this.mode = mode;
	}
	
}
