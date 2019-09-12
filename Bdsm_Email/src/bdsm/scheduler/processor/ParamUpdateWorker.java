/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.TmpParameterUploadDAO;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixInbox;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.TmpParameterUpload;
import bdsm.scheduler.util.FileUtil;
import bdsm.service.ParameterUploadService;
import bdsm.util.SchedulerUtil;
import bdsm.util.excel.XLSReader;
import bdsm.util.excel.XLSXReader;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.ParameterDao;


/**
 * 
 * @author NCBS
 */
public class ParamUpdateWorker extends BaseProcessor {
	private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	private static String emailApproval = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your approval is required for the attached file to be processed further. <br/>"
			+ "<br/>" + "Please reply this email with:<br/>"
			+ "<b>Ok</b>, if you approve the file to be processed, or<br/>"
			+ "<b>Not ok</b>, if otherwise.<br/>" + "<br/>"
			+ "Thanks & regards,<br/>" + "- BDSM -";
	private static String emailDone = "Dear Sir/Madam,<br/>" + "<br/>"
			+ "Process Update PARAMETER has been Processed. <br/>"
			+ "Please see result Report in Attachment. <br/>" + "<br/>"
			+ "Thanks & regards,<br/>" + "- BDSM -";
	private static String emailRejected = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your requested process to update PARAMETER has been Rejected by Supervisor. <br/>"
			+ "<br/>" + "Thanks & regards,<br/>" + "- BDSM -";

	
	public ParamUpdateWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}
	

	@Override
	protected boolean doExecute() throws Exception {
		ParameterUploadService paramUpldSrvc = new ParameterUploadService();
		FcrBaBankMastDao fcrBABankMastDAO = new FcrBaBankMastDao(this.session);
		TmpParameterUploadDAO tmpParameterUploadDAO = new TmpParameterUploadDAO(this.session);
		ParameterDao parameterDAO = new ParameterDao(this.session);
		FixClassConfigDao classConfigDAO = new FixClassConfigDao(this.session);
		FixSchedulerXtractDao fixSchedulerXtractDAO = new FixSchedulerXtractDao(this.session);
		String configFile = "excelutil_parameter.properties";
		FixClassConfig fClassConfig = null;
		FixSchedulerXtract fixSchedulerXtract = null;
		String extFile = "";
		String batchNo = context.get(MapKey.batchNo).toString();
		String status = context.get(MapKey.status).toString();
		String param1 = context.get(MapKey.param1).toString();
		String param5 = (String) context.get(MapKey.param5);
		String filePattern = (String) context.get(MapKey.filePattern);
		String sourceProcess = context.get(MapKey.processSource).toString();
		int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
		int idSchedulerXtract;
		String outFileName;
		
		
		this.getLogger().info("Done Prepare before execute status U/A");
		
		paramUpldSrvc.setTmpParameterUploadDAO(tmpParameterUploadDAO);
		paramUpldSrvc.setParameterDAO(parameterDAO);

		if (status.equals(StatusDefinition.UNAUTHORIZED)) {
			String filename = (String) context.get(MapKey.fileName);
			this.getLogger().info("Status : UNAUTHORIZED");
			this.getLogger().info("Param 5 : " + param5);
			this.getLogger().info("Filename : " + filename);

			/* Pattern filename */
			try {
				String xtractFilename;
				String dateInFilename;
				java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(filePattern);
				java.util.regex.Matcher matcher = pattern.matcher(filename);
				if (matcher.find()) {
					this.getLogger().info("pattern match start at: " + matcher.start());
					this.getLogger().info("pattern match end at: " + matcher.end());

					xtractFilename = filename.substring(matcher.start(), matcher.end());
					dateInFilename = xtractFilename.substring(xtractFilename.length() - 8);

					dateFormatter.parse(dateInFilename);
					if (!dateFormatter.format(fcrBABankMastDAO.get().getDatProcess()).equals(dateInFilename))
						throw new FIXException("date in filename must be same with business date");
				}
			}
			catch (java.text.ParseException pe) {
				throw new FIXException("Invalid date in filename");
			}

			readExcel(param5, configFile, tmpParameterUploadDAO);
			
			this.getLogger().info("Run validate PARAMETER UPLOAD");
			paramUpldSrvc.runValidate(batchNo);
			
			fClassConfig = classConfigDAO.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
			idSchedulerXtract = fClassConfig.getIdScheduler();
			this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: "
							+ sourceProcess + " and Status : "
							+ StatusDefinition.UNAUTHORIZED + "DONE");
			fixSchedulerXtract = fixSchedulerXtractDAO.get(idSchedulerXtract);
			
			outFileName = FileUtil.getDateTimeFormatedFileName(
					FilenameUtils.getBaseName(param1.replaceFirst(context.get(MapKey.templateName) + "\\s+", "")) + "_{HHmmss}.");
			extFile = fixSchedulerXtract.getFileFormat();
			outFileName += extFile;
			this.getLogger().info("Out File Name : " + outFileName);
			
			if (!sourceProcess.equalsIgnoreCase("sftp")) {
				fixQXtract = new FixQXtract();
				fixQXtract.setIdScheduler(idSchedulerXtract);
				fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
				fixQXtract.setDtmRequest(SchedulerUtil.getTime());
				fixQXtract.setParam1("RE: " + param1);
				FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
				fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
				fixQXtract.setParam4(emailApproval);
				fixQXtract.setParam5(outFileName);
				fixQXtract.setParam6(batchNo);
				this.getLogger().info("PARAMETER UPLOAD Register FixQXtract Done");
			}
		}
		else if (status.equals(StatusDefinition.AUTHORIZED)) {
			this.getLogger().info("Status : AUTHORIZED");
			paramUpldSrvc.runProcess(batchNo);
			PropertyPersister.refreshParameter(this.session); // Reload data parameter
			this.getLogger().info("Run update to FCR Done");
			
			this.getLogger().info("PARAMETER UPDATE Register FixQXtract");
			fClassConfig = classConfigDAO.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
			idSchedulerXtract = fClassConfig.getIdScheduler();
			this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: " + sourceProcess 
					+ " and Status : " 
					+ StatusDefinition.AUTHORIZED + "DONE");
			fixSchedulerXtract = fixSchedulerXtractDAO.get(idSchedulerXtract);
			outFileName = FileUtil.getDateTimeFormatedFileName(
					FilenameUtils.getBaseName(param1.replaceFirst(
							"[R|r][E|e]:\\s+" + context.get(MapKey.templateName) + "\\s+", "")) + "_{HHmmss}.");
			extFile = fixSchedulerXtract.getFileFormat();
			outFileName += extFile;
			this.getLogger().info("Out File Name : " + outFileName);
			
			fixQXtract = new FixQXtract();
			fixQXtract.setIdScheduler(idSchedulerXtract);
			fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
			fixQXtract.setDtmRequest(SchedulerUtil.getTime());
			fixQXtract.setParam1(param1);
			
			FixInboxDao fixInboxDao = new FixInboxDao(session);
			if (!context.get(MapKey.itemIdLink).toString().equals(""))
				fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
			if (context.get(MapKey.spvAuth).toString().equals("N"))
				fixQXtract.setParam2(context.get(MapKey.param2).toString());
			
			FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
			fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
			fixQXtract.setParam4(emailDone);
			fixQXtract.setParam5(outFileName);
			fixQXtract.setParam6(batchNo);
			
			this.getLogger().info("PARAMETER UPLOAD Register FixQXtract Done");
		}
		else if (status.equals(StatusDefinition.REJECTED)) {
			this.getLogger().info("Status : REJECTED");
			this.getLogger().info("PARAMETER UPLAOD Register FixQXtract");
			paramUpldSrvc.runReject(batchNo);
			fixQXtract = new FixQXtract();
			fClassConfig = classConfigDAO.get(getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
			idSchedulerXtract = fClassConfig.getIdScheduler();
			fixQXtract.setIdScheduler(idSchedulerXtract);
			fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
			fixQXtract.setDtmRequest(SchedulerUtil.getTime());
			fixQXtract.setParam1(param1);
			FixInboxDao fixInboxDao = new FixInboxDao(session);
			if (!context.get(MapKey.itemIdLink).toString().equals(""))
				fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
			fixQXtract.setParam4(emailRejected);
			fixQXtract.setParam5("");
			
			this.getLogger().info("PARAMETER UPLAOD Register FixQXtract");
		} else {
			this.getLogger().info("Status : IGNORED");
			FixInboxDao fixInboxDao = new FixInboxDao(session);
			FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
			fixInbox.setFlgProcess(StatusDefinition.IGNORED);
			fixInboxDao.update(fixInbox);
		}
		
		return true;
	}

	private void readExcel(String param5, String configFile, TmpParameterUploadDAO tmpParameterUploadDao) {
		if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls"))
			readXLS(param5, configFile, tmpParameterUploadDao);
		else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx"))
			readXLSX(param5, configFile, tmpParameterUploadDao);
	}

	private void readXLS(String param5, String configFile, TmpParameterUploadDAO tmpParameterUploadDao) {
		XLSReader xr = XLSReader.getInstance(param5, configFile);
		TmpParameterUpload tmpParameterUpload;
		String[] propertyNames = {"codeParam", "value", "command"};
		
		while (xr.hasNextRow()) {
			tmpParameterUpload = new TmpParameterUpload();
			tmpParameterUpload.setBatchNo(context.get(MapKey.batchNo).toString());
			
			try {
				if (xr.nextRow(tmpParameterUpload, propertyNames))
					tmpParameterUploadDao.insert(tmpParameterUpload);
			}
			catch(Exception ex) {
				this.getLogger().error(ex, ex);
				tmpParameterUpload.setFlagStatus(StatusDefinition.REJECTED);
				tmpParameterUpload.setStatusReason(ex.toString());
			}
		}
		
	}

	private void readXLSX(String param5, String configFile, TmpParameterUploadDAO tmpParameterUploadDao) {
		XLSXReader xr = XLSXReader.getInstance(param5, configFile);
		TmpParameterUpload tmpParameterUpload;
		String[] propertyNames = {"codeParam", "value", "command"};
		
		while (xr.hasNextRow()) {
			tmpParameterUpload = new TmpParameterUpload();
			tmpParameterUpload.setBatchNo(context.get(MapKey.batchNo).toString());
			
			try {
				if (xr.nextRow(tmpParameterUpload, propertyNames))
					tmpParameterUploadDao.insert(tmpParameterUpload);
			}
			catch(Exception ex) {
				this.getLogger().error(ex, ex);
				tmpParameterUpload.setFlagStatus(StatusDefinition.REJECTED);
				tmpParameterUpload.setStatusReason(ex.toString());
			}
		}
	}
}
