/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.LnAcctDtls;
import bdsm.fcr.model.LnAcctDtlsPK;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author v00020841
 */
public class LnAcctDtlsDAO extends  BaseDao{

    public LnAcctDtlsDAO(Session session) {
        super(session);
    }

    public LnAcctDtls get(LnAcctDtlsPK id){
        return (LnAcctDtls) this.getSession().get(LnAcctDtls.class, id);
    }
    
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
