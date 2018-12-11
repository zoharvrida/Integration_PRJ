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
 * @author v00019722
 */
public class Dialog10304Action extends BaseDialogAction {
    private static final String ACTION_LIST = "fixMasterReport_list.action";
        
    private String reportName;

    private String idTemplate;
    private String reportId;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        
        getLogger().info("test Id Template " +getIdTemplate());
        getLogger().info("test report " + reportName);
        
        params.put("modelT.compositeId.idTemplate", getIdTemplate());
        params.put("model.reportName", reportName);
        params.put("model.idReport", reportId);
        
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION_LIST, params);
        //getLogger().debug("idReport : " + reportId);
        //getLogger().debug("reportName : " + reportName);
        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);
        List listRet = (List)ret.get("modelList");
        String status = (String)ret.get("jsonStatus");
        addActionError(status);

        return listRet;
    }
        /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }
}

    
