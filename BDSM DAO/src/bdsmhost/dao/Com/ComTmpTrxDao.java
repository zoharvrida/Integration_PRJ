/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao.Com;

import bdsm.model.BaseModel;
import bdsm.model.Com.ComBrnMast;
import bdsm.model.Com.TmpComTrx;
import bdsm.model.Com.TmpComTrxPK;
import bdsmhost.dao.BaseDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class ComTmpTrxDao extends BaseDao{

    private Logger logger = Logger.getLogger(ComTmpTrxDao.class);
    public ComTmpTrxDao(Session session) {
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
        getSession().delete(model);
        return true;
    }
    public List getTmpList(TmpComTrxPK pk){
        List<TmpComTrx> detail = new ArrayList();
        try {
            Query q = this.getSession().getNamedQuery("TmpComTrx#getListDenom");
            q.setString("codccy", pk.getCodCcy());
            q.setString("txnstatus", pk.getTxnStatus());
            q.setString("txnid", pk.getTxnId());
            q.setString("txntype", pk.getTxnType());
            q.setString("iduser", pk.getUserId());
            q.setInteger("txnSeq", 1);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return detail;
        }
    }
    public List getBrnPair(String branch, String type){
        List<ComBrnMast> lisTdenom = new ArrayList();
        try {
            Query q = this.getSession().getNamedQuery("ComTxnID#getbranchvendor");
            q.setString("pBranch", branch);
            q.setString("pType", type);
            //q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return lisTdenom;
        }
    }
    
}
