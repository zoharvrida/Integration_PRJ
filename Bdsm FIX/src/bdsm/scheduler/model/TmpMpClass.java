package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpMpClass extends bdsm.model.BaseModel {
    private TmpMpPK compositeId;
    private Integer codClass;
    private Integer codProd;
    private String codMis;
    private String namClass;
    private String flagStatus;
    private String statusReason;
    private String cmd;


    /**
     * 
     * @return
     */
    public TmpMpPK getCompositeId() {
        return compositeId;
    }
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpMpPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * 
     * @return
     */
    public Integer getCodClass() {
        return codClass;
    }
    /**
     * 
     * @param codClass
     */
    public void setCodClass(Integer codClass) {
        this.codClass = codClass;
    }

    /**
     * 
     * @return
     */
    public Integer getCodProd() {
        return codProd;
    }
    /**
     * 
     * @param codProd
     */
    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    /**
     * 
     * @return
     */
    public String getCodMis() {
        return codMis;
    }
    /**
     * 
     * @param codMis
     */
    public void setCodMis(String codMis) {
        this.codMis = codMis;
    }

    /**
     * 
     * @return
     */
    public String getNamClass() {
        return namClass;
    }
    /**
     * 
     * @param namClass
     */
    public void setNamClass(String namClass) {
        this.namClass = namClass;
    }

    /**
     * 
     * @return
     */
    public String getFlagStatus() {
        return flagStatus;
    }
    /**
     * 
     * @param flagStatus
     */
    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    /**
     * 
     * @return
     */
    public String getStatusReason() {
        return statusReason;
    }
    /**
     * 
     * @param statusReason
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    /**
     * 
     * @return
     */
    public String getCmd() {
        return cmd;
    }
    /**
     * 
     * @param cmd
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    
}
