/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.exception.SkipProcessException;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.HibernateUtil;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Transaction;

/**
 *
 * @author bdsm
 */
public class FileObjectReflection extends BaseReflection {

	@Override
	public void execute(Map context) {
		this.session = HibernateUtil.getSession();
		String inboxId = context.get(MapKey.batchNo).toString();
		Integer idScheduler = (Integer) context.get(MapKey.idScheduler);
		updateFixLog(inboxId, StatusDefinition.PROCESS, idScheduler);
		Class[] paramString = new Class[1];
		paramString[0] = Map.class;
		Class cls;

		try {
			String javaClass = context.get(MapKey.javaClass).toString();
			String filepath = context.get(MapKey.filePath).toString();
			String filename = context.get(MapKey.fileName).toString();
			//move file to process folder
			File file = new File(filepath, filename);
			File fileDest = new File(PropertyPersister.dirFixInProc, filename);

			if (file.renameTo(fileDest) == false) FileUtils.moveFile(file, fileDest);
			
			context.put(MapKey.param5, fileDest.getPath());

			//decrypt
			if (context.get(MapKey.flgEncrypt).toString().equals("Y")) {
				getLogger().info("Decrypting File " + context.get(MapKey.param5).toString());
				String modDecrypt = context.get(MapKey.modDecrypt).toString();
				String keyDecrypt = context.get(MapKey.keyDecrypt).toString();
				String newFile = FileUtil.decrypt(context.get(MapKey.param5).toString(), modDecrypt, keyDecrypt, context.get(MapKey.fileBackup).toString());
				context.put(MapKey.param5, (new File(PropertyPersister.dirFixInProc + newFile)).getPath());
				getLogger().info("Decrypting File " + context.get(MapKey.param5).toString() + " DONE");
			}

			getLogger().info("fileName = " + context.get(MapKey.fileName));

			// File extension validation
			String importFileExtension = (String) context.get(MapKey.importFileExtension);
			if (StringUtils.isNotBlank(importFileExtension)) {
				importFileExtension = (";" + importFileExtension + ";").toLowerCase();
				String userFileExtension = (";" + FilenameUtils.getExtension(context.get(MapKey.param5).toString()) + ";").toLowerCase();

				if (importFileExtension.indexOf(userFileExtension) == -1)
					throw new FIXException("Invalid File Extension!!!");
			}

			cls = Class.forName(javaClass);
			Constructor cons = cls.getConstructor(paramString);

			//1st execute
			getLogger().info("Execute class " + javaClass + " 1st");
			context.put(MapKey.session, this.session);
			context.put(MapKey.status, StatusDefinition.UNAUTHORIZED);
			BaseProcessor baseProcessor = (BaseProcessor) cons.newInstance(context);
			boolean auth = context.get(MapKey.spvAuth).toString().equals("N");
			
			try {
				boolean success = baseProcessor.execute();
				updateFixLog(inboxId, StatusDefinition.UNAUTHORIZED, idScheduler);
				context.put(MapKey.spvAuth, auth);

				getLogger().info("Finish class " + javaClass + " 1st execution. Result = " + success);

				//2nd execute
				if (success && auth) {
					getLogger().info("Execute class " + javaClass + " 2nd");
					context.put(MapKey.status, StatusDefinition.AUTHORIZED);
					context.remove(MapKey.param5);
					success = baseProcessor.execute();
					getLogger().info("Finish class" + javaClass + " 2nd execution. Result = " + success);
					updateFixLog(inboxId, StatusDefinition.DONE, idScheduler);
					if (success) {

						FixQXtract fixQXtract;
						if ((fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract)) != null) {
							getLogger().info("Register FixQXtract");
							FixQXtractDao fixQXtractDao = new FixQXtractDao(this.session);
							Transaction tx = this.session.beginTransaction();
							fixQXtractDao.insert(fixQXtract);
							tx.commit();
							getLogger().info("Register FixQXtract DONE");
						}
					}
				}
			} catch (SkipProcessException skip) {
				getLogger().info("SKIP PROCESS : " + context.get(MapKey.param5));
				try {
					updateFixLog(inboxId, StatusDefinition.ERROR, idScheduler, skip);
					Thread.currentThread().sleep((5 * 60) * 1000);
					if ("Y".equals(context.get(MapKey.flgEncrypt).toString())) {
						String newFilename = FileUtil.encrypt(context.get(MapKey.param5).toString(), context.get(MapKey.modDecrypt).toString(), context.get(MapKey.keyDecrypt).toString(), false);
						context.put(MapKey.param5, (new File(PropertyPersister.dirFixInProc, newFilename)).getPath());
					}
					FileUtils.moveFile(new File(context.get(MapKey.param5).toString()), new File(filepath, filename));
					deleteFixLog(inboxId);
				} catch (Exception x) {
					updateFixLog(inboxId, StatusDefinition.ERROR, idScheduler, x);
				}
			}
		} catch (Exception ex) {
			getLogger().error(ex, ex);
			updateFixLog(inboxId, StatusDefinition.ERROR, idScheduler, ex);

			try {
				if (context.get(MapKey.param5) != null)
					FileUtil.moveFile((String) context.get(MapKey.param5), PropertyPersister.dirFixInErr, "");
			} catch (Exception e) {
				getLogger().error(e, e);
			}
		} finally {
			HibernateUtil.closeSession(this.session);
		}
	}

	private void updateFixLog(String inboxId, String sd, Integer idScheduler) {
		updateFixLog(inboxId, sd, idScheduler, null);
	}

	private void updateFixLog(String inboxId, String sd, Integer idScheduler, Exception ex) {
		FixLogDao fixLogDao = new FixLogDao(this.session);
		FixLog fixLog = fixLogDao.get(inboxId);
		fixLog.setFlgProcess(sd);
		fixLog.setIdScheduler(idScheduler);
		if (sd.equals(StatusDefinition.PROCESS)) {
			fixLog.setDtmStartProcess(SchedulerUtil.getTime());
		} else if (sd.equals(StatusDefinition.DONE) || sd.equals(StatusDefinition.ERROR)
				|| sd.equals(StatusDefinition.UNAUTHORIZED)) {
			fixLog.setDtmEndProcess(SchedulerUtil.getTime());
		}
		if (ex != null)
			fixLog.setReason(ex.getMessage());
		else
			fixLog.setReason(null);

		Transaction tx = this.session.beginTransaction();
		fixLogDao.update(fixLog);
		tx.commit();
	}

	private void deleteFixLog(String inboxId) {
		FixLogDao fixLogDao = new FixLogDao(this.session);
		FixLog fixLog = fixLogDao.get(inboxId);
		//fixLog.setIdScheduler(idScheduler);

		Transaction tx = this.session.beginTransaction();
		fixLogDao.delete(fixLog);
		tx.commit();
	}
}
