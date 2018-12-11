/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.util.oracle.DateUtility;


/**
 * 
 * @author v00019237
 */
public class SknNgInwardDebitWorkerDAO extends bdsmhost.dao.BaseDao implements org.hibernate.jdbc.Work {
	private static final Logger LOGGER = Logger.getLogger(SknNgInwardDebitWorkerDAO.class);
	private int workType;
	private String fileId;
	private String batchNo;
	private String branchNo;
	private String idUser;
	private Integer hPlus;
	private String dateTime;
	private String command;
	
	
    /**
     * 
     * @param session
     */
    public SknNgInwardDebitWorkerDAO(Session session) {
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
			CallableStatement stmt = cnctn.prepareCall(this.command);
			stmt.setString(1, this.fileId);
			stmt.setString(2, this.batchNo);
			
			if (this.workType == 1) { // preProcessIncoming
				stmt.setInt(3, this.hPlus);
			}
			else if (this.workType == 2) { // processIncoming
				stmt.setString(3, this.branchNo);
				stmt.setString(4, this.idUser);
				stmt.setString(5, this.dateTime);
			}
			
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
     * @param fileId
     * @param batchNo
     * @param hPlus
     */
    public void preProcessIncoming(String fileId, String batchNo, Integer hPlus) {
		Session session = this.getSession();
		this.workType = 1;
		this.fileId = fileId;
		this.batchNo = batchNo;
		this.hPlus = hPlus;
		this.command = "{ CALL PKG_SKNNG.preProcessIncoming(?, ?, ?) }";
		
		session.doWork(this);
	}

    /**
     * 
     * @param fileId
     * @param batchNo
     * @param branchNo
     * @param idUser
     * @param dateTime
     */
    public void processIncoming(String fileId, String batchNo, String branchNo, String idUser, String dateTime) {
		Session session = this.getSession();
		this.workType = 2;
		this.fileId = fileId;
		this.batchNo = batchNo;
		this.branchNo = branchNo;
		this.idUser = idUser;
		this.dateTime = dateTime;
		this.command = "{ CALL PKG_SKNNG.processIncoming(?, ?, ?, ?, ?) }";
		
		session.doWork(this);
	}
	
    /**
     * 
     * @param branchCode
     * @param workingDate
     * @return
     * @throws java.text.ParseException
     */
    public Date getClearingBranchNextWorkingDate(Integer branchCode, Date workingDate) throws java.text.ParseException {
		Query q = this.getSession().createSQLQuery("SELECT GET_CLRG_BRN_NEXT_WRK_DATE(:branchCode, :workingDate, :type) FROM DUAL");
		q.setInteger("branchCode", branchCode);
		q.setString("workingDate", DateUtility.DATE_FORMAT_DDMMYYYY.format(workingDate));
		q.setInteger("type", 1);
		
		String result = (String) q.uniqueResult();
		
		if (result != null) {
			Date date = null;
			synchronized(DateUtility.DATE_FORMAT_DDMMYYYY) {
				date = DateUtility.DATE_FORMAT_DDMMYYYY.parse(result);
			}
			return (date);
		}
		else
			return null;
	}
	
    /**
     * 
     * @param codeBI
     * @param codeSector
     * @return
     */
    public String getRoutingId(String codeBI, String codeSector) {
		Query q = this.getSession().createSQLQuery("SELECT PKG_SKNNG.getRoutingID(:codeBI, :codeSector) FROM DUAL");
		q.setString("codeBI", codeBI);
		q.setString("codeSector", codeSector);
		
		return ((String) q.uniqueResult());
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
