package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class PmFinInstDirMast implements java.io.Serializable {
	private PmFinInstDirMastPK compositeId;
	private String namBank;
	private String namBranch;
	private String codZoneId;
	private String codBank;
	private String codBI;
	
	
	public PmFinInstDirMastPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(PmFinInstDirMastPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getNamBank() {
		return this.namBank;
	}
	public void setNamBank(String namBank) {
		this.namBank = namBank;
	}
	
	public String getNamBranch() {
		return this.namBranch;
	}
	public void setNamBranch(String namBranch) {
		this.namBranch = namBranch;
	}
	
	public String getCodZoneId() {
		return this.codZoneId;
	}
	public void setCodZoneId(String codZoneId) {
		this.codZoneId = codZoneId;
	}
	
	public String getCodBank() {
		return this.codBank;
	}
	public void setCodBank(String codBank) {
		this.codBank = codBank;
	}
	
	public String getCodBI() {
		return this.codBI;
	}
	public void setCodBI(String codBI) {
		this.codBI = codBI;
	}
	
}
