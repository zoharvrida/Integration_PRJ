/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu23205;


import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "serial", "unchecked" })
public class Menu23205Action extends BaseContentAction {
	private static final String MODULE_NAME = "SKNNGINCDB";
	private static final String APPROVE_ACTION = "SknNgInwardDebit_save.action";
        private static final String NAMMENU = "23205";
	private String codeNo;
	private String recIds;
	private boolean isApprove;
	
	
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
    @Override
	public String doAdd() {

		Map<String, String> map = new HashMap<String, String>();
		String result, batchNo, branchCode;
		Date bdt;
		HashMap<String, ? extends Object> resultMap;
		String reqResult = null;
		
		branchCode = this.session.get(Constant.C_CODEBRANCH).toString();
		bdt = (Date) this.session.get(Constant.C_DATEBUSINESS);
		batchNo = BdsmUtil.leftPad(branchCode, 5, '0') + "-" + DateUtility.DATE_FORMAT_YYYYMMDD.format(bdt) 
					+ "-" + this.codeNo + "-" + MODULE_NAME; 
		
		this.getLogger().info("codeNo : " + this.codeNo);
		this.getLogger().info("batchNo : " + batchNo);
		this.getLogger().info("branchCode : " + branchCode);
		this.getLogger().info("recIds : " + this.recIds);
		this.getLogger().info("isApprove : " + this.isApprove);

                map.put("nameMenu",NAMMENU);
		map.put("modelPk.batchNo", batchNo);
		map.put("cdBranch", branchCode);
		map.put("recIds", this.recIds);
		map.put("mode", (this.isApprove)? "1": "2");
		map.put("idUser", (String) this.session.get(Constant.C_IDUSER));
		map.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));

		result = HttpUtil.request(this.getBdsmHost() + APPROVE_ACTION, map);

		try {
			resultMap = (HashMap<String, ? extends Object>) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			//errorCode = (String) resultMap.get("errorCode");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		if (reqResult.equals(ERROR))
			addActionError("Fail " + ((this.isApprove)? "Approve": "Reject") + " For Code No. " + this.codeNo);
		else if (reqResult.equals(SUCCESS))
			addActionMessage("Code No. " + this.codeNo + " " + ((this.isApprove)? "Approved": "Rejected") + " Successfully");

		return SUCCESS;

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
    public String getCodeNo() {
		return this.codeNo;
	}
    /**
     * 
     * @param codeNo
     */
    public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

    /**
     * 
     * @return
     */
    public String getRecIds() {
		return this.recIds;
	}
    /**
     * 
     * @param recIds
     */
    public void setRecIds(String recIds) {
		this.recIds = recIds;
	}
	
    /**
     * 
     * @return
     */
    public boolean getIsApprove() {
		return this.isApprove;
	}
    /**
     * 
     * @param isApprove
     */
    public void setIsApprove(boolean isApprove) {
		this.isApprove = isApprove;
	}

}
