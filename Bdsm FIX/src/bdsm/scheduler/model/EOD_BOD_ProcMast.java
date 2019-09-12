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
    /**
     * 
     * @return
     */
    public Integer getIdScheduler() {
		return idScheduler;
	}
    /**
     * 
     * @param idScheduler
     */
    public void setIdScheduler(Integer idScheduler) {
		this.idScheduler = idScheduler;
	}
	private Integer procIdDepend;
	private String flagEom;
	private String flagProc;
	private Date datLastStartRun;
	private Date datLastEndRun;

	
    /**
     * 
     * @return
     */
    public Integer getProcId() {
		return this.procId;
	}
    /**
     * 
     * @param procId
     */
    public void setProcId(Integer procId) {
		this.procId = procId;
	}

    /**
     * 
     * @return
     */
    public String getModuleName() {
		return this.moduleName;
	}
    /**
     * 
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

    /**
     * 
     * @return
     */
    public String getProcedureName() {
		return this.procedureName;
	}
    /**
     * 
     * @param procedureName
     */
    public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

    /**
     * 
     * @return
     */
    public Integer getType() {
		return this.type;
	}
    /**
     * 
     * @param type
     */
    public void setType(Integer type) {
		this.type = type;
	}

    /**
     * 
     * @return
     */
    public Integer getProcIdDepend() {
		return this.procIdDepend;
	}
    /**
     * 
     * @param procIdDepend
     */
    public void setProcIdDepend(Integer procIdDepend) {
		this.procIdDepend = procIdDepend;
	}

    /**
     * 
     * @return
     */
    public String getFlagEom() {
		return this.flagEom;
	}
    /**
     * 
     * @param flgEom
     */
    public void setFlagEom(String flgEom) {
		this.flagEom = flgEom;
	}

    /**
     * 
     * @return
     */
    public String getFlagProc() {
		return this.flagProc;
	}
    /**
     * 
     * @param flagProc
     */
    public void setFlagProc(String flagProc) {
		this.flagProc = flagProc;
	}

    /**
     * 
     * @return
     */
    public Date getDatLastStartRun() {
		return this.datLastStartRun;
	}
    /**
     * 
     * @param datLastStartRun
     */
    public void setDatLastStartRun(Date datLastStartRun) {
		this.datLastStartRun = datLastStartRun;
	}

    /**
     * 
     * @return
     */
    public Date getDatLastEndRun() {
		return this.datLastEndRun;
	}
    /**
     * 
     * @param datLastEndRun
     */
    public void setDatLastEndRun(Date datLastEndRun) {
		this.datLastEndRun = datLastEndRun;
	}
	
	
	/* Hibernate to Database properties */
	
    /**
     * 
     * @return
     */
    protected Character getFlagEomDB() {
		return ((this.flagEom == null)? null: this.flagEom.charAt(0));
	}
    /**
     * 
     * @param flagEomDB
     */
    protected void setFlagEomDB(Character flagEomDB) {
		this.flagProc = (flagEomDB == null)? null: flagEomDB.toString();
	}
	
    /**
     * 
     * @return
     */
    protected Character getFlagProcDB() {
		return ((this.flagProc == null)? null: this.flagProc.charAt(0));
	}
    /**
     * 
     * @param flagProcDB
     */
    protected void setFlagProcDB(Character flagProcDB) {
		this.flagProc = (flagProcDB == null)? null: flagProcDB.toString();
	}
	
}
