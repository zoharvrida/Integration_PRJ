package bdsm.scheduler.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

import bdsm.scheduler.model.TmpTxnUploadHeader;


public class TmpTxnUploadHeaderDAO extends bdsmhost.dao.BaseDao implements Work {
	private static final String QUERY_UPLOAD  = "{call PK_GEFU.processTxnUpload(?,?,?,?,?,?)}";
	private static final String QUERY_RESPOND = "{call PK_GEFU.responsTxnUpload(?)}";
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TmpTxnUploadHeaderDAO.class);
	
	protected String idBatch;
	protected String xfSystem;
	protected String fileType;
	protected String prmBranch;
	protected String prmFile;
	protected String profileName;
	private String mode;
	private int workResult;
	
	public TmpTxnUploadHeaderDAO(Session session) {
		super(session);
	}
	
	public TmpTxnUploadHeader getByFileId(String fileId) {
		Criteria crt = this.getSession().createCriteria(TmpTxnUploadHeader.class);
		crt.add(Restrictions.eq("compositeId.fileId", fileId));

		return (TmpTxnUploadHeader) crt.uniqueResult();
	}
	
	public int runTxn(List<? extends Object> parameters) {
		Session session = this.getSession();
		session.flush();
		
		this.idBatch = parameters.get(0).toString();
		this.fileType = parameters.get(1).toString();
		this.prmBranch = parameters.get(2).toString();
		this.xfSystem = parameters.get(3).toString();
		this.prmFile = parameters.get(4).toString();
		this.profileName = parameters.get(5).toString();
		this.mode = "UPLOAD";
		session.doWork(this);
		
		return this.workResult;
	}

	public int respTxn(String Idbatch) {
		Session session = this.getSession();
		this.idBatch = Idbatch;
		this.mode = "RESPOND";
		session.doWork(this);
		
		return this.workResult;
	}
	
	public void execute(Connection cnctn) throws SQLException {
		try {
			String query = null;

			if (this.mode.equalsIgnoreCase("UPLOAD"))
				query = QUERY_UPLOAD;
			else
				query = QUERY_RESPOND;
			
			LOGGER.info("IDBATCH: " + this.idBatch);
			CallableStatement stmt = cnctn.prepareCall(query);
			if (this.mode.equalsIgnoreCase("UPLOAD")) {
				stmt.setString(1, this.idBatch);
				stmt.setString(2, this.xfSystem);
				stmt.setString(3, this.fileType);
				stmt.setString(4, this.prmBranch);
				stmt.setString(5, this.prmFile);
				stmt.setString(6, this.profileName);
			}
			else {
				stmt.setString(1, this.idBatch);
			}
			
			int workRes = stmt.executeUpdate();
			stmt.close();
			this.workResult = workRes;
			
			LOGGER.info("IDBATCH after PACKAGE: " + this.idBatch);
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
	}
	

	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		this.getSession().save((TmpTxnUploadHeader) model);
		return true;
	}

	@Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}
	
}
