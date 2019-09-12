package bdsm.scheduler.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class TmpTxnUploadHeader extends TmpTxnUploadParent implements java.io.Serializable {
	private String dateCreate;
	
	
	public TmpTxnUploadHeader(String fileId, String moduleName) {
		this.setCompositeId(new TmpTxnUploadPK(fileId, 1));
		this.setModuleName(moduleName);
		this.setTarget("EXT_TXNUPLOAD_HEADERRECDTO");
		this.setDtmProcStart(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.setRecType((byte) 1);
		this.setRecordType(this.getRecType().toString());
		this.setRecordName("Header");
	}
	
	
	public String getDateCreate() {
		return this.dateCreate;
	}
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(super.toString())
			.append(",dateCreate=").append(this.dateCreate)
			.append("}");
		
		return sb.toString();
	}
	
}
