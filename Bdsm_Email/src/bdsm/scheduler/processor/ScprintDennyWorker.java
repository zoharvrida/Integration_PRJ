/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author NCBS
 */
public class ScprintDennyWorker extends BaseProcessor{

	public ScprintDennyWorker(Map context) {
        super(context);
    }
	@Override
	protected boolean doExecute() throws Exception {
		try {
			getLogger().info("STARTING SCPRINT ");
			Query query = this.session.createSQLQuery("{ CALL PK_SC.getDataSC }");
			query.executeUpdate();
		} catch (HibernateException hibernateException) {
			getLogger().info("HIBERNATE :" + hibernateException);
		}
		return true;
	}
	
}
