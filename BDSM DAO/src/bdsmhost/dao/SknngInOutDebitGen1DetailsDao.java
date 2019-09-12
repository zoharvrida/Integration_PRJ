/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.SknngInOutDebitGen1Details;
import bdsm.model.SknngInOutDebitPK;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class SknngInOutDebitGen1DetailsDao extends BaseDao {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SknngInOutDebitGen1DetailsDao.class);

    public SknngInOutDebitGen1DetailsDao(Session session) {
        super(session);
    }

    public SknngInOutDebitGen1Details get(SknngInOutDebitPK pk) {
        return (SknngInOutDebitGen1Details) this.getSession().get(SknngInOutDebitGen1Details.class, pk);
    }

    public String getCod_Bi(String bankCode) {
        Map BICcode;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT cod_bi ");
        sb.append("from pm_fin_inst_dir_mast ");
        sb.append("where cod_fin_inst_id = :bankCode");

        Query q = getSession().createSQLQuery(sb.toString());
        q.setString("bankCode", bankCode);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        BICcode = (Map) q.list().get(0);
        return BICcode.get("COD_BI").toString();
    }
    public String getCode_sector(String codSector) {
        Map sectorCode;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT cod_sector ");
        sb.append("from ST_ROUTING_NO_DTLS ");
        sb.append("where cod_routing_no = :codSector");

        Query q = getSession().createSQLQuery(sb.toString());
        q.setString("codSector", codSector);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        sectorCode = (Map) q.list().get(0);
        return sectorCode.get("COD_SECTOR").toString();
    }
    public List getList(String FileId, Integer parentId) {
        Criteria crt = this.getSession().createCriteria(SknngInOutDebitGen1Details.class);
        crt.add(Restrictions.eq("compositeId.fileId", FileId));
        crt.add(Restrictions.eq("compositeId.parentId", parentId));
        crt.add(Restrictions.eq("flgReject", "F"));
        return crt.list();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
        return true;
    }
}
