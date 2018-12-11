package bdsm.web.login;

import bdsm.model.MasterTemplate;
import bdsm.model.MasterUser;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.Constant;
import bdsm.web.DateInquiryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.util.ServletContextAware;

/**
 * 
 * @author bdsm
 */
public class LoginAction extends ActionSupport implements SessionAware, ServletContextAware {
    private static Logger logger = Logger.getLogger(LoginAction.class);
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    private static final String ACTION_LOGIN = "login.action";
    private static final String ACTION_GETUSER = "user_get.action";
    private static final String ACTION_GETTEMPLATE = "template_get.action";
    private static final String ACTION_GETDATE = "date_get.action";
    
    private ServletContext servletContext;
    private String idUser;
    private String password;
    private String postback;
    private Map<String, Object> session;

    /**
     * 
     * @return
     */
    @Override
    public String execute(){
        logger.info("[Begin] execute()");
        //create random number for delay error login
        int min=1;
        int max=25;
        int delay = (min + (int)(Math.random()*((max-min)+1)))*1000;
        logger.debug("Delay : " + delay);
        try {
            if (postback==null) {
                return ActionSupport.NONE;
            }

            if (isValidCredential(this.idUser, this.password) == 1) {

                MasterUser user = getUser(idUser);
                MasterTemplate tmpl = getTemplate(user.getIdTemplate());
                Date dt = getBusinessDate();
                Date df = getDateFirst();
                Date dl = getDateLast();
                
                session.put(Constant.C_IDUSER, idUser);
                session.put(Constant.C_NAMUSER, user.getNamUser());
                session.put(Constant.C_CODEBRANCH, user.getCdBranch());
                session.put(Constant.C_IDTEMPLATE, user.getIdTemplate());
                session.put(Constant.C_NAMTEMPLATE, tmpl.getNamTemplate());
                session.put(Constant.C_DATEBUSINESS, dt);
                session.put(Constant.C_DATEFIRST, df);
                session.put(Constant.C_DATELAST, dl);
                setSession(session);

                return ActionSupport.SUCCESS;
            } else {
                Thread.sleep(delay);
                addActionError(getText("error.login"));
                return ActionSupport.INPUT;
            }
        } catch (Throwable e) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                logger.fatal(ex, ex);
            }
            logger.fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            logger.info("[ End ] execute()");
        }
    }

    private int isValidCredential(String idUser, String password) {
        Map<String, String> map = new HashMap<String, String>();
        String token = BdsmUtil.generateToken(getTokenKey(), getTzToken());
        map.put("action", "login");
        map.put("idUser", idUser);
        map.put("password", password);
        logger.debug("Token generated : " + token);
        map.put("token", token);
        String result = HttpUtil.request(getBdsmHost() + ACTION_LOGIN, map);
        logger.debug("---- Response:");
        logger.debug(result);
        logger.debug("---- End Response");
        if (result == null) return 0;

        int ret;
        try {
            HashMap resultMap = (HashMap)JSONUtil.deserialize(result);
            //Boolean httpStatus = (Boolean) resultMap.get("jsonResult");
            //if (httpStatus != null && httpStatus==true) {
                ret = ((Long) resultMap.get("validLogin")).intValue();
//                if (ret==null) ret=false;
            //}
        } catch (JSONException ex) {
            logger.fatal(ex);
            return 0;
        }

        return ret;
    }

    /**
     * 
     * @return
     */
    public String getIdUser() {
        return idUser;
    }

   /**
    * 
    * @param idUser
    */
   public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

   /**
    * 
    * @return
    */
   public String getPassword() {
        return password;
    }

   /**
    * 
    * @param password
    */
   public void setPassword(String password) {
        this.password = password;
    }

   /**
    * 
    * @return
    */
   public String getPostback() {
        return postback;
    }

    /**
     * 
     * @param postback
     */
    public void setPostback(String postback) {
        this.postback = postback;
    }

    /**
     * 
     * @param map
     */
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    /**
     * 
     * @return
     */
    public Map<String, Object> getSession() {
        return this.session;
    }

    /**
     * 
     * @param sc
     */
    public void setServletContext(ServletContext sc) {
        this.servletContext = sc;
    }
    
    /**
     * 
     * @return
     */
    protected String getBdsmHost() {
        return servletContext.getInitParameter("bdsmHost");
    }

    /**
     * 
     * @return
     */
    protected String getTokenKey() {
        return servletContext.getInitParameter("tokenKey");
    }

    /**
     * 
     * @return
     */
    protected String getTzToken() {
        return servletContext.getInitParameter("tzToken");
    }

    private MasterUser getUser(String idUser) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("model.idUser", idUser);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        String result = HttpUtil.request(getBdsmHost() + ACTION_GETUSER, map);
        Map resultMap = (Map) JSONUtil.deserialize(result);
        Map modelMap = (Map) resultMap.get("model");
        String modelJson = gson.toJson(modelMap);
        return (MasterUser) gson.fromJson(modelJson, MasterUser.class);
    }

    private MasterTemplate getTemplate(String idTemplate) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("model.idTemplate", idTemplate);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        String result = HttpUtil.request(getBdsmHost() + ACTION_GETTEMPLATE, map);
        Map resultMap = (Map) JSONUtil.deserialize(result);
        Map modelMap = (Map) resultMap.get("model");
        String modelJson = gson.toJson(modelMap);
        return (MasterTemplate) gson.fromJson(modelJson, MasterTemplate.class);
    }

    private Date getBusinessDate() throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        String result = HttpUtil.request(getBdsmHost() + ACTION_GETDATE, map);
        Map resultMap = (Map) JSONUtil.deserialize(result);
        String modelJson = (String) resultMap.get("model");
        //if (!modelJson.endsWith("Z")) modelJson += "Z";
        modelJson = "\"" + modelJson + "\"";
        return (Date) gson.fromJson(modelJson, java.sql.Date.class);
    }
    private Date getDateFirst(){
        Date dateFirst = null;
        DateInquiryService srv = new DateInquiryService(session,this.getBdsmHost(),getTokenKey(),getTzToken());
        Map rangeDate = srv.getRangeDate("FIRST");
        logger.debug("MAP LOGIN :" + rangeDate);
        try {
            String rangeFirst = (String) rangeDate.get("firstDate");
            rangeFirst = "\"" + rangeFirst + "\"";
            dateFirst = gson.fromJson(rangeFirst, java.sql.Date.class);
        } catch (Exception e) {
            logger.debug("EXCEPTION : " +e,e);
            dateFirst = new Date();
        }
        return dateFirst;
    }
    private Date getDateLast(){
        Date dateLast = null;
        DateInquiryService srv = new DateInquiryService(session,this.getBdsmHost(),getTokenKey(),getTzToken());
        Map rangeDate = srv.getRangeDate("LAST");
        logger.debug("MAP LOGIN :" + rangeDate);
        try {
            String rangeFirst = (String) rangeDate.get("lastDate");
            rangeFirst = "\"" + rangeFirst + "\"";
            dateLast = gson.fromJson(rangeFirst, java.sql.Date.class);
        } catch (Exception e) {
            logger.debug("EXCEPTION : " +e,e);
            dateLast = new Date();
        }
        return dateLast;
    }
}