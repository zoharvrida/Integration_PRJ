/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.BaProdBrnXref;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00020841
 */
public class BaProdBrnXrefDAO extends BaseDao {

    public BaProdBrnXrefDAO(Session session) {
        super(session);
    }

    public List<BaProdBrnXref> getBranchProd(Integer ccBrn, Integer prod, String module) {
        StringBuilder sb = new StringBuilder();
        sb.append("from BaProdBrnXref");
        sb.append(" where codCcBrn = :codCcBrn");
        sb.append(" and codProd = :codProd and moduleCode = :codModule");
        Query query = this.getSession().createQuery(sb.toString());
        query.setInteger("codCcBrn", ccBrn);
        query.setInteger("codProd", prod);
        query.setString("codModule", module);
        return query.list();
    }

    public List<BaProdBrnXref> getBranchProdCH(Integer ccBrn, Integer prod, String module, String custType) {
        StringBuilder sb = new StringBuilder();
        sb.append("from BaProdBrnXref");
        sb.append(" where codCcBrn = :codCcBrn and codProd = :codProd");
        sb.append("  and moduleCode = :codModule and codCustType = :codCustType");
        Query query = this.getSession().createQuery(sb.toString());
        query.setInteger("codCcBrn", ccBrn);
        query.setInteger("codProd", prod);
        query.setString("codModule", module);
        query.setString("codCustType", custType);
        return query.list();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
