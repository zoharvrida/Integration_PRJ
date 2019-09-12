/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import java.io.Serializable;

/**
 *
 * @author NCBS
 */
public class BaCcBrnMastPK implements Serializable{
	private Integer codCcBrn;
	private String flgMntStatus;
	private Integer codEntityVpd;

	/**
	 * @return the codccBrn
	 */
	public Integer getCodccBrn() {
		return codCcBrn;
	}

	/**
	 * @param codccBrn the codccBrn to set
	 */
	public void setCodccBrn(Integer codCcBrn) {
		this.codCcBrn = codCcBrn;
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
}
