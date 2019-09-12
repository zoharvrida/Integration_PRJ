package bdsm.scheduler.processor;


import org.hibernate.Query;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.EOD_BOD_ActivityDAO;
import bdsm.scheduler.dao.EOD_BOD_ProcMastDAO;
import bdsm.scheduler.model.EOD_BOD_Activity;
import bdsm.scheduler.model.EOD_BOD_ProcMast;
import bdsm.util.SchedulerUtil;


/**
 * 
 * @author v00019372
 */
public class EOD_BOD_Worker extends BaseProcessor {

	public EOD_BOD_Worker(java.util.Map<? extends String, ? extends Object> context) {
		super(context);
	}


	@Override
	@SuppressWarnings("unchecked")
	protected boolean doExecute() throws Exception {
		EOD_BOD_ProcMastDAO procMastDAO = new EOD_BOD_ProcMastDAO(this.session);
		EOD_BOD_ActivityDAO actvDAO = new EOD_BOD_ActivityDAO(this.session);
		EOD_BOD_ProcMast procMast;
		EOD_BOD_Activity actv;
		String msg = "";
		
		String param1 = (String) context.get(MapKey.param1); // Start Proc Id
		String param2 = (String) context.get(MapKey.param2); // Posting Date
		String param3 = (String) context.get(MapKey.param3); // Admin Email
		
		this.getLogger().debug("Param1: " + param1);
		this.getLogger().debug("Param2: " + param2);
		this.getLogger().debug("Param3: " + param3);
		
		procMast = procMastDAO.get(Integer.valueOf(param1));
		do {
			if (procMast == null)
				break;
			
			procMast.setFlagProc(StatusDefinition.PROCESS);
			procMast.setDatLastStartRun(SchedulerUtil.getTime());
			actv = new EOD_BOD_Activity(procMast.getProcId(), Integer.valueOf(param2), new java.util.Date());
			actvDAO.insert(actv);
			this.commitAndBeginNewTransaction();
			
			try {
				msg = (procMast.getType() == 1)? "EOD": "BOD" 
						+ " Proc [" + procMast.getModuleName() + "]#" + procMast.getProcId() + ": " 
						+ procMast.getProcedureName();
				this.getLogger().info("[RUN] " + msg);
				this.runProc(procMast);
				this.getLogger().info("[FINISH]" + msg);
				
				procMast.setFlagProc(StatusDefinition.DONE);
				procMast.setDatLastEndRun(SchedulerUtil.getTime());
				
				procMast = procMastDAO.getNextRequestProcess(procMast.getProcId());
			}
			catch (Exception ex) {
				procMast.setFlagProc(StatusDefinition.ERROR);
				this.getLogger().error("[ERROR] " + msg, ex);
				this.context.put(MapKey.param1, "[ERROR] " + msg);
				this.context.put(MapKey.param2, this.context.get(MapKey.param3));
				
				procMastDAO.rejectAllSuccessorProcess(procMast.getProcId());
				
				throw ex;
			}
			finally {
				this.commitAndBeginNewTransaction();
			}
		}
		while (true);
		
		return true;
	}

	private void runProc(EOD_BOD_ProcMast procMast) {
		Query query = this.session.createSQLQuery("{ CALL " + procMast.getProcedureName() + " }");
		query.executeUpdate();
	}

	private void commitAndBeginNewTransaction() {
		this.tx.commit();
		this.tx = this.session.beginTransaction();
	}

}
