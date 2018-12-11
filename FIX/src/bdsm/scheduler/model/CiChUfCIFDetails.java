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
public class CiChUfCIFDetails extends BaseModel {
    private Integer record_id;
    private Integer record_id_details;
    private String rec_type;
    private Integer cod_cust;
    private String agama;
    private String txn_Value;
    private String sumber_Dana_Lain;
    private String fax_No;
    private String nama_Spouse_Ortu;
    private String pekerjaan_Spouse_Ortu;
    private String kantor_Spouse_Ortu;
    private Integer respcode;
    private String comment_resp;
    private String status;
    private Timestamp dtmRequest;
    private Timestamp dtmProcess;
    private Timestamp dtmFinish;
    private String createdBy;
    private String updatedBy;
    private String id_Batch;
    private String jabatan_Spouse_Ortu;
    //private String fileName;
    private String home_No_Phone;
    private String home_No_Area;
    private String home_No_Country;
    private String office_No_Phone;
    private String office_No_Area;
    private String office_No_Country;
    private String ext_Office_No;
    private String nama_ibu_kandung;
    private String nama_alias;
    private String e_Statement_Email;
    private String e_Statement_Branch;
    private String data;

    /**
     * @return the cod_cust
     */
    public Integer getCod_cust() {
        return cod_cust;
    }

    /**
     * @param cod_cust the cod_cust to set
     */
    public void setCod_cust(Integer cod_cust) {
        this.cod_cust = cod_cust;
    }

    /**
     * @return the agama
     */
    public String getAgama() {
        return agama;
    }

    /**
     * @param agama the agama to set
     */
    public void setAgama(String agama) {
        this.agama = agama;
    }

    /**
     * @return the txn_Value
     */
    public String getTxn_Value() {
        return txn_Value;
    }

    /**
     * @param txn_Value the txn_Value to set
     */
    public void setTxn_Value(String txn_Value) {
        this.txn_Value = txn_Value;
    }

    /**
     * @return the sumber_Dana_Lain
     */
    public String getSumber_Dana_Lain() {
        return sumber_Dana_Lain;
    }

    /**
     * @param sumber_Dana_Lain the sumber_Dana_Lain to set
     */
    public void setSumber_Dana_Lain(String sumber_Dana_Lain) {
        this.sumber_Dana_Lain = sumber_Dana_Lain;
    }

    /**
     * @return the fax_No
     */
    public String getFax_No() {
        return fax_No;
    }

    /**
     * @param fax_No the fax_No to set
     */
    public void setFax_No(String fax_No) {
        this.fax_No = fax_No;
    }

    /**
     * @return the nama_Spouse_Ortu
     */
    public String getNama_Spouse_Ortu() {
        return nama_Spouse_Ortu;
    }

    /**
     * @param nama_Spouse_Ortu the nama_Spouse_Ortu to set
     */
    public void setNama_Spouse_Ortu(String nama_Spouse_Ortu) {
        this.nama_Spouse_Ortu = nama_Spouse_Ortu;
    }

    /**
     * @return the pekerjaan_Spouse_Ortu
     */
    public String getPekerjaan_Spouse_Ortu() {
        return pekerjaan_Spouse_Ortu;
    }

    /**
     * @param pekerjaan_Spouse_Ortu the pekerjaan_Spouse_Ortu to set
     */
    public void setPekerjaan_Spouse_Ortu(String pekerjaan_Spouse_Ortu) {
        this.pekerjaan_Spouse_Ortu = pekerjaan_Spouse_Ortu;
    }

    /**
     * @return the kantor_Spouse_Ortu
     */
    public String getKantor_Spouse_Ortu() {
        return kantor_Spouse_Ortu;
    }

    /**
     * @param kantor_Spouse_Ortu the kantor_Spouse_Ortu to set
     */
    public void setKantor_Spouse_Ortu(String kantor_Spouse_Ortu) {
        this.kantor_Spouse_Ortu = kantor_Spouse_Ortu;
    }

    /**
     * @return the ext_Office_No
     */
    public String getExt_Office_No() {
        return ext_Office_No;
    }

    /**
     * @param ext_Office_No the ext_Office_No to set
     */
    public void setExt_Office_No(String ext_Office_No) {
        this.ext_Office_No = ext_Office_No;
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
     * @return the jabatan_Spouse_Ortu
     */
    public String getJabatan_Spouse_Ortu() {
        return jabatan_Spouse_Ortu;
    }

    /**
     * @param jabatan_Spouse_Ortu the jabatan_Spouse_Ortu to set
     */
    public void setJabatan_Spouse_Ortu(String jabatan_Spouse_Ortu) {
        this.jabatan_Spouse_Ortu = jabatan_Spouse_Ortu;
    }

    /**
     * @return the office_No_Phone
     */
    public String getOffice_No_Phone() {
        return office_No_Phone;
    }

    /**
     * @param office_No_Phone the office_No_Phone to set
     */
    public void setOffice_No_Phone(String office_No_Phone) {
        this.office_No_Phone = office_No_Phone;
    }

    /**
     * @return the office_No_Area
     */
    public String getOffice_No_Area() {
        return office_No_Area;
    }

    /**
     * @param office_No_Area the office_No_Area to set
     */
    public void setOffice_No_Area(String office_No_Area) {
        this.office_No_Area = office_No_Area;
    }

    /**
     * @return the office_No_Country
     */
    public String getOffice_No_Country() {
        return office_No_Country;
    }

    /**
     * @param office_No_Country the office_No_Country to set
     */
    public void setOffice_No_Country(String office_No_Country) {
        this.office_No_Country = office_No_Country;
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
     * @return the nama_ibu_kandung
     */
    public String getNama_ibu_kandung() {
        return nama_ibu_kandung;
    }

    /**
     * @param nama_ibu_kandung the nama_ibu_kandung to set
     */
    public void setNama_ibu_kandung(String nama_ibu_kandung) {
        this.nama_ibu_kandung = nama_ibu_kandung;
    }

    /**
     * @return the nama_alias
     */
    public String getNama_alias() {
        return nama_alias;
    }

    /**
     * @param nama_alias the nama_alias to set
     */
    public void setNama_alias(String nama_alias) {
        this.nama_alias = nama_alias;
    }

    /**
     * @return the e_Statement_Email
     */
    public String getE_Statement_Email() {
        return e_Statement_Email;
    }

    /**
     * @param e_Statement_Email the e_Statement_Email to set
     */
    public void setE_Statement_Email(String e_Statement_Email) {
        this.e_Statement_Email = e_Statement_Email;
    }

    /**
     * @return the e_Statement_Branch
     */
    public String getE_Statement_Branch() {
        return e_Statement_Branch;
    }

    /**
     * @param e_Statement_Branch the e_Statement_Branch to set
     */
    public void setE_Statement_Branch(String e_Statement_Branch) {
        this.e_Statement_Branch = e_Statement_Branch;
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
     * @return the record_id_details
     */
    public Integer getRecord_id_details() {
        return record_id_details;
    }

    /**
     * @param record_id_details the record_id_details to set
     */
    public void setRecord_id_details(Integer record_id_details) {
        this.record_id_details = record_id_details;
    }

    /**
     * @return the rec_type
     */
    public String getRec_type() {
        return rec_type;
    }

    /**
     * @param rec_type the rec_type to set
     */
    public void setRec_type(String rec_type) {
        this.rec_type = rec_type;
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
     * @return the home_No_Phone
     */
    public String getHome_No_Phone() {
        return home_No_Phone;
    }

    /**
     * @param home_No_Phone the home_No_Phone to set
     */
    public void setHome_No_Phone(String home_No_Phone) {
        this.home_No_Phone = home_No_Phone;
    }

    /**
     * @return the home_No_Area
     */
    public String getHome_No_Area() {
        return home_No_Area;
    }

    /**
     * @param home_No_Area the home_No_Area to set
     */
    public void setHome_No_Area(String home_No_Area) {
        this.home_No_Area = home_No_Area;
    }

    /**
     * @return the home_No_Country
     */
    public String getHome_No_Country() {
        return home_No_Country;
    }

    /**
     * @param home_No_Country the home_No_Country to set
     */
    public void setHome_No_Country(String home_No_Country) {
        this.home_No_Country = home_No_Country;
    }
}
