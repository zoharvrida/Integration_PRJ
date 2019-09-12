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

    public DataPoolingDao(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public List scheDulerXtractReport(Integer idScheduler){
        Query schedReport = this.getSession().getNamedQuery("DataPooling#getReportJp");
        schedReport.setString("pidScheduler", "%" + idScheduler.toString() + "%");
        schedReport.setString("pflagStatus", "A");
        return schedReport.list();
    }

    public List fixqXtractWatcher(Integer idScheduler, String BatchID){
        Query qxtractReport = this.getSession().getNamedQuery("DataPooling#getQxtract");
        qxtractReport.setString("pidScheduler", "%" + idScheduler.toString() + "%");
        qxtractReport.setString("pIdBatch", BatchID);
        qxtractReport.setString("pflagStatus", "A");
        return qxtractReport.list();
    }
}
