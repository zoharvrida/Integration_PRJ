/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcyTxnManInputCIF;
import java.math.BigDecimal;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class FcyTxnManInputCIFDao extends BaseDao {
    public FcyTxnManInputCIFDao(Session session) {
        super(session);
    }

    public FcyTxnManInputCIF get(Integer noCif, Integer period) {
        FcyTxnManInputCIF fcyTxnManInputCIF = null;
                
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("select a.cod_cust_id, a.nam_cust_full, ");
        qryStr.append("nvl(b.amtAvailUsd, (select value from parameter where cdparam = 'MFCLMTAMT')) amtAvailUsd ");
        qryStr.append("from ci_custmast a, MfcTxnMaster b ");
        qryStr.append("where a.cod_cust_id = :pNoCif ");
        qryStr.append("and a.flg_mnt_status = 'A' ");
        qryStr.append("and a.cod_entity_vpd = 11 ");
        qryStr.append("and b.noCif(+) = a.cod_cust_id ");
        qryStr.append("and b.period(+) = :pPeriod ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setInteger("pNoCif", noCif);
        q.setInteger("pPeriod", period);
        
        Object[] objFcyTxnManInputCIF = (Object[])q.uniqueResult();
        
        if(objFcyTxnManInputCIF != null) {
            fcyTxnManInputCIF = new FcyTxnManInputCIF();
            fcyTxnManInputCIF.setNoCif(new Integer(((BigDecimal)objFcyTxnManInputCIF[0]).intValue()));
            fcyTxnManInputCIF.setPeriod(period);
            fcyTxnManInputCIF.setNamCustFull((String)objFcyTxnManInputCIF[1]);
            fcyTxnManInputCIF.setAmtAvailUsd(new Double(((BigDecimal)objFcyTxnManInputCIF[2]).doubleValue()));
        }
        
        return fcyTxnManInputCIF;
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