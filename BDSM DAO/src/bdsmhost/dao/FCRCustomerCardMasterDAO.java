package bdsmhost.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.FCRCustomerCardMaster;
import bdsm.model.FcrChAcctMast;
import bdsm.model.FcrChAcctMastPK;

public class FCRCustomerCardMasterDAO extends BaseDao {
	
	public FCRCustomerCardMasterDAO(Session session) {
		super(session);
	}
	
	
	public FCRCustomerCardMaster get(java.io.Serializable pk) {
		return (FCRCustomerCardMaster) this.getSession().get(FCRCustomerCardMaster.class, pk);
	}
	
	@SuppressWarnings("unchecked")
	public java.util.List<FcrChAcctMast> getAccounts(FCRCustomerCardMaster customerCard) {
		Query query = this.getSession().getNamedQuery("fcrCustomerCardMaster#getAccounts");
		query.setString("cardNo", customerCard.getCardNo());
		query.setString("maintenanceStatus", customerCard.getMaintenanceStatus());
		query.setInteger("entityVPD", customerCard.getEntityVPD());
		
		List<Object[]> resultList = query.list();
		List<FcrChAcctMast> acctList = new java.util.ArrayList<FcrChAcctMast>();
		FcrChAcctMast acct;
		
		for (Object[] arrObj : resultList) {
			acct = new FcrChAcctMast();
			acct.setCompositeId(new FcrChAcctMastPK());
			
			acct.getCompositeId().setCodAcctNo(arrObj[0].toString());
			acct.getCompositeId().setFlgMntStatus(arrObj[1].toString());
			acct.setCodEntityVpd(Integer.parseInt(arrObj[2].toString()));
			acct.setCodCust(Integer.parseInt(arrObj[3].toString()));
			acct.setCodAcctStat(Integer.parseInt(arrObj[4].toString()));
			acct.setCodAcctTitle(arrObj[5].toString());
			
			acctList.add(acct);
		}
		
		return acctList;
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
