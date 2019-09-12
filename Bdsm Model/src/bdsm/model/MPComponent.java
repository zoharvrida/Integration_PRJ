package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MPComponent extends BaseModel {
	public static final String COMP_COMMITMENT = "C";
	public static final String COMP_LOGIC = "L";
	public static final String COMP_PARAMETER = "P";
	public static final String COMP_READ_ONLY = "R";
	
	private Integer code;
	private String name;
	private String type;
	private String dataType;
	
	
	public Integer getCode() {
		return this.code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	
	
	/* Hibernate to Database properties */
	
	protected Character getTypeDB() {
		return ((this.type == null)? null: this.type.charAt(0));
	}
	protected void setTypeDB(Character typeDB) {
		this.type = (typeDB == null)? null: typeDB.toString();
	}
	
	protected Character getDataTypeDB() {
		return ((this.dataType == null)? null: this.dataType.charAt(0));
	}
	protected void setDataTypeDB(Character dataTypeDB) {
		this.dataType = (dataTypeDB == null)? null: dataTypeDB.toString();
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",code=").append(this.code)
			.append(",name=").append(this.name)
			.append(",type=").append(this.type)
			.append(",dataType=").append(this.dataType)
			.append("}");
    	
		return sb.toString();
	}
}
