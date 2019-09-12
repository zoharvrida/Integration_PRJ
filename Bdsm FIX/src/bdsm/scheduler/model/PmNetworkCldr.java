/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00019722
 */
public class PmNetworkCldr {
    private FixCalendarPK pkID;
    private String namNetwork;
    private String monthDAy;
    /**
     * @return the namNetwork
     */
    public String getNamNetwork() {
        return namNetwork;
    }

    /**
     * @param namNetwork the namNetwork to set
     */
    public void setNamNetwork(String namNetwork) {
        this.namNetwork = namNetwork;
    }


    /**
     * @return the monthDAy
     */
    public String getMonthDAy() {
        return monthDAy;
    }

    /**
     * @param monthDAy the monthDAy to set
     */
    public void setMonthDAy(String monthDAy) {
        this.monthDAy = monthDAy;
    }

    /**
     * @return the pkID
     */
    public FixCalendarPK getPkID() {
        return pkID;
    }

    /**
     * @param pkID the pkID to set
     */
    public void setPkID(FixCalendarPK pkID) {
        this.pkID = pkID;
    }
}
