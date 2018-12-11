/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.scheduler.dao;

/**
 *
 * @author V00020654
 */
import bdsm.model.BaseModel;
import bdsm.model.GLCostCentreXref;
//import bdsm.scheduler.model.UajTmpSrc;
import bdsm.scheduler.model.TmpGlm;
import bdsmhost.dao.BaseDao;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jdbc.Work;

/**
 * 
 * @author bdsm
 */
public class TmpGlmDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryUpload = "{call PK_UPD_AAJI.updateOldValue(?)}";
    private static final String queryUpdate = "{call PK_UPD_AAJI.updateAAJI(?)}";
    private static final String queryReject = "{call PK_UPD_AAJI.rejectAAJI(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    /**
     * 
     * @param session
     */
    public TmpGlmDao(Session session) {
        super(session);
    } 
   
    /**
     * 
     * @param param
     * @return
     */
    public List<TmpGlm> getByBachNo(String param) {        
        Query query = getSession().createQuery("from TmpGlm where flagStatus ='U' and batchNo = :prm_batch");
        query.setString("prm_batch", param);
        return query.list();
    } 
   
   /**
    * 
    * @param model
    * @return
    */
   @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((TmpGlm) model);
        return true;
    }

   /**
    * 
    * @param model
    * @return
    */
   @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((TmpGlm) model);
        return true;
    }

   /**
    * 
    * @param model
    * @return
    */
   @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * 
     * @param cnctn
     */
    public void execute(Connection cnctn) {
        try {
            String query = null;
            switch (workType) {
                case 1:
                    query = queryUpload;
                    break;
                case 2:
                    query = queryUpdate;
                    break;
                case 3:
                    query = queryReject;
                    break;                    
            }
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.setString(1, parameter);
            int workRes = stmt.executeUpdate();
            stmt.close();
            switch (workType) {
                case 1:
                    workResult1 = workRes;
                    break;
                case 2:
                    workResult2 = workRes;
                    break;
                case 3:
                    workResult3 = workRes;
                    break;                  
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runUploadUaj(String param) {
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        return workResult1;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runUpdateUaj(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runRejectUaj(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }
    
}
