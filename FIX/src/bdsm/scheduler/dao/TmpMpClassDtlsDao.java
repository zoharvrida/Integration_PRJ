/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpMpClassDtls;
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
public class TmpMpClassDtlsDao extends BaseDao implements Work {

    private String parameter = null;
    private static final String queryUpload = "{call PK_BDSM_MITRAPASTI.validateMpClassDtls(?, ?)}";
    private static final String queryProcess = "{call PK_BDSM_MITRAPASTI.processMpClassDtls(?)}";
    private static final String queryRejectSpv = "{call PK_BDSM_MITRAPASTI.rejectMpClassDtls(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    /**
     * 
     * @param session
     */
    public TmpMpClassDtlsDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param tmpMpClassDtlsPK
     * @return
     */
    public TmpMpClassDtls get(java.io.Serializable tmpMpClassDtlsPK) {
        return (TmpMpClassDtls) this.getSession().get(TmpMpClassDtls.class, tmpMpClassDtlsPK);
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
            if (workType == 1)
                stmt.registerOutParameter(2, java.sql.Types.INTEGER);
            int workRes = stmt.executeUpdate();
            stmt.close();
            
            switch (workType) {
                case 1:
                    workResult1 = stmt.getInt(2);
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

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save((TmpMpClassDtls) model);
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

}
