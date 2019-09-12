package bdsmhost.dao;


import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import bdsm.model.MfcUdMasterPK;
import bdsm.model.MfcUdMaster_LLD;


/**
 * @author v00019372
 */
public class MfcUdMaster_LLDDAO extends BaseDao {
	private static final Logger LOGGER = Logger.getLogger(MfcUdMaster_LLDDAO.class);


	public MfcUdMaster_LLDDAO(org.hibernate.Session session) {
		super(session);
	}
	

	public MfcUdMaster_LLD getDummyUD(MfcUdMasterPK pk){
		MfcUdMaster_LLD result = new MfcUdMaster_LLD();
		result = (MfcUdMaster_LLD)this.getSession().get(MfcUdMaster_LLD.class, pk);
		return result;
	}

	public MfcUdMaster_LLD get(MfcUdMasterPK id) {
		LOGGER.debug("session :" + this.getSession());
		return (MfcUdMaster_LLD) this.getSession().get(MfcUdMaster_LLD.class, id);
	}

	public java.util.List<MfcUdMaster_LLD> listByCifNo(Integer codCust, String codAcctNo, BigDecimal txnAmt, String codTxnCcy) throws Exception {
		return new LLDDAO(this.getSession()).getAvailUd(codCust, codAcctNo, txnAmt, codTxnCcy);
	}

	public java.util.List<MfcUdMaster_LLD> listByCifNo(Integer codCust) throws Exception {
		return new LLDDAO(this.getSession()).listOfUd(codCust);
	}

	@SuppressWarnings("unchecked")
	public List<MfcUdMaster_LLD> listByNoUD(String noUD) {
		StringBuffer sb = new StringBuffer()
			.append("SELECT a ")
			.append("  FROM MfcUdMaster_LLD a, BaBankMast b ")
			.append(" WHERE a.compositeId.noUd = :noUd ")
			.append("   AND a.dtExpiry         >= b.datProcess ");
		Query q = this.getSession().createQuery(sb.toString());
		q.setString("noUd", noUD);
		
		List<MfcUdMaster_LLD> listResult = q.list();
		for (MfcUdMaster_LLD ud : listResult)
			this.getSession().evict(ud);
		
		return listResult; 
	}


	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		this.getSession().save((MfcUdMaster_LLD) model);
		return true;
	}

	@Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		this.getSession().update((MfcUdMaster_LLD) model);
		return true;
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		this.getSession().delete((MfcUdMaster_LLD) model);
		return true;
	}

}
