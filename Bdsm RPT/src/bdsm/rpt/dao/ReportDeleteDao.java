/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class ReportDeleteDao extends BaseDao {
  public ReportDeleteDao(Session session) {
        super(session);
    }

    public List list(String reportName,String idTemplate) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT A.idReport, A.reportName, A.idScheduler, A.remarks, A.namScheduler ,A.flg_Auth ");
        queryString.append("FROM FixMasterReport A ");
        queryString.append("WHERE EXISTS (SELECT 'x' ");
        queryString.append("FROM XrefReportTemplate B ");
        queryString.append("WHERE B.idMasterReport = A.idReport ");
        queryString.append("AND B.idTemplate LIKE '");
        queryString.append(idTemplate).append("' ");
        queryString.append("AND A.reportName LIKE '%");
        queryString.append(reportName).append("%')");       
        queryString.append(" ORDER BY to_number(A.idReport)");
        Query q = getSession().createSQLQuery(queryString.toString());
        getLogger().info("Cek Query Report = "+ q.toString());
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return q.list();
    }
    protected org.apache.log4j.Logger getLogger() {
        return org.apache.log4j.Logger.getLogger(this.getClass().getName());
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
