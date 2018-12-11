/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;


import org.apache.commons.io.FilenameUtils;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.TmpKabAcctDAO;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.TmpKabAcct;
import bdsm.scheduler.model.TmpKabAcctPK;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.excel.XLSReader;
import bdsm.util.excel.XLSXReader;


/**
 * 
 * @author v00019237
 */
public class KabQAcctWorker extends BaseProcessor {
	private static String emailDone = "Dear Sir/Madam,<br/>" + "<br/>"
			+ "Process Inquiry KAB Account Data has been Processed. <br/>"
			+ "Please see result Report in Attachment. <br/>" + "<br/>"
			+ "Thanks & regards,<br/>" + "- BDSM -";
	
	
    /**
     * 
     * @param context
     */
    public KabQAcctWorker(java.util.Map<? extends String, ? extends Object> context) {
		super(context);
	}
	

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String configFile = "excelutil_kabacct.properties";
		FixClassConfig fClassConfig = null;
		FixSchedulerXtract fixSchedulerXtract = null;
		TmpKabAcctDAO tmpKabAcctDao = new TmpKabAcctDAO(this.session);
		FixClassConfigDao classConfigDao = new FixClassConfigDao(this.session);
		FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(this.session);
		String extFile;
		String batchNo = context.get(MapKey.batchNo).toString();
		String status = context.get(MapKey.status).toString();
		String sourceProcess = context.get(MapKey.processSource).toString();
		String param1 = context.get(MapKey.param1).toString();
		int idScheduler;
		
		this.getLogger().info("Begin Execute Worker: " + this.getClass().getName());
		
		if (status.equals(StatusDefinition.UNAUTHORIZED)) {
			this.getLogger().info("Status: UNAUTHORIZED");
			String param5 = context.get(MapKey.param5).toString();
			this.getLogger().info("Param 5 : " + param5);
			this.readExcel(param5, configFile, tmpKabAcctDao);
			this.getLogger().info("Import Excel file from Requestor Success");
		}
		else if (status.equals(StatusDefinition.AUTHORIZED)) {
			this.getLogger().info("Scheduler no need SPV Approval");
			this.getLogger().info("Status : AUTHORIZED");
			
			fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
			idScheduler = fClassConfig.getIdScheduler();
			this.getLogger().info("Getting IdScheduler = " + idScheduler + " for Source: "
							+ sourceProcess + " and Status : "
							+ StatusDefinition.AUTHORIZED + "DONE");
			fixSchedulerXtract = fixSchedulerXtractDao.get(idScheduler);
			
			String outFileName = FileUtil.getDateTimeFormatedFileName(
					FilenameUtils.getBaseName(param1.replaceFirst(context.get(MapKey.templateName)+ "\\s+", "")) + "_{HHmmss}.");
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

	private void readExcel(String param5, String configFile, TmpKabAcctDAO tmpKabAcctDao) {
		if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls"))
			readXLS(param5, configFile, tmpKabAcctDao);
		else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx"))
			readXLSX(param5, configFile, tmpKabAcctDao);
	}

	private void readXLS(String param5, String configFile, TmpKabAcctDAO tmpKabAcctDAO) {
		XLSReader xr = XLSReader.getInstance(param5, configFile);
		TmpKabAcct tmpKabAcct;
		TmpKabAcctPK tmpKabAcctPK;
		String[] propertyNames = {"cif"};
		int counter = 0;

		while (xr.hasNextRow()) {
			tmpKabAcct = new TmpKabAcct();
			tmpKabAcctPK = new TmpKabAcctPK();
			tmpKabAcctPK.setBatchNo(context.get(MapKey.batchNo).toString());
			tmpKabAcctPK.setAccount(String.valueOf(++counter));
			tmpKabAcct.setCompositeId(tmpKabAcctPK);
			tmpKabAcct.setCommand("Q"); // Query by CIF No
			
			try {
				if (xr.nextRow(tmpKabAcct, propertyNames))
					tmpKabAcctDAO.insert(tmpKabAcct);
			}
			catch(Exception ex) {
				tmpKabAcct.setFlagStatus(StatusDefinition.REJECTED);
				tmpKabAcct.setStatusReason(ex.toString());
			}
		}
	}

	private void readXLSX(String param5, String configFile, TmpKabAcctDAO tmpKabAcctDAO) {
		XLSXReader xr = XLSXReader.getInstance(param5, configFile);
		TmpKabAcct tmpKabAcct;
		TmpKabAcctPK tmpKabAcctPK;
		String[] propertyNames = {"cif"};
		int counter = 0;

		while (xr.hasNextRow()) {
			tmpKabAcct = new TmpKabAcct();
			tmpKabAcctPK = new TmpKabAcctPK();
			tmpKabAcctPK.setBatchNo(context.get(MapKey.batchNo).toString());
			tmpKabAcctPK.setAccount(String.valueOf(++counter));
			tmpKabAcct.setCompositeId(tmpKabAcctPK);
			tmpKabAcct.setCommand("Q"); // Query by CIF No

			try {
				if (xr.nextRow(tmpKabAcct, propertyNames))
					tmpKabAcctDAO.insert(tmpKabAcct);
			}
			catch(Exception ex) {
				tmpKabAcct.setFlagStatus(StatusDefinition.REJECTED);
				tmpKabAcct.setStatusReason(ex.toString());
			}
		}
	}
}
