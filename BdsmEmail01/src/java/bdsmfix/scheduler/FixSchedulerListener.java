/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmfix.scheduler;


import bdsm.scheduler.BDSM_EOD_BOD;
import bdsm.scheduler.FolderWatcher2;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.email.FixExport;
import bdsm.scheduler.email.FixImportMail;
import bdsm.scheduler.util.BdsmLogger;
import bdsm.scheduler.util.MailUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.oracle.DateUtility;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;


/**
 *
 * @author USER
 */
public class FixSchedulerListener implements ServletContextListener {

    private static FixImportMail fixImportMail = null;
    private static FixExport fixExport = null;
    private static FolderWatcher2 folderWatcher2 = null;
    private static BDSM_EOD_BOD bdsmEodBod = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.getLogger().info("----- [BEGIN] FixSchedulerListener -----");
        
        try {
                getLogger().debug("EMAIL :" + System.getenv());
            } catch (Exception e) {
                getLogger().debug("ENVIRONMENT VARIABLE DOESN'T EXIST : EMAIL");
            }
        int threadcount = PropertyPersister.maxProcess;
        this.notifyStateOfModule(sce, 1);

        fixImportMail = new FixImportMail(PropertyPersister.mailTimeout, PropertyPersister.mailSleep, PropertyPersister.mailSleep, threadcount/3);
        this.getLogger().info("----- Start Import Mail  -----");
        fixImportMail.start();

        /*
        fixExport = new FixExport(PropertyPersister.mailTimeout, PropertyPersister.mailSleep, PropertyPersister.mailSleep, threadcount);
        this.getLogger().info("----- Start Export Job FIX -----");
        fixExport.start();
        */

        /*
        folderWatcher2 = new FolderWatcher2();
        this.getLogger().info("----- Start Watching Folder -----");
        folderWatcher2.startWatcher();
        */

        /*
        bdsmEodBod = new BDSM_EOD_BOD(PropertyPersister.mailTimeout, PropertyPersister.mailSleep);
        this.getLogger().info("----- Start Eod Job Watcher -----");
        bdsmEodBod.start();
        */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (fixImportMail != null) {
            getLogger().info("----- Terminate Import Mail -----");
            fixImportMail.terminate(true);
        }
        if (fixExport != null) {
            getLogger().info("----- Terminate Export Job -----");
            fixExport.terminate(true);
        }
        if (folderWatcher2 != null) {
            getLogger().info("----- Terminate Watching Folder -----");
            folderWatcher2.stopWatcher(true);
        }
        if (bdsmEodBod != null) {
            this.getLogger().info("----- Terminate Eod Job Watcher -----");
            bdsmEodBod.terminate(true);
        }
        
        this.notifyStateOfModule(sce, 2);
        this.getLogger().info("----- [END] FixSchedulerListener -----");
    }


    protected void notifyStateOfModule(ServletContextEvent sce, int type) {
        Session session = HibernateUtil.getSession();
        try {
            MailUtil.sentNewMessage(
                PropertyPersister.adminEmail, 
                null, 
                "BDSM Email (" + java.net.Inet4Address.getLocalHost().getHostName() + ") is " + (type==1? "starting": "stopped") + " at " + DateUtility.DATE_TIME_FORMAT_DDMMMYYYY.format(new java.util.Date()),
                "as subject",
                null, 
                session, 
                0);
        }
        catch (Exception ex) {
            this.getLogger().error(ex, ex);
        }
        finally {
            HibernateUtil.closeSession(session);
        }
    }

    private BdsmLogger getLogger() {
        return (BdsmLogger) BdsmLogger.getLogger(this.getClass().getName());
    }
}
