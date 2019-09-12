/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import sun.misc.BASE64Encoder;
import bdsm.scheduler.IBdsmClass;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.exception.SkipProcessException;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.MailUtil;
import bdsm.util.EncryptionUtil;
import bdsm.util.SchedulerUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * 
 * @author bdsm
 */
public abstract class BaseProcessor implements IBdsmClass {
	private static final Logger LOGGER = Logger.getLogger(BaseProcessor.class.getName()); 
	private String pathOk;
	private String pathErr;
	
	
	public BaseProcessor(Map context) {
		this.context = context;
		if (context.get(MapKey.typeFix).toString() == StatusDefinition.IN) {
			pathOk = PropertyPersister.dirFixInOk;
			pathErr = PropertyPersister.dirFixInErr;
		}
		else if (context.get(MapKey.typeFix).toString() == StatusDefinition.XTRACT) {
			pathOk = PropertyPersister.dirFixOutOk;
			pathErr = PropertyPersister.dirFixOutErr;
		}
	}

	public boolean execute() throws Exception {
		LOGGER.debug("Initiate Session");
		this.session = (Session) this.context.get(MapKey.session);
		this.tx = session.beginTransaction();
		LOGGER.debug("Begin Transaction: " + this.tx.toString());
		boolean result = false;
		boolean hasFile = (context.get(MapKey.param5) != null);
		
		try {
			LOGGER.debug("Begin Execute()");
			result = doExecute();
			LOGGER.debug("Flush Session");
			this.session.flush();
			LOGGER.debug("Commit Session");
			this.tx.commit();
			LOGGER.debug("Clear Session");
			this.session.clear();
			this.context.put(MapKey.fixQXtract, this.fixQXtract);
			LOGGER.debug("CONTEXT :" + this.context);
            if ((hasFile) && (context.get(MapKey.typeFix).toString().equals(StatusDefinition.IN))) {
				FileUtil.moveFile(context.get(MapKey.param5).toString(), pathOk, "");
            }
        } catch (SkipProcessException exception) {
            LOGGER.info("EX :" + exception, exception);
            throw exception;
        } catch (Exception ex) {
			LOGGER.error("Execute Base Procesor Error");
			LOGGER.error(ex, ex);
			// this.session.flush();
			if (this.tx.isActive())
				this.tx.rollback();
			setFixQXtract(ex);
			this.session.clear();
			if (hasFile) {
				FileUtil.moveFile(context.get(MapKey.param5).toString(), pathErr, "");
				// context.remove(MapKey.param5);
			}
			throw ex;
		}
		return result;
	}

	protected abstract boolean doExecute() throws Exception;
	
	protected Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}
	
	
	private void setFixQXtract(Exception ex) throws Exception {
		String errFile = PropertyPersister.dirFixOut + 
				((context.get(MapKey.param5)!=null)? FilenameUtils.getBaseName(context.get(MapKey.param5).toString()): this.getClass().getSimpleName()) 
				+ ".err";
		String errMsg = "";
		int idScheduler = ScheduleDefinition.emailOnly;
		
		LOGGER.error("Set FixQXtract for Error Information to Sender");
		LOGGER.error("errFile: " + errFile);
		
		try {
			FIXException fie = (FIXException) ex;
			idScheduler = fie.getIdSchedulerXtractReporter();
		}
		catch (Exception ee) {
			LOGGER.error(ee.getClass().getName() + " is not FIXException" );
		}
		
		try {
			PrintStream p = new PrintStream(new File(errFile));
			ex.printStackTrace(p);
			EncryptionUtil.ZIPcompress(errFile, errFile + ".zip", null);
	
			InputStream is = new FileInputStream(errFile + ".zip");
			errMsg = new BASE64Encoder().encode(IOUtils.toByteArray(is));
			FileUtils.deleteQuietly(new File(errFile));
			FileUtils.deleteQuietly(new File(errFile + ".zip"));
			
			
			this.fixQXtract = new FixQXtract();
			this.fixQXtract.setIdScheduler(idScheduler);
			this.fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
			this.fixQXtract.setDtmRequest(SchedulerUtil.getTime());
			this.fixQXtract.setReason(ex.getMessage());
			
			if (this.context.get(MapKey.param1) != null)
				this.fixQXtract.setParam1(MailUtil.createEmailSubject(this.context.get(MapKey.param1).toString()));
			if (this.context.get(MapKey.param2) != null)
				this.fixQXtract.setParam2(this.context.get(MapKey.param2).toString());
			
			this.fixQXtract.setParam4("BDSM Worker Error, Please Contact Application Support.<br/><br/>-- Begin Message --<br/>" 
					+ errMsg + "<br/>-- End Message --");
			
			this.context.put(MapKey.fixQXtract, this.fixQXtract);
		}
		catch (Exception e) {
			LOGGER.error(e, e);
		}
}

	protected Map context;
	protected Session session;
	protected FixQXtract fixQXtract;
	protected Transaction tx;

}
