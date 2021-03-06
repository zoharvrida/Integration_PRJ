package bdsm.scheduler.processor;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.TmpMpClassDtlsDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.TmpMpClassDtls;
import bdsm.scheduler.model.TmpMpPK;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.excel.XLSReader;
import bdsm.util.excel.XLSXReader;
import bdsmhost.dao.FcrBaBankMastDao;


/**
 *
 * @author v00020841
 */
public class MpClassDtlsWorker extends BaseProcessor {

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    private String emailApproval = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your approval is required for the attached file to be processed further. <br/>"
            + "<br/>"
            + "Please reply this email with:<br/>"
            + "<b>Ok</b>, if you approve the file to be processed, or<br/>"
            + "<b>Not ok</b>, if otherwise.<br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailDone = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Process mp_class_details product mitra pasti has been processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process mp_class_details product mitra pasti has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";


    /**
     * 
     * @param context
     */
    public MpClassDtlsWorker(Map<String, ? extends Object> context) {
        super(context);
    }


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        String configFile;
        configFile = "excelutil_mp.properties";
        FixClassConfig fClassConfig;
        FixSchedulerXtract fixSchedulerXtract;
        FcrBaBankMastDao fcrBABankMastDAO = new FcrBaBankMastDao(session);
        FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
        FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(this.session);
        FixQXtractDao fixQXtractDAO = new FixQXtractDao(this.session);
        TmpMpClassDtlsDao mpClassDtlsDao = new TmpMpClassDtlsDao(session);
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
        String extFile;
        String filePattern = (String) context.get(MapKey.filePattern);
        String batchNo = context.get(MapKey.batchNo).toString();
        String status = context.get(MapKey.status).toString();
        String sourceProcess = context.get(MapKey.processSource).toString();
        String param1 = context.get(MapKey.param1).toString();
        String param5 = (String) context.get(MapKey.param5);
        int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
        int idSchedulerXtract;
        String outFileName;

        this.getLogger().info("Begin Execute Worker : " + this.getClass().getName());
        idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
        getLogger().info("Done Prepare before execute status U/A");

        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            String filename = (String) context.get(MapKey.fileName);
            this.getLogger().info("Status : UNAUTHORIZED");
            this.getLogger().info("Param 5 : " + param5);
            this.getLogger().info("Filename : " + filename);

            /* Pattern filename */
            try {
                String dateInFilename;
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(filePattern);
                java.util.regex.Matcher matcher = pattern.matcher(filename);
                if (matcher.find()) {
                    this.getLogger().info("pattern match start at: " + matcher.start());
                    this.getLogger().info("pattern match end at: " + matcher.end());

                    dateInFilename = filename.substring(6, 14);
                    this.getLogger().info("dateInFilename: " + dateInFilename);
//                    dateFormatter.parse(dateInFilename);
                    if (!dateFormatter.format(fcrBABankMastDAO.get().getDatProcess()).equals(dateInFilename)) {
                        throw new FIXException("date in filename must be same with business date");
                    }
                }
            } catch (Exception pe) {
                throw new FIXException("Invalid date in filename");
            }

            this.readExcel(param5, configFile, mpClassDtlsDao);
            this.session.flush();
            this.getLogger().info("Import Excel file from Requestor Success");
            int idSchedulerReturn = mpClassDtlsDao.runValidate(batchNo);
            this.getLogger().info("Filter and Validate Source Data Done");

            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
            idSchedulerXtract = fClassConfig.getIdScheduler();
            this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: "
                    + sourceProcess + " and Status : "
                    + StatusDefinition.UNAUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idSchedulerXtract);
            outFileName = FilenameUtils.getBaseName(param1.replaceFirst(context.get(MapKey.templateName) + "\\s+", ""));
            extFile = fixSchedulerXtract.getFileFormat();
            this.getLogger().info("Out File Name : " + outFileName);

            if (!sourceProcess.equalsIgnoreCase("sftp")) {
                this.fixQXtract = new FixQXtract();
                this.fixQXtract.setIdScheduler(idSchedulerReturn); // ID Scheduler for Report Impacted Account
                this.fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                this.fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                this.fixQXtract.setParam1("RE: " + param1 + " - Impacted Account");
                this.fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
                this.fixQXtract.setParam5(outFileName + "_Impacted_Account." + extFile);
                this.fixQXtract.setParam6(batchNo);
                fixQXtractDAO.insert(this.fixQXtract);
                
                this.fixQXtract = new FixQXtract();
                this.fixQXtract.setIdScheduler(idSchedulerXtract);
                this.fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                this.fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                this.fixQXtract.setParam1("RE: " + param1);
                this.fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
                this.fixQXtract.setParam4(emailApproval);
                this.fixQXtract.setParam5(FileUtil.getDateTimeFormatedFileName(outFileName + "_{HHmmss}.") + extFile);
                this.fixQXtract.setParam6(batchNo);
                this.getLogger().info("Register FixQXtract Done");
            }
        } else if (status.equals(StatusDefinition.AUTHORIZED)) {
            this.getLogger().info("Status : AUTHORIZED");
            this.getLogger().debug("runProcess --> Batch No : " + batchNo);
            this.getLogger().debug("runProcess --> SPV : " + context.get(MapKey.emailSender).toString());

            mpClassDtlsDao.runProcess(batchNo);
            this.getLogger().info("Register FixQXtract");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
            idSchedulerXtract = fClassConfig.getIdScheduler();
            this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: "
                    + sourceProcess + " and Status : "
                    + StatusDefinition.AUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idSchedulerXtract);

            outFileName = FileUtil.getDateTimeFormatedFileName(
                    FilenameUtils.getBaseName(param1.replaceFirst(
                    "[R|r][E|e]:\\s+" + context.get(MapKey.templateName) + "\\s+", "")) + "_{HHmmss}.");
            extFile = fixSchedulerXtract.getFileFormat();
            outFileName += extFile;
            this.getLogger().info("Out File Name : " + outFileName);

            fixQXtract = new FixQXtract();
            fixQXtract.setIdScheduler(idSchedulerXtract);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1(param1);

            FixInboxDao fixInboxDao = new FixInboxDao(session);
            if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
            }
            if (context.get(MapKey.spvAuth).toString().equals("N")) {
                fixQXtract.setParam2(context.get(MapKey.param2).toString());
            }

            fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
            fixQXtract.setParam4(this.emailDone);
            fixQXtract.setParam5(outFileName);
            fixQXtract.setParam6(batchNo);

        } else if (status.equals(StatusDefinition.REJECTED)) {
            this.getLogger().info("Status : REJECTED");
            mpClassDtlsDao.runReject(batchNo);
            this.getLogger().info("Register FixQXtract");
            fixQXtract = new FixQXtract();
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
            idSchedulerXtract = fClassConfig.getIdScheduler();
            fixQXtract.setIdScheduler(idSchedulerXtract);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1(param1);
            FixInboxDao fixInboxDao = new FixInboxDao(session);

            if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
            }

            fixQXtract.setParam4(this.emailRejected);
            fixQXtract.setParam5("");
            this.getLogger().info("Register FixQXtract");
        } else {
            this.getLogger().info("Status : IGNORED");
            //maybe ignored
            FixInboxDao fixInboxDao = new FixInboxDao(session);
            FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
            fixInbox.setFlgProcess(StatusDefinition.IGNORED);
            fixInboxDao.update(fixInbox);
        }

        return true;
    }

    private void readExcel(String param5, String configFile, TmpMpClassDtlsDao mpClassDtlsDao) throws Exception {
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, configFile, mpClassDtlsDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            readXLSX(param5, configFile, mpClassDtlsDao);
        }
    }

    private void readXLS(String param5, String configFile, TmpMpClassDtlsDao mpClassDtlsDao) throws Exception {
        XLSReader xr = XLSReader.getInstance(param5, configFile);
        TmpMpClassDtls tmpMpClassDtls;
        TmpMpPK tmpMPPK;
        String[] propertyNames = {"codClass", "codComp", "minimal", "maximal", "defaultVal", "cmd"};
        Integer recordId = 0;
        Integer codClass = null;
        
        while (xr.hasNextRow()) {
            recordId += 1;
            tmpMpClassDtls = new TmpMpClassDtls();
            tmpMPPK = new TmpMpPK();
            tmpMPPK.setBatchNo(context.get(MapKey.batchNo).toString());
            tmpMPPK.setRecordId(recordId);
            tmpMpClassDtls.setCompositeId(tmpMPPK);
            tmpMpClassDtls.setIdCreatedBy("BDSM");
            tmpMpClassDtls.setIdCreatedSpv("BDSM");
            tmpMpClassDtls.setDtmCreated(SchedulerUtil.getTime());
            tmpMpClassDtls.setDtmUpdated(SchedulerUtil.getTime());
            tmpMpClassDtls.setFlagStatus(StatusDefinition.UNAUTHORIZED);
            
            try {
                if (xr.nextRow(tmpMpClassDtls, propertyNames)) {
                    if (codClass == null)
                        codClass = tmpMpClassDtls.getCodClass();
                    else if (codClass.compareTo(tmpMpClassDtls.getCodClass()) != 0)
                        throw new FIXException("a File must contain only 1 class code");
                    
                    if (tmpMpClassDtls.getMinimal() == null) {
                        tmpMpClassDtls.setMinimal("0");
                    }
                    if (tmpMpClassDtls.getMaximal() == null) {
                        tmpMpClassDtls.setMaximal("0");
                    }
                    if (tmpMpClassDtls.getDefaultVal()== null) {
                        tmpMpClassDtls.setDefaultVal("0");
                    }
                    tmpMpClassDtls.setMinVal(new BigDecimal(tmpMpClassDtls.getMinimal()));
                    tmpMpClassDtls.setMaxVal(new BigDecimal(tmpMpClassDtls.getMaximal()));
                    tmpMpClassDtls.setDefVal(new BigDecimal(tmpMpClassDtls.getDefaultVal()));
                    mpClassDtlsDao.insert(tmpMpClassDtls);
                }
            }
            catch (FIXException fex) {
                throw new Exception(fex.getMessage());
            }
            catch (Exception ex) {
                this.getLogger().error(ex, ex);
                tmpMpClassDtls.setFlagStatus(StatusDefinition.REJECTED);
                tmpMpClassDtls.setStatusReason(ex.toString());
            }
        }
    }

    private void readXLSX(String param5, String configFile, TmpMpClassDtlsDao mpClassDtlsDao) throws Exception {
        XLSXReader xr = XLSXReader.getInstance(param5, configFile);
        TmpMpClassDtls tmpMpClassDtls;
        TmpMpPK tmpMPPK;
        String[] propertyNames = {"codClass", "codComp", "minimal", "maximal", "defaultVal", "cmd"};
        Integer recordId = 0;
        Integer codClass = null;
        
        while (xr.hasNextRow()) {
            recordId += 1;
            tmpMpClassDtls = new TmpMpClassDtls();
            tmpMPPK = new TmpMpPK();
            tmpMPPK.setBatchNo(context.get(MapKey.batchNo).toString());
            tmpMPPK.setRecordId(recordId);
            tmpMpClassDtls.setCompositeId(tmpMPPK);
            tmpMpClassDtls.setIdCreatedBy("BDSM");
            tmpMpClassDtls.setIdCreatedSpv("BDSM");
            tmpMpClassDtls.setDtmCreated(SchedulerUtil.getTime());
            tmpMpClassDtls.setDtmUpdated(SchedulerUtil.getTime());
            tmpMpClassDtls.setFlagStatus(StatusDefinition.UNAUTHORIZED);
            
            try {
                if (xr.nextRow(tmpMpClassDtls, propertyNames)) {
                    if (codClass == null)
                        codClass = tmpMpClassDtls.getCodClass();
                    else if (codClass.compareTo(tmpMpClassDtls.getCodClass()) != 0)
                        throw new FIXException("a File must contain only 1 class code");
                    
                    if (tmpMpClassDtls.getMinimal() == null) {
                        tmpMpClassDtls.setMinimal("0");
                    }
                    if (tmpMpClassDtls.getMaximal() == null) {
                        tmpMpClassDtls.setMaximal("0");
                    }
                    if (tmpMpClassDtls.getDefaultVal()== null) {
                        tmpMpClassDtls.setDefaultVal("0");
                    }
                    tmpMpClassDtls.setMinVal(new BigDecimal(tmpMpClassDtls.getMinimal()));
                    tmpMpClassDtls.setMaxVal(new BigDecimal(tmpMpClassDtls.getMaximal()));
                    tmpMpClassDtls.setDefVal(new BigDecimal(tmpMpClassDtls.getDefaultVal()));
                    mpClassDtlsDao.insert(tmpMpClassDtls);
                }
            }
            catch (FIXException fex) {
                throw new Exception(fex.getMessage());
            }
            catch (Exception ex) {
                this.getLogger().error(ex, ex);
                tmpMpClassDtls.setFlagStatus(StatusDefinition.REJECTED);
                tmpMpClassDtls.setStatusReason(ex.toString());
            }
        }
    }
    
}
