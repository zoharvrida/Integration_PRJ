/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.ExtMfcDetailsDao;
import bdsm.scheduler.dao.ExtMfcHeaderDao;
import bdsm.scheduler.model.ExtMfcDetails;
import bdsm.scheduler.model.ExtMfcHeader;
import bdsm.scheduler.model.FixQXtract;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v00013493
 */
public class MfcGenFileResp extends BaseProcessor {
    public MfcGenFileResp(Map context) {
        super(context);
    }
    
    @Override
    public boolean doExecute() throws Exception {
        try {
        getLogger().info("Start Generate File Response");
        System.out.println("Start Generate File Response");
        int loopCount = 0;
        String delimiter = "|";
        fixQXtract = (FixQXtract)context.get(MapKey.fixQXtract);
        String outputFile = fixQXtract.getParam5();
        String BatchID = fixQXtract.getParam6();
        ExtMfcHeaderDao headerDao = new ExtMfcHeaderDao(session);
        ExtMfcDetailsDao detailDao = new ExtMfcDetailsDao(session);
        ExtMfcHeader h = headerDao.getModel(BatchID);
        List listDetails = detailDao.getDetails(BatchID);
        StringBuilder sbHeader = new StringBuilder();
        StringBuilder sbDetails = new StringBuilder();
        
        Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile));
        
        sbHeader.append(h.getRecType()).append(delimiter).append(h.getSrcSystem()).append(delimiter);
        sbHeader.append(h.getNoBatch()).append(delimiter).append(h.getRecCount()).append(delimiter);
        sbHeader.append(h.getRespCode()).append(delimiter);
        out.write(sbHeader.toString());
        out.write(System.getProperty("line.separator"));

        for(int i = 0; i < listDetails.size(); i++) {
            loopCount++;
            ExtMfcDetails d = (ExtMfcDetails)listDetails.get(i);
            sbDetails.append(d.getRecType()).append(delimiter).append(d.getNoCif()).append(delimiter);
            sbDetails.append(d.getNoAcct()).append(delimiter).append(d.getRefTxn()).append(delimiter);
            sbDetails.append(d.getTypMsg()).append(delimiter).append(d.getTypAcct()).append(delimiter);
            sbDetails.append(d.getDtPost()).append(delimiter).append(d.getDtValue()).append(delimiter);
            sbDetails.append(d.getDtmTxn()).append(delimiter).append(d.getCcyTxn()).append(delimiter);
            sbDetails.append(d.getAmtTxn()).append(delimiter).append(d.getRatFcyIdr()).append(delimiter);
            sbDetails.append(d.getAmtTxnLcy()).append(delimiter).append(d.getTxnDesc()).append(delimiter);
            sbDetails.append(d.getTxnBranch()).append(delimiter);
            sbDetails.append(d.getNoUd() == null ? "" : d.getNoUd()).append(delimiter);
            sbDetails.append(d.getUdLmtAmt() == null ? "" : d.getUdLmtAmt()).append(delimiter);
            sbDetails.append(d.getUdDtExp() == null ? "" : d.getUdDtExp()).append(delimiter);
            sbDetails.append(d.getRespCode()).append(delimiter);
            
            out.write(sbDetails.toString());
            out.write(System.getProperty("line.separator"));
            sbDetails.delete(0, sbDetails.length());
            
            if(loopCount == 1000) {
                loopCount = 0;
                out.flush();
            }
        }
        
        out.write("9");
        out.close();
        
        getLogger().info("Finished Generate File Response");
        System.out.println("Finished Generate File Response");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return true;
    }
}
