package bdsm.web.menu23402;

import bdsm.util.BdsmUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import bdsm.web.UploadContentAction;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author bdsm
 */
@SuppressWarnings({"serial", "restriction", "unchecked"})
public class Menu23402Action extends BaseContentAction {

	private static final long serialVersionUID = 1L;
	private static final String FILE_PROFILE = "SKNCNF";
	private static final String NAM_MENU = "SKN Incoming Kredit Input";
	private String cdBranch;
	private File theFile;
	private String theFileContentType;
	private String theFileFileName;
	private String idMaintainedBy;
	private String idMaintainedSpv;
	private String realName;
	
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
		StringBuilder linkBatch = new StringBuilder();
		Date bdt = (Date) session.get(Constant.C_DATEBUSINESS);
		String stDate = DateUtility.DATE_FORMAT_YYMMDD.format(bdt);
		String stTime = DateUtility.TIME_FORMAT_NO_COLON.format(new Date());

		this.getLogger().info("CD_BRANCH : " + session.get(Constant.C_CODEBRANCH).toString());
		this.getLogger().info("DATETIME : " + stDate);
		linkBatch.append(BdsmUtil.leftPad(session.get(Constant.C_CODEBRANCH).toString(), 5, '0')).append(stDate).append(stTime);
		String batchNo = linkBatch.toString();

		realName = FILE_PROFILE + "_" + batchNo + "_" + idMaintainedBy + "_" + theFileFileName;
			
		UploadContentAction myFramework = new UploadContentAction(theFileFileName,
				session, FILE_PROFILE, idMaintainedBy, idMaintainedSpv,
				NAM_MENU, theFile, realName, batchNo, getTokenKey(), getTzToken(), getBdsmHost());
					String Notif = batchNo.substring(batchNo.length() - 6, batchNo.length());
		List status = myFramework.startUpload();
		if (!status.isEmpty()) {
			if (status.get(0).toString().equalsIgnoreCase(UploadContentAction.ERROR)) {
				addActionError(status.get(1).toString() + Notif);
			} else {
				addActionMessage(status.get(1).toString() + Notif);
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
		// TODO Auto-generated method stub
		return null;
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
}
