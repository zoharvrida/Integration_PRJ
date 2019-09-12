/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.fcr.model.BaCcBrnMast;
import bdsm.fcr.model.BaCcBrnMastPK;
import bdsm.model.EktpLog;
import bdsm.model.FcrBaCcBrnMast;
import bdsm.rpt.dao.FixMasterReportDao;
import bdsm.rpt.model.FixMasterReport;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralARDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.EktpLogDao;
import bdsmhost.dao.FcrBaCcBrnMastDao;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaccBrnMastDAO;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections.map.HashedMap;

public class KKLogRespGenerator extends BaseProcessor {

    private String getProperties;
    private List textSetting;
    private List columnSetting;
    private List numSequence;
    private List columnSequence;
    private List HeaderColSequence;
    private List HeaderComm;
    private List finalSelect;
    private List InjectedVal;
    private List headerVal;
    private String getSeparator;
    private String separator; // separator
    private String Param1;
    private String HOSymbol;
    private String tableName;
    private String idTemplate;
    private int fieldNum;
    private List<EktpLog> getListKtp;

    public KKLogRespGenerator(Map context) {
        super(context);
    }

    @Override
    protected boolean doExecute() throws Exception {
        this.getLogger().info("START doExecute()[KKLogRespGenerator]");
        EktpLogDao ektpDao = new EktpLogDao(session);
        GeneralARDao adDao = new GeneralARDao(session);
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);
        ParameterDao paramDao = new ParameterDao(session);
        getJavaParam(StatusDefinition.delimiter);
        String idBatch = null;
        String idUser = null;
        Date dateStart = null;
        Date endDate = null;
        //start
        String branch = "";
		Integer idReport = null;
        String inQuirer = "";
        Integer idScheduler = null;
		
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            getLogger().info("FULL MAP :" + super.context);
            idBatch = context.get(MapKey.param6).toString();
            idUser = context.get(MapKey.hdUserid).toString();
            this.idTemplate = context.get(MapKey.hdidTemplate).toString();
			idReport = Integer.parseInt(context.get(MapKey.reportId).toString());
            idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());
        } catch (Exception e) {
            getLogger().info("FAILED TO GET MAP :" + e, e);
        }
        // parameter category
        try {
            if(this.idTemplate.contains(Param1)){
                branch = context.get(MapKey.param3).toString();
            } else {
                branch = context.get(MapKey.hdcdBranch).toString();
            }

        } catch (Exception e) {
            branch = "";
            getLogger().info("INVALID PARAMETER for Branch : " + e);
        }
        try {
            synchronized(formatter){
                dateStart = new java.sql.Date(formatter.parse(context.get(MapKey.param1).toString()).getTime());
            }
        } catch (Exception e) {
            getLogger().info("INVALID PARAMETER for DateStart :" + e, e);
        }
        try {
            synchronized(formatter){
                endDate = new java.sql.Date(formatter.parse(context.get(MapKey.param2).toString()).getTime());
            }
        } catch (Exception e) {
            getLogger().info("INVALID PARAMETER for DateEnd :" + e, e);
        }

        //insert value for header
        getLogger().debug("BRANCH :" + branch);
        this.InjectedVal = new ArrayList();
        int codCcBrn = 0;
        String namBranch = null;

        BaccBrnMastDAO baCcBrnMastDao = new BaccBrnMastDAO(session);
        BaCcBrnMast baCcBrnMast = new BaCcBrnMast();

        if (!branch.equalsIgnoreCase("ALL")) {
            try {
                codCcBrn = Integer.parseInt(branch.trim());
            } catch (Exception e) {
                codCcBrn = 0;
            }
            try {
                BaCcBrnMastPK pk = new BaCcBrnMastPK();
                pk.setCodccBrn(codCcBrn);
                baCcBrnMast = baCcBrnMastDao.getBranch(pk);
                namBranch = baCcBrnMast.getNamBranch();
            } catch (Exception e) {
                namBranch = null;
            }
        } else {
            namBranch = null;
        }

        InjectedVal.add(this.idTemplate.contains(Param1) ? HOSymbol : branch + " " + namBranch);
        InjectedVal.add(idBatch);
        InjectedVal.add(context.get(MapKey.param1).toString());
        InjectedVal.add(context.get(MapKey.param2).toString());
        InjectedVal.add(SchedulerUtil.getTime());
        InjectedVal.add(idUser);

        HOSymbol = this.idTemplate.contains(Param1)&&("ALL".equalsIgnoreCase(branch)) ? "" : branch;
        getLogger().info("VALUE INJECTED for Header:" + InjectedVal);
        getParagraph(StatusDefinition.delimiter, 1); // header

		FixSchedulerXtract xtract = xtractDao.get(idScheduler);

        //Map fparam
        getLogger().debug("FILEPATTERN :" + xtract.getFilePattern());
        HOSymbol = xtract.getFilePattern();
        Map ektpProfiler = PropertyPersister.ekkStreaming;
		
		inQuirer = SchedulerUtil.inquirer(ektpProfiler, xtract, idReport, paramDao);
        //String fromDate = formatDate(getDateStart, getMonthStart - 1, getYearStart);
        //String toDate = formatDate(getDateEnd, getMonthEnd - 1, getYearEnd);
        //Date startDate = (Date) DateUtility.DATE_FORMAT_DDMMYYYY.parse(fromDate.replace("-", ""));;
        //Date endDate = (Date) DateUtility.DATE_FORMAT_DDMMYYYY.parse(toDate.replace("-", ""));
        getLogger().info("Parameter for Count Data : " + context.get(MapKey.param1).toString() + "|" + endDate + "|" + branch + "|" + HOSymbol);
        
		List<Map> dEKTP = ektpDao.getDataKKLog(dateStart, endDate, branch, HOSymbol,inQuirer,1);
        
        int countRec = 0;
        if(!dEKTP.isEmpty() || dEKTP.size()> 0){
            try {
                countRec = Integer.parseInt(dEKTP.get(0).get("CNT").toString());
            } catch (NumberFormatException numberFormatException) {
                getLogger().info("FAILED TO CONVERT :" + numberFormatException,numberFormatException);
                getLogger().info("LIST : " + dEKTP.get(0));
                countRec = 0;
            }
        }
        
        int persist = PropertyPersister.KKFETCH;
        //int countRec = ektpDao.countRowTableKK(dateStart, endDate, branch, HOSymbol);
        //int countSplit = (countRec / persist) < 1 ? 1 : ((countRec % persist == 0) ? countRec / persist : (countRec / persist) + 1);
        int countSplit = countRec;
        getLogger().info("COUNT ROWS :" + countRec);
        getLogger().info("COUNT SPLIT :" + countSplit);

        String outputFile = context.get(MapKey.reportFileName).toString();
        columnSetting = ektpDao.getColumnName(this.tableName);
        getLogger().info("COLUMN SIZE :" + columnSetting.size());
        loadHeaderSetting();
        loadSelectSetting(columnSetting, fieldNum);

        int recordCount = 0;
        int pharagraph = 0;
        int listSize = 0;
        int selectList = 0;
        try {
            StringBuilder KTPtxt = new StringBuilder();

            pharagraph = HeaderComm.size();
            getLogger().info("HEADER PHARAGRAPH SIZE :" + pharagraph);
            //header
            this.getLogger().info("Header");
            if (pharagraph != 0) {
                for (int h = 0; h < pharagraph; h++) {
                    this.headerVal = SchedulerUtil.getParameter(HeaderComm.get(h).toString(), "!");
                    if (headerVal.get(1).equals(" ")) {
                        KTPtxt.append(headerVal.get(0)).append(System.getProperty("line.separator"));
                    } else {
                        KTPtxt.append(headerVal.get(0)).
                                append(InjectedVal.get(Integer.parseInt(headerVal.get(1).toString()))).
                                append(System.getProperty("line.separator"));
                    }
                }
            }

            //header column
            this.getLogger().info("Header Column");
            selectList = finalSelect.size();
            getLogger().info("SELECT SIZE :" + selectList);

            listSize = columnSetting.size();
            getLogger().info("SETTING SIZE :" + listSize);
            for (int j = 0; j < selectList; j++) {
                KTPtxt.append(HeaderColSequence.get(j).toString());
                if (j < selectList - 1) {
                    KTPtxt.append(this.separator);
                }
            }

            getLogger().debug("FILEPATTERN :" + xtract.getFilePattern());
            HOSymbol = xtract.getFilePattern();
            ektpProfiler = PropertyPersister.ekkStreaming;
        
            inQuirer = SchedulerUtil.inquirer(ektpProfiler, xtract, idReport, paramDao);
            //value
            this.getLogger().info("Value Data KK");
            KTPtxt.append(System.getProperty("line.separator"));
            if (countRec != 0) {
                for (int i = 0; i < 1; i++) {
                    getLogger().info("SPLIT :" + i);
                    BigDecimal beginCount = null;
                    if (i > 0) {
                        beginCount = new BigDecimal(recordCount + 1);
                    } else {
                        beginCount = new BigDecimal(recordCount);
                    }

                    BigDecimal endCount = new BigDecimal(countSplit == 1 ? countRec : (((i + 1) * persist) > countRec) ? countRec : (persist));
                    getLogger().info("BEGIN :" + beginCount);
                    getLogger().info("END :" + endCount);
                    this.getListKtp = ektpDao.getDataKKLog(dateStart, endDate, branch, HOSymbol, inQuirer,2);
                    //branch name
                    if (getListKtp.size() > 0) {
                            codCcBrn = 0;
                        getLogger().debug("BRANCH STRING :" + branch);
                    for (EktpLog balObj : getListKtp) {
                            //if ("".equals(branch) || branch.equalsIgnoreCase("ALL")) {
                            codCcBrn = Integer.parseInt(balObj.getCdBranch());
                            try {
                                    baCcBrnMastDao = new BaccBrnMastDAO(session);
                                    BaCcBrnMastPK pk = new BaCcBrnMastPK();
                                    pk.setCodccBrn(codCcBrn);
                                    baCcBrnMast = baCcBrnMastDao.getBranch(pk);
                                    namBranch = baCcBrnMast.getNamBranch();
                            } catch (Exception e) {
                                    getLogger().debug("EXCEPTION :" + e,e);
                                    namBranch = null;
                            }
                            //}
                        recordCount++;
                        String KKtoText = rearrangeString(this.separator, numSequence, fieldNum, balObj, namBranch, recordCount);
                        KTPtxt.append(KKtoText).append(System.getProperty("line.separator"));
                        getLogger().info("RECORDCOUNT :" + recordCount);
                        }
                    }
                    FileUtil.writeWithFileChannerDMA(KTPtxt.toString(), PropertyPersister.dirFixOut + outputFile);
                    context.put(MapKey.param5, outputFile);
                    KTPtxt.delete(0, KTPtxt.length());
                }
            } else {
                getLogger().info("NO RECORD FOUND");
                FileUtil.writeWithFileChannerDMA(KTPtxt.toString(), PropertyPersister.dirFixOut + outputFile);
                context.put(MapKey.param5, outputFile);
                KTPtxt.delete(0, KTPtxt.length());
            }
            InjectedVal.add(SchedulerUtil.getTime());
            InjectedVal.add(recordCount);

        } catch (Exception e) {
            getLogger().info("FAILED TO STREAM TEXT : " + e, e);
            FileUtil.writeWithFileChannerDMA(e.getMessage(), PropertyPersister.dirFixOut + outputFile);
        }
        this.getLogger().info("END doExecute()[KKLogRespGenerator]");
        return true;
    }

    private void getJavaParam(String delimiter) {
        try {
            List parameter = PropertyPersister.getRealParam(session, Integer.parseInt(context.get(MapKey.idScheduler).toString()), "B"); // get String parameter
            //List parameter = SchedulerUtil.getParameter(PropertyPersister.KKparamString, delimiter); // get String parameter
            Map mapParam = (Map) parameter.get(0);
            Map lv1 = (Map) mapParam.get("STRING");
            if (lv1.isEmpty()) {
                throw new FIXException("PARAMETER INVALID");
            } else {
                String paramString = lv1.get("1").toString();
                parameter = SchedulerUtil.getParameter(paramString, delimiter);
            }
            this.Param1 = parameter.get(0).toString();
            this.HOSymbol = parameter.get(1).toString();
            this.getProperties = parameter.get(3).toString();
            this.tableName = parameter.get(4).toString();
            if (parameter.get(2).toString().trim().length() > 1) {
                loadSeparatorSetting(parameter.get(2).toString());
                this.separator = this.getSeparator;
            } else {
                this.separator = parameter.get(2).toString();
            }
            this.fieldNum = Integer.parseInt(parameter.get(5).toString());
        } catch (Exception e) {
            getLogger().info("EXCEPTION : FAILED GET JAVA PARAM - " + e, e);
            this.Param1 = "HO";
            this.getProperties = "kk_field.properties";
            this.separator = "|"; // separator
            this.HOSymbol = "Kantor Pusat";
            this.tableName = "EKTP_LOG";
            this.fieldNum = 11;
        }
    }

    private void getParagraph(String delimiter, int flag) {
        List parameter = new ArrayList();
        this.HeaderComm = new ArrayList();
        try {
            if (flag == 1) {
                parameter = PropertyPersister.getRealParam(session, Integer.parseInt(context.get(MapKey.idScheduler).toString()), "A");
                //parameter = SchedulerUtil.getParameter(PropertyPersister.KTPHeaders, delimiter); // get String parameter
                Map mapParam = (Map) parameter.get(0);
                Map lv1 = (Map) mapParam.get("STRING");
                if (lv1.isEmpty()) {
                    throw new FIXException("PARAMETER INVALID");
                } else {
                    String paramString = lv1.get("1").toString();
                    parameter = SchedulerUtil.getParameter(paramString, delimiter);
                }
                //parameter = SchedulerUtil.getParameter(PropertyPersister.KKHeaders, delimiter); // get String Parameter
            }
            // check Type Report
            int paramsIndex = 1;
            if (this.Param1.equalsIgnoreCase(this.idTemplate)) {
                paramsIndex = 0;
            }
            for (int h = 0; h < parameter.size(); h++) {
                List param = SchedulerUtil.getParameter(parameter.get(h).toString(), "|");
                getLogger().info("PARAM :" + param);
                getLogger().info("LENGTH :" + param.get(paramsIndex).toString().length());
                this.HeaderComm.add(param.get(paramsIndex).toString().charAt(0) == '0'
                        ? param.get(Integer.parseInt(param.get(paramsIndex).toString().substring(0, 1))) : param.get(paramsIndex).toString());

            }
        } catch (Exception e) {
            getLogger().info("EXCEPTION : FAILED GET HEADER PARAM - " + e, e);
            this.HeaderComm = null;
        }
    }

    private void loadSeparatorSetting(String separator) throws Exception {
        Properties properties = new Properties();
        InputStream in = KKLogRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        this.getSeparator = properties.getProperty(separator);
        in.close();
    }

    private void loadHeaderSetting() throws Exception {
        Properties properties = new Properties();
        InputStream in = KKLogRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        textSetting = new ArrayList();
        numSequence = new ArrayList();
        columnSequence = new ArrayList();
        HeaderColSequence = new ArrayList();
        getLogger().info("COLUMNS :" + columnSetting);
        for (int h = 0; h < columnSetting.size(); h++) {
            textSetting.add(properties.getProperty(columnSetting.get(h).toString()));
            if (!textSetting.isEmpty()) {
                getLogger().info("SETTING :" + textSetting);
                List param = SchedulerUtil.getParameter(textSetting.get(h).toString(), ":");
                getLogger().info("SETTING PARAM:" + param);
                numSequence.add(Integer.parseInt(param.get(0).toString()));
                try {
                    this.columnSequence.add(param.get(1));
                } catch (Exception e) {
                    getLogger().info("UNIDENTIFED FIELD " + param.get(0));
                    this.columnSequence.add("");
                }
            }
        }
        getLogger().info("TEXTSETTING :" + textSetting);
        // resorting for Header
        int k = 0;
        int f = fieldNum + 1;
        for (int z = 0; z < textSetting.size();) {
            //getLogger().info("BATAS ATAS :" + f);
            if (k < f) {
                List splitter = SchedulerUtil.getParameter(textSetting.get(z).toString(), ":");
                //getLogger().info("SPLITTER :" + splitter.get(0));
                if (k == Integer.parseInt(splitter.get(0).toString())) {
                    if(splitter.size() > 1){
                        HeaderColSequence.add(splitter.get(1).toString());
                    z = 0;
                    k++;
                }
                }
                z++;
            }
            //getLogger().info("BATAS NUMBER :" + HeaderColSequence.size());
            if (HeaderColSequence.size() == f) {
                z = textSetting.size();
            }
        }
        getLogger().info("HEADERCOLL :" + HeaderColSequence);
        in.close();
    }

    private void loadSelectSetting(List column, int sizing) {
        Properties properties = new Properties();
        getLogger().debug("INIT PROP :" + getProperties);
        InputStream in = KKLogRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
        try {
        properties.load(in);
        this.finalSelect = new ArrayList();
        Map pair = new HashedMap();
        getLogger().info("COLUMN LISTZ : " + column);
        for (int c = 0; c < column.size(); c++) {
            List split = SchedulerUtil.getParameter(properties.getProperty(column.get(c).toString()), ":");
            try {
                pair.put(Integer.parseInt(split.get(0).toString()), column.get(c));
            } catch (Exception e) {
                getLogger().info("UNIDENTIFED SELECT " + split.get(0));
                pair.put(Integer.parseInt(split.get(0).toString()), "");
            }
        }
        getLogger().info("PAIRING :" + pair);
        for (int d = 0; d < sizing; d++) {
            getLogger().info("FINAL LISTZ : " + pair.get(d).toString());
            this.finalSelect.add(pair.get(d).toString());
        }

        } catch (IOException ex) {
            getLogger().debug("PROPLOADER : Failed in Properties " + ex,ex);
        } finally {
            try {
        in.close();
            } catch (IOException ex) {
                getLogger().debug("FAILED TO CLOSE :" + ex,ex);
            }
        }
    }

    public String rearrangeString(String delimiter, List sequence, int sizing, EktpLog model, String namBranch, int counter) {
        StringBuilder SB = new StringBuilder();
        Map kkModel = new LinkedHashMap();
        StringBuilder KKText = new StringBuilder();
        boolean flags = true;

        getLogger().info("VALUE FIRST :" + model.getDtmRequest());

        if (flags) {
            //getLogger().info("IDUSER :" + model.getClientIp());
            //getLogger().info("SEQUENCE : " + numSequence);
            kkModel.put(sequence.get(0), counter);
            kkModel.put(sequence.get(1), model.getIdUser() == null ? "" : model.getIdUser());
            kkModel.put(sequence.get(2), namBranch == null ? "" : namBranch);
            kkModel.put(sequence.get(3), model.getInputNik() == null ? "" : model.getInputNik());
            kkModel.put(sequence.get(4), model.getDtmRequest() == null ? "" : model.getDtmRequest());
            kkModel.put(sequence.get(5), model.getDtmResponse() == null ? "" : model.getDtmResponse());
            kkModel.put(sequence.get(6), model.getDatPost() == null ? "" : model.getDatPost());
            kkModel.put(sequence.get(7), model.getDatLog() == null ? "" : model.getDatLog());
            kkModel.put(sequence.get(8), model.getResponse() == null ? "" : model.getResponse());
            kkModel.put(sequence.get(9), model.getFlgRespFromExternal() == null ? "" : model.getFlgRespFromExternal().equalsIgnoreCase("Y") ? "" : "no respon from dukcapil");
            kkModel.put(sequence.get(10), model.getClientIp() == null ? "" : model.getClientIp());
            kkModel.put(sequence.get(11), model.getDatLog() == null ? "" : model.getDatLog());
            kkModel.put(sequence.get(12), model.getFlg_Nik_Found() == null ? "" : model.getFlg_Nik_Found().equalsIgnoreCase("Y") ? "OK" : "Gagal" );
            kkModel.put(sequence.get(13), model.getCdBranch() == null ? "" : model.getCdBranch());
            kkModel.put(sequence.get(14), model.getSystem_Src() == null ? "" : model.getSystem_Src());
            kkModel.put(sequence.get(15), model.getMenuSrc() == null ? "" : model.getMenuSrc());
        }
        getLogger().debug("KKMODE :" + kkModel);
        for (int i = 0; i < sizing; i++) {
            SB.append(kkModel.get(i)).append(delimiter);
        }
        SB.deleteCharAt(SB.length() - 1);
        KKText.append(SB.toString());
        return KKText.toString();
    }
}
