/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcyTxnManInputUD;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class FcyTxnManInputUDDao extends BaseDao {
    public FcyTxnManInputUDDao(Session session) {
        super(session);
    }

    public List list(Integer noCif, String noUd, String ccyUd, Double amtTxn) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("select b.noUd, CAST(b.ccyUd as VARCHAR2(3)) as CCYUD, b.amtLimit, b.amtAvail, b.amtLimitUsd, b.amtAvailUsd, ");
        qryStr.append("b.cdBranch, b.dtExpiry ");
        qryStr.append("from ci_custmast a ");
        qryStr.append(", MfcUdMaster b ");
        qryStr.append("where a.cod_cust_id = :pNoCif and a.flg_mnt_status = 'A' and a.cod_entity_vpd = 11 ");
        qryStr.append("and b.noCif = a.cod_cust_id ");
        qryStr.append("and b.noUd like :pNoUd ");
        //qryStr.append("and b.status='A' and b.dtExpiry >= trunc(sysdate)");
        qryStr.append("and b.status='A' and b.dtExpiry >= (SELECT dat_process FROM ba_bank_mast WHERE flg_mnt_status = 'A') ");
        qryStr.append("AND b.amtAvailUsd >= :pAmtTxn ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setInteger("pNoCif", noCif);
        q.setString("pNoUd", noUd + "%");
        //q.setString("pCcyUd", ccyUd);
        q.setDouble("pAmtTxn", amtTxn);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        return q.list();
    }

    private List getListUD(List listData) {
        FcyTxnManInputUD fcyTxnManInputUD;
        Object[] object;
        List listUD = new ArrayList<FcyTxnManInputUD>();
        
        for(int i = 0; i < listData.size(); i++) {
            object = (Object[])listData.get(i);
            
            fcyTxnManInputUD = new FcyTxnManInputUD();
            fcyTxnManInputUD.setNoUd((String)object[0]);
            fcyTxnManInputUD.setCcyUd((String)object[1]);
            fcyTxnManInputUD.setAmtLimit(new Double(((BigDecimal)object[2]).doubleValue()));
            fcyTxnManInputUD.setAmtAvail(new Double(((BigDecimal)object[3]).doubleValue()));
            fcyTxnManInputUD.setCdBranch(new Integer(((BigDecimal)object[4]).intValue()));
            fcyTxnManInputUD.setDtExpiry((Date)object[5]);
            
            listUD.add(fcyTxnManInputUD);
        }
        
        return listUD;
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