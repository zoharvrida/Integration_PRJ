/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.FixTemplateMasterPK;
import bdsm.scheduler.util.SchedulerUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v00019722
 */
public abstract class SchTimerSync extends BaseProcessor {

    /**
     * 
     * @param context
     */
    public SchTimerSync(Map context) {
        super(context);
    }
    /**
     * 
     */
    protected boolean runningFlag = true;
    /**
     * 
     * @param context
     * @return
     * @throws Exception
     */
    protected abstract boolean doExecute(Map context) throws Exception;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        getLogger().info("<<<<< ---SET PARAMETER SCHTIMER--- >>>>>>");
        getLogger().info("ID SCHEDULER : " + context.get(MapKey.param2).toString());
        Integer idschedXtract = Integer.parseInt(context.get(MapKey.param2).toString());

        boolean superflag = true;
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);

        FixTemplateMasterPK templatePK = new FixTemplateMasterPK();
        FixSchedulerXtract scheduler = xtractDao.get(idschedXtract);
        templatePK.setIdTemplate(idschedXtract);
        String strFlag = null;
        List flgCalendar = new ArrayList();
        try {
            strFlag = scheduler.getFlgCalendar();
			getLogger().info("flag CALENDAR :" + strFlag);
			if(strFlag == null){
				strFlag = "";
			}
        } catch (Exception e) {
            strFlag = "";
        }
        if (!"".equalsIgnoreCase(strFlag)) {
            flgCalendar = SchedulerUtil.getParameter(scheduler.getFlgCalendar(), ";");
        }

        String schtimerFlag = scheduler.getSchTimerProfile();
        if ("Y".equalsIgnoreCase(schtimerFlag)) {
            String pattern = scheduler.getFilePattern();
            try {
                if (flgCalendar.isEmpty()) {
                    bdsm.scheduler.util.SchedulerUtil.timerUpdate(1, pattern, session);
                } else {
                    superflag = bdsm.scheduler.util.SchedulerUtil.specificTimerUpdate(flgCalendar.get(0).toString(), flgCalendar.get(1).toString(), pattern, session);
                    if (superflag) {
                        tx.commit();
                        this.tx = session.beginTransaction();
                    }
                }
            } catch (Exception e) {
                getLogger().info("EXCEPTION :" + e, e);
                getLogger().debug("SCHTIMER PRE PROCESS FAILED: " + pattern);
                return false;
            }
        }
        if (superflag) {
        try{
                if (doExecute(this.context)) {
        if ("Y".equalsIgnoreCase(schtimerFlag)) {
            String pattern = scheduler.getFilePattern();
            try {
                            if (flgCalendar.isEmpty() && runningFlag) {
                    bdsm.scheduler.util.SchedulerUtil.timerUpdate(2, pattern, session);
                } else {
                                if (superflag && runningFlag) {
                        bdsm.scheduler.util.SchedulerUtil.javaBasedTimerUpdate(flgCalendar.get(1).toString(), pattern, session, "POST");
                    }
                }
            } catch (Exception e) {
                getLogger().info("EXCEPTION :" + e, e);
                getLogger().debug("SCHTIMER POST PROCESS FAILED: " + pattern);
                return false;
            }
        }
                } else {
                    return false;
                }
            } catch (Exception e) {
                getLogger().debug("SCHEDULER PROCESS FAILED :" + scheduler.getFixSchedulerPK().getNamScheduler());
                getLogger().info("CLASS EXCEPTION :" + e, e);
                return false;
            }
        }
        return true;
    }
}
