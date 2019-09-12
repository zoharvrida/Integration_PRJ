package bdsm.fcr.service;


import org.apache.log4j.Logger;

import bdsm.fcr.model.XfOlStTxnLogCurrent;
import bdsm.fcr.model.XfOlStTxnLogCurrentPK;
import bdsmhost.fcr.dao.XfOlStTxnLogCurrentDAO;


/**
 * 
 * @author v00019372
 */
public class TransactionService {
	private static Logger LOGGER = Logger.getLogger(TransactionService.class);
	private XfOlStTxnLogCurrentDAO FCRTrxDAO;
	
	
	/* == Setter Injection == */
	public void setFCRTransactionDAO(XfOlStTxnLogCurrentDAO FCRTrxDAO) {
		this.FCRTrxDAO = FCRTrxDAO;
	}
	
	
	public XfOlStTxnLogCurrent getByPK(XfOlStTxnLogCurrentPK pk) {
		return this.FCRTrxDAO.get(pk);
	}
	
	public XfOlStTxnLogCurrent getByTrxRefNo(String trxRefNo) {
		return this.getByTrxRefNo(trxRefNo, null);
	}
	
	public XfOlStTxnLogCurrent getByTrxRefNo(String trxRefNo, String codBranch) {
		LOGGER.debug("[QUERY Begin]");
		XfOlStTxnLogCurrent result = this.FCRTrxDAO.getByRefTxnNoAndCodOrgBrn(trxRefNo, (codBranch!=null)? Integer.parseInt(codBranch): null);
		if (result != null)
			this.FCRTrxDAO.evictObjectFromPersistenceContext(result);
		
		LOGGER.debug("[QUERY End]");
		return result;
	}
	
}
