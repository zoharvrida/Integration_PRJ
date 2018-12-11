package bdsmhost.dao;


import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.MPAcctReg;
import bdsm.model.MPAcctRegDtls;
import bdsmhost.fcr.dao.BaBankMastDAO;


/**
 * @author v00019372
 */
public class MPAcctRegDAO extends BaseDao {

	public MPAcctRegDAO(Session session) {
		super(session);
	}
	
	
	public MPAcctReg getById(java.io.Serializable id) {
		return ((MPAcctReg) this.getSession().get(MPAcctReg.class, id));
	}
	
	public MPAcctReg getByActiveAccountNo(String accountNo) {
		java.util.List<MPAcctReg> list = this.getAllByActiveAccountNo(accountNo);
		
		if ((list != null) && (list.size() > 0))
			return list.get(0);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public java.util.List<MPAcctReg> getAllByActiveAccountNo(String accountNo) {
		BaBankMastDAO bankDAO = new BaBankMastDAO(this.getSession());
		java.util.Date date = bankDAO.get().getBusinessDate();
		
		
		Query query = this.getSession().getNamedQuery("MPAcctReg#getByActiveAccountNo");
		query.setString("accountNo", bdsm.util.BdsmUtil.rightPad(accountNo, bdsm.fcr.model.ChAcctMastPK.ACCT_NO_LENGTH));
		query.setDate("date", DateUtils.setDays(date, 1));
		
		return query.list();
	}
	
	public Long generateIdRegistration(String businessDate) {
		Query query = this.getSession().getNamedQuery("MPAcctReg#getMaximumId");
		Long maxId = (Long) query.uniqueResult();
		Long id = null;
		
		if ((maxId == null) || !(maxId.toString().startsWith(businessDate)))
			id = 1L;
		else
			id = Long.valueOf(maxId.toString().substring(businessDate.length())) + 1;
			
		return Long.valueOf(businessDate + bdsm.util.BdsmUtil.leftPad(id.toString(), 3, '0'));
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		MPAcctReg acctReg = (MPAcctReg) model;
		java.util.Set<MPAcctRegDtls> details;
		
		if ((details = acctReg.getDetails()) != null) {
			java.util.Iterator<MPAcctRegDtls> it = details.iterator();
			while(it.hasNext()) {
				MPAcctRegDtls d = it.next();
				d.setIdCreatedBy(model.getIdCreatedBy());
				d.setDtmCreated(model.getDtmCreated());
				d.setIdCreatedSpv(model.getIdCreatedSpv());
				d.setDtmCreatedSpv(model.getDtmCreatedSpv());
			}
		}
		
		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented...");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented...");
	}

}
