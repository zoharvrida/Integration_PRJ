/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpMaintenanceCim11Spouse;
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
public class TmpMaintenanceCim11SpouseDao extends BaseDao implements Work{

    public TmpMaintenanceCim11SpouseDao(Session session) {
        super(session);
    }  
    private String parameter = null;
    private static final String queryUpload = "{call PK_BDSM_UPDATE_CIM11.validationDatSpouse(?)}";
    private static final String queryProcess = "{call PK_BDSM_UPDATE_CIM11.datProcSpouse(?)}";
    private static final String queryRejectSpv = "{call PK_BDSM_UPDATE_CIM11.rejectSpvSpouse(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;
    
    public TmpMaintenanceCim11Spouse get(java.io.Serializable TmpMaintenanceCim11SpousePK){
        return (TmpMaintenanceCim11Spouse) this.getSession().get(TmpMaintenanceCim11Spouse.class, TmpMaintenanceCim11SpousePK);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save((TmpMaintenanceCim11Spouse)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
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

    public int runValidate(String param) {
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        return workResult1;
    }
    
     public int runProcess(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    public int runReject(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }
}
