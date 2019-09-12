package bdsm.web.menu20301;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.util.TokenHelper;

/**
 * 
 * @author bdsm
 */
public class Menu20301Action extends BaseContentAction {

    private static final String ACTION_GET_CUSTMAST = "ciCustmast_get.action";
    private static final String ACTION_GET_MFCSLMASTER = "mfcSlMaster_get.action";
    private static final String ACTION_INSERT = "mfcSlMaster_insert.action";
    private static final String NAM_MENU = "Statement Letter Maintenance";
    private Integer noCif;
    private String namCustFull;
    private Integer period;
    private String periodStr;
    private Integer cdBranch;
    private String slCreatorUser;
    private String idMaintainedBy;
    private String idMaintainedSpv;

    /**
     * 
     * @return
     */
    public String exec() {
        return ActionSupport.SUCCESS;
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String inquiryCIF() {
        getLogger().info("[Begin] inquiryCIF()");

        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel;
                String reqResult = null;
                String errorCode = null;

                getLogger().debug("noCif : " + getNoCif());

                map.put("model.compositeId.codCustId", String.valueOf(getNoCif()));
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_GET_CUSTMAST, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    mapModel = (HashMap) resultMap.get("model");
                    if (mapModel != null) {
                        namCustFull = (String) mapModel.get("namCustFull");
                    }

                    String tokenS = TokenHelper.setToken();
                    getLogger().debug("TokenS : " + tokenS);
                    mapModel.put("struts.token.name", tokenS);

                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }

                return "inquiryCIF";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            setErrorMessage("CIF: " + getNoCif() + " not found");
            return ActionSupport.ERROR;
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] inquiryCIF()");
        }
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String inquiry() {
        getLogger().info("[Begin] inquiry()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel;
                String reqResult = null;
                String errorCode = null;

                getLogger().debug("noCif : " + getNoCif());

                map.put("model.compositeId.codCustId", String.valueOf(getNoCif()));
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_GET_CUSTMAST, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    mapModel = (HashMap) resultMap.get("model");
                    if (mapModel != null) {
                        namCustFull = (String) mapModel.get("namCustFull");
                    }
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }

                getLogger().debug("noCif : " + getNoCif());
                getLogger().debug("periodStr : " + getPeriodStr());
                getLogger().debug("period : " + period);

                map = new HashMap<String, String>();
                map.put("model.compositeId.noCif", String.valueOf(getNoCif()));
                map.put("model.compositeId.period", String.valueOf(period));
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_GET_MFCSLMASTER, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    mapModel = (HashMap) resultMap.get("model");
                    if (mapModel != null) {
                        cdBranch = Integer.valueOf(((Long) mapModel.get("cdBranch")).intValue());
                        slCreatorUser = (String) mapModel.get("idCreatedBy");
                    }

                    String tokenS = TokenHelper.setToken();
                    getLogger().debug("TokenS : " + tokenS);
                    mapModel.put("struts.token.name", tokenS);

                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }

                return "inquiry";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            setErrorMessage("SL for CIF: '" + getNoCif() + "' and Period: '" +  getPeriodStr() + "' not found");
            return ActionSupport.ERROR;
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] inquiry()");
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        getLogger().debug("noCif : " + getNoCif());
        getLogger().debug("period : " + period);
        getLogger().debug("cdBranch : " + getCdBranch());
        getLogger().debug("slCreatorUser : " + getSlCreatorUser());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());

        map.put("model.compositeId.noCif", String.valueOf(getNoCif()));
        map.put("model.compositeId.period", String.valueOf(getPeriod()));
        map.put("model.cdBranch", String.valueOf(getCdBranch()));
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + ACTION_INSERT, map);

        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }

        if (reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if (reqResult.equals(ActionSupport.SUCCESS)) {
            addActionMessage(getText(errorCode));
        }

        return ActionSupport.SUCCESS;
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
     * @return the noCif
     */
    public Integer getNoCif() {
        return noCif;
    }

    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(Integer noCif) {
        this.noCif = noCif;
    }

    /**
     * @return the namCustFull
     */
    public String getNamCustFull() {
        return namCustFull;
    }

    /**
     * @param namCustFull the namCustFull to set
     */
    public void setNamCustFull(String namCustFull) {
        this.namCustFull = namCustFull;
    }

    /**
     * @return the period
     */
    private Integer getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    private void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * @return the period string
     */
    public String getPeriodStr() {
        String s = String.format("%02d/%04d", period % 100, period / 100);
        return s;
    }

    /**
     * @param periodStr 
     */
    public void setPeriodStr(String periodStr) {
        String[] s = periodStr.split("/");
        if (s.length < 2) {
            this.period = new Integer(0);
        }
        this.period = new Integer(Integer.parseInt(s[1]) * 100 + Integer.parseInt(s[0]));
    }

    /**
     * @return the cdBranch
     */
    public Integer getCdBranch() {
        return cdBranch;
    }

    /**
     * @param cdBranch the cdBranch to set
     */
    public void setCdBranch(Integer cdBranch) {
        this.cdBranch = cdBranch;
    }

    /**
     * @return the slCreatorUser
     */
    public String getSlCreatorUser() {
        return slCreatorUser;
    }

    /**
     * @param slCreatorUser the slCreatorUser to set
     */
    public void setSlCreatorUser(String slCreatorUser) {
        this.slCreatorUser = slCreatorUser;
    }

    /**
     * @return the idMaintainedBy
     */
    public String getIdMaintainedBy() {
        return idMaintainedBy;
    }

    /**
     * @param idMaintainedBy the idMaintainedBy to set
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
        this.idMaintainedBy = idMaintainedBy;
    }

    /**
     * @return the idMaintainedSpv
     */
    public String getIdMaintainedSpv() {
        return idMaintainedSpv;
    }

    /**
     * @param idMaintainedSpv the idMaintainedSpv to set
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
        this.idMaintainedSpv = idMaintainedSpv;
    }
}
