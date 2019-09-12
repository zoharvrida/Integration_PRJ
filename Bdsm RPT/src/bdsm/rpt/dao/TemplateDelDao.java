
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsm.model.MasterTemplate;
import bdsm.rpt.model.TemplateDelComp;
import bdsm.rpt.model.XrefReportTemplate;
import bdsmhost.dao.BaseDao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class TemplateDelDao extends BaseDao {

    public TemplateDelDao(Session session) {
        super(session);
    }

    public List list(String namTemplate) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT A, B ");
        queryString.append("FROM MasterTemplate A, XrefReportTemplate B, FixMasterReport C ");
        queryString.append("WHERE A.idTemplate = B.compositeId.idTemplate ");
        queryString.append("AND B.compositeId.idMasterReport = C.compositeId.idReport ");
        queryString.append("AND A.namTemplate LIKE '%").append(namTemplate).append("%'");
        queryString.append(" order by namTemplate ");
        
        Query q = getSession().createQuery(queryString.toString());

        return getListTemplate(q.list());
        //return q.list();
    }

    public List list() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT A, B ");
        queryString.append("FROM MasterTemplate A, XrefReportTemplate B, FixMasterReport C ");
        queryString.append("WHERE A.idTemplate = B.compositeId.idTemplate ");
        queryString.append("AND B.compositeId.idMasterReport = C.compositeId.idReport ");
        //queryString.append("AND B.namTemplate LIKE ").append(" '%").append(namTemplate).append("%'");
        queryString.append(" order by namTemplate ");
        Query q = getSession().createQuery(queryString.toString());
        //return getListTemplate(q.list());

        return getListTemplate(q.list());
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

    private List getListTemplate(List listData) {
        MasterTemplate template;
        XrefReportTemplate xRef;
        TemplateDelComp xreFComp;
        Object[] objectJoin;
        
        List listUser = new ArrayList<TemplateDelComp>();

        for (int i = 0; i < listData.size(); i++) {
            objectJoin = (Object[]) listData.get(i);
            
            template = (MasterTemplate) objectJoin[0];
            xRef = (XrefReportTemplate) objectJoin[1];
            
            xreFComp = new TemplateDelComp();
            xreFComp.setCompositeId(xRef.getCompositeId());
            xreFComp.setNamTemplate(template.getNamTemplate());
            
            listUser.add(xreFComp);
        }
        return listUser;

    }
}
