/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020800
 */
public class TmpStRoutingNoDtls extends bdsm.model.BaseModel implements java.io.Serializable {

    private TmpStRoutingNoDtlsPK compositeId;
    private Integer codRoutingNo;
    private Integer codBiOffCode;
    private Integer codSector;
    private Integer codCity;
    private Integer codProvince;
    private Integer codBankType;
    private String flgMntStatus;
    private Integer ctrUpdatSrlNo;
    private Integer codEntityVpd;
    private String flgstatus;
    private String statusReason;
    private String cmd;
    private String codLastMntMakerid;

    /**
     * @return the codRoutingNo
     */
    public Integer getCodRoutingNo() {
        return codRoutingNo;
    }

    /**
     * @param codRoutingNo the codRoutingNo to set
     */
    public void setCodRoutingNo(Integer codRoutingNo) {
        this.codRoutingNo = codRoutingNo;
    }

    /**
     * @return the codBiOffCode
     */
    public Integer getCodBiOffCode() {
        return codBiOffCode;
    }

    /**
     * @param codBiOffCode the codBiOffCode to set
     */
    public void setCodBiOffCode(Integer codBiOffCode) {
        this.codBiOffCode = codBiOffCode;
    }

    /**
     * @return the codSector
     */
    public Integer getCodSector() {
        return codSector;
    }

    /**
     * @param codSector the codSector to set
     */
    public void setCodSector(Integer codSector) {
        this.codSector = codSector;
    }

    /**
     * @return the codCity
     */
    public Integer getCodCity() {
        return codCity;
    }

    /**
     * @param codCity the codCity to set
     */
    public void setCodCity(Integer codCity) {
        this.codCity = codCity;
    }

    /**
     * @return the codProvince
     */
    public Integer getCodProvince() {
        return codProvince;
    }

    /**
     * @param codProvince the codProvince to set
     */
    public void setCodProvince(Integer codProvince) {
        this.codProvince = codProvince;
    }

    /**
     * @return the codBankType
     */
    public Integer getCodBankType() {
        return codBankType;
    }

    /**
     * @param codBankType the codBankType to set
     */
    public void setCodBankType(Integer codBankType) {
        this.codBankType = codBankType;
    }

    /**
     * @return the flgMntStatus
     */
    public String getFlgMntStatus() {
        return flgMntStatus;
    }

    /**
     * @param flgMntStatus the flgMntStatus to set
     */
    public void setFlgMntStatus(String flgMntStatus) {
        this.flgMntStatus = flgMntStatus;
    }

    /**
     * @return the DatLastMnt
     */
    /**
     * @return the codEntityVpd
     */
    public Integer getCodEntityVpd() {
        return codEntityVpd;
    }

    /**
     * @param codEntityVpd the codEntityVpd to set
     */
    public void setCodEntityVpd(Integer codEntityVpd) {
        this.codEntityVpd = codEntityVpd;
    }

    /**
     * @return the ctrUpdatSrlNo
     */
    public Integer getCtrUpdatSrlNo() {
        return ctrUpdatSrlNo;
    }

    /**
     * @param ctrUpdatSrlNo the ctrUpdatSrlNo to set
     */
    public void setCtrUpdatSrlNo(Integer ctrUpdatSrlNo) {
        this.ctrUpdatSrlNo = ctrUpdatSrlNo;
    }

    /**
     * @return the compositeId
     */
    public TmpStRoutingNoDtlsPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(TmpStRoutingNoDtlsPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the flgstatus
     */
    public String getFlgstatus() {
        return flgstatus;
    }

    /**
     * @param flgstatus the flgstatus to set
     */
    public void setFlgstatus(String flgstatus) {
        this.flgstatus = flgstatus;
    }

    /**
     * @return the statusReason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the codLastMntMakerid
     */
    public String getCodLastMntMakerid() {
        return codLastMntMakerid;
    }

    /**
     * @param codLastMntMakerid the codLastMntMakerid to set
     */
    public void setCodLastMntMakerid(String codLastMntMakerid) {
        this.codLastMntMakerid = codLastMntMakerid;
    }
}
