package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class XfOlStTxnLogCurrent implements java.io.Serializable {
	public static final byte REF_TXN_NO_LENGTH = 40;
	
	private XfOlStTxnLogCurrentPK pk;
	private String refTxnNo;
	private Long codCust;
	private String codAcctNo;
	private String codFromAcctNo;
	private String codToAcctNo;
	private Integer codTxnCcy;
	private java.math.BigDecimal amtTxnTcy;
	private Integer codTxnMnemonic;
	private String flgDrCr;
	private String txnNrrtv;
	private String codUserId;
	private java.sql.Timestamp datTxnStr;
	private java.sql.Timestamp datValueStr; 
	
	
	public XfOlStTxnLogCurrentPK getPk() {
		return this.pk;
	}
	public void setPk(XfOlStTxnLogCurrentPK pk) {
		this.pk = pk;
	}
	
	public String getRefTxnNo() {
		return this.refTxnNo;
	}
	public void setRefTxnNo(String refTxnNo) {
		this.refTxnNo = refTxnNo;
	}
	
	public Long getCodCust() {
		return this.codCust;
	}
	public void setCodCust(Long codCust) {
		this.codCust = codCust;
	}
	
	public String getCodAcctNo() {
		return this.codAcctNo;
	}
	public void setCodAcctNo(String codAcctNo) {
		this.codAcctNo = codAcctNo;
	}
	
	public String getCodFromAcctNo() {
		return this.codFromAcctNo;
	}
	public void setCodFromAcctNo(String codFromAcctNo) {
		this.codFromAcctNo = codFromAcctNo;
	}
	
	public String getCodToAcctNo() {
		return this.codToAcctNo;
	}
	public void setCodToAcctNo(String codToAcctNo) {
		this.codToAcctNo = codToAcctNo;
	}
	
	public Integer getCodTxnCcy() {
		return this.codTxnCcy;
	}
	public void setCodTxnCcy(Integer codTxnCcy) {
		this.codTxnCcy = codTxnCcy;
	}
	
	public java.math.BigDecimal getAmtTxnTcy() {
		return this.amtTxnTcy;
	}
	public void setAmtTxnTcy(java.math.BigDecimal amtTxnTcy) {
		this.amtTxnTcy = amtTxnTcy;
	}
	
	public Integer getCodTxnMnemonic() {
		return this.codTxnMnemonic;
	}
	public void setCodTxnMnemonic(Integer codTxnMnemonic) {
		this.codTxnMnemonic = codTxnMnemonic;
	}
	
	public String getFlgDrCr() {
		return this.flgDrCr;
	}
	public void setFlgDrCr(String flgDrCr) {
		this.flgDrCr = flgDrCr;
	}
	
	public String getTxnNrrtv() {
		return this.txnNrrtv;
	}
	public void setTxnNrrtv(String txnNrrtv) {
		this.txnNrrtv = txnNrrtv;
	}
	
	public String getCodUserId() {
		return this.codUserId;
	}
	public void setCodUserId(String codUserId) {
		this.codUserId = codUserId;
	}
	
	public java.sql.Timestamp getDatTxnStr() {
		return this.datTxnStr;
	}
	public void setDatTxnStr(java.sql.Timestamp datTxnStr) {
		this.datTxnStr = datTxnStr;
	}
	
	public java.sql.Timestamp getDatValueStr() {
		return this.datValueStr;
	}
	public void setDatValueStr(java.sql.Timestamp datValueStr) {
		this.datValueStr = datValueStr;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getFlgDrCrDB() {
		return ((this.flgDrCr == null)? null: this.flgDrCr.charAt(0));
	}
	protected void setFlgDrCrDB(Character flgDrCr) {
		this.flgDrCr = (flgDrCr == null)? null: flgDrCr.toString();
	}

}
