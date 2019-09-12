/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class FcyTxnSummPendingTxnDao extends BaseDao {
    public FcyTxnSummPendingTxnDao(Session session) {
        super(session);
    }

    public List list(String noAcct) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("select CAST(dtmTxn AS Timestamp) dtmTxn, dtPost, refTxn, idTxn, ");
        qryStr.append("CAST(ccyTxn as VARCHAR2(3)) ccyTxn, amtTxn, txnBranch, ");
        qryStr.append("CAST(typMsg as VARCHAR2(1)) typMsg, CAST(noAcct as VARCHAR2(16)) noAcct ");
        qryStr.append("from MfcNoBook ");
        qryStr.append("where noAcct = CAST(:pNoAcct AS CHAR(16)) and status = 'P' ");
        qryStr.append("  and dtPost = ( ");
        qryStr.append("    select dat_process from ba_bank_mast ");
        qryStr.append("    where flg_mnt_status = 'A' ");
        qryStr.append("  ) ");
        qryStr.append(" AND typAcct <> 'L' ");
        qryStr.append(" AND typTrx = 'VALAS' ");
        qryStr.append("order by dtmTxn ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pNoAcct", noAcct);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        return q.list();
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
