/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.model.FixEmailAccess;
import bdsm.scheduler.model.FixEmailAccessPK;
import bdsmhost.dao.AuditlogDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Transaction;

/**
 *
 * @author v00019722
 */
public class FixAccessEmailAction extends BaseHostAction {

    private List model;
    //private String modelReq;
    //private String modelSup;
    //private String modelMon;
    private String requeSter;
    private String superVisor;
    private String moniToring;
    private FixEmailAccess modelObj;
    private FixEmailAccessPK modelStore;
    private String token;
    private ArrayList value = new ArrayList();
    private ArrayList oldValue = new ArrayList();
    private ArrayList fieldName = new ArrayList();
    private ArrayList whiteList = new ArrayList();
    private List<FixEmailAccess> fiXAcc = new ArrayList<FixEmailAccess>();

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
        getLogger().info("Begin List Object");
        FixEmailAccessDao dao = new FixEmailAccessDao(getHSession());
        model = dao.list();
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        getLogger().info("test isi requester Server " + getRequeSter());
        getLogger().info("test isi supervisor Server " + getSuperVisor());
        getLogger().info("test isi monitoring Server " + getMoniToring());
        getLogger().info("test id User " + getModelObj().getIdMaintainedBy());
        getLogger().info("test id spv " + getModelObj().getIdMaintainedSpv());
        getLogger().info("GROUP ID " + getModelObj().getGrpId());
        getLogger().info("nama Menu : " + getNamMenu());
        getLogger().info("id Scheduler " + getModelObj().getFixEmailAccessPK().getIdScheduler());
        getLogger().info("Token " + getToken());

        ArrayList<String> cdAccess = new ArrayList<String>();
        FixEmailAccess modelReq = new FixEmailAccess();
        FixEmailAccess modelSup = new FixEmailAccess();
        FixEmailAccess modelMon = new FixEmailAccess();

        String[] requesteR = null;
        String[] superVisor = null;
        String[] moniToring = null;
        int reqList = 0;
        int reqDummy = 0;
        int supDummy = 0;
        int monDummy = 0;

        FixEmailAccess modelObjR = new FixEmailAccess();
        modelObjR = modelObj;
        modelReq = modelObjR;
        modelReq.setCdAccess("100");

        FixEmailAccessDao dao = new FixEmailAccessDao(getHSession());

        //fiXAcc = dao.emailing(modelReq.getGrpId(), modelReq.getFixEmailAccessPK().getIdScheduler());
        //getLogger().info("LIST FIX :" + fiXAcc);
        List<FixEmailAccess> FIxR = new ArrayList<FixEmailAccess>();
        //List<FixEmailAccess> FIx = new ArrayList<FixEmailAccess>();
        FIxR = dao.list(modelReq.getGrpId(),
                modelReq.getCdAccess(),
                modelReq.getFixEmailAccessPK().getIdScheduler());
        StringBuilder str = new StringBuilder();
        if (FIxR != null) {
            for (int r = 0; r < FIxR.size(); r++) {
                str.append(FIxR.get(r).getFixEmailAccessPK().getSender()).append(";");
                getLogger().info("REQUESTER :" + str);
            }
        }
        //FixAccessEmailDao dao = new FixAccessEmailDao(getHSession());

        FixEmailAccess modelObjS = new FixEmailAccess();
        modelObjS = modelObj;
        modelSup = modelObjS;
        modelSup.setCdAccess("010");
        
        List<FixEmailAccess> FIxS = new ArrayList<FixEmailAccess>();
        FIxS = dao.list(modelSup.getGrpId(),
                modelSup.getCdAccess(),
                modelSup.getFixEmailAccessPK().getIdScheduler());
        StringBuilder sts = new StringBuilder();
        if (FIxS != null) {
            for (int s = 0; s < FIxS.size(); s++) {
                sts.append(FIxS.get(s).getFixEmailAccessPK().getSender()).append(";");
                getLogger().info("SUPERVISOR :" + sts);
            }
        }

        FixEmailAccess modelObjM = new FixEmailAccess();
        modelObjM = modelObj;
        modelMon = modelObjM;
        modelMon.setCdAccess("001");

        List<FixEmailAccess> FIxM = new ArrayList<FixEmailAccess>();
        FIxM = dao.list(modelMon.getGrpId(),
                modelMon.getCdAccess(),
                modelMon.getFixEmailAccessPK().getIdScheduler());
        StringBuilder stm = new StringBuilder();
        if (FIxM != null) {
            for (int m = 0; m < FIxM.size(); m++) {
                stm.append(FIxM.get(m).getFixEmailAccessPK().getSender()).append(";");
                getLogger().info("MONITORING :" + stm);
            }
        }
        cdAccess.add("100"); // requester
        cdAccess.add("010"); // supervisor
        cdAccess.add("001"); // monitoring
        
        if (!"".equals(getRequeSter())) {

            requesteR = getRequeSter().toLowerCase().split(";");
            reqList = requesteR.length;
            modelReq.setCdAccess("100");
            
            getLogger().info("requester size " + reqList); // request object
            getLogger().info("persistence Request list =" + FIxR.size());
            getLogger().info("REQUESTER new : " + getRequeSter());
            getLogger().info("req cdAccess: " + modelReq.getCdAccess());
            getLogger().info("req sender  : " + modelReq.getFixEmailAccessPK().getSender());
            try {
                reqDummy = Validation(FIxR,FIxS,FIxM, 
                                        str,sts, stm, 
                                        getRequeSter(),
                                        cdAccess.get(0),
                                        cdAccess.get(1),cdAccess.get(2), 
                                        dao, 1, "REQUESTER");
            } catch (SQLException ex) {
                Logger.getLogger(FixAccessEmailAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLogger().info(" Requester Dummy : " + oldValue.size()); // deleting object
            getLogger().info(" new Value : " + value.size()); // insert object

        }
        getLogger().info("WHITE LIST :" + whiteList.size());
        if (!"".equals(getSuperVisor())) {

            superVisor = getSuperVisor().toLowerCase().split(";");
            reqList = superVisor.length;
            modelSup.setCdAccess("010");

            getLogger().info("superVisor size " + reqList); // request object
            getLogger().info("persistence superVisor list =" + FIxS.size());
            getLogger().info("SUPERVISOR new :" + getSuperVisor());
            getLogger().info("supervisor cdAccess:" + modelSup.getCdAccess());
            getLogger().info("sup sender  : " + modelSup.getFixEmailAccessPK().getSender());
            try {
                supDummy = Validation(FIxS,FIxM, FIxR,
                                        sts, stm, str, 
                                        getSuperVisor(),
                                        cdAccess.get(1), 
                                        cdAccess.get(2),cdAccess.get(0),
                                        dao, 2, "SUPERVISOR");
            } catch (SQLException ex) {
                Logger.getLogger(FixAccessEmailAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLogger().info(" Supervisor Dummy : " + oldValue.size()); // deleting object
            getLogger().info(" new Value : " + value.size()); // insert object

            //super.setErrorCode("success.1");
            //return SUCCESS;
        }
        getLogger().info("WHITE LIST :" + whiteList.size());
        if (!"".equals(getMoniToring())) {

            moniToring = getMoniToring().toLowerCase().split(";");
            reqList = moniToring.length;
            modelMon.setCdAccess("001");

            getLogger().info("moniToring size " + reqList); // request object
            getLogger().info("persistence moniToring list =" + FIxM.size());
            getLogger().info("MONITORING new :" + getMoniToring());
            getLogger().info("monitor cdAccess:" + modelMon.getCdAccess());
            getLogger().info("Mon sender  : " + modelMon.getFixEmailAccessPK().getSender());
            try {
                monDummy = Validation(FIxM,FIxR, FIxS,
                                        stm, str, sts, 
                                        getMoniToring(),
                                        cdAccess.get(2), 
                                        cdAccess.get(0),cdAccess.get(1),
                                        dao, 3, "MONITORING");
            } catch (SQLException ex) {
                Logger.getLogger(FixAccessEmailAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLogger().info(" Monitor Dummy : " + oldValue.size()); // deleting object
            getLogger().info(" new Value : " + value.size()); // insert object

            //super.setErrorCode("success.1");
        } else { // delete all Monitoring in Database
            for (int mScan = 0; mScan < FIxM.size(); mScan++) {
                 getLogger().info(" Build up String (delete phase): " + stm.toString());
                 dao.delete(DeleteInstance("001", FIxM, mScan));
            }
        }
        getLogger().info("WHITE LIST :" + whiteList.size());
        getLogger().info(" reqDummy : " + reqDummy);
        getLogger().info(" supDummy : " + supDummy);
        if (reqDummy != 0 && supDummy != 0) {
            super.setErrorCode("success.1");
            return SUCCESS;
        } else {
            super.setErrorCode("error.0");
            return ERROR;
        }
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
     * @param FiX
     * @param FiX2
     * @param FiX3
     * @param str
     * @param sts
     * @param stm
     * @param Email
     * @param code1
     * @param code2
     * @param code3
     * @param dao
     * @param Code
     * @param flaG
     * @return
     * @throws SQLException
     */
    public int Validation(List<FixEmailAccess> FiX,
            List<FixEmailAccess> FiX2,
            List<FixEmailAccess> FiX3,
            StringBuilder str, // get Request EXISTING
            StringBuilder sts,
            StringBuilder stm,
            String Email, // get request
            String code1,
            String code2,
            String code3,
            FixEmailAccessDao dao,
            int Code,
            String flaG) throws SQLException {

        int reqDummy = 0;
        String[] requesteR = Email.toLowerCase().split(";");
        int reqList = requesteR.length;

        getLogger().info("requester + " + flaG +" size " + reqList);
        getLogger().info("persistence list =" + FiX.size());

        if (!FiX.isEmpty()) {
        String acc =     
            AccessLoop(reqList,flaG, 
                       str.toString(), 
                       sts.toString(), 
                       stm.toString(),requesteR,
                       getModelObj().getGrpId(),
                       getModelObj().getFixEmailAccessPK().getIdScheduler(), 
                       code1,code2,code3,FiX,FiX2,FiX3);
        //String[] delEmail = acc.toLowerCase().split(";"); 
        if(acc == null){
            getLogger().info("ISNULL");
        } else if("".equals(acc)){
            getLogger().info("EMPTY");
        } else if(" ".equals(acc)){
            getLogger().info("SPACE");
        } else {
            getLogger().info("get All String : " + acc);
        }
        
            for (int rScan = 0; rScan < FiX.size(); rScan++) {
                getLogger().debug("GET SENDER DB :" + FiX.get(rScan).getFixEmailAccessPK().getSender());
                getLogger().debug("GET EMAIL SCREEN :" + Email);
                if (!Email.toLowerCase().contains(FiX.get(rScan).getFixEmailAccessPK().getSender())) { // jika tidak ada prepare to delete
                    if (!acc.toLowerCase().contains(FiX.get(rScan).getFixEmailAccessPK().getSender())) {
                        getLogger().info(" Build up String (delete phase): " + FiX.get(rScan).getFixEmailAccessPK().getSender());
                        getLogger().info(" CDACCESS: " + FiX.get(rScan).getCdAccess());
                        if (acc == null || "".equals(acc)) {
                            dao.delete(DeleteInstance(code1, FiX, rScan));
                        } else {
                            StringBuilder combination = new StringBuilder();
                            if (whiteList.isEmpty()) {
                                dao.delete(DeleteInstance(code1, FiX, rScan));
                            } else if (whiteList.size() == 2 && moniToring == null) {
                                for (int i = 0; i < whiteList.size(); i++) {
                                    combination.append(whiteList.get(i)).append(";");
                                }
                                getLogger().info("STRING TO COMPARE NO MONITORING :" + combination.toString());
                                if (!combination.toString().toLowerCase().contains(FiX.get(rScan).getFixEmailAccessPK().getSender())) {
                                    dao.delete(DeleteInstance(code1, FiX, rScan));
                                }
                            } else if (whiteList.size() == 3) {
                                for (int i = 0; i < whiteList.size(); i++) {
                                    combination.append(whiteList.get(i)).append(";");
                                }
                                getLogger().info("STRING TO COMPARE :" + combination.toString());
                                if (!combination.toString().toLowerCase().contains(FiX.get(rScan).getFixEmailAccessPK().getSender())) {
                                    dao.delete(DeleteInstance(code1, FiX, rScan));
                                }
                            }
                        }
                        //  
                    }
                    //dao.delete(DeleteInstance(cdAccess, FiX, rScan));
                } else {
                    getLogger().info("EXISTING data on DATABASE : " + FiX.get(rScan).getFixEmailAccessPK().getSender());
                }
            }
        } else {
            for (int i = 0; i < reqList; i++) {
                try {
                    FixEmailAccessPK primary = new FixEmailAccessPK();
                    FixEmailAccess insObject = new FixEmailAccess();
                    getLogger().info(" Begin Insertion .....");

                    primary.setIdScheduler(getModelObj().getFixEmailAccessPK().getIdScheduler());
                    primary.setSender(requesteR[i]);

                    getLogger().info("Primary = " + primary);
                    insObject.setCdAccess(code1);
                    insObject.setGrpId(modelObj.getGrpId());
                    insObject.setFixEmailAccessPK(primary);

                    getLogger().info("requester Action Add = " + requesteR[i]);
                    getLogger().info("object Add = " + insObject);
                    getLogger().info("SNEDER Request :" + insObject.getFixEmailAccessPK().getSender());
                    getLogger().info(" SENDER to Insert :" + primary.getSender());
                    dao.insert(insObject);

                    oldValue.add("");
                    value.add("[" + getModelObj().getFixEmailAccessPK().getIdScheduler() + "][" + code1 + "] " + requesteR[i]);
                    fieldName.add("SENDER");

                    getLogger().info(" Data Inserted ");

                } catch (NullPointerException e) {
                    getLogger().info("null return :" + e);
                } catch (UnknownError e) {
                    getLogger().info("undetected :" + e);
                }
            }
        }
        getLogger().info(" Req Dummy : " + oldValue.size()); // deleting object
        getLogger().info(" new Value : " + value.size()); // insert object
        if (!oldValue.isEmpty() && !value.isEmpty()) {
            Logging(oldValue, value, "EDIT", "UPDATE");
        }
        reqDummy++;
        return reqDummy;
    }

    /**
     * 
     * @param model
     * @param FiX
     * @param rScan
     * @return
     */
    public FixEmailAccess DeleteInstance(String model, List<FixEmailAccess> FiX, int rScan) {
        
        oldValue.add("[" + getModelObj().getFixEmailAccessPK().getIdScheduler() + "][" + model + "] " + FiX.get(rScan).getFixEmailAccessPK().getSender());
        value.add("");
        fieldName.add("SENDER");

        getLogger().info("Delete Flag Area");
        getLogger().info(" ===================== ");
        getLogger().info("Size of Existing List :" + FiX.size());

        getLogger().info("FIX :" + FiX.get(rScan));
        FixEmailAccess delObject = new FixEmailAccess();

        delObject = FiX.get(rScan);
        getLogger().info("DelObject :" + delObject);

        getLogger().info(" SENDER request =" + delObject.getFixEmailAccessPK().getSender());
        getLogger().info("[LIST] " + delObject.getFixEmailAccessPK().getSender());
        return delObject;
    }
    /**
     * 
     * @param FiXc
     * @param updateObject
     * @param requesteR
     * @param rScan
     * @param cdAcc2
     * @return
     */
    public String UpdateInstance(List<FixEmailAccess> FiXc, 
                                         String updateObject, 
                                         String[] requesteR, 
                                         int rScan,
                                         String cdAcc2){
        String before = null;
        FixEmailAccessDao dao = new FixEmailAccessDao(getHSession());
        int loop = 0;
        getLogger().info(" UPDATE TIME []");
        StringBuilder forDelete = new StringBuilder();
        
        try {
               FixEmailAccess insObject = new FixEmailAccess();
               getLogger().info(" Begin Updating .....");

               String[] last_update = updateObject.toLowerCase().split(";");
               for(int i = 0; i< last_update.length ; i++){
                   if(last_update[i].equalsIgnoreCase(requesteR[rScan])){
                                    loop = i;
                   }
               }
               insObject = FiXc.get(loop);
               getLogger().info("Object to Update :" + insObject.getFixEmailAccessPK().getSender());
               getLogger().info("CD ACCESS :" + cdAcc2);
               getLogger().info("INS OBJECT :" + insObject);
               //before = insObject.getCdAccess();
               if (insObject != null) {
                   insObject.setCdAccess(cdAcc2);
                   dao.update(insObject);
                   getLogger().info("UPDATE SUCCESS : " + cdAcc2); 
                   before = insObject.getFixEmailAccessPK().getSender();
                   forDelete.append(insObject.getFixEmailAccessPK().getSender()).append(";");
               }
               //getLogger().info("Action Update (Old) = " + cdAcc1);
               getLogger().info("Action Update (New) = " + cdAcc2);
               getLogger().info("object Add = " + insObject);
               getLogger().info("SNEDER Request :" + insObject.getFixEmailAccessPK().getSender());

               getLogger().info(" Data Updated ");

               } catch (NullPointerException e) {
                   getLogger().info("null return :" + e);
               } catch (UnknownError e) {
                   getLogger().info("undetected :" + e);
               }
        return before;
    }

    /**
     * 
     * @param reqList
     * @param flag
     * @param first
     * @param second
     * @param third
     * @param requesteR
     * @param groupID
     * @param ID_Scheduler
     * @param cdAcc1
     * @param cdAcc2
     * @param cdAcc3
     * @param FiX
     * @param FiXb
     * @param FiXc
     * @return
     */
    public String AccessLoop(int reqList,
            String flag,
            String first,
            String second,
            String third,
            String[] requesteR,
            String groupID,
            int ID_Scheduler,
            String cdAcc1,
            String cdAcc2,
            String cdAcc3,
            List<FixEmailAccess> FiX,
            List<FixEmailAccess> FiXb,
            List<FixEmailAccess> FiXc) {
        /*
         * AccessLoop validation function with mutation rule From left to right
         * (first, second, third) 1. Request -> SPV -> Monitoring 2. SPV ->
         * Monitoring -> Request 3. Monitoring -> Request -> SPV
         *
         */
        StringBuilder forDelete = new StringBuilder();
        
        FixEmailAccessDao dao = new FixEmailAccessDao(getHSession());
        for (int rScan = 0; rScan < requesteR.length; rScan++) {

            getLogger().info(" EMAIL " + flag + " String : (Insert Phase)");
            getLogger().info(first.toLowerCase() + "COMPARE TO 1 :" + requesteR[rScan]);
            if (!first.toLowerCase().contains(requesteR[rScan])) { // jika tidak ada lanjutkan check ke SPV
                getLogger().info(second.toLowerCase() + "COMPARE TO 2 :" + requesteR[rScan]);
                if (!second.toLowerCase().contains(requesteR[rScan])) { // Jika tidak ada lanjutkan cek ke monitoring
                    getLogger().info(third.toLowerCase() + "COMPARE TO 3 :" + requesteR[rScan]);
                    if (!third.toLowerCase().contains(requesteR[rScan])) { // Jika tidak ada lanjutkan Insert
                        oldValue.add("");
                        value.add("[" + getModelObj().getFixEmailAccessPK().getIdScheduler()
                                + "][" + cdAcc1
                                + "] " + requesteR[rScan]);
                        fieldName.add("SENDER");

                        getLogger().info(" INSERT TIME []");
                        try {
                            FixEmailAccessPK primary = new FixEmailAccessPK();
                            FixEmailAccess insObject = new FixEmailAccess();
                            getLogger().info(" Begin Insertion .....");

                            primary.setIdScheduler(modelObj.getFixEmailAccessPK().getIdScheduler());
                            primary.setSender(requesteR[rScan]);

                            getLogger().info("Primary = " + primary);
                            insObject.setCdAccess(cdAcc1);
                            insObject.setGrpId(modelObj.getGrpId());
                            insObject.setFixEmailAccessPK(primary);

                            getLogger().info("requester Action Add = " + requesteR[rScan]);
                            getLogger().info("object Add = " + insObject);
                            getLogger().info("SNEDER Request :" + insObject.getFixEmailAccessPK().getSender());
                            getLogger().info(" SENDER to Insert :" + primary.getSender());
                            dao.insert(insObject);

                            //result_Acc = 1;
                            getLogger().info(" Data Inserted ");

                        } catch (NullPointerException e) {
                            getLogger().info("null return :" + e);
                        } catch (UnknownError e) {
                            getLogger().info("undetected :" + e);
                        }
                    } else { // Lainnya update CDACCESS
                        getLogger().info(cdAcc1);
                        getLogger().info(cdAcc3);
                        oldValue.add("[" + getModelObj().getFixEmailAccessPK().getIdScheduler()
                                + "][" + cdAcc3
                                + "] " + requesteR[rScan]);
                        value.add("[" + getModelObj().getFixEmailAccessPK().getIdScheduler() + "][" + cdAcc1 + "] " + requesteR[rScan]);
                        fieldName.add("CDACCESS");
                        forDelete.append(UpdateInstance(FiXc, third, requesteR, rScan, cdAcc1)).append(";");
                    }
                } else {
                    getLogger().info(cdAcc1);
                    getLogger().info(cdAcc2);
                    oldValue.add("[" + getModelObj().getFixEmailAccessPK().getIdScheduler()
                                + "][" + cdAcc2
                                + "] " + requesteR[rScan]);
                    value.add("[" + getModelObj().getFixEmailAccessPK().getIdScheduler() + "][" + cdAcc1 + "] " + requesteR[rScan]);
                    fieldName.add("CDACCESS");
                    forDelete.append(UpdateInstance(FiXb, second, requesteR, rScan, cdAcc1)).append(";");
                }
            }
        }
        whiteList.add(forDelete);
        return forDelete.toString();
    }

    /**
     * 
     * @param reqDummy
     * @param values
     * @param Action
     * @param Activity
     * @throws SQLException
     */
    public void Logging(List reqDummy, List values, String Action, String Activity) throws SQLException {
        getLogger().info(" Req Dummy(after_class) : " + reqDummy.size()); // old value
        getLogger().info(" new Value(after class) : " + values.size()); // new value

        AuditlogDao auditdao = new AuditlogDao(getHSession());
        auditdao.runPackage(getNamMenu(),
                "FixEmailAccess",
                getModelObj().getIdMaintainedBy(),
                getModelObj().getIdMaintainedSpv(),
                Action, Activity, value, oldValue, fieldName);

        value = new ArrayList();
        oldValue = new ArrayList();
        fieldName = new ArrayList();

    }

    /**
     * @return the Model
     */
    public List getModel() {
        return model;
    }

    /**
     * @param Model the Model to set
     */
    public void setModel(List Model) {
        this.model = Model;
    }

    /**
     * @return the ModelObj
     */
    public FixEmailAccess getModelObj() {
        return modelObj;
    }

    /**
     * @param ModelObj the ModelObj to set
     */
    public void setModelObj(FixEmailAccess ModelObj) {
        this.modelObj = ModelObj;
    }

    /**
     * @return the requeSter
     */
    public String getRequeSter() {
        return requeSter;
    }

    /**
     * @return the superVisor
     */
    public String getSuperVisor() {
        return superVisor;
    }

    /**
     * @return the moniToring
     */
    public String getMoniToring() {
        return moniToring;
    }

    /**
     * @param requeSter the requeSter to set
     */
    public void setRequeSter(String requeSter) {
        this.requeSter = requeSter;
    }

    /**
     * @param superVisor the superVisor to set
     */
    public void setSuperVisor(String superVisor) {
        this.superVisor = superVisor;
    }

    /**
     * @param moniToring the moniToring to set
     */
    public void setMoniToring(String moniToring) {
        this.moniToring = moniToring;
    }

    /**
     * @return the modelStore
     */
    public FixEmailAccessPK getModelStore() {
        return modelStore;
    }

    /**
     * @param modelStore the modelStore to set
     */
    public void setModelStore(FixEmailAccessPK modelStore) {
        this.modelStore = modelStore;
    }
}
