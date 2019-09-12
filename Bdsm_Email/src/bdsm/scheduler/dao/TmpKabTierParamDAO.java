/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpKabTierParam;
import bdsmhost.dao.BaseDao;


/**
 * 
 * @author v00019237
 */
public class TmpKabTierParamDAO extends BaseDao implements Work {
	private static Logger logger = Logger.getLogger(TmpKabTierParamDAO.class);
	private static final String QUERY_UPLOAD = "{call PKG_KAB.validateParam(?)}";
	private static final String QUERY_PROCESS = "{call PKG_KAB.processParam(?,?)}";
	private static final String QUERY_REJECT = "{call PKG_KAB.rejectParam(?,?)}";
	private String parameter = null;
	private String idSpv = null;
	private int workResult1 = 0;
	private int workResult2 = 0;
	private int workResult3 = 0;
	private int workType = 0;
	

	public TmpKabTierParamDAO(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	public List<TmpKabTierParam> get(String param) {
		Query query = this.getSession().createQuery("from TmpKabTierParam where compositeId.batchNo = :prm_batch");
		query.setString("prm_batch", param);
		
		return query.list();
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

	public void execute(Connection cnctn) throws SQLException {
		try {
			String query = null;
			
			switch (workType) {
				case 1:
					query = QUERY_UPLOAD;
					break;
				case 2:
					query = QUERY_PROCESS;
					break;
				case 3:
					query = QUERY_REJECT;
			}
			
			CallableStatement stmt = cnctn.prepareCall(query);
			logger.debug("TmpKabTierParamDao --> execute : query : " + query);
			
			stmt.setString(1, this.parameter);
			if (this.workType != 1)
				stmt.setString(2, this.idSpv);
			
			int workRes = stmt.executeUpdate();
			stmt.close();
			
			switch (this.workType) {
				case 1:
					this.workResult1 = workRes;
					break;
				case 2:
					this.workResult2 = workRes;
					break;
				case 3:
					this.workResult3 = workRes;
			}
		}
		catch (SQLException ex) {
			logger.error(ex, ex);
		}
	}

	public int runValidate(String param) {
		Session session = this.getSession();
		this.workType = 1;
		this.parameter = param;
		session.doWork(this);
		
		return this.workResult1;
	}

	public int runProcess(String param, String spv) {
		Session session = this.getSession();
		this.workType = 2;
		this.parameter = param;
		this.idSpv = spv;
		logger.debug("TmpKabTierParamDao --> runProcess : parameter : " + this.parameter);
		logger.debug("TmpKabTierParamDao --> runProcess : idSpv : " + this.idSpv);
		session.doWork(this);
		
		return this.workResult2;
	}

	public int runReject(String param, String spv) {
		Session session = this.getSession();
		this.workType = 3;
		this.parameter = param;
		this.idSpv = spv;
		session.doWork(this);
		
		return this.workResult3;
	}
}
