/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.FieldParameter;
import bdsm.scheduler.model.FieldParameterPK;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class FieldParamaterDao extends BaseDao {
    /**
     * 
     * @param session
     */
    public FieldParamaterDao(Session session) {
        super(session);
    }
    /**
     * 
     * @param moduleName
     * @return
     */
    public List<FieldParameter> get(String moduleName) {
        Criteria criteriaQuery = getSession().createCriteria(FieldParameter.class);
        criteriaQuery.add(Restrictions.eq("fieldParameterPK.moduleName", moduleName));
        criteriaQuery.add(Restrictions.eq("fieldParameterPK.IdField", Integer.parseInt(StatusDefinition.idMappingReport)));
        return criteriaQuery.list();
    }    
    /**
     * 
     * @param pk
     * @return
     */
    public FieldParameter get(FieldParameterPK pk) {
        return (FieldParameter) getSession().get(FieldParameter.class, pk);
    }    
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
