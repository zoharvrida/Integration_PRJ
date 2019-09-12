package bdsmhost.dao;


import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;

import bdsm.model.BaseModel;
import bdsm.model.BillerStandingInstruction;
import bdsm.util.BdsmUtil;


/**
 * @author v00019372
 */
public class BillerStandingInstructionDAO extends BaseHistoryInterceptorDAO {

	public BillerStandingInstructionDAO(Session session) {
		super(session);
	}


	public BillerStandingInstruction get(java.io.Serializable id) {
		return (BillerStandingInstruction) this.getSession().get(BillerStandingInstruction.class, id);
	}

	public BillerStandingInstruction getById(String id) {
		BillerStandingInstruction model = this.get(id);
		if (model != null)
			this.getSession().evict(model);
		
		return model;
	}

	public String getBICode(Integer productCode) throws Exception {
		String sql = "{? = call PKG_SKNNG_BULK.get_BIC(?, ?) }";
		CallableStatement stmt = ((SessionImpl) (this.getSession())).connection().prepareCall(sql);
		stmt.registerOutParameter(1, Types.NUMERIC);
		stmt.setInt(2, productCode);
		stmt.registerOutParameter(3, Types.VARCHAR);
		
		stmt.executeQuery();
		
		String result = null;
		if (stmt.getInt(1) == 1)
			result = stmt.getString(3); 
		
		stmt.close();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<BillerStandingInstruction> listByIdStartAndBillingNoAndBillerAccountNo(String idStartBy, String billingNo, String billerAccountNo) {
		Query query = this.getSession().getNamedQuery("BillerStandingInstruction#listByBillingNoAndBillerAccountNo")
				.setString("idStartBy", idStartBy)
				.setString("billingNo", billingNo)
				.setString("billerAccountNo", billerAccountNo);
		
		return query.list();
	}
	
	public BillerStandingInstruction getByBillingNoAndBillerAccountNo(String billingNo, String billerAccountNo) {
		List<BillerStandingInstruction> list = this.listByIdStartAndBillingNoAndBillerAccountNo("", billingNo, billerAccountNo);
		if (list.size() > 0)
			return list.get(0);
		
		return null;
	}

	public boolean isAlreadyExist(String idStartBy, String billingNo, String billerAccountNo) {
		return (this.listByIdStartAndBillingNoAndBillerAccountNo(idStartBy, billingNo, billerAccountNo).size() > 0);
	}

	@SuppressWarnings("unchecked")
	protected BillerStandingInstruction getLastCreatedData() {
		List<BillerStandingInstruction> list;
		Query query = this.getSession().getNamedQuery("BillerStandingInstruction#listLastCreatedData");
		query.setMaxResults(1);
		
		if ((list = query.list()).size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	protected synchronized boolean doInsert(BaseModel model) {
		BillerStandingInstruction data = (BillerStandingInstruction) model;
		BillerStandingInstruction lastCreatedData = this.getLastCreatedData();
		Integer sequence = 1;
		int maxLength = 6;
		
		if (lastCreatedData != null) {
			String strSeq = lastCreatedData.getId().substring(lastCreatedData.getId().length() - maxLength);
			if (strSeq.equals(BdsmUtil.leftPad("", maxLength, '9')) == false)
				sequence = Integer.parseInt(strSeq) + 1;
		}
		
		data.setId(data.getId() + BdsmUtil.leftPad(sequence.toString(), maxLength, '0'));
		data.setIdUpdatedBy(null);
		data.setIdUpdatedSpv(null);
		data.setDtmUpdated(null);
		
		this.getSession().save(data);
		
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		BillerStandingInstruction oldEntity = null;
		if ((oldEntity = this.getById(((BillerStandingInstruction) model).getId())) != null) {
			model.setIdCreatedBy(oldEntity.getIdCreatedBy());
			model.setIdCreatedSpv(oldEntity.getIdCreatedSpv());
			model.setDtmCreated(oldEntity.getDtmCreated());
						
			this.getSession().merge(model);
			
			this.commitTransaction();
			return true;
		}
		else
			return false;
	}
}
