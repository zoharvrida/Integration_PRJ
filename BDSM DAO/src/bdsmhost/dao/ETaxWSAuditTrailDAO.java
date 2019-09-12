/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import org.hibernate.Session;

/**
 *
 * @author bdsm
 */
public class ETaxWSAuditTrailDAO extends BaseDao{

    public ETaxWSAuditTrailDAO(Session session) {
		super(session);
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
    
}
