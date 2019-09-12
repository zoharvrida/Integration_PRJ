/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu23401;

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
 * @author v00019372
 */
@SuppressWarnings({"serial", "restriction", "unchecked"})
public class Menu23401Action extends BaseContentAction {

	private static final String FILE_PROFILE = "SKNIND";
	private static final String MODULE_NAME = "SKNNGINCDB";
	private static final String NAM_MENU = "SKN Inward Debit Input";
	private File theFile;
	private String theFileContentType;
	private String theFileFileName;
	private String idMaintainedBy;
	private String idMaintainedSpv;
		
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
		Date bdt = (Date) this.session.get(Constant.C_DATEBUSINESS);
		String stDate = DateUtility.DATE_FORMAT_YYYYMMDD.format(bdt);
		String stTime = DateUtility.TIME_FORMAT_NO_COLON.format(new Date());
				
		this.getLogger().info("CD_BRANCH : " + this.session.get(Constant.C_CODEBRANCH).toString());
		String batchNo = BdsmUtil.leftPad(this.session.get(Constant.C_CODEBRANCH).toString(), 5, '0') + "-" + stDate + "-" + stTime + "-" + MODULE_NAME;

		String realName = FILE_PROFILE + "_" + batchNo + "_" + this.session.get(Constant.C_IDUSER).toString() + "_" + theFileFileName;
		UploadContentAction myFramework = new UploadContentAction(theFileFileName,
				session, FILE_PROFILE, idMaintainedBy, idMaintainedSpv,
				NAM_MENU, theFile, realName, batchNo, getTokenKey(), getTzToken(), getBdsmHost());
		List status = myFramework.startUpload();
		if (!status.isEmpty()) {
			if (status.get(0).toString().equalsIgnoreCase(UploadContentAction.ERROR)) {
				addActionError(status.get(1).toString());
			} else {
				addActionMessage(status.get(1).toString() + " " + stTime);
			}
			}
		return SUCCESS;
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
     * 
     * @return
     */
    public File getTheFile() {
		return this.theFile;
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
		return this.theFileContentType;
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
		return this.theFileFileName;
	}
    /**
     * 
     * @param theFileFileName
     */
    public void setTheFileFileName(String theFileFileName) {
		this.theFileFileName = theFileFileName;
	}
}
