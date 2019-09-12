/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.EktpSysuser;
import bdsm.model.EktpUser;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class EktpSysuserDao extends BaseDao {

    private Logger logger = Logger.getLogger(EktpUserDao.class);
    public EktpSysuserDao(Session session) {
        super(session);
    }
    public EktpSysuser get(String ktpUser) {
        return (EktpSysuser) getSession().get(EktpSysuser.class, ktpUser);
    }
    public List getMaxRank(){
        Query ektpQuota = getSession().getNamedQuery("ektp#getMAXRank");
        logger.info("QUERY :" + ektpQuota.getQueryString());
        ektpQuota.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List q = ektpQuota.list();
        logger.info("COUNT LIST :" + q.size());
        return q;
    }
    public List getSpecificRank(Integer rank){
        Query ektpQuota = getSession().getNamedQuery("ektp#getUserRank");
        logger.info("QUERY :" + ektpQuota.getQueryString());
        //ektpQuota = getSession().createSQLQuery(ektpQuota.getQueryString());
        //((org.hibernate.SQLQuery) ektpQuota).addEntity(EktpSysuser.class);
        ektpQuota.setParameter("pRanks", rank);
        ektpQuota.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List q = ektpQuota.list();
        logger.info("COUNT LIST :" + q.size());
        return q;
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
