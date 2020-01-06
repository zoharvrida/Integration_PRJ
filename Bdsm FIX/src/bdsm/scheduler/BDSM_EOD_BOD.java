/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.fcr.model.BaBankMast;
import bdsm.fcr.model.ExtEventData;
import bdsm.scheduler.dao.EOD_BOD_DatesDAO;
import bdsm.scheduler.dao.EOD_BOD_ProcMastDAO;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.EOD_BOD_Dates;
import bdsm.scheduler.model.EOD_BOD_ProcMast;
import bdsm.scheduler.model.FixQXtract;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.ExtEventDataDAO;


/**
 * 
 * @author v00019372
 */
public class BDSM_EOD_BOD extends BDSMScheduler2 {
	private static final Logger LOGGER = Logger.getLogger(BDSM_EOD_BOD.class);
	private static final Integer ID_SCHDLR_XTRCT = PropertyPersister.EOD_BOD_SchedulerXtractID;
	private static final String ADMIN_EMAIL = PropertyPersister.adminEmail;
	private ExtEventDataDAO eventDAO;
	private BaBankMastDAO bankMastDAO;
	private EOD_BOD_DatesDAO datesDAO;
	private EOD_BOD_ProcMastDAO procMastDAO;
	private FixQXtractDao fixQDAO;
	private ExtEventData event;
	private boolean isNowPreEOD, isNowPostBOD;
	private int cutOffDate, completedBODDate;
	private Date datLastProc, datProc, datNextProc;
	
	
    /**
     * 
     * @param timeout
     * @param sleep
     */
    public BDSM_EOD_BOD(long timeout, long sleep) {
		super(timeout, sleep, 0, 0);
		
		this.eventDAO = new ExtEventDataDAO(null);
		this.bankMastDAO = new BaBankMastDAO(null);
		this.datesDAO = new EOD_BOD_DatesDAO(null);
		this.procMastDAO = new EOD_BOD_ProcMastDAO(null);
		this.fixQDAO = new FixQXtractDao(null);
	}
	
	
    /**
     * 
     */
    @Override
	protected void execute() {
		Session session = null;
		try {
            try {
                LOGGER.info("FIX 2 EOD:" + System.getProperty("server.Name"));
                LOGGER.info("CUSTOM EOD:" + System.getenv("server.Name"));
            } catch (Exception e) {
                LOGGER.info("ENVIRONMENT VARIABLE DOESN'T EXIST : EOD");
            }
			session = HibernateUtil.getSession();
			this.eventDAO.setSession(session);
			this.bankMastDAO.setSession(session);
			this.datesDAO.setSession(session);
			this.fixQDAO.setSession(session);
			
			
			if (this.isNowPreEOD == false) {
				if (this.isPreEOD()) {
					/* pre EOD */
					this.isNowPreEOD = true;
					this.doPreEOD(session);
				}
			}
			else {
				/* check Finished pre EOD */
				if (this.checkPreEODFinish(session))
					this.isNowPreEOD = false;
			}
			
			
			if (this.isNowPostBOD == false) {
				if (this.isPostBOD()) {
					/* post BOD */
					this.isNowPostBOD = true;
					this.doPostBOD(session);
				}
			}
			else {
				/* check Finished post BOD */
				if (this.checkPostBODFinish(session))
					this.isNowPostBOD = false;
			}
		}
		catch (Exception e) {
			LOGGER.error("Error Execute pre EOD Check / post BOD Check");
			LOGGER.error(e, e);
		}
		finally {
			if (session != null)
				HibernateUtil.closeSession(session);
		}
	}
	
	private boolean isPreEOD() {
		boolean result = false;
		
		this.event = eventDAO.get(1);
		if (this.event != null) {
			if (this.event.getPostingDate() > this.cutOffDate) {
				BaBankMast bankMast = this.bankMastDAO.get();
				EOD_BOD_Dates dates = this.datesDAO.get();
				populateBankDate(bankMast);
				
				if(dates == null)
					dates = new EOD_BOD_Dates();
				if ((StatusDefinition.DONE.equals(dates.getPreStatus()))
						&& (DateUtils.isSameDay(this.datProc, dates.getPreNextDatProcess()))
						&& (this.cutOffDate > 0))
					result = true;
			}
			this.cutOffDate = this.event.getPostingDate();
		}
				
		return result;
	}
	
	private boolean isPostBOD() {
		boolean result = false;
		
		this.event = eventDAO.get(2);
		if (this.event != null) {
			if (this.event.getPostingDate() > this.completedBODDate) {
				BaBankMast bankMast = this.bankMastDAO.get();
				EOD_BOD_Dates dates = this.datesDAO.get();
				populateBankDate(bankMast);
				
				if(dates == null)
					dates = new EOD_BOD_Dates();
				if ((StatusDefinition.DONE.equals(dates.getPostStatus()))
						&& (DateUtils.isSameDay(this.datProc, dates.getPostNextDatProcess()))
						&& (this.completedBODDate > 0))
					result = true;
			}
			this.completedBODDate = this.event.getPostingDate();
		}
		
		return result;
	}
	
	
	private void doPreEOD(Session session) {
		this.do_EOD_BOD(session, 1);
	}
	
	private void doPostBOD(Session session) {
		this.do_EOD_BOD(session, 2);
	}
	
	private void do_EOD_BOD(Session session, int type) {
		Transaction trx = session.beginTransaction();
		
		try {
			EOD_BOD_Dates dates = this.datesDAO.get();
			if (type == 1) {
				LOGGER.info("Pre EOD: " + this.event.getPostingDate());
				dates.setPreStatus(StatusDefinition.PROCESS);
			}
			else {
				LOGGER.info("Post BOD: " + this.event.getPostingDate());
				dates.setPostStatus(StatusDefinition.PROCESS);
			}
			this.procMastDAO.setSession(session);
			this.procMastDAO.reset_EOD_BOD_Status(type);
			trx.commit();
			
			/* Get First parent ProcMast per Module */
			List<EOD_BOD_ProcMast> requestList = this.procMastDAO.listStartingRequest(type);
			Map<String, EOD_BOD_ProcMast> requestMap = new java.util.LinkedHashMap<String, EOD_BOD_ProcMast>();
			EOD_BOD_ProcMast procMast;
			
			for (int i=0; i<requestList.size(); i++) {
				procMast = requestList.get(i);
				if (requestMap.containsKey(procMast.getModuleName()) == false)
					requestMap.put(procMast.getModuleName(), procMast);
			}
			requestList.clear();
			
			
			/* Register to FixQXtract per Module */
			trx = session.beginTransaction();
			FixQXtract fixQ;
			Iterator<String> itModuleName = requestMap.keySet().iterator();
			while (itModuleName.hasNext()) {
				procMast = requestMap.get(itModuleName.next());
				fixQ = new FixQXtract();
				fixQ.setIdScheduler(ID_SCHDLR_XTRCT);
				fixQ.setParam1(String.valueOf(procMast.getProcId()));
				fixQ.setParam2(DateUtility.DATE_FORMAT_YYYYMMDD.format(this.datProc));
				fixQ.setParam3(ADMIN_EMAIL);
				fixQ.setFlgProcess(StatusDefinition.REQUEST);
				fixQ.setDtmRequest(SchedulerUtil.getTime());
				
				LOGGER.info("Registering " + (type==1? "Pre EOD": "Post BOD" ) + " Parent Process: [" + procMast.getModuleName() + "]#" 
						+ procMast.getProcId() + ": " + procMast.getProcedureName());
				this.fixQDAO.insert(fixQ);
			}
			trx.commit();
		}
		catch(Exception ex) {
			LOGGER.error(ex, ex);
			trx.rollback();
		}
	}
	
	
	private boolean checkPreEODFinish(Session session) {
		return this.check_EOD_BOD_Finish(session, 1);
	}
	
	private boolean checkPostBODFinish(Session session) {
		return this.check_EOD_BOD_Finish(session, 2);
	}
	
	private boolean check_EOD_BOD_Finish(Session session, int type) {
		this.procMastDAO.setSession(session);
		List<EOD_BOD_ProcMast> procList = this.procMastDAO.listUnfinish(type);
		String stType = (type == 1)? "pre": "post";
		String status;
		
		if (procList.size() == 0) {
			EOD_BOD_Dates dates = this.datesDAO.get();
			try {
				status = (String) PropertyUtils.getProperty(dates, stType + "Status");
				if (StatusDefinition.PROCESS.equals(status)) {
					Transaction trx = session.beginTransaction();
					PropertyUtils.setProperty(dates, stType + "Status", StatusDefinition.DONE);
					PropertyUtils.setProperty(dates, stType + "LastDatProcess", this.datProc);
					PropertyUtils.setProperty(dates, stType + "NextDatProcess", this.datNextProc);
					trx.commit();
				}
				return true;
			}
			catch (Exception ex) {
				LOGGER.error(ex, ex);
			}
		}
		
		return false;
	}
	
	private void populateBankDate(BaBankMast bankMaster) {
		this.datLastProc = bankMaster.getDatLastProcess();
		this.datProc = bankMaster.getDatProcess();
		this.datNextProc = bankMaster.getDatNextProcess();
	}

}
