/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.ThreadLocalSessionContext;
import org.hibernate.impl.SessionImpl;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author user
 */
public class HibernateUtil {

    private static final Logger logger = Logger.getLogger(HibernateUtil.class);
    private static final Map sessionFactoryMap;
    private static SessionFactory sessFactory = null;
    private static final int[] errCodes = {600, 2393, 2399};

    static {
        try {
            String dsName = "";
            sessionFactoryMap = new HashMap<String, Object>();
            logger.debug("Config List Size : " + getConfigList().size());
            for (int i = 0; i < getConfigList().size(); i++) {
                logger.debug("Build Session Factory for config : " + getConfigList().get(i).toString());
                dsName = getConfigList().get(i).toString().replace("hibernate_", "").replace(".cfg.xml", "");
                sessFactory = new Configuration().configure(getConfigList().get(i).toString()).buildSessionFactory();
                sessionFactoryMap.put(dsName, sessFactory);
            }
        } catch (Throwable ex) {
            logger.fatal("Initial SessionFactory creation failed." + ex, ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Configuration getConfigOnTheFly(String dataSource) {
        logger.debug("Start Build Config On the fly, Datasource : " + dataSource);
        Configuration config = new Configuration();
        config.setProperty("hibernate.connection.datasource", dataSource);
        config.setProperty("hibernate.connection.release_mode", "on_close");
        config.setProperty("show_sql", "true");
        config.setProperty("format_sql", "true");
        config.setProperty("current_session_context_class", "thread");
        config.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        logger.debug("End Build Config On the fly");
        return config;
    }

    private static List getConfigList() {
        List retval = new ArrayList();
        String[] ext = new String[1];
        String configPath = HibernateUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("lib/Bdsm_Util.jar", "classes/");
        ext[0] = "cfg.xml";
        File tmpFile = null;
        Iterator<File> iter = FileUtils.iterateFiles(new File(configPath), ext, false);
        while (iter.hasNext()) {
            tmpFile = iter.next();
            retval.add(tmpFile.getName());
        }
        return retval;
    }

    private static void testSession(Session session) throws SQLException {
        getDBDateTime(session);
    }
    
    public static Date getDBDateTime(Session session) throws SQLException {
        Connection con = ((SessionImpl) session).connection();
        CallableStatement stmt = con.prepareCall("SELECT SYSTIMESTAMP FROM dual");
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next())
            return rs.getDate(1);
        else
            return null;
    }

    public static SessionFactory getSessionFactory() {
        return (SessionFactory) sessionFactoryMap.get("hibernate");
    }

    public static SessionFactory getSessionFactory(String dataSource) {
        logger.debug("[START] Get Session Factory for Other Datasource");
        if ("".equalsIgnoreCase(dataSource) || dataSource == null) {
            return getSessionFactory();
        }
        //check if session factory not exists in the map
        if (sessionFactoryMap.get(dataSource) == null) {
            logger.debug("Other Data Source Not Exists");
            sessFactory = getConfigOnTheFly(dataSource).buildSessionFactory();
            sessionFactoryMap.put(dataSource, sessFactory);
        } else {
            logger.debug("Other Data Source Exists");
        }
        logger.debug("[END] Get Session Factory for Other Datasource");
        return (SessionFactory) sessionFactoryMap.get(dataSource);
    }

    public static Session getSession() {
        boolean isError = false;
        int errorCode = 0;
        Session session = null;
        do {
            try {
                session = getSessionFactory().openSession();
                ThreadLocalSessionContext.bind(session);
                testSession(session);
                logger.debug("Session Valid");
                isError = false;
            } catch (SQLException ex) {
                logger.error("Error Test Session");
                logger.error("Error Code :" + ex.getErrorCode());
                logger.error(ex, ex);
                isError = true;
                errorCode = ex.getErrorCode();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex1) {
                    logger.error("Sleep Waiting recreate Session fail");
                    logger.error(ex1, ex1);
                }
            }
        } while ((isError) && (Arrays.asList(errCodes).contains(errorCode)));

        return session;
    }

    public static Session getSession(String dataSource) {
        boolean isError = false;
        int errorCode = 0;
        Session session = null;
        do {
            try {
                session = getSessionFactory(dataSource).openSession();
                ThreadLocalSessionContext.bind(session);
                testSession(session);
                logger.debug("Session Valid");
                isError = false;
            } catch (SQLException ex) {
                logger.error("Error Test Session");
                logger.error("Error Code :" + ex.getErrorCode());
                logger.error(ex, ex);
                isError = true;
                errorCode = ex.getErrorCode();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex1) {
                    logger.error("Sleep Waiting recreate Session fail");
                    logger.error(ex1, ex1);
                }
            }
        } while ((isError) && (Arrays.asList(errCodes).contains(errorCode)));

        return session;
    }

    public static void closeSession(Session session) {
        if (session != null) {
            ThreadLocalSessionContext.unbind(getSessionFactory());
            session.close();
        }
    }

    public static void closeSession(Session session, String dataSource) {
        if (session != null) {
            ThreadLocalSessionContext.unbind(getSessionFactory(dataSource));
            session.close();
        }
    }

    public static void evictObjectFromPersistenceContext(Object obj, Session hSession) {
        if (hSession.contains(obj)) {
            hSession.evict(obj);
        }
    }
}
