/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.ExtMfcDetails;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00013493
 */
public class ExtMfcDetailsDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryRun = "{call PK_TROPS.run(?)}";
    private int workResult = 0;
    
    /**
     * 
     * @param session
     */
    public ExtMfcDetailsDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param batchId
     * @return
     */
    public List getDetails(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(ExtMfcDetails.class);
        criteriaQuery.add(Restrictions.eq("batchId", batchId));
        criteriaQuery.addOrder(Order.asc("recordId"));
        return criteriaQuery.list();
    }
    
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((ExtMfcDetails)model);
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
     * @param cnctn
     */
    @Override
    public void execute(Connection cnctn) {
        try {
            CallableStatement stmt = cnctn.prepareCall(queryRun);
            stmt.setString(1, parameter);
            workResult = stmt.executeUpdate();
            stmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param param
     * @return
     */
    public int runPackage(String param) {
        Session session = getSession();
        parameter = param;
        session.doWork(this);
        return workResult;
    }
}
