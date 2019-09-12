/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author NCBS
 */
public class AmortizeXref extends BaseModel implements Serializable{
	private String fileId;
	private Integer id;
	private String amount;
	private String extType;
	private String gefuStatus;
	private String gefuComment;

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the extType
	 */
	public String getExtType() {
		return extType;
	}

	/**
	 * @param extType the extType to set
	 */
	public void setExtType(String extType) {
		this.extType = extType;
	}

	/**
	 * @return the gefuStatus
	 */
	public String getGefuStatus() {
		return gefuStatus;
	}

	/**
	 * @param gefuStatus the gefuStatus to set
	 */
	public void setGefuStatus(String gefuStatus) {
		this.gefuStatus = gefuStatus;
	}

	/**
	 * @return the gefuComment
	 */
	public String getGefuComment() {
		return gefuComment;
	}

	/**
	 * @param gefuComment the gefuComment to set
	 */
	public void setGefuComment(String gefuComment) {
		this.gefuComment = gefuComment;
	}
}
