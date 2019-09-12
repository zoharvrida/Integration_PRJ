/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.model.EktpCoreMappingPK;
import bdsm.model.KtpData;
import bdsm.model.ScreenOpening;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.DefinitionConstant;
import bdsm.util.HttpUtil;
import bdsm.util.SchedulerUtil;
import bdsm.web.request.JSONService;
import com.opensymphony.xwork2.ActionSupport;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author SDM
 */
public class KTPInquiryService extends ActionSupport {

    private String responData;
    private KtpData model = new KtpData();
    private EktpCoreMappingPK pk = new EktpCoreMappingPK();
    private String contentData;
    private String HRcode;
    private String HRdesc;
    private Map<String, String> params = new HashMap<String, String>();
    private Map<String, Object> resultMap = new HashMap<String, Object>();
    private String jsonStatus;
    private String errorCode;
    private String MenuSrc;
    private Logger logger = Logger.getLogger(KTPInquiryService.class);
    private HttpServletRequest servletRequest;
    private Map<String, Object> session;
    private String bdsmHost;
    private String tokenKey;
    private String tzToken;
    //private JSONService JSONUtil = new JSONService();

    /**
     * 
     * @param link
     * @param nik
     * @return
     */
    public String inquiry(String link, String nik) {
        getLogger().info("[BEGIN] inquiry()");


        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;

        getLogger().debug("inputNik : " + nik);

        String ipAddress = this.getServletRequest().getHeader("X-FORWARDED-FOR");
        //String ipAddress = "10.148.109.63";
        if (ipAddress == null || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
            getLogger().debug("ipAddress=" + ipAddress);
            ipAddress = this.getServletRequest().getRemoteAddr();
            getLogger().debug("ipAddress2=" + ipAddress);
        }
        String serverIp = null;
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
            getLogger().debug("serverIp=" + serverIp);
        } catch (UnknownHostException e) {
            serverIp = this.getServletRequest().getLocalAddr();
            getLogger().debug("serverIp2=" + serverIp);
        }

        String cdBranch = null;
        try {
            cdBranch = ((Integer) getSession().get(Constant.C_CODEBRANCH)).toString();
        } catch (Exception e) {
            getLogger().info("FAILED GET BRANCH :" + e, e);
        }
        map.put("SysSource", DefinitionConstant.SystemSrc);
        map.put("MenuSource", getMenuSrc());
        map.put("cdBranch", cdBranch);
        map.put("inputNik", nik);
        map.put("clientIp", ipAddress);
        map.put("serverIp", serverIp);
        map.put("userId", (String) getSession().get(Constant.C_IDUSER));
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + link, map);

        try {
            if (result == null) {
                getLogger().info("result=null");
                setResponData("{\"content\":[{\"RESPON\":\"Timeout from host\"}]}");
            } else {
                resultMap = (HashMap) JSONUtil.deserialize(result);
                getLogger().info("result=" + resultMap);
                setResponData((String) resultMap.get("responseData"));
                getLogger().info("--responData=" + getResponData());
                if (getResponData() == null || "".equals(getResponData())) {
                    this.addActionError("Got null response from host");
                    setResponData("{\"content\":[{\"RESPON\":\"Null data from host\"}]}");
                }
            }
            HashMap responseDataMap = (HashMap) JSONUtil.deserialize(getResponData());
            List contentList = (List) responseDataMap.get("content");
            for (Object o : contentList) {
                HashMap content = (HashMap) o;
                getLogger().info("----content=" + content);
                if (content == null) {
                    this.addActionError("Got null content from the response");
                } else {
                    this.getModel().read(content);
                    getLogger().info("------RESPON=" + this.getModel().getResponseMessage());
                    if (this.getModel().getResponseMessage() == null || "".equals(this.getModel().getResponseMessage())) {
                        setContentData(JSONUtil.serialize(getModel()));
                        getLogger().info("--------contentData=" + getContentData());
                    } else {
                        setContentData(JSONUtil.serialize(getModel()));
                        this.addActionError(this.getModel().getResponseMessage());
                    }
                }
            }
        } catch (JSONException ex) {
            getLogger().fatal("[FATAL] inquiry() " + ex.getMessage(), ex);
        } finally {
            getLogger().info("[ END] inquiry()");
        }
        return "done";
    }

    /**
     * 
     * @param so
     * @param flgExtSource
     * @param flagSuccess
     * @return
     */
    public ScreenOpening ktpdataReplication(ScreenOpening so, String flgExtSource, boolean flagSuccess) {
        // EKTP custom Mapping Value
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFBack = new SimpleDateFormat("dd/MM/yyyy");
        List dukcapilVal = new ArrayList();
        getLogger().debug("MODEL KTP :" + model.toString());
        
        so.setPermaAddress1(this.model.getAddress());
        so.setPermaAddress2("RT " + this.model.getRt() + " RW " + this.model.getRw());
        so.setPermaAddress3(this.model.getNamKel() + "," + this.model.getNamKec());
        so.setPermaZipCode(this.model.getPosCode());
        so.setNamMother(this.model.getNamMother());

        getLogger().info("DOB :" + this.model.getDob());
        Date AccDate = null;
        try {
            AccDate = dateFormat.parse(this.model.getDob());
            this.getLogger().info("AccDate" + AccDate);
            so.setBirthDate(dateFBack.format(AccDate));
            this.getLogger().info(so.getBirthDate());

        } catch (ParseException ex) {
            getLogger().info("EXCEPTION :" + ex, ex);
            getLogger().info("DATE :" + this.model.getDob());
            so.setBirthDate(this.model.getDob());
        } catch (NullPointerException nulls) {
            getLogger().info("EXCEPTION DOB NULLS :" + nulls, nulls);
            getLogger().info("DATE :" + this.model.getDob());
            so.setBirthDate(this.model.getDob());
        }

        if ("0".equalsIgnoreCase(flgExtSource)) {
        if (model.getSex().equalsIgnoreCase("LAKI-LAKI")) {
            so.setGender("M");
        } else if (model.getSex().equalsIgnoreCase("PEREMPUAN")) {
            so.setGender("F");
        } else {
            if (flgExtSource.equalsIgnoreCase("0")) {
                so.setGender("");
            }
        }
        } else {
            String genderFlag = null;
            String genderDesc = null;

            if (model.getSex().equalsIgnoreCase("LAKI-LAKI")){
                 genderFlag = "M";
                 genderDesc = "MALE";
             } else if ("PEREMPUAN".equalsIgnoreCase(model.getSex())){
                 genderFlag = "F";
                 genderDesc = "FEMALE";
             }
             if(!genderFlag.equalsIgnoreCase(so.getGender())){
                 so.setSalutation(null);
                 so.setSalutationDesc(null);
                 so.setGender(genderFlag);
                 so.setGenderDesc(genderDesc);
             }
        }


        so.setFullName(this.model.getName());
        List spaceName = SchedulerUtil.getParameter(this.model.getName(), " ");

        if (!spaceName.isEmpty()) {
            StringBuilder lastName = new StringBuilder();
            for (int i = 0; i < spaceName.size(); i++) {
                if (i == 0) {
                    so.setCustFirstName(spaceName.get(i).toString());
                } else if (i == 1) {
                    if (spaceName.size() == 2) {
                        so.setCustLastName(spaceName.get(i).toString());
                    } else {
                        so.setCustMidName(spaceName.get(i).toString());
                    }
                } else {
                    lastName.append(spaceName.get(i).toString()).append(" ");
                }
            }
            so.setCustLastName(lastName.toString().trim());
        }

        if ("0".equalsIgnoreCase(flgExtSource)) {
            so.setMailAddrs1(so.getPermaAddress1());
            so.setMailAddrs2(so.getPermaAddress2());
            so.setMailAddrs3(so.getPermaAddress3());
            so.setMaillingZipCode(so.getPermaZipCode());
            so.setMaillingCode(so.getPermaZipCode());

            so.setHoldAddress1(so.getPermaAddress1());
            so.setHoldAddress2(so.getPermaAddress2());
            so.setHoldAddress3(so.getPermaAddress3());
            so.setHoldZipCode(so.getPermaZipCode());

            // reformatting Date
            if (flagSuccess == false) {
                
                getLogger().info("flagsuccess :" + flagSuccess );
                
                so.setFatca("3");
                so.setBadanAS("2");
                so.setAlamatAS("2");
                so.setGreenCard("2");
                so.setNationalityDesc("ID-INDONESIA - 09002");
                so.setNationality("ID");
                so.setCommunicationDesc("SETUJU SEWAKTU-WAKTU");
                so.setDataTransXtractFlag(3);
                so.setProductCodeDesc("TABUNGAN DANAMON LEBIH");
                so.setProductCode(422);
                so.setIdcardTDesc("KTP");
                so.setIdcardType("KTP");
                so.setFlgIctype("1");
                so.setStateIndicator("MAIL");
                so.setStateIndicatorDesc("MAIL");
                so.setRelation("SOLE OWNER");
                so.setEchannel("1");
            }
        }

        dukcapilVal.add(model.getReligion() + ":cmbEthnic:-");
        dukcapilVal.add(model.getNamKab() + ":-:namKab");
        dukcapilVal.add(model.getNamProp() + ":-:namProp");
        dukcapilVal.add(model.getLastEdu() + ":ctlEducation:-");
        dukcapilVal.add(model.getProfession() + ":-:profession");
        dukcapilVal.add(model.getMarStat() + ":cmbMartialStatus:-");
        
        List getNCBSval = new ArrayList();
        for (int i = 1; i < 7; i++) {
            String identifier = "";
            String nameParam = "";
            String counter = String.valueOf(i);
            getPk().setFieldCode(counter);
            List paramQuery = bdsm.util.SchedulerUtil.getParameter(dukcapilVal.get(i - 1).toString(), ":");
            getPk().setEktpVal(paramQuery.get(0).toString());
            getPk().setFlgMntStatus("A");
            getLogger().info("PRIMARY :" + getPk());
            if (!"-".equals(paramQuery.get(2))) {
                identifier = paramQuery.get(2).toString();
            }
            if (!"-".equals(paramQuery.get(1))) {
                nameParam = paramQuery.get(1).toString();
            }
            String ktpCoreVal = getMappingdata(nameParam, this.getPk(), identifier);
            getNCBSval.add(ktpCoreVal);
        }

        List tempContainer = new ArrayList();
        if (!"".equals(getNCBSval.get(0).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(0).toString(), ":");
            so.setReligionDesc(tempContainer.get(0).toString());
            so.setReligion(tempContainer.get(1).toString());
        } else {
            so.setReligionKTP(model.getReligion());
            so.setReligion(DefinitionConstant.replicateFailed);
        }
        if (!"".equals(getNCBSval.get(1).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(1).toString(), ":");
            so.setPermaTownCityDesc(tempContainer.get(0).toString());
            so.setPermaTownCity(tempContainer.get(1).toString());
            if ("0".equalsIgnoreCase(flgExtSource)) {
            so.setMaillingTownCityDesc(so.getPermaTownCityDesc());
            so.setMaillingTownCity(so.getPermaTownCity());
            }
        } else {
            so.setPermaTownCityKTP(model.getNamKab());
            so.setPermaTownCity(DefinitionConstant.replicateFailed);
            if ("0".equalsIgnoreCase(flgExtSource)) {
                so.setMaillingTownCityKTP(model.getNamKab());
            }
        }
        if (!"".equals(getNCBSval.get(2).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(2).toString(), ":");
            so.setPermaStateDesc(tempContainer.get(0).toString());
            so.setPermaState(tempContainer.get(1).toString());
            if ("0".equalsIgnoreCase(flgExtSource)) {
            so.setMaillingStateDesc(so.getPermaStateDesc());
            so.setMaillingState(so.getPermaState());
        } else {
                // city from channel check for MaillingTown
                getPk().setFieldCode("2");
                getPk().setFlgMntStatus("A");
                getPk().setEktpVal(so.getMaillingStateDesc());
                String mapData = getMappingdata("", this.pk, "namProp");
                if (!"".equalsIgnoreCase(mapData)) {
                    tempContainer = bdsm.util.SchedulerUtil.getParameter(mapData, ":");
                    so.setMaillingStateDesc(tempContainer.get(0).toString());
                    so.setMaillingState(tempContainer.get(1).toString());
                } else {
                    /*so.setMaillingStateKTP(so.getMaillingStateDesc());
                     so.setMaillingTownCity(DefinitionConstant.replicateFailed);*/
                }
            }
        } else {
            so.setPermaStateKTP(model.getNamProp());
            so.setPermaState(DefinitionConstant.replicateFailed);
            if ("0".equalsIgnoreCase(flgExtSource)) {
                so.setMaillingStateKTP(model.getNamProp());
            }
        }
        if (!"".equals(getNCBSval.get(3).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(3).toString(), ":");
            so.setLastEduDesc(tempContainer.get(0).toString());
            so.setLastEdu(tempContainer.get(1).toString());
        } else {
            so.setLastEduKTP(model.getLastEdu());
            so.setLastEdu(DefinitionConstant.replicateFailed);
        }
        if (!"".equals(getNCBSval.get(4).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(4).toString(), ":");
            so.setProfessionDesc(tempContainer.get(0).toString());
            so.setProfession(tempContainer.get(1).toString());
            if (!"".equals(this.HRcode)) {
                so.setHighRiskprofessionCode(this.getHRcode());
                so.setHighRiskprofession(this.getHRdesc());
            }
        } else {
            so.setProfessionKTP(model.getProfession());
            so.setProfession(DefinitionConstant.replicateFailed);
        }
        if (!"".equals(getNCBSval.get(5).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(5).toString(), ":");
            so.setMarStatDesc(tempContainer.get(0).toString());
            so.setMarStat(tempContainer.get(1).toString());

        } else {
            so.setMarStatKTP(model.getMarStat());
            so.setMarStat(DefinitionConstant.replicateFailed);
        }
        return so;
    }

    private String getMappingdata(String inquiryParam, EktpCoreMappingPK pk, String inqNames) {

        String LastValue = "";
        getLogger().info("PARAM_INQUIRY :" + inquiryParam);
        getLogger().info("name_INQUIRY :" + inqNames);
        StringBuilder valIndex = new StringBuilder();

        Object[] listObj = {pk};
        String[] nameless = {"pk"};
        String[] prefix = {""};

        this.setParams((Map<String, String>) ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1));
        this.getParams().put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        this.getParams().put("methodName", DefinitionConstant.KTPCREATION);
        this.getParams().put("useTransaction", "true");
        this.getParams().put("inqParam", inquiryParam);
        this.getParams().put("inqNames", inqNames);

        try {
            String result = HttpUtil.request(this.getBdsmHost() + DefinitionConstant.CALLMETHOD_ACTION, getParams());

            setResultMap((Map<String, Object>) JSONUtil.deserialize(result));

            List<Map> ktpMapping = (List) getResultMap().get("modelList");
            if (!ktpMapping.isEmpty()) {
                valIndex.append(ktpMapping.get(0).get("val").toString());
                valIndex.append(":").append(ktpMapping.get(0).get("fieldprop").toString());
            }
            LastValue = valIndex.toString();
            getLogger().info("VAL :" + LastValue);
            this.setJsonStatus((String) getResultMap().get("jsonStatus"));
            this.setErrorCode((String) getResultMap().get("errorCode"));
        } catch (JSONException jex) {
            this.getLogger().fatal(jex, jex);
        }

        if (ActionSupport.SUCCESS.equals(this.getJsonStatus())) {
            this.addActionMessage(getText(this.getErrorCode()));
        } else if (ActionSupport.ERROR.equals(this.getJsonStatus())) {
            this.addActionError(getText(this.getErrorCode()));
        }

        return LastValue;
    }

    /**
     * @return the responData
     */
    public String getResponData() {
        return responData;
    }

    /**
     * @param responData the responData to set
     */
    public void setResponData(String responData) {
        this.responData = responData;
    }

    /**
     * @return the model
     */
    public KtpData getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(KtpData model) {
        this.model = model;
    }

    /**
     * @return the contentData
     */
    public String getContentData() {
        return contentData;
    }

    /**
     * @param contentData the contentData to set
     */
    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    /**
     * @return the pk
     */
    public EktpCoreMappingPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(EktpCoreMappingPK pk) {
        this.pk = pk;
    }

    /**
     * @return the HRcode
     */
    public String getHRcode() {
        return HRcode;
    }

    /**
     * @param HRcode the HRcode to set
     */
    public void setHRcode(String HRcode) {
        this.HRcode = HRcode;
    }

    /**
     * @return the HRdesc
     */
    public String getHRdesc() {
        return HRdesc;
    }

    /**
     * @param HRdesc the HRdesc to set
     */
    public void setHRdesc(String HRdesc) {
        this.HRdesc = HRdesc;
    }

    /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * @return the resultMap
     */
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    /**
     * @param resultMap the resultMap to set
     */
    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * @return the jsonStatus
     */
    public String getJsonStatus() {
        return jsonStatus;
    }

    /**
     * @param jsonStatus the jsonStatus to set
     */
    public void setJsonStatus(String jsonStatus) {
        this.jsonStatus = jsonStatus;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the MenuSrc
     */
    public String getMenuSrc() {
        return MenuSrc;
    }

    /**
     * @param MenuSrc the MenuSrc to set
     */
    public void setMenuSrc(String MenuSrc) {
        this.MenuSrc = MenuSrc;
    }

    /**
     * @return the servletRequest
     */
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    /**
     * @param servletRequest the servletRequest to set
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * @return the session
     */
    public Map<String, Object> getSession() {
        return session;
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * @return the bdsmHost
     */
    public String getBdsmHost() {
        return bdsmHost;
    }

    /**
     * @param bdsmHost the bdsmHost to set
     */
    public void setBdsmHost(String bdsmHost) {
        this.bdsmHost = bdsmHost;
    }

    /**
     * @return the tokenKey
     */
    public String getTokenKey() {
        return tokenKey;
    }

    /**
     * @param tokenKey the tokenKey to set
     */
    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    /**
     * @return the tzToken
     */
    public String getTzToken() {
        return tzToken;
    }

    /**
     * @param tzToken the tzToken to set
     */
    public void setTzToken(String tzToken) {
        this.tzToken = tzToken;
    }
}
