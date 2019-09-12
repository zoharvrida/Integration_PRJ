/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.FixTemplateMasterDao;
import bdsm.scheduler.exception.GenRptException;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixTemplateMasterPK;
import bdsm.util.EncryptionUtil;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.MailUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author bdsm
 */
public class XtractJobProcessor extends XtractProcessor {

    @Override
    public void execute(Map context) {
        this.context = context;
        this.session = HibernateUtil.getSession();

        this.fixQXtract = (FixQXtract) context.get(MapKey.model);

        this.fixSchedulerXtractDao = new FixSchedulerXtractDao(this.session);
        this.fixQXtractDao = new FixQXtractDao(this.session);
        this.fixSchedulerXtract = this.fixSchedulerXtractDao.get(this.fixQXtract.getIdScheduler());
        if ("I".equalsIgnoreCase(this.fixSchedulerXtract.getFlgStatus())) {
            getLogger().info("Scheduler Xtract InActive : " + this.fixSchedulerXtract.getFixSchedulerPK().getNamScheduler());
            this.fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            this.fixQXtract.setDtmProcess(SchedulerUtil.getTime());
            try {
                Transaction tx = session.beginTransaction();
                this.fixQXtractDao.update(this.fixQXtract);
                this.session.flush();
                tx.commit();
            } catch (Exception ee) {
                getLogger().error(ee, ee);
            }
            try {
                MailUtil.sentNewMessage(PropertyPersister.adminEmail, null, "[FIX Alert] Scheduler InActive", "Scheduler Xtract InActive : " + this.fixSchedulerXtract.getFixSchedulerPK().getNamScheduler(), null, this.session, ScheduleDefinition.emailOnly);
            } catch (Exception ex) {
                getLogger().error("Error sent email alert for Scheduler Xtract InActive");
                getLogger().error(ex, ex);
            }
            return;
        } else {
            getLogger().debug("FIXQXTRACT START");
        }
        this.fixTemplateMasterDao = new FixTemplateMasterDao(this.session);
        this.fixTemplateMasterPK = new FixTemplateMasterPK();
        this.fixTemplateMasterPK.setIdTemplate(this.fixSchedulerXtract.getFixSchedulerPK().getIdTemplate());
        this.fixTemplateMaster = this.fixTemplateMasterDao.get(this.fixTemplateMasterPK);

        this.context.put(MapKey.session, this.session);
        try {
            getLogger().debug("BEGIN CLASS :" + this.fixTemplateMaster.getJavaClass());
            if (this.fixTemplateMaster.getRptFileTemplate() != null) {
                //generate with jasper
                getLogger().info("Generate Report with Jasper");
                generateReport();
                getLogger().info("Generate Report with Jasper DONE");
            }

            if (this.fixTemplateMaster.getJavaClass() != null) {
                this.context.put(MapKey.fixQXtract, this.fixQXtract);
                this.context.put(MapKey.javaClass, this.fixTemplateMaster.getJavaClass());
                this.session.clear();
                executeClass();

                // enhance for General AutoReply parameter
                try {
                    if (this.fixQXtract.getReason() != null) {
                        List reasonSplit = SchedulerUtil.getParameter(this.fixQXtract.getReason(), "\\|");
                        if (reasonSplit.size() == 3) {
                            this.fixSchedulerXtract = this.fixSchedulerXtractDao.get(Integer.parseInt(reasonSplit.get(2).toString()));
                            getLogger().debug("XTRACT : " + this.fixSchedulerXtract.toString());
                        }
                    }
                } catch (Exception e) {
                    getLogger().debug("FAILED GET NEW SCHEDULER :" + e,e);
                }

            }
            getLogger().debug("XTRACT CONTEXT :" + context);

            encryptAttachment();

            if ("email".equalsIgnoreCase(this.fixSchedulerXtract.getTypDest())) {
                getLogger().info("Begin Send Email");
                sentMessages();
                getLogger().info("Send Email DONE");
            } else if ("sftp".equalsIgnoreCase(this.fixSchedulerXtract.getTypDest())) {
                getLogger().info("Begin SFTP Transfer");
                String path = null;
                try {
                    path = this.fixQXtract.getParam5();
                    if (!ScheduleDefinition.param5.equalsIgnoreCase(path)) {
                        sFTP(path);
                        getLogger().info("SFTP Transfer Done");
                    }
                } catch (Exception e) {
                    getLogger().info("NO path available");
                }
            }
            this.fixQXtract.setFlgProcess(StatusDefinition.DONE);
        } catch (Exception ex) {
            fixQXtract.setFlgProcess(StatusDefinition.ERROR);
            fixQXtract.setReason(ex.getMessage());
            getLogger().info(ex, ex);
        }
        getLogger().debug("Session status for update FixQXtract : " + this.session.isOpen());
        if (!this.session.isOpen()) {
            getLogger().debug("Session isClosed, re Generate Session");
            this.session = HibernateUtil.getSession();
            getLogger().debug("re Create fixQXtractDao");
            this.fixQXtractDao = new FixQXtractDao(this.session);
        }
        this.fixQXtract.setDtmFinish(SchedulerUtil.getTime());
        this.transaction = this.session.beginTransaction();
        this.fixQXtractDao.update(this.fixQXtract);
        this.transaction.commit();
        HibernateUtil.closeSession(this.session);
    }

    private void sentMessages() throws Exception {
        /*
         * Get Monitoring Email Address
         */
        //FixEmailAccessDao emailDao = new FixEmailAccessDao(this.session);

        /*
         * End
         */
        if (!this.session.isOpen()) {
            this.session = HibernateUtil.getSession();
        }
        MailUtil.sentNewMessage(getValue(this.fixSchedulerXtract.getEmailTo()),
                getValue(this.fixSchedulerXtract.getEmailCC()),
                getValue(this.fixSchedulerXtract.getEmailSubjectPattern()),
                getValue(this.fixSchedulerXtract.getEmailBody()),
                //getValue(this.fixSchedulerXtract.getEmailAttachment()),
                this.fixQXtract.getParam5(),
                this.session, this.fixSchedulerXtract.getFixSchedulerPK().getIdScheduler());
    }

    private void sFTP(String sourceFile) throws Exception {
        getLogger().info("Initiate SFTP Transfer");
        if (!this.session.isOpen()) {
            this.session = HibernateUtil.getSession();
        }
        getLogger().info("FILE TO SENT :" + sourceFile);
        FixLog fixLog = FileUtil.sftpToFCR(this.fixSchedulerXtract.getSftpIP(),
                this.fixSchedulerXtract.getSftpPort(),
                this.fixSchedulerXtract.getSftpUserId(),
                this.fixSchedulerXtract.getSftpPass(),
                this.fixSchedulerXtract.getSftpKeyPath(), sourceFile,
                this.fixSchedulerXtract.getSftpDestPath(), this.fixSchedulerXtract.getFixSchedulerPK().getIdScheduler());
        getLogger().info("Initiate SFTP Transfer Done, Begin Logging SFTP Transfer to FixLog");
        FixLogDao fixLogDao = new FixLogDao(this.session);
        this.transaction = this.session.beginTransaction();
        fixLog.getFixLogPK().setInboxId(this.fixQXtract.getParam6());
        fixLogDao.insert(fixLog);
        this.transaction.commit();
        getLogger().info("SFTP Save log to FixLog Done");
    }

    private void encryptAttachment() throws Exception {
        String param5 = getValue(this.fixSchedulerXtract.getEmailAttachment());
        getLogger().debug("PARAM5 :" + this.fixSchedulerXtract.getEmailAttachment());
        boolean fileComplete = false;

        if (StringUtils.isNotBlank(param5)) {
            List listParam5 = SchedulerUtil.getParameter(param5, ";");

            if (listParam5.size() == 1) {
                if ("Y".equals(this.fixSchedulerXtract.getFlgEncrypt()) && new File(filePath + param5).exists()) {
                    getLogger().info("Encrypt Attachment");
                    String newFile = FileUtil.encrypt(filePath + param5, this.fixSchedulerXtract.getModEncrypt(), this.fixSchedulerXtract.getKeyEncrypt());
                    this.fixQXtract.setParam5(newFile);
                    getLogger().info("Encrypt Attachment DONE");
                }
            } else if (listParam5.size() > 1) {
                String zipName = FileUtil.getDateTimeFormatedFileName(this.fixSchedulerXtract.getFilePattern());
                String newFile = null;
                Iterator<String> it = null;
                it = listParam5.iterator();

                while (it.hasNext()) {
                    String filename = it.next().toString();
                    getLogger().debug("FILE :" + filename);
                    if (new File(filename).exists()) {
                        fileComplete = true;
                        getLogger().debug("CHECK OK");
                    } else {
                        fileComplete = false;
                        getLogger().debug("CHECK NO");
                    }
                }

                if (fileComplete) {
                    if ("Y".equals(this.fixSchedulerXtract.getFlgEncrypt())) {
                        getLogger().info("Encrypt Attachment with Multiple File");
                        newFile = FileUtil.encrypt(param5, PropertyPersister.dirFixOut + zipName, this.fixSchedulerXtract.getModEncrypt(), this.fixSchedulerXtract.getKeyEncrypt());
                        this.fixQXtract.setParam5(newFile);
                        getLogger().info("FILEPARAM :" + newFile);
                        getLogger().info("Encrypt Attachment DONE");
                    } else {
                        getLogger().info("Join Attachment into Multiple File");
                        newFile = EncryptionUtil.ZIPcompress(param5, zipName, null);
                        this.fixQXtract.setParam5(newFile);
                        FileUtil.moveFile(newFile, PropertyPersister.dirFixOutOk, "");
                        getLogger().info("Encrypt Attachment DONE");
                    }
                }
            }
        }
        getLogger().debug("Attachment Compiled into :" + this.fixQXtract.getParam5());
    }

    private void generateReport() throws GenRptException {
        HashMap contextReport = new HashMap();
        contextReport.put(MapKey.reportId, this.fixTemplateMaster.getRptFileTemplate());
        contextReport.put(MapKey.reportFormat, this.fixSchedulerXtract.getFileFormat());
        contextReport.put(MapKey.reportFileName, getValue(this.fixSchedulerXtract.getEmailAttachment()));
        contextReport.put(MapKey.session, this.session);

        HashMap parameter = new HashMap();
        parameter.put(MapKey.param1, this.fixQXtract.getParam1());
        parameter.put(MapKey.param2, this.fixQXtract.getParam2());
        parameter.put(MapKey.param3, this.fixQXtract.getParam3());
        parameter.put(MapKey.param4, this.fixQXtract.getParam4());
        parameter.put(MapKey.param5, this.fixQXtract.getParam5());
        parameter.put(MapKey.param6, this.fixQXtract.getParam6());

        contextReport.put(MapKey.reportParam, parameter);
        contextReport.put(MapKey.templateName, this.fixSchedulerXtract.getFilePattern());
        JasperGenRpt jasperGenRpt = new JasperGenRpt(contextReport);
        jasperGenRpt.generateReport();
    }
}
