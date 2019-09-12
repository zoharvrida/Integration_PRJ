/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import java.util.Map;

/**
 *
 * @author bdsm
 */
public class DummyProcessor extends BaseProcessor {

    /**
     * 
     * @param Context
     */
    public DummyProcessor(Map Context) {
        super(Context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected boolean doExecute() throws Exception {
        if (context.get(MapKey.status) != null) {
            String status = context.get(MapKey.status).toString();
            if (status.equals("U")) {
                getLogger().info("Status is U");
                return true;
            } else if (status.equals("A")) {
                getLogger().info("Status is A");
                return true;
            } else if (status.equals("R")) {
                getLogger().info("Status is R");
                return true;
            } else {
                return false;
            }
        } else{
            return true;
        }
    }
}
