/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao.Com;

import bdsm.model.BaseModel;
import bdsm.model.Com.ComCdDtls;
import bdsm.model.Com.ComCdMast;
import bdsmhost.dao.BaseDao;
import java.util.ArrayList;
import java.util.Date;
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
public class ComCdMastDao extends BaseDao {

    private Logger logger = Logger.getLogger(ComCdMastDao.class);
    public ComCdMastDao(Session session) {
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
    
    public List getlistDetails(String user,String ccy, Date datReq){
        List lisTdenom = new ArrayList();
        try {
            Query q = this.getSession().getNamedQuery("ComCdMast#getListTrx");
            q.setString("iduser", user);
            q.setDate("dtmreq", datReq);
            q.setString("codccy", ccy);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return lisTdenom;
        }
    }
    public List checkExisting(String pk){
        List<ComCdDtls> detail = new ArrayList();
        try {
            Query q = this.getSession().getNamedQuery("ComCdMast#getTrxbyBatch");
            q.setString("txnid", pk);
            
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            return q.list();
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return detail;
        }
    }
}
