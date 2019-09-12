package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MfcTxnDetails_LLD extends BaseModel {
	private MfcTxnDetailsPK compositeId;
	private Integer noCif;
	private String noUd;
	private String status;


	public MfcTxnDetails_LLD() {}

	public MfcTxnDetails_LLD(MfcTxnDetailsPK compositeId) {
		this.compositeId = compositeId;
	}


	public MfcTxnDetailsPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(MfcTxnDetailsPK compositeId) {
		this.compositeId = compositeId;
	}

	public Integer getNoCif() {
		return this.noCif;
	}
	public void setNoCif(Integer noCif) {
		this.noCif = noCif;
	}

	public String getNoUd() {
		return this.noUd;
	}
	public void setNoUd(String noUd) {
		this.noUd = noUd;
	}

	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("compositeId=").append(this.compositeId)
			.append(",noCif=").append(this.noCif)
			.append(",noUd=").append(this.noUd)
			.append(",status=").append(this.status)
			.append("}");
		
		return sb.toString();
	
	}
	
}
