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
 * @author user
 */
public class FcrBaCcyCodeDao extends BaseDao {

    public FcrBaCcyCodeDao(Session session) {
        super(session);
    }

    public List list() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT A.namCcyShort ");
        queryString.append("FROM FcrBaCcyCode A ");
        queryString.append("WHERE A.compositeId.flgMntStatus = 'A'");
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
