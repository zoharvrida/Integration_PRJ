package bdsmhost.web;

import bdsm.model.FcrBaBankMast;
import bdsmhost.dao.FcrBaBankMastDao;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * @author 00030663
 */
public class BusinessDateAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;

    private List<Date> modelList;
    private Date model;
    private Date lastDate;
    private Date firstDate;
    private String flag;

    /**
     * 
     * @return
     */
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Input:
     * HTTP Request parameters: none
     * 
     * Output:
     * JSON Object: { "model": {...}, "modelList": {...} }
     * @return 
     */ 
    public String doGet() {
        FcrBaBankMastDao dao = new FcrBaBankMastDao(getHSession());
        FcrBaBankMast obj = dao.get();
        if (obj==null) model = null;
        else
            model = obj.getDatProcess();
        return SUCCESS;
    }
    /**
     * 
     * @return
     */
    @JSON(serialize=false)
    public String getMonthRange(){
        FcrBaBankMastDao dao = new FcrBaBankMastDao(getHSession());
        SimpleDateFormat dateFBack = new SimpleDateFormat("dd/MM/yyyy");
        try{
        synchronized (dateFBack) {
            String dateToConv = dateFBack.format(dao.getDayMonth(flag));
            getLogger().debug("DATE CONV :" + dateToConv);
        if("FIRST".equalsIgnoreCase(flag)){
                setFirstDate(dateFBack.parse(dateToConv));
            getLogger().debug("FIRST :" + firstDate);
        } else if("LAST".equalsIgnoreCase(flag)) {
                setLastDate(dateFBack.parse(dateToConv));
            getLogger().debug("LAST :" + lastDate);
            }
        }
        } catch (Exception e){
          getLogger().debug("EXCEPTION :" + e,e);
        }
        return SUCCESS;
    }
    /**
     * 
     * @return
     */
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	
    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
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
     * @return the modelList
     */
    public List<Date> getModelList() {
        return modelList;
    }

    /**
     * @param model the model to set
     */
    public void setModel(Date model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public Date getModel() {
        return model;
    }
    /**
     * 
     * @return
     */
    public String getFlag() {
        return flag;
    }
    /**
     * 
     * @param flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    /**
     * 
     * @return
     */
    public Date getLastDate() {
        return lastDate;
    }
    /**
     * 
     * @param lastDate
     */
    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
    /**
     * 
     * @return
     */
    public Date getFirstDate() {
        return firstDate;
    }
    /**
     * 
     * @param firstDate
     */
    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }
}
