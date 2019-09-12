/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;

/**
 *
 * @author 00030663
 */
public class BusinessDateDao extends BaseDao {
    public BusinessDateDao(Session session) {
        super(session);
    }
    
    public Date get() {
        return Calendar.getInstance().getTime();
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
}
