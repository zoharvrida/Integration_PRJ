/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00018192
 */
public class AmtHldDetails extends BaseModel {
    private AmtHldKey compositeId;
    private int rectype;
    private String dattxn;
    private String codacctno;
    private int codccbrn;
    private String amthold;
    private String holddesc;
    private int earmarktype;
    private int earmarkreason;
    private String datexpire;
    private String recordtype;
    private String recordname;
    private String data;
    private int length;
    private String comments;
    private String recordstatus;
    private int parentrecid;

    /**
     * @return the compositeId
     */
    public AmtHldKey getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(AmtHldKey compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the rectype
     */
    public int getRectype() {
        return rectype;
    }

    /**
     * @param rectype the rectype to set
     */
    public void setRectype(int rectype) {
        this.rectype = rectype;
    }

    /**
     * @return the dattxn
     */
    public String getDattxn() {
        return dattxn;
    }

    /**
     * @param dattxn the dattxn to set
     */
    public void setDattxn(String dattxn) {
        this.dattxn = dattxn;
    }

    /**
     * @return the codacctno
     */
    public String getCodacctno() {
        return codacctno;
    }

    /**
     * @param codacctno the codacctno to set
     */
    public void setCodacctno(String codacctno) {
        this.codacctno = codacctno;
    }

    /**
     * @return the codccbrn
     */
    public int getCodccbrn() {
        return codccbrn;
    }

    /**
     * @param codccbrn the codccbrn to set
     */
    public void setCodccbrn(int codccbrn) {
        this.codccbrn = codccbrn;
    }

    /**
     * @return the amthold
     */
    public String getAmthold() {
        return amthold;
    }

    /**
     * @param amthold the amthold to set
     */
    public void setAmthold(String amthold) {
        this.amthold = amthold;
    }

    /**
     * @return the holddesc
     */
    public String getHolddesc() {
        return holddesc;
    }

    /**
     * @param holddesc the holddesc to set
     */
    public void setHolddesc(String holddesc) {
        this.holddesc = holddesc;
    }

    /**
     * @return the earmarktype
     */
    public int getEarmarktype() {
        return earmarktype;
    }

    /**
     * @param earmarktype the earmarktype to set
     */
    public void setEarmarktype(int earmarktype) {
        this.earmarktype = earmarktype;
    }

    /**
     * @return the earmarkreason
     */
    public int getEarmarkreason() {
        return earmarkreason;
    }

    /**
     * @param earmarkreason the earmarkreason to set
     */
    public void setEarmarkreason(int earmarkreason) {
        this.earmarkreason = earmarkreason;
    }

    /**
     * @return the datexpire
     */
    public String getDatexpire() {
        return datexpire;
    }

    /**
     * @param datexpire the datexpire to set
     */
    public void setDatexpire(String datexpire) {
        this.datexpire = datexpire;
    }

    /**
     * @return the recordtype
     */
    public String getRecordtype() {
        return recordtype;
    }

    /**
     * @param recordtype the recordtype to set
     */
    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    /**
     * @return the recordname
     */
    public String getRecordname() {
        return recordname;
    }

    /**
     * @param recordname the recordname to set
     */
    public void setRecordname(String recordname) {
        this.recordname = recordname;
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
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the recordstatus
     */
    public String getRecordstatus() {
        return recordstatus;
    }

    /**
     * @param recordstatus the recordstatus to set
     */
    public void setRecordstatus(String recordstatus) {
        this.recordstatus = recordstatus;
    }

    /**
     * @return the parentrecid
     */
    public int getParentrecid() {
        return parentrecid;
    }

    /**
     * @param parentrecid the parentrecid to set
     */
    public void setParentrecid(int parentrecid) {
        this.parentrecid = parentrecid;
    }

}
