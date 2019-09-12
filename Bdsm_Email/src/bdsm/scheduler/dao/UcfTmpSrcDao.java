/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.UcfTmpSrc;
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
 * @author NCBS
 */
public class UcfTmpSrcDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryUpload = "{call PK_UDF_CUS.updateOldValue(?)}";
    private static final String queryInsert = "{call PK_UDF_CUS.insertUDFCUS(?)}";
    private static final String queryUpdate = "{call PK_UDF_CUS.updateUDFCUS(?)}";
    private static final String queryReject = "{call PK_UDF_CUS.rejectUDFCUS(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workResult4 = 0;
    private int workType = 0;

    public UcfTmpSrcDao(Session session) {
        super(session);
    }

    public List<UcfTmpSrc> get(String param) {        
        Query query = getSession().createQuery("from UcfTmpSrc where nobatch = :prm_batch");
        query.setString("prm_batch", param);
        return query.list();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((UcfTmpSrc) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((UcfTmpSrc) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((UcfTmpSrc) model);
        return true;
    }

    public void execute(Connection cnctn) {
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

    public int runUploadUlc(String param) {
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        return workResult1;
    }

    public int runInsertUlc(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    public int runUpdateUlc(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }

    public int runRejectUlc(String param) {
        Session session = getSession();
        workType = 4;
        parameter = param;
        session.doWork(this);
        return workResult4;
    }
}
