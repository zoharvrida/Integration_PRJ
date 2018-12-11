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
public class Dialog13301IAction extends BaseDialogAction{
    private static final String ACTION_LIST = "fixSchedulerImport_list.action";

    private String typeS;
    private String namschedProfile;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        
        getLogger().info("nama scheduler :" + getNamschedProfile());
        params.put("model.fixSchedulerPK.namScheduler", getNamschedProfile());

        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        String result = HttpUtil.request(getBdsmHost() + ACTION_LIST, params);  
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String status = (String)ret.get("jsonStatus");

        addActionError(status);

        return listRet;
    }

    /**
     * @return the typeS
     */
    public String getTypeS() {
        return typeS;
    }

    /**
     * @param typeS the typeS to set
     */
    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    /**
     * @return the namschedProfile
     */
    public String getNamschedProfile() {
        return namschedProfile;
    }

    /**
     * @param namschedProfile the namschedProfile to set
     */
    public void setNamschedProfile(String namschedProfile) {
        this.namschedProfile = namschedProfile;
    }
}
