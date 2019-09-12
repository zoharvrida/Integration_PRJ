package bdsm.scheduler.processor;


import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import org.apache.commons.lang.StringUtils;


/**
 * @author v00019372
 */
public class SknInwardCreditSTPKReportWorker extends BaseProcessor implements org.hibernate.jdbc.Work {
	private String type;
	private String batchNoSTPK;
	private Map<String, Object> threadObject;
	
	
	public SknInwardCreditSTPKReportWorker(Map<String, ? extends Object> context) {
		super(context);
	}
	
	@Override
	protected boolean doExecute() throws Exception {
		FixSchedulerXtractDao fsxDAO = new FixSchedulerXtractDao(this.session);
		FixQXtractDao fqxDAO = new FixQXtractDao(this.session);
		
		this.type = (String) this.context.get(MapKey.param1);
		String reportFilename = (this.context.get(MapKey.reportFileName) != null)? 
				this.context.get(MapKey.reportFileName).toString(): 
				FilenameUtils.getName(this.context.get(MapKey.param5).toString())
		;
		String reportFilenameSTPK = FilenameUtils.getBaseName(reportFilename).replaceAll("\\d{6}-\\d{6}", "") + ".hit";
		
		this.session.doWork(this);
		if (this.batchNoSTPK == null)
			return true;
		
		
		//addded amex ncard double sftp
		FixSchedulerXtract fsx = fsxDAO.get(StringUtils.isBlank(this.type) ? "BDSM_STPK_REPORT" :"BDSM_STPK_REPORT_AMEX");
		FixQXtract model = new FixQXtract();
		model.setIdScheduler(fsx.getFixSchedulerPK().getIdScheduler());
		model.setDtmRequest(new Timestamp((new Date()).getTime()));
		model.setFlgProcess(StatusDefinition.REQUEST);
		model.setParam5(reportFilename);
		model.setParam6(this.batchNoSTPK);
		fqxDAO.insert(model);
		
		this.getLogger().info("Calling STPK report generation");
		
		this.tx.commit();
		this.tx = this.session.beginTransaction();
		
		FixQXtract fqx = fqxDAO.get(model.getIdScheduler(), this.batchNoSTPK);
		int loop = 0;
		do {
			if (loop > 0) Thread.sleep(30000); // 30 second
			this.session.refresh(fqx);
			loop++;
		}
		while ((loop < 15) && (StatusDefinition.DONE.equals(fqx.getFlgProcess()) == false));
		
		this.getLogger().info("STPK report generation status: " + fqx.getFlgProcess());
		
		if (StatusDefinition.DONE.equals(fqx.getFlgProcess())) {
			File originalFile = new File(PropertyPersister.dirFixOut, reportFilename);
			File STPKFile = new File(PropertyPersister.dirFixOut, reportFilenameSTPK);
			
			// make copy file
			FileUtils.copyFile(originalFile, STPKFile);
			
			this.threadObject = new java.util.HashMap<String, Object>(0);
			this.threadObject.put("fixSchedulerXtract", fsx);
			this.threadObject.put("STPKFile", STPKFile);
			this.threadObject.put("parentThread", Thread.currentThread().getName());
			
			// SFTP with new Thread
			new Thread() {
				Logger LOGGER = Logger.getLogger(this.getClass());
				
				@Override
				public void run() {
					Session session = HibernateUtil.getSession();
					Transaction tx = session.beginTransaction();
					FixLogDao fixLogDAO = new FixLogDao(session);
					
					FixSchedulerXtract fsx = (FixSchedulerXtract) threadObject.get("fixSchedulerXtract");
					File sourceFile = (File) threadObject.get("STPKFile");
					String sourceFilePath = sourceFile.getPath();
					FixLog fixLog = null;
					
					try {
						LOGGER.info("parent Thread: " + threadObject.get("parentThread"));
						LOGGER.info("Initiate SFTP Transfer");
						LOGGER.info("File being send: " + sourceFilePath);
						LOGGER.info("Initiate SFTP Transfer Done, Begin Logging SFTP Transfer to FixLog");
						
						fixLog = FileUtil.sftpToFCR(
								fsx.getSftpIP(), 
								fsx.getSftpPort(),
								fsx.getSftpUserId(),
								fsx.getSftpPass(),
								fsx.getSftpKeyPath(), 
								sourceFilePath,
								fsx.getSftpDestPath(),
								fsx.getFixSchedulerPK().getIdScheduler().intValue()
						);
						
						LOGGER.info("SFTP Transfer Done!");
					}
					catch (Exception ex) {
						LOGGER.info("SFTP Transfer Error!!!", ex);
						fixLog = new FixLog();
						fixLog.getFixLogPK().setFileName(sourceFilePath);
						fixLog.getFixLogPK().setDtPost(DateUtils.truncate(new Date(), java.util.Calendar.DATE));
						fixLog.getFixLogPK().setTypFix(StatusDefinition.XTRACT);
						fixLog.setFileSize((int) sourceFile.length());
						fixLog.setIdScheduler(fsx.getFixSchedulerPK().getIdScheduler().intValue());
			            fixLog.setDtmStartProcess(SchedulerUtil.getTime());
			            fixLog.setFlgProcess(StatusDefinition.ERROR);
						fixLog.setReason(ex.getMessage());
					}
					finally {
						fixLog.getFixLogPK().setInboxId(batchNoSTPK);
						fixLogDAO.insert(fixLog);
						if (tx != null) tx.commit();
						if (session != null) HibernateUtil.closeSession(session);
					}
				}
			};
		}
		
		return true;
	}
	
	@Override
	public void execute(Connection connection) throws SQLException {
		CallableStatement cs = connection.prepareCall("{ ? = call PKG_SKN_STPK.REPT_AMEX_NCARD(?) }");
		cs.registerOutParameter(1, java.sql.Types.VARCHAR);
		cs.setString(2, this.type);
		
		cs.execute();
		this.batchNoSTPK = cs.getString(1);
	}

}
