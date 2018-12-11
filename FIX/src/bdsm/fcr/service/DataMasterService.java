package bdsm.fcr.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * 
 * @author v00019372
 */
public class DataMasterService {
	private static Logger LOGGER = Logger.getLogger(DataMasterService.class);
	private static Map<Object, Object> genderMap;
	private static Map<Object, Object> maritalStatusMap;
	private static Map<Object, Object> WICCustomerMap;
	private static Map<Object, Object> WICCustomerInquiryMap;
	private bdsmhost.fcr.dao.DataMasterDAO dataMasterDAO;
	
	
	static {
		genderMap = new LinkedHashMap<Object, Object>();
		genderMap.put("M", "gender.male");
		genderMap.put("F", "gender.female");
		
		maritalStatusMap = new LinkedHashMap<Object, Object>();
		maritalStatusMap.put(1, "marital.status.single");
		maritalStatusMap.put(2, "marital.status.married");
		maritalStatusMap.put(3, "marital.status.widowed");
		maritalStatusMap.put(4, "marital.status.divorced");
		
		WICCustomerMap = new LinkedHashMap<Object, Object>();
		WICCustomerMap.put(1, "wic.type.wic");
		WICCustomerMap.put(2, "wic.type.courier");
		
		WICCustomerInquiryMap = new LinkedHashMap<Object, Object>();
		WICCustomerInquiryMap.put(1, "wic.type.wic");
		WICCustomerInquiryMap.put(2, "wic.type.courier");
		WICCustomerInquiryMap.put(3, "wic.type.non.wic");
	}
	
	
	/* == Setter Injection == */
	
    /**
     * 
     * @param dataMasterDAO
     */
    public void setDataMasterDAO(bdsmhost.fcr.dao.DataMasterDAO dataMasterDAO) {
		this.dataMasterDAO = dataMasterDAO;
	}
	
	
    /**
     * 
     * @param type
     * @param like
     * @return
     */
    public List<Object[]> getDataList(String type, String like) {
		LOGGER.debug("type: " + type);
		LOGGER.debug("like: " + like); // used only data from DB, not from Local Map
		
		List<Object[]> returnList = new java.util.ArrayList<Object[]>();
		
		if ("gender".equals(type))
			returnList.addAll(this.convertFromMapToObjectArrayList(genderMap));
		else if ("maritalStatus".equals(type))
			returnList.addAll(this.convertFromMapToObjectArrayList(maritalStatusMap));
		else if ("WICCustomer".equals(type))
			returnList.addAll(this.convertFromMapToObjectArrayList(WICCustomerMap));
		else if ("WICCustomerInquiry".equals(type))
			returnList.addAll(this.convertFromMapToObjectArrayList(WICCustomerInquiryMap));
		else
			returnList.addAll(dataMasterDAO.getAllData(type, like));

		return returnList;
	}
	
    /**
     * 
     * @param type
     * @param id
     * @return
     */
    public Object[] getDataById(String type, Object id) {
		Object[] result = null;
		
		if (id != null) {
			if ("gender".equals(type))
				result = this.getById(genderMap, id);
			else if ("maritalStatus".equals(type))
				result = this.getById(maritalStatusMap, (id instanceof Integer)? id: Integer.parseInt(id.toString()));
			else if ("WICCustomer".equals(type))
				result = this.getById(WICCustomerMap, (id instanceof Integer)? id: Integer.parseInt(id.toString()));
			else if ("WICCustomerInquiry".equals(type))
				result = this.getById(WICCustomerInquiryMap, (id instanceof Integer)? id: Integer.parseInt(id.toString()));
			else
				result = dataMasterDAO.getDataById(type, id);
		}
		
		return result;
	}
	
	
    /**
     * 
     * @param map
     * @param id
     * @return
     */
    protected Object[] getById(Map<Object, Object> map, Object id) {
		Object[] result = null;
		Object data;
		
		if ((data=map.get(id)) != null) {
			Map<Object, Object> tempMap = new LinkedHashMap<Object, Object>();
			tempMap.put(id, data);
			result = this.convertFromMapToObjectArrayList(tempMap).get(0);
		}
		
		return result;
	}
	
    /**
     * 
     * @param map
     * @return
     */
    protected List<Object[]> convertFromMapToObjectArrayList(Map<Object, Object> map) {
		List<Object[]> returnList = new java.util.ArrayList<Object[]>(map.size());
		java.util.Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
		Map.Entry<Object, Object> e;
		
		while (it.hasNext()) {
			e = it.next();
			if (e.getValue().getClass().isArray()) {
				Object[] temp = new Object[1 + ((Object []) e.getValue()).length];
				temp[0] = e.getKey();
				System.arraycopy(e.getValue(), 0, temp, 1, ((Object []) e.getValue()).length);
			}
			else
				returnList.add(new Object[] {e.getKey(), e.getValue()});
		}
		
		return returnList;
	}
	
}
