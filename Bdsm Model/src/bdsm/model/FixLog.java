package bdsm.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

public class FixLog extends BaseModel{
    private FixLogPK fixLogPK;
    private Integer logId;    
    private Integer idScheduler;
    private String grpId;
    private String flgAuth;
    private String flgProcess;
    private String reason;
    private String sender;
    private Timestamp dtmReceive;
    private String senderSpv;
    private Timestamp dtmAuth;
    private Integer fileSize;
    private String fcrFileName;
    private Timestamp dtmStartProcess;
    private Timestamp dtmEndProcess;
    

    public FixLog(){
        this.fixLogPK = new FixLogPK();
    }

    /**
     * @return the grpId
     */
    public String getGrpId() {
        return grpId;
    }

    /**
     * @param grpId the grpId to set
     */
    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    /**
     * @return the flgAuth
     */
    public String getFlgAuth() {
        return flgAuth;
    }

    /**
     * @param flgAuth the flgAuth to set
     */
    public void setFlgAuth(String flgAuth) {
        this.flgAuth = flgAuth;
    }

    /**
     * @return the flgProcess
     */
    public String getFlgProcess() {
        return flgProcess;
    }

    /**
     * @param flgProcess the flgProcess to set
     */
    public void setFlgProcess(String flgProcess) {
        this.flgProcess = flgProcess;
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
        if (reason != null) {
            if (reason.length() > 200) {
                this.reason = reason.substring(0, 200);
            } else {
                this.reason = reason;
            }
        }
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the senderSpv
     */
    public String getSenderSpv() {
        return senderSpv;
    }

    /**
     * @param senderSpv the senderSpv to set
     */
    public void setSenderSpv(String senderSpv) {
        this.senderSpv = senderSpv;
    }

    /**
     * @return the fileSize
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the fcrFileName
     */
    public String getFcrFileName() {
        return fcrFileName;
    }

    /**
     * @param fcrFileName the fcrFileName to set
     */
    public void setFcrFileName(String fcrFileName) {
        this.fcrFileName = fcrFileName;
    }

    /**
     * @return the logId
     */
    public Integer getLogId() {
        return logId;
    }

    /**
     * @param logId the logId to set
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(Integer idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the fixLogPK
     */
    public FixLogPK getFixLogPK() {
        return fixLogPK;
    }

    /**
     * @param fixLogPK the fixLogPK to set
     */
    public void setFixLogPK(FixLogPK fixLogPK) {
        this.fixLogPK = fixLogPK;
    }

    /**
     * @return the dtmReceive
     */
    public Timestamp getDtmReceive() {
        return dtmReceive;
    }

    /**
     * @param dtmReceive the dtmReceive to set
     */
    public void setDtmReceive(Timestamp dtmReceive) {
        this.dtmReceive = dtmReceive;
    }

    /**
     * @return the dtmAuth
     */
    public Timestamp getDtmAuth() {
        return dtmAuth;
    }

    /**
     * @param dtmAuth the dtmAuth to set
     */
    public void setDtmAuth(Timestamp dtmAuth) {
        this.dtmAuth = dtmAuth;
    }

    /**
     * @return the dtmStartProcess
     */
    public Timestamp getDtmStartProcess() {
        return dtmStartProcess;
    }

    /**
     * @param dtmStartProcess the dtmStartProcess to set
     */
    public void setDtmStartProcess(Timestamp dtmStartProcess) {
        this.dtmStartProcess = dtmStartProcess;
    }

    /**
     * @return the dtmEndProcess
     */
    public Timestamp getDtmEndProcess() {
        return dtmEndProcess;
    }

    /**
     * @param dtmEndProcess the dtmEndProcess to set
     */
    public void setDtmEndProcess(Timestamp dtmEndProcess) {
        this.dtmEndProcess = dtmEndProcess;
    }
    
}
