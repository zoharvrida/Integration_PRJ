/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.XrefTemplateMenu;
import bdsm.model.XrefTemplateMenuPK;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 00030663
 */
public class XrefTemplateMenuDao extends BaseDao {

    public XrefTemplateMenuDao(Session session) {
        super(session);
    }

    public List list(String idTemplate) {
        Criteria criteriaQuery = getSession().createCriteria(XrefTemplateMenu.class);
        criteriaQuery.add(Restrictions.eq("compositeId.idTemplate", idTemplate));
        criteriaQuery.addOrder(Order.asc("compositeId.idMenu"));
        return criteriaQuery.list();
    }

    public List listAvailable(String idTemplate) {
        Criteria criteriaQuery = getSession().createCriteria(XrefTemplateMenu.class);
        criteriaQuery.add(Restrictions.eq("compositeId.idTemplate", idTemplate));
        criteriaQuery.add(Restrictions.not(Restrictions.like("accessRight", "0000%") ));
        criteriaQuery.addOrder(Order.asc("compositeId.idMenu"));
        return criteriaQuery.list();
    }

    public XrefTemplateMenu get(XrefTemplateMenuPK pk) {
        return (XrefTemplateMenu) getSession().get(XrefTemplateMenu.class, pk);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((XrefTemplateMenu) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((XrefTemplateMenu) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((XrefTemplateMenu) model);
        return true;
    }
}
