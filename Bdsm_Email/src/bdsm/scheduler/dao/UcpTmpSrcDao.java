/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.UcpTmpSrc;
import bdsmhost.dao.BaseDao;
import java.sql.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author NCBS
 */
public class UcpTmpSrcDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryUpload = "{call PK_UPD_CIFPROP.updateOldValue(?)}";
    private static final String queryUpdate = "{call PK_UPD_CIFPROP.updateCIFPROP(?)}";
    private static final String queryReject = "{call PK_UPD_CIFPROP.rejectCIFPROP(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    public UcpTmpSrcDao(Session session) {
        super(session);
    }

    public List<UcpTmpSrc> get(String param){        
        Query query = getSession().createQuery("from UcpTmpSrc where nobatch = :prm_batch");
        query.setString("prm_batch", param);
        return query.list();
    }

    @Override
    protected boolean doInsert(BaseModel model){
        getSession().save((UcpTmpSrc) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((UcpTmpSrc) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((UcpTmpSrc) model);
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

    public int runUploadUcp(String param) {
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        return workResult1;
    }

    public int runUpdateUcp(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    public int runRejectUcp(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }
}
