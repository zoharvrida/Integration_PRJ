/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu26004;

import bdsm.model.ScreenOpening;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.HttpUtil;
import bdsm.util.SchedulerUtil;
import bdsm.web.BaseContentAction;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class Menu26004Action extends BaseContentAction {

    private static final String CALLMETHOD_ACTION = "Screen_callMethod.action";
    private static final String CASACREATION = "casaCreation";
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Map<String, String> params = new HashMap<String, String>();
    private ScreenOpening so = new ScreenOpening();
    private String cancelling;
    private String jsonStatus;
    private String errorCode;
    private String flagButton;
    private String actionConf;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        getLogger().info("CHECK Flag :" + flagButton);
        getLogger().info("CHECK VALUE :" + cancelling);
        if (this.flagButton.equalsIgnoreCase("1")) {
            return "input";
        } else {
            if ("PROCESS".equalsIgnoreCase(cancelling)) {
                try {
                    process();
                    this.session.put("ACTION", "CREATED");
                    return SUCCESS;
                } catch (Exception e) {
                    getLogger().info("EXCEPTION :" + e, e);
                    this.session.put("ACTION", "FAILED");
                    return "input";
                }
                //dropDown();
            } else if ("CANCEL".equalsIgnoreCase(cancelling)) {
                return "input";
            } else {
                return "input";
            }
        }

    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public ScreenOpening getModel() {
        return this.so;
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected void process() {
        String action = "";
        String cIFnumber = null;
        String accountNumber = null;
        String commentsAcct;

        //this.so.setCdParam("casaDefMap");
        //this.so.setCdParamAcct("casaDefAcctMap");
        getLogger().info("SO CREATE :" + so.toString());
        List spaceName = SchedulerUtil.getParameter(this.so.getFullName(), " ");
        
        getLogger().info("NAME SIZE:" + spaceName.size());
        if (!spaceName.isEmpty()) {
            StringBuilder lastName = new StringBuilder();
            for (int i = 0; i < spaceName.size(); i++) {
                if (i == 0) {
                    this.so.setCustFirstName(spaceName.get(i).toString());
                } else if (i == 1) {
                    if (spaceName.size() == 2) {
                        lastName.append(spaceName.get(i).toString()).append(" ");
                    } else {
                        this.so.setCustMidName(spaceName.get(i).toString());
                    }
                } else {
                    lastName.append(spaceName.get(i).toString()).append(" ");
                }
            }
            this.so.setCustLastName(lastName.toString().trim());
        }

        getLogger().debug("SO BEFORE CREATE:" + this.so.toString());
        Object[] listObj = {this.so};
        String[] nameless = {"so"};
        String[] prefix = {""};

        this.params = (Map<String, String>) ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1);
        if (this.so.getFlagCIF()) {
            this.params.put("so.flagCIF", "true");
        } else {
            this.params.put("so.flagCIF", "false");
        }

        getLogger().info("PARAMS BEFORE TOKEN :" + this.params);
        String Tokenizer = BdsmUtil.generateToken(getTokenKey(), getTzToken());
        getLogger().info("TOKEN : " + Tokenizer);

        this.params.put("token", Tokenizer);
        this.params.put("methodName", CASACREATION);
        getLogger().info("PARAMS :" + this.params);
        try {
            String result = HttpUtil.request(this.getBdsmHost() + CALLMETHOD_ACTION, this.params);

            Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
            Map getSO = (Map) resultMap.get("model");
            cIFnumber = getSO.get("cifNumber").toString();
            try {
                accountNumber = getSO.get("acctNo").toString();
            } catch (Exception e) {
                getLogger().info("EXCEPTION ACCT :" + e, e);
                getSO = (Map) resultMap.get("so");
                accountNumber = getSO.get("acctNo").toString();
            }
            this.jsonStatus = (String) resultMap.get("jsonStatus");
            this.errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException jex) {
            this.getLogger().fatal(jex, jex);
            this.errorCode = "26001.error.data";
        }

        if (SUCCESS.equals(this.jsonStatus)) {
            this.addActionMessage(getText(this.errorCode).replace("{0}", cIFnumber).replace("{1}", accountNumber));
            this.so.setCifNumber(Integer.parseInt(cIFnumber));
            this.so.setAcctNo(accountNumber);
        } else if (ERROR.equals(this.jsonStatus)) {
            this.addActionError(getText(this.errorCode).replace("{0}", so.getApplicationID()));
        }
    }

    /**
     * @return the so
     */
    public ScreenOpening getSo() {
        return so;
    }

    /**
     * @param so the so to set
     */
    public void setSo(ScreenOpening so) {
        this.so = so;
    }

    /**
     * @return the flagButton
     */
    public String getFlagButton() {
        return flagButton;
    }

    /**
     * @param flagButton the flagButton to set
     */
    public void setFlagButton(String flagButton) {
        this.flagButton = flagButton;
    }

    /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * @return the actionConf
     */
    public String getActionConf() {
        return actionConf;
    }

    /**
     * @param actionConf the actionConf to set
     */
    public void setActionConf(String actionConf) {
        this.actionConf = actionConf;
    }

    /**
     * @return the cancelling
     */
    public String getCancelling() {
        return cancelling;
    }

    /**
     * @param cancelling the cancelling to set
     */
    public void setCancelling(String cancelling) {
        this.cancelling = cancelling;
    }
}
