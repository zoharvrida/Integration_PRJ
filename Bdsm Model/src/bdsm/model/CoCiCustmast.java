/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author user
 */
public class CoCiCustmast extends BaseModel {

    private CoCiCustmastPK compositeId;
    	private String address;
        private Integer codCustId;
        private String namCustFull;
		private String namCustFirst;
    private String extFlag;
 
    /**
     * @return the compositeId
     */
    /**
     * @return the namCustFull
     */
    public String getNamCustFull() {
        return namCustFull;
    }

    /**
     * @param namCustFull the namCustFull to set
     */
    public void setNamCustFull(String namCustFull) {
        this.namCustFull = namCustFull;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the compositeId
     */
    public CoCiCustmastPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(CoCiCustmastPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the codCustId
     */
    public Integer getCodCustId() {
        return codCustId;
    }

    /**
     * @param codCustId the codCustId to set
     */
    public void setCodCustId(Integer codCustId) {
        this.codCustId = codCustId;
    }

	/**
	 * @return the namCustFirst
	 */
	public String getNamCustFirst() {
		return namCustFirst;
	}

	/**
	 * @param namCustFirst the namCustFirst to set
	 */
	public void setNamCustFirst(String namCustFirst) {
		this.namCustFirst = namCustFirst;
	}

    /**
     * @return the extFlag
     */
    public String getExtFlag() {
        return extFlag;
    }

    /**
     * @param extFlag the extFlag to set
     */
    public void setExtFlag(String extFlag) {
        this.extFlag = extFlag;
    }
   
    @Override
    public String toString() {
        return "CoCiCustmast{" + "address=" + address + ", namCustFull=" + namCustFull + ", namCustFirst=" + namCustFirst + ", extFlag=" + extFlag + '}';
    }
}