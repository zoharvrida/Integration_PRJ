package bdsm.service;


import org.apache.log4j.Logger;

import bdsm.model.WIC;
import bdsm.model.WICTrx;
import bdsm.scheduler.exception.FIXException;
import bdsmhost.dao.WICDao;
import bdsmhost.dao.WICTrxDAO;


/**
 * 
 * @author v00019372
 */
public class WICService {
	private static Logger LOGGER = Logger.getLogger(WICService.class);
	private WICDao wicDAO;
	private WICTrxDAO wicTrxDAO;
	
	/* == Setter Injection == */
	
    /**
     * 
     * @param wicDAO
     */
    public void setWICDAO(WICDao wicDAO) {
		this.wicDAO = wicDAO;
	}
	
    /**
     * 
     * @param wicTrxDAO
     */
    public void setWICTrxDAO(WICTrxDAO wicTrxDAO) {
		this.wicTrxDAO = wicTrxDAO;
	}
	
	
	
	/* == WIC == */
	
    /**
     * 
     * @param wic
     * @return
     */
    public WIC get(WIC wic) {
		LOGGER.debug("[GET WIC Begin]");
		
		WIC result = null;
		if (wic.getWICNo() != null)
			result = this.wicDAO.get(wic.getWICNo());
		else
			result = this.wicDAO.getByCustomerTypeAndId(wic);
		
		LOGGER.debug("[GET WIC End]");
		return result;
	}
	
    /**
     * 
     * @param wic
     * @return
     * @throws FIXException
     */
    public WIC insert(WIC wic) throws FIXException {
		LOGGER.debug("[INSERT WIC Begin]");
		
		if (this.get(wic) == null) {
			wic.setFlagStatus("A");
			this.wicDAO.insert(wic);
		}
		else
			throw new FIXException("Failed, Model already exists!!!");
		
		LOGGER.debug("[INSERT WIC End]");
		return wic;
	}
	
    /**
     * 
     * @param wic
     * @return
     * @throws FIXException
     */
    public WIC update(WIC wic) throws FIXException {
		LOGGER.debug("[UPDATE WIC Begin]");
		
		if (!this.wicDAO.update(wic))
			throw new FIXException("Failed update!!!");
		
		LOGGER.debug("[UPDATE WIC End]");
		return wic;
	}
	
    /**
     * 
     * @param wic
     * @return
     * @throws FIXException
     */
    public WIC delete(WIC wic) throws FIXException {
		LOGGER.debug("[DELETE WIC Begin]");
		String requester = wic.getRequester();
		String approver = wic.getApprover();
		
		if ((wic.getWICNo() != null) && ((wic = this.wicDAO.get(wic.getWICNo())) != null)) {
			wic.setRequester(requester);
			wic.setApprover(approver);
			wic.setFlagStatus("X");
			if (!this.wicDAO.update(wic))
				throw new FIXException("Failed delete logical!!!");
		}
		else
			throw new FIXException("Invalid WIC No!!!");
		
		LOGGER.debug("[DELETE WIC End]");
		return wic;
	}
	
	
	/* == WIC Trx == */
	
    /**
     * 
     * @param wicTrx
     * @return
     */
    public WICTrx getTrx(WICTrx wicTrx) {
		LOGGER.debug("[GET WIC TRX Begin]");
		
		WICTrx result = null;
		if (wicTrx.getTrxNo() != null)
			result =  this.wicTrxDAO.get(wicTrx.getTrxNo());
		else
			result = this.wicTrxDAO.getByWICNoAndTrxRefNo(wicTrx);
		
		LOGGER.debug("[GET WIC TRX End]");
		return result;
	}
	
    /**
     * 
     * @param wicTrx
     * @return
     * @throws FIXException
     */
    public WICTrx insertTrx(WICTrx wicTrx) throws FIXException {
		LOGGER.debug("[INSERT WIC TRX Begin]");
		
		if (this.getTrx(wicTrx) == null) {
			if (this.wicTrxDAO.getByTrxRefNo(wicTrx.getRefNo()) == null) {
				wicTrx.setFlagStatus("A");
				this.wicTrxDAO.insert(wicTrx);
			}
			else
				throw new FIXException("Transaction Ref. No. has already been recorded using other WIC!!!");
		}
		else
			throw new FIXException("Failed, Model already exists!!!");
		
		LOGGER.debug("[INSERT WIC TRX End]");
		return wicTrx;
	}
	
    /**
     * 
     * @param wicTrx
     * @return
     * @throws FIXException
     */
    public WICTrx deleteTrx(WICTrx wicTrx) throws FIXException {
		LOGGER.debug("[DELETE WIC TRX Begin]");
		String approver = wicTrx.getApprover();
		
		wicTrx = this.getTrx(wicTrx);
		if (wicTrx != null) {
			wicTrx.setApprover(approver);
			wicTrx.setFlagStatus("X");
			if (!this.wicTrxDAO.update(wicTrx))
				throw new FIXException("Failed delete logical!!!");
		}
		else
			throw new FIXException("Invalid WIC Trx!!!");
		
		LOGGER.debug("[DELETE WIC TRX End]");
		return wicTrx;
	}
	
	
	
    /**
     * 
     */
    public void commit() {
		if (this.wicDAO != null) this.wicDAO.commit();
		if (this.wicTrxDAO != null)this.wicTrxDAO.commit();
	}
	
    /**
     * 
     */
    public void rollback() {
		if (this.wicDAO != null) this.wicDAO.rollback();
		if (this.wicTrxDAO != null)this.wicTrxDAO.rollback();
	}
	
}
