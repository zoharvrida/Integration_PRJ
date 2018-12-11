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
public class FcyTxnFlagCIFDao extends BaseDao {
    public FcyTxnFlagCIFDao(Session session) {
        super(session);
    }

    public List list(String noAcct, Integer period) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("select b.cod_cust, c.nam_cust_full, b.cod_acct_cust_rel, ");
        qryStr.append("nvl(d.amtAvailUsd, (select value from parameter where cdparam = 'MFCLMTAMT')) amtAvailUsd ");
        qryStr.append("from (");
        qryStr.append("    select cod_acct_no from ch_acct_mast a1 ");
        qryStr.append("    where a1.cod_acct_no = CAST(:pNoAcct1 AS CHAR(16)) and a1.flg_mnt_status = 'A' ");
        qryStr.append("    and a1.cod_entity_vpd = 11");
        qryStr.append("    union");
        qryStr.append("    select cod_acct_no from td_acct_mast a2 ");
        qryStr.append("    where a2.cod_acct_no = CAST(:pNoAcct2 AS CHAR(16)) and a2.flg_mnt_status = 'A' ");
        qryStr.append("    and a2.cod_entity_vpd = 11");
        qryStr.append(") a ");
        qryStr.append(", ch_acct_cust_xref b ");
        qryStr.append(", ci_custmast c ");
        qryStr.append(", MfcTxnMaster d ");
        qryStr.append("where b.cod_acct_no = a.cod_acct_no and b.flg_mnt_status = 'A' ");
        qryStr.append("and b.cod_entity_vpd = 11 ");
        qryStr.append("and c.cod_cust_id = b.cod_cust and c.flg_mnt_status = 'A' ");
        qryStr.append("and c.cod_entity_vpd = 11");
        qryStr.append("and d.noCif(+) = b.cod_cust and d.period(+) = :pPeriod ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pNoAcct1", noAcct);
        q.setString("pNoAcct2", noAcct);
        q.setInteger("pPeriod", period);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        return q.list();
    }

    public List list(String noAcct) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("select DISTINCT b.cod_cust, c.nam_cust_full, b.cod_acct_cust_rel ");
        //qryStr.append("nvl(d.amtAvailUsd, (select value from parameter where cdparam = 'LLD_LIMIT_AMOUNT')) amtAvailUsd ");
        qryStr.append("from (");
        qryStr.append("    select cod_acct_no from ch_acct_mast a1 ");
        qryStr.append("    where a1.cod_acct_no = CAST(:pNoAcct1 AS CHAR(16)) and a1.flg_mnt_status = 'A' ");
        qryStr.append("    and a1.cod_entity_vpd = 11");
        qryStr.append("    union");
        qryStr.append("    select cod_acct_no from td_acct_mast a2 ");
        qryStr.append("    where a2.cod_acct_no = CAST(:pNoAcct2 AS CHAR(16)) and a2.flg_mnt_status = 'A' ");
        qryStr.append("    and a2.cod_entity_vpd = 11");
        qryStr.append(") a ");
        qryStr.append(", ch_acct_cust_xref b ");
        qryStr.append(", ci_custmast c ");
        qryStr.append(", mfcudmaster_lld d ");
        qryStr.append("where b.cod_acct_no = a.cod_acct_no and b.flg_mnt_status = 'A' ");
        qryStr.append("and b.cod_entity_vpd = 11 ");
        qryStr.append("and c.cod_cust_id = b.cod_cust and c.flg_mnt_status = 'A' ");
        qryStr.append("and c.cod_entity_vpd = 11 ");
        //qryStr.append("and (c.cod_cust_id = d.nocif or d.nocif = 0)");
        qryStr.append("and d.nocif(+)= b.cod_cust ");
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pNoAcct1", noAcct);
        q.setString("pNoAcct2", noAcct);
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
