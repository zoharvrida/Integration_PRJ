package bdsmhost.dao.Email;


import bdsm.model.BaseModel;
import bdsm.model.Email.FixEmailAccess;
import bdsm.model.Email.FixEmailAccessPK;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class FixEmailAccessDao extends bdsmhost.dao.BaseDao {

	public FixEmailAccessDao(Session session) {
		super(session);
	}
	
	
	public String getReq(String grpId) {
		return this.getUserEmail(grpId, "100", null);
	}

	public String getSpv(String grpId) {
		return this.getUserEmail(grpId, "010", null);
	}

	public String getMonUser(String grpId) {
		return this.getUserEmail(grpId, "001", null);
	}
	
	public String getReq(String grpId, int idScheduler) {
		return this.getUserEmail(grpId, "100", Integer.valueOf(idScheduler));
	}
	
	/*
	 * INT-848 : [BDSM] Auto Reply Group Access Right Enhancement Add Method to
	 * Get SPV with Param GrpID and ID Scheduler
	 */
	public String getSpv(String grpId, int idScheduler) {
		return this.getUserEmail(grpId, "010", Integer.valueOf(idScheduler));
	}
	
	
	public String getSpvFromSender(String sender, int idScheduler) {
		return this.getSpvFromReq(sender, idScheduler);
	}

	public String getSpvFromReq(String sender, int idScheduler) {
		Criteria c = this.getSession().createCriteria(FixEmailAccess.class);
		c.add(Restrictions.eq("fixEmailAccessPK.idScheduler", Integer.valueOf(idScheduler)));
		c.add(Restrictions.ilike("fixEmailAccessPK.sender", sender));
		FixEmailAccess fixEmailAccess = (FixEmailAccess) c.uniqueResult();
		
		return this.getSpv(fixEmailAccess.getGrpId(), idScheduler);
	}
	

	public List<FixEmailAccess> get(FixEmailAccessPK fixEmailAccessPK) {
		Criteria criteriaQuery = this.getSession().createCriteria(FixEmailAccess.class);

		if (fixEmailAccessPK.getSender() != null)
			criteriaQuery.add(Restrictions.ilike("fixEmailAccessPK.sender", fixEmailAccessPK.getSender()));

		if (fixEmailAccessPK.getIdScheduler() != null)
			criteriaQuery.add(Restrictions.eq("fixEmailAccessPK.idScheduler", fixEmailAccessPK.getIdScheduler()));

		return criteriaQuery.list();
	}

	public List list() {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT DISTINCT a.grpID ");
		queryString.append("FROM fixemailaccess a ");
		queryString.append("ORDER BY a.grpid");
		Query q = this.getSession().createSQLQuery(queryString.toString());

		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return q.list();
	}

	public List lister(String grpID, String cdAccess, int idScheduler) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT a.sender o");
		queryString.append(" FROM fixemailaccess a ");
		queryString.append("WHERE a.grpID = '");
		queryString.append(grpID).append("'");
		queryString.append(" AND a.cdAccess = '");
		queryString.append(cdAccess).append("'");
		queryString.append(" AND a.idScheduler =");
		queryString.append(idScheduler);
		queryString.append(" ORDER BY a.grpid");
		
		Query q = this.getSession().createSQLQuery(queryString.toString());
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return q.list();
	}

	public List list(String grpID, String cdAccess, int idScheduler) {
		FixEmailAccess fixAccessEmail = null;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT a.grpID, a.cdAccess,a.idScheduler, a.sender");
		queryString.append(" FROM fixemailaccess a ");
		queryString.append("WHERE a.grpID = '");
		queryString.append(grpID).append("'");
		queryString.append(" AND a.cdAccess = '");
		queryString.append(cdAccess).append("'");
		queryString.append(" AND a.idScheduler =");
		queryString.append(idScheduler);
		queryString.append(" ORDER BY a.grpid");
		Query q = this.getSession().createSQLQuery(queryString.toString());

		List<FixEmailAccess> objFixEmail = new ArrayList<FixEmailAccess>();

		for (int i = 0; i < q.list().size(); i++) {
			Object[] objFixAccessEmail = (Object[]) q.list().get(i);

			if (objFixAccessEmail != null) {
				FixEmailAccessPK fixAccessEmailPK = new FixEmailAccessPK();
				fixAccessEmailPK.setIdScheduler((new Integer(((BigDecimal) objFixAccessEmail[2]).intValue())));
				fixAccessEmailPK.setSender((String) objFixAccessEmail[3]);

				fixAccessEmail = new FixEmailAccess();
				fixAccessEmail.setGrpId((String) objFixAccessEmail[0]);
				fixAccessEmail.setCdAccess((String) objFixAccessEmail[1]);
				fixAccessEmail.setFixEmailAccessPK(fixAccessEmailPK);
				// fixAccessEmail.setSender((String)objFixAccessEmail[3]);

				objFixEmail.add(fixAccessEmail);
			}
		}

		return objFixEmail;
		// q.list();
	}

	public List list(String grpID) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT a.grpID,a.sender ");
		queryString.append("FROM fixemailaccess a ");
		queryString.append("WHERE a.grpID = '");
		queryString.append(grpID).append("'");
		queryString.append("ORDER BY a.grpid");
		
		Query q = this.getSession().createSQLQuery(queryString.toString());
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return q.list();
	}

	public FixEmailAccess get(FixEmailAccessPK pk, String grpID, String cdAccess) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT a.grpID,a.sender,a.idScheduler,a.cdAccess ");
		queryString.append("FROM fixemailaccess a ");
		queryString.append("WHERE a.grpID = '");
		queryString.append(grpID).append("'");
		queryString.append("AND a.sender = '");
		queryString.append(pk.getSender()).append("'");
		queryString.append("AND a.idScheduler = '");
		queryString.append(pk.getIdScheduler()).append("'");
		queryString.append("AND a.cdAccess = '");
		queryString.append(cdAccess).append("'");
		queryString.append("ORDER BY a.grpid");
		
		Query q = this.getSession().createSQLQuery(queryString.toString());

		return (FixEmailAccess) this.getSession().get(FixEmailAccess.class, pk);
	}
	

	@Override
	protected boolean doInsert(BaseModel baseModel) {
		this.getSession().save((FixEmailAccess) baseModel);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel baseModel) {
		this.getSession().update((FixEmailAccess) baseModel);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel baseModel) {
		this.getSession().delete((FixEmailAccess) baseModel);
		return true;
	}
	

	private String getUserEmail(String grpId, String codeAccess, Integer idScheduler) {
		List<FixEmailAccess> userEmailList = null;
		String retVal = "";

		Criteria criteriaQuery = this.getSession().createCriteria(FixEmailAccess.class);
		criteriaQuery.add(Restrictions.eq("grpId", grpId.trim()));
		criteriaQuery.add(Restrictions.eq("cdAccess", codeAccess));
		if (idScheduler != null)
			criteriaQuery.add(Restrictions.eq("fixEmailAccessPK.idScheduler", idScheduler));

		userEmailList = criteriaQuery.list();

		if (userEmailList.size() > 0) {
			java.util.Set<String> userEmailSet = new java.util.LinkedHashSet<String>();

			// Remove duplicates using Set
			for (int i = 0; i < userEmailList.size(); i++) {
				FixEmailAccess fx = userEmailList.get(i);
				userEmailSet.add(fx.getFixEmailAccessPK().getSender());
			}

			retVal = org.apache.commons.lang.StringUtils.join(userEmailSet, ";");
		}

		return retVal;
	}

}