/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;


/**
 * 
 * @author v00019237
 */
public class TmpKabAcctPK implements java.io.Serializable {
	private String batchNo;
	private String account;
	private String typeAccount;

	
    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return this.batchNo;
	}
    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

    /**
     * 
     * @return
     */
    public String getAccount() {
		return this.account;
	}
    /**
     * 
     * @param account
     */
    public void setAccount(String account) {
		this.account = account;
	}

    /**
     * 
     * @return
     */
    public String getTypeAccount() {
		return this.typeAccount;
	}
    /**
     * 
     * @param typeAccount
     */
    public void setTypeAccount(String typeAccount) {
		this.typeAccount = typeAccount;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("{")
			.append("batchNo: ").append(this.batchNo).append(", ")
			.append("account: ").append(this.account).append(", ")
			.append("typeAccount: ").append(this.typeAccount)
			.append("}").toString();
	}
}
