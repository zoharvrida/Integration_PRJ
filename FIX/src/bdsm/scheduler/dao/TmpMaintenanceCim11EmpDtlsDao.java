/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpMaintenanceCim11EmpDtls;
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
public class TmpMaintenanceCim11EmpDtlsDao extends BaseDao implements Work{

    /**
     * 
     * @param session
     */
    public TmpMaintenanceCim11EmpDtlsDao(Session session) {
        super(session);
    }  
    private String parameter = null;
    private static final String queryUpload = "{call PK_BDSM_UPDATE_CIM11.validationDatEmpDtls(?)}";
    private static final String queryProcess = "{call PK_BDSM_UPDATE_CIM11.datProcEmpDtls(?)}";
    private static final String queryRejectSpv = "{call PK_BDSM_UPDATE_CIM11.rejectSpvEmpDtls(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;
    
    /**
     * 
     * @param TmpMaintenanceCim11EmpDtlsPK
     * @return
     */
    public TmpMaintenanceCim11EmpDtls get(java.io.Serializable TmpMaintenanceCim11EmpDtlsPK){
        return (TmpMaintenanceCim11EmpDtls) this.getSession().get(TmpMaintenanceCim11EmpDtls.class, TmpMaintenanceCim11EmpDtlsPK);
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save((TmpMaintenanceCim11EmpDtls)model);
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
     */
    public void execute(Connection cnctn) {
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
    public int runValidate(String param) {
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
