/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.math.BigDecimal;

/**
 *
 * @author user
 */
public class FcrChAcctMast extends BaseModel {
    private FcrChAcctMastPK compositeId;
    private Integer codEntityVpd;
    private Integer codCust;
    private Integer codTds;
    private Integer codAcctStat;
    private String codAcctTitle;
    private Integer codCcy;
	private BigDecimal balAvail;
	private BigDecimal amtOdLmt;
	private BigDecimal balacct;
	private BigDecimal amtHld;
    
    
    /**
     * @return the compositeId
     */
    public FcrChAcctMastPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FcrChAcctMastPK compositeId) {
        this.compositeId = compositeId;
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

    /**
     * @return the codCust
     */
    public Integer getCodCust() {
        return codCust;
    }

    /**
     * @param codCust the codCust to set
     */
    public void setCodCust(Integer codCust) {
        this.codCust = codCust;
    }

    /**
     * @return the codTds
     */
    public Integer getCodTds() {
        return codTds;
    }

    /**
     * @param codTds the codTds to set
     */
    public void setCodTds(Integer codTds) {
        this.codTds = codTds;
    }
    
    /**
     * @return the codAcctStat
     */
    public Integer getCodAcctStat() {
        return codAcctStat;
    }

    /**
     * @param codAcctStat the codAcctStat to set
     */
    public void setCodAcctStat(Integer codAcctStat) {
        this.codAcctStat = codAcctStat;
    }

    /**
     * @return the codAcctTitle
     */
    public String getCodAcctTitle() {
        return codAcctTitle;
    }

    /**
     * @param codAcctTitle the codAcctTitle to set
     */
    public void setCodAcctTitle(String codAcctTitle) {
        this.codAcctTitle = codAcctTitle;
    }

    /**
     * @return the codCcy
     */
    public Integer getCodCcy() {
        return codCcy;
    }

    /**
     * @param codCcy the codCcy to set
     */
    public void setCodCcy(Integer codCcy) {
        this.codCcy = codCcy;
    }

	/**
	 * @return the balAvail
	 */
	public BigDecimal getBalAvail() {
		return balAvail;
	}

	/**
	 * @param balAvail the balAvail to set
	 */
	public void setBalAvail(BigDecimal balAvail) {
		this.balAvail = balAvail;
	}

	/**
	 * @return the amtOdLmt
	 */
	public BigDecimal getAmtOdLmt() {
		return amtOdLmt;
	}

	/**
	 * @param amtOdLmt the amtOdLmt to set
	 */
	public void setAmtOdLmt(BigDecimal amtOdLmt) {
		this.amtOdLmt = amtOdLmt;
	}

	/**
	 * @return the balacct
	 */
	public BigDecimal getBalacct() {
		return balacct;
	}

	/**
	 * @param balacct the balacct to set
	 */
	public void setBalacct(BigDecimal balacct) {
		this.balacct = balacct;
	}

	/**
	 * @return the amtHld
	 */
	public BigDecimal getAmtHld() {
		return amtHld;
	}

	/**
	 * @param amtHld the amtHld to set
	 */
	public void setAmtHld(BigDecimal amtHld) {
		this.amtHld = amtHld;
	}

    @Override
    public String toString() {
        return "FcrChAcctMast{" + "compositeId=" + compositeId + ", codEntityVpd=" + codEntityVpd + ", codCust=" + codCust + ", codTds=" + codTds + ", codAcctStat=" + codAcctStat + ", codAcctTitle=" + codAcctTitle + ", codCcy=" + codCcy + ", balAvail=" + balAvail + ", amtOdLmt=" + amtOdLmt + ", balacct=" + balacct + ", amtHld=" + amtHld + '}';
    }
    
}