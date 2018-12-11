/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.model.CiChUfCASADetails;
import bdsm.scheduler.model.CiChUfCASAHeader;
import bdsm.scheduler.model.CiChUfCIFDetails;
import bdsm.scheduler.model.CiChufCIFHeader;
import bdsm.scheduler.util.SchedulerUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v00019722
 */
public class DDFTXTmapping extends TXTrespGenerator {

    private String patternMAtcherCIF;
    private String patternMAtcherCASA;
    
    /**
     * 
     * @param context
     */
    public DDFTXTmapping(Map context) {
        super(context);
    }

    private StringBuilder CASAheaderTXT_gen(String delimiter,Integer header) {
        
        getLogger().debug("(CASA)ID_BATCH reps Header :" + this.BatchID);
        CiChufCASAHeaderDao headerDao = new CiChufCASAHeaderDao(session);
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        StringBuilder sbHeader = new StringBuilder();

        if (this.reason != null){
            try {
                this.reason = errDao.get(reason, this.ErrValue, StatusDefinition.RESPONSE);
            } catch (SQLException ex) {
                getLogger().info("ERROR GET :" + ex);
            }
        } else {
            this.reason = "";
            this.ErrValue = "";
        }
        try {
            List<CiChUfCASAHeader> h = new ArrayList();
            getLogger().debug("BEFORE PARSING HEADER CASA :" + header + " " + BatchID);
            try {
                h = headerDao.getNumber(this.BatchID, header);
                getLogger().info(h);
            } catch (Exception e) {
                getLogger().info("ERROR GET :" + e);
            }
            //List listDetails = detailDao.getDetails(BatchID);
            sbHeader.append(h.get(0).getRecType()).append(delimiter).append(h.get(0).getFileDate()).append(delimiter);
            sbHeader.append(h.get(0).getBatchId()).append(delimiter).append(h.get(0).getRecCount()).append(delimiter);
            sbHeader.append(h.get(0).getRespCode()).append(delimiter);
            if (this.reason != null){
                sbHeader.append(this.reason).append(":").append(this.ErrValue);
            }
            getLogger().debug("HEADER CASA:" + sbHeader.toString());
        } catch (Exception e) {
           getLogger().error("ERROR PARSING HEADER CASA:" + e); 
        }
        return sbHeader; 
    }

    private StringBuilder CIFheaderTXT_gen(String delimiter,Integer header) {
        
        getLogger().debug("(CIF)ID_BATCH reps Header :" + BatchID);
        CiChUfCIFHeaderDao headerDao = new CiChUfCIFHeaderDao(session);
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        StringBuilder sbHeader = new StringBuilder();
        
        if (this.reason != null){
            try {
                this.reason = errDao.get(reason, this.ErrValue, StatusDefinition.RESPONSE);
            } catch (SQLException ex) {
                getLogger().info("ERROR GET :" + ex);
            }
        } else {
            this.reason = "";
            this.ErrValue = "";
        }
        try {
            List<CiChufCIFHeader> h = new ArrayList();
            getLogger().debug("BEFORE PARSING HEADER CIF :" + header);
            try {
                h = headerDao.getNumber(BatchID, header);
                getLogger().info(h);
            } catch (Exception e) {
                getLogger().info("ERROR GET :" + e);
            }
            //List listDetails = detailDao.getDetails(BatchID);
            sbHeader.append(h.get(0).getRecType()).append(delimiter).append(h.get(0).getFileDate()).append(delimiter);
            sbHeader.append(h.get(0).getBatchId()).append(delimiter).append(h.get(0).getRecCount()).append(delimiter);
            sbHeader.append(h.get(0).getRespCode()).append(delimiter);
            if (this.reason != null){
                sbHeader.append(this.reason).append(":").append(this.ErrValue);
            }
            getLogger().debug("HEADER CIF:" + sbHeader.toString());
        } catch (Exception e) {
            getLogger().error("ERROR PARSING HEADER CIF:" + e);
        }
        return sbHeader;    
    }

    private StringBuilder CASAdetailsTXT_gen(String delimiter, Integer count) {
        
        getLogger().debug("(CASA)ID_BATCH reps Details :" + BatchID);
        CiChUfCASADetailsDao detailDao = new CiChUfCASADetailsDao(session);
        StringBuilder sbDetails = new StringBuilder();
        
        try {
            List<CiChUfCASADetails> d = new ArrayList();
            getLogger().debug("BEFORE PARSING DETAILS CASA :" + count);
            try {
                d = detailDao.getDetails(BatchID, count);
                getLogger().info(d);
            } catch (Exception e) {
                getLogger().info("ERROR GET :" + e);
            }
            sbDetails.append(d.get(0).getCod_acct_no()).append(delimiter).append(d.get(0).getTujuan_rek()).append(delimiter);
            sbDetails.append(d.get(0).getSumber_dana_rek()).append(delimiter).append(d.get(0).getMedia_mutasi()).append(delimiter);
            sbDetails.append(d.get(0).getKepemilikan()).append(delimiter).append(d.get(0).getEmail_Statement()).append(delimiter);
            sbDetails.append(d.get(0).getBranch_Statement()).append(delimiter).append(d.get(0).getStatus()).append(delimiter);
            sbDetails.append(d.get(0).getRespcode()).append(delimiter).append(d.get(0).getComment_resp()).append(delimiter).append(d.get(0).getDtmFinish());
            getLogger().debug("DETAILS CASA:" + sbDetails.toString());
        } catch (Exception e) {
            getLogger().error("ERROR PARSING DETAIL CASA:" + e);
        }
        return sbDetails;
    }

    private StringBuilder CIFdetailsTXT_gen(String delimiter, Integer count) {
        
        getLogger().debug("(CIF)DETAIL ID_BATCH reps Details :" + BatchID);
        CiChUfCIFDetailsDao detailDao = new CiChUfCIFDetailsDao(session);
        StringBuilder sbDetails = new StringBuilder(); 
               
        try {
            List<CiChUfCIFDetails> d = new ArrayList();
            getLogger().debug("BEFORE PARSING DETAILS CIF :" + count);
            try {
                d = detailDao.getDetails(BatchID, count);
                getLogger().info(d);
            } catch (Exception e) {
                getLogger().info("GET ERROR :" + e);
            }
            sbDetails.append(d.get(0).getCod_cust()).append(delimiter).append(d.get(0).getAgama()).append(delimiter);
            sbDetails.append(d.get(0).getTxn_Value()).append(delimiter).append(d.get(0).getSumber_Dana_Lain()).append(delimiter);
            sbDetails.append(d.get(0).getFax_No()).append(delimiter).append(d.get(0).getNama_Spouse_Ortu()).append(delimiter);
            sbDetails.append(d.get(0).getPekerjaan_Spouse_Ortu()).append(delimiter).append(d.get(0).getJabatan_Spouse_Ortu()).append(delimiter);
            sbDetails.append(d.get(0).getKantor_Spouse_Ortu()).append(delimiter).
                    append(d.get(0).getOffice_No_Country()).append(delimiter).append(d.get(0).getHome_No_Country()).append(delimiter).
                    append(d.get(0).getHome_No_Area()).append(delimiter).append(d.get(0).getHome_No_Phone()).append(delimiter);
            sbDetails.append(d.get(0).getOffice_No_Area()).append(delimiter).append(d.get(0).getOffice_No_Phone()).append(delimiter);
            sbDetails.append(d.get(0).getExt_Office_No()).append(delimiter).append(d.get(0).getNama_ibu_kandung()).append(delimiter);
            sbDetails.append(d.get(0).getNama_alias()).append(delimiter).append(d.get(0).getE_Statement_Email()).append(delimiter);
            sbDetails.append(d.get(0).getE_Statement_Branch()).append(delimiter).append(d.get(0).getStatus()).append(delimiter);
            sbDetails.append(d.get(0).getRespcode()).append(delimiter).append(d.get(0).getComment_resp()).append(delimiter);
            sbDetails.append(d.get(0).getDtmFinish()).append(delimiter);
            getLogger().debug("DETAILS CIF:" + sbDetails.toString());
        } catch (Exception e) {
            getLogger().error("ERROR PARSING DETAIL CIF:" + e);
        }
        return sbDetails;
    }

    /**
     * 
     * @param headerNum
     * @return
     */
    @Override
    public StringBuilder headerS(Integer headerNum) {
        StringBuilder header = new StringBuilder();
        getLogger().info("ASSIGN INTO MAPPER");
        getLogger().debug("DELIMITER CHECK :" + this.delimiter);
        
        if (patternMAtcherCIF.equals(namTemplate)) {
            getLogger().info("ASSIGN INTO MAPPER CIF");
            header = CIFheaderTXT_gen(this.delimiter,headerNum);
        } else if (patternMAtcherCASA.equals(namTemplate)) {
            getLogger().info("ASSIGN INTO MAPPER CASA");
            header = CASAheaderTXT_gen(this.delimiter,headerNum);
        }
       return header; 
    }

    /**
     * 
     * @param count
     * @return
     */
    @Override
    public StringBuilder detailS(Integer count) {
        StringBuilder details = new StringBuilder();
        getLogger().info("ASSIGN INTO MAPPER DETAILS");
        if (patternMAtcherCIF.equals(namTemplate)) {
            getLogger().info("ASSIGN INTO MAPPER CIF");
            details = CIFdetailsTXT_gen(this.delimiter,count);
        } else if (patternMAtcherCASA.equals(namTemplate)) {
            getLogger().info("ASSIGN INTO MAPPER CASA");
            details = CASAdetailsTXT_gen(this.delimiter,count);
        }
        return details;
    }

    /**
     * 
     * @return
     */
    @Override
    public Integer checkNumHeader() {
        
        this.patternMAtcherCASA = "";
        this.patternMAtcherCIF = "";
        
        getLogger().debug("DELIMITER CHECK :" + this.delimiter);
        List getPattern = SchedulerUtil.getParameter(PropertyPersister.DFPattern,";");
        String localDelimiter = "\\|";

        List PatternCIF = SchedulerUtil.getParameter(getPattern.get(0).toString(),localDelimiter);
        List PatternCASA = SchedulerUtil.getParameter(getPattern.get(1).toString(),localDelimiter);
        //getLogger().info("BEGIN SCANNING FILE");
        
        if(PatternCIF.indexOf(namTemplate)==-1){
            if(PatternCASA.indexOf(namTemplate)>-1){
                patternMAtcherCASA = namTemplate;
                getLogger().info("Pattern CASA :" + patternMAtcherCASA);
            }
        } else {
            patternMAtcherCIF = namTemplate;
            getLogger().info("Pattern CIF :" + patternMAtcherCIF);
        }
        if (patternMAtcherCIF.equals(namTemplate)) {
            getLogger().info("BEGIN CHECKNUM FILE CIF");
            this.checknumHeader = getCIFnum();
        } else if (patternMAtcherCASA.equals(namTemplate)) {
            getLogger().info("BEGIN CHECKNUM FILE CASA");
            this.checknumHeader = getCASAnum();
        }
        return this.checknumHeader;
    }

    private Integer getCIFnum() {
        Integer num;
        CiChUfCIFHeaderDao headerDao = new CiChUfCIFHeaderDao(session);
        
        List<CiChufCIFHeader> h = headerDao.getModel(this.BatchID);
        num = h.size();
        getLogger().info("NUMBER OF CIF HEADER : " + num);
        
        this.checknumHeader = num;
        return num;
    }
    
    private Integer getCASAnum() {
        Integer num;
        CiChufCASAHeaderDao headerDao = new CiChufCASAHeaderDao(session);
        
        List<CiChUfCASAHeader> h = headerDao.getModel(this.BatchID);
        num = h.size();
        getLogger().info("NUMBER OF CASA HEADER : " + num);
        
        this.checknumHeader = num;
        return num;
    }

    /**
     * 
     * @param count
     * @return
     */
    @Override
    public Integer checkNumDetail(Integer count) {
        Integer num = 0;
        CiChUfCIFHeaderDao CIFDao = new CiChUfCIFHeaderDao(session);
        CiChufCASAHeaderDao CASADao = new CiChufCASAHeaderDao(session);
        
        if (patternMAtcherCIF.equals(namTemplate)) {
            List<CiChufCIFHeader> h = CIFDao.getNumber(this.BatchID,count);
            num = h.get(0).getRecCount();
        } else if (patternMAtcherCASA.equals(namTemplate)) {
            List<CiChUfCASAHeader> h = CASADao.getNumber(this.BatchID,count);
            num = h.get(0).getRecCount();
        }
        getLogger().info("Details Number :" + num);
        this.checknumDetails = num;
        return this.checknumDetails;
    }
    
}
