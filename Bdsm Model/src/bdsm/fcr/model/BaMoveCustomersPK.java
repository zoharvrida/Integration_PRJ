/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import java.util.Date;

/**
 *
 * @author v00020841
 */
public class BaMoveCustomersPK implements java.io.Serializable{
    private Integer codCustId;
    private String flagMntStatus;
    private Date datProcess;

	public Integer getCodCustId() {
		return codCustId;
	}

	public void setCodCustId(Integer codCustId) {
		this.codCustId = codCustId;
	}

	public String getFlagMntStatus() {
		return flagMntStatus;
	}

	public void setFlagMntStatus(String flagMntStatus) {
		this.flagMntStatus = flagMntStatus;
	}
    
    public Date getDatProcess() {
        return datProcess;
    }
    public void setDatProcess(Date datProcess) {
        this.datProcess = datProcess;
    }
    
}
