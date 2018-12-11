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
	
        /**
         * 
         * @return
         */
        public TmpSknngInoutHFPK getCompositeId() {
		return this.compositeId;
	}
        /**
         * 
         * @param compositeId
         */
        public void setCompositeId(TmpSknngInoutHFPK compositeId) {
		this.compositeId = compositeId;
	}


    /**
     * 
     * @return
     */
    public String getTipeRecord() {
		return this.tipeRecord;
	}
    /**
     * 
     * @param tipeRecord
     */
    public void setTipeRecord(String tipeRecord) {
		this.tipeRecord = tipeRecord;
	}

    /**
     * 
     * @return
     */
    public String getKodeDke() {
		return this.kodeDke;
	}
    /**
     * 
     * @param kodeDke
     */
    public void setKodeDke(String kodeDke) {
		this.kodeDke = kodeDke;
	}

    /**
     * 
     * @return
     */
    public String getCrc() {
		return this.crc;
	}
    /**
     * 
     * @param crc
     */
    public void setCrc(String crc) {
		this.crc = crc;
	}

    /**
     * 
     * @return
     */
    public String getType() {
		return this.type;
	}
    /**
     * 
     * @param type
     */
    public void setType(String type) {
		this.type = type;
	}

    /**
     * 
     * @return
     */
    public String getExtractStatus() {
		return this.extractStatus;
	}
    /**
     * 
     * @param extractStatus
     */
    public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

    /**
     * 
     * @return
     */
    public String getMessage() {
		return this.message;
	}
    /**
     * 
     * @param message
     */
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
