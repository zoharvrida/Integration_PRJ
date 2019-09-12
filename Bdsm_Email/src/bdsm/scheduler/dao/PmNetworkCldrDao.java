/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixCalendarPK;
import bdsm.scheduler.model.PmNetworkCldr;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class PmNetworkCldrDao extends BaseDao {
    public PmNetworkCldrDao(Session session) {
        super(session);
    }
    public PmNetworkCldr get(FixCalendarPK pkID) {
        return (PmNetworkCldr) getSession().get(PmNetworkCldr.class, pkID);
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
