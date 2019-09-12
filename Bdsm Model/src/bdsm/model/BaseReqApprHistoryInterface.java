package bdsm.model;


/**
 * 
 * @author v00019372
 */
public interface BaseReqApprHistoryInterface extends BaseHistoryInterface {
	Character getProcessType();
	void setProcessType(Character processType);
	
	String getProcessRequester();
	void setProcessRequester(String processRequester);
	
	String getProcessApprover();
	void setProcessApprover(String processApprover);
}
