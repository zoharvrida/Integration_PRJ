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
 * @author SDM
 */
public class DialogCOMDenomAction extends BaseDialogAction {
    
    private static final String DENOM_LIST = "DenomOps_list";
    
    private String txnId;
    private String namMenu;
    private String txnType;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        
        getLogger().info("MENU :" + namMenu);
        getLogger().info("STATUS :" + this.getTxnType());
        
        HashMap<String, String> params = new HashMap<String, String>();
        
        if(this.txnType != null){
            params.put("txnID", this.txnType);
        }
        if("21506".equalsIgnoreCase(namMenu)){
            params.put("status", "1");
        } else {
            params.put("status", "4");
        }
        params.put("userId", Constant.C_IDUSER);
        params.put("txnType", this.getTxnType());
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        this.getLogger().info("PARAM " + params);

        String result = HttpUtil.request(getBdsmHost() + DENOM_LIST, params);
        this.getLogger().info(result);

        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        List listRet = (List) ret.get("modelList");

        String statusRet = (String) ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }   

    /**
     * @return the namMenu
     */
    public String getNamMenu() {
        return namMenu;
    }

    /**
     * @param namMenu the namMenu to set
     */
    public void setNamMenu(String namMenu) {
        this.namMenu = namMenu;
    }

    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }

    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
}
