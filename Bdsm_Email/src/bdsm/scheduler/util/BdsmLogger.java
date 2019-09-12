/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.util;

import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.util.EncryptionUtil;
import bdsm.util.HibernateUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.*;
import org.hibernate.Session;
import sun.misc.BASE64Encoder;

/**
 *
 * @author v00019237
 */
public class BdsmLogger {

    private static java.util.Map<String, BdsmLogger> instances = new java.util.HashMap<String, BdsmLogger>(0);
    private Logger logger;

    public static BdsmLogger getLogger(String st) {
        if (instances.get(st) == null) {
            BdsmLogger bdsmLogger = new BdsmLogger();
            bdsmLogger.logger = Logger.getLogger(st);
            instances.put(st, bdsmLogger);
        } 
        return instances.get(st);
    }

    public static BdsmLogger getLogger(Class cl) {
        return getLogger(cl.getName());
    }

    public void error(Object message) {
        this.logger.error(message);
        //sendMailError(message);
    }

    public void error(Object message, Throwable t) {
        this.logger.error(message, t);
        //sendMailError(message);
    }

    public void fatal(Object message) {
        this.logger.fatal(message);
        //sendMailError(message);
    }

    public void fatal(Object message, Throwable t) {
        this.logger.fatal(message, t);
        //sendMailError(message);
    }

    public void trace(Object message) {
        this.logger.trace(message);
    }

    public void trace(Object message, Throwable t) {
        this.logger.trace(message, t);
    }

    public void debug(Object message) {
        this.logger.debug(message);
    }

    public void debug(Object message, Throwable t) {
        this.logger.debug(message, t);
    }

    public void info(Object message) {
        this.logger.info(message);
    }

    public void info(Object message, Throwable t) {
        this.logger.info(message, t);
    }

    private void sendMailError(Object err) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            MailUtil.sentNewMessage(PropertyPersister.adminEmail, "", "[BDSM] Error Allert", getBodyEmailErrorMessage(err), null, session, ScheduleDefinition.emailOnly);
        } catch (Exception ex) {
            //Write to critical Error File
            PrintStream p = null;
            try {
                p = new PrintStream(new File(PropertyPersister.dirFixOut + "Critical.err"));
                ex.printStackTrace(p);
            } catch (FileNotFoundException ex1) {
                org.apache.log4j.Logger.getLogger(BdsmLogger.class.getName()).error(ex1, ex1);
            }
        } finally {
            HibernateUtil.closeSession(session);
        }
    }

    private String getBodyEmailErrorMessage(Object err) throws Exception {
        String errFile = PropertyPersister.dirFixOut + "BdsmLogger.err";
        String errMsg = "";
        String bodyEmail = "";
        Exception ex = (Exception) err;
        PrintStream p = new PrintStream(new File(errFile));
        ex.printStackTrace(p);
        EncryptionUtil.ZIPcompress(errFile, errFile + ".zip", null);

        InputStream is = new FileInputStream(errFile + ".zip");
        errMsg = new BASE64Encoder().encode(IOUtils.toByteArray(is));
        FileUtils.deleteQuietly(new File(errFile));
        FileUtils.deleteQuietly(new File(errFile + ".zip"));
        bodyEmail = "BDSM Error Alert<br/></br/>--Begin Message--<br/>" + errMsg + "<br/>--End Message--";
        return bodyEmail;
    }
}
