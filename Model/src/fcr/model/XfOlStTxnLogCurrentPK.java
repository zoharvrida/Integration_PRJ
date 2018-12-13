package bdsm.fcr.model;

/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class XfOlStTxnLogCurrentPK implements java.io.Serializable {
	private Long datTxn;
	private Integer codOrgBrn;
	private Long codUserNo;
	private Long ctrBatchNo;
	private Long refSysTrAudNo;
	private Integer refSubSeqNo;
	private Integer codMsgTyp;
	private Integer codEntityVpd;
	
	
	public Long getDatTxn() {
		return this.datTxn;
	}
	public void setDatTxn(Long datTxn) {
		this.datTxn = datTxn;
	}
	
	public Integer getCodOrgBrn() {
		return this.codOrgBrn;
	}
	public void setCodOrgBrn(Integer codOrgBrn) {
		this.codOrgBrn = codOrgBrn;
	}
	
	public Long getCodUserNo() {
		return this.codUserNo;
	}
	public void setCodUserNo(Long codUserNo) {
		this.codUserNo = codUserNo;
	}
	
	public Long getCtrBatchNo() {
		return this.ctrBatchNo;
	}
	public void setCtrBatchNo(Long ctrBatchNo) {
		this.ctrBatchNo = ctrBatchNo;
	}
	
	public Long getRefSysTrAudNo() {
		return this.refSysTrAudNo;
	}
	public void setRefSysTrAudNo(Long refSysTrAudNo) {
		this.refSysTrAudNo = refSysTrAudNo;
	}
	
	public Integer getRefSubSeqNo() {
		return this.refSubSeqNo;
	}
	public void setRefSubSeqNo(Integer refSubSeqNo) {
		this.refSubSeqNo = refSubSeqNo;
	}
	
	public Integer getCodMsgTyp() {
		return this.codMsgTyp;
	}
	public void setCodMsgTyp(Integer codMsgTyp) {
		this.codMsgTyp = codMsgTyp;
	}
	
	public Integer getCodEntityVpd() {
		return this.codEntityVpd;
	}
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}
	
}
