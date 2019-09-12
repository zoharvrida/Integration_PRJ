/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author NCBS
 */
public class UfcrCfFlyUsrTmpSrc extends BaseModel{

    private String CodUserId;
    private String SourceCodTempl;
    private String SourceNamTempl;
    private String codBrnSource;
    private String SourceNamBranch;
    private String TargetCodTempl;
    private String TargetNamTempl;    
    private String codBrnTarget;
    private String TargetNamBranch;
    private String EfektifDate;        
    private String status;
    private String statusreason;
    private String nobatch;
    private Integer id;

	/**
	 * @return the CodUserId
	 */
	public String getCodUserId() {
		return CodUserId;
	}

	/**
	 * @param CodUserId the CodUserId to set
	 */
	public void setCodUserId(String CodUserId) {
		this.CodUserId = CodUserId;
	}

	/**
	 * @return the SourceCodTempl
	 */
	public String getSourceCodTempl() {
		return SourceCodTempl;
	}

	/**
	 * @param SourceCodTempl the SourceCodTempl to set
	 */
	public void setSourceCodTempl(String SourceCodTempl) {
		this.SourceCodTempl = SourceCodTempl;
	}

	/**
	 * @return the SourceNamTempl
	 */
	public String getSourceNamTempl() {
		return SourceNamTempl;
	}

	/**
	 * @param SourceNamTempl the SourceNamTempl to set
	 */
	public void setSourceNamTempl(String SourceNamTempl) {
		this.SourceNamTempl = SourceNamTempl;
	}

	/**
	 * @return the codBrnSource
	 */
	public String getCodBrnSource() {
		return codBrnSource;
	}

	/**
	 * @param codBrnSource the codBrnSource to set
	 */
	public void setCodBrnSource(String codBrnSource) {
		this.codBrnSource = codBrnSource;
	}

	/**
	 * @return the SourceNamBranch
	 */
	public String getSourceNamBranch() {
		return SourceNamBranch;
	}

	/**
	 * @param SourceNamBranch the SourceNamBranch to set
	 */
	public void setSourceNamBranch(String SourceNamBranch) {
		this.SourceNamBranch = SourceNamBranch;
	}

	/**
	 * @return the TargetCodTempl
	 */
	public String getTargetCodTempl() {
		return TargetCodTempl;
	}

	/**
	 * @param TargetCodTempl the TargetCodTempl to set
	 */
	public void setTargetCodTempl(String TargetCodTempl) {
		this.TargetCodTempl = TargetCodTempl;
	}

	/**
	 * @return the TargetNamTempl
	 */
	public String getTargetNamTempl() {
		return TargetNamTempl;
	}

	/**
	 * @param TargetNamTempl the TargetNamTempl to set
	 */
	public void setTargetNamTempl(String TargetNamTempl) {
		this.TargetNamTempl = TargetNamTempl;
	}

	/**
	 * @return the codBrnTarget
	 */
	public String getCodBrnTarget() {
		return codBrnTarget;
	}

	/**
	 * @param codBrnTarget the codBrnTarget to set
	 */
	public void setCodBrnTarget(String codBrnTarget) {
		this.codBrnTarget = codBrnTarget;
	}

	/**
	 * @return the TargetNamBranch
	 */
	public String getTargetNamBranch() {
		return TargetNamBranch;
	}

	/**
	 * @param TargetNamBranch the TargetNamBranch to set
	 */
	public void setTargetNamBranch(String TargetNamBranch) {
		this.TargetNamBranch = TargetNamBranch;
	}

	/**
	 * @return the EfektifDate
	 */
	public String getEfektifDate() {
		return EfektifDate;
	}

	/**
	 * @param EfektifDate the EfektifDate to set
	 */
	public void setEfektifDate(String EfektifDate) {
		this.EfektifDate = EfektifDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusreason
	 */
	public String getStatusreason() {
		return statusreason;
	}

	/**
	 * @param statusreason the statusreason to set
	 */
	public void setStatusreason(String statusreason) {
		this.statusreason = statusreason;
	}

	/**
	 * @return the nobatch
	 */
	public String getNobatch() {
		return nobatch;
	}

	/**
	 * @param nobatch the nobatch to set
	 */
	public void setNobatch(String nobatch) {
		this.nobatch = nobatch;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


}
