/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler;

/**
 *
 * @author bdsm
 */
public class ScheduleDefinition {

    /**
     * 
     */
    public static final int emailOnly = 1;
    /**
     * 
     */
    public static final int sftpOnly = 2;
    /**
     * 
     */
    public static final int bdsmReport = 3;
    /**
     * 
     */
    public static final int purging = 4;
    /**
     * 
     */
    public static final int gefuRespons = 5;
    /**
     * 
     */
    public static final int gefuUpdate = 6;
    /**
     * 
     */
    public static final int closingReport = 0;
    /**
     * 
     */
    public static final Integer ReportParam = 9;
    /**
     * 
     */
    public static final String LOG = "LOG";
    /**
     * 
     */
    public static final String ALL = "all";
    /**
     * 
     */
    public static final String Email = "EMAIL";
    /**
     * 
     */
    public static final String WebService = "WS";
    /**
     * 
     */
    public static final String App = "App";
    /**
     * 
     */
    public static final String Web = "Web";
    /**
     * 
     */
    public static final String Fix = "Fix";
    /**
     * 
     */
    public static final int[] reqFixQ = {44,43};
    /**
     * 
     */
    public static final String monitorCasa = "monitorCASA";
    /**
     * 
     */
    public static final String packAge = "PK_DDF_CICH";
    /**
     * 
     */
    public static final String updateCIFPROSPECT = "updateCIFPROSPECT";
    //public static final String DDFPaternCIF = "SCIFUF";
    //public static final String DDFPaternCASA = "SCCHUF";
    
    /**
     * 
     */
    public static final String BaBankMast = "1";
    /**
     * 
     */
    public static final String BIBankMastIncoming = "2";
    /**
     * 
     */
    public static final String BIBankMastOutgoing = "3";
    /**
     * 
     */
    public static final String FCCBankMast = "4";
    /**
     * 
     */
    public static final String RealCalendar = "A";
    /**
     * 
     */
    public static final String SystemCalendar = "B";
    
    /**
     * 
     */
    public static final Integer BaBranch = 9999;
    /**
     * 
     */
    public static final String PmNetworkChnlO = "SKNCO";
    /**
     * 
     */
    public static final String PmNetworkChnlI = "SKNCI";
    /**
     * 
     */
    public static final String codCCY = "IDR";
    
    // EKTP Schedule
    /**
     * 
     */
    public static final String HO = "HeadOffice";
    /**
     * 
     */
    public static final String param5 = "${Param5}";
}
