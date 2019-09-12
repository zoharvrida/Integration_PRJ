package bdsm.scheduler.processor;


import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixQXtract;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;


public class MailObjectReflection extends BaseReflection {
	private org.hibernate.Transaction tx;

	@Override
	public void execute(java.util.Map context) {
		this.session = HibernateUtil.getSession();
		String status = context.get(MapKey.status).toString();
		String inboxId = context.get(MapKey.inboxId).toString();
		boolean y = status.equals(StatusDefinition.AUTHORIZED) || status.equals(StatusDefinition.REJECTED);
		this.updateInboxStatus(inboxId, StatusDefinition.PROCESS, y);

		Class[] paramTypes = new Class[1];
		paramTypes[0] = java.util.Map.class;
		FixQXtract fixQXtract;
		Class cls;

		String javaClass = context.get(MapKey.javaClass).toString();
		try {
			this.getLogger().info("Execute class : " + javaClass);
			this.getLogger().debug("status = " + status);
			if (status.equals(StatusDefinition.UNAUTHORIZED)) {
				getLogger().debug("fileName = " + context.get(MapKey.param5));
			}

			cls = Class.forName(javaClass);
			java.lang.reflect.Constructor cons = cls.getConstructor(paramTypes);
			context.put(MapKey.session, this.session);
			
			// 1st execute
			this.getLogger().info("Prepare 1st Execute");
			BaseProcessor baseProcessor = (BaseProcessor) cons.newInstance(context);
			boolean success = baseProcessor.execute();
			boolean auth = context.get(MapKey.spvAuth).toString().equals("Y");
			
			if (auth && !status.equalsIgnoreCase(StatusDefinition.IGNORED))
				this.updateInboxStatus(inboxId, StatusDefinition.DONE, y);
			else
				this.updateInboxStatus(inboxId, StatusDefinition.IGNORED, false);
			
			this.getLogger().info("Finish class" + javaClass + " execution. Result = " + success);

			// 2nd execute
			if (!auth && context.get(MapKey.cdAccess).toString().equals("100") && success) {
				getLogger().info("Prepare 2nd Execute");

				if (context.containsKey(MapKey.param5))
					context.remove(MapKey.param5);
				
				context.put(MapKey.status, StatusDefinition.AUTHORIZED);
				y = true;
				this.getLogger().info("Execute class " + javaClass + " with no need approval");
				this.getLogger().debug("status = " + context.get(MapKey.status).toString());
				this.getLogger().debug("fileName = " + context.get(MapKey.param5));
				
				success = baseProcessor.execute();
				this.updateInboxStatus(inboxId, StatusDefinition.DONE, y);
				this.getLogger().info("Finish class " + javaClass + " execution with no need approval. Result = " + success);
			}

		}
		catch (Exception ex) {
			this.getLogger().error("Execute Clas Worker : " + javaClass + " Error");
			this.getLogger().error(ex, ex);
			this.updateInboxStatus(inboxId, StatusDefinition.ERROR, y, ex);
		}
		finally {
			fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract);
			this.insertToFixQXtract(fixQXtract);
			HibernateUtil.closeSession(this.session);
		}
	}

	private void insertToFixQXtract(FixQXtract fixQXtract) {
		FixQXtractDao fixQXtractDao = new FixQXtractDao(this.session);
		this.tx = session.beginTransaction();
		fixQXtractDao.insert(fixQXtract);
		this.tx.commit();
	}
	
	private void updateInboxStatus(String inboxId, String sd, boolean y) {
		this.updateInboxStatus(inboxId, sd, y, null);
	}

	private void updateInboxStatus(String inboxId, String sd, boolean y, Exception ex) {
		this.getLogger().debug("Begin updateInboxStatus --> inboxId : " + inboxId);
		this.tx = this.session.beginTransaction();
		
		// Update FixInbox
		FixInboxDao fixInboxDao = new FixInboxDao(this.session);
		FixInbox fixInbox = fixInboxDao.get(inboxId);
		fixInbox.setFlgProcess(sd);
		if ((StatusDefinition.ERROR.equals(sd)) && (ex != null))
			fixInbox.setReason(ex.getMessage());
		
		// Update FixLog
		if (y || sd.equals(StatusDefinition.ERROR)) {
			this.getLogger().debug("Update Fixlog");
			FixLogDao fixLogDao = new FixLogDao(this.session);
			FixLog fixLog;
			if (org.apache.commons.lang3.StringUtils.isNotBlank(fixInbox.getItemIdLink())) {
				fixLog = fixLogDao.get(fixInbox.getItemIdLink());
				this.getLogger().debug("Fixlog from ItemID Link");
			}
			else {
				fixLog = fixLogDao.get(inboxId);
				this.getLogger().debug("Fixlog From inboxID");
			}
			
			this.getLogger().debug("FixLog value : " + fixLog);
			this.getLogger().debug("FixLog Status : " + sd);
			
			fixLog.setFlgProcess(sd);
			if ((StatusDefinition.ERROR.equals(sd)) && (ex != null))
				fixLog.setReason(ex.getMessage());
			
			if (sd.equals(StatusDefinition.PROCESS))
				fixLog.setDtmStartProcess(SchedulerUtil.getTime());
			else if (sd.equals(StatusDefinition.DONE) || sd.equals(StatusDefinition.ERROR))
				fixLog.setDtmEndProcess(SchedulerUtil.getTime());
			
			this.getLogger().debug("Update Fixlog");
		}
		
		this.tx.commit(); // synchronize updated object(fixInbox and fixLog) with DB
		this.getLogger().debug("END updateInboxStatus");
	}
}
