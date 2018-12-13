/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.scheduler;

import bdsm.util.HibernateUtil;
import bdsmhost.dao.MfcSchedulerDao;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author 00030663
 */
public final class MFCScheduler extends Thread {
    private static int SLEEP = 1000;
    private int timeout = 15 * 60 * 1000;
    private boolean stale = false;
    private boolean terminated = false;
    
    /**
     * 
     * @param period
     */
    public MFCScheduler(int period) {
        setTimeout(period);
    }

    @Override
    public void run() {
        try {
            long start = Calendar.getInstance().getTimeInMillis();
            long end, elapse;
            getLogger().debug("----- [START] RUN at :"  + start);
            while (!terminated) {
                try {
                    MfcSchedulerDao dao = null;
                    end = Calendar.getInstance().getTimeInMillis();
                    elapse = end - start;
                    getLogger().debug("----- [START] Scheduler mfc on elapsed :" + elapse);
                    if (elapse >= timeout) {
                        Session hsession = HibernateUtil.getSession();
                        try {
                            dao = new MfcSchedulerDao(hsession);
                            int result = 0;
                            try { result = dao.runFCRScheduler(); } catch (Throwable e) { result = -1; }
                            getLogger().info("[MFCScheduler] result FCR: " + result);
                            result = 0;
                            try { result = dao.runTFScheduler(); } catch (Throwable e) { result = -1; }
                            getLogger().info("[MFCScheduler] result TF: " + result);
                        } finally {
                            if (dao != null) {
                                HibernateUtil.closeSession(hsession);
                                dao = null;
                            }
                        }
                        start = end;
                    }
                } catch (Throwable e) {
                    getLogger().debug("THROW :" + e,e);
                }
                    
                try {
                    Thread.sleep(SLEEP);
                } catch (InterruptedException ex) {
                    getLogger().debug("EX :" + ex,ex);
                }
            }
        } catch (Throwable e) {
            stale = true;
            getLogger().fatal("Stale", e);
        }
    }

    /**
     * @return the stale
     */
    public boolean isStale() {
        return stale;
    }
    
    /**
     * 
     */
    public void terminate() {
        terminated=true;
    }

    private Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    /**
     * @param period 
     */
    public void setTimeout(int period) {
        if (period > 0) {
            this.timeout = period * 60 * 1000;
        }
    }
}
