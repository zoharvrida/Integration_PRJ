/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpAdqPmb;
import bdsm.scheduler.model.TmpAdqPml;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author NCBS
 */
public class AdqWorkerDao extends BaseDao implements Work {

    /**
     * 
     * @param session
     */
    public AdqWorkerDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param param
     * @return
     */
    public List<TmpAdqPmb> getPmb(String param) {
        Query query = getSession().createQuery("from TmpAdqPmb where batchNo = :prm_batch");
        query.setString("prm_batch", param);
        return query.list();
    }

    /**
     * 
     * @param param
     * @return
     */
    public List<TmpAdqPml> getPml(String param) {
        Query query = getSession().createQuery("from TmpAdqPml where batchNo = :prm_batch");
        query.setString("prm_batch", param);
        return query.list();
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
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
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
        logger.info("begin execute package");
        logger.info("worktype : " + workType);
        try {
            String query = null;
            switch (workType) {
                case 1:
                    query = queryUploadPmb;
                    break;
                case 2:
                    query = queryUploadPml;
                    break;
                case 3:
                    query = queryApprovePmb;
                    break;
                case 4:
                    query = queryApprovePml;
                    break;
                case 5:
                    query = queryRejectPmb;
                    break;
                case 6:
                    query = queryRejectPml;
                    break;
            }
            CallableStatement stmt = cnctn.prepareCall(query);
            logger.info("prepare execute statement");
            stmt.setString(1, parameter);
            if ((workType==3)||(workType==4)){
                stmt.registerOutParameter(2, OracleTypes.NUMBER);
            }
            logger.info("batchno : " + parameter);
            int workRes = stmt.executeUpdate();
            if ((workType==3)||(workType==4)){
                workRes = stmt.getInt(2);
            }
            logger.info("execute statement");
            switch (workType) {
                case 1:
                    workResult1 = workRes;
                    break;
                case 2:
                    workResult2 = workRes;
                    break;
                case 3:
                    workResult3 = workRes;
                    break;
                case 4:
                    workResult4 = workRes;
                    break;
                case 5:
                    workResult5 = workRes;
                    break;
                case 6:
                    workResult6 = workRes;
                    break;
            }
            logger.info("workres : " + workRes);
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
        logger.info("end execute package");
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runUploadPmb(String param) {
        logger.info("begin runUploadPmb");
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        logger.info("end runUploadPmb");
        return workResult1;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runUploadPml(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runApprovePmb(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runApprovePml(String param) {
        Session session = getSession();
        workType = 4;
        parameter = param;
        session.doWork(this);
        return workResult4;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runRejectPmb(String param) {
        Session session = getSession();
        workType = 5;
        parameter = param;
        session.doWork(this);
        return workResult5;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runRejectPml(String param) {
        Session session = getSession();
        workType = 6;
        parameter = param;
        session.doWork(this);
        return workResult6;
    }
    private String parameter = null;
    private static final String queryUploadPmb = "{call PK_ADQ_PMT.sumarizePmb(?)}";
    private static final String queryUploadPml = "{call PK_ADQ_PMT.sumarizePml(?)}";
    private static final String queryApprovePmb = "{call PK_ADQ_PMT.approvePmb(?,?)}";
    private static final String queryApprovePml = "{call PK_ADQ_PMT.approvePml(?,?)}";
    private static final String queryRejectPmb = "{call PK_ADQ_PMT.rejectPmb(?)}";
    private static final String queryRejectPml = "{call PK_ADQ_PMT.rejectPml(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workResult4 = 0;
    private int workResult5 = 0;
    private int workResult6 = 0;
    private int workType = 0;
    private Logger logger = Logger.getLogger(AdqWorkerDao.class);
}
