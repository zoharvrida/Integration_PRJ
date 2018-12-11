package bdsm.service;


import org.apache.log4j.Logger;

import bdsm.model.CasaOpening;
import bdsm.model.CifOpening;
import bdsm.model.ScreenOpening;
import bdsm.scheduler.exception.FIXException;
import bdsmhost.dao.CasaOpeningDao;
import bdsmhost.dao.CifOpeningDao;
import bdsmhost.dao.WICTrxDAO;


/**
 * 
 * @author v00019372
 */
public class OpeningService {
	private static Logger LOGGER = Logger.getLogger(OpeningService.class);
	private CasaOpeningDao casaDao;
	private CifOpeningDao cifDao;
	
	/* == Setter Injection == */
	
	
	
	
	
	/* == WIC == */
	
    /**
     * 
     * @param cif
     * @return
     */
    public CifOpening getCif(CifOpening cif) {
		LOGGER.debug("[GET Cif Opening Begin]");
		
		CifOpening result = null;
		if (cif.getNik()!= null)
			result = this.cifDao.get(cif.getNik());
		else
//			result = this.casaDao.get(casaDao);
		
		LOGGER.debug("[GET Cif Opening End]");
		return result;
	}
    /**
     * 
     * @param casa
     * @return
     */
    public CasaOpening getCasa(CasaOpening casa) {
		LOGGER.debug("[GET Casa Opening Begin]");
		
		CasaOpening result = null;
		if (casa.getAcctNo()!= null)
			result = this.casaDao.get(casa.getAcctNo());
		else
//			result = this.casaDao.get(casaDao);
		
		LOGGER.debug("[GET Casa Opening End]");
		return result;
	}
	
//	public WIC insert(WIC wic) throws FIXException {
//		LOGGER.debug("[INSERT WIC Begin]");
//		
//		if (this.get(wic) == null) {
//			wic.setFlagStatus("A");
//			this.wicDAO.insert(wic);
//		}
//		else
//			throw new FIXException("Failed, Model already exists!!!");
//		
//		LOGGER.debug("[INSERT WIC End]");
//		return wic;
//	}
//	
//	public WIC update(WIC wic) throws FIXException {
//		LOGGER.debug("[UPDATE WIC Begin]");
//		
//		if (!this.wicDAO.update(wic))
//			throw new FIXException("Failed update!!!");
//		
//		LOGGER.debug("[UPDATE WIC End]");
//		return wic;
//	}
//	
//	public WIC delete(WIC wic) throws FIXException {
//		LOGGER.debug("[DELETE WIC Begin]");
//		String requester = wic.getRequester();
//		String approver = wic.getApprover();
//		
//		if ((wic.getWICNo() != null) && ((wic = this.wicDAO.get(wic.getWICNo())) != null)) {
//			wic.setRequester(requester);
//			wic.setApprover(approver);
//			wic.setFlagStatus("X");
//			if (!this.wicDAO.update(wic))
//				throw new FIXException("Failed delete logical!!!");
//		}
//		else
//			throw new FIXException("Invalid WIC No!!!");
//		
//		LOGGER.debug("[DELETE WIC End]");
//		return wic;
//	}
//	
//	
//	/* == WIC Trx == */
//	
//	public WICTrx getTrx(WICTrx wicTrx) {
//		LOGGER.debug("[GET WIC TRX Begin]");
//		
//		WICTrx result = null;
//		if (wicTrx.getTrxNo() != null)
//			result =  this.wicTrxDAO.get(wicTrx.getTrxNo());
//		else
//			result = this.wicTrxDAO.getByWICNoAndTrxRefNo(wicTrx);
//		
//		LOGGER.debug("[GET WIC TRX End]");
//		return result;
//	}
//	
//	public WICTrx insertTrx(WICTrx wicTrx) throws FIXException {
//		LOGGER.debug("[INSERT WIC TRX Begin]");
//		
//		if (this.getTrx(wicTrx) == null) {
//			if (this.wicTrxDAO.getByTrxRefNo(wicTrx.getRefNo()) == null) {
//				wicTrx.setFlagStatus("A");
//				this.wicTrxDAO.insert(wicTrx);
//			}
//			else
//				throw new FIXException("Transaction Ref. No. has already been recorded using other WIC!!!");
//		}
//		else
//			throw new FIXException("Failed, Model already exists!!!");
//		
//		LOGGER.debug("[INSERT WIC TRX End]");
//		return wicTrx;
//	}
//	
//	public WICTrx deleteTrx(WICTrx wicTrx) throws FIXException {
//		LOGGER.debug("[DELETE WIC TRX Begin]");
//		String approver = wicTrx.getApprover();
//		
//		wicTrx = this.getTrx(wicTrx);
//		if (wicTrx != null) {
//			wicTrx.setApprover(approver);
//			wicTrx.setFlagStatus("X");
//			if (!this.wicTrxDAO.update(wicTrx))
//				throw new FIXException("Failed delete logical!!!");
//		}
//		else
//			throw new FIXException("Invalid WIC Trx!!!");
//		
//		LOGGER.debug("[DELETE WIC TRX End]");
//		return wicTrx;
//	}
//	
//	
//	
//	public void commit() {
//		if (this.wicDAO != null) this.wicDAO.commit();
//		if (this.wicTrxDAO != null)this.wicTrxDAO.commit();
//	}
//	
//	public void rollback() {
//		if (this.wicDAO != null) this.wicDAO.rollback();
//		if (this.wicTrxDAO != null)this.wicTrxDAO.rollback();
//	}

    /**
     * @return the casaDao
     */
    public CasaOpeningDao getCasaDao() {
        return casaDao;
    }

    /**
     * @param casaDao the casaDao to set
     */
    public void setCasaDao(CasaOpeningDao casaDao) {
        this.casaDao = casaDao;
    }

    /**
     * @return the cifDao
     */
    public CifOpeningDao getCifDao() {
        return cifDao;
    }

    /**
     * @param cifDao the cifDao to set
     */
    public void setCifDao(CifOpeningDao cifDao) {
        this.cifDao = cifDao;
    }
	
}
