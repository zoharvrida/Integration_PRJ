/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.model.CiChUfCIFDetails;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
 * @author v00019722 
 */
public class CiChUfCIFDetailsDao extends BaseDao implements Work {
    
    private String parameter = null;
    private Integer cdBranch;
    private String number;
    
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult = 0;
    private int workType = 0;
    
    private Logger logger = Logger.getLogger(CiChUfCIFDetailsDao.class);
    
    /**
     * 
     * @param session
     */
    public CiChUfCIFDetailsDao (Session session){
        super(session);
    }
    
    /**
     * 
     * @param batchId
     * @param counter
     * @return
     */
    public List getDetails(String batchId,Integer counter) {
        Criteria criteriaQuery = getSession().createCriteria(CiChUfCIFDetails.class);
        criteriaQuery.add(Restrictions.eq("id_Batch", batchId));
        criteriaQuery.add(Restrictions.eq("record_id_details", counter));
        criteriaQuery.addOrder(Order.asc("record_id_details"));
        return criteriaQuery.list();
    }
    /**
     * 
     * @param batchId
     * @return
     */
    public List getListDetails(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(CiChUfCIFDetails.class);
        criteriaQuery.add(Restrictions.eq("id_Batch", batchId));
        return criteriaQuery.list();
    }
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
       getSession().save((CiChUfCIFDetails)model);
       return true;      
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
       getSession().delete((CiChUfCIFDetails)model); 
        return true;
    }

    /**
     * 
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
        try {
            String query = null;
            switch (workType) {
                case 1:
                    query = queryRun;
                    break;
                case 2:
                    query = queryUpdate;
                    break;
            }
            CallableStatement stmt = cnctn.prepareCall(query);
            switch (workType) {
                case 1:
                    stmt.registerOutParameter(1, java.sql.Types.INTEGER);
                    stmt.setString(2, parameter);
                    stmt.setInt(3, cdBranch);                    
                    break;
                case 2:
                    stmt.registerOutParameter(1, java.sql.Types.INTEGER);
                    stmt.setString(2, parameter);
                    break;
            }
            stmt.executeUpdate();
            workResult = stmt.getInt(1);
            logger.info("return result :" + workResult);
            stmt.close();
            switch (workType) {
                case 1:
                    workResult1 = workResult;
                    break;
                case 2:
                    workResult2 = workResult;
                    break;
            }
            logger.info("RESULT :" + workResult);
        } catch(SQLException e) {
            e.printStackTrace();
            logger.info("ERROR_SQL :" + e);
        } catch(Exception e) {
            e.printStackTrace();
            logger.info("ERROR_EXCEPTION :" + e);
        }
    }
    /**
     * 
     * @param param
     * @param cdBranch
     * @return
     */
    public int runPackage(String param,Integer cdBranch) {
            Session session = getSession();
            workType = 1;
            this.parameter = param;
            this.cdBranch = cdBranch;
        try {
            session.doWork(this);
            //runDDF(workType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("EXCEPTION RUN:" + e);
        }
        return workResult;
    }
    /**
     * 
     * @param param
     * @return
     */
    public int runUpdateUF(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        try {
            session.doWork(this);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("EXCEPTION UPDATE:" + e);
        }
        return workResult2;
    }
    /**
     * 
     * @param codcust
     * @return
     */
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
    /**
     * 
     * @param codcust
     * @return
     */
    public String getFlagCust(Integer codcust) {
        //CustomResult1 customResult1 = null;
        StringBuilder qry = new StringBuilder("select flg_cust_typ");
        qry.append(" from ci_custmast a");
        qry.append(" where a.cod_cust_id = :codcust");
        qry.append(" and a.flg_mnt_status = 'A'");
        qry.append(" and a.cod_entity_vpd = 11");
        
        Query q = getSession().createSQLQuery(qry.toString());
        q.setInteger("codcust", codcust);
        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
    /**
     * 
     * @param type
     * @param order
     * @param flg
     * @param naming
     * @return
     */
    public String getMandatory(Integer type, Integer order, String flg, String naming) {
        //CustomResult1 customResult1 = null;
        naming = "%"+naming+"%";
        StringBuilder qry = new StringBuilder("select flg_mandatory");
        qry.append(" from fieldparameter a");
        qry.append(" where a.ID_FIELD = :type and a.order_no = :order");
        qry.append(" and a.FLG_TMP = :flg");
        qry.append(" and a.module_name like :nName ");
        
        Query q = getSession().createSQLQuery(qry.toString());
        q.setInteger("order", order);
        q.setInteger("type", type);
        q.setString("flg",flg);
        q.setString("nName",naming);
        return q.uniqueResult().toString();
    }
    /**
     * 
     * @param agama
     * @return
     */
    public String Agama_Val(String agama) {
        //CustomResult1 customResult1 = null;
        StringBuilder qry = new StringBuilder("select count(1)");
        qry.append(" from mow_scrn_dropdown a");
        qry.append(" where a.ctl_name = 'cmbEthnic' and a.item_index = :agama");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("agama", agama);
        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
    /**
     * 
     * @param Occup
     * @return
     */
    public String Occup_Val(String Occup) {
        //CustomResult1 customResult1 = null;
        StringBuilder qry = new StringBuilder("select count(1)");
        qry.append(" from ci_prof_codes a");
        qry.append(" where a.txt_profess_cat = :occup");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("occup", Occup);
        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
    /**
     * 
     * @param value
     * @param Tag
     * @return
     */
    public String lovMast_val(String value,String Tag) {
        //CustomResult1 customResult1 = null;
        StringBuilder qry = new StringBuilder("select count(1)");
        qry.append(" from udf_lov_mast a");
        qry.append(" where a.flg_mnt_status = 'A' and a.cod_field_tag = :Tag");
        qry.append(" and a.cod_entity_vpd = '11' and a.txt_lov_value = :value");
        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("Tag", Tag);
        q.setString("value", value);
        if (q.uniqueResult().toString() == null){
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
    public String getMax(String idBatch){
        StringBuilder qry = new StringBuilder("select max(RECORD_ID)");
        qry.append(" FROM TMP_DDFCIF_DETAILS a");
        qry.append(" where a.ID_BATCH = :idBatch");

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
     * @param idBatch
     * @return
     */
    public String deleteRecord(String idBatch){
        StringBuilder qry = new StringBuilder("delete ");
        qry.append(" FROM TMP_DDFCIF_DETAILS a");
        qry.append(" where a.ID_BATCH = :idBatch");

        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("idBatch", idBatch);
        if (q.uniqueResult().toString() == null){
           number = "0"; 
        } else {
           number = q.uniqueResult().toString(); 
        }
        return number;
    }
    private static final String queryRun = "{? = call PK_DDF_CICH.beginsCIF(?,?)}";
    private static final String queryUpdate = "{? = call PK_DDF_CICH.updateCIF(?)}";
}
