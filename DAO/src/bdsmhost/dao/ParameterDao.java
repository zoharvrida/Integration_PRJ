/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.Parameter;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class ParameterDao extends BaseDao {
    
    private Logger logger = Logger.getLogger(ParameterDao.class);
    
    public ParameterDao(Session session) {
        super(session);
    }

    public Parameter get(String cdParam) {
        logger.info("get Cd Param = " + cdParam);
        return (Parameter)getSession().get(Parameter.class, cdParam);
    }
    
    public List<Parameter> getList(){
        Criteria criteria = getSession().createCriteria(Parameter.class);
        return criteria.list();
    }    

    public List<Parameter> getAllParameter(){        
        return getSession().createQuery("from Parameter").list();
    }
    
    public List getFieldParameter(Integer idScheduler, String typfix, Integer number) {
        List newlist = null;
        try {
            Query q = this.getSession().getNamedQuery("FieldParameter#getRealParam");
            q.setInteger("pIdScheduler", idScheduler);
            q.setString("pTypfix",typfix);
            q.setInteger("pField",number);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return newlist;
        }
    }

    public List getFieldParameterbyModuleName(Integer idScheduler, String typfix, Integer number, String namModule) {
        List newlist = null;
        try {
            Query q = this.getSession().getNamedQuery("FieldParameter#getParambyModul");
            q.setString("pModule", namModule);
            q.setInteger("pIdScheduler", idScheduler);
            q.setString("pTypfix",typfix);
            q.setInteger("pField",number);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return newlist;
        }
    }

    public List getFieldParameterbyTemplate(Integer idScheduler, Integer order, Integer number, String namModule) {
        List newlist = null;
        try {
            Query q = this.getSession().getNamedQuery("FieldParameter#getParambyTemplate");
            q.setString("pModule", namModule);
            q.setInteger("pIdScheduler", idScheduler);
            q.setInteger("pOrder", order);
            q.setInteger("pField", number);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return newlist;
        }
    }

    public List getValueFromTable(String query) {
        List retResult = null;
        StringBuilder qryStr = new StringBuilder();
        try {
            qryStr.append(query);
            Query q = getSession().createSQLQuery(qryStr.toString());
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            retResult = q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
        }
        return retResult;
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((Parameter)model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}
