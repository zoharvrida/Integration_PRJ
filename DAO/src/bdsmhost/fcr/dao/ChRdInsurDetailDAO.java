/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.ChRdInsurDetail;
import bdsm.fcr.model.ChRdInsurDetailPK;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00020841
 */
public class ChRdInsurDetailDAO extends BaseDao {

    public ChRdInsurDetailDAO(Session session) {
        super(session);
    }

   public ChRdInsurDetail get(ChRdInsurDetailPK id){
       return (ChRdInsurDetail) this.getSession().get(ChRdInsurDetail.class, id);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((ChRdInsurDetail) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
