/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.fcr.model.*;
import bdsm.fcr.model.CytmRates;
import bdsm.fcr.model.CytmRatesPK;
import bdsm.fcr.service.AccountService;
import bdsm.model.MCRCurrencyRate;
import bdsm.model.MCRTrxTable;
import bdsm.util.ClassConverterUtil;
import bdsmhost.dao.MCRBuyRateDAO;
import bdsmhost.dao.MCRCurrencyRateDAO;
import bdsmhost.dao.MCRTrxTableDAO;
import bdsmhost.fcr.dao.PmTxnLogDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.BaCcyCodeDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import freemarker.core.ParseException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import org.apache.commons.lang.time.DateUtils;

/**
 * 
 * @author bdsm
 */
public class MCRCalcAction extends BaseHostAction {

    private CytmRates model;
    private CytmRatesPK modelpk;
    private PmTxnLog pmtxn;
    private List<Map> modelList;
    private String codCcy1;
    private String amtTxn;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private BigDecimal destAmount;
    private BigDecimal destRate;
    private BaBankMastDAO bankMastDAO;
    private Date datTxn;
    private Integer codOrgBrn;
    private Integer codCcy;
    private String refNetworkNo;
    private String codAccNo;
    private String nameCurrency;
    private Integer amtTxnAcy;
    private Integer amtTxnTcy;
    private String currencyCode;
    private Boolean saveVal;
    private String idUser;
    private MCRTrxTable mcrData;
    private String flag;
    private String codAcctTitle;

    /**
     * 
     * @return
     */
    public String takeGetBuyRate() {
        getLogger().info("amtxn " + getAmtTxn());
        getLogger().info("codCcy1 " + getCodCcy1());

        this.modelpk = new CytmRatesPK();
        this.model = new CytmRates();
        this.modelpk.setCodCcy1(codCcy1);
        this.model.setCompositeId(modelpk);

        getLogger().info("Log :" + model.getCompositeId().getCodCcy1());

        MCRBuyRateDAO dao = new MCRBuyRateDAO(getHSession());
        this.modelList = new ArrayList();
        setModelList(dao.getBuyRateByCcy(model.getCompositeId().getCodCcy1()));
        getLogger().info("Modelist: " + getModelList());

        List<Map> l = modelList;
        l.size();

        for (int i = 0; i < l.size(); i++) {
            Map maps = l.get(i);

            BigDecimal amount = new BigDecimal(amtTxn);
            BigDecimal buyrate = new BigDecimal(maps.get("0").toString());
            BigDecimal sellrate = new BigDecimal(maps.get("1").toString());
            try {

                getLogger().debug("CCY CODE :" + this.codCcy1);
                getLogger().debug("BUY RATE :" + buyrate.toPlainString());

                if ("IDR".equalsIgnoreCase(codCcy1)) {
                    List<Map> ls = dao.getBuyRateByCcy("USD");
                    ls.size();
                    if (ls.size() > 0) {
                        Map map = ls.get(i);
                        BigDecimal buyrates = new BigDecimal(map.get("0").toString());
                        getLogger().debug("Buy rate in USD :" + buyrates);
                        BigDecimal rates = amount.divide(buyrates, RoundingMode.FLOOR);
                        this.buyRate = rates;
                        getLogger().debug("Hasil equivalent not in USD/IDR :" + rates);

                    }
                } else if ("USD".equalsIgnoreCase(codCcy1)) {
                    this.buyRate = amount;
                    getLogger().debug("Hasil equivalent :" + this.buyRate);

                } else if (!"IDR".equalsIgnoreCase(codCcy1)) {
                    BigDecimal idramt = amount.multiply(sellrate); //idr amount
                    getLogger().debug("Hasil idr amount :" + idramt);

                    List<Map> ls = dao.getBuyRateByCcy("USD");
                    ls.size();
                    if (ls.size() > 0) {
                        Map map = ls.get(i);
                        BigDecimal buyrates = new BigDecimal(map.get("0").toString());
                        getLogger().debug("Buy rate in USD :" + buyrates);
                        BigDecimal rates = idramt.divide(buyrates, RoundingMode.FLOOR);
                        this.buyRate = rates;
                        getLogger().debug("Hasil equivalent not in USD/IDR :" + rates);

                    }
                }

            } catch (Exception e) {
                getLogger().debug("FAILED get Buy Rate");
                getLogger().debug(e, e);
            }
            //maps.put("buyRates", rates);
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String takeGetRateDestiation() {

        getLogger().info("Currency Code : " + getCurrencyCode());

        MCRCurrencyRateDAO dao = new MCRCurrencyRateDAO(getHSession());
        List<Map> destRate = dao.getDestinationRate(currencyCode);
        setModelList(destRate);
        getLogger().info("modelrate :" + getModelList());

        List<Map> l = modelList;
        l.size();

        for (int i = 0; i < l.size(); i++) {
            Map maps = l.get(i);

            BigDecimal sellrate = new BigDecimal(maps.get("0").toString());
            try {
                getLogger().debug("currency code :" + this.currencyCode);
                getLogger().debug("sell rate :" + sellrate.toString());

                this.sellRate = sellrate;

            } catch (Exception e) {
            }
        }
        return SUCCESS;
    }

    //service menu 29402 --Begin
    /**
     * 
     * @return
     * @throws ParseException
     */
    public String retriveValueTrx() throws ParseException {
        BaBankMastDAO babank = new BaBankMastDAO(getHSession());
        BaBankMast datO = babank.get();
        Date datProc = datO.getBusinessDate();

        Date datProc2 = new Date(datProc.getTime());
        datProc2 = DateUtils.addMonths(datProc2, -3);
        getLogger().info("Date : " + datProc2);

        MCRTrxTableDAO trxDAO = new MCRTrxTableDAO(getHSession());
        this.mcrData = trxDAO.MCR_getByRefNo(this.refNetworkNo, this.codOrgBrn);

        if (this.mcrData != null) {
            getLogger().debug("[BEGIN INQUIRY]");
            getLogger().debug("Ref no : " + getRefNetworkNo());
            getLogger().debug("cod brn : " + getCodOrgBrn());
            
            this.flag="M";
            PmTxnLog pm = new PmTxnLog();
            pm.setCodAccNo(mcrData.getAcctNo());
            setNameCurrency(mcrData.getOriCcy());
            pm.setAmtTxnAcy(mcrData.getOriAmt());
            pm.setAmtTxnTcy(mcrData.getUsdAmt());
            setCurrencyCode(mcrData.getDesCcy());
            setDestAmount(mcrData.getDesAmt());
            setSellRate(mcrData.getDesRet());
			setCodAcctTitle(mcrData.getCodAcctTitle());
            
            this.pmtxn = pm;
            
            
            getLogger().debug("account no : " + mcrData.getAcctNo());
            getLogger().debug("origin currency : " + mcrData.getOriCcy());
            getLogger().debug("origin amount : " + mcrData.getOriAmt());
            getLogger().debug("origin usd equivalent : " + mcrData.getUsdAmt());
            getLogger().debug("destination currency : " + mcrData.getDesCcy());
            getLogger().debug("destination amount : " + mcrData.getDesAmt());
            getLogger().debug("destination rate : " + mcrData.getDesRet());
            
            getLogger().debug("[END INQUIRY]");
        } else {
            getLogger().debug("[BEGIN INQUIRY FROM PMTXNLOG]");
            this.flag="P";
            PmTxnLogDAO dao = new PmTxnLogDAO(getHSession());
            this.pmtxn = dao.MCR_getByRefNetworkNo(this.refNetworkNo, datProc2, this.codOrgBrn);

            ChAcctMastDAO caDAO = new ChAcctMastDAO(getHSession());
            ChAcctMastPK chacctpk = new ChAcctMastPK(this.pmtxn.getCodAccNo(), "A", 11);
            getLogger().debug("CMPK " + chacctpk);
            
            ChAcctMast chacct = caDAO.get(chacctpk);
            getLogger().debug("Codccy : " + chacct);

            BaCcyCodeDAO baDAO = new BaCcyCodeDAO(getHSession());
            BaCcyCode baCcyCode = baDAO.getByCcyCod(chacct.getCodCcy());
            
            this.codAcctTitle = chacct.getCodAcctTitle();
            getLogger().debug("tittle : " + this.codAcctTitle);
           
            getLogger().debug("codccy 1 : " + baCcyCode);
            getLogger().info("codccy 2 : " + baCcyCode);
            this.setNameCurrency(baCcyCode.getNamCcyShort());
            
            getLogger().debug("[END INQUIRY FROM PMTXNLOG]");
            
        }
        
        return SUCCESS;
    }

    /**
     * 
     * @return
     * @throws ParseException
     */
    public String retriveValueDestinationRate() throws ParseException {

        getLogger().info("Currency Code : " + getCurrencyCode());
        getLogger().info("refNetworkNo : " + getRefNetworkNo());

        MCRCurrencyRateDAO dao = new MCRCurrencyRateDAO(getHSession());
        this.sellRate = dao.MCR_getDestinationRateAfter(this.currencyCode, getRefNetworkNo());
        this.sellRate.setScale(3,BigDecimal.ROUND_UP);
        getLogger().debug("dest rate : " + this.sellRate);

        return SUCCESS;
    }

    /**
     * 
     * @return
     * @throws ParseException
     */
    public String saveValueCalcAfter() throws ParseException {
        getLogger().info("ref network no : " + getRefNetworkNo());
        getLogger().info("acc no : " + getCodAccNo());
        getLogger().info("origial currency : " + getNameCurrency());
        getLogger().info("Amount Acy : " + getAmtTxnAcy());
        getLogger().info("Amount Tcy : " + getAmtTxnTcy());
        getLogger().info("Currency Destination : " + getCurrencyCode());
        getLogger().info("Destination Amount : " + getDestAmount());
		getLogger().info("Rate Destination : " + getSellRate());

        MCRTrxTableDAO mcrtrxDAO = new MCRTrxTableDAO(getHSession());
        MCRTrxTable mcrtrxtable = new MCRTrxTable();

        getLogger().debug("mcr : " + mcrtrxtable);

        java.util.Date sydate = new java.util.Date();
        this.datTxn = sydate;

        mcrtrxtable.setCodOrgBrn(getCodOrgBrn());
        mcrtrxtable.setDatTxn(sydate);
        mcrtrxtable.setIdUser(getIdUser());
        mcrtrxtable.setRefNo(getRefNetworkNo());
        mcrtrxtable.setAcctNo(getCodAccNo());
        mcrtrxtable.setOriCcy(getNameCurrency());
        mcrtrxtable.setOriAmt(getAmtTxnAcy());
        mcrtrxtable.setUsdAmt(getAmtTxnTcy());
        mcrtrxtable.setDesCcy(getCurrencyCode());
        mcrtrxtable.setDesAmt(getDestAmount());
		mcrtrxtable.setDesRet(getSellRate());
		mcrtrxtable.setCodAcctTitle(getCodAcctTitle());

        mcrtrxDAO.insert(mcrtrxtable);

        getLogger().debug("END INSERT");


        return SUCCESS;
    }

    //service menu 29402 --End
    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the amtTxn
     */
    public String getAmtTxn() {
        return amtTxn;
    }

    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(String amtTxn) {
        this.amtTxn = amtTxn;
    }

    /**
     * @return the model
     */
    public CytmRates getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(CytmRates model) {
        this.model = model;
    }

    /**
     * @return the codCcy2
     */
    public String getCodCcy1() {
        return codCcy1;
    }

    /**
     * @param codCcy1 
     */
    public void setCodCcy1(String codCcy1) {
        this.codCcy1 = codCcy1;
    }

    /**
     * @return the buyRate
     */
    public BigDecimal getBuyRate() {
        return buyRate;
    }

    /**
     * @param buyRate the buyRate to set
     */
    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    /**
     * @return the modelpk
     */
    public CytmRatesPK getModelpk() {
        return modelpk;
    }

    /**
     * @param modelpk the modelpk to set
     */
    public void setModelpk(CytmRatesPK modelpk) {
        this.modelpk = modelpk;
    }

    /**
     * @return the modelList
     */
    public List<Map> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<Map> modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        getLogger().debug("SET : " + currencyCode);
        this.currencyCode = currencyCode;
    }

    /**
     * @return the sellRate
     */
    public BigDecimal getSellRate() {
        return sellRate;
    }

    /**
     * @param sellRate the sellRate to set
     */
    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    /**
     * @return the refNetNo
     */
    public String getRefNetworkNo() {
        return this.refNetworkNo;
    }

    /**
     * @param refNetworkNo 
     */
    public void setRefNetworkNo(String refNetworkNo) {
        this.refNetworkNo = refNetworkNo;
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
     * @return the pmtxn
     */
    public PmTxnLog getPmtxn() {
        return pmtxn;
    }

    /**
     * 
     * @return
     */
    public String getNameCurrency() {
        return nameCurrency;
    }

    /**
     * public Integer getCodCcy() { return codCcy; }
     *
     * public MCRCurrencyRate getMcrcurrencyrate() { return mcrcurrencyrate;
     *
     * /
     *
     **
     * @return the codAccNo
     */
    public String getCodAccNo() {
        return codAccNo;
    }

    /**
     * @param codAccNo the codAccNo to set
     */
    public void setCodAccNo(String codAccNo) {
        this.codAccNo = codAccNo;
    }

    /**
     * @return the amtTxnLcy
     */
    public Integer getAmtTxnAcy() {
        return amtTxnAcy;
    }

    /**
     * @param amtTxnAcy 
     */
    public void setAmtTxnAcy(Integer amtTxnAcy) {
        this.amtTxnAcy = amtTxnAcy;
    }

    /**
     * @return the amtTxnTcy
     */
    public Integer getAmtTxnTcy() {
        return amtTxnTcy;
    }

    /**
     * @param amtTxnTcy the amtTxnTcy to set
     */
    public void setAmtTxnTcy(Integer amtTxnTcy) {
        this.amtTxnTcy = amtTxnTcy;
    }

    /**
     * @return the destAmount
     */
    public BigDecimal getDestAmount() {
        return destAmount;
    }

    /**
     * @param destAmount the destAmount to set
     */
    public void setDestAmount(BigDecimal destAmount) {
        this.destAmount = destAmount;
    }

    /**
     * @return the saveVal
     */
    public Boolean getSaveVal() {
        return saveVal;
    }

    /**
     * @param saveVal the saveVal to set
     */
    public void setSaveVal(Boolean saveVal) {
        this.saveVal = saveVal;
    }

    /**
     * @param nameCurrency the nameCurrency to set
     */
    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
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
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the destRate
     */
    public BigDecimal getDestRate() {
        return destRate;
    }

    /**
     * @param destRate the destRate to set
     */
    public void setDestRate(BigDecimal destRate) {
        this.destRate = destRate;
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
