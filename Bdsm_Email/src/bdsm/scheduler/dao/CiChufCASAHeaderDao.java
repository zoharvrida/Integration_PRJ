/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.CiChUfCASAHeader;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class CiChufCASAHeaderDao extends BaseDao {
    public CiChufCASAHeaderDao (Session session){
        super(session);
    }

    public List get(String noBatch) {
        Criteria criteriaQuery = getSession().createCriteria(CiChUfCASAHeader.class);
        criteriaQuery.add(Restrictions.eq("batchId", noBatch));
        return criteriaQuery.list();
    }
    public List<CiChUfCASAHeader> getModel(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(CiChUfCASAHeader.class);
        criteriaQuery.add(Restrictions.eq("batchId", batchId));
        return criteriaQuery.list();
    }
    public List<CiChUfCASAHeader> getNumber(String batchId,Integer record_id) {
        Criteria criteriaQuery = getSession().createCriteria(CiChUfCASAHeader.class);
        criteriaQuery.add(Restrictions.eq("batchId", batchId));
        criteriaQuery.add(Restrictions.eq("recordId", record_id));
        return criteriaQuery.list();
    }
    public String getMax(String idBatch){
        String number;
        StringBuilder qry = new StringBuilder("select max(RECORD_ID)");
        qry.append(" FROM TMP_DDFCH_HEADER a");
        qry.append(" where a.BATCH_ID = :idBatch");
        
        Query q = getSession().createSQLQuery(qry.toString());
        q.setString("idBatch", idBatch);
        if(q.uniqueResult().toString() == null){
            number = "0";
        } else {
            number = q.uniqueResult().toString();
        }
        return number;
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((CiChUfCASAHeader)model);
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
    public String bankMast() {
        //CustomResult1 customResult1 = null;
        StringBuilder qry = new StringBuilder("select dat_process");
        qry.append(" from ba_bank_mast a");
        
        Query q = getSession().createSQLQuery(qry.toString());
        return q.uniqueResult().toString();
    }
    
}
