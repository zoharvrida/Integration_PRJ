/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler;

import java.util.Map;

/**
 *
 * @author bdsm
 */
public interface IBdsmWorker {
    /**
     * 
     * @param context
     */
    public void execute(Map context);
}
