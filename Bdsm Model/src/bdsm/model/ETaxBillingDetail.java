package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author Jaka
 */
public class ETaxBillingDetail implements Serializable {
    
    private String idBilling;
    private String userID;

    public ETaxBillingDetail() {
    }

    /**
     * @return the idBilling
     */
    public String getIdBilling() {
        return idBilling;
    }

    /**
     * @param idBilling the idBilling to set
     */
    public void setIdBilling(String idBilling) {
        this.idBilling = idBilling;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
}
