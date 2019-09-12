package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeGLNo implements java.io.Serializable {
	public static final Integer TYPE_ADVANCED = AmortizeGLNoPK.TYPE_ADVANCED;
	public static final Integer TYPE_ARREAR = AmortizeGLNoPK.TYPE_ARREAR;
	public static final Integer PROCESS_TYPE_OPENING_ACCOUNT = AmortizeGLNoPK.PROCESS_TYPE_OPENING_ACCOUNT;
	public static final Integer PROCESS_TYPE_CANCELING_ACCOUNT = AmortizeGLNoPK.PROCESS_TYPE_CANCELING_ACCOUNT;
	public static final Integer PROCESS_TYPE_END_OF_MONTH = AmortizeGLNoPK.PROCESS_TYPE_END_OF_MONTH;
	public static final Integer PROCESS_TYPE_CLOSING_ACCOUNT = AmortizeGLNoPK.PROCESS_TYPE_CLOSING_ACCOUNT;
	public static final String DRCR_DEBIT = AmortizeGLNoPK.DRCR_DEBIT;
	public static final String DRCR_CREDIT = AmortizeGLNoPK.DRCR_CREDIT;
	
	private AmortizeGLNoPK compositeId;
	private String GLNo;
	private String codCCBrn;
	private String codLOB;
	private Integer codProduct;
	private Integer codTxnMnemonic;
	
	
	public AmortizeGLNoPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(AmortizeGLNoPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getGLNo() {
		return this.GLNo;
	}
	public void setGLNo(String GLNo) {
		this.GLNo = GLNo;
	}
	
	public String getCodCCBrn() {
		return this.codCCBrn;
	}
	public void setCodCCBrn(String codCCBrn) {
		this.codCCBrn = codCCBrn;
	}
	
	public String getCodLOB() {
		return this.codLOB;
	}
	public void setCodLOB(String codLOB) {
		this.codLOB = codLOB;
	}
	
	public Integer getCodProduct() {
		return this.codProduct;
	}
	public void setCodProduct(Integer codProduct) {
		this.codProduct = codProduct;
	}
	
	public Integer getCodTxnMnemonic() {
		return this.codTxnMnemonic;
	}
	public void setCodTxnMnemonic(Integer codTxnMnemonic) {
		this.codTxnMnemonic = codTxnMnemonic;
	}
	
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("compositeId=").append(this.compositeId)
    		.append(",GLNo=").append(this.GLNo)
    		.append(",codCCBrn=").append(this.codCCBrn)
    		.append(",codLOB=").append(this.codLOB)
    		.append(",codProduct=").append(this.codProduct)
    		.append(",codTxnMnemonic=").append(this.codTxnMnemonic)
    		.append("}");
    	
    	return sb.toString();
    }

}
