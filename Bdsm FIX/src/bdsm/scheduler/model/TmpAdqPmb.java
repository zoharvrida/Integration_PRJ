/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author NCBS
 */
public class TmpAdqPmb extends BaseModel {

    private int id;
    private String batchNo;
    private String rowFlag;
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String flgStatus;
    private String checksum;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the rowFlag
     */
    public String getRowFlag() {
        return rowFlag;
    }

    /**
     * @param rowFlag the rowFlag to set
     */
    public void setRowFlag(String rowFlag) {
        this.rowFlag = rowFlag;
    }

    /**
     * @return the col1
     */
    public String getCol1() {
        return col1;
    }

    /**
     * @param col1 the col1 to set
     */
    public void setCol1(String col1) {
        this.col1 = StringUtils.leftPad(col1.trim(), 12, "0");
    }

    /**
     * @return the col2
     */
    public String getCol2() {
        return col2;
    }

    /**
     * @param col2 the col2 to set
     */
    public void setCol2(String col2) {
        this.col2 = col2;
    }

    /**
     * @return the col3
     */
    public String getCol3() {
        return col3;
    }

    /**
     * @param col3 the col3 to set
     */
    public void setCol3(String col3) {
        this.col3 = col3;
    }

    /**
     * @return the col4
     */
    public String getCol4() {
        return col4;
    }

    /**
     * @param col4 the col4 to set
     */
    public void setCol4(String col4) {
        this.col4 = col4;
    }

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
     * @return the checksum
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * @param checksum the checksum to set
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

}
