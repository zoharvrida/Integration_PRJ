/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.LHPReport;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class LHPReportDAO extends BaseDao {

    public LHPReportDAO(Session session) {
        super(session);
    }

    protected Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    public List<LHPReport> listLHPReport(String tanggalBuku, String ccyCode) {
        List<LHPReport> result = new ArrayList<LHPReport>();
        List<Map> list = null;
        try {
            Query query = this.getSession().getNamedQuery("LHPREPORT#getData");
            query.setString("tanggalBuku", tanggalBuku);
            query.setString("ccyCode", ccyCode);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            list = query.list();
            this.getLogger().info("list LHP Report");
            this.getLogger().info("HASIL LIST MAP " + list);
            for (Map getList : list) {
                if (getList == null) {
                    break;
                }
                LHPReport lhpr = new LHPReport();
                try {
                    lhpr.setTanggalBuku(getList.get("TanggalBuku").toString());
                } catch (Exception e) {
                    lhpr.setTanggalBuku("");
                }
                try {
                    lhpr.setBankCode(getList.get("BankCode").toString());
                } catch (Exception e) {
                    lhpr.setBankCode("");
                }
                try {
                    lhpr.setBankName(getList.get("BankName").toString());
                } catch (Exception e) {
                    lhpr.setBankName("");
                }
                try {
                    lhpr.setAccountNo(getList.get("AccountNumber").toString());
                } catch (Exception e) {
                    lhpr.setAccountNo("");
                }
                try {
                    lhpr.setMataUang(getList.get("CcyCode").toString());
                } catch (Exception e) {
                    lhpr.setMataUang("");
                }
                try {
                    lhpr.setRecordCount(getList.get("RecordCount").toString());
                } catch (Exception e) {
                    lhpr.setRecordCount("");
                }
                try {
                    lhpr.setTotalAmount(new BigDecimal(getList.get("TotalAmount").toString()));
                } catch (Exception e) {
                    lhpr.setTotalAmount(new BigDecimal(BigInteger.ZERO));
                }
                try {
                    lhpr.setNoSakti(getList.get("NoSakti").toString());
                } catch (Exception e) {
                    lhpr.setNoSakti("");
                }
                try {
                    lhpr.setTotalAmountIDR(new BigDecimal(getList.get("TotalAmountIDR").toString()));
                } catch (Exception e) {
                    lhpr.setTotalAmountIDR(new BigDecimal(BigInteger.ZERO));
                }
                result.add(lhpr);
            }
            this.getLogger().info("HASIL LIST " + list.size());
            this.getLogger().info("HASIL LIST MAP " + list);

        } catch (Exception e) {
            getLogger().info("LOG ERROR-listLHPReport " + e);
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
}
