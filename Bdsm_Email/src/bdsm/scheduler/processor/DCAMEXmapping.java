/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.ExtMfcDetailsDao;
import bdsm.scheduler.dao.ExtMfcHeaderDao;
import bdsm.scheduler.dao.OtpCustDetailDao;
import bdsm.scheduler.dao.OtpCustHeaderDao;
import bdsm.scheduler.model.ExtMfcDetails;
import bdsm.scheduler.model.ExtMfcHeader;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.OtpCustDetail;
import bdsm.scheduler.model.OtpCustHeader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v00013493
 */
public class DCAMEXmapping extends BaseProcessor {
    public DCAMEXmapping(Map context) {
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
        OtpCustHeaderDao headerDao = new OtpCustHeaderDao(session);
        OtpCustDetailDao detailDao = new OtpCustDetailDao(session);
        OtpCustHeader h = headerDao.getModel(BatchID);
        List listDetails = detailDao.getDetails(BatchID);
        StringBuilder sbHeader = new StringBuilder();
        StringBuilder sbDetails = new StringBuilder();
        
        Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + outputFile));
      
        sbHeader.append(h.getTypeRecord()).append(delimiter).append(h.getFileDate()).append(delimiter);
        sbHeader.append(h.getBatchID()).append(delimiter).append(h.getRecID()).append(delimiter);
        sbHeader.append(h.getRespCode()).append(delimiter);
        out.write(sbHeader.toString());
        out.write(System.getProperty("line.separator"));

        for(int i = 0; i < listDetails.size(); i++) {
            loopCount++;
            OtpCustDetail d = (OtpCustDetail)listDetails.get(i);
            
            sbDetails.append(d.getCifNumber()).append(delimiter).append(d.getNama()).append(delimiter);
            sbDetails.append(d.getAc()).append(delimiter).append(d.getSpk()).append(delimiter);
            sbDetails.append(d.getKodeAo()).append(delimiter).append(d.getLob()).append(delimiter);
            sbDetails.append(d.getFasilitas()).append(delimiter).append(d.getPlafonAwal()).append(delimiter);
            sbDetails.append(d.getOs()).append(delimiter).append(d.getTransaksiOtp()).append(delimiter);
            sbDetails.append(d.getSisaPlafon()).append(delimiter).append(d.getCodprodNcbs()).append(delimiter);
            sbDetails.append(d.getCodprodIcbs()).append(delimiter).append(d.getCostCenter()).append(delimiter);
            sbDetails.append(d.getJumlah()).append(delimiter).append(d.getIndexRate()).append(delimiter);
            sbDetails.append(d.getRatecode()).append(delimiter).append(d.getPeriode()).append(delimiter);
            sbDetails.append(d.getSukuBunga()).append(delimiter).append(d.getVariance()).append(delimiter);
            sbDetails.append(d.getRatePinalty()).append(delimiter).append(d.getTanggalPelunasan()).append(delimiter);

         
            
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
