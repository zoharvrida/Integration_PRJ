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
import bdsm.scheduler.model.TmpKabAcct;
import bdsmhost.dao.BaseDao;


/**
 * 
 * @author v00019237
 */
public class TmpKabAcctDAO extends BaseDao implements Work {
	private static Logger LOGGER = Logger.getLogger(TmpKabAcctDAO.class);
	private static final String QUERY_UPLOAD = "{call PKG_KAB.validateAcct(?)}";
	private static final String QUERY_PROCESS = "{call PKG_KAB.processAcct(?,?)}";
	private static final String QUERY_REJECT = "{call PKG_KAB.rejectAcct(?,?)}";
	private String parameter = null;
	private String idSpv = null;
	private int workResult1 = 0;
	private int workResult2 = 0;
	private int workResult3 = 0;
	private int workType = 0;

	
    /**
     * 
     * @param session
     */
    public TmpKabAcctDAO(Session session) {
		super(session);
	}
	
	
    /**
     * 
     * @param pk
     * @return
     */
    public TmpKabAcct get(java.io.Serializable pk) {
		return (TmpKabAcct) this.getSession().get(TmpKabAcct.class, pk);
	}
	
    /**
     * 
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<TmpKabAcct> getByBatchNo(String param) {
		Query query = this.getSession().createQuery("FROM TmpKabAcct WHERE compositeId.batchNo = :prmBatchNo");
		query.setString("prmBatchNo", param);
		
		return query.list();
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		TmpKabAcct kabAcct = (TmpKabAcct) model;
		
		if (this.get(kabAcct.getCompositeId()) == null) {
			model.setIdCreatedSpv(null); model.setDtmCreatedSpv(null);
			model.setIdUpdatedBy(null); model.setDtmUpdated(null);
			model.setIdUpdatedSpv(null); model.setDtmUpdatedSpv(null);
			
			this.getSession().save(model);
		}
		else
			LOGGER.error("KAB Acct: " + kabAcct.getCompositeId() + " already exists!!!");
			
		return true;
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return true;
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}
	
	
    /**
     * 
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
		try {
			String query = null;
			
			switch (this.workType) {
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
			LOGGER.error(ex, ex);
		}
	}

    /**
     * 
     * @param param
     * @return
     */
    public int runValidate(String param) {
		Session session = this.getSession();
		this.workType = 1;
		this.parameter = param;
		session.doWork(this);
		
		return this.workResult1;
	}

    /**
     * 
     * @param param
     * @param spv
     * @return
     */
    public int runProcess(String param, String spv) {
		Session session = this.getSession();
		this.workType = 2;
		this.parameter = param;
		this.idSpv = spv;
		session.doWork(this);
		
		return this.workResult2;
	}

    /**
     * 
     * @param param
     * @param spv
     * @return
     */
    public int runReject(String param, String spv) {
		Session session = this.getSession();
		this.workType = 3;
		this.parameter = param;
		this.idSpv = spv;
		session.doWork(this);
		
		return this.workResult3;
	}
}
