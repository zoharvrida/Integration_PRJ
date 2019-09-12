package bdsmhost.interceptor;


import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public abstract class AbstractHistoryInterceptor<T> extends EmptyInterceptor {
	private static Logger LOGGER = Logger.getLogger(AbstractHistoryInterceptor.class);
	private Session session;
	
	
	protected abstract void process(BaseModel oldEntity, T history, String[] propertyNames,
			Object[] previousState, Object[] currentState) throws Exception;
	
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean onFlushDirty(Object entity, java.io.Serializable id, 
			Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		java.lang.reflect.Type type = this.getClass().getGenericSuperclass();
		java.lang.reflect.ParameterizedType pType = (java.lang.reflect.ParameterizedType) type;
		T t = null;
		
		try {
			t = ((Class<T>) pType.getActualTypeArguments()[0]).newInstance();
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
		
		if ((entity instanceof BaseModel) && (entity.getClass().isAssignableFrom(t.getClass()))) {
			ClassMetadata cm = this.session.getSessionFactory().getClassMetadata(entity.getClass());
			String propertyIdName = cm.getIdentifierPropertyName();
			BaseModel oldEntity = (BaseModel) this.session.get(entity.getClass(), cm.getIdentifier(entity, EntityMode.POJO));
			
			try {
				/* manipulate propertyNames */
				for (int i=0; i<propertyNames.length; i++) {
					if (propertyNames[i].endsWith("DB"))
						propertyNames[i] = propertyNames[i].substring(0, propertyNames[i].length()-2);
				}
				
				String[] idPropertyNames;
				if (propertyIdName != null) {
					idPropertyNames = new String[1];
					idPropertyNames[0] = propertyIdName;
				}
				else {
					StringBuilder sb = new StringBuilder(cm.getIdentifierType().getName().substring("component[".length())); // remove first 'component['
					sb.deleteCharAt(sb.length() - 1); // remove last ']'
					idPropertyNames = sb.toString().split(",");
				}
				
				String[] temp = new String[propertyNames.length + idPropertyNames.length];
				System.arraycopy(propertyNames, 0, temp, 0, propertyNames.length);
				System.arraycopy(idPropertyNames, 0, temp, propertyNames.length, idPropertyNames.length);
				
				propertyNames = temp;
				
				this.process(oldEntity, t, propertyNames, previousState, currentState);
				
				new BaseDao(this.session) {
					public boolean insert(BaseModel model) {
						if (model != null) {
							try {
								Calendar calendar = Calendar.getInstance();
								if (model.getIdCreatedBy() == null)
									model.setIdCreatedBy(model.getIdMaintainedBy());
								if (model.getIdCreatedSpv() == null)
									model.setIdCreatedSpv(model.getIdMaintainedSpv());
								Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
								model.setDtmCreated(dt);
								model.setDtmCreatedSpv(dt);
							}
							catch (Exception e) {
								LOGGER.info("EXCEPTION BASE :" + e, e);
							}

							return this.doInsert(model);
						}
						else
							return false;
					};
					
					@Override
					protected boolean doInsert(BaseModel model) { 
						this.getSession().save(model);
						return true; 
					}
					
					@Override 
					public boolean save(BaseModel model) { 
						return this.insert(model); 
					};
					
					
					@Override protected boolean doUpdate(BaseModel model) { throw new RuntimeException("Unsupported method"); }
					@Override protected boolean doDelete(BaseModel model) { throw new RuntimeException("Unsupported method"); }
				}.save((BaseModel) t);
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		else {
			LOGGER.error("[ERROR] 1. " + entity.getClass() + " must be subclass of " + BaseModel.class.getName() + "!!!\n" +
					"2. " + t.getClass().getName() + " must subclass of " + entity.getClass() + "!!!");
		}
		
		return true;
	}
	
}
