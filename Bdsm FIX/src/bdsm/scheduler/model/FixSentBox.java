/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author bdsm
 */
public class FixSentBox extends BaseModel{
    private String sentBoxId;
    private String emailTo;
    private String emailCc;
    private String subject;
    private String emailBody;
    private Timestamp dtmSend;
    private String emailAttachment;

    /**
     * @return the inboxId
     */
    public String getSentBoxId() {
        return sentBoxId;
    }

    /**
     * @param sentBoxId 
     */
    public void setSentBoxId(String sentBoxId) {
        this.sentBoxId = sentBoxId;
    }

    /**
     * @return the emailTo
     */
    public String getEmailTo() {
        return emailTo;
    }

    /**
     * @param emailTo the emailTo to set
     */
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the emailBody
     */
    public String getEmailBody() {
        return emailBody;
    }

    /**
     * @param emailBody the emailBody to set
     */
    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
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
     * @return the emailCc
     */
    public String getEmailCc() {
        return emailCc;
    }

    /**
     * @param emailCc the emailCc to set
     */
    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    /**
     * @return the dtmSend
     */
    public Timestamp getDtmSend() {
        return dtmSend;
    }

    /**
     * @param dtmSend the dtmSend to set
     */
    public void setDtmSend(Timestamp dtmSend) {
        this.dtmSend = dtmSend;
    }

}
