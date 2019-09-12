/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.util.Date;
import java.math.BigDecimal;

/**
 *
 * @author v00022309
 */
public class MCRTrxTable extends BaseModel{
    private Integer codOrgBrn;
    private Date datTxn;
    private String idUser;
    private String refNetworkNo;
    private String acctNo;
    private String oriCcy;
    private Integer oriAmt;
    private Integer usdAmt;
    private String desCcy;
    private BigDecimal desAmt;
	private BigDecimal desRet;
	private String codAcctTitle;

    /**
     * @return the refNo
     */
    public String getRefNetworkNo() {
        return refNetworkNo;
    }

    /**
     * @param refNo the refNo to set
     */
    public void setRefNo(String refNetworkNo) {
        this.refNetworkNo = refNetworkNo;
    }

    /**
     * @return the acctNo
     */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * @param acctNo the acctNo to set
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    /**
     * @return the oriCcy
     */
    public String getOriCcy() {
        return oriCcy;
    }

    /**
     * @param oriCcy the oriCcy to set
     */
    public void setOriCcy(String oriCcy) {
        this.oriCcy = oriCcy;
    }

    /**
     * @return the oriAmt
     */
    public Integer getOriAmt() {
        return oriAmt;
    }

    /**
     * @param oriAmt the oriAmt to set
     */
    public void setOriAmt(Integer oriAmt) {
        this.oriAmt = oriAmt;
    }

    /**
     * @return the usdAmt
     */
    public Integer getUsdAmt() {
        return usdAmt;
    }

    /**
     * @param usdAmt the usdAmt to set
     */
    public void setUsdAmt(Integer usdAmt) {
        this.usdAmt = usdAmt;
    }

    /**
     * @return the desCcy
     */
    public String getDesCcy() {
        return desCcy;
    }

    /**
     * @param desCcy the desCcy to set
     */
    public void setDesCcy(String desCcy) {
        this.desCcy = desCcy;
    }

    /**
     * @return the desAmt
     */
    public BigDecimal getDesAmt() {
        return desAmt;
    }

    /**
     * @param desAmt the desAmt to set
     */
    public void setDesAmt(BigDecimal desAmt) {
        this.desAmt = desAmt;
    }

    /**
     * @return the codOrgBrn
     */
    public Integer getCodOrgBrn() {
        return codOrgBrn;
    }

    /**
     * @param codOrgBrn the codOrgBrn to set
     */
    public void setCodOrgBrn(Integer codOrgBrn) {
        this.codOrgBrn = codOrgBrn;
    }

    /**
     * @return the datTxn
     */
    public Date getDatTxn() {
        return datTxn;
    }

    /**
     * @param datTxn the datTxn to set
     */
    public void setDatTxn(Date datTxn) {
        this.datTxn = datTxn;
    }

    /**
     * @return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
	
    /**
     * @return the desRet
     */
    public BigDecimal getDesRet() {
        return desRet;
    }

    /**
     * @param desRet the desRet to set
     */
    public void setDesRet(BigDecimal desRet) {
        this.desRet = desRet;
    }
	
	/**
     * @return the codAcctTitle
     */
    public String getCodAcctTitle() {
        return codAcctTitle;
    }

    /**
     * @param codAcctTitle the codAcctTitle to set
     */
    public void setCodAcctTitle(String codAcctTitle) {
        this.codAcctTitle = codAcctTitle;
    }
    
}
