/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author v00020841
 */
public class CmCustcardMast extends BaseModel {
    private CmCustcardMastPK cmCustcardMastPK;
    private Integer codCcHomeBrn;
    private String codCardNo;
    private String namCustEmbossed;
    private Integer codCardProd;
    private Integer typCard;
    private String flgCardStatus;
    private Date datIssue;
    private Date datExpiry;
    private String codLang;
    private Integer codReason;
    private Integer codCccollBrn;
    private String codMntAction;
    private String codLastMntMakerid;
    private String codLastMntChkrid;
    private Timestamp datLastMnt;
    private Integer ctrUpdtSrlNo;

    public CmCustcardMastPK getCmCustcardMastPK() {
        return cmCustcardMastPK;
    }

    public Integer getCodCcHomeBrn() {
        return codCcHomeBrn;
    }
	
    public void setCodCcHomeBrn(Integer codCcHomeBrn) {
        this.codCcHomeBrn = codCcHomeBrn;
    }

    public void setCmCustcardMastPK(CmCustcardMastPK cmCustcardMastPK) {
        this.cmCustcardMastPK = cmCustcardMastPK;
    }

    public String getCodCardNo() {
        return codCardNo;
    }

    public void setCodCardNo(String codCardNo) {
        this.codCardNo = codCardNo;
    }

    public String getNamCustEmbossed() {
        return namCustEmbossed;
    }

    public void setNamCustEmbossed(String namCustEmbossed) {
        this.namCustEmbossed = namCustEmbossed;
    }

    public Integer getCodCardProd() {
        return codCardProd;
    }

    public void setCodCardProd(Integer codCardProd) {
        this.codCardProd = codCardProd;
    }

    public Integer getTypCard() {
        return typCard;
    }

    public void setTypCard(Integer typCard) {
        this.typCard = typCard;
    }

    public String getFlgCardStatus() {
        return flgCardStatus;
    }

    public void setFlgCardStatus(String flgCardStatus) {
        this.flgCardStatus = flgCardStatus;
    }

    public Date getDatIssue() {
        return datIssue;
    }

    public void setDatIssue(Date datIssue) {
        this.datIssue = datIssue;
    }

    public Date getDatExpiry() {
        return datExpiry;
    }

    public void setDatExpiry(Date datExpiry) {
        this.datExpiry = datExpiry;
    }

    public String getCodLang() {
        return codLang;
    }

    public void setCodLang(String codLang) {
        this.codLang = codLang;
    }
	
  public Integer getCodReason() {
        return codReason;
    }

    public void setCodReason(Integer codReason) {
        this.codReason = codReason;
    }

    public Integer getCodCccollBrn() {
        return codCccollBrn;
    }

    public void setCodCccollBrn(Integer codCccollBrn) {
        this.codCccollBrn = codCccollBrn;
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

    public String getCodLastMntChkrid() {
        return codLastMntChkrid;
    }

    public void setCodLastMntChkrid(String codLastMntChkrid) {
        this.codLastMntChkrid = codLastMntChkrid;
    }

    public Timestamp getDatLastMnt() {
        return datLastMnt;
    }

    public void setDatLastMnt(Timestamp datLastMnt) {
        this.datLastMnt = datLastMnt;
    }

    public Integer getCtrUpdtSrlNo() {
        return ctrUpdtSrlNo;
    }

    public void setCtrUpdtSrlNo(Integer ctrUpdtSrlNo) {
        this.ctrUpdtSrlNo = ctrUpdtSrlNo;
    }
    
    
}
