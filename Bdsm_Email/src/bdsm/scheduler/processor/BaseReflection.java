/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.IBdsmWorker;
import bdsm.scheduler.util.BdsmLogger;
import org.hibernate.Session;

/**
 *
 * @author bdsm
 */
public abstract class BaseReflection implements IBdsmWorker {

    protected Session session;    

    protected BdsmLogger getLogger() {
        return (BdsmLogger) BdsmLogger.getLogger(this.getClass().getName());
    }
    
}
