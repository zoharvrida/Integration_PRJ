/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.EOD_BOD_ProcMast;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("unchecked")
public class EOD_BOD_ProcMastDAO extends bdsmhost.dao.BaseDao {
	private static final Logger LOGGER = Logger.getLogger(EOD_BOD_ProcMastDAO.class);
	

    /**
     * 
     * @param session
     */
    public EOD_BOD_ProcMastDAO(Session session) {
		super(session);
	}


    /**
     * 
     * @param id
     * @return
     */
    public EOD_BOD_ProcMast get(Integer id) {
		return (EOD_BOD_ProcMast) this.getSession().get(EOD_BOD_ProcMast.class, id);
	}

    /**
     * 
     * @param moduleName
     * @param procedureName
     * @return
     */
    public EOD_BOD_ProcMast getByModuleNameAndProcedureName(String moduleName, String procedureName) {
		Criteria crt = this.getSession().createCriteria(EOD_BOD_ProcMast.class);
		crt.add(Restrictions.eq("moduleName", moduleName));
		crt.add(Restrictions.eq("procedureName", procedureName));
		
		return (EOD_BOD_ProcMast) crt.uniqueResult();
	}


    /**
     * 
     * @param type
     * @return
     */
    public List<EOD_BOD_ProcMast> listStartingRequest(int type) {
		Criteria crt = this.getSession().createCriteria(EOD_BOD_ProcMast.class);
		crt.add(Restrictions.eq("type", type));
		crt.add(Restrictions.isNull("procIdDepend"));
		crt.addOrder(Order.asc("procId"));
		
		return crt.list();
	}

    /**
     * 
     * @param id
     * @return
     */
    public EOD_BOD_ProcMast getNextRequestProcess(Integer id) {
		Criteria crt = this.getSession().createCriteria(EOD_BOD_ProcMast.class);
		crt.add(Restrictions.eq("procIdDepend", id));
		crt.add(Restrictions.eq("flagProcDB", StatusDefinition.REQUEST.charAt(0)));
		List<EOD_BOD_ProcMast> procMastList = crt.list();
		
		if (procMastList.size() == 1)
			return procMastList.get(0);
		else
			return null;
	}

    /**
     * 
     * @param id
     * @return
     */
    public List<Integer> listSuccessorProcessId(Integer id) {
		Query query = this.getSession().getNamedQuery("EOD_BOD_ProcMast#listSuccessorProcId");
		query.setInteger("procId", id);
		
		return query.list();
	}

    /**
     * 
     * @param id
     */
    public void rejectAllSuccessorProcess(Integer id) {
		List<Integer> listSuccessor = this.listSuccessorProcessId(id);
		
		if ((listSuccessor != null) && (listSuccessor.size() > 0)) {
			Query query = this.getSession().getNamedQuery("EOD_BOD_ProcMast#updateProcFlagByProcIdList");
			query.setCharacter("flgProc", StatusDefinition.REJECTED.charAt(0));
			query.setParameterList("procIdList", listSuccessor);
			query.executeUpdate();
		}
	}

    /**
     * 
     * @param type
     * @return
     */
    public List<EOD_BOD_ProcMast> listUnfinish(int type) {
		Criteria crt = this.getSession().createCriteria(EOD_BOD_ProcMast.class);
		crt.add(Restrictions.eq("type", type));
		crt.add(Restrictions.in("flagProcDB", 
				new Character[] {StatusDefinition.REQUEST.charAt(0), StatusDefinition.PROCESS.charAt(0)}));
		crt.addOrder(Order.asc("procId"));
		
		return crt.list();
	}

    /**
     * 
     * @param type
     */
    public void reset_EOD_BOD_Status(int type) {
		LOGGER.debug("[BEGIN] resetEODStatus");
		Query q = this.getSession().getNamedQuery("EOD_BOD_ProcMast#resetProcAndDateByType");
		q.setInteger("pType", type);
		q.executeUpdate();
		LOGGER.debug("[END] resetEODStatus");
	}


    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Insert Operation");
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Update Operation");
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Delete Operation");
	}

}
