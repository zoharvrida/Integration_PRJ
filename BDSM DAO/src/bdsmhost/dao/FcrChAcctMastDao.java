/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrChAcctMast;
import bdsm.model.FcrChAcctMastPK;
import java.math.BigDecimal;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author user
 */
public class FcrChAcctMastDao extends BaseDao {
    
	 private Logger logger = Logger.getLogger(FcrChAcctMastDao.class);
	public FcrChAcctMastDao(Session session) {
        super(session);
    }

    public FcrChAcctMast get(FcrChAcctMastPK pk) {
        FcrChAcctMast fcrChAcctMast = null;
        
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT CAST(A.cod_Acct_No as VARCHAR2(16)), A.flg_Mnt_Status, A.cod_Entity_Vpd, A.cod_Cust, A.cod_tds, ");
        qryStr.append("A.cod_acct_stat, A.cod_acct_title, A.cod_ccy ");
        qryStr.append("FROM Ch_Acct_Mast A ");
        qryStr.append("WHERE A.cod_Acct_No = CAST(:pCodAcctNo AS CHAR(16)) ");
        qryStr.append("AND A.flg_Mnt_Status = :pFlgMntStatus ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pCodAcctNo", pk.getCodAcctNo());
        q.setString("pFlgMntStatus", pk.getFlgMntStatus());
        
        Object[] objFcrChAcctMast = (Object[])q.uniqueResult();
        
        if(objFcrChAcctMast != null) {
            FcrChAcctMastPK fcrChAcctMastPK = new FcrChAcctMastPK();
            fcrChAcctMastPK.setCodAcctNo((String)objFcrChAcctMast[0]);
            fcrChAcctMastPK.setFlgMntStatus(((Character)objFcrChAcctMast[1]).toString());

            fcrChAcctMast = new FcrChAcctMast();
            fcrChAcctMast.setCompositeId(fcrChAcctMastPK);
            fcrChAcctMast.setCodEntityVpd(new Integer(((BigDecimal)objFcrChAcctMast[2]).intValue()));
            fcrChAcctMast.setCodCust(new Integer(((BigDecimal)objFcrChAcctMast[3]).intValue()));
            fcrChAcctMast.setCodTds(new Integer(((BigDecimal)objFcrChAcctMast[4]).intValue()));
            fcrChAcctMast.setCodAcctStat(new Integer(((BigDecimal)objFcrChAcctMast[5]).intValue()));
            fcrChAcctMast.setCodAcctTitle((String)objFcrChAcctMast[6]);
            fcrChAcctMast.setCodCcy(new Integer(((BigDecimal)objFcrChAcctMast[7]).intValue()));
        }
        
        return fcrChAcctMast;
    }
    public FcrChAcctMast getFull(FcrChAcctMastPK pk){
		return (FcrChAcctMast) this.getSession().get(FcrChAcctMast.class, pk);
    }    
        
    public Object[] getAccountStatus(String accountNo) {
    	return this.getAccountStatus(accountNo, null, null);
    }
    
    public Object[] getAccountStatus(String accountNo, String flagMntStatus, String accountType) {
    	Query query = this.getSession().getNamedQuery("fcrChAcctMast#getAccountStatusByAccountNoAndAccountType");
    	query.setString("accountNo", accountNo);
    	query.setString("flagMntStatus", (flagMntStatus == null? "A": flagMntStatus));
    	query.setString("accountType", (accountType == null? "CH": accountType));
    	
    	/*
    	 * object[0] = code
    	 * object[1] = description
    	 * */
    	return (Object[]) query.uniqueResult();
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