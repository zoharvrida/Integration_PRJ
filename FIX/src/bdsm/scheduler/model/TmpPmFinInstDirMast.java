/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.io.Serializable;

/**
 *
 * @author v00020800
 */
public class TmpPmFinInstDirMast extends BaseModel implements Serializable {

	private TmpPmFinInstDirMastPK compositeId;
	private Integer codExtInstKey;
	private String namBank;
	private String namBranch;
	private String brnCity;
	private String brnCountry;
    private String brnProvince;
    private String flgInternalClg;
    private String txtBrnAdd1;
    private String txtBrnAdd2;
    private String txtBrnAdd3;
	private String codZoneId;
	private String codCircleId;
	private String finInstPhone;
    private String codBank;
    private String codBi;
    private String codCcBrn;
	private String ctrUpdatSrlNo;
	private String flgstatus;
	private String statusReason;
	private String cmd;
	private String flgMntStatus;
	private Integer codEntityVpd;
	private Integer codNostroGlAcct;
    private String txtBrnZip;
    private String detailsNetwork; 
    private String codLastMntMakerid;


	/**
	 * @return the codExtInstKey
	 */
	public Integer getCodExtInstKey() {
		return codExtInstKey;
	}

	/**
	 * @param codExtInstKey the codExtInstKey to set
	 */
	public void setCodExtInstKey(Integer codExtInstKey) {
		this.codExtInstKey = codExtInstKey;
	}

	/**
	 * @return the namBank
	 */
	public String getNamBank() {
		return namBank;
	}

	/**
	 * @param namBank the namBank to set
	 */
	public void setNamBank(String namBank) {
		this.namBank = namBank;
	}

	/**
	 * @return the codZoneId
	 */
	public String getCodZoneId() {
		return codZoneId;
	}

	/**
	 * @param codZoneId the codZoneId to set
	 */
	public void setCodZoneId(String codZoneId) {
		this.codZoneId = codZoneId;
	}

	/**
	 * @return the codCircleId
	 */
	public String getCodCircleId() {
		return codCircleId;
	}

	/**
	 * @param codCircleId the codCircleId to set
	 */
	public void setCodCircleId(String codCircleId) {
		this.codCircleId = codCircleId;
	}

	/**
	 * @return the inInstPhone
	 */
	public String getFinInstPhone() {
		return finInstPhone;
	}

	/**
     * 
     * @param finInstPhone 
     */
	public void setFinInstPhone(String finInstPhone) {
		this.finInstPhone = finInstPhone;
	}
	/**
	 * @return the codUpdatSrlNo
	 */
	public String getCtrUpdatSrlNo() {
		return ctrUpdatSrlNo;
	}

	/**
	 * @param codUpdatSrlNo the codUpdatSrlNo to set
	 */
	public void setCtrUpdatSrlNo(String codUpdatSrlNo) {
		this.ctrUpdatSrlNo = codUpdatSrlNo;
	}

	/**
	 * @return the compositeId
	 */
	public TmpPmFinInstDirMastPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(TmpPmFinInstDirMastPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the flgstatus
	 */
	public String getFlgstatus() {
		return flgstatus;
	}

	/**
	 * @param flgstatus the flgstatus to set
	 */
	public void setFlgstatus(String flgstatus) {
		this.flgstatus = flgstatus;
	}

	/**
	 * @return the statusReason
	 */
	public String getStatusReason() {
		return statusReason;
	}

	/**
	 * @param statusReason the statusReason to set
	 */
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * @param cmd the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return the brnCity
	 */
	public String getBrnCity() {
		return brnCity;
	}

	/**
	 * @param brnCity the brnCity to set
	 */
	public void setBrnCity(String brnCity) {
		this.brnCity = brnCity;
	}

	/**
	 * @return the brnCountry
	 */
	public String getBrnCountry() {
		return brnCountry;
	}

	/**
	 * @param brnCountry the brnCountry to set
	 */
	public void setBrnCountry(String brnCountry) {
		this.brnCountry = brnCountry;
	}

	/**
	 * @return the flgMntStatus
	 */
	public String getFlgMntStatus() {
		return flgMntStatus;
	}

	/**
	 * @param flgMntStatus the flgMntStatus to set
	 */
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}

	/**
	 * @return the codEntityVpd
	 */
	public Integer getCodEntityVpd() {
		return codEntityVpd;
	}

	/**
	 * @param codEntityVpd the codEntityVpd to set
	 */
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}

	/**
	 * @return the namBranch
	 */
	public String getNamBranch() {
		return namBranch;
	}

	/**
	 * @param namBranch the namBranch to set
	 */
	public void setNamBranch(String namBranch) {
		this.namBranch = namBranch;
	}

	/**
	 * @return the codNostroGlAcct
	 */
	public Integer getCodNostroGlAcct() {
		return codNostroGlAcct;
	}

	/**
	 * @param codNostroGlAcct the codNostroGlAcct to set
	 */
	public void setCodNostroGlAcct(Integer codNostroGlAcct) {
		this.codNostroGlAcct = codNostroGlAcct;
	}

    /**
     * @return the txtBrnAdd1
     */
    public String getTxtBrnAdd1() {
        return txtBrnAdd1;
    }

    /**
     * @param txtBrnAdd1 the txtBrnAdd1 to set
     */
    public void setTxtBrnAdd1(String txtBrnAdd1) {
        this.txtBrnAdd1 = txtBrnAdd1;
    }

    /**
     * @return the txtBrnAdd2
     */
    public String getTxtBrnAdd2() {
        return txtBrnAdd2;
    }

    /**
     * @param txtBrnAdd2 the txtBrnAdd2 to set
     */
    public void setTxtBrnAdd2(String txtBrnAdd2) {
        this.txtBrnAdd2 = txtBrnAdd2;
    }

    /**
     * @return the txtBrnAdd3
     */
    public String getTxtBrnAdd3() {
        return txtBrnAdd3;
    }

    /**
     * @param txtBrnAdd3 the txtBrnAdd3 to set
     */
    public void setTxtBrnAdd3(String txtBrnAdd3) {
        this.txtBrnAdd3 = txtBrnAdd3;
    }

    /**
     * @return the txtBrnZip
     */
    public String getTxtBrnZip() {
        return txtBrnZip;
    }

    /**
     * @param txtBrnZip the txtBrnZip to set
     */
    public void setTxtBrnZip(String txtBrnZip) {
        this.txtBrnZip = txtBrnZip;
    }

    /**
     * @return the detailsNetwork
     */
    public String getDetailsNetwork() {
        return detailsNetwork;
    }

    /**
     * @param detailsNetwork the detailsNetwork to set
     */
    public void setDetailsNetwork(String detailsNetwork) {
        this.detailsNetwork = detailsNetwork;
    }

    /**
     * @return the codLastMntMakerid
     */
    public String getCodLastMntMakerid() {
        return codLastMntMakerid;
    }

    /**
     * @param codLastMntMakerid the codLastMntMakerid to set
     */
    public void setCodLastMntMakerid(String codLastMntMakerid) {
        this.codLastMntMakerid = codLastMntMakerid;
    }


    /**
     * @return the flgInternalClg
     */
    public String getFlgInternalClg() {
        return flgInternalClg;
    }

    /**
     * @param flgInternalClg the flgInternalClg to set
     */
    public void setFlgInternalClg(String flgInternalClg) {
        this.flgInternalClg = flgInternalClg;
    }
    /**
     * @return the codBi
     */
    public String getCodBi() {
        return codBi;
    }

    /**
     * @param codBi the codBi to set
     */
    public void setCodBi(String codBi) {
        this.codBi = codBi;
    }

    /**
     * @return the codBank
     */
    public String getCodBank() {
        return codBank;
    }

    /**
     * @param codBank the codBank to set
     */
    public void setCodBank(String codBank) {
        this.codBank = codBank;
    }

    /**
     * @return the codCcBrn
     */
    public String getCodCcBrn() {
        return codCcBrn;
    }

    /**
     * @param codCcBrn the codCcBrn to set
     */
    public void setCodCcBrn(String codCcBrn) {
        this.codCcBrn = codCcBrn;
    }

    /**
     * @return the brnProvince
     */
    public String getBrnProvince() {
        return brnProvince;
    }

    /**
     * @param brnProvince the brnProvince to set
     */
    public void setBrnProvince(String brnProvince) {
        this.brnProvince = brnProvince;
    }
}
