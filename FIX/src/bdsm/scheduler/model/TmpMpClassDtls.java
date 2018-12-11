package bdsm.scheduler.model;

import java.math.BigDecimal;

/**
 *
 * @author v00020841
 */
public class TmpMpClassDtls extends bdsm.model.BaseModel{
    private TmpMpPK compositeId;
    private Integer codClass;
    private Integer codComp;
    private BigDecimal minVal;
    private BigDecimal maxVal;
    private BigDecimal defVal;
    private String minimal;
    private String maximal;
    private String defaultVal;
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
    public BigDecimal getMinVal() {
        return minVal;
    }
    /**
     * 
     * @param minVal
     */
    public void setMinVal(BigDecimal minVal) {
        this.minVal = minVal;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getMaxVal() {
        return maxVal;
    }
    /**
     * 
     * @param maxVal
     */
    public void setMaxVal(BigDecimal maxVal) {
        this.maxVal = maxVal;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getDefVal() {
        return defVal;
    }
    /**
     * 
     * @param defVal
     */
    public void setDefVal(BigDecimal defVal) {
        this.defVal = defVal;
    }

    /**
     * 
     * @return
     */
    public String getMinimal() {
        return minimal;
    }
    /**
     * 
     * @param minimal
     */
    public void setMinimal(String minimal) {
        this.minimal = minimal;
    }

    /**
     * 
     * @return
     */
    public String getMaximal() {
        return maximal;
    }
    /**
     * 
     * @param maximal
     */
    public void setMaximal(String maximal) {
        this.maximal = maximal;
    }

    /**
     * 
     * @return
     */
    public String getDefaultVal() {
        return defaultVal;
    }
    /**
     * 
     * @param defaultVal
     */
    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
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
