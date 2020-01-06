/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.web.Menu12201;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.rpt.web.BaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.util.TokenHelper;

/**
 *
 * @author v00019722
 */
public class Menu12201Action extends BaseContentAction {

	private static final String ACTION_LIST = "fixReportParam_list.action";
	private static final String ACTION_SAVE = "fixReportParam_save.action";
	private static final String ACTION_ADD = "fixReportParam_insert.action";
	private static final String NAM_MENU = "fixReportParam";
	private String idReport;
	private String parameter;
	private String format;
	private String resultS;
	private String idScheduler;
	private String mandatory;
	private String maxLength;
	private String regexp;
        private String customQuery;
	private String idMaintainedBy;
	private String idMaintainedSpv;
	private String user;
	private String dummy;
	private String reportName;
	private String remarks;
	private String btnSave;
	private String idUser;
	private String idTemplate;
	private String namUser;
	private String cdBranch;
	private String dtBusiness;
	private String namTemplate;
	private String namReportSearch;

	@Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@SkipValidation
	public String parameter() {
		/*
		 * Getting Requester from requester action and dao, then serialize
		 * result with ";"
		 */
		List session = new ArrayList();
		List valSession = new ArrayList();
		valSession.add(idUser);
		valSession.add(idTemplate);
		valSession.add(namUser);
		valSession.add(cdBranch);
		valSession.add(dtBusiness);
		session.add("idUser");
		session.add("idTemplate");
		session.add("namUser");
		session.add("cdBranch");
		session.add("dtBusiness");

		getLogger().info("idUser :" + idUser);
		getLogger().info("idTemplate :" + idTemplate);
		getLogger().info("namUser :" + namUser);
		getLogger().info("cdBranch :" + cdBranch);
		getLogger().info("dtBusiness :" + dtBusiness);

		StringBuilder parameterString = new StringBuilder();
		StringBuilder formatString = new StringBuilder();
		StringBuilder regExpString = new StringBuilder();
		StringBuilder mandatoryString = new StringBuilder();
		StringBuilder maxLengthString = new StringBuilder();
    	StringBuilder customQueryString = new StringBuilder();
		getLogger().info("[Begin] Parameter Label Change()");
		try {
			if (isValidSession()) {
				getLogger().info("id Report :" + getIdReport());
				Map<String, String> map = new HashMap<String, String>();
				String result;
				HashMap resultMap;
				HashMap mapModel = null;
				ArrayList paraMeter;
				String temp;
				String forMat = null;
				String regExp;
				String manDatory;
				String maXLength;
                String CustomQuery;
				int paramTemp = 0;
				int last;
				String reqResult = null;
				String errorCode = null;

				map.put("model.compositeId.idReport", getIdReport());
				map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

				result = HttpUtil.request(getBdsmHost() + ACTION_LIST, map);


				try {
					resultMap = (HashMap) JSONUtil.deserialize(result);
					reqResult = (String) resultMap.get("jsonStatus");
					errorCode = (String) resultMap.get("errorCode");
					//mapModel =  (HashMap) resultMap.get("model");
					paraMeter = (ArrayList) resultMap.get("modelList");
					if (paraMeter != null) {
						paramTemp = paraMeter.size();
						for (int k = 0; k < paramTemp; k++) {
							mapModel = (HashMap) paraMeter.get(k);
							temp = (String) mapModel.get("title");
							forMat = (String) mapModel.get("format");
							regExp = (String) mapModel.get("regExp");
							manDatory = (String) mapModel.get("manDatory");
							maXLength = (String) mapModel.get("maxLength");
                            CustomQuery = (String) mapModel.get("customQuery");
							parameterString.append(temp).append(";");
							getLogger().info("temp :" + temp);
							if (forMat == null) {
								forMat = "default";
							} else {
                          forMat = (String) mapModel.get("format");
									getLogger().info("format :" + forMat);
									for (int i = 0; i < session.size(); i++) {
										getLogger().info("cek_session :" + session.get(i));
										if (forMat.equals(session.get(i).toString())) {
											forMat = session.get(i).toString();
											regExp = valSession.get(i).toString();
										}
									}
								}
							if (regExp == null) {
								regExp = "default";
							}
	                        if (CustomQuery == null ){
	                                CustomQuery = "default";
	                        }
							maxLengthString.append(maXLength).append(";");
							formatString.append(forMat).append(";");
							regExpString.append(regExp).append(";");
							mandatoryString.append(manDatory).append(";");
                            customQueryString.append(CustomQuery).append(";");
							getLogger().info("format :" + forMat);
							getLogger().info("escape :" + regExp);
						}
					}
					setFormat(formatString.toString());
					setParameter(parameterString.toString());
					mandatory = mandatoryString.toString();
					maxLength = maxLengthString.toString();
					regexp = regExpString.toString();
                    setCustomQuery(customQueryString.toString());

					getLogger().info(parameter);
					getLogger().info(getFormat());


					String tokenS = TokenHelper.setToken();
					getLogger().debug("TokenS : " + tokenS);


				} catch (JSONException ex) {
					getLogger().fatal(ex, ex);
					return ActionSupport.ERROR;
				}

				return "parameter";
			} else {
				return logout();
			}
		} catch (NullPointerException e) {
			getLogger().info("null Pointer" + e);
			setErrorMessage("Report: " + getIdReport() + " not found");
			return "parameter";
			//return ActionSupport.ERROR;
		} catch (Throwable e) {
			getLogger().fatal(e, e);
			return ActionSupport.ERROR;
		} finally {
			getLogger().info("[ End ] Parameter()");
		}
	}

	@Override
	public String doAdd() {

		List valSession = new ArrayList();
		valSession.add(idUser);
		valSession.add(idTemplate);
		valSession.add(namUser);
		valSession.add(cdBranch);
		valSession.add(dtBusiness);
		getLogger().info("REMARKS : " + remarks);
		getLogger().info("button save :" + btnSave);
		Map<String, String> map = new HashMap<String, String>();
		HashMap resultMap;
		HashMap resultMap2;
		String reqResult = null;
		String errorCode = null;
		int k = 0;
		String result;
		String regexps = "\\|";
		String[] paramS = resultS.split(regexps);
		StringBuilder batchQ = new StringBuilder();

		getLogger().info("idUser 2:" + idUser);
		getLogger().info("idTemplate 2:" + idTemplate);
		getLogger().info("namUser 2:" + namUser);
		getLogger().info("cdBranch 2:" + cdBranch);
		getLogger().info("dtBusiness 2:" + dtBusiness);
		getLogger().info("namTemplate 2:" + namTemplate);

		map.put("modelQ.idScheduler", getIdScheduler());
		int length = paramS.length;
		for (int i = 0; i < length; i++) {
			k = i + 1;
			if (!"".equals(paramS[i])) {
				map.put("modelQ.param" + k, paramS[i]);
			} else {
				map.put("modelQ.param" + k, "null");
			}
			getLogger().info(paramS[i]);
		}
		map.put("idUser", idUser);
		map.put("idTemplate", idTemplate);
		map.put("namUser", namUser);
		map.put("cdBranch", cdBranch);
		map.put("dtProcess", dtBusiness);
		map.put("namTemplate", namTemplate);
		//map.put("modelM.idRequest", getUser());
		map.put("modelM.idReport", getIdReport());
		map.put("modelR.reportName", getReportName());
		map.put("modelR.remarks", getRemarks());
		map.put("modelQ.idMaintainedBy", getIdMaintainedBy());
		map.put("modelQ.idMaintainedSpv", getIdMaintainedSpv());
		map.put("namMenu", NAM_MENU);
		map.put("sequential", getDummy());
		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + ACTION_SAVE, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
		} catch (JSONException ex) {
			getLogger().fatal(ex, ex);
		} catch (NullPointerException e) {
			getLogger().info(" NULL P : " + e.getMessage());
		}

		if (reqResult.equals(ActionSupport.ERROR)) {
			addActionError(getText(errorCode));
		} else if (reqResult.equals(ActionSupport.SUCCESS)) {
			addActionMessage(getText(errorCode));
		}
		return ActionSupport.SUCCESS;
	}

	@Override
	public String doEdit() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @return the idReport
	 */
	public String getIdReport() {
		return idReport;
	}

	/**
	 * @param idReport the idReport to set
	 */
	public void setIdReport(String idReport) {
		this.idReport = idReport;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the resultS
	 */
	public String getResultS() {
		return resultS;
	}

	/**
	 * @param resultS the resultS to set
	 */
	public void setResultS(String resultS) {
		this.resultS = resultS;
	}

	/**
	 * @return the idScheduler
	 */
	public String getIdScheduler() {
		return idScheduler;
	}

	/**
	 * @param idScheduler the idScheduler to set
	 */
	public void setIdScheduler(String idScheduler) {
		this.idScheduler = idScheduler;
	}

	/**
	 * @return the mandatory
	 */
	public String getMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * @return the maxLength
	 */
	public String getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @return the regexp
	 */
	public String getRegexp() {
		return regexp;
	}

	/**
	 * @param regexp the regexp to set
	 */
	public void setRegexp(String regexp) {
		this.regexp = regexp;
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
	 * @return the idMaintainedSpv
	 */
	public String getIdMaintainedSpv() {
		return idMaintainedSpv;
	}

	/**
	 * @param idMaintainedSpv the idMaintainedSpv to set
	 */
	public void setIdMaintainedSpv(String idMaintainedSpv) {
		this.idMaintainedSpv = idMaintainedSpv;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the dummy
	 */
	public String getDummy() {
		return dummy;
	}

	/**
	 * @param dummy the dummy to set
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the idTemplate
	 */
	public String getIdTemplate() {
		return idTemplate;
	}

	/**
	 * @param idTemplate the idTemplate to set
	 */
	public void setIdTemplate(String idTemplate) {
		this.idTemplate = idTemplate;
	}

	/**
	 * @return the namUser
	 */
	public String getNamUser() {
		return namUser;
	}

	/**
	 * @param namUser the namUser to set
	 */
	public void setNamUser(String namUser) {
		this.namUser = namUser;
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
	 * @return the dtBusiness
	 */
	public String getDtBusiness() {
		return dtBusiness;
	}

	/**
	 * @param dtBusiness the dtBusiness to set
	 */
	public void setDtBusiness(String dtBusiness) {
		this.dtBusiness = dtBusiness;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the btnSave
	 */
	public String getBtnSave() {
		return btnSave;
	}

	/**
	 * @param btnSave the btnSave to set
	 */
	public void setBtnSave(String btnSave) {
		this.btnSave = btnSave;
	}

	/**
	 * @return the namTemplate
	 */
	public String getNamTemplate() {
		return namTemplate;
	}

	/**
	 * @param namTemplate the namTemplate to set
	 */
	public void setNamTemplate(String namTemplate) {
		this.namTemplate = namTemplate;
	}

    /**
     * @return the customQuery
     */
    public String getCustomQuery() {
        return customQuery;
    }

    /**
     * @param customQuery the customQuery to set
     */
    public void setCustomQuery(String customQuery) {
        this.customQuery = customQuery;
    }
}
