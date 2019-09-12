/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.rpt.dao.FixReportReqMasterDao;
import bdsm.rpt.model.FixReportReqMaster;
import bdsm.rpt.model.FixReportReqMasterPK;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.FixTemplateMasterDao;
import bdsm.scheduler.exception.GenRptException;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.FixTemplateMaster;
import bdsm.scheduler.model.FixTemplateMasterPK;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.DirectoryUtil;
import bdsm.util.EncryptionUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author NCBS
 */
public class BdsmReportWorker extends BaseProcessor {

    private static final String reportDir = PropertyPersister.dirFixOut;
    private static final String logFixDir = PropertyPersister.dirLogFix;
    private static final String logAppDir = PropertyPersister.dirLogApp;
    private static final String logWebDir = PropertyPersister.dirLogWeb;
    private static final String reportDirTemp = PropertyPersister.reportDirTemp;
    private String param2;
    private String[] param1;
    private String realParam1;
    private String oldEncrypt = null;
    // hidden param
    private String idUser;
    private String cdBranch;
    private String idTemplate;
    private String dtProcess;
    private String namUser;
    private String namTemplate;
    private String reportType;

    /**
     * 
     * @param context
     */
    public BdsmReportWorker(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        getLogger().info("Start BDSM Report Worker");
        //getLogger().info("FIXLOG : " + logDir);
        getLogger().info("Main Param : " + context.get(MapKey.param1).toString());
        try {
            getLogger().info("Param 2    : " + context.get(MapKey.param2).toString());
            param2 = context.get(MapKey.param2).toString();
        } catch (Exception e) {
            getLogger().info("EXCEPTION : " + e);
            param2 = "null";
        }
        param1 = context.get(MapKey.param1).toString().split("\\~");

        getLogger().info("Get Main Param");
        idUser = param1[0];
        getLogger().info(" ID User requester : " + idUser);
        //String user = param1[1];
        //getLogger().info("User Requestor Report : " + user);
        int idScheduler = Integer.parseInt(param1[1]);
        getLogger().info("Id Scheduler Report : " + idScheduler);
        String filenameResult = param1[2];
        getLogger().info("Filename Result : " + filenameResult);
        String idReport = param1[3];
        getLogger().info("Id Report : " + idReport);
        setReportType(param1[4]);
        getLogger().info("Report Type : " + reportType);
        cdBranch = param1[5];
        getLogger().info("Branch Code : " + cdBranch);
        namUser = param1[6];
        getLogger().info("Login Name : " + namUser);
        dtProcess = param1[7];
        getLogger().info("Tgl Report : " + dtProcess);
        idTemplate = param1[8];
        getLogger().info("Id Template : " + idTemplate);
        namTemplate = param1[9];
        getLogger().info("Name Template : " + namTemplate);
        realParam1 = param1[10];
        getLogger().info("Report Param 1 : " + realParam1);

        FixSchedulerXtract scheduler = new FixSchedulerXtract();
        FixTemplateMaster template = new FixTemplateMaster();
        FixTemplateMasterPK templatePK = new FixTemplateMasterPK();
        FixReportReqMasterPK modelRequestPK = new FixReportReqMasterPK();
        FixReportReqMaster modelRequest = new FixReportReqMaster();

        FixTemplateMasterDao templateDao = new FixTemplateMasterDao(session);
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);
        FixReportReqMasterDao reportReqDao = new FixReportReqMasterDao(session);

        modelRequestPK.setIdBatch(context.get(MapKey.param6).toString());
        //modelReportPK.setIdReport(idReport);
        getLogger().info("Report batch : " + context.get(MapKey.param6).toString());
        scheduler = xtractDao.get(idScheduler);
        getLogger().info("SCHEDULER : " + scheduler.getFileFormat());
        //FixMasterReport masterReport = mDao.get(idReport);
        //getLogger().info("Remarks Report :" + masterReport.getRemarks());
        try {
            modelRequest = reportReqDao.get(modelRequestPK);
            getLogger().info("MODEL REQUEST : " + modelRequest);
        } catch (Exception e) {
            getLogger().info("ERROR : " + e);
        }


        getLogger().info("Create User Folder : " + reportDir + idUser + "/");
        File userReportDir = new File(reportDir + idUser + "/");
        if (!userReportDir.exists()) {
            userReportDir.mkdir();
        }
        File directoryZip = new File(logWebDir + "/" + realParam1);
        File newDirectory = new File(reportDir + idUser + "/" + realParam1);

        getLogger().info("REPORT TYPE : " + reportType);
        // Specific for log Download
        if (ScheduleDefinition.LOG.equalsIgnoreCase(reportType)) {

            if (realParam1.equalsIgnoreCase(ScheduleDefinition.App)) {
                directoryZip = new File(logAppDir + "/" + realParam1);
                //newDirectory = new File(reportDir + idUser + "/" + realParam1);
                newDirectory = new File(logAppDir + "/" + realParam1);
                //File tempFileReport = new File(logDir + "\\temp");
                getLogger().info("Begin Zipping Folder");

            } else if (realParam1.equalsIgnoreCase(ScheduleDefinition.Web)) {
                directoryZip = new File(logWebDir + "/" + realParam1);
                newDirectory = new File(logWebDir + "/" + realParam1);
                //File tempFileReport = new File(logDir + "\\temp");
                getLogger().info("Begin Zipping Folder");

            } else if (realParam1.equalsIgnoreCase(ScheduleDefinition.Fix)) {
                directoryZip = new File(logFixDir + "/" + realParam1);
                newDirectory = new File(logFixDir + "/" + realParam1);
                //File tempFileReport = new File(logDir + "\\temp");
                getLogger().info("Begin Zipping Folder");
            }
            try {
                getLogger().info("Create Directory");
                getLogger().info("ZIP directory : " + directoryZip);
                getLogger().info("new directory : " + newDirectory);
                getLogger().info("Format : " + scheduler.getFileFormat());
                DirectoryUtil.Begin(directoryZip, newDirectory, scheduler.getFileFormat());
            } catch (Exception e) {
                getLogger().error(e, e);
                modelRequest.setStatus("Error : " + e.toString());
                modelRequest.setDtmFinish(SchedulerUtil.getTime());
                reportReqDao.update(modelRequest);
            }

            //
            String fileZip = null;
            String timeSTAMP = ((SchedulerUtil.getTime().toString().replace("-", "").replace(":", "").replace(" ", "").replace(".", "")));
            String downloadDirectory = newDirectory.toString() + timeSTAMP + "." + scheduler.getFileFormat();
            if (!"null".equals(param2)) {
                getLogger().info("NO_ENCRYPTION:" + param2);

                oldEncrypt = EncryptionUtil.getAES(param2, "ZIP@@@@@@@@@@@@@", 2);
                getLogger().info("ENCRYPTION :" + oldEncrypt);
            }
            getLogger().info("Source        : " + newDirectory.toString() + "." + scheduler.getFileFormat().toLowerCase());
            getLogger().info("Destination   : " + newDirectory.toString() + timeSTAMP + "." + scheduler.getFileFormat());
            String Ecript = null;
            try {
                Ecript = EncryptionUtil.ZIPcompress(newDirectory.toString() + "." + scheduler.getFileFormat().toLowerCase(), newDirectory.toString() + timeSTAMP + "." + scheduler.getFileFormat(), oldEncrypt);
            } catch (Exception zipException) {
                getLogger().info("ERROR REASON   : " + zipException);
            }
            //System.out.println("Try Check for compression");                
            File downloadReport = new File(newDirectory.toString() + timeSTAMP + "." + scheduler.getFileFormat());
            FileUtil.delFile(newDirectory.toString() + "." + scheduler.getFileFormat().toLowerCase());
            //File downloadReport = userReportDir;
            getLogger().info("ENCRIPTION DONE : " + Ecript);

            //move to user folder report 
            modelRequest.setStatus("Ready To Download");
            modelRequest.setDtmFinish(SchedulerUtil.getTime());
            modelRequest.setFilePath(downloadReport.getPath());
            reportReqDao.update(modelRequest);
            getLog(idReport, scheduler.getFileFormat(), downloadReport.getName());
            return true;
        } else {
            boolean FlagErr = true;
            try {
                HashMap cont = new HashMap();
                //modelRequest = reportReqDao.get(modelRequestPK);
                getLogger().debug("GET ModelRequestPK :" + modelRequestPK);
                getLogger().debug("GET ModelRequest :" + modelRequest);
                getLogger().info("GET ID SCHEDULER :" + idScheduler);
                scheduler = xtractDao.get(idScheduler);
                templatePK.setIdTemplate(scheduler.getFixSchedulerPK().getIdTemplate());
                template = templateDao.get(templatePK);
                getLogger().info("GET PRIMARY templatePK :" + templatePK);
                getLogger().info("GET TEMPLATE PK : " + scheduler.getFixSchedulerPK().getIdTemplate());
                getLogger().info("file template : " + template.getRptFileTemplate());
                getLogger().info("file Extension : " + scheduler.getFileFormat());
                getLogger().info("Java Class : " + template.getJavaClass());
                getLogger().info("FILENAME : " + filenameResult + "." + scheduler.getFileFormat());
                cont = getLog(template.getRptFileTemplate(), scheduler.getFileFormat(), filenameResult + "." + scheduler.getFileFormat());
                if (template.getJavaClass() != null) {
                    Map classMap = (Map) cont.get(MapKey.reportParam);
                    classMap.put(MapKey.idScheduler, idScheduler);
                    classMap.put(MapKey.javaClass, template.getJavaClass());
                    classMap.put(MapKey.typeFix, StatusDefinition.XTRACT);
                    classMap.put(MapKey.reportId, idReport);
                    classMap.put(MapKey.reportFormat, cont.get(MapKey.reportFormat));
                    classMap.put(MapKey.reportFileName, cont.get(MapKey.reportFileName));
                    classMap.put(MapKey.session, this.session);
                    executeClass(classMap);
                } else {
                    generateReport(cont);
                }
                //create user folder

                File tempFileReport = new File(reportDir + filenameResult + "." + scheduler.getFileFormat());
                File downloadReport = new File(reportDir + idUser + "/" + filenameResult + "." + scheduler.getFileFormat());
                //File downloadReport = userReportDir;
                //move to user folder report
                getLogger().debug("move to user folder report");
                getLogger().debug("Src File : " + tempFileReport.getAbsolutePath());
                getLogger().debug("Dst File File : " + downloadReport.getAbsolutePath());
                getLogger().debug("FILE TEMP : " + tempFileReport.getName());
                getLogger().debug("FILE SRC : " + downloadReport.getName());
                try {
                FileUtils.moveFile(tempFileReport, downloadReport);
                } catch (IOException iOException) {
                    getLogger().info("FILE ALREADY EXIST!!" + iOException);
                    FileUtils.deleteQuietly(tempFileReport);
                    FlagErr = false;
                    modelRequest.setFilePath(downloadReport.getPath());
                }

                String filename = downloadReport.getName();
                if (!ScheduleDefinition.LOG.equalsIgnoreCase(reportType) && "Y".equals(scheduler.getFlgEncrypt()) && FlagErr) {
                    filename = encryptAttachment(downloadReport.getPath(), downloadReport.getName(), idScheduler);
                }
                downloadReport = new File(reportDir + idUser + "/" + filename);
                getLogger().info("update download link : " + downloadReport.getPath());
                //update download link
                getLogger().info("DOWNLOAD PATH :" + downloadReport);
                if (ScheduleDefinition.WebService.equalsIgnoreCase(reportType)) {
                    modelRequest.setStatus("Download");
                } else {
                    modelRequest.setStatus("Ready To Download");
                }
				
                modelRequest.setDtmFinish(SchedulerUtil.getTime());
                modelRequest.setFilePath(downloadReport.toString());
                reportReqDao.update(modelRequest);
            } catch (GenRptException ee) {
                getLogger().error(ee, ee);
                if(!FlagErr){
                    modelRequest.setStatus("Download");
                } else {
                    modelRequest.setStatus("Error");
                }
                
                modelRequest.setDtmFinish(SchedulerUtil.getTime());
                reportReqDao.update(modelRequest);
            }
            return true;
        }

    }

    private void generateReport(HashMap contextReport) throws GenRptException {
        //call jasper function
        getLogger().info("Call Jasper Generate Report");
        JasperGenRpt jasperGenRpt = new JasperGenRpt(contextReport);
        jasperGenRpt.generateReport();

    }

    private void executeClass(Map contextClass) throws Exception {
        Class[] paramString = new Class[1];
        paramString[0] = Map.class;
        String className = contextClass.get(MapKey.javaClass).toString();
        Class cls;
        cls = Class.forName(className);
        Constructor cons = cls.getConstructor(paramString);
        getLogger().info("Execute class : " + className);
        BaseProcessor baseProcessor = (BaseProcessor) cons.newInstance(contextClass);
        boolean result = baseProcessor.execute();

        this.tx = this.session.beginTransaction();

        getLogger().info("Finish class" + className + " execution. Result : " + result);

    }

    private HashMap getLog(String reportId, String reportFormat, String reportFileName) {
        getLogger().info("Start GetLog from path");
        HashMap contextReport = new HashMap();
        contextReport.put(MapKey.reportId, reportId);
        contextReport.put(MapKey.reportFormat, reportFormat);
        contextReport.put(MapKey.reportFileName, reportFileName);
        contextReport.put(MapKey.session, this.session);

        getLogger().info("Get FixQXtract from Request");
        FixQXtract fixQ = (FixQXtract) context.get(MapKey.model);

        HashMap parameter = new HashMap();
        getLogger().info("Hidden_Param idUser : " + idUser);
        parameter.put(MapKey.hdUserid, idUser);
        getLogger().info("Hidden_Param cdBranch : " + cdBranch);
        parameter.put(MapKey.hdcdBranch, cdBranch);
        getLogger().info("Hidden_Param dtProcess : " + dtProcess);
        parameter.put(MapKey.hddtProcess, dtProcess);
        getLogger().info("Hidden_Param idTemplate : " + idTemplate);
        parameter.put(MapKey.hdidTemplate, idTemplate);
        getLogger().info("Hidden_Param namUser : " + namUser);
        parameter.put(MapKey.hdnamUser, namUser);
        getLogger().info("Hidden_Param namTemplate : " + namTemplate);
        parameter.put(MapKey.hdnamTemplate, namTemplate);
        if (ScheduleDefinition.Email.equalsIgnoreCase(reportType)) {
            getLogger().info("Report Param 6 : " + realParam1);
            parameter.put(MapKey.param1, (String) context.get(MapKey.param6));
            getLogger().info("Report Param 1 : " + (String) context.get(MapKey.param6));
            parameter.put(MapKey.param6, realParam1);
        } else {
            getLogger().info("Report Param 1 : " + realParam1);
            parameter.put(MapKey.param1, realParam1);
            getLogger().info("Report Param 6 : " + (String) context.get(MapKey.param6));
            parameter.put(MapKey.param6, (String) context.get(MapKey.param6));
        }
        getLogger().info("Report Param 2 : " + (String) context.get(MapKey.param2));
        parameter.put(MapKey.param2, (String) context.get(MapKey.param2));
        getLogger().info("Report Param 3 : " + (String) context.get(MapKey.param3));
        parameter.put(MapKey.param3, (String) context.get(MapKey.param3));
        getLogger().info("Report Param 4 : " + (String) context.get(MapKey.param4));
        parameter.put(MapKey.param4, (String) context.get(MapKey.param4));
        getLogger().info("Report Param 5 : " + (String) context.get(MapKey.param5));
        parameter.put(MapKey.param5, (String) context.get(MapKey.param5));
        contextReport.put(MapKey.reportParam, parameter);
        getLogger().info("CONTEXT : " + contextReport);
        return contextReport;
    }

    private String encryptAttachment(String filePath, String namaFile, Integer idSched) throws Exception {
        getLogger().info("Encrypt Attachment");
        getLogger().info("SCHEDULER ID :" + idSched);
        if (!this.session.isOpen()) {
            this.session = HibernateUtil.getSession();
        }
        FixSchedulerXtractDao dao = new FixSchedulerXtractDao(this.session);
        FixSchedulerXtract fixSchedulerXtract = dao.get(idSched);

        getLogger().info("XTRACT :" + fixSchedulerXtract);
        getLogger().info("FilePath :" + filePath);
        getLogger().info("Name :" + namaFile);
        getLogger().info("DAO MOD :" + fixSchedulerXtract.getModEncrypt());
        getLogger().info("DAO ENCRIPT :" + fixSchedulerXtract.getKeyEncrypt());
        String newFile = FileUtil.encrypt(filePath, fixSchedulerXtract.getModEncrypt(), fixSchedulerXtract.getKeyEncrypt());
        getLogger().info("FILE NEW :" + newFile);
        //this.fixQXtract.setParam5(newFile);
        getLogger().info("Encrypt Attachment DONE");
        return newFile;
    }

    /**
     * @return the oldEncrypt
     */
    public String getOldEncrypt() {
        return oldEncrypt;
    }

    /**
     * @param oldEncrypt the oldEncrypt to set
     */
    public void setOldEncrypt(String oldEncrypt) {
        this.oldEncrypt = oldEncrypt;
    }

    /**
     * @return the namTemplate
     */
    public String getNamTemplate() {
        return namTemplate;
    }

    /**
     * @param namTemplate the namTemplate to set
     */
    public void setNamTemplate(String namTemplate) {
        this.namTemplate = namTemplate;
    }

    /**
     * @return the reportType
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
