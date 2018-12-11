package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.FixQXtract;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 * @author bdsm
 */
public class FixQXtractDao extends BaseDao {

    /**
     * 
     * @param session
     */
    public FixQXtractDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param reason
     * @param param1
     * @param param2
     * @return
     */
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
    
    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save((FixQXtract) baseModel);
        return true;
    }

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update((FixQXtract) baseModel);
        return true;
    }

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete((FixQXtract) baseModel);
        return true;
    }

    /**
     * 
     * @param size
     * @return
     */
    public List<FixQXtract> getAllQXtractList(int size) {
        Query query = getSession().createQuery("from FixQXtract where flgProcess = :flgProcess");
        query.setString("flgProcess", StatusDefinition.REQUEST);
        query.setMaxResults(size);
        return query.list();
    }

    /**
     * 
     * @param size
     * @param idScheduler
     * @return
     */
    public List<FixQXtract> getAllQXtractDeleteScheduledList(int size, int idScheduler) {
        Query query = getSession().createQuery("from FixQXtract where flgProcess in ('D','E') and idScheduler = :v_idScheduler");
        query.setInteger("v_idScheduler", idScheduler);
        query.setMaxResults(size);
        return query.list();
    }    

    /**
     * 
     * @param idScheduler
     * @param param6
     * @return
     */
    public FixQXtract get(Integer idScheduler, String param6){
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT a ");
        queryString.append("FROM FixQXtract a ");
        queryString.append("WHERE a.idScheduler = :idScheduler ");
        queryString.append("AND a.param6 = :param6 ");
        
        Query q = getSession().createQuery(queryString.toString())
                .setInteger("idScheduler", idScheduler)
                .setString("param6", param6)
        ;
        
        return (FixQXtract) q.uniqueResult();
    }

       /**
        * 
        * @param qId
        * @return
        */
    public FixQXtract get(Integer qId) {
        return (FixQXtract) getSession().get(FixQXtract.class, qId);
    }
}
