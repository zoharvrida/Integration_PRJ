package bdsm.fcr.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class ChAcctMast implements Serializable{
	private ChAcctMastPK compositeId;
	private Integer codCcBrn;
	private Integer codProd;
	private Date datAcctOpen;
	private String codAcctTitle;
	private Integer codCust;
	private String namCustShrt;
	private Integer codCcy;
	private Integer codAcctStat;
	private Integer codTds;
	private BigDecimal balAcctMinReqd;
	private BigDecimal amtOdLmt;
	private BigDecimal balBook;
	private BigDecimal amtUnclr;
	private BigDecimal amtHld;
	private BigDecimal balAvailable;
	
	
	public ChAcctMastPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(ChAcctMastPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public Integer getCodCcBrn() {
		return this.codCcBrn;
	}
	public void setCodCcBrn(Integer codCcBrn) {
		this.codCcBrn = codCcBrn;
	}
	
	public Integer getCodProd() {
		return this.codProd;
	}
	public void setCodProd(Integer codProd) {
		this.codProd = codProd;
	}
	
	public Date getDatAcctOpen() {
		return this.datAcctOpen;
	}
	public void setDatAcctOpen(Date datAcctOpen) {
		this.datAcctOpen = datAcctOpen;
	}
	
	public String getCodAcctTitle() {
		return this.codAcctTitle;
	}
	public void setCodAcctTitle(String codAcctTitle) {
		this.codAcctTitle = codAcctTitle;
	}
	
	public Integer getCodCust() {
		return this.codCust;
	}
	public void setCodCust(Integer codCust) {
		this.codCust = codCust;
	}
	
	public String getNamCustShrt() {
		return this.namCustShrt;
	}
	public void setNamCustShrt(String namCustShrt) {
		this.namCustShrt = namCustShrt;
	}
	
	public Integer getCodCcy() {
		return this.codCcy;
	}
	public void setCodCcy(Integer codCcy) {
		this.codCcy = codCcy;
	}
	
	public Integer getCodAcctStat() {
		return this.codAcctStat;
	}
	public void setCodAcctStat(Integer codAcctStat) {
		this.codAcctStat = codAcctStat;
	}
	
	public Integer getCodTds() {
		return this.codTds;
	}
	public void setCodTds(Integer codTds) {
		this.codTds = codTds;
	}
	
	public BigDecimal getBalAcctMinReqd() {
		return this.balAcctMinReqd;
	}
	public void setBalAcctMinReqd(BigDecimal balAcctMinReqd) {
		this.balAcctMinReqd = balAcctMinReqd;
	}
	
	public BigDecimal getAmtOdLmt() {
		return this.amtOdLmt;
	}
	public void setAmtOdLmt(BigDecimal amtOdLmt) {
		this.amtOdLmt = amtOdLmt;
	}
	
	public BigDecimal getBalBook() {
		return this.balBook;
	}
	public void setBalBook(BigDecimal balBook) {
		this.balBook = balBook;
	}
	
	public BigDecimal getAmtUnclr() {
		return this.amtUnclr;
	}
	public void setAmtUnclr(BigDecimal amtUnclr) {
		this.amtUnclr = amtUnclr;
	}
	
	public BigDecimal getAmtHld() {
		return this.amtHld;
	}
	public void setAmtHld(BigDecimal amtHld) {
		this.amtHld = amtHld;
	}
	
	public BigDecimal getBalAvailable() {
		return this.balAvailable;
	}
	public void setBalAvailable(BigDecimal balAvailable) {
		this.balAvailable = balAvailable;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",compositeId=").append(this.compositeId)
			.append(",codCcBrn=").append(this.codCcBrn)
			.append(",codProd=").append(this.codProd)
			.append(",datAcctOpen=").append(this.datAcctOpen)
			.append(",codAcctTitle=").append(this.codAcctTitle)
			.append(",codCust=").append(this.codCust)
			.append(",namCustShrt=").append(this.namCustShrt)
			.append(",codCcy=").append(this.codCcy)
			.append(",codAcctStat=").append(this.codAcctStat)
			.append(",codTds=").append(this.codTds)
			.append(",balAcctMinReqd=").append(this.balAcctMinReqd)
			.append(",amtOdLmt=").append(this.amtOdLmt)
			.append(",balBook=").append(this.balBook)
			.append(",amtUnclr=").append(this.amtUnclr)
			.append(",amtHld=").append(this.amtHld)
			.append(",balAvailable=").append(this.balAvailable)
			.append("}");
		
		return sb.toString();
	}
	
}
