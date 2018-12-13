/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author v00020841
 */
public class BaCollHdr extends BaseModel{
    private BaCollHdrPK baCollHdrPK;
    private Integer codCollHomeBrn;
    private String codMntAction;
    private String codLastMntMakerid;
    private String codLastMntchkrid;
    private Timestamp datLastMnt;
    private Integer ctrUpdtSrlno;
    
    public BaCollHdrPK getBaCollHdrPK() {
        return baCollHdrPK;
    }

    public void setBaCollHdrPK(BaCollHdrPK baCollHdrPK) {
        this.baCollHdrPK = baCollHdrPK;
    }

    public Integer getCodCollHomeBrn() {
        return codCollHomeBrn;
    }

    public void setCodCollHomeBrn(Integer codCollHomeBrn) {
        this.codCollHomeBrn = codCollHomeBrn;
    }

    public String getCodMntAction() {
        return codMntAction;
    }

    public void setCodMntAction(String codMntAction) {
        this.codMntAction = codMntAction;
    }

    public String getCodLastMntMakerid() {
        return codLastMntMakerid;
    }

    public void setCodLastMntMakerid(String codLastMntMakerid) {
        this.codLastMntMakerid = codLastMntMakerid;
    }

    public String getCodLastMntchkrid() {
        return codLastMntchkrid;
    }

    public void setCodLastMntchkrid(String codLastMntchkrid) {
        this.codLastMntchkrid = codLastMntchkrid;
    }

    public Timestamp getDatLastMnt() {
        return datLastMnt;
    }

    public void setDatLastMnt(Timestamp datLastMnt) {
        this.datLastMnt = datLastMnt;
    }

    public Integer getCtrUpdtSrlno() {
        return ctrUpdtSrlno;
    }

    public void setCtrUpdtSrlno(Integer ctrUpdtSrlno) {
        this.ctrUpdtSrlno = ctrUpdtSrlno;
    }


}
