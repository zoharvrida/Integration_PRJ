/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.util;

import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.BaBankCldrDao;
import bdsm.scheduler.dao.PmNetworkCldrDao;
import bdsm.scheduler.dao.mappingScheduleDao;
import bdsm.scheduler.model.*;
import bdsm.util.DefinitionConstant;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.ParameterDao;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author bdsm
 */
public class SchedulerUtil {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SchedulerUtil.class);

    /**
     * 
     * @return
     */
    public static Timestamp getTime() {
        Calendar calendar = Calendar.getInstance();
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 
     * @param format
     * @return
     * @throws ParseException
     */
    public static String getDate(String format) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Timestamp(calendar.getTime().getTime()));
    }

    /**
     * 
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 
     * @param props
     * @param delimiter
     * @return
     */
    public static List getParameter(String props, String delimiter){
        List StringParameter = new ArrayList();
        StringTokenizer token = new StringTokenizer(props.toString(),delimiter);
        
        while(token.hasMoreTokens()){
            StringParameter.add(token.nextToken());
        }
        return StringParameter;
    }

    /**
     * 
     * @param Params
     * @param pattern
     * @param session
     * @return
     */
    public static boolean timerUpdate(Integer Params, String pattern, Session session) {
        String GET_TIMER = "pk_bdsm_sms_trans.timerStart(:timer) ";
        String POST_TIMER = "pk_bdsm_sms_trans.timerPost(:timer) ";
        
        String param1 = pattern;
        StringBuilder sql;
        sql = new StringBuilder("{ CALL ");
        // From Scheduler
        if (Params.equals(1)) {
            sql.append(GET_TIMER);
        } else {
            sql.append(POST_TIMER);
        }
        sql.append("} ");
        logger.info(sql);
        try {
            //this.getLogger().info("STARTING SET SCHTIMER ");
            Query query = session.createSQLQuery(sql.toString());
            query.setString("timer", param1);
            
            query.executeUpdate();
            return true;
        } catch (HibernateException he) {
            logger.info("HIBERNATE :" + he, he);
            return false;
        }
    }

     /**
      * 
      * @param timeused
      * @param pattern
      * @param session
      * @param pointer
      * @return
      */
     public static boolean javaBasedTimerUpdate(String timeused, String pattern, Session session, String pointer) {
        FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
        mappingScheduleDao timerDAO = new mappingScheduleDao(session);
        Timestamp FCRtime = fcrDao.getFCRtime();

        if ("PRE".equalsIgnoreCase(pointer)) {
            try {
                MappingSchedule timer = timerDAO.get(pattern, "A");

                if (timer.getDateTo() != null) {
                    if ("A".equalsIgnoreCase(timer.getStatus())) {
                        timer.setDateFrom(timer.getDateTo());
                        timer.setStatus("I");
                    }
                    if ("A".equalsIgnoreCase(timeused)) {
                        timer.setDateTo(getTime());
                    } else {
                        timer.setDateTo(FCRtime);
                    }
                    timerDAO.update(timer);
                }
                // cek date to apakah null
            } catch (Exception exception) {
                logger.info("SCHTIMER POST faileD :" + exception);
                return false;
            }
        } else {
            try {
                MappingSchedule timer = timerDAO.get(pattern, "I");
                timer.setStatus("A");
                timerDAO.update(timer);
                // cek date to apakah null
            } catch (Exception exception) {
                logger.info("SCHTIMER POST faileD :" + exception);
                return false;
            }
        }
        return true;
    }

     /**
      * 
      * @param dateused
      * @param timeused
      * @param pattern
      * @param session
      * @return
      */
     public static boolean specificTimerUpdate(String dateused, String timeused, String pattern, Session session) {
        boolean flgProcess = false;

        FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
        Date FcrDate = fcrDao.getFCRtime();

        if (ScheduleDefinition.BaBankMast.equals(dateused) || ScheduleDefinition.BIBankMastIncoming.equals(dateused) || ScheduleDefinition.BIBankMastOutgoing.equals(dateused)) {

            DateFormat year = new SimpleDateFormat(StatusDefinition.yearCalPattern);
            DateFormat month = new SimpleDateFormat(StatusDefinition.monthCalPattern);
            DateFormat day = new SimpleDateFormat(StatusDefinition.dateCalPattern);

            Integer workDay = 0;
            FixCalendarPK pkID = new FixCalendarPK();
            if (ScheduleDefinition.SystemCalendar.equalsIgnoreCase(timeused)) {
                pkID.setMonth(Integer.parseInt(month.format((Date) FcrDate)));
                pkID.setYEAR(Integer.parseInt(year.format((Date) FcrDate)));
                workDay = Integer.parseInt(day.format((Date) FcrDate));
            } else if (ScheduleDefinition.RealCalendar.equalsIgnoreCase(timeused)) {
                pkID.setMonth(Integer.parseInt(month.format((Date) SchedulerUtil.getTime())));
                pkID.setYEAR(Integer.parseInt(year.format((Date) SchedulerUtil.getTime())));
                workDay = Integer.parseInt(day.format((Date) SchedulerUtil.getTime()));
            }
            pkID.setFlgType(StatusDefinition.AUTHORIZED);
            logger.info("TODAY is :" + workDay);
            try {
                if ((ScheduleDefinition.BaBankMast).equalsIgnoreCase(dateused)) {
                    // flexcube calendar
                    pkID.setModule_Name(ScheduleDefinition.BaBranch.toString());

                    BaBankCldrDao bDAO = new BaBankCldrDao(session);
                    BaBankCldr bCldr = bDAO.get(pkID);

                    if (StatusDefinition.ActiveDay.equals(bCldr.getWorkingDays().substring(workDay - 1, workDay))) {
                        flgProcess = true;
                    }

                } else if ((ScheduleDefinition.BIBankMastIncoming).equalsIgnoreCase(dateused)) {
                    // BI Calendar
                    pkID.setModule_Name(ScheduleDefinition.PmNetworkChnlI.toString());

                    PmNetworkCldrDao pDAO = new PmNetworkCldrDao(session);
                    PmNetworkCldr pmCldr = pDAO.get(pkID);

                    if (StatusDefinition.ActiveDay.equals(pmCldr.getMonthDAy().substring(workDay - 1, workDay))) {
                        flgProcess = true;
                    }

                } else if ((ScheduleDefinition.BIBankMastOutgoing).equalsIgnoreCase(dateused)) {
                    pkID.setModule_Name(ScheduleDefinition.PmNetworkChnlO.toString());

                    PmNetworkCldrDao pDAO = new PmNetworkCldrDao(session);
                    PmNetworkCldr pmCldr = pDAO.get(pkID);

                    if (StatusDefinition.ActiveDay.equals(pmCldr.getMonthDAy().substring(workDay - 1, workDay))) {
                        flgProcess = true;
                    }

                } else { // null
                    flgProcess = true;
                }
            } catch (Exception e) {
                logger.debug("SCHEDULER IMPORT TYPE" + e);
                flgProcess = true;
            }
        } 
        if(flgProcess)
        flgProcess = javaBasedTimerUpdate(timeused, pattern, session, "PRE");
        return flgProcess;
    }
    
     /**
      * 
      * @param ektpProfiler
      * @param xtract
      * @param idReport
      * @param paramDao
      * @return
      */
     public static String inquirer(Map ektpProfiler, FixSchedulerXtract xtract, Integer idReport, ParameterDao paramDao){
        String inQuirer = null;
        logger.debug("MAP PROFILER :" + ektpProfiler);
        Map profSpec = PropertyPersister.parseKeyAndValueToMap(ektpProfiler.get(xtract.getFilePattern()).toString().replaceAll(":", ";"));
        logger.debug("MAP PROFSPEC :" + profSpec);
        if (profSpec != null) {
            Map patternMap = PropertyPersister.parseKeyAndValueToMap(profSpec.get("rnD").toString().replaceAll("\\|", ";"));
            logger.debug("MAP PATTERN :" + patternMap);
            if (patternMap != null) {
                Map idMap = PropertyPersister.parseKeyAndValueToMap(patternMap.get(idReport.toString()).toString().replaceAll("<>", ";"));
                logger.debug("MAP ID :" + idMap);
                logger.debug("1 :" + xtract.getFixSchedulerPK().getIdTemplate() + " 2 :" + idMap.get("orderId") + " 3 :" + profSpec.get("fieldId") + " 4 :" + xtract.getFilePattern());
                if (idMap != null) {
                    if (idMap.get("orderId") != null) {
                        List<Map> logMap = paramDao.getFieldParameterbyTemplate(xtract.getFixSchedulerPK().getIdTemplate(),
                                Integer.parseInt(idMap.get("orderId").toString()),
                                Integer.parseInt(profSpec.get("fieldId").toString()),
                                xtract.getFilePattern());

                        logger.debug("MAP LOG :" + logMap);
                        if (!logMap.isEmpty()) {
                            if ("AUTO".equalsIgnoreCase(idMap.get("type").toString())) {
                                if ("REFLECT".equalsIgnoreCase(logMap.get(0).get(DefinitionConstant.formatData).toString())) {
                                    inQuirer = logMap.get(0).get(DefinitionConstant.defValParam).toString().
                                            replaceAll(logMap.get(0).get(DefinitionConstant.fieldName).toString(),
                                            logMap.get(0).get(DefinitionConstant.destTable).toString());
                                } else {
                                    inQuirer = logMap.get(0).get(DefinitionConstant.defValParam).toString();
                                }
                            }
                        }
                    } else {
                        inQuirer = "";
                    }
                } else {
                    inQuirer  = "";
                }
            }
        }
        return inQuirer;
    }
}
