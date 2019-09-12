/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.ARParamMasterInterface;
import bdsm.model.ARParamProfileInterface;
import bdsm.scheduler.ErrorPersister;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.util.SchedulerUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author bdsm
 */
public abstract class NewTxtReaderWorker extends BaseProcessor {

    protected Map innerContext;
    protected String ErrFlag;
    protected String flgSpv;
    protected String sourceProcess;
    protected String param1;
    //protected String param4;
    protected String delimiter;
    protected String errValue;
    protected Integer schedID;
    protected String idBatch;
    private String status;
    protected String errID;
    protected String errFile;
    protected String pattern;
    // for param4
    protected String emailApproval;
    protected String emailDone;
    protected String emailRejected;
    protected List HeaderCount;
    protected List DetailCount;
    protected List FooterCount;
    private String headerDelim = StatusDefinition.Headers;
    private String detailDelim = StatusDefinition.Details;
    private String footerDelim = StatusDefinition.Footers;
    private Integer importID;
    private boolean masterProc;
    //protected abstract Map fieldInterface(String scanDetail, Integer Maxrec, Integer count);

    protected abstract boolean valueInterface();

    protected abstract boolean valueProcess();

    protected abstract boolean valueReject();

    protected abstract Map rejectAction();

    protected abstract FixQXtract registerQU();

    protected abstract FixQXtract registerQR();

    protected abstract FixQXtract registerQA();

    protected abstract Boolean process(String file, Integer conter);

    public NewTxtReaderWorker(Map context) {
        super(context);
    }

    /*
     * TxtReader class for general purpose EMAIL and SFTP based on Text Files
     * Class will receive file from both Email and SFTP with general behavior :
     * SFTP : U -> Sent data into temp table and set to Unauthorized SFTP : A ->
     * Validate data on temp table and return respective respond to FixQxtract
     * SFTP : R -> Sent signal to FixQxtract with Error and Respcode
     *
     * EMAIL : U -> sent data into temp table and set to Unauthorized wait user
     * to authorized EMAIl : A -> Validate data on temp table and return
     * respective Respond to FixQxtract EMAIL : R -> Data Rejected delete data
     * from tmp and send signal to FixQxtract
     */
    @Override
    protected boolean doExecute() {
        getLogger().info("EXECUTE TXT WORKER");

        HashMap hMap = new HashMap();
        innerContext = hMap;
        masterProc = false;

        String typExtract;
        String fileName = null;
        String namTemplate = null;
        String idScheduler = null;
        HeaderCount = new ArrayList();
        DetailCount = new ArrayList();
        FooterCount = new ArrayList();

        Integer hCounter = 1;
        Integer dCounter = 1;
        Integer fCounter = 1;
        getLogger().debug("CONTEXT :" + context);
        try {
            namTemplate = context.get(MapKey.templateName).toString(); // filepatern
            idScheduler = context.get(MapKey.idScheduler).toString();
            try {
                fileName = context.get(MapKey.param5).toString();
                this.pattern = fileName;
            } catch (Exception e) {
                getLogger().info("CHANGE VALUE :" + e);
                fileName = this.pattern;
            }
            this.idBatch = context.get(MapKey.batchNo).toString();
            this.status = context.get(MapKey.status).toString();
            //String javClass = context.get(MapKey.javaClass).toString();
            this.sourceProcess = context.get(MapKey.processSource).toString();
            this.param1 = context.get(MapKey.param1).toString();
            this.flgSpv = context.get(MapKey.spvAuth).toString();

            getLogger().debug("Batch ID :" + this.idBatch);
            getLogger().debug("Source Process :" + sourceProcess);
            getLogger().debug("status :" + status);
            //getLogger().info("javClass :" + javClass);
            getLogger().debug("filename :" + fileName);
            getLogger().debug("Pattern :" + namTemplate);
            getLogger().debug("ID Scheduler :" + idScheduler);

        } catch (Exception e) {
            getLogger().info("Exception :" + e);
        }
        if (this.status == null) {
            this.status = StatusDefinition.PROCESS;
        }
        //String lineHeader;
        String lineDetails;

        //String hResult;
        boolean valValidation;
        Integer conter = 0;
        Integer totalCounter = 0;
        FixSchedulerXtractDao inDao = new FixSchedulerXtractDao(session);
        FixSchedulerImportDao imDao = new FixSchedulerImportDao(session);
        FixSchedulerXtract xtractS = inDao.get(namTemplate);
        FixSchedulerImport importS = imDao.get(namTemplate);
        try {
            getLogger().debug("TYPE SCHEDULER :" + xtractS.getTypScheduler());
            typExtract = xtractS.getTypScheduler();
        } catch (Exception e) {
            typExtract = "";
        }
        /*
         * UNAUTHORIZE Status to do Validation Format
         */
        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            getLogger().info("PROCEED WITH DATA INSERTION .... ");
            getLogger().debug("FILE NAME :" + fileName);
            getLogger().debug("TEMPLATE NAME :" + namTemplate);
            getLogger().debug("SCHEDULER ID:" + idScheduler);

            BufferedReader br = null;
            String param5 = fileName;

            conter = 0;
            try {
                br = new BufferedReader(new FileReader(param5));
            } catch (FileNotFoundException ex) {
                getLogger().info("FILE NOT FOUND :" + ex);
            }
            GeneralINTDao arDao = new GeneralINTDao(session);
            importID = importS.getFixSchedulerPK().getIdScheduler();
            List<ARParamMasterInterface> mit = arDao.listparamProfile(importID);
            if (mit != null) {
                 masterProc = true;
            } else {
                // fixing for xtract only side
                mit = arDao.listparamProfile(xtractS.getFixSchedulerPK().getIdScheduler(), "X");
                if(mit != null){
                    masterProc = true;
                }
            }
            if (masterProc){
                for (ARParamMasterInterface pi : mit) {
                    if (pi.getOrderNo().compareTo(1) == 0) {
                        headerDelim = pi.getTypProcess();
                    } else if (pi.getOrderNo().compareTo(2) == 0) {
                        detailDelim = pi.getTypProcess();
                    } else if (pi.getOrderNo().compareTo(3) == 0) {
                        footerDelim = pi.getTypProcess();
                    }
                }
            }
            getLogger().info("Open File First : " + conter);
            try {
                while (((lineDetails = br.readLine()) != null)) {
                    totalCounter++;
                    if (headerDelim.equalsIgnoreCase(lineDetails.substring(0, 1))) {
                        HeaderCount.add(hCounter);// get total line, header detail footer
                        getLogger().debug("Position :" + HeaderCount);
                        getLogger().debug("HEADER COUNT" + HeaderCount.size());
                        hCounter++;
                    } else if (detailDelim.equalsIgnoreCase(lineDetails.substring(0, 1))) {
                        DetailCount.add(dCounter);
                        getLogger().debug("Position Detail :" + DetailCount);
                        getLogger().debug("DETAIL COUNT" + DetailCount.size());
                        dCounter++;
                    } else if (footerDelim.equalsIgnoreCase(lineDetails.substring(0, 1))) {
                        FooterCount.add(fCounter);
                        getLogger().debug("Position Footer :" + FooterCount);
                        getLogger().debug("FOOTER COUNT" + FooterCount.size());
                        fCounter++;
                    }
                    getLogger().debug(conter + " ." + lineDetails);
                    conter++;
                }
            } catch (Exception exception) {
                getLogger().info("EXCEPTION :" + exception, exception);
                exception.getStackTrace();
                this.ErrFlag = StatusDefinition.YES; // failed to finish read file
                this.errValue = exception.getMessage();
                this.errID = ErrorPersister.readFailed;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException iOException) {
                        getLogger().info("IO EXCEPTION :" + iOException);
                        this.ErrFlag = StatusDefinition.YES; // failed to finish read file
                        this.errValue = iOException.toString();
                        this.errID = ErrorPersister.fileClosed;
                    }
                }
            }
            getLogger().info("File Closed with rowcount : " + DetailCount.size());

            /*if (DetailCount.isEmpty()) {
                this.ErrFlag = StatusDefinition.YES; // forced failed
                getLogger().info("NO RECORD FOUND");
                // possible no detail header and footer only
                return false;
            } else {
                if (process(param5, DetailCount.size())) {
                    this.ErrFlag = StatusDefinition.NO; // process finish
                    getLogger().info("SUCCESS PROCESSED RECORD");
                } else {
                    this.ErrFlag = StatusDefinition.YES; // forced failed
                    getLogger().info("FAILED PROCESSED RECORD");
                }
            }*/

            if (process(param5, DetailCount.size())) {
                this.ErrFlag = StatusDefinition.NO; // process finish
                getLogger().info("SUCCESS PROCESSED RECORD");
            } else {
                this.ErrFlag = StatusDefinition.YES; // forced failed
                getLogger().info("FAILED PROCESSED RECORD");
            }

            if (StatusDefinition.YES.equals(this.ErrFlag)) {
                // failed insert sent to fixQ
                setFixQxtract();
                return false;
            } else {
                if (StatusDefinition.YES.equals(this.flgSpv)) { // authorizen not requiered 2nd working not invoke
                    getLogger().info("PROCEED WITH DATA VALIDATION .... ");
                    valValidation = valueInterface();
                    if (valValidation) { // validation success
                        getLogger().info("SUCCESS VALIDATION");
                        valValidation = valueProcess();
                        if (valValidation) {
                            getLogger().info("SUCCESS PROCESS");
                        } else {
                            valValidation = valueReject();
                            this.ErrFlag = "Y";
                            getLogger().info("FAILED PROCESS");
                        }
                    } else {
                        this.ErrFlag = "Y";
                        getLogger().info("FAILED");
                    }

                    setFixQxtract();
                    this.context.put(MapKey.spvAuth, StatusDefinition.NO);
                    // begin validation for After Scan Record
                    getLogger().info("SUCCESS PROCESSED ALL RECORD");
                } else {
                    // begin validation for After Scan Record
                    try {
                        valValidation = valueInterface();
                    } catch (Exception e) {
                        getLogger().debug("CALL FUNCTION FAILED :" + e, e);
                    }
                    getLogger().info("SUCCESS PROCESSED RECORD INTO BASEPROCESSOR");

                }
                return true;
            }
            // data already insert set to unathorized for sftp

            //return true;
        } else if (status.equals(StatusDefinition.REJECTED)) {
            getLogger().info("Register FixQXtract For REJECT");
            innerContext = rejectAction();
            setFixQxtract();
            return true;

        } else if (this.status.equals(StatusDefinition.AUTHORIZED)) { // 2ND baseprocessor run
            if (StatusDefinition.YES.equalsIgnoreCase(importS.getSpvAuth())) {
                getLogger().info("ALREADY PROCESSED... ");
                setFixQxtract();
                return true;
            } else {
                getLogger().info("PROCEED WITH DATA UPDATE .... ");
                try {
                    valValidation = valueProcess();
                    if (valValidation) { // validation success
                        setFixQxtract();
                        return true;
                    } else {
                        valueReject();
                        setFixQxtract();
                        return false;
                    }
                } catch (Exception e) {
                    getLogger().debug("CALL PROCESS FAILED :" + e, e);
                    setFixQxtract();
                    return false;
                }

            }

        } else if (this.status == null) {
            String param5 = null;
            if (StatusDefinition.PROCESS.equalsIgnoreCase(typExtract)) { // if no file Periodic scheduler
                getLogger().info("PROCESSING SCHEDULER PERIODIC :" + namTemplate);
                if (process(param5, conter)) {
                    this.ErrFlag = "N";
                    this.context.put(MapKey.spvAuth, this.flgSpv);
                    setFixQxtract();
                    return true;
                } else {
                    this.ErrFlag = "Y"; // failed
                    setFixQxtract();
                    return false;
                }
            } else {
                return false;
            }
        } else {
            //maybe ignored
            FixInboxDao fixInboxDao = new FixInboxDao(this.session);
            FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
            fixInbox.setFlgProcess(StatusDefinition.IGNORED);
            fixInboxDao.update(fixInbox);
            return true;
        }
    }

    protected boolean callPackage(Integer number) {
        GeneralINTDao arDao = new GeneralINTDao(session);
        List<ARParamProfileInterface> mit = arDao.listparamProfileInterface(this.importID);
        // check for Function
        String packageExecute = null;
        getLogger().debug("MIT :" + mit);
        try {
            for (ARParamProfileInterface i : mit) {
                if (i != null) {
                    getLogger().debug("interface " + i);
                    if (number.compareTo(1) == 0) { // validation
                        if (i.getValidationFunction() != null) {
                            packageExecute = i.getValidationFunction();
                            getLogger().debug("package 1 :" + packageExecute);
                            break;
                        }
                    } else if (number.compareTo(2) == 0) { // Process
                        if (i.getProcessFunction() != null) {
                            packageExecute = i.getProcessFunction();
                            getLogger().debug("package 2 :" + packageExecute);
                            break;
                        }
                    } else if (number.compareTo(3) == 0) { // reject
                        if (i.getRejectFunction() != null) {
                            packageExecute = i.getRejectFunction();
                            getLogger().debug("package 3 :" + packageExecute);
                            break;
                        }
                    }
                    // fixing so can call accross different package on same profile 
                }
            }
            if (packageExecute != null) {
                boolean aftermath = true;
                try {
                    aftermath = arDao.callFunction(packageExecute, idBatch);
                } catch (Exception e) {
                    getLogger().debug("FUNC FAILED :" + e, e);
                    try {
                        arDao.callPackage(packageExecute, idBatch);
                    } catch (Exception ex) {
                        getLogger().debug("PROCEDURE FAILED :" + ex, ex);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            getLogger().debug("failed to get Function :" + e, e);
            return false;
        }
    }

    protected Boolean setFixQxtract() {
        getLogger().info("Set FixQXtract for Information to Sender");
        getLogger().debug("CONTEXT FOR Q :" + context);
        //ErrorCodeDao errDao = new ErrorCodeDao(session);

        StringBuilder st = new StringBuilder();
        this.idBatch = context.get(MapKey.batchNo).toString();
        this.schedID = Integer.parseInt(context.get(MapKey.idScheduler).toString());

        try {
            this.errID = context.get(MapKey.errID).toString();
            this.errFile = FilenameUtils.getBaseName(context.get(MapKey.param5).toString()) + ".err"; // err destination
        } catch (Exception e) {
            getLogger().info("EXCEPTION : " + e, e);
            if (this.errID == null) {
                this.errID = "";
            }
            this.errFile = FilenameUtils.getBaseName(this.pattern) + ".err"; // err destination
        }
        this.fixQXtract = new FixQXtract();
        if (this.status.equals(StatusDefinition.UNAUTHORIZED)) {
            this.fixQXtract = registerQU();
        } else if (this.status.equals(StatusDefinition.REJECTED)) {
            this.fixQXtract = registerQR();
        } else if (this.status.equals(StatusDefinition.AUTHORIZED)) {
            this.fixQXtract = registerQA();
        }
        getLogger().debug("ID SCHEDULER :" + this.schedID);
        //this.session.persist(this.fixQXtract);

        this.fixQXtract.setIdScheduler(this.schedID);
        this.fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
        this.fixQXtract.setDtmRequest(SchedulerUtil.getTime());
        this.fixQXtract.setParam6(this.idBatch); // ID Batch
        this.context.put(MapKey.fixQXtract, this.fixQXtract);
        getLogger().info("Register FixQXtract Done");

        getLogger().debug("CONTEXT AFTER Q :" + context);
        FixQXtractDao fixQXtractDao = new FixQXtractDao(this.session);
        if ("Y".equals(this.ErrFlag)) {
            FixQXtract fixQXtract;
            if ((fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract)) != null) {
                getLogger().info("Register FixQXtract When Error 1st Worker");

                //Transaction tx = this.session.beginTransaction()
                fixQXtractDao.insert(fixQXtract);
                //tx.commit();
                getLogger().info("Register FixQXtract FOR ERROR DONE");
            }
            return false;
        } else {
            //fixQXtractDao.insert(fixQXtract);
            return true;
        }
    }
}
