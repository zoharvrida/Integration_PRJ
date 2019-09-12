/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.CiChUfCIFDetails;
import bdsm.scheduler.model.OtpCustDetail;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00020800
 */
public class OtpCustDetailDao extends BaseDao{
  private String parameter = null;
    private Integer cdBranch;
    private String number;
    
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult = 0;
    private int workType = 0;
        private Logger logger = Logger.getLogger(OtpCustDetailDao.class);

    public OtpCustDetailDao(Session session) {
        super(session);
    }
     public int runPackage(String param,Integer cdBranch) {
            Session session = getSession();
            workType = 1;
            this.parameter = param;
            this.cdBranch = cdBranch;
        try {
            session.doWork((Work) this);
           // runDDF(workType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("EXCEPTION RUN:" + e);
        }
        return workResult;
    } 
public String getMax(){
        StringBuilder qry = new StringBuilder("select count(RECORDID)");
        qry.append(" FROM TMP_OTP_DTLS ");
        Query q = getSession().createSQLQuery(qry.toString());
        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
public String getCount(String idBatch, String type){
        StringBuilder qry = new StringBuilder("select count(TYPEOTP)");
        qry.append(" FROM TMP_OTP_DTLS a");
        qry.append(" where a.BATCHID = :idBatch");
        qry.append(" and a.typeotp = :type");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("idBatch", idBatch);
        q.setString("type", type);

        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
public String getCountT(String idBatch){
        StringBuilder qry = new StringBuilder("select count(TYPEOTP)");
        qry.append(" FROM TMP_OTP_DTLS a");
        qry.append(" where a.BATCHID = :idBatch");
        qry.append(" and a.typeotp = 'T'");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("idBatch", idBatch);

        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
public String getCountP(String idBatch){
        StringBuilder qry = new StringBuilder("select count(TYPEOTP)");
        qry.append(" FROM TMP_OTP_DTLS a");
        qry.append(" where a.BATCHID = :idBatch");
        qry.append(" and a.typeotp = 'P'");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("idBatch", idBatch);

        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
 public List getDetails(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(OtpCustDetail.class);
        criteriaQuery.add(Restrictions.eq("batchId", batchId));
        criteriaQuery.addOrder(Order.asc("recordId"));
        return criteriaQuery.list();
    }
 public String deleteRecord(String idBatch){
        StringBuilder qry = new StringBuilder("delete ");
        qry.append(" FROM TMP_OTP_DTLS a");
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
 public String CodCust_Val(Integer codcust) {
        //CustomResult1 customResult1 = null;
        
        StringBuilder qry = new StringBuilder("select count(1)");
        qry.append(" from ci_custmast a");
        qry.append(" where a.flg_mnt_status = 'A' and a.cod_cust_id = :codcust");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setInteger("codcust", codcust);
        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
    @Override
    protected boolean doInsert(BaseModel model) {
         getSession().save((OtpCustDetail)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
