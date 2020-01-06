/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MasterService;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00024535
 */
public class MasterServiceDAO extends BaseDao {

    private Logger logger = Logger.getLogger(MasterServiceDAO.class);

    public MasterServiceDAO(Session session) {
        super(session);
    }

    public MasterService getService(Integer idScheduler, String type) {
        logger.info("get ID Scheduler = " + idScheduler);
        logger.info("get Type Scheduler = " + type);
        MasterService service = new MasterService();
        Criteria criteriaQuery = getSession().createCriteria(MasterService.class);
        criteriaQuery.add(Restrictions.eq("idScheduler", idScheduler));
        criteriaQuery.add(Restrictions.eq("type", type));
        List<MasterService> m = criteriaQuery.list();
        try {
           service = m.get(0);         
        } catch (Exception e) {
           logger.info("Log ERROR - getService " + e);
        }
        return service;
        
        //return (MasterService) criteriaQuery.uniqueResult();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
