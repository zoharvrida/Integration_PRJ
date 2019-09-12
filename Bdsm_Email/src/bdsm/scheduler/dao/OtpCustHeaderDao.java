/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.OtpCustHeader;
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
public class OtpCustHeaderDao extends BaseDao{
    private String number;

    public OtpCustHeaderDao(Session session) {
        super(session);
    }
     public List get(String noBatch) {
        Criteria criteriaQuery = getSession().createCriteria(OtpCustHeader.class);
        criteriaQuery.add(Restrictions.eq("batchID", noBatch));
        return criteriaQuery.list();
    }
    
    public OtpCustHeader getModel(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(OtpCustHeader.class);
        criteriaQuery.add(Restrictions.eq("batchID", batchId));
         return (OtpCustHeader)criteriaQuery.uniqueResult();
    }  
    
    public List<OtpCustHeader> getNumber(String batchId,Integer record_id) {
        Criteria criteriaQuery = getSession().createCriteria(OtpCustHeader.class);
        criteriaQuery.add(Restrictions.eq("batchID", batchId));
        criteriaQuery.add(Restrictions.eq("recID", record_id));
        return criteriaQuery.list();
    }
    public String getMax(String idBatch){
        String number;
        StringBuilder qry = new StringBuilder("select max(ID)");
        qry.append(" FROM TMP_OTP_HEADER a");
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
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((OtpCustHeader)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
         getSession().update((OtpCustHeader)model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((OtpCustHeader)model);
        return true;
    }
    public String bankMast() {
        //CustomResult1 customResult1 = null;
        StringBuilder qry = new StringBuilder("select dat_process");
        qry.append(" from ba_bank_mast a");
        
        Query q = getSession().createSQLQuery(qry.toString());
        return q.uniqueResult().toString();
    }
}
