/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author v00019722
 */
public class CiChUfCASADetails extends BaseModel {
    private Integer record_id;
    private String cod_acct_no;
    private String rec_Type;
    private String tujuan_rek;
    private String sumber_dana_rek;
    private String media_mutasi;
    private String kepemilikan;
    private String email_Statement;
    private String branch_Statement;
    private Integer respcode;
    private String comment_resp;
    private String status;
    private Timestamp dtmRequest;
    private Timestamp dtmProcess;
    private Timestamp dtmFinish;
    private String createdBy;
    private String updatedBy;
    private String id_Batch;
    private String data;
    private Integer id_Data;

    /**
     * @return the cod_acct_no
     */
    public String getCod_acct_no() {
        return cod_acct_no;
    }

    /**
     * @param cod_acct_no the cod_acct_no to set
     */
    public void setCod_acct_no(String cod_acct_no) {
        this.cod_acct_no = cod_acct_no;
    }

    /**
     * @return the tujuan_rek
     */
    public String getTujuan_rek() {
        return tujuan_rek;
    }

    /**
     * @param tujuan_rek the tujuan_rek to set
     */
    public void setTujuan_rek(String tujuan_rek) {
        this.tujuan_rek = tujuan_rek;
    }

    /**
     * @return the sumber_dana_rek
     */
    public String getSumber_dana_rek() {
        return sumber_dana_rek;
    }

    /**
     * @param sumber_dana_rek the sumber_dana_rek to set
     */
    public void setSumber_dana_rek(String sumber_dana_rek) {
        this.sumber_dana_rek = sumber_dana_rek;
    }

    /**
     * @return the media_mutasi
     */
    public String getMedia_mutasi() {
        return media_mutasi;
    }

    /**
     * @param media_mutasi the media_mutasi to set
     */
    public void setMedia_mutasi(String media_mutasi) {
        this.media_mutasi = media_mutasi;
    }

    /**
     * @return the kepemilikan
     */
    public String getKepemilikan() {
        return kepemilikan;
    }

    /**
     * @param kepemilikan the kepemilikan to set
     */
    public void setKepemilikan(String kepemilikan) {
        this.kepemilikan = kepemilikan;
    }

    /**
     * @return the email_Statement
     */
    public String getEmail_Statement() {
        return email_Statement;
    }

    /**
     * @param email_Statement the email_Statement to set
     */
    public void setEmail_Statement(String email_Statement) {
        this.email_Statement = email_Statement;
    }

    /**
     * @return the branch_Statement
     */
    public String getBranch_Statement() {
        return branch_Statement;
    }

    /**
     * @param branch_Statement the branch_Statement to set
     */
    public void setBranch_Statement(String branch_Statement) {
        this.branch_Statement = branch_Statement;
    }

    /**
     * @return the respcode
     */
    public Integer getRespcode() {
        return respcode;
    }

    /**
     * @param respcode the respcode to set
     */
    public void setRespcode(Integer respcode) {
        this.respcode = respcode;
    }

    /**
     * @return the comment_resp
     */
    public String getComment_resp() {
        return comment_resp;
    }

    /**
     * @param comment_resp the comment_resp to set
     */
    public void setComment_resp(String comment_resp) {
        this.comment_resp = comment_resp;
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
     * @return the dtmProcess
     */
    public Timestamp getDtmProcess() {
        return dtmProcess;
    }

    /**
     * @param dtmProcess the dtmProcess to set
     */
    public void setDtmProcess(Timestamp dtmProcess) {
        this.dtmProcess = dtmProcess;
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
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the id_Batch
     */
    public String getId_Batch() {
        return id_Batch;
    }

    /**
     * @param id_Batch the id_Batch to set
     */
    public void setId_Batch(String id_Batch) {
        this.id_Batch = id_Batch;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the rec_Type
     */
    public String getRec_Type() {
        return rec_Type;
    }

    /**
     * @param rec_Type the rec_Type to set
     */
    public void setRec_Type(String rec_Type) {
        this.rec_Type = rec_Type;
    }

    /**
     * @return the record_id
     */
    public Integer getRecord_id() {
        return record_id;
    }

    /**
     * @param record_id the record_id to set
     */
    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    /**
     * @return the id_Data
     */
    public Integer getId_Data() {
        return id_Data;
    }

    /**
     * @param id_Data the id_Data to set
     */
    public void setId_Data(Integer id_Data) {
        this.id_Data = id_Data;
    }
}
