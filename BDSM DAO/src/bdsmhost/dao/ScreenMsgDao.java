/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MenuRedirectionPK;
import bdsm.model.ScreenMsg;
import org.hibernate.Session;

/**
 *
 * @author 00110310
 */
public class ScreenMsgDao extends BaseDao {

    public ScreenMsgDao(Session session) {
        super(session);
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
    public ScreenMsg get(String tag) {
        return (ScreenMsg) getSession().get(ScreenMsg.class, tag);
    }
}
