/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.MappingSchedule;
import bdsmhost.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class mappingScheduleDao extends BaseDao {
    /**
     * 
     * @param session
     */
    public mappingScheduleDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param moduleName
     * @param Status
     * @return
     */
    public MappingSchedule get(String moduleName, String Status) {
        Criteria criteriaQuery = getSession().createCriteria(MappingSchedule.class);
        criteriaQuery.add(Restrictions.eq("ModuleName", moduleName));
        criteriaQuery.add(Restrictions.eq("Status", Status));
        return (MappingSchedule)criteriaQuery.uniqueResult();
    }
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
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
