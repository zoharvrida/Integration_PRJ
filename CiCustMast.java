package bdsm.fcr.model;


import java.util.Date;


/**
 * 
 * @author v00020841
 */
@SuppressWarnings("serial")
public class CiCustMast extends bdsm.model.BaseModel implements java.io.Serializable {
	private CiCustMastPK ciCustMastPK;
	private java.util.Date datCustOpen;
	private String codBankCard;
	private String flgCustTyp;
	private String cifType;
	private String flagStaff;
	private String flgLocGlob;
	private Integer codCcHomeBrn;
	private String namCustShrt;
	private String namCustFull;
	private String txtCustDesgn;
	private String codCustNatlId;
	private String txtCustAdrAdd1;
	private String txtCustAdrAdd2;
	private String txtCustAdrAdd3;
	private String namCustAdrCity;
	private String namCustAdrState;
	private String namCustAdrCntry;
	private String txtCustAdrZip;
	private String refCustPhone;
	private String refCustPhoneOff;
	private String refCustEmail;
	private String txtPermadrAdd3;
	private String namPermadrCity;
	private String namPermadrState;
	private String txtPermadrZip;
	private Date datBirthCust;
	private String txtCustSex;
	private String codCustMarstat;
	private String txtCustEducn;


	public CiCustMastPK getCiCustMastPK() {
		return this.ciCustMastPK;
	}
	public void setCiCustMastPK(CiCustMastPK ciCustMastPK) {
		this.ciCustMastPK = ciCustMastPK;
	}

	public java.util.Date getDatCustOpen() {
		return this.datCustOpen;
	}
	public void setDatCustOpen(java.util.Date datCustOpen) {
		this.datCustOpen = datCustOpen;
	}

	public String getCodBankCard() {
		return this.codBankCard;
	}
	public void setCodBankCard(String codBankCard) {
		this.codBankCard = codBankCard;
	}

	public String getFlgCustTyp() {
		return this.flgCustTyp;
	}
	public void setFlgCustTyp(String flgCustTyp) {
		this.flgCustTyp = flgCustTyp;
	}

	public String getCifType() {
		return this.cifType;
	}
	public void setCifType(String cifType) {
		this.cifType = cifType;
	}

	public String getFlagStaff() {
		return this.flagStaff;
	}
	public void setFlagStaff(String flagStaff) {
		this.flagStaff = flagStaff;
	}

	public String getFlgLocGlob() {
		return this.flgLocGlob;
	}
	public void setFlgLocGlob(String flgLocGlob) {
		this.flgLocGlob = flgLocGlob;
	}

	public Integer getCodCcHomeBrn() {
		return this.codCcHomeBrn;
	}
	public void setCodCcHomeBrn(Integer codCcHomeBrn) {
		this.codCcHomeBrn = codCcHomeBrn;
	}

	public String getNamCustShrt() {
		return this.namCustShrt;
	}
	public void setNamCustShrt(String namCustShrt) {
		this.namCustShrt = namCustShrt;
	}

	public String getNamCustFull() {
		return this.namCustFull;
	}
	public void setNamCustFull(String namCustFull) {
		this.namCustFull = namCustFull;
	}

	public String getTxtCustDesgn() {
		return this.txtCustDesgn;
	}
	public void setTxtCustDesgn(String txtCustDesgn) {
		this.txtCustDesgn = txtCustDesgn;
	}
	
	public String getCodCustNatlId() {
		return this.codCustNatlId;
	}
	public void setCodCustNatlId(String codCustNatlId) {
		this.codCustNatlId = codCustNatlId;
	}

	public String getTxtCustAdrAdd1() {
		return this.txtCustAdrAdd1; 
	}
	public void setTxtCustAdrAdd1(String txtCustAdrAdd1) {
		this.txtCustAdrAdd1 = txtCustAdrAdd1;
	}

	public String getTxtCustAdrAdd2() {
		return this.txtCustAdrAdd2; 
	}
	public void setTxtCustAdrAdd2(String txtCustAdrAdd2) {
		this.txtCustAdrAdd2 = txtCustAdrAdd2;
	}

	public String getTxtCustAdrAdd3() {
		return this.txtCustAdrAdd3; 
	}
	public void setTxtCustAdrAdd3(String txtCustAdrAdd3) {
		this.txtCustAdrAdd3 = txtCustAdrAdd3;
	}

	public String getNamCustAdrCity() {
		return this.namCustAdrCity; 
	}
	public void setNamCustAdrCity(String namCustAdrCity) {
		this.namCustAdrCity = namCustAdrCity;
	}

	public String getNamCustAdrState() {
		return this.namCustAdrState; 
	}
	public void setNamCustAdrState(String namCustAdrState) {
		this.namCustAdrState = namCustAdrState;
	}

	public String getNamCustAdrCntry() {
		return this.namCustAdrCntry; 
	}
	public void setNamCustAdrCntry(String namCustAdrCntry) {
		this.namCustAdrCntry = namCustAdrCntry;
	}

	public String getTxtCustAdrZip() {
		return this.txtCustAdrZip; 
	}
	public void setTxtCustAdrZip(String txtCustAdrZip) {
		this.txtCustAdrZip = txtCustAdrZip;
	}

	public String getRefCustPhone() {
		return this.refCustPhone; 
	}
	public void setRefCustPhone(String refCustPhone) {
		this.refCustPhone = refCustPhone;
	}

	public String getRefCustPhoneOff() {
		return this.refCustPhoneOff; 
	}
	public void setRefCustPhoneOff(String refCustPhoneOff) {
		this.refCustPhoneOff = refCustPhoneOff;
	}

	public String getRefCustEmail() {
		return this.refCustEmail; 
	}
	public void setRefCustEmail(String refCustEmail) {
		this.refCustEmail = refCustEmail;
	}

	public String getTxtPermadrAdd3() {
		return this.txtPermadrAdd3;
	}
	public void setTxtPermadrAdd3(String txtPermadrAdd3) {
		this.txtPermadrAdd3 = txtPermadrAdd3;
	}

	public String getNamPermadrCity() {
		return this.namPermadrCity;
	}
	public void setNamPermadrCity(String namPermadrCity) {
		this.namPermadrCity = namPermadrCity;
	}

	public String getNamPermadrState() {
		return this.namPermadrState;
	}
	public void setNamPermadrState(String namPermadrState) {
		this.namPermadrState = namPermadrState;
	}

	public String getTxtPermadrZip() {
		return this.txtPermadrZip;
	}

	public void setTxtPermadrZip(String txtPermadrZip) {
		this.txtPermadrZip = txtPermadrZip;
	}

	public Date getDatBirthCust() {
		return this.datBirthCust;
	}
	public void setDatBirthCust(Date datBirthCust) {
		this.datBirthCust = datBirthCust;
	}

	public String getTxtCustSex() {
		return this.txtCustSex;
	}
	public void setTxtCustSex(String txtCustSex) {
		this.txtCustSex = txtCustSex;
	}

	public String getCodCustMarstat() {
		return this.codCustMarstat;
	}
	public void setCodCustMarstat(String codCustMarstat) {
		this.codCustMarstat = codCustMarstat;
	}

	public String getTxtCustEducn() {
		return this.txtCustEducn;
	}
	public void setTxtCustEducn(String txtCustEducn) {
		this.txtCustEducn = txtCustEducn;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(this.getClass().getSimpleName())
			.append("{")
			.append("ciCustMastPK=").append(this.ciCustMastPK)
			.append(",datCustOpen=").append(this.datCustOpen)
			.append(",codBankCard=").append(this.codBankCard)
			.append(",flgCustTyp=").append(this.flgCustTyp)
			.append(",cifType=").append(this.cifType)
			.append(",flagStaff=").append(this.flagStaff)
			.append(",flgLocGlob=").append(this.flgLocGlob)
			.append(",codCcHomeBrn=").append(this.codCcHomeBrn)
			.append(",namCustShrt=").append(this.namCustShrt)
			.append(",namCustFull=").append(this.namCustFull)
			.append(",txtCustDesgn=").append(this.txtCustDesgn)
			.append(",codCustNatlId=").append(this.codCustNatlId)
			.append(",txtCustAdrAdd1=").append(this.txtCustAdrAdd1)
			.append(",txtCustAdrAdd2=").append(this.txtCustAdrAdd2)
			.append(",txtCustAdrAdd3=").append(this.txtCustAdrAdd3)
			.append(",namCustAdrCity=").append(this.namCustAdrCity)
			.append(",namCustAdrState=").append(this.namCustAdrState)
			.append(",namCustAdrCntry=").append(this.namCustAdrCntry)
			.append(",txtCustAdrZip=").append(this.txtCustAdrZip)
			.append(",refCustPhone=").append(this.refCustPhone)
			.append(",refCustPhoneOff=").append(this.refCustPhoneOff)
			.append(",refCustEmail=").append(this.refCustEmail)
			.append(",txtPermadrAdd3=").append(this.txtPermadrAdd3)
			.append(",namPermadrCity=").append(this.namPermadrCity)
			.append(",namPermadrState=").append(this.namPermadrState)
			.append(",txtPermadrZip=").append(this.txtPermadrZip)
			.append(",datBirthCust=").append(this.datBirthCust)
			.append(",txtCustSex=").append(this.txtCustSex)
			.append(",codCustMarstat=").append(this.codCustMarstat)
			.append(",txtCustEducn=").append(this.txtCustEducn)
			.append("}");
		
		return super.toString();
	}
	
}
