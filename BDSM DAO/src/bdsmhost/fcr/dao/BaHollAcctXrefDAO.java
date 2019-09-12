/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.BaHoCollAcctXref;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00020841
 */
public class BaHollAcctXrefDAO extends BaseDao{

    public BaHollAcctXrefDAO(Session session) {
        super(session);
    }

    public List<BaHoCollAcctXref> getByAcctNo(String codAcctNo){
        Query query = this.getSession().createQuery("from BaHoCollAcctXref where codAcctNo = :codAcctNo");
        query.setString("codAcctNo", codAcctNo);
        return query.list();
    }

    public List<BaHoCollAcctXref> listAcctXref(String codAcctNo) {
        Criteria criteriaQuery = getSession().createCriteria(BaHoCollAcctXref.class);
        criteriaQuery.add(Restrictions.eq("acctXrefPK.codAcctNo", codAcctNo));
        List<BaHoCollAcctXref> last = criteriaQuery.list();
        return last;
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
