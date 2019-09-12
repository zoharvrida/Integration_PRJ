package bdsm.model;

public class TPINStatusMaster extends BaseModel {
	private String code;
	private String description;
	
	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getCodeDB() {
		return ((this.code==null)? null: this.code.charAt(0));
	}
	protected void setCodeDB(Character code) {
		this.code = ((code==null)? null: code.toString());
	}
	
	
	public String toString() {
		return new StringBuilder("TPINStatusMaster{")
			.append("code='").append(this.getCodeDB()).append("'")
			.append("description='").append(this.getDescription()).append("'")
			.append("}")
			.toString();
	}
}
