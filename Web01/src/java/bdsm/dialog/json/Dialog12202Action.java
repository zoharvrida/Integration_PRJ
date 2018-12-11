/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00019722
 */
public class Dialog12202Action extends BaseDialogAction {
    private static final String ACTION_LIST = "fixReportDownload_list.action";
    //private static final String ACTION_CLEAN = "fixReportDownload_save.action";
    
    private String user;
	private String flag;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        String result;
        String result2;
        getLogger().debug("user Request : " + getUser());
		getLogger().debug("flag : " + this.getFlag());
        
		params.put("flag", this.getFlag());
        params.put("model.idRequest", getUser());
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + ACTION_LIST, params);

        HashMap ret = (HashMap) JSONUtil.deserialize(result);
        List listRet = (List) ret.get("modelList");
        String statusRet = (String) ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
	 * @return the flag
     */
	public String getFlag() {
		return flag;
    }

    /**
	 * @param flag the flag to set
     */
	public void setFlag(String flag) {
		this.flag = flag;
    }
    
}
