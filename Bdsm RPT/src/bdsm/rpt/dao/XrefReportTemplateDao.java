/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsm.rpt.model.XrefReportTemplate;
import bdsm.rpt.model.XrefReportTemplatePK;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class XrefReportTemplateDao extends BaseDao {
    
    public XrefReportTemplateDao(Session session) {
    super(session);
    }

    public List list(String idTemplate) {
        Criteria criteriaQuery = getSession().createCriteria(XrefReportTemplate.class);
        criteriaQuery.add(Restrictions.eq("compositeId.idTemplate",idTemplate ));
        criteriaQuery.addOrder(Order.asc("compositeId.idMasterReport"));
        return criteriaQuery.list();
    }
    public XrefReportTemplate get(XrefReportTemplatePK pk) {
       return (XrefReportTemplate) getSession().get(XrefReportTemplate.class, pk);
    }
     @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((XrefReportTemplate) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((XrefReportTemplate) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {

        getSession().delete((XrefReportTemplate) model);
        return true;
    }

}
