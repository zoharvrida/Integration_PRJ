/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.Auditlog;
import bdsm.model.BaseModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jdbc.Work;

/**
 *
 * @author user
 */
public class AuditlogDao {
    private Session session;
    private String nam_Field = null;
    private Timestamp dtm_log = null;
    private String nam_Table = null;
    private String id_User = null;
    private String id_user_SPV = null;
    private String action = null;
    private String activity = null;
    ArrayList<String> valueNew = new ArrayList<String>();
    ArrayList<String> valueOld = new ArrayList<String>();
    ArrayList<String> fieldName = new ArrayList<String>();
    
    private Logger logger = Logger.getLogger(AuditlogDao.class);
    

    private int workResult;
    private int workDetails;
    private int workType = 1;
    
    public AuditlogDao(Session session) {
        this.session = session;
    }
    
    public void insert(String idUser, String idUserSpv, String namTable,
                       String namMenu, String activity, String action) {
        Calendar calendar = Calendar.getInstance();
        Auditlog auditlog = new Auditlog();
        auditlog.setIdUser(idUser);
        auditlog.setIdUserSpv(idUserSpv);
        auditlog.setNamTable(namTable);
        Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
        auditlog.setDtmLog(dt);
        auditlog.setNamMenu(namMenu);
        auditlog.setActivity(activity);
        auditlog.setAction(action);
        session.save(auditlog);
    }
    public void runLog(int workType) throws SQLException {
        try {
            Connection cnctn = ((SessionImpl) session).connection();
            StringBuilder qryStr = new StringBuilder();
 
            int workRes = 0;
            String queryB = null;
            queryB = queryBefore;
            CallableStatement stmtB = cnctn.prepareCall(queryB);
            stmtB.setTimestamp(1, dtm_log);
            stmtB.registerOutParameter(2, OracleTypes.NUMBER);
            int workBefore = stmtB.executeUpdate();
            
            workBefore = stmtB.getInt(2);
            stmtB.close();
            
            //logger.info(workBefore);
            
            String query = null;
            query = queryRun;
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.setTimestamp(1, dtm_log);
            stmt.setString(2, nam_Table);
            stmt.setString(3, id_User);
            stmt.setString(4, id_user_SPV);
            stmt.setString(5, nam_Field);
            stmt.setString(6, action);
            stmt.setString(7, activity);
            stmt.setInt(8, workBefore);
            workResult = stmt.executeUpdate();
            
            //logger.info(workBefore);
            stmt.close();
            String queryL = null;
           
            queryL = queryLoop;
            
            //logger.info("Value New : "+ valueNew.size());
            //logger.info("Value Old : "+ valueOld.size());
            //logger.info("fieldName : "+ fieldName.size());
            
            for(int i =0; i < valueNew.size();i++){
                try {
                    CallableStatement stmt2 = cnctn.prepareCall(queryL);
                    //logger.info("checkVAL :" + fieldName.get(i) + " " + valueOld.get(i) + " " + valueNew.get(i));
                    stmt2.setString(1, fieldName.get(i));
                    stmt2.setString(2, valueOld.get(i));
                    stmt2.setString(3, valueNew.get(i));
                    stmt2.setInt(4, workBefore);
                    workDetails = stmt2.executeUpdate();
                } catch (SQLException sQLException) {
                    logger.info(sQLException);
                } catch (Exception e) {
                    logger.info(e);
            }}
            
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public int runPackage(String namMenu,
                          String namTable,
                          String idUser,
                          String idUserSpv,
                          String action,
                          String activity,
                          ArrayList valueKey,
                          ArrayList valueOld,
                          ArrayList fieldName) throws SQLException {
        //Session sessions = getSession();
        this.fieldName = fieldName;
        this.nam_Field = namMenu;
        Calendar calendar = Calendar.getInstance();
        Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
        this.dtm_log = dt;
        //logger.info("Test Date :" + dt);
        this.nam_Table = namTable;
        this.id_User = idUser;
        this.id_user_SPV = idUserSpv;
        this.action = action;
        this.activity = activity;
        this.valueOld = valueOld;
        this.valueNew = valueKey;
        this.fieldName = fieldName;
        //this.session.doWork(this);
        workType = 1;
        runLog(workType);
        return workType;
    }
    private static final String queryBefore = "{call PK_BDSM_AUDITLOG.audit_log_before(?,?)}";
    private static final String queryRun = "{call PK_BDSM_AUDITLOG.audit_log_update(?,?,?,?,?,?,?,?)}";
    private static final String queryLoop = "{call PK_BDSM_AUDITLOG.audit_details_insert(?,?,?,?)}";
}
