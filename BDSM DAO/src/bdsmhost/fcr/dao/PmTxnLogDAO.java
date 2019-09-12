/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.BaCcyCode;
import bdsm.model.BaseModel;
import bdsm.fcr.model.PmTxnLog;
import bdsm.fcr.model.PmTxnLogPK;
import bdsmhost.dao.BaseDao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00022309
 */
public class PmTxnLogDAO extends BaseDao {
    private Logger logger = Logger.getLogger(PmTxnLogDAO.class);
    
    public PmTxnLogDAO(Session session) {
        super(session);
    }
    
    public PmTxnLog MCR_getByRefNetworkNo(String refNetworkNo, Date datTxn, Integer codOrgBrn) {
        org.hibernate.Query query = this.getSession().getNamedQuery("PmTxnLog#MCR_getByRefNetworkNo");
        query.setString("refNetworkNo", refNetworkNo.toString());
        query.setDate("datTxn", datTxn);
        query.setInteger("codOrgBrn", codOrgBrn); 
		PmTxnLog pmtxn = (PmTxnLog) query.uniqueResult(); 
		return pmtxn;
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
