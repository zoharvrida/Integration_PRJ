/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu20201;

import bdsm.dialog.json.BaseDialogAction;
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
public class Tab3GridAction extends BaseDialogAction {
    private static final String ACTION = "FcyTxnSummLimForward_list.action";
    private String flgAcct;
    private String noAccount;
    private Integer period;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();

        getLogger().debug("flgAcct : " + flgAcct);
        getLogger().debug("noAcct : " + noAccount);
        getLogger().debug("period : " + period);
        
        params.put("model.flgAcct", flgAcct);
        params.put("model.noAcct", noAccount);
        params.put("model.period", String.valueOf(period));
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION, params);
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String statusRet = (String)ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }
    
    /**
     * @param flgAcct the flgAcct to set
     */
    public void setFlgAcct(String flgAcct) {
        this.flgAcct = flgAcct;
    }    
    
    /**
     * @param noAccount the noAccount to set
     */
    public void setNoAccount(String noAccount) {
        this.noAccount = noAccount;
    }    
    
    /**
     * @return the period
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }
}
