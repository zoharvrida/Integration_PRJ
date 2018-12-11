/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.CmCustcardMast;
import bdsm.fcr.model.CmCustcardMastPK;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00020841
 */
public class CmCustcardMastDao extends  BaseDao{

    public CmCustcardMastDao(Session session) {
        super(session);
    }
    
 public CmCustcardMast get(CmCustcardMastPK id){
     return (CmCustcardMast) this.getSession().get(CmCustcardMast.class, id);
 }

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((CmCustcardMast) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
