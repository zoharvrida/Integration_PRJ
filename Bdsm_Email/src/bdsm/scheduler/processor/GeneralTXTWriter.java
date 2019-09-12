/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralARDao;
import bdsm.scheduler.dao.GeneralINTDao;
import bdsm.service.GeneralTXTService;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 00110310
 */
public class GeneralTXTWriter extends NewTxtRespGenerator {

    public GeneralTXTWriter(Map context) {
        super(context);
    }
    
    @Override
    public Integer checkNumHeader() {
        return checkNumData();
    }

    @Override
    public Integer checkNumDetail(Integer count) {
        return checkNumData();
    }

    @Override
    public Integer checkNumFooter() {
        return checkNumData();
    }

    @Override
    public StringBuilder headerS(Integer count) {
        StringBuilder sb = new StringBuilder();
        GeneralTXTService serv = letsWrite();
        sb.append(serv.txtWriter(interfaceParameter, interfaceDetails, count));
        return sb;
    }

    @Override
    public StringBuilder detailS(Integer count) {
        StringBuilder sb = new StringBuilder();
        GeneralTXTService serv = letsWrite();
        sb.append(serv.txtWriter(interfaceParameter, interfaceDetails, count));
        return sb;
    }

    @Override
    public StringBuilder footerS(Integer count) {
        StringBuilder sb = new StringBuilder();
        GeneralTXTService serv = letsWrite();
        sb.append(serv.txtWriter(interfaceParameter, interfaceDetails, count));
        return sb;
    }
    
    public GeneralTXTService letsWrite(){
        String batchID = this.BatchID;
        getLogger().debug("PROCESS START");
        
        ParameterDao pDao = new ParameterDao(session);
        BaBankMastDAO baDao = new BaBankMastDAO(session);
        FixSchedulerImportDao impDao = new FixSchedulerImportDao(session);
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);
        GeneralINTDao genDao = new GeneralINTDao(session);
        
        GeneralTXTService serv = new GeneralTXTService(impDao, xtractDao, baDao, pDao, genDao, session, this.context);
        serv.setBatchNo(batchID);
        return serv;
        
    }
    
    public Integer checkNumData(){
        GeneralTXTService serv = letsWrite();
        getLogger().debug("MASTER :" + interfaceParameter);
        getLogger().debug("DETAILS :" + interfaceDetails);
        getLogger().debug("CONTEXT IN WRITER :" + this.context);
        List<Map> dataMap = serv.queryDataBatch(interfaceParameter, interfaceDetails, false);
        getLogger().debug("MAP DATA :" + dataMap.size());
        return dataMap.size();
    }
}
