package bdsm.scheduler.email;

import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.GeneralARDao;
import bdsm.util.SchedulerUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.WebCredentials;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class MailServiceInstance {

    private static ExchangeService service = null;
    private static final Logger logger = Logger.getLogger(MailServiceInstance.class);
    private static Map servMap = new HashMap<String, Object>();
    private static Session session = null;
    //<editor-fold defaultstate="collapsed" desc="comment">
    //public static ExchangeService getService() {
    //    if (MailServiceInstance.service == null) {
    //        MailServiceInstance.service = createService();
    //    }
    //    return MailServiceInstance.service;
    //}
    //</editor-fold>

    public static ExchangeService getService() {
        //boolean flgCount = true;

        int counter = 3;
        boolean flgSent = false;
        //if (MailServiceInstance.service == null) {
        logger.info("mailService :" + MailServiceInstance.service);
        while (MailServiceInstance.service == null) {
            try {
                MailServiceInstance.setService(createService());
                if (MailServiceInstance.service == null) {
                    counter--;
                    if (counter == 0) {
                        // sent file to SFTP
                        counter = 3;
                        flgSent = true;
                        if (servMap != null);
                        servMap.put("STATE", "ERROR");
                        stampLog(servMap);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException interruptedException) {
                        logger.info("SLEEP Interupted :" + interruptedException, interruptedException);
                    }
                } else {
                    //flgCount = false;
                    if (flgSent) {
                        // sent file Logger
                        if (servMap != null);
                        servMap.put("STATE", "OK");
                        stampLog(servMap);
                    }

                }
            } catch (Exception e) {
                logger.info("MAIL Exception :" + e, e);

            }
            // } else {

            //}  
        }
        return MailServiceInstance.service;
    }

    private static synchronized ExchangeService createService() {
        ExchangeService srv = null;
        logger.debug("Initiating Mail Service Instance");
        logger.debug("URL : " + PropertyPersister.url);
        logger.debug("username : " + PropertyPersister.username);
        logger.debug("password : " + PropertyPersister.password);
        List urls = new ArrayList();
        List userList = SchedulerUtil.getParameter(PropertyPersister.url, ";");
        logger.debug("LIST :" + userList);
        for (Object user : userList) {
            ExchangeCredentials credentials = new WebCredentials(PropertyPersister.username, PropertyPersister.password);
            try {
                srv = new ExchangeService(ExchangeVersion.Exchange2007_SP1, TimeZone.getTimeZone("GMT+07:00"));
                //srv = new ExchangeService(ExchangeVersion.Exchange2010_SP2, TimeZone.getTimeZone("GMT+07:00"));
                srv.setPreAuthenticate(true);
                srv.setCredentials(credentials);
                srv.setUrl(new URI(user.toString()));
                
                logger.debug("Autoreply url: " + user.toString());
                break;
            } catch (URISyntaxException ex) {
                logger.error(ex, ex);
                srv = null;
                return srv;
            } catch (Exception ex) {
                logger.error(ex, ex);
                srv = null;
                return srv;
            }
        }
        logger.debug("Initiate Mail Service Instance Success");
        return srv;
    }

    /**
     * @param aService the service to set
     */
    public static void setService(ExchangeService aService) {
        service = aService;
    }

    public static void stampLog(Map logString) {
        //GeneralARDao arDao = new GeneralARDao(session);
        //arDao.putLog(logString);
        if (logString != null) {
            logger.error("STATE :" + logString.get("STATE") + " PROFILE :" + logString.get("PROFILE") + "\n PROCESS STEP :" + logString.get("PROCESS_TYPE") + "\n FILENAME :" + logString.get("FILENAME"));
        }
    }

    /**
     * @param aServMap the servMap to set
     */
    public static void setServMap(Map aServMap) {
        servMap = aServMap;
    }

    /**
     * @param aSession the session to set
     */
    public static void setSession(Session aSession) {
        session = aSession;
    }
}
