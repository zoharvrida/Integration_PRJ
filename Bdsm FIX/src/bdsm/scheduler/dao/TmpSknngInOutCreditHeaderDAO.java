package bdsm.scheduler.dao;


import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpSknngInOutCreditHeader;
import bdsm.scheduler.model.TmpSknngInoutHFPK;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


/**
 * @author Jeffri Tambunan
 */
public class TmpSknngInOutCreditHeaderDAO extends BaseDao {
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TmpSknngInOutCreditHeaderDAO.class);


	public TmpSknngInOutCreditHeaderDAO(Session session) {
		super(session);
	}


	public TmpSknngInOutCreditHeader get(TmpSknngInoutHFPK pk) {
		return (TmpSknngInOutCreditHeader) getSession().get(TmpSknngInOutCreditHeader.class, pk);
	}

	
	/*
	public List<TmpSknngInOutCreditHeader> getData(String batchNo, Integer parendRecordId) {
		logger.info("PARAMETER : " + batchNo + " " + parendRecordId);
		return (List<TmpSknngInOutCreditHeader>) getSession().createCriteria(TmpSknngInOutCreditHeader.class)
			.add(Restrictions.eq("compositeId.batchNo", batchNo))
			.add(Restrictions.eq("compositeId.parentRecordId", parendRecordId))
			.list();
	}
	*/

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TmpSknngInOutCreditHeader> getFullData(String batchNo, List parent) {
		return (List<TmpSknngInOutCreditHeader>) 
			getSession().createCriteria(TmpSknngInOutCreditHeader.class)
				.add(Restrictions.eq("compositeId.batchNo", batchNo))
				.add(Restrictions.in(" compositeId.parentRecordId", parent))
				.list();
	}

	@SuppressWarnings("rawtypes")
	public List getQueryFullData(String batchNo) {
		StringBuilder q = new StringBuilder();
		q.append("SELECT * FROM TMP_SKNNG_IN_OUT_CREDIT_HEADER A "
				+ "WHERE A.BATCH_NO = :pBatchNo AND EXTRACT_STATUS = 'N' ");
		Query qlist = this.getSession()
				.createSQLQuery(q.toString())
				.addEntity(TmpSknngInOutCreditHeader.class)
				.setParameter("pBatchNo", batchNo);
		
		return qlist.list();
	}

	@SuppressWarnings("unchecked")
	public List<TmpSknngInOutCreditHeader> list(String batchNo) {
		Query q = this.getSession().createQuery(
					"FROM TmpSknngInOutCreditHeader "
							+ "WHERE compositeId.batchNo = :pBatchNo "
							+ "AND extractStatus='N' "
							+ "AND flgReject='F' ORDER BY compositeId.parentRecordId");
		q.setString("pBatchNo", batchNo);

		List<TmpSknngInOutCreditHeader> finalList = q.list();
		return finalList;
	}

	@SuppressWarnings("unchecked")
	public List<TmpSknngInOutCreditHeader> listBy_Tanggal_IdPeserta_Kota_BatchId(String tanggalBatch, String identitasPesertaPengirim, String sandiKotaPengirim, String batchId) {
		Query query = this.getSession().getNamedQuery("TmpSknngInOutCreditHeader#listBy_Tanggal_IdPeserta_Kota_BatchId")
				.setString("tanggalBatch", tanggalBatch)
				.setString("identitasPesertaPengirim", identitasPesertaPengirim)
				.setString("sandiKotaPengirim", sandiKotaPengirim)
				.setString("batchId", batchId);
		
		return query.list();
	} 

	public List<TmpSknngInOutCreditHeader> getQueryHeader(String batchNo) {
		StringBuilder q = new StringBuilder();
        q.append("SELECT B.* FROM TMP_SKNNG_IN_OUT_CREDIT_DETAIL A, TMP_SKNNG_IN_OUT_CREDIT_HEADER B WHERE B.BATCH_NO = :pBatchNo AND A.BATCH_NO = B.BATCH_NO AND A.PARENT_RECORD_ID = B.PARENT_RECORD_ID ORDER BY TO_NUMBER(b.PARENT_RECORD_ID)");
		Query qlist = this.getSession().createSQLQuery(q.toString()).addEntity(TmpSknngInOutCreditHeader.class)
				.setParameter("pBatchNo", batchNo);
		
		return qlist.list();
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
}
