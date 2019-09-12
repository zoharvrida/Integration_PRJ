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

    public Integer getCodComp() {
        return codComp;
    }
    public void setCodComp(Integer codComp) {
        this.codComp = codComp;
    }

    public BigDecimal getMinVal() {
        return minVal;
    }
    public void setMinVal(BigDecimal minVal) {
        this.minVal = minVal;
    }

    public BigDecimal getMaxVal() {
        return maxVal;
    }
    public void setMaxVal(BigDecimal maxVal) {
        this.maxVal = maxVal;
    }

    public BigDecimal getDefVal() {
        return defVal;
    }
    public void setDefVal(BigDecimal defVal) {
        this.defVal = defVal;
    }

    public String getMinimal() {
        return minimal;
    }
    public void setMinimal(String minimal) {
        this.minimal = minimal;
    }

    public String getMaximal() {
        return maximal;
    }
    public void setMaximal(String maximal) {
        this.maximal = maximal;
    }

    public String getDefaultVal() {
        return defaultVal;
    }
    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
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
