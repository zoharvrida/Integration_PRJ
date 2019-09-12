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


    public TmpMpPK getCompositeId() {
        return compositeId;
    }
    public void setCompositeId(TmpMpPK compositeId) {
        this.compositeId = compositeId;
    }

    public Integer getCodClass() {
        return codClass;
    }
    public void setCodClass(Integer codClass) {
        this.codClass = codClass;
    }

    public Integer getCodProd() {
        return codProd;
    }
    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    public String getCodMis() {
        return codMis;
    }
    public void setCodMis(String codMis) {
        this.codMis = codMis;
    }

    public String getNamClass() {
        return namClass;
    }
    public void setNamClass(String namClass) {
        this.namClass = namClass;
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
