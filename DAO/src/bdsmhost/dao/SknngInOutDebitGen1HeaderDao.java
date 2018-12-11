/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.SknngInOutDebitGen1Header;
import bdsm.model.SknngInOutDebitHPK;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class SknngInOutDebitGen1HeaderDao extends BaseDao {

    private Logger logger = Logger.getLogger(SknngInOutDebitGen1HeaderDao.class);

    public SknngInOutDebitGen1HeaderDao(Session session) {
        super(session);
    }

    public SknngInOutDebitGen1Header get(SknngInOutDebitHPK pk) {
        return (SknngInOutDebitGen1Header) this.getSession().get(SknngInOutDebitGen1Header.class, pk);
    }

    public List getList(String FileId, Integer parentId) {
        Criteria crt = this.getSession().createCriteria(SknngInOutDebitGen1Header.class);
        crt.add(Restrictions.eq("compositeId.fileId", FileId));
        crt.add(Restrictions.eq("compositeId.parentId", parentId));
        return crt.list();
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
    
    public List listGrid(String batchNo, String mode) {
        Query q;
        List Mapper = new ArrayList();
        //logger.info("BATCHID : " + batchNo);
        //logger.info("MODE BATCH : " + mode);
        if ("1".equals(mode)) {
            q = this.getSession().getNamedQuery("SknngDebitgen1Header#listByBatchNoGrid");
        } else if ("2".equals(mode)) {
            q = this.getSession().getNamedQuery("SknngDebitgen1Header#listByBatchApproval");
            q.setString("pXtract", "N");
            q.setString("pFlag", "F");
        } else if ("authorize".equals(mode)) {
            StringBuilder sb = new StringBuilder();
            logger.info("BATCH NO TO ADD: " + batchNo);
			sb.append("select distinct(substr(file_id,length(file_id)-5,length(file_id))) as GRPID ");
            sb.append("from SKNNG_IN_OUT_DEBIT_GEN1_HEADER a ");
            sb.append("where a.file_id like :pBatchNo||'%' ");
            sb.append("and a.flg_reject = :pFlag ");
            sb.append("and a.extract_status = :pXtract ");
            q = getSession().createSQLQuery(sb.toString());
            q.setString("pXtract", "N");
            q.setString("pFlag", "F");
        } else if ("inquiry".equals(mode)) {
            StringBuilder sb = new StringBuilder();
            sb.append("select distinct(substr(file_id,length(file_id)-5,length(file_id))) as GRPID ");
            sb.append("from SKNNG_IN_OUT_DEBIT_GEN1_HEADER a ");
            sb.append("where a.file_id like :pBatchNo||'%' ");
            q = getSession().createSQLQuery(sb.toString());
        } else if ("authorizeDtls".equals(mode)) {
            q = this.getSession().getNamedQuery("SknngDebitgen1Details#listByBatchNoGrid");
            q.setString("pXtract", "N");
            q.setString("pFlag", "F");
        } else if ("inquiryDtls".equals(mode)) {
            q = this.getSession().getNamedQuery("SknngDebitgen1Details#listByBatchApproval");
        } else {
            StringBuilder sb = new StringBuilder();
            q = getSession().createSQLQuery(sb.toString());
        }
        q.setString("pBatchNo", batchNo);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        //logger.info("RETURN LIST : " + q.list());
        return q.list();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        this.getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        this.getSession().delete(model);
        return true;
    }
}
