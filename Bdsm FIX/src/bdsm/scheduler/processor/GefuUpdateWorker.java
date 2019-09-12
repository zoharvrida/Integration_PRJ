/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixTemplateMasterDao;
import bdsm.scheduler.dao.GefuUpdateWorkerDAO;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.model.CustomResult1;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.TmpGefuRespons;
import bdsm.util.SchedulerUtil;


/**
 * 
 * @author NCBS
 */
public class GefuUpdateWorker extends BaseProcessor {
	private static final String emailDone = "Dear Sir/Madam,<br/>" + "<br/>"
			+ "Process {1} has been Processed. <br/>"
			+ "Please see result Report in Attachment. <br/>" + "<br/>"
			+ "Thanks & regards,<br/>" + "- BDSM -";

	
    /**
     * 
     * @param context
     */
    public GefuUpdateWorker(java.util.Map<? extends String, ? extends Object> context) {
		super(context);
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		this.getLogger().info("Begin update GEFU for batch : " + context.get(MapKey.param6).toString());
		try {
			this.getLogger().info("Initialize Dao");
			GefuUpdateWorkerDAO dao = new GefuUpdateWorkerDAO(session);
			FixQXtractDao fixQDao = new FixQXtractDao(session);
			FixTemplateMasterDao templateDao = new FixTemplateMasterDao(session);
			FixInboxDao fixInboxDao = new FixInboxDao(session);
			FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
			TmpGefuResponsDao tmpGefuDAO = new TmpGefuResponsDao(session);
			CustomResult1 custom = null;
			this.getLogger().info("Initialize Param");
			String inboxId = null;
			Integer idScheduler = null;
			
			try {
				inboxId = context.get(MapKey.param3).toString();
			}
			catch (Exception e) {
				this.getLogger().info("NOT USING INBOX ID :" + e);
				inboxId = null;
			}
			
			String packageName = context.get(MapKey.param4).toString();
			String functionName = context.get(MapKey.param2).toString();
			String batchNo = context.get(MapKey.param6).toString();
			String moduleDescription = context.get(MapKey.param5).toString();
			// impacted from bdsm.scheduler.processor.XtractPocessor, when set
			// param5 to param5 added with filePath
			// in fact here, param5 should just only contain module description,
			// so we extract module description from param5
			if (Math.max(moduleDescription.lastIndexOf('\\'), moduleDescription.lastIndexOf('/')) > -1) {
				int pos = moduleDescription.replace('\\', '/').lastIndexOf('/');
				moduleDescription = moduleDescription.substring(pos + 1);
			}
			this.getLogger().info("Do update Trx Status");
                        this.getLogger().info("PACKAGE NAME :" + packageName);
                        this.getLogger().info("Function NAME :" + functionName);
                        this.getLogger().info("Batch :" + batchNo);
			dao.runDBPackage(packageName, functionName, batchNo);
			
			
			if (context.get(MapKey.param1) != null) 
				idScheduler = Integer.parseInt(context.get(MapKey.param1).toString());
			
			if (inboxId != null) {
				this.getLogger().info("Register fixQXtract for Final Report");
				// register fixqxtract for reporting
				FixInbox fixInbox = null;
				this.getLogger().info("Begin Register FixQXtract");
				fixInbox = fixInboxDao.get(inboxId);
				custom = templateDao.getAccessTemplate(fixInbox.getSender(), 
						fixInbox.getFixInboxPK().getSubject().substring(0, 6), "I");
				fixQXtract = new FixQXtract();
				fixQXtract.setIdScheduler(idScheduler);
				fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
				fixQXtract.setDtmRequest(SchedulerUtil.getTime());
				fixQXtract.setParam1("RE: " + fixInbox.getFixInboxPK().getSubject());
				fixQXtract.setParam2(fixInbox.getSender());
				fixQXtract.setParam3(fixEmailAccessDao.getSpvFromSender(fixInbox.getSender(), custom.getIdScheduler()));
				fixQXtract.setParam4(emailDone.replace("{1}", moduleDescription));
				fixQXtract.setParam5(fixInbox.getEmailAttachment() + ".xls");
				fixQXtract.setParam6(batchNo);
				fixQDao.insert(fixQXtract);
				
				this.getLogger().info("End Register FixQXtract");
			}
			else if ((idScheduler != null) && (idScheduler.intValue() != 0)) {
				fixQXtract = new FixQXtract();
				fixQXtract.setIdScheduler(idScheduler);
				fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
				fixQXtract.setDtmRequest(SchedulerUtil.getTime());
				fixQXtract.setParam6(batchNo);
				
				fixQDao.insert(fixQXtract);
			}
			
			TmpGefuRespons tmpGEFURespons = tmpGefuDAO.getByBatchNo(batchNo);
			if (tmpGEFURespons != null)
				tmpGEFURespons.setDateTimeFinish(SchedulerUtil.getTime());
			
			this.getLogger().info("END update Trx Status");
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
		}
		
		this.getLogger().info("End update GEFU for batch : " + context.get(MapKey.param6).toString());
		return true;
	}
}
