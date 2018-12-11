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
 * @author v00013493
 */
public class Dialog20402Action extends BaseDialogAction {
    private static final String ACTION = "fcyTxnManInputList_list.action";
    private String noAcct;
    private String typMsg;
    private String typAcct;
    private String refTxn;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        getLogger().debug("noAcct : " + noAcct);
        getLogger().debug("typMsg : " + typMsg);
        getLogger().debug("typAcct : " + typAcct);
        getLogger().debug("refTxn : " + refTxn);
        
        params.put("model.compositeId.noAcct", noAcct);
        params.put("model.compositeId.typMsg", typMsg);
        params.put("model.typAcct", typAcct);
        params.put("model.compositeId.refTxn", refTxn);
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
     * @param typMsg the typMsg to set
     */
    public void setTypMsg(String typMsg) {
        this.typMsg = typMsg;
    }
    
    /**
     * @param typAcct the typAcct to set
     */
    public void setTypAcct(String typAcct) {
        this.typAcct = typAcct;
    }
    
    /**
     * @param refTxn the refTxn to set
     */
    public void setRefTxn(String refTxn) {
        this.refTxn = refTxn;
    }
}
