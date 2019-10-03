package bdsm.web.menu32102;

import bdsm.model.BdsmEtaxPaymXref;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.MasterLimitEtax;
import bdsm.util.ClassConverterUtil;
import bdsm.web.ModelDrivenBaseContentAction;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00024535
 */
@SuppressWarnings("serial")
public class Menu32102Action extends ModelDrivenBaseContentAction<Object> {
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
private BdsmEtaxPaymXref mdp =  new BdsmEtaxPaymXref();


    private BdsmEtaxPaymXref epv ;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private ETaxInquiryBillingResp etax;
    private MasterLimitEtax limVal;
    private String state;
    private String responseStatus;
    private String contentData;
    private String dialogState;

    public Menu32102Action() {
    }

    @Override
    public String doAdd() {
        try {
            Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
            requestMap.put("methodName", "validateLimitUser");
            requestMap.put("idMaintainedBy", this.getIdMaintainedBy());
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap,this.getTokenKey(),this.getTzToken());
            Map viewData = (Map) resultMap.get("epv");
            if(viewData == null) {
                viewData = new HashMap();
            }
            Map limitInfo = (Map) resultMap.get("limVal");
            
            this.epv = new BdsmEtaxPaymXref();
            ClassConverterUtil.MapToClass(viewData, this.epv);
            
            MasterLimitEtax limit = new MasterLimitEtax();
            ClassConverterUtil.MapToClass(limitInfo, limit);
            this.epv.setLimitVal(limit);
            
            this.getLogger().debug("Error Code: " + this.epv.getLimitVal().getErrCode().toString());
            this.getLogger().debug("Error Desc: " + this.epv.getLimitVal().getErrDesc().toString());
            if(!this.epv.getLimitVal().getErrCode().toString().equalsIgnoreCase("0000")){
                setDialogState("0");
                setResponseStatus("0");
            }else{
                this.setContentData(JSONUtil.serialize(this.epv));
                setResponseStatus("1");
                setDialogState("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getLogger().debug("Error Authorize Limit User: " +getEpv().getLimitVal().getErrDesc().toString()+ e, e);
        }

        return SUCCESS;
    }

    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getModel() {
        return null;
    }  

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
     * @return the idMaintainedSpv
     */
    public String getIdMaintainedSpv() {
        return idMaintainedSpv;
    }

    /**
     * @param idMaintainedSpv the idMaintainedSpv to set
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
        this.idMaintainedSpv = idMaintainedSpv;
    }

        /**
     * @return the idMaintainedBy
     */
    public String getIdMaintainedBy() {
        return idMaintainedBy;
    }

    /**
     * @param idMaintainedBy the idMaintainedBy to set
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
        this.idMaintainedBy = idMaintainedBy;
    }
    /**
     * @return the etax
     */
    public ETaxInquiryBillingResp getEtax() {
        return etax;
    }

    /**
     * @param etax the etax to set
     */
    public void setEtax(ETaxInquiryBillingResp etax) {
        this.etax = etax;
    }

    /**
     * @return the epv
     */
    public BdsmEtaxPaymXref getEpv() {
        return epv;
    }

    /**
     * @param epv the epv to set
     */
    public void setEpv(BdsmEtaxPaymXref epv) {
        this.epv = epv;
    }

    /**
     * @return the limVal
     */
    public MasterLimitEtax getLimVal() {
        return limVal;
    }

    /**
     * @param limVal the limVal to set
     */
    public void setLimVal(MasterLimitEtax limVal) {
        this.limVal = limVal;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the responseStatus
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * @return the contentData
     */
    public String getContentData() {
        return contentData;
    }

    /**
     * @param contentData the contentData to set
     */
    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    /**
     * @return the dialogState
     */
    public String getDialogState() {
        return dialogState;
    }

    /**
     * @param dialogState the dialogState to set
     */
    public void setDialogState(String dialogState) {
        this.dialogState = dialogState;
    }
    
    
}
