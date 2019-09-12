/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.SMSBallanceDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.SMSBallance;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author v00019722
 */
public class SMSRespGenerator extends BaseProcessor {

	private List<SMSBallance> getListBal;
	private String getProperties;
	private List textSetting;
    private List columnSetting;
	private String getSeparator;
	private String separator; // separator
    private String lineBreak;
    private String getLineBreak;
	private String Param1;
	private String patternDate;
    private String tableName;

    /**
     * 
     * @param context
     */
    public SMSRespGenerator(Map context) {
		super(context);
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		getJavaParam(StatusDefinition.delimiter);
		boolean condition = false;
		try {
			condition = context.get(MapKey.param1).equals(Param1) ? true : false;
		} catch (Exception e) {
			condition = true;
		}
		SMSBallanceDao smsDao = new SMSBallanceDao(session);
		FixSchedulerXtractDao xDao = new FixSchedulerXtractDao(session);
		if (condition) {
			// for populate UDF Table
            smsDao.runPopulate(1);
		} else {
			// for writing into textfile
            FixQXtract qxtract = (FixQXtract) this.context.get(MapKey.fixQXtract);
			int persist = PropertyPersister.FILEFETCH; // flush count
			int countRec = smsDao.countRowTable();
			int countSplit = (countRec / persist) < 1 ? 1 : ((countRec % persist == 0) ? countRec / persist : (countRec / persist) + 1);
			getLogger().info("COUNT ROWS :" + countRec);
			getLogger().info("COUNT SPLIT :" + countSplit);

			String outputFile = null;
			try {
				getLogger().info("IDSCHEDULER :" + context.get(MapKey.param2).toString());
				int idScheduler = Integer.parseInt(context.get(MapKey.param2).toString());
				FixSchedulerXtract xTract = xDao.get(idScheduler);
                
				outputFile = xTract.getFilePattern() + SchedulerUtil.getDate(patternDate) + "." + xTract.getFileFormat();
			} catch (NumberFormatException numberFormatException) {
				getLogger().info("FAILED TO PARSING ??" + numberFormatException, numberFormatException);
				outputFile = null;
			}
			int count = 0;
			int recordCount = 0;

            columnSetting = smsDao.getColumnName(2, this.tableName);
			loadHeaderSetting();
            loadLineSetting(lineBreak);
            getLogger().info("LINE BREAK :" + lineBreak);
            StringWriter out = new StringWriter();
            writerB writer = new writerB(out);
            
			try {
				StringBuilder SMStxt = new StringBuilder();

				int listSize = textSetting.size();
				getLogger().info("SETTING SIZE :" + listSize);
				for (int j = 0; j < listSize; j++) {
					SMStxt.append(textSetting.get(j).toString());
					if (j < listSize - 1) {
						SMStxt.append(this.separator);
					}
				}
                //SMStxt.deleteCharAt(SMStxt.length());
                getLogger().info("SMS TEXT :" + SMStxt.toString());
                writer.write(SMStxt.toString());
                writer.newLine();

                /*
                 * if (lineBreak.equalsIgnoreCase("LINEWINDOWS")) {
                 * SMStxt.append(getLineBreak); } else {
                 * SMStxt.append(System.getProperty(getLineBreak));
                }
                 */

				for (int i = 0; i < countSplit; i++) {
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
                    getLogger().info("Buffer SIze Before :" + out.getBuffer().length());
					this.getListBal = smsDao.SMSgreatData(beginCount, endCount, persist);
					for (SMSBallance balObj : getListBal) {
						String SMStoText = SMStextArrange(balObj);
						//out.write(SMStextArrange(balObj));
                        writer.write(SMStoText);
                        getLogger().info("LENGTH REC " + recordCount + ":" + SMStoText.length());
                        writer.newLine();
                        getLogger().info("Buffer REC :" + out.getBuffer().length());
						recordCount++;
						count++;
						getLogger().info("RECORDCOUNT :" + recordCount);
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
            
            getLogger().info("NAME FILE :" + context.get(MapKey.param5));
            qxtract.setParam5(context.get(MapKey.param5).toString());
		}
		return true;
	}

	private void getJavaParam(String delimiter) {
		try {
			List parameter = SchedulerUtil.getParameter(PropertyPersister.ParamString, delimiter); // get String parameter
			this.Param1 = parameter.get(0).toString();
			this.patternDate = parameter.get(1).toString();
			this.getProperties = parameter.get(3).toString();
            this.tableName = parameter.get(4).toString();
            this.lineBreak = parameter.get(5).toString();
			if (parameter.get(2).toString().trim().length() > 1) {
				loadSeparatorSetting(parameter.get(2).toString());
				this.separator = this.getSeparator;
			} else {
				this.separator = parameter.get(2).toString();
			}
		} catch (Exception e) {
			getLogger().info("EXCEPTION : FAILED GET JAVA PARAM - " + e, e);
			this.getProperties = "sms_field.properties";
			this.separator = "\t"; // separator
			this.Param1 = "POPULATE";
			this.patternDate = "yyyyMMddHHmmss";
            this.tableName = "TMP_SMS_BALLANCE";
            this.lineBreak = "LINEWINDOWS";
		}
	}

	private String SMStextArrange(SMSBallance ballObj) {
		StringBuilder SMSText = new StringBuilder();
		SMSText.append(ballObj.rearrangeString(this.separator));
		return SMSText.toString();
	}

	private void loadSeparatorSetting(String separator) throws Exception {
		Properties properties = new Properties();
		InputStream in = SMSRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
		properties.load(in);
		this.getSeparator = properties.getProperty(separator);
	}
	private void loadHeaderSetting() throws Exception {
		Properties properties = new Properties();
		InputStream in = SMSRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
		properties.load(in);
		textSetting = new ArrayList();
        for(int h=0; h<columnSetting.size();h++){
            textSetting.add(properties.getProperty(columnSetting.get(h).toString().toLowerCase()));
        }
		in.close();
	}

    private void loadLineSetting(String separator) throws Exception {
        Properties properties = new Properties();
        InputStream in = SMSRespGenerator.class.getClassLoader().getResourceAsStream(getProperties);
        properties.load(in);
        this.getLineBreak = properties.getProperty(separator);
    }
}


class writerB extends BufferedWriter {
    
    public writerB(Writer w){
        super(w);
    }
    @Override
    public void newLine() throws IOException {
        this.write("\r\n");
    }
}