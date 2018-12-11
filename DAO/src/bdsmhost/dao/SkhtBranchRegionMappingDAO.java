/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.SkhtBranchRegionMapping;
import org.hibernate.Session;

/**
 *
 * @author v00022309
 */
public class SkhtBranchRegionMappingDAO extends BaseDao{
    
     public SkhtBranchRegionMappingDAO(Session session){
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
     
 
    public  SkhtBranchRegionMapping getByBranchCode(Integer codOrgBrn) {
       return (SkhtBranchRegionMapping) getSession().get(SkhtBranchRegionMapping.class, codOrgBrn);
    }

  
    
}
