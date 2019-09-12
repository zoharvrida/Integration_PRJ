/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.util.HibernateUtil;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class PeriodObjectReflection extends BaseReflection {

    private Transaction tx;

    public void execute(Map context) {
        this.session = HibernateUtil.getSession();
        String javaClass = context.get(MapKey.javaClass).toString();
        Class[] paramString = new Class[1];
        paramString[0] = Map.class;
        FixQXtract fixQXtract;
        try {
            Class cls = Class.forName(javaClass);
            Constructor cons = cls.getConstructor(paramString);
            BaseProcessor baseProcessor = (BaseProcessor) cons.newInstance(context);
            boolean success = baseProcessor.execute();
        } catch (Exception ex) {
            getLogger().error(ex.toString());
        } finally {
            fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract);
            insertToFixQXtract(fixQXtract);
            HibernateUtil.closeSession(session);
        }
    }

    private void insertToFixQXtract(FixQXtract fixQXtract) {
        FixQXtractDao fixQXtractDao = new FixQXtractDao(this.session);
        this.tx = session.beginTransaction();
        fixQXtractDao.insert(fixQXtract);
        this.tx.commit();
    }
}
