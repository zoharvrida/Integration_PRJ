/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.model;

/**
 *
 * @author V00020654
 */
import java.io.Serializable;
import java.util.Date;



public class GLCostCentreXref extends BaseModel implements Serializable { 
    
    private GLCostCentreXrefPK compositeId;
    private Date datLastMnt;
    private String codLast ;
    private String codLastMntChkrid ;
    private Integer ctrUpdatStrlno ;
    private String codMntAction ;

    /**
     * @return the compositeId
     */
    public GLCostCentreXrefPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(GLCostCentreXrefPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the datLastMnt
     */
    public Date getDatLastMnt() {
        return datLastMnt;
    }

    /**
     * @param datLastMnt the datLastMnt to set
     */
    public void setDatLastMnt(Date datLastMnt) {
        this.datLastMnt = datLastMnt;
    }

    /**
     * @return the codLast
     */
    public String getCodLast() {
        return codLast;
    }

    /**
     * @param codLast the codLast to set
     */
    public void setCodLast(String codLast) {
        this.codLast = codLast;
    }

    /**
     * @return the codLastMntChkrid
     */
    public String getCodLastMntChkrid() {
        return codLastMntChkrid;
    }

    /**
     * @param codLastMntChkrid the codLastMntChkrid to set
     */
    public void setCodLastMntChkrid(String codLastMntChkrid) {
        this.codLastMntChkrid = codLastMntChkrid;
    }

    /**
     * @return the ctrUpdatStrlno
     */
    public Integer getCtrUpdatStrlno() {
        return ctrUpdatStrlno;
    }

    /**
     * @param ctrUpdatStrlno the ctrUpdatStrlno to set
     */
    public void setCtrUpdatStrlno(Integer ctrUpdatStrlno) {
        this.ctrUpdatStrlno = ctrUpdatStrlno;
    }

    /**
     * @return the codMntAction
     */
    public String getCodMntAction() {
        return codMntAction;
    }

    /**
     * @param codMntAction the codMntAction to set
     */
    public void setCodMntAction(String codMntAction) {
        this.codMntAction = codMntAction;
    }
}
