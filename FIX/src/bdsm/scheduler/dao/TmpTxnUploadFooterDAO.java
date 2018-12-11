package bdsm.scheduler.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.scheduler.model.TmpTxnUploadFooter;


/**
 * 
 * @author bdsm
 */
public class TmpTxnUploadFooterDAO extends bdsmhost.dao.BaseDao {
	
    /**
     * 
     * @param session
     */
    public TmpTxnUploadFooterDAO(Session session) {
		super(session);
	}
	
	
    /**
     * 
     * @param fileId
     * @return
     */
    public TmpTxnUploadFooter getByFileId(String fileId) {
		Criteria crt = this.getSession().createCriteria(TmpTxnUploadFooter.class);
		crt.add(Restrictions.eq("compositeId.fileId", fileId));
		
		return (TmpTxnUploadFooter) crt.uniqueResult();
	}
	
	
    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		this.getSession().save((TmpTxnUploadFooter) model);
		return true;
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}


}
