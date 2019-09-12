/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author v00019722
 */
public class TmpSMSTrans {
    private TmpSMSTransPK compositeID;
    private String flg_cust_typ;
    private String ref_phone_mobile;
    private String ref_cust_email;
    private String cod_acct_title;
    private BigDecimal acctbrn;
    private BigDecimal cod_lob;
    private String cod_txn_mnemonic;
    private String cod_txn_literal;
    private String flg_drcr;
    private String acctccy;
    private BigDecimal amt_acct;
    private BigDecimal amt_txn_lcy;
    private String txt_txn_desc;
    private String cod_msg_typ;
    private Timestamp dat_txn;
    private String udfacct;
    private String udfcif;
    private String cod_user_id;

    /**
     * @return the compositeID
     */
    public TmpSMSTransPK getCompositeID() {
        return compositeID;
    }

    /**
     * @param compositeID the compositeID to set
     */
    public void setCompositeID(TmpSMSTransPK compositeID) {
        this.compositeID = compositeID;
    }

    /**
     * @return the flg_cust_typ
     */
    public String getFlg_cust_typ() {
        return flg_cust_typ;
    }

    /**
     * @param flg_cust_typ the flg_cust_typ to set
     */
    public void setFlg_cust_typ(String flg_cust_typ) {
        this.flg_cust_typ = flg_cust_typ;
    }

    /**
     * @return the ref_phone_mobile
     */
    public String getRef_phone_mobile() {
        return ref_phone_mobile;
    }

    /**
     * @param ref_phone_mobile the ref_phone_mobile to set
     */
    public void setRef_phone_mobile(String ref_phone_mobile) {
        this.ref_phone_mobile = ref_phone_mobile;
    }

    /**
     * @return the ref_cust_email
     */
    public String getRef_cust_email() {
        return ref_cust_email;
    }

    /**
     * @param ref_cust_email the ref_cust_email to set
     */
    public void setRef_cust_email(String ref_cust_email) {
        this.ref_cust_email = ref_cust_email;
    }

    /**
     * @return the cod_acct_title
     */
    public String getCod_acct_title() {
        return cod_acct_title;
    }

    /**
     * @param cod_acct_title the cod_acct_title to set
     */
    public void setCod_acct_title(String cod_acct_title) {
        this.cod_acct_title = cod_acct_title;
    }

    /**
     * @return the acctbrn
     */
    public BigDecimal getAcctbrn() {
        return acctbrn;
    }

    /**
     * @param acctbrn the acctbrn to set
     */
    public void setAcctbrn(BigDecimal acctbrn) {
        this.acctbrn = acctbrn;
    }

    /**
     * @return the cod_lob
     */
    public BigDecimal getCod_lob() {
        return cod_lob;
    }

    /**
     * @param cod_lob the cod_lob to set
     */
    public void setCod_lob(BigDecimal cod_lob) {
        this.cod_lob = cod_lob;
    }

    /**
     * @return the cod_txn_mnemonic
     */
    public String getCod_txn_mnemonic() {
        return cod_txn_mnemonic;
    }

    /**
     * @param cod_txn_mnemonic the cod_txn_mnemonic to set
     */
    public void setCod_txn_mnemonic(String cod_txn_mnemonic) {
        this.cod_txn_mnemonic = cod_txn_mnemonic;
    }

    /**
     * @return the cod_txn_literal
     */
    public String getCod_txn_literal() {
        return cod_txn_literal;
    }

    /**
     * @param cod_txn_literal the cod_txn_literal to set
     */
    public void setCod_txn_literal(String cod_txn_literal) {
        this.cod_txn_literal = cod_txn_literal;
    }

    /**
     * @return the flg_drcr
     */
    public String getFlg_drcr() {
        return flg_drcr;
    }

    /**
     * @param flg_drcr the flg_drcr to set
     */
    public void setFlg_drcr(String flg_drcr) {
        this.flg_drcr = flg_drcr;
    }

    /**
     * @return the acctccy
     */
    public String getAcctccy() {
        return acctccy;
    }

    /**
     * @param acctccy the acctccy to set
     */
    public void setAcctccy(String acctccy) {
        this.acctccy = acctccy;
    }

    /**
     * @return the amt_acct
     */
    public BigDecimal getAmt_acct() {
        return amt_acct;
    }

    /**
     * @param amt_acct the amt_acct to set
     */
    public void setAmt_acct(BigDecimal amt_acct) {
        this.amt_acct = amt_acct;
    }

    /**
     * @return the amt_txn_lcy
     */
    public BigDecimal getAmt_txn_lcy() {
        return amt_txn_lcy;
    }

    /**
     * @param amt_txn_lcy the amt_txn_lcy to set
     */
    public void setAmt_txn_lcy(BigDecimal amt_txn_lcy) {
        this.amt_txn_lcy = amt_txn_lcy;
    }

    /**
     * @return the txt_txn_desc
     */
    public String getTxt_txn_desc() {
        return txt_txn_desc;
    }

    /**
     * @param txt_txn_desc the txt_txn_desc to set
     */
    public void setTxt_txn_desc(String txt_txn_desc) {
        this.txt_txn_desc = txt_txn_desc;
    }

    /**
     * @return the cod_msg_typ
     */
    public String getCod_msg_typ() {
        return cod_msg_typ;
    }

    /**
     * @param cod_msg_typ the cod_msg_typ to set
     */
    public void setCod_msg_typ(String cod_msg_typ) {
        this.cod_msg_typ = cod_msg_typ;
    }

    /**
     * @return the dat_txn
     */
    public Timestamp getDat_txn() {
        return dat_txn;
    }

    /**
     * @param dat_txn the dat_txn to set
     */
    public void setDat_txn(Timestamp dat_txn) {
        this.dat_txn = dat_txn;
    }

    /**
     * @return the udfacct
     */
    public String getUdfacct() {
        return udfacct;
    }

    /**
     * @param udfacct the udfacct to set
     */
    public void setUdfacct(String udfacct) {
        this.udfacct = udfacct;
    }

    /**
     * @return the udfcif
     */
    public String getUdfcif() {
        return udfcif;
    }

    /**
     * @param udfcif the udfcif to set
     */
    public void setUdfcif(String udfcif) {
        this.udfcif = udfcif;
    }

    /**
     * @return the cod_user_id
     */
    public String getCod_user_id() {
        return cod_user_id;
    }

    /**
     * @param cod_user_id the cod_user_id to set
     */
    public void setCod_user_id(String cod_user_id) {
        this.cod_user_id = cod_user_id;
    }
    
    public String rearrangeString(String delimiter) {
        StringBuilder SMS = new StringBuilder();
        SMS.append(this.compositeID.getCod_cust()).append(delimiter).
                append(this.flg_cust_typ==null?"":this.flg_cust_typ).append(delimiter).
                append(this.ref_phone_mobile==null?"":this.ref_phone_mobile).append(delimiter).
                append(this.ref_cust_email==null?"":this.ref_cust_email).append(delimiter).
                append(this.compositeID.getCod_acct_no()==null?"":this.compositeID.getCod_acct_no()).append(delimiter).
                append(this.cod_acct_title==null?"":this.cod_acct_title).append(delimiter).
                append(this.acctbrn==null?"":this.acctbrn).append(delimiter).
                append(this.cod_lob==null?"":this.cod_lob).append(delimiter).
                append(this.cod_txn_mnemonic==null?"":this.cod_txn_mnemonic.trim()).append(delimiter).
                append(this.cod_txn_literal==null?"":this.cod_txn_literal.trim()).append(delimiter).
                append(this.flg_drcr==null?"":this.flg_drcr).append(delimiter).
                append(this.acctccy==null?"":this.acctccy).append(delimiter).
                append(this.amt_acct==null?"":this.amt_acct.setScale(2)).append(delimiter).
                append(this.amt_txn_lcy==null?"":this.amt_txn_lcy.setScale(2)).append(delimiter).
                append(this.txt_txn_desc==null?"":this.txt_txn_desc).append(delimiter).
                append(this.cod_msg_typ==null?"":this.cod_msg_typ).append(delimiter).
                append(this.dat_txn==null?"":this.dat_txn).append(delimiter).
                append(this.udfacct==null?"":this.udfacct).append(delimiter).
                append(this.udfcif==null?"":this.udfcif).append(delimiter).
                append(this.cod_user_id==null?"":this.cod_user_id);     
        return SMS.toString();
    }
}
