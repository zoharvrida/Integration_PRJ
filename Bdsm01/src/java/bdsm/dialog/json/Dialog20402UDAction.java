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
public class Dialog20402UDAction extends BaseDialogAction {
    private static final String ACTION = "fcyTxnManInputUD_list.action";
    private Integer noCif;
    private String noUd;
    private String ccyUd;
    private java.math.BigDecimal amtTxn;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        getLogger().debug("noCif : " + noCif);
        getLogger().debug("noUd : " + noUd);
        getLogger().debug("ccyUd : " + ccyUd);
        getLogger().debug("amtTxn : " + amtTxn);
        
        params.put("model.noCif", String.valueOf(noCif));
        params.put("model.noUd", noUd);
        params.put("model.ccyUd", ccyUd);
        params.put("model.amtTxn", String.valueOf(amtTxn));
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION, params);
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String statusRet = (String)ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }
    
    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(Integer noCif) {
        this.noCif = noCif;
    }
    
    /**
     * @param noUd the noUd to set
     */
    public void setNoUd(String noUd) {
        this.noUd = noUd;
    }
    
    /**
     * @param ccyUd the ccyUd to set
     */
    public void setCcyUd(String ccyUd) {
        this.ccyUd = ccyUd;
    }
    
    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(java.math.BigDecimal amtTxn) {
        this.amtTxn = amtTxn;
    }
}
