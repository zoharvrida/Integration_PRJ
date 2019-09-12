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

    public OJKReportWorker(Map context) {
        super(context);
    }
    @Override
    protected boolean process() throws Exception {
        return true;
    }

}
