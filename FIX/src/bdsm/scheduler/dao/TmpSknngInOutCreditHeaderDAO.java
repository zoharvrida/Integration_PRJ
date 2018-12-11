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


    /**
     * 
     * @param session
     */
    public TmpSknngInOutCreditHeaderDAO(Session session) {
		super(session);
	}


    /**
     * 
     * @param pk
     * @return
     */
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

    /**
     * 
     * @param batchNo
     * @param parent
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TmpSknngInOutCreditHeader> getFullData(String batchNo, List parent) {
		return (List<TmpSknngInOutCreditHeader>) 
			getSession().createCriteria(TmpSknngInOutCreditHeader.class)
				.add(Restrictions.eq("compositeId.batchNo", batchNo))
				.add(Restrictions.in("compositeId.parentRecordId", parent))
				.list();
	}

    /**
     * 
     * @param batchNo
     * @return
     */
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

    /**
     * 
     * @param batchNo
     * @return
     */
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

    /**
     * 
     * @param tanggalBatch
     * @param batchId
     * @return
     */
    @SuppressWarnings("unchecked")
	public TmpSknngInOutCreditHeader getByTanggalBatchAndBatchId(String tanggalBatch, String batchId) {
		Query query = this.getSession().getNamedQuery("TmpSknngInOutCreditHeader#getByTanggalBatchAndBatchId");
		query.setString("tanggalBatch", tanggalBatch);
		query.setString("batchId", batchId);
		
		List<TmpSknngInOutCreditHeader> list = query.list();
		if (list.size() > 0)
			return list.get(0);
		
		return null;
	} 


    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save(model);
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
}
