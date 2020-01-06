/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.LHPReport;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.LHPReportDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author v00024535
 */
public class LHPReportWorker extends BaseProcessor {

    public LHPReportWorker(Map context) {
        super(context);
    }

    @Override
    protected boolean doExecute() throws Exception {
        try {
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<LHPReport> lHPReports = new LinkedList<LHPReport>();
            String reportFilename = context.get(MapKey.reportFileName).toString();
            String outputFile = reportFilename;
            LHPReportDAO dao = new LHPReportDAO(session);
            String tanggalBuku = context.get(MapKey.param1).toString();
            String ccyCode = context.get(MapKey.param2).toString();
            String delimeter = "\r\n";
            lHPReports = dao.listLHPReport(tanggalBuku, ccyCode);

            Writer out = new OutputStreamWriter(new FileOutputStream(new File(PropertyPersister.dirFixOut, outputFile)));
            if (lHPReports.size() > 0) {
                for (LHPReport hPReport : lHPReports) {
                    if (ccyCode.equalsIgnoreCase("IDR")) {
                        totalAmount = totalAmount.add(hPReport.getTotalAmountIDR());
                    } else if (ccyCode.equalsIgnoreCase("USD")) {
                        totalAmount = totalAmount.add(hPReport.getTotalAmount());
                    }
                }
                out.write("LAPORAN HARIAN PENERIMAAN" + delimeter);
                out.write("Tanggal Buku|" + lHPReports.get(0).getTanggalBuku() + delimeter);
                out.write("Kode Bank|" + lHPReports.get(0).getBankCode() + delimeter);
                out.write("Nama Bank|" + lHPReports.get(0).getBankName() + delimeter);
                out.write("Nomor Rekening|" + lHPReports.get(0).getAccountNo() + delimeter);
                out.write("Mata Uang|" + lHPReports.get(0).getMataUang() + delimeter);
                out.write("Jumlah Transaksi|" + lHPReports.get(0).getRecordCount() + delimeter);
                out.write("Jumlah Penerimaan|" + totalAmount + delimeter);
                out.write("Pelimpahan Penerimaan" + delimeter);
                out.write("Nomor Referensi Pelimpahan|Total Pelimpahan" + delimeter);
                for (LHPReport getMap : lHPReports) {
                    if (ccyCode.equalsIgnoreCase("IDR")) {
                        out.write(getMap.getNoSakti() + "|" + getMap.getTotalAmountIDR() + delimeter);
                    } else if (ccyCode.equalsIgnoreCase("USD")) {
                        out.write(getMap.getNoSakti() + "|" + getMap.getTotalAmount() + delimeter);
                    }
                }
            } else {
                out.write("");
            }
            out.close();
            this.getLogger().info("LHP REPORT LIST : " + lHPReports.size());

        } catch (Exception e) {
            this.getLogger().info("LHPReportWorker - doExecute" + e);
        }
        return true;
    }
}
