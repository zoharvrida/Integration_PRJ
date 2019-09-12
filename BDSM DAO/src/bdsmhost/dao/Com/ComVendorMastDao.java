/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao.Com;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class ComVendorMastDao extends BaseDao {

    public ComVendorMastDao(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
        return true;
    }
}
