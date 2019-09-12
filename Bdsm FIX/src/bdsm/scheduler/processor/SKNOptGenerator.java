/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

/**
 *
 * @author bdsm
 */

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.ErrorCodeDao;
import bdsm.scheduler.util.FileUtil;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public abstract class SKNOptGenerator extends BaseProcessor {
    protected String namTemplate;
	protected String BatchID;
	protected Integer checknumHeader;
	protected Integer checknumDetails;
	protected Integer checknumFooter;
	protected Integer checknumHeaderFooter;
	protected String delimiter;
	protected String reason;
	protected String ErrValue;
	protected StringBuilder CrcCheck = new StringBuilder();
    protected String outputFile;
	protected Integer CheckSum;
    Logger LOGGER = Logger.getLogger(SKNOptGenerator.class); 
    
    
    public SKNOptGenerator(Map context) {
		super(context);
	}
    
    public abstract boolean validate(Map context);
	public abstract StringBuilder detailS();
    
    @Override
	public boolean doExecute() throws Exception {
		HashMap cont = new HashMap();
		Integer loopCount = 0;
		Integer k;
		
		try {

			ErrorCodeDao errDao = new ErrorCodeDao(session);

			getLogger().info("Start Generate File Response" + context.get(MapKey.reportFileName));
			LOGGER.debug("Get Parameter1 :" + context.get(MapKey.param1));
			LOGGER.debug("Get Parameter2 :" + context.get(MapKey.param2));
			LOGGER.debug("Get Parameter3 :" + context.get(MapKey.param3));
			LOGGER.debug("Get Parameter4 :" + context.get(MapKey.param4));
			LOGGER.debug("Get Parameter5 :" + context.get(MapKey.param5));
			LOGGER.debug("Get parameter6 :" + context.get(MapKey.param6));
			LOGGER.debug("Get Hidden Parameter 1 :" + context.get(MapKey.hdUserid));
			LOGGER.debug("Get Hidden Parameter 2 :" + context.get(MapKey.hdcdBranch));
			LOGGER.debug("Get Hidden Parameter 3 :" + context.get(MapKey.hddtProcess));
			LOGGER.debug("Get Hidden Parameter 4 :" + context.get(MapKey.hdidTemplate));
			LOGGER.debug("Get Hidden Parameter 5 :" + context.get(MapKey.hdnamUser));
			LOGGER.debug("Get Java Class :" + context.get(MapKey.javaClass));
			LOGGER.debug("Get Report ID  :" + context.get(MapKey.reportId));
			LOGGER.debug("Get Report Format:" + context.get(MapKey.reportFormat));
			LOGGER.debug("Get Type Fix :" + context.get(MapKey.typeFix));
			this.outputFile = context.get(MapKey.reportFileName).toString();
			//this.context.put(MapKey.batchNo, BatchID);
			//this.context.put(MapKey.templateName,namTemplate);

			//Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile));
			StringWriter write = new StringWriter();
            StringBuilder detail = null;
            //if (!"".equalsIgnoreCase(detail.toString())) {
				try {
					if (validate(context)){
                        try {
                            detail.append(detailS());
                            //write.write(detail.toString());
                            //write.flush();
                            //FileUtil.writeWithFileChannerDMA(write.getBuffer().toString(), PropertyPersister.dirFixOut + outputFile);
                        } catch (Exception e) {
                            getLogger().info("FAILED GETTING RESPOND :" + e,e);
                        }
                    }
				} catch (Exception e) {
					getLogger().info("FAILED VALIDATE RESPOND :" + e,e);
					//write.write(e.getMessage());
					//write.close();
				} finally {
					write.flush();
					write.close();
				}
			//}			
		} catch (Exception e) {
			getLogger().info("FAILED GENERATED RESPOND :" + e,e);
			outputFile = context.get(MapKey.reportFileName).toString();
			Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile));
			out.write(e.getMessage());
			out.close();
		}
	return true;
	}
}
