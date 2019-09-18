/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.model;

import java.sql.Date;

/**
 *
 * @author rptuatdrsuperid
 */
public class BdsmEtaxPaymXref extends BaseModel{
private String billCode;
private String paymentType;
private String taxPayeeNo;
private String taxPayeeName;
private String taxPayeeAddr;
private String taxCcy;
private int taxAmount;
private String taxPayeeAcct;
private String codAcctNo;
private String codAcctCcy;
private String codTrxBrn;
private String codCcBrn;
private String refNtb;
private String refNtpn;
private String codStanId;
private String codSspcpNo;
private String codUserId;
private String codAuthId;
private String refUsrNo;
private Date dtmTrx;
private Date dtmRequest;
private Date dtmResp;
private Date dtmPost;
private String errCode;
private String errDesc;

    /**
     * @return the billCode
     */
    public String getBillCode() {
        return billCode;
    }

    /**
     * @param billCode the billCode to set
     */
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    /**
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the taxPayeeNo
     */
    public String getTaxPayeeNo() {
        return taxPayeeNo;
    }

    /**
     * @param taxPayeeNo the taxPayeeNo to set
     */
    public void setTaxPayeeNo(String taxPayeeNo) {
        this.taxPayeeNo = taxPayeeNo;
    }

    /**
     * @return the taxPayeeName
     */
    public String getTaxPayeeName() {
        return taxPayeeName;
    }

    /**
     * @param taxPayeeName the taxPayeeName to set
     */
    public void setTaxPayeeName(String taxPayeeName) {
        this.taxPayeeName = taxPayeeName;
    }

    /**
     * @return the taxPayeeAddr
     */
    public String getTaxPayeeAddr() {
        return taxPayeeAddr;
    }

    /**
     * @param taxPayeeAddr the taxPayeeAddr to set
     */
    public void setTaxPayeeAddr(String taxPayeeAddr) {
        this.taxPayeeAddr = taxPayeeAddr;
    }

    /**
     * @return the taxCcy
     */
    public String getTaxCcy() {
        return taxCcy;
    }

    /**
     * @param taxCcy the taxCcy to set
     */
    public void setTaxCcy(String taxCcy) {
        this.taxCcy = taxCcy;
    }

    /**
     * @return the taxAmount
     */
    public int getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount the taxAmount to set
     */
    public void setTaxAmount(int taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return the taxPayeeAcct
     */
    public String getTaxPayeeAcct() {
        return taxPayeeAcct;
    }

    /**
     * @param taxPayeeAcct the taxPayeeAcct to set
     */
    public void setTaxPayeeAcct(String taxPayeeAcct) {
        this.taxPayeeAcct = taxPayeeAcct;
    }

    /**
     * @return the codAcctNo
     */
    public String getCodAcctNo() {
        return codAcctNo;
    }

    /**
     * @param codAcctNo the codAcctNo to set
     */
    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }

    /**
     * @return the codAcctCcy
     */
    public String getCodAcctCcy() {
        return codAcctCcy;
    }

    /**
     * @param codAcctCcy the codAcctCcy to set
     */
    public void setCodAcctCcy(String codAcctCcy) {
        this.codAcctCcy = codAcctCcy;
    }

    /**
     * @return the codTrxBrn
     */
    public String getCodTrxBrn() {
        return codTrxBrn;
    }

    /**
     * @param codTrxBrn the codTrxBrn to set
     */
    public void setCodTrxBrn(String codTrxBrn) {
        this.codTrxBrn = codTrxBrn;
    }

    /**
     * @return the codCcBrn
     */
    public String getCodCcBrn() {
        return codCcBrn;
    }

    /**
     * @param codCcBrn the codCcBrn to set
     */
    public void setCodCcBrn(String codCcBrn) {
        this.codCcBrn = codCcBrn;
    }

    /**
     * @return the refNtb
     */
    public String getRefNtb() {
        return refNtb;
    }

    /**
     * @param refNtb the refNtb to set
     */
    public void setRefNtb(String refNtb) {
        this.refNtb = refNtb;
    }

    /**
     * @return the refNtpn
     */
    public String getRefNtpn() {
        return refNtpn;
    }

    /**
     * @param refNtpn the refNtpn to set
     */
    public void setRefNtpn(String refNtpn) {
        this.refNtpn = refNtpn;
    }

    /**
     * @return the codStanId
     */
    public String getCodStanId() {
        return codStanId;
    }

    /**
     * @param codStanId the codStanId to set
     */
    public void setCodStanId(String codStanId) {
        this.codStanId = codStanId;
    }

    /**
     * @return the codSspcpNo
     */
    public String getCodSspcpNo() {
        return codSspcpNo;
    }

    /**
     * @param codSspcpNo the codSspcpNo to set
     */
    public void setCodSspcpNo(String codSspcpNo) {
        this.codSspcpNo = codSspcpNo;
    }

    /**
     * @return the codUserId
     */
    public String getCodUserId() {
        return codUserId;
    }

    /**
     * @param codUserId the codUserId to set
     */
    public void setCodUserId(String codUserId) {
        this.codUserId = codUserId;
    }

    /**
     * @return the codAuthId
     */
    public String getCodAuthId() {
        return codAuthId;
    }

    /**
     * @param codAuthId the codAuthId to set
     */
    public void setCodAuthId(String codAuthId) {
        this.codAuthId = codAuthId;
    }

    /**
     * @return the refUsrNo
     */
    public String getRefUsrNo() {
        return refUsrNo;
    }

    /**
     * @param refUsrNo the refUsrNo to set
     */
    public void setRefUsrNo(String refUsrNo) {
        this.refUsrNo = refUsrNo;
    }

    /**
     * @return the dtmTrx
     */
    public Date getDtmTrx() {
        return dtmTrx;
    }

    /**
     * @param dtmTrx the dtmTrx to set
     */
    public void setDtmTrx(Date dtmTrx) {
        this.dtmTrx = dtmTrx;
    }

    /**
     * @return the dtmRequest
     */
    public Date getDtmRequest() {
        return dtmRequest;
    }

    /**
     * @param dtmRequest the dtmRequest to set
     */
    public void setDtmRequest(Date dtmRequest) {
        this.dtmRequest = dtmRequest;
    }

    /**
     * @return the dtmResp
     */
    public Date getDtmResp() {
        return dtmResp;
    }

    /**
     * @param dtmResp the dtmResp to set
     */
    public void setDtmResp(Date dtmResp) {
        this.dtmResp = dtmResp;
    }

    /**
     * @return the dtmPost
     */
    public Date getDtmPost() {
        return dtmPost;
    }

    /**
     * @param dtmPost the dtmPost to set
     */
    public void setDtmPost(Date dtmPost) {
        this.dtmPost = dtmPost;
    }
     
    public String geterrCode() {
        return errCode;
    }

    /**
     * @param dtmPost the dtmPost to set
     */
    public void seterrCode(String errCode) {
        this.errCode = errCode;
    }
    
    public String geterrDesc() {
        return errDesc;
    }

    /**
     * @param dtmPost the dtmPost to set
     */
    public void seterrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
    
}
