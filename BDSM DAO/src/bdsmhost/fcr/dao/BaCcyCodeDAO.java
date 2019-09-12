package bdsmhost.fcr.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.fcr.model.BaCcyCode;
import bdsm.fcr.model.BaCcyCodePK;
import bdsm.model.BaseModel;


/**
 * 
 * @author v00019372
 */
public class BaCcyCodeDAO extends bdsmhost.dao.BaseDao {

	public BaCcyCodeDAO(Session session) {
		super(session);
	}
	
	
	public BaCcyCode get(BaCcyCodePK id) {
		return (BaCcyCode) this.getSession().get(BaCcyCode.class, id);
	}
	
	public BaCcyCode getByNamCcyShort(String namCcyShort) {
		Criteria crt = this.getSession().createCriteria(BaCcyCode.class);
		crt.add(Restrictions.eq("namCcyShort", namCcyShort));
		crt.add(Restrictions.eq("compositeId.flgMntStatus", "A"));
		crt.add(Restrictions.eq("compositeId.codEntityVPD", 11));
		
		return ((BaCcyCode) crt.uniqueResult());
	}
    public BaCcyCode getByCcyCod(Integer codCcy) {
        BaCcyCodePK id = new BaCcyCodePK(codCcy, "A", 11);
        return this.get(id);
    }

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

}
