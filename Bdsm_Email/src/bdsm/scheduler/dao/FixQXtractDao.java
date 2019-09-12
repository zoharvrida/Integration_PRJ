package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.FixQXtract;
import bdsmhost.dao.BaseDao;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class FixQXtractDao extends BaseDao {

    public FixQXtractDao(Session session) {
        super(session);
    }

    public FixQXtract checkDupplicate(String reason, String param1, String param2) {
        StringBuilder sb = new StringBuilder("from FixQXtract ");
        sb.append(" where reason = :reason ");
        sb.append("and param1 = :param1 ");
        sb.append("and param2 = :param2 ");
        Query q = getSession().createQuery(sb.toString());
        q.setString("reason", reason);
        q.setString("param1", param1);
        q.setString("param2", param2);
        List result = q.list();
        if (result.size() != 0) {
            return (FixQXtract) result.get(0);
        }
        return null;
    }
    
    @Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save((FixQXtract) baseModel);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update((FixQXtract) baseModel);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete((FixQXtract) baseModel);
        return true;
    }
    public List<FixQXtract> getAllQXtractList(int size) {
        return getAllQXtractList(size,"");
    }
    public List<FixQXtract> getAllQXtractList(int size, String type) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("from FixQXtract A where flgProcess = :flgProcess ");
        queryString.append("AND idScheduler IN (SELECT fixSchedulerPK.idScheduler FROM FixSchedulerXtract WHERE fixSchedulerPK.idScheduler = A.idScheduler ");
        if(!"".equalsIgnoreCase(type)){
           queryString.append("AND lower(typDest) = :type) "); 
        } else {
           queryString.append("AND lower(typDest) NOT LIKE 'email' ) "); 
        }
        Query query = getSession().createQuery(queryString.toString());
        query.setString("flgProcess", StatusDefinition.REQUEST);
        if(!"".equalsIgnoreCase(type)){
            query.setString("type",type);
        }
        query.setMaxResults(size);
        return query.list();
    }

    public List<FixQXtract> getAllQXtractDeleteScheduledList(int size, int idScheduler) {
        Query query = getSession().createQuery("from FixQXtract where flgProcess in ('D','E') and idScheduler = :v_idScheduler");
        query.setInteger("v_idScheduler", idScheduler);
        query.setMaxResults(size);
        return query.list();
    }    

       public FixQXtract get(Integer idScheduler,String param6){
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT * ");
        queryString.append("FROM fixqxtract a ");
        queryString.append("WHERE a.idScheduler = '");
        queryString.append(idScheduler).append("'");
        queryString.append("AND a.param6 = '");
        queryString.append(param6).append("'");
        Query q = getSession().createSQLQuery(queryString.toString());
        return (FixQXtract) q.uniqueResult();
    }

    public FixQXtract get(Integer qId) {
        return (FixQXtract) getSession().get(FixQXtract.class, qId);
    }
    
    public List<FixQXtract> getQXtractbasedWatcher(Integer qid,Integer idscheduler,String idBatch) {
        Query query = getSession().getNamedQuery("Fixqxtract#getWatcherdata");
        query.setString("pIdbatch", idBatch);
        query.setInteger("pQid", qid);
        query.setInteger("pIdScheduler", idscheduler);
        return query.list();
    }
}
