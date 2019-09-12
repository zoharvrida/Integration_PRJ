/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.SMSAutoProfileDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.TmpSMSTrans;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import bdsmhost.dao.ParameterDao;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public class SMSAutoProfiler extends SchTimerSync {

    private final String DefaultParam = "ParamString";
    private final String endStamp = "StampRec";
    private List<TmpSMSTrans> getListSMS;
    private String getProperties;
    private List textSetting;
    private List columnSetting;
    private String getSeparator;
    private String separator; // separator
    private String lineBreak;
    private String getLineBreak;
    private String dbProfile;
    private String patternDate;
    private String tableName;
    private String callFunction;
    private String condition;
    private String fileName;
    private Integer typDB;
    private int seqNumber;
    private String returnFunction;
    private String BatchId;
    private Integer idScheduler;
    private String typFix;

    public SMSAutoProfiler(Map context) {
        super(context);
    }

    private void getJavaParam(String delimiter, String pattern) {
        try {
            getLogger().info("PATTERN :" + pattern);
            List parameter = null;
            try {
                parameter = SchedulerUtil.getParameter(PropertyPersister.class.getDeclaredField(pattern).get(pattern).toString(), delimiter); // get String parameter
            } catch (Exception ex) {
                getLogger().debug("EXception Begin :" + pattern);
                List tempParameter = PropertyPersister.getRealParam(session, idScheduler, typFix);
                Map mapParam = (Map) tempParameter.get(0);
                Map lv1 = (Map) mapParam.get("STRING");
                if (lv1.isEmpty()) {
                    throw new FIXException("PARAMETER INVALID");
                } else {
                    String paramString = lv1.get("1").toString();
                    parameter = SchedulerUtil.getParameter(paramString, delimiter);
                }
            }
            getLogger().info("LIST PARAMETER :" + parameter);
            this.fileName = parameter.get(0).toString();
            this.patternDate = parameter.get(1).toString();
            if (DefaultParam.equalsIgnoreCase(pattern)) {
                this.getProperties = parameter.get(3).toString();
                if (parameter.get(2).toString().trim().length() > 1) {
                    loadSeparatorSetting(parameter.get(2).toString());
                    this.separator = this.getSeparator;
                } else {
                    this.separator = parameter.get(2).toString();
                }
                this.lineBreak = parameter.get(5).toString();
            } else {
                this.callFunction = parameter.get(3).toString();
                try {
                    this.condition = context.get(MapKey.param4).toString();
                    if ("${Param4}".equalsIgnoreCase(this.condition)) {
                        this.condition = parameter.get(2).toString();
                    }
                    getLogger().info("PARAM4 :" + context.get(MapKey.param4).toString());
                } catch (Exception e) {
                    getLogger().info("next param : " + e, e);
                    if (this.condition == null) {
                        this.condition = "NONE";
                    } else {
                        this.condition = parameter.get(2).toString();
                    }
                }

                this.dbProfile = parameter.get(5).toString();
                this.typDB = Integer.parseInt(parameter.get(6).toString());
            }
            this.tableName = parameter.get(4).toString();
        } catch (Exception e) {
            getLogger().info("EXCEPTION : FAILED GET JAVA PARAM - " + e, e);
        }
    }

    private String SMStextArrange(TmpSMSTrans ballObj) {
        StringBuilder SMSText = new StringBuilder();
        SMSText.append(ballObj.rearrangeString(this.separator));
        return SMSText.toString();
    }

    private void loadSeparatorSetting(String separator) throws Exception {
        Properties properties = new Properties();
        InputStream in = SMSAutoProfiler.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        this.getSeparator = properties.getProperty(separator);
    }

    private void loadHeaderSetting() throws Exception {
        Properties properties = new Properties();
        InputStream in = SMSAutoProfiler.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        textSetting = new ArrayList();
        for (int h = 0; h < columnSetting.size(); h++) {
            textSetting.add(properties.getProperty(columnSetting.get(h).toString().toLowerCase()));
        }
        in.close();
    }

    private void loadLineSetting(String separator) throws Exception {
        Properties properties = new Properties();
        InputStream in = SMSAutoProfiler.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        this.getLineBreak = properties.getProperty(separator);
    }

    @Override
    protected boolean doExecute(Map context) throws Exception {
        String condition = null;
        String patternContext = null;
        try {
            patternContext = context.get(MapKey.param1).toString();
            this.idScheduler = Integer.parseInt(context.get(MapKey.param2).toString());
            this.typFix = context.get(MapKey.typeFix).toString();
            getLogger().info("FLAG RUNNING :" + context.get(MapKey.param5).toString());
            try {
                List filepath = SchedulerUtil.getParameter(context.get(MapKey.param5).toString(), "/");
                this.runningFlag = (filepath.get(filepath.size()-1).toString().equalsIgnoreCase("1")) ? false : true;
                getLogger().info("FLAG RUN :" + this.runningFlag);
            } catch (Exception e) {
                getLogger().info("FLAG GET :" + e,e);
                this.runningFlag = true;
            }
            // revision for get default param
            getJavaParam(StatusDefinition.delimiter, DefaultParam);
            getJavaParam(StatusDefinition.delimiter, patternContext);
            //condition = context.get(MapKey.param1).equals(Param1) ? true : false;
        } catch (Exception e) {
            // Pattern null
            getLogger().info("COULDN'T GET PATTERN : " + e, e);
            return false;
        }

        SMSAutoProfileDao smsDao = new SMSAutoProfileDao(session);
        FixSchedulerXtractDao xDao = new FixSchedulerXtractDao(session);
        ParameterDao pDao = new ParameterDao(session);
        List separateSeq = new ArrayList();

        getLogger().info("IDSCHEDULER :" + context.get(MapKey.param2).toString());
        this.idScheduler = Integer.parseInt(context.get(MapKey.param2).toString());
        FixSchedulerXtract xTract = xDao.get(idScheduler);

        if (StatusDefinition.Populate.equalsIgnoreCase(this.condition)) {

                // package with i/o 
                // i/o default value refer to FieldParameter based on idScheduler, and identity ID
                String delimiter = "";
                StringBuilder valueDelim = new StringBuilder();
                StringBuilder typeDelim = new StringBuilder();
            try {
                List aliasing = pDao.getFieldParameter(idScheduler, typFix, StatusDefinition.customSMSParam);
                for (Object mapper : aliasing) {
                    if (mapper != null) {
                        Map tempValue = (Map) mapper;
						getLogger().info("MAPTEMP :" + tempValue);
                        delimiter = tempValue.get("formatRule").toString();
                        valueDelim.append(tempValue.get("fieldName").toString()).append(delimiter);
                        typeDelim.append(tempValue.get("formatData").toString()).append(delimiter);
                    }
                }
                if(aliasing.isEmpty()){
                    returnFunction = smsDao.runQuery(typDB, this.callFunction);
                } else {
                    getLogger().info("QUERY PARAM :"+valueDelim.toString());
                returnFunction = smsDao.runQueryWithParam(typDB, this.callFunction, valueDelim.toString(), typeDelim.toString(), aliasing.size(), delimiter);
                valueDelim.delete(0, valueDelim.length()-1);
                typeDelim.delete(0, valueDelim.length()-1);
                }
                
            } catch (Exception e) {
                getLogger().info("LIST EXCEPTION :" + e,e);
                returnFunction = smsDao.runQuery(typDB, this.callFunction);
            }

            getLogger().info("BEGIN POPULATE DATA :" + this.condition);
            if (!"Report".equalsIgnoreCase(xTract.getTypDest())) {
                return false;
            }
        } else {
            getLogger().info("BEGIN STRAIGHT DATA :" + this.condition);
            if (StatusDefinition.Direct.equalsIgnoreCase(this.condition)) {
                returnFunction = smsDao.runQuery(typDB, this.callFunction);
                if (typDB.equals(2)) {
                    separateSeq = SchedulerUtil.getParameter(returnFunction, ",");
                    BatchId = separateSeq.get(1).toString();
                    seqNumber = Integer.parseInt(separateSeq.get(0).toString());
                }
            } else {
                if (typDB.equals(1) || typDB.equals(5)) {
                    // procedure get value from param3
                    seqNumber = Integer.parseInt(context.get(MapKey.param3).toString());
                    BatchId = context.get(MapKey.param6).toString();
                }
            }
            // for writing into textfile
            FixQXtract qxtract = (FixQXtract) this.context.get(MapKey.fixQXtract);
            int persist = PropertyPersister.FILEFETCH; // flush count
            int countRec = smsDao.countRowTable(dbProfile, BatchId);
            int countSplit = (countRec / persist) < 1 ? 1 : ((countRec % persist == 0) ? countRec / persist : (countRec / persist) + 1);
            getLogger().info("COUNT ROWS :" + countRec);
            getLogger().info("COUNT SPLIT :" + countSplit);

            String outputFile = null;
            try {
                try {
                    outputFile = this.fileName + SchedulerUtil.getDate(patternDate) + "." + xTract.getFileFormat();
                } catch (Exception ex) {
                    getLogger().debug("FAILED TO PARSING 1ST LEVEL ??" + ex);
                    try {
                        List optParam = SchedulerUtil.getParameter(patternDate, ":");
                        String sequence = null;
                        Integer seqID = smsDao.getSequence();
                        sequence = bdsm.util.BdsmUtil.leftPad(seqID.toString(), Integer.parseInt(optParam.get(1).toString()), '0');
                        try {
                            if ("R".equalsIgnoreCase(optParam.get(0).toString())) {
                                outputFile = this.fileName + SchedulerUtil.getDate(optParam.get(2).toString()) + sequence + "." + xTract.getFileFormat();
                            } else if ("L".equalsIgnoreCase(optParam.get(0).toString())) {
                                outputFile = this.fileName + sequence + SchedulerUtil.getDate(optParam.get(2).toString()) + "." + xTract.getFileFormat();
                            }
                        } catch (ParseException ez) {
                            getLogger().debug("FAILED TO PARSING 3RD LEVEL ??" + ez);
                            if ("R".equalsIgnoreCase(optParam.get(0).toString())) {
                                outputFile = this.fileName + optParam.get(2).toString() + sequence + "." + xTract.getFileFormat();
                            } else if ("L".equalsIgnoreCase(optParam.get(0).toString())) {
                                outputFile = this.fileName + sequence + optParam.get(2).toString() + "." + xTract.getFileFormat();
                            }
                        }
                    } catch (Exception e) {
                        getLogger().debug("FAILED TO PARSING 2ND LEVEL ??" + e);
                        outputFile = this.fileName + patternDate + "." + xTract.getFileFormat();
                    }
                }
            } catch (NumberFormatException numberFormatException) {
                getLogger().info("FAILED TO PARSING ??" + numberFormatException, numberFormatException);
                outputFile = null;
            }
            int count = 0;
            int recordCount = 0;

            columnSetting = smsDao.getColumnName(4, this.tableName);
            try {
                loadHeaderSetting();
            } catch (Exception ex) {
                Logger.getLogger(SMSAutoProfiler.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                loadLineSetting(lineBreak);
            } catch (Exception ex) {
                Logger.getLogger(SMSAutoProfiler.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLogger().info("LINE BREAK :" + lineBreak);
            StringWriter out = new StringWriter();
            autoWriter writer = new autoWriter(out);

            try {
                StringBuilder SMStxt = new StringBuilder();

                int listSize = textSetting.size();
                getLogger().info("SETTING SIZE :" + listSize);
                for (int j = 0; j < listSize - 1; j++) {
                    SMStxt.append(textSetting.get(j).toString());
                    //getLogger().info("FIELD :" + SMStxt);
                    if (j < listSize - 2) {
                        SMStxt.append(this.separator);
                    }
                }
                //SMStxt.deleteCharAt(SMStxt.length());
                //getLogger().info("SMS TEXT :" + SMStxt.toString());
                try {
                    writer.write(SMStxt.toString());
                    writer.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(SMSAutoProfiler.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i = 0; i < countSplit; i++) {
                    //getLogger().info("SPLIT :" + i);
                    BigDecimal beginCount = null;
                    if (i > 0) {
                        beginCount = new BigDecimal(recordCount + 1);
                    } else {
                        beginCount = new BigDecimal(recordCount);
                    }

                    BigDecimal endCount = new BigDecimal(countSplit == 1 ? countRec : (((i + 1) * persist) > countRec) ? countRec : (persist));
                    getLogger().info("BEGIN :" + beginCount);
                    BigDecimal lastCount = endCount.subtract(beginCount);
                    getLogger().info("END :" + endCount);
                    getLogger().info("MAX RESULT :" + lastCount);
                    getLogger().info("Buffer SIze Before :" + out.getBuffer().length());
                    
                    this.getListSMS = smsDao.SMSgreatData(beginCount, lastCount, this.dbProfile, this.BatchId);
                    for (TmpSMSTrans balObj : getListSMS) {
                        String SMStoText = SMStextArrange(balObj);
                        writer.write(SMStoText);
                        writer.newLine();
                        count++;
                    }
                    getLogger().info("LINE Length : " + out.getBuffer().length());

                    writer.flush();
                    FileUtil.writeWithFileChannerDMA(out.getBuffer().toString(), PropertyPersister.dirFixOut + outputFile);
                    //getLogger().info("LINE : " + out.getBuffer().toString());
                    context.put(MapKey.param5, outputFile);
                    SMStxt.delete(0, SMStxt.length());
                    //out.close();
                    out.getBuffer().delete(0, out.getBuffer().length());
                    getLogger().info("LINE DELETE : " + out.getBuffer().toString());
                    session.clear();
                    //writer.close();
                }
                //out.close();
            } catch (NumberFormatException numberFormatException) {
                getLogger().info("FAILED CONVERT TO NUMBER :" + numberFormatException, numberFormatException);
            } finally {
                writer.close();

            }
            try {
                smsDao.runStamp(seqNumber, endStamp);
            } catch (Exception e) {
                getLogger().info("FAILED STAMP TABLE :" + e, e);
            }
            getLogger().info("NAME FILE :" + context.get(MapKey.param5));
            qxtract.setParam5(context.get(MapKey.param5).toString());
        }
        return true;
    }

    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(Integer idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the typFix
     */
    public String getTypFix() {
        return typFix;
    }

    /**
     * @param typFix the typFix to set
     */
    public void setTypFix(String typFix) {
        this.typFix = typFix;
    }
}

class autoWriter extends BufferedWriter {

    public autoWriter(Writer w) {
        super(w);
    }

    @Override
    public void newLine() throws IOException {
        this.write("\r\n");
    }
}