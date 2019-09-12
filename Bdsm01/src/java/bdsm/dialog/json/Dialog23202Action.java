package bdsm.dialog.json;

/*
 * Jeffri Tambunan
 */
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.json.JSONUtil;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.Constant;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author bdsm
 */
@SuppressWarnings({"serial", "rawtypes"})
public class Dialog23202Action extends BaseDialogAction {

	private static final String SKN_NG_RETRIEVE_INCOMING_CREDIT = "SknNgIncomingCredit_list.action";
	private String gRPID;
	private String mode;
	private String modes;
	private String totalNominal;
	private String identitasPesertaPengirim;
	private String cdBranch;
	private String idMaintainedBy;
	private String IDBATCH;
	private List grpiDList;
	private List schedImportList;
	private String GRPID;

	//private List codeNoList;
	//private String dropdown_batch;
	//private String BATCH_NO;
    /**
     * 
     * @return
     */
    public String getListType() {
		try {
			this.getLogger().info("dropdown Mode : " + this.getModes());
			this.getLogger().info("Mode : " + this.getMode());
			doList();
			this.getLogger().info(getGrpiDList());
		} catch (Exception ex) {
			Logger.getLogger(Dialog23202Action.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ActionSupport.SUCCESS;
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List doList() throws Exception {
		this.getLogger().info("batchNo : " + this.IDBATCH);
		this.getLogger().info("Mode : " + this.getMode());
		this.getLogger().info("cdBranch : " + this.getCdBranch());
		this.getLogger().info("idMaintainedBy : " + this.getIdMaintainedBy());

		//String DateS = SchedulerUtil.getDate("yyMMdd");

		HashMap<String, String> params = new HashMap<String, String>();
		StringBuilder batchNos = new StringBuilder();
		Date bdt = (Date) this.session.get(Constant.C_DATEBUSINESS);
		String stDate = DateUtility.DATE_FORMAT_YYMMDD.format(bdt);

		if (StringUtils.isBlank(this.mode)) {
			batchNos.append(BdsmUtil.leftPad(this.session.get(Constant.C_CODEBRANCH).toString(), 5, '0')).append(stDate);
			if ("1".equals(this.getModes())) {
				this.getLogger().info("INQUIRY");
				params.put("modes", "inquiry");
			} else {
				this.getLogger().info("AUTHORIZE");
				params.put("modes", "authorize");
			}
			params.put("mode", getModes());
		} else {
			batchNos.append(BdsmUtil.leftPad(this.session.get(Constant.C_CODEBRANCH).toString(), 5, '0')).append(stDate).append(this.IDBATCH);
			params.put("mode", getMode());
			params.put("modes", "selection");
		}

		this.getLogger().info("TRUE BATCH :" + batchNos);
		params.put("modelPk.batchNo", batchNos.toString());
		params.put("idMaintainedBy", this.getIdMaintainedBy());
		//params.put("mode", getModes());
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		String result = HttpUtil.request(getBdsmHost() + SKN_NG_RETRIEVE_INCOMING_CREDIT, params);
		this.getLogger().info(result);

		HashMap ret = (HashMap) JSONUtil.deserialize(result);
		List listRet = (List) ret.get("modelList");
		String status = (String) ret.get("jsonStatus");

		setGrpiDList((List) ret.get("modelList"));
		//setCodeNoList((List) ret.get("modelList"));
		addActionError(status);

		this.getLogger().info("LIST :" + listRet);
		return listRet;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the totalNominal
	 */
	public String getTotalNominal() {
		return totalNominal;
	}

	/**
	 * @param totalNominal the totalNominal to set
	 */
	public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
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
	 * @return the cdBranch
	 */
	public String getCdBranch() {
		return cdBranch;
	}

	/**
	 * @param cdBranch the cdBranch to set
	 */
	public void setCdBranch(String cdBranch) {
		this.cdBranch = cdBranch;
	}

	/**
	 * @return the idMaintainedBy
	 */
	public String getIdMaintainedBy() {
		return idMaintainedBy;
	}

	/**
	 * @param idMaintainedBy the idMaintainedBy to set
	 */
	public void setIdMaintainedBy(String idMaintainedBy) {
		this.idMaintainedBy = idMaintainedBy;
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
	 * @return the grpiDList
	 */
	public List getGrpiDList() {
		return grpiDList;
	}

	/**
	 * @param grpiDList the grpiDList to set
	 */
	public void setGrpiDList(List grpiDList) {
		this.grpiDList = grpiDList;
	}

	/**
	 * @return the gRPID
	 */
	public String getgRPID() {
		return gRPID;
	}

	/**
	 * @param gRPID the gRPID to set
	 */
	public void setgRPID(String gRPID) {
		this.gRPID = gRPID;
	}

	/**
	 * @return the GRPID
	 */
	public String getGRPID() {
		return GRPID;
	}

	/**
	 * @param GRPID the GRPID to set
	 */
	public void setGRPID(String GRPID) {
		this.GRPID = GRPID;
	}

	/**
	 * @return the modes
	 */
	public String getModes() {
		return modes;
	}

	/**
	 * @param modes the modes to set
	 */
	public void setModes(String modes) {
		this.modes = modes;
	}
}
