/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.*;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.excel.XLSReader;
import bdsm.util.excel.XLSXReader;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

/**
 *
 * @author v00020800
 */
public class RoutingBranchWorker extends BaseProcessor {

	private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

	private String emailApproval = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your approval is required for the attached file to be processed further. <br/>"
			+ "<br/>"
			+ "Please reply this email with:<br/>"
			+ "<b>Ok</b>, if you approve the file to be processed, or<br/>"
			+ "<b>Not ok</b>, if otherwise.<br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";
	private String emailDone = "Dear Sir/Madam,<br/>"
			+ "<br/>"
            + "Process Insert or Delete STM54 Data has been Processed. <br/>"
			+ "Please see result Report in Attachment. <br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";
	private String emailRejected = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your requested process to Reject STM54 Data has been Rejected by Supervisor. <br/>"
			+ "<br/>"
			+ "Thanks & regards,<br/>"
			+ "- BDSM -";

    /**
     * 
     * @param context
     */
    public RoutingBranchWorker(Map context) {
		super(context);
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    protected boolean doExecute() throws Exception {
		String configFile;
		configFile = "excelutil_STM54.properties";
		FixClassConfig fClassConfig = null;
		FixSchedulerXtract fixSchedulerXtract = null;
		FcrBaBankMastDao fcrBABankMastDAO = new FcrBaBankMastDao(session);
		StRoutingBranchDao stBranchDao = new StRoutingBranchDao(session);
		FixClassConfigDao classConfigDao = new FixClassConfigDao(session);
		FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
		String extFile;
		String filePattern = (String) context.get(MapKey.filePattern);
		String batchNo = context.get(MapKey.batchNo).toString();
		String status = context.get(MapKey.status).toString();
		String sourceProcess = context.get(MapKey.processSource).toString();
		String param1 = context.get(MapKey.param1).toString();
		String param5 = (String) context.get(MapKey.param5);
		int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
		int idSchedulerXtract;
		String outFileName;

		this.getLogger().info("Begin Execute Worker : " + this.getClass().getName());

		idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
		getLogger().info("Done Prepare before execute status U/A");
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
					dateInFilename = filename.substring(6, 14);
					this.getLogger().info("dateInFilename: " + dateInFilename);

					if (!dateFormatter.format(fcrBABankMastDAO.get().getDatProcess()).equals(dateInFilename)) {
						throw new FIXException("date in filename must be same with business date");
					}
				}
            } catch (Exception pe) {
				throw new FIXException("Invalid date in filename");
			}

			this.readExcel(param5, configFile, stBranchDao);
			this.session.flush();
			this.getLogger().info("Import Excel file from Requestor Success");
            stBranchDao.runUpload(batchNo);    
			this.getLogger().info("Filter and Validate Source Data Done");
           
			fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0);
			idSchedulerXtract = fClassConfig.getIdScheduler();
			this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: "
					+ sourceProcess + " and Status : "
					+ StatusDefinition.UNAUTHORIZED + "DONE");
			fixSchedulerXtract = fixSchedulerXtractDao.get(idSchedulerXtract);
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
				this.getLogger().info("Register FixQXtract Done");
            }
			} else if (status.equals(StatusDefinition.AUTHORIZED)) {
				this.getLogger().info("Status : AUTHORIZED");
				this.getLogger().debug("runProcess --> Batch No : " + batchNo);
				this.getLogger().debug("runProcess --> SPV : " + context.get(MapKey.emailSender).toString());

				stBranchDao.runUpdate(batchNo);
            this.getLogger().info("IdScheduler ");
				this.getLogger().info("Register FixQXtract");
				fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.AUTHORIZED).get(0);
				idSchedulerXtract = fClassConfig.getIdScheduler();
				this.getLogger().info("Getting IdScheduler = " + idSchedulerXtract + " for Source: "
						+ sourceProcess + " and Status : "
						+ StatusDefinition.AUTHORIZED + "DONE");
				fixSchedulerXtract = fixSchedulerXtractDao.get(idSchedulerXtract);

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
				if (!context.get(MapKey.itemIdLink).toString().equals("")) {
					fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
				}
				if (context.get(MapKey.spvAuth).toString().equals("N")) {
					fixQXtract.setParam2(context.get(MapKey.param2).toString());
				}

				FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
				fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString(), idScheduler));
				fixQXtract.setParam4(this.emailDone);
				fixQXtract.setParam5(outFileName);
				fixQXtract.setParam6(batchNo);

			} else if (status.equals(StatusDefinition.REJECTED)) {
				this.getLogger().info("Status : REJECTED");
				this.getLogger().info("Register FixQXtract");

				stBranchDao.runReject(batchNo);
				fixQXtract = new FixQXtract();
				fClassConfig = classConfigDao.get(getClass().getName(), sourceProcess, StatusDefinition.REJECTED).get(0);
				idSchedulerXtract = fClassConfig.getIdScheduler();
				fixQXtract.setIdScheduler(idSchedulerXtract);
				fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
				fixQXtract.setDtmRequest(SchedulerUtil.getTime());
				fixQXtract.setParam1(param1);
				FixInboxDao fixInboxDao = new FixInboxDao(session);

				if (!context.get(MapKey.itemIdLink).toString().equals("")) {
					fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
				}

				fixQXtract.setParam4(this.emailRejected);
				fixQXtract.setParam5("");
				this.getLogger().info("Register FixQXtract");
			} else {
				this.getLogger().info("Status : IGNORED");
				//maybe ignored
				FixInboxDao fixInboxDao = new FixInboxDao(session);
				FixInbox fixInbox = fixInboxDao.get(context.get(MapKey.inboxId).toString());
				fixInbox.setFlgProcess(StatusDefinition.IGNORED);
				fixInboxDao.update(fixInbox);
			}
		
		return true;
	}
	
	private void readExcel(String param5, String configFile, StRoutingBranchDao stBranchDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException, ParseException {
		if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xls")) {
			readXLS(param5, configFile, stBranchDao);
			System.out.println("Success");
		} else if (FilenameUtils.getExtension(param5).equalsIgnoreCase("xlsx")) {
			readXLSX(param5, configFile, stBranchDao);
		}
	}

	private void readXLS(String param5, String configFile, StRoutingBranchDao stBranchDao) throws FileNotFoundException, IOException, InvalidFormatException, OpenXML4JException, SAXException, ParserConfigurationException {
		XLSReader xr = XLSReader.getInstance(param5, configFile);
		TmpStRoutingBranch tmpStRoutingBranch;
		TmpStRoutingBranchPK tmpStRoutingBranchPK;
		String[] propertyNames = {"compositeId.codBank",
			"codCcBrn", "namBranch","codSector", "flgIntercityClg", "cmd"};
		Integer recordId = 0;
		while (xr.hasNextRow()) {
			getLogger().info("Isi :" + propertyNames);
			tmpStRoutingBranch = new TmpStRoutingBranch();
			tmpStRoutingBranchPK = new TmpStRoutingBranchPK();
            recordId = recordId + 1;
			tmpStRoutingBranchPK.setBatchNo(context.get(MapKey.batchNo).toString());
            tmpStRoutingBranchPK.setRecordId(recordId);
            getLogger().info("recordId : " + recordId);
            tmpStRoutingBranch.setFlgstatus("U");
            tmpStRoutingBranch.setCodEntityVpd(11);
            tmpStRoutingBranch.setFlgMntStatus("A");
            tmpStRoutingBranch.setCtrUpdatSrlno(1);
			tmpStRoutingBranch.setCompositeId(tmpStRoutingBranchPK);
			tmpStRoutingBranch.setDtmCreated(SchedulerUtil.getTime());
            tmpStRoutingBranch.setCodLastMntMakerid(context.get(MapKey.emailSender).toString());
			tmpStRoutingBranch.setIdMaintainedBy(context.get(MapKey.emailSender).toString());
			getLogger().info("Extract :");
			try {
				if (xr.nextRow(tmpStRoutingBranch, propertyNames)) {
					stBranchDao.insert(tmpStRoutingBranch);
				}
			} catch (Exception ex) {
				this.getLogger().error(ex, ex);
				tmpStRoutingBranch.setFlgstatus(StatusDefinition.REJECTED);
				tmpStRoutingBranch.setStatusReason(ex.toString());
			}
		}
	}

	private void readXLSX(String param5, String configFile, StRoutingBranchDao stBranchDao) {
		XLSXReader xr = XLSXReader.getInstance(param5, configFile);
		TmpStRoutingBranch tmpStRoutingBranch;
		TmpStRoutingBranchPK tmpStRoutingBranchPK;
		String[] propertyNames = {"compositeId.codBank",
			"codCcBrn", "namBranch", "codSector", "flgIntercityClg", "cmd"};
		Integer recordId = 0;
		getLogger().info("Extract :");
		while (xr.hasNextRow()) {
            getLogger().info("Isi :" + propertyNames);
			tmpStRoutingBranch = new TmpStRoutingBranch();
			tmpStRoutingBranchPK = new TmpStRoutingBranchPK();
            recordId = recordId + 1;
			tmpStRoutingBranchPK.setBatchNo(context.get(MapKey.batchNo).toString());
            tmpStRoutingBranchPK.setRecordId(recordId);
            getLogger().info("recordId : " + recordId);
            tmpStRoutingBranch.setFlgstatus("U");
            tmpStRoutingBranch.setCodEntityVpd(11);
            tmpStRoutingBranch.setFlgMntStatus("A");
            tmpStRoutingBranch.setCtrUpdatSrlno(1);
			tmpStRoutingBranch.setCompositeId(tmpStRoutingBranchPK);
            tmpStRoutingBranch.setCodLastMntChkrid(context.get(MapKey.emailSender).toString());
			tmpStRoutingBranch.setDtmCreated(SchedulerUtil.getTime());
			tmpStRoutingBranch.setIdMaintainedBy(context.get(MapKey.emailSender).toString());
            getLogger().info("Extract :");
			try {
				if (xr.nextRow(tmpStRoutingBranch, propertyNames)) {
					getLogger().info("Extract :");
					stBranchDao.insert(tmpStRoutingBranch);
				}
			} catch (Exception ex) {
				this.getLogger().error(ex, ex);
				tmpStRoutingBranch.setFlgstatus(StatusDefinition.REJECTED);
				tmpStRoutingBranch.setStatusReason(ex.toString());
			}
		}
		}
	 enum xssfDataType {

		BOOL,
		ERROR,
		FORMULA,
		INLINESTR,
		SSTINDEX,
		NUMBER,
	}
}
