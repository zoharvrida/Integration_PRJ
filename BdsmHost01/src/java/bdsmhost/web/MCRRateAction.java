package bdsmhost.web;

import bdsm.model.MCRCurrencyRate;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.MCRCurrencyRateDAO;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author bdsm
 */
public class MCRRateAction extends BaseHostAction {
    private MCRCurrencyRate model;
    private List<Map> modelList;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        
        getLogger().info("[EXEC]currencyCode = " + model.getCurrencyCode());
        getLogger().info("[EXEC]valueDate = " + model.getValueDate());
       return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {

        if (StringUtils.isBlank(model.getCurrencyCode())){
            model.setCurrencyCode(null);
        };
        
        MCRCurrencyRateDAO dao = new MCRCurrencyRateDAO(getHSession());
        setModelList(dao.listByCurrencyCodeAndDate(model.getCurrencyCode(), model.getValueDate()));
        
        getLogger().info("modelList = " + getModelList());
        
        List<Map> l = modelList;
        l.size();
        
        for (int i = 0 ; i < l.size() ; i++){
            Map maps = l.get(i);
            Date date1 =  (Date) maps.get("valueDate");
            Date date2 =  (Date) maps.get("dtmLog");
            
            StringBuilder sb = new StringBuilder(DateUtility.DATE_FORMAT_DDMMMYYYY.format(date1));
            sb.append(" ").append(DateUtility.TIME_FORMAT_COLON.format((date2)));
            try {
                date1 = DateUtility.DATE_TIME_FORMAT_DDMMMYYYY.parse(sb.toString());
            } catch (ParseException ex) {
                Logger.getLogger(MCRRateAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            maps.put("valueDate", date1);
        }
    
        getLogger().info("currencyCode = " + model.getCurrencyCode());
        getLogger().info("valueDate = " + model.getValueDate());

        getLogger().info("modelList = " + getModelList());
        
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
       
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
       
        return SUCCESS;
    }


    /**
     * @return the model
     */
    public MCRCurrencyRate getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MCRCurrencyRate model) {
        this.model = model;
    }

    /**
     * @return the modelList
     */
    public List<Map> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<Map> modelList) {
        this.modelList = modelList;
    }

    
}
