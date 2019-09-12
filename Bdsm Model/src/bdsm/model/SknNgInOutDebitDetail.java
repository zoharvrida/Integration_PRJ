/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutDebitDetail extends SknNgInOutDebitParent implements java.io.Serializable {
	private String identitasPesertaPenerima;
	private String sandiKotaPenerbit;
	private String namaNasabahPemegang;
	private String destCreditAccount;
	private String nomorIdentitasPemegang;
	private String debitAccount;
	private String jenisTransaksi;
	private String nomorWarkat;
	private String nominal;
	private String nomorUrut;
	private String nomorReferensi;
	private String bebanBiaya;
	private String sor;
	private Integer parentRecordId;
	private String recordStatus;
	private String comments;
	
	public String getIdentitasPesertaPenerima() {
		return this.identitasPesertaPenerima;
	}
	public void setIdentitasPesertaPenerima(String identitasPesertaPenerima) {
		this.identitasPesertaPenerima = identitasPesertaPenerima;
	}

	public String getSandiKotaPenerbit() {
		return this.sandiKotaPenerbit;
	}
	public void setSandiKotaPenerbit(String sandiKotaPenerbit) {
		this.sandiKotaPenerbit = sandiKotaPenerbit;
	}

	public String getNamaNasabahPemegang() {
		return this.namaNasabahPemegang;
	}
	public void setNamaNasabahPemegang(String namaNasabahPemegang) {
		this.namaNasabahPemegang = namaNasabahPemegang;
	}

	public String getDestCreditAccount() {
		return this.destCreditAccount;
	}
	public void setDestCreditAccount(String destCreditAccount) {
		this.destCreditAccount = destCreditAccount;
	}

	public String getNomorIdentitasPemegang() {
		return this.nomorIdentitasPemegang;
	}
	public void setNomorIdentitasPemegang(String nomorIdentitasPemegang) {
		this.nomorIdentitasPemegang = nomorIdentitasPemegang;
	}

	public String getDebitAccount() {
		return this.debitAccount;
	}
	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}

	public String getJenisTransaksi() {
		return this.jenisTransaksi;
	}
	public void setJenisTransaksi(String jenisTransaksi) {
		this.jenisTransaksi = jenisTransaksi;
	}

	public String getNomorWarkat() {
		return this.nomorWarkat;
	}
	public void setNomorWarkat(String nomorWarkat) {
		this.nomorWarkat = nomorWarkat;
	}

	public String getNominal() {
		return this.nominal;
	}
	public void setNominal(String nominal) {
		this.nominal = nominal;
	}

	public String getNomorUrut() {
		return this.nomorUrut;
	}
	public void setNomorUrut(String nomorUrut) {
		this.nomorUrut = nomorUrut;
	}

	public String getNomorReferensi() {
		return this.nomorReferensi;
	}
	public void setNomorReferensi(String nomorReferensi) {
		this.nomorReferensi = nomorReferensi;
	}

	public String getBebanBiaya() {
		return this.bebanBiaya;
	}
	public void setBebanBiaya(String bebanBiaya) {
		this.bebanBiaya = bebanBiaya;
	}

	public String getSor() {
		return this.sor;
	}
	public void setSor(String sor) {
		this.sor = sor;
	}
	
	public Integer getParentRecordId() {
		return parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	public String getComments() {
		return this.comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append(super.toString())
    		.append(",identitasPesertaPenerima=").append(this.identitasPesertaPenerima)
    		.append(",sandiKotaPenerbit=").append(this.sandiKotaPenerbit)
    		.append(",namaNasabahPemegang=").append(this.namaNasabahPemegang)
    		.append(",destCreditAccount=").append(this.destCreditAccount)
    		.append(",nomorIdentitasPemegang=").append(this.nomorIdentitasPemegang)
    		.append(",debitAccount=").append(this.debitAccount)
    		.append(",jenisTransaksi=").append(this.jenisTransaksi)
    		.append(",nomorWarkat=").append(this.nomorWarkat)
    		.append(",nominal=").append(this.nominal)
    		.append(",nomorUrut=").append(this.nomorUrut)
    		.append(",nomorReferensi=").append(this.nomorReferensi)
    		.append(",bebanBiaya=").append(this.bebanBiaya)
    		.append(",sor=").append(this.sor)
    		.append(",parentRecordId=").append(this.parentRecordId)
    		.append(",recordStatus=").append(this.recordStatus)
    		.append(",comments=").append(this.comments)
    		.append("}");
    	
    	return sb.toString();
    }
    
}
