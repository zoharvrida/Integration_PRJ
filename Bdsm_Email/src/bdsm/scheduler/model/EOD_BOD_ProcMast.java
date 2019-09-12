package bdsm.scheduler.model;


import java.util.Date;


/**
 * 
 * @author v00019372
 */
public class EOD_BOD_ProcMast implements java.io.Serializable {
	private Integer procId;
	private String moduleName;
	private String procedureName;
	private Integer type;
	private Integer idScheduler;
	public Integer getIdScheduler() {
		return idScheduler;
	}
	public void setIdScheduler(Integer idScheduler) {
		this.idScheduler = idScheduler;
	}
	private Integer procIdDepend;
	private String flagEom;
	private String flagProc;
	private Date datLastStartRun;
	private Date datLastEndRun;

	
	public Integer getProcId() {
		return this.procId;
	}
	public void setProcId(Integer procId) {
		this.procId = procId;
	}

	public String getModuleName() {
		return this.moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getProcedureName() {
		return this.procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public Integer getType() {
		return this.type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getProcIdDepend() {
		return this.procIdDepend;
	}
	public void setProcIdDepend(Integer procIdDepend) {
		this.procIdDepend = procIdDepend;
	}

	public String getFlagEom() {
		return this.flagEom;
	}
	public void setFlagEom(String flgEom) {
		this.flagEom = flgEom;
	}

	public String getFlagProc() {
		return this.flagProc;
	}
	public void setFlagProc(String flagProc) {
		this.flagProc = flagProc;
	}

	public Date getDatLastStartRun() {
		return this.datLastStartRun;
	}
	public void setDatLastStartRun(Date datLastStartRun) {
		this.datLastStartRun = datLastStartRun;
	}

	public Date getDatLastEndRun() {
		return this.datLastEndRun;
	}
	public void setDatLastEndRun(Date datLastEndRun) {
		this.datLastEndRun = datLastEndRun;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getFlagEomDB() {
		return ((this.flagEom == null)? null: this.flagEom.charAt(0));
	}
	protected void setFlagEomDB(Character flagEomDB) {
		this.flagProc = (flagEomDB == null)? null: flagEomDB.toString();
	}
	
	protected Character getFlagProcDB() {
		return ((this.flagProc == null)? null: this.flagProc.charAt(0));
	}
	protected void setFlagProcDB(Character flagProcDB) {
		this.flagProc = (flagProcDB == null)? null: flagProcDB.toString();
	}
	
}
