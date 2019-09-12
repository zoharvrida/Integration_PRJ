/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.BaMoveAccounts;
import bdsm.fcr.model.BaMoveAccountsPK;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00020841
 */
public class BaMoveAccountsDao extends BaseDao {

    public BaMoveAccountsDao(Session session) {
        super(session);
    }

  public BaMoveAccounts get(BaMoveAccountsPK id){
      return (BaMoveAccounts) this.getSession().get(BaMoveAccounts.class, id);
  }
    
    
    
    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save((BaMoveAccounts) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        this.getSession().update((BaMoveAccounts)model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
