package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.EKTPReaderLog;
import bdsm.model.EKTPReaderUser;


/**
 * @author v00019372
 */
public class EKTPReaderUserDAO extends BaseDao {

	public EKTPReaderUserDAO(Session session) {
		super(session);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listTemporaryByBatchNo(String batchNo, String status) {
		StringBuffer sb = new StringBuffer()
			.append("SELECT mu.iduser, eu.role, eu.password, eu.operation, eu.idcreatedby, eu.idcreatedspv ")
			.append("  FROM tmp_ektp_reader_user eu INNER JOIN masteruser mu ON UPPER(eu.id_user) = mu.iduser ")
			.append(" WHERE eu.batch_no = :batchNo AND eu.status = :status ")
			.append(" ORDER BY mu.cdbranch");
		
		Query query = this.getSession().createSQLQuery(sb.toString())
			.setString("batchNo", batchNo)
			.setString("status", status);
		
		return query.list();
	}
	
	public boolean updateTemporary(String batchNo, String idUser, String role, String status, String reason) {
		StringBuffer sb = new StringBuffer()
			.append("UPDATE tmp_ektp_reader_user ")
			.append("   SET   status = :status ")
			.append("       , reason = :reason ")
			.append(" WHERE batch_no       = :batchNo ")
			.append("   AND UPPER(id_user) = :idUser ")
			.append("   AND role           = :role ");
		
		Query query = this.getSession().createSQLQuery(sb.toString())
			.setString("batchNo", batchNo)
			.setString("idUser", idUser)
			.setString("role", role)
			.setString("status", status)
			.setString("reason", reason);
		
		return (query.executeUpdate() > 0);
	}
	
	
	public EKTPReaderUser get(EKTPReaderUser pk) {
		return (EKTPReaderUser) this.getSession().get(EKTPReaderUser.class, pk);
	}
	
	public String checkValidOperatorOnDevice(String idUser, String deviceIP) {
		return checkValidUserOnDevice(idUser, deviceIP, "OPR");
	}
	
	public String checkValidSupervisorOnDevice(String idUser, String deviceIP) {
		return checkValidUserOnDevice(idUser, deviceIP, "SPV");
	}
	
	private String checkValidUserOnDevice(String idUser, String deviceIP, String role) {
		Query query = this.getSession().getNamedQuery("EKTPReaderUser#checkValidUserOnDevice")
			.setString("idUser", idUser)
			.setString("deviceIP", deviceIP)
			.setString("role", role);
			;
		Object result = query.uniqueResult();
		
		return ((result != null)? result.toString(): null);
	}
	
	public boolean saveLog(EKTPReaderLog log) {
		this.getSession().save(log);
		
		return true;
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		model.setIdUpdatedBy(null); model.setDtmUpdated(null);
		model.setIdUpdatedSpv(null); model.setDtmUpdatedSpv(null);
		
		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}

}
