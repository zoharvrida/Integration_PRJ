package bdsm.web.menu32102;

import bdsm.model.BdsmEtaxPaymXref;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.EtaxPrint;
import bdsm.model.MasterLimitEtax;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.web.ModelDrivenBaseContentAction;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00024535
 */
@SuppressWarnings("serial")
public class Menu32102Action extends ModelDrivenBaseContentAction<Object> {

    private BdsmEtaxPaymXref epv;
    private String idTrx;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private ETaxInquiryBillingResp etax;
    private MasterLimitEtax limVal;
    private EtaxPrint etaxPrint;
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
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap, this.getTokenKey(), this.getTzToken());
            Map viewData = (Map) resultMap.get("epv");
            if (viewData == null) {
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
            if (!this.epv.getLimitVal().getErrCode().toString().equalsIgnoreCase("0000")) {
                setDialogState("0");
                setResponseStatus("0");
            } else {
                if (this.epv.getErrCode().equalsIgnoreCase("000000") || this.epv.getErrCode().equalsIgnoreCase("000050")
                        || this.epv.getErrCode().equalsIgnoreCase("481050")) {
                    setResponseStatus("1");
                    setDialogState("1");
                } else {
                    setResponseStatus("2");
                    setDialogState("1");
                }
                this.setContentData(JSONUtil.serialize(this.epv));
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getLogger().debug("Error Authorize Limit User: " + getEpv().getLimitVal().getErrDesc().toString() + e, e);
        }

        return SUCCESS;
    }

    public final String reqPrint() {
        this.getLogger().info("[Begin] Print Request");
        try {
            if (isValidSession()) {
                return this.reqPrint_();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            this.getLogger().fatal(e, e);
            return ERROR;
        } finally {
            this.getLogger().info("[ End ] Print Request");
        }
    }

    @SuppressWarnings("unchecked")
    public String reqPrint_() {
        try {
            Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
            requestMap.put("methodName", "printBPN");
            requestMap.put("idTrx", this.getIdTrx());

            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
            Map viewData = (Map) resultMap.get("etaxPrint");
            if (viewData == null) {
                viewData = new HashMap();
            }

            this.etaxPrint = new EtaxPrint();
            ClassConverterUtil.MapToClass(viewData, this.etaxPrint);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = dateFormat.parse(this.etaxPrint.getTglBayar());
            String result = dateFormat1.format(date);
            this.etaxPrint.setTglBayar(result);
            this.setContentData(JSONUtil.serialize(this.etaxPrint));

            // Change yyyy-MM-ddTHH:mm:ss into dd MMMM yyyy
            Pattern pattern = Pattern.compile("\\d{4}[-]\\d{2}[-]\\d{2}T\\d{2}:\\d{2}:\\d{2}");
            Matcher matcher = pattern.matcher(this.contentData);
            DateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            DateFormat dateFormatter2 = new SimpleDateFormat("dd/MM/yyyy", new java.util.Locale("in", "ID"));
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String g = matcher.group();
                try {
                    Date d = dateFormatter1.parse(g);
                    String strReplace = dateFormatter2.format(d);

                    matcher.appendReplacement(sb, strReplace);
                } catch (ParseException e) {
                }
            }
            matcher.appendTail(sb);
            this.contentData = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.getLogger().debug("Error Print BPN: " + e, e);
        }
        return "result";
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

    /**
     * @return the etaxPrint
     */
    public EtaxPrint getEtaxPrint() {
        return etaxPrint;
    }

    /**
     * @param etaxPrint the etaxPrint to set
     */
    public void setEtaxPrint(EtaxPrint etaxPrint) {
        this.etaxPrint = etaxPrint;
    }

    /**
     * @return the idTrx
     */
    public String getIdTrx() {
        return idTrx;
    }

    /**
     * @param idTrx the idTrx to set
     */
    public void setIdTrx(String idTrx) {
        this.idTrx = idTrx;
    }
}
