/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.sql.Timestamp;

/**
 *
 * @author user
 */
public class Auditlog {
    private Integer id;
    private String idUser;
    private String idUserSpv;
    private String namTable;
    private Timestamp dtmLog;
    private String namMenu;
    private String activity;
    private String action;
    
    /**
    * @return the id
    */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
    * @return the idUser
    */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
    * @return the idUserSpv
    */
    public String getIdUserSpv() {
        return idUserSpv;
    }

    /**
     * @param idUserSpv the idUserSpv to set
     */
    public void setIdUserSpv(String idUserSpv) {
        this.idUserSpv = idUserSpv;
    }

    /**
    * @return the namTable
    */
    public String getNamTable() {
        return namTable;
    }

    /**
     * @param namTable the namTable to set
     */
    public void setNamTable(String namTable) {
        this.namTable = namTable;
    }
    
    /**
    * @return the dtmLog
    */
    public Timestamp getDtmLog() {
        return dtmLog;
    }

    /**
     * @param dtmLog the dtmLog to set
     */
    public void setDtmLog(Timestamp dtmLog) {
        this.dtmLog = dtmLog;
    }
    
    /**
     * @return the namMenu
     */
    public String getNamMenu() {
        return namMenu;
    }
    
    /**
     * @param namMenu the namMenu to set
     */
    public void setNamMenu(String namMenu) {
        this.namMenu = namMenu;
    }

    /**
     * @return the activity
     */
    public String getActivity() {
        return activity;
    }
    
    /**
     * @param activity the activity to set
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }
    
    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }    
}
