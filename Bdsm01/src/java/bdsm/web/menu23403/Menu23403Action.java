package bdsm.web.menu23403;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import bdsm.web.UploadContentAction;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
@SuppressWarnings({"serial", "restriction", "unchecked"})
public class Menu23403Action extends BaseContentAction {

	private static final long serialVersionUID = 1L;
	private static final String FILE_PROFILE = "SKNSMO";
	private static final String ACTION_GETMAST = "fcrChAcctMast_save.action";
	private static final String NAM_MENU = "SKN SMO Debit";
	private String cdBranch;
	private File theFile;
	private String theFileContentType;
	private String theFileFileName;
	private String idMaintainedBy;
	private String idMaintainedSpv;
	private String acctSearch;
	private String account;

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
    public String $add() {
		return add();
	}
	
    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		//String batchNo = SchedulerUtil.generateUUID();

		StringBuilder linkBatch = new StringBuilder();
		String DateTime = null;

		Date bdt = (Date) this.session.get(Constant.C_DATEBUSINESS);
		String stDate = DateUtility.DATE_FORMAT_YYMMDD.format(bdt);
		String stTime = DateUtility.TIME_FORMAT_NO_COLON.format(new Date());

		this.getLogger().info("CD_BRANCH : " + this.session.get(Constant.C_CODEBRANCH).toString());
		this.getLogger().info("DATETIME : " + DateTime);
		linkBatch.append(BdsmUtil.leftPad(this.session.get(Constant.C_CODEBRANCH).toString(), 5, '0')).append(stDate).append(stTime);
		String batchNo = linkBatch.toString();

		String realName = FILE_PROFILE + "_" + batchNo + "_" + idMaintainedBy + ":" + this.acctSearch + "_" + theFileFileName;

		UploadContentAction myFramework = new UploadContentAction(theFileFileName,
				session, FILE_PROFILE, idMaintainedBy, idMaintainedSpv,
				NAM_MENU, theFile, realName, batchNo, getTokenKey(), getTzToken(), getBdsmHost());
		String Notif = batchNo.substring(batchNo.length() - 6, batchNo.length());
		List status = myFramework.startUpload();
		if (!status.isEmpty()) {
			if (status.get(0).toString().equalsIgnoreCase(UploadContentAction.ERROR)) {
				addActionError(status.get(1).toString());
			} else {
				addActionMessage(status.get(1).toString() + " " + Notif);
			}
		}
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		Map<String, String> map = new HashMap<String, String>();
		boolean finish = false;
		String result;
		HashMap resultMap = null;
		String reqResult = null;
		String errorCode = null;

		String AccountNo = BdsmUtil.rightPad(getAcctSearch(), 16);
		this.getLogger().debug("Account No :" + getAcctSearch());
		this.getLogger().info("idMaintainedBy : " + this.idMaintainedBy);
		this.getLogger().info("idMaintainedSpv : " + this.idMaintainedSpv);

		// Check whether file is uploaded before or not
		map.put("modelPKfcr.codAcctNo", AccountNo);
		map.put("modelPKfcr.flgMntStatus", "A");
		map.put("modelPKfcr.idMaintainedBy", this.idMaintainedBy);
		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
		result = HttpUtil.request(getBdsmHost() + ACTION_GETMAST, map);
		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
		} catch (JSONException je) {
			this.getLogger().error(je, je);
		}
		resultMap = (HashMap) resultMap.get("modelFCR");
		this.getLogger().info("model: " + resultMap);
		if (resultMap == null) {
			this.getLogger().debug("Account is not found");
			account = "Account is not found";
		} else {
			this.getLogger().debug("Account is Valid");
			account = "";
		}
		return "account";
	}

    /**
     * 
     * @return
     */
    public File getTheFile() {
		return theFile;
	}

    /**
     * 
     * @param theFile
     */
    public void setTheFile(File theFile) {
		this.theFile = theFile;
	}

    /**
     * 
     * @return
     */
    public String getTheFileContentType() {
		return theFileContentType;
	}

    /**
     * 
     * @param theFileContentType
     */
    public void setTheFileContentType(String theFileContentType) {
		this.theFileContentType = theFileContentType;
	}

    /**
     * 
     * @return
     */
    public String getTheFileFileName() {
		return theFileFileName;
	}

    /**
     * 
     * @param theFileFileName
     */
    public void setTheFileFileName(String theFileFileName) {
		this.theFileFileName = theFileFileName;
	}

    /**
     * 
     * @return
     */
    public String getIdMaintainedBy() {
		return idMaintainedBy;
	}

    /**
     * 
     * @param idMaintainedBy
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
		this.idMaintainedBy = idMaintainedBy;
	}

    /**
     * 
     * @return
     */
    public String getIdMaintainedSpv() {
		return idMaintainedSpv;
	}

    /**
     * 
     * @param idMaintainedSpv
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
		this.idMaintainedSpv = idMaintainedSpv;
	}

	/**
	 * @return the cdBranch
	 */
	public String getCdBranch() {
		return cdBranch;
	}

	/**
	 * @param cdBranch the cdBranch to set
	 */
	public void setCdBranch(String cdBranch) {
		this.cdBranch = cdBranch;
	}

	/**
	 * @return the acctSearch
	 */
	public String getAcctSearch() {
		return acctSearch;
	}

	/**
	 * @param acctSearch the acctSearch to set
	 */
	public void setAcctSearch(String acctSearch) {
		this.acctSearch = acctSearch;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
}
