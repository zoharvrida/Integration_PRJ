/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class DataPoolingDao extends BaseDao {

    /**
     * 
     * @param session
     */
    public DataPoolingDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * 
     * @param idScheduler
     * @return
     */
    public List scheDulerXtractReport(Integer idScheduler){
        Query schedReport = this.getSession().getNamedQuery("DataPooling#getReportJp");
        schedReport.setString("pidScheduler", "%" + idScheduler.toString() + "%");
        schedReport.setString("pflagStatus", "A");
        return schedReport.list();
    }

    /**
     * 
     * @param idScheduler
     * @param BatchID
     * @return
     */
    public List fixqXtractWatcher(Integer idScheduler, String BatchID){
        Query qxtractReport = this.getSession().getNamedQuery("DataPooling#getQxtract");
        qxtractReport.setString("pidScheduler", "%" + idScheduler.toString() + "%");
        qxtractReport.setString("pIdBatch", BatchID);
        qxtractReport.setString("pflagStatus", "A");
        return qxtractReport.list();
    }
}
