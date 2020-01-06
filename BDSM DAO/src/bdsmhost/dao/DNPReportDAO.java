/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.DNPReport;
import bdsm.util.BdsmUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00024535
 */
public class DNPReportDAO extends BaseDao {

    public DNPReportDAO(Session session) {
        super(session);
    }

    protected Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    public List<DNPReport> listDNPReport(String tanggalBuku, String ccyCode) {
        List<DNPReport> result = new ArrayList<DNPReport>();
        List<Map> list = null;
        try {
            Query query = this.getSession().getNamedQuery("DNPREPORT#getData");
            query.setString("tanggalBuku", tanggalBuku);
            query.setString("ccyCode", ccyCode);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            list = query.list();
            this.getLogger().info("list DNP Report");

            for (Map getList : list) {
                if (getList == null) {
                    break;
                }
                DNPReport dnpr = new DNPReport();
                try {
                    dnpr.setStrval(getList.get("Strval").toString());
                } catch (Exception e) {
                    dnpr.setStrval("");
                }
                try {
                    dnpr.setTrxDateTime(getList.get("TrxDateTime").toString());
                } catch (Exception e) {
                    dnpr.setTrxDateTime("");
                }
                try {
                    dnpr.setTrxHourTime(getList.get("TrxHourTime").toString());
                } catch (Exception e) {
                    dnpr.setTrxHourTime("");
                }
                try {
                    dnpr.setTanggalBuku(getList.get("TanggalBuku").toString());
                } catch (Exception e) {
                    dnpr.setTanggalBuku("");
                }
                try {
                    dnpr.setBillingId(getList.get("BillingId").toString());
                } catch (Exception e) {
                    dnpr.setBillingId("");
                }
                try {
                    dnpr.setNtb(getList.get("Ntb").toString());
                } catch (Exception e) {
                }
                try {
                    dnpr.setNtpn(getList.get("Ntpn").toString());
                } catch (Exception e) {
                    dnpr.setNtpn("");
                }
                try {
                    dnpr.setJmlSetoran(formatNumericString("0", 16 - (getList.get("JmlSetoran").toString().replace(".", "").length())) + getList.get("JmlSetoran").toString().replace(".", ""));
                } catch (Exception e) {
                    dnpr.setJmlSetoran("");
                }
                try {
                    dnpr.setMataUang(getList.get("MataUang").toString());
                } catch (Exception e) {
                    dnpr.setMataUang("");
                }
                try {
                    dnpr.setNoSk(getList.get("NoSk").toString());
                } catch (Exception e) {
                    dnpr.setNoSk("");
                }
                result.add(dnpr);
            }
            this.getLogger().info("HASIL LIST " + list.size());
            this.getLogger().info("HASIL LIST MAP " + list);

        } catch (Exception e) {
            this.getLogger().info("LOG ERROR-listDNPReport " + e);
        }
        return result;
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String formatNumericString(String input, int length) {
        return BdsmUtil.leftPad(input, length, '0');
    }
}
