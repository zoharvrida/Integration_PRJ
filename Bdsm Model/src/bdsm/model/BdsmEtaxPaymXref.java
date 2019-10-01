/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author rptuatdrsuperid
 */
public class BdsmEtaxPaymXref extends BaseModel implements Serializable{
private String binNo;
private String ServiceCode;
private String channelId;
private String billCode;
private String paymentType;
private String taxPayeeNo;
private String taxPayeeName;
private String taxPayeeAddr;
private String taxCcy;
private BigDecimal taxAmount;
private String taxPayeeAcct;
private String codAcctNo;
private String codAcctCcy;
private String codAcctType;
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
private ETaxBillingInfo BillInfo;
private ETaxInquiryBillingResp InqBillResp;
private ETaxInquiryBillingReq InqBillReq;
private MasterLimitEtax limitVal;
private String seterrCode;
private String seterrDesc;

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
    
    public String getcodAcctType() {
        return codAcctType;
    }

    /**
     * @param billCode the billCode to set
     */
    public void setcodAcctType(String codAcctType) {
        this.codAcctType = codAcctType;
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
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount the taxAmount to set
     */
    public void setTaxAmount(BigDecimal taxAmount) {
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

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * @return the errDesc
     */
    public String getErrDesc() {
        return errDesc;
    }

    /**
     * @param errDesc the errDesc to set
     */
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    /**
     * @return the BillInfo
     */
    public ETaxBillingInfo getBillInfo() {
        return BillInfo;
    }

    /**
     * @param BillInfo the BillInfo to set
     */
    public void setBillInfo(ETaxBillingInfo BillInfo) {
        this.BillInfo = BillInfo;
    }

    /**
     * @return the InqBillResp
     */
    public ETaxInquiryBillingResp getInqBillResp() {
        return InqBillResp;
    }

    /**
     * @param InqBillResp the InqBillResp to set
     */
    public void setInqBillResp(ETaxInquiryBillingResp InqBillResp) {
        this.InqBillResp = InqBillResp;
    }

    /**
     * @return the seterrCode
     */
    public String getSeterrCode() {
        return seterrCode;
    }

    /**
     * @param seterrCode the seterrCode to set
     */
    public void setSeterrCode(String seterrCode) {
        this.seterrCode = seterrCode;
    }

    /**
     * @return the seterrDesc
     */
    public String getSeterrDesc() {
        return seterrDesc;
    }

    /**
     * @param seterrDesc the seterrDesc to set
     */
    public void setSeterrDesc(String seterrDesc) {
        this.seterrDesc = seterrDesc;
    }

    /**
     * @return the limitVal
     */
    public MasterLimitEtax getLimitVal() {
        return limitVal;
    }

    /**
     * @param limitVal the limitVal to set
     */
    public void setLimitVal(MasterLimitEtax limitVal) {
        this.limitVal = limitVal;
    }

    /**
     * @return the ServiceCode
     */
    public String getServiceCode() {
        return ServiceCode;
    }

    /**
     * @param ServiceCode the ServiceCode to set
     */
    public void setServiceCode(String ServiceCode) {
        this.ServiceCode = ServiceCode;
    }

    /**
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the ntb
     */

    /**
     * @param ntb the ntb to set
     */

    /**
     * @return the binNo
     */
    public String getBinNo() {
        return binNo;
    }

    /**
     * @param binNo the binNo to set
     */
    public void setBinNo(String binNo) {
        this.binNo = binNo;
    }
  

    /**
     * @return the billCode
     */
    
    
}
