package bdsm.scheduler.processor;


import java.io.CharArrayWriter;
import java.io.FileReader;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import bdsm.model.TPINHistory;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixQXtract;
import bdsm.service.FCRCustomerCardService;
import bdsm.service.FCR_CMPreembossCardInventoryService;
import bdsm.service.TPINService;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.FCRCustomerCardMasterDAO;
import bdsmhost.dao.FCR_CMPreembossCardInventoryDAO;
import bdsmhost.dao.TPINDao;
import bdsmhost.dao.TPINStatusMasterDAO;
import bdsmhost.interceptor.AbstractTimeHistoryInterceptor;


public class TPINWorker extends BaseProcessor {
	private static Logger logger = Logger.getLogger(TPINWorker.class);
	private Session localSession;
	
	
	public TPINWorker(Map<String, Object> context) {
		super(context);
	}
	

	@Override
	protected boolean doExecute() throws Exception {
		logger.info("[BEGIN] TPINWorker Execution");
		
		String typeFix = (String) this.context.get(MapKey.typeFix);
		String status = (String) this.context.get(MapKey.status);
		String batchNo = (String) this.context.get(MapKey.batchNo);
		String fileFullPath = (String) this.context.get(MapKey.param5);
		
		if ((StatusDefinition.IN.equals(typeFix)) && (StatusDefinition.UNAUTHORIZED.equals(status))) {
			TPINDao tpinDAO = new TPINDao(this.session);
			tpinDAO.setInterceptor(new AbstractTimeHistoryInterceptor<TPINHistory>() {}); // inject interceptor
			
			FCRCustomerCardService custCardService = new FCRCustomerCardService();
			custCardService.setFCRCustomerCardMasterDAO(new FCRCustomerCardMasterDAO(this.session)); // inject DAO
			
			FCR_CMPreembossCardInventoryService cardInventoryService = new FCR_CMPreembossCardInventoryService();
			cardInventoryService.setFCR_CMPreembossCardInventoryDAO(new FCR_CMPreembossCardInventoryDAO(this.session)); // inject DAO
			
			TPINService tpinService = new TPINService();
			tpinService.setTpinDAO(tpinDAO); // inject DAO
			tpinService.setTpinStatusMasterDAO(new TPINStatusMasterDAO(this.session));
			tpinService.setFCRCustomerCardService(custCardService);
			tpinService.setFCR_CMPreembossCardInventoryService(cardInventoryService);
			
			java.io.File file = new java.io.File(fileFullPath);
			FileReader fr = new FileReader(file);
			CharArrayWriter caw = new CharArrayWriter();
			char[] buffer = new char[1024];
			int iRead;
			
			try {
				while((iRead = fr.read(buffer)) != -1)
					caw.write(buffer, 0, iRead);
				
				buffer = caw.toCharArray();
			}
			finally {
				if (fr != null) {
					fr.close(); fr = null;
				}
				
				if (caw !=null) {
					caw.close(); caw = null;
				}
				
				doGarbageCollection();
			}
			
			int iDetail = tpinService.importTPIN(buffer, file.getName());
			if (iDetail == 0) { // If zero detail file (only EOF line exist)
				FixClassConfigDao fixClassConfigDAO = new FixClassConfigDao(this.session);
				FixQXtractDao fixQXtractDAO = new FixQXtractDao(this.session);
				int idSchedulerXtract = fixClassConfigDAO.get(this.getClass().getName(), (String)this.context.get(MapKey.processSource), status).get(0).getIdScheduler();
				
				FixQXtract fqx = new FixQXtract();
				fqx.setIdScheduler(idSchedulerXtract);
				fqx.setFlgProcess(StatusDefinition.REQUEST);
				fqx.setDtmRequest(SchedulerUtil.getTime());
				fqx.setParam1(batchNo);
				
				fixQXtractDAO.insert(fqx);
			}
			
			this.localSession = tpinDAO.getLocalSession();
			logger.info("[END] TPINWorker Execution");
		}
		else if (StatusDefinition.XTRACT.equals(typeFix)) {
			FixLogDao fixLogDAO = new FixLogDao(this.session);
			FixLog fixLog = fixLogDAO.get((String) this.context.get(MapKey.param1)); // FixLog inboxid
			
			if (fixLog != null) {
				logger.debug("Remove fixLog inboxId: " + this.context.get(MapKey.param1));
				fixLogDAO.delete(fixLog);
			}
		}
		
		return true;
	}
	
	private static void doGarbageCollection() { 
		// If Windows system, then do garbage collection, in order to delete file in folder fixinproc
		if (java.io.File.separatorChar == '\\')
			System.gc();
	}
	
	
	
	@Override
	protected Session getLocalSession() {
		return this.localSession;
	}
	
	
}
