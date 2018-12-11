package bdsmhost.fcr.dao;


import static bdsm.fcr.model.ChAcctMastPK.ACCT_NO_LENGTH;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.ChAcctMastPK;
import bdsm.model.BaseModel;


/**
 * 
 * @author v00019372
 */
public class ChAcctMastDAO extends bdsmhost.dao.BaseDao {
	private static final Logger LOGGER = Logger.getLogger(ChAcctMastDAO.class);
	private static final Integer COD_ENTITY_VPD = 11;

	public ChAcctMastDAO(Session session) {
		super(session);
	}
	
	public ChAcctMast get(ChAcctMastPK id) {
		this.rightPadScaceAccountNo(id);
		return ((ChAcctMast) this.getSession().get(ChAcctMast.class, id));
	}
    
    public ChAcctMast getByCodAcctNo(String codAcctNo) {
        ChAcctMastPK id = new ChAcctMastPK(codAcctNo, "A", 11);
        return this.get(id);
    }
    
    public ChAcctMast getByCodAcctTitle (String codAcctTitle){
        Criteria crt = this.getSession().createCriteria(ChAcctMast.class);
		crt.add(Restrictions.eq("codAcctTitle", codAcctTitle));
        
        return ((ChAcctMast) crt.uniqueResult());
    }

	@SuppressWarnings("unchecked")
	public List<ChAcctMast> list(ChAcctMastPK id) {
		this.rightPadScaceAccountNo(id);
		
		Query query = this.getSession().getNamedQuery("ChAcctMast#getByAccountNo");
		query.setString("accountNo", id.getCodAcctNo());
		query.setString("flagMaintenanceStatus", id.getFlgMntStatus());
		query.setInteger("codEntityVpd", id.getCodEntityVpd());
		
		return ((List<ChAcctMast>) query.list());
	}
	
	public ChAcctMast getActiveAccount(String accountNo) {
		List<ChAcctMast> list = this.list(new ChAcctMastPK(accountNo, "A", COD_ENTITY_VPD));
		
		if (list.size() > 0)
			return list.get(0);
		LOGGER.debug("LIST " + list.size());
		return null;
	}

	@SuppressWarnings("unchecked")
	public String getCod_LOB(String account) {
		Map<String, Object> codBank;
		String LOBCOD = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COD_LOB ");
		sb.append("FROM ba_cust_acct_ao_lob_xref ");
		sb.append("WHERE cod_acct_no = :account");
		sb.append("AND flg_mnt_status = 'A' AND ROWNUM < 2");

		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("account", this.rightPadSpaceAccountNo(account));
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		try {
			codBank = (Map<String, Object>) q.list().get(0);
			LOBCOD = codBank.get("COD_LOB").toString();
		}
		catch (Exception e) {
			LOGGER.info("COD LOB not Found");
		}

		return LOBCOD;
	}

	@SuppressWarnings("unchecked")
	public List<ChAcctMast> listbyAcctStat(ChAcctMastPK pk, List<Integer> acctStat) {
		this.rightPadScaceAccountNo(pk);
		
		Criteria criteriaQuery = getSession().createCriteria(ChAcctMast.class);
		criteriaQuery.add(Restrictions.like("compositeId.codAcctNo", pk.getCodAcctNo()));
		criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus", pk.getFlgMntStatus()));
		criteriaQuery.add(Restrictions.eq("compositeId.codEntityVpd", pk.getCodEntityVpd()));
		criteriaQuery.add(Restrictions.not(Restrictions.in("codAcctStat", acctStat)));
		return ((List<ChAcctMast>) criteriaQuery.list());
	}
    public ChAcctMast SKHT_getByAcctNo(String acctNo, Integer codOrgBrn) {
        org.hibernate.Query query = this.getSession().getNamedQuery("CiCustMast#SKHT_getByAcctNo");
        query.setString("acctNo", acctNo.toString());
        query.setInteger("codOrgBrn", codOrgBrn); 
		ChAcctMast cm = (ChAcctMast) query.uniqueResult(); 
		return cm;
    }

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}
	
	
	protected final void rightPadScaceAccountNo(ChAcctMastPK pk) {
		pk.setCodAcctNo(this.rightPadSpaceAccountNo(pk.getCodAcctNo()));
	}
	
	protected final String rightPadSpaceAccountNo(String accountNo) {
		if (accountNo == null)
			accountNo = "";
		else if (accountNo.length() >= ACCT_NO_LENGTH)
			return accountNo;
		
		StringBuilder sb = new StringBuilder(accountNo);
		for (int i=accountNo.length(); i<ACCT_NO_LENGTH; i++)
			sb.append(' ');
		
		return sb.toString();
	}
}
