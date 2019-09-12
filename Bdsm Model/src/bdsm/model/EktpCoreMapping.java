/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class EktpCoreMapping extends BaseModel{
    private EktpCoreMappingPK pk;
    private String coreVal;

    /**
     * @return the pk
     */
    public EktpCoreMappingPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(EktpCoreMappingPK pk) {
        this.pk = pk;
    }

    /**
     * @return the coreVal
     */
    public String getCoreVal() {
        return coreVal;
    }

    /**
     * @param coreVal the coreVal to set
     */
    public void setCoreVal(String coreVal) {
        this.coreVal = coreVal;
    }
}
