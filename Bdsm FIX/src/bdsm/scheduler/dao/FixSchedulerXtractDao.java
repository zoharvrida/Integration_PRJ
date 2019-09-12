package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.FixSchedulerPK;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.BaseDao;
import java.sql.Timestamp;
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
public class FixSchedulerXtractDao extends BaseDao {
    private Logger logger = Logger.getLogger(FixSchedulerXtractDao.class);
    
    
    /**
     * 
     * @param session
     */
    public FixSchedulerXtractDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param namScheduler
     * @return
     */
    public List list(String namScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.like("fixSchedulerPK.namScheduler", namScheduler + "%"));
        criteriaQuery.add(Restrictions.not(Restrictions.in("fixSchedulerPK.idScheduler",new Integer[]{1,2,3,4,5,6,7,8,9,10})));
        criteriaQuery.addOrder(Order.asc("fixSchedulerPK.namScheduler"));
        return criteriaQuery.list();
    }

    /**
     * 
     * @param schedID
     * @return
     */
    public List<FixSchedulerXtract> getSchTimer(List schedID){
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.eq("schTimerProfile", StatusDefinition.YES));
        criteriaQuery.add(Restrictions.in("fixSchedulerPK.idScheduler",schedID));
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
        queryString.append("SELECT A.keyEncrypt ");
        queryString.append("FROM FixSchedulerXtract A ");
        queryString.append("WHERE A.idScheduler = ");
        queryString.append(idScheduler);
        queryString.append(" AND A.idTemplate = ");
        queryString.append(idTemplate);
        Query q = getSession().createSQLQuery(queryString.toString());
        //q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        k = q.list().toString();
        k2 = k.substring(1).split("]");
        
        return k2[0];
    }

    /**
     * 
     * @param patternScheduler
     * @return
     */
    public List listbyPattern(String patternScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.like("filePattern", patternScheduler + "%"));
        criteriaQuery.add(Restrictions.not(Restrictions.in("fixSchedulerPK.idScheduler",new Integer[]{1,2,3,4,5,6,7,8,9,10})));
        criteriaQuery.addOrder(Order.asc("fixSchedulerPK.namScheduler"));
        return criteriaQuery.list();
    }
    
    /**
     * 
     * @param pk
     * @return
     */
    public FixSchedulerXtract get(FixSchedulerPK pk) {
        return (FixSchedulerXtract) getSession().get(FixSchedulerXtract.class, pk);
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

    /**
     * 
     * @param namScheduler
     * @return
     */
    public FixSchedulerXtract get(String namScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.eq("fixSchedulerPK.namScheduler", namScheduler));
        return (FixSchedulerXtract)criteriaQuery.uniqueResult();
    }
    
    /**
     * 
     * @param idScheduler
     * @return
     */
    public FixSchedulerXtract get(Integer idScheduler) {
        StringBuilder sb = new StringBuilder("select A from FixSchedulerXtract A where (select datProcess from FcrBaBankMast where compositeId.flgMntStatus = 'A') between A.dtEffStart and A.dtEffEnd ");
        sb.append("and A.fixSchedulerPK.idScheduler = :idScheduler");
        Query query = getSession().createQuery(sb.toString());
        query.setInteger("idScheduler", idScheduler);
        List list = query.list();
        if (list.size() == 1) {
            return (FixSchedulerXtract) list.get(0);
        }
        return null;
    }

    /**
     * 
     * @param idTemplate
     * @return
     */
    public FixSchedulerXtract getByIdTemplate(Integer idTemplate) {
        Query query = this.getSession().getNamedQuery("FixSchedulerXtract#getByIdTemplate");
        query.setInteger("idTemplate", idTemplate);
        List list = query.list();
        
        if (list.size() == 1)
            return (FixSchedulerXtract) list.get(0);
        else
            return null;
    }

    /**
     * 
     * @param size
     * @return
     */
    public List<FixSchedulerXtract> getAllXtractSchedule(int size) {
        Timestamp t = SchedulerUtil.getTime();
        logger.debug("TIME :" + t);
        logger.debug("TIME MINUTES :" + t.getMinutes());
        
        StringBuilder sb = new StringBuilder("select A from FixSchedulerXtract A where "
                + "(select datProcess from FcrBaBankMast where compositeId.flgMntStatus = 'A') between A.dtEffStart and A.dtEffEnd ");
        sb
            .append("and A.flgStatus = 'A' and A.typScheduler = 'P' and lower(A.typDest) <> 'email' ")
            .append("and CONCAT(',', A.minute, ',') LIKE :min ")
            .append("and CONCAT(',', A.hour, ',') LIKE :hour ")
            .append("and CONCAT(',', A.monthDay, ',') LIKE :date ")
            .append("and CONCAT(',', A.month, ',') LIKE :month ")
            .append("and CONCAT(',', A.weekDay, ',') LIKE :day ");
        
        Query query = getSession().createQuery(sb.toString());
        query.setString("min", ("%," + t.getMinutes() + ",%"));
        query.setString("hour", ("%," + t.getHours() + ",%"));
        query.setString("date", ("%," + t.getDate() + ",%"));
        query.setString("month", ("%," + (t.getMonth() + 1) + ",%"));
        query.setString("day", ("%," + t.getDay() + ",%"));
        query.setMaxResults(size);
        
        return query.list();
    }
}
