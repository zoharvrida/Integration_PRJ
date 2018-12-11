/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.HostSknngInOutHFPK;
import bdsm.model.HostSknngInOutCreditHeader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jeffri Tambunan
 */
public class SknngInOutGen2CreditHeaderDAO extends BaseDao {

	private Logger logger = Logger.getLogger(SknngInOutGen2CreditHeaderDAO.class);
	private Map batchLIst;
	
	public SknngInOutGen2CreditHeaderDAO(Session session) {
		super(session);
	}

	public HostSknngInOutCreditHeader get(HostSknngInOutHFPK pk) {
		return (HostSknngInOutCreditHeader) getSession().get(HostSknngInOutCreditHeader.class, pk);
	}

	public List<HostSknngInOutCreditHeader> getData(String batchNo, int parendRecordId) {
		return (List<HostSknngInOutCreditHeader>) getSession().createCriteria(HostSknngInOutCreditHeader.class).add(Restrictions.eq("compositeId.batchNo", batchNo)).add(Restrictions.eq("compositeId.parentRecordId", parendRecordId)).list();
	}

	public HostSknngInOutCreditHeader get(String batchNo) {
		return (HostSknngInOutCreditHeader) getSession().get(HostSknngInOutCreditHeader.class, batchNo);
	}

	public List listGrid(String batchNo, String mode) {
		Query q;
		List Mapper = new ArrayList();
		logger.info("BATCHID : " + batchNo);
		logger.info("MODE BATCH : " + mode);
		if ("1".equals(mode)) {
			q = this.getSession().getNamedQuery("TmpSknngInOutCreditHeader#listByBatchNoGrid");
		} else if("2".equals(mode)){
			q = this.getSession().getNamedQuery("TmpSknngInOutCreditHeader#listByBatchApproval");
			q.setString("pXtract", "N");
			q.setString("pFlag", "F");
		} else if("authorize".equals(mode)){
			StringBuilder sb = new StringBuilder();
			sb.append("select distinct(substr(batch_no,length(batch_no)-5,length(batch_no))) as GRPID ");
			sb.append("from tmp_sknng_in_out_credit_header a ");
			sb.append("where a.batch_no like :pBatchNo||'%' ");
			sb.append("and a.flg_reject = :pFlag ");
			sb.append("and a.extract_status = :pXtract ");
			q = getSession().createSQLQuery(sb.toString());
			q.setString("pXtract", "N");
			q.setString("pFlag", "F");
		} else if("inquiry".equals(mode)) {
			StringBuilder sb = new StringBuilder();
			sb.append("select distinct(substr(batch_no,length(batch_no)-5,length(batch_no))) as GRPID ");
			sb.append("from tmp_sknng_in_out_credit_header a ");
			sb.append("where a.batch_no like :pBatchNo||'%' ");
			q = getSession().createSQLQuery(sb.toString());
		} else {
			StringBuilder sb = new StringBuilder();
			q = getSession().createSQLQuery(sb.toString());
		}
		q.setString("pBatchNo", batchNo);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		/*if("3".equals(mode)){
			for(int i= 0;i < q.list().size();i++){
				batchLIst = (Map)q.list().get(i);
				Mapper.add(batchLIst.get("BATCH_NO"));
			}
			return Mapper;
		}*/
		//logger.info("RETURN LIST : " + q.list());
		return q.list();
	}

	public List<HostSknngInOutCreditHeader> SingleApprove(String BatchNo){
		Criteria crt = getSession().createCriteria(HostSknngInOutCreditHeader.class);
		crt.add(Restrictions.eq("compositeId.batchNo", BatchNo));
		crt.add(Restrictions.eq("extractStatus", "N"));
		crt.add(Restrictions.eq("flgReject", "F"));
		List single = crt.list();
		return single;
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
