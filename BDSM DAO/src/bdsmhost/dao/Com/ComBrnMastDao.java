/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao.Com;

import bdsm.model.BaseModel;
import bdsm.model.Com.ComBrnMast;
import bdsmhost.dao.BaseDao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class ComBrnMastDao extends BaseDao {

    public ComBrnMastDao(Session session) {
        super(session);
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
    public List<ComBrnMast> getComBrnMast(ComBrnMast mast){
        List<ComBrnMast> cvmList = new ArrayList();
        
        Query query = this.getSession().getNamedQuery("ComBrnMast#getListAllBranch");
        query.setString("idBrn",mast.getIdBrn());
        cvmList = (List<ComBrnMast>) query.list();

        return cvmList;
    }
}
