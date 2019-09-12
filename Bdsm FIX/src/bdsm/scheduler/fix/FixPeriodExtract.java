/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.fix;

import bdsm.scheduler.BdsmScheduler;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.processor.PeriodObjectReflection;
import bdsm.util.SchedulerUtil;
import bdsm.util.HibernateUtil;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author USER
 */
public class FixPeriodExtract extends BdsmScheduler {

    /**
     * 
     */
    protected Session session = null;
    /**
     * 
     */
    protected FixSchedulerXtractDao fixSchedulerXtractDao = null;
    /**
     * 
     */
    protected List<FixSchedulerXtract> listFixSchedulerXtract = null;
    /**
     * 
     */
    protected FixSchedulerXtract fixSchedulerXtract = null;
    /**
     * 
     */
    protected FixQXtract fixQXtract = null;

    /**
     * 
     * @param timeout
     * @param sleep
     * @param btsleep
     * @param threadcount
     */
    public FixPeriodExtract(long timeout, long sleep, long btsleep, int threadcount) {
        super(timeout, sleep, btsleep, threadcount);
    }
    //@Override

    /**
     * 
     */
    protected void execute() {
        try {
            getLogger().debug(System.getenv());
        } catch (Exception e) {
            getLogger().debug("ENVIRONMENT VARIABLE DOESN'T EXIST : PERIOD");
        }
        session = HibernateUtil.getSession();
        fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
        listFixSchedulerXtract = fixSchedulerXtractDao.getAllXtractSchedule(10);
        for (int i = 0; i < listFixSchedulerXtract.size(); i++) {
            fixSchedulerXtract = listFixSchedulerXtract.get(i);
            getLogger().debug("PROFILE :" + fixSchedulerXtract.getFixSchedulerPK().getNamScheduler());
            if (NumberUtils.isNumber(fixSchedulerXtract.getEmailTo().substring(0, 1))) {
                //Register FixQXtract
                registerFixQXtract(Integer.parseInt(fixSchedulerXtract.getEmailTo().substring(0, 1)));
                fixSchedulerXtractDao.insert(fixQXtract);
            } else {
                //execute class
                PeriodObjectReflection periodObjectReflection = new PeriodObjectReflection();
                HashMap hashMap = new HashMap();
                hashMap.put(MapKey.javaClass, fixSchedulerXtract.getEmailTo());
                this.assign(hashMap, periodObjectReflection);
            }
        }
        HibernateUtil.closeSession(session);
    }

    /**
     * 
     * @param idScheduler
     */
    protected void registerFixQXtract(int idScheduler) {
        FixSchedulerXtractDao fDao = new FixSchedulerXtractDao(session);
        FixSchedulerXtract fXtract = fDao.get(idScheduler);
        fixQXtract = new FixQXtract();
        fixQXtract.setIdScheduler(idScheduler);
        fixQXtract.setDtmRequest(SchedulerUtil.getTime());
        fixQXtract.setParam1(fXtract.getEmailSubjectPattern());
        fixQXtract.setParam2(fXtract.getEmailTo());
        fixQXtract.setParam3(fXtract.getEmailCC());
        fixQXtract.setParam4(fXtract.getEmailBody());
        fixQXtract.setParam5(fXtract.getEmailAttachment());
    }
}
