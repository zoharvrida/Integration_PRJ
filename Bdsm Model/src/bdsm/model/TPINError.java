package bdsm.model;

public class TPINError extends BaseModel {
	private String filename;
	private Integer lineNumber;
	private String reason;
	
	
	public TPINError() {}
	
	public TPINError(String filename, Integer lineNumber, String reason) {
		this.filename = filename;
		this.lineNumber = lineNumber;
		this.reason = reason;
	}
	
	
	public String getFilename() {
		return this.filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public Integer getLineNumber() {
		return this.lineNumber;
	}
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getReason() {
		return this.reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
