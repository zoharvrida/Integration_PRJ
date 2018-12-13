package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class ChAcctMastPK implements java.io.Serializable {
	public static final byte ACCT_NO_LENGTH = 16;
	
	private String codAcctNo;
	private String flgMntStatus;
	private Integer codEntityVpd;
	
	
	public ChAcctMastPK() {}
	
	public ChAcctMastPK(String codAcctNo, String flgMntStatus, Integer codEntityVpd) {
		this.codAcctNo = codAcctNo;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVpd = codEntityVpd;
	}
	
	
	public String getCodAcctNo() {
		return this.codAcctNo;
	}
	public void setCodAcctNo(String codAcctNo) {
		this.codAcctNo = codAcctNo;
	}
	
	public String getFlgMntStatus() {
		return this.flgMntStatus;
	}
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}
	
	public Integer getCodEntityVpd() {
		return this.codEntityVpd;
	}
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("codAcctNo=").append(this.codAcctNo)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVpd=").append(this.codEntityVpd)
			.append("}");
		
		return sb.toString();
	}
	
}
