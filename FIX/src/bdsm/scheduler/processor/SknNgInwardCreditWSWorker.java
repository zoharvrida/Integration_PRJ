package bdsm.scheduler.processor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.model.MasterUser;
import bdsm.model.SknNgInOutCreditHeader;
import bdsm.rpt.dao.FixMasterReportDao;
import bdsm.rpt.model.FixMasterReport;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.dao.TmpSknngInOutCreditHeaderDAO;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixClassConfig;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.TmpGefuRespons;
import bdsm.scheduler.util.FileUtil;
import bdsm.service.HTTPRequestService;
import bdsm.service.SknNgInwardCreditBulkToIndividualService;
import bdsm.service.SknNgInwardCreditService;
import bdsm.service.SknNgSPKWebService;
import bdsm.service.SknNgService;
import bdsm.util.BdsmUtil;
import bdsm.util.EncryptionUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.MasterUserDao;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;


/**
 * @author v00019372
 */
public class SknNgInwardCreditWSWorker extends BaseProcessor {
	private StringWriter buffer = new StringWriter();
	private boolean isDetail;
	
	
	public SknNgInwardCreditWSWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}


	@Override
	protected boolean doExecute() throws Exception {
		ParameterDao paramDAO = new ParameterDao(this.session);
		FixSchedulerXtractDao xtractDAO = new FixSchedulerXtractDao(this.session);
		FixSchedulerXtract fsx = null;
		SknNgInwardCreditService service = new SknNgInwardCreditService();
		SknNgInwardCreditBulkToIndividualService bulkService = new SknNgInwardCreditBulkToIndividualService(); 
		String idUserLogin = "SYSTEM";
		String batchNo = null;
		String param3, hour;
		File generatedFile;
		boolean isGenerateSTPKReport = true;
		
		
		String period = (String) this.context.get(MapKey.param1);
		String cityCode = (String) this.context.get(MapKey.param2);
		
		if (this.context.get(MapKey.reportFileName) != null) { // From Download Report
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
			String temp = new SknNgInwardDebitBulkWorkerDAO(this.session).getBICFromBranchCode(Integer.valueOf((String) this.context.get(MapKey.hdcdBranch)));
			param3 = (SknNgService.BIC_CONVENT.equals(temp))? "C": "S";
			hour = sdf.format(HibernateUtil.getDBDateTime(this.session));
			generatedFile = new File(PropertyPersister.dirFixOut, this.context.get(MapKey.reportFileName).toString());
		}
		else { // From BDSM Fix Scheduler
			xtractDAO = new FixSchedulerXtractDao(this.session);
			fsx = xtractDAO.get(((FixQXtract) this.context.get(MapKey.fixQXtract)).getIdScheduler());
			param3 = (String) this.context.get(MapKey.param3);
			hour = (String) this.context.get(MapKey.param4);
			generatedFile = new File(PropertyPersister.dirFixInTemp, FileUtil.getDateTimeFormatedFileName(fsx.getFilePattern() + "_S" + param3 + "_PRD" + period + "_{yyyyMMdd-HHmmss}." + fsx.getFileFormat()));
		}
		
		this.getLogger().info("param1: " + period);
		this.getLogger().info("param2: " + cityCode);
		this.getLogger().info("param3: " + param3);
		this.getLogger().info("param4: " + hour);
		
		String BIC = "C".equals(param3)? SknNgSPKWebService.CONVENT_BIC: SknNgSPKWebService.SYARIAH_BIC;
		
		// Check whether the hour is greater than the last hour of last periode
		Map<String, String> schedulerTimeMap = BdsmUtil.parseKeyAndValueToMap(paramDAO.get("SKNNG.INWARD_CREDIT_WS_SCHEDULER").getStrVal());
		if (Integer.parseInt(period) == schedulerTimeMap.size()) {
			StringTokenizer st = new StringTokenizer(schedulerTimeMap.get(period), "|");
			while (st.hasMoreTokens())
				if (hour.compareTo(st.nextToken()) == -1) {
					isGenerateSTPKReport = false;
					break;
				}
		}
		else
			isGenerateSTPKReport = false;
		
		this.getLogger().info("isGenerateSTPKReport: " + isGenerateSTPKReport);
		
		
		/* Call Web Service to SPK */
		StringBuffer sbResult = new StringBuffer();
		
		String result = service.getResultOfRequestInwardWSToSPK(this.session, BIC, period, StringUtils.isBlank(cityCode)? "": cityCode);
		this.getLogger().debug("WS Individual Result:\n" + result);
		sbResult.append(result);
		
		result = bulkService.getResultOfRequestInwardWSToSPK(this.session, BIC, period, StringUtils.isBlank(cityCode)? "": cityCode);
		this.getLogger().debug("WS Bulk Result:\n" + result);
		sbResult.append(result);
		
		
		BufferedReader reader = new BufferedReader(new StringReader(sbResult.toString()));
		BufferedWriter buffWriter = new BufferedWriter(this.buffer);
		BufferedWriter writer = null;
		TmpSknngInOutCreditHeaderDAO headerDAO = new TmpSknngInOutCreditHeaderDAO(this.session);
		BaBankMastDAO baBankMastDAO = new BaBankMastDAO(this.session);
		SknNgInOutCreditHeader modelHeader = null;
		String line;
		
		boolean isBlank = true;
		boolean isValid = false;
		String errorMessage = null;
		Date businessDate = baBankMastDAO.get().getBusinessDate();
		
		
		try {
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0)
					continue;
				
				switch (line.charAt(0)) {
					case '0' : {
							this.resetState();	
							modelHeader = service.parseToInOutHeader(line, null, null);
								if ((headerDAO.getByTanggalBatchAndBatchId(modelHeader.getTanggalBatch(), modelHeader.getBatchId()) == null))
									isValid = true;
						}
						break;
					case '1' : {
							if (isValid) {
								if (isBlank == true)
									isBlank = false;
								
								this.isDetail = true;
							}
						}
						break;
					case '3' : {
							if (isValid && this.isDetail) {
								writeln(buffWriter, line);
								
								if (writer == null)
									writer = new BufferedWriter(new FileWriter(generatedFile));
								
								writer.write(this.buffer.toString());
								
								this.resetState();
								isValid = false;
							}
							else
								isValid = false;
						}
				}
				
				if (isValid)
					writeln(buffWriter, line);
			}
		}
		finally {
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
		
		
		this.getLogger().info("isBlank: " + isBlank);
		if (isBlank == false) {
			boolean done = false;
			
			/* Check Encryption */
			FixSchedulerImportDao fsiDAO = new FixSchedulerImportDao(this.session);
			FixSchedulerImport fsi = fsiDAO.get(generatedFile.getName().substring(0, 6));
			if (StatusDefinition.YES.equalsIgnoreCase(fsi.getFlgEncrypt()))
				generatedFile = new File(generatedFile.getParentFile(), FileUtil.encrypt(generatedFile.getPath(), fsi.getModDecrypt(), fsi.getKeyDecrypt()));
			
			
			// process upload & approve into BDSM Browser
			while (!done) {
				String resp;
				String batchCode;
				Map<String, Object> resultMap = new java.util.HashMap<String, Object>(0);
				
				// Get JSESSIONID
				String sessionId = this.getJSessionId();
				if (StringUtils.isBlank(sessionId)) {
					this.getLogger().error("\n\t" + (errorMessage = "No session id got from server"));
					done = true; continue;
				}
				
				// Login
				resp = this.loginToBDSMWeb(sessionId, idUserLogin, resultMap);
				if (resp.contains("frmLogin")) {
					this.getLogger().error("\n\t" + (errorMessage = "Login \"" + idUserLogin + "\" failed"));
					done = true; continue;
				}
				MasterUser user = (MasterUser)resultMap.get("user");
				
				// Menu 23402 -- Upload Inward Credit File
				resp = this.uploadFile(sessionId, generatedFile, user);
				/* if success, it should be like this pattern: 
			 	   <ul id="actionMessage" class="actionMessage"><li><span>File Uploaded Succesfully with Code No : xxxxxx</span></li></ul> */
				Pattern pattern = Pattern.compile("<ul id=\"actionMessage\".+</ul>", Pattern.DOTALL);
				Matcher matcher = pattern.matcher(resp);
				if (!matcher.find()) {
					this.getLogger().error("\n\t" + (errorMessage = "No actionMessage result"));
					done = true; continue;
				}
				
				String strMatch = matcher.group();
				pattern = Pattern.compile("\\d{6}", Pattern.DOTALL);
				matcher = pattern.matcher(strMatch);
				if  (!matcher.find()) {
					this.getLogger().error("\n\t" + (errorMessage = "The result doesn't come with Batch Code"));
					done = true; continue;
				}
				batchCode = matcher.group();
				this.getLogger().info("\n\tBatch Code: " + batchCode);
				this.getLogger().info("\n\tDelete file " + generatedFile + "? " + (FileUtils.deleteQuietly(generatedFile)));
				
				// get new token
				String token = this.getNewTokenFromServer(sessionId);
				if (token == null) {
					this.getLogger().error("\n\t" + (errorMessage = "Error when requesting token"));
					done = true; continue;
				}
				else
					this.getLogger().debug("token got from server: '" + token + "'");
				
				// process approval all Inward Credit - Menu 23204
				resp = this.approveAllInwardCredit(sessionId, batchCode, token);
				pattern = Pattern.compile("<ul id=\"actionMessage\".+</ul>", Pattern.DOTALL);
				matcher = pattern.matcher(resp);
				
				if (matcher.find()) {
					this.getLogger().info("\n\t" + "Approve all inward with batchCode " + batchCode + " success");
					
					String strDate = "";
					synchronized (DateUtility.DATE_FORMAT_YYMMDD) {
						strDate = DateUtility.DATE_FORMAT_YYMMDD.format(businessDate);
					}
					StringBuilder sbBatchNo = new StringBuilder(BdsmUtil.leftPad(user.getCdBranch().toString(), 5, '0'))
						.append(strDate)
						.append(batchCode);
					
					batchNo = sbBatchNo.toString();
					
					// Call BDSM STPK Process
					List<FixClassConfig> listFCC = (new FixClassConfigDao(this.session)).get(
							this.getClass().getName(), "EVENT", bdsm.scheduler.StatusDefinition.AUTHORIZED, (String) this.context.get(MapKey.typeFix));
					
					if (listFCC.size() == 0)
						throw new FIXException("There is no BDSM STPK Process Configuration in BDSM");
					
					FixQXtract fq = new FixQXtract();
					fq.setIdScheduler(listFCC.get(0).getIdScheduler());
					fq.setFlgProcess(StatusDefinition.REQUEST);
					fq.setDtmRequest(SchedulerUtil.getTime());
					fq.setParam1(batchNo);
					
					(new FixQXtractDao(this.session)).insert(fq);
				}
				else
					this.getLogger().error("\n\t" + (errorMessage = "Approve all inward with batchCode " + batchCode + " failed"));
					
				done = true;
			}
		}
		
		if (errorMessage != null)
			throw new FIXException(errorMessage);
		
		
		if (isGenerateSTPKReport) {
			FixMasterReportDao fmrDAO = new FixMasterReportDao(this.session);
			FixMasterReport fmr = fmrDAO.getByNamScheduler("SKNNG_BDSM_STPK_REPORT");
			fsx = xtractDAO.get(fmr.getCompositeId().getIdScheduler());
			
			Map<String, Object> threadObjectMap = new HashMap<String, Object>(0);
			threadObjectMap.put("fixSchedulerXtract", fsx);
			threadObjectMap.put("reportName", fmr.getReportName());
			threadObjectMap.put("batchNo", batchNo);
			
			new GenerateSTPKProcess(threadObjectMap).start();
		}
		
		return true;
	}

	private void resetState() {
		StringBuffer bf = this.buffer.getBuffer();
		bf.delete(0, bf.length());
		this.isDetail = false;
	}

	private static void writeln(BufferedWriter writer, String data) throws Exception {
		writer.append(data);
		writer.newLine();
		writer.flush();
	}
	
	
	protected String getJSessionId() throws Exception {
		String sessionId = HTTPRequestService.getJSessionId(PropertyPersister.BDSM_URL_WEB_MODULE + "/login_input.action", null);
		this.getLogger().debug("Session ID: " + sessionId);
		
		return sessionId;
	}
	
	protected String loginToBDSMWeb(String sessionId, String idUser, Map<String, Object> otherResult) throws Exception {
		MasterUserDao userDAO = new MasterUserDao(this.session);
		MasterUser user = userDAO.get(idUser);
		
		Map<String, String> params = new java.util.HashMap<String, String>(0);
		params.put("idUser", idUser);
		params.put("password", EncryptionUtil.getAES(user.getIdUserFcr(), BdsmUtil.rightPad(user.getIdUser(), 16, '@'), 2));
		params.put("postback", "Y");
		String response = (String) HTTPRequestService.sendPOSTRequest(PropertyPersister.BDSM_URL_WEB_MODULE + "/login.action", params, sessionId)[1];
		
		otherResult.put("user", user);
		return response;
	}
	
	protected String uploadFile(String sessionId, File file, MasterUser user) throws Exception {
		Map<String, File> files = new java.util.HashMap<String, File>(0);
		Map<String, String> params = new java.util.HashMap<String, String>(0);
		
		files.put("theFile", file);
		params.put("cdBranch", user.getCdBranch().toString());
		params.put("idMaintainedBy", user.getIdUser());
		params.put("struts.token.name", "token");
		params.put("token", SchedulerUtil.generateUUID2());
		String response = (String) HTTPRequestService.sendPOSTRequest(PropertyPersister.BDSM_URL_WEB_MODULE + "/23402_add.action", files, params, sessionId)[1];
		
		this.getLogger().debug("Resp Result (uploadFile): \n\t" + response);
		
		return response;
	}
	
	protected String getNewTokenFromServer(String sessionId) throws Exception {
		Map<String, String> params = new java.util.HashMap<String, String>(0);
		String keyTokenName = "tokenWS";
		String token = null;
		
		params.put("name", keyTokenName);
		String response = (String) HTTPRequestService.sendGETRequest(PropertyPersister.BDSM_URL_WEB_MODULE + "/generateToken.action", params, sessionId)[1];
		this.getLogger().debug("Resp Result (token): " + response);
		
		Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"" + keyTokenName + "\"[^/]+", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		Matcher matcher = pattern.matcher(response);
		
		if (matcher.find()) {
			String strMatch = matcher.group();
			pattern = Pattern.compile("value=\"[A-Z0-9]{30,}\"", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
			matcher = pattern.matcher(strMatch);
			if (matcher.find()) {
				token = matcher.group();
				token = token.substring("value=\"".length(), token.indexOf('"', "value=\"".length()));
			}
		}
		
		return token;
	}
	
	protected String approveAllInwardCredit(String sessionId, String batchCode, String token) throws Exception {
		Map<String, String> params = new java.util.HashMap<String, String>(0);
		String keyTokenName = "tokenWS";
		
		params.put("IDBATCH", batchCode);
		params.put("recIds", "T");
		params.put("isApprove", "true");
		params.put("struts.token.name", keyTokenName);
		params.put(keyTokenName, token);
		String response = (String) HTTPRequestService.sendPOSTRequest(PropertyPersister.BDSM_URL_WEB_MODULE + "/23204_add.action", params, sessionId)[1];
		
		this.getLogger().debug("Resp Result (approveAll): \n\t" + response);
		
		return response;
	}

}


class GenerateSTPKProcess extends Thread {
	Map<String, ? extends Object> context;
	
	public GenerateSTPKProcess(Map<String, ? extends Object> context) {
		this.context = context;
	}
	
	
	@Override
	public void run() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		TmpGefuResponsDao tgrDAO = new TmpGefuResponsDao(session);
		FixQXtractDao fqxDAO = new FixQXtractDao(session);
		
		String[] arrFilenameSuffix = new String[] { "", "AE" };
		FixSchedulerXtract fsx = (FixSchedulerXtract) this.context.get("fixSchedulerXtract");
		String reportName = (String) this.context.get("reportName");
		String batchNo = (String) this.context.get("batchNo");
		
		if (batchNo != null) {
			try {
				Thread.sleep(5 * 60 * 1000);
			}
			catch (Exception e) {}
			
			boolean isDone = false;
			while (!isDone) {
				List<TmpGefuRespons> list = tgrDAO.listByBatchNoLike(batchNo);
				if (list.size() > 0)
					for (TmpGefuRespons tgr : list) {
						isDone = true;
						if ((tgr.getStatus() == null) || ("5|6".indexOf(tgr.getStatus()) == -1)) {
							isDone = false;
							break;
						}
					}
				
				if (isDone)
					break;
				else {
					try {
						Thread.sleep(10 * 60 * 1000);
					}
					catch (Exception e) {}
				}
			}
		}
		
		for (String s: arrFilenameSuffix) {
			FixQXtract fqx = new FixQXtract();
			fqx.setIdScheduler(fsx.getFixSchedulerPK().getIdScheduler());
			fqx.setDtmRequest(new java.sql.Timestamp(new Date().getTime()));
			fqx.setFlgProcess(StatusDefinition.REQUEST);
			fqx.setParam1(s);
			fqx.setParam5(FileUtil.getDateTimeFormatedFileName(reportName + s + "{yyMMdd-HHmmss}." + fsx.getFileFormat()));
			
			fqxDAO.insert(fqx);
			tx.commit();
			
			tx = session.beginTransaction();
		}

	}
}

