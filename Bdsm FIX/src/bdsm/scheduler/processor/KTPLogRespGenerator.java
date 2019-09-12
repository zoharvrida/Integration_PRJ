/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.EktpLog;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralARDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.DefinitionConstant;
import bdsmhost.dao.EktpLogDao;
import bdsmhost.dao.ParameterDao;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author v00019722
 */
public class KTPLogRespGenerator extends BaseProcessor {

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
    //private ParameterDao paramDao = new ParameterDao(session);

    /**
     * 
     * @param context
     */
    public KTPLogRespGenerator(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        EktpLogDao ektpDao = new EktpLogDao(session);
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);
        ParameterDao paramDao = new ParameterDao(session);
        GeneralARDao adDao = new GeneralARDao(session);
        
        getJavaParam(StatusDefinition.delimiter);
        String getBranch = null;
        String idBatch = null;
        String idUser = null;
        String inQuirer = "";

        Integer idScheduler = null;
        Integer idReport = null;

        int getMonth = 0;
        int getYear = 0;
        try {
            getLogger().info("FULL MAP :" + super.context);

            idBatch = context.get(MapKey.param6).toString();
            idUser = context.get(MapKey.hdUserid).toString();
            //getLogger().info("MAP :" + repParam);
            this.idTemplate = context.get(MapKey.hdidTemplate).toString();
            getBranch = context.get(MapKey.hdcdBranch).toString();
            idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());
            idReport = Integer.parseInt(context.get(MapKey.reportId).toString());
        } catch (Exception e) {
            getLogger().info("FAILED TO GET MAP :" + e, e);
        }
        try {
            List param = SchedulerUtil.getParameter(context.get(MapKey.param1).toString(), "/");
            getMonth = Integer.parseInt(param.get(0).toString());
            getYear = Integer.parseInt(param.get(1).toString());
            getLogger().info("MONTH :" + getMonth + " YEAR :" + getYear);
        } catch (Exception e) {
            getLogger().info("INVALID PARAMETER :" + e, e);
        }
        this.InjectedVal = new ArrayList();
        InjectedVal.add(this.idTemplate.contains(Param1) ? HOSymbol : getBranch);
        InjectedVal.add(idBatch);
        InjectedVal.add(formatDate(getMonth - 1, getYear));
        InjectedVal.add(SchedulerUtil.getTime());
        InjectedVal.add(idUser);

        HOSymbol = this.idTemplate.contains(Param1) ? "" : getBranch;
        getLogger().info("VALUE INJECTED :" + InjectedVal);
        getParagraph(StatusDefinition.delimiter, 1);

        int persist = PropertyPersister.KTPFETCH;
        // get Filepattern

        FixSchedulerXtract xtract = xtractDao.get(idScheduler);

        //Map fparam
        getLogger().debug("FILEPATTERN :" + xtract.getFilePattern());
        HOSymbol = xtract.getFilePattern();
        Map ektpProfiler = PropertyPersister.ektpStreaming;
        
        inQuirer = SchedulerUtil.inquirer(ektpProfiler, xtract, idReport, paramDao);
        
        /*getLogger().debug("MAP PROFILER :" + ektpProfiler);
        Map profSpec = PropertyPersister.parseKeyAndValueToMap(ektpProfiler.get(xtract.getFilePattern()).toString().replaceAll(":", ";"));
        getLogger().debug("MAP PROFSPEC :" + profSpec);
        if (profSpec != null) {
            Map patternMap = PropertyPersister.parseKeyAndValueToMap(profSpec.get("rnD").toString().replaceAll("\\|", ";"));
            getLogger().debug("MAP PATTERN :" + patternMap);
            if (patternMap != null) {
                Map idMap = PropertyPersister.parseKeyAndValueToMap(patternMap.get(idReport.toString()).toString().replaceAll("<>", ";"));
                getLogger().debug("MAP ID :" + idMap);
                getLogger().debug("1 :" + xtract.getFixSchedulerPK().getIdTemplate() + " 2 :" + idMap.get("orderId") + " 3 :" + profSpec.get("fieldId") + " 4 :" + xtract.getFilePattern());
                if (idMap != null) {
                    if (idMap.get("orderId") != null) {
                        List<Map> logMap = paramDao.getFieldParameterbyTemplate(xtract.getFixSchedulerPK().getIdTemplate(),
                                Integer.parseInt(idMap.get("orderId").toString()),
                                Integer.parseInt(profSpec.get("fieldId").toString()),
                                xtract.getFilePattern());

                        getLogger().debug("MAP LOG :" + logMap);
                        if (!logMap.isEmpty()) {
                            if ("AUTO".equalsIgnoreCase(idMap.get("type").toString())) {
                                if ("REFLECT".equalsIgnoreCase(logMap.get(0).get(DefinitionConstant.formatData).toString())) {
                                    inQuirer = logMap.get(0).get(DefinitionConstant.defValParam).toString().
                                            replaceAll(logMap.get(0).get(DefinitionConstant.fieldName).toString(),
                                            logMap.get(0).get(DefinitionConstant.destTable).toString());
                                } else {
                                    inQuirer = logMap.get(0).get(DefinitionConstant.defValParam).toString();
                                }
                            }
                        }
                    } else {
                        inQuirer = "";
                    }
                } else {
                    inQuirer  = "";
                }
            }
        }*/
		
        int countRec = 0;
        getLogger().debug("INQUIRY :" + inQuirer);
        getLogger().debug("brn :" + getBranch);
        List<Map> dEKTP = ektpDao.getDataEktpLog(getMonth, getYear, getBranch, HOSymbol,inQuirer,1);
        
        if(!dEKTP.isEmpty() || dEKTP.size()> 0){
            try {
                countRec = Integer.parseInt(dEKTP.get(0).get("CNT").toString());
            } catch (NumberFormatException numberFormatException) {
                getLogger().info("FAILED TO CONVERT :" + numberFormatException,numberFormatException);
                getLogger().info("LIST : " + dEKTP.get(0));
                countRec = 0;
            }
        }
        //int countRec = ektpDao.countRowTable(getMonth, getYear, getBranch, HOSymbol, inQuirer);

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
            StringBuilder Select_Query = new StringBuilder();

            pharagraph = HeaderComm.size();
            getLogger().info("HEADER PHARAGRAPH SIZE :" + pharagraph);

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
            KTPtxt.append(System.getProperty("line.separator"));
            if (countRec != 0) {
                for (int i = 0; i < 1; i++) {
                    getLogger().info("SPLIT :" + i);
                    /*BigDecimal beginCount = null;
                    if (i > 0) {
                        beginCount = new BigDecimal(recordCount + 1);
                    } else {
                        beginCount = new BigDecimal(recordCount);
                    }

                    BigDecimal endCount = new BigDecimal(countSplit == 1 ? countRec : (((i + 1) * persist) > countRec) ? countRec : (persist));
                    getLogger().info("BEGIN :" + beginCount);
                    getLogger().info("END :" + endCount);*/
                    //this.getListKtp = ektpDao.EKTPgreatData(beginCount, endCount, getMonth, getYear, getBranch, HOSymbol, inQuirer);
                    List<EktpLog> dataEktp = ektpDao.getDataEktpLog(getMonth, getYear, getBranch, HOSymbol,inQuirer,2);
                    //ClassConverterUtil.MapToClass(profSpec, i)
                    for (EktpLog balObj : dataEktp) {
                        getLogger().debug("EKTP : " + balObj.toString());
                        //EktpLog ektpModel = new EktpLog();
                        //ClassConverterUtil.MapToClass(balObj, ektpModel);
                        String KTPtoText = rearrangeString(this.separator, numSequence, fieldNum, balObj);
                        //out.write(SMStextArrange(balObj));
                        KTPtxt.append(KTPtoText).append(System.getProperty("line.separator"));
                        recordCount++;
                        getLogger().info("RECORDCOUNT :" + recordCount);
                    }
                }
            }
            FileUtil.writeWithFileChannerDMA(KTPtxt.toString(), PropertyPersister.dirFixOut + outputFile);
            context.put(MapKey.param5, outputFile);
            KTPtxt.delete(0, KTPtxt.length());
            InjectedVal.add(SchedulerUtil.getTime());
            InjectedVal.add(recordCount);

        } catch (Exception e) {
            getLogger().info("FAILED TO STREAM TEXT : " + e, e);
            FileUtil.writeWithFileChannerDMA(e.getMessage(), PropertyPersister.dirFixOut + outputFile);
        }
        return true;
    }

    private void getJavaParam(String delimiter) {
        try {
            //List parameter = SchedulerUtil.getParameter(PropertyPersister.KTPparamString, delimiter); // get String parameter
            List parameter = PropertyPersister.getRealParam(session, Integer.parseInt(context.get(MapKey.idScheduler).toString()), "B"); // get String parameter
            //List tempParameter = PropertyPersister.getRealParam(session, idScheduler, typFix);
            Map mapParam = (Map) parameter.get(0);
            Map lv1 = (Map) mapParam.get("STRING");
            if (lv1.isEmpty()) {
                throw new FIXException("PARAMETER INVALID");
            } else {
                String paramString = lv1.get("1").toString();
                parameter = SchedulerUtil.getParameter(paramString, delimiter);
            }

            this.Param1 = parameter.get(0).toString();
            this.HOSymbol = parameter.get(1).toString();;
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
            this.getProperties = "ektp_field.properties";
            this.separator = ","; // separator
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
        InputStream in = KTPLogRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        this.getSeparator = properties.getProperty(separator);
        in.close();
    }

    private void loadHeaderSetting() throws Exception {
        Properties properties = new Properties();
        InputStream in = KTPLogRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        textSetting = new ArrayList();
        numSequence = new ArrayList();
        columnSequence = new ArrayList();
        HeaderColSequence = new ArrayList();
        getLogger().info("COLUMNS :" + columnSetting);
        for (int h = 0; h < columnSetting.size(); h++) {
            textSetting.add(properties.getProperty(columnSetting.get(h).toString()));
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
                    HeaderColSequence.add(splitter.get(1).toString());
                    z = 0;
                    k++;
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

    private void loadSelectSetting(List column, int sizing) throws Exception {
        Properties properties = new Properties();
        InputStream in = KTPLogRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
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
        in.close();
    }

    private String formatDate(int dates, int year) {
        DateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, dates);
        calendar.set(Calendar.YEAR, year);
        return formatter.format(calendar.getTime());
    }

    /**
     * 
     * @param delimiter
     * @param sequence
     * @param sizing
     * @param model
     * @return
     */
    public String rearrangeString(String delimiter, List sequence, int sizing, EktpLog model) {
        StringBuilder SMS = new StringBuilder();
        Map ktpModel = new LinkedHashMap();
        StringBuilder KTPText = new StringBuilder();
        boolean flags = true;

        getLogger().info("VALUE FIRST :" + model.getIdLog());

        if (flags) {
            //getLogger().info("IDUSER :" + model.getClientIp());
            //getLogger().info("SEQUENCE : " + numSequence);
            ktpModel.put(sequence.get(0), model.getIdLog() == null ? "" : model.getIdLog());
            ktpModel.put(sequence.get(1), model.getIdUser() == null ? "" : model.getIdUser());
            ktpModel.put(sequence.get(2), model.getKtpUser() == null ? "" : model.getKtpUser());
            ktpModel.put(sequence.get(3), model.getInputNik() == null ? "" : model.getInputNik());
            ktpModel.put(sequence.get(4), model.getDtmRequest() == null ? "" : model.getDtmRequest());
            ktpModel.put(sequence.get(5), model.getDtmResponse() == null ? "" : model.getDtmResponse());
            ktpModel.put(sequence.get(6), model.getDatPost() == null ? "" : model.getDatPost());
            ktpModel.put(sequence.get(7), model.getDatLog() == null ? "" : model.getDatLog());
            ktpModel.put(sequence.get(8), model.getResponse() == null ? "" : model.getResponse());
            ktpModel.put(sequence.get(9), model.getFlgRespFromExternal() == null ? "" : model.getFlgRespFromExternal());
            ktpModel.put(sequence.get(10), model.getClientIp() == null ? "" : model.getClientIp());
            ktpModel.put(sequence.get(11), model.getDatLog() == null ? "" : model.getDatLog());
            ktpModel.put(sequence.get(12), model.getFlg_Nik_Found() == null ? "" : model.getFlg_Nik_Found());
            ktpModel.put(sequence.get(13), model.getCdBranch() == null ? "" : model.getCdBranch());
            ktpModel.put(sequence.get(14), model.getSystem_Src() == null ? "" : model.getSystem_Src());
            ktpModel.put(sequence.get(15), model.getMenuSrc() == null ? "" : model.getMenuSrc());
        }

        for (int i = 0; i < sizing; i++) {
            //getLogger().info("GETFIELD :" + ktpModel.get(i));
            SMS.append(ktpModel.get(i)).append(delimiter);
        }
        SMS.deleteCharAt(SMS.length() - 1);
        KTPText.append(SMS.toString());
        return KTPText.toString();
    }
}
