/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.util.SchedulerUtil;
import bdsm.util.HibernateUtil;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bdsm
 */
public class XtractSchedulerProcessor extends XtractProcessor {

    /**
     * 
     * @param context
     */
    @Override
    public void execute(Map context) {
        List<FixQXtract> listDelQtraxt = null;
        this.context = context;
        this.session = HibernateUtil.getSession();
        //Register to FixQXtract
        getLogger().debug("MAPKey : "+ context); // remove after check ya
        this.fixSchedulerXtract = (FixSchedulerXtract) context.get(MapKey.model);
        getLogger().info("Registering job : "+ this.fixSchedulerXtract.getFixSchedulerPK().getIdScheduler());

        this.fixQXtract  = new FixQXtract();
        this.fixQXtract.setIdScheduler(this.fixSchedulerXtract.getFixSchedulerPK().getIdScheduler());
        this.fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
        this.fixQXtract.setDtmRequest(SchedulerUtil.getTime());
        this.fixQXtract.setParam1(this.fixSchedulerXtract.getEmailSubjectPattern());
        this.fixQXtract.setParam2(this.fixSchedulerXtract.getEmailTo());
        this.fixQXtract.setParam3(this.fixSchedulerXtract.getEmailCC());
        this.fixQXtract.setParam4(this.fixSchedulerXtract.getEmailBody());
        this.fixQXtract.setParam5(this.fixSchedulerXtract.getEmailAttachment());
        this.fixQXtractDao = new FixQXtractDao(this.session);
        listDelQtraxt = this.fixQXtractDao.getAllQXtractDeleteScheduledList(10, this.fixSchedulerXtract.getFixSchedulerPK().getIdScheduler());
        this.transaction = this.session.beginTransaction();
        for (int i = 0; i < listDelQtraxt.size(); i++) {
            this.fixQXtractDao.delete(listDelQtraxt.get(i));
        }
        this.transaction.commit();
        this.transaction = this.session.beginTransaction();
        this.fixQXtractDao.insert(this.fixQXtract);
        this.transaction.commit();

        getLogger().info("Registering job : "+ this.fixSchedulerXtract.getFixSchedulerPK().getIdScheduler()+" success");
        HibernateUtil.closeSession(this.session);
    }

}
