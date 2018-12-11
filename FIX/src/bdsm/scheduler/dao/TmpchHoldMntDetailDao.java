/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrCiCustmast;
import bdsm.scheduler.model.TmpchHoldMntDetail;
import bdsm.scheduler.model.TmpchHoldMntHeader;
import bdsm.scheduler.model.TmpchHoldMntPK;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class TmpchHoldMntDetailDao extends BaseDao {

    /**
     * 
     * @param session
     */
    public TmpchHoldMntDetailDao(Session session) {
        super(session);
    }
    /**
     * 
     * @param pk
     * @return
     */
    public TmpchHoldMntDetail get(TmpchHoldMntPK pk){
        return (TmpchHoldMntDetail) getSession().get(TmpchHoldMntDetail.class, pk);
    }
    
    /**
     * 
     * @param idBatch
     * @return
     */
    public List<TmpchHoldMntDetail> getList(String idBatch){
        Criteria criteriaQuery = getSession().createCriteria(TmpchHoldMntDetail.class);
        criteriaQuery.add(Restrictions.eq("compositeId.idBatch", idBatch));
        return criteriaQuery.list();
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
