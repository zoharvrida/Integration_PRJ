/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsm.rpt.model.FixReportParam;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class FixReportParamDao extends BaseDao {
    public FixReportParamDao(Session session) {
        super(session);
    }
    
    public List list(String idReport) {
        Criteria criteriaQuery = getSession().createCriteria(FixReportParam.class);
        criteriaQuery.add(Restrictions.eq("compositeId.idReport",idReport));
        criteriaQuery.addOrder(Order.asc("compositeId.seq"));
        List<FixReportParam> result = criteriaQuery.list();
        try {
            for (FixReportParam fixReportParam : result) {
                if (fixReportParam.getCustomQuery() != null && fixReportParam.getCustomQuery().length() > 0) {
                    String tempQuery = fixReportParam.getCustomQuery();
                    Query query = getSession().createSQLQuery(tempQuery);
                    try {
                        fixReportParam.setCustomQuery((String) query.uniqueResult());
                    } catch (Exception e) {
                        fixReportParam.setCustomQuery(null);
                    }
                }
            }
        } catch (Exception e) {
            
        }
        return result;
    }
    
    public FixReportParam get(String idReport) {
        return (FixReportParam) getSession().get(FixReportParam.class, idReport);
    }
 
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
        return true;
    }
}
