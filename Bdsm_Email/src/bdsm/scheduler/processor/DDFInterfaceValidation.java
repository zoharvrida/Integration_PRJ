/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.ErrorPersister;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.model.*;
import bdsm.scheduler.util.SchedulerUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import sun.misc.Regexp;

/**
 *
 * @author v00019722
 */
public class DDFInterfaceValidation extends TXTReaderWorker {

    public DDFInterfaceValidation(Map context) {
        super(context);
    }
    private Integer xID;
    private String respond;
    private String patternMAtcherCIF;
    private String patternMAtcherCASA;
    private String flags;
    private StringBuilder errIDs = new StringBuilder();
    private StringBuilder errVal = new StringBuilder();
    private String namTemplate;
    private final String HEADER_LINE = "6";
    private final Integer MAX_ROW_DETAILS = 10000;
    //private String[] mandatory;

    protected Integer CIFValidation() {
        getLogger().info("AUTHORIZED CIF BEGIN");

        Integer idScheduler;
        try {
            this.namTemplate = context.get(MapKey.templateName).toString();
            this.idBatch = context.get(MapKey.batchNo).toString(); // idBatch
            idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());

            getLogger().debug("NAMTEMPLATE :" + namTemplate);
            getLogger().debug("BATCH ID :" + idBatch);
            getLogger().debug("ID SCHEDULER :" + idScheduler);
        } catch (Exception e) {
            getLogger().info("EXCEPTION : " + e);
        }

        getLogger().info("CLASS " + getClass().getName());
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        FixSchedulerXtractDao fixQdao = new FixSchedulerXtractDao(session);
        try {
            FixSchedulerXtract fixSchedulerXtract = fixQdao.get(namTemplate);
            this.xID = fixSchedulerXtract.getFixSchedulerPK().getIdScheduler();
            getLogger().info("FIXSCHEDULER EXTRACT :" + this.xID);
        } catch (Exception e) {
            try {
                getLogger().info(errDao.get(ErrorPersister.idschedulerNRCIF, e.toString(), StatusDefinition.PROCESS));
            } catch (SQLException ex1) {
                getLogger().info("FAILED TO SET FIXQXTRACT " + ex1);
                this.respond = ErrorPersister.idschedulerNRCIF;
            }
        }
        CiChUfCIFDetailsDao detailDao = new CiChUfCIFDetailsDao(session);

        //detailDao.getDetails(BatchID);
        Integer runUpdateCIF = detailDao.runUpdateUF(idBatch);
        getLogger().info("runPackageUpdate : " + runUpdateCIF);
        this.context.put(MapKey.idScheduler, this.xID);

        if (runUpdateCIF == 1) {
            super.ErrFlag = StatusDefinition.NO;
        } else {
            this.context.put(MapKey.errID, this.respond);
            super.ErrFlag = StatusDefinition.YES;
        }
        return runUpdateCIF;
    }

    protected Integer CASAValidation() {
        getLogger().info("AUTHORIZED CASA BEGIN");

        Integer idScheduler;
        try {
            this.namTemplate = context.get(MapKey.templateName).toString();
            this.idBatch = context.get(MapKey.batchNo).toString(); // idBatch
            idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());

            getLogger().debug("NAMTEMPLATE :" + namTemplate);
            getLogger().debug("BATCH ID :" + idBatch);
            getLogger().debug("ID SCHEDULER :" + idScheduler);
        } catch (Exception e) {
            getLogger().debug("EXCEPTION : " + e);
        }

        ErrorCodeDao errDao = new ErrorCodeDao(session);
        FixSchedulerXtractDao fixQdao = new FixSchedulerXtractDao(session);
        try {
            FixSchedulerXtract fixSchedulerXtract = fixQdao.get(namTemplate);
            this.xID = fixSchedulerXtract.getFixSchedulerPK().getIdScheduler();

        } catch (Exception e) {
            try {
                getLogger().info(errDao.get(ErrorPersister.idschedulerNRCASA, e.toString(), StatusDefinition.PROCESS));
            } catch (SQLException ex1) {
                getLogger().info("FAILED TO SET FIXQXTRACT " + ex1);
                respond = ErrorPersister.idschedulerNRCASA;
            }
        }

        CiChUfCASADetailsDao detailDao = new CiChUfCASADetailsDao(session);

        Integer runUpdateCASA = detailDao.runPackage(idBatch);
        getLogger().info("runPackageStatus : " + runUpdateCASA);
        this.context.put(MapKey.idScheduler, this.xID);

        if (runUpdateCASA == 1) {
            super.ErrFlag = StatusDefinition.NO;
        } else {
            super.ErrFlag = StatusDefinition.YES;
            this.context.put(MapKey.errID, this.respond);
        }
        return runUpdateCASA;
    }

    protected String HeaderCIF(String[] detail, Integer total) {
        Integer hTotal;
        String result = null;
        Integer errFlag = 0;
        Integer counter = 0;
        Integer realcount = 0;
        String[] mandatory = new String[detail.length];

        String FileName = null;
        try {
            FileName = context.get(MapKey.param5).toString();
            getLogger().debug("FILENAME :" + FileName);
        } catch (Exception e) {
            getLogger().debug("NO FILENAME ??");
            FileName = context.get(MapKey.fileName).toString();
        }
        SimpleDateFormat df = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);
        this.errValue = StatusDefinition.NO;

        getLogger().debug("DELIMITER EXIST :" + this.delimiter);
        StringBuilder comPilation = new StringBuilder();
        CiChUfCIFHeaderDao dao = new CiChUfCIFHeaderDao(session);
        CiChUfCIFDetailsDao daoD = new CiChUfCIFDetailsDao(session);

        CiChufCIFHeader mCIF = new CiChufCIFHeader();
        getLogger().info("EARLY HEADER BEGIN");
        getLogger().debug("IDBATCH :" + idBatch);
        ErrorCodeDao errDao = new ErrorCodeDao(session);

        StringBuilder details = new StringBuilder();

        String dateF = df.format(Date.valueOf(detail[1]));
        String dateFcomp = df.format(Date.valueOf(dao.bankMast()));
        getLogger().debug(dateF + " " + dateFcomp);

        try {
            counter = Integer.parseInt(dao.getMax(this.idBatch));
            realcount = counter + 1;
            mCIF.setRecordId(counter + 1);
        } catch (Exception e) {
            getLogger().debug("NO VALUE in TMP" + e);
            counter = 1;
            realcount = counter;
            mCIF.setRecordId(counter);
        }

        if (!dao.get(idBatch).isEmpty()) {
            try {
                getLogger().info(errDao.get(ErrorPersister.DuplicateCIF, this.idBatch, StatusDefinition.PROCESS));
            } catch (SQLException ex) {
                getLogger().info("ERROR :" + ex);
                getLogger().info("DUPLICATE ID BATCH");
            }
            result = ErrorPersister.DuplicateCIF;
            errFlag = 1;
            // duplicate ID batch
        } else {
            getLogger().info("HEADER BEGIN");
            // get DATA
            for (Integer i = 0; i < detail.length; i++) {
                details.append(detail[i]).append(this.delimiter);
                try {
                    this.flags = daoD.getMandatory(0, i + 1, StatusDefinition.IN,this.namTemplate);
                } catch (Exception e) {
                    getLogger().info("EXCEPTION :" + e);
                    this.flags = StatusDefinition.YES;
                }
                getLogger().debug("DETAILS :" + details + " FLAGS :" + this.flags);
                if (StatusDefinition.YES.equals(this.flags)) {
                    mandatory[i] = StatusDefinition.YES;
                } else {
                    mandatory[i] = StatusDefinition.NO;
                }
                getLogger().debug("FINISH INSERT MANDATORY");
                if (PropertyPersister.DEFAULT_REPLACEMENT.equals(detail[i])) {
                    if (StatusDefinition.YES.equals(mandatory[i])) {
                        this.errValue = StatusDefinition.YES;
                        //this.errIDs.append(ErrorPersister.Mandatorynull).append("/");
                        getLogger().debug(errIDs.toString());
                    }
                }
            }
            getLogger().debug("Rec_TYPE : " + detail[0]);
            mCIF.setRecType(detail[0]);
            getLogger().debug(dao.bankMast() + " " + detail[1]);

            if (!dateF.equals(dateFcomp)) {
                if (StatusDefinition.YES.equals(mandatory[1])) {
                    this.errValue = StatusDefinition.YES;
                    this.errIDs.append(ErrorPersister.invDateCIF).append("/");
                    this.errVal.append(":").append(detail[1]).append("/");
                }
                try {
                    getLogger().info(errDao.get(ErrorPersister.invDateCIF, detail[1], StatusDefinition.PROCESS));
                } catch (SQLException ex) {
                    getLogger().info("ERROR :" + ex);
                }
            }
            try {
                mCIF.setFileDate(Date.valueOf(detail[1]));
            } catch (Exception e) {
                // failed to cast into date
                if (StatusDefinition.YES.equals(mandatory[1])) {
                    this.errValue = StatusDefinition.YES;
                    this.errIDs.append(ErrorPersister.convDateFailedCIF).append("/");
                    this.errVal.append(":").append(detail[1]).append("/");
                }
                try {
                    getLogger().info(errDao.get(ErrorPersister.convDateFailedCIF, detail[1], StatusDefinition.PROCESS));
                } catch (SQLException ex) {
                    getLogger().info("ERROR :" + ex);
                }
            }
            getLogger().debug("FileDate : " + detail[1]);
            try {
                hTotal = Integer.parseInt(detail[2]);
                if (!hTotal.equals(total)) {
                    if (StatusDefinition.YES.equals(mandatory[2])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.recordNotsame).append("/");
                        this.errVal.append(":").append(detail[2]).append("/");
                    }
                    try {
                        getLogger().debug(errDao.get(ErrorPersister.recordNotsame, detail[2], StatusDefinition.PROCESS));
                        //errFlag = 1;
                    } catch (SQLException ex) {
                        getLogger().info("ERROR SQL :" + ex);
                        getLogger().debug("INVALID NUMBER :" + hTotal + "|" + total);
                    }
                }
                mCIF.setRecCount(hTotal);
            } catch (NumberFormatException numberFormatException) {
                // invalid number row
                this.errIDs.append(ErrorPersister.convIntCIF).append("/");
                this.errVal.append(":").append(detail[2]).append("/");
                getLogger().info("ERROR NUMBER :" + numberFormatException);
            }
            getLogger().info("Total Record : " + detail[2]);
            mCIF.setFileName(FilenameUtils.getName(FileName));
            mCIF.setDtmReceive(SchedulerUtil.getTime());
            mCIF.setFlgUpdate(detail[3]);
            mCIF.setBatchId(this.idBatch);
            mCIF.setData(details.toString());
            getLogger().info("READY TO INSERT CIF HEADER..");
            if (StatusDefinition.YES.equals(errValue)) {
                mCIF.setRespCode(1);
                try {
                    Integer errCount = this.errIDs.toString().split("/").length;
                    Integer valCount = this.errVal.toString().split("/").length;
                    String[] error = new String[errCount];
                    String[] valueS = new String[valCount];
                    error = this.errIDs.toString().split("/");
                    valueS = this.errVal.toString().split("/");
                    getLogger().debug("ERROR SUM :" + error.length);
                    StringBuilder str = new StringBuilder();
                    Integer i;
                    for (i = 0; i < errCount; i++) {
                        str.append(errDao.get(error[i], valueS[i], StatusDefinition.RESPONSE)).append("/");
                        getLogger().debug(error[i]);
                    }
                    getLogger().info("ERROR MASSAGE:" + str);
                    this.errIDs = new StringBuilder();
                    this.errVal = new StringBuilder();
                    mCIF.setComments(str.toString() + "ERROR SUM :" + i);
                } catch (SQLException ex) {
                    mCIF.setComments(comPilation.append("ERROR GENERATED MASSAGE").append("/").toString());
                    getLogger().info("ERROR :" + ex);
                }
            } else {
                mCIF.setRespCode(0);
            }
            try {
                dao.insert(mCIF);
                tx.commit();
                this.tx = this.session.beginTransaction();
            } catch (Exception e) {
                getLogger().info("EXCEPTION CIF :" + e);
                errFlag = 1;
                tx.rollback();
            }
            getLogger().debug("ERRFLAG :" + errFlag);
            if (errFlag == 1) {
                result = ErrorPersister.HeaderRejectedCIF; // Reject
            } else {
                result = ErrorPersister.HeaderInserted; // Success
            }
        }
        return result;
    }

    protected String DetailsCIF(String[] detail) {
        Integer dTotal = 0;
        Integer count = 0;
        Integer errFlag = 0;
        String result = null;
        String flg_cust = null;
        Integer counter = 0;
        Integer realcount = 0;

        String[] mandatory = new String[detail.length];
        this.errValue = StatusDefinition.NO;
        getLogger().debug("DELIMITER EXIST :" + this.delimiter);
        StringBuilder comPilation = new StringBuilder();
        CiChUfCIFHeaderDao hDao = new CiChUfCIFHeaderDao(session);
        CiChUfCIFDetailsDao dao = new CiChUfCIFDetailsDao(session);
        CiChUfCIFDetails mCIF = new CiChUfCIFDetails();
        StringBuilder details = new StringBuilder();
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        getLogger().debug("IDBATCH :" + idBatch);

        try {
            counter = Integer.parseInt(dao.getMax(this.idBatch));
            realcount = counter + 1;
            mCIF.setRecord_id_details(counter + 1);
        } catch (Exception e) {
            getLogger().info("NO VALUE in TMP" + e);
            counter = 1;
            realcount = counter;
            mCIF.setRecord_id_details(counter);
        }

        if (!dao.getDetails(this.idBatch, realcount).isEmpty()) {
            try {
                // duplicate ID batch and record number
                getLogger().info(errDao.get(ErrorPersister.DuplicateRecordCIF, this.idBatch, StatusDefinition.PROCESS));
            } catch (SQLException ex) {
                getLogger().info("ERROR SQL :" + ex);
                getLogger().info("DUPLICATE Exact Record");
            }
            result = ErrorPersister.DuplicateRecordCIF;
            errFlag = 1;
        } else {
            List<CiChufCIFHeader> hCIF = new ArrayList();
            hCIF = hDao.get(this.idBatch);

            if (hCIF.get(0).getRespCode() == 1) {
                // Reject
                try {
                    // sent error to fixQ
                    getLogger().info(errDao.get(ErrorPersister.recordNotsame, this.idBatch, StatusDefinition.PROCESS));
                } catch (SQLException ex) {
                    getLogger().info("ERROR HEADERCOUNT :" + ex);
                    getLogger().info("HEADER COUNT MISSMATCH");
                }
                result = ErrorPersister.recordNotsame;
                errFlag = 1;
            } else {
                getLogger().info("PROCESSING");
                for (Integer i = 0; i < detail.length; i++) {
                    details.append(detail[i]).append(this.delimiter);
                    try {
                        getLogger().debug(i + 1);
                        this.flags = dao.getMandatory(1, i + 1, StatusDefinition.DONE,this.namTemplate);
                    } catch (Exception e) {
                        getLogger().info("EXCEPTION :" + this.flags + " " + e);
                        this.flags = StatusDefinition.YES;
                    }
                    getLogger().debug("DETAILS :" + details + " FLAGS :" + this.flags);
                    if (StatusDefinition.YES.equals(flags)) {
                        mandatory[i] = StatusDefinition.YES;
                    } else {
                        mandatory[i] = StatusDefinition.NO;
                    }
                    if (PropertyPersister.DEFAULT_REPLACEMENT.equals(detail[i])) {
                        if (StatusDefinition.YES.equals(mandatory[i])) {
                            this.errValue = StatusDefinition.YES;
                            getLogger().info("MANDATORY VALUE NULL");
                            //this.errIDs.append(ErrorPersister.Mandatorynull).append("/");
                            getLogger().debug(errIDs.toString());
                        }
                        detail[i] = "";
                    }
                }
                getLogger().debug("RecTYPE Detail :" + detail[0]);

                mCIF.setRec_type(detail[0]);
                try {
                    dTotal = Integer.parseInt(detail[1]);
                    try {
                        count = Integer.parseInt(dao.CodCust_Val(dTotal));
                        if (count != 0) {
                            mCIF.setCod_cust(dTotal);
                        } else {
                            if (StatusDefinition.YES.equals(mandatory[1])) {
                                this.errValue = StatusDefinition.YES;
                                this.errIDs.append(ErrorPersister.noCust).append("/");
                                this.errVal.append(":").append(detail[1]).append("/");
                            }
                            getLogger().info("NO COSTUMER FOUND");
                        }
                    } catch (NumberFormatException numberFormatException) {
                        // failed to get Cust_ID
                        getLogger().info("ERROR NUMBER CODCUST LV2:" + numberFormatException);
                        this.errIDs.append(ErrorPersister.convIntCIF).append("/");
                        this.errVal.append(":").append(detail[1]).append("/");
                        //errValue = "13129";
                    }
                } catch (NumberFormatException numberFormatException) {
                    // not valid number
                    getLogger().info("ERROR NUMBER CODCUST LV1:" + numberFormatException);
                    this.errIDs.append(ErrorPersister.convIntCIF).append("/");
                    this.errVal.append(":").append(detail[1]).append("/");
                }
                try {
                    count = Integer.parseInt(detail[2]);
                    count = Integer.parseInt(dao.Agama_Val(count.toString()));
                    if (count != 0) {
                        mCIF.setAgama(detail[2]);
                    } else {
                        if (StatusDefinition.YES.equals(mandatory[2])) {
                            this.errValue = StatusDefinition.YES;
                            this.errIDs.append(ErrorPersister.noAgama).append("/");
                            this.errVal.append(":").append(detail[2]).append("/");
                        }
                        try {
                            getLogger().info(errDao.get(ErrorPersister.noAgama, detail[2], StatusDefinition.PROCESS));
                        } catch (SQLException ex) {
                            getLogger().info("ERROR SQL :" + ex);
                            getLogger().info("AGAMA NOT FOUND");
                        }
                    }
                } catch (NumberFormatException numberFormatException) {
                    // not valid number
                    getLogger().info("ERROR NUMBER AGAMA :" + numberFormatException);
                    this.errIDs.append(ErrorPersister.convIntCIF).append("/");
                    this.errVal.append(":").append(detail[2]).append("/");
                }
                try {
                    count = Integer.parseInt(detail[3]);
                    count = Integer.parseInt(dao.lovMast_val(count.toString(), PropertyPersister.tagTXNPerbulan));
                    if (count != 0) {
                        mCIF.setTxn_Value(detail[3]);
                    } else {
                        getLogger().info("TXN VALUE not FOUND");
                        if (StatusDefinition.YES.equals(mandatory[3])) {
                            this.errValue = StatusDefinition.YES;
                            this.errIDs.append(ErrorPersister.noTXN).append("/");
                            this.errVal.append(":").append(detail[3]).append("/");
                        }
                    }
                } catch (NumberFormatException numberFormatException) {
                    getLogger().info("ERROR NUMBER TXN :" + numberFormatException);
                    this.errIDs.append(ErrorPersister.noTXN).append("/");
                    this.errVal.append(":").append(detail[3]).append("/");
                }
                try {
                    count = Integer.parseInt(detail[7]);
                    count = Integer.parseInt(dao.Occup_Val(count.toString()));
                    if (count == 0) {
                        getLogger().info("JOBS NOT REGISTERED");
                        if (StatusDefinition.YES.equals(mandatory[7])) {
                            this.errValue = StatusDefinition.YES;
                            this.errIDs.append(ErrorPersister.noJobs).append("/");
                            this.errVal.append(":").append(detail[7]).append("/");
                        }
                    }
                    mCIF.setPekerjaan_Spouse_Ortu(detail[7]);

                } catch (NumberFormatException numberFormatException) {
                    getLogger().info("ERROR NUMBER JOBS :" + numberFormatException);
                    this.errIDs.append(ErrorPersister.noJobs).append("/");
                    this.errVal.append(":").append(detail[7]).append("/");
                }
                try {
                    dTotal = Integer.parseInt(detail[1]);
                    try {
                    flg_cust = dao.getFlagCust(dTotal);
                        getLogger().debug("FLAGNYA : " + flg_cust);
                    } catch (Exception e) {
                        getLogger().debug("FLAGNULL : " + e,e);
                    }
                    if (PropertyPersister.PERSONALPAJAK.equals(flg_cust) || PropertyPersister.PERSONALNONPAJAK.equals(flg_cust)) {
                        if (StatusDefinition.YES.equals(mandatory[17]) && ("".equals(detail[17]))) {
                            this.errValue = StatusDefinition.YES;
                            this.errIDs.append(ErrorPersister.noMother).append("/");
                            this.errVal.append(":").append(detail[17]).append("/");
                            getLogger().info("MOTHER NAME IS MANDATORY");
                        }
                    }
                } catch (NumberFormatException numberFormatException) {
                    if (StatusDefinition.YES.equals(mandatory[1])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.noCust).append("/");
                        this.errVal.append(":").append(detail[1]).append("/");
                    }
                    getLogger().info("NO COSTUMER FOUND :" + numberFormatException);
                }
                if (!(detail[19]).matches(StatusDefinition.EMAILONLY)) {
                    if (StatusDefinition.YES.equals(mandatory[19])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.noEmail).append("/");
                        this.errVal.append(":").append(detail[19]).append("/");
                        getLogger().info("EMAIL NOT VALID FORMAT");
                    }
                }
                if (!(detail[10]).matches(StatusDefinition.PHONENUMBER)) {
                    if (StatusDefinition.YES.equals(mandatory[10])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.invHomePhoneCountry).append("/");
                        this.errVal.append(":").append(detail[10]).append("/");
                        getLogger().info("HOME COUNTRY CODE INVALID");
                    }
                }
                if (!(detail[11]).matches(StatusDefinition.PHONENUMBER)) {
                    if (StatusDefinition.YES.equals(mandatory[11])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.invHomePhoneArea).append("/");
                        this.errVal.append(":").append(detail[11]).append("/");
                        getLogger().info("HOME AREA CODE INVALID");
                    }
                }
                if (!(detail[12]).matches(StatusDefinition.PHONENUMBER)) {
                    if (StatusDefinition.YES.equals(mandatory[12])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.invHomePhone).append("/");
                        this.errVal.append(":").append(detail[12]).append("/");
                        getLogger().info("HOME PHONE INVALID");
                    }
                }
                if (!(detail[13]).matches(StatusDefinition.PHONENUMBER)) {
                    if (StatusDefinition.YES.equals(mandatory[13])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.invOfficePhoneCountry).append("/");
                        this.errVal.append(":").append(detail[13]).append("/");
                        getLogger().info("OFFICE COUNTRY CODE INVALID");
                    }
                }
                if (!(detail[14]).matches(StatusDefinition.PHONENUMBER)) {
                    if (StatusDefinition.YES.equals(mandatory[14])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.invOfficePhoneArea).append("/");
                        this.errVal.append(":").append(detail[14]).append("/");
                        getLogger().info("OFFICE AREA CODE INVALID");
                    }
                }
                if (!(detail[15]).matches(StatusDefinition.PHONENUMBER)) {
                    if (StatusDefinition.YES.equals(mandatory[15])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.invOfficePhone).append("/");
                        this.errVal.append(":").append(detail[15]).append("/");
                        getLogger().info("OFFICE PHONE INVALID");
                    }
                }
                mCIF.setSumber_Dana_Lain(detail[4]);
                mCIF.setFax_No(detail[5]);
                mCIF.setNama_Spouse_Ortu(detail[6]);
                mCIF.setJabatan_Spouse_Ortu(detail[8]);
                mCIF.setKantor_Spouse_Ortu(detail[9]);
                mCIF.setHome_No_Country(detail[10]);
                mCIF.setHome_No_Area(detail[11]);
                mCIF.setHome_No_Phone(detail[12]);
                mCIF.setOffice_No_Country(detail[13]);
                mCIF.setOffice_No_Area(detail[14]);
                mCIF.setOffice_No_Phone(detail[15]);
                mCIF.setExt_Office_No(detail[16]);
                mCIF.setNama_ibu_kandung(detail[17]);
                mCIF.setNama_alias(detail[18]);
                mCIF.setE_Statement_Email(detail[19]);
                mCIF.setE_Statement_Branch(detail[20]);
                mCIF.setData(details.toString());
                mCIF.setId_Batch(this.idBatch);
                //mCIF.setFileName(FileName);
                getLogger().info("SET DIRECTION ->");
                if (StatusDefinition.YES.equals(this.errValue)) {
                    getLogger().info("RECORD REJECT");
                    mCIF.setStatus(StatusDefinition.REJECT);
                    mCIF.setRespcode(1);
                    try {
                        Integer errCount = this.errIDs.toString().split("/").length;
                        Integer valCount = this.errVal.toString().split("/").length;
                        String[] error = new String[errCount];
                        String[] valueS = new String[valCount];
                        error = this.errIDs.toString().split("/");
                        valueS = this.errVal.toString().split("/");
                        getLogger().debug("ERROR SUM :" + error.length);
                        StringBuilder str = new StringBuilder();
                        Integer i;
                        for (i = 0; i < errCount; i++) {
                            str.append(errDao.get(error[i], valueS[i], StatusDefinition.RESPONSE)).append("/");
                            getLogger().debug(error[i]);
                        }
                        getLogger().info("ERROR MASSAGE:" + str);
                        this.errIDs = new StringBuilder();
                        this.errVal = new StringBuilder();
                        mCIF.setComment_resp(str.toString());
                    } catch (SQLException ex) {
                        mCIF.setComment_resp(comPilation.append(ex).append("/").toString());
                        getLogger().info("ERROR :" + ex,ex);
                    }
                } else {
                    mCIF.setStatus(StatusDefinition.UNAUTHORIZED);
                    mCIF.setRespcode(0);
                }

                mCIF.setDtmRequest(SchedulerUtil.getTime());
                getLogger().info("READY TO INSERT CIF DETAILS");
                try {
                    dao.insert(mCIF);
                    tx.commit();
                    this.tx = this.session.beginTransaction();
                } catch (Exception e) {
                    getLogger().info("EXCEPTION CIF :" + e);
                    errFlag = 1;
                    tx.rollback();
                    dao.deleteRecord(this.idBatch);
                }
                getLogger().debug("ERRFLAG :" + errFlag);
                if (errFlag == 1) {
                    result = ErrorPersister.DetailProcessFailedCIF; // Reject
                } else {
                    result = ErrorPersister.DetailProcessSuccessCIF; // Success
                }
            }
        }
        return result;
    }

    protected String HeaderCASA(String[] detail, Integer total) {
        Integer hTotal;
        Integer counter;
        Integer realcount = 0;

        String result = null;
        Integer errFlag = 0; // set 1 if mandatory field invalid
        getLogger().info("PROCESS HEADER CASA..");

        String[] mandatory = new String[detail.length];
        this.errValue = StatusDefinition.NO;
        getLogger().debug("DELIMITER EXIST :" + this.delimiter);
        String FileName = null;
        try {
            FileName = context.get(MapKey.param5).toString();
            getLogger().info("FILENAME :" + FileName);
        } catch (Exception e) {
            getLogger().info("NO FILENAME ??");
            FileName = context.get(MapKey.fileName).toString();
        }
        getLogger().debug("IDBATCH :" + idBatch);
        StringBuilder comPilation = new StringBuilder();
        CiChufCASAHeaderDao dao = new CiChufCASAHeaderDao(session);
        CiChUfCASADetailsDao daoC = new CiChUfCASADetailsDao(session);
        CiChUfCASAHeader mCASA = new CiChUfCASAHeader();
        ErrorCodeDao errDao = new ErrorCodeDao(session);

        SimpleDateFormat df = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);
        String dateF = df.format(Date.valueOf(detail[1]));
        String dateFcomp = df.format(Date.valueOf(dao.bankMast()));
        getLogger().debug(dateF + " " + dateFcomp);

        try {
            counter = Integer.parseInt(dao.getMax(this.idBatch));
            realcount = counter + 1;
            mCASA.setRecordId(counter + 1);
        } catch (Exception e) {
            getLogger().info("NO VALUE in TMP" + e);
            counter = 1;
            realcount = counter;
            mCASA.setRecordId(counter);
        }

        StringBuilder details = new StringBuilder();
        if (!dao.get(idBatch).isEmpty()) {
            try {
                // sent error to fixQ
                getLogger().info(errDao.get(ErrorPersister.DuplicateCASA, this.idBatch, StatusDefinition.PROCESS));
            } catch (SQLException ex) {
                getLogger().info("ERROR SQL :" + ex);
                getLogger().info("DUPLICATE CASA");
            }
            result = ErrorPersister.DuplicateCASA;
            errFlag = 1;
        } else {
            for (Integer i = 0; i < detail.length; i++) {
                details.append(detail[i]).append(this.delimiter);
                try {
                    this.flags = daoC.getMandatory(0, i + 1, StatusDefinition.IN,this.namTemplate);
                } catch (Exception e) {
                    getLogger().info("EXCEPTION :" + this.flags + " " + e);
                    this.flags = StatusDefinition.YES;
                }
                getLogger().info("DETAILS :" + details + " FLAGS :" + this.flags);
                if (StatusDefinition.YES.equals(flags)) {
                    mandatory[i] = StatusDefinition.YES;
                } else {
                    mandatory[i] = StatusDefinition.NO;
                }
                if (PropertyPersister.DEFAULT_REPLACEMENT.equals(detail[i])) {
                    if (StatusDefinition.YES.equals(mandatory[i])) {
                        this.errValue = StatusDefinition.YES;
                        //this.errIDs.append(ErrorPersister.MandatoryNullCASA).append("/");
                        getLogger().debug(errIDs.toString());
                    }
                }
            }
            mCASA.setRecType(detail[0]);
            if (!dateF.equals(dateFcomp)) {
                if (StatusDefinition.YES.equals(mandatory[1])) {
                    this.errValue = StatusDefinition.YES;
                    this.errIDs.append(ErrorPersister.invDateCASA).append("/");
                    this.errVal.append(":").append(detail[1]).append("/");
                    mCASA.setRespCode(1);
                }
                try {
                    getLogger().info(errDao.get(ErrorPersister.invDateCASA, detail[1], StatusDefinition.PROCESS));
                } catch (SQLException ex) {
                    getLogger().info("ERROR SQL :" + ex);
                    getLogger().info("INVALID DATE");
                }
            }    // cod_cust
            mCASA.setFileDate(Date.valueOf(detail[1]));
            try {
                hTotal = Integer.parseInt(detail[2]);
                if (!hTotal.equals(total)) {
                    if (StatusDefinition.YES.equals(mandatory[2])) {
                        this.errValue = StatusDefinition.YES;
                        this.errIDs.append(ErrorPersister.recordNotsameCASA).append("/");
                        this.errVal.append(":").append(detail[2]).append("/");
                        mCASA.setRespCode(1);
                    }
                    try {
                        getLogger().info(errDao.get(ErrorPersister.recordNotsameCASA, detail[2], StatusDefinition.PROCESS));
                    } catch (SQLException ex) {
                        getLogger().info("ERROR SQL :" + ex);
                        getLogger().info("INVALID RECORD WITH HEADER");
                    }

                }    // cod_cust
                mCASA.setRecCount(hTotal);
            } catch (NumberFormatException numberFormatException) {
                // invalid number
                getLogger().info("ERROR NUMBER :" + numberFormatException);
                this.errIDs.append(ErrorPersister.convIntCASA).append("/");
                this.errVal.append(":").append(detail[2]).append("/");
            }
            mCASA.setFlgUpdate(detail[3]);
            if (StatusDefinition.YES.equals(this.errValue)) {
                mCASA.setRespCode(1);
                try {
                    Integer errCount = this.errIDs.toString().split("/").length;
                    Integer valCount = this.errVal.toString().split("/").length;
                    String[] error = new String[errCount];
                    String[] valueS = new String[valCount];
                    error = this.errIDs.toString().split("/");
                    valueS = this.errVal.toString().split("/");
                    getLogger().debug("ERROR SUM :" + error.length);
                    StringBuilder str = new StringBuilder();
                    Integer i;
                    for (i = 0; i < errCount; i++) {
                        str.append(errDao.get(error[i], valueS[i], StatusDefinition.RESPONSE)).append("/");
                        getLogger().debug(error[i]);
                    }
                    getLogger().info("ERROR MASSAGE:" + str);
                    this.errIDs = new StringBuilder();
                    this.errVal = new StringBuilder();
                    mCASA.setComments(str.toString());
                } catch (SQLException ex) {
                    mCASA.setComments(comPilation.append(ex).append("/").toString());
                    getLogger().info("ERROR :" + ex);
                }
            } else {
                mCASA.setRespCode(0);
            }
            mCASA.setDtmReceive(SchedulerUtil.getTime());
            mCASA.setFileName(FilenameUtils.getBaseName(FileName));
            mCASA.setBatchId(this.idBatch);
            mCASA.setData(details.toString());
            getLogger().info("READY TO INSERT CASA HEADER");
            try {
                dao.insert(mCASA);
                tx.commit();
                this.tx = this.session.beginTransaction();
            } catch (Exception e) {
                errFlag = 1;
                getLogger().info("ERROR :" + e);
                tx.rollback();
            }
            getLogger().debug("ERRFLAG :" + errFlag);
            if (errFlag == 1) {
                result = ErrorPersister.HeaderRejectedCASA; // Reject
            } else {
                result = ErrorPersister.HeaderInsertedCASA; // Success
            }
        }
        return result;
    }

    protected String DetailsCASA(String[] detail) {
        String result = null;
        String acct;
        String tujuanRek;
        Integer count = 0;
        Integer realcount = 0;

        Integer counter;
        String flag = null;
        String stmnt = null;
        this.errValue = StatusDefinition.NO;
        getLogger().debug("DELIMITER EXIST :" + this.delimiter);
        String CIF = null;
        Integer errFlag = 0;

        String[] mandatory = new String[detail.length];

        StringBuilder comPilation = new StringBuilder();
        CiChufCASAHeaderDao hDao = new CiChufCASAHeaderDao(session);
        CiChUfCASADetailsDao dao = new CiChUfCASADetailsDao(session);
        CiChUfCASADetails mCASA = new CiChUfCASADetails();
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        StringBuilder details = new StringBuilder();

        getLogger().debug("IDBATCH :" + idBatch);

        try {
            counter = Integer.parseInt(dao.getMax(this.idBatch));
            realcount = counter + 1;
            mCASA.setRecord_id(counter + 1);
        } catch (Exception e) {
            getLogger().info("NO VALUE in TMP" + e);
            counter = 1;
            realcount = counter;
            mCASA.setRecord_id(counter);
        }

        if (!dao.getDetails(idBatch, realcount).isEmpty()) {
            try {
                // sent error to fixQ
                getLogger().info(errDao.get(ErrorPersister.DuplicateRecordCASA, this.idBatch, StatusDefinition.PROCESS));
            } catch (SQLException ex) {
                getLogger().info("ERROR SQL :" + ex);
                getLogger().info("DUPLICATE EXACT RECORD");
            }
            result = ErrorPersister.DuplicateRecordCASA;
            errFlag = 1;
        } else {
            List<CiChUfCASAHeader> hCASA = new ArrayList();
            hCASA = hDao.get(this.idBatch);

            if (hCASA.get(0).getRespCode() == 1) {
                // Reject
                try {
                    // sent error to fixQ
                    getLogger().info(errDao.get(ErrorPersister.recordNotsameCASA, this.idBatch, StatusDefinition.PROCESS));
                } catch (SQLException ex) {
                    getLogger().info("ERROR HEADERCOUNT :" + ex);
                    getLogger().info("HEADER COUNT MISSMATCH");
                }
                result = ErrorPersister.recordNotsameCASA;
                errFlag = 1;
            } else {
                for (Integer i = 0; i < detail.length; i++) {
                    details.append(detail[i]).append(this.delimiter);
                    try {
                        getLogger().debug(i + 1);
                        this.flags = dao.getMandatory(1, i + 1, StatusDefinition.DONE, namTemplate);
                    } catch (Exception e) {
                        getLogger().info("EXCEPTION :" + this.flags + " " + e);
                        this.flags = StatusDefinition.YES;
                    }
                    getLogger().debug("DETAILS :" + details + " FLAGS :" + this.flags);
                    if (StatusDefinition.YES.equals(flags)) {
                        mandatory[i] = StatusDefinition.YES;
                    } else {
                        mandatory[i] = StatusDefinition.NO;
                    }
                    if (PropertyPersister.DEFAULT_REPLACEMENT.equals(detail[i])) {
                        if (StatusDefinition.YES.equals(mandatory[i])) {
                            this.errValue = StatusDefinition.YES;
                            //this.errIDs.append(ErrorPersister.MandatoryNullCASA).append("/");
                            getLogger().debug(errIDs.toString());
                        }
                        detail[i] = "";
                    }
                }
                mCASA.setRec_Type(detail[0]);
                try {
                    count = Integer.parseInt(dao.Codacct_Val(detail[1].toString()));
                    if (count == 0) {
                        if (StatusDefinition.YES.equals(mandatory[1])) {
                            this.errValue = StatusDefinition.YES;
                            this.errIDs.append(ErrorPersister.noAcct).append("/");
                            this.errVal.append(":").append(detail[1]).append("/");
                        }
                        try {
                            getLogger().info(errDao.get(ErrorPersister.noAcct, detail[1], StatusDefinition.PROCESS));
                        } catch (SQLException ex) {
                            getLogger().info("ERROR SQL :" + ex);
                            getLogger().info("COD ACCT NO NOT FOUND");
                        }
                    }
                    mCASA.setCod_acct_no(detail[1]); // cod acct
                } catch (NumberFormatException numberFormatException) {
                    // Count Result not number
                    getLogger().info("ERROR NUMBER CODACCT:" + numberFormatException);
                    this.errIDs.append(ErrorPersister.convIntCASA).append("/");
                    this.errVal.append(":").append(detail[1]).append("/");
                }
                try {
                    count = Integer.parseInt(dao.lovMast_val(detail[2], PropertyPersister.tagTujuanRek));
                    if (count == 0) {
                        if (StatusDefinition.YES.equals(mandatory[2])) {
                            this.errValue = StatusDefinition.YES;
                            this.errIDs.append(ErrorPersister.noTujuan).append("/");
                            this.errVal.append(":").append(detail[2]).append("/");
                        }
                        try {
                            getLogger().info(errDao.get(ErrorPersister.noTujuan, detail[1], StatusDefinition.PROCESS));
                        } catch (SQLException ex) {
                            getLogger().info("ERROR : " + ex);
                            getLogger().info("Not Valid Tujuan rekening");
                        }
                    }
                    mCASA.setTujuan_rek(detail[2]); // tujuan rekening 
                } catch (NumberFormatException numberFormatException) {
                    // Count Result not number
                    getLogger().info("ERROR NUMBER TUJUANREK:" + numberFormatException);
                    this.errIDs.append(ErrorPersister.convIntCASA).append("/");
                    this.errVal.append(":").append(detail[2]).append("/");
                }
                try {
                    count = Integer.parseInt(dao.lovMast_val(detail[3], PropertyPersister.tagSumberDanaRek));
                    if (count == 0) {
                        if (StatusDefinition.YES.equals(mandatory[3])) {
                            this.errValue = StatusDefinition.YES;
                            this.errIDs.append(ErrorPersister.nosumberDana).append("/");
                            this.errVal.append(":").append(detail[3]).append("/");
                        }
                        try {
                            getLogger().info(errDao.get(ErrorPersister.nosumberDana, detail[3], StatusDefinition.PROCESS));
                        } catch (SQLException ex) {
                            getLogger().info("ERROR :" + ex);
                            getLogger().info("Not Valid Sumber Dana Rekening");
                        }
                    }
                    mCASA.setSumber_dana_rek(detail[3]); // sumber dana rekening
                } catch (NumberFormatException numberFormatException) {
                    // Count Result not number
                    getLogger().info("ERROR NUMBER SUMBERDANA:" + numberFormatException);
                    this.errIDs.append(ErrorPersister.convIntCASA).append("/");
                    this.errVal.append(":").append(detail[3]).append("/");
                }
                mCASA.setMedia_mutasi(detail[4]);
                mCASA.setKepemilikan(detail[5]);
                mCASA.setEmail_Statement(detail[6]);
                mCASA.setBranch_Statement(detail[7]);
                if (StatusDefinition.YES.equals(errValue)) {
                    mCASA.setStatus(StatusDefinition.REJECT);
                    mCASA.setRespcode(1);
                    try {
                        Integer errCount = this.errIDs.toString().split("/").length;
                        Integer valCount = this.errVal.toString().split("/").length;
                        String[] error = new String[errCount];
                        String[] valueS = new String[valCount];
                        error = this.errIDs.toString().split("/");
                        valueS = this.errVal.toString().split("/");
                        getLogger().debug("ERROR SUM :" + error.length);
                        StringBuilder str = new StringBuilder();
                        Integer i;
                        for (i = 0; i < errCount; i++) {
                            str.append(errDao.get(error[i], valueS[i], StatusDefinition.RESPONSE)).append("/");
                            getLogger().debug(error[i]);
                            getLogger().debug(valueS[i]);
                        }
                        getLogger().info("ERROR MASSAGE:" + str);
                        this.errIDs = new StringBuilder();
                        this.errVal = new StringBuilder();
                        mCASA.setComment_resp(str.toString());
                    } catch (SQLException ex) {
                        mCASA.setComment_resp(comPilation.append(ex).append("/").toString());
                        getLogger().info("ERROR :" + ex);
                    }
                } else {
                    mCASA.setStatus(StatusDefinition.UNAUTHORIZED);
                    mCASA.setRespcode(0);
                }
                mCASA.setId_Batch(this.idBatch);
                mCASA.setData(details.toString());

                mCASA.setDtmRequest(SchedulerUtil.getTime());
                getLogger().info("READY TO INSERT DETAIL CASA..");
                try {
                    dao.insert(mCASA);
                    tx.commit();
                    this.tx = this.session.beginTransaction();
                } catch (Exception e) {
                    errFlag = 1;
                    getLogger().info("ERROR :" + e);
                    tx.rollback();
                    dao.deleteRecord(this.idBatch);
                }
                if (errFlag == 1) {
                    result = ErrorPersister.DetailProcessFailedCASA; // Reject
                } else {
                    result = ErrorPersister.DetailProcessSuccessCASA; // Success
                }
            }
        }
        return result;
    }

    protected Map fieldInterface(String value, Integer total) {
        String[] detailCounter;
        Scanner headerScan;
        String getType;
        //String Value = "";
        Integer HeaderCount = 0;
        Integer detailsCount = 0;
        //Integer FooterCount = 0;
        //String flg_dtls = "";
        //String delimiter = "\\|";
        Integer count = 0;

        getLogger().info("BEGIN SPLIT INTERFACE");
        //FixPatternTable fixPattern = new FixPatternTable();
        this.namTemplate = context.get(MapKey.templateName).toString(); // filepatern
        String idScheduler = context.get(MapKey.idScheduler).toString();
        this.idBatch = context.get(MapKey.batchNo).toString(); // idBatch
        getLogger().debug("GETIDBATCH : " + idBatch);
        String FileName = context.get(MapKey.fileName).toString(); // filename
        this.schedID = Integer.parseInt(idScheduler); // idScheduler

        ErrorCodeDao errDao = new ErrorCodeDao(session);
        FixSchedulerXtractDao fixQdao = new FixSchedulerXtractDao(session);
        try {
            FixSchedulerXtract fixSchedulerXtract = fixQdao.get(namTemplate);
            this.xID = fixSchedulerXtract.getFixSchedulerPK().getIdScheduler();
        } catch (Exception e) {
            try {
                getLogger().info(errDao.get(ErrorPersister.idschedulerNRCIF, e.toString(), StatusDefinition.PROCESS));
            } catch (SQLException ex1) {
                getLogger().info("FAILED TO SET FIXQXTRACT " + ex1);
                this.respond = ErrorPersister.idschedulerNRCIF;
            }
        }
        getLogger().debug("Get Value : " + namTemplate + " " + idBatch + " " + FileName + " " + schedID);
        getLogger().debug("VALUE :" + value);
        headerScan = new Scanner(value);
        getType = String.valueOf(value.charAt(0));

        getLogger().debug("DELIMITER :" + this.delimiter);
        detailCounter = value.split("\\" + this.delimiter);
        headerScan.useDelimiter("\\" + this.delimiter);
        getLogger().info("FIELD LENGTH :" + detailCounter.length);
        if (this.patternMAtcherCIF == null) {
            this.patternMAtcherCIF = "";
        }
        if (this.patternMAtcherCASA == null) {
            this.patternMAtcherCASA = "";
        }
        //StringBuilder Hscanner = new StringBuilder();
        // 
        if (this.patternMAtcherCIF.equals(namTemplate)) {
            if (StatusDefinition.Headers.equals(detailCounter[0])) {
                getLogger().info("BEGIN HEADER (CIF) PROCESSING ..");

                this.respond = HeaderCIF(detailCounter, total);
                getLogger().info("SUCCESS PROCESSING HEADER WITH :" + respond);
                if (ErrorPersister.DuplicateCIF.equals(respond) || ErrorPersister.HeaderRejectedCIF.equals(respond)
                        || ErrorPersister.recordNotsame.equalsIgnoreCase(respond)) {
                    // fatal error- set FixQxtract
                    this.context.put(MapKey.errID, respond);
                    super.ErrFlag = StatusDefinition.YES;
                    //super.setFixQxtract();
                } else {
                    super.ErrFlag = StatusDefinition.NO;
                }
            } else if (StatusDefinition.Details.equals(detailCounter[0])) {
                //detailsCount = counter + detailsCount;
                getLogger().info("BEGIN DETAIL (CIF) PROCESSING ..");

                this.respond = DetailsCIF(detailCounter);
                if (ErrorPersister.DetailProcessFailedCIF.equals(respond) || ErrorPersister.DuplicateRecordCIF.equals(respond)) {
                    // fatal error- set FixQxtract
                    this.context.put(MapKey.errID, respond);
                    super.ErrFlag = StatusDefinition.YES;
                    //setFixQxtract();

                } else {
                    super.ErrFlag = StatusDefinition.NO;
                }
            }
        } else if (this.patternMAtcherCASA.equals(namTemplate)) {
            if (StatusDefinition.Headers.equals(detailCounter[0])) {
                getLogger().info("BEGIN HEADER (CASA) PROCESSING ..");

                this.respond = HeaderCASA(detailCounter, total);
                getLogger().info("SUCCESS PROCESSING HEADER WITH :" + respond);
                if (ErrorPersister.DuplicateCASA.equals(respond) || ErrorPersister.HeaderRejectedCASA.equals(respond)
                        || ErrorPersister.recordNotsameCASA.equals(respond)) {

                    this.context.put(MapKey.errID, respond);
                    super.ErrFlag = StatusDefinition.YES;

                } else {

                    super.ErrFlag = StatusDefinition.NO;

                }
            } else if (StatusDefinition.Details.equals(detailCounter[0])) {

                getLogger().info("BEGIN DETAIL (CASA) PROCESSING ..");

                this.respond = DetailsCASA(detailCounter);
                getLogger().info("SUCCESS PROCESSING DETAIL (CASA) WITH :" + respond);
                //detailsCount = counter + detailsCount;
                if (ErrorPersister.DetailProcessFailedCASA.equals(respond) || ErrorPersister.DuplicateRecordCASA.equals(respond)) {
                    // fatal error- set FixQxtract
                    this.context.put(MapKey.errID, respond);
                    super.ErrFlag = StatusDefinition.YES;
                    //setFixQxtract();
                } else {
                    super.ErrFlag = StatusDefinition.NO;
                }
            }
        }
        //this.innerContext.put("counter", counter);
        this.innerContext.put("comment_Code", this.respond);
        return innerContext;
    }

    @Override
    protected Integer valueInterface() {
        String namTemplate = context.get(MapKey.templateName).toString();
        String IDBatch = context.get(MapKey.batchNo).toString();
        Integer idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());
        getLogger().debug("IDBATCH : " + IDBatch + " " + idScheduler);
        CiChUfCIFDetailsDao cifDao = new CiChUfCIFDetailsDao(session);
        CiChUfCASADetailsDao casaDao = new CiChUfCASADetailsDao(session);

        Integer flag = 0;
        Integer Inst = 0;
        if (patternMAtcherCIF.equals(namTemplate)) {
            Inst = cifDao.runPackage(IDBatch, idScheduler);
            getLogger().info("FINISH 1st PACKAGE CIF..  Status :" + Inst);
            flag = CIFValidation();
        } else if (patternMAtcherCASA.equals(namTemplate)) {
            Inst = casaDao.runValidate(IDBatch, idScheduler);
            getLogger().info("FINISH 1st PACKAGE CASA..  Status :" + Inst);
            flag = CASAValidation();
        }
        return flag;
    }

    @Override
    protected Boolean process(String file, Integer conter) {
        BufferedReader br = null;
        String lineDetails;
        String errCount = null;
        String flag_fatal = null;
        namTemplate = context.get(MapKey.templateName).toString();
        String idScheduler = context.get(MapKey.idScheduler).toString();

        HashMap hMap = new HashMap(innerContext);
        hMap.put("counter", 0);
        hMap.put("comment_Code", "");

        ErrorCodeDao errDao = new ErrorCodeDao(session);
        FixPatternTable getdim = new FixPatternTable();

        // get Correct pattern
        Integer rowCount = 0;
        Scanner headerScan;
        
        /* Enhance Moving java parameter into Database Parameter*/
        
        String[] detailCounter;
        List getPattern = SchedulerUtil.getParameter(PropertyPersister.DFPattern,";");
        String localDelimiter = "\\|";

        List PatternCIF = SchedulerUtil.getParameter(getPattern.get(0).toString(),localDelimiter);
        List PatternCASA = SchedulerUtil.getParameter(getPattern.get(1).toString(),localDelimiter);
        //getLogger().info("BEGIN SCANNING FILE");
        
        if(PatternCIF.indexOf(namTemplate)==-1){
            if(PatternCASA.indexOf(namTemplate)>-1){
                patternMAtcherCASA = this.namTemplate;
                getLogger().info("Pattern CASA :" + patternMAtcherCASA);
            }
        } else {
            patternMAtcherCIF = this.namTemplate;
            getLogger().info("Pattern CIF :" + patternMAtcherCIF);
        }

        try {
            boolean hasFooter = false;
            br = new BufferedReader(new FileReader(file));
            while ((lineDetails = br.readLine()) != null) { // looping through end of file for scanning
                if (lineDetails.trim().equals(HEADER_LINE)) { // detect end of line
                    flag_fatal = StatusDefinition.NO;
                    hasFooter = true;
                    break;
                } else if (lineDetails.trim().equals("")) { // end of file
                    hasFooter = false;
                    flag_fatal = StatusDefinition.NO;
                    break;
                } else { // begin to read file
                    rowCount++;
                    for (Integer z = 0; z < HeaderCount.size(); z++) {
                        if (HeaderCount.get(z) == rowCount) {
                            // Header Flag
                            getLogger().info("HEADER FLAG ACTIVED");
                            getdim = errDao.get(Integer.parseInt(idScheduler), StatusDefinition.Headers, namTemplate, StatusDefinition.IN);
                            delimiter = getdim.getDelimiter();
                        }
                    }
                    for (Integer z = 0; z < DetailCount.size(); z++) {
                        if (DetailCount.get(z) == rowCount) {
                            // detailFlag
                            getLogger().info("DETAIL FLAG ACTIVED");
                            getdim = errDao.get(Integer.parseInt(idScheduler), StatusDefinition.Details, namTemplate, StatusDefinition.IN);
                            delimiter = getdim.getDelimiter();
                        }
                    }
                    //conter++;
                    headerScan = new Scanner(lineDetails);
                    lineDetails = (lineDetails.replaceAll("\\" + this.delimiter + "\\" + this.delimiter, "\\"
                            + this.delimiter + PropertyPersister.DEFAULT_REPLACEMENT + "\\" + this.delimiter)).replaceAll("\\" + this.delimiter + "\\" + this.delimiter, "\\" + this.delimiter + PropertyPersister.DEFAULT_REPLACEMENT + "\\" + this.delimiter);
                    detailCounter = lineDetails.split("\\" + this.delimiter);

                    getLogger().info(rowCount + ".Field Count :" + detailCounter.length);
                    getLogger().debug(lineDetails);
                    //headerScan.nextLine();
                    //headerScan.useDelimiter(delimiter);
                    getLogger().info("BEGIN SCANNING FILE");
                    // insert via dao non Package
                    try {
                        innerContext = fieldInterface(lineDetails, conter); // processing record
                        getLogger().debug("CHECK MAPPING : " + innerContext);
                        try {
                            //rowCount = Integer.parseInt(innerContext.get("counter").toString());
                            errCount = innerContext.get("comment_Code").toString();
                            getLogger().debug("counter :" + rowCount + " errCode :" + errCount);
                            if (ErrorPersister.HeaderRejectedCIF.equalsIgnoreCase(errCount) || ErrorPersister.DuplicateCIF.equalsIgnoreCase(errCount)
                                    || ErrorPersister.DetailProcessFailedCIF.equalsIgnoreCase(errCount) || ErrorPersister.DuplicateRecordCIF.equalsIgnoreCase(errCount)
                                    || ErrorPersister.HeaderRejectedCASA.equalsIgnoreCase(errCount) || ErrorPersister.DuplicateCASA.equalsIgnoreCase(errCount)
                                    || ErrorPersister.DetailProcessFailedCASA.equalsIgnoreCase(errCount) || ErrorPersister.DuplicateRecordCASA.equalsIgnoreCase(errCount)
                                    || ErrorPersister.recordNotsame.equalsIgnoreCase(errCount) || ErrorPersister.recordNotsameCASA.equalsIgnoreCase(errCount)) { // failed to process file
                                // failed insert sent to fixQ
                                flag_fatal = StatusDefinition.YES;
                                getLogger().info("ERROR:FATAL " + flag_fatal);
                                break;
                            } else {
                                flag_fatal = StatusDefinition.NO;
                                getLogger().info("SUCCES:FATAL " + flag_fatal);
                            }
                            getLogger().debug("Value :" + rowCount + " " + errCount);
                        } catch (NumberFormatException numberFormatException) {
                            // Error sent to Break
                            getLogger().info("ERROR : " + numberFormatException);
                            flag_fatal = StatusDefinition.YES;
                            break;
                        }
                    } catch (Exception exception) {
                        // Error sent to Break
                        getLogger().info("ERROR : " + exception);
                        flag_fatal = StatusDefinition.YES;
                        break;
                    }
                    // already inserted
                    // insert via package
                }
                if (rowCount > MAX_ROW_DETAILS) {
                    // lebih dari parameter
                    getLogger().info("Total Row Processed :" + rowCount);
                }
            }
            //setFixQxtract();
        } catch (Exception e) {
            getLogger().info(e,e);
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    getLogger().info("Exception :" + ex);
                    Logger.getLogger(DDFInterfaceValidation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (StatusDefinition.YES.equals(flag_fatal)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected FixQXtract registerQU() {
        //this.fixQXtract = new FixQXtract();
        getLogger().info("Set FixQXtract for Information to Sender VIA SFTP(U)");
        String FileName = context.get(MapKey.fileName).toString(); // filename
        String namTemplate = context.get(MapKey.templateName).toString();
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
        FixSchedulerXtract xtracT = fixSchedulerXtractDao.get(namTemplate);
        String patern = fixSchedulerXtractDao.get(namTemplate).getFilePattern();

        getLogger().debug("PATTERN :" + namTemplate);
        this.schedID = xtracT.getFixSchedulerPK().getIdScheduler();
        getLogger().debug("XTRACT :" + this.schedID);

        if (StatusDefinition.YES.equals(this.ErrFlag)) {
            this.fixQXtract.setParam1(StatusDefinition.ERR); // flag Error
            this.fixQXtract.setReason(this.errIDs.toString());
            this.fixQXtract.setParam3(this.errValue); // flag Error
            this.fixQXtract.setParam4(namTemplate);
            this.fixQXtract.setParam5(errFile);
        } else {
            this.fixQXtract.setParam1(StatusDefinition.SUCCESS); // flag Error
            this.fixQXtract.setParam4(namTemplate);
            this.fixQXtract.setParam5(FileName + "." + patern + ".resp");
        }
        this.fixQXtract.setParam2(this.delimiter); // ErrID
        return this.fixQXtract;
    }

    @Override
    protected FixQXtract registerQR() {
        getLogger().info("Set FixQXtract for Information to Sender VIA SFTP(R)");
        FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
        FixInboxDao fixInboxDao = new FixInboxDao(session);
        fixQXtract.setParam1(this.param1);

        if (!context.get(MapKey.itemIdLink).toString().equals("")) {
            fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
        }
        fixQXtract.setParam4(PropertyPersister.emailRejected);
        fixQXtract.setParam5("");
        return this.fixQXtract;
    }

    @Override
    protected FixQXtract registerQA() {
        getLogger().info("Set FixQXtract for Information to Sender VIA SFTP(A)");
        String namTemplate = context.get(MapKey.templateName).toString();
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);

        String FileName = FilenameUtils.getBaseName(this.pattern); // filename
        String patern = fixSchedulerXtractDao.get(namTemplate).getFilePattern();
        FixSchedulerXtract xtracT = fixSchedulerXtractDao.get(namTemplate);
        this.schedID = xtracT.getFixSchedulerPK().getIdScheduler();

        getLogger().info("ERRFLAG :" + this.ErrFlag);
        getLogger().debug("XTRACT :" + this.schedID);
        if (StatusDefinition.YES.equals(this.ErrFlag)) {
            this.fixQXtract.setParam1(StatusDefinition.ERR); // flag Error
            this.fixQXtract.setReason(this.errIDs.toString());
            this.fixQXtract.setParam3(this.errValue); // flag Error
            this.fixQXtract.setParam5(errFile);
        } else {
            getLogger().info("Process SUccess Prepare sent to SFTP");
            this.fixQXtract.setParam1(StatusDefinition.SUCCESS); // flag Error
            this.fixQXtract.setParam4(namTemplate);
            this.fixQXtract.setParam5(FileName + ".resp");
        }
        this.fixQXtract.setParam2(this.delimiter); // ErrID
        return this.fixQXtract;
    }

    @Override
    protected Map rejectAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
