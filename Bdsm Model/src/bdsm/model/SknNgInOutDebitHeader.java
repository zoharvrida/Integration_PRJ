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
public class SknNgInOutDebitHeader extends SknNgInOutDebitParent implements java.io.Serializable {
	private String batchId;
	private String jamTanggalMessage;
	private String jumlahRecords;
	private String totalNominal;
	private String tanggalBatch;
	private String jenisSetelmen;
	private String identitasPesertaPengirim;
	private String sandiKotaPengirim;
	private Boolean hPlus1;
	private Boolean approved;
	private String status;
	private String comments;
	
	
	public String getBatchId() {
		return this.batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getJamTanggalMessage() {
		return this.jamTanggalMessage;
	}
	public void setJamTanggalMessage(String jamTanggalMessage) {
		this.jamTanggalMessage = jamTanggalMessage;
	}

	public String getJumlahRecords() {
		return this.jumlahRecords;
	}
	public void setJumlahRecords(String jumlahRecords) {
		this.jumlahRecords = jumlahRecords;
	}

	public String getTotalNominal() {
		return this.totalNominal;
	}
	public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
	}

	public String getTanggalBatch() {
		return this.tanggalBatch;
	}
	public void setTanggalBatch(String tanggalBatch) {
		this.tanggalBatch = tanggalBatch;
	}

	public String getJenisSetelmen() {
		return this.jenisSetelmen;
	}
	public void setJenisSetelmen(String jenisSetelmen) {
		this.jenisSetelmen = jenisSetelmen;
	}

	public String getIdentitasPesertaPengirim() {
		return this.identitasPesertaPengirim;
	}
	public void setIdentitasPesertaPengirim(String identitasPesertaPengirim) {
		this.identitasPesertaPengirim = identitasPesertaPengirim;
	}

	public String getSandiKotaPengirim() {
		return this.sandiKotaPengirim;
	}
	public void setSandiKotaPengirim(String sandiKotaPengirim) {
		this.sandiKotaPengirim = sandiKotaPengirim;
	}
	
	public Boolean getHPlus1() {
		return this.hPlus1;
	}
	public void setHPlus1(Boolean hPlus1) {
		this.hPlus1 = hPlus1;
	}
	
	public Boolean getApproved() {
		return this.approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getComments() {
		return this.comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	/* Hibernate to Database properties */
	protected Character getStatusDB() {
		return (this.status == null)? null: this.status.charAt(0);
	}
	protected void setStatusDB(Character status) {
		this.status = (status == null)? null: status.toString();
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append(super.toString())
    		.append(",batchId=").append(this.batchId)
    		.append(",jamTanggalMessage=").append(this.jamTanggalMessage)
    		.append(",jumlahRecords=").append(this.jumlahRecords)
    		.append(",totalNominal=").append(this.totalNominal)
    		.append(",tanggalBatch=").append(this.tanggalBatch)
    		.append(",jenisSetelmen=").append(this.jenisSetelmen)
    		.append(",identitasPesertaPengirim=").append(this.identitasPesertaPengirim)
    		.append(",sandiKotaPengirim=").append(this.sandiKotaPengirim)
    		.append(",approved=").append(this.approved)
    		.append(",status=").append(this.status)
    		.append(",comments=").append(this.comments)
    		.append("}");
    	
    	return sb.toString();
    }

}
