package bdsm.scheduler;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;

import bdsm.scheduler.email.FileInWatcherWorker;


public class FolderWatcherListener implements FileAlterationListener {
	private Map<File, Object[]> ready;
	private Map<File, Long> check;
	private Logger logger = Logger.getLogger(this.getClass());
	private Map<File, Map<String, Object>> folderWatcherDataMap;
	
	
	public FolderWatcherListener() {
		this.ready = new HashMap<File, Object[]>();
		this.check = new HashMap<File, Long>();
	}
	
	public void setFolderWatcherDataMap(Map<File, Map<String, Object>> folderWatcherDataMap) {
		this.folderWatcherDataMap = folderWatcherDataMap;
	}
	
	public Integer getStatus(File file) {
		Object[] arr = this.ready.get(file);
		return ((arr != null)? (Integer) arr[1]: -1);
	}
	
	public synchronized void setStatus(File file, Integer status) {
		Object[] arr = this.ready.get(file);
		arr[1] = status;
	}
	
	
	@Override
	public void onStart(FileAlterationObserver observer) {
		this.logger.info("Start Observing: " + observer.getDirectory());
	}

	@Override
	public void onFileCreate(File file) {
		this.logger.info("File Created: " + file.getName());
		this.check.put(file, Long.valueOf(file.length()));
	}

	@Override
	public void onFileChange(File file) {
		this.logger.info("File Updated: " + file.getName());
		this.check.put(file, Long.valueOf(file.length()));
	}

	@Override
	public void onFileDelete(File file) {
		this.logger.info("File Deleted: " + file.getName());
		this.check.remove(file);
		this.ready.remove(file);
	}
	
	@Override
	public void onDirectoryCreate(File directory) {}

	@Override
	public void onDirectoryChange(File directory) {}

	@Override
	public void onDirectoryDelete(File directory) {}

	@Override
	public void onStop(FileAlterationObserver observer) {
		Iterator<File> iter;
		Set<File> temp = new HashSet<File>(); 
		File file;
		Object[] arr;
		
		// updated file
		iter = this.ready.keySet().iterator();
		while (iter.hasNext()) {
			file = iter.next();
			if (this.check.get(file) != null) {
				// update the size of file
				arr = this.ready.get(file);
				arr[0] = this.check.get(file);
				
				this.check.remove(file);
			}
			else
				temp.add(file);
		}
		
		// new file
		iter = this.check.keySet().iterator();
		while (iter.hasNext()) {
			file = iter.next();
			// 0 = not yet processed / still transfering; 1 = file inactived scheduler; 2 = processing; 3 = processed
			this.ready.put(file, new Object[]{this.check.get(file), 0});  
		}
		
		int i = 0;
		
		// transfer already finished
		iter = temp.iterator();
		while (iter.hasNext()) {
			file = iter.next();
			if (observer.getDirectory().equals(file.getParentFile()) == false)
				continue;
			
			this.logger.debug("Monitoring file: " + file.getPath());
			arr = this.ready.get(file);
			if (((Integer) arr[1]).intValue() < 2) {
				if (Integer.valueOf(0).equals(arr[1]))
					arr[1] = 2;
				
				Map<String, Object> dataMap = this.folderWatcherDataMap.get(new File(file.getParent()));
				Thread thread = new Thread(new FileInWatcherWorker(this, file, dataMap.get("prependFileBackup").toString()), "FileAssignerThread-" + ++i);
				thread.start();
			}
		}
		
		this.check.clear();
		this.logger.info("Stop Observing: " + observer.getDirectory());
	}

}
