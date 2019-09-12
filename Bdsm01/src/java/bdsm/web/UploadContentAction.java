/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.util.DirectoryUtil;
import bdsm.util.HttpUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author NCBS
 */
public class UploadContentAction extends BaseContentAction {

	private static final String ACTION_GETXT = "fixSchedulerImport_get.action";
	private static final String ACTION_CHECK = "SknNgGeneralFileUpload_get.action";
	private static final String ACTION_UPLOAD = "SknNgGeneralFileUpload_insert.action";
	private static final String ACTION_GETSIZE = "fileUpload_get.action";
	private static final String multiEXT = "MULTI";
	private String theFileFileName;
	private String FILE_PROFILE;
	private String idMaintainedBy;
	private String idMaintainedSpv;
	private String NAM_MENU;
	private File theFile;
	private String realName;
	private String batchNo;
	private String TokenKey;
	private String TokenZ;
	private String bdsmhost;

    /**
     * 
     */
    public UploadContentAction() {
	}

    /**
     * 
     * @param theFileFileName
     * @param session
     * @param FILE_PROFILE
     * @param idMaintainedBy
     * @param idMaintainedSpv
     * @param NAM_MENU
     * @param theFile
     * @param realName
     * @param batchNo
     * @param Token
     * @param TokenZ
     * @param bdsmHost
     */
    public UploadContentAction(String theFileFileName, Map<String, Object> session, String FILE_PROFILE, String idMaintainedBy, String idMaintainedSpv, String NAM_MENU, File theFile, String realName, String batchNo, String Token, String TokenZ, String bdsmHost) {
		this.setTheFileFileName(theFileFileName);
		this.session = session;
		this.setFILE_PROFILE(FILE_PROFILE);
		this.setIdMaintainedBy(idMaintainedBy);
		this.setIdMaintainedSpv(idMaintainedSpv);
		this.setNAM_MENU(NAM_MENU);
		this.setTheFile(theFile);
		this.setRealName(realName);
		this.TokenKey = Token;
		this.TokenZ = TokenZ;
		this.bdsmhost = bdsmHost;
		this.batchNo = batchNo;
	}

    /**
     * 
     * @return
     */
    public String getExt() {
		Map<String, String> map = new HashMap<String, String>();
		String extFile = FilenameUtils.getExtension(getTheFileFileName());
		String result;
		String extComp = null;
		HashMap resultMap = null;
		String reqResult = null;
		String errorCode = null;

		this.getLogger().debug("EXTENSION :" + extFile);
		//String batchNo = SchedulerUtil.generateUUID();
		// Check Extension with Filepattern
		map.put("checkPattern", getFILE_PROFILE());
		map.put("model.idMaintainedBy", getIdMaintainedBy());
		HttpUtil protocol = new HttpUtil();
		result = protocol.commonRequest(map, this.bdsmhost, ACTION_GETXT, this.TokenKey, this.TokenZ);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
		} catch (JSONException je) {
			this.getLogger().error(je, je);
		}
		//resultMap = (HashMap) resultMap.get("checkPattern");
		extComp = resultMap.get("checkPattern").toString();
		//List listimports = (List) resultMap.get("modelList");

		return extComp;
	}

    /**
     * 
     * @return
     */
    public final List startUpload() {
		Map<String, String> map = new HashMap<String, String>();
		List status = new ArrayList();
		String extFile = FilenameUtils.getExtension(getTheFileFileName());
		boolean finish = false;
		String result;
		String extComp = null;
		HashMap resultMap = null;
		String reqResult = null;
		String errorCode = null;

		extComp = getExt();

		do {
			if (!extComp.equalsIgnoreCase(extFile)) {
				if (!extComp.equalsIgnoreCase(multiEXT)) {
					this.getLogger().info("File Upload Fail, Extension File not Recognized");
					status.add(UploadContentAction.ERROR);
					status.add(getText("error.file.extension"));
					//addActionError(getText("error.file.extension"));
					finish = true;
					continue;
				}
			}
			// Check whether file is uploaded before or not
			map.put("model.fileName", getFILE_PROFILE() + "%" + getTheFileFileName());
			map.put("model.idMaintainedBy", getIdMaintainedBy());
			HttpUtil protocol = new HttpUtil();
			result = protocol.commonRequest(map, this.bdsmhost, ACTION_CHECK, this.TokenKey, this.TokenZ);
			try {
				resultMap = (HashMap) JSONUtil.deserialize(result);
				reqResult = (String) resultMap.get("jsonStatus");
				errorCode = (String) resultMap.get("errorCode");
			} catch (JSONException je) {
				this.getLogger().error(je, je);
			}

			resultMap = (HashMap) resultMap.get("model");
			this.getLogger().info("model: " + resultMap);
			if ((result = (String) resultMap.get("filePath")) != null) {
				if (result.equalsIgnoreCase("processing")) {
					status.add(UploadContentAction.ERROR);
					status.add(getText(errorCode));
					//addActionError(getText(errorCode));
				} else if (result.equalsIgnoreCase("uploaded")) {
					status.add(UploadContentAction.ERROR);
					status.add(getText(errorCode));
					//addActionError(getText(errorCode));
				}

				finish = true;
				continue;
			}
			protocol = new HttpUtil();
			result = protocol.commonRequest(map, this.bdsmhost, ACTION_GETSIZE, this.TokenKey, this.TokenZ);
			try {
				resultMap = (HashMap) JSONUtil.deserialize(result);
				reqResult = (String) resultMap.get("jsonStatus");
				errorCode = (String) resultMap.get("errorCode");
			} catch (JSONException je) {
				this.getLogger().error(je, je);
			}
			resultMap = (HashMap) resultMap.get("model");
			result = (String) resultMap.get("sizeStatus");

			long sizeParam = Long.parseLong(result);
			long Filesize = getTheFile().length();
			getLogger().info("SIZEPARAM :" + sizeParam);
			getLogger().info("SIZEFILE :" + Filesize);

			String encryptedContent = null;
			File pathFile = null;
			boolean sizeTrigger = true;

			if (Filesize >= sizeParam) {
				try {
					DirectoryUtil zipping = new DirectoryUtil();
					getLogger().info("REAL NAME :" + this.getRealName());
					if(this.getRealName().charAt(0) == '_'){
						setRealName(this.getRealName().substring(1,this.getRealName().length()-1));
					}
					pathFile = zipping.WebZipping(this.getRealName(), this.getTheFile());
					sizeTrigger = true;
				} catch (Exception ex) {
					getLogger().error("ERROR :" + ex, ex);
					pathFile = null;

					status.add(UploadContentAction.ERROR);
					status.add(getText("error.file.zip") + this.theFileFileName);
					finish = true;
					continue;
				}
				encryptedContent = DirectoryUtil.getTheFileAsString(pathFile);
			} else {
				encryptedContent = DirectoryUtil.getTheFileAsString(this.theFile);
				sizeTrigger = false;
			}

			if (encryptedContent != null) {
				map.clear();
				if (sizeTrigger) {
					map.put("model.fileName", (pathFile.getName().replaceFirst("temp", "")));
				} else {
					map.put("model.fileName", this.realName);
				}
				map.put("model.sizeStatus", (sizeTrigger ? "LARGE" : "SMALL"));
				map.put("model.idMaintainedBy", getIdMaintainedBy());
				map.put("model.theFileString", encryptedContent);
				map.put("model.idMaintainedSpv", getIdMaintainedSpv());
				map.put("namMenu", this.getNAM_MENU());
				protocol = new HttpUtil();
				result = protocol.commonRequest(map, this.bdsmhost, ACTION_UPLOAD, this.TokenKey, this.TokenZ);
				try {
					resultMap = (HashMap) JSONUtil.deserialize(result);
					reqResult = (String) resultMap.get("jsonStatus");
					errorCode = (String) resultMap.get("errorCode");
				} catch (JSONException ex) {
					this.getLogger().fatal(ex, ex);
				}

				if (reqResult.equalsIgnoreCase(UploadContentAction.ERROR)) {
					//addActionError(getText("error.file.upload") + errorCode);
					status.add(UploadContentAction.ERROR);
					status.add(getText("error.file.upload") + errorCode);
				} else if (reqResult.equalsIgnoreCase(UploadContentAction.SUCCESS)) {

					this.getLogger().info(getText("success.10"));
					status.add(UploadContentAction.SUCCESS);
					status.add(getText("success.10"));
					//addActionMessage(getText("success.10") + Notif);
				}
				if (sizeTrigger) {
					try {
						// clean file on source web
						DirectoryUtil.delFile(pathFile.getPath());
					} catch (Exception ex) {
						getLogger().debug("WARNING FAILED to DELETE : " + pathFile);
						getLogger().error(ex, ex);
					}
				}

			} else {
				status.add(UploadContentAction.ERROR);
				status.add(getText("error.file.content") + this.theFileFileName);
				addActionError(getText("error.file.content"));
			}

			finish = true;
		} while (!finish);

		return status;
	}

    /**
     * 
     * @return
     */
    protected Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @return the theFileFileName
	 */
	public String getTheFileFileName() {
		return theFileFileName;
	}

	/**
	 * @param theFileFileName the theFileFileName to set
	 */
	public void setTheFileFileName(String theFileFileName) {
		this.theFileFileName = theFileFileName;
	}

	/**
	 * @return the FILE_PROFILE
	 */
	public String getFILE_PROFILE() {
		return FILE_PROFILE;
	}

	/**
	 * @param FILE_PROFILE the FILE_PROFILE to set
	 */
	public void setFILE_PROFILE(String FILE_PROFILE) {
		this.FILE_PROFILE = FILE_PROFILE;
	}

	/**
	 * @return the idMaintainedBy
	 */
	public String getIdMaintainedBy() {
		return idMaintainedBy;
	}

	/**
	 * @param idMaintainedBy the idMaintainedBy to set
	 */
	public void setIdMaintainedBy(String idMaintainedBy) {
		this.idMaintainedBy = idMaintainedBy;
	}

	/**
	 * @return the idMaintainedSpv
	 */
	public String getIdMaintainedSpv() {
		return idMaintainedSpv;
	}

	/**
	 * @param idMaintainedSpv the idMaintainedSpv to set
	 */
	public void setIdMaintainedSpv(String idMaintainedSpv) {
		this.idMaintainedSpv = idMaintainedSpv;
	}

	/**
	 * @return the NAM_MENU
	 */
	public String getNAM_MENU() {
		return NAM_MENU;
	}

	/**
	 * @param NAM_MENU the NAM_MENU to set
	 */
	public void setNAM_MENU(String NAM_MENU) {
		this.NAM_MENU = NAM_MENU;
	}

	/**
	 * @return the theFile
	 */
	public File getTheFile() {
		return theFile;
	}

	/**
	 * @param theFile the theFile to set
	 */
	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}
