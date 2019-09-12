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
 * @author v00018192
 */
public class AmtHldHeaderDAO extends BaseDao implements Work {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AmtHldHeaderDAO.class);
    private int workResult1 = 1;
    private static final String query = "{? = call BDI_CARD_HOLD_FUND(?,?,?)}";
    private String FileName = null;
    private String FileId = null;
    private int Tot_rec = 0;

    public AmtHldHeaderDAO(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        this.getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        this.getSession().delete(model);
        return true;
    }

    public int runAmtHold(String FileName, String Fileid,int tot_rec) {
        this.FileName = FileName;
        this.FileId = Fileid;
        this.Tot_rec = tot_rec;
        Session session = getSession();
        session.doWork(this);
        return workResult1;
    }

    public void execute(Connection cnctn) throws SQLException {
        try {

            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, FileId);
            stmt.setString(3, FileName);
            stmt.setInt(4, Tot_rec);
            stmt.executeUpdate();
            workResult1 = stmt.getInt(1);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("EXCEPTION SQL : " + e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("EXCEPTION : " + e);
        }
    }
}
