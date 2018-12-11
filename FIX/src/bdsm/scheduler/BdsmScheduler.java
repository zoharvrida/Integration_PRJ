/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler;

import bdsm.scheduler.exception.UncaughtExceptions;
import bdsm.scheduler.util.BdsmLogger;
import bdsm.scheduler.util.MailUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.oracle.DateUtility;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author 00030663
 */
abstract public class BdsmScheduler extends Thread {

    private boolean terminated;
    private long sleep;
    private long btSleep;
    private boolean stale;
    private long timeout;
    private int backThreadCount;
    private List<BdsmBackThread> backThreads;
    private List<BdsmBackThread> assignedBackThreads;
    private List<BdsmBackThread> unassignedBackThreads;
    private static Object sharedLock = new Object();

    /**
     * 
     * @param timeout
     * @param sleep
     * @param btSleep
     * @param threadCount
     */
    public BdsmScheduler(long timeout, long sleep, long btSleep, int threadCount) {
        this.terminated = false;
        this.sleep = sleep;
        this.btSleep = btSleep;
        this.stale = false;
        this.timeout = timeout;
        this.backThreadCount = threadCount;
        this.setUncaughtExceptionHandler(new UncaughtExceptions());
    }

    private void initialize() {
        backThreads = new ArrayList<BdsmBackThread>();
        assignedBackThreads = new ArrayList<BdsmBackThread>();
        unassignedBackThreads = new ArrayList<BdsmBackThread>();
        for (int i = 0; i < backThreadCount; i++) {
            BdsmBackThread bt = new BdsmBackThread(this, btSleep);
            backThreads.add(bt);
            bt.start();
            unassignedBackThreads.add(bt);
        }
    }

    private void cleanup() {
        for (int i = 0; i < backThreadCount; i++) {
            BdsmBackThread bt = backThreads.get(i);
            bt.terminate();
            try {
                bt.join();
            } catch (InterruptedException e) {
                getLogger().fatal("execute", e);
            }
        }
        backThreads.clear();
        assignedBackThreads.clear();
        unassignedBackThreads.clear();
    }

    @Override
    public final void run() {
        try {
            getLogger().info("RUN BACKTREAD ");
            initialize();
            long start = Calendar.getInstance().getTimeInMillis();
            long end, elapse;
            while (!terminated) {
                try {
                    end = Calendar.getInstance().getTimeInMillis();
                    elapse = end - start;
                    if (elapse >= timeout) {
                        execute();
                        start = end;
                    }
                } catch (Throwable e) {
                    getLogger().fatal("execute", e);
                }

                try {
                    if (sleep > 0) {
                        Thread.sleep(sleep);
                    }
                } catch (InterruptedException e) {
                    getLogger().fatal("sleep", e);
                }
            }
        } catch (Throwable e) {
            stale = true;
            getLogger().fatal("Stale", e);
        } finally {
            cleanup();
        }
    }

    /**
     * 
     * @return
     */
    protected final BdsmLogger getLogger() {
        return (BdsmLogger) BdsmLogger.getLogger(this.getClass().getName());
    }

    /**
     * 
     */
    abstract protected void execute();

    /**
     * 
     */
    public final void terminate() {
        terminated = true;
    }

    /**
     * 
     * @param context
     * @param worker
     * @return
     */
    protected synchronized final BdsmBackThread assign(Map context, IBdsmWorker worker) {
        getLogger().debug("[ASSIGN] UNASSIGNED THREAD :" + unassignedBackThreads.size());
        getLogger().debug("[ASSIGN] ASSIGNED THREAD :" + assignedBackThreads.size());

        Map contexts = context;
        //synchronized (sharedLock) {
            if (unassignedBackThreads.size() > 0) {
                BdsmBackThread bt = null;
                bt = unassignedBackThreads.remove(0);
                getLogger().info("BACKTHREAD :" + bt.getName());
                bt.assign(context, worker);
                assignedBackThreads.add(bt);
                return bt;
            }
            return null;
        //}
    }

    /**
     * 
     * @param bt
     */
    public synchronized void unassigned(BdsmBackThread bt) {
        //synchronized (sharedLock) {
            if (assignedBackThreads.remove(bt)) {
                unassignedBackThreads.add(bt);
            }
            getLogger().debug("[UN-ASSIGN] UNASSIGNED THREAD :" + unassignedBackThreads.size());
            getLogger().debug("[UN-ASSIGN] ASSIGNED THREAD :" + assignedBackThreads.size());
        //}
    }
}