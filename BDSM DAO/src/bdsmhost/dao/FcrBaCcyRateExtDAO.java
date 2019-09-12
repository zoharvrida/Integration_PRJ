package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.fcr.model.BDSMBICloseRate;
import bdsm.model.FcrBaCcyRate;
import bdsm.model.FcrBaCcyRatePK;
import bdsmhost.fcr.dao.BDSMBICloseRateDAO;


/**
 * @author v00020800
 */
public class FcrBaCcyRateExtDAO extends FcrBaCcyRateDao {

	public FcrBaCcyRateExtDAO(Session session) {
		super(session);
	}


	public FcrBaCcyRate get() {
		return this.get(840);
	}

	public FcrBaCcyRate get(int codCcy) {
		BDSMBICloseRateDAO rateDAO = new BDSMBICloseRateDAO(this.getSession());
		BDSMBICloseRate BIRate = rateDAO.get(codCcy);
		
		FcrBaCcyRate fcrRate = new FcrBaCcyRate();
		fcrRate.setCompositeId(new FcrBaCcyRatePK());
		
		fcrRate.setCodLcy(codCcy);
		fcrRate.setRatCcyMid(BIRate.getMidRate());
		fcrRate.getCompositeId().setDatTimRateEff(new java.sql.Timestamp(BIRate.getCompositeId().getBICloseDate().getTime()));
		fcrRate.getCompositeId().setFlgMntStatus("A");

		return fcrRate;
	}
}
