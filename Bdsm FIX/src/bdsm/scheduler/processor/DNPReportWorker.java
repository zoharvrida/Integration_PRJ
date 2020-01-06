/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.DNPReport;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.DNPReportDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author v00024535
 */
public class DNPReportWorker extends BaseProcessor {

    public DNPReportWorker(Map context) {
        super(context);
    }

    @Override
    protected boolean doExecute() throws Exception {
        try {
            List<DNPReport> dNPReports = new LinkedList<DNPReport>();
            String reportFilename = context.get(MapKey.reportFileName).toString();
            String outputFile = reportFilename;
            DNPReportDAO dao = new DNPReportDAO(session);
            String tanggalBuku = context.get(MapKey.param1).toString();
            String ccyCode = context.get(MapKey.param2).toString();
            String delimeter = "\r\n";
            String split = ";";
            dNPReports = dao.listDNPReport(tanggalBuku, ccyCode);

            Writer out = new OutputStreamWriter(new FileOutputStream(new File(PropertyPersister.dirFixOut, outputFile)));
            if (dNPReports.size() > 0) {
                for (DNPReport dnpr : dNPReports) {
                    out.write(dnpr.getStrval() + split);
                    out.write(dnpr.getTrxDateTime() + split);
                    out.write(dnpr.getTrxHourTime() + split);
                    out.write(dnpr.getTanggalBuku() + split);
                    out.write(dnpr.getBillingId() + split);
                    out.write(dnpr.getNtb() + split);
                    out.write(dnpr.getNtpn() + split);
                    out.write(dnpr.getJmlSetoran() + split);
                    out.write(dnpr.getMataUang() + split);
                    out.write(dnpr.getNoSk() + delimeter);
                }
            } else {
                out.write("");
            }
            out.close();
            this.getLogger().info("DNP REPORT LIST : " + dNPReports.size());

        } catch (Exception e) {
            this.getLogger().info("DNPReportWorker - doExecute" + e);
        }
        return true;
    }
}
