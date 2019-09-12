/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.DataPoolingDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.service.DataPoolingService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SDM
 */
public abstract class DataPoolingEmail extends BaseProcessor {

    private boolean flag;

    /**
     * 
     * @param context
     */
    public DataPoolingEmail(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    protected boolean doExecute() throws Exception {
        // check into fixqxtract
        DataPoolingDao dpDao = new DataPoolingDao(session);
        DataPoolingService dpServ = new DataPoolingService();
        dpServ.setdPoolingDao(dpDao);
        getLogger().debug("CONTEXT : " + this.context);
        FixQXtract qX = (FixQXtract) context.get(MapKey.fixQXtract);
        Integer schedId = qX.getIdScheduler();
        
        String batchID = qX.getParam6();
        this.flag = process();

        if (flag) {
            List<FixQXtract> dataList = dpServ.dataPoolingReport(schedId,batchID);
            StringBuilder sb = new StringBuilder();

            Iterator<FixQXtract> it = dataList.iterator();
            while (it.hasNext()) {
                sb.append(PropertyPersister.dirFixOut);
                FixQXtract q = it.next();
                if("D".equalsIgnoreCase(q.getFlgProcess())){
                    sb.append(q.getParam5());
                    sb.append(";");
                }
            }
            this.fixQXtract = qX;
            this.fixQXtract.setParam5(sb.toString());
        }
        return true;
    }
    /**
     * 
     * @return
     * @throws Exception
     */
    protected abstract boolean process() throws Exception;
}
