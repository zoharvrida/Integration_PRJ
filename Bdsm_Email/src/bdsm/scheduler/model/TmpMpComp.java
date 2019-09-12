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


    public TmpMpPK getCompositeId() {
        return compositeId;
    }
    public void setCompositeId(TmpMpPK compositeId) {
        this.compositeId = compositeId;
    }

    public Integer getCodComp() {
        return codComp;
    }
    public void setCodComp(Integer codComp) {
        this.codComp = codComp;
    }

    public String getNamComp() {
        return namComp;
    }
    public void setNamComp(String namComp) {
        this.namComp = namComp;
    }

    public String getTypComp() {
        return typComp;
    }
    public void setTypComp(String typComp) {
        this.typComp = typComp;
    }

    public String getTypData() {
        return typData;
    }
    public void setTypData(String typData) {
        this.typData = typData;
    }

    public String getTxtNarrative() {
        return txtNarrative;
    }
    public void setTxtNarrative(String txtNarrative) {
        this.txtNarrative = txtNarrative;
    }

    public String getFlagStatus() {
        return flagStatus;
    }
    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    public String getStatusReason() {
        return statusReason;
    }
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getCmd() {
        return cmd;
    }
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    
}
