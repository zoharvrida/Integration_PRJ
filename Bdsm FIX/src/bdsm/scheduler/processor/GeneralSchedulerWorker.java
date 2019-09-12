package bdsm.scheduler.processor;


import java.util.Map;

import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralSchedulerDao;
import bdsm.service.SchedulerPeriodicService;
import bdsmhost.dao.ParameterDao;


/**
 * 
 * @author bdsm
 */
public class GeneralSchedulerWorker extends BaseProcessor {
    /**
     * 
     * @param context
     */
    public GeneralSchedulerWorker(Map<String, ? extends Object> context) {
		super(context);
	}


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		this.getLogger().debug("doExecute BEGIN");
		ParameterDao paramDao = new ParameterDao(this.session);
		GeneralSchedulerDao schedDao = new GeneralSchedulerDao(this.session);
		FixSchedulerXtractDao xtract = new FixSchedulerXtractDao(this.session);
		SchedulerPeriodicService ss = new SchedulerPeriodicService(this.session, this.context, paramDao, schedDao, xtract);

		ss.parameterRetrieval();
		this.getLogger().debug("doExecute END");
		
		return true;
	}

}
