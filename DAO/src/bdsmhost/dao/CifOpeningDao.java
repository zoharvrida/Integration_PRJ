/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.CifOpening;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00020800
 */
public class CifOpeningDao extends BaseDao implements Work {

	private String Natld;
	private String idBatch;
	private Integer result;
	private int flag;
	private Logger logger = Logger.getLogger(CifOpeningDao.class);

	public CifOpeningDao(Session session) {
		super(session);
	}

    public List getExisting(Integer cod_cust, String prodEx) {
		List newlist = null;
		try {
			Query q = this.getSession().getNamedQuery("Cif#getExistingAccount");
			q.setInteger("pId", cod_cust);
            q.setString("pCodProd",prodEx);
			q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			logger.info("QUERY CONVERTED SUCCESSFULLY");
			return q.list();
		} catch (HibernateException hibernateException) {
			logger.info("HIBERNATE :" + hibernateException, hibernateException);
			return newlist;
		}
	}

    public List getExixtingCIF(Integer cod_cust, Map parameter, String appID) {
		List newlist = null;
        List repList = null;
		try {
			Query q = this.getSession().getNamedQuery("Cif#getReplicatedCIF");
			q.setInteger("pcif", cod_cust);
			q.setString("pEducation", parameter.get("lastEdu").toString());
			q.setString("pMarstat", parameter.get("marStat").toString());
			q.setString("pReligion", parameter.get("religion").toString());
			q.setString("pAlias", parameter.get("aliasName").toString());
			q.setString("pMother", parameter.get("namMother").toString());
			q.setString("pResidence", parameter.get("residenceType").toString());
			q.setString("piDcardType", parameter.get("iDcardType").toString());
			q.setString("pExpDate", parameter.get("iCExpiryDate").toString());
			q.setString("pBirthPlace", parameter.get("birthPlace").toString());
			q.setString("pIncome", parameter.get("monthLyIncome").toString());
			q.setString("pDataExtract", parameter.get("dataTransXtractFlagDesc").toString());
			q.setString("pHomeStatus", parameter.get("homeStatusDesc").toString());
			q.setString("pNoDepend", parameter.get("noOfDependant").toString());
			q.setString("pTin", parameter.get("tin").toString());
			q.setString("pGreenCard", parameter.get("greenCard").toString());
            q.setString("pProductBundling", parameter.get("productBundling").toString());
            q.setString("pSandiDatiII", parameter.get("sandiDatiII").toString());
			q.setString("pAlamatAS", parameter.get("alamatAS").toString());
			q.setString("pBadanAS", parameter.get("badanAS").toString());
			q.setString("pFatca", parameter.get("fatca").toString());
			q.setString("pExpectedLimit", parameter.get("expectedLimit").toString());
			q.setString("pCommunication", parameter.get("communication").toString());
		    q.setString("pSumberDanaLain", parameter.get("sumberDanaLain").toString()); 

						logger.info("Before : " + q.getQueryString());

			q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			logger.info("QUERY CONVERTED SUCCESSFULLY");
            newlist = q.list();
            if (newlist.isEmpty()) {
                q = this.getSession().getNamedQuery("Cif#getReplicatedCIFNoDetl");
                q.setInteger("pcif", cod_cust);
                q.setString("pEducation", parameter.get("lastEdu").toString());
                q.setString("pMarstat", parameter.get("marStat").toString());
                q.setString("pReligion", parameter.get("religion").toString());
                q.setString("pAlias", parameter.get("aliasName").toString());
                q.setString("pMother", parameter.get("namMother").toString());
                q.setString("pResidence", parameter.get("residenceType").toString());
                q.setString("piDcardType", parameter.get("iDcardType").toString());
                q.setString("pExpDate", parameter.get("iCExpiryDate").toString());
                q.setString("pBirthPlace", parameter.get("birthPlace").toString());
                q.setString("pIncome", parameter.get("monthLyIncome").toString());
                q.setString("pDataExtract", parameter.get("dataTransXtractFlagDesc").toString());
                q.setString("pHomeStatus", parameter.get("homeStatusDesc").toString());
                q.setString("pNoDepend", parameter.get("noOfDependant").toString());
                q.setString("pTin", parameter.get("tin").toString());
                q.setString("pGreenCard", parameter.get("greenCard").toString());
                q.setString("pAlamatAS", parameter.get("alamatAS").toString());
                q.setString("pBadanAS", parameter.get("badanAS").toString());
                q.setString("pFatca", parameter.get("fatca").toString());
                q.setString("pExpectedLimit", parameter.get("expectedLimit").toString());
                q.setString("pCommunication", parameter.get("communication").toString());
				q.setString("pSumberDanaLain", parameter.get("sumberDanaLain").toString());
                q.setString("pProductBundling", parameter.get("productBundling").toString());
                q.setString("pSandiDatiII", parameter.get("sandiDatiII").toString());
                q.setString("pBranchLocCodes", parameter.get("branchlocCodes").toString());
                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                logger.info("QUERY CONVERTED SUCCESSFULLY");
                repList = q.list();
                if(repList.isEmpty()){
                    q = this.getSession().getNamedQuery("Cif#getExternalData");
                    q.setString("pBatchNo", appID);
			return q.list();
            } else {
                    return repList;
                }
            } else {
                return newlist;
            }
		} catch (HibernateException hibernateException) {
			logger.info("HIBERNATE :" + hibernateException, hibernateException);
			return newlist;
		}
	}
	public List getNum(Integer codcust){
		DetachedCriteria maxNum = DetachedCriteria.forClass(CifOpening.class)
				.setProjection(Projections.max("incr"));
		Criteria crt = this.getSession().createCriteria(CifOpening.class);
        crt.add(Restrictions.eq("cifNumber", codcust));
        crt.add(Restrictions.eq("compositeId.parentId", "Y"));
		crt.add(Property.forName("incr").eq(maxNum));
        return crt.list();
	}
	
	public List getUdfVal(String inqParam, String value, String fieldName) {
		List newlist = null;
		try {
			Query q = this.getSession().getNamedQuery("Cif#" + fieldName);
			q.setString("pId",value.toUpperCase().toString());
			if (inqParam.length() > 0) {
				q.setString("pField", inqParam);
			}
			q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			logger.info("QUERY CONVERTED SUCCESSFULLY");
			return q.list();
		} catch (HibernateException hibernateException) {
			logger.info("HIBERNATE :" + hibernateException, hibernateException);
			return newlist;
		}
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		getSession().save(model);
		return true;
	}

	public boolean cifInsert(CifOpening model) {
		getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public CifOpening get(String pk) {
		return (CifOpening) getSession().get(CifOpening.class, pk);
	}

	public int runCIF(String Natld, String idBatch) {
		Session session = getSession();
		this.flag = 1;
		this.Natld = Natld;
		this.idBatch = idBatch;
		session.doWork(this);
		return result;
	}

	public void execute(Connection cnctn) throws SQLException {

		try {
			if (this.flag == 1) {
				String query = queryCIF;
				CallableStatement stmt = cnctn.prepareCall(query);
				stmt.registerOutParameter(1, java.sql.Types.INTEGER);
				stmt.setString(2, Natld);
				stmt.setString(3, idBatch);
				stmt.executeUpdate();
				result = stmt.getInt(1);
				stmt.close();
			} else {
			}

		} catch (SQLException e) {
			logger.info("SQL EXCEPTION :" + e, e);
		} catch (Exception e) {
			logger.info("EXCEPTION :" + e, e);
		}
	}
	private static final String queryCIF = "{? = call PK_BDSM_OPENING.ADD_CUSTOMER(?,?)}";
}
