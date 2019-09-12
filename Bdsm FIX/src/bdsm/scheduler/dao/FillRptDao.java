/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.exception.GenRptException;
import bdsmhost.dao.BaseDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00013493
 */
public class FillRptDao extends BaseDao implements Work {

    private String reportId;
    private String reportDirTemp;
    private String reportFileName;
    private Map reportParam;

    /**
     * 
     * @param session
     */
    public FillRptDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param conn
     * @throws SQLException
     */
    @Override
    public void execute(Connection conn) throws SQLException {
        JRSwapFileVirtualizer virtualizer = null;
        virtualizer = new JRSwapFileVirtualizer(3, new JRSwapFile(reportDirTemp, 2048, 1024), false);
        reportParam.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
        try {
            JasperFillManager.fillReportToFile(reportDirTemp + reportId + ".jasper", reportDirTemp + reportFileName + ".jrprint", reportParam, conn);
        } catch (JRException ex) {
            throw new GenRptException(ex);
        }
    }

    /**
     * 
     * @throws SQLException
     * @throws GenRptException
     */
    public void fillReport() throws SQLException, GenRptException {
        Session session = getSession();
        session.doWork(this);
    }

    /**
     * 
     * @param reportId
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * 
     * @param reportDirTemp
     */
    public void setReportDirTemp(String reportDirTemp) {
        this.reportDirTemp = reportDirTemp;
    }

    /**
     * 
     * @param reportParam
     */
    public void setReportParam(Map reportParam) {
        this.reportParam = reportParam;
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

    /**
     * @param reportFileName the reportFileName to set
     */
    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }
}
