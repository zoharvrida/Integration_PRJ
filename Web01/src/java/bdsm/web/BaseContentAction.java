/*
 * BASE CONTENT for WEB Wrapper
 */
package bdsm.web;


import static bdsm.web.Constant.C_WEB_SESSION_ID;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.util.ServletContextAware;

/**
 *
 * @author bdsm
 */
public abstract class BaseContentAction extends ActionSupport implements SessionAware, ServletContextAware, ServletRequestAware {

    private ServletContext servletContext;
    protected Map<String, Object> session;
    private HttpServletRequest servletRequest;
    
    /**
     * 
     * @return logger specific for class
     */
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }
    private String errorMessage;

    /**
     * 
     * Function : getting input method action
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID doInput()
     */
    @Override
    @SkipValidation
    public final String input() {
        getLogger().info("[Begin] input()");
        try {
            if (isValidSession()) {
                return doInput();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            setErrorMessage(e.getMessage());
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] input()");
        }
    }

    /**
     * 
     * Function : getting execute method action
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID exec()
     */
    @Override
    public final String execute() {
        getLogger().info("[Begin] execute()");
        try {
            if (isValidSession()) {
                return exec();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            setErrorMessage(e.getMessage());
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] execute()");
        }
    }

    /**
     * 
     * Function : getting add method action
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID doAdd()
     */
    @SkipValidation
    public final String add() {
        getLogger().info("[Begin] add()");
        try {
            if (isValidSession()) {
                return doAdd();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            setErrorMessage(e.getMessage());
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] add()");
        }
    }

    /**
     * 
     * Function : getting get method action
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID doEdit()
     */
    @SkipValidation
    public final String edit() {
        getLogger().info("[Begin] edit()");
        try {
            if (isValidSession()) {
                return doEdit();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            setErrorMessage(e.getMessage());
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] edit()");
        }
    }

    /**
     * 
     * Function : getting delete action
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID doDelete() 
     */
    @SkipValidation
    public final String delete() {
        getLogger().info("[Begin] delete()");
        try {
            if (isValidSession()) {
                return doDelete();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            setErrorMessage(e.getMessage());
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] delete()");
        }
    }

    /**
     * 
     * Function : getting title get directly from parameter
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID title string to Struts2.xml
     */
    @SkipValidation
    public String title_() {
        this.getLogger().info("[Begin] title_()");
        
        try {
            if (isValidSession())
                return "title_";
            else
                return logout();
        }
        catch (Throwable e) {
            getLogger().fatal(e, e);
            return ERROR;
        }
        finally {
            this.getLogger().info("[ End ] title_()");
        }
    }

    /**
     * 
     * Function : getting title based on JS
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID title string to Struts2.xml
     */
    @SkipValidation
    public final String title() {
        getLogger().info("[Begin] title()");
        try {
            if (isValidSession()) {
                return "title";
            } else {
                return logout();
            }
        } catch (Throwable e) {
            setErrorMessage(e.getMessage());
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] title()");
        }
    }

    /**
     * 
     * Function : getting button action
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID buttons string to Struts2.xml
     */
    @SkipValidation
    public final String buttons() {
        getLogger().info("[Begin] buttons()");
        try {
            if (isValidSession()) {
                return "buttons";
            } else {
                return logout();
            }
        } catch (Throwable e) {
            setErrorMessage(e.getMessage());
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] buttons()");
        }
    }

    /**
     * 
     * Function : getting blank page 
     * @return ERROR type from com.opensymphony.xwork2.ActionSupport 
     *         NOT VALID logout()
     *         VALID blank string to Struts2.xml
     */
    @SkipValidation
    public final String blank() {
        getLogger().info("[Begin] blank()");
        try {
            if (isValidSession()) {
                return "blank";
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] blank()");
        }
    }
    
    
    /**
     * 
     * Function : general HTTPrequest to be called by other class
     * @param serviceName (Name of service used)
     * @param methodName (Method taht need to be called)
     * @param requestMap (Map that need to parse to BDSMHOST01
     * @return
     * @throws org.apache.struts2.json.JSONException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Map callHostHTTPRequest(String serviceName, String methodName, java.util.Map requestMap) 
                throws org.apache.struts2.json.JSONException {
        if (requestMap == null)
            requestMap = new java.util.HashMap();
        requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
        
        String response = bdsm.util.HttpUtil.request(this.getBdsmHost() + serviceName + "_" + methodName, requestMap);
        return (Map<String, ? extends Object>) JSONUtil.deserialize(response);
    }

    /**
     * Function : method to get session id
     * @return session Id
     */
    protected String getSessionId() {
        String sessionId = (String) this.session.get(C_WEB_SESSION_ID);
        
        if (sessionId == null) {
            sessionId = ServletActionContext.getRequest().getSession().getId();
            this.session.put(C_WEB_SESSION_ID, sessionId);
        }
        
        return sessionId;
    }
    

    /**
     * 
     * @param map session to set
     */
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    //public Map<String, Object> getSession() {
    //    return this.session;
    //}

    /**
     * Function : method for checking session
     * @return boolean
     */
    protected boolean isValidSession() {
        if (!(this instanceof MenuAction) && "GET".equalsIgnoreCase(servletRequest.getMethod())) {
            getLogger().info("INVALID SESSION :" + servletRequest.getMethod());
            return false;
        }
        if (session==null) return false;
        String userId = (String) session.get(Constant.C_IDUSER);
        if (userId==null || "".equals(userId)) return false;
        getLogger().info("VALID SESSION :" + userId);
        
        getLogger().debug("SESSION ?? : " + this.session);
        return true;
    }

    /**
     * 
     * @return NONE type from com.opensymphony.xwork2.ActionSupport 
     */
    protected String logout() {

        return ActionSupport.NONE;
    }
    
    /**
     * 
     * @return initParameter for BDSMHOST
     */
    protected String getBdsmHost() {
        return servletContext.getInitParameter("bdsmHost");
    }

    /**
     * 
     * @return TokenKey
     */
    protected String getTokenKey() {
        return servletContext.getInitParameter("tokenKey");
    }

    /**
     * 
     * @return tzToken
     */
    protected String getTzToken() {
        return servletContext.getInitParameter("tzToken");
    }

    /**
     * 
     * @return INPUT type from com.opensymphony.xwork2.ActionSupport
     */
    public String doInput() {
        return ActionSupport.INPUT;
    }
    
    /**
     * 
     * @return using for inheritance execution class
     */
    public abstract String exec();
    /**
     * 
     * @return using for inheritance Add Operation
     */
    public abstract String doAdd();
    /**
     * 
     * @return using for inheritance Edit Operation
     */
    public abstract String doEdit();
    /**
     * 
     * @return using for inheritance Delete Operation
     */
    public abstract String doDelete();

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 
     * @return servletContext
     */
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    /**
     * @return the errorMessage
     */
    @JSON(serialize=false)
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    /**
     * 
     * @param hsr http Servlet Request
     */
    public void setServletRequest(HttpServletRequest hsr) {
        this.servletRequest = hsr;
    }

    /**
     * @return the servletRequest 
     */
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }
}
