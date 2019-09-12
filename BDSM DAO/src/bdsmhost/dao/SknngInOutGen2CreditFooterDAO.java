/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel; 
import bdsm.model.HostSknngInOutCreditFooter;
import bdsm.model.SknNgInOutDebitFooter;
import bdsm.model.SknNgInOutDebitHeader;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jeffri Tambunan
 */
public class SknngInOutGen2CreditFooterDAO extends BaseDao {
	
    public SknngInOutGen2CreditFooterDAO(Session session) {
        super(session);
    }
    
    public HostSknngInOutCreditFooter get(String batchNo, int parentRecordNo) {
		Query q = this.getSession().createQuery("FROM TmpSknngInOutCreditFooter WHERE compositeId.batchNo = :pBatchNo AND parentRecordId = :pParentRecordNo");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return (HostSknngInOutCreditFooter) q.uniqueResult();
	}

    public List<HostSknngInOutCreditFooter> list(String batchNo) {
        Query q = getSession().createQuery("FROM TmpSknngInOutCreditFooter WHERE compositeId.batchNo = :pBatchNo ORDER BY compositeId.recordId");
        q.setString("pBatchNo", batchNo);
        
        return q.list();
    }

    public List<SknNgInOutDebitFooter> listgetYear(String batchNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitFooter WHERE compositeId.batchNo = :pBatchNo "
                        + "AND typeDB = :pType AND extractStatusDB = :pExtract "
				+ "ORDER BY compositeId.recordId ");
        q.setString("pBatchNo", batchNo);
		q.setCharacter("pType", 'I');
		q.setCharacter("pExtract",'N');
		return q.list();
	}
    
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
        return true;
    }
}
