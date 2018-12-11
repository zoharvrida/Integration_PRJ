/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.UdfRrmTmpSrc;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00020801
 */
public class UdfRrmTmpSrcDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryUpload = "{call PK_BDSM_RRM_UDF_CUS.updateOldValue(?)}";
    private static final String queryInsert = "{call PK_BDSM_RRM_UDF_CUS.insertUDFRRMCUS(?)}";
    private static final String queryUpdate = "{call PK_BDSM_RRM_UDF_CUS.updateUDFRRMCUS(?)}";
    private static final String queryReject = "{call PK_BDSM_RRM_UDF_CUS.rejectUDFRRMCUS(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workResult4 = 0;
    private int workType = 0;

    /**
     * 
     * @param session
     */
    public UdfRrmTmpSrcDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param param
     * @return
     */
    public List<UdfRrmTmpSrc> get(String param) {        
        Query query = getSession().createQuery("from UdfRrmTmpSrc where batch = :prm_batch");
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
        getSession().save((UdfRrmTmpSrc) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
       getSession().update((UdfRrmTmpSrc) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
       getSession().delete((UdfRrmTmpSrc) model);
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
                    query = queryUpload;
                    break;
                case 2:
                    query = queryInsert;
                    break;
                case 3:
                    query = queryUpdate;
                    break;
                case 4:
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
                case 4:
                    workResult4 = workRes;
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
    public int runUploadRrm(String param) {
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
    public int runInsertRrm(String param) {
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
    public int runUpdateRrm(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runRejectRrm(String param) {
        Session session = getSession();
        workType = 4;
        parameter = param;
        session.doWork(this);
        return workResult4;
    }
    
}
