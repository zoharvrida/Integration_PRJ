/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.rpt.dao.BaseDao;
import bdsm.scheduler.model.CiChUfCASADetails;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00019722
 */
public class DFCiChUfDetailsDao extends BaseDao implements Work {
    
    private String parameter = null;
    private String branch = null;
    private Session session;
    
    private String ID_Batch = null;
    private String DOB_Branch = null;
    private String codXF = null;
    private String fileType = null;
    private String pattern = null;
    private String fileName = null;
    private int closing;
    private String makerID = null;
    private String days = null;
    
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workResult4 = 0;
    private int workResult = 0;
    private int workType = 0;
    
    private Logger logger = Logger.getLogger(CiChUfCASADetailsDao.class);
    
    /**
     * 
     * @param session
     */
    public DFCiChUfDetailsDao (Session session){
        super(session);
    }
    /**
     * 
     * @param batchId
     * @return
     */
    public List getDetails(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(CiChUfCASADetails.class);
        criteriaQuery.add(Restrictions.eq("id_Batch", batchId));
        criteriaQuery.addOrder(Order.asc("record_id"));
        return criteriaQuery.list();
    }
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((CiChUfCASADetails)model);
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
     * @param workType
     * @throws SQLException
     */
    public void runMonitor(int workType) throws SQLException {
        try {
            Connection cnctn = ((SessionImpl) super.getSession()).connection();
            StringBuilder qryStr = new StringBuilder();
 
            int workRes = 0;
            
            logger.debug("DOB BRANCH DAO :" + DOB_Branch);
            logger.debug("cod XF DAO :" + codXF);
            logger.debug("fileType DAO :" + fileType);
            logger.debug("patern DAO :" + pattern);
            logger.debug("file Name DAO :" + fileName);
            logger.debug("Maker ID DAO :" + makerID);
            logger.debug("DAYS DAO :" + days);
            logger.debug("ID Scheduler :" + closing);
            
            String query = null;
            query = queryMonitor;
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, ID_Batch);
            stmt.setString(3, codXF);
            stmt.setString(4, fileType);
            stmt.setString(5, fileName);
            stmt.setInt(6, closing);
            stmt.setString(7, makerID);
            stmt.setInt(8, Integer.parseInt(DOB_Branch));
            stmt.setInt(9, Integer.parseInt(days));
            
            workResult = stmt.executeUpdate();
            logger.debug(stmt.getInt(1));
            stmt.close();
            
            String queryL = null;
            workResult4 = workResult;
            logger.debug("RESULT :" + workResult4);
            //workType = 1;
        } catch(SQLException e) {
            e.printStackTrace();
            logger.debug("EXCEPTION CLOSING SQL : " + e);
            //workType = 2;
        } catch(Exception e) {
            e.printStackTrace();
            logger.debug("EXCEPTION CLOSING : " + e);
            //workType = 2;
        }
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
                    query = queryRun;
                    break;
                case 2:
                    query = queryUpdate;
                    break;
            }
            CallableStatement stmt = cnctn.prepareCall(query);
            switch (workType) {
                case 1:
                    stmt.registerOutParameter(1, java.sql.Types.INTEGER);
                    stmt.setString(2, parameter);
                    stmt.setString(3, branch);
                    break;
                case 2:
                    stmt.registerOutParameter(1, java.sql.Types.INTEGER);
                    stmt.setString(2, parameter);
                    break;
            }
            stmt.executeUpdate();
            workResult1 = stmt.getInt(1);
            stmt.close();
            
        } catch(SQLException e) {
            e.printStackTrace();
            logger.debug("EXCEPTION SQL : " + e);
        } catch(Exception e) {
            e.printStackTrace();
            logger.debug("EXCEPTION : " + e);
        }
    }
    /**
     * 
     * @param param
     * @param branch
     * @return
     */
    public int runValidate(String param,String branch) {
        Session session = getSession();
        workType = 1;
        this.parameter = param;
        this.branch = branch;
        session.doWork(this);
        return workResult1;
    }
    /**
     * 
     * @param param
     * @return
     */
    public int runPackage(String param) {
        Session session = getSession();
        workType = 2;
        this.parameter = param;
        
        session.doWork(this);
        return workResult1;
    }
    /**
     * 
     * @param ID_Batch
     * @param DOB_Branch
     * @param codXf
     * @param fileType
     * @param pattern
     * @param fileName
     * @param closing
     * @param makerID
     * @param days
     * @return
     * @throws SQLException
     */
    public int runMonitoring(String ID_Batch,
                             String DOB_Branch,
                             String codXf,
                             String fileType,
                             String pattern,
                             String fileName,
                             int closing,
                             String makerID,
                             String days) throws SQLException{
        Session session = getSession();
        this.ID_Batch = ID_Batch;
        this.DOB_Branch = DOB_Branch;
        this.codXF = codXf;
        this.fileName = fileName;
        this.pattern = pattern;
        this.fileType = fileType;
        this.makerID = makerID;
        this.days = days;
        this.closing = closing;
        
        logger.debug("DOB BRANCH BEFORE DAO:" + DOB_Branch);
        logger.debug("cod XF BEFORE DAO:" + codXF);
        logger.debug("fileType BEFORE DAO:" + fileType);
        logger.debug("patern BEFORE DAO:" + pattern);
        logger.debug("file Name BEFORE DAO:" + fileName);
        logger.debug("makerID BEFORE DAO:" + makerID);
        logger.debug("days BEFORE DAO:" + days);
        
        workType = 4;
        //workType = 1;
        runMonitor(workType);
        return workResult4;
    }
        private static final String queryUpdate = "{? = call PK_DDF_CICH.updateCH(?)}";
        private static final String queryMonitor = "{? = call PK_DDF_CICH.monitorCASA(?,?,?,?,?,?,?,?)}";
        private static final String queryRun = "{? = call PK_DDF_CICH.beginsCH(?,?)}";
}
