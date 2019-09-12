/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import java.math.BigDecimal;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class SknSMIDetailDao extends BaseDao {

	private Logger logger = Logger.getLogger(SknSMIDetailDao.class);

    /**
     * 
     * @param session
     */
    public SknSMIDetailDao(Session session) {
		super(session);
	}

    /**
     * 
     * @param Account
     * @return
     */
    public String getCod_LOB(String Account) {
		Map codBank;
		String LOBCOD = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COD_LOB ");
		sb.append("FROM ba_cust_acct_ao_lob_xref ");
		sb.append("WHERE cod_acct_no = CAST(:Account AS CHAR(16)) ");
		sb.append("AND flg_mnt_status = 'A' AND ROWNUM < 2");

		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("Account", Account);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		logger.info("LIST CODLOB :" + q.list());
		try {
			codBank = (Map) q.list().get(0);
			LOBCOD = codBank.get("COD_LOB").toString();
		} catch (Exception e) {
			logger.info("COD LOB not Found");
		}
		return LOBCOD;
	}

    /**
     * 
     * @param Account
     * @return
     */
    public String getCC_BRN(String Account) {
		Map codBank;
		String CCBRN = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COD_CC_BRN ");
		sb.append("FROM ch_acct_mast ");
		sb.append("WHERE cod_acct_no = CAST(:Account AS CHAR(16)) ");
		sb.append("AND flg_mnt_status = 'A'");

		Query q = getSession().createSQLQuery(sb.toString());
		q.setString("Account", Account);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		logger.info("LIST CODBRANCH :" + q.list());
		try {
			codBank = (Map) q.list().get(0);
			CCBRN = codBank.get("COD_CC_BRN").toString();
		} catch (Exception e) {
			logger.info("COD BRANCH not Found");
		}
		return CCBRN;
	}

    /**
     * 
     * @param Identitas
     * @param SandiKota
     * @param Mark
     * @return
     */
    public String getAll_cod(String Identitas, String SandiKota, Integer Mark) {
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
		sb.append("and to_number(a.cod_fin_inst_id) = b.cod_routing_no ");
		sb.append("and b.cod_sector = :SandiKota ");
		
		Query q = getSession().createSQLQuery(sb.toString());
            q.setString("Identitas", Identitas);
            q.setString("SandiKota", SandiKota);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            //logger.info("ALL_LIST : " + q.list());
			if(Mark.equals(1)){
				tempField = (Map) q.list().get(0);
				lastTemp = tempField.get("COD_BI_OFF_CODE").toString();
            } else if(Mark.equals(2)){
                tempField = (Map) q.list().get(0);
				try {
					Object test = tempField.get("COD_PROVINCE");
					intTemp = (BigDecimal) tempField.get("COD_PROVINCE");
				} catch (Exception e) {
					logger.debug("FAILED TO GET BIGDECIMAL");
				}
				lastTemp = intTemp.toString();
            } else if(Mark.equals(3)){
				tempField = (Map) q.list().get(0);
				try {
					Object test = tempField.get("COD_CITY");
					intTemp = (BigDecimal) tempField.get("COD_CITY");
				} catch (Exception e) {
					logger.debug("FAILED TO GET BIGDECIMAL");
				}
				lastTemp = intTemp.toString();
            } else if(Mark.equals(4)){
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
		this.getSession().save(model);
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
}
