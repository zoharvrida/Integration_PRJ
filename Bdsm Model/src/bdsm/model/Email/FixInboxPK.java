/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Email;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author bdsm
 */
public class FixInboxPK implements Serializable{
    private String inboxId;
    private String subject;
    private Date dtPost;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public FixInboxPK(){
        
    }

    public FixInboxPK(String inboxId, String subject, Date dtPost){
        this.inboxId = inboxId;
        this.subject = subject;
        this.dtPost = dtPost;
    }

    public FixInboxPK(String inboxId, String subject, String date) throws ParseException{
        this.inboxId = inboxId;
        this.subject = subject;
        this.dtPost = sdf.parse(date);
    }

    /**
     * @return the inboxId
     */
    public String getInboxId() {
        return inboxId;
    }

    /**
     * @param inboxId the inboxId to set
     */
    public void setInboxId(String inboxId) {
        this.inboxId = inboxId;
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
     * @return the dtPost
     */
    public Date getDtPost() {
        return dtPost;
    }

    /**
     * @param dtPost the dtPost to set
     */
    public void setDtPost(Date dtPost) {
        this.dtPost = dtPost;
    }



}
