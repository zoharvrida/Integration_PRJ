/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.web.Menu10304;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.rpt.web.BaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00019722
 */
public class Menu10304Action extends BaseContentAction {
    private static final String ACTION_ADD = "xrefReportTemplate_insert.action";
    private static final String ACTION_DELETE = "xrefReportTemplate_delete.action";
    private static final String NAM_MENU = "Report Registration";
    
    private String reportName;
    private String idTemplate;
    private String idScheduler;
    private String idReport;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    //@SkipValidation
    public String doAdd() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        map.put("model.compositeId.idMasterReport", getIdReport());
        map.put("model.compositeId.idTemplate", getIdTemplate());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        

        result = HttpUtil.request(getBdsmHost() + ACTION_ADD, map);
            
        try {
            resultMap = (HashMap)JSONUtil.deserialize(result);
            reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
       } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
            
        if(reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if(reqResult.equals(ActionSupport.SUCCESS)) {
            addActionMessage(getText(errorCode));
        }   
        return ActionSupport.SUCCESS;
    }
    @Override
    public String doEdit()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public String doDelete() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        map.put("model.compositeId.idMasterReport", getIdReport());
        map.put("model.compositeId.idTemplate", getIdTemplate());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + ACTION_DELETE, map);
        
        try {
            resultMap = (HashMap)JSONUtil.deserialize(result);
            reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
    }
    
        if(reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if(reqResult.equals(ActionSupport.SUCCESS)) {
            addActionMessage(getText(errorCode));
        }     
        return ActionSupport.SUCCESS;
    }
    /**
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }
    
    /**
     * @return the idReport
     */
    public String getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idReport the idReport to set
     */
    public void setIdScheduler(String idScheduler) {
        this.idScheduler = idScheduler;
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

    /**
     * @return the idReport
     */
    public String getIdReport() {
        return idReport;
    }

    /**
     * @param idReport the idReport to set
     */
    public void setIdReport(String idReport) {
        this.idReport = idReport;
    }
    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
