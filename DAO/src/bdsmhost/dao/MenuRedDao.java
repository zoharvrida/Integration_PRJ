/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MenuRedirect;
import bdsm.model.MenuRedirectionPK;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class MenuRedDao extends BaseDao {
    public MenuRedDao(Session session) {
        super(session);
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public MenuRedirect get(MenuRedirectionPK menuID) {
        return (MenuRedirect) getSession().get(MenuRedirect.class, menuID);
    }
}
