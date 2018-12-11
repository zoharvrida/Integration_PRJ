/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class FcyTxnSummLimForwardDao extends BaseDao {
    public FcyTxnSummLimForwardDao(Session session) {
        super(session);
    }

    public List list(String flgAcct, String noAcct, Integer period) {
        List retResult = null;
        
        if (flgAcct.equals("0")) {
            Query q = getSession().getNamedQuery("FcyTxnSummLimForward#byCIF");
            q.setString("pNoAcct", noAcct.substring(4));
            q.setInteger("pPeriod", period);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            retResult = q.list();
        }
        else if(flgAcct.equals("1")) {
            Query q = getSession().getNamedQuery("FcyTxnSummLimForward#byAccountNo");
            q.setString("pNoAcct1", noAcct);
            q.setString("pNoAcct2", noAcct);
            q.setInteger("pPeriod", period);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            
            retResult = q.list();
        }
        
        return retResult;
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
