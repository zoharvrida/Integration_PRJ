package bdsmhost.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.AmortizeAccount;
import bdsm.model.BaseModel;


@SuppressWarnings("unchecked")
public class AmortizeAccountDAO extends BaseDao {

	public AmortizeAccountDAO(Session session) {
		super(session);
	}
	
	
	public AmortizeAccount get(Integer pk){
		return (AmortizeAccount) this.getSession().get(AmortizeAccount.class, pk);
	}
	
	public List<AmortizeAccount> getByProgramDetailIdAndStatus(Integer programDetailId, Integer status) {
		Query q = this.getSession().getNamedQuery("AmortizeAccount#getByProgramDetailIdAndStatus");
		q.setInteger("programDetailId", programDetailId);
		q.setInteger("status", status);
		
		return q.list();
	}
	
	public List<AmortizeAccount> getByStatusAndMethod(String accountNo, Integer status, Integer method) {
		return this.getByStatusAndMethod(accountNo, status, method, null, null, null, null, null);
	}
	
	public List<AmortizeAccount> getByStatusAndMethod(String accountNo, Integer status, Integer method, Date beforeDate, 
				Integer firstRowNumber, Integer rowNumbers, List<Integer> outProgramDetailTerm,
				List<Map<String, Object>> outFCRData) {
		List<String> accountNoList = null;
		Query q;
		
		if ((accountNo != null) && (accountNo.trim().length() > 0)) {
			StringTokenizer st = new StringTokenizer(accountNo, ";");
			accountNoList = new ArrayList<String>();
			
			while (st.hasMoreTokens())
				accountNoList.add(st.nextToken().trim());
			
			q = this.getSession().getNamedQuery("AmortizeAccount#getByAccountNoStatusAndMethod");
			q.setParameterList("accountNoList", accountNoList);
		}
		else {
			q = this.getSession().getNamedQuery("AmortizeAccount#getByStatusAndMethod");
		}
		
		q.setInteger("status", status);
		q.setInteger("method", method);
		q.setDate("beforeDate", DateUtils.addDays(beforeDate, -1));
		
		if (firstRowNumber != null)
			q.setFirstResult(firstRowNumber - 1);
		
		if (rowNumbers != null)
			q.setMaxResults(rowNumbers);
		
		List<Object[]> resultList = q.list();
		List<AmortizeAccount> amrtAccounts = new ArrayList<AmortizeAccount>();
		
		if (outProgramDetailTerm != null) outProgramDetailTerm.clear();
		if (outFCRData != null) outFCRData.clear();
		
		
		for (Object[] objArr : resultList) {
			amrtAccounts.add((AmortizeAccount) objArr[0]);
			Map<String, Object> map = new java.util.HashMap<String, Object>();
			
			if (outProgramDetailTerm != null) {
				outProgramDetailTerm.add((Integer) objArr[1]);
			}
			
			if (outFCRData != null) {
				map.put("accountStatus", (Integer) objArr[2]);
				map.put("accountBranchCode", (Integer) objArr[3]);
				map.put("accountTypeProduct", (String) objArr[4]);
				outFCRData.add(map);
			}
		}
		
		return amrtAccounts; 
	}

	public List<AmortizeAccount> checkExistingAccount(String account){
		Criteria crt = this.getSession().createCriteria(AmortizeAccount.class);
		crt.setProjection(Projections.distinct(Projections.property("accountNo")));
		crt.add(Restrictions.like("accountNo", account.concat("%")));
		crt.add(Restrictions.eq("status", AmortizeAccount.STATUS_OPENED));
		
		return crt.list();
	}
	
	public int updateStatusFinish(Integer oldStatus, Integer newStatus) {
		Query query = this.getSession().getNamedQuery("AmortizeAccount#updateStatusFinish");
		query.setInteger("oldStatus", oldStatus);
		query.setInteger("newStatus", newStatus);
		
		return (query.executeUpdate());
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((AmortizeAccount) model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update((AmortizeAccount) model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete((AmortizeAccount) model);
		return true;
	}

}
