
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
public class Dialog10304DAction extends BaseDialogAction {
    private static final String ACTION_LIST = "fixMasterReportDelete_list.action";
    
    private String reportName;
    private String idReport;
    private String idTemplate;
    private String idMasterReport;
    private String namReportSearch;

    /**
     * 
     * @return
     * @throws Exception
     */
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();

        getLogger().info("NAM Report :" + reportName);
        getLogger().info("NAM SEARCH :" + namReportSearch);
        getLogger().info("Id Template :" + idTemplate);
        params.put("model.idTemplate", getIdTemplate());
        params.put("model.reportName", getReportName());
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION_LIST, params);  
        
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
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @return the idMasterReport
     */
    public String getIdMasterReport() {
        return idMasterReport;
    }

    /**
     * @param idMasterReport the idMasterReport to set
     */
    public void setIdMasterReport(String idMasterReport) {
        this.idMasterReport = idMasterReport;
    }

    /**
     * @return the namReportSearch
     */
    public String getNamReportSearch() {
        return namReportSearch;
    }

    /**
     * @param namReportSearch the namReportSearch to set
     */
    public void setNamReportSearch(String namReportSearch) {
        this.namReportSearch = namReportSearch;
    }

}
