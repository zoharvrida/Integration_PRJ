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
    /**
     * 
     * @param session
     */
    public PmNetworkCldrDao(Session session) {
        super(session);
    }
    /**
     * 
     * @param pkID
     * @return
     */
    public PmNetworkCldr get(FixCalendarPK pkID) {
        return (PmNetworkCldr) getSession().get(PmNetworkCldr.class, pkID);
    }
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
