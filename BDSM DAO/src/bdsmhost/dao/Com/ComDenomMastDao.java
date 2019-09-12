/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao.Com;

import bdsm.model.BaseModel;
import bdsm.model.Com.ComDenomReq;
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
public class ComDenomMastDao extends BaseDao {

    private Logger logger = Logger.getLogger(ComDenomMastDao.class);
    public ComDenomMastDao(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
        return true;
    }
    
    public List getDenomList(String currency){
        List<ComDenomReq> lisTdenom = new ArrayList();
        try {
            Query q = this.getSession().getNamedQuery("ComDenomMast#getListDenom");
            q.setString("idCCy", currency);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return lisTdenom;
        }
    }
    public List getDenomListOnEdit(String idUser, String txnType, String status){
        List lisTdenom = new ArrayList();
        try {
            Query q = this.getSession().getNamedQuery("ComDenomMast#getListDenomEdit");
            q.setString("pidUser", idUser);
            q.setString("ptxnType", txnType);
            q.setString("ptxnStatus", status);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return lisTdenom;
        }
    }
    public List getDenomListOnEditBatch(String idUser, String idBatch, String txnType, String status){
        List lisTdenom = new ArrayList();
        try {
            Query q = this.getSession().getNamedQuery("ComDenomMast#getListDenomEditBatch");
            q.setString("pidUser", idUser);
            q.setString("ptxnId", idBatch);
            q.setString("ptxnType", txnType);
            q.setString("ptxnStatus", status);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return lisTdenom;
        }
    }
}
