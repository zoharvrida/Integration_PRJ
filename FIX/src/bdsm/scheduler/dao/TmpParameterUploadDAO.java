/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpParameterUpload;
import bdsmhost.dao.BaseDao;


/**
 * 
 * @author v00019237
 */
@SuppressWarnings("unchecked")
public class TmpParameterUploadDAO extends BaseDao {
    /**
     * 
     * @param session
     */
    public TmpParameterUploadDAO(Session session) {
		super(session);
	}
	
	
    /**
     * 
     * @param id
     * @return
     */
    public TmpParameterUpload get(java.io.Serializable id) {
		return (TmpParameterUpload) this.getSession().get(TmpParameterUpload.class, id);
	}
	
    /**
     * 
     * @param batchNo
     * @return
     */
    public java.util.List<TmpParameterUpload> getByBatchNo(String batchNo) {
		return this.getByBatchNoAndFlagStatus(batchNo, null);
	}
	
    /**
     * 
     * @param batchNo
     * @param flagStatus
     * @return
     */
    public java.util.List<TmpParameterUpload> getByBatchNoAndFlagStatus(String batchNo, String flagStatus) {
		Criteria criteria = this.getSession().createCriteria(TmpParameterUpload.class);
		criteria.add(Restrictions.eq("batchNo", batchNo));
		if (flagStatus != null) {
			if ("null".equalsIgnoreCase(flagStatus))
				criteria.add(Restrictions.isNull("flagStatusDB"));
			else
				criteria.add(Restrictions.eq("flagStatusDB", flagStatus.charAt(0)));
		}
		
		return criteria.list();
	}
	
    /**
     * 
     * @param batchNo
     * @param codeParam
     * @return
     */
    public java.util.List<TmpParameterUpload> getByBatchNoAndCodeParam(String batchNo, String codeParam) {
		Criteria criteria = this.getSession().createCriteria(TmpParameterUpload.class);
		criteria.add(Restrictions.eq("batchNo", batchNo));
		criteria.add(Restrictions.eq("codeParam", codeParam));
		
		return criteria.list();
	}
	
    /**
     * 
     * @param codeParam
     * @return
     */
    public java.util.List<TmpParameterUpload> getByCodeParam(String codeParam) {
		Criteria criteria = this.getSession().createCriteria(TmpParameterUpload.class);
		criteria.add(Restrictions.eq("codeParam", codeParam));
		
		return criteria.list();
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save(model);
		return true;
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return true;
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}

}
