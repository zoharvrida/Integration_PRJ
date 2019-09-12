/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.ScreenOpening;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.service.SingleScreenOPService;
import bdsm.util.ClassConverterUtil;
import bdsmhost.dao.CifCasaExtOpeningDao;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author SDM
 */
public class ExtDDFRespondWorker extends TXTrespGenerator {

    /**
     * 
     */
    public CifCasaExtOpeningDao cDao;
    /**
     * 
     */
    public Map ext;

    /**
     * 
     * @param context
     */
    public ExtDDFRespondWorker(Map context) {
        super(context);
    }
    /**
     * 
     * @return
     */
    @Override
    public Integer checkNumHeader() {
        return 1;
    }

    /**
     * 
     * @param count
     * @return
     */
    @Override
    public Integer checkNumDetail(Integer count) {
        cDao = new CifCasaExtOpeningDao(session);
        List<ScreenOpening> d = cDao.getNumber(BatchID, 0);
        
        getLogger().info("Details Number :" + d.size());
        this.checknumDetails = d.size();
        return this.checknumDetails;
    }

    /**
     * 
     * @param count
     * @return
     */
    @Override
    public StringBuilder headerS(Integer count) {
        cDao = new CifCasaExtOpeningDao(session);
        BaBankMastDAO bankDao = new BaBankMastDAO(session);

        ext = new HashedMap();
        ext.put("recType", "0");
        ext.put("datProcess", bankDao.get().getDatProcess());
        ext.put("totalSuccess", cDao.getEntityByStat(BatchID, "").size());
        ext.put("totalReject", cDao.getEntityByStat(BatchID, "R").size());
        ext.put("totalRecord", cDao.getEntityByStat(BatchID, "P").size());
        getLogger().debug("HEADERMAP :" + ext);
        return callService(0);
    }

    /**
     * 
     * @param count
     * @return
     */
    @Override
    public StringBuilder detailS(Integer count) {
        getLogger().debug("(CASA)ID_BATCH reps Details :" + BatchID);
        StringBuilder sbDetails = new StringBuilder();
        
        try {
            cDao = new CifCasaExtOpeningDao(session);
            List<ScreenOpening> d = cDao.getNumber(BatchID, count);
            getLogger().debug("BEFORE PARSING DETAILS CASA :" + count);

            ScreenOpening cifCasa = d.get(0);
            Object[] listObj = {cifCasa};
            String[] nameless = {""};
            String[] prefix = {"", ""};
            
            this.ext = new HashedMap();
            this.ext = ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1);
            getLogger().debug("DETAILS MAP:" + ext);
            sbDetails.append(callService(1));
            getLogger().debug("DETAILS CASA:" + sbDetails.toString());
        } catch (Exception e) {
            getLogger().error("ERROR PARSING DETAIL CASA:" + e,e);
        }
        return sbDetails;
    }
    /**
     * 
     * @param flag
     * @return
     */
    public StringBuilder callService(Integer flag){
        StringBuilder sb = new StringBuilder();
        FixQXtractDao fixqDao = new FixQXtractDao(session);
        ParameterDao paramDao = new ParameterDao(session);
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);
        CifCasaExtOpeningDao cifDao = new CifCasaExtOpeningDao(session);
        
        SingleScreenOPService respServ = new SingleScreenOPService();
        respServ.setSession(session);

        respServ.setFixQDao(fixqDao);
        respServ.setParamDao(paramDao);
        respServ.setXtractDao(xtractDao);
        respServ.setCifAcctExtDao(cifDao);

        sb.append(respServ.ExtDDFRespons(context, ext, flag));
        getLogger().info("ERROR RESP :" + this.reason);
        if(this.reason != null){
            sb.append(context.get(MapKey.param2).toString()).append(this.reason);
        }

        return sb;
    }
}
