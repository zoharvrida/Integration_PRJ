/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.SKNIncomingCreditDetails;
import bdsmhost.dao.BaseDao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00019722
 */
public class SKNIncomingCreditDetailsDao extends BaseDao {
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SKNIncomingCreditDetailsDao.class);
	private final String FIRST = "FIRST";

    /**
     * 
     * @param session
     */
    public SKNIncomingCreditDetailsDao(Session session) {
		super(session);
	}
    /**
     * 
     * @param batchNo
     * @param parentRecordNo
     * @return
     */
    public List<SKNIncomingCreditDetails> listRecord(String batchNo, Integer parentRecordNo) {
		Query q = this.getSession().createQuery("FROM SKNIncomingCreditDetails WHERE pk.batchNo = :pBatchNo AND parentRecid = :pParentRecordNo ORDER BY pk.RecordId");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return (List<SKNIncomingCreditDetails>) q.list();
	}
    /**
     * 
     * @param batchNo
     * @return
     */
    public List getAllParent(String batchNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT parentrecid ");
		sb.append("from TMP_SKNNG_GEN1_CREDIT_DETAIL ");
        sb.append("where fileid = :batchNo");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("batchNo", batchNo);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

    /**
     * 
     * @param recid
     * @param BatchNo
     * @return
     */
    public List getDetails(Integer recid, String BatchNo) {
		Criteria criteriaQuery = getSession().createCriteria(SKNIncomingCreditDetails.class);
		criteriaQuery.add(Restrictions.eq("pk.batchNo", BatchNo));
		criteriaQuery.add(Restrictions.eq("pk.RecordId", recid));
		criteriaQuery.addOrder(Order.asc("pk.RecordId"));
		return criteriaQuery.list();
	}

    public List getDetails(String BatchNo) {
		Criteria criteriaQuery = getSession().createCriteria(SKNIncomingCreditDetails.class);
		criteriaQuery.add(Restrictions.eq("pk.batchNo", BatchNo));
		criteriaQuery.addOrder(Order.asc("pk.RecordId"));
		return criteriaQuery.list();
	}
    /**
     * 
     * @param idbatch
     * @return
     */
    public List getMaxId(String idbatch) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MAX(recordid) AS MAXRECORD ");
		sb.append("from TMP_SKNNG_GEN1_CREDIT_DETAIL ");
		sb.append("where fileid = :batchNo");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("batchNo", idbatch);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

    /**
     * 
     * @param date
     * @param BatchNo
     * @return
     */
    public List getModels(String date, String BatchNo) {
		logger.info("CHECK BATCH :" + BatchNo);

		Query q = this.getSession().createQuery("FROM SKNIncomingCreditDetails WHERE pk.batchNo = :pBatchNo");
		q.setString("pBatchNo", BatchNo);

		logger.info("LIST cRITERIA :" + q.list());
		return q.list();
	}

    /**
     * 
     * @param Identitas
     * @return
     */
    public List<Map> getCod_Bi(String batchNo){
        StringBuilder sb = new StringBuilder();
		sb.append(" SELECT (SELECT DISTINCT COD_BANK ");
		sb.append("FROM PM_FIN_INST_DIR_MAST B ");
        sb.append("WHERE B.COD_BI = A.IDENTITAS_PENERIMA_AKHIR) COD_BI ");
		sb.append("FROM TMP_SKNNG_IN_OUT_CREDIT_DETAIL A ");
        sb.append("WHERE A.BATCH_NO = :pBatchno ");
		Query q = getSession().createSQLQuery(sb.toString());
        q.setString("pBatchno", batchNo);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return q.list();
    }
    public String getCod_Bi(String Identitas, String a) {
		Map codBank;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cod_bank ");
		sb.append("from pm_fin_inst_dir_mast ");
		sb.append("where cod_bi = :Identitas ");
		sb.append("and flg_mnt_status = 'A' ");
		sb.append("and cod_entity_vpd = 11 ");

		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("Identitas", Identitas);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		codBank = (Map) q.list().get(0);
		return codBank.get("COD_BANK").toString();
	}

    /**
     * 
     * @param Identitas
     * @param SandiKota
     * @param Mark
     * @param Flag
     * @return
     */
    public String getAll_cod(String Identitas, String SandiKota, Integer Mark, String Flag) {
		Map tempField;
		String lastTemp = null;
		java.math.BigDecimal intTemp = null;
		StringBuilder sb = new StringBuilder();
		if (Mark.equals(1)) {
			sb.append("SELECT distinct b.COD_BI_OFF_CODE ");
		} else if (Mark.equals(2)) {
			sb.append("SELECT distinct b.cod_province ");
		} else if (Mark.equals(3)) {
			sb.append("SELECT distinct b.cod_city ");
		} else if (Mark.equals(4)) {
			sb.append("SELECT distinct a.cod_fin_inst_id ");
		}

		sb.append("from pm_fin_inst_dir_mast a, ST_ROUTING_NO_DTLS b ");
		sb.append("where a.cod_bi = :Identitas ");
		sb.append("and a.cod_fin_inst_id = b.cod_routing_no ");
		sb.append("and a.flg_mnt_status = 'A' ");
		sb.append("and a.cod_entity_vpd = 11 ");
		if (Flag.equalsIgnoreCase(FIRST)) {
			sb.append("and b.cod_city = :SandiKota ");
		} else {
			sb.append("AND ROWNUM < 2 ");
		}

		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("Identitas", Identitas);
		if (Flag.equalsIgnoreCase(FIRST)) {
			q.setString("SandiKota", SandiKota);
		}
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		//logger.info("ALL_LIST : " + q.list());
		if (Mark.equals(1)) {
			tempField = (Map) q.list().get(0);
			lastTemp = tempField.get("COD_BI_OFF_CODE").toString();
		} else if (Mark.equals(2)) {
			tempField = (Map) q.list().get(0);
			try {
				Object test = tempField.get("COD_PROVINCE");
				intTemp = (BigDecimal) tempField.get("COD_PROVINCE");
			} catch (Exception e) {
				logger.debug("FAILED TO GET BIGDECIMAL");
			}
			lastTemp = intTemp.toString();
		} else if (Mark.equals(3)) {
			tempField = (Map) q.list().get(0);
			try {
				Object test = tempField.get("COD_CITY");
				intTemp = (BigDecimal) tempField.get("COD_CITY");
			} catch (Exception e) {
				logger.debug("FAILED TO GET BIGDECIMAL");
			}
			lastTemp = intTemp.toString();
		} else if (Mark.equals(4)) {
			tempField = (Map) q.list().get(0);
			lastTemp = (String) tempField.get("COD_FIN_INST_ID");
		}
		return lastTemp;
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		getSession().save((SKNIncomingCreditDetails) model);
		return true;
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
