package bdsmhost.web;


import java.util.Date;
import java.util.Map;


/**
 * 
 * @param <T> 
 * @author v00019372
 */
@SuppressWarnings("serial")
public abstract class ModelDrivenBaseHostAction<T> extends BaseHostAction implements com.opensymphony.xwork2.ModelDriven<T> {
    /**
     * 
     */
    protected static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ModelDrivenBaseHostAction.class);
	private Map<String, String> strData = new java.util.HashMap<String, String>(); // Data from Form Browser Client
	private Map<String, Object> objData = new java.util.HashMap<String, Object>(); // Data Sent to Web Tier
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
	
	
	/* === Map <=> Map === */
	
    /**
     * 
     * @param map
     * @return
     */
    public static Map<String, String> convertObjectValueMapToStringValueMap(Map<String, Object> map) {
		return convertObjectValueMapToStringValueMap(map, null);
	}
	
    /**
     * 
     * @param map
     * @param strPrepend
     * @return
     */
    public static Map<String, String> convertObjectValueMapToStringValueMap(Map<String, Object> map, String strPrepend) {
		Map<String, String> returnMap = new java.util.HashMap<String, String>();
		String value;
		
		if (map != null) {
			strPrepend = (strPrepend == null)? "" : (strPrepend + "__");
			
			for(Map.Entry<String, Object> e : map.entrySet()) {
				value = null;
				
				if (e.getValue() != null) {
					if (Date.class.isAssignableFrom(e.getValue().getClass()) == false)
						value = e.getValue().toString(); 
				}
				returnMap.put(strPrepend + e.getKey(), value);
			}
		}
		
		return returnMap;
	}
		
}
