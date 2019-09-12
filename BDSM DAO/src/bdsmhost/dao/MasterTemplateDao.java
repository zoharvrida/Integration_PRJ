/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MasterTemplate;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 00030663
 */
public class MasterTemplateDao extends BaseDao {
    public MasterTemplateDao(Session session) {
        super(session);
    }
    
    public List list(String namTemplate) {
        Criteria criteriaQuery = getSession().createCriteria(MasterTemplate.class);
        criteriaQuery.add(Restrictions.like("namTemplate", namTemplate + "%"));
        criteriaQuery.addOrder(Order.asc("namTemplate"));
        return criteriaQuery.list();
    }

    public MasterTemplate get(String idTemplate) {
        return (MasterTemplate) getSession().get(MasterTemplate.class, idTemplate);
    }
 
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((MasterTemplate)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((MasterTemplate)model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((MasterTemplate)model);
        return true;
    }
    
}
