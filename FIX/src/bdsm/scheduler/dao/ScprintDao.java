/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrBaBankMast;
import bdsm.model.FcrBaBankMastPK;
import bdsm.model.GLCostCentreXref;
//import bdsm.scheduler.model.UajTmpSrc;
import bdsm.scheduler.model.TmpGlm;
import bdsmhost.dao.BaseDao;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jdbc.Work;
/**
 * @author V00020654
 */
public class ScprintDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryUpload = "{call PK_UPD_AAJI.updateOldValue(?)}";
    private static final String queryUpdate = "{call PK_UPD_AAJI.updateAAJI(?)}";
    private static final String queryPKSC = "{call PK_SC.getDataSC }";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    /**
     * 
     * @param session
     */
    public ScprintDao(Session session) {
        super(session);
    }

    
    /**
     * 
     * @param glCostCentreXrefPK
     * @return
     */
    public GLCostCentreXref get(java.io.Serializable glCostCentreXrefPK) {
	return (GLCostCentreXref) this.getSession().get(GLCostCentreXref.class, glCostCentreXrefPK);
    }
    

   
    
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
       // getSession().save((TmpGlm) model);
     //   return true; 
        throw new UnsupportedOperationException("not yet implemented");
       
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
       throw new UnsupportedOperationException("not yet implemented");      
    } 
 
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
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
                    query = queryUpload;
                    break;
                case 2:
                    query = queryUpdate;
                    break;
                case 3:
                    query = queryPKSC;
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
    public int runUploadUaj(String param) {
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
    public int runUpdateUaj(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    /**
     * 
     * @return
     */
    public int runSCPrint() {
        Session session = getSession();
        workType = 3;
      //  parameter = param;
        session.doWork(this);
        return workResult3;
    }
}
