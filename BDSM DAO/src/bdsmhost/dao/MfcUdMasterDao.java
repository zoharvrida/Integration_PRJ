/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MfcUdMaster;
import bdsm.model.MfcUdMasterPK;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author user
 */
public class MfcUdMasterDao extends BaseDao {

    public MfcUdMasterDao(Session session) {
        super(session);
    }

    public MfcUdMaster get(MfcUdMasterPK pk) {
        return (MfcUdMaster) getSession().get(MfcUdMaster.class, pk);
    }

    public MfcUdMaster get(Integer noCif, String noUd, String ccyUd) {
        MfcUdMaster mfcUdMaster = null;

        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT A.noCif, A.noUd, A.dtExpiry, CAST(A.ccyUd as VARCHAR2(3)), A.amtLimit, ");
        qryStr.append("A.amtAvail, A.cdBranch, A.status, A.remarks, A.idCreatedBy, A.idCreatedSpv, ");
        qryStr.append("CAST(A.dtmCreated as Timestamp), CAST(A.dtmCreatedSpv as Timestamp), A.amtLimitUsd, A.amtAvailUsd, A.ratFcyLim, A.ratUsdLim  ");
        qryStr.append(", A.type_ud, A.payee_name, A.payee_country, A.trading_product, A.dt_issued ");
        qryStr.append("FROM MfcUdMaster A ");
        qryStr.append("WHERE A.noCif = :pNoCif ");
        qryStr.append("AND A.noUd = :pNoUd ");
        //qryStr.append("AND A.ccyUd = CAST(:pCcyUd AS CHAR(3)) ");
        qryStr.append("AND A.status = :pStatus ");

        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setInteger("pNoCif", noCif);
        q.setString("pNoUd", noUd);
        //q.setString("pCcyUd", ccyUd);
        q.setString("pStatus", "A");

        Object[] objMfcUdMaster = (Object[]) q.uniqueResult();

        if (objMfcUdMaster != null) {
            MfcUdMasterPK mfcUdMasterPK = new MfcUdMasterPK();
            mfcUdMasterPK.setNoCif(new Integer(((BigDecimal) objMfcUdMaster[0]).intValue()));
            mfcUdMasterPK.setNoUd((String) objMfcUdMaster[1]);

            mfcUdMaster = new MfcUdMaster();
            mfcUdMaster.setCompositeId(mfcUdMasterPK);
            mfcUdMaster.setDtExpiry((Date) objMfcUdMaster[2]);
            mfcUdMaster.setCcyUd((String) objMfcUdMaster[3]);
            mfcUdMaster.setAmtLimit(new Double(((BigDecimal) objMfcUdMaster[4]).doubleValue()));
            mfcUdMaster.setAmtAvail(new Double(((BigDecimal) objMfcUdMaster[5]).doubleValue()));
            mfcUdMaster.setCdBranch(new Integer(((BigDecimal) objMfcUdMaster[6]).intValue()));
            mfcUdMaster.setStatus(((Character) objMfcUdMaster[7]).toString());
            mfcUdMaster.setRemarks((String) objMfcUdMaster[8]);
            mfcUdMaster.setIdCreatedBy((String) objMfcUdMaster[9]);
            mfcUdMaster.setIdCreatedSpv((String) objMfcUdMaster[10]);
            mfcUdMaster.setDtmCreated((Timestamp) objMfcUdMaster[11]);
            mfcUdMaster.setDtmCreatedSpv((Timestamp) objMfcUdMaster[12]);
            mfcUdMaster.setAmtLimitUsd(new Double(((BigDecimal) objMfcUdMaster[13]).doubleValue()));
            mfcUdMaster.setAmtAvailUsd(new Double(((BigDecimal) objMfcUdMaster[14]).doubleValue()));
            mfcUdMaster.setRatFcyLim(new Double(((BigDecimal) objMfcUdMaster[15]).doubleValue()));
            mfcUdMaster.setRatUsdLim(new Double(((BigDecimal) objMfcUdMaster[16]).doubleValue()));
            mfcUdMaster.setTypeUD((String) objMfcUdMaster[17]);
            mfcUdMaster.setPayeeName((String) objMfcUdMaster[18]);
            mfcUdMaster.setPayeeCountry((String) objMfcUdMaster[19]);
            mfcUdMaster.setTradingProduct((String) objMfcUdMaster[20]);
            mfcUdMaster.setDtIssued((Date) objMfcUdMaster[21]);
        }

        return mfcUdMaster;
    }

    public List list(MfcUdMasterPK pk) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("select a.noCif, a.noUd, a.dtExpiry, CAST(a.ccyUd as VARCHAR2(3)) ccyUd, a.amtLimit, ");
        qryStr.append("a.remarks, a.cdBranch, case when a.dtexpiry >= (select dat_process from ba_bank_mast) then ");
        qryStr.append("'Active' else 'Inactive' end status, ");
        qryStr.append("a.amtLimitUsd, a.amtAvailUsd, a.ratFcyLim, a.ratUsdLim ");
        qryStr.append(", a.type_ud, a.payee_name, a.payee_country, a.trading_product, a.dt_issued ");
        qryStr.append("from MfcUdMaster a ");
        qryStr.append("where a.noCif = :pNoCif ");
        qryStr.append("and a.noUd like :pNoUd ");

        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setInteger("pNoCif", pk.getNoCif());
        q.setString("pNoUd", pk.getNoUd() + "%");
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return q.list();
    }
    
    @SuppressWarnings("unchecked")
	public List<MfcUdMaster> listByNoUD(String noUD) {
    	Query q = this.getSession().getNamedQuery("MfcUdMaster#listByNoUD");
    	q.setString("noUd", noUD);
    	List<MfcUdMaster> listResult = q.list();
    	
    	for (MfcUdMaster ud : listResult)
    		this.getSession().evict(ud);
    	
    	return listResult; 
    }
    
    

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((MfcUdMaster) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((MfcUdMaster) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((MfcUdMaster) model);
        return true;
    }
}
