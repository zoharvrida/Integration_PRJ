/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrBaCcyCode;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00017250
 */
public class ETaxCurrencyDao extends BaseDao{

    public ETaxCurrencyDao(Session session) {
		super(session);
	}
    
	public List list() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT e ");
        queryString.append("FROM ETaxCurrency e ");
        queryString.append("ORDER BY e.ordering");        
        Query q = getSession().createQuery(queryString.toString());
        
        return q.list();
    }
	
	public FcrBaCcyCode get(String namCcyShort) {
        Criteria criteriaQuery = getSession().createCriteria(FcrBaCcyCode.class);
        criteriaQuery.add(Restrictions.eq("namCcyShort", namCcyShort));
        criteriaQuery.setMaxResults(1);
        
        return (FcrBaCcyCode) criteriaQuery.uniqueResult();
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
