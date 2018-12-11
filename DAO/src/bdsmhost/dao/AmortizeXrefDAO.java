/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.AmortizeProgramDetail;
import bdsm.model.AmortizeXref;
import bdsm.model.BaseModel;
import org.hibernate.Session;

/**
 *
 * @author NCBS
 */
public class AmortizeXrefDAO extends BaseDao {

	public AmortizeXrefDAO(Session session) {
		super(session);
	}
	
	public AmortizeXref get(String pk){
		return (AmortizeXref) this.getSession().get(AmortizeXref.class, pk);
    }
	@Override
	protected boolean doInsert(BaseModel model) {
        this.getSession().save((AmortizeXref) model);
        return true;
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
