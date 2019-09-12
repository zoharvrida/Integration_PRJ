/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.model;

import java.io.File;

/**
 *
 * @author v00019237
 */
public class FileUpload extends BaseModel{
    private String fileName;
    private String filePath;
    private File theFile;
    private String theFileString;
	private String idMaintainedBy;
	private String sizeStatus;

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
     * @return the theFileString
     */
    public String getTheFileString() {
        return theFileString;
    }

    /**
     * @param theFileString the theFileString to set
     */
    public void setTheFileString(String theFileString) {
        this.theFileString = theFileString;
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
	 * @return the sizeStatus
     */
	public String getSizeStatus() {
		return sizeStatus;
    }

    /**
	 * @param sizeStatus the sizeStatus to set
     */
	public void setSizeStatus(String sizeStatus) {
		this.sizeStatus = sizeStatus;
    }


}
