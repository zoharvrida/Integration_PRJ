/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.util.EncryptionUtil;
import bdsmhost.dao.AuditlogDao;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public class FixSchedulerXtractPasswordAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;
    
    private List modelList;
    private FixSchedulerXtract model;
    private String resetState;
    private String idTemplate;
    private String idScheduler;
    private String Key;
    private String oldEncrypt;
    private String newEncrypt;
    private ArrayList value = new ArrayList();
    private ArrayList oldValue = new ArrayList();
    private ArrayList fieldName = new ArrayList();

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        int mode = 1;
        String ExistingKey = null;
        getLogger().debug("idTemplate     = " + getModel().getFixSchedulerPK().getIdTemplate());
        getLogger().debug("Scheduler      = " + getModel().getFixSchedulerPK().getIdScheduler());
        getLogger().debug("nama Scheduler = " + getModel().getFixSchedulerPK().getNamScheduler());
        getLogger().debug("Password = " + getKey()); //pembanding password lama
        getLogger().debug("New Password= " + getModel().getKeyEncrypt());
        
        FixSchedulerXtractDao dao = new FixSchedulerXtractDao(getHSession());
        FixSchedulerXtract persistence = dao.get(getModel().getFixSchedulerPK());
        if (!"".equals(getKey())) {
            try {
                setOldEncrypt(EncryptionUtil.getAES(getKey(), (persistence.getModEncrypt()+"@@@@@@@@@@@@@").substring(0,16), mode).replaceAll("\\s", ""));
            } catch (Exception ex) {
                Logger.getLogger(FixSchedulerXtractAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ExistingKey = dao.before(getModel().getFixSchedulerPK().getIdScheduler(),
                getModel().getFixSchedulerPK().getIdTemplate());
        getLogger().debug("Encrypt = " + getOldEncrypt());
        getLogger().debug("old Encrypt =" + ExistingKey);
        if (!ExistingKey.equals(getOldEncrypt())) {
            if("true".equals(resetState)){
                setNewEncrypt(ExistingKey);
                return SUCCESS;
            } else {
                super.setErrorCode("error.3");
                return ActionSupport.ERROR;
            }
        } else {
            setNewEncrypt(ExistingKey);
            return SUCCESS;
        }
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
        int mode = 1;
        String ExistingKey = null;
        getLogger().debug("Extract idTemplate     = " + getModel().getFixSchedulerPK().getIdTemplate());
        getLogger().debug("Extract Scheduler      = " + getModel().getFixSchedulerPK().getIdScheduler());
        getLogger().debug("Extract nama Scheduler = " + getModel().getFixSchedulerPK().getNamScheduler());
        
        getLogger().debug("Old Password = " + getKey()); //pembanding password lama
        getLogger().debug("New Password= " + getModel().getKeyEncrypt());
        
        FixSchedulerXtractDao dao = new FixSchedulerXtractDao(getHSession());
        FixSchedulerXtract persistence = dao.get(getModel().getFixSchedulerPK());
        if (!"".equals(getKey())) {
            try {
                setOldEncrypt(EncryptionUtil.getAES(getModel().getKeyEncrypt(), (persistence.getModEncrypt()+"@@@@@@@@@@@@@").substring(0,16), mode).replaceAll("\\s", ""));
            } catch (Exception ex) {
                Logger.getLogger(FixSchedulerXtractAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ExistingKey = dao.before(getModel().getFixSchedulerPK().getIdScheduler(),
                getModel().getFixSchedulerPK().getIdTemplate());
        getLogger().debug("Encrypt = " + getOldEncrypt());
        getLogger().debug("old Encrypt =" + ExistingKey);
        /*if (!ExistingKey.equals(getOldEncrypt())) {
            if("true".equals(resetState)){
                setNewEncrypt(beforeUpdate(persistence));
            } else {
                super.setErrorCode("error.3");
                return ActionSupport.ERROR;
            }
        } else {*/
        setNewEncrypt(getOldEncrypt());
        //}
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        getLogger().info("Audit");
        
        getLogger().debug("Encript = " + dao.get(getModel().getFixSchedulerPK()));
        //getLogger().info("Nama Scheduler Extract = "+ persistence.getNamScheduler());
        if (persistence != null) {
            persistence.setKeyEncrypt(getNewEncrypt());
            getLogger().debug("Enkripsi =" + persistence.getKeyEncrypt());
            persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
            persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());
            
            oldValue.add(persistence.getKeyEncrypt());
            oldValue.add(persistence.getIdMaintainedBy());
            oldValue.add(persistence.getIdMaintainedSpv());
            if("true".equals(getResetState())){
               value.add(getNewEncrypt().concat("[RESET PASSWORD]"));  
            } else {
               value.add(getNewEncrypt());
            }
            value.add(getModel().getIdMaintainedBy());
            value.add(getModel().getIdMaintainedBy());
            fieldName.add("KEYENCRYPT");
            fieldName.add("IDUSER");
            fieldName.add("IDUSERSPV");
            
            dao.update(persistence);
            try {
                auditdao.runPackage(getNamMenu(),
                        "FixSchedulerExtract",
                        getModel().getIdMaintainedBy(),
                        getModel().getIdMaintainedSpv(),
                        "Edit", "Edit", value, oldValue, fieldName);
            } catch (SQLException ex) {
                Logger.getLogger(FixSchedulerXtractPasswordAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLogger().debug("Error Code.....");
            super.setErrorCode("success.8");
            return SUCCESS;
        } else {
            super.setErrorCode("error.5");
            return ERROR;
        }

        //super.setErrorCode("success.1");
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
     * @return
     */
    public String refreshParameter() {
        bdsm.scheduler.PropertyPersister.refreshParameter(this.getHSession());
        
        return SUCCESS;
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
    public FixSchedulerXtract getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FixSchedulerXtract model) {
        this.model = model;
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
     * @return the idScheduler
     */
    public String getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(String idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the oldEncrypt
     */
    public String getOldEncrypt() {
        return oldEncrypt;
    }

    /**
     * @param OldEncrypt 
     */
    public void setOldEncrypt(String OldEncrypt) {
        this.oldEncrypt = OldEncrypt;
    }

    /**
     * @return the newEncrypt
     */
    public String getNewEncrypt() {
        return newEncrypt;
    }

    /**
     * @param newEncrypt the newEncrypt to set
     */
    public void setNewEncrypt(String newEncrypt) {
        this.newEncrypt = newEncrypt;
    }

    /**
     * @return the Key
     */
    public String getKey() {
        return Key;
    }

    /**
     * @param Key the Key to set
     */
    public void setKey(String Key) {
        this.Key = Key;
    }

    /**
     * @return the resetState
     */
    public String getResetState() {
        return resetState;
    }

    /**
     * @param resetState the resetState to set
     */
    public void setResetState(String resetState) {
        this.resetState = resetState;
    }
}
