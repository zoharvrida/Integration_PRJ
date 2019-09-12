/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixqxtractWatcher;
import bdsmhost.dao.BaseDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;


/**
 *
 * @author SDM
 */
public class FixQxtractWatcherDao extends BaseDao implements Work {

    private String callFunc;
    private String costumParam;
    private String result;
    private Integer paramNumber;
    private List paramCollection = new ArrayList();
    private List refparam = new ArrayList();
    
    private static final String queryStore = "call ";
    private Logger logger = Logger.getLogger(FixQxtractWatcherDao.class);
    public FixQxtractWatcherDao(Session session) {
        super(session);
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((FixqxtractWatcher) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	@SuppressWarnings("rawtypes")
    public List get(String qstatus, String pstatus){
		List crit = new ArrayList();
        Criteria criteriaQuery = getSession().createCriteria(FixqxtractWatcher.class);
        criteriaQuery.add(Restrictions.eq("flgProcess", qstatus));
        criteriaQuery.add(Restrictions.eq("processStatus", pstatus));
		crit = criteriaQuery.list();
		logger.debug("LIST WATCHER :" + crit);
        return crit;
    }
    public List getAllError(Integer qstatus, String idBatch, Integer idSched){
        Criteria criteriaQuery = getSession().createCriteria(FixqxtractWatcher.class);
        criteriaQuery.add(Restrictions.eq("qidRef", qstatus));
        criteriaQuery.add(Restrictions.eq("param6", idBatch));
        criteriaQuery.add(Restrictions.eq("idScheduler", idSched));
        return criteriaQuery.list();
    }
    public String runQuery(String pattern, String parameter,String delimiter , String fields) {
        Session session = getSession();
        StringBuilder getParam = new StringBuilder(" (");
        this.callFunc = pattern;
        try {
            refparam = bdsm.scheduler.util.SchedulerUtil.getParameter(fields, delimiter);
            paramCollection = bdsm.scheduler.util.SchedulerUtil.getParameter(parameter, delimiter);
            for (int i = 0; i < paramCollection.size(); i++) {
                if (i == paramCollection.size() - 1) {
                    getParam.append("?)");
                } else {
                    getParam.append("?,");
                }
            }
            this.costumParam = getParam.toString();
        } catch (Exception e) {
            logger.debug("FAILED GET PARAMETER :" + e,e);
        }
        session.doWork(this);
        return result;
    }
    public void execute(Connection cnctn) throws SQLException {
        Statement labelSt = null;
        ResultSet rs = null;
        
        try {
                String query = "{" + queryStore + this.callFunc + this.costumParam + "}";
                CallableStatement stmt = cnctn.prepareCall(query);
                int x = 0;
                for(int i=1;i<(paramNumber+1);i++){
                    if("STRING".equalsIgnoreCase(refparam.get(x).toString())){
                        stmt.setString(i, paramCollection.get(x).toString());
                    } else if("NUMBER".equalsIgnoreCase(refparam.get(x).toString())){
                        stmt.setInt(i, Integer.parseInt(paramCollection.get(i-1).toString()));
                    } else if("DATE".equalsIgnoreCase(refparam.get(x).toString())){
                        stmt.setDate(i, null);
                    } else if("INSTRING".equalsIgnoreCase(refparam.get(x).toString())){
                        stmt.registerOutParameter(i, java.sql.Types.VARCHAR);
                    }
                    x++;
                }
                stmt.executeUpdate();
                //result = stmt.getInt(1);
                stmt.close();

        } catch (SQLException e) {
            logger.info("SQL EXCEPTION :" + e, e);
        } catch (Exception e) {
            logger.info("EXCEPTION :" + e, e);
        } 
    }
}
