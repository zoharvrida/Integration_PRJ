/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00020841
 */
public class BaCcBrnMast extends BaseModel{
	private BaCcBrnMastPK compositeId;
    private Integer codBrnType;
    private Integer codMainBranch;
    private String namBranch;
    private String namBranchShrt;
    private String namCcCity;
    private String namCcCountry;
    private Integer updtSrlNo;
    private Integer codCcBrnXref;


    public Integer getCodBrnType() {
        return codBrnType;
    }

    public void setCodBrnType(Integer codBrnType) {
        this.codBrnType = codBrnType;
    }

    public Integer getCodMainBranch() {
        return codMainBranch;
    }

    public void setCodMainBranch(Integer codMainBranch) {
        this.codMainBranch = codMainBranch;
    }

    public String getNamBranch() {
        return namBranch;
    }

    public void setNamBranch(String namBranch) {
        this.namBranch = namBranch;
    }

    public String getNamBranchShrt() {
        return namBranchShrt;
    }

    public void setNamBranchShrt(String namBranchShrt) {
        this.namBranchShrt = namBranchShrt;
    }

    public String getNamCcCity() {
        return namCcCity;
    }

    public void setNamCcCity(String namCcCity) {
        this.namCcCity = namCcCity;
    }

    public String getNamCcCountry() {
        return namCcCountry;
    }

    public void setNamCcCountry(String namCcCountry) {
        this.namCcCountry = namCcCountry;
    }

    public Integer getUpdtSrlNo() {
        return updtSrlNo;
    }

    public void setUpdtSrlNo(Integer updtSrlNo) {
        this.updtSrlNo = updtSrlNo;
    }

    public Integer getCodCcBrnXref() {
        return codCcBrnXref;
    }

    public void setCodCcBrnXref(Integer codCcBrnXref) {
        this.codCcBrnXref = codCcBrnXref;
    }

	/**
	 * @return the compositeId
	 */
	public BaCcBrnMastPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(BaCcBrnMastPK compositeId) {
		this.compositeId = compositeId;
	}
}
