package bdsm.model;


import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BigBillTrx implements java.io.Serializable {
	private String recordId;
	private String customerIdPelanggan;
	private String typeTransaction;
	private String tarifDaya;
	private String periode;
	private String KWHAwal;
	private String KWHAkhir;
	private BigDecimal amountFlagging;
	private BigDecimal amountTagihan;
	private String sisaTunggakan;
	private String responStatus;
	private String remarks;
	private String userId;
	private String supervisorId;
	private String recordStatus;
	private Integer retry;
	private Timestamp dateTimeUpload;
	private Timestamp dateTimeProcess;
	private Timestamp dateTimeFinish;
	private String idBatchSMSTransaction;
	
	
	
	public String getRecordId() {
		return this.recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getCustomerIdPelanggan() {
		return this.customerIdPelanggan;
	}
	public void setCustomerIdPelanggan(String customerIdPelanggan) {
		this.customerIdPelanggan = customerIdPelanggan;
	}
	
	public String getTypeTransaction() {
		return this.typeTransaction;
	}
	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}
	
	public String getTarifDaya() {
		return this.tarifDaya;
	}
	public void setTarifDaya(String tarifDaya) {
		this.tarifDaya = tarifDaya;
	}
	
	public String getPeriode() {
		return this.periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	
	public String getKWHAwal() {
		return this.KWHAwal;
	}
	public void setKWHAwal(String kWHAwal) {
		this.KWHAwal = kWHAwal;
	}
	
	public String getKWHAkhir() {
		return this.KWHAkhir;
	}
	public void setKWHAkhir(String kWHAkhir) {
		this.KWHAkhir = kWHAkhir;
	}
	
	public BigDecimal getAmountFlagging() {
		return this.amountFlagging;
	}
	
	public void setAmountFlagging(BigDecimal amountFlagging) {
		this.amountFlagging = amountFlagging;
	}
	
	public BigDecimal getAmountTagihan() {
		return this.amountTagihan;
	}
	public void setAmountTagihan(BigDecimal amountTagihan) {
		this.amountTagihan = amountTagihan;
	}
	
	public String getSisaTunggakan() {
		return this.sisaTunggakan;
	}
	public void setSisaTunggakan(String sisaTunggakan) {
		this.sisaTunggakan = sisaTunggakan;
	}
	
	public String getResponStatus() {
		return this.responStatus;
	}
	public void setResponStatus(String responStatus) {
		this.responStatus = responStatus;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSupervisorId() {
		return this.supervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	public Integer getRetry() {
		return this.retry;
	}
	
	public void setRetry(Integer retry) {
		this.retry = retry;
	}
	
	public Timestamp getDateTimeUpload() {
		return this.dateTimeUpload;
	}
	public void setDateTimeUpload(Timestamp dateTimeUpload) {
		this.dateTimeUpload = dateTimeUpload;
	}
	
	public Timestamp getDateTimeProcess() {
		return this.dateTimeProcess;
	}
	public void setDateTimeProcess(Timestamp dateTimeProcess) {
		this.dateTimeProcess = dateTimeProcess;
	}
		
	public Timestamp getDateTimeFinish() {
		return this.dateTimeFinish;
	}
	public void setDateTimeFinish(Timestamp dateTimeFinish) {
		this.dateTimeFinish = dateTimeFinish;
	}
	
	public String getIdBatchSMSTransaction() {
		return this.idBatchSMSTransaction;
	}
	public void setIdBatchSMSTransaction(String idBatchSMSTransaction) {
		this.idBatchSMSTransaction = idBatchSMSTransaction;
	}
	
	
	
	/* Hibernate to Database properties */
	
	protected Character getTypeTransactionDB() {
		return ((this.typeTransaction == null)? null: this.typeTransaction.charAt(0));
	}
	protected void setTypeTransactionDB(Character typeTransactionDB) {
		this.typeTransaction = (typeTransactionDB == null)? null: typeTransactionDB.toString();
	}
	
	protected Character getRecordStatusDB() {
		return ((this.recordStatus == null)? null: this.recordStatus.charAt(0));
	}
	protected void setRecordStatusDB(Character recordStatusDB) {
		this.recordStatus = (recordStatusDB == null)? null: recordStatusDB.toString();
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("recordId=").append(this.recordId)
			.append(",customerIdPelanggan=").append(this.customerIdPelanggan)
			.append(",typeTransaction=").append(this.typeTransaction)
			.append(",tarifDaya=").append(this.tarifDaya)
			.append(",periode=").append(this.periode)
			.append(",KWHAwal=").append(this.KWHAwal)
			.append(",KWHAkhir=").append(this.KWHAkhir)
			.append(",amountFlagging=").append(this.amountFlagging)
			.append(",amountTagihan=").append(this.amountTagihan)
			.append(",sisaTunggakan=").append(this.sisaTunggakan)
			.append(",responStatus=").append(this.responStatus)
			.append(",remarks=").append(this.remarks)
			.append(",userId=").append(this.userId)
			.append(",supervisorId=").append(this.supervisorId)
			.append(",recordStatus=").append(this.recordStatus)
			.append(",retry=").append(this.retry)
			.append(",dateTimeUpload=").append(this.dateTimeUpload)
			.append(",dateTimeProcess=").append(this.dateTimeProcess)
			.append(",dateTimeFinish=").append(this.dateTimeFinish)
			.append(",idBatchSMSTransaction=").append(this.idBatchSMSTransaction)
			.append("}");
    	
		return sb.toString();
	}
	
	
}
