package bdsmhost.dao;


import bdsm.model.BaseModel;
import bdsm.model.MfcNoBook;
import bdsm.model.MfcNoBookPK;
import bdsm.model.MfcNoBook_LLD;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * 
 * @author v00020800
 */
public class MfcNoBook_LLDDAO extends BaseDao {
	public MfcNoBook_LLDDAO(Session session) {
		super(session);
	}

    private Logger logger = Logger.getLogger(MfcNoBook_LLDDAO.class);

	public MfcNoBook_LLD get(MfcNoBookPK pk) {
		return (MfcNoBook_LLD) this.getSession().get(MfcNoBook_LLD.class, pk);
	}

	@SuppressWarnings("rawtypes")
	public List list(String noAcct, Date dtPost, String typMsg, String status, Integer amtTxnUsd, String typAcct) {
		StringBuilder qryStr = new StringBuilder()
			.append("SELECT A.dtmTxn as dtmTxn")
			.append(",A.dtValue as dtValue ")
			.append(",A.refTxn as refTxn ") 
			.append(",A.ratFcyIdr as ratFcyIdr")
			.append(",A.amtTxnLcy as amtTxnLcy")
			.append(",A.txnDesc as txnDesc")
			.append(",A.idTxn as idTxn")
			.append(",A.txnBranch as txnBranch")
			.append(",A.typMsg as typMsg")
			.append(",CAST(A.ccyTxn as VARCHAR2(3)) AS strCcyTxn ")
			.append(",A.amtTxn as amtTxn ")
			.append(",A.amtTxnUsd as amtTxnUsd ")
			.append(", pk_bdsm_valas_lld.lld_get_rate_by_nam_ccy('USD') as ratUsdIdr ")
            .append(", pk_bdsm_valas_lld.lld_get_rate_by_nam_ccy(A.ccytxn) as ratFcyIdr ")
            .append(", A.typTrx as typTrx FROM MfcNoBook A ")    
			.append("WHERE A.noAcct = CAST(:pNoAcct AS CHAR(16)) ") 
			.append("AND A.dtPost = :pDtPost ")
			.append("AND A.typMsg = :pTypMsg ")
			.append("AND A.typAcct = :pTypAcct ")
			.append("AND A.status = :pStatus ")
			.append("AND A.amtTxnUsd > :pAmtUsd ")
            .append("AND A.typTrx is not null ");
        
		logger.debug("query :" + qryStr);
		Query q = this.getSession().createSQLQuery(qryStr.toString());
		q.setString("pNoAcct", noAcct);
		q.setDate("pDtPost", dtPost);
		q.setString("pTypMsg", typMsg);
		q.setString("pStatus", status);
		q.setInteger("pAmtUsd", amtTxnUsd);
		q.setString("pTypAcct", typAcct);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return q.list();
	}

	@SuppressWarnings("rawtypes")
	public List listPendingTxn(String acctNo) {
		Query q = this.getSession().getNamedQuery("MfcNoBook_LLD#listLLDPendingTransaction");
		q.setString("pNoAcct", acctNo);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return q.list();
	}
    
    @Override
    protected boolean doInsert(BaseModel model) {
        logger.debug("INSERT :" + (MfcNoBook_LLD)model);
        getSession().save((MfcNoBook_LLD)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((MfcNoBook_LLD)model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((MfcNoBook_LLD)model);
        return true;
    }
}
