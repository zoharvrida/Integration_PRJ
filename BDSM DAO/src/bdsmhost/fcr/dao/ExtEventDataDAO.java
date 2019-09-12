/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.fcr.model.ExtEventData;
import bdsm.model.BaseModel;


/**
 * 
 * @author v00019372
 */
public class ExtEventDataDAO extends bdsmhost.dao.BaseDao {

	public ExtEventDataDAO(Session session) {
		super(session);
	}
	
	
	public ExtEventData get(int type) {
		return this.get(type, null);
	}
	
	@SuppressWarnings("unchecked")
	public ExtEventData get(int type, Integer postingDate) {
		String eventType = null;
		
		switch(type) {
			case 1:
				eventType = "Cutoff";
				break;
			case 2:
				eventType = "Beginning of Day completed";
		}
		
		Criteria crt = this.getSession().createCriteria(ExtEventData.class);
		crt.add(Restrictions.eq("eventType", eventType));
		if (postingDate != null)
			crt.add(Restrictions.eq("postingDate", postingDate));
		else
			crt.addOrder(Order.desc("postingDate"));
		
		List<ExtEventData> list = crt.list();
		if (list.size() > 0)
			return list.get(0);
		
		return null;
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Insert Operation");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Update Operation");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Delete Operation");
	}
}
