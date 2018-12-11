/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.EktpCoreMapping;
import bdsm.model.EktpCoreMappingPK;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class EktpCoreMappingDao extends BaseDao{

	protected Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }
    public EktpCoreMappingDao(Session session) {
        super(session);
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((EktpCoreMapping)model);
        return true;
    }

    public EktpCoreMapping get(EktpCoreMappingPK pk) {
		getLogger().info("PK :" + pk);
        return (EktpCoreMapping)getSession().get(EktpCoreMapping.class, pk);
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
