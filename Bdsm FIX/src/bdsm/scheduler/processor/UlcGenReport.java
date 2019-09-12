/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.UlcTmpSrcDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.util.ExcelUtil;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 */
public class UlcGenReport extends BaseProcessor {

    ExcelUtil excel = null;
    private String configFile;

    /**
     * 
     * @param context
     */
    public UlcGenReport(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public boolean doExecute() throws Exception {        
            this.fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract);                        
            configFile = "excelutil.properties";
            UlcTmpSrcDao ulcTmpSrcDao = new UlcTmpSrcDao(this.session);
            //String outFileName = FileUtil.getDateTimeFormatedFileName("ULCTMPSRC{yyMMddhhmmss}.xls");
            String param5 = this.fixQXtract.getParam5();
            String param6 = this.fixQXtract.getParam6();
            excel = new ExcelUtil(param5, configFile);

            List<String[]> uploadedData = ulcTmpSrcDao.getUploadedLBU(param6);
            getLogger().info("Start writing to Excel "+param5);
            excel.writeData(uploadedData, PropertyPersister.dirFixOut + param5);
            getLogger().info("Done writing to Excel "+param5);
            return true;
    }
}
