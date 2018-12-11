/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.GLCostCentreXref;
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
public class GLCostCentreXrefDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryUpload = "{call PK_UPD_AAJI.updateOldValue(?)}";
    private static final String queryUpdate = "{call PK_UPD_AAJI.updateAAJI(?)}";
    private static final String queryReject = "{call PK_UPD_AAJI.rejectAAJI(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    public GLCostCentreXrefDao(Session session) {
        super(session);
    }

    public GLCostCentreXref get(java.io.Serializable glCostCentreXrefPK) {
	return (GLCostCentreXref) this.getSession().get(GLCostCentreXref.class, glCostCentreXrefPK);
    }
    

   
    
    @Override
    protected boolean doInsert(BaseModel model) {
       // getSession().save((TmpGlm) model);
     //   return true; 
        throw new UnsupportedOperationException("not yet implemented");
       
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
       throw new UnsupportedOperationException("not yet implemented");      
    } 
 
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
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

    public int runUploadUaj(String param) {
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        return workResult1;
    }

    public int runUpdateUaj(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    public int runRejectUaj(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }
}
