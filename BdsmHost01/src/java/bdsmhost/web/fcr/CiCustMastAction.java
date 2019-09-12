package bdsmhost.web.fcr;

import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.service.CustomerService;
import bdsmhost.fcr.dao.CiCustMastDao;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class CiCustMastAction extends bdsmhost.web.BaseHostAction {
	private Integer codCustId;
	private CiCustMast model;
	
	
    /**
     * 
     * @param codCustId
     */
    public void setCodCustId(Integer codCustId) {
		this.codCustId = codCustId;
	}
	
    /**
     * 
     * @return
     */
    public CiCustMast getModel() {
		return this.model;
	}
	
	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		CustomerService service = new CustomerService();
		service.setCiCustMastDAO(new CiCustMastDao(this.getHSession()));
		
		this.model = service.getByCustomerId(this.codCustId);
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
