/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;
import java.io.Serializable;
import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author v00020800
 */
public class TmpStRoutingBranch extends BaseModel {
	private TmpStRoutingBranchPK compositeId;
	private Integer codCcBrn;
	private String namBranch;
	private Integer codSector;
	private String flgMntStatus;
	private String codMntAction;
	private String codLastMntMakerid;
	private String codLastMntChkrid;
	private Date datLastMnt;
	private Integer ctrUpdatSrlno;
	private String flgIntercityClg;
	private Integer codEntityVpd;
	private String flgstatus;
	private String statusReason;
	private String cmd;

	/**
	 * @return the flgMntStatus
	 */
	public String getFlgMntStatus() {
		return flgMntStatus;
	}

	/**
	 * @param flgMntStatus the flgMntStatus to set
	 */
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}

	/**
     * 
     * @param codEntityVpd 
     */
	public void setCodEntityVpd(String codEntityVpd) {
		this.setCodEntityVpd(codEntityVpd);
	}

	/**
	 * @return the codBank
	 */
  

	/**
	 * @return the codCcBrn
	 */
	public Integer getCodCcBrn() {
		return codCcBrn;
	}

	/**
	 * @param codCcBrn the codCcBrn to set
	 */
	public void setCodCcBrn(Integer codCcBrn) {
		this.codCcBrn = codCcBrn;
	}

	/**
	 * @return the namBranch
	 */
	public String getNamBranch() {
		return namBranch;
	}

	/**
	 * @param namBranch the namBranch to set
	 */
	public void setNamBranch(String namBranch) {
		this.namBranch = namBranch;
	}

	/**
	 * @return the codSector
	 */
	public Integer getCodSector() {
		return codSector;
	}

	/**
	 * @param codSector the codSector to set
	 */
	public void setCodSector(Integer codSector) {
		this.codSector = codSector;
	}

	/**
	 * @return the codMntAction
	 */
	public String getCodMntAction() {
		return codMntAction;
	}

	/**
	 * @param codMntAction the codMntAction to set
	 */
	public void setCodMntAction(String codMntAction) {
		this.codMntAction = codMntAction;
	}

	/**
	 * @return the codLastMntMakerid
	 */
	public String getCodLastMntMakerid() {
		return codLastMntMakerid;
	}

	/**
	 * @param codLastMntMakerid the codLastMntMakerid to set
	 */
	public void setCodLastMntMakerid(String codLastMntMakerid) {
		this.codLastMntMakerid = codLastMntMakerid;
	}

	/**
	 * @return the codLastMntChkrid
	 */
	public String getCodLastMntChkrid() {
		return codLastMntChkrid;
	}

	/**
	 * @param codLastMntChkrid the codLastMntChkrid to set
	 */
	public void setCodLastMntChkrid(String codLastMntChkrid) {
		this.codLastMntChkrid = codLastMntChkrid;
	}

	/**
	 * @return the datLastMnt
	 */
	public Date getDatLastMnt() {
		return datLastMnt;
	}

	/**
	 * @param datLastMnt the datLastMnt to set
	 */
	public void setDatLastMnt(Date datLastMnt) {
		this.datLastMnt = datLastMnt;
	}

	/**
	 * @return the ctrUpdatSrlno
	 */
	public Integer getCtrUpdatSrlno() {
		return ctrUpdatSrlno;
	}

	/**
	 * @param ctrUpdatSrlno the ctrUpdatSrlno to set
	 */
	public void setCtrUpdatSrlno(Integer ctrUpdatSrlno) {
		this.ctrUpdatSrlno = ctrUpdatSrlno;
	}

	/**
	 * @return the flgIntercityClg
	 */
	public String getFlgIntercityClg() {
		return flgIntercityClg;
	}

	/**
	 * @param flgIntercityClg the flgIntercityClg to set
	 */
	public void setFlgIntercityClg(String flgIntercityClg) {
		this.flgIntercityClg = flgIntercityClg;
	}

	/**
	 * @param codEntityVpd the codEntityVpd to set
	 */
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}

	/**
	 * @return the flgstatus
	 */
	public String getFlgstatus() {
		return flgstatus;
	}

	/**
	 * @param flgstatus the flgstatus to set
	 */
	public void setFlgstatus(String flgstatus) {
		this.flgstatus = flgstatus;
	}

	/**
	 * @return the statusReason
	 */
	public String getStatusReason() {
		return statusReason;
	}

	/**
	 * @param statusReason the statusReason to set
	 */
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * @param cmd the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return the compositeId
	 */
	public TmpStRoutingBranchPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(TmpStRoutingBranchPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the codEntityVpd
	 */
	public Integer getCodEntityVpd() {
		return codEntityVpd;
	} 
}
