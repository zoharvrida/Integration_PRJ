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
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.*;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import sun.misc.Regexp;

/**
 *
 * @author v00020800
 */
public class OTPCustScheduler extends TXTReaderWorker {

    public OTPCustScheduler(Map context) {
        super(context);
    }
    protected List FooterCount;
    private Integer xID;
    private String respond;
    private String xreport;
    private String patternMAtcherCIF;
    private String flags;
    private StringBuilder errIDs = new StringBuilder();
    private StringBuilder errVal = new StringBuilder();
    private String namTemplate;
    private final String HEADER_LINE = "6";
    private final Integer MAX_ROW_DETAILS = 10000;
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dfBuss = new SimpleDateFormat(StatusDefinition.patternddMMyyyy);
    private String emailDone = "Dear Sir/Madam,<br/>"
            + "<br/>"
            + "Process Bulk Update UDF CIF has been Processed. <br/>"
            + "Please see result Report in Attachment. <br/>"
            + "<br/>"
            + "Thanks & regards,<br/>"
            + "- BDSM -";
    
    protected String HeaderOtp(String[] detail, Integer total) {
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

        this.errValue = StatusDefinition.NO;

        getLogger().debug("DELIMITER EXIST :" + this.delimiter);
        StringBuilder comPilation = new StringBuilder();
        OtpCustHeaderDao dao = new OtpCustHeaderDao(session);
        OtpCustDetailDao daoD = new OtpCustDetailDao(session);
        FcrBaBankMastDao fcrBABankMastDAO = new FcrBaBankMastDao(session);

        OtpCustHeader headerOtp = new OtpCustHeader();
        getLogger().info("EARLY HEADER BEGIN");
        getLogger().debug("IDBATCH :" + idBatch);
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        getLogger().debug("ERROR DAo :" + idBatch);
        StringBuilder details = new StringBuilder();
        getLogger().info("Date Value " + detail[1]);
        getLogger().info("Date BA BANK MAST " + dao.bankMast());

        String dateF = detail[1];
        getLogger().info("dateF " + dateF);

        String dateFcomp = df.format(Date.valueOf(dao.bankMast()));
        //

        getLogger().debug("DATE " + dateF + " " + dateFcomp);

    /*    try {
            counter = Integer.parseInt(dao.getMax(this.idBatch));
            getLogger().info("COUNTER HEADER " + counter);
            realcount = counter + 1;
            //   headerOtp.getTypeRecord(counter + 1);
        } catch (Exception e) {
            getLogger().debug("NO VALUE in TMP" + e, e);
            counter = 1;
            realcount = counter;
            // headerOtp.setRecordId(counter);
        }
*/
        if (!dao.get(idBatch).isEmpty()) {
            try {
                getLogger().info(errDao.get(ErrorPersister.DuplicateCIF, this.idBatch, StatusDefinition.PROCESS));
            } catch (SQLException ex) {
                getLogger().info("ERROR :" + ex, ex);
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
                getLogger().debug("DETAILS :" + details + " FLAGS :" + this.flags);

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
            headerOtp.setTypeRecord(detail[0]);
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
                headerOtp.setFileDate(java.sql.Date.valueOf(detail[1]));
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
                //     mCIF.setRecCount(hTotal);
            } catch (NumberFormatException numberFormatException) {
                // invalid number row
                this.errIDs.append(ErrorPersister.convIntCIF).append("/");
                this.errVal.append(":").append(detail[2]).append("/");
                getLogger().info("ERROR NUMBER :" + numberFormatException);
            }

          
            java.util.Date sysDate = new java.util.Date();
            getLogger().info("Total Record : " + detail[2]);
            headerOtp.setBatchID(this.idBatch);
             if (dateFormatter.format(fcrBABankMastDAO.get().getDatProcess()).equals(detail[1])) {
            headerOtp.setStatus(StatusDefinition.UNAUTHORIZED);
             }else{
                             headerOtp.setStatus(StatusDefinition.REJECT);

             }
            // headerOtp.setDtmReceive(bdsm.util.SchedulerUtil.getTime());
            headerOtp.setUploadDate(sysDate);
            getLogger().info("READY TO INSERT CIF HEADER..");
            if (StatusDefinition.YES.equals(errValue)) {
                headerOtp.setRespCode(1);
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
                    //  mCIF.setComments(str.toString() + "ERROR SUM :" + i);
                } catch (SQLException ex) {
                    //     mCIF.setComments(comPilation.append("ERROR GENERATED MASSAGE").append("/").toString());
                    getLogger().info("ERROR :" + ex);
                }
            } else {
                headerOtp.setRespCode(0);
            }
            try {
                 String date =dateFormatter.format(fcrBABankMastDAO.get().getDatProcess());
                getLogger().info("Date Buss "+ date);
                getLogger().info("Date Header" + detail[1]);
                 
                dao.insert(headerOtp);
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

    protected String OtpFooter(String[] detail) {
        Integer hTotal;
        String result = null;
        Integer errFlag = 0;
        Integer counter = 0;
        Integer realcount = 0;
        String[] mandatory = new String[detail.length];

        this.errValue = StatusDefinition.NO;

        getLogger().debug("DELIMITER EXIST :" + this.delimiter);
        StringBuilder comPilation = new StringBuilder();
        OtpCustFooterDao dao = new OtpCustFooterDao(session);
        OtpCustDetailDao daoD = new OtpCustDetailDao(session);

        OtpCustFooter footerOtp = new OtpCustFooter();
        getLogger().info("EARLY FOOTER BEGIN");
        getLogger().debug("IDBATCH :" + idBatch);
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        getLogger().debug("ERROR DAo :" + idBatch);
        StringBuilder details = new StringBuilder();

        try {
            counter = Integer.parseInt(dao.getMax(this.idBatch));
            realcount = counter + 1;
        } catch (Exception e) {
            getLogger().debug("NO VALUE in TMP" + e);
            counter = 1;
            realcount = counter;
        }

     
            getLogger().info("HEADER BEGIN");
            // get DATA
        try {
            for (Integer i = 0; i < detail.length; i++) {
                details.append(detail[i]).append(this.delimiter);
                getLogger().debug("DETAILS :" + details + " FLAGS :" + this.flags);

                getLogger().debug("FINISH INSERT MANDATORY");

            }
            getLogger().debug("Rec_TYPE : " + detail[0]);
            footerOtp.setTyperecord(detail[0]);
        getLogger().info("Total Record T: " + detail[1]);
        getLogger().info("Total Record P: " + detail[2]);
        String tcount = detail[1];
        String pcount = detail[2];
        String dtlsCountT = daoD.getCountT(idBatch);
        String dtlsCountP = daoD.getCountP(idBatch);

        if (!tcount.equals(dtlsCountT) || !pcount.equals(dtlsCountP)) {
            footerOtp.setTyperecord(detail[0]);
            footerOtp.setBatchID(this.idBatch);
            footerOtp.setNorecordpencairan(detail[1]);
            footerOtp.setNorecordpelunasan(detail[2]);
            footerOtp.setStatus("R");
        } else {
            footerOtp.setTyperecord(detail[0]);
            footerOtp.setBatchID(this.idBatch);
            footerOtp.setNorecordpencairan(detail[1]);
            footerOtp.setNorecordpelunasan(detail[2]);
            footerOtp.setStatus("U");
        }
        } catch (Exception e) {
            getLogger().info("ERROR MAPPING "+ e,e);
        }
        

            getLogger().info("READY TO INSERT FOOTER..");
            if (StatusDefinition.YES.equals(errValue)) {
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
                    //  mCIF.setComments(str.toString() + "ERROR SUM :" + i);
                } catch (SQLException ex) {
                    //     mCIF.setComments(comPilation.append("ERROR GENERATED MASSAGE").append("/").toString());
                    getLogger().info("ERROR :" + ex);
                }
            } else {
                getLogger().info("DATA NOT FOUND: ");
            }
            try {
                dao.insert(footerOtp);
                tx.commit();
                this.tx = this.session.beginTransaction();
            } catch (Exception e) {
            getLogger().info("EXCEPTION FOOTER :" + e,e);
                errFlag = 1;
                tx.rollback();
            }
            getLogger().debug("ERRFLAG :" + errFlag);
            if (errFlag == 1) {
                result = ErrorPersister.HeaderRejectedCIF; // Reject
            } else {
                result = ErrorPersister.HeaderInserted; // Success
            }
        
        return result;
    }

    protected String otpDetails(String[] detail) throws ParseException {
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
        OtpCustHeaderDao hDao = new OtpCustHeaderDao(session);
        OtpCustDetailDao dao = new OtpCustDetailDao(session);
        OtpCustDetail otpDetail = new OtpCustDetail();
        OtpCustHeader otpHeader = new OtpCustHeader();
        StringBuilder details = new StringBuilder();
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        getLogger().debug("IDBATCH :" + idBatch);

        try {
            counter = Integer.parseInt(dao.getMax());
            realcount = counter + 1;
            otpDetail.setRecordId(counter + 1);
            
        } catch (Exception e) {
            getLogger().info("NO VALUE in TMP" + e,e);
            
            counter = 1;
            realcount = counter;
            otpDetail.setRecordId(counter);
        }

        /**
         * if (!dao.getDetails(this.idBatch, realcount).isEmpty()) { try { //
         * duplicate ID batch and record number
         * getLogger().info(errDao.get(ErrorPersister.DuplicateRecordCIF,
         * this.idBatch, StatusDefinition.PROCESS)); } catch (SQLException ex) {
         * getLogger().info("ERROR SQL :" + ex); getLogger().info("DUPLICATE
         * Exact Record"); } result = ErrorPersister.DuplicateRecordCIF; errFlag
         * = 1; } else { List<OtpCustHeader> hCIF = new ArrayList(); hCIF =
         * hDao.get(this.idBatch);
         *
         * if (hCIF.get(0).getRespCode() == 1) { // Reject try { // sent error
         * to fixQ getLogger().info(errDao.get(ErrorPersister.recordNotsame,
         * this.idBatch, StatusDefinition.PROCESS)); } catch (SQLException ex) {
         * getLogger().info("ERROR HEADERCOUNT :" + ex);
         * getLogger().info("HEADER COUNT MISSMATCH"); } result =
         * ErrorPersister.recordNotsame; errFlag = 1; } else {
         * getLogger().info("PROCESSING"); for (Integer i = 0; i <
         * detail.length; i++) {
         * details.append(detail[i]).append(this.delimiter); try {
         * getLogger().debug(i + 1); this.flags = dao.getMandatory(1, i + 1,
         * StatusDefinition.DONE,this.namTemplate); } catch (Exception e) {
         * getLogger().info("EXCEPTION :" + this.flags + " " + e); this.flags =
         * StatusDefinition.YES; } getLogger().debug("DETAILS :" + details + "
         * FLAGS :" + this.flags); if (StatusDefinition.YES.equals(flags)) {
         * mandatory[i] = StatusDefinition.YES; } else { mandatory[i] =
         * StatusDefinition.NO; } if
         * (PropertyPersister.DEFAULT_REPLACEMENT.equals(detail[i])) { if
         * (StatusDefinition.YES.equals(mandatory[i])) { this.errValue =
         * StatusDefinition.YES; getLogger().info("MANDATORY VALUE NULL");
         * //this.errIDs.append(ErrorPersister.Mandatorynull).append("/");
         * getLogger().debug(errIDs.toString()); } detail[i] = ""; } }
         */
        getLogger().debug("RecTYPE Detail :" + detail[0]);

        otpDetail.setTypeRecord(detail[0]);
        try {
            dTotal = Integer.parseInt(detail[2]);
            try {
                count = Integer.parseInt(dao.CodCust_Val(dTotal));
                if (count != 0) {
                    //otpDetail.(dTotal);
                } else {
                    if (StatusDefinition.YES.equals(mandatory[2])) {
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
       int size=detail.length;
               getLogger().info("==LOOP==" + size);
       
        String type = detail[1];
        getLogger().info("==TYPE OTP==" + type);
        getLogger().info("==START DETAIL==");
        try {
            OtpCustHeader headerD = hDao.getModel(idBatch);
            if (headerD.getStatus().equals(StatusDefinition.REJECT)) {
                otpDetail.setFlgStatus(StatusDefinition.REJECT);
            } else {
                otpDetail.setFlgStatus(StatusDefinition.UNAUTHORIZED);
            }

           if (type.equals("T")) {
            getLogger().info("==MAPPING PENCAIRAN==");
            otpDetail.setBatchId(idBatch);
            otpDetail.setTypeOtp(type);
            otpDetail.setCifNumber(detail[2]);
            otpDetail.setNama(detail[3]);
            otpDetail.setAc(detail[4]);
            otpDetail.setSpk(detail[5]);
            otpDetail.setKodeAo(detail[6]);
            otpDetail.setLob(detail[7]);
            otpDetail.setFasilitas(Double.parseDouble(detail[8]));
            otpDetail.setPlafonAwal(Double.parseDouble(detail[9]));
            otpDetail.setOs(Double.parseDouble(detail[10]));
            otpDetail.setTransaksiOtp(Double.parseDouble(detail[11]));
            otpDetail.setSisaPlafon(Double.parseDouble(detail[12]));
            otpDetail.setCodprodNcbs(detail[13]);
            otpDetail.setCodprodIcbs(detail[14]);
            otpDetail.setCostCenter(detail[15]);
            otpDetail.setBookingLoan(detail[16]);
            otpDetail.setGrading(detail[17]);
            otpDetail.setJumlah(Double.parseDouble(detail[18]));
            otpDetail.setIndexRate(Double.parseDouble(detail[19]));
            otpDetail.setRatecode(detail[20]);
            otpDetail.setPeriode(detail[21]);
            otpDetail.setSukuBunga(detail[22]);
            otpDetail.setVariance(detail[23]);
            otpDetail.setRatePinalty(Double.parseDouble(detail[24]));
            otpDetail.setTanggalPelunasan(detail[25]);
                        getLogger().info("==END MAPPING PENCAIRAN==");

        } else if (type.equals("P")) {
            getLogger().info("==MAPPING PELUNASAN==");
otpDetail.setBatchId(idBatch);
            otpDetail.setTypeOtp(type);
            otpDetail.setCifNumber(detail[2]);
            otpDetail.setNama(detail[3]);
            otpDetail.setAc(detail[4]);
            otpDetail.setSpk(detail[5]);
            otpDetail.setKodeAo(detail[6]);
            otpDetail.setLob(detail[7]);
            otpDetail.setFasilitas(Double.parseDouble(detail[8]));
            otpDetail.setPlafonAwal(Double.parseDouble(detail[9]));
            otpDetail.setOs(Double.parseDouble(detail[10]));
            otpDetail.setTransaksiOtp(Double.parseDouble(detail[11]));
            otpDetail.setSisaPlafon(Double.parseDouble(detail[12]));
            otpDetail.setCodprodNcbs(detail[13]);
            otpDetail.setTanggalPelunasan(detail[14]);
            otpDetail.setBatchId(idBatch);

            } else {
                getLogger().info("--> FAILED TYPE");
                otpDetail.setFlgStatus(StatusDefinition.REJECT);
                otpDetail.setStatus("Transaction type is null");
                otpDetail.setCifNumber(detail[2]);
                otpDetail.setBatchId(idBatch);
        }  


        } catch (Exception e) {
                        getLogger().info("MAPPING ERROR"+ e,e);

        }
       

        //mCIF.setFileName(FileName);
        getLogger().info("SET DIRECTION ->");
        if (StatusDefinition.YES.equals(this.errValue)) {
            getLogger().info("RECORD REJECT");
            otpDetail.setStatus(StatusDefinition.REJECT);

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
                otpDetail.setComments(str.toString());
            } catch (SQLException ex) {
                otpDetail.setComments(comPilation.append(ex).append("/").toString());
                getLogger().info("ERROR :" + ex, ex);
            }
        }

        //     mCIF.setDtmRequest(SchedulerUtil.getTime());
        try {
            dao.insert(otpDetail);
            getLogger().info("==PROCESS DETAIL==" );
            tx.commit();
            this.tx = this.session.beginTransaction();

        } catch (Exception e) {
            getLogger().info("EXCEPTION CIF :" + e, e);
            getLogger().info("==ERROR DETAIL==");
            errFlag = 1;
            tx.rollback();
            dao.deleteRecord(this.idBatch);
            hDao.updateReject(this.idBatch);
        }
        getLogger().info("==END DETAIL==");

        getLogger().debug("ERRFLAG :" + errFlag);
        if (errFlag == 1) {
            result = ErrorPersister.DetailProcessFailedCIF; // Reject
        } else {
            result = ErrorPersister.DetailProcessSuccessCIF; // Success
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

        //StringBuilder Hscanner = new StringBuilder();
        // 
        if (this.patternMAtcherCIF.equals(namTemplate)) {
            if (StatusDefinition.Headers.equals(detailCounter[0])) {
                getLogger().info("BEGIN HEADER (CIF) PROCESSING ..");

                this.respond = HeaderOtp(detailCounter, total);
                getLogger().info("SUCCESS PROCESSING HEADER WITH :" + respond);

            } else if (StatusDefinition.Details.equals(detailCounter[0])) {
                //detailsCount = counter + detailsCount;
                getLogger().info("BEGIN DETAIL (CIF) PROCESSING ..");
                try {
//                    if(detailCounter[1].equals("T"))
                    getLogger().info("detailCounter" );
                    this.respond = otpDetails(detailCounter);
//                    else{
                        
                    
                  //  this.xreport = OtpReport(idBatch);

                } catch (ParseException ex) {
                    Logger.getLogger(OTPCustScheduler.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (StatusDefinition.Footers.equals(detailCounter[0])) {
                //detailsCount = counter + detailsCount;
                                    getLogger().info("Footer Counter" );

                if (StatusDefinition.Footers.equals(detailCounter[0])) {
                    getLogger().info("BEGIN FOOTER (CIF) PROCESSING ..");

                    this.respond = OtpFooter(detailCounter);
                    getLogger().info("SUCCESS PROCESSING HEADER WITH :" + respond);
                }
            }
        }
        //this.innerContext.put("counter", counter);
        this.innerContext.put("comment_Code", this.respond);
        return innerContext;
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
        List getPattern = SchedulerUtil.getParameter(PropertyPersister.DFPattern, ";");
        String localDelimiter = "\\|";

        //getLogger().info("BEGIN SCANNING FILE");

        patternMAtcherCIF = this.namTemplate;
        getLogger().info("Pattern CIF :" + patternMAtcherCIF);

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
                            getLogger().info("NAME TEMPLATE" + namTemplate);
                            getLogger().info("ID SCHEDULER" + Integer.parseInt(idScheduler));
                            getdim = errDao.get(Integer.parseInt(idScheduler), StatusDefinition.Headers, namTemplate, StatusDefinition.IN);
                            getLogger().info(getdim);
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
                    for (Integer z = 0; z < DetailCount.size(); z++) {
                        if (DetailCount.get(z) == rowCount) {
                            // detailFlag
                            getLogger().info("FOOTER FLAG ACTIVED");
                            getdim = errDao.get(Integer.parseInt(idScheduler), StatusDefinition.Footers, namTemplate, StatusDefinition.IN);
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
                          // 
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
                        getLogger().info("ERROR : " + exception, exception);
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
            getLogger().info(e, e);
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

    @Override
    protected Integer valueInterface() {
        String namTemplate = context.get(MapKey.templateName).toString();
        String IDBatch = context.get(MapKey.batchNo).toString();
        Integer idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());
        getLogger().debug("IDBATCH : " + IDBatch + " " + idScheduler);
        OtpCustDetailDao cifDao = new OtpCustDetailDao(session);

        Integer flag = 1;
        Integer Inst = 0;
          if (patternMAtcherCIF.equals(namTemplate)) {
         Inst = cifDao.runPackage(IDBatch, idScheduler);
         getLogger().info("FINISH 1st PACKAGE CIF..  Status :" + Inst);
         //           flag = CIFValidation();
         } 
        return flag;
        //return true;
    }
    }
