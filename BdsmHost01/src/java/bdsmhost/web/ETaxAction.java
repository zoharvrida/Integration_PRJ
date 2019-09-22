package bdsmhost.web;


import bdsm.fcr.model.BaCcyCode;
import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.GLMaster;
import bdsm.model.MasterLimitEtax;
import bdsmhost.dao.MasterLimitEtaxDao;
import bdsmhost.dao.BdsmEtaxPaymXrefDao;
import bdsm.model.BdsmEtaxPaymXref;
import bdsm.fcr.service.AccountService;
import bdsm.fcr.service.DataMasterService;
import bdsm.model.ETaxInquiryBillingReq;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.ETaxReInquiryBillingReq;
import bdsm.model.ETaxReInquiryBillingResp;
import bdsm.model.Parameter;
import bdsmhost.fcr.dao.DataMasterDAO;
import bdsm.service.ETaxService;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.ETaxCurrencyDao;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaCcyCodeDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.GLMasterDAO;
import static bdsmhost.web.ModelDrivenBaseHostAction.LOGGER;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.List;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.Session;

/**
 *
 * @author v00022309
 */
@SuppressWarnings("serial")
public class ETaxAction extends ModelDrivenBaseHostAction<Object> {

    private static final String GET_CH_ACCT_MAST = "fcrChAcctMast_get.action";
    private String billingId;
    private String paymentType;
    private ETaxInquiryBillingReq inquiryReq;
    private ETaxInquiryBillingResp inquiryResp;
    private ETaxReInquiryBillingReq reInquiryReq;
    private ETaxReInquiryBillingResp reInquiryResp;
    private List currencyList;
    private String accountNo;
    private HashMap accountDetail;
    private String glNo;
    private Integer branchCode;    
    private Integer currencyCode;
    private GLMaster glMaster;
    private BdsmEtaxPaymXref paymentReq;
    private String codAuthid;
    private String taxAmount;
    private String errCode;
    private String errDesc;
	private BdsmEtaxPaymXref mdp = new BdsmEtaxPaymXref();

    public String inquiryBilling() {

        LOGGER.info("BILLING ID: " + getBillingId());
        LOGGER.info("PAYMENT TYPE: " + getPaymentType());

        try {
            inquiryReq = new ETaxInquiryBillingReq();
            inquiryReq.setBillingId(billingId);
            inquiryReq.setBranchCode("09233");
            inquiryReq.setCostCenter("09233");
            inquiryReq.setUserId("ebanking");            
            
            ETaxService etaxService = new ETaxService(this.getHSession());
            this.inquiryResp = etaxService.inquiryBilling(inquiryReq);
            
            // get credit account
            getCreditAccount();
            
        } catch (Exception ex) {
            this.getLogger().info("Exception: " + ex, ex);
            return this.quitOfError(this.getErrorMessageFromException(ex));
        }

        this.getLogger().info("inquiryResp: " + this.inquiryResp);

        return SUCCESS;
    }

    public String reInquiryBilling() {
        LOGGER.info("BILLING ID: " + getBillingId());

        try {
            reInquiryReq = new ETaxReInquiryBillingReq();
            reInquiryReq.setBillingId(billingId);
            reInquiryReq.setBranchCode("09233");
            reInquiryReq.setCostCenter("09233");
            reInquiryReq.setUserId("ebanking");            
            
            ETaxService etaxService = new ETaxService(this.getHSession());
            this.reInquiryResp = etaxService.reinquiryBilling(reInquiryReq);
            
            // get credit account
            //getCreditAccount();
            
        } catch (Exception ex) {
            this.getLogger().info("Exception: " + ex, ex);
            return this.quitOfError(this.getErrorMessageFromException(ex));
        }

        this.getLogger().info("reInquiryResp: " + this.reInquiryResp);

        return SUCCESS;
    }
    
    public String listCurrency() {
        LOGGER.info("GET ETAX CURRENCY");
        try {
            Session hsession = HibernateUtil.getSession();
            ETaxCurrencyDao ccyDao = new ETaxCurrencyDao(hsession);
            setCurrencyList(ccyDao.list());
        } catch (Exception er) {
            this.getLogger().debug("Error List Currency: " + er, er);
        }
        return SUCCESS;
    }
    
    @JSON(serialize=false)
    public String getCreditAccount() {
        ParameterDao paramDAO = new ParameterDao(this.getHSession());
        Parameter param = paramDAO.get("ETAX_KPPN_ACCT_NO");
        
        if(param != null && param.getStrVal() != null &&  !"".equals(param.getStrVal())) {
            String kppnAccountNo = param.getStrVal();
            
            AccountService srvc = new AccountService();
            srvc.setFCRChAcctMastDAO(new ChAcctMastDAO(this.getHSession()));
            BaCcyCodeDAO ccyDao = new BaCcyCodeDAO(this.getHSession());
            
            ChAcctMast account = srvc.getByAccountNo(param.getStrVal());
            this.putToObjData("kppnAccountNo", kppnAccountNo);
            if(account != null) {
                BaCcyCode ccyCode = ccyDao.getByCcyCod(account.getCodCcy());
                
                this.putToObjData("kppnAccountCcyCode", ccyCode.getCompositeId().getCodCcy());
                this.putToObjData("kppnAccountCcyName", ccyCode.getNamCcyShort());
                this.putToObjData("kppnAccountName", account.getNamCustShrt());
            } else {
                // TODO: throw exception
            }
        } else {
            // TODO: throw exception
        }
        return SUCCESS;
    }
    
    public String inquiryGLAccount() {
        String reqResult = null;
        String errorCode = null;

        getLogger().debug("GL Acct No : " + getGlNo());
        Integer _glNo = Integer.valueOf(getGlNo());
        Integer _branchCode = getBranchCode();
        Integer _currencyCode = getCurrencyCode();
        try {
            AccountService fcrAcctService = new AccountService();
            fcrAcctService.setFCRGLMasterDAO(new GLMasterDAO(this.getHSession()));
            GLMaster GLMaster = fcrAcctService.getGLAccountByBranchAccountAndCurrency(
                    _branchCode, _glNo, _currencyCode);
            
            setGlMaster(GLMaster);
            
            setJsonStatus(reqResult);
            setErrorCode(errorCode);
        } catch (Exception ex) {
            getLogger().fatal(ex, ex);
            return ActionSupport.ERROR;
        }
        return SUCCESS;
    }
    
    public void validateLimitUser() {        
       
        
        int TotAmount = 0;
        int limAmount;
        
       
       codAuthid = mdp.getCodAuthId();       
       TotAmount = mdp.getTaxAmount();
        
        try
        {
            BdsmEtaxPaymXrefDao mleDao = new BdsmEtaxPaymXrefDao(this.getHSession());
            limAmount = mleDao.cleans(codAuthid, TotAmount);
            if (limAmount == 1)
            {
                this.errCode = "0000";
                this.errDesc = "Success";
            } else if (limAmount == 99)
            {
                this.errCode = "1000";
                this.errDesc = "Template User Unauthorized to do transactions"; 
                
            } else if (limAmount == 97)
            {
                this.errCode = "2000";
                this.errDesc = "user limit will be exceed"; 
            } else if (limAmount == 98)
            {
                this.errCode = "3000";
                this.errDesc = "Trx is greater then user limit"; 
            } else
            {
                this.errCode = "9999";
                this.errDesc = "General Error"; 
            }
        }catch(Exception e)
        {
           this.getLogger().debug("Error Authorize Limit User: " + e, e);
        }       
        mdp.seterrCode(this.errCode);
        mdp.seterrDesc(this.errDesc);
    
           
    }
    
    
    public String PostingEtaxPaymentRequest() { 
        BdsmEtaxPaymXrefDao mle =  new BdsmEtaxPaymXrefDao(this.getHSession());
        BdsmEtaxPaymXref paym =  new BdsmEtaxPaymXref();
        paym.setBillCode(this.paymentReq.getBillCode());
        paym.setCodAcctCcy(this.paymentReq.getCodAcctCcy());
        paym.setCodAcctNo(this.paymentReq.getCodAcctNo());
        paym.setCodAuthId(this.paymentReq.getCodAuthId());
        paym.setCodCcBrn(this.paymentReq.getCodCcBrn());
        paym.setCodSspcpNo(this.paymentReq.getCodSspcpNo());
        paym.setCodStanId(this.paymentReq.getCodStanId());
        paym.setCodTrxBrn(this.paymentReq.getCodTrxBrn());
        paym.setCodUserId(this.paymentReq.getCodUserId());
        paym.setDtmPost(this.paymentReq.getDtmPost());
        paym.setDtmRequest(this.paymentReq.getDtmRequest());
        paym.setDtmResp(this.paymentReq.getDtmResp());
        paym.setDtmTrx(this.paymentReq.getDtmTrx());
        paym.setPaymentType(this.paymentReq.getPaymentType());
        paym.setRefNtb(this.paymentReq.getRefNtb());
        paym.setRefNtpn(this.paymentReq.getRefNtpn());
        paym.setRefUsrNo(this.paymentReq.getRefUsrNo());
        paym.setTaxAmount(this.paymentReq.getTaxAmount());
        paym.setTaxCcy(this.paymentReq.getTaxCcy());
        paym.setTaxPayeeAcct(this.paymentReq.getTaxPayeeAcct());
        paym.setTaxPayeeAddr(this.paymentReq.getTaxPayeeAcct());
        paym.setTaxPayeeName(this.paymentReq.getTaxPayeeName());
        paym.setTaxPayeeNo(this.paymentReq.getTaxPayeeNo());
        paym.seterrCode(this.paymentReq.geterrCode());
        paym.seterrDesc(this.paymentReq.geterrDesc());
        mle.insert(paym);       
        return SUCCESS;
    }
    
    
    private String getErrorMessageFromException(Exception ex) {
        Throwable t = ex;
        String errorMessage = "";
        while ((errorMessage = t.getMessage()) == null) {
            t = t.getCause();
            if (t == null) {
                break;
            }
        }

        return errorMessage;
    }

    private String quitOfError(String errorCode) {
        this.setErrorCode(errorCode);
        return ERROR;
    }

    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String pickListDesc(String masterType, Object id) throws Exception {
        DataMasterService dmService = new DataMasterService();
        dmService.setDataMasterDAO(new DataMasterDAO(this.getHSession()));

        return (String) dmService.getDataById(masterType, id)[1];
    }

    @Override
    public Object getModel() {
        return new Object();
    }

    /**
     * @return the billingId
     */
    public String getBillingId() {
        return billingId;
    }

    /**
     * @param billingId the billingId to set
     */
    public void setBillingId(String billingId) {
        this.billingId = billingId;
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

    public ETaxInquiryBillingResp getInquiryResp() {
        return inquiryResp;
    }

    public void setInquiryResp(ETaxInquiryBillingResp inquiryResp) {
        this.inquiryResp = inquiryResp;
    }

    public ETaxInquiryBillingReq getInquiryReq() {
        return inquiryReq;
    }

    public void setInquiryReq(ETaxInquiryBillingReq inquiryReq) {
        this.inquiryReq = inquiryReq;
    }

    public List getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List currencyList) {
        this.currencyList = currencyList;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setAccountDetail(HashMap accountDetail) {
        this.accountDetail = accountDetail;
    }

    public String getGlNo() {
        return glNo;
    }

    public void setGlNo(String glNo) {
        this.glNo = glNo;
    }

    public Integer getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public GLMaster getGlMaster() {
        return glMaster;
    }

    public void setGlMaster(GLMaster glMaster) {
        this.glMaster = glMaster;
    }

    public ETaxReInquiryBillingReq getReInquiryReq() {
        return reInquiryReq;
    }

    public void setReInquiryReq(ETaxReInquiryBillingReq reInquiryReq) {
        this.reInquiryReq = reInquiryReq;
    }

    public ETaxReInquiryBillingResp getReInquiryResp() {
        return reInquiryResp;
    }

    public void setReInquiryResp(ETaxReInquiryBillingResp reInquiryResp) {
        this.reInquiryResp = reInquiryResp;
    }
    
}
