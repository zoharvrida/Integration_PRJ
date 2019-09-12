/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.sql.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00022309
 */
public class ProTimePutLogActionDao extends BaseDao implements Work{

    /**
     * @return the queryProcTime
     */
    public static String getQueryProcTime() {
        return queryProcTime;
    }

    /**
     * @param aQueryProcTime the queryProcTime to set
     */
    public static void setQueryProcTime(String aQueryProcTime) {
        queryProcTime = aQueryProcTime;
    }
    
    private String sessionid;
    private String userid;
    private String cdmenu;
    private String stepname;
    private String errmsg;
    private Logger logger = Logger.getLogger(ProTimePutLogActionDao.class);
    private int flag;
    private String result;
    
    public ProTimePutLogActionDao(Session session){
        super(session);
    }
    
     public String runPROCTIME(String sessionid, String userid, String cdmenu, String stepname, String errmsg) {
        Session session = getSession();
        this.sessionid = sessionid;
        this.flag = 1;
        this.userid = userid;
        this.cdmenu = cdmenu;
        this.stepname = stepname;
        this.errmsg = errmsg;
        session.doWork(this);
        return result;
	}
    
    public void execute(Connection cnctn) throws SQLException {
        Statement labelSt = null;
        ResultSet rs = null;
   
        try {
            if (this.getFlag() == 1) {
                String query = getQueryProcTime();
                CallableStatement stmt = cnctn.prepareCall(query);
                stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                stmt.setString(2, getSessionid());
                stmt.setString(3, userid);
                stmt.setString(4, cdmenu);
                stmt.setString(5, stepname);
                stmt.setString(6, errmsg);	
                stmt.executeUpdate();
                setResult(stmt.getString(1));
                stmt.close();
            } else {
	
            }

        } catch (SQLException e) {
            getLogger().info("SQL EXCEPTION :" + e, e);
        } catch (Exception e) {
            getLogger().info("EXCEPTION :" + e, e);
        }
    }
    private static String queryProcTime = "{? = call PROC_TIMER_PUT(?,?,?,?,?)}";

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @return the sessionid
     */
    public String getSessionid() {
        return sessionid;
    }

    /**
     * @param sessionid the sessionid to set
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the cdmenu
     */
    public String getCdmenu() {
        return cdmenu;
    }

    /**
     * @param cdmenu the cdmenu to set
     */
    public void setCdmenu(String cdmenu) {
        this.cdmenu = cdmenu;
    }

    /**
     * @return the stepname
     */
    public String getStepname() {
        return stepname;
    }

    /**
     * @param stepname the stepname to set
     */
    public void setStepname(String stepname) {
        this.stepname = stepname;
    }

    /**
     * @return the errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * @param errmsg the errmsg to set
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * @return the flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    
  
}
