package bdsmhost.dao;

import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgIncomingCreditDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

public class SknNgInOutGen1CreditDetailDAO extends BaseDao{
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SknNgInOutGen1CreditDetailDAO.class);
	public SknNgInOutGen1CreditDetailDAO(Session session) {
		super(session);
		// TODO Auto-generated constructor stub
	}
	public List<SknNgIncomingCreditDetail> listRecord(String batchNo, Integer parentRecordNo) {
		Query q = this.getSession().createQuery("FROM TmpSknngInOutCreditDetail WHERE compositeId.batchNo = :pBatchNo AND parentRecordId = :pParentRecordNo ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return (List<SknNgIncomingCreditDetail>) q.list();
	}
	public List getAllParent(String batchNo){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT parentrecid ");
            sb.append("from TMP_SKNNG_GEN1_CREDIT_DETAIL ");
            sb.append("where fileid = :batchNo");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("batchNo", batchNo);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	@Override
	protected boolean doDelete(BaseModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		getSession().save((SknNgIncomingCreditDetail) model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		getSession().update(model);
		return true;
	}
}
