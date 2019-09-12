/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu26301;

import bdsm.model.EktpCoreMapping;
import bdsm.model.EktpCoreMappingPK;
import bdsm.model.KtpData;
import bdsm.model.MenuRedirect;
import bdsm.model.MenuRedirectionPK;
import bdsm.model.ScreenOpening;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.DefinitionConstant;
import bdsm.util.HttpUtil;
import bdsm.util.SchedulerUtil;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import bdsm.web.KTPInquiryService;
import bdsm.web.ProTimePutLogAct;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00020800
 */
public class Menu26301Action extends BaseContentAction {

    private static final long serialVersionUID = 1L;
    // Action Declaration
    private static String GET_DATA_CUST = "CustomerSearchHost_list";
    private static String ACTION_GET = "ktp_get.action";
    private static String SCREEN_GET = "Screen_get.action";
    private static final String CALLMETHOD_ACTION = "Screen_callMethod.action";
    private static final String KTPCREATION = "ktpValChecking";
    private static String TEMP_CALL_METHOD;
    private static String MenuSrc = "26301";
    private static final String replicateFailed = "EKTP";
    private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private String jsonStatus;
    private String errorCode;
    private String FlagProc;
    private String customerName;
    private String noKartuIden;
    private String noCard;
    private String flagCard;
    private Date dateofBirth;
    private String cifNo;
    private String state;
    private String inputNik;
    private String responData;
    private String contentData;
    private String flagButton;
    private String current;
    private boolean skip;
    private KtpData model = new KtpData();
    private EktpCoreMapping cm = new EktpCoreMapping();
    private EktpCoreMappingPK pk = new EktpCoreMappingPK();
    private ScreenOpening so = new ScreenOpening();
    private MenuRedirect red = new MenuRedirect();
    private MenuRedirectionPK pkRed = new MenuRedirectionPK();
    private Map<String, String> params = new HashMap<String, String>();
    Map<String, Object> resultMap = new HashMap<String, Object>();
    private List classField;
    private List<Map<String, Object>> modelList;
    private String HRcode;
    private String HRdesc;
    private boolean flagSuccess;
    private String strutsAction = "3";
    private String flgExtSource;
    private String tempRedirect;
    private boolean customClass = true;
    private Map defaultValues;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        getLogger().info("flag Button " + getFlagButton());
        if ("1".equalsIgnoreCase(getFlagButton())) {
            this.session.put("Action", "KTP");
            Map sess = session;
            sess.put("MESSAGE","FLAG:1");
            ProTimePutLogAct times = new ProTimePutLogAct(sess, getTokenKey(), getTzToken(), getBdsmHost());
            getLogger().info("STATE :" + this.getState());
            try {
                getLogger().info("NIK :" + this.getInputNik());
                getLogger().info("FLAGCIF :" + this.so.getFlagCIF());
            } catch (Exception e) {
                getLogger().info("NIK null");
            }
            if (getState().equalsIgnoreCase("1")) {
                getLogger().info("next EKTP");
                if (!isValidSession()) {
                    return logout();
                }
                KTPInquiryService ktpServ = new KTPInquiryService();
                ktpServ.setBdsmHost(this.getBdsmHost());
                ktpServ.setServletRequest(this.getServletRequest());
                ktpServ.setSession(session);
                ktpServ.setMenuSrc(MenuSrc);
                ktpServ.setTokenKey(this.getTokenKey());
                ktpServ.setTzToken(this.getTzToken());

                ktpServ.inquiry(ACTION_GET, this.inputNik);
                //inquiry();
                this.model = ktpServ.getModel();
                this.contentData = ktpServ.getContentData();

                this.setActionErrors(ktpServ.getActionErrors());
                this.setActionMessages(ktpServ.getActionMessages());

                this.state = "2";
                this.session.put("Action", "KTP");
                getLogger().info("AFTER INQ :" + this.contentData);
                sess = session;
                sess.put("MESSAGE","VALIDATE BEGIN");
                times = new ProTimePutLogAct(sess, getTokenKey(), getTzToken(), getBdsmHost());
                return "2";
            } else if (getState().equalsIgnoreCase("2")) {
                getLogger().info("MODEL :" + getModel().toString());

                if ("CANCEL".equalsIgnoreCase(this.current)) {
                    return "1";
                } else {
                    String BatchId = null;
                    try {
                        getLogger().info("SO INIT :" + this.so.toString());
                        getLogger().info("MENU :" + this.so.getMenuAccount());
                        this.pkRed.setSourceMenu(this.so.getMenuAccount().trim());
                        getLogger().info("EXT ALIAS :" + this.so.getExtUser());
                        // replicated Branch

                        if (this.so.getExtUser() == null || this.so.getExtUser().isEmpty()) {
                            this.so.setExtUser(DefinitionConstant.singleScreenAlias);
                            BatchId = SchedulerUtil.generateUUID();
                            this.so.setApplicationID(BatchId);
                            this.so.setBranchCodeCasa(this.so.getBranchCode());
                        } else {
                            this.flgExtSource = "1";
                            // change IB HomeBranch
                            this.so.setBranchCode(null);
                        }

                        if(DefinitionConstant.trueFlag.equalsIgnoreCase(so.getFlagCIF().toString())){
                            this.so.setBranchCode(null);
                        }
                        
                        this.pkRed.setAliasName(this.so.getExtUser());
                        //this.pkRed.setAliasName("SSCASA");

                        this.tempRedirect = GET_DATA_CUST;
                        getMenuMapping();
                        GET_DATA_CUST = this.red.getInQuiry();
                        strutsAction = this.red.getStrutsAction();
                        MenuSrc = this.so.getMenuAccount().trim();

                        this.flagSuccess = true;
                        List redCUst = SchedulerUtil.getParameter(this.tempRedirect, "_");
                        List compCUst = SchedulerUtil.getParameter(this.GET_DATA_CUST, "_");

                        if (!redCUst.isEmpty() && !compCUst.isEmpty()) {
                            if (redCUst.get(0).toString().equalsIgnoreCase(compCUst.get(0).toString())) {
                                customClass = false;
                                List callMethods = SchedulerUtil.getParameter(this.CALLMETHOD_ACTION, "_");
                                if (!callMethods.isEmpty()) {
                                    GET_DATA_CUST = compCUst.get(0).toString() + "_" + callMethods.get(1).toString();
                                    TEMP_CALL_METHOD = compCUst.get(1).toString();
                                }
                            }
                        }
                        getLogger().info("PARAMS WEB :" + GET_DATA_CUST + " : " + TEMP_CALL_METHOD);

                    } catch (Exception e) {
                        getLogger().info("FAILED GET MENU :" + e, e);
                        this.flagSuccess = false;
                    }

                    if (so.getFlagCIF() == null) {
                        so.setFlagCIF(true);
                    }

                    if (this.flgExtSource == null) {
                        this.flgExtSource = "0";
                    }
                    //this.classField = ClassConverterUtil.getRefClass(this.so, "so");
                    dataMapping();
                    getLogger().info("MODEL BEFORE :" + this.so.toString());
                    if (!so.getFlagCIF()) {
                        // if not skip and not Existing CIF
                        getLogger().info("MODEL SCREEN :" + this.so.toString());
                        ClassConverterUtil.ReferenceClass(model, so);
                        getLogger().info("GETcUSTOMvALUE ");

                        //ktpdataReplication();
                        KTPInquiryService ktpServ = new KTPInquiryService();
                        ktpServ.setModel(this.model);
                        ktpServ.setBdsmHost(this.getBdsmHost());
                        ktpServ.setTokenKey(this.getTokenKey());
                        ktpServ.setTzToken(this.getTzToken());

                        this.so = ktpServ.ktpdataReplication(so, flgExtSource, flagSuccess);

                        getLogger().info("DOneGet ");
                        this.session.put("ACTION", "NEW");

                    }
                    getBranch();
                    getLogger().info("SO EXT USER :" + so.toString());
                    this.flagButton = "2";
                    if (flagSuccess == false) {
                        this.so.setStaff("N");
                        this.so.setFlgIctype("1");
                    }
                    sess = session;
                    sess.put("MESSAGE","VALIDATE END");
                    times = new ProTimePutLogAct(sess, getTokenKey(), getTzToken(), getBdsmHost());
                    return strutsAction;
                }
            } else {
                return "1";
            }
        } else {
            return "input";
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        //validateCustomerName();
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected MenuRedirect getMenuMapping() {
        Object[] listObj = {this.red, this.pkRed};
        String[] nameless = {"red", "pkRed"};
        String[] prefix = {"", ""};

        this.params = (Map<String, String>) ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1);
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        this.params.put("methodName", DefinitionConstant.MENUREDIRECCT);
        this.params.put("useTransaction", "true");
        try {
            String result = HttpUtil.request(this.getBdsmHost() + CALLMETHOD_ACTION, params);

            resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
            getLogger().info("MAP :" + resultMap);
            Map Menured = (Map) resultMap.get("red");
            defaultValues = (Map) resultMap.get("so");
            getLogger().info("SO DEFAULT MAP :" + defaultValues);

            ClassConverterUtil.MapToClass(defaultValues, so);
            ClassConverterUtil.MapToClass(Menured, red);

            getLogger().info("SO DEFAULT :" + this.so.toString());

            this.jsonStatus = (String) resultMap.get("jsonStatus");
            this.errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException jex) {
            this.getLogger().fatal(jex, jex);
        }
        return this.red;
    }

    /**
     * 
     * @return
     */
    public String getBranch() {
        this.getLogger().info("START GET BRANCH");
        try {
            Map<String, String> requestMap = new HashMap<String, String>();
            String result = null;
            Map<String, Object> resultMap = null;
            String cdBranch = null;
            try {
                if (DefinitionConstant.EXTFLAG.equalsIgnoreCase(this.flgExtSource)) {
                    cdBranch = this.so.getBranchCodeCasa().toString();
                } else {
                    if(DefinitionConstant.trueFlag.equalsIgnoreCase(this.so.getFlagCIF().toString())){
                        cdBranch = this.so.getBranchCodeCasa().toString();
                    } else {
                        cdBranch = this.so.getBranchCode().toString();
                    }
                }

            } catch (Exception e) {
                getLogger().info("FAILED GET BRANCH :" + e, e);
            }

            getLogger().info("Get Branch Name [ Start ]" + cdBranch);
            requestMap.clear();
            requestMap.put("cdBranch", cdBranch);
            requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
            result = bdsm.util.HttpUtil.request(this.getBdsmHost() + SCREEN_GET, requestMap);
            resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
            Map getBranch = (Map) resultMap.get("model");
            this.getLogger().info("getBranch " + getBranch);
            String branchNameCode = getBranch.get("branchCodeName").toString();

            this.getLogger().info("EXTFLAG " + this.flgExtSource + " | FLAGcif :" + so.getFlagCIF());
            if (DefinitionConstant.EXTFLAG.equalsIgnoreCase(this.flgExtSource)) {
                getLogger().info("Result 1");
            } else {
                if(DefinitionConstant.falseFlag.equalsIgnoreCase(this.so.getFlagCIF().toString())){
            so.setBranchCodeName(branchNameCode);
                }
            so.setBranchCodeCasaDesc(branchNameCode);
                getLogger().info("Result 2");
            }

            return SUCCESS;

        } catch (Throwable e) {
            getLogger().fatal(e, e);
            getLogger().info("Error " + e);
            return Menu26301Action.ERROR;
        }
    }

    /**
     * 
     */
    public void ktpdataReplication() {
        // EKTP custom Mapping Value
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFBack = new SimpleDateFormat("dd/MM/yyyy");
        List dukcapilVal = new ArrayList();

        this.so.setPermaAddress1(this.model.getAddress());
        this.so.setPermaAddress2("RT " + this.model.getRt() + " RW " + this.model.getRw());
        this.so.setPermaAddress3(this.model.getNamKel() + "," + this.model.getNamKec());
        this.so.setPermaZipCode(this.model.getPosCode());
        this.so.setNamMother(this.model.getNamMother());

        getLogger().info("DOB :" + this.model.getDob());
        Date AccDate = null;
        try {
            AccDate = dateFormat.parse(this.model.getDob());
            this.getLogger().info("AccDate" + AccDate);
            this.so.setBirthDate(dateFBack.format(AccDate));
            this.getLogger().info(this.so.getBirthDate());

        } catch (ParseException ex) {
            getLogger().info("EXCEPTION :" + ex, ex);
            getLogger().info("DATE :" + this.model.getDob());
            this.so.setBirthDate(this.model.getDob());
        } catch (NullPointerException nulls) {
            getLogger().info("EXCEPTION DOB NULLS :" + nulls, nulls);
            getLogger().info("DATE :" + this.model.getDob());
            this.so.setBirthDate(this.model.getDob());
        }

        if (model.getSex().equalsIgnoreCase("LAKI-LAKI")) {
            this.so.setGender("M");
        } else if (model.getSex().equalsIgnoreCase("PEREMPUAN")) {
            this.so.setGender("F");
        } else {
            if (this.flgExtSource.equalsIgnoreCase("0")) {
                this.so.setGender("");
            }
        }

        this.so.setFullName(this.model.getName());
        List spaceName = SchedulerUtil.getParameter(this.model.getName(), " ");

        if (!spaceName.isEmpty()) {
            StringBuilder lastName = new StringBuilder();
            for (int i = 0; i < spaceName.size(); i++) {
                if (i == 0) {
                    this.so.setCustFirstName(spaceName.get(i).toString());
                } else if (i == 1) {
                    if (spaceName.size() == 2) {
                        this.so.setCustLastName(spaceName.get(i).toString());
                    } else {
                        this.so.setCustMidName(spaceName.get(i).toString());
                    }
                } else {
                    lastName.append(spaceName.get(i).toString()).append(" ");
                }
            }
            this.so.setCustLastName(lastName.toString().trim());
        }

        if (this.flgExtSource.equalsIgnoreCase("0")) {
            this.so.setMailAddrs1(this.so.getPermaAddress1());
            this.so.setMailAddrs2(this.so.getPermaAddress2());
            this.so.setMailAddrs3(this.so.getPermaAddress3());
            this.so.setMaillingZipCode(this.so.getPermaZipCode());
            this.so.setMaillingCode(this.so.getPermaZipCode());

            this.so.setHoldAddress1(this.so.getPermaAddress1());
            this.so.setHoldAddress2(this.so.getPermaAddress2());
            this.so.setHoldAddress3(this.so.getPermaAddress3());
            this.so.setHoldZipCode(this.so.getPermaZipCode());

            // reformatting Date
            if (flagSuccess == false) {
                this.so.setFatca("3");
                this.so.setBadanAS("2");
                this.so.setAlamatAS("2");
                this.so.setGreenCard("2");
                this.so.setNationalityDesc("ID-INDONESIA - 09002");
                this.so.setNationality("ID");
                this.so.setCommunicationDesc("SETUJU SEWAKTU-WAKTU");
                this.so.setDataTransXtractFlag(3);
                this.so.setProductCodeDesc("TABUNGAN DANAMON LEBIH");
                this.so.setProductCode(422);
                this.so.setIdcardTDesc("KTP");
                this.so.setIdcardType("KTP");
                this.so.setFlgIctype("1");
                this.so.setStateIndicator("MAIL");
                this.so.setStateIndicatorDesc("MAIL");
                this.so.setRelation("SOLE OWNER");
			this.so.setEchannel("Y");
			this.so.setEchannelDesc("YES");
			this.so.setFatcaDesc("No");
			this.so.setGreenCardDesc("No");
			this.so.setAlamatASDesc("No");
			this.so.setBadanASDesc("No");
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
            pk.setFieldCode(counter);
            List paramQuery = bdsm.util.SchedulerUtil.getParameter(dukcapilVal.get(i - 1).toString(), ":");
            pk.setEktpVal(paramQuery.get(0).toString());
            pk.setFlgMntStatus("A");
            getLogger().info("PRIMARY :" + pk);
            if (!"-".equals(paramQuery.get(2))) {
                identifier = paramQuery.get(2).toString();
            }
            if (!"-".equals(paramQuery.get(1))) {
                nameParam = paramQuery.get(1).toString();
            }
            String ktpCoreVal = getMappingdata(nameParam, this.pk, identifier);
            getNCBSval.add(ktpCoreVal);
        }

        List tempContainer = new ArrayList();
        if (!"".equals(getNCBSval.get(0).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(0).toString(), ":");
            this.so.setReligionDesc(tempContainer.get(0).toString());
            this.so.setReligion(tempContainer.get(1).toString());
        } else {
            this.so.setReligionKTP(model.getReligion());
            this.so.setReligion(replicateFailed);
        }
        if (!"".equals(getNCBSval.get(1).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(1).toString(), ":");
            this.so.setPermaTownCityDesc(tempContainer.get(0).toString());
            this.so.setPermaTownCity(tempContainer.get(1).toString());
            this.so.setMaillingTownCityDesc(this.so.getPermaTownCityDesc());
            this.so.setMaillingTownCity(this.so.getPermaTownCity());
        } else {
            this.so.setMaillingTownCityKTP(model.getNamKab());
            this.so.setPermaTownCityKTP(model.getNamKab());
            this.so.setPermaTownCity(replicateFailed);
        }
        if (!"".equals(getNCBSval.get(2).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(2).toString(), ":");
            this.so.setPermaStateDesc(tempContainer.get(0).toString());
            this.so.setPermaState(tempContainer.get(1).toString());
            this.so.setMaillingStateDesc(this.so.getPermaStateDesc());
            this.so.setMaillingState(this.so.getPermaState());
        } else {
            this.so.setMaillingStateKTP(model.getNamProp());
            this.so.setPermaStateKTP(model.getNamProp());
            this.so.setPermaState(replicateFailed);
        }
        if (!"".equals(getNCBSval.get(3).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(3).toString(), ":");
            this.so.setLastEduDesc(tempContainer.get(0).toString());
            this.so.setLastEdu(tempContainer.get(1).toString());
        } else {
            this.so.setLastEduKTP(model.getLastEdu());
            this.so.setLastEdu(replicateFailed);
        }
        if (!"".equals(getNCBSval.get(4).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(4).toString(), ":");
            this.so.setProfessionDesc(tempContainer.get(0).toString());
            this.so.setProfession(tempContainer.get(1).toString());
            if (!"".equals(this.HRcode)) {
                this.so.setHighRiskprofessionCode(this.HRcode);
                this.so.setHighRiskprofession(this.HRdesc);
            }
        } else {
            this.so.setProfessionKTP(model.getProfession());
            this.so.setProfession(replicateFailed);
        }
        if (!"".equals(getNCBSval.get(5).toString())) {
            tempContainer = bdsm.util.SchedulerUtil.getParameter(getNCBSval.get(5).toString(), ":");
            this.so.setMarStatDesc(tempContainer.get(0).toString());
            this.so.setMarStat(tempContainer.get(1).toString());

        } else {
            this.so.setMarStatKTP(model.getMarStat());
            this.so.setMarStat(replicateFailed);
        }
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected void process() {
        getLogger().info("cif :" + this.cifNo);
        getLogger().info("METHOD :" + TEMP_CALL_METHOD);

        Object[] listObj = {this.so};
        String[] nameless = {"so"};
        String[] prefix = {""};

        this.params = (Map<String, String>) ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1);

        params.put("cifNo", this.cifNo);
        params.put("flagsCIF", FlagProc);
        params.put("prodExcluder", DefinitionConstant.prodExcluder);
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        if (!customClass) {
            this.params.put("methodName", TEMP_CALL_METHOD);
            this.params.put("useTransaction", "true");
        }

        try {
            String result = HttpUtil.request(this.getBdsmHost() + GET_DATA_CUST, params);

            resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
            getLogger().info("MAP :" + resultMap);
            Map ScreenOpening = (Map) resultMap.get("so");
            //Map ScreenOpening = (Map) resultMap.get("modelList");

            ClassConverterUtil.MapToClass(ScreenOpening, so, true);
            getLogger().info("SO PROCESS :" + so.toString());

            getLogger().info("CIF :" + this.so.getCifNumber());
            this.jsonStatus = (String) resultMap.get("jsonStatus");
            this.errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException jex) {
            this.getLogger().fatal(jex, jex);
        }

        if (SUCCESS.equals(this.jsonStatus)) {
            this.addActionMessage(getText(this.errorCode));
        } else if (ERROR.equals(this.jsonStatus)) {
            this.addActionError(getText(this.errorCode));
        }
    }

    @SkipValidation
    private void dataMapping() {
        // for data mapping check FlagCIF
        // if it's True, data CIF will be Inquiry from database
        // else Data CIF will be replicated from EKTP Inquiry
        if (so.getFlagCIF()) {
            this.FlagProc = "CIFDATA";
            getLogger().info("CIF PROCESS BEGIN");
            process();
            this.session.put("ACTION", "PROCESSED");
            this.so.setFlgProcess("Y");
        } else {
            if (DefinitionConstant.EXTFLAG.equalsIgnoreCase(this.flgExtSource)) {
                getLogger().info("EXT CIF PROCESS BEGIN");
                process();
                this.so.setFlgProcess("Y");
            } else {
            this.so.setFlgProcess("N");
        }
        }
        //ReferenceClass(this.so, this.model);
    }

    @SkipValidation
    private String getMappingdata(String inquiryParam, EktpCoreMappingPK pk, String inqNames) {

        String LastValue = "";
        getLogger().info("PARAM_INQUIRY :" + inquiryParam);
        getLogger().info("name_INQUIRY :" + inqNames);
        StringBuilder valIndex = new StringBuilder();

        Object[] listObj = {pk};
        String[] nameless = {"pk"};
        String[] prefix = {""};

        this.params = (Map<String, String>) ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1);
        this.params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        this.params.put("methodName", KTPCREATION);
        this.params.put("useTransaction", "true");
        this.params.put("inqParam", inquiryParam);
        this.params.put("inqNames", inqNames);

        try {
            String result = HttpUtil.request(this.getBdsmHost() + CALLMETHOD_ACTION, params);

            resultMap = (Map<String, Object>) JSONUtil.deserialize(result);

            List<Map> ktpMapping = (List) resultMap.get("modelList");
            if (!ktpMapping.isEmpty()) {
                valIndex.append(ktpMapping.get(0).get("val").toString());
                valIndex.append(":").append(ktpMapping.get(0).get("fieldprop").toString());
            }
            LastValue = valIndex.toString();
            getLogger().info("VAL :" + LastValue);
            this.jsonStatus = (String) resultMap.get("jsonStatus");
            this.errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException jex) {
            this.getLogger().fatal(jex, jex);
        }

        if (SUCCESS.equals(this.jsonStatus)) {
            this.addActionMessage(getText(this.errorCode));
        } else if (ERROR.equals(this.jsonStatus)) {
            this.addActionError(getText(this.errorCode));
        }

        return LastValue;
    }

    /**
     * @return the custName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the flagCard
     */
    public String getFlagCard() {
        return flagCard;
    }

    /**
     * @param flagCard the flagCard to set
     */
    public void setFlagCard(String flagCard) {
        this.flagCard = flagCard;
    }

    /**
     * @return the dateofBirth
     */
    public Date getDateofBirth() {
        return dateofBirth;
    }

    /**
     * @param dateofBirth the dateofBirth to set
     */
    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    /**
     * @return the noKartuIden
     */
    public String getNoKartuIden() {
        return noKartuIden;
    }

    /**
     * @param noKartuIden the noKartuIden to set
     */
    public void setNoKartuIden(String noKartuIden) {
        this.noKartuIden = noKartuIden;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the inputNik
     */
    public String getInputNik() {
        return inputNik;
    }

    /**
     * @param inputNik the inputNik to set
     */
    public void setInputNik(String inputNik) {
        this.inputNik = inputNik;
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
     * @return the flagButton
     */
    public String getFlagButton() {
        return flagButton;
    }

    /**
     * @param flagButton the flagButton to set
     */
    public void setFlagButton(String flagButton) {
        this.flagButton = flagButton;
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
     * @return the so
     */
    public ScreenOpening getSo() {
        return so;
    }

    /**
     * @param so the so to set
     */
    public void setSo(ScreenOpening so) {
        this.so = so;
    }

    /**
     * @return the FlagProc
     */
    public String getFlagProc() {
        return FlagProc;
    }

    /**
     * @param FlagProc the FlagProc to set
     */
    public void setFlagProc(String FlagProc) {
        this.FlagProc = FlagProc;
    }

    /**
     * @return the dateFormatter
     */
    public static DateFormat getDateFormatter() {
        return dateFormatter;
    }

    /**
     * @param aDateFormatter the dateFormatter to set
     */
    public static void setDateFormatter(DateFormat aDateFormatter) {
        dateFormatter = aDateFormatter;
    }

    /**
     * @return the ACTION_GET
     */
    public static String getACTION_GET() {
        return ACTION_GET;
    }

    /**
     * @param aACTION_GET the ACTION_GET to set
     */
    public static void setACTION_GET(String aACTION_GET) {
        ACTION_GET = aACTION_GET;
    }

    /**
     * @return the MenuSrc
     */
    public static String getMenuSrc() {
        return MenuSrc;
    }

    /**
     * @param aMenuSrc the MenuSrc to set
     */
    public static void setMenuSrc(String aMenuSrc) {
        MenuSrc = aMenuSrc;
    }

    /**
     * @return the skip
     */
    public boolean isSkip() {
        return skip;
    }

    /**
     * @param skip the skip to set
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    /**
     * @return the current
     */
    public String getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(String current) {
        this.current = current;
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
     * @return the cifNo
     */
    public String getCifNo() {
        return cifNo;
    }

    /**
     * @param cifNo the cifNo to set
     */
    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    /**
     * @return the noCard
     */
    public String getNoCard() {
        return noCard;
    }

    /**
     * @param noCard the noCard to set
     */
    public void setNoCard(String noCard) {
        this.noCard = noCard;
    }

    /**
     * @return the classField
     */
    public List getClassField() {
        return classField;
    }

    /**
     * @param classField the classField to set
     */
    public void setClassField(List classField) {
        this.classField = classField;
    }

    /**
     * @return the modelList
     */
    public List<Map<String, Object>> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<Map<String, Object>> modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the cm
     */
    public EktpCoreMapping getCm() {
        return cm;
    }

    /**
     * @param cm the cm to set
     */
    public void setCm(EktpCoreMapping cm) {
        this.cm = cm;
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
     * @return the red
     */
    public MenuRedirect getRed() {
        return red;
    }

    /**
     * @param red the red to set
     */
    public void setRed(MenuRedirect red) {
        this.red = red;
    }

    /**
     * @return the flgExtSource
     */
    public String getFlgExtSource() {
        return flgExtSource;
    }

    /**
     * @param flgExtSource the flgExtSource to set
     */
    public void setFlgExtSource(String flgExtSource) {
        this.flgExtSource = flgExtSource;
    }
}
