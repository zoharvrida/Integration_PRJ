/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpStRoutingBranch;

//import bdsm.scheduler.model.TmpGlm;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.hibernate.Session;
/**
 *
 * @author v00020800
 */
public class StRoutingBranchDao extends BaseDao implements Work{
    private String parameter = null;
    private static final String queryUpload = "{call PK_BDSM_ST_ROUTING_BRANCH.validationDat(?)}";
    private static final String queryProcess = "{call PK_BDSM_ST_ROUTING_BRANCH.datProc(?)}";

    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;
    
    /**
     * 
     * @param session
     */
    public StRoutingBranchDao(Session session){
        super(session);
    }

    
    /**
     * 
     * @param status
     * @param param
     * @return
     */
    public List<TmpStRoutingBranch> getByBachNo(String status,String param) {        
         Query query = getSession().createQuery("from TmpStRoutingBranch where flgstatus = COALESCE(:prm_status, flgstatus) and compositeId.batchNo = :prm_batch");
        query.setString("prm_status", status);
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
        getSession().save((TmpStRoutingBranch) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public int runUpdate(String param) {
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
     * @param stBranchPK
     * @return
     */
    public TmpStRoutingBranch get(java.io.Serializable stBranchPK) {
	return (TmpStRoutingBranch) this.getSession().get(TmpStRoutingBranch.class, stBranchPK);
    }
}
