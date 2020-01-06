/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00024535
 */
public class DetailServiceDAO extends BaseDao {

    private Logger logger = Logger.getLogger(DetailServiceDAO.class);

    public DetailServiceDAO(Session session) {
        super(session);
    }

    public List getDetails(Integer idScheduler, String type, String serviceName) {
        Query query = null;
        try {
            query = this.getSession().getNamedQuery("DetailService#getData");
            query.setInteger("idScheduler", idScheduler);
            query.setString("type", type);
            query.setString("serviceName", serviceName);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        } catch (Exception e) {
            logger.info("LOG ERROR - getDetails ", e);
        }
        return query.list();
    }
    
    public List getBuildData(Integer idScheduler, String type, String serviceName) {
        Query query = null;
        try {
            query = this.getSession().getNamedQuery("DetailService#getBuildData");
            query.setInteger("idScheduler", idScheduler);
            query.setString("type", type);
            query.setString("serviceName", serviceName);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        } catch (Exception e) {
            logger.info("LOG ERROR - getBuildData ", e);
        }
        return query.list();
    }
    
    public List getChild(Integer idScheduler, String type, String serviceName, String parent) {
        Query query = null;
        try {
            query = this.getSession().getNamedQuery("DetailService#getChild");
            query.setInteger("idScheduler", idScheduler);
            query.setString("type", type);
            query.setString("serviceName", serviceName);
            query.setString("parent", parent);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        } catch (Exception e) {
            logger.info("LOG ERROR - getChild ", e);
        }
        return query.list();
    }

    public String getQuery(String fieldName, String tableName, String condition) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT  ");
        queryString.append(fieldName);
        queryString.append(" FROM ");
        queryString.append(tableName+" ");
        queryString.append(condition);

        Query q = getSession().createSQLQuery(queryString.toString());
        logger.info("Cek Query  = " + q.toString());
        return (String) q.uniqueResult();
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
