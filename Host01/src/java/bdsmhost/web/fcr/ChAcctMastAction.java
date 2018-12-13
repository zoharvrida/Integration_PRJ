package bdsmhost.web.fcr;


import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.service.AccountService;
import bdsmhost.fcr.dao.ChAcctMastDAO;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class ChAcctMastAction extends bdsmhost.web.BaseHostAction {
	private String codAcctNo;
	private ChAcctMast model;
	
	
    /**
     * 
     * @param codAcctNo
     */
    public void setCodAcctNo(String codAcctNo) {
		this.codAcctNo = codAcctNo;
	}
	
    /**
     * 
     * @return
     */
    public ChAcctMast getModel() {
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
		AccountService srvc = new AccountService();
		srvc.setFCRChAcctMastDAO(new ChAcctMastDAO(this.getHSession()));	
		this.model = srvc.getByAccountNo(this.codAcctNo);
		getLogger().debug("MODEL ACCT :" + this.model);
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
