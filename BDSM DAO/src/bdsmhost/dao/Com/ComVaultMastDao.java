/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao.Com;

import bdsm.model.BaseModel;
import bdsm.model.Com.ComVaultDtls;
import bdsm.model.Com.ComVaultMast;
import bdsm.model.Com.ComVaultDtlsHist;
import bdsmhost.dao.BaseDao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class ComVaultMastDao extends BaseDao {

    public ComVaultMastDao(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    public boolean vaultDtlInsert(ComVaultDtls model) {
        getSession().save(model);
        return true;
    }
    
    public boolean vaultMastHistInsert(ComVaultDtlsHist model) {
        getSession().save(model);
        return true;
    }
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    protected boolean vaultDtlUpdate(ComVaultDtls model) {
        getSession().update(model);
        return true;
    }
    
    public List<ComVaultMast> getVaultMast(){
        List<ComVaultMast> cvmList = new ArrayList();
        
        return cvmList;
    }
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().update(model);
        return true;
    }
}
