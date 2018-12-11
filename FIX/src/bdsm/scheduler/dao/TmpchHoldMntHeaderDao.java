/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpchHoldMntHeader;
import bdsm.scheduler.model.TmpchHoldMntPK;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00019722
 */
public class TmpchHoldMntHeaderDao extends BaseDao implements Work{

	private static final String QUERY_UPLOAD = "{call PKG_GEFU.processHoldUpload(?,?,?,?,?)}";
    private static final String QUERY_RESPOND = "{call PKG_GEFU.responsHoldUpload(?)}";
	private static Logger LOGGER = Logger.getLogger(TmpchHoldMntHeaderDao.class);
    /**
     * 
     */
    protected String idBatch;
    /**
     * 
     */
    protected String xfSystem;
    /**
     * 
     */
    protected String fileType;
    /**
     * 
     */
    protected String prmBranch;
    /**
     * 
     */
    protected String prmFile;
	private int workResult;
    private String mode;

    /**
     * 
     * @param session
     */
    public TmpchHoldMntHeaderDao(Session session) {
		super(session);
	}
    /**
     * 
     * @param pk
     * @return
     */
    public TmpchHoldMntHeader get(TmpchHoldMntPK pk){
        return (TmpchHoldMntHeader) getSession().get(TmpchHoldMntHeader.class, pk);
    }

    /**
     * 
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
		try {
			String query = null;

            if (this.mode.equalsIgnoreCase("UPLOAD")) {
			query = QUERY_UPLOAD;
            } else {
                query = QUERY_RESPOND;
            }

			CallableStatement stmt = cnctn.prepareCall(query);
            if (this.mode.equalsIgnoreCase("UPLOAD")) {
			stmt.setString(1, this.idBatch);
			stmt.setString(2, this.xfSystem);
			stmt.setString(3, this.fileType);
			stmt.setString(4, this.prmBranch);
			stmt.setString(5, this.prmFile);
            } else {
                stmt.setString(1, this.idBatch);
            }

			int workRes = stmt.executeUpdate();
			stmt.close();
			this.workResult = workRes;

		} catch (SQLException ex) {
			LOGGER.error(ex, ex);
		}
	}
	
    /**
     * 
     * @param Parameter
     * @return
     */
    public int runHold(List Parameter) {
		Session session = this.getSession();
        this.idBatch = Parameter.get(0).toString();
        this.fileType = Parameter.get(1).toString();
        this.prmBranch = Parameter.get(2).toString();
        this.xfSystem = Parameter.get(3).toString();
        this.prmFile = Parameter.get(4).toString();
		session.doWork(this);
        return this.workResult;
    }

    /**
     * 
     * @param Idbatch
     * @return
     */
    public int respHold(String Idbatch) {
        Session session = this.getSession();
        this.idBatch = Idbatch;
        this.mode = "RESPOND";
        session.doWork(this);
		return this.workResult;
	}
	
    
    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		getSession().save((TmpchHoldMntHeader) model);
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
}
