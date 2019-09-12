/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.web.Menu12202;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import sun.misc.BASE64Decoder;
import bdsm.rpt.web.BaseContentAction;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.util.SchedulerUtil;

/**
 * 
 * @author v00019722
 */
public class Menu12202Action extends BaseContentAction {
	private static final DateFormat DATE_FORMATTER = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String ACTION_ADD = "fixReportDownload_update.action";
	private static final String ACTION_UPDATE = "fixReportDownload_insert.action";
	private static final String ACTION_GET = "fixReportDownload_get.action";
	private static final String ACTION_GETDOWNLOAD = "fileDownload_get.action";
	private static final String ACTION_GET_FILEPATH = "fixReportParam_get.action";
	private static final String NAM_MENU = "Fix Report Download";
	private String user;
	private String dtmRequest;
	private String fileName;
	private InputStream fileInputStream;
	private String filePath;
	private String usertemp;
	private String reason;
	private String idBatch;
	private String idReport;
	private String flag;
	
	@Override
	public String exec() {
		String[] relative;
		StringBuilder pPath = new StringBuilder();
		this.getLogger().info("Reason :" + reason);
		this.getLogger().info("path :" + filePath);
		this.getLogger().info("user :" + usertemp);
		this.getLogger().info("idBatch :" + idBatch);
		this.getLogger().info("filename :" + fileName);
		int i = 0;
		int j = 0;
		String time = SchedulerUtil.getTime().toString();
		String truepath = null;
		String filepathHostRetriever = "";

		try {
			Map<String, String> mapBatch = new HashMap<String, String>();
			HashMap mapModelBatch;

			mapBatch.put("modelM.compositeId.idBatch", idBatch);
			mapBatch.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

			String result = HttpUtil.request(getBdsmHost() + ACTION_GET_FILEPATH, mapBatch);
			Map resultMap = (Map) JSONUtil.deserialize(result);
			mapModelBatch = (HashMap) resultMap.get("modelResp");
			filepathHostRetriever = (String) mapModelBatch.get("filePath");
		}
		catch (Exception e) {
			this.getLogger().info(" NULL P : " + e.getMessage());
		}

		relative = filepathHostRetriever.split("\\/");
		for (int k = 0; i < relative.length - 1; i++)
			pPath.append(relative[k]).append("/");
		
		this.getLogger().info("relative_name : " + relative[relative.length - 1]);
		// String onlyPath = pPath.toString();
		String onlyPath = FilenameUtils.getPath(filepathHostRetriever);
		this.getLogger().info("only Path : " + onlyPath);
		// pPath.append(realPath()).append(usertemp);
		String realPath = filepathHostRetriever;
		String trueFilename = relative[relative.length - 1];
		String JSONTag = "";
		
		this.getLogger().info("RELATIVE PATH :" + filePath);
		this.getLogger().info("Cek Reason :" + reason);

		if (!"".equals(reason)) {
			truepath = error(onlyPath, time);
			String regexps = "\\.";
			// this.getLogger().info("Pathing : " + realPath());

			String[] format = null;
			try {
				format = truepath.split(regexps);
			}
			catch (NullPointerException e) {
				this.getLogger().info("FileName Empty");
			}
			
			i = format.length - 1;
			j = i - 1;
			try {
				StringBuilder reportContentFromHost = new StringBuilder();

				reportContentFromHost.append("Time: " + DATE_FORMATTER.format(new java.util.Date()) + LINE_SEPARATOR);
				reportContentFromHost.append("Error: " + reason);
				byte[] tmp = reportContentFromHost.toString().getBytes();

				// tmp = new
				// BASE64Decoder().decodeBuffer(reportContentFromHost);

				InputStream is = new ByteArrayInputStream(tmp);
				setFileInputStream(is);
				doAdd();

			}
			catch (Exception fileNotFoundException) {
				this.getLogger().error(this.getClass().getSimpleName() + ":File cannot be found");
				doEdit();
				return SUCCESS;
			}
			this.getLogger().info("Error Log :" + truepath);
			// update dtmFinish
			return "downloadtxt";
		}
		else {
			String regexps = "\\.";
			this.getLogger().info("Pathing : " + realPath);

			String[] format = null;
			try {
				format = trueFilename.split(regexps);
			}
			catch (NullPointerException e) {
				this.getLogger().info("FileName Empty");
			}
			
			i = format.length - 1;
			j = i - 1;

			this.getLogger().info(realPath);
			this.getLogger().info("Format : " + format[i]);
			try {
				// File file = new File(realPath);
				// setFileInputStream(new FileInputStream(file));
				Map<String, String> map = new HashMap<String, String>();
				String reportContentFromHost = "";
				
				HashMap mapModel;

				map.put("model.reportPath", realPath);
				map.put("model.reportType", format[i].toUpperCase());
				map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
				String result = HttpUtil.request(getBdsmHost() + ACTION_GETDOWNLOAD, map);
				Map resultMap = (Map) JSONUtil.deserialize(result);
				mapModel = (HashMap) resultMap.get("model");
				reportContentFromHost = (String) mapModel.get("reportContent");
				JSONTag = (String) mapModel.get("reportJSON");
				
				byte[] tmp = new BASE64Decoder().decodeBuffer(reportContentFromHost);

				InputStream is = new ByteArrayInputStream(tmp);
				// InputStream is2 = new FileInputStream(new
				// File("D:\\BDSM\\log.zip"));
				setFileInputStream(is);
			}
			catch (Exception fileNotFoundException) {
				this.getLogger().error(this.getClass().getSimpleName() + ":File cannot be found");
				this.addActionError(this.getClass().getSimpleName() + ":File cannot be found");
				doEdit();
				return ERROR;
			}
			
			doAdd(); // update dtmFinish
			this.getLogger().info("Format : " + format[i]);
			if ("failed".equals(JSONTag)){
				this.getLogger().info("FILE EXTENSION unsupported!!!");
				this.addActionError("FILE EXTENSION unsupported!!!");
				doEdit();
				return ERROR;
			} else {
				return JSONTag;
			}
		}
		// fileName = "Book1.xls";
	}

	@SkipValidation
	public String gridDownload() {
		this.getLogger().info("REQUEST : " + user);
		return "gridDownload";
	}

	@SkipValidation
	public String gridDownloaded() {
		this.getLogger().info("REQUEST : " + user);
		return "gridDownloaded";
	}

	@SkipValidation
	public String error(String path, String time) {
		this.getLogger().info("[ Begin ] Error Log Creation()");
		this.getLogger().info("Reason(for Error): " + getReason());
		
		try {
			if (isValidSession()) {
				Map<String, String> map = new HashMap<String, String>();
				HashMap resultMap;
				HashMap resPath = null;
				String reqResult = null;
				String errorCode = null;
				String realPath = null;

				this.getLogger().info("ID_BATCH :" + getIdBatch());
				String result = null;
				map.put("time", time);
				map.put("reason", getReason());
				map.put("realPath", path);
				map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
				map.put("model.compositeId.idBatch", getIdBatch());

				// result = HttpUtil.request(getBdsmHost() + ACTION_EXEC, map);
				result = HttpUtil.request(getBdsmHost() + ACTION_UPDATE, map);

				try {
					resultMap = (HashMap) JSONUtil.deserialize(result);
					reqResult = (String) resultMap.get("jsonStatus");
					errorCode = (String) resultMap.get("errorCode");
					resPath = (HashMap) resultMap.get("path");
					realPath = (String) resultMap.get("fullPath");

					this.getLogger().info("Path for download :" + realPath);
				}
				catch (JSONException ex) {
					this.getLogger().fatal(ex, ex);
				}
				catch (NullPointerException e) {
					this.getLogger().info(" NULL P : " + e.getMessage());
				}

				if (reqResult.equals(ERROR))
					addActionError(getText(errorCode));
				else if (reqResult.equals(SUCCESS))
					addActionMessage(getText(errorCode));
				
				return realPath;
			}
			else {
				return logout();
			}
		}
		catch (NullPointerException e) {
			this.getLogger().info("null Pointer" + e);
			// setErrorMessage("CIF: " + getNoCif() + " not found");
			return ERROR;
		}
		catch (Throwable e) {
			this.getLogger().fatal(e, e);
			return ERROR;
		}
		finally {
			this.getLogger().info("[ End ] Error Creation()");
		}
		//
	}

	@Override
	public String doAdd() {
		this.getLogger().info("IDBATCH :" + getIdBatch());
		this.getLogger().info("idReport :" + getIdReport());
		Map<String, String> map = new HashMap<String, String>();
		HashMap resultMap;
		String reqResult = null;
		String errorCode = null;
		int k = 0;
		String result;

		map.put("model.compositeId.idBatch", getIdBatch());
		map.put("model.idReport", getIdReport());
		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		result = HttpUtil.request(getBdsmHost() + ACTION_ADD, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}
		catch (NullPointerException e) {
			this.getLogger().info(" NULL P : " + e.getMessage());
		}

		if (reqResult.equals(ERROR))
			addActionError(getText(errorCode));
		else if (reqResult.equals(SUCCESS))
			addActionMessage(getText(errorCode));
		
		return "SUCCESS";
	}

	@Override
	public String doEdit() {
		this.getLogger().info("IDBATCH FNF :" + getIdBatch());
		Map<String, String> map = new HashMap<String, String>();
		HashMap resultMap;
		String reqResult = null;
		String errorCode = null;
		String result;

		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		result = HttpUtil.request(getBdsmHost() + ACTION_GET, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
			this.getLogger().info("ERROR : " + errorCode);
			this.getLogger().info("JSON  : " + reqResult);

		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}
		catch (NullPointerException e) {
			this.getLogger().info(" NULL P : " + e.getMessage());
		}

		if (reqResult.equals(ERROR))
			addActionError(getText(errorCode));
		else if (reqResult.equals(SUCCESS))
			addActionMessage(getText(errorCode));
		
		return SUCCESS;
	}

	@Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the dtmRequest
	 */
	public String getDtmRequest() {
		return dtmRequest;
	}
	/**
	 * @param dtmRequest the dtmRequest to set
	 */
	public void setDtmRequest(String dtmRequest) {
		this.dtmRequest = dtmRequest;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileInputStream
	 */
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	/**
	 * @param fileInputStream the fileInputStream to set
	 */
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	/**
	 * @return the usertemp
	 */
	public String getUsertemp() {
		return usertemp;
	}
	/**
	 * @param usertemp the usertemp to set
	 */
	public void setUsertemp(String usertemp) {
		this.usertemp = usertemp;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the idBatch
	 */
	public String getIdBatch() {
		return idBatch;
	}
	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(String idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the idReport
	 */
	public String getIdReport() {
		return idReport;
	}
	/**
	 * @param idReport the idReport to set
	 */
	public void setIdReport(String idReport) {
		this.idReport = idReport;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
