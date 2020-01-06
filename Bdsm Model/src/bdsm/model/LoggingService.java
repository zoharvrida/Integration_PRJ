/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00024535
 */
@SuppressWarnings("serial")
public class LoggingService extends BaseModel {

    private String refNo;
    private String serviceName;
    private String type;
    private int idScheduler;
    private String status;
    private String clob;

    /**
     * @return the refNo
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     * @param refNo the refNo to set
     */
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the idScheduler
     */
    public int getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(int idScheduler) {
        this.idScheduler = idScheduler;
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
     * @return the clob
     */
    public String getClob() {
        return clob;
    }

    /**
     * @param clob the clob to set
     */
    public void setClob(String clob) {
        this.clob = clob;
    }

    @Override
    public String toString() {
        return "LoggingService{" + "refNo=" + refNo + ", serviceName=" + serviceName + ", type=" + type + ", idScheduler=" + idScheduler + ", status=" + status + ", clob=" + clob + '}';
    }
    
    
}
