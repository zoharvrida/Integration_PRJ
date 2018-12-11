package bdsm.scheduler;


import java.io.File;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixLogPK;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import org.apache.commons.lang.time.DateUtils;


/**
*
* @author v00019372
*/
public abstract class FileWatcherWorker implements Runnable {
	private static Logger logger = Logger.getLogger(FileWatcherWorker.class);
	private static MyBdsmScheduler bdsmScheduler;
    /**
     * 
     */
    protected FolderWatcherListener parent;
    /**
     * 
     */
    protected File file;
    /**
     * 
     */
    protected String prependFileBackup;
    /**
     * 
     */
    protected Session session;
	
	static {
		bdsmScheduler = new MyBdsmScheduler(PropertyPersister.fileTimeout, PropertyPersister.mailSleep, 
				PropertyPersister.mailSleep, PropertyPersister.maxProcess);
		bdsmScheduler.setName("BDSMFW_Thread");
		bdsmScheduler.start();
	}
	
    /**
     * 
     * @param parent
     * @param file
     * @param prependFileBackup
     */
    public FileWatcherWorker(FolderWatcherListener parent, File file, String prependFileBackup) {
		this.parent = parent;
		this.file = file;
		this.prependFileBackup = prependFileBackup;
	}
	
    /**
     * 
     * @param force
     */
    public static void stopBDSMScheduler(boolean force) {
		bdsmScheduler.terminateThread(force);
	}
	
	
    /**
     * 
     * @param file
     * @param prependFileBackup
     * @return
     */
    public abstract boolean processFile(File file, String prependFileBackup); 
	
	
	@Override
	public void run() {
		logger.debug("FileWatcherWorker run");
		
		// Saving to FixLog
		if (this.parent.getStatus(file) == 2) {
			this.session = HibernateUtil.getSession();
			
			try {
				this.saveToFixLog(this.file);
			}
			finally {
				HibernateUtil.closeSession(this.session);
			}
		}
		
		boolean check = false;
		while (!check) {
			this.session = HibernateUtil.getSession();
			
			try {
				check = this.processFile(this.file, this.prependFileBackup);
				if (!check) {
					logger.warn("No BackThread for file " + this.file + " ");
					Thread.sleep(PropertyPersister.fileSleep);
				}
			} catch (InterruptedException ie) {
				logger.error(ie, ie);
				check = true;
			} finally {
				HibernateUtil.closeSession(this.session);
			}
		}
		
		if (this.parent.getStatus(file) == 2) {
			this.parent.setStatus(this.file, 3);
			logger.debug("file '" + this.file + "' already being processing by BackThread");
		}
	}

    /**
     * 
     * @param file
     */
    protected void saveToFixLog(File file) {
		String batchId = SchedulerUtil.generateUUID();
		FixLog fixLog = new FixLog();
		FixLogDao fixLogDao = new FixLogDao(this.session);
		try {
			fixLog.setFixLogPK(new FixLogPK(file.getName(), DateUtils.truncate(new java.util.Date(), java.util.Calendar.DATE)));
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		fixLog.setDtmReceive(SchedulerUtil.getTime());
		fixLog.getFixLogPK().setTypFix(StatusDefinition.IN);
		fixLog.getFixLogPK().setInboxId(batchId);
		fixLog.setFileSize((int) file.length());
		fixLog.setFlgProcess(StatusDefinition.REQUEST);
		Transaction transaction = this.session.beginTransaction();
		fixLogDao.insert(fixLog);
		transaction.commit();
	}

    /**
     * 
     * @param context
     * @param worker
     * @return
     */
    public BdsmBackThread assignToWorker(java.util.Map<String, Object> context, IBdsmWorker worker) {
		BdsmBackThread bts = bdsmScheduler.assign(context, worker);
        logger.info("FILE ASSIGMENT :" + bts.getName() + " with Content :" + context);
        return bts;
	}
	
}


// Extended BdsmScheduler
class MyBdsmScheduler extends BDSMScheduler2 {
	private boolean isFirst = true;
	private List<BdsmBackThread> backThreadsList;
	
	public MyBdsmScheduler(long timeout, long sleep, long btSleep, int maxProcess) {
		super(timeout, sleep, btSleep, maxProcess);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	protected void execute() {
		if (isFirst) {
			try {
				this.backThreadsList = (List<BdsmBackThread>) getFieldValue(this, "backThreads");
				for (int i=0; i<this.backThreadsList.size(); i++) {
					BdsmBackThread bt = this.backThreadsList.get(i);
					bt.setName("BDSMFW_BackThread_" + (i+1));
				}
			}
			catch (Exception e) {
				this.getLogger().error(e.getMessage(), e);
			}
			
			isFirst = false;
		}
	}
	
	public final void terminateThread(boolean force) {
		if (force)
			this.forceTerminate();
		else
			this.terminate();
	}
};