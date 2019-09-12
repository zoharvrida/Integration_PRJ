/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;


import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;


/**
 * 
 * @author v00019237
 */
public class KabQParamWorker extends BaseProcessor {
	private static String emailDone = "Dear Sir/Madam,<br/>" + "<br/>"
			+ "Process Query KAB Tier Parameter Data has been Processed. <br/>"
			+ "Please see result Report in Attachment. <br/>" + "<br/>"
			+ "Thanks & regards,<br/>" + "- BDSM -";

	
    /**
     * 
     * @param context
     */
    public KabQParamWorker(java.util.Map<? extends String, ? extends Object> context) {
		super(context);
	}
	

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String status = context.get(MapKey.status).toString();
		String sourceProcess = context.get(MapKey.processSource).toString();
		String param1 = context.get(MapKey.param1).toString();
		int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
		FixClassConfigDao classConfigDao = new FixClassConfigDao(this.session);
		FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(this.session);
		String batchNo = context.get(MapKey.batchNo).toString();
		String outFileName;
		String extFile;
		FixClassConfig fClassConfig;
		FixSchedulerXtract fixSchedulerXtract;
		
		if (status.equals(StatusDefinition.UNAUTHORIZED)) {
			this.getLogger().info("Skip process for UNAUTHORIZED status in Scheduler no need SPV Approval");
		}
		else if (status.equals(StatusDefinition.AUTHORIZED)) {
			this.getLogger().info("Start Inquiry KAB Tier Param Data");
			fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
			idScheduler = fClassConfig.getIdScheduler();
			this.getLogger().info("Get ID Scheduler Xtract for Inquiry Param Data Done : " + idScheduler);
			fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
			
			outFileName = FileUtil.getDateTimeFormatedFileName(
					org.apache.commons.io.FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replaceFirst(
							context.get(MapKey.templateName)+ "\\s+", "")) + "_{HHmmss}.");
			extFile = fixSchedulerXtract.getFileFormat();
			outFileName += extFile;
			this.getLogger().info("Out File Name : " + outFileName);
			
			fixQXtract = new FixQXtract();
			fixQXtract.setIdScheduler(idScheduler);
			fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
			fixQXtract.setDtmRequest(SchedulerUtil.getTime());
			fixQXtract.setParam1("RE: " + param1);
			fixQXtract.setParam2(context.get(MapKey.emailSender).toString());
			fixQXtract.setParam4(emailDone);
			fixQXtract.setParam5(outFileName);
			fixQXtract.setParam6(batchNo);
			this.getLogger().info("Register FixQXtract Done");
		}
		return true;
	}
}
