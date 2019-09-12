/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.CifOpening;
import bdsm.model.FieldMapping;
import bdsm.model.FieldMappingPK;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class FieldMappingDao extends BaseDao {

    public FieldMappingDao(Session session) {
        super(session);
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
    public FieldMapping get(FieldMappingPK pk) {
        return (FieldMapping) getSession().get(FieldMapping.class, pk);
    }
}
