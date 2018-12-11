package bdsm.scheduler.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class TmpTxnUploadDetail extends TmpTxnUploadParent implements java.io.Serializable {
    /**
     * 
     */
    public static final Integer TYPE_TXN_CASA       = 1;
    /**
     * 
     */
    public static final Integer TYPE_TXN_GL         = 3;
    /**
     * 
     */
    public static final Integer TXN_TYPE_NORMAL     = 0;
    /**
     * 
     */
    public static final Integer TXN_TYPE_ADVICE     = 1;
    /**
     * 
     */
    public static final String  COD_TXN_CASA_DEBIT  = "1008";
    /**
     * 
     */
    public static final String  COD_TXN_CASA_CREDIT = "1408";
    /**
     * 
     */
    public static final String  COD_TXN_GL_DEBIT    = "1060";
    /**
     * 
     */
    public static final String  COD_TXN_GL_CREDIT   = "1460";
    /**
     * 
     */
    public static final String  COD_DRCR_DEBIT      = "D";
    /**
     * 
     */
    public static final String  COD_DRCR_CREDIT     = "C";
	
	
	private Integer typTxn;
	private String codAcctNo;
	private Integer codCcBrn;
	private Integer codLob;
	private String codUserId;
	private Integer txnType;
	private String codTxn;
	private Integer prodCod;
	private String datTxn;
	private String codDrCr;
	private String datValue;
	private String txnCurrency;
	private java.math.BigDecimal amtTxnLcy;
	private java.math.BigDecimal amtTxnTcy;
	private java.math.BigDecimal convRate;
	private String referenceNo;
	private String refDocNo;
	private String txtTxnDesc;
	
	
    /**
     * 
     * @param header
     * @param recordId
     */
    public TmpTxnUploadDetail(TmpTxnUploadHeader header, Integer recordId) {
		this.setCompositeId(header.getCompositeId().clone());
		this.getCompositeId().setRecordId(recordId);
		this.setModuleName(header.getModuleName());
		this.setTarget("EXT_TXNUPLOAD_DETAILRECDTO");
		this.setDtmProcStart(header.getDtmProcStart());
		this.setRecType((byte) 2);
		this.setTxnType(TXN_TYPE_NORMAL);
		this.setDatTxn(header.getDateCreate());
		this.setDatValue(header.getDateCreate());
		this.setRecordType(this.getRecType().toString());
		this.setReferenceNo("1");
		this.setRefDocNo("1");
		this.setRecordName("Detail");
		this.setParentRecId(header.getCompositeId().getRecordId());
	}
	
	
    /**
     * 
     * @return
     */
    public Integer getTypTxn() {
		return this.typTxn;
	}
    /**
     * 
     * @param typTxn
     */
    public void setTypTxn(Integer typTxn) {
		this.typTxn = typTxn;
	}
	
    /**
     * 
     * @return
     */
    public String getCodAcctNo() {
		return this.codAcctNo;
	}
    /**
     * 
     * @param codAcctNo
     */
    public void setCodAcctNo(String codAcctNo) {
		this.codAcctNo = codAcctNo;
	}
	
    /**
     * 
     * @return
     */
    public Integer getCodCcBrn() {
		return this.codCcBrn;
	}
    /**
     * 
     * @param codCcBrn
     */
    public void setCodCcBrn(Integer codCcBrn) {
		this.codCcBrn = codCcBrn;
	}
	
    /**
     * 
     * @return
     */
    public Integer getCodLob() {
		return this.codLob;
	}
    /**
     * 
     * @param codLob
     */
    public void setCodLob(Integer codLob) {
		this.codLob = codLob;
	}
	
    /**
     * 
     * @return
     */
    public String getCodUserId() {
		return this.codUserId;
	}
    /**
     * 
     * @param codUserId
     */
    public void setCodUserId(String codUserId) {
		this.codUserId = codUserId;
	}
	
    /**
     * 
     * @return
     */
    public Integer getTxnType() {
		return this.txnType;
	}
    /**
     * 
     * @param txnType
     */
    public void setTxnType(Integer txnType) {
		this.txnType = txnType;
	}
	
    /**
     * 
     * @return
     */
    public String getCodTxn() {
		return this.codTxn;
	}
    /**
     * 
     * @param codTxn
     */
    public void setCodTxn(String codTxn) {
		this.codTxn = codTxn;
	}
	
    /**
     * 
     * @return
     */
    public Integer getProdCod() {
		return this.prodCod;
	}
    /**
     * 
     * @param prodCod
     */
    public void setProdCod(Integer prodCod) {
		this.prodCod = prodCod;
	}
	
    /**
     * 
     * @return
     */
    public String getDatTxn() {
		return this.datTxn;
	}
    /**
     * 
     * @param datTxn
     */
    public void setDatTxn(String datTxn) {
		this.datTxn = datTxn;
	}
	
    /**
     * 
     * @return
     */
    public String getCodDrCr() {
		return this.codDrCr;
	}
    /**
     * 
     * @param codDrCr
     */
    public void setCodDrCr(String codDrCr) {
		this.codDrCr = codDrCr;
	}
	
    /**
     * 
     * @return
     */
    public String getDatValue() {
		return this.datValue;
	}
    /**
     * 
     * @param datValue
     */
    public void setDatValue(String datValue) {
		this.datValue = datValue;
	}
	
    /**
     * 
     * @return
     */
    public String getTxnCurrency() {
		return this.txnCurrency;
	}
    /**
     * 
     * @param txnCurrency
     */
    public void setTxnCurrency(String txnCurrency) {
		this.txnCurrency = txnCurrency;
	}
	
    /**
     * 
     * @return
     */
    public java.math.BigDecimal getAmtTxnLcy() {
		return this.amtTxnLcy;
	}
    /**
     * 
     * @param amtTxnLcy
     */
    public void setAmtTxnLcy(java.math.BigDecimal amtTxnLcy) {
		this.amtTxnLcy = amtTxnLcy;
	}
	
    /**
     * 
     * @return
     */
    public java.math.BigDecimal getAmtTxnTcy() {
		return this.amtTxnTcy;
	}
    /**
     * 
     * @param amtTxnTcy
     */
    public void setAmtTxnTcy(java.math.BigDecimal amtTxnTcy) {
		this.amtTxnTcy = amtTxnTcy;
	}
	
    /**
     * 
     * @return
     */
    public java.math.BigDecimal getConvRate() {
		return this.convRate;
	}
    /**
     * 
     * @param convRate
     */
    public void setConvRate(java.math.BigDecimal convRate) {
		this.convRate = convRate;
	}
	
    /**
     * 
     * @return
     */
    public String getReferenceNo() {
		return this.referenceNo;
	}
    /**
     * 
     * @param referenceNo
     */
    public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
    /**
     * 
     * @return
     */
    public String getRefDocNo() {
		return this.refDocNo;
	}
    /**
     * 
     * @param refDocNo
     */
    public void setRefDocNo(String refDocNo) {
		this.refDocNo = refDocNo;
	}
	
    /**
     * 
     * @return
     */
    public String getTxtTxnDesc() {
		return this.txtTxnDesc;
	}
    /**
     * 
     * @param txtTxnDesc
     */
    public void setTxtTxnDesc(String txtTxnDesc) {
		this.txtTxnDesc = txtTxnDesc;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(super.toString())
			.append(",typTxn=").append(this.typTxn)
			.append(",codAcctNo=").append(this.codAcctNo)
			.append(",codCcBrn=").append(this.codCcBrn)
			.append(",codLob=").append(this.codLob)
			.append(",codUserId=").append(this.codUserId)
			.append(",txnType=").append(this.txnType)
			.append(",codTxn=").append(this.codTxn)
			.append(",prodCod=").append(this.prodCod)
			.append(",datTxn=").append(this.datTxn)
			.append(",codDrCr=").append(this.codDrCr)
			.append(",datValue=").append(this.datValue)
			.append(",txnCurrency=").append(this.txnCurrency)
			.append(",amtTxnLcy=").append(this.amtTxnLcy)
			.append(",amtTxnTcy=").append(this.amtTxnTcy)
			.append(",convRate=").append(this.convRate)
			.append(",referenceNo=").append(this.referenceNo)
			.append(",refDocNo=").append(this.refDocNo)
			.append(",txtTxnDesc=").append(this.txtTxnDesc)
			.append("}");
		
		return sb.toString();
	}
	
}
