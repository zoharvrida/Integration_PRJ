/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.exception;

import org.apache.log4j.Logger;
/**
 *
 * @author bdsm
 */
public class UncaughtExceptions implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.getRootLogger().info("THREAD EXCEPTION");
        Logger logger = Logger.getLogger(getClass().getName());
        logger.error("Error Occured in "+t.getName());
        logger.error(e.getMessage());
    }

}
