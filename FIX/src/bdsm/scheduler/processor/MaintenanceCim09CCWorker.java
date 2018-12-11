/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.TmpMaintenanceCim09CCDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.TmpMaintenanceCim09CC;
import bdsm.scheduler.model.TmpMaintenanceCim09CCPK;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.excel.XLSReader;
import bdsm.util.excel.XLSXReader;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

/**
 *
 * @author v00020800
 */
public class MaintenanceCim09CCWorker extends BaseProcessor {

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat dateFormatter2 = new SimpleDateFormat("dd/MM/yyyy");
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
            + "Process maintenance cif cim09  has been processed. <br/>"
            + "Please see result report in attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process to maintenance cif cim09 has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";

    /**
     * 
     * @param context
     */
    public MaintenanceCim09CCWorker(Map context) {
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
        configFile = "excelutil_cim09CC.properties";
        FixClassConfig fClassConfig;
        FixSchedulerXtract fixSchedulerXtract;
        FcrBaBankMastDao fcrBABankMastDAO = new FcrBaBankMastDao(session);
        FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
        TmpMaintenanceCim09CCDao cim09CCDao = new TmpMaintenanceCim09CCDao(session);
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

        this.getLogger().info("Done Prepare before execute status U/A");
        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            String filename = (String) context.get(MapKey.fileName);
            this.getLogger().info("Status : UNAUTHORIZED");
            this.getLogger().info("Param 5 : " + param5);
            this.getLogger().info("Filename : " + filename);

            /*
             * Pattern filename
             */
            try {
                String xtractFilename;
                String dateInFilename;
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(filePattern);
                java.util.regex.Matcher matcher = pattern.matcher(filename);
                if (matcher.find()) {
                    this.getLogger().info("pattern match start at: " + matcher.start());
                    this.getLogger().info("pattern match end at: " + matcher.end());

                    xtractFilename = filename.substring(matcher.start(), matcher.end());
                    dateInFilename = filename.substring(6, 14);
                    this.getLogger().info("dateInFilename: " + dateInFilename);
                    if (!dateFormatter.format(fcrBABankMastDAO.get().getDatProcess()).equals(dateInFilename)) {
                        throw new FIXException("date in filename must be same with business date");
                    }
                }
            } catch (Exception pe) {
                throw new FIXException("Invalid date in filename");
            }

            this.readExcel(param5, configFile, cim09CCDao); //Must Change
            this.session.flush();
            this.getLogger().info("Import Excel file from Requestor Success");
            cim09CCDao.runValidate(batchNo);
            this.getLogger().info("Filter and Validate Source Data Done");

            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
            idSchedulerXtract = fClassConfig.getIdScheduler();
            this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: " + sourceProcess + " and Status : "
                    + StatusDefinition.UNAUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idSchedulerXtract);
            outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(param1.replaceFirst(context.get(MapKey.templateName) + "\\s+", "")) + "_{HHmmss}.");
            extFile = fixSchedulerXtract.getFileFormat();
            outFileName += extFile;
            this.getLogger().info("Out File Name : " + outFileName);

            if (!sourceProcess.equalsIgnoreCase("sftp")) {
                fixQXtract = new FixQXtract();
                fixQXtract.setIdScheduler(idSchedulerXtract);
                fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                fixQXtract.setParam1("RE: " + param1);
                FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
                fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
                fixQXtract.setParam4(emailApproval);
                fixQXtract.setParam5(outFileName);
                fixQXtract.setParam6(batchNo);
                this.getLogger().info("Register FixQXtract Done");
            }
        } else if (status.equals(StatusDefinition.AUTHORIZED)) {
            this.getLogger().info("Status : AUTHORIZED");
            this.getLogger().debug("runProcess --> Batch No : " + batchNo);
            this.getLogger().debug("runProcess --> SPV : " + context.get(MapKey.emailSender).toString());

            cim09CCDao.runProcess(batchNo);
            this.getLogger().info("IdScheduler ");
            this.getLogger().info("Register FixQXtract");
            fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
            idSchedulerXtract = fClassConfig.getIdScheduler();
            this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: " + sourceProcess + " and Status : "
                    + StatusDefinition.AUTHORIZED + "DONE");
            fixSchedulerXtract = fixSchedulerXtractDao.get(idSchedulerXtract);

            outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(param1.replaceFirst(
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

            FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
            fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
            fixQXtract.setParam4(this.emailDone);
            fixQXtract.setParam5(outFileName);
            fixQXtract.setParam6(batchNo);

        } else if (status.equals(StatusDefinition.REJECTED)) {
            this.getLogger().info("Status : REJECTED");
            this.getLogger().info("Register FixQXtract");

            cim09CCDao.runReject(batchNo);
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
            FixInboxDao fixInboxDao = new FixInboxDao(session);
            FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
            fixInbox.setFlgProcess(StatusDefinition.IGNORED);
            fixInboxDao.update(fixInbox);
        }
        return true;
    }

    private void readExcel(String param5, String configFile, TmpMaintenanceCim09CCDao cim09CCDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, ParseException {
        if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
            readXLS(param5, configFile, cim09CCDao);
        } else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
            readXLSX(param5, configFile, cim09CCDao);
        }
    }

    private void readXLS(String param5, String configFile, TmpMaintenanceCim09CCDao cim09CCDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
        XLSReader xr = XLSReader.getInstance(param5, configFile);
        TmpMaintenanceCim09CC cim09CC;
        TmpMaintenanceCim09CCPK cim09CCPK;
        String[] propertyNames = {"compositeId.cif", "bussType", "datRegistered","registrationNo",  "signName1", "signName2", "signName3", "signName4", "signName5", "signDesgn1", "signDesgn2", "signDesgn3", "signDesgn4", "signDesgn5",
            "signDirect1", "signDirect2", "signDirect3", "signDirect4", "signDirect5"};
        Integer recordId = 0;
        Date dateFormat = null;
        String dateSTring = null;
        while (xr.hasNextRow()) {
            recordId += 1;
            cim09CCPK = new TmpMaintenanceCim09CCPK();
            cim09CCPK.setBatchiId(context.get(MapKey.batchNo).toString());
            cim09CCPK.setRecordId(recordId);
            cim09CC = new TmpMaintenanceCim09CC();
            cim09CC.setCompositeId(cim09CCPK);
            cim09CC.setFlagStatus(StatusDefinition.UNAUTHORIZED);
            try {
                if (xr.nextRow(cim09CC, propertyNames)) {
                    if (cim09CC.getBussType()!= null) {
                        try {
                            cim09CC.setCodBussCat(Integer.parseInt(cim09CC.getBussType()));
                        } catch (Exception e) {
                            getLogger().info("REJECT NUMBER " + cim09CC.getBussType());
                            cim09CC.setStatusReason("Reject, [Type of Business Must Be Number]");
                            cim09CC.setFlagStatus(StatusDefinition.REJECTED);
                        }
                    }
//                    if (cim09CC.getvDateReg() != null) {
//                        try {
//                            dateFormat = dateFormatter2.parse(cim09CC.getvDateReg());
//                            cim09CC.setDatRegistered(dateFormatter2.parse(cim09CC.getvDateReg()));
//                            dateSTring = dateFormatter2.format(dateFormat);
//                            if (!cim09CC.getvDateReg().equals(dateSTring)) {
//                                getLogger().info("VDate " + cim09CC.getvDateReg());
//                                getLogger().info("dateFormat " + dateFormat);
//                                getLogger().info("dateString " + dateSTring);
//                                cim09CC.setStatusReason("Invalid date, format must be [DD/MM/YYYY]");
//                                cim09CC.setFlagStatus(StatusDefinition.REJECTED);
//                            }
//                        } catch (Exception e) {
//                            getLogger().info("REJECT DATE " + cim09CC.getvDateReg() + e);
//                            cim09CC.setStatusReason("Invalid date, format must be [DD/MM/YYYY]");
//                            cim09CC.setFlagStatus(StatusDefinition.REJECTED);
//                        }
//                    }
                    cim09CCDao.insert(cim09CC);

                }
            } catch (Exception ex) {
                this.getLogger().error(ex, ex);
                cim09CC.setStatusReason(ex.toString());
                cim09CC.setFlagStatus(StatusDefinition.REJECTED);
                cim09CCDao.insert(cim09CC);
            }
        }
    }

    private void readXLSX(String param5, String configFile, TmpMaintenanceCim09CCDao cim09CCDao) {
        XLSXReader xr = XLSXReader.getInstance(param5, configFile);
        TmpMaintenanceCim09CC cim09CC;
        TmpMaintenanceCim09CCPK cim09CCPK;
          String[] propertyNames = {"compositeId.cif", "bussType", "datRegistered","registrationNo",  "signName1", "signName2", "signName3", "signName4", "signName5", "signDesgn1", "signDesgn2", "signDesgn3", "signDesgn4", "signDesgn5",
            "signDirect1", "signDirect2", "signDirect3", "signDirect4", "signDirect5"};
        Integer recordId = 0;
        Date dateFormat = null;
        String dateSTring = null;
        while (xr.hasNextRow()) {
            recordId += 1;
            cim09CCPK = new TmpMaintenanceCim09CCPK();
            cim09CCPK.setBatchiId(context.get(MapKey.batchNo).toString());
            cim09CCPK.setRecordId(recordId);
            cim09CC = new TmpMaintenanceCim09CC();
            cim09CC.setFlagStatus(StatusDefinition.UNAUTHORIZED);
            cim09CC.setCompositeId(cim09CCPK);
            try {
                if (xr.nextRow(cim09CC, propertyNames)) {
                    if (cim09CC.getBussType()!= null) {
                        try {
                            cim09CC.setCodBussCat(Integer.parseInt(cim09CC.getBussType()));
                        } catch (Exception e) {
                            getLogger().info("REJECT NUMBER " + cim09CC.getBussType());
                            cim09CC.setStatusReason("Reject, [Type of Business Must Be Number]");
                            cim09CC.setFlagStatus(StatusDefinition.REJECTED);
                        }
                    }
//                        if (cim09CC.getvDateReg() != null) {
//                            try {
//                                dateFormat = dateFormatter2.parse(cim09CC.getvDateReg());
//                                cim09CC.setDatRegistered(dateFormatter2.parse(cim09CC.getvDateReg()));
//                                dateSTring = dateFormatter2.format(dateFormat);
//                                if (!cim09CC.getvDateReg().equals(dateSTring)) {
//                                    getLogger().info("VDate " + cim09CC.getvDateReg());
//                                    getLogger().info("dateFormat " + dateFormat);
//                                    getLogger().info("dateString " + dateSTring);
//                                    cim09CC.setStatusReason("Invalid date, format must be [DD/MM/YYYY]");
//                                    cim09CC.setFlagStatus(StatusDefinition.REJECTED);
//                                }
//                            } catch (Exception e) {
//                                getLogger().info("REJECT DATE " + cim09CC.getvDateReg() + e);
//                                cim09CC.setStatusReason("Invalid date, format must be [DD/MM/YYYY]");
//                                cim09CC.setFlagStatus(StatusDefinition.REJECTED);
//                            }
//                        }
                    cim09CCDao.insert(cim09CC);
                }
            } catch (Exception ex) {
                this.getLogger().error(ex, ex);
                cim09CC.setStatusReason(ex.toString());
                cim09CC.setFlagStatus(StatusDefinition.REJECTED);
                cim09CCDao.insert(cim09CC);
            }
        }
    }
}
