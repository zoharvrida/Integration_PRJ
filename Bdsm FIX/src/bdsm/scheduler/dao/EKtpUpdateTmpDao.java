/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.EKtpUpdateTmp;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author NCBS
 */
public class EKtpUpdateTmpDao extends BaseDao implements Work {

    private String parameter = null;
    private static final String queryValidate = "{call pk_ektp_update.validateEktp(?)}";
    private static final String queryUpdate = "{call pk_ektp_update.updateEktp(?)}";
    private static final String queryReject = "{call pk_ektp_update.rejectEktp(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    /**
     * 
     * @param session
     */
    public EKtpUpdateTmpDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param param
     * @return
     */
    public List<EKtpUpdateTmp> get(String param) {
        Query query = getSession().createQuery("from EKtpUpdateTmp where nobatch = :prm_batch");
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
        getSession().save((EKtpUpdateTmp) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((EKtpUpdateTmp) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((EKtpUpdateTmp) model);
        return true;
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
                    query = queryValidate;
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
            
        }
    }

    /**
     * 
     * @param param
     * @return
     */
    public int runValidateEKtpUpdate(String param) {
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
    public int runUpdateEKtpUpdate(String param) {
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
    public int runRejectEKtpUpdate(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }
}
