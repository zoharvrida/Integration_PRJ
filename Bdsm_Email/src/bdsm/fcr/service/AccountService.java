package bdsm.fcr.service;


import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.GLMaster;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.GLMasterDAO;


/**
 * 
 * @author v00019372
 */
public class AccountService {
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AccountService.class);
	private ChAcctMastDAO chAcctMastDAO;
	private GLMasterDAO glMasterDAO;
	
	
	/* == Setter Injection == */
	public void setFCRChAcctMastDAO(ChAcctMastDAO chAcctMastDAO) {
		this.chAcctMastDAO = chAcctMastDAO;
	}
	
	public void setFCRGLMasterDAO(GLMasterDAO glMasterDAO) {
		this.glMasterDAO = glMasterDAO;
	}
	
	
	
	public ChAcctMast getByAccountNo(String accountNo) {
		return this.chAcctMastDAO.getActiveAccount(accountNo);
	}
	
	public GLMaster getGLAccountByBranchAccountAndCurrency(Integer branchCode, Integer accountNo, Integer currencyCode) {
		return this.glMasterDAO.getByBranchAccountAndCurrency(branchCode, accountNo, currencyCode);
	}
}
