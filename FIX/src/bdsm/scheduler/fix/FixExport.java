package bdsm.scheduler.fix;

import bdsm.scheduler.*;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.mappingScheduleDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.MappingSchedule;
import bdsm.scheduler.processor.XtractJobProcessor;
import bdsm.scheduler.processor.XtractProcessor;
import bdsm.scheduler.processor.XtractSchedulerProcessor;
import bdsm.scheduler.util.MailUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * 
 * @author bdsm
 */
public class FixExport extends BDSMScheduler2 {

    private int maxJob;
    private static final String adminEmail = PropertyPersister.adminEmail;

    /**
     * 
     * @param timeout
     * @param sleep
     * @param btsleep
     * @param threadcount
     */
    public FixExport(long timeout, long sleep, long btsleep, int threadcount) {
        super(timeout, sleep, btsleep, threadcount);
        this.maxJob = threadcount;
    }

    /**
     * 
     */
    @Override
    protected void execute() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
            List<FixSchedulerXtract> listFixSchedulerXtract = fixSchedulerXtractDao.getAllXtractSchedule(this.maxJob);
            FixSchedulerXtract executedFixSchedulerXtract;
			getLogger().debug("NUMBER OF THREAD :" + this.maxJob);
            //set maximum thread assign
            int max = listFixSchedulerXtract.size() > this.maxJob ? this.maxJob : listFixSchedulerXtract.size();
            //get All SCHTIMER enabled flag
			getLogger().debug("NUMBER OF SCHEDULER :" + max);
            HashMap hashMap;
            XtractProcessor xtractProcessor;

            //assign thread
            getLogger().info(">>>>>FixSchedulerXtract<<<<<");
            for (int i = 0; i < max; i++) {
                xtractProcessor = new XtractSchedulerProcessor();
                hashMap = new HashMap();
                //executedFixSchedulerXtract = fixSchedulerXtractDao.get(Integer.parseInt(listFixSchedulerXtract.get(i).getEmailTo()));
                executedFixSchedulerXtract = listFixSchedulerXtract.get(i);
                hashMap.put(MapKey.model, executedFixSchedulerXtract);
                hashMap.put(MapKey.flgCalendar, executedFixSchedulerXtract.getFlgCalendar());
                hashMap.put(MapKey.schTimerProfile, executedFixSchedulerXtract.getSchTimerProfile());
                hashMap.put(MapKey.typeFix, StatusDefinition.XTRACT);
                if (this.assign(hashMap, xtractProcessor) == null) {
                    try {
                        MailUtil.sentNewMessage(adminEmail, "", "[BDSM ALERT]", "Not Enough Thread", null, session, ScheduleDefinition.emailOnly);
                    } catch (Exception ex) {
                        getLogger().error(ex);
                    }
                    break;
                }
            }

            FixQXtractDao fixQXtractDao = new FixQXtractDao(session);
            List<FixQXtract> listFixQXtract = fixQXtractDao.getAllQXtractList(this.maxJob);
            
            getLogger().debug("LIST SIZE :" + listFixQXtract.size());
            for(FixQXtract qx : listFixQXtract){
                if(qx != null){
                    getLogger().debug("LIST IDscheduler :" + qx.getIdScheduler());
                    getLogger().debug("LIST XTRACT :" + qx.getqId());
                }
            }
            //set maximum thread assign
            max = listFixQXtract.size() > this.maxJob ? this.maxJob : listFixQXtract.size();
            String ds = "";
            String fTimer = "";
            String fCal = "";
            //assign thread
            getLogger().info(">>>>>FixQXtract<<<<< : " + max);
            for (int i = 0; i < max; i++) {
                xtractProcessor = new XtractJobProcessor();
                hashMap = new HashMap();
                FixQXtract fixQXtract = listFixQXtract.get(i);
                getLogger().debug("ID SCHEDULER :" + listFixQXtract.get(i).getIdScheduler());
                fixQXtract.setFlgProcess(StatusDefinition.PROCESS);
                fixQXtract.setDtmProcess(SchedulerUtil.getTime());
                executedFixSchedulerXtract = fixSchedulerXtractDao.get(fixQXtract.getIdScheduler());
                try {
                    ds = executedFixSchedulerXtract.getDataSource();
                } catch (Exception e) {
                    ds = "";
                    getLogger().debug("Data Source Null");
                }
                try {
                    fTimer = executedFixSchedulerXtract.getFlgCalendar();
                } catch (Exception e) {
                    fTimer = "";
                    getLogger().debug("SCHTIMER Flag Null");
                }
                try {
                    fCal = executedFixSchedulerXtract.getSchTimerProfile();
                } catch (Exception e) {
                    fCal = "";
                    getLogger().debug("Not Using Any Calendar");
                }
                hashMap.put(MapKey.model, fixQXtract);
                hashMap.put(MapKey.typeFix, StatusDefinition.XTRACT);
                hashMap.put(MapKey.dataSource, ds);
                hashMap.put(MapKey.flgCalendar, fCal);
                hashMap.put(MapKey.schTimerProfile, fTimer);
                if (this.assign(hashMap, xtractProcessor) == null) {
                    try {
                        getLogger().error("NO THREAD");
                        MailUtil.sentNewMessage(adminEmail, "", "[BDSM ALERT]", "Not Enough Thread", null, session, ScheduleDefinition.emailOnly);
                    } catch (Exception ex) {
                        getLogger().error(ex,ex);
                    }
                    break;
                } else {
                    try {
                        getLogger().error("THREAD EXIST");
                        tx = session.beginTransaction();
                        fixQXtractDao.update(fixQXtract);
                        session.flush();
                        tx.commit();
                    } catch (Exception ee) {
                        getLogger().error(ee, ee);
                    }
                }
            }
        } catch (Exception e) {
            getLogger().error(e, e);
        } finally {
            getLogger().info(">>>>>FixExport Done<<<<<");
            HibernateUtil.closeSession(session);
        }
    }
}