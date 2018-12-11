/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MasterMenu;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 00030663
 */
public class MasterMenuDao extends BaseDao {
    public MasterMenuDao(Session session) {
        super(session);
    }
    
/**    
    public List<MasterMenu> list(String idMenuParent) {
        if (idMenuParent==null) {
            Query q = getSession().createQuery("from MasterMenu where idMenuParent is null order by idMenu");
            return q.list();
        } else {
            Query q = getSession().createQuery("from MasterMenu where idMenuParent=:p1 order by idMenu");
            q.setString("p1", idMenuParent);
            return q.list();
        }
    }
*/

    public List listComplete(String namMenu) {
        Criteria criteriaQuery = getSession().createCriteria(MasterMenu.class);
        criteriaQuery.add(Restrictions.like("namMenu", namMenu + "%"));
        criteriaQuery.addOrder(Order.asc("idMenu"));
        return criteriaQuery.list();
    }

    public List list(String namMenu) {
        Criteria criteriaQuery = getSession().createCriteria(MasterMenu.class);
        criteriaQuery.add(Restrictions.like("namMenu", namMenu + "%"));
        criteriaQuery.add(Restrictions.isNotNull("url"));
        criteriaQuery.addOrder(Order.asc("idMenu"));
        return criteriaQuery.list();
    }

    public MasterMenu get(String idMenu) {
        return (MasterMenu) getSession().get(MasterMenu.class, idMenu);
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
