package bdsm.scheduler.email;


import bdsm.scheduler.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixTemplateMasterDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixTemplateMaster;
import bdsm.scheduler.model.FixTemplateMasterPK;
import bdsm.scheduler.processor.FileObjectReflection;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.MailUtil;
import bdsm.util.SchedulerUtil;


/**
 * 
 * @author v00019372
 */
public class FileInWatcherWorker extends FileWatcherWorker {
	private static Logger LOGGER = Logger.getLogger(FileInWatcherWorker.class); 
	private static String adminEmail = PropertyPersister.adminEmail;
	
	
	public FileInWatcherWorker(FolderWatcherListener parent, File file, String prependFileBackup) {
		super(parent, file, prependFileBackup);
	}

	@Override
	public boolean processFile(File file, String prependFileBackup) {
		FileObjectReflection objectReflection = new FileObjectReflection();
		Transaction transaction = this.session.beginTransaction();
		int fileStatus = this.parent.getStatus(file);
		
		try {
            try {
                LOGGER.debug(System.getenv());
            } catch (Exception e) {
                LOGGER.debug("ENVIRONMENT VARIABLE DOESN'T EXIST : FILE");
            }
			FixLogDao fixLogDao = new FixLogDao(this.session);
			FixLog fixLog = null;

			try {
				// managed object FixLog by Hibernate
				fixLog = fixLogDao.get(file.getName(), SchedulerUtil.getDate("dd/MM/yyyy"));
				
				if (fixLog == null) {
					throw new Exception("file '" + file.getName() + "'is not registered in fix Log");
				}
			} catch (ParseException ex) {
				LOGGER.error(ex, ex);
			}

			// validation
			FixSchedulerImportDao fixSchedulerImportDao = new FixSchedulerImportDao(this.session);
			String namTemplate = FileUtil.getTemplateFromFileName(file.getName());
			LOGGER.debug("Template Name : " + namTemplate);
			FixSchedulerImport fixSchedulerImport = fixSchedulerImportDao.get(namTemplate);

			if ((fixSchedulerImport != null) && ("A".equals(fixSchedulerImport.getFlgStatus()))) { // Active scheduler
				LOGGER.info("Processing file : " + this.file);
				
				// If from inactive scheduler import, and suddenly scheduler import become active again
				if (fileStatus == 1)
					this.parent.setStatus(file, 2);
				
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put(MapKey.flgEncrypt, "N");
				hashMap.put(MapKey.templateName, namTemplate);
				if (fixSchedulerImport.getFlgEncrypt().equalsIgnoreCase("Y")) {
					try {
						hashMap.put(MapKey.flgEncrypt, fixSchedulerImport.getFlgEncrypt());
						LOGGER.debug("FLG Encrypt : " + fixSchedulerImport.getFlgEncrypt());
						hashMap.put(MapKey.modDecrypt, fixSchedulerImport.getModDecrypt());
						LOGGER.debug("Mod Encrypt : " + fixSchedulerImport.getModDecrypt());
						hashMap.put(MapKey.keyDecrypt, fixSchedulerImport.getKeyDecrypt());
						LOGGER.debug("KeyDecrypt : " + fixSchedulerImport.getKeyDecrypt());
					} catch (Exception ex) {
						LOGGER.error("Error Decrypting : " + ex, ex);
						MailUtil.sentNewMessage(adminEmail, "", "[FIX ALERT]Error Decrypting SFTP File",
								"Error Decrypt File <br/>" + ex.toString(), null, session, ScheduleDefinition.emailOnly);
					}
				}

				hashMap.put(MapKey.idScheduler, fixSchedulerImport.getFixSchedulerPK().getIdScheduler());
				LOGGER.debug("ID Scheduler : " + fixSchedulerImport.getFixSchedulerPK().getIdScheduler());
				hashMap.put(MapKey.batchNo, fixLog.getFixLogPK().getInboxId());
				LOGGER.debug("Batch No : " + fixLog.getFixLogPK().getInboxId());
				hashMap.put(MapKey.fileName, file.getName());
				hashMap.put(MapKey.grpId, "");
				hashMap.put(MapKey.itemIdLink, "");
				hashMap.put(MapKey.param1, "");
				hashMap.put(MapKey.param5, file.getPath());
				LOGGER.debug("Param5/Filename : " + file.getPath());
				hashMap.put(MapKey.filePath, file.getParent());
				hashMap.put(MapKey.fileBackup, prependFileBackup);
				hashMap.put(MapKey.flgChecksum, fixSchedulerImport.getFlg()); // flag checksum

				// Checking based on template id
				FixTemplateMasterDao fixTemplateMasterDao = new FixTemplateMasterDao(this.session);
				FixTemplateMasterPK fixTemplateMasterPK = new FixTemplateMasterPK();
				fixTemplateMasterPK.setIdTemplate(fixSchedulerImport.getFixSchedulerPK().getIdTemplate());
				FixTemplateMaster fixTemplateMaster = fixTemplateMasterDao.get(fixTemplateMasterPK);
				
				if (fixTemplateMaster == null)
					throw new FIXException("No Template Master with template name '" + namTemplate + "' " + 
							"or template id " + fixSchedulerImport.getFixSchedulerPK().getIdTemplate());

				// execute class
				hashMap.put(MapKey.javaClass, fixTemplateMaster.getJavaClass());
				LOGGER.debug("Java Class : " + fixTemplateMaster.getJavaClass());
				hashMap.put(MapKey.spvAuth, fixSchedulerImport.getSpvAuth());
				LOGGER.debug("SPV AUTH : " + fixSchedulerImport.getSpvAuth());
				hashMap.put(MapKey.processSource, "SFTP");
				LOGGER.debug("processSource : SFTP");
				hashMap.put(MapKey.typeFix, StatusDefinition.IN);
				LOGGER.debug("Type Fix : " + StatusDefinition.IN);
				hashMap.put(MapKey.importFileExtension, fixSchedulerImport.getFileExtension());
				LOGGER.debug("Import File Ext. : " + fixSchedulerImport.getFileExtension());
				
				LOGGER.debug("=== Trying assign to BackThread ===");
                bdsm.scheduler.BdsmBackThread backThread = this.assignToWorker(hashMap, objectReflection);
				if (backThread != null)
					LOGGER.info(file.getName() + " got BackThread ==> " + backThread.getName());
				return (backThread != null);
				//return (this.assignToWorker(hashMap, objectReflection) != null);
			} else {
				if (fileStatus != 1) {
					fixLog.setFlgProcess(StatusDefinition.ERROR);
					fixLog.setReason((fixSchedulerImport==null)? "Scheduler Import not found": "Scheduler Import is inactive");
					fixLogDao.update(fixLog);
					
					if (fixSchedulerImport == null)
						FileUtil.moveFile(file.getPath(), PropertyPersister.dirFixInErr, "");
					else {
						this.parent.setStatus(file, 1); // inactive file because inactive scheduler import
						
						MailUtil.sentNewMessage(adminEmail, "", "[FIX ALERT] Inactive Scheduler",
								"Error Procesing File " + file.getName() + " <br/>", null, session, ScheduleDefinition.emailOnly);
					}
				}
				
				return true;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			try {
				FileUtil.moveFile(file.getPath(), PropertyPersister.dirFixInErr, "");
			} catch (Exception e) {
				LOGGER.error(ex.getMessage(), ex);
			}
			
			try {
				MailUtil.sentNewMessage(adminEmail, "", "[FIX ALERT] Error Processing File: " + ex.toString(),
						"Error Procesing File '" + file.getName() + "'(" + file.length() + "B)<br/><pre>" + detailStackTrace(ex) + "</pre>", 
						null, session, ScheduleDefinition.emailOnly);
			} catch (Exception ex1) {
				LOGGER.error("Error Send Email to Fix Admin");
				LOGGER.error(ex1.getMessage(), ex1);
			}
		} finally {
			// automatically commit managed object by Hibernate
			transaction.commit();
		}
		
		return true;
	}
	
	private String detailStackTrace(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		
		return sw.getBuffer().toString();
	}
}
