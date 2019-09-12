package bdsm.scheduler.model;

import java.io.Serializable;

/**
 * 
 * @author bdsm
 */
public class FixEmailAccessPK implements Serializable{
    private Integer idScheduler;
    private String sender;


    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(Integer idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }


}
