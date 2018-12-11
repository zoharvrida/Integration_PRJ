/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;


import java.util.List;
import java.util.Map;

import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.ExtFileUploadMastDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.model.ExtFileUploadMast;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.TmpGefuRespons;
import bdsm.util.SchedulerUtil;

/**
 * 
 * @author NCBS
 */
public class GefuResponsWorker extends BaseProcessor {

    /**
     * 
     * @param context
     */
    public GefuResponsWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}
	


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		this.getLogger().info("[BEGIN] Read GEFU Respons");
		TmpGefuResponsDao tmpGefuResponsDAO = new TmpGefuResponsDao(session);
		ExtFileUploadMastDao extFileUploadMastDAO = new ExtFileUploadMastDao(session);
		FixQXtractDao fixQDao = new FixQXtractDao(session);
		this.getLogger().debug("Finish initialize Dao");
		
		List<TmpGefuRespons> listGefuRespons = tmpGefuResponsDAO.list();
		this.getLogger().debug("LIST :" + listGefuRespons);
		this.getLogger().debug("Finish listing all batch to Monitor");
		
		ExtFileUploadMast extFileUploadModel = null;
		TmpGefuRespons gefuResponsModel = null;
		String batchNo = "";
		String packageName = "";
		String functionName = "";
		String inboxId = "";
		String moduleDesc = "";
		Integer idScheduler = 0;
		
		for (int i=0; i<listGefuRespons.size(); i++) {
			gefuResponsModel = listGefuRespons.get(i);
			
			try {
				extFileUploadModel = extFileUploadMastDAO.get(gefuResponsModel.getCompositeId().getBatchNo());
				this.getLogger().info("CHECK : " + extFileUploadModel.getCodFileStatus());
				
				if ("5|6".indexOf((extFileUploadModel.getCodFileStatus()!=null)? extFileUploadModel.getCodFileStatus(): " ") > -1) {
					this.getLogger().info("Process finished for Batch : " + gefuResponsModel.getCompositeId().getBatchNo());
					gefuResponsModel.setStatus(extFileUploadModel.getCodFileStatus());
					tmpGefuResponsDAO.update(gefuResponsModel);
					packageName = gefuResponsModel.getCompositeId().getOcpackage();
					functionName = gefuResponsModel.getCompositeId().getOcfunction();
					batchNo = gefuResponsModel.getCompositeId().getBatchNo();
					inboxId = gefuResponsModel.getInboxid();
					idScheduler = gefuResponsModel.getIdScheduler();
					moduleDesc = gefuResponsModel.getModuleDesc();

					this.getLogger().info("Register to FixQXtract for Batch : "+ batchNo);
					fixQXtract = new FixQXtract();
					this.getLogger().info("IDScheduler Update : " + gefuResponsModel.getIdScheduler());
					fixQXtract.setIdScheduler(ScheduleDefinition.gefuUpdate);
					fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
					fixQXtract.setDtmRequest(SchedulerUtil.getTime());
					this.getLogger().info("Param1 : " + idScheduler);
					fixQXtract.setParam1((idScheduler!=null)? String.valueOf(idScheduler): null);
					this.getLogger().info("Param3 : " + inboxId);
					fixQXtract.setParam3(inboxId);
					this.getLogger().info("Param4 : " + packageName);
					fixQXtract.setParam4(packageName);
					this.getLogger().info("Param2 : " + functionName);
					fixQXtract.setParam2(functionName);
					this.getLogger().info("Param6 : " + batchNo);
					fixQXtract.setParam6(batchNo);
					this.getLogger().info("Param5 : " + moduleDesc);
					fixQXtract.setParam5(moduleDesc);
					this.getLogger().info("Do Insert to FixQXtract");
					fixQDao.insert(fixQXtract);
				}
			}
			catch (Exception ex) {
				this.getLogger().error("ERROR Batch No: " + gefuResponsModel.getCompositeId().getBatchNo() , ex);
			}
		}
		
		this.getLogger().info("[END] Read GEFU Respons");
		return true;
	}
}
