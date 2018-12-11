/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import java.util.Map;

/**
 *
 * @author SDM
 */
public class OJKReportWorker extends DataPoolingEmail {

    /**
     * 
     * @param context
     */
    public OJKReportWorker(Map context) {
        super(context);
    }
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean process() throws Exception {
        return true;
    }

}
