package bdsm.fcr.model;


import java.io.Serializable;
import java.util.Date;


/**
 * @author v00019372
 */

@SuppressWarnings("serial")
public class GLMaster implements Serializable {
	private GLMasterPK compositeId;
	private Integer codGLControlAc;
	private Integer codGLType;
	private String namGLCode;
	private Date datAcctOpened;
	private Date datTxnPosting;
	private Date datLastReconciled;
	private String flgDepAccum;
	private String flgBrnVeAllowed;


	public GLMasterPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(GLMasterPK compositeId) {
		this.compositeId = compositeId;
	}

	public Integer getCodGLControlAc() {
		return this.codGLControlAc;
	}
	public void setCodGLControlAc(Integer codGLControlAc) {
		this.codGLControlAc = codGLControlAc;
	}

	public Integer getCodGLType() {
		return this.codGLType;
	}
	public void setCodGLType(Integer codGLType) {
		this.codGLType = codGLType;
	}

	public String getNamGLCode() {
		return this.namGLCode;
	}
	public void setNamGLCode(String namGLCode) {
		this.namGLCode = namGLCode;
	}

	public Date getDatAcctOpened() {
		return this.datAcctOpened;
	}
	public void setDatAcctOpened(Date datAcctOpened) {
		this.datAcctOpened = datAcctOpened;
	}

	public Date getDatTxnPosting() {
		return this.datTxnPosting;
	}
	public void setDatTxnPosting(Date datTxnPosting) {
		this.datTxnPosting = datTxnPosting;
	}

	public Date getDatLastReconciled() {
		return this.datLastReconciled;
	}
	public void setDatLastReconciled(Date datLastReconciled) {
		this.datLastReconciled = datLastReconciled;
	}

	public String getFlgDepAccum() {
		return this.flgDepAccum;
	}
	public void setFlgDepAccum(String flgDepAccum) {
		this.flgDepAccum = flgDepAccum;
	}

	public String getFlgBrnVeAllowed() {
		return this.flgBrnVeAllowed;
	}
	public void setFlgBrnVeAllowed(String flgBrnVeAllowed) {
		this.flgBrnVeAllowed = flgBrnVeAllowed;
	}


	/* Hibernate to Database properties */
	protected Character getFlgDepAccumDB() {
		return ((this.flgDepAccum == null) ? null : this.flgDepAccum.charAt(0));
	}
	protected void setFlgDepAccumDB(Character flgDepAccumDB) {
		this.flgDepAccum = (flgDepAccumDB == null) ? null : flgDepAccumDB.toString();
	}

	protected Character getFlgBrnVeAllowedDB() {
		return ((this.flgBrnVeAllowed == null) ? null : this.flgBrnVeAllowed.charAt(0));
	}
	protected void setFlgBrnVeAllowedDB(Character flgBrnVeAllowedDB) {
		this.flgBrnVeAllowed = (flgBrnVeAllowedDB == null) ? null : flgBrnVeAllowedDB.toString();
	}	


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",compositeId=").append(this.compositeId)
			.append(",codGLControlAc=").append(this.codGLControlAc)
			.append(",codGLType=").append(this.codGLType)
			.append(",namGLCode=").append(this.namGLCode)
			.append(",datAcctOpened=").append(this.datAcctOpened)
			.append(",datTxnPosting=").append(this.datTxnPosting)
			.append(",datLastReconciled=").append(this.datLastReconciled)
			.append(",flgDepAccum=").append(this.flgDepAccum)
			.append(",flgBrnVeAllowed=").append(this.flgBrnVeAllowed)
			.append("}");
	
		return sb.toString();
	}

}
