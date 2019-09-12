package bdsm.scheduler.processor;


import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import bdsm.scheduler.MapKey;


/**
 *
 * @author NCBS
 */
public class ScprintWorker extends BaseProcessor{
	private static final String GET_DATA_SC = "PK_SC.getDataSC";
	private static final String CHECK_TO_PRINT_REPORT = "PK_SC.checkToPrintReport(?)";

    /**
     * 
     * @param context
     */
    public ScprintWorker(Map<String, ? extends Object> context) {
        super(context);
    }
	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String param6 = (String) this.context.get(MapKey.param6);
		StringBuilder sql;
		
		sql = new StringBuilder("{ CALL ");
		if (param6 == null) // From Scheduler
			sql.append(GET_DATA_SC);
		else // From GEFU Response
			sql.append(CHECK_TO_PRINT_REPORT);
		sql.append(" }");
		
		try {
			this.getLogger().info("STARTING SCPRINT ");
			Query query = this.session.createSQLQuery(sql.toString());
			if (param6 != null)
				query.setParameter(0, param6);
			query.executeUpdate();
		}
		catch (HibernateException he) {
			this.getLogger().info("HIBERNATE :" + he, he);
		}
		
		return true;
	}
}
