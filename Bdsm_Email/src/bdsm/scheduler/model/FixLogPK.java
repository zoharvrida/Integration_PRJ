/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author bdsm
 */
public class FixLogPK implements Serializable{
    
    private String fileName;
    private Date dtPost;
    private String inboxId;
    private String typFix;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public FixLogPK(){

    }
    
    public FixLogPK(String fileName, Date dtPost){
        this.fileName = fileName;
        this.dtPost = dtPost;
    }

    public FixLogPK(String fileName, String date) throws ParseException {
        synchronized(sdf) {
            this.dtPost = sdf.parse(date);
        }
        this.fileName = fileName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * @return the typFix
     */
    public String getTypFix() {
        return typFix;
    }

    /**
     * @param typFix the typFix to set
     */
    public void setTypFix(String typFix) {
        this.typFix = typFix;
    }
}
