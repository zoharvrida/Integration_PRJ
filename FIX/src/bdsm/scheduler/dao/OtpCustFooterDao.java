/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.scheduler.dao;

import bdsm.model.BaseModel; 
import bdsm.scheduler.model.OtpCustFooter;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00020800
 */
public class OtpCustFooterDao extends BaseDao{
    private String number;

    /**
     * 
     * @param session
     */
    public OtpCustFooterDao(Session session) {
        super(session);
    }
    /**
     * 
     * @param noBatch
     * @return
     */
    public List get(String noBatch) {
        Criteria criteriaQuery = getSession().createCriteria(OtpCustFooter.class);
        criteriaQuery.add(Restrictions.eq("batchID", noBatch));
        return criteriaQuery.list();
    }
    
    /**
     * 
     * @param batchId
     * @return
     */
    public List<OtpCustFooter> getModel(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(OtpCustFooter.class);
        criteriaQuery.add(Restrictions.eq("batchID", batchId));
        return criteriaQuery.list();
    }
    
    /**
     * 
     * @param batchId
     * @param record_id
     * @return
     */
    public List<OtpCustFooter> getNumber(String batchId,Integer record_id) {
        Criteria criteriaQuery = getSession().createCriteria(OtpCustFooter.class);
        criteriaQuery.add(Restrictions.eq("batchID", batchId));
        criteriaQuery.add(Restrictions.eq("recID", record_id));
        return criteriaQuery.list();
    }
    /**
     * 
     * @param idBatch
     * @return
     */
    public String getMax(String idBatch){
        String number;
        StringBuilder qry = new StringBuilder("select max(ID)");
        qry.append(" FROM TMP_OTP_FOOTER a");
        qry.append(" where a.BATCHID = :idBatch");
        
        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("idBatch", idBatch);
        if(q.uniqueResult().toString() == null){
            number = "0";
        } else {
            number = q.uniqueResult().toString();
        }
        return number;
    }
    /**
     * 
     * @param idBatch
     * @return
     */
    public String updateReject(String idBatch){
        StringBuilder qry = new StringBuilder("update ");
        qry.append(" TMP_OTP_HEADER a set STATUS='R' ");
        qry.append(" where a.BATCHID = :idBatch");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("idBatch", idBatch);
        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((OtpCustFooter)model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
         getSession().update((OtpCustFooter)model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((OtpCustFooter)model);
        return true;
    }
  
}
