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
