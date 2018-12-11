package bdsm.dialog.json;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.struts2.json.JSONUtil;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;

/**
 *
 * @author v00019372
 */
@SuppressWarnings({"rawtypes", "serial", "unchecked"})
public class DialogDataMasterActionRisk extends BaseDialogAction {

	private static String DATAMASTERHOST_GET = "FCRDataMasterHost_get";
	private static String DATAMASTERHOST_LIST = "FCRDataMasterHost_list";
	private String master;
	private String id;
	private String term; // for autocompleter
	private Boolean constant = Boolean.FALSE;
	private Map<String, Object> data;

    /**
     * 
     * @param master
     */
    public void setMaster(String master) {
		this.master = master;
	}

    /**
     * 
     * @param id
     */
    public void setId(String id) {
		this.id = id;
	}

    /**
     * 
     * @param term
     */
    public void setTerm(String term) {
		this.term = term;
	}

    /**
     * 
     * @param constant
     */
    public void setConstant(Boolean constant) {
		this.constant = constant;
	}

    /**
     * 
     * @return
     */
    public Map<String, Object> getData() {
		return this.data;
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    public String cityList() throws Exception {
		this.master = "city";
		return list();
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    public String stateList() throws Exception {
		this.master = "state";
		return list();
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    public String countryList() throws Exception {
		this.master = "country";
		return list();
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    public String citizenshipList() throws Exception {
		this.master = "citizenship";
		return list();
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    public String occupationList() throws Exception {
		this.master = "occupation";
		return list();
	}

    /**
     * 
     * @return
     */
    public String get() {
		this.getLogger().info("[Begin] get()");


		try {
			if (this.isValidSession()) {
				try {
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("master", this.master);
					params.put("id", this.id);
					params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

					String result = HttpUtil.request(getBdsmHost() + DATAMASTERHOST_GET, params);
					this.getLogger().info(result);

					Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
					List<Object> list = (List<Object>) ret.get("data");

					this.data = new HashMap<String, Object>();
					this.data.put("id", list.get(0));
					this.data.put("desc", list.get(1));
					this.data.put("riskcode", list.get(2));
					this.data.put("riskdesc", list.get(3));


					if (list.size() > 2) {
						this.data.put("data1", list.get(2));
					}

				} catch (Throwable ex) {
					this.getLogger().fatal(ex, ex);
					this.setErrorMessage(ex.getMessage());
					return ERROR;
				}

				return SUCCESS;
			} else {
				return logout();
			}
		} finally {
			getLogger().info("[ End ] get()");
		}
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List<Map<String, Object>> doList() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("master", this.master);
		params.put("like", (this.term == null) ? "" : this.term);
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		String result = HttpUtil.request(getBdsmHost() + DATAMASTERHOST_LIST, params);
		this.getLogger().info(result);

		Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
		List<Map<String, Object>> listRet = this.convertList((List) ret.get("dataList"));
		String statusRet = (String) ret.get("jsonStatus");
		addActionError(statusRet);

		return listRet;
	}

    /**
     * 
     * @param dataList
     * @return
     */
    protected List<Map<String, Object>> convertList(List dataList) {
		List<Object> element;
		Map<String, Object> tempMap;

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				element = (List<Object>) dataList.get(i);
				tempMap = new java.util.HashMap<String, Object>();

				tempMap.put("id", element.get(0));

				if (this.constant) {
					tempMap.put("desc", getText((String) element.get(1)));
				} else {
					tempMap.put("desc", element.get(1));
					tempMap.put("riskcode", element.get(2));
					tempMap.put("riskdesc", element.get(3));

				}

				dataList.set(i, tempMap);
			}
		}

		return dataList;
	}


}
