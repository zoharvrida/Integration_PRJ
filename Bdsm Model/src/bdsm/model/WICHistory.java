package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class WICHistory extends WIC implements BaseReqApprHistoryInterface, java.io.Serializable {
	private BaseReqApprHistoryModel baseReqApprHistoryModel = new BaseReqApprHistoryModel();
	
	
	public Character getProcessType() {
		return this.baseReqApprHistoryModel.getProcessType();
	}
	public void setProcessType(Character processType) {
		this.baseReqApprHistoryModel.setProcessType(processType);
	}
	
	public String getProcessRequester() {
		return this.baseReqApprHistoryModel.getProcessRequester();
	}
	public void setProcessRequester(String processRequester) {
		this.baseReqApprHistoryModel.setProcessRequester(processRequester);
	}
	
	public String getProcessApprover() {
		return this.baseReqApprHistoryModel.getProcessApprover();
	}
	public void setProcessApprover(String processApprover) {
		this.baseReqApprHistoryModel.setProcessApprover(processApprover);
	}
	
	public java.sql.Timestamp getDtmCreatedOld() {
		return this.baseReqApprHistoryModel.getDtmCreatedOld();
	}
	public void setDtmCreatedOld(java.sql.Timestamp dtmCreatedOld) {
		this.baseReqApprHistoryModel.setDtmCreatedOld(dtmCreatedOld);
	}
	
	public java.sql.Timestamp getDtmUpdatedOld() {
		return this.baseReqApprHistoryModel.getDtmUpdatedOld();
	}
	public void setDtmUpdatedOld(java.sql.Timestamp dtmUpdatedOld) {
		this.baseReqApprHistoryModel.setDtmUpdatedOld(dtmUpdatedOld);
	}
	
}
