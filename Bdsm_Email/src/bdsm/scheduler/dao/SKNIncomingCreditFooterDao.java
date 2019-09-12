/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.SKNIncomingCreditFooter;
import bdsm.scheduler.model.SknNgIncomingCreditFPK;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class SKNIncomingCreditFooterDao extends BaseDao {
	private Logger logger = Logger.getLogger(SKNIncomingCreditFooterDao.class);
    public SKNIncomingCreditFooterDao (Session session){
        super(session);
   }
	public SKNIncomingCreditFooter get(SknNgIncomingCreditFPK pk) {
	        return (SKNIncomingCreditFooter) getSession().get(SKNIncomingCreditFooter.class, pk);
	 }
    public List<SKNIncomingCreditFooter> getModel(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(SKNIncomingCreditFooter.class);
        criteriaQuery.add(Restrictions.eq("compositeId.batchNo", batchId));
        return criteriaQuery.list();
    }
    @Override
    protected boolean doInsert(BaseModel model) {
         getSession().save((SKNIncomingCreditFooter) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
         try {
			getSession().update((SKNIncomingCreditFooter) model);
		} catch (HibernateException hibernateException) {
			logger.info(hibernateException,hibernateException);
		}
		 return true;
    }

	public List getMaxId() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MAX(recordid) AS MAXRECORD ");
		sb.append("from TMP_SKNNG_GEN1_CREDIT_FOOTER ");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
