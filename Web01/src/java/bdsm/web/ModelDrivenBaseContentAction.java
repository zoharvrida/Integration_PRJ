package bdsm.web;


import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import bdsm.util.oracle.DateUtility;


/**
 * 
 * @param <T> 
 * @author v00019372
 */
@SuppressWarnings("serial")
public abstract class ModelDrivenBaseContentAction<T> extends BaseContentAction implements com.opensymphony.xwork2.ModelDriven<T> {
	private static final Logger LOGGER = Logger.getLogger(ModelDrivenBaseContentAction.class);
	private static final Class<? extends Object>[] EMPTY_CLASS = new Class<?>[0];
	private Map<String, String> strData = new java.util.HashMap<String, String>(); // Data from Form Browser Client
	private Map<String, Object> objData = new java.util.HashMap<String, Object>(); // Data from Host
	private Map<String, String> objType = new java.util.HashMap<String, String>(); // Data Type for objData
	
	
    /**
     * 
     * @return
     */
    public Map<String, String> getStrData() {
		return this.strData;
	}
    /**
     * 
     * @param strData
     */
    public void setStrData(Map<String, String> strData) {
		this.strData = strData;
	}
	
    /**
     * 
     * @return
     */
    public Map<String, Object> getObjData() {
		return this.objData;
	}
    /**
     * 
     * @param objData
     */
    public void setObjData(Map<String, Object> objData) {
		this.objData = objData;
	}
	
    /**
     * 
     * @return
     */
    public Map<String, String> getObjType() {
		return this.objType;
	}
    /**
     * 
     * @param objType
     */
    public void setObjType(Map<String, String> objType) {
		this.objType = objType;
	}
	
	
    /**
     * 
     * @param key
     * @return
     */
    protected final String getFromStrData(String key) {
		return this.strData.get(key);
	}
    /**
     * 
     * @param key
     * @param value
     */
    protected final void putToStrData(String key, String value) {
		this.strData.put(key, value);
	}
	
    /**
     * 
     * @param key
     * @return
     */
    protected final Object getFromObjData(String key) {
		return this.objData.get(key);
	}
    /**
     * 
     * @param key
     * @param value
     */
    protected final void putToObjData(String key, Object value) {
		this.objData.put(key, value);
	}
	
    /**
     * 
     * @param key
     * @return
     */
    protected final String getFromObjType(String key) {
		return this.objType.get(key);
	}
    /**
     * 
     * @param key
     * @param value
     */
    protected final void putToObjType(String key, String value) {
		this.objType.put(key, value);
	}
	
	
    /**
     * 
     * @return
     */
    protected Map<String, String> createParameterMapFromHTTPRequest() {
		Map<String, String> ret = new java.util.HashMap<String, String>();
		Map<String, String[]> params = this.getServletRequest().getParameterMap();
		Iterator<String> it = params.keySet().iterator();
		
		while (it.hasNext()) {
			String key = it.next();
			ret.put(key, params.get(key)[0]);
		}
		
		return ret;
	}
	
	
	/* === Map => Object === */
	
    /**
     * 
     * @param map
     * @param obj
     */
    protected static void copyMapValueToObject(Map<String, ? extends Object> map, Object obj) {
		copyMapValueToObject(map, obj, null);
	}
	
    /**
     * 
     * @param map
     * @param obj
     * @param cls
     */
    protected static void copyMapValueToObject(Map<String, ? extends Object> map, Object obj, Class<? extends Object> cls) {
		if ((map == null) || (obj == null))
			return;
		
		for (Map.Entry<String, ? extends Object> e : map.entrySet()) {
			if ((cls==null) || (e.getValue()==null) || ((cls!=null) && (cls.isAssignableFrom(e.getValue().getClass()))))
				copyValueToObjectProperty(e.getValue(), obj, e.getKey());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void copyValueToObjectProperty(Object value, Object object, String propertyName) {
		Method m;
		Class<? extends Object> returnType;
		String propMethodName;
		
		
		if (object == null)
			return;
		
		if ((propertyName.length() > 1) && (Character.isLowerCase(propertyName.charAt(1))))
			propMethodName = Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
		else
			propMethodName = propertyName;
			
		try {
			try {
				m = object.getClass().getMethod("get" + propMethodName, EMPTY_CLASS);
			}
			catch (NoSuchMethodException exIn1) {
				try {
					m = object.getClass().getMethod("is" + propMethodName, EMPTY_CLASS);
					if (!Boolean.class.isAssignableFrom(m.getReturnType()))
						return;
				}
				catch (NoSuchMethodException exIn2) {
					return;
				}
			}
			
			returnType = m.getReturnType();
			
			m = object.getClass().getMethod("set" + propMethodName, new Class[] {returnType});
			
			try {
				if (value == null)
					m.invoke(object, returnType.cast(null));
				else if ((Map.class.isAssignableFrom(value.getClass())) && !(returnType.equals(value.getClass()))) {
					Object newObject = returnType.newInstance();
					copyMapValueToObject((Map) value, newObject);
					m.invoke(object, newObject);
				}
				else
					m.invoke(object, convertValueToType(value, returnType));
			}
			catch (Exception e) { 
				LOGGER.error(e, e);
				LOGGER.error("'" + returnType + "' is not assignable from '" + value.getClass() + "' for property '" + propertyName + "'");
			}
		}
		catch (NoSuchMethodException ex1) {}
		catch (Exception ex2) {
			LOGGER.error(ex2, ex2);
		}
	}
	
	
	/* === Converting elements type in objData Map === */
	
    /**
     * 
     */
    protected void convertObjDataValueAccordingToObjType() {
		Iterator<Map.Entry<String, String>> it = this.objType.entrySet().iterator();
		Map.Entry<String, String> e;
		Object newValue = null;
		
		while (it.hasNext()) {
			e = it.next();
			try {
				newValue = convertValueToType(objData.get(e.getKey()), Class.forName(e.getValue()));
				objData.put(e.getKey(), newValue);
			}
			catch (Exception e1) {
				LOGGER.error(e);
			}
		}
	}
	
	
	
	private static Object convertValueToType(Object value, Class<?> toType) throws Exception {
		Object convertedValue = null;
		
		try {
			if (value == null)
				convertedValue = toType.cast(null);
			else if (toType.equals(value.getClass())) {
				if (
						(isPrimitiveOrWrapper(toType)) ||
						(String.class.isAssignableFrom(toType)) ||
						(Number.class.isAssignableFrom(toType)) ||
						(Date.class.isAssignableFrom(toType)) ||
						(Collection.class.isAssignableFrom(toType)) ||
						(Map.class.isAssignableFrom(toType))
					) {
					convertedValue = value;
				}
			}
			else if ((Number.class.isAssignableFrom(toType)) && 
				((Number.class.isAssignableFrom(value.getClass())) || (String.class.isAssignableFrom(value.getClass())))
			) {
				java.lang.reflect.Constructor<?> c = toType.getConstructor(String.class);
				convertedValue = c.newInstance(value.toString());
			}
			else if ((Date.class.isAssignableFrom(toType)) && (String.class.isAssignableFrom(value.getClass()))) {
				java.lang.reflect.Constructor<?> c = toType.getConstructor(Long.TYPE);
				convertedValue = c.newInstance(DateUtility.DATE_TIME_FORMAT_YYYYMMDD.parse(((String) value).replace('/', '-').replace('T', ' ')).getTime());
			}
			
			return convertedValue;
		}
		catch (Exception ex) {
			throw new Exception("'" + toType + "' is not assignable from '" + value.getClass() + "'");
		}
	}
	
	private static boolean isPrimitiveOrWrapper(Class<?> type) {
		if (type == null)
			return false;

		return ((type.isPrimitive()) || (ClassUtils.wrapperToPrimitive(type) != null));
	}
	
}
