package bdsm.model;


import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeRecord extends BaseModel implements java.io.Serializable {
	private AmortizeRecordPK compositeId;
	private BigDecimal amortizedThisMonth;
	private BigDecimal amortizedAccumalation;
	private Short termDays;
	private Date postDate;
	private String txnUploadFileId;
	private String txnUploadReferenceNo;
	
	
	public AmortizeRecordPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(AmortizeRecordPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public BigDecimal getAmortizedThisMonth() {
		return this.amortizedThisMonth;
	}
	public void setAmortizedThisMonth(BigDecimal amortizedThisMonth) {
		this.amortizedThisMonth = amortizedThisMonth;
	}
	
	public BigDecimal getAmortizedAccumalation() {
		return this.amortizedAccumalation;
	}
	public void setAmortizedAccumalation(BigDecimal amortizedAccumalation) {
		this.amortizedAccumalation = amortizedAccumalation;
	}
	
	public Short getTermDays() {
		return this.termDays;
	}
	public void setTermDays(Short termDays) {
		this.termDays = termDays;
	}
	
	public Date getPostDate() {
		return this.postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
	public String getTxnUploadFileId() {
		return this.txnUploadFileId;
	}
	public void setTxnUploadFileId(String txnUploadFileId) {
		this.txnUploadFileId = txnUploadFileId;
	}
	
	public String getTxnUploadReferenceNo() {
		return this.txnUploadReferenceNo;
	}
	public void setTxnUploadReferenceNo(String txnUploadReferenceNo) {
		this.txnUploadReferenceNo = txnUploadReferenceNo;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AmortizeRecord) {
			AmortizeRecord other = (AmortizeRecord) obj;
			if (
					((this.compositeId == null)? (other.compositeId == null): this.compositeId.equals(other.compositeId))
					&& ((this.amortizedThisMonth == null)? (other.amortizedThisMonth == null): this.amortizedThisMonth.equals(other.amortizedThisMonth))
					&& ((this.amortizedAccumalation == null)? (other.amortizedAccumalation == null): this.amortizedAccumalation.equals(other.amortizedAccumalation))
					&& ((this.termDays == null)? (other.termDays == null): this.termDays.equals(other.termDays))
					&& ((this.postDate == null)? (other.postDate == null): this.postDate.equals(other.postDate))
					&& ((this.txnUploadFileId == null)? (other.txnUploadFileId == null): this.txnUploadFileId.equals(other.txnUploadFileId))
					&& ((this.txnUploadReferenceNo == null)? (other.txnUploadReferenceNo == null): this.txnUploadReferenceNo.equals(other.txnUploadReferenceNo))
				)
					return true;
		}
		
		return false;
	}
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("compositeId=").append(this.compositeId)
    		.append(",amortizedThisMonth=").append(this.amortizedThisMonth)
    		.append(",amortizedAccumalation=").append(this.amortizedAccumalation)
    		.append(",termDays=").append(this.termDays)
    		.append(",postDate=").append(this.postDate)
    		.append(",txnUploadFileId=").append(this.txnUploadFileId)
    		.append(",txnUploadReferenceNo=").append(this.txnUploadReferenceNo)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
