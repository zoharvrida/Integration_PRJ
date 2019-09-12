package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MPAcctReg extends BaseModel implements java.io.Serializable {
	private Long id;
	private String noAccount;
	private Integer codeClass;
	private java.util.Date dateCommitment;
	private java.util.Date dateExpiry;
	private Boolean deleted;
	private java.util.Set<MPAcctRegDtls> details;
	
	
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNoAccount() {
		return this.noAccount;
	}
	public void setNoAccount(String noAccount) {
		this.noAccount = noAccount;
	}
	
	public Integer getCodeClass() {
		return this.codeClass;
	}
	public void setCodeClass(Integer codeClass) {
		this.codeClass = codeClass;
	}
	
	public java.util.Date getDateCommitment() {
		return this.dateCommitment;
	}
	public void setDateCommitment(java.util.Date dateCommitment) {
		this.dateCommitment = dateCommitment;
	}
	
	public java.util.Date getDateExpiry() {
		return this.dateExpiry;
	}
	public void setDateExpiry(java.util.Date dateExpiry) {
		this.dateExpiry = dateExpiry;
	}
	
	public Boolean isDeleted() {
		return this.deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	/* Hibernate to Database properties */
	
	protected Character getDeletedDB() {
		Character result = null;
		
		if (this.deleted != null)
			result = Character.valueOf((this.deleted)? 'Y': 'N');
		
		return result;
	}
	protected void setDeletedDB(Character deletedDB) {
		Boolean result = null;
		
		if (deletedDB != null)
			result = Boolean.valueOf(deletedDB.equals('Y'));
		
		this.deleted = result;
	}
	
	
	public java.util.Set<MPAcctRegDtls> getDetails() {
		return this.details;
	}
	public void setDetails(java.util.Set<MPAcctRegDtls> details) {
		this.details = details;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("id=").append(this.id)
			.append(",noAccount=").append(this.noAccount)
			.append(",codeClass=").append(this.codeClass)
			.append(",dateCommitment=").append(this.dateCommitment)
			.append(",dateExpiry=").append(this.dateExpiry)
			.append(",deleted=").append(this.deleted)
			.append(",details=").append(this.details)
			.append("}");
    	
		return sb.toString();
	}
}
