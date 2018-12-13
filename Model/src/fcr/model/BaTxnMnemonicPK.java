package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaTxnMnemonicPK implements java.io.Serializable {
	private Integer codTxnMnemonic;
	private String flgMntStatus;
	private Integer codEntityVPD = new Integer(11);
	
	
	public BaTxnMnemonicPK() {}
	
	public BaTxnMnemonicPK(Integer codTxnMnemonic) {
		this(codTxnMnemonic, "A");
	}
	
	public BaTxnMnemonicPK(Integer codTxnMnemonic, String flgMntStatus) {
		this.codTxnMnemonic = codTxnMnemonic;
		this.flgMntStatus = flgMntStatus;
	}
	
	
	public Integer getCodTxnMnemonic() {
		return this.codTxnMnemonic;
	}
	public void setCodTxnMnemonic(Integer codTxnMnemonic) {
		this.codTxnMnemonic = codTxnMnemonic;
	}
	
	public String getFlgMntStatus() {
		return this.flgMntStatus;
	}
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}
	
	public Integer getCodEntityVPD() {
		return this.codEntityVPD;
	}
	public void setCodEntityVPD(Integer codEntityVPD) {
		this.codEntityVPD = codEntityVPD;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("codTxnMnemonic=").append(this.codTxnMnemonic)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVPD=").append(this.codEntityVPD)
			.append("}");
	
		return sb.toString();
	}

}
