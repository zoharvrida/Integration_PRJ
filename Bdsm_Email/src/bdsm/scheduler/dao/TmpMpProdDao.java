/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpMpProd;
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
public class TmpMpProdDao extends BaseDao implements Work {

    private String parameter = null;
    private static final String queryUpload = "{call PK_BDSM_MITRAPASTI.validateMPProd(?)}";
    private static final String queryProcess = "{call PK_BDSM_MITRAPASTI.processMPProd(?)}";
    private static final String queryRejectSpv = "{call PK_BDSM_MITRAPASTI.rejectMPProd(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    public TmpMpProdDao(Session session) {
        super(session);
    }

    public TmpMpProd get(java.io.Serializable tmpMpProdPK) {
        return (TmpMpProd) this.getSession().get(TmpMpProd.class, tmpMpProdPK);
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

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save((TmpMpProd) model);
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

}
