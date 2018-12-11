package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FixSchedulerPK;
import bdsm.model.FixSchedulerXtract;
import bdsm.util.SchedulerUtil;
import bdsm.util.StatusDefinition;
import java.sql.Timestamp;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class FixSchedulerXtractDao extends BaseDao {

	private Logger logger = Logger.getLogger(FixSchedulerXtractDao.class);
    public FixSchedulerXtractDao(Session session) {
        super(session);
    }

    public List list(String namScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.like("fixSchedulerPK.namScheduler", namScheduler + "%"));
        criteriaQuery.add(Restrictions.not(Restrictions.in("fixSchedulerPK.idScheduler",new Integer[]{1,2,3,4,5,6,7,8,9,10})));
        criteriaQuery.addOrder(Order.asc("fixSchedulerPK.namScheduler"));
        return criteriaQuery.list();
    }

    public List<FixSchedulerXtract> getSchTimer(List schedID){
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.eq("schTimerProfile", StatusDefinition.YES));
        criteriaQuery.add(Restrictions.in("fixSchedulerPK.idScheduler",schedID));
        return criteriaQuery.list();
    }
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

    public List listbyPattern(String patternScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.like("filePattern", patternScheduler + "%"));
        criteriaQuery.add(Restrictions.not(Restrictions.in("fixSchedulerPK.idScheduler",new Integer[]{1,2,3,4,5,6,7,8,9,10})));
        criteriaQuery.addOrder(Order.asc("fixSchedulerPK.namScheduler"));
        return criteriaQuery.list();
    }
    
    public FixSchedulerXtract get(FixSchedulerPK pk) {
        return (FixSchedulerXtract) getSession().get(FixSchedulerXtract.class, pk);
    }
    
    @Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save(baseModel);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update(baseModel);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete(baseModel);
        return true;
    }

    public FixSchedulerXtract get(String namScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixSchedulerXtract.class);
        criteriaQuery.add(Restrictions.eq("fixSchedulerPK.namScheduler", namScheduler));
        return (FixSchedulerXtract)criteriaQuery.uniqueResult();
    }
    
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

	public FixSchedulerXtract getByIdTemplate(Integer idTemplate) {
		Query query = this.getSession().getNamedQuery("FixSchedulerXtract#getByIdTemplate");
		query.setInteger("idTemplate", idTemplate);
		List list = query.list();
		
		if (list.size() == 1)
			return (FixSchedulerXtract) list.get(0);
		else
			return null;
	}

    public List<FixSchedulerXtract> getAllXtractSchedule(int size) {
        Timestamp t = SchedulerUtil.getTime();
		logger.debug("TIME :" + t);
		logger.debug("TIME MINUTES :" + t.getMinutes());
        StringBuilder sb = new StringBuilder("select A from FixSchedulerXtract A where "
                + "(select datProcess from FcrBaBankMast where compositeId.flgMntStatus = 'A') between A.dtEffStart and A.dtEffEnd ");
        sb.append("and A.flgStatus = 'A' and A.typScheduler = 'P' ");
        sb.append("and (A.hour LIKE :hour1 or A.hour LIKE :hour2 or A.hour LIKE :hour3 or A.hour = :hour4) ");
        sb.append("and (A.minute LIKE :min1 or A.minute LIKE :min2 or A.minute LIKE :min3 or A.minute = :min4) ");
        sb.append("and (A.monthDay LIKE :date1 or A.monthDay LIKE :date2 or A.monthDay LIKE :date3 or A.monthDay = :date4) ");
        sb.append("and (A.month LIKE :month1 or A.month LIKE :month2 or A.month LIKE :month3 or A.month = :month4) ");
        sb.append("and (A.weekDay LIKE :day1 or A.weekDay LIKE :day2 or A.weekDay LIKE :day3 or A.weekDay = :day4) ");
        Query query = getSession().createQuery(sb.toString());

        query.setString("min1", (t.getMinutes() + ",%"));
        query.setString("hour1", (t.getHours() + ",%"));
        query.setString("date1", (t.getDate() + ",%"));
        query.setString("month1", ((t.getMonth() + 1) + ",%"));
        query.setString("day1", (t.getDay() + ",%"));

        query.setString("min2", ("%," + t.getMinutes() + ",%"));
        query.setString("hour2", ("%," + t.getHours() + ",%"));
        query.setString("date2", ("%," + t.getDate() + ",%"));
        query.setString("month2", ("%," + (t.getMonth() + 1) + ",%"));
        query.setString("day2", ("%," + t.getDay() + ",%"));

        query.setString("min3", ("%," + t.getMinutes()));
        query.setString("hour3", ("%," + t.getHours()));
        query.setString("date3", ("%," + t.getDate()));
        query.setString("month3", ("%," + (t.getMonth() + 1)));
        query.setString("day3", ("%," + t.getDay()));

        query.setString("min4", String.valueOf(t.getMinutes()));
        query.setString("hour4", String.valueOf(t.getHours()));
        query.setString("date4", String.valueOf(t.getDate()));
        query.setString("month4", String.valueOf(t.getMonth() + 1));
        query.setString("day4", String.valueOf(t.getDay()));

        query.setMaxResults(size);
        return query.list();
    }
}
