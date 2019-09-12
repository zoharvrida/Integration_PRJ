/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import bdsm.model.BaseModel;


/**
 * 
 * @author v00019237
 */
public class SknNgOutwardDebitWorkerDAO extends bdsmhost.dao.BaseDao implements org.hibernate.jdbc.Work {
	private static final Logger LOGGER = Logger.getLogger(SknNgOutwardDebitWorkerDAO.class);
	private String user;
	private String batchno;
	private Date runDate;
	private float clgType;
	private String branch;
	private String piBatch;
	
	
    /**
     * 
     * @param session
     */
    public SknNgOutwardDebitWorkerDAO(Session session) {
		super(session);
	}
	
	
    /**
     * 
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
		LOGGER.debug("Start Execute ");
		try {
			CallableStatement stmt = cnctn.prepareCall("{ CALL PKG_SKNNG.getoutgoingdebit(?, ?, ?, ?, ?, ?, ?) }");
			stmt.setString(1, user);
			stmt.setString(2, batchno);
			stmt.setDate(3, runDate);
			stmt.setFloat(4, clgType);
			stmt.setString(5, branch);
			stmt.setString(6, piBatch);
			stmt.setString(7, branch);
			stmt.executeQuery();
		}
		catch (Exception e) {
			LOGGER.error("Error Execute Procedure");
			LOGGER.error(e, e);
		}
		LOGGER.debug("Finish Execute ");
	}
	

    /**
     * 
     * @param batchNo
     * @param user
     * @param runDate
     * @param clgType
     * @param branch
     * @param piBatch
     */
    public void getDownloadData(String batchNo, String user, Date runDate, float clgType, String branch, String piBatch) {
		this.user = user;
		this.batchno = batchNo;
		this.runDate = runDate;
		this.clgType = clgType;
		this.branch = branch;
		this.piBatch = piBatch;
		Session session = getSession();
		session.doWork(this);
	}
	
	
    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

	
}
