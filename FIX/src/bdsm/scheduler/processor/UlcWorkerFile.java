/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixInboxDao;
import bdsm.scheduler.dao.UlcTmpSrcDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.UlcTmpSrc;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.ExcelUtil;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author NCBS
 */
public class UlcWorkerFile extends BaseProcessor{

    private String configFile;

    /**
     * 
     * @param context
     */
    public UlcWorkerFile(Map context) {
        super(context);
        this.configFile = "excelutil.properties";
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        UlcTmpSrc ulcTmpSrc = null;
        UlcTmpSrcDao ulcTmpSrcDao = new UlcTmpSrcDao(session);
        ExcelUtil excel = null;
        String BatchID = context.get(MapKey.batchNo).toString();
        String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.fileName).toString())+"{yyMMddhhmmss}.xls");
        String status = context.get(MapKey.status).toString();
        String param1 = context.get(MapKey.param1).toString();

        int idScheduler = Integer.valueOf(context.get(MapKey.idScheduler).toString());
        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            String param5 = context.get(MapKey.param5).toString();
            excel = new bdsm.util.ExcelUtil(param5, configFile);
            List<String[]> data = excel.getData();
            for (int i = 0; i < data.size(); i++) {
                String[] rowData = data.get(i);

                ulcTmpSrc = new UlcTmpSrc();

                ulcTmpSrc.setCodAcctNo(rowData[0]);
                ulcTmpSrc.setCodFieldTag(rowData[1]);
                ulcTmpSrc.setCodTask(rowData[2]);
                ulcTmpSrc.setFieldValue(rowData[3]);
                ulcTmpSrc.setCmd(rowData[4]);
                ulcTmpSrc.setBatch(BatchID);
                ulcTmpSrcDao.insert(ulcTmpSrc);
            }
            //buat ilangin bug
            ulcTmpSrcDao.get(BatchID);
            getLogger().info("LBU : Import Excel file from Requestor Success");

            // do filter and validation
            ulcTmpSrcDao.runUploadUlc(BatchID);
            getLogger().info("LBU : Filter and Validate Source Data Done");

            fixQXtract = new FixQXtract();
            fixQXtract.setIdScheduler(5);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1("RE: " + param1);
            FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
            fixQXtract.setParam2(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
            fixQXtract.setParam4("Need Approval");
            fixQXtract.setParam5(outFileName);
            fixQXtract.setParam6(BatchID);

            return true;
        } else if (status.equals(StatusDefinition.AUTHORIZED)) {

            //excel = new ExcelUtil("", configFile);

            ulcTmpSrcDao.runInsertUlc(BatchID);
            getLogger().info("LBU : Run insert to FCR Done");

            ulcTmpSrcDao.runUpdateUlc(BatchID);
            getLogger().info("LBU : Run update to FCR Done");

            String out2FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.fileName).toString())+"{yyMMddhhmmss}.xls");

            fixQXtract = new FixQXtract();
            fixQXtract.setIdScheduler(5);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1(param1);
            FixEmailAccessDao fixEmailAccessDao = new FixEmailAccessDao(session);
            fixQXtract.setParam3(fixEmailAccessDao.getSpv(context.get(MapKey.grpId).toString()));
            FixInboxDao fixInboxDao = new FixInboxDao(session);
            if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
            }
            fixQXtract.setParam4("DONE");
            fixQXtract.setParam5(out2FileName);
            fixQXtract.setParam6(BatchID);
            return true;
        } else if (status.equals(StatusDefinition.REJECTED)) {
            ulcTmpSrcDao.runRejectUlc(BatchID);
            fixQXtract = new FixQXtract();
            fixQXtract.setIdScheduler(idScheduler);
            fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
            fixQXtract.setDtmRequest(SchedulerUtil.getTime());
            fixQXtract.setParam1(param1);
            FixInboxDao fixInboxDao = new FixInboxDao(session);
            if (!context.get(MapKey.itemIdLink).toString().equals("")) {
                fixQXtract.setParam2(fixInboxDao.get(context.get(MapKey.itemIdLink).toString()).getSender());
            }
            fixQXtract.setParam4("REJECTED");
            fixQXtract.setParam5("");
            return true;

        } else {
            return false;
        }
    }

}
