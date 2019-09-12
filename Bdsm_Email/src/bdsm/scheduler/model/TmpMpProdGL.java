package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpMpProdGL extends bdsm.model.BaseModel {
    private TmpMpPK compositeId;
    private Integer codProd;
    private Integer codComp;
    private Integer codBranch;
    private Integer codLob;
    private String codGl;
    private String glProduct;
    private String flagStatus;
    private String statusReason;
    private String cmd;


    public TmpMpPK getCompositeId() {
        return compositeId;
    }
    public void setCompositeId(TmpMpPK compositeId) {
        this.compositeId = compositeId;
    }

    public Integer getCodProd() {
        return codProd;
    }
    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    public Integer getCodComp() {
        return codComp;
    }
    public void setCodComp(Integer codComp) {
        this.codComp = codComp;
    }

    public Integer getCodBranch() {
        return codBranch;
    }
    public void setCodBranch(Integer codBranch) {
        this.codBranch = codBranch;
    }

    public Integer getCodLob() {
        return codLob;
    }
    public void setCodLob(Integer codLob) {
        this.codLob = codLob;
    }

    public String getCodGl() {
        return codGl;
    }
    public void setCodGl(String codGl) {
        this.codGl = codGl;
    }

    public String getGlProduct() {
        return glProduct;
    }
    public void setGlProduct(String glProduct) {
        this.glProduct = glProduct;
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
