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
public class FcyTxnFlagUDDao extends BaseDao {
    public FcyTxnFlagUDDao(Session session) {
        super(session);
    }

    public List list(Integer noCif, String ccyUd, String noUd, Double amtTxn) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("select b.noUd, CAST(b.ccyUd as VARCHAR(3)) ccyUd, b.amtLimit, ");
        qryStr.append("b.amtAvail, b.cdBranch, b.dtExpiry, b.amtLimitUsd, b.amtAvailUsd ");
        qryStr.append("from ci_custmast a ");
        qryStr.append(", MfcUdMaster b ");
        qryStr.append("where a.cod_cust_id = :pNoCif and a.flg_mnt_status = 'A' and a.cod_entity_vpd = 11 ");
        //qryStr.append("and b.noCif = a.cod_cust_id and b.ccyUd = :pCcyUd and b.noUd LIKE :pNoUd ");
        qryStr.append("and b.noCif = a.cod_cust_id and b.noUd LIKE :pNoUd ");
        //qryStr.append("and b.status='A' and b.dtExpiry >= trunc(sysdate)");
        qryStr.append("and b.status='A' and b.dtExpiry >= (select DAT_PROCESS from BA_BANK_MAST)");
        qryStr.append("AND b.amtAvailUsd >= :pAmtTxn ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setInteger("pNoCif", noCif);
        //q.setString("pCcyUd", ccyUd);
        q.setString("pNoUd", noUd + "%");
        q.setDouble("pAmtTxn", amtTxn);
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