/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;


/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class CasaStatusTmp extends BaseModel{
    private Integer id;
    private String codAcctNo;
    private String codCCBrn;
    private int codAcctStatCurr;
    private int codAcctStatNew;
    private Date datApplied;
    private String status;
    private String statusReason;
    private String noBatch;

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

	/**
	 * @return the codAcctNo
	 */
	public String getCodAcctNo() {
		return codAcctNo;
	}

	/**
	 * @param codAcctNo the codAcctNo to set
	 */
	public void setCodAcctNo(String codAcctNo) {
		this.codAcctNo = codAcctNo;
	}

	/**
	 * @return the codCCBrn
	 */
	public String getCodCCBrn() {
		return codCCBrn;
	}

	/**
	 * @param codCCBrn the codCCBrn to set
	 */
	public void setCodCCBrn(String codCCBrn) {
		this.codCCBrn = codCCBrn;
	}

	/**
	 * @return the codAcctStatCurr
	 */
	public int getCodAcctStatCurr() {
		return codAcctStatCurr;
	}

	/**
	 * @param codAcctStatCurr the codAcctStatCurr to set
	 */
	public void setCodAcctStatCurr(int codAcctStatCurr) {
		this.codAcctStatCurr = codAcctStatCurr;
	}

	/**
	 * @return the codAcctStatNew
	 */
	public int getCodAcctStatNew() {
		return codAcctStatNew;
	}

	/**
	 * @param codAcctStatNew the codAcctStatNew to set
	 */
	public void setCodAcctStatNew(int codAcctStatNew) {
		this.codAcctStatNew = codAcctStatNew;
	}

	/**
	 * @return the datApplied
	 */
	public Date getDatApplied() {
		return datApplied;
	}

	/**
	 * @param datApplied the datApplied to set
	 */
	public void setDatApplied(Date datApplied) {
		this.datApplied = datApplied;
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
	 * @return the noBatch
	 */
	public String getNoBatch() {
		return noBatch;
	}

	/**
	 * @param noBatch the noBatch to set
	 */
	public void setNoBatch(String noBatch) {
		this.noBatch = noBatch;
	}

    
}
