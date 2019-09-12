/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author v00019722
 */
public class AmortizeCustomOpening implements Serializable {
    private String acctNo;
    private String codProd;
    private String giftCode;
    private String programID;
    private String programName;
    private String Term;
    private String Status;
    private String OpenCancelDate;
    private String giftPrice;
    private String giftGrossPrice;
    private String taxAmount;
    private String holdAmount;
    private String minHoldAmount;
    private String maxHoldAmount;
    private String AccrualID;
    private String tax;
    private String dateexpiry;
    private String amortType;
    private String state;
    private String Mode;
    private String effectiveDate;
    private String sequenceID;
    private String id_programDetails;
	private String namProduct;
	private String instrMode;
	private String batchNo;
	private String voucherType;
	private String glCode;
	private String Branch;
	private String LOBS;
		/**
     * @return the acctNo
     */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * @param acctNo the acctNo to set
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    /**
     * @return the codProd
     */
    public String getCodProd() {
        return codProd;
    }

    /**
     * @param codProd the codProd to set
     */
    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    /**
     * @return the giftCode
     */
    public String getGiftCode() {
        return giftCode;
    }

    /**
     * @param giftCode the giftCode to set
     */
    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }

    /**
     * @return the programID
     */
    public String getProgramID() {
        return programID;
    }

    /**
     * @param programID the programID to set
     */
    public void setProgramID(String programID) {
        this.programID = programID;
    }

    /**
     * @return the Term
     */
    public String getTerm() {
        return Term;
    }

    /**
     * @param Term the Term to set
     */
    public void setTerm(String Term) {
        this.Term = Term;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * @return the OpenCancelDate
     */
    public String getOpenCancelDate() {
        return OpenCancelDate;
    }

    /**
     * @param OpenCancelDate the OpenCancelDate to set
     */
    public void setOpenCancelDate(String OpenCancelDate) {
        this.OpenCancelDate = OpenCancelDate;
    }

    /**
     * @return the holdAmount
     */
    public String getHoldAmount() {
        return holdAmount;
    }

    /**
     * @param holdAmount the holdAmount to set
     */
    public void setHoldAmount(String holdAmount) {
        this.holdAmount = holdAmount;
    }

    /**
     * @return the programName
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * @param programName the programName to set
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * @return the AccrualID
     */
    public String getAccrualID() {
        return AccrualID;
    }

    /**
     * @param AccrualID the AccrualID to set
     */
    public void setAccrualID(String AccrualID) {
        this.AccrualID = AccrualID;
    }

    /**
     * @return the giftPrice
     */
    public String getGiftPrice() {
        return giftPrice;
    }

    /**
     * @param giftPrice the giftPrice to set
     */
    public void setGiftPrice(String giftPrice) {
        this.giftPrice = giftPrice;
    }

    /**
     * @return the tax
     */
    public String getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     * @return the dateexpiry
     */
    public String getDateexpiry() {
        return dateexpiry;
    }

    /**
     * @param dateexpiry the dateexpiry to set
     */
    public void setDateexpiry(String dateexpiry) {
        this.dateexpiry = dateexpiry;
    }

    /**
     * @return the amortType
     */
    public String getAmortType() {
        return amortType;
    }

    /**
     * @param amortType the amortType to set
     */
    public void setAmortType(String amortType) {
        this.amortType = amortType;
    }

    /**
     * @return the giftGrossPrice
     */
    public String getGiftGrossPrice() {
        return giftGrossPrice;
    }

    /**
     * @param giftGrossPrice the giftGrossPrice to set
     */
    public void setGiftGrossPrice(String giftGrossPrice) {
        this.giftGrossPrice = giftGrossPrice;
    }

    /**
     * @return the taxAmount
     */
    public String getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount the taxAmount to set
     */
    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the Mode
     */
    public String getMode() {
        return Mode;
    }

    /**
     * @param Mode the Mode to set
     */
    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    /**
     * @return the effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the minHoldAmount
     */
    public String getMinHoldAmount() {
        return minHoldAmount;
    }

    /**
     * @param minHoldAmount the minHoldAmount to set
     */
    public void setMinHoldAmount(String minHoldAmount) {
        this.minHoldAmount = minHoldAmount;
    }

    /**
     * @return the maxHoldAmount
     */
    public String getMaxHoldAmount() {
        return maxHoldAmount;
    }

    /**
     * @param maxHoldAmount the maxHoldAmount to set
     */
    public void setMaxHoldAmount(String maxHoldAmount) {
        this.maxHoldAmount = maxHoldAmount;
    }

    /**
     * @return the sequenceID
     */
    public String getSequenceID() {
        return sequenceID;
    }

    /**
     * @param sequenceID the sequenceID to set
     */
    public void setSequenceID(String sequenceID) {
        this.sequenceID = sequenceID;
    }

    /**
     * @return the id_programDetails
     */
    public String getId_programDetails() {
        return id_programDetails;
    }

    /**
     * @param id_programDetails the id_programDetails to set
     */
    public void setId_programDetails(String id_programDetails) {
        this.id_programDetails = id_programDetails;
    }

	/**
	 * @return the namProduct
	 */
	public String getNamProduct() {
		return namProduct;
	}

	/**
	 * @param namProduct the namProduct to set
	 */
	public void setNamProduct(String namProduct) {
		this.namProduct = namProduct;
	}

	/**
	 * @return the instrMode
	 */
	public String getInstrMode() {
		return instrMode;
	}

	/**
	 * @param instrMode the instrMode to set
	 */
	public void setInstrMode(String instrMode) {
		this.instrMode = instrMode;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the voucherType
	 */
	public String getVoucherType() {
		return voucherType;
	}

	/**
	 * @param voucherType the voucherType to set
	 */
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	/**
	 * @return the glCode
	 */
	public String getGlCode() {
		return glCode;
	}

	/**
	 * @param glCode the glCode to set
	 */
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	/**
	 * @return the Branch
	 */
	public String getBranch() {
		return Branch;
	}

	/**
	 * @param Branch the Branch to set
	 */
	public void setBranch(String Branch) {
		this.Branch = Branch;
	}

	/**
	 * @return the LOBS
	 */
	public String getLOBS() {
		return LOBS;
	}

	/**
	 * @param LOBS the LOBS to set
	 */
	public void setLOBS(String LOBS) {
		this.LOBS = LOBS;
	}
}
