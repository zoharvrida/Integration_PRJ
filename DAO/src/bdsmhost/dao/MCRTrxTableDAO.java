/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MCRTrxTable;
import org.hibernate.Session;

/**
 *
 * @author v00022309
 */
public class MCRTrxTableDAO extends BaseDao {

    public MCRTrxTableDAO(Session session) {
        super(session);
    }
    
    
    public MCRTrxTable MCR_getByRefNo(String refNetworkNo, Integer codOrgBrn) {
        org.hibernate.Query query = this.getSession().getNamedQuery("MCRTrxTable#MCR_getByRefNo");
        query.setString("refNetworkNo", refNetworkNo.toString());
        query.setInteger("codOrgBrn", codOrgBrn); 
		MCRTrxTable mcrData = (MCRTrxTable) query.uniqueResult(); 
		return mcrData;
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save(model);
        
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
    
    public MCRTrxTable get(String refNo) {
        return (MCRTrxTable) getSession().get(MCRTrxTable.class, refNo);
    }
    
}
