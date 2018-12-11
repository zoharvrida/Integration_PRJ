package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.AmortizeRecord;
import bdsm.model.BaseModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


public class AmortizeRecordDAO extends BaseDao {

	public AmortizeRecordDAO(Session session) {
		super(session);
	}
	
	
	public AmortizeRecord getRecordByAmortizeAccountIdAndSequenceId(Integer amortizeAccountId, Integer sequenceId) {
		List<AmortizeRecord> list = this.getRecordsByAmortizeAccountId(amortizeAccountId, false);
		AmortizeRecord record = null;
		
		if (sequenceId < list.size())
			record = list.get(list.size() - sequenceId);
		
		list.clear();
		return record;
	}
	
	public AmortizeRecord getLastRecordByAmortizeAccountId(Integer amortizeAccountId) {
		List<AmortizeRecord> list = this.getRecordsByAmortizeAccountId(amortizeAccountId, true);
		AmortizeRecord record = null;
		
		if (list.size() > 0)
			record = list.get(0);
		
		list.clear();
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public List<AmortizeRecord> getRecordsByAmortizeAccountId(Integer amortizeAccountId, boolean lastRecord) {
		Query query = this.getSession().getNamedQuery("AmortizeRecord#ByAmortizeAccountIdAndSequenceId");
		query.setInteger("amortizeAccountId", amortizeAccountId);
		query.setParameter("sequenceId", null, new org.hibernate.type.ShortType());
		
		if (lastRecord) {
			query.setFirstResult(0);
			query.setMaxResults(1);
		}
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AmortizeRecord> getDistinct(Integer amortID){
		Criteria crt = this.getSession().createCriteria(AmortizeRecord.class);
		crt.setProjection(Projections.distinct(Projections.property("amortizeAccountId")));
		crt.add(Restrictions.like("amortizeAccountId", amortID));
		return ((List<AmortizeRecord>) crt.list()); 
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((AmortizeRecord) model);
		
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not yet Implemented!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not yet Implemented!!!");
	}

}
