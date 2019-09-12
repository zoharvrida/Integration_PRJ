package bdsm.scheduler.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.EOD_BOD_Activity;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("unchecked")
public class EOD_BOD_ActivityDAO extends bdsmhost.dao.BaseDao {

    /**
     * 
     * @param session
     */
    public EOD_BOD_ActivityDAO(Session session) {
		super(session);
	}
	
	
    /**
     * 
     * @return
     */
    public  List<EOD_BOD_Activity> listAll() {
		Criteria crt = this.getSession().createCriteria(EOD_BOD_Activity.class);
		crt.addOrder(Order.desc("postingDate"));
		crt.addOrder(Order.desc("systemDate"));
		
		return crt.list();
	}
	
    /**
     * 
     * @param postingDate
     * @return
     */
    public List<EOD_BOD_Activity> listByPostingDate(Integer postingDate) {
		Criteria crt = this.getSession().createCriteria(EOD_BOD_Activity.class);
		crt.add(Restrictions.eq("postingDate", postingDate));
		crt.addOrder(Order.desc("systemDate"));
		
		return crt.list();
	}
	

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((EOD_BOD_Activity) model);
		return true;
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
