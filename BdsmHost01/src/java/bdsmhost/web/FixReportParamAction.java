/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.rpt.dao.FixReportParamDao;
import bdsm.rpt.dao.FixReportReqMasterDao;
import bdsm.rpt.dao.FixReportReqParamsDao;
import bdsm.rpt.model.*;
import bdsm.scheduler.ScheduleDefinition;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.EncryptionUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.AuditlogDao;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public class FixReportParamAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;
    private static final String Log = "LOG";
    //private String listQ;
    private List modelList;
    private FixReportParam model;
    private FixQXtract modelQ;
    private FixReportReqMaster modelM;
    private FixReportReqParams modelP;
    private FixMasterReport modelR;
    private String sequential;
    /**
     * 
     */
    protected Map context;
    private String oldEncrypt;
    private String batch;
    private String qid;
    private FixReportReqMaster modelResp;
    
    private ArrayList value = new ArrayList();
    private ArrayList oldValue = new ArrayList();
    private ArrayList fieldName = new ArrayList();  
    // hidden param
    private String idUser;
    private String cdBranch;
    private String idTemplate;
    private String dtProcess;
    private String namUser;
    private String namTemplate;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        FixReportParamDao dao = new FixReportParamDao(getHSession());

        setModelList(dao.list(getModel().getCompositeId().getIdReport()));
        //List<FixReportParam> lList = dao.list(getModel().getCompositeId().getIdReport());
        //getLogger().info("ModelList = " + lList);
        //getLogger().info("modelnya  = " + lList.get(0));
        getLogger().info("list Suceess :" + String.valueOf(getModelList().size()));
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        FixReportReqMasterDao dao = new FixReportReqMasterDao(getHSession());
        FixReportReqMasterPK pk = new FixReportReqMasterPK();
        FixReportReqMaster reportC = new FixReportReqMaster();
        getLogger().info("Report ID Batch :" + modelM.getCompositeId().getIdBatch());
        pk.setIdBatch(modelM.getCompositeId().getIdBatch());
        FixReportReqMaster report = dao.get(pk);
        
        reportC.setFilePath(report.getFilePath());
        setModelResp(reportC);
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        StringBuilder mainParam = new StringBuilder();
        getLogger().info("modelQ 1:" + modelQ.getParam1());
        getLogger().info("modelQ 2:" + modelQ.getParam2());
        getLogger().info("modelQ 3:" + modelQ.getParam3());
        getLogger().info("modelQ 4:" + modelQ.getParam4());
        getLogger().info("modelQ 5:" + modelQ.getParam5());
        getLogger().info("modelQ format:" + modelQ.getIdScheduler());
        getLogger().info("modelQ idUser :" + modelQ.getIdMaintainedBy());
        getLogger().info("modelQ idSpv :" + modelQ.getIdMaintainedSpv());
        getLogger().info("modelR Remarks :" + modelR.getRemarks());
        getLogger().info("modelM id Request :" + modelM.getIdRequest());
        getLogger().info("ID USER :" + getIdUser());
        getLogger().info("BRANCH CODE:" + getCdBranch());
        getLogger().info("ID TEMPLATE :" + getIdTemplate());
        getLogger().info("DT PROCC :" + getDtProcess());
        getLogger().info("nama User :" + getNamUser());
        getLogger().info("nama Template :" + getNamTemplate());
        getLogger().info("QID :" + modelQ.getqId());
        
        mainParam.append(getIdUser()).append("~");

        mainParam.append(getModelQ().getIdScheduler()).append("~");

        if (Log.equalsIgnoreCase(modelR.getRemarks())) {
            mainParam.append(modelQ.getParam1()).append("~");
            if (modelQ.getParam2() != null) {
                try {
                    setOldEncrypt(EncryptionUtil.getAES(modelQ.getParam2(), "ZIP@@@@@@@@@@@@@", 1));
                    modelQ.setParam2(oldEncrypt);
                } catch (Exception ex) {
                    Logger.getLogger(FixReportParamAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                getLogger().info("Encryption Key : " + getOldEncrypt());
            } else {
                int temp = Integer.parseInt(sequential)-1;
                sequential = String.valueOf(temp);
            }
        } else {
            String reportfileresult = getModelR().getReportName().replace("{branch}", getCdBranch());
            reportfileresult = reportfileresult.replace("{user}", getIdUser());
            try {
            reportfileresult = reportfileresult.replace("{param1}", modelQ.getParam1());
            reportfileresult = reportfileresult.replace("{param2}", modelQ.getParam2());
            reportfileresult = reportfileresult.replace("{param3}", modelQ.getParam3());
            reportfileresult = reportfileresult.replace("{param4}", modelQ.getParam4());
            reportfileresult = reportfileresult.replace("{param5}", modelQ.getParam5());
            } catch (Exception e) {

            }
            mainParam.append(FileUtil.getDateTimeFormatedString(reportfileresult + "{yyMMdd-HHmmss}")).append("~");
        }
        mainParam.append(getModelM().getIdReport()).append("~");
        mainParam.append(modelR.getRemarks()).append("~");
        mainParam.append(getCdBranch()).append("~");
        mainParam.append(getNamUser()).append("~");
        mainParam.append(getDtProcess()).append("~");
        mainParam.append(getIdTemplate()).append("~");
        mainParam.append(getNamTemplate()).append("~");
        mainParam.append(getModelQ().getParam1());
        
        getLogger().info("mainParam :" + mainParam);
        getLogger().info("modelM_request  :" + modelM.getIdRequest());
        getLogger().info("modelM_report :" + modelM.getIdReport());
        getLogger().info("modelQ_idScheduler :" + modelQ.getIdScheduler());
        getLogger().info("sequential :" + sequential);
        getLogger().info("QID :" + modelQ.getqId());

        getHSession().persist(modelQ);
        getLogger().info("Report Entity :" +  modelQ.getqId());
        
        modelQ.setFlgProcess(StatusDefinition.REQUEST);
        modelQ.setParam6(SchedulerUtil.generateUUID());

        getLogger().info("Change to first Input");
        
        
        if (ScheduleDefinition.LOG.equals(modelR.getRemarks())) {
            modelQ.setIdScheduler(StatusDefinition.LOGSCHEDULER); // hardcode
        } else if (ScheduleDefinition.Email.equals(modelR.getRemarks())){
            modelQ.setIdScheduler(StatusDefinition.EMAILSCHEDULER); // hardcode
        } else {
            modelQ.setIdScheduler(StatusDefinition.SCREENSCHEDULER);
        }
        modelQ.setFlgProcess(getModelQ().getFlgProcess());
        modelQ.setDtmRequest(SchedulerUtil.getTime());
        if ("".equals(getModelQ().getParam1())) {
            getModelQ().setParam1("NULL");
        }
        if ("".equals(getModelQ().getParam2())) {
            getModelQ().setParam2("NULL");
        }
        if ("".equals(getModelQ().getParam3())) {
            getModelQ().setParam3("NULL");
        }
        if ("".equals(getModelQ().getParam4())) {
            getModelQ().setParam4("NULL");
        }
        if ("".equals(getModelQ().getParam5())) {
            getModelQ().setParam5("NULL");
        }
        modelQ.setParam1(mainParam.toString());
        getLogger().debug("persistence : " + modelQ.toString());
        getLogger().info("testing");
        getLogger().info("LAST SCHEDULER : " + modelQ.getIdScheduler()); 
        
        this.setBatch(getModelQ().getParam6());
        setQid(modelQ.getqId().toString());
        getLogger().info("QID :" + modelQ.getqId());
        getLogger().info("persistence :" + modelQ.getFlgProcess());
        //getHSession().flush();
        doInsert();
        super.setErrorCode("success.7");
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
    public String doInsert() {
        String param1 = modelQ.getParam1();
        getLogger().info("BACTHING CODE:" + batch);
        getLogger().info("QID Hibernate2:" + getQid());
        int k = 0;
        //Integer qID = qDao.get(100, getModelQ().getParam6()).getqId();
        //getLogger().info("Test QID :" + qID);
        
        int seqTransform = Integer.parseInt(sequential);

        FixReportReqParamsDao pDao = new FixReportReqParamsDao(getHSession());
        
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        getLogger().info("Audit");
        
        StringBuilder fileSname = new StringBuilder();

        String reportfileresult = getModelR().getReportName().replace("{branch}", getCdBranch());
        reportfileresult = reportfileresult.replace("{user}", getIdUser());
        try {
        reportfileresult = reportfileresult.replace("{param1}", modelQ.getParam1());
        reportfileresult = reportfileresult.replace("{param2}", modelQ.getParam2());
        reportfileresult = reportfileresult.replace("{param3}", modelQ.getParam3());
        reportfileresult = reportfileresult.replace("{param4}", modelQ.getParam4());
        reportfileresult = reportfileresult.replace("{param5}", modelQ.getParam5());
        } catch (Exception e) {
        
        }
        fileSname.append(FileUtil.getDateTimeFormatedString(reportfileresult + "{yyMMdd-HHmmss}"));
        getLogger().info("fileName : " + fileSname);
        String filename = fileSname.toString();
        
        //FixQXtract persistence = dao.get(getModelQ().getIdScheduler());
        for (int i = 0; i < seqTransform; i++) {
            FixReportReqParams setting = new FixReportReqParams();
            FixReportReqParamsPK pk = new FixReportReqParamsPK();
            
            for(int z=0; z<6;z++ ){
                  oldValue.add("");
            }
            getLogger().info("Old Valuenya :" + oldValue.size());
            getLogger().info("getBatch_value :" + getModelQ().getParam6());
            
            k = i + 1;
            value.add(batch);
            value.add(getModelM().getIdReport().toString());
            value.add(String.valueOf(k));
            
            try {
                getLogger().info("AUDIT_TRACK");
                if (i == 0 && !"null".equals(modelQ.getParam1()) && modelQ.getParam1()!= null) {
                    pk.setIdBatch(batch);
                    pk.setIdReport(getModelM().getIdReport());
                    pk.setNoSeq(k);
                    pk.setValue(param1);
                    
                    setting.setCompositeId(pk);
                    setting.setIdMaintainedBy(getModelQ().getIdMaintainedBy());
                    setting.setIdMaintainedSpv(getModelQ().getIdMaintainedSpv());
                    
                    value.add(modelQ.getParam1().toString());
                    
                } else if (i == 1 && !"null".equals(modelQ.getParam2()) && modelQ.getParam2()!= null) {
                    pk.setIdBatch(batch);
                    pk.setIdReport(getModelM().getIdReport());
                    pk.setNoSeq(k);
                    pk.setValue(modelQ.getParam2());
                    
                    setting.setCompositeId(pk);
                    setting.setIdMaintainedBy(getModelQ().getIdMaintainedBy());
                    setting.setIdMaintainedSpv(getModelQ().getIdMaintainedSpv());                    
                    
                    value.add(modelQ.getParam2().toString());
                    
                } else if (i == 2 && !"null".equals(modelQ.getParam3()) && modelQ.getParam3()!= null) {
                    pk.setIdBatch(batch);
                    pk.setIdReport(getModelM().getIdReport());
                    pk.setNoSeq(k);
                    pk.setValue(modelQ.getParam3());
                    
                    setting.setCompositeId(pk);
                    setting.setIdMaintainedBy(getModelQ().getIdMaintainedBy());
                    setting.setIdMaintainedSpv(getModelQ().getIdMaintainedSpv());
                    
                    value.add(modelQ.getParam3().toString());
                } else if (i == 3 && !"null".equals(modelQ.getParam4()) && modelQ.getParam4()!= null) {
                    pk.setIdBatch(batch);
                    pk.setIdReport(getModelM().getIdReport());
                    pk.setNoSeq(k);
                    pk.setValue(modelQ.getParam4());
                    
                    setting.setCompositeId(pk);
                    setting.setIdMaintainedBy(getModelQ().getIdMaintainedBy());
                    setting.setIdMaintainedSpv(getModelQ().getIdMaintainedSpv());
                    
                    value.add(modelQ.getParam4().toString());
                } else if (i == 4 && !"null".equals(modelQ.getParam5()) && modelQ.getParam5()!= null) {
                    pk.setIdBatch(batch);
                    pk.setIdReport(getModelM().getIdReport());
                    pk.setNoSeq(k);
                    pk.setValue(modelQ.getParam5());
                    
                    setting.setCompositeId(pk);
                    setting.setIdMaintainedBy(getModelQ().getIdMaintainedBy());
                    setting.setIdMaintainedSpv(getModelQ().getIdMaintainedSpv());
                    
                    value.add(modelQ.getParam5().toString());
                } else {
                    setting = null;
                }
            } catch (Exception e) {
                getLogger().info("EXCEPTION :" + e);
            }
            if (setting != null) {

                pDao.insert(setting);
                value.add(getModelQ().getIdMaintainedBy());
                value.add(getModelQ().getIdMaintainedSpv());
                fieldName.add("IDBATCH");
                fieldName.add("IDREPORT");
                fieldName.add("NOSEQ");
                fieldName.add("VALUE");
                fieldName.add("IDUSER");
                fieldName.add("IDUSERSPV");
                try {
                    auditdao.runPackage(getNamMenu(),
                        "fixreportreqparams",
                        getModelQ().getIdMaintainedBy(),
                        getModelQ().getIdMaintainedSpv(),
                        "Add", "Insert", value, oldValue, fieldName);
                } catch (SQLException ex) {
                    Logger.getLogger(FixReportParamAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                getLogger().info("setting :" + setting);
                
                getLogger().info("nilai Akhir : " + oldValue);
                getLogger().info("check Value : " + value);
                oldValue = new ArrayList();
                value = new ArrayList();
                fieldName = new ArrayList();
        
            }

        }
        oldValue = new ArrayList();
        value = new ArrayList();
        fieldName = new ArrayList();
        
        FixReportReqMasterDao mDao = new FixReportReqMasterDao(getHSession());
        FixReportReqMasterPK pkM = new FixReportReqMasterPK();

        pkM.setIdBatch(batch);
        FixReportReqMaster master = mDao.get(pkM);

        getLogger().info("modelM_request  :" + modelM.getIdRequest());
        getLogger().info("modelM_report :" + modelM.getIdReport());
        getLogger().info("ID_BACTH : " + getModelQ().getParam6());
        
        FixReportReqMaster newMaster = new FixReportReqMaster();
        if (master != null) {
            pkM.setIdBatch(batch);
            master.setIdReport(getModelM().getIdReport());
            master.setIdRequest(getIdUser());
            master.setFilePath(filename);
            master.setStatus("Queing");
            master.setDtmRequest(SchedulerUtil.getTime());
            master.setCompositeId(pkM);
            master.setQid(Integer.parseInt(getQid()));

            getLogger().info("upDATE!!");
            mDao.update(master);
            
            getLogger().info("master :" + master);
        } else {
            pkM.setIdBatch(batch);
            newMaster.setIdReport(modelM.getIdReport());
            newMaster.setIdRequest(getIdUser());
            newMaster.setStatus("Queing");
            newMaster.setFilePath(filename);
            newMaster.setDtmRequest(SchedulerUtil.getTime());
            newMaster.setCompositeId(pkM);
            newMaster.setQid(Integer.parseInt(getQid()));
            //master.setQid(qID);
            
            mDao.insert(newMaster);
            getLogger().info("master Insert :" + newMaster);
        }
        FixQXtractDao qDao = new FixQXtractDao(getHSession());
        FixQXtract persistence = qDao.get(modelQ.getIdScheduler(),modelQ.getParam6());
        
        getLogger().info("PERSISTENCE :" + persistence);
        
        if(persistence != null){
            qDao.update(modelQ);
        } else {
            qDao.insert(modelQ);
        }
        super.setErrorCode("success.7");
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param pathName
     * @param dirName
     * @return
     */
    public String getDirectory(String pathName, String dirName) {
        String parent = (new File(pathName).getParent());
        String dirPath = parent + "\\" + dirName;
        getLogger().info("Folder : " + dirPath);

        File dir = new File(dirPath);

        try {
            if (!dir.exists()) {
                boolean success = dir.mkdir();
                if (!success) {
                    return null;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return dirPath;
    }

    /**
     * @return the modelList
     */
    public List getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the model
     */
    public FixReportParam getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FixReportParam model) {
        this.model = model;
    }

    /**
     * @return the modelQ
     */
    public FixQXtract getModelQ() {
        return modelQ;
    }

    /**
     * @param modelQ the modelQ to set
     */
    public void setModelQ(FixQXtract modelQ) {
        this.modelQ = modelQ;
    }

    /**
     * @return the modelM
     */
    public FixReportReqMaster getModelM() {
        return modelM;
    }

    /**
     * @param modelM the modelM to set
     */
    public void setModelM(FixReportReqMaster modelM) {
        this.modelM = modelM;
    }

    /**
     * @return the modelP
     */
    public FixReportReqParams getModelP() {
        return modelP;
    }

    /**
     * @param modelP the modelP to set
     */
    public void setModelP(FixReportReqParams modelP) {
        this.modelP = modelP;
    }

    /**
     * @return the sequential
     */
    public String getSequential() {
        return sequential;
    }

    /**
     * @param sequential the sequential to set
     */
    public void setSequential(String sequential) {
        this.sequential = sequential;
    }

    /**
     * @return the modelR
     */
    public FixMasterReport getModelR() {
        return modelR;
    }

    /**
     * @param modelR the modelR to set
     */
    public void setModelR(FixMasterReport modelR) {
        this.modelR = modelR;
    }

    /**
     * @return the oldEncrypt
     */
    public String getOldEncrypt() {
        return oldEncrypt;
    }

    /**
     * @param oldEncrypt the oldEncrypt to set
     */
    public void setOldEncrypt(String oldEncrypt) {
        this.oldEncrypt = oldEncrypt;
    }

    /**
     * @return the batch
     */
    public String getBatch() {
        return batch;
    }

    /**
     * @param batch the batch to set
     */
    public void setBatch(String batch) {
        this.batch = batch;
    }

    /**
     * @return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the cdBranch
     */
    public String getCdBranch() {
        return cdBranch;
    }

    /**
     * @param cdBranch the cdBranch to set
     */
    public void setCdBranch(String cdBranch) {
        this.cdBranch = cdBranch;
    }

    /**
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the dtProcess
     */
    public String getDtProcess() {
        return dtProcess;
    }

    /**
     * @param dtProcess the dtProcess to set
     */
    public void setDtProcess(String dtProcess) {
        this.dtProcess = dtProcess;
    }

    /**
     * @return the namUser
     */
    public String getNamUser() {
        return namUser;
    }

    /**
     * @param namUser the namUser to set
     */
    public void setNamUser(String namUser) {
        this.namUser = namUser;
    }

    /**
     * @return the namTemplate
     */
    public String getNamTemplate() {
        return namTemplate;
    }

    /**
     * @param namTemplate the namTemplate to set
     */
    public void setNamTemplate(String namTemplate) {
        this.namTemplate = namTemplate;
    }

    /**
     * @return the qid
     */
    public String getQid() {
        return qid;
    }

    /**
     * @param qid the qid to set
     */
    public void setQid(String qid) {
        this.qid = qid;
    }

    /**
     * @return the modelResp
     */
    public FixReportReqMaster getModelResp() {
        return modelResp;
    }

    /**
     * @param modelResp the modelResp to set
     */
    public void setModelResp(FixReportReqMaster modelResp) {
        this.modelResp = modelResp;
    }
}
