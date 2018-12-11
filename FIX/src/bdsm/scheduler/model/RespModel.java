/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author 00110310
 */
public class RespModel {
    private Integer orderNo;
    private Integer dependNo;
    private String typRecord;

    /**
     * @return the dependNo
     */
    public Integer getDependNo() {
        return dependNo;
    }

    /**
     * @param dependNo the dependNo to set
     */
    public void setDependNo(Integer dependNo) {
        this.dependNo = dependNo;
    }

    /**
     * @return the typRecord
     */
    public String getTypRecord() {
        return typRecord;
    }

    /**
     * @param typRecord the typRecord to set
     */
    public void setTypRecord(String typRecord) {
        this.typRecord = typRecord;
    }

    /**
     * @return the orderNo
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
