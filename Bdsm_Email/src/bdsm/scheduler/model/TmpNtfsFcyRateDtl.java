/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author bdsm
 */
public class TmpNtfsFcyRateDtl extends BaseModel {

	private int id;
    private String bdsmBatch;
    private String Identifier;
    private String refTxnNo;
    private Integer codOrgBrn;
    private Integer refSysTrAudNo;
    private Integer refSubseqNo;
    private Date datValue;
    private Date datPost;
    private String infoType;
    private Double rateConvTxn;
    private Double ratConvBds;
    private String codTask;
    private Integer codTxnMnemonic;
    private String buyCcyShortNam;
    private Double buyAmt;
    private String soldCcyShortNam;
    private String codPurOrSell;
    private Integer codLob;
    private String namCustShrt;
    private String respCode;

    /**
     * @return the bdsmBatch
     */
    public String getBdsmBatch() {
        return bdsmBatch;
    }

    /**
     * @param bdsmBatch the bdsmBatch to set
     */
    public void setBdsmBatch(String bdsmBatch) {
        this.bdsmBatch = bdsmBatch;
    }

    /**
     * @return the Identifier
     */
    public String getIdentifier() {
        return Identifier;
    }

    /**
     * @param Identifier the Identifier to set
     */
    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }

    /**
     * @return the refTxnNo
     */
    public String getRefTxnNo() {
        return refTxnNo;
    }

    /**
     * @param refTxnNo the refTxnNo to set
     */
    public void setRefTxnNo(String refTxnNo) {
        this.refTxnNo = refTxnNo;
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
     * @return the refSysTrAudNo
     */
    public Integer getRefSysTrAudNo() {
        return refSysTrAudNo;
    }

    /**
     * @param refSysTrAudNo the refSysTrAudNo to set
     */
    public void setRefSysTrAudNo(Integer refSysTrAudNo) {
        this.refSysTrAudNo = refSysTrAudNo;
    }

    /**
     * @return the refSubseqNo
     */
    public Integer getRefSubseqNo() {
        return refSubseqNo;
    }

    /**
     * @param refSubseqNo the refSubseqNo to set
     */
    public void setRefSubseqNo(Integer refSubseqNo) {
        this.refSubseqNo = refSubseqNo;
    }

    /**
     * @return the datValue
     */
    public Date getDatValue() {
        return datValue;
    }

    /**
     * @param datValue the datValue to set
     */
    public void setDatValue(Date datValue) {
        this.datValue = datValue;
    }

    /**
     * @return the datPost
     */
    public Date getDatPost() {
        return datPost;
    }

    /**
     * @param datPost the datPost to set
     */
    public void setDatPost(Date datPost) {
        this.datPost = datPost;
    }

    /**
     * @return the infoType
     */
    public String getInfoType() {
        return infoType;
    }

    /**
     * @param infoType the infoType to set
     */
    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    /**
     * @return the rateConvTxn
     */
    public Double getRateConvTxn() {
        return rateConvTxn;
    }

    /**
     * @param rateConvTxn the rateConvTxn to set
     */
    public void setRateConvTxn(Double rateConvTxn) {
        this.rateConvTxn = rateConvTxn;
    }

    /**
     * @return the ratConvBds
     */
    public Double getRatConvBds() {
        return ratConvBds;
    }

    /**
     * @param ratConvBds the ratConvBds to set
     */
    public void setRatConvBds(Double ratConvBds) {
        this.ratConvBds = ratConvBds;
    }

    /**
     * @return the codTask
     */
    public String getCodTask() {
        return codTask;
    }

    /**
     * @param codTask the codTask to set
     */
    public void setCodTask(String codTask) {
        this.codTask = codTask;
    }

    /**
     * @return the codTxnMnemonic
     */
    public Integer getCodTxnMnemonic() {
        return codTxnMnemonic;
    }

    /**
     * @param codTxnMnemonic the codTxnMnemonic to set
     */
    public void setCodTxnMnemonic(Integer codTxnMnemonic) {
        this.codTxnMnemonic = codTxnMnemonic;
    }

    /**
     * @return the buyCcyShortNam
     */
    public String getBuyCcyShortNam() {
        return buyCcyShortNam;
    }

    /**
     * @param buyCcyShortNam the buyCcyShortNam to set
     */
    public void setBuyCcyShortNam(String buyCcyShortNam) {
        this.buyCcyShortNam = buyCcyShortNam;
    }

    /**
     * @return the buyAmt
     */
    public Double getBuyAmt() {
        return buyAmt;
    }

    /**
     * @param buyAmt the buyAmt to set
     */
    public void setBuyAmt(Double buyAmt) {
        this.buyAmt = buyAmt;
    }

    /**
     * @return the soldCcyShortNam
     */
    public String getSoldCcyShortNam() {
        return soldCcyShortNam;
    }

    /**
     * @param soldCcyShortNam the soldCcyShortNam to set
     */
    public void setSoldCcyShortNam(String soldCcyShortNam) {
        this.soldCcyShortNam = soldCcyShortNam;
    }

    /**
     * @return the codPurOrSell
     */
    public String getCodPurOrSell() {
        return codPurOrSell;
    }

    /**
     * @param codPurOrSell the codPurOrSell to set
     */
    public void setCodPurOrSell(String codPurOrSell) {
        this.codPurOrSell = codPurOrSell;
    }

    /**
     * @return the codLob
     */
    public Integer getCodLob() {
        return codLob;
    }

    /**
     * @param codLob the codLob to set
     */
    public void setCodLob(Integer codLob) {
        this.codLob = codLob;
    }

    /**
     * @return the namCustShrt
     */
    public String getNamCustShrt() {
        return namCustShrt;
    }

    /**
     * @param namCustShrt the namCustShrt to set
     */
    public void setNamCustShrt(String namCustShrt) {
        this.namCustShrt = namCustShrt;
    }

    /**
     * @return the respCode
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
