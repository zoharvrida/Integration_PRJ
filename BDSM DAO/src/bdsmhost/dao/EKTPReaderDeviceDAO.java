package bdsmhost.dao;


import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.EKTPReaderDevice;


/**
 * @author v00019372
 */
public class EKTPReaderDeviceDAO extends BaseDao {

	public EKTPReaderDeviceDAO(Session session) {
		super(session);
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> listTemporaryByBatchNo(String batchNo, String status) {
		StringBuffer sb = new StringBuffer()
			.append("SELECT cod_cc_brn, ip, name, sa_username, sa_password, operation, idcreatedby, idcreatedspv ")
			.append("  FROM tmp_ektp_reader_device ")
			.append(" WHERE batch_no = :batchNo AND status = :status ")
			.append(" ORDER BY cod_cc_brn");
		
		Query query = this.getSession().createSQLQuery(sb.toString())
			.setString("batchNo", batchNo)
			.setString("status", status);
		
		return query.list();
	}


	public boolean updateTemporary(String batchNo, Integer branchCode, String name, String ip, String status, String reason) {
		StringBuffer sb = new StringBuffer()
			.append("UPDATE tmp_ektp_reader_device ")
			.append("   SET   status = :status ")
			.append("       , reason = :reason ")
			.append(" WHERE batch_no   = :batchNo ")
			.append("   AND cod_cc_brn = :branchCode ")
			.append("   AND name       = DECODE(:name, NULL, NULL, 'N/A', name, :name) ")
			.append("   AND ip         = DECODE(:ip, NULL, NULL, 'N/A', ip, :ip) ");
		
		if (name != null)
			name = (name.trim().length() == 0)? null: name.trim();
		
		if (ip != null)
			ip = (ip.trim().length() == 0)? null: ip.trim();
		
		Query query = this.getSession().createSQLQuery(sb.toString())
			.setString("batchNo", batchNo)
			.setInteger("branchCode", branchCode)
			.setString("name", name)
			.setString("ip", ip)
			.setString("status", status)
			.setString("reason", reason);
		
		return (query.executeUpdate() > 0);
	}



	public EKTPReaderDevice get(EKTPReaderDevice pk) {
		return ((EKTPReaderDevice) this.getSession().get(EKTPReaderDevice.class, pk));
	}

	public EKTPReaderDevice getByCCandName(Integer codeCCBranch, String name) {
		EKTPReaderDevice id = new EKTPReaderDevice();
		id.setCodeCCBranch(codeCCBranch);
		id.setName(name);
		
		return this.get(id);
	}
	
	public EKTPReaderDevice getByCCandIP(Integer codeCCBranch, String IP) {
		Criteria c = this.getSession().createCriteria(EKTPReaderDevice.class);
		c.add(Restrictions.eq("codeCCBranch", codeCCBranch));
		c.add(Restrictions.eq("IP", IP));
		
		return ((EKTPReaderDevice) c.uniqueResult());
	}


	@SuppressWarnings("unchecked")
	public List<EKTPReaderDevice> listByCodeCCBranch(Integer codeCCBranch) {
		Query query = this.getSession().getNamedQuery("EKTPReaderDevice#listByCodeCCBranch");
		query.setInteger("codeCCBranch", codeCCBranch);
		
		return query.list();
	}


	@SuppressWarnings("unchecked")
	public List<Map<String, String>> listDevicesByUserId(String idUser) {
		Query query = this.getSession().getNamedQuery("EKTPReaderDevice#listDevicesByUserId");
		query.setString("idUser", idUser);
		
		return query.list();
	}



	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

}
