/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.service;

import bdsm.scheduler.dao.DataPoolingDao;
import bdsm.scheduler.dao.ErrorCodeDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class DataPoolingService {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DataPoolingService.class);
    private DataPoolingDao dpoolingDao;
    private ErrorCodeDao errDao;
    private Session session;
    private String errFlag;
    private String errValue;
    private String errID;

    /**
     * 
     * @param idScheduler
     * @param batchID
     * @return
     * @throws InterruptedException
     */
    public List dataPoolingReport(Integer idScheduler, String batchID) throws InterruptedException {
        List nameContainList = new ArrayList();
        List<FixSchedulerXtract> dpReport = dpoolingDao.scheDulerXtractReport(idScheduler);
        boolean flag = true;
        while (flag) {
                List<FixQXtract> qxtractList = dpoolingDao.fixqXtractWatcher(idScheduler,batchID);
                if (!qxtractList.isEmpty()) {
                    // check total Report if Same set Flag to true
                    if(dpReport.size() == qxtractList.size()){
                        nameContainList.addAll(qxtractList);
                        logger.debug("list DP :" + nameContainList);
                        flag = false;
                        logger.debug("DATA COLLECTED : " + qxtractList.size());
                    } else if(dpReport.size() < qxtractList.size()){
                        // probably multiple
                    } else if(dpReport.size() > qxtractList.size()) {
                        // not complete
                        Thread.sleep(5000);
                        logger.debug("SLEEP TIME :" + qxtractList.size());
                    } else {
                        nameContainList = null;
                    }
                } else {
                    Thread.sleep(5000);
                    logger.debug("PROCESS NOT DONE PUT TO SLEEP");
                    // check maxRetry and elapsed time
                }
        }
        return nameContainList;
    }

    

    /**
     * @return the errFlag
     */
    public String getErrFlag() {
        return errFlag;
    }

    /**
     * @param errFlag the errFlag to set
     */
    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag;
    }

    /**
     * @return the errValue
     */
    public String getErrValue() {
        return errValue;
    }

    /**
     * @param errValue the errValue to set
     */
    public void setErrValue(String errValue) {
        this.errValue = errValue;
    }

    /**
     * @return the errID
     */
    public String getErrID() {
        return errID;
    }

    /**
     * @param errID the errID to set
     */
    public void setErrID(String errID) {
        this.errID = errID;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the errDao
     */
    public ErrorCodeDao getErrDao() {
        return errDao;
    }

    /**
     * @param errDao the errDao to set
     */
    public void setErrDao(ErrorCodeDao errDao) {
        this.errDao = errDao;
    }

    /**
     * @return the dpoolingDao
     */
    public DataPoolingDao getdPoolingDao() {
        return dpoolingDao;
    }

    /**
     * @param dPoolingDao 
     */
    public void setdPoolingDao(DataPoolingDao dPoolingDao) {
        this.dpoolingDao = dPoolingDao;
    }
}
