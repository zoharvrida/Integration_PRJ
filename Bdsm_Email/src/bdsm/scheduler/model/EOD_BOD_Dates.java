package bdsm.scheduler.model;


import java.util.Date;


/**
 * 
 * @author v00019372
 */
public class EOD_BOD_Dates {
	private Integer id;
	private Date preLastDatProcess;
	private Date preNextDatProcess;
	private String preStatus;
	private Date postLastDatProcess;
	private Date postNextDatProcess;
	private String postStatus;
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the preLastDatProcess
	 */
	public Date getPreLastDatProcess() {
		return this.preLastDatProcess;
	}

	/**
	 * @param preLastDatProcess the preLastDatProcess to set
	 */
	public void setPreLastDatProcess(Date preLastDatProcess) {
		this.preLastDatProcess = preLastDatProcess;
	}

	/**
	 * @return the preNextDatProcess
	 */
	public Date getPreNextDatProcess() {
		return this.preNextDatProcess;
	}

	/**
	 * @param preNextDatProcess the preNextDatProcess to set
	 */
	public void setPreNextDatProcess(Date preNextDatProcess) {
		this.preNextDatProcess = preNextDatProcess;
	}

	/**
	 * @return the preStatus
	 */
	public String getPreStatus() {
		return this.preStatus;
	}

	/**
	 * @param preStatus the preStatus to set
	 */
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

	/**
	 * @return the postLastDatProcess
	 */
	public Date getPostLastDatProcess() {
		return this.postLastDatProcess;
	}

	/**
	 * @param postLastDatProcess the postLastDatProcess to set
	 */
	public void setPostLastDatProcess(Date postLastDatProcess) {
		this.postLastDatProcess = postLastDatProcess;
	}

	/**
	 * @return the postNextDatProcess
	 */
	public Date getPostNextDatProcess() {
		return this.postNextDatProcess;
	}

	/**
	 * @param postNextDatProcess the postNextDatProcess to set
	 */
	public void setPostNextDatProcess(Date postNextDatProcess) {
		this.postNextDatProcess = postNextDatProcess;
	}

	/**
	 * @return the postStatus
	 */
	public String getPostStatus() {
		return this.postStatus;
	}

	/**
	 * @param postStatus the postStatus to set
	 */
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
}
