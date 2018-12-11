/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.SknNgIncomingCreditFooter;
import bdsm.model.SknNgIncomingCreditPK;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class SknNgInOutGen1CreditFooterDAO extends BaseDao{
    public SknNgInOutGen1CreditFooterDAO(Session session) {
		super(session);
		// TODO Auto-generated constructor stub
    }
	public SknNgIncomingCreditFooter get(SknNgIncomingCreditPK pk) {
	        return (SknNgIncomingCreditFooter) getSession().get(SknNgIncomingCreditFooter.class, pk);
	 }
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((SknNgIncomingCreditFooter) model);
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
