package bdsmhost.interceptor;


import bdsm.model.BaseHistoryInterface;
import bdsm.model.BaseModel;
import bdsm.util.BdsmUtil;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public abstract class AbstractTimeHistoryInterceptor<T extends BaseHistoryInterface> extends AbstractHistoryInterceptor<T> {
	
	protected void process(BaseModel oldEntity, BaseHistoryInterface history, String[] propertyNames,
			Object[] previousState, Object[] currentState) throws Exception {
		/* copy from original to history object */
		BdsmUtil.copyBeanProperties(oldEntity, history, propertyNames);
		history.setDtmCreatedOld(oldEntity.getDtmCreated());
		history.setDtmUpdatedOld(oldEntity.getDtmUpdated());
	}

}
