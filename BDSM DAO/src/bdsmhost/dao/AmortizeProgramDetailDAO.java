package bdsmhost.dao;

import bdsm.model.AmortizeProgramDetail;
import bdsm.model.BaseModel;
import bdsm.util.SchedulerUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019372
 */
public class AmortizeProgramDetailDAO extends BaseDao {

	private static final String COPY_PROGRAM_DETAIL_TO_HISTORY = "PKG_AMORTIZE_GIFT.COPY_PROGRAM_DETAIL_TO_HISTORY(:id, :userId, :spvId, :currentTime)";
	private static final String GENERATE_PROGRAM_DETAIL_ID = "PKG_AMORTIZE_GIFT.GENERATE_PROGRAM_DETAIL_ID";
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AmortizeProgramDetailDAO.class);

	public AmortizeProgramDetailDAO(Session session) {
		super(session);
	}

	public AmortizeProgramDetail get(Integer id) {
		return (AmortizeProgramDetail) this.getSession().get(AmortizeProgramDetail.class, id);
	}

	public AmortizeProgramDetail get(String giftCode, Integer productCode, java.util.Date effectiveDate, Integer term) {
		Criteria crt = this.getSession().createCriteria(AmortizeProgramDetail.class);
		crt.add(Restrictions.eq("giftCode", giftCode));
		crt.add(Restrictions.eq("productCode", productCode));
		crt.add(Restrictions.eq("effectiveDate", effectiveDate));
		crt.add(Restrictions.eq("term", term));

		return (AmortizeProgramDetail) crt.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<AmortizeProgramDetail> getAll() {
		return this.getSession().createCriteria(AmortizeProgramDetail.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<AmortizeProgramDetail> listByGiftCodeAndProductCode(String giftCode, Integer productCode) {
		Criteria crt = this.getSession().createCriteria(AmortizeProgramDetail.class);
		crt.add(Restrictions.eq("giftCode", giftCode));
		crt.add(Restrictions.eq("productCode", productCode));
		crt.addOrder(Order.desc("effectiveDate"));
		crt.addOrder(Order.asc("term"));

		return crt.list();
	}

	public List<AmortizeProgramDetail> listDistinct(String giftCode, Integer productCode) {
		Criteria crt = this.getSession().createCriteria(AmortizeProgramDetail.class);
		crt.setProjection(Projections.distinct(Projections.property("giftCode")));
		crt.add(Restrictions.like("giftCode", giftCode.concat("%")));
		crt.add(Restrictions.eq("productCode", productCode));
		crt.addOrder(Order.desc("effectiveDate"));
		crt.addOrder(Order.asc("term"));

		return crt.list();
	}

	@SuppressWarnings("unchecked")
	public List<Date> listDate(Integer codeProd, String giftCode, String DateS, Integer Terms) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT DISTINCT A.effective_date ");
		queryString.append("FROM amortize_program_detail A ");
		queryString.append("where effective_date <= TO_DATE(:DateS, 'dd/mm/yyyy') ");
		queryString.append("and product = :pcodeProd ");
		queryString.append("and GIFT_CODE LIKE :pgiftCode ");
		queryString.append("and TERM = :pTerm ");
		queryString.append("ORDER BY A.effective_date DESC ");

		Query q = getSession().createSQLQuery(queryString.toString());
		//q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		q.setInteger("pcodeProd", codeProd);
		q.setInteger("pTerm", Terms);
		q.setString("pgiftCode", giftCode);
		q.setString("DateS", DateS);
		return q.list();

	}

	@SuppressWarnings("rawtypes")
	public List listGift(String giftCode, Integer codeProd, String mode, String Account) {
		StringBuilder queryString = new StringBuilder();
		// add operations
		queryString.append(ListgiftState(mode, Account, codeProd));
		Query q = getSession().createSQLQuery(queryString.toString());
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		q.setString("pgiftCode", giftCode);
		if (StringUtils.isNotBlank(Account)) {
			q.setString("pAcctNo", Account);
		}
		if (codeProd.compareTo(0) >= 0) {
			q.setInteger("pcodeProd", codeProd);
		}
		//return getListReport(q.list());
		return q.list();
	}

	public List listTerm(String dateEff, String giftCode, Integer codeProd, String State, String Account, Integer Term) {
		StringBuilder queryString = new StringBuilder();
		// add operations
		List TokenStat = new ArrayList();
		StringTokenizer statusToken = new StringTokenizer(State, ":");
		while (statusToken.hasMoreTokens()) {
			TokenStat.add(statusToken.nextToken());
		}
		if (TokenStat.get(0).toString().equalsIgnoreCase("TermInquiry")) {
			queryString.append(ListTermState(TokenStat.get(1).toString(), Account));
		} else {
			queryString.append(ListTermState(State, Account));
		}
		Query q = getSession().createSQLQuery(queryString.toString());
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		q.setString("pgiftCode", giftCode);
		if (StringUtils.isNotBlank(Account)) {
			try {
				String flag = TokenStat.get(1).toString();
				if(flag.equalsIgnoreCase("2") || flag.equalsIgnoreCase("3"))
					q.setString("pAcctNo", Account);
				else
					q.setInteger("pcodeProd", codeProd);	
			} catch (Exception e) {
				logger.info("EXCEPTION : TOKEN NOT FOUND");
				q.setString("pAcctNo", Account);
			}
		} else {
			try {
				String flag = TokenStat.get(1).toString();
				if(flag.equalsIgnoreCase("1")) 
				q.setInteger("pcodeProd", codeProd);	
			} catch (Exception e) {
				logger.info("EXCEPTION : TOKEN NOT FOUND");
			}
		}
		if (TokenStat.get(0).toString().equalsIgnoreCase("TermInquiry")) {
			q.setString("pdateEff", dateEff);
			q.setInteger("pTerm", Term);
		}
		/*if (!State.equalsIgnoreCase("Inquiry")) {
			if (!TokenStat.get(0).toString().equalsIgnoreCase("TermInquiry")) {
				q.setInteger("pcodeProd", codeProd);
			}
		}*/

		//return getListReport(q.list());
		return q.list();
	}

	public List listAcct(String Acct, String State) {
		StringBuilder queryString = new StringBuilder();
		// add operations
		queryString.append(ListacctState(State));
		Query q = getSession().createSQLQuery(queryString.toString());
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		q.setString("pAcctNo", Acct);

		//return getListReport(q.list());
		return q.list();
	}

	public StringBuilder ListTermState(String Mode, String Account) {
		StringBuilder queryString = new StringBuilder();
		if (Mode.equalsIgnoreCase("Add")) {
			queryString.append("SELECT DISTINCT(B.TERM) ");
			queryString.append("FROM amortize_program_detail B ");
			//queryString.append("WHERE B.product = :pcodeProd ");
			queryString.append("WHERE B.GIFT_CODE LIKE :pgiftCode ");
			/*if (StringUtils.isNotBlank(Account)) {
				queryString.append("and not exists(select 1 from amortize_account D ");
				queryString.append("where D.program_detail_id = B.id ");
				queryString.append("and D.ACCT_NO LIKE '%'||:pAcctNo||'%' ");
				queryString.append("and D.status = '1') ");
			}*/
			queryString.append("ORDER BY B.TERM ASC ");
		} else if (Mode.equalsIgnoreCase("Delete") || Mode.equalsIgnoreCase("Inquiry")) {
			queryString.append("SELECT B.TERM,D.ID ");
			queryString.append("FROM amortize_program_detail B, amortize_account D ");
			//queryString.append("WHERE B.product = :pcodeProd ");
			queryString.append("WHERE B.GIFT_CODE LIKE :pgiftCode ");
			queryString.append("AND B.ID = D.PROGRAM_DETAIL_ID ");
			if(StringUtils.isNotBlank(Account)){
				queryString.append("AND D.ACCTNO LIKE :pAcctNo||'%' ");
			}
			queryString.append("and D.status = '1' ");
			/*if (StringUtils.isNotBlank(Account)) {
				queryString.append("and exists(select 1 from amortize_account D ");
				queryString.append("where D.program_detail_id = B.id ");
				queryString.append("and trim(D.ACCT_NO) LIKE trim(:pAcctNo) ");
				queryString.append("and D.status = '1') ");
			}*/
			queryString.append("ORDER BY B.ID ASC ");
		} else { // Add
			//queryString.append("SELECT B.TERM, B.GIFT_PRICE, B.MINIMUM_HOLD, B.HOLD_AMOUNT ,B.MAXIMUM_HOLD, C.ID, C.PROGRAM_DETAIL_ID, C.STATUS, C.OPEN_DATE,C.CANCEL_DATE, C.HOLD_AMOUNT,");
			queryString.append("SELECT (SELECT DAT_PROCESS FROM BA_BANK_MAST WHERE FLG_MNT_STATUS = 'A') AS DATEEFF,");
			if (StringUtils.isNotBlank(Account) && ((Mode.equalsIgnoreCase("2") || Mode.equalsIgnoreCase("3")))) {
				queryString.append("B.TERM, C.GIFT_PRICE, B.MINIMUM_HOLD ,B.MAXIMUM_HOLD, C.ID, B.ID AS PROGRAM_DETAIL_ID, "
						+ "C.STATUS, nvl(C.OPEN_DATE,null) AS OPEN_DATE ,nvl(C.CANCEL_DATE,null) AS CANCEL_DATE, "
						+ "nvl(C.HOLD_AMOUNT,'0') AS HOLD_AMOUNT , nvl(C.GIFT_PRICE_GROSS,'0') AS GIFT_PRICE_GROSS,nvl(C.TAX_AMOUNT,'0') AS TAX_AMOUNT ");
				queryString.append("FROM amortize_program_detail B, amortize_account C ");
			} else if(StringUtils.isBlank(Account) || Mode.equalsIgnoreCase("1")) {
				queryString.append("B.TERM, B.GIFT_PRICE, B.MINIMUM_HOLD ,B.MAXIMUM_HOLD, ('0') AS ID, "
						+ "B.ID AS PROGRAM_DETAIL_ID,('1') AS STATUS, ('0') AS OPEN_DATE, ('0') AS CANCEL_DATE,"
						+ "('0') AS HOLD_AMOUNT, (0) AS GIFT_PRICE_GROSS, (0) AS TAX_AMOUNT ");
				queryString.append("FROM amortize_program_detail B ");
			}
			queryString.append("where B.effective_date = TO_DATE(:pdateEff, 'dd/mm/yyyy') ");
			queryString.append("and B.GIFT_CODE LIKE :pgiftCode ");
			if (StringUtils.isNotBlank(Account)) {
				/*if (Mode.equalsIgnoreCase("2")){
					queryString.append("and C.ACCT_NO LIKE '%'||:pAcctNo||'%' ");
				}*/			
				if (Mode.equalsIgnoreCase("2") || Mode.equalsIgnoreCase("3")) {
					queryString.append("and C.ACCT_NO LIKE '%'||:pAcctNo||'%' ");
					queryString.append("AND C.PROGRAM_DETAIL_ID = B.ID ");
					queryString.append("and c.status = '1' ");
				} else {
					queryString.append("and B.PRODUCT = :pcodeProd ");
				}
			} else {
				if (Mode.equalsIgnoreCase("1"))
					queryString.append("and B.PRODUCT = :pcodeProd ");
			}
			queryString.append("and B.TERM = :pTerm ");
			queryString.append("ORDER BY B.effective_date DESC ");
		}
		logger.info("QUERY TERM :" + queryString);
		return queryString;
	}

	public StringBuilder ListgiftState(String Mode, String Account, Integer codeProd) {
		StringBuilder queryString = new StringBuilder();
		if (Mode.equalsIgnoreCase("Inquiry") || Mode.equalsIgnoreCase("Delete")) {
			queryString.append("SELECT DISTINCT(A.GIFT_CODE),A.GIFT_NAME, C.PROGRAM_ID, C.PROGRAM_NAME, A.ID_ACCRUAL, C.TAX_PCT, A.PRODUCT, A.TYPE, A.VOUCHER ");
			queryString.append("FROM amortize_program_master A,amortize_program_detail B, amortize_account C ");
			queryString.append("WHERE A.GIFT_CODE LIKE '%'||:pgiftCode||'%' ");
			if (codeProd.compareTo(0) >= 0) {
				queryString.append("AND A.product = :pcodeProd ");
			}
			if (StringUtils.isNotBlank(Account)) {
				queryString.append("AND A.PRODUCT = B.PRODUCT ");
				queryString.append("AND A.GIFT_CODE = B.GIFT_CODE ");
				queryString.append("and C.ACCT_NO LIKE :pAcctNo||'%' ");
				queryString.append("and B.ID = C.PROGRAM_DETAIL_ID ");
			}
			if(Mode.equalsIgnoreCase("Delete")){
				queryString.append("AND C.STATUS = '1'");
			}
			queryString.append("ORDER BY A.GIFT_CODE ASC ");
		} else {
			queryString.append("SELECT DISTINCT(A.GIFT_CODE),A.GIFT_NAME, A.PROGRAM_ID, A.PROGRAM_NAME, A.ID_ACCRUAL, A.TAX_PCT, A.PRODUCT, A.TYPE, A.VOUCHER ");
			if (StringUtils.isNotBlank(Account)) {
				queryString.append("FROM amortize_program_master A,amortize_program_detail B, amortize_account C ");
			} else {
				queryString.append("FROM amortize_program_master A,amortize_program_detail B ");
			}
			queryString.append("WHERE A.GIFT_CODE = B.GIFT_CODE ");
			queryString.append("and A.product = B.product ");
			queryString.append("and A.product = :pcodeProd ");
			queryString.append("and A.GIFT_CODE LIKE '%'||:pgiftCode||'%' ");
			if (StringUtils.isNotBlank(Account)) {
				queryString.append("and C.ACCT_NO LIKE '%'||:pAcctNo||'%' ");
				queryString.append("and B.ID <> C.PROGRAM_DETAIL_ID ");
			}
			queryString.append("ORDER BY A.GIFT_CODE DESC ");
		}
		logger.info("QUERY :" + queryString);
		return queryString;
	}

	public StringBuilder ListacctState(String Mode) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT DISTINCT(ACCT_NO),C.COD_PROD,C.NAM_PRODUCT ");
		queryString.append("FROM AMORTIZE_ACCOUNT A,AMORTIZE_PROGRAM_DETAIL B,CH_PROD_MAST C ");
		queryString.append("WHERE A.PROGRAM_DETAIL_ID = B.ID ");
		queryString.append("AND B.PRODUCT = C.COD_PROD ");
		//queryString.append("AND C.CH_ACCT_NO = CAST(A.ACCT_NO AS CHAR(16)) ");
		queryString.append("AND C.FLG_MNT_STATUS = 'A' ");
		queryString.append("AND A.ACCT_NO LIKE '%'||:pAcctNo||'%' ");
		if (Mode.equalsIgnoreCase("Delete") || (Mode.equalsIgnoreCase("Inquiry"))) {
			queryString.append("AND A.STATUS = '1'");
		}
		queryString.append("ORDER BY A.ACCT_NO ASC ");
		logger.info("QUERY :" + queryString);
		return queryString;
	}

	public void copyToHistory(Integer id, String userId, String spvId) {
		Query query = this.getSession().createSQLQuery("{ CALL " + COPY_PROGRAM_DETAIL_TO_HISTORY + "}");
		query.setInteger("id", id);
		query.setString("userId", userId);
		query.setString("spvId", spvId);
		query.setTimestamp("currentTime", SchedulerUtil.getTime());

		query.executeUpdate();
	}

	public Integer generateId() {
		Query query = this.getSession().createSQLQuery("SELECT " + GENERATE_PROGRAM_DETAIL_ID + " FROM dual");
		int id = ((Number) query.uniqueResult()).intValue();

		return Integer.valueOf(id);
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((AmortizeProgramDetail) model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		AmortizeProgramDetail modelUser = (AmortizeProgramDetail) model;
		AmortizeProgramDetail modelDB = this.get(modelUser.getId());
		this.getSession().delete(modelDB);

		return true;
	}
}
