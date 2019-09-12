/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpPmFinInstDirMast;
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
 * @author v00020800
 */
public class PmFinInstDirMastDao extends BaseDao implements Work {
     private String parameter = null;
     private Logger logger = Logger.getLogger(getClass().getName());
//    private static final String queryUpload = "{call PK_UPD_AAJI.updateOldValue(?)}";
  private static final String queryUpload = "{call PK_BDSM_PM_FIN_DIR_MAST.validationDat(?)}";
    private static final String queryProcess = "{call PK_BDSM_PM_FIN_DIR_MAST.datProc(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;
    
    public PmFinInstDirMastDao(Session session){
        super(session);
    }

 public List<TmpPmFinInstDirMast> getByBachNo(String status,String param) {        
         Query query = getSession().createQuery("from TmpPmFinInstDirMast where flgstatus = COALESCE(:prm_status, flgstatus) and compositeId.batchNo = :prm_batch");
        query.setString("prm_status", status);
        query.setString("prm_batch", param);
        return query.list();
    }
    @Override
    protected boolean doInsert(BaseModel model) {
 getSession().save((TmpPmFinInstDirMast) model);

        return true;    
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
//                case 3:
//                    query = queryReject;
//                    break;                  
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
        }    }
    
      public int runUpload(String param) {
          logger.error("Procces to Upload");
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        return workResult1;
    }

    public int runUpdate(String param) {
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
      public TmpPmFinInstDirMast get(java.io.Serializable pmFinInstPK) {
	return (TmpPmFinInstDirMast) this.getSession().get(TmpPmFinInstDirMast.class, pmFinInstPK);
    }
}
