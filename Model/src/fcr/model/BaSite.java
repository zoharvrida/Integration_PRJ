package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaSite implements java.io.Serializable {
	private BaSitePK compositeId;
	private String macroType;
	private String macroDescription;
	private String codMntAction;
	private String codLastMntMakerId;
	private String codLastMntChkrId;
	private java.util.Date datLastMnt;
	
	
	public BaSitePK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(BaSitePK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getMacroType() {
		return this.macroType;
	}
	public void setMacroType(String macroType) {
		this.macroType = macroType;
	}
	
	public String getMacroDescription() {
		return this.macroDescription;
	}
	public void setMacroDescription(String macroDescription) {
		this.macroDescription = macroDescription;
	}
	
	public String getCodMntAction() {
		return this.codMntAction;
	}
	public void setCodMntAction(String codMntAction) {
		this.codMntAction = codMntAction;
	}
	
	public String getCodLastMntMakerId() {
		return this.codLastMntMakerId;
	}
	public void setCodLastMntMakerId(String codLastMntMakerId) {
		this.codLastMntMakerId = codLastMntMakerId;
	}
	
	public String getCodLastMntChkrId() {
		return this.codLastMntChkrId;
	}
	public void setCodLastMntChkrId(String codLastMntChkrId) {
		this.codLastMntChkrId = codLastMntChkrId;
	}
	
	public java.util.Date getDatLastMnt() {
		return this.datLastMnt;
	}
	public void setDatLastMnt(java.util.Date datLastMnt) {
		this.datLastMnt = datLastMnt;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",compositeId=").append(this.compositeId)
			.append(",macroType=").append(this.macroType)
			.append(",macroDescription=").append(this.macroDescription)
			.append(",codMntAction=").append(this.codMntAction)
			.append(",codLastMntMakerId=").append(this.codLastMntMakerId)
			.append(",codLastMntChkrId=").append(this.codLastMntChkrId)
			.append(",datLastMnt=").append(this.datLastMnt)
			.append("}");
	
		return sb.toString();
	}
	
}
