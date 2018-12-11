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
public class Dialog20401CIFAction extends BaseDialogAction {
    private static final String ACTION = "fcyTxnFlagCIF_list.action";
    private String acctNo;
    private Integer period;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        getLogger().debug("noAcct : " + acctNo);
        getLogger().debug("period : " + period);
        getLogger().debug("idMenu : " + this.session.get(Constant.C_IDMENU));
        
        params.put("model.acctNo", acctNo);
        params.put("model.period", String.valueOf(period));
        params.put("idMenu", this.session.get(Constant.C_IDMENU).toString());
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION, params);
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String statusRet = (String)ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }
    
    /**
     * @param acctNo the acctNo to set
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }
    
    /**
     * @param period the period to set
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }
}
