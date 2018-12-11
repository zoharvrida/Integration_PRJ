/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.TmpAdqPmb;
import bdsm.scheduler.model.TmpAdqPml;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.BdsmUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author NCBS
 */
public class AdqWorker extends BaseProcessor {

    private String checksum;
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
            + "Process Adira Quantum Merchant Payment has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    private String emailRejected = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Your requested process Adira Quantum Merchant Payment has been Rejected by Supervisor. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";

    /**
     * 
     * @param context
     */
    public AdqWorker(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        getLogger().info("[BEGIN] AdqWorker Execution");
        try {
            getLogger().info("Loading Config");
            loadConfig();
            getLogger().info("Initialize Parameter and Dao");
            AdqWorkerDao dao = new AdqWorkerDao(session);
            FixClassConfigDao fClassDao = new FixClassConfigDao(session);
            FcrBaBankMastDao fdao = new FcrBaBankMastDao(session);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String datProcess = sdf.format(fdao.get().getDatProcess());
            String lastDatProccess = sdf.format(fdao.get().getDatLastProcess());
            String batchNo = context.get(MapKey.batchNo).toString();
            String namTemplate = context.get(MapKey.templateName).toString();
            String status = context.get(MapKey.status).toString();
            String sourceProcess = context.get(MapKey.processSource).toString();
            String param1 = context.get(MapKey.param1).toString();
            int idScheduler = 0;
            getLogger().info("Status execution is : " + status);
            if (status.equalsIgnoreCase(StatusDefinition.UNAUTHORIZED)) {
                getLogger().info("Begin Upload for : " + namTemplate);
                String filename = context.get(MapKey.param5).toString();
                getLogger().info("Bagin validate ADQMCP Filename");
                String fileDate = filename.replace(".txt", "").substring(filename.replace(".txt", "").length() - 8);
                getLogger().info("fileDate is : " + fileDate);
                getLogger().info("datProcess is : " + datProcess);
                getLogger().info("lastDatProcess is : " + lastDatProccess);
                if ((!fileDate.equals(datProcess)) && (!fileDate.equals(lastDatProccess))) {
                    getLogger().info("Invalid ADQ Filename");
                    getLogger().info("Registerto FixQXtract for Invalid ADQ Filename");
                    /*
                    fixQXtract = new FixQXtract();
                    idScheduler = ScheduleDefinition.emailOnly;
                    fixQXtract.setIdScheduler(idScheduler);
                    fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                    fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                    fixQXtract.setParam1("RE: " + param1);
                    fixQXtract.setParam2(context.get(MapKey.param2).toString());
                    fixQXtract.setParam4("INVALID EMAIL");
                    fixQXtract.setParam5("");
                    * */
                    getLogger().info("[END] AdqWorker Execution");
                    throw new Exception("Invalid ADQ Filename");
                    //return true;
                }
                getLogger().info("Begin Checksum File");

                if (namTemplate.equalsIgnoreCase("adqmpb")) {
                    idScheduler = 33;
                    try {
                        doImportPmb(filename, batchNo);
                    } catch (Exception e) {
                        getLogger().error(e, e);
                        getLogger().info("[END] AdqWorker Execution");
                        throw new Exception("File Import Error", e);
                    }
                    dao.getPmb(batchNo); //still execute this to remove bug
                    dao.runUploadPmb(batchNo);

                }
                if (namTemplate.equalsIgnoreCase("adqmpl")) {
                    idScheduler = 35;
                    try {
                        doImportPml(filename, batchNo);
                    } catch (Exception e) {
                        getLogger().error(e, e);
                        getLogger().info("[END] AdqWorker Execution");
                        throw new Exception("File Import Error", e);
                        //return true;
                    }
                    dao.getPml(batchNo); //still execute this to remove bug
                    dao.runUploadPml(batchNo);
                }
                String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().substring(7)) + "{hhmmss}.xls");
                //idScheduler = fClassDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0).getIdScheduler();
                if (!sourceProcess.equalsIgnoreCase("sftp")) {
                    fixQXtract = new FixQXtract();
                    fixQXtract.setIdScheduler(idScheduler);
                    fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                    fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                    fixQXtract.setParam1("RE: " + param1);
                    FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
                    fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), Integer.parseInt(context.get(MapKey.idScheduler).toString())));
                    fixQXtract.setParam4(this.emailApproval);
                    fixQXtract.setParam5(outFileName);
                    fixQXtract.setParam6(batchNo);
                    getLogger().info("Register FixQXtract Done");
                }

            } else if (status.equalsIgnoreCase(StatusDefinition.AUTHORIZED)) {
                getLogger().info("Begin Approve for : " + namTemplate);
                int retval;
                if (namTemplate.equalsIgnoreCase("adqmpb")) {
                    retval = dao.runApprovePmb(batchNo);
                    if (retval != 1) {
                        idScheduler = 34;
                    }
                }
                if (namTemplate.equalsIgnoreCase("adqmpl")) {
                    retval = dao.runApprovePml(batchNo);
                    if (retval != 1) {
                        idScheduler = 36;
                    }
                }
                if (idScheduler != 0) {
                    //do register fixqxtract for error result
                    String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace("RE: " + context.get(MapKey.templateName) + " ", "")) + "{hhmmss}.xls.zip");
                    fixQXtract = new FixQXtract();
                    fixQXtract.setIdScheduler(idScheduler);
                    fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                    fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                    fixQXtract.setParam1(param1);
                    FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
                    fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(),Integer.parseInt(context.get(MapKey.idScheduler).toString())));
                    FixInboxDao fixInboxDao = new FixInboxDao(session);
                    if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                        fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
                    }
                    if (context.get(MapKey.spvAuth).toString().equals("N")) {
                        fixQXtract.setParam2(context.get(MapKey.param2).toString());
                    }
                    fixQXtract.setParam4(this.emailDone);
                    fixQXtract.setParam5(out2FileName);
                    fixQXtract.setParam6(batchNo);
                } else {
                    getLogger().info("Approval Done, Waiting for FCR Respons in Another Process");
                }
            } else if (status.equalsIgnoreCase(StatusDefinition.REJECT)) {
                getLogger().info("Begin Reject for : " + namTemplate);
                if (namTemplate.equalsIgnoreCase("adqmpb")) {
                    dao.runRejectPmb(batchNo);
                }
                if (namTemplate.equalsIgnoreCase("adqmpl")) {
                    dao.runRejectPml(batchNo);
                }
                fixQXtract = new FixQXtract();
                idScheduler = fClassDao.get(getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0).getIdScheduler();
                fixQXtract.setIdScheduler(idScheduler);
                fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                fixQXtract.setParam1(param1);
                FixInboxDao fixInboxDao = new FixInboxDao(session);
                if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                    fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
                }
                fixQXtract.setParam4(this.emailRejected);
                fixQXtract.setParam5("");
                getLogger().info("Register FixQXtract");
            } else {
                getLogger().info("State is Ignored");
            }
        } catch (Exception e) {
            getLogger().error("Execute AqmWorker Error");
            getLogger().error(e, e);
            throw new Exception(e);
        }
        getLogger().info("[END] AdqWorker Execution");
        return true;
    }

    private void loadConfig() throws IOException {
        Properties properties = new Properties();
        InputStream in = AdqWorker.class.getClassLoader().getResourceAsStream("txtutil_aqm.properties");
        properties.load(in);
        totalFieldPml = Integer.parseInt(properties.getProperty("total_field_pml"));
        totalFieldPmb = Integer.parseInt(properties.getProperty("total_field_pmb"));
        fieldSeparator = properties.getProperty("field_separator");
        headerRow = Integer.parseInt(properties.getProperty("header_row"));
        footerPosition = properties.getProperty("footer_position");
        in.close();
    }

    private void doImportPmb(String filename, String batchNo) throws Exception {
        getLogger().info("Begin Import PMB File");
        TmpAdqPmbDao dao = new TmpAdqPmbDao(session);
        checksum = BdsmUtil.getChecksum(filename);
        if (dao.getChecksum(checksum) != null) {
            throw new Exception("Duplicate file content found by Checksum check");
        }
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";
        int counter = 0;
        TmpAdqPmb model = null;
        while ((line = br.readLine()) != null) {
            if (!"".equalsIgnoreCase(line)) {
                model = new TmpAdqPmb();
                //header row checking
                if ((headerRow != 0) && (counter == headerRow)) {
                    //is header row
                    model.setRowFlag("H"); //rowflag H for Header Row
                    model.setBatchNo(batchNo);
                    dao.insert(model);
                } //footer row checking
                else if ((!footerPosition.equalsIgnoreCase("none")) && (counter == 0)) {
                    //is footer row
                    model.setRowFlag("F"); //rowflag F for Footer Row
                    model.setBatchNo(batchNo);
                    dao.insert(model);
                } else {
                    //detail data mining
                    String[] theData = line.split(fieldSeparator);
                    if (theData.length != totalFieldPmb) {
                        getLogger().error("Field Count Wrong");
                        getLogger().error("Data Length : " + theData.length +" Parameter : " + totalFieldPmb);
                        throw new Exception("Total field wrong");
                    } else {
                        model.setRowFlag("D"); //rowflag D for Detail Row
                        model.setBatchNo(batchNo);
                        model.setCol1(theData[0]);
                        model.setCol2(theData[1]);
                        model.setCol3(theData[2]);
                        model.setCol4(theData[3]);
                        model.setFlgStatus("U");
                        model.setChecksum(checksum);
                        dao.insert(model);
                    }
                }
                counter++;
            }
        }
        getLogger().info("End Import PMB File");
    }

    private void doImportPml(String filename, String batchNo) throws Exception {
        getLogger().info("Begin Import PML File");
        TmpAdqPmlDao dao = new TmpAdqPmlDao(session);
        checksum = BdsmUtil.getChecksum(filename);
        if (dao.getChecksum(checksum) != null) {
            throw new Exception("Duplicate file content found by Checksum check");
        }
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";
        int counter = 0;
        TmpAdqPml model = null;
        while ((line = br.readLine()) != null) {
            if (!"".equalsIgnoreCase(line)) {
                model = new TmpAdqPml();
                //header row checking
                if ((headerRow != 0) && (counter == headerRow)) {
                    //is header row
                    model.setRowFlag("H"); //rowflag H for Header Row
                    model.setBatchNo(batchNo);
                    dao.insert(model);
                } //footer row checking
                else if ((!footerPosition.equalsIgnoreCase("none")) && (counter == 0)) {
                    //is footer row
                    model.setRowFlag("F"); //rowflag F for Footer Row
                    model.setBatchNo(batchNo);
                    dao.insert(model);
                } else {
                    //detail data mining
                    String[] theData = line.split(fieldSeparator);
                    if (theData.length != totalFieldPml) {
                        getLogger().error("Field Count Wrong");
                        getLogger().error("Data Length : " + theData.length +" Parameter : " + totalFieldPml);
                        throw new Exception("Total field wrong");
                    } else {
                        model.setRowFlag("D"); //rowflag D for Detail Row
                        model.setBatchNo(batchNo);
                        model.setCol1(theData[0]);
                        model.setCol2(theData[1]);
                        model.setCol3(theData[2]);
                        model.setCol4(theData[3]);
                        model.setCol5(theData[4]);
                        model.setCol6(theData[5]);
                        model.setCol7(theData[6]);
                        model.setCol8(theData[7]);
                        model.setCol9(theData[8]);
                        model.setCol10(theData[9]);
                        model.setFlgStatus("U");
                        model.setChecksum(checksum);
                        dao.insert(model);
                    }
                }
            }
        }
        getLogger().info("End Import PML File");
    }
    private int totalFieldPml;
    private int totalFieldPmb;
    private String fieldSeparator;
    private int headerRow;
    private String footerPosition;
}
