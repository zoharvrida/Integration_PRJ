/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00022309
 */
public class ProTimePutLog {

    private String sessionid;
    private String userid;
    private String cdmenu;
    private String stepname;
    private String errmsg;

    /**
     * @return the sessionid
     */
    public String getSessionid() {
        return sessionid;
    }

    @Override
    public String toString() {
        return "ProTimePutLog{" + "sessionid=" + sessionid + ", userid=" + userid + ", cdmenu=" + cdmenu + ", stepname=" + stepname + ", errmsg=" + errmsg + '}';
    }

    /**
     * @param sessionid the sessionid to set
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the cdmenu
     */
    public String getCdmenu() {
        return cdmenu;
    }

    /**
     * @param cdmenu the cdmenu to set
     */
    public void setCdmenu(String cdmenu) {
        this.cdmenu = cdmenu;
    }

    /**
     * @return the stepname
     */
    public String getStepname() {
        return stepname;
    }

    /**
     * @param stepname the stepname to set
     */
    public void setStepname(String stepname) {
        this.stepname = stepname;
    }

    /**
     * @return the errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * @param errmsg the errmsg to set
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
