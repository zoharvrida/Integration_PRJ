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
public abstract class ComCDHistParent extends BaseModel implements Serializable {
    private ComCdMastHistPK pk;

    /**
     * @return the pk
     */
    public ComCdMastHistPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(ComCdMastHistPK pk) {
        this.pk = pk;
    }
}
