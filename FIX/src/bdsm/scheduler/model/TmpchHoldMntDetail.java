/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class TmpchHoldMntDetail extends BaseModel{
    private TmpchHoldMntPK compositeId;
    private Integer rectype;
    private String datTxn;
    private String codAcct;
    private Integer codBrn;
    private String amtHold;
    private String holDesc;
    private Integer earMarkType;
    private Integer earmarkreason;
    private String dateExpire;
	private String recType;
    private String recName;
    private String data;
    private Integer lengths;
    private String comments;
    private String gefuStatus;
    
    private String dtmProcStart;
    private String dtmProcFinish;
    private String validationstatus;

	/**
	 * @return the compositeId
	 */
	public TmpchHoldMntPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(TmpchHoldMntPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the rectype
	 */
	public Integer getRectype() {
		return rectype;
	}

	/**
	 * @param rectype the rectype to set
	 */
	public void setRectype(Integer rectype) {
		this.rectype = rectype;
	}

	/**
	 * @return the datTxn
	 */
	public String getDatTxn() {
		return datTxn;
	}

	/**
	 * @param datTxn the datTxn to set
	 */
	public void setDatTxn(String datTxn) {
		this.datTxn = datTxn;
	}

	/**
	 * @return the codAcct
	 */
	public String getCodAcct() {
		return codAcct;
	}

	/**
	 * @param codAcct the codAcct to set
	 */
	public void setCodAcct(String codAcct) {
		this.codAcct = codAcct;
	}

	/**
	 * @return the codBrn
	 */
	public Integer getCodBrn() {
		return codBrn;
	}

	/**
	 * @param codBrn the codBrn to set
	 */
	public void setCodBrn(Integer codBrn) {
		this.codBrn = codBrn;
	}

	/**
	 * @return the amtHold
	 */
	public String getAmtHold() {
		return amtHold;
	}

	/**
	 * @param amtHold the amtHold to set
	 */
	public void setAmtHold(String amtHold) {
		this.amtHold = amtHold;
	}

	/**
	 * @return the holDesc
	 */
	public String getHolDesc() {
		return holDesc;
	}

	/**
	 * @param holDesc the holDesc to set
	 */
	public void setHolDesc(String holDesc) {
		this.holDesc = holDesc;
	}

	/**
	 * @return the earMarkType
	 */
	public Integer getEarMarkType() {
		return earMarkType;
	}

	/**
	 * @param earMarkType the earMarkType to set
	 */
	public void setEarMarkType(Integer earMarkType) {
		this.earMarkType = earMarkType;
	}

	/**
	 * @return the earmarkreason
	 */
	public Integer getEarmarkreason() {
		return earmarkreason;
	}

	/**
	 * @param earmarkreason the earmarkreason to set
	 */
	public void setEarmarkreason(Integer earmarkreason) {
		this.earmarkreason = earmarkreason;
	}

	/**
	 * @return the dateExpire
	 */
	public String getDateExpire() {
		return dateExpire;
	}

	/**
	 * @param dateExpire the dateExpire to set
	 */
	public void setDateExpire(String dateExpire) {
		this.dateExpire = dateExpire;
	}

	/**
	 * @return the recName
	 */
	public String getRecName() {
		return recName;
	}

	/**
	 * @param recName the recName to set
	 */
	public void setRecName(String recName) {
		this.recName = recName;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the lengths
	 */
	public Integer getLengths() {
		return lengths;
	}

	/**
	 * @param lengths the lengths to set
	 */
	public void setLengths(Integer lengths) {
		this.lengths = lengths;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the gefuStatus
	 */
	public String getGefuStatus() {
		return gefuStatus;
	}

	/**
	 * @param gefuStatus the gefuStatus to set
	 */
	public void setGefuStatus(String gefuStatus) {
		this.gefuStatus = gefuStatus;
	}

	/**
	 * @return the dtmProcStart
	 */
	public String getDtmProcStart() {
		return dtmProcStart;
	}

	/**
	 * @param dtmProcStart the dtmProcStart to set
	 */
	public void setDtmProcStart(String dtmProcStart) {
		this.dtmProcStart = dtmProcStart;
	}

	/**
	 * @return the dtmProcFinish
	 */
	public String getDtmProcFinish() {
		return dtmProcFinish;
	}

	/**
	 * @param dtmProcFinish the dtmProcFinish to set
	 */
	public void setDtmProcFinish(String dtmProcFinish) {
		this.dtmProcFinish = dtmProcFinish;
	}

	/**
	 * @return the validationstatus
	 */
	public String getValidationstatus() {
		return validationstatus;
	}

	/**
	 * @param validationstatus the validationstatus to set
	 */
	public void setValidationstatus(String validationstatus) {
		this.validationstatus = validationstatus;
	}

	/**
	 * @return the recType
	 */
	public String getRecType() {
		return recType;
	}

	/**
	 * @param recType the recType to set
	 */
	public void setRecType(String recType) {
		this.recType = recType;
	}
}
