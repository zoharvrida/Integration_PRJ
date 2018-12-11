/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.Constant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00013493
 */
public class Dialog20401Action extends BaseDialogAction {
    private static final String ACTION = "mfcNoBook_list.action";
    private String noAcct;
    private String dtPost;
    private String typMsg;
    private String status;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        getLogger().debug("noAcct : " + noAcct);
        getLogger().debug("dtPost : " + dtPost);
        getLogger().debug("typMsg : " + typMsg);
        getLogger().debug("status : " + status);
        getLogger().debug("idmenu : " + session.get(Constant.C_IDMENU));


        params.put("model.compositeId.noAcct", noAcct);
        params.put("model.strDtPost", dtPost);
        params.put("model.compositeId.typMsg", typMsg);
        params.put("model.status", status);
        params.put("idMenu", session.get(Constant.C_IDMENU).toString());
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION, params);
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String statusRet = (String)ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }    
    
    /**
     * @param noAcct the noAcct to set
     */
    public void setNoAcct(String noAcct) {
        this.noAcct = noAcct;
    }
    
    /**
     * @param dtPost the dtPost to set
     */
    public void setDtPost(String dtPost) {
        this.dtPost = dtPost;
    }
    
    /**
     * @param typMsg the typMsg to set
     */
    public void setTypMsg(String typMsg) {
        this.typMsg = typMsg;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the idMenu
     */

    /**
     * @param idMenu the idMenu to set
     */

   
}
