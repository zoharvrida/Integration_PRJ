/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.RekeningKoran;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class RekeningKoranDAO extends BaseDao {

    DateFormat formatterDate1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat formatterDate2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public RekeningKoranDAO(Session session) {
        super(session);
    }

    protected Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    @SuppressWarnings("unchecked")
    public List<RekeningKoran> listRekKoran(String acctNo,String startDate, String endDate) {
        List<RekeningKoran> rekeningKoran = new ArrayList<RekeningKoran>();
        try {
            this.getLogger().info("list Rek Koran");
            Query query = this.getSession().getNamedQuery("RekeningKoran#getData");
            query.setString("acctNo", acctNo);
            query.setString("startDate", startDate);
            query.setString("endDate", endDate);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            
            List<Map> list = query.list();
            this.getLogger().info("HASIL LIST " + list.size());
            this.getLogger().info("HASIL LIST MAP " + list);
            for (Map getList : list) {
                if (getList == null) {
                    break;
                } else {
                    RekeningKoran rekKoran = new RekeningKoran();
                    rekKoran.setSenderName(getList.get("SenderName").toString());
                    rekKoran.setReceiverName(getList.get("ReceiverName").toString());
                    rekKoran.setCreationDateTime(getList.get("CreationDateTime").toString());
                    rekKoran.setMessageIdentifier(getList.get("MessageIdentifier").toString());
                    rekKoran.setMessageType(getList.get("MessageType").toString());
                    rekKoran.setBankCode(getList.get("BankCode").toString());
                    rekKoran.setBankAccountNo(getList.get("BankAccountNumber").toString());
                    rekKoran.setBankStatementDate(formatterDate1.parse(getList.get("BankStatementDate").toString()));
                    rekKoran.setCurrency(getList.get("Currency").toString());
                    rekKoran.setBeginningBalance(new BigDecimal(getList.get("BeginningBalance").toString()));
                    rekKoran.setEndingBalance(new BigDecimal(getList.get("EndingBalance").toString()));
                    rekKoran.setLineNumber(getList.get("LineNumber").toString());
                    rekKoran.setBankTransactionCode(getList.get("BankTransactionCode") == null ? "" : getList.get("BankTransactionCode").toString());
                    rekKoran.setDebitCredit(getList.get("DebitCredit").toString());
                    rekKoran.setBankReferenceNumber(getList.get("BankReferenceNumber") == null ? "" : getList.get("BankReferenceNumber").toString());
                    rekKoran.setTransactionDate(formatterDate1.parse(getList.get("TransactionDate").toString()));
                    rekKoran.setValueDate(formatterDate1.parse(getList.get("ValueDate").toString()));
                    rekKoran.setOriginalAmount(new BigDecimal(getList.get("OriginalAmount").toString()));
                    rekKoran.setVersionText(getList.get("VersionText").toString());
                    rekeningKoran.add(rekKoran);
                }
            }
        } catch (Exception e) {
            getLogger().info("LOG ERROR-listRekKoran " + e);
            e.printStackTrace();
        }
        return rekeningKoran;
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save(model);
        return true;
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
