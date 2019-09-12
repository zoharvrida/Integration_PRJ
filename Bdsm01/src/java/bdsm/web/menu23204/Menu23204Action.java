/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu23204;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author NCBS
 */
public class Menu23204Action extends BaseContentAction {

	private String APPROVE_ACTION = "SknNgIncomingCredit_update.action";
    private String REJECT_ACTION ="SknNgIncomingCredit_insert.action";
    private String batchNo;
    private String cdBranch;
    private String batchId;
    private String parentIds;
    private String sandiKotaPengirim;
    private double totalNominal;
    private int jumlahRecords;
    private String identitasPesertaPengirim;
    private String idMaintainedBy;
    private String idMaintainedSpv;
	private String Mode;
    private String recIds;
    private String IDBATCH;
    private String isApprove;
	
    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public String $add() {
		this.getLogger().info("[BEGIN] $add()");
		try {
			return add();
		} finally {
			this.getLogger().info("[  END] $add()");
		}
	}
	
    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {

        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
		
        this.getLogger().debug("batchNo : " + this.IDBATCH);
		this.getLogger().debug("Mode : " + this.getMode());
		this.getLogger().debug("idMaintainedBy : " + this.getIdMaintainedBy());
        this.getLogger().debug("parent ID : " + this.recIds);
        this.getLogger().debug("isApprove : " + this.getIsApprove());
		
		Date bdt = (Date) this.session.get(Constant.C_DATEBUSINESS);
		String stDate = DateUtility.DATE_FORMAT_YYMMDD.format(bdt);
		//DateUtility.DATE_FORMAT_YYYYMMDD
        HashMap<String, String> params = new HashMap<String, String>();
        StringBuilder batchNos = new StringBuilder();
		
        batchNos.append(BdsmUtil.leftPad(this.session.get(Constant.C_CODEBRANCH).toString(), 5, '0')).append(stDate).append(this.IDBATCH);
		
		this.getLogger().info("TRUE BATCH :" + batchNos);
		
        getLogger().info("batchNo : " + batchNos);
        getLogger().info("cdBranch : " + cdBranch);
		getLogger().info("user : " + idMaintainedBy);
        
        map.put("parentIds", recIds);
        map.put("batchNo", batchNos.toString());
		map.put("user",idMaintainedBy);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        if ("true".equals(this.getIsApprove())) {
            this.setMode("1");
            map.put("mode", this.getMode());
            result = HttpUtil.request(getBdsmHost() + APPROVE_ACTION, map, 30);
        } else {
            this.setMode("2");
            map.put("mode", this.getMode());
            result = HttpUtil.request(getBdsmHost() + REJECT_ACTION, map);
        }
        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }

        if (reqResult.equals(ActionSupport.ERROR)) {
            addActionError("Fail Approve Batch " + IDBATCH);
        } else if (reqResult.equals(ActionSupport.SUCCESS)) {
            if ("true".equals(this.getIsApprove())) {
                addActionMessage("Batch " + IDBATCH + " Approved Successfully");
            } else {
                addActionMessage("Batch " + IDBATCH + " Reject Successfully");
            }
            
        }
//
        return ActionSupport.SUCCESS;

    }
    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
      throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public String getParentIds() {
		return parentIds;
	}

    /**
     * 
     * @param parentIds
     */
    public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	/**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * 
     * @return
     */
    public String getBatchId() {
		return batchId;
	}

    /**
     * 
     * @param batchId
     */
    public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

    /**
     * 
     * @return
     */
    public double getTotalNominal() {
		return totalNominal;
	}

    /**
     * 
     * @param totalNominal
     */
    public void setTotalNominal(double totalNominal) {
		this.totalNominal = totalNominal;
	}

    /**
     * 
     * @return
     */
    public int getJumlahRecords() {
		return jumlahRecords;
	}

    /**
     * 
     * @param jumlahRecords
     */
    public void setJumlahRecords(int jumlahRecords) {
		this.jumlahRecords = jumlahRecords;
	}

    /**
     * 
     * @return
     */
    public String getIdentitasPesertaPengirim() {
		return identitasPesertaPengirim;
	}

    /**
     * 
     * @param identitasPesertaPengirim
     */
    public void setIdentitasPesertaPengirim(String identitasPesertaPengirim) {
		this.identitasPesertaPengirim = identitasPesertaPengirim;
	}

    /**
     * 
     * @return
     */
    public String getSandiKotaPengirim() {
		return sandiKotaPengirim;
	}

    /**
     * 
     * @param sandiKotaPengirim
     */
    public void setSandiKotaPengirim(String sandiKotaPengirim) {
		this.sandiKotaPengirim = sandiKotaPengirim;
	}

    /**
     * 
     * @return
     */
    public String getIdMaintainedBy() {
		return idMaintainedBy;
	}

    /**
     * 
     * @param idMaintainedBy
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
		this.idMaintainedBy = idMaintainedBy;
	}

    /**
     * 
     * @return
     */
    public String getIdMaintainedSpv() {
		return idMaintainedSpv;
	}

    /**
     * 
     * @param idMaintainedSpv
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
		this.idMaintainedSpv = idMaintainedSpv;
	}

    /**
     * 
     * @return
     */
    public String getCdBranch() {
		return cdBranch;
	}

    /**
     * 
     * @param cdBranch
     */
    public void setCdBranch(String cdBranch) {
		this.cdBranch = cdBranch;
	}

	/**
	 * @return the Mode
	 */
	public String getMode() {
		return Mode;
	}

	/**
	 * @param Mode the Mode to set
	 */
	public void setMode(String Mode) {
		this.Mode = Mode;
	}

    /**
     * @return the recIds
     */
    public String getRecIds() {
        return recIds;
    }
	
    /**
     * @param recIds the recIds to set
     */
    public void setRecIds(String recIds) {
        this.recIds = recIds;
    }

    /**
     * @return the IDBATCH
     */
    public String getIDBATCH() {
        return IDBATCH;
    }

    /**
     * @param IDBATCH the IDBATCH to set
     */
    public void setIDBATCH(String IDBATCH) {
        this.IDBATCH = IDBATCH;
    }

	/**
	 * @return the isApprove
	 */
	public String getIsApprove() {
		return isApprove;
	}

	/**
	 * @param isApprove the isApprove to set
	 */
	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}
    /**
     * @return the cdBranch
     */
}
