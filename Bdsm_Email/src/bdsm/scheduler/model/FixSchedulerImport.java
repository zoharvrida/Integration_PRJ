package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

public class FixSchedulerImport extends BaseModel{
    private FixSchedulerPK fixSchedulerPK;    
    private String flgStatus;
    private Date dtEffStart;
    private Date dtEffEnd;
    private String filePattern;
    private String flgEncrypt;
    private String modDecrypt;
    private String keyDecrypt;
    private String flgFCRPMcutoff;
    private String spvAuth;
    private String flg;
    private String fileExtension;
    
    /**
     * @return the flgStatus
     */
    public String getFlgStatus() {
        return flgStatus;
    }

    /**
     * @param flgStatus the flgStatus to set
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
    }

    /**
     * @return the dtEffStart
     */
    public Date getDtEffStart() {
        return dtEffStart;
    }

    /**
     * @param dtEffStart the dtEffStart to set
     */
    public void setDtEffStart(Date dtEffStart) {
        this.dtEffStart = dtEffStart;
    }

    /**
     * @return the dtEffEnd
     */
    public Date getDtEffEnd() {
        return dtEffEnd;
    }

    /**
     * @param dtEffEnd the dtEffEnd to set
     */
    public void setDtEffEnd(Date dtEffEnd) {
        this.dtEffEnd = dtEffEnd;
    }

    /**
     * @return the filePattern
     */
    public String getFilePattern() {
        return filePattern;
    }

    /**
     * @param filePattern the filePattern to set
     */
    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    /**
     * @return the flgEncrypt
     */
    public String getFlgEncrypt() {
        return flgEncrypt;
    }

    /**
     * @param flgEncrypt the flgEncrypt to set
     */
    public void setFlgEncrypt(String flgEncrypt) {
        this.flgEncrypt = flgEncrypt;
    }

    /**
     * @return the modDecrypt
     */
    public String getModDecrypt() {
        return modDecrypt;
    }

    /**
     * @param modDecrypt the modDecrypt to set
     */
    public void setModDecrypt(String modDecrypt) {
        this.modDecrypt = modDecrypt;
    }

    /**
     * @return the keyDecrypt
     */
    public String getKeyDecrypt() {
        return keyDecrypt;
    }

    /**
     * @param keyDecrypt the keyDecrypt to set
     */
    public void setKeyDecrypt(String keyDecrypt) {
        this.keyDecrypt = keyDecrypt;
    }

    /**
     * @return the flgFCRPMcutpff
     */
    public String getFlgFCRPMcutoff() {
        return flgFCRPMcutoff;
    }

    /**
     * @param flgFCRPMcutpff the flgFCRPMcutpff to set
     */
    public void setFlgFCRPMcutoff(String flgFCRPMcutoff) {
        this.flgFCRPMcutoff = flgFCRPMcutoff;
    }

    /**
     * @return the spvAuth
     */
    public String getSpvAuth() {
        return spvAuth;
    }

    /**
     * @param spvAuth the spvAuth to set
     */
    public void setSpvAuth(String spvAuth) {
        this.spvAuth = spvAuth;
    }

    /**
     * @return the fixSchedulerPK
     */
    public FixSchedulerPK getFixSchedulerPK() {
        return fixSchedulerPK;
    }

    /**
     * @param fixSchedulerPK the fixSchedulerPK to set
     */
    public void setFixSchedulerPK(FixSchedulerPK fixSchedulerPK) {
        this.fixSchedulerPK = fixSchedulerPK;
    }

    /**
     * @return the flg
     */
    public String getFlg() {
        return flg;
    }

    /**
     * @param flg the flg to set
     */
    public void setFlg(String flg) {
        this.flg = flg;
    }

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

}
