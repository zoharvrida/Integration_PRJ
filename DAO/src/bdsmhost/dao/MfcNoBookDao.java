/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MfcNoBook;
import bdsm.model.MfcNoBookPK;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class MfcNoBookDao extends BaseDao {

    public MfcNoBookDao(Session session) {
        super(session);
    }
    private Logger logger = Logger.getLogger(MfcNoBookDao.class);

    public MfcNoBook get(String noAcct, String refTxn, String typMsg, String typTrx) {
        MfcNoBook mfcNoBook = null;

        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT CAST(A.noAcct as VARCHAR2(16)), A.refTxn, A.typMsg, A.typAcct, A.dtPost, ");
        qryStr.append("A.dtValue, CAST(A.dtmTxn as Timestamp), CAST(A.ccyTxn as VARCHAR2(3)), A.amtTxn, A.ratFcyIdr, ");
        qryStr.append("A.amtTxnLcy, A.ratUsdIdr, A.amtTxnUsd, A.txnDesc, A.txnBranch, A.status, A.idTxn, ");
        qryStr.append("A.idCreatedBy, A.idCreatedSpv, CAST(A.dtmCreated as Timestamp), CAST(A.dtmCreatedSpv as Timestamp) ");
        qryStr.append("FROM MfcNoBook A ");
        qryStr.append("WHERE A.noAcct = CAST(:pNoAcct AS CHAR(16)) ");
        qryStr.append("AND A.refTxn = :pRefTxn ");
        qryStr.append("AND A.typMsg = :pTypMsg ");
        //qryStr.append("AND A.typAcct = :pTypAcct ");
        qryStr.append("AND A.typTrx = :pTypTrx ");
       
        logger.debug("QUERY2 :" + qryStr);
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pNoAcct", noAcct);
        q.setString("pRefTxn", refTxn);
        q.setString("pTypMsg", typMsg);
        q.setString("pTypTrx", typTrx);

        Object[] objMfcNoBook = (Object[]) q.uniqueResult();
        logger.debug("MFC :" + objMfcNoBook);
        if (objMfcNoBook != null) {
            MfcNoBookPK mfcNoBookPK = new MfcNoBookPK();
            mfcNoBookPK.setNoAcct((String) objMfcNoBook[0]);
            mfcNoBookPK.setRefTxn((String) objMfcNoBook[1]);
            mfcNoBookPK.setTypMsg(((Character) objMfcNoBook[2]).toString());
            mfcNoBookPK.setTypTrx(typTrx);

            mfcNoBook = new MfcNoBook();
            mfcNoBook.setCompositeId(mfcNoBookPK);
            mfcNoBook.setTypAcct(((Character) objMfcNoBook[3]).toString());
            mfcNoBook.setDtPost((Date) objMfcNoBook[4]);
            mfcNoBook.setDtValue((Date) objMfcNoBook[5]);
            mfcNoBook.setDtmTxn((Timestamp) objMfcNoBook[6]);
            mfcNoBook.setCcyTxn((String) objMfcNoBook[7]);
            mfcNoBook.setAmtTxn(new Double(((BigDecimal) objMfcNoBook[8]).doubleValue()));
            mfcNoBook.setRatFcyIdr(new Double(((BigDecimal) objMfcNoBook[9]).doubleValue()));
            mfcNoBook.setAmtTxnLcy(new Double(((BigDecimal) objMfcNoBook[10]).doubleValue()));
            mfcNoBook.setRatUsdIdr(new Double(((BigDecimal) objMfcNoBook[11]).doubleValue()));
            mfcNoBook.setAmtTxnUsd(new Double(((BigDecimal) objMfcNoBook[12]).doubleValue()));
            mfcNoBook.setTxnDesc((String) objMfcNoBook[13]);
            mfcNoBook.setTxnBranch(new Integer(((BigDecimal) objMfcNoBook[14]).intValue()));
            mfcNoBook.setStatus(((Character) objMfcNoBook[15]).toString());
            mfcNoBook.setIdTxn((String) objMfcNoBook[16]);
            mfcNoBook.setIdCreatedBy((String) objMfcNoBook[17]);
            mfcNoBook.setIdCreatedSpv((String) objMfcNoBook[18]);
            mfcNoBook.setDtmCreated((Timestamp) objMfcNoBook[19]);
            mfcNoBook.setDtmCreatedSpv((Timestamp) objMfcNoBook[20]);
        }

        return mfcNoBook;
    }

    public MfcNoBook get(String noAcct, String refTxn, String typMsg, String typAcct, String typTrx) {
        MfcNoBook mfcNoBook = null;

        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT CAST(A.noAcct as VARCHAR2(16)), A.refTxn, A.typMsg, A.typAcct, A.dtPost, ");
        qryStr.append("A.dtValue, CAST(A.dtmTxn as Timestamp), CAST(A.ccyTxn as VARCHAR2(3)), A.amtTxn, A.ratFcyIdr, ");
        qryStr.append("A.amtTxnLcy, A.ratUsdIdr, A.amtTxnUsd, A.txnDesc, A.txnBranch, A.status, A.idTxn, ");
        qryStr.append("A.idCreatedBy, A.idCreatedSpv, CAST(A.dtmCreated as Timestamp), CAST(A.dtmCreatedSpv as Timestamp) ");
        qryStr.append("FROM MfcNoBook A ");
        qryStr.append("WHERE A.noAcct = CAST(:pNoAcct AS CHAR(16)) ");
        qryStr.append("AND TRIM(A.refTxn) = :pRefTxn ");
        qryStr.append("AND A.typMsg = :pTypMsg ");
        qryStr.append("AND A.typTrx = :pTypTrx ");
        if (typAcct != null) {
            qryStr.append("AND A.typAcct = :pTypAcct ");
        }
        logger.debug("QUERY2 :" + qryStr);
        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pNoAcct", noAcct);
        q.setString("pRefTxn", refTxn);
        q.setString("pTypMsg", typMsg);
        if (typAcct != null) {
            q.setString("pTypAcct", typAcct);
        }
        q.setString("pTypTrx", typTrx);

        Object[] objMfcNoBook = (Object[]) q.uniqueResult();
        logger.debug("MFC :" + objMfcNoBook);
        if (objMfcNoBook != null) {
            MfcNoBookPK mfcNoBookPK = new MfcNoBookPK();
            mfcNoBookPK.setNoAcct((String) objMfcNoBook[0]);
            mfcNoBookPK.setRefTxn((String) objMfcNoBook[1]);
            mfcNoBookPK.setTypMsg(((Character) objMfcNoBook[2]).toString());
            mfcNoBookPK.setTypTrx(typTrx);

            mfcNoBook = new MfcNoBook();
            mfcNoBook.setCompositeId(mfcNoBookPK);
            mfcNoBook.setTypAcct(((Character) objMfcNoBook[3]).toString());
            mfcNoBook.setDtPost((Date) objMfcNoBook[4]);
            mfcNoBook.setDtValue((Date) objMfcNoBook[5]);
            mfcNoBook.setDtmTxn((Timestamp) objMfcNoBook[6]);
            mfcNoBook.setCcyTxn((String) objMfcNoBook[7]);
            mfcNoBook.setAmtTxn(new Double(((BigDecimal) objMfcNoBook[8]).doubleValue()));
            mfcNoBook.setRatFcyIdr(new Double(((BigDecimal) objMfcNoBook[9]).doubleValue()));
            mfcNoBook.setAmtTxnLcy(new Double(((BigDecimal) objMfcNoBook[10]).doubleValue()));
            mfcNoBook.setRatUsdIdr(new Double(((BigDecimal) objMfcNoBook[11]).doubleValue()));
            mfcNoBook.setAmtTxnUsd(new Double(((BigDecimal) objMfcNoBook[12]).doubleValue()));
            mfcNoBook.setTxnDesc((String) objMfcNoBook[13]);
            mfcNoBook.setTxnBranch(new Integer(((BigDecimal) objMfcNoBook[14]).intValue()));
            mfcNoBook.setStatus(((Character) objMfcNoBook[15]).toString());
            mfcNoBook.setIdTxn((String) objMfcNoBook[16]);
            mfcNoBook.setIdCreatedBy((String) objMfcNoBook[17]);
            mfcNoBook.setIdCreatedSpv((String) objMfcNoBook[18]);
            mfcNoBook.setDtmCreated((Timestamp) objMfcNoBook[19]);
            mfcNoBook.setDtmCreatedSpv((Timestamp) objMfcNoBook[20]);
        }

        return mfcNoBook;
    }

    public List list(String noAcct, String typMsg, String typAcct, String refTxn) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT A.*, CAST(A.ccyTxn as VARCHAR2(3)) AS strCcyTxn FROM MfcNoBook A ");
        qryStr.append("WHERE A.noAcct = CAST(:pNoAcct AS CHAR(16)) ");
        qryStr.append("AND A.typMsg = :pTypMsg ");
        qryStr.append("AND A.typAcct = :pTypAcct ");
        qryStr.append("AND A.refTxn LIKE :pRefTxn ");

        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pNoAcct", noAcct);
        q.setString("pTypMsg", typMsg);
        q.setString("pTypAcct", typAcct);
        q.setString("pRefTxn", refTxn + "%");
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return q.list();
    }

    public List list(String noAcct, Date dtPost, String typMsg, String status) {
        StringBuilder qryStr = new StringBuilder();
        qryStr.append("SELECT CAST(a.dtmtxn AS TIMESTAMP) AS DTMTXN, CAST(A.ccyTxn as VARCHAR2(3)) AS strCcyTxn, A.* FROM MfcNoBook A ");
        qryStr.append("WHERE A.noAcct = CAST(:pNoAcct AS CHAR(16)) ");
        qryStr.append("AND A.dtPost = :pDtPost ");
        qryStr.append("AND A.typMsg = :pTypMsg ");
        qryStr.append("AND A.status = :pStatus ");
        qryStr.append("AND A.typTrx = 'VALAS' ");

        Query q = getSession().createSQLQuery(qryStr.toString());
        q.setString("pNoAcct", noAcct);
        q.setDate("pDtPost", dtPost);
        q.setString("pTypMsg", typMsg);
        q.setString("pStatus", status);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return q.list();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((MfcNoBook) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((MfcNoBook) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((MfcNoBook) model);
        return true;
    }
}