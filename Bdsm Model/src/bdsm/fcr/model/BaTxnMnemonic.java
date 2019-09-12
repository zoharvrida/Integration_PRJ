package bdsm.fcr.model;


/**
 * @author v00019372
 */

@SuppressWarnings("serial")
public class BaTxnMnemonic implements java.io.Serializable {
	private BaTxnMnemonicPK compositeId;
	private String codTxnLiteral;
	private String txtTxnDesc;
	private Integer codFmtTxn;
	private Integer codSC;
	private String codDRCR;
	private Integer codTxnType;
	private String codMntAction;
	private Integer codBaseTxnMnemonic;
	private String txtBaseTxnDesc;
	private String codTxnCategory;
	private String codTxnMode;
	private String codOrgChannel;
	private String codModule;
	private String refUDFNo;
	
	
	public BaTxnMnemonicPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(BaTxnMnemonicPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getCodTxnLiteral() {
		return this.codTxnLiteral;
	}
	public void setCodTxnLiteral(String codTxnLiteral) {
		this.codTxnLiteral = codTxnLiteral;
	}
	
	public String getTxtTxnDesc() {
		return this.txtTxnDesc;
	}
	public void setTxtTxnDesc(String txtTxnDesc) {
		this.txtTxnDesc = txtTxnDesc;
	}
	
	public Integer getCodFmtTxn() {
		return this.codFmtTxn;
	}
	public void setCodFmtTxn(Integer codFmtTxn) {
		this.codFmtTxn = codFmtTxn;
	}
	
	public Integer getCodSC() {
		return this.codSC;
	}
	public void setCodSC(Integer codSC) {
		this.codSC = codSC;
	}
	
	public String getCodDRCR() {
		return this.codDRCR;
	}
	public void setCodDRCR(String codDRCR) {
		this.codDRCR = codDRCR;
	}
	
	public Integer getCodTxnType() {
		return this.codTxnType;
	}
	public void setCodTxnType(Integer codTxnType) {
		this.codTxnType = codTxnType;
	}
	
	public String getCodMntAction() {
		return this.codMntAction;
	}
	public void setCodMntAction(String codMntAction) {
		this.codMntAction = codMntAction;
	}
	
	public Integer getCodBaseTxnMnemonic() {
		return this.codBaseTxnMnemonic;
	}
	public void setCodBaseTxnMnemonic(Integer codBaseTxnMnemonic) {
		this.codBaseTxnMnemonic = codBaseTxnMnemonic;
	}
	
	public String getTxtBaseTxnDesc() {
		return this.txtBaseTxnDesc;
	}
	public void setTxtBaseTxnDesc(String txtBaseTxnDesc) {
		this.txtBaseTxnDesc = txtBaseTxnDesc;
	}
	
	public String getCodTxnCategory() {
		return this.codTxnCategory;
	}
	public void setCodTxnCategory(String codTxnCategory) {
		this.codTxnCategory = codTxnCategory;
	}
	
	public String getCodTxnMode() {
		return this.codTxnMode;
	}
	public void setCodTxnMode(String codTxnMode) {
		this.codTxnMode = codTxnMode;
	}
	
	public String getCodOrgChannel() {
		return this.codOrgChannel;
	}
	public void setCodOrgChannel(String codOrgChannel) {
		this.codOrgChannel = codOrgChannel;
	}
	
	public String getCodModule() {
		return this.codModule;
	}
	public void setCodModule(String codModule) {
		this.codModule = codModule;
	}
	
	public String getRefUDFNo() {
		return this.refUDFNo;
	}
	public void setRefUDFNo(String refUDFNo) {
		this.refUDFNo = refUDFNo;
	}
	
	
	
	/* Hibernate to Database properties */
	protected Character getCodDRCRDB() {
		return (this.codDRCR == null)? null: this.codDRCR.charAt(0);
	}
	protected void setCodDRCRDB(Character status) {
		this.codDRCR = (codDRCR == null)? null: codDRCR.toString();
	}
	
	protected Character getCodMntActionDB() {
		return (this.codMntAction == null)? null: this.codMntAction.charAt(0);
	}
	protected void setCodMntActionDB(Character codMntAction) {
		this.codMntAction = (codMntAction == null)? null: codMntAction.toString();
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",compositeId=").append(this.compositeId)
			.append(",codTxnLiteral=").append(this.codTxnLiteral)
			.append(",txtTxnDesc=").append(this.txtTxnDesc)
			.append(",codFmtTxn=").append(this.codFmtTxn)
			.append(",codSC=").append(this.codSC)
			.append(",codDRCR=").append(this.codDRCR)
			.append(",codTxnType=").append(this.codTxnType)
			.append(",codMntAction=").append(this.codMntAction)
			.append(",codBaseTxnMnemonic=").append(this.codBaseTxnMnemonic)
			.append(",txtBaseTxnDesc=").append(this.txtBaseTxnDesc)
			.append(",codTxnCategory=").append(this.codTxnCategory)
			.append(",codTxnMode=").append(this.codTxnMode)
			.append(",codOrgChannel=").append(this.codOrgChannel)
			.append(",codModule=").append(this.codModule)
			.append(",refUDFNo=").append(this.refUDFNo)
			.append("}");
	
		return sb.toString();
	}
	
}
