/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;


/**
 * 
 * @author NCBS
 */
public class GefuUpdateWorkerDAO extends BaseDao implements Work {
	private static final Logger LOGGER = Logger.getLogger(GefuUpdateWorkerDAO.class);
	private String query = "";
	private String param = "";
	
	
    /**
     * 
     * @param session
     */
    public GefuUpdateWorkerDAO(Session session) {
		super(session);
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
		CallableStatement call = cnctn.prepareCall(query);
		call.setString(1, param);
		call.executeUpdate();
	}
	
    /**
     * 
     * @param packageName
     * @param functionName
     * @param batchNo
     */
    public void runDBPackage(String packageName, String functionName, String batchNo) {
		Session session = this.getSession();
		param = batchNo;
		
		LOGGER.info("Start Execute Update for Batch : " + batchNo);
		query = "{call " + packageName + "." + functionName + "(?)}";
		LOGGER.info("Query is : " + query);
		
		session.doWork(this);
		LOGGER.info("End Execute Update for Batch : " + batchNo);
	}

    /**
     * 
     * @param packageName
     * @param functionName
     * @param batchNo
     */
    public void updateAdq(String packageName, String functionName, String batchNo) {
		this.runDBPackage(packageName, functionName, batchNo);
	}
}
