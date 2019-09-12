/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrCiCustmast;
import bdsm.rpt.model.FixMasterReport;
import bdsm.rpt.model.FixMasterReportPK;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class FixMasterReportDao extends BaseDao {
    
    public FixMasterReportDao(Session session) {
    	super(session);
    }
    
    public List list(String reportName,String idTemplate) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT B.idReport, B.reportName, B.idScheduler, B.remarks, B.namScheduler ");
        queryString.append("FROM FixMasterReport B ");
        queryString.append("WHERE B.idReport not in (");
        queryString.append("SELECT DISTINCT A.idmasterreport ");
        queryString.append("FROM xrefreporttemplate A ");
        queryString.append("WHERE A.idtemplate = '");
        queryString.append(idTemplate).append("') ");
        queryString.append("AND B.reportName LIKE '%");
        queryString.append(reportName).append("%'");
        queryString.append(" ORDER BY to_number(B.idReport)");
        //queryString.append("WHERE B.compositeId.idMasterReport = A.idReport )");
        
        //queryString.append(" order by reportName ");
        
        Query q = getSession().createSQLQuery(queryString.toString());
        getLogger().info("Cek Query Report = "+ q.toString());
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        //return getListReport(q.list());
        return q.list();
    }
    
    public FixMasterReport get(String idReport) {
        Criteria criteriaQuery = getSession().createCriteria(FixMasterReport.class);
        criteriaQuery.add(Restrictions.eq("compositeId.idReport", idReport));
        return (FixMasterReport)criteriaQuery.uniqueResult();
    }
    
    public FixMasterReport getByNamScheduler(String namScheduler) {
        Criteria criteriaQuery = getSession().createCriteria(FixMasterReport.class);
        criteriaQuery.add(Restrictions.eq("namScheduler", namScheduler));
        return (FixMasterReport)criteriaQuery.uniqueResult();
    }
    
    public FixMasterReport get(FixMasterReportPK idReport) {
        getLogger().info("ID REPORT :" + idReport);
        return (FixMasterReport) getSession().get(FixMasterReport.class, idReport);
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
        protected org.apache.log4j.Logger getLogger() {
        return org.apache.log4j.Logger.getLogger(this.getClass().getName());
    }
}
