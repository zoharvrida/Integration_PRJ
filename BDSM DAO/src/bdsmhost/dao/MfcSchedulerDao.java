/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00013493
 */
public class MfcSchedulerDao extends BaseDao implements Work {
    private final static String queryFCR = "begin ? := scheduler.run() ; end;" ;
    private final static String queryTF = "begin ? := TFscheduler.run() ; end;" ;
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workType = 0;
    
    public MfcSchedulerDao(Session session) {
        super(session);
    }
 
    public void execute(Connection conn) throws SQLException {
        String query = null;
        switch (workType) {
            case 1:
                query = queryFCR;
                break;
            case 2:
                query = queryTF;
                break;
        }
        CallableStatement stmt = conn.prepareCall(query) ;
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.execute();
        int workResult = stmt.getInt(1);
        switch (workType) {
            case 1:
                workResult1 = workResult;
                break;
            case 2:
                workResult2 = workResult;
                break;
        }
    }

    public int runFCRScheduler() {
        
        Session session = getSession();
        workType = 1;
        session.doWork(this);
        
        return workResult1;
    }

    public int runTFScheduler() {
        
        Session session = getSession();
        workType = 2;
        session.doWork(this);
        
        return workResult2;
    }

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
}
