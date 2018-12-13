/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.scheduler;

import bdsm.model.EktpSysuser;
import bdsm.scheduler.StatusDefinition;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

/**
 * Web application lifecycle listener.
 *
 * @author 00030663
 */
public class SchedulerListener implements ServletContextListener, HttpSessionListener {
    private boolean started = false;
    private MFCScheduler mfcScheduler = null;
    private int mfcPeriod = 15;
    
    private void createMFCScheduler() {
        getLogger().debug("----- [START] Mfc -----");
        if (mfcScheduler == null) {
            getLogger().debug("----- [START] MfcScheduler -----");
            synchronized(this) {
                getLogger().debug("----- [START] After Sync :" + mfcScheduler);
                if (mfcScheduler == null) {
                    if (mfcPeriod>0) {
                        
                        mfcScheduler = new MFCScheduler(mfcPeriod);
                        mfcScheduler.start();
                        getLogger().debug("----- [END] MfcSchedulerPeriod -----");
                    }
                }
                getLogger().debug("----- [END] After Sync :" + mfcScheduler);
            }
        }
    }

    /**
     * 
     * @param sce
     */
    public void contextInitialized(ServletContextEvent sce) {
        started = true;
        try {
            Class.forName("bdsm.scheduler.PropertyPersister");
        }
        catch (ClassNotFoundException cnfe) {
            this.getLogger().error(cnfe, cnfe);
        }
        
        getLogger().info("----- [BEGIN] SchedulerListener -----");
        StatusDefinition.setQuotaSync(1);
        
        getLogger().info("----- [BEGIN] KTPSEQUENCE :" + StatusDefinition.getQuotaSync());
        setMfcPeriod(sce.getServletContext());
        createMFCScheduler();
        getLogger().info("----- [END] Scheduler Listener -------");
    }

    /**
     * 
     * @param sce
     */
    public void contextDestroyed(ServletContextEvent sce) {
        started = false;
        if (mfcScheduler != null) {
            mfcScheduler.terminate();
            mfcScheduler = null;
        }
        getLogger().info("----- [END  ] SchedulerListener -----");
    }

    /**
     * 
     * @param se
     */
    public void sessionCreated(HttpSessionEvent se) {
        setMfcPeriod(se.getSession().getServletContext());
        if (started && mfcScheduler != null) {
            synchronized(this) {
                if (mfcScheduler.isStale()) {
                    mfcScheduler = null;
                    createMFCScheduler();
                }
            }
        }
    }

    /**
     * 
     * @param se
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        setMfcPeriod(se.getSession().getServletContext());
        if (started && mfcScheduler != null) {
            synchronized(this) {
                if (mfcScheduler.isStale()) {
                    mfcScheduler = null;
                    createMFCScheduler();
                }
            }
        }
    }
    
    private Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    /**
     * @param mfcPeriod the mfcPeriod to set
     */
    private void setMfcPeriod(ServletContext sc) {
        String s = sc.getInitParameter("mfcSchedulerPeriod");
        this.mfcPeriod = 15;
        try { this.mfcPeriod = Integer.parseInt(s); } catch (NumberFormatException e) {}
        if (this.mfcPeriod==0) {
            if (mfcScheduler != null) {
                mfcScheduler.terminate();
                mfcScheduler = null;
            }
        } else if (mfcScheduler != null) {
            mfcScheduler.setTimeout(this.mfcPeriod);
        }
    }
}
