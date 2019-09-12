package bdsm.model;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MfcNoBook_LLD extends BaseModel {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private MfcNoBookPK compositeId;
	private String typAcct;
	private Date dtPost;
	private String strDtPost;
	private Date dtValue;
	private Timestamp dtmTxn;
	private String ccyTxn;
	private Double amtTxn;
	private Double ratFcyIdr;
	private Double amtTxnLcy;
	private Double ratUsdIdr;
	private Double amtTxnUsd;
	private String txnDesc;
	private Integer txnBranch;
	private String status;
	private String idTxn;
	private Integer documentType;
	private String channelId;
	
	
	public MfcNoBook_LLD() {}
	
	public MfcNoBook_LLD(MfcNoBookPK compositeId) {
		this.compositeId = compositeId;
	}
	

	public MfcNoBookPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(MfcNoBookPK compositeId) {
		this.compositeId = compositeId;
	}


	public String getTypAcct() {
		return this.typAcct;
	}
	public void setTypAcct(String typAcct) {
		this.typAcct = typAcct;
	}

	public Date getDtPost() {
		return this.dtPost;
	}
	public void setDtPost(Date dtPost) {
		this.dtPost = dtPost;
	}

	public String getStrDtPost() {
		return this.strDtPost;
	}
	public void setStrDtPost(String strDtPost) throws ParseException {
		this.dtPost = dateFormat.parse(strDtPost);
	}

	public Date getDtValue() {
		return this.dtValue;
	}
	public void setDtValue(Date dtValue) {
		this.dtValue = dtValue;
	}

	public Timestamp getDtmTxn() {
		return this.dtmTxn;
	}
	public void setDtmTxn(Timestamp dtmTxn) {
		this.dtmTxn = dtmTxn;
	}

	public String getCcyTxn() {
		return this.ccyTxn;
	}
	public void setCcyTxn(String ccyTxn) {
		this.ccyTxn = ccyTxn;
	}

	public Double getAmtTxn() {
		return this.amtTxn;
	}
	public void setAmtTxn(Double amtTxn) {
		this.amtTxn = amtTxn;
	}

	public Double getRatFcyIdr() {
		return this.ratFcyIdr;
	}
	public void setRatFcyIdr(Double ratFcyIdr) {
		this.ratFcyIdr = ratFcyIdr;
	}

	public Double getAmtTxnLcy() {
		return this.amtTxnLcy;
	}
	public void setAmtTxnLcy(Double amtTxnLcy) {
		this.amtTxnLcy = amtTxnLcy;
	}

	public Double getRatUsdIdr() {
		return this.ratUsdIdr;
	}
	public void setRatUsdIdr(Double ratUsdIdr) {
		this.ratUsdIdr = ratUsdIdr;
	}

	public Double getAmtTxnUsd() {
		return this.amtTxnUsd;
	}
	public void setAmtTxnUsd(Double amtTxnUsd) {
		this.amtTxnUsd = amtTxnUsd;
	}

	public String getTxnDesc() {
		return this.txnDesc;
	}
	public void setTxnDesc(String txnDesc) {
		this.txnDesc = txnDesc;
	}

	public Integer getTxnBranch() {
		return this.txnBranch;
	}
	public void setTxnBranch(Integer txnBranch) {
		this.txnBranch = txnBranch;
	}

	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdTxn() {
		return this.idTxn;
	}
	public void setIdTxn(String idTxn) {
		this.idTxn = idTxn;
	}

	public Integer getDocumentType() {
		return this.documentType;
	}
	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}

	public String getChannelId() {
		return this.channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}



	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("compositeId=").append(this.compositeId)
			.append(",typAcct=").append(this.typAcct)
			.append(",dtPost=").append(this.dtPost)
			.append(",strDtPost=").append(this.strDtPost)
			.append(",dtValue=").append(this.dtValue)
			.append(",dtmTxn=").append(this.dtmTxn)
			.append(",ccyTxn=").append(this.ccyTxn)
			.append(",amtTxn=").append(this.amtTxn)
			.append(",ratFcyIdr=").append(this.ratFcyIdr)
			.append(",amtTxnLcy=").append(this.amtTxnLcy)
			.append(",ratUsdIdr=").append(this.ratUsdIdr)
			.append(",amtTxnUsd=").append(this.amtTxnUsd)
			.append(",txnDesc=").append(this.txnDesc)
			.append(",txnBranch=").append(this.txnBranch)
			.append(",status=").append(this.status)
			.append(",idTxn=").append(this.idTxn)
			.append(",documentType=").append(this.documentType)
			.append(",channelId=").append(this.channelId)
			.append("}");
		
		return sb.toString();
	
	}
}