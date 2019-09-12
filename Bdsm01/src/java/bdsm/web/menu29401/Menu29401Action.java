/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu29401;


import bdsm.web.BaseContentAction;
import bdsm.web.MCRCalcBeforeServices;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v00022309
 */
public class Menu29401Action extends BaseContentAction {
    private String codCcy1;
    private String amtTxn;
    private String buyRate;
    private String usdEquivalent;
    private String namCcyShort;
    private String currencyCode;
    private String sellRate;
    private String amtdestination;
    
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
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
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
     * 
     * @return
     */
    public String getCalc(){
        Map mapModel = new HashMap();
        
        getLogger().info("amtTxn = " + getAmtTxn());
        getLogger().info("codCcy1 = " + getCodCcy1());
        
        MCRCalcBeforeServices mcrbefore = new MCRCalcBeforeServices(session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken(), this.getServletRequest());
        mapModel = mcrbefore.getBuyRateValue(codCcy1,amtTxn);
        
        getLogger().info("mapModel :" + mapModel);
        
        this.usdEquivalent = mapModel.get("buyRate").toString();
        
        getLogger().info("buyRate = " + getBuyRate());
        getLogger().info("usdEquivalent = " + getUsdEquivalent());
        
        return "calcAmt";
      
    }
    
    /**
     * 
     * @return
     */
    public String rtvRate(){
        Map mapModel = new HashMap();
        List Model = new ArrayList();
        Map maps = new HashMap();
        getLogger().info("ccyCode :>" + getCurrencyCode());
        
        MCRCalcBeforeServices mcrbefore = new MCRCalcBeforeServices(session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken(), this.getServletRequest());
        mapModel = mcrbefore.getRateDestination(currencyCode);
        
        getLogger().info("mapModel :>" + mapModel);
        
        Model = (List) mapModel.get("modelList");
        getLogger().info("model :>" + Model);
        try {
            maps = (Map) Model.get(0);
            this.sellRate = maps.get("0").toString();
        } catch (Exception e) {
            getLogger().info("sell rate :" + sellRate);
        }

        
        return "rateDes";
    }
    
    /**
     * 
     * @return
     */
    public String calcAmtDest(){
        
        BigDecimal equi = new BigDecimal(getUsdEquivalent());
        BigDecimal ret = new BigDecimal(getSellRate());

        BigDecimal amt = equi.multiply(ret);
        
        getLogger().info("usdequivalent :" + equi);
        getLogger().info("rate :" + ret);
        
        this.amtdestination = amt.toString();
        
        getLogger().info("amount destination :" + amtdestination);
        
        return "amtDest";
    }



    /**
     * @return the namCcyShort
     */
    public String getNamCcyShort() {
        return namCcyShort;
    }

    /**
     * @param namCcyShort the namCcyShort to set
     */
    public void setNamCcyShort(String namCcyShort) {
        this.namCcyShort = namCcyShort;
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
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the codCcy1
     */
    public String getCodCcy1() {
        return codCcy1;
    }

    /**
     * @param codCcy1 the codCcy1 to set
     */
    public void setCodCcy1(String codCcy1) {
        this.codCcy1 = codCcy1;
    }

    /**
     * @return the usdEquivalent
     */
    public String getUsdEquivalent() {
        return usdEquivalent;
    }

    /**
     * @param usdEquivalent the usdEquivalent to set
     */
    public void setUsdEquivalent(String usdEquivalent) {
        this.usdEquivalent = usdEquivalent;
    }

    /**
     * @return the buyRate
     */
    public String getBuyRate() {
        return buyRate;
    }

    /**
     * @param buyRate the buyRate to set
     */
    public void setBuyRate(String buyRate) {
        this.buyRate = buyRate;
    }

    /**
     * @return the sellRate
     */
    public String getSellRate() {
        return sellRate;
    }

    /**
     * @param sellRate the sellRate to set
     */
    public void setSellRate(String sellRate) {
        this.sellRate = sellRate;
    }

    /**
     * @return the amtdestination
     */
    public String getAmtdestination() {
        return amtdestination;
    }

    /**
     * @param amtdestination the amtdestination to set
     */
    public void setAmtdestination(String amtdestination) {
        this.amtdestination = amtdestination;
    }

    
    
}
