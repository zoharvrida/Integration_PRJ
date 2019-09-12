package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpMpComp extends bdsm.model.BaseModel {
    private TmpMpPK compositeId;
    private Integer codComp;
    private String namComp;
    private String typComp;
    private String typData;
    private String txtNarrative;
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
    public Integer getCodComp() {
        return codComp;
    }
    /**
     * 
     * @param codComp
     */
    public void setCodComp(Integer codComp) {
        this.codComp = codComp;
    }

    /**
     * 
     * @return
     */
    public String getNamComp() {
        return namComp;
    }
    /**
     * 
     * @param namComp
     */
    public void setNamComp(String namComp) {
        this.namComp = namComp;
    }

    /**
     * 
     * @return
     */
    public String getTypComp() {
        return typComp;
    }
    /**
     * 
     * @param typComp
     */
    public void setTypComp(String typComp) {
        this.typComp = typComp;
    }

    /**
     * 
     * @return
     */
    public String getTypData() {
        return typData;
    }
    /**
     * 
     * @param typData
     */
    public void setTypData(String typData) {
        this.typData = typData;
    }

    /**
     * 
     * @return
     */
    public String getTxtNarrative() {
        return txtNarrative;
    }
    /**
     * 
     * @param txtNarrative
     */
    public void setTxtNarrative(String txtNarrative) {
        this.txtNarrative = txtNarrative;
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
