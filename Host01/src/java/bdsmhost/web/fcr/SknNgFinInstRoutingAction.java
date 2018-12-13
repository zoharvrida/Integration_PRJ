package bdsmhost.web.fcr;


import java.util.List;
import java.util.Map;

import bdsm.fcr.model.BaCityMaster;
import bdsm.fcr.model.BaSectorMast;
import bdsm.util.BdsmUtil;
import bdsmhost.fcr.dao.BaCityMasterDAO;
import bdsmhost.fcr.dao.BaSectorMastDAO;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgFinInstRoutingAction extends bdsmhost.web.BaseHostAction {
	private String BIC;
	private int mode;
	private String term = ""; // for autocompleter
	private List<Map<String, String>> routingList;
	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		this.routingList = new java.util.ArrayList<Map<String, String>>();
		
		if (this.mode == 1) {
			BaSectorMastDAO baSectorMastDAO = new BaSectorMastDAO(this.getHSession());
			List<BaSectorMast> list = baSectorMastDAO.listByCodBI(this.BIC, this.term);
			
			for (int i=0; i<list.size(); i++) {
				BaSectorMast s = list.get(i);
				Map<String, String> map = new java.util.HashMap<String, String>(2);
				map.put("id", BdsmUtil.leftPad(s.getCompositeId().getCodClgSector().toString(), 4, '0'));
				map.put("value", map.get("id") + " - " + s.getNamClgSector().substring(3));
				
				this.routingList.add(map);
			}
		}
		/*else if (this.mode == 2) {
			BaCityMasterDAO baCityMasterDAO = new BaCityMasterDAO(this.getHSession());
			List<BaCityMaster> list = baCityMasterDAO.listByCodBI(this.BIC, this.term);
			
			for (int i=0; i<list.size(); i++) {
				BaCityMaster c = list.get(i);
				Map<String, String> map = new java.util.HashMap<String, String>(2);
				
				String[] arrStr = c.getCityName().split("-");
				map.put("id", BdsmUtil.leftPad(arrStr[1].trim(), 4, '0'));
				map.put("value", map.get("id") + " - " + arrStr[0].trim());
				
				this.routingList.add(map); 
			}
		}*/
		else if (this.mode == 2) {
			BaCityMasterDAO baCityMasterDAO = new BaCityMasterDAO(this.getHSession());
			this.routingList = baCityMasterDAO.listByCodBI_2(this.BIC, this.term);
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}
	
	
    /**
     * 
     * @param BIC
     */
    public void setBIC(String BIC) {
		this.BIC = BIC;
	}
	
    /**
     * 
     * @param mode
     */
    public void setMode(int mode) {
		this.mode = mode;
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
     * @return
     */
    public List<Map<String, String>> getRoutingList() {
		return this.routingList;
	}

}
