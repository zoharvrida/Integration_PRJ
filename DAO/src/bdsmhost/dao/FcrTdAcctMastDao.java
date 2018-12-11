/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrTdAcctMast;
import bdsm.model.FcrTdAcctMastPK;
import java.math.BigDecimal;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author user
 */
public class FcrTdAcctMastDao extends BaseDao {
    public FcrTdAcctMastDao(Session session) {
        super(session);
    }

    public FcrTdAcctMast get(FcrTdAcctMastPK pk) {
        FcrTdAcctMast fcrTdAcctMast = null;
        
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT CAST(A.cod_Acct_No as VARCHAR2(16)), A.flg_Mnt_Status, A.cod_Entity_Vpd, ");
        qryStr.append("A.cod_Acct_Title, A.cod_Prod, A.cod_Cust, A.cod_Tds ");
        qryStr.append("FROM Td_Acct_Mast A ");
        qryStr.append("WHERE A.cod_Acct_No = CAST(:pCodAcctNo AS CHAR(16)) ");
        qryStr.append("AND A.flg_Mnt_Status = :pFlgMntStatus ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pCodAcctNo", pk.getCodAcctNo());
        q.setString("pFlgMntStatus", pk.getFlgMntStatus());
        
        Object[] objFcrTdAcctMast = (Object[])q.uniqueResult();
        
        if(objFcrTdAcctMast != null) {
            FcrTdAcctMastPK fcrTdAcctMastPK = new FcrTdAcctMastPK();
            fcrTdAcctMastPK.setCodAcctNo((String)objFcrTdAcctMast[0]);
            fcrTdAcctMastPK.setFlgMntStatus(((Character)objFcrTdAcctMast[1]).toString());

            fcrTdAcctMast = new FcrTdAcctMast();
            fcrTdAcctMast.setCompositeId(fcrTdAcctMastPK);
            fcrTdAcctMast.setCodEntityVpd(new Integer(((BigDecimal)objFcrTdAcctMast[2]).intValue()));
            fcrTdAcctMast.setCodAcctTitle(objFcrTdAcctMast[3].toString());
            fcrTdAcctMast.setCodProd(new Integer(((BigDecimal)objFcrTdAcctMast[4]).intValue()));
            fcrTdAcctMast.setCodCust(new Integer(((BigDecimal)objFcrTdAcctMast[5]).intValue()));
            fcrTdAcctMast.setCodTds(new Integer(((BigDecimal)objFcrTdAcctMast[6]).intValue()));
        }
        
        return fcrTdAcctMast;
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