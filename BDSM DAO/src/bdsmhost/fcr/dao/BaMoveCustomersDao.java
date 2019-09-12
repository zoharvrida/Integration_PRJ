/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.BaMoveCustomers;
import bdsm.fcr.model.BaMoveCustomersPK;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00020841
 */
public class BaMoveCustomersDao extends BaseDao{

    public BaMoveCustomersDao(Session session) {
        super(session);
    }

    public BaMoveCustomers get(BaMoveCustomersPK id) {
        return (BaMoveCustomers) this.getSession().get(BaMoveCustomers.class, id);
    }
    
    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save((BaMoveCustomers) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        this.getSession().update((BaMoveCustomers) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}
