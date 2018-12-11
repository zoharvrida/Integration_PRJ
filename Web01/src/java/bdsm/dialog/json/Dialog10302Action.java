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
public class Dialog10302Action extends BaseDialogAction {
    private static final String ACTION = "menu_list.action";
    private String namMenu;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        getLogger().debug("namMenu : " + namMenu);
        
        params.put("model.namMenu", namMenu);
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION, params);
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String status = (String)ret.get("jsonStatus");
        addActionError(status);

        return listRet;
    }
    
    /**
     * @param namMenu the namTemplate to set
     */
    public void setNamMenu(String namMenu) {
        this.namMenu = namMenu;
    }
}
