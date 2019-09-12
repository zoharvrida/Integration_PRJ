/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author bdsm
 */
public class NtfsFcyRateReaderWorkerDao extends BaseDao implements Work {

    private static final String query = "{call PK_NTFS_RATE.doProcessRate(?)}";
    private String parameter;

    /**
     * 
     * @param session
     */
    public NtfsFcyRateReaderWorkerDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param batchNo
     */
    public void doProcessRate(String batchNo) {
        this.parameter = batchNo;
        Session ses = getSession();
        ses.doWork(this);
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
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
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
        CallableStatement stmt = cnctn.prepareCall(query);
        stmt.setString(1, parameter);
        stmt.executeUpdate();
    }
}
