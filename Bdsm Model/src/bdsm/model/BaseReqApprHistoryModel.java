package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaseReqApprHistoryModel extends BaseHistoryModel implements BaseReqApprHistoryInterface, java.io.Serializable {
	private Character processType;
	private String processRequester;
	private String processApprover;
	

	public Character getProcessType() {
		return this.processType;
	}
	public void setProcessType(Character processType) {
		this.processType = processType;
	}

	public String getProcessRequester() {
		return this.processRequester;
	}
	public void setProcessRequester(String processRequester) {
		this.processRequester = processRequester;
	}

	public String getProcessApprover() {
		return this.processApprover;
	}
	public void setProcessApprover(String processApprover) {
		this.processApprover = processApprover;
	}

}
