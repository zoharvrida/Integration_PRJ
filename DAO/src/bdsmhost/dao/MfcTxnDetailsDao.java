/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MfcTxnDetails;
import bdsm.model.MfcTxnDetailsPK;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class MfcTxnDetailsDao extends BaseDao {
    public MfcTxnDetailsDao(Session session) {
        super(session);
    }

    public MfcTxnDetails get(Integer noCif, String noAcct, String refTxn) {
        MfcTxnDetails mfcTxnDetails = null;
        
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT A.noCif, A.period, A.noUd, CAST(A.noAcct as VARCHAR2(16)), ");
        qryStr.append("A.refTxn, A.status, A.idCreatedBy, A.idCreatedSpv, ");
        qryStr.append("CAST(A.dtmCreated as Timestamp), CAST(A.dtmCreatedSpv as Timestamp) ");
        qryStr.append("FROM MfcTxnDetails A ");
        qryStr.append("WHERE A.noCif = :pNoCif ");
        qryStr.append("AND A.noAcct = CAST(:pNoAcct AS CHAR(16)) ");
        qryStr.append("AND TRIM(A.refTxn) = :pRefTxn ");
        
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setInteger("pNoCif", noCif);
        q.setString("pNoAcct", noAcct);
        q.setString("pRefTxn", refTxn);
        
        Object[] objMfcTxnDetails = (Object[])q.uniqueResult();
        
        if(objMfcTxnDetails != null) {
            MfcTxnDetailsPK mfcTxnDetailsPK = new MfcTxnDetailsPK();
            mfcTxnDetailsPK.setNoAcct((String)objMfcTxnDetails[3]);
            mfcTxnDetailsPK.setRefTxn((String)objMfcTxnDetails[4]);

            mfcTxnDetails = new MfcTxnDetails();
            mfcTxnDetails.setCompositeId(mfcTxnDetailsPK);
            mfcTxnDetails.setNoCif(new Integer(((BigDecimal)objMfcTxnDetails[0]).intValue()));
            mfcTxnDetails.setPeriod(new Integer(((BigDecimal)objMfcTxnDetails[1]).intValue()));
            mfcTxnDetails.setNoUd((String)objMfcTxnDetails[2]);
            mfcTxnDetails.setStatus(((Character)objMfcTxnDetails[5]).toString());
            mfcTxnDetails.setIdCreatedBy((String)objMfcTxnDetails[6]);
            mfcTxnDetails.setIdCreatedSpv((String)objMfcTxnDetails[7]);
            mfcTxnDetails.setDtmCreated((Timestamp)objMfcTxnDetails[8]);
            mfcTxnDetails.setDtmCreatedSpv((Timestamp)objMfcTxnDetails[9]);
        }
        
        return mfcTxnDetails;
    }
    
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((MfcTxnDetails)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((MfcTxnDetails)model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
