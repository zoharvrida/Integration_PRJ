/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.fcr.model.BDSMBICloseRate;
import bdsm.util.BdsmUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.LLDDAO;
import bdsmhost.fcr.dao.BDSMBICloseRateDAO;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author 00110310
 */
public class ValasMidRateAction extends BaseHostAction {

    private BDSMBICloseRate modelClose;
    private String dateTXN;
    private String ccyName;
    private String amtTxn;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    public String doCalcMidRate() {
        LLDDAO dao = new LLDDAO(getHSession());
        BigDecimal dec = null;
        getLogger().debug("VALUE :" + amtTxn + " CCY :" + ccyName);
        boolean flag = true;
        try {
            String amountLast = BdsmUtil.convertDecimalSeparator(amtTxn);
            if("INVALID".equalsIgnoreCase(amountLast)){
                    flag = false;
                }
            /*
            } else if (count < countLess) {
                if ((count == 1 || count == 0)) {
                    this.amtTxn = this.amtTxn.replace(".", "");
                    this.amtTxn = this.amtTxn.replace(",", ".");
                } else {
                    getLogger().debug("AMOUNT TXN INVALID 2 :" + amtTxn);
                    this.amtTxn = "0.0";
                    this.addActionError("NOT Amount Value");
                    flag = false;
                }
            } else if (count == countLess) {
                if((countLess == 1 || countLess == 0)){
                    this.amtTxn = this.amtTxn.replace(",", "");
                } else {
                    getLogger().debug("AMOUNT TXN SAME :" + amtTxn);
                    this.amtTxn = "0.0";
                    this.addActionError("NOT Amount Value");
                    flag = false;
                }
            } else {
                getLogger().debug("AMOUNT TXN :" + amtTxn);
                this.amtTxn = "0.0";
                this.addActionError("NOT Amount Value");
                flag = false;
            }
            getLogger().debug("AMOUNT :" + amtTxn);*/
            if (flag) {
                getLogger().debug("FLAG TRUE :" + amountLast);
                dec = new BigDecimal(amountLast);
            try {
                    BigDecimal hasil = dao.getEquivUSDAmount(dec, ccyName);
                    getLogger().debug("CALCULATE :" + hasil);
                this.amtTxn = hasil.toString();
            } catch (Exception e) {
                getLogger().debug("FAILED get Rate");
                getLogger().debug(e, e);
                    super.setErrorCode("valas.Error.Calculate");
                }
            }
        } catch (Exception e) {
            getLogger().debug("NOT Big Decimal");
            getLogger().info(e, e);
            super.setErrorCode("valas.Error.Number");
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String doGetMidrate() {
        LLDDAO dao = new LLDDAO(getHSession());
        setModelClose(new BDSMBICloseRate());
        try {
            getLogger().debug("CCY NAME :" + this.ccyName);
            BigDecimal dec = dao.getRateByNamCcy(getCcyName());
            getLogger().debug("CCY RATE :" + dec.toPlainString());
            getModelClose().setMidRate(dec.doubleValue());
            dao.evictObjectFromPersistenceContext(getModelClose());
        } catch (Exception e) {
            getLogger().debug("FAILED get Rate");
            getLogger().debug(e, e);
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String doCalcDatMidrate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        BDSMBICloseRateDAO dao = new BDSMBICloseRateDAO(getHSession());
        List<BDSMBICloseRate> biRate = dao.getBIDate(840);

        Date batchDate;
        int incr = 0;
        int realRates = 0;
        Calendar calendar = Calendar.getInstance();
        try {
            synchronized (DateUtility.DATE_TIME_SLASH_DDMMMYYYY) {
                batchDate = DateUtils.truncate(DateUtility.DATE_TIME_SLASH_DDMMMYYYY
                        .parse(dateTXN.replaceAll("-", "")), java.util.Calendar.DATE);

                //calendar.setTime(batchDate);
                for (BDSMBICloseRate rates : biRate) {
                    if (rates != null) {
                        if (incr == 0) {
                            realRates = rates.getDtmInsertLog().compareTo(batchDate);
                            if (realRates >= 0) {
                                setModelClose(rates);
                                break;
                            }
                        } else if (incr == 1) {
                            if (realRates < 0) {
                                setModelClose(rates);
                                break;
                            }
                        }
                        incr++;
                    }
                }
                dateTXN = sdf.format(getModelClose().getCompositeId().getDateEff());
            }
            dao.evictObjectFromPersistenceContext(getModelClose());
        } catch (Exception ex) {
            getLogger().info(ex, ex);
        }
        return SUCCESS;
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
     * @return the ccyName
     */
    public String getCcyName() {
        return ccyName;
    }

    /**
     * @param ccyName the ccyName to set
     */
    public void setCcyName(String ccyName) {
        this.ccyName = ccyName;
    }

    /**
     * @return the modelClose
     */
    public BDSMBICloseRate getModelClose() {
        return modelClose;
    }

    /**
     * @param modelClose the modelClose to set
     */
    public void setModelClose(BDSMBICloseRate modelClose) {
        this.modelClose = modelClose;
    }
}
