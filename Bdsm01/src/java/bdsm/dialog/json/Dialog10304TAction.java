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
public class Dialog10304TAction extends BaseDialogAction{
    private static final String ACTION_LIST = "fixMasterTemplateDelete_list.action";
    
    private String title;
    private String namTemplate;
    private String idTemplate;


    /**
     * 
     * @return
     * @throws Exception
     */
    public List doList() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        
        params.put("model.namTemplate",getNamTemplate());
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        String result = HttpUtil.request(getBdsmHost() + ACTION_LIST, params); 

        
        HashMap ret = (HashMap)JSONUtil.deserialize(result);

        List listRet = (List)ret.get("modelRep");
        String status = (String)ret.get("jsonStatus");

        addActionError(status);

        return listRet;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the namTemplate
     */
    public String getNamTemplate() {
        return namTemplate;
    }

    /**
     * @param namTemplate the namTemplate to set
     */
    public void setNamTemplate(String namTemplate) {
        this.namTemplate = namTemplate;
    }
  
}
