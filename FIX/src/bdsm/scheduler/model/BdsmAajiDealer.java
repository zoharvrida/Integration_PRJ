/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author NCBS
 */
public class BdsmAajiDealer extends BaseModel{
    private String Status;
    private String Batch;
    private String CodAcctNo;
    private String CodFieldTag;
    private String CodTask;
    private String FieldValue;
    private String Cmd;
    private int Id;

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * @return the Batch
     */
    public String getBatch() {
        return Batch;
    }

    /**
     * @param Batch the Batch to set
     */
    public void setBatch(String Batch) {
        this.Batch = Batch;
    }

    /**
     * @return the CodAcctNo
     */
    public String getCodAcctNo() {
        return CodAcctNo;
    }

    /**
     * @param CodAcctNo the CodAcctNo to set
     */
    public void setCodAcctNo(String CodAcctNo) {
        this.CodAcctNo = CodAcctNo;
    }

    /**
     * @return the CodFieldTag
     */
    public String getCodFieldTag() {
        return CodFieldTag;
    }

    /**
     * @param CodFieldTag the CodFieldTag to set
     */
    public void setCodFieldTag(String CodFieldTag) {
        this.CodFieldTag = CodFieldTag;
    }

    /**
     * @return the CodTask
     */
    public String getCodTask() {
        return CodTask;
    }

    /**
     * @param CodTask the CodTask to set
     */
    public void setCodTask(String CodTask) {
        this.CodTask = CodTask;
    }

    /**
     * @return the FieldValue
     */
    public String getFieldValue() {
        return FieldValue;
    }

    /**
     * @param FieldValue the FieldValue to set
     */
    public void setFieldValue(String FieldValue) {
        this.FieldValue = FieldValue;
    }

    /**
     * @return the Cmd
     */
    public String getCmd() {
        return Cmd;
    }

    /**
     * @param Cmd the Cmd to set
     */
    public void setCmd(String Cmd) {
        this.Cmd = Cmd;
    }

    /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }
    
}
