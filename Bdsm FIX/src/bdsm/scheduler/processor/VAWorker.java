package bdsm.scheduler.processor;


import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.dao.TmpVADao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixQXtract;
import bdsm.service.VAService;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.BdsmUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.FcrChAcctMastDao;
import bdsmhost.dao.TransactionParameterDao;


/**
 * 
 * @author bdsm
 */
public class VAWorker extends BaseProcessor {
	private static final Logger logger = Logger.getLogger(VAWorker.class);

	private static final String EMAIL_NEED_APPROVAL = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your approval is required for the attached file to be processed further. <br/>"
			+ "<br/>" + "Please reply this email with:<br/>"
			+ "<b>Ok</b>, if you approve the file to be processed, or<br/>"
			+ "<b>Not ok</b>, if otherwise.<br/>" + "<br/>"
			+ "Thanks & regards,<br/>" + "- BDSM -";
	private static final String EMAIL_REJECTED = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your requested process Cash Pick Up has been Rejected by Supervisor. <br/>"
			+ "<br/>" + "Thanks & regards,<br/>" + "- BDSM -";
	
	

    /**
     * 
     * @param context
     */
    public VAWorker(Map<String, Object> context) {
		super(context);
	}
	

    /**
     * 
     * @return
     */
    protected String getNeedApprovalMessage() {
		return EMAIL_NEED_APPROVAL;
	}
	
    /**
     * 
     * @return
     */
    protected String getRejectedMessage() {
		return EMAIL_REJECTED;
	}
	
    /**
     * 
     * @return
     */
    protected VAService getService() {
		return new VAService();
	}
	

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		logger.info("[BEGIN] VAWorker Execution");
		
		FixInboxDao fixInboxDao = new FixInboxDao(this.session);
		FixEmailAccessDao fixEmailAccessDAO = new FixEmailAccessDao(session);
		FixClassConfigDao fixClassConfigDAO = new FixClassConfigDao(this.session);
		
		int idScheduler = (Integer) this.context.get(MapKey.idScheduler);
		String status = (String) this.context.get(MapKey.status);
		String batchNo = (String) this.context.get(MapKey.batchNo);
		String inboxId = (String) this.context.get(MapKey.inboxId);
		String groupId = (String) this.context.get(MapKey.grpId);
		String processSource = (String) this.context.get(MapKey.processSource);
		String itemIdLink = (String) this.context.get(MapKey.itemIdLink);
		String param1 = (String) this.context.get(MapKey.param1);
		String param2 = (String) this.context.get(MapKey.param2);
		String param5 = (String) this.context.get(MapKey.param5);
		String flagChecksum = (String) this.context.get(MapKey.flgChecksum);
		String templateName = (String) this.context.get(MapKey.templateName);
		
		/* Business Service and Injection Fields */
		VAService vaService = this.getService();
		vaService.setSession(this.session);
		vaService.setTrxParamDAO(new TransactionParameterDao(this.session));
		vaService.setFcrBaBankMasterDAO(new FcrBaBankMastDao(this.session));
		vaService.setFcrChAcctMastDAO(new FcrChAcctMastDao(this.session));
		vaService.setTmpVADAO(new TmpVADao(this.session));
		vaService.setTmpGefuResponseDAO(new TmpGefuResponsDao(this.session));
		
		
		File file = null;
		int idSchedulerXtract = ScheduleDefinition.emailOnly;
		String fullClassName = this.getClass().getName();
		Reader reader = null;
		
		if (StringUtils.isEmpty(param5) == false)
			file = new File(param5);
		
		try {
			idSchedulerXtract = fixClassConfigDAO.get(fullClassName, processSource, status).get(0).getIdScheduler();
		}
		catch (Exception ex) {}
		
		try {
			logger.info(fullClassName + " - Status: " + status);
			java.util.Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
				
			if (status.equalsIgnoreCase(StatusDefinition.UNAUTHORIZED)) {
				reader = new FileReader(file);
				CharArrayWriter caw = new CharArrayWriter();
				char[] buffer = new char[1024];
				int read;
				
				while ((read = reader.read(buffer)) != -1) {
					if (read < 1024) { // Often last time read
						char[] temp = new char[read];
						System.arraycopy(buffer, 0, temp, 0, read);
						buffer = temp;
					}
					caw.write(buffer);
				}
				
				dataMap.put("filename", file.getName());
				dataMap.put("buffer", caw.toCharArray());
				dataMap.put("templateName", templateName);
				dataMap.put("flagChecksum", (flagChecksum==null)? Boolean.FALSE: flagChecksum.equalsIgnoreCase("Y"));
				dataMap.put("strChecksum", BdsmUtil.getChecksum(file.getPath()));
				
				vaService.importGEFU(batchNo, dataMap);

				String outFileName;
				if (processSource.equalsIgnoreCase(ScheduleDefinition.Email)) {
					outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(param1.substring(7)) + "_{HHmmss}.xls");
					
					logger.info("Register To FixQXtract");
					this.fixQXtract = 
						VAWorker.createFixQXtract(idSchedulerXtract, StatusDefinition.REQUEST, SchedulerUtil.getTime(), 
							"RE: " + param1,
							fixEmailAccessDAO.getSpv(groupId, idScheduler), 
							null,
							EMAIL_NEED_APPROVAL, 
							outFileName, 
							batchNo
						);
				}
			}
			else if (status.equalsIgnoreCase(StatusDefinition.AUTHORIZED)) {
				dataMap.put("itemIdLink", itemIdLink);
				dataMap.put("idSchedulerXtract", idSchedulerXtract);
				dataMap.put("templateName", templateName);
				
				vaService.authorizeGEFU(batchNo, dataMap);
			}
			else if (status.equalsIgnoreCase(StatusDefinition.REJECTED)) {
				vaService.rejectGEFU(batchNo);
				
				this.fixQXtract = 
					VAWorker.createFixQXtract(idSchedulerXtract, StatusDefinition.REQUEST, SchedulerUtil.getTime(), 
						param1,
						(StringUtils.isBlank(itemIdLink) == false)? fixInboxDao.get(itemIdLink).getSender(): null, 
						null,
						EMAIL_REJECTED, 
						"" 
					);

			}
		} catch (Exception ex) {
			logger.error(ex, ex);
			
			this.session.clear();
			boolean isFixException = ex.getClass().isAssignableFrom(FIXException.class);
			boolean isFromEmail = processSource.equalsIgnoreCase(ScheduleDefinition.Email); 
			this.updateInboxAndLogReason(
					isFromEmail? inboxId: batchNo, 
					ex.getMessage()==null? ex.toString(): ex.getMessage(), 
					isFixException, 
					isFromEmail
			);
			
			/*
			if (isFixException) {
				if (isFromEmail) {
					this.context.put(MapKey.typeFix, "");
					
					this.fixQXtract = 
							VAWorker.createFixQXtract(ScheduleDefinition.emailOnly, StatusDefinition.REQUEST, SchedulerUtil.getTime(), 
								"[ERROR] RE: " + param1.replaceAll("[R|r][E|e]:", ""),
								param2, 
								null,
								ex.getMessage(),
								""
							);
					this.fixQXtract.setReason(ex.getMessage());
				}
			}
			else */
				throw ex;
		}
		finally {
			if (reader != null) {
				reader.close();
				reader = null;
			}
			
			// If Windows system, then do garbage collection, in order to delete file in folder fixinproc
			if (File.separatorChar == '\\')
				System.gc();
			
			logger.info("[END] VAWorker Execution");
		}

		return true;
	}

    /**
     * 
     * @param inboxId
     * @param reason
     * @param updateFlagProcess
     * @param isFromEmail
     */
    protected void updateInboxAndLogReason(String inboxId, String reason, boolean updateFlagProcess, boolean isFromEmail) {
		Session hbs = HibernateUtil.getSessionFactory().openSession();
		Transaction hbt = hbs.beginTransaction();
		
		FixInboxDao fixInboxDao = new FixInboxDao(hbs);
        FixInbox fixInbox = fixInboxDao.get(inboxId);
        FixLogDao fixLogDao = new FixLogDao(hbs);
        FixLog fixLog = null;
        
		if (isFromEmail) {
	        fixInbox.setReason((reason.length() <= 200)? reason: reason.substring(0, 200));
	        if (updateFlagProcess)
	        	fixInbox.setFlgProcess(StatusDefinition.ERROR);
	        
	        fixLog = fixLogDao.get(!StringUtils.isEmpty(fixInbox.getItemIdLink())? fixInbox.getItemIdLink(): inboxId);
		}
		else {
			fixLog = fixLogDao.get(inboxId);
		}
        
        fixLog.setReason(reason);
        if (updateFlagProcess)
        	fixLog.setFlgProcess(StatusDefinition.ERROR);
        
		hbt.commit();
		hbs.close();
	}

    /**
     * 
     * @param idScheduler
     * @param flagProcess
     * @param dateTimeRequest
     * @param params
     * @return
     */
    protected static FixQXtract createFixQXtract(Integer idScheduler, String flagProcess, java.sql.Timestamp dateTimeRequest, String ... params) {
		FixQXtract fqx = new FixQXtract();
		fqx.setIdScheduler(idScheduler);
		fqx.setFlgProcess(flagProcess);
		fqx.setDtmRequest(dateTimeRequest);
		
		Class<FixQXtract> clazz = FixQXtract.class;
		java.lang.reflect.Method method;
		
		for (int i=0; i<params.length; i++) {
			if (params[i] == null) continue;
			
			try {
				method = clazz.getMethod("setParam" + (i+1), String.class);
				method.invoke(fqx, params[i]);
			}
			catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}

		return fqx;
	}
}
