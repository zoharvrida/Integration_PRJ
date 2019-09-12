/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.Constant;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00013493
 */
public class Dialog20401UDAction extends BaseDialogAction {
    private static final String ACTION = "fcyTxnFlagUD_list.action";
    private Integer noCif;
    private String ccyUd;
    private String noUd;
    private BigDecimal amtTxn;
    private String idMenu;
    private String noAcct;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        getLogger().debug("noCif : " + noCif);
        getLogger().debug("ccyUd : " + ccyUd);
        getLogger().debug("noUd : " + noUd);
        getLogger().debug("amtTxn : " + amtTxn);
        getLogger().debug("idMenu : " + session.get(Constant.C_IDMENU));
        getLogger().debug("noAcct : " + noAcct);

        
        try {
            if(noCif == null)noCif = 0; 
            params.put("model.noCif", String.valueOf(noCif));
        } catch (Exception e) {
            getLogger().debug("nocif :" + e,e);
            params.put("model.noCif", "");
        }
        params.put("model.ccyUd", ccyUd);
        params.put("model.noUd", noUd);
        params.put("model.amtTxn", String.valueOf(amtTxn));
        params.put("idMenu", session.get(Constant.C_IDMENU).toString());
        params.put("noAcct", noAcct);

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
     * @param ccyUd the ccyUd to set
     */
    public void setCcyUd(String ccyUd) {
        this.ccyUd = ccyUd;
    }
    
    /**
     * @param noUd the noUd to set
     */
    public void setNoUd(String noUd) {
        this.noUd = noUd;
    }
    
    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(BigDecimal amtTxn) {
        this.amtTxn = amtTxn;
    }

    /**
     * @return the idMenu
     */
    public String getIdMenu() {
        return idMenu;
    }

    /**
     * @param idMenu the idMenu to set
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * @return the noAcct
     */
    public String getNoAcct() {
        return noAcct;
    }

    /**
     * @param noAcct the noAcct to set
     */
    public void setNoAcct(String noAcct) {
        this.noAcct = noAcct;
    }
}
