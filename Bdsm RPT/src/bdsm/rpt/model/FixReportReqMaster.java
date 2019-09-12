/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.model;

import bdsm.model.BaseModel;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author v00019722
 */
public class FixReportReqMaster extends BaseModel {
    private FixReportReqMasterPK compositeId;
    private FixStatus tempStat;
    private Integer idReport;
    private String status;
    private String idRequest;
    private String filePath;
    private Timestamp dtmRequest;
    private Timestamp dtmFinish;
	private Integer qid;
    /**
     * @return the compositeId
     */
    public FixReportReqMasterPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FixReportReqMasterPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the idReport
     */
    public Integer getIdReport() {
        return idReport;
    }

    /**
     * @param idReport the idReport to set
     */
    public void setIdReport(Integer idReport) {
        this.idReport = idReport;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the idRequest
     */
    public String getIdRequest() {
        return idRequest;
    }

    /**
     * @param idRequest the idRequest to set
     */
    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
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
     * @return the dtmRequest
     */
    public Timestamp getDtmRequest() {
        return dtmRequest;
    }

    /**
     * @param dtmRequest the dtmRequest to set
     */
    public void setDtmRequest(Timestamp dtmRequest) {
        this.dtmRequest = dtmRequest;
    }

    /**
     * @return the dtmFinish
     */
    public Timestamp getDtmFinish() {
        return dtmFinish;
    }

    /**
     * @param dtmFinish the dtmFinish to set
     */
    public void setDtmFinish(Timestamp dtmFinish) {
        this.dtmFinish = dtmFinish;
    }

    /**
     * @return the tempStat
     */
    public FixStatus getTempStat() {
        return tempStat;
    }

    /**
     * @param tempStat the tempStat to set
     */
    public void setTempStat(FixStatus tempStat) {
        this.tempStat = tempStat;
    }

    /**
     * @return the qid
     */
    public Integer getQid() {
		return qid;
    }

    /**
     * @param qid the qid to set
     */
    public void setQid(Integer qid) {
		this.qid = qid;
    }
}
