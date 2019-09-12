/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;


/**
 * 
 * @author v00019237
 */
public class TmpSknngInOutCreditFooter extends BaseModel implements java.io.Serializable {
	private TmpSknngInoutHFPK compositeId;
	private String tipeRecord;
	private String kodeDke;
	private String crc;
	private String type;
	private String extractStatus;
	private String message;
	private Integer Tsuccess;
        private Integer Treject;
	
	public TmpSknngInoutHFPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(TmpSknngInoutHFPK compositeId) {
		this.compositeId = compositeId;
	}


	public String getTipeRecord() {
		return this.tipeRecord;
	}
	public void setTipeRecord(String tipeRecord) {
		this.tipeRecord = tipeRecord;
	}

	public String getKodeDke() {
		return this.kodeDke;
	}
	public void setKodeDke(String kodeDke) {
		this.kodeDke = kodeDke;
	}

	public String getCrc() {
		return this.crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getExtractStatus() {
		return this.extractStatus;
	}
	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    /**
     * @return the Tsuccess
     */
    public Integer getTsuccess() {
        return Tsuccess;
    }

    /**
     * @param Tsuccess the Tsuccess to set
     */
    public void setTsuccess(Integer Tsuccess) {
        this.Tsuccess = Tsuccess;
    }

    /**
     * @return the Treject
     */
    public Integer getTreject() {
        return Treject;
    }

    /**
     * @param Treject the Treject to set
     */
    public void setTreject(Integer Treject) {
        this.Treject = Treject;
    }
}
