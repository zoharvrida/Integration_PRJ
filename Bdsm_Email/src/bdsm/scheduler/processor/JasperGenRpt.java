/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.FillRptDao;
import bdsm.scheduler.dao.mappingScheduleDao;
import bdsm.scheduler.exception.GenRptException;
import bdsm.scheduler.model.MappingSchedule;
import bdsm.util.HibernateUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author v00013493
 */
public class JasperGenRpt {
	private static Map<String, String> lockMap = new java.util.HashMap<String, String>();
	private String reportId;
	private String reportName;
	private String reportFileName;
	private String reportFormat;
	private String dataSource = "";
	private Map reportParam;
	private Session session;
	private Session otherSession;
	private static final String reportDir = PropertyPersister.dirFixOut;
	private static final String reportDirTemp = PropertyPersister.reportDirTemp;
	private static final Logger logger = Logger.getLogger(JasperGenRpt.class);
	private boolean hasSub = false;
	protected Transaction transaction;

	public JasperGenRpt(Map context) {
		try {
			this.reportName = (String) context.get(MapKey.templateName);
		} catch (Exception e) {
			logger.info("NOT USING TEMPLATE NAME");
			this.reportName = (String) context.get(MapKey.reportFileName);
		}
		logger.debug("ReportPattern : " + this.reportName);
		this.reportId = (String) context.get(MapKey.reportId);
		logger.debug("ReportId : " + this.reportId);
		this.reportFileName = (String) context.get(MapKey.reportFileName);
		logger.debug("Report FileName : " + this.reportFileName);
		this.reportFormat = (String) context.get(MapKey.reportFormat);
		logger.debug("Report Format : " + this.reportFormat);
		this.reportParam = (Map) context.get(MapKey.reportParam);
		logger.debug("Report Param 1 : " + this.reportParam.get(MapKey.param1));
		logger.debug("Report Param 2 : " + this.reportParam.get(MapKey.param2));
		logger.debug("Report Param 3 : " + this.reportParam.get(MapKey.param3));
		logger.debug("Report Param 4 : " + this.reportParam.get(MapKey.param4));
		logger.debug("Report Param 5 : " + this.reportParam.get(MapKey.param5));
		logger.debug("Report Param 6 : " + this.reportParam.get(MapKey.param6));
		this.session = (Session) context.get(MapKey.session);
		try {
			this.dataSource = context.get(MapKey.dataSource).toString();
		} catch (Exception e) {
			this.dataSource = "";
		}
	}

	public void generateReport() {
		if (!lockMap.containsKey(this.reportId))
			lockMap.put(this.reportId, new String(this.reportId));
		
		String reportIdLock = lockMap.get(this.reportId);
		
		synchronized(reportIdLock) {
			logger.info("Preparing generate report");
			logger.debug("Report Template Directory :" + this.reportDirTemp);
			logger.debug("Report Result Directory :" + this.reportDir);
			
			try {
				logger.info("Compile report");
				JasperCompileManager.compileReportToFile(reportDirTemp + reportId + ".jrxml", reportDirTemp + reportId + ".jasper");

				File f2 = new File(reportDirTemp + reportId + "_SUB.jasper");
				if (!f2.exists()) {
					logger.info("Sub Report Has no Jasper, compile it...");
					File f = new File(reportDirTemp + reportId + "_SUB.jrxml");
					if (f.exists()) {
						logger.info("Report Has a Sub report, compile it...");
						hasSub = true;
						JasperCompileManager.compileReportToFile(reportDirTemp + reportId + "_SUB.jrxml", reportDirTemp + reportId + "_SUB.jasper");
					}
				}
				logger.info("Compile report success");
			} catch (JRException ex) {
				throw new GenRptException(ex.getMessage());
			}
			FillRptDao fillRptDao = null;
			if (!"".equalsIgnoreCase(dataSource)) {
				otherSession = HibernateUtil.getSession(dataSource);
				fillRptDao = new FillRptDao(otherSession);
			} else {
				fillRptDao = new FillRptDao(session);
			}
			fillRptDao.setReportDirTemp(reportDirTemp);
			fillRptDao.setReportId(reportId);
			fillRptDao.setReportParam(reportParam);
			fillRptDao.setReportFileName(reportFileName);
			try {
				File sourceFile = new File(reportDirTemp + reportFileName + ".jrprint");
				File sourceFileSub = null;
				logger.info("Try to generate report");
				fillRptDao.fillReport();
				if (hasSub) {
					logger.info("Report Has a Sub report, fill it...");
					fillRptDao.setReportId(reportId + "_SUB");
					fillRptDao.setReportFileName(reportFileName + "_SUB");
					sourceFileSub = new File(reportDirTemp + reportFileName + "_SUB.jrprint");
					fillRptDao.fillReport();
				}
				logger.info("Generate report done");
				logger.info("Trying to convert report");
				JasperPrint jasperPrint;
				JasperPrint jasperPrintSub;
				String reportOutput = reportDir + reportFileName;
				logger.info("Begin Convert JRPRINT to " + reportFormat);
				if (reportFormat.toLowerCase().equals("pdf")) {
					jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
					logger.info("Load JRPRINT Done");
					pdf(jasperPrint, reportOutput);
				} else if (reportFormat.toLowerCase().equals("xls")) {
					if (!hasSub) {
						xls(reportDirTemp + reportFileName + ".jrprint", reportOutput);
					} else {
						jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
						jasperPrintSub = (JasperPrint) JRLoader.loadObject(sourceFileSub);
						ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
						list.add(jasperPrint);
						list.add(jasperPrintSub);
						xls(list, reportOutput);
					}
				} else if (reportFormat.toLowerCase().equals("xlsx")) {
					xlsx(reportDirTemp + reportFileName + ".jrprint", reportOutput);
				} else if (reportFormat.toLowerCase().equals("docx")) {
					jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
					logger.info("Load JRPRINT Done");
					docx(jasperPrint, reportOutput);
				} else if (reportFormat.toLowerCase().equals("csv")) {
					jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
					logger.info("Load JRPRINT Done");
					csv(jasperPrint, reportOutput);
				} else if (reportFormat.toLowerCase().equals("html")) {
					jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
					logger.info("Load JRPRINT Done");
					html(jasperPrint, reportOutput);
				} else {
					throw new GenRptException("Output format not supported");
				}
				logger.info("Convert report success");
			} catch (Exception ex) {
				logger.error(ex, ex);
				mappingScheduleDao mDAO = new mappingScheduleDao(session);
				MappingSchedule schTimer = mDAO.get(reportName,"I");
				if (checkJasper(reportName)) {
					// jsprint already generated    
					if (schTimer.getDateTo() != null) {
						schTimer.setDateFrom(schTimer.getDateTo());
						schTimer.setDateTo(null);
						try {
							mDAO.update(schTimer);
							this.transaction.commit();
							session.flush();
							this.transaction = session.beginTransaction();
						} catch (Exception hibernateException) {
							logger.error("(AFTER JASPER) FAILED TO CLEAN HI :" + hibernateException);
						}
					}
				} else {
					if (schTimer.getDateTo() != null) {
						schTimer.setDateTo(null);
						try {
							mDAO.update(schTimer);
							this.transaction.commit();
							session.flush();
							this.transaction = session.beginTransaction();
						} catch (Exception hibernateException) {
							logger.error("FAILED TO CLEAN HI :" + hibernateException);
						}
					}
				}
				throw new GenRptException(ex);
			} finally {
				if (!"".equalsIgnoreCase(dataSource)) {
					HibernateUtil.closeSession(otherSession, dataSource);
				}
			}
		}
		lockMap.remove(this.reportId);
	}

	private void pdf(JasperPrint jasperPrint, String reportOutput) throws JRException {
		JasperExportManager.exportReportToPdfFile(jasperPrint, reportOutput);
	}

	private void xls(JasperPrint jasperPrint, String reportOutput) throws JRException {
		JRXlsExporter xlsExporter = new JRXlsExporter();

		xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportOutput);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		xlsExporter.exportReport();
	}

	private void xls(ArrayList<JasperPrint> list, String reportOutput) throws JRException {
		JRXlsExporter xlsExporter = new JRXlsExporter();

		xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, list);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportOutput);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		xlsExporter.exportReport();
	}

	private void xls(String jasperPrint, String reportOutput) throws JRException {
		JRXlsExporter xlsExporter = new JRXlsExporter();

		//xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		xlsExporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, jasperPrint);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportOutput);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		xlsExporter.exportReport();
	}

	private void xlsx(JasperPrint jasperPrint, String reportOutput) throws JRException {
		JRXlsxExporter xlsExporter = new JRXlsxExporter();

		xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportOutput);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);

		xlsExporter.exportReport();
	}

	private void xlsx(String jasperPrint, String reportOutput) throws JRException {
		JRXlsxExporter xlsExporter = new JRXlsxExporter();

		//xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		xlsExporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, jasperPrint);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportOutput);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);

		xlsExporter.exportReport();
	}

	private void docx(JasperPrint jasperPrint, String reportOutput) throws JRException {
		JRDocxExporter docxExporter = new JRDocxExporter();

		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportOutput);

		docxExporter.exportReport();
	}

	private void csv(JasperPrint jasperPrint, String reportOutput) throws JRException {
		JRCsvExporter csvExporter = new JRCsvExporter();

		csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		csvExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportOutput);

		csvExporter.exportReport();
	}

	private void html(JasperPrint jasperPrint, String reportOutput) throws JRException {
		JasperExportManager.exportReportToHtmlFile(jasperPrint, reportOutput);
	}
	private boolean checkJasper(String filename) {
		boolean exists = new File(filename).exists();
		return exists;
	}
}
