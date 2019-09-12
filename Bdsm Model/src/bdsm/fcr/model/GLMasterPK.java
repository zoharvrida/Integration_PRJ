package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class GLMasterPK implements java.io.Serializable {
	private Integer codCCBranch;
	private Integer codGLAcct;
	private Integer codGLAcctCcy;
	private String flgMntStatus;
	private Integer codEntityVpd;


	public Integer getCodCCBranch() {
		return this.codCCBranch;
	}
	public void setCodCCBranch(Integer codCCBranch) {
		this.codCCBranch = codCCBranch;
	}

	public Integer getCodGLAcct() {
		return this.codGLAcct;
	}
	public void setCodGLAcct(Integer codGLAcct) {
		this.codGLAcct = codGLAcct;
	}

	public Integer getCodGLAcctCcy() {
		return this.codGLAcctCcy;
	}
	public void setCodGLAcctCcy(Integer codGLAcctCcy) {
		this.codGLAcctCcy = codGLAcctCcy;
	}

	public String getFlgMntStatus() {
		return flgMntStatus;
	}
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}

	public Integer getCodEntityVpd() {
		return codEntityVpd;
	}
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}



	/* Hibernate to Database properties */
	protected Character getFlgMntStatusDB() {
		return ((this.flgMntStatus == null) ? null : this.flgMntStatus.charAt(0));
	}

	protected void setFlgMntStatusDB(Character flgMntStatusDB) {
		this.flgMntStatus = (flgMntStatusDB == null) ? null : flgMntStatusDB.toString();
	}


	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
				.append("{")
				.append("codCCBranch=").append(this.codCCBranch)
				.append(",codGLAcct=").append(this.codGLAcct)
				.append(",codGLAcctCcy=").append(this.codGLAcctCcy)
				.append(",flgMntStatus=").append(this.flgMntStatus)
				.append(",codEntityVpd=").append(this.codEntityVpd)
				.append("}");

		return sb.toString();
	}

}
