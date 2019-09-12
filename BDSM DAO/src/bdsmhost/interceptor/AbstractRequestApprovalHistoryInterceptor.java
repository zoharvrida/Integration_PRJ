package bdsmhost.interceptor;


import bdsm.model.BaseModel;
import bdsm.model.BaseReqApprHistoryInterface;
import bdsm.util.BdsmUtil;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public abstract class AbstractRequestApprovalHistoryInterceptor<T extends BaseReqApprHistoryInterface> extends AbstractHistoryInterceptor<T> {
	
	@Override
	protected void process(BaseModel oldEntity, T history, String[] propertyNames, 
			Object[] previousState, Object[] currentState) throws Exception {
		
		/* copy from original to history object */
		BdsmUtil.copyBeanProperties(oldEntity, history, propertyNames);
		history.setDtmCreatedOld(oldEntity.getDtmCreated());
		history.setDtmUpdatedOld(oldEntity.getDtmUpdated());
		
		BaseReqApprHistoryInterface reqApprHistory = (BaseReqApprHistoryInterface) history;
		for (int i=0; i<propertyNames.length; i++) {
			String propertyName = propertyNames[i].toString();
			if (propertyName.equals("flagStatus")) {
				if (Character.valueOf('X').equals(currentState[i]))
					reqApprHistory.setProcessType('2'); // Delete
				else
					reqApprHistory.setProcessType('1'); // Update
			}
			else if (propertyName.equals("requester")) 
				reqApprHistory.setProcessRequester((currentState[i]==null)? null: currentState[i].toString());
			else if (propertyName.equals("approver")) 
				reqApprHistory.setProcessApprover((currentState[i]==null)? null: currentState[i].toString());
		}
		
	}
	
}
