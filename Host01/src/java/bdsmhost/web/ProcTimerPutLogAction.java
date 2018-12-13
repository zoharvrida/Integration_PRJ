/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;
import bdsm.model.ProTimePutLog;
import bdsmhost.dao.ProTimePutLogActionDao;

/**
 *
 * @author v00022309
 */
public class ProcTimerPutLogAction extends BaseHostAction{
    private ProTimePutLog model;

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
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        getLogger().info("insert model :" + getModel());
        getLogger().info("lihat hses :" + getHSession());               
        ProTimePutLogActionDao dao = new ProTimePutLogActionDao(getHSession());
        dao.runPROCTIME(getModel().getSessionid(), getModel().getUserid(),getModel().getCdmenu(), getModel().getStepname(), getModel().getErrmsg());
        return ProcTimerPutLogAction.SUCCESS;
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
     * @return the model
     */
    public ProTimePutLog getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(ProTimePutLog model) {
        this.model = model;
    }
    
}
