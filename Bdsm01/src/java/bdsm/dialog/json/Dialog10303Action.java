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
 * @author user
 */
public class Dialog10303Action extends BaseDialogAction {
    private static final String ACTION = "userComp_list.action";
    private String namUser;

    /**
     * 
     * @return
     * @throws Exception
     */
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        getLogger().debug("namUser : " + namUser);
        
        params.put("model.namUser", namUser);
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION, params);
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String status = (String)ret.get("jsonStatus");
        addActionError(status);
        
        return listRet;
    }

    /**
     * @param namUser the namUser to set
     */
    public void setNamUser(String namUser) {
        this.namUser = namUser;
    }
}
