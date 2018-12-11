/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.BaBankCldr;
import bdsm.scheduler.model.FixCalendarPK;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class BaBankCldrDao extends BaseDao {
    /**
     * 
     * @param session
     */
    public BaBankCldrDao(Session session) {
        super(session);
    }
    /**
     * 
     * @param pkID
     * @return
     */
    public BaBankCldr get(FixCalendarPK pkID) {
        return (BaBankCldr) getSession().get(BaBankCldr.class, pkID);
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
