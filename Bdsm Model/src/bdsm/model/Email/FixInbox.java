package bdsm.model.Email;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

public class FixInbox extends BaseModel{
    private FixInboxPK fixInboxPK;
    private String sender;    
    private String flgProcess;
    private String reason;
    private Timestamp dtmReceive;
    private String flgAttach;
    private String emailAttachment;
    private String itemIdLink;
    private String noBatch;

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

    /**
     * @return the flgProcess
     */
    public String getFlgProcess() {
        return flgProcess;
    }

    /**
     * @param flgProcess the flgProcess to set
     */
    public void setFlgProcess(String flgProcess) {
        this.flgProcess = flgProcess;
    }

    
    /**
     * @return the flgAttach
     */
    public String getFlgAttach() {
        return flgAttach;
    }

    /**
     * @param flgAttach the flgAttach to set
     */
    public void setFlgAttach(String flgAttach) {
        this.flgAttach = flgAttach;
    }

    /**
     * @return the emailAttachment
     */
    public String getEmailAttachment() {
        return emailAttachment;
    }

    /**
     * @param emailAttachment the emailAttachment to set
     */
    public void setEmailAttachment(String emailAttachment) {
        this.emailAttachment = emailAttachment;
    }

    /**
     * @return the itemIdLink
     */
    public String getItemIdLink() {
        return itemIdLink;
    }

    /**
     * @param itemIdLink the itemIdLink to set
     */
    public void setItemIdLink(String itemIdLink) {
        this.itemIdLink = itemIdLink;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        if (reason != null) {
            if (reason.length() > 200) {
                this.reason = reason.substring(0, 200);
            } else {
                this.reason = reason;
            }
        }
    }

    /**
     * @return the noBatch
     */
    public String getNoBatch() {
        return noBatch;
    }

    /**
     * @param noBatch the noBatch to set
     */
    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }

    /**
     * @return the fixInboxPK
     */
    public FixInboxPK getFixInboxPK() {
        return fixInboxPK;
    }

    /**
     * @param fixInboxPK the fixInboxPK to set
     */
    public void setFixInboxPK(FixInboxPK fixInboxPK) {
        this.fixInboxPK = fixInboxPK;
    }

    /**
     * @return the dtmReceive
     */
    public Timestamp getDtmReceive() {
        return dtmReceive;
    }

    /**
     * @param dtmReceive the dtmReceive to set
     */
    public void setDtmReceive(Timestamp dtmReceive) {
        this.dtmReceive = dtmReceive;
    }
    
}
