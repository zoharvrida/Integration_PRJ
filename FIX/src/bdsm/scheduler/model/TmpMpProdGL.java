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
    public Integer getCodBranch() {
        return codBranch;
    }
    /**
     * 
     * @param codBranch
     */
    public void setCodBranch(Integer codBranch) {
        this.codBranch = codBranch;
    }

    /**
     * 
     * @return
     */
    public Integer getCodLob() {
        return codLob;
    }
    /**
     * 
     * @param codLob
     */
    public void setCodLob(Integer codLob) {
        this.codLob = codLob;
    }

    /**
     * 
     * @return
     */
    public String getCodGl() {
        return codGl;
    }
    /**
     * 
     * @param codGl
     */
    public void setCodGl(String codGl) {
        this.codGl = codGl;
    }

    /**
     * 
     * @return
     */
    public String getGlProduct() {
        return glProduct;
    }
    /**
     * 
     * @param glProduct
     */
    public void setGlProduct(String glProduct) {
        this.glProduct = glProduct;
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
