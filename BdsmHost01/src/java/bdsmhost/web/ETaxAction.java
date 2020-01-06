package bdsmhost.web;


import bdsm.fcr.model.BaCcyCode;
import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.GLMaster;
import bdsm.model.MasterLimitEtax;
import bdsmhost.dao.BdsmEtaxPaymXrefDao;
import bdsm.model.BdsmEtaxPaymXref;
import bdsm.fcr.service.AccountService;
import bdsm.fcr.service.DataMasterService;
import bdsm.model.ETaxInquiryBillingReq;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.ETaxReInquiryBillingReq;
import bdsm.model.ETaxReInquiryBillingResp;
import bdsm.model.EtaxPrint;
import bdsm.model.Parameter;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.fcr.dao.DataMasterDAO;
import bdsm.service.ETaxService;
import bdsm.util.BdsmUtil;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.ETaxCurrencyDao;
import bdsmhost.dao.EtaxPrintDAO;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaCcyCodeDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.GLMasterDAO;
import static bdsmhost.web.ModelDrivenBaseHostAction.LOGGER;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private String idTrx;
    private String paymentType;
    private ETaxInquiryBillingReq inquiryReq;
    private ETaxInquiryBillingResp etax;
    private MasterLimitEtax limVal;
    private ETaxReInquiryBillingReq reInquiryReq;
    private ETaxReInquiryBillingResp reInquiryResp;
    private List currencyList;
    private String accountNo;
    private HashMap accountDetail;
    private String glNo;
    private Integer branchCode;    
    private Integer currencyCode;
    private GLMaster glMaster;    
    private String codAuthid;
    private String amount;
    private String errCode;
    private String errDesc;
    private BdsmEtaxPaymXref epv;
    private EtaxPrint etaxPrint;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private Integer codeBranchGl;
    private String codIdBy;
    private String codSpvIdBy;
    
    public String PostingEtaxPaymentRequest() {
        try {
            ETaxService etaxService = new ETaxService(this.getHSession());
            codIdBy = this.getIdMaintainedBy();
            if (this.getIdMaintainedSpv().length() == 0) {
                codSpvIdBy = this.getIdMaintainedBy();
            } else {
                codSpvIdBy = this.getIdMaintainedSpv();
            }
            this.epv = etaxService.paymentBilling(etax, codIdBy, codSpvIdBy);
        } catch (Exception ex) {
            this.getLogger().info("Exception: " + ex, ex);
        }

        return SUCCESS;
    }

    public String inquiryBilling() {

        LOGGER.info("BILLING ID: " + getBillingId());
        LOGGER.info("PAYMENT TYPE: " + getPaymentType());

        try {
            inquiryReq = new ETaxInquiryBillingReq();
            inquiryReq.setBillingId(billingId);
            inquiryReq.setBranchCode(this.codeBranchGl.toString());
            inquiryReq.setCostCenter(BdsmUtil.leftPad("0",5-this.codeBranchGl.toString().length(),'0')+this.codeBranchGl.toString());
            inquiryReq.setUserId(PropertyPersister.userId);
            inquiryReq.setRefNo(generateRefNo());
            
            ETaxService etaxService = new ETaxService(this.getHSession());
            this.etax = etaxService.inquiryBilling(inquiryReq);
            if(paymentType.equalsIgnoreCase("1")){
                this.etax.setPmtType("00");
            }else if(paymentType.equalsIgnoreCase("2")){
                this.etax.setPmtType("30");
            }
            else {
                this.etax.setPmtType("40");
                this.setGlNo(PropertyPersister.glAccountNo);
                this.setBranchCode(this.codeBranchGl);
                this.setCurrencyCode(360);
                inquiryGLAccount();
                this.etax.setGlAccountNo(PropertyPersister.glAccountNo);
                this.etax.setGlAccountName(getGlMaster().getNamGLCode());
            }
            // get credit account
            getCreditAccount();
            
        } catch (Exception ex) {
            this.getLogger().info("Exception: " + ex, ex);
            return this.quitOfError(this.getErrorMessageFromException(ex));
        }

        this.getLogger().info("inquiryResp: " + this.etax);

        return SUCCESS;
    }

    public String reInquiryBilling() {
        LOGGER.info("BILLING ID: " + getBillingId());

        try {     
            reInquiryReq = new ETaxReInquiryBillingReq();
            reInquiryReq.setBillingId(billingId);
            reInquiryReq.setBranchCode(this.codeBranchGl.toString());
            reInquiryReq.setCostCenter(BdsmUtil.leftPad("0",5-this.codeBranchGl.toString().length(),'0')+this.codeBranchGl.toString());
            reInquiryReq.setUserId(PropertyPersister.userId);   
            reInquiryReq.setRefNo(generateRefNo());
            
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
       int limAmount;      
       if(this.getIdMaintainedSpv().length() == 0){
           codAuthid = this.getIdMaintainedBy();
       }else{
           codAuthid = this.getIdMaintainedSpv(); 
       }
       BigDecimal TotAmount = etax.getAmount();
       this.etax.setRefNo(generateRefNo());
       if(etax.getPmtType().equalsIgnoreCase("00")){
          this.etax.setGlAccountNo(PropertyPersister.cashGlAccountNo);
       }
        try
        {
            BdsmEtaxPaymXrefDao mleDao = new BdsmEtaxPaymXrefDao(this.getHSession());
            limAmount = mleDao.cleans(codAuthid, TotAmount);
            if (limAmount == 1)
            {
                this.setErrCode("0000");
                this.setErrDesc("Success");
                this.PostingEtaxPaymentRequest();
            } else if (limAmount == 99)
            {
                this.setErrCode("1000");
                this.setErrDesc("Template User Unauthorized to do transactions"); 
                
            } else if (limAmount == 97)
            {
                this.setErrCode("2000");
                this.setErrDesc("user limit will be exceed"); 
            } else if (limAmount == 98)
            {
                this.setErrCode("3000");
                this.setErrDesc("Trx is greater then user limit"); 
            } else
            {
                this.setErrCode("9999");
                this.setErrDesc("General Error"); 
            }
        }catch(Exception e)
        {
           this.getLogger().debug("Error Authorize Limit User: " + e, e);
        }
        limVal = new MasterLimitEtax();
        limVal.setErrCode(this.errCode);
        limVal.setErrDesc(this.errDesc);
        
    }
    
    public void printBPN() {   
        LOGGER.info("ID TRX: " + getIdTrx());
        try {
            EtaxPrintDAO epdao = new EtaxPrintDAO(this.getHSession());
            this.etaxPrint = epdao.findAll(idTrx);
            String result = epdao.cleans(this.etaxPrint.getJmlSetoran());
            this.etaxPrint.setTerbilang(result);
        } catch (Exception e) {
            this.getLogger().debug("Error Print BPN: " + e, e);
        }
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

    public String generateRefNo() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        return (dateFormatter.format(new Date()));
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

    public ETaxInquiryBillingResp getEtax() {
        return etax;
    }

    public void setEtax(ETaxInquiryBillingResp etax) {
        this.etax = etax;
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
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
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

    /**
     * @return the codeBranchGl
     */
    public Integer getCodeBranchGl() {
        return codeBranchGl;
    }

    /**
     * @param codeBranchGl the codeBranchGl to set
     */
    public void setCodeBranchGl(Integer codeBranchGl) {
        this.codeBranchGl = codeBranchGl;
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
