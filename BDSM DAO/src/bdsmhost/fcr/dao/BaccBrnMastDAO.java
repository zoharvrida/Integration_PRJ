/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.BaCcBrnMast;
import bdsm.fcr.model.BaCcBrnMastPK;
import bdsm.model.BaseModel;
import bdsm.model.FcrCiCustmast;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00020841
 */
public class BaccBrnMastDAO extends BaseDao {

	private static final Integer codEntity = 11;
	private static final String Status = "A";
	
	public BaccBrnMastDAO(Session session) {
		super(session);
	}

	public BaCcBrnMast getBranch(BaCcBrnMastPK pk) {
		pk.setCodEntityVpd(codEntity);
                pk.setFlgMntStatus(Status);
		return (BaCcBrnMast) this.getSession().get(BaCcBrnMast.class, pk);
	}

	public List<BaCcBrnMast> getSpecificRegion(List region, Integer branchS) {
		Criteria criteriaQuery = getSession().createCriteria(FcrCiCustmast.class);
		criteriaQuery.add(Restrictions.in("namCcCity", region));
		criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus", Status));
		criteriaQuery.add(Restrictions.eq("compositeId.codEntityVpd", codEntity));
		criteriaQuery.add(Restrictions.eq("compositeId.codCcBrn", branchS));
		List<BaCcBrnMast> branch = criteriaQuery.list();
		return branch;
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
