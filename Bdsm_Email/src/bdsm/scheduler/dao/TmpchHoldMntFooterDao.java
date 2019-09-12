/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpchHoldMntDetail;
import bdsm.scheduler.model.TmpchHoldMntFooter;
import bdsm.scheduler.model.TmpchHoldMntPK;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class TmpchHoldMntFooterDao extends BaseDao{

    public TmpchHoldMntFooterDao(Session session) {
        super(session);
    }
    public TmpchHoldMntFooter get(TmpchHoldMntPK pk){
        return (TmpchHoldMntFooter) getSession().get(TmpchHoldMntFooter.class, pk);
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
