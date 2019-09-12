/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.io.Serializable;

/**
 *
 * @author v00020800
 */
public class TmpPmFinInstDirDtls  extends BaseModel implements Serializable{

    private TmpPmFinInstDirDtlsPK compositeId;

    private String codNetworkId;
    private String flgDirectParticipant;
    private String namBankDirectParticip;
    private String namBranchDirectParticip;
    private String flgDirectAddressable;
    private String codClgDirectAddr;
    private String codClg;
    private String flgIbanMand;
    private String networkDetailsIndex;
    private String flgMntStatus;
    private String codMntAction;
    private String codLastMntMakerid;
    private String codLastMntChkrid;
    private String datLastMnt;
    private Integer ctrUpdatSrlno;
    private Integer codEntityVpd;
    private String flgstatus;
    private String statusReason;
    private String cmd;

    /**
     * @return the codFinInstId
     */
    

    /**
     * @return the codNetworkId
     */
    public String getCodNetworkId() {
        return codNetworkId;
    }

    /**
     * @param codNetworkId the codNetworkId to set
     */
    public void setCodNetworkId(String codNetworkId) {
        this.codNetworkId = codNetworkId;
    }

    /**
     * @return the flgDirectParticipant
     */
    public String getFlgDirectParticipant() {
        return flgDirectParticipant;
    }

    /**
     * @param flgDirectParticipant the flgDirectParticipant to set
     */
    public void setFlgDirectParticipant(String flgDirectParticipant) {
        this.flgDirectParticipant = flgDirectParticipant;
    }

    /**
     * @return the namBankDirectParticip
     */
    public String getNamBankDirectParticip() {
        return namBankDirectParticip;
    }

    /**
     * @param namBankDirectParticip the namBankDirectParticip to set
     */
    public void setNamBankDirectParticip(String namBankDirectParticip) {
        this.namBankDirectParticip = namBankDirectParticip;
    }

    /**
     * @return the namBranchDirectParticip
     */
    public String getNamBranchDirectParticip() {
        return namBranchDirectParticip;
    }

    /**
     * @param namBranchDirectParticip the namBranchDirectParticip to set
     */
    public void setNamBranchDirectParticip(String namBranchDirectParticip) {
        this.namBranchDirectParticip = namBranchDirectParticip;
    }

    /**
     * @return the flgDirectAddressable
     */
    public String getFlgDirectAddressable() {
        return flgDirectAddressable;
    }

    /**
     * @param flgDirectAddressable the flgDirectAddressable to set
     */
    public void setFlgDirectAddressable(String flgDirectAddressable) {
        this.flgDirectAddressable = flgDirectAddressable;
    }

    /**
     * @return the codClgDirectAddr
     */
    public String getCodClgDirectAddr() {
        return codClgDirectAddr;
    }

    /**
     * @param codClgDirectAddr the codClgDirectAddr to set
     */
    public void setCodClgDirectAddr(String codClgDirectAddr) {
        this.codClgDirectAddr = codClgDirectAddr;
    }

    /**
     * @return the codClg
     */
    public String getCodClg() {
        return codClg;
    }

    /**
     * @param codClg the codClg to set
     */
    public void setCodClg(String codClg) {
        this.codClg = codClg;
    }

    /**
     * @return the flgIbanMand
     */
    public String getFlgIbanMand() {
        return flgIbanMand;
    }

    /**
     * @param flgIbanMand the flgIbanMand to set
     */
    public void setFlgIbanMand(String flgIbanMand) {
        this.flgIbanMand = flgIbanMand;
    }

    /**
     * @return the networkDetailsIndex
     */
    public String getNetworkDetailsIndex() {
        return networkDetailsIndex;
    }

    /**
     * @param networkDetailsIndex the networkDetailsIndex to set
     */
    public void setNetworkDetailsIndex(String networkDetailsIndex) {
        this.networkDetailsIndex = networkDetailsIndex;
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
     * @return the codMntAction
     */
    public String getCodMntAction() {
        return codMntAction;
    }

    /**
     * @param codMntAction the codMntAction to set
     */
    public void setCodMntAction(String codMntAction) {
        this.codMntAction = codMntAction;
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

    /**
     * @return the codLastMntChkrid
     */
    public String getCodLastMntChkrid() {
        return codLastMntChkrid;
    }

    /**
     * @param codLastMntChkrid the codLastMntChkrid to set
     */
    public void setCodLastMntChkrid(String codLastMntChkrid) {
        this.codLastMntChkrid = codLastMntChkrid;
    }

    /**
     * @return the datLastMnt
     */
    public String getDatLastMnt() {
        return datLastMnt;
    }

    /**
     * @param datLastMnt the datLastMnt to set
     */
    public void setDatLastMnt(String datLastMnt) {
        this.datLastMnt = datLastMnt;
    }

    /**
     * @return the ctrUpdatSrlno
     */
    public Integer getCtrUpdatSrlno() {
        return ctrUpdatSrlno;
    }

    /**
     * @param ctrUpdatSrlno the ctrUpdatSrlno to set
     */
    public void setCtrUpdatSrlno(Integer ctrUpdatSrlno) {
        this.ctrUpdatSrlno = ctrUpdatSrlno;
    }

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
     * @return the compositeId
     */
    public TmpPmFinInstDirDtlsPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(TmpPmFinInstDirDtlsPK compositeId) {
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
}
