/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpBdFuzzyName;
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
 * @author v00019237
 */
public class TmpBdFuzzyNameDao extends BaseDao implements Work {

    private String parameter = null;
    private String spv = null;
    private static final String queryUpload = "{call PK_BD_FUZZY.validateSource(?,?)}";
    private static final String queryInsert = "{call PK_BD_FUZZY.insertFuzzy(?,?)}";
    private static final String queryReject = "{call PK_BD_FUZZY.rejectFuzzy(?,?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    /**
     * 
     * @param session
     */
    public TmpBdFuzzyNameDao(Session session) {
        super(session);
    }
    
    /**
     * 
     * @param param
     * @return
     */
    public List get(String param){
        Query query = getSession().createQuery("from TmpBdFuzzyName where batchNo = :prm_batch");
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
        getSession().save((TmpBdFuzzyName) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((TmpBdFuzzyName) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((TmpBdFuzzyName) model);
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
                    query = queryReject;
                    break;                    
            }
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.setString(1, parameter);
            stmt.setString(2, spv);
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
            throw new SQLException(ex);
        }
    }

    /**
     * 
     * @param param
     * @param spv
     * @return
     */
    public int runValidateSource(String param,String spv) {
        Session session = getSession();
        workType = 1;
        this.parameter = param;
        this.spv = spv;
        session.doWork(this);
        return workResult1;
    }

    /**
     * 
     * @param param
     * @param spv
     * @return
     */
    public int runInsert(String param,String spv) {
        Session session = getSession();
        workType = 2;
        this.parameter = param;
        this.spv = spv;
        session.doWork(this);
        return workResult2;
    }

    /**
     * 
     * @param param
     * @param spv
     * @return
     */
    public int runReject(String param,String spv) {
        Session session = getSession();
        workType = 3;
        this.parameter = param;
        this.spv = spv;
        session.doWork(this);
        return workResult3;
    }    
}
