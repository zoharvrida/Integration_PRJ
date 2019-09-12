/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.Com.ComBrnMast;
import bdsm.model.Com.ComCdDtls;
import bdsm.model.Com.ComCdDtlsHist;
import bdsm.model.Com.ComCdMast;
import bdsm.model.Com.ComCdMastHist;
import bdsm.model.Com.ComCdMastHistPK;
import bdsm.model.Com.ComDenomReq;
import bdsm.model.Com.ComDenomMast;
import bdsm.model.Com.TmpComTrx;
import bdsm.model.Com.TmpComTrxPK;
import bdsm.util.ClassConverterUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.Com.ComCdMastDao;
import bdsmhost.dao.Com.ComDenomMastDao;
import bdsmhost.dao.Com.ComTmpTrxDao;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.Transaction;

/**
 *
 * @author SDM
 */
@SuppressWarnings("serial")
public class ComCashDeliveryAction extends BaseHostAction {

    private String requestState;
    private String branch;
    private String currency;
    private String txnId;
    private String denomId;
    private String status;
    private String userId;
    private String txnType;
    private String menuName;
    private String dtmRequest;
    private String dateTxn;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat numbering = new SimpleDateFormat("ddMMyyyy");
    private List<Map<String, Object>> modelList;
    private ComDenomReq denom;
    private List<ComDenomReq> denomList;
    private List<ComDenomMast> denomMasterList;
    private String webSessionId;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        this.getLogger().info("BRANCH : " + this.branch);
        this.getLogger().info("Denom : " + this.denomId);
        this.getLogger().info("MENU : " + this.menuName);
        this.getLogger().info("STATUS :" + this.status);

        ComTmpTrxDao tmpTrxDao = new ComTmpTrxDao(getHSession());
        ComCdMastDao cdDao = new ComCdMastDao(getHSession());
        if ("21204".equals(menuName)) {
            Date dates = new Date();
            try {
                dates = dateFormat.parse(this.dateTxn);
                getLogger().info("DATEPARSE SUCCESS :" + dates);
            } catch (ParseException parseException) {
                getLogger().info("DATEPARSE FAILED :" + parseException, parseException);
                dates = SchedulerUtil.getTime();
            }
            this.modelList = cdDao.getlistDetails(this.userId, this.currency, dates);
            getLogger().debug("TRX LIST :" + modelList);
        } else if ("21506".equals(menuName)) {
            if ("1".equals(status)) {
                ComDenomMastDao denomDao = new ComDenomMastDao(getHSession());
                setModelList(denomDao.getDenomList(currency));
                getLogger().info("[END] QUERY END :" + getModelList());
                for (Map model : modelList) {
                    ClassConverterUtil.MapToClass(model, this.getDenom());
                    this.getDenom().setAmount("0");
                    this.getDenom().setPiece("0");
                    this.getDenomList().add(getDenom());
                }
            } else if ("2".equals(status)) {
                TmpComTrxPK pk = new TmpComTrxPK();
                setModelList(tmpTrxDao.getTmpList(pk));
            }
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        this.getLogger().info("BRANCH : " + this.branch);
        this.getLogger().info("VENDOR : " + this.denomId);
        this.getLogger().info("VENDOR : " + this.denomId);
        
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String doUpdateGrid() {
        this.getLogger().info("BRANCH : " + this.branch);
        this.getLogger().info("VENDOR : " + this.denomId);
        this.getLogger().info("STATUS :" + this.status);

        this.modelList = null;
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        ComCdMast cdMast = new ComCdMast();
        //ComCdDtls cdDtls = new ComCdDtls();
        ComCdMastHist mastHist = new ComCdMastHist();
        ComCdDtlsHist dtlsHist = new ComCdDtlsHist();

        List<ComCdDtls> cdDtlsList = new ArrayList();
        ComCdMastDao cdDao = new ComCdMastDao(getHSession());
        cdDao.insert(cdMast);
        cdDao.insert(mastHist);
        for (ComCdDtls cdDtl : cdDtlsList) {
            cdDao.insert(cdDtl);
            ComCdMastHistPK pk = new ComCdMastHistPK();

            //dtlsHist.setDtmLog(SchedulerUtil.getTime());
            dtlsHist.setTxnId(getTxnId());
            dtlsHist.setTxnDtlsId(cdDtl.getTxnDtlsId());
            cdDao.insert(dtlsHist);
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {

        ComCdMast cdMast = new ComCdMast();
        if (requestState.equals("CANCEL")) {
            cdMast.setStatus("4");
        } else if (requestState.equals("CONFIRM")) {
            cdMast.setStatus("3");
        } else if (requestState.equals("ASSIGNED")) {
            cdMast.setStatus("2");
        }
        //ComCdDtls cdDtls = new ComCdDtls();
        ComCdMastHist mastHist = new ComCdMastHist();
        ComCdDtlsHist dtlsHist = new ComCdDtlsHist();
        List<ComCdDtls> cdDtlsList = new ArrayList();
        ComCdMastDao cdDao = new ComCdMastDao(getHSession());
        cdDao.update(cdMast);
        cdDao.insert(mastHist);
        for (ComCdDtls cdDtl : cdDtlsList) {
            cdDao.update(cdDtl);
            ComCdMastHistPK pk = new ComCdMastHistPK();

            pk.setDtmLog(SchedulerUtil.getTime());
            //dtlsHist.setPk(pk);
            cdDao.insert(dtlsHist);
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String addrequest(){
        this.getLogger().info("BRANCH : " + this.branch);
        this.getLogger().info("txnID : " + this.getTxnId());
        this.getLogger().info("STATUS :" + this.status);
        this.getLogger().info("user : " + this.userId);
        Integer sequence = 0;
        
        ComTmpTrxDao tmpDao = new ComTmpTrxDao(getHSession());
        ComCdMastDao cdDao = new ComCdMastDao(getHSession());
        List getSeq = SchedulerUtil.getParameter(txnId, "-");
        sequence = Integer.parseInt(getSeq.get(getSeq.size()-1).toString().substring(8));
        
        TmpComTrxPK pk = new TmpComTrxPK(userId, currency, status, getTxnId(),getTxnType(),sequence);
        List<TmpComTrx> comTrx = tmpDao.getTmpList(pk);
        
        ComCdMast cdMast = new ComCdMast();
        ComCdMastHist cdMHist = new ComCdMastHist();
        
        cdMast.setCodCcy(currency);
        cdMast.setIdBranch(Integer.parseInt(this.branch));
        cdMast.setTxnId(getTxnId());
        cdMast.setStatus(status);
        
        ClassConverterUtil.ReferenceClass(cdMast,cdMHist);
        
        for(TmpComTrx trx : comTrx){
            ComCdDtls dtls = new ComCdDtls();
            ClassConverterUtil.ReferenceClass(trx, dtls);
            ClassConverterUtil.ReferenceClass(trx.getPk(), dtls);
        }
        Transaction tx = getHSession().beginTransaction();
            cdDao.insert(cdMast);
        tx.commit();
        tx.begin();
            
        tx.commit();
        return SUCCESS;
    }
    /**
     * 
     * @return
     */
    @JSON(serialize=false)
    public String getTxn() {
        this.getLogger().info("BRANCH : " + this.branch);
        this.getLogger().info("DATTXN : " + this.dateTxn);
        StringBuilder txnPhrase = new StringBuilder();
		
        Transaction tx = getHSession().beginTransaction();
        ComTmpTrxDao tmpDao = new ComTmpTrxDao(getHSession());
        ComCdMastDao cdDao = new ComCdMastDao(getHSession());
        Integer seq = 1;
        
        Date dates = new Date();
        try {
            dates = dateFormat.parse(this.dateTxn);
            getLogger().info("DATEPARSE SUCCESS :" + dates);
        } catch (ParseException parseException) {
            getLogger().info("DATEPARSE FAILED :" + parseException, parseException);
            dates = SchedulerUtil.getTime();
        }

        try {
            List<ComBrnMast> brnMast = tmpDao.getBrnPair(branch, "2");
            
            txnPhrase.append(brnMast.get(0).getIdBrn()).append("-")
                    .append(brnMast.get(0).getIdBranchParent()).append("-")
                    .append(numbering.format(dates));
            
            List<ComCdDtls> tmpMast = cdDao.checkExisting(txnPhrase.toString());
            if(tmpMast.isEmpty()){
            } else {
                seq = tmpMast.get(0).getSeqId();         
            }
            txnId = txnPhrase.append(seq).toString(); 
            TmpComTrxPK pk = new TmpComTrxPK(userId, currency, status, getTxnId(),getTxnType());
            TmpComTrx tmpDel = new TmpComTrx();
            tmpDel.setPk(pk);
            tmpDao.delete(tmpDel);
            tx.commit();
        } catch (Exception e) {
            this.getLogger().info("FAILED TO CONVERT : " + e, e);
        }
        return SUCCESS;
    }
    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the requestState
     */
    public String getRequestState() {
        return requestState;
    }

    /**
     * @param requestState the requestState to set
     */
    public void setRequestState(String requestState) {
        this.requestState = requestState;
    }

    /**
     * @return the modelList
     */
    public List<Map<String, Object>> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<Map<String, Object>> modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the denomId
     */
    public String getDenomId() {
        return denomId;
    }

    /**
     * @param denomId the denomId to set
     */
    public void setDenomId(String denomId) {
        this.denomId = denomId;
    }

    /**
     * @return the denom
     */
    public ComDenomReq getDenom() {
        return denom;
    }

    /**
     * @param denom the denom to set
     */
    public void setDenom(ComDenomReq denom) {
        this.denom = denom;
    }

    /**
     * @return the denomList
     */
    public List<ComDenomReq> getDenomList() {
        return denomList;
    }

    /**
     * @param denomList the denomList to set
     */
    public void setDenomList(List<ComDenomReq> denomList) {
        this.denomList = denomList;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the dtmRequest
     */
    public String getDtmRequest() {
        return dtmRequest;
    }

    /**
     * @param dtmRequest the dtmRequest to set
     */
    public void setDtmRequest(String dtmRequest) {
        this.dtmRequest = dtmRequest;
    }

    /**
     * @param txnId the txnId to set
     */
    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }

    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    /**
     * @return the dateTxn
     */
    public String getDateTxn() {
        return dateTxn;
    }

    /**
     * @param dateTxn the dateTxn to set
     */
    public void setDateTxn(String dateTxn) {
        this.dateTxn = dateTxn;
    }

    /**
     * @return the txnId
     */
    public String getTxnId() {
        return txnId;
    }
}
