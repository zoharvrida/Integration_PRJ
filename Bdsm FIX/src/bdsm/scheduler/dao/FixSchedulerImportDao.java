package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixSchedulerPK;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * @author bdsm
 */
public class FixSchedulerImportDao extends BaseDao {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 
     * @param session
     */
    public FixSchedulerImportDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param filePattern
     * @return
     */
    public FixSchedulerImport get(String filePattern) {
        Query query = getSession().getNamedQuery("fixSchedulerImport#getByFlagStatusAndFilePattern");
        query.setString("flagStatus", null);
        query.setString("pattern", filePattern + "%");
        List<FixSchedulerImport> list = query.list();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 
     * @param idScheduler
     * @return
     */
    public FixSchedulerImport get(Integer idScheduler) {
        Query query = getSession().getNamedQuery("fixSchedulerImport#getByIdScheduler");
        query.setString("flagStatus", null);
        query.setInteger("pidScheduler", idScheduler);
        List<FixSchedulerImport> list = query.list();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }
    /**
     * 
     * @return
     */
    public List list() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT a.idScheduler,a.namScheduler,a.idTemplate ");
        queryString.append("FROM FixSchedulerImport a ");
       
        Query q = getSession().createSQLQuery(queryString.toString());
		
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return q.list();
    }

    /**
     * 
     * @param idScheduler
     * @return
     */
    public List list(int idScheduler) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT A.namScheduler,A.flgStatus,A.idTemplate ");
        queryString.append("FROM FixSchedulerImport A ");
        queryString.append("WHERE A.idScheduler =");
        queryString.append(idScheduler);
        Query q = getSession().createSQLQuery(queryString.toString());
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return q.list();
    }

    /**
     * 
     * @param GrpId
     * @return
     */
    public List list(String GrpId) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT DISTINCT a.idScheduler,a.namScheduler ");
        queryString.append("FROM FixSchedulerImport a,FixEmailAccess b ");
        queryString.append("WHERE a.idScheduler = b.idScheduler ");
        queryString.append("AND b.grpId = '");
        queryString.append(GrpId).append("'");
        Query q = getSession().createSQLQuery(queryString.toString());
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return q.list();
    }

    /**
     * 
     * @param namScheduler
     * @return
     */
    public List list(FixSchedulerPK namScheduler) {
        logger.info("namScheduler " + namScheduler.getNamScheduler());
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerImport.class);
        criteriaQuery.add(Restrictions.like("fixSchedulerPK.namScheduler", namScheduler.getNamScheduler() + "%"));
        criteriaQuery.addOrder(Order.asc("fixSchedulerPK.namScheduler"));
        return criteriaQuery.list();
    }

    /**
     * 
     * @param patternScheduler
     * @return
     */
    public List<FixSchedulerImport> listbyPattern(String patternScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerImport.class);
        criteriaQuery.add(Restrictions.like("filePattern", patternScheduler + "%"));
        criteriaQuery.add(Restrictions.not(Restrictions.in("fixSchedulerPK.idScheduler",new Integer[]{1,2,3,4,5,6,7,8,9,10})));
        criteriaQuery.addOrder(Order.asc("fixSchedulerPK.namScheduler"));
        return criteriaQuery.list();
    }
    /**
     * 
     * @param idScheduler
     * @param idTemplate
     * @return
     */
    public String before(int idScheduler, int idTemplate) {
        String k;
        String[] k2;
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT A.keyDecrypt ");
        queryString.append("FROM FixSchedulerImport A ");
        queryString.append("WHERE A.idScheduler = ");
        queryString.append(idScheduler);
        queryString.append(" AND A.idTemplate = ");
        queryString.append(idTemplate);
        Query q = getSession().createSQLQuery(queryString.toString());
        k = q.list().toString();
        k2 = k.substring(1).split("]");
        return k2[0];
    }

    /**
     * 
     * @param pk
     * @return
     */
    public FixSchedulerImport get(FixSchedulerPK pk) {
        return (FixSchedulerImport) getSession().get(FixSchedulerImport.class, pk);
    }

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save(baseModel);
        return true;
    }

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update(baseModel);
        return true;
    }

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete(baseModel);
        return true;
    }
}
