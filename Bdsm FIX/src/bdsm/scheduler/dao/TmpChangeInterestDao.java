/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpChangeInterest;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00020841
 */
public class TmpChangeInterestDao extends BaseDao implements Work{

    private String parameter = null;
    private static final String queryUpload = "{call pk_bdsm_chrate_interest_fcc.validationDat(?)}";
    private static final String queryProcess = "{call pk_bdsm_chrate_interest_fcc.datProc(?)}";
    private static final String queryRejectSpv = "{call pk_bdsm_chrate_interest_fcc.rejectSpv(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;
    
    /**
     * 
     * @param session
     */
    public TmpChangeInterestDao(Session session) {
        super(session);
    }
    
    /**
     * 
     * @param TmpChangeInterestPK
     * @return
     */
    public TmpChangeInterest get(java.io.Serializable TmpChangeInterestPK){
        return (TmpChangeInterest) this.getSession().get(TmpChangeInterest.class, TmpChangeInterestPK);
    }
    
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save((TmpChangeInterest)model);
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
        throw new UnsupportedOperationException("Not supported yet.");
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
                    query = queryProcess;
                    break;
                case 3:
                    query = queryRejectSpv;
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
    public int runUpload(String param) {
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
    public int runProcess(String param) {
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
    public int runReject(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }
    
}
