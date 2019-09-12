/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.util.EncryptionUtil;
import bdsmhost.dao.AuditlogDao;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public class FixSchedulerImportPasswordAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;
    private FixSchedulerImport model;
    private FixSchedulerImport modelImport;
    private String resetState;
    private String Key;
    private String newEncrypt;
    private String Decrypt;
    private String oldEncrypt;
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
        String KeyTemp = null;
        String ExistingKey = null;
        getLogger().debug("idTemplate     = " + getModelImport().getFixSchedulerPK().getIdTemplate());
        getLogger().debug("Scheduler      = " + getModelImport().getFixSchedulerPK().getIdScheduler());
        getLogger().debug("nama Scheduler = " + getModelImport().getFixSchedulerPK().getNamScheduler());
        getLogger().debug("Password = " + getKey()); //pembanding password lama
        getLogger().debug("New Password= " + getModelImport().getKeyDecrypt());
        
        FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());
        FixSchedulerImport persistence = dao.get(getModelImport().getFixSchedulerPK());
        if (!"".equals(getKey())) {
            EncryptionUtil encription = new EncryptionUtil();
            try {
                setOldEncrypt(encription.getAES(getKey(), (persistence.getModDecrypt()+"@@@@@@@@@@@@@").substring(0,16), mode).replaceAll("\\s", ""));
            } catch (Exception ex) {
                Logger.getLogger(FixSchedulerXtractAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ExistingKey = dao.before(getModelImport().getFixSchedulerPK().getIdScheduler(),
                getModelImport().getFixSchedulerPK().getIdTemplate());
        getLogger().info("Encrypt = " + getOldEncrypt());
        getLogger().info("old Encrypt =" + ExistingKey);
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
	@SuppressWarnings("unchecked")
    public String doUpdate() {
        int mode = 1;
        //String KeyTemp = null;
        String ExistingKey = null;
        getLogger().debug(" Import modelImport " + getModelImport());
        getLogger().debug(" Import Id template : " + getModelImport().getFixSchedulerPK().getIdTemplate());
        getLogger().debug(" Import Id Scheduler : " + getModelImport().getFixSchedulerPK().getIdScheduler());
        getLogger().debug(" Import Scheduler Name : " + getModelImport().getFixSchedulerPK().getNamScheduler());

        getLogger().debug("Old Password = " + getKey());
        getLogger().debug("New Password= " + getModelImport().getKeyDecrypt());
        
        FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());
        FixSchedulerImport persistence = dao.get(getModelImport().getFixSchedulerPK());      
        if (!"".equals(getKey())) {
            EncryptionUtil encription = new EncryptionUtil();
            try {
                setOldEncrypt(encription.getAES(getModelImport().getKeyDecrypt(),(persistence.getModDecrypt()+"@@@@@@@@@@@@@").substring(0,16), mode).replaceAll("\\s", ""));
            } catch (Exception ex) {
                Logger.getLogger(FixSchedulerImportAction.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        ExistingKey = dao.before(getModelImport().getFixSchedulerPK().getIdScheduler(),
                getModelImport().getFixSchedulerPK().getIdTemplate());
        getLogger().debug("Encrypt = " + getOldEncrypt());
        getLogger().debug("old Encrypt =" + ExistingKey);
        /*if (!ExistingKey.equals(getOldEncrypt())) {
            if("true".equals(resetState)){
                setNewEncrypt(ExistingKey);
            } else {
                super.setErrorCode("error.3");
                return ActionSupport.ERROR;
            }
        } 
        else {*/
        setNewEncrypt(getOldEncrypt());
        //}
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        getLogger().debug("Audit");
        
        getLogger().debug("Encript = " + dao.get(getModelImport().getFixSchedulerPK()));
        if (persistence != null) {

            oldValue.add(persistence.getKeyDecrypt());
            oldValue.add(persistence.getIdMaintainedBy());
            oldValue.add(persistence.getIdMaintainedSpv());
            if("true".equals(resetState)){
               value.add(getNewEncrypt().concat("[RESET PASSWORD]"));  
            } else {
               value.add(getNewEncrypt());
            }
            value.add(getModelImport().getIdMaintainedBy());
            value.add(getModelImport().getIdMaintainedSpv());
            fieldName.add("KEYDECRYPT");
            fieldName.add("IDUSER");
            fieldName.add("IDUSERSPV");
            
            persistence.setKeyDecrypt(getNewEncrypt());
            getLogger().debug("Enkripsi =" + persistence.getKeyDecrypt());
            persistence.setIdMaintainedBy(getModelImport().getIdMaintainedBy());
            persistence.setIdMaintainedSpv(getModelImport().getIdMaintainedSpv());

            dao.update(persistence);
            try {
                auditdao.runPackage(getNamMenu(),
                        "FixSchedulerImport",
                        getModelImport().getIdMaintainedBy(),
                        getModelImport().getIdMaintainedSpv(),
                        "Edit", "Edit", value, oldValue, fieldName);
            } catch (SQLException ex) {
                Logger.getLogger(FixSchedulerImportPasswordAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLogger().debug("Error Code.....");
            super.setErrorCode("success.8");
            return SUCCESS;
        } else {
            super.setErrorCode("error.5");
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
     * @return the modelImport
     */
    public FixSchedulerImport getModelImport() {
        return modelImport;
    }

    /**
     * @param modelImport the modelImport to set
     */
    public void setModelImport(FixSchedulerImport modelImport) {
        this.modelImport = modelImport;
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
     * @return the Decrypt
     */
    public String getDecrypt() {
        return Decrypt;
    }

    /**
     * @param Decrypt the Decrypt to set
     */
    public void setDecrypt(String Decrypt) {
        this.Decrypt = Decrypt;
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
