/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.TmpNtfsFcyRateDtlDao;
import bdsm.scheduler.dao.TmpNtfsFcyRateDtlDao;
import bdsm.scheduler.dao.TmpNtfsFcyRateFtrDao;
import bdsm.scheduler.dao.TmpNtfsFcyRateFtrDao;
import bdsm.scheduler.dao.TmpNtfsFcyRateHdrDao;
import bdsm.scheduler.dao.TmpNtfsFcyRateHdrDao;
import bdsm.scheduler.model.TmpNtfsFcyRateDtl;
import bdsm.scheduler.model.TmpNtfsFcyRateFtr;
import bdsm.scheduler.model.TmpNtfsFcyRateHdr;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author bdsm
 */
public class NtfsFcyRateResponsWorker extends BaseProcessor {

    public NtfsFcyRateResponsWorker(Map context) {
        super(context);
    }

    @Override
    protected boolean doExecute() throws Exception {
        getLogger().info("Start Generate Respons");
        //this batchid from fixqxtraxt that will be generate the respons
        String batchRespons = (String) context.get(MapKey.param1);
        String fileRespons = (String) context.get(MapKey.param5);
        TmpNtfsFcyRateHdrDao daoHdr = new TmpNtfsFcyRateHdrDao(session);
        TmpNtfsFcyRateDtlDao daoDtl = new TmpNtfsFcyRateDtlDao(session);
        TmpNtfsFcyRateFtrDao daoFtr = new TmpNtfsFcyRateFtrDao(session);
        TmpNtfsFcyRateHdr modelHdr = null;
        List<TmpNtfsFcyRateDtl> listModelDtl;
        TmpNtfsFcyRateDtl modelDtl = null;
        TmpNtfsFcyRateFtr modelFtr = null;
		
		fileRespons = FilenameUtils.getName(fileRespons);

        Writer out = new OutputStreamWriter(new FileOutputStream(PropertyPersister.dirFixOut + fileRespons));
        StringBuilder sb = new StringBuilder();
        //write header
        modelHdr = daoHdr.get(batchRespons);
        sb.append(modelHdr.getIdentifier()).append("|").append(modelHdr.getBatchId()).append("|").append(modelHdr.getRespCode());
        out.write(sb.toString());
        out.write(System.getProperty("line.separator"));
        sb.delete(0, sb.length());
        //write details
        listModelDtl = daoDtl.list(batchRespons);
        for (int i = 0; i < listModelDtl.size(); i++) {
            modelDtl = listModelDtl.get(i);
            sb.append(modelDtl.getIdentifier()).append("|");
            sb.append(modelDtl.getRefTxnNo()).append("|");
            sb.append(modelDtl.getCodOrgBrn()).append("|");
            sb.append(modelDtl.getRefSysTrAudNo()).append("|");
            sb.append(modelDtl.getRefSubseqNo()).append("|");
            sb.append(modelDtl.getDatValue()).append("|");
            sb.append(modelDtl.getDatPost()).append("|");
            sb.append(modelDtl.getInfoType()).append("|");
            sb.append(modelDtl.getRateConvTxn()).append("|");
            sb.append(modelDtl.getRatConvBds()).append("|");
            sb.append(modelDtl.getCodTxnMnemonic()).append("|");
            sb.append(modelDtl.getBuyCcyShortNam()).append("|");
            sb.append(modelDtl.getBuyAmt()).append("|");
            sb.append(modelDtl.getSoldCcyShortNam()).append("|");
            sb.append(modelDtl.getCodPurOrSell()).append("|");
            sb.append(modelDtl.getCodLob()).append("|");
            sb.append(modelDtl.getNamCustShrt()).append("|");
            sb.append(modelDtl.getRespCode()).append("|");
            out.write(sb.toString());
            out.write(System.getProperty("line.separator"));
            sb.delete(0, sb.length());

            if ((i % 1000) == 0) {
                out.flush();
            }
        }
        //write footer
        modelFtr = daoFtr.get(batchRespons);
        sb.append(modelFtr.getIdentifier()).append("|").append(modelFtr.getCounter());
        out.write(sb.toString());
        out.write(System.getProperty("line.separator"));
        sb.delete(0, sb.length());

        out.close();

        getLogger().info("Respons Generated");
        return true;
    }
}
