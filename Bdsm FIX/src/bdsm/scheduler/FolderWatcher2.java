package bdsm.scheduler;


import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import bdsm.scheduler.dao.FixFolderWatcherDao;
import bdsm.scheduler.model.FixFolderWatcher;
import bdsm.util.HibernateUtil;


/**
*
* @author v00019372
*/
public class FolderWatcher2 {
    /**
     * 
     */
    protected FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor();
    /**
     * 
     */
    protected Map<File, Map<String, Object>> folderWatcherDataMap;
	
	private static Logger LOGGER = Logger.getLogger(FolderWatcher2.class);
	
		
    /**
     * 
     */
    public void startWatcher() {
		LOGGER.info("[START] Observing Folder");
		long sleep = PropertyPersister.fileTimeout;
		
		// Getting folder list being watched
		List<FixFolderWatcher> fixFolderWatcherList = this.getFixFolderWatcherList();
		this.folderWatcherDataMap = new HashMap<File, Map<String, Object>>(fixFolderWatcherList.size());
		
		// Preparing
		Map<String, File[]> backupFilesMap = this.renameExistingFilesForBackup(fixFolderWatcherList);
		this.prepareWatching(fixFolderWatcherList);
		
		// Start watching
		try {
			this.fileAlterationMonitor.start();
			Class.forName(FileWatcherWorker.class.getName()); // Load class FileWatcherWorker, to initiate BDSMScheduler
			Thread.sleep(sleep);
			this.renameBackupFilesToOriginal(backupFilesMap);
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
	}
	
    /**
     * 
     */
    public void stopWatcher() {
		this.stopWatcher(false);
	}
	
    /**
     * 
     * @param force
     */
    public void stopWatcher(boolean force) {
		try {
			this.fileAlterationMonitor.stop();
			FileWatcherWorker.stopBDSMScheduler(force);
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
		LOGGER.info("[STOP] Observing Folder");
	}
	
	
	private List<FixFolderWatcher> getFixFolderWatcherList() {
		Session hibernateSession = HibernateUtil.getSession();
		
		FixFolderWatcherDao ffwDAO = new FixFolderWatcherDao(hibernateSession);
		List<FixFolderWatcher> list = ffwDAO.list();
		
		HibernateUtil.closeSession(hibernateSession);
		return list;
	}
	
	private Map<String, File[]> renameExistingFilesForBackup(List<FixFolderWatcher> fixFolderWatcherList) {
		Map<String, File[]> result = null;
		String rootPath;
		File[] files;
		File newFile;
		
		FilenameFilter filter = new FolderWatcherFilter();
		for (int i=0; i<fixFolderWatcherList.size(); i++) {
			rootPath = ((FixFolderWatcher) fixFolderWatcherList.get(i)).getCompositeId().getFolderPath();
			File rootFolder = new File(rootPath);
			if (rootFolder.exists()) {
				files = (new File(rootPath)).listFiles(filter);
				
				for (int c=0; c<files.length; c++) {
					File f = files[c];
					newFile = new File(f.getParent(), "~" + f.getName());
					f.renameTo(newFile);
					files[c] = newFile;
				}
			
				if (result == null) result = new HashMap<String, File[]>();
				result.put(rootPath, files);
			}
			else {
				LOGGER.warn("Folder " + rootPath + " doesn't exist!!!");
				//fixFolderWatcherList.remove(i--);
			}
		}
		
		return result;
	}
	
	private void renameBackupFilesToOriginal(Map<String, File[]> backupFilesMap) {
		File[] files = null;
		
		java.util.Iterator<String> it = backupFilesMap.keySet().iterator();
		while (it.hasNext()) {
			files = backupFilesMap.get(it.next());
			for (File f : files) {
				f.renameTo(new File(f.getParent(), f.getName().substring(1)));
			}
		}
	}
	
	private void prepareWatching(List<FixFolderWatcher> fixFolderWatcherList) {
		FileAlterationObserver fao = null;
		FolderWatcherListener folderWatcherListener = new FolderWatcherListener();
		Map<String, Object> dataMap = null;
		
		for (int i=0; i<fixFolderWatcherList.size(); i++) {
			FixFolderWatcher ffw = fixFolderWatcherList.get(i);
			if ("A".equals(ffw.getFlgStat()) == false)
				continue;
			dataMap = new HashMap<String, Object>();
			dataMap.put("prependFileBackup", ffw.getCompositeId().getPrependFileBackup());
			this.folderWatcherDataMap.put(new File(ffw.getCompositeId().getFolderPath()), dataMap);
			
			// Filter: file only (not directory) and skip file(s) which have ~(tilde) prefixed filename and .temp postfixed filename
			FileFilter fileFilter = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), 
					FileFilterUtils.notFileFilter(FileFilterUtils.prefixFileFilter("~")),
					FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".temp"))
			);
			// Observer
			fao = new FileAlterationObserver(ffw.getCompositeId().getFolderPath(), fileFilter);
			fao.addListener(folderWatcherListener);
			this.fileAlterationMonitor.addObserver(fao);
		}
		
		folderWatcherListener.setFolderWatcherDataMap(this.folderWatcherDataMap);
	}
	
	// Filter: 
	// - file only (not directory) 
	// - skip file(s) which have ~(tilde) prefixed filename 
	// - skip file(s) with .temp postfix filename
	private class FolderWatcherFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			return ((!new File(dir, name).isDirectory()) && (!name.startsWith("~")) && (!name.endsWith(".temp")));
		}
	}
	
}
