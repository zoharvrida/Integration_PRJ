/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.io.Serializable;

/**
 *
 * @author SDM
 */
public abstract class ComCPHistParent extends BaseModel implements Serializable {
    private ComCpMastHistPK pk;

    /**
     * @return the pk
     */
    public ComCpMastHistPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(ComCpMastHistPK pk) {
        this.pk = pk;
    }

}
