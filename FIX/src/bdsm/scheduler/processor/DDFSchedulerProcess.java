/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.dao.CiChUfCASADetailsDao;
import bdsm.util.SchedulerUtil;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public class DDFSchedulerProcess extends BaseProcessor {

    /**
     * 
     * @param context
     */
    public DDFSchedulerProcess(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        getLogger().debug("Begin Read ACCOUNT FOR CLOSING");
                    Integer runMonitoringStatus = 0;
        try {
            CiChUfCASADetailsDao detailDao = new CiChUfCASADetailsDao(session);

            getLogger().info("Finish listing all batch to Monitor");
            String DOB_Branch = null;
            String codXf = null;
            String fileType = null;
            String pattern = null;
            String fileName = null;
            String makerID = null;
            String days = null;
            Integer closingScheduler = 0;

            try {
                DOB_Branch = PropertyPersister.branchCode;
                codXf = PropertyPersister.codXf;
                fileType = PropertyPersister.fileType;
                pattern = PropertyPersister.pattern;
                fileName = PropertyPersister.fileName;
                makerID = PropertyPersister.makerID;
                days = PropertyPersister.days;
                closingScheduler = ScheduleDefinition.closingReport;
            } catch (Exception e) {
                getLogger().debug("EXCEPTION :" + e);
                getLogger().info("Couldn't Get Parameter");
            }

            String B_code = String.valueOf(DOB_Branch);
            getLogger().debug("DOB BRANCH :" + DOB_Branch);
            getLogger().debug("cod XF :" + codXf);
            getLogger().debug("fileType :" + fileType);
            getLogger().debug("patern:" + pattern);
            getLogger().debug("file Name :" + fileName);
            getLogger().debug("makerID :" + makerID);
            getLogger().debug("Days :" + days);

            String ID_Batch = null;
            StringBuilder name = new StringBuilder();
            String timeSTAMP = ((SchedulerUtil.getTime().toString().replace("-", "").replace(":", "").replace(" ", "").replace(".", "")));
            name.append(fileName).append(timeSTAMP).append(".txt");

            ID_Batch = SchedulerUtil.generateUUID();

            runMonitoringStatus = detailDao.runMonitoring(ID_Batch,
                    B_code,
                    codXf,
                    fileType,
                    pattern,
                    name.toString(),
                    closingScheduler, makerID, days);
            //TmpGefuRespons gefuReg = new TmpGefuRespons();
            //bdsm.scheduler.model.TmpGefuResponsPK pk = new bdsm.scheduler.model.TmpGefuResponsPK();
            getLogger().debug("monitor CASA :" + runMonitoringStatus);

        } catch (Exception e) {
            getLogger().debug("SCHEDULER PERIOD FAILED : " + e);
            getLogger().error(e, e);
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                Logger.getLogger(DDFSchedulerProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getLogger().debug("END INSERT Gefu Respond");
        if (runMonitoringStatus != 0){
            return true;
        } else {
            return false;
        }
    }
}
