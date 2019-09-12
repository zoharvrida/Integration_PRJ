/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.util.ServletContextAware;

/**
 *
 * @author bdsm
 */
public abstract class BaseTabContentAction extends ActionSupport implements SessionAware, ServletContextAware {

    private ServletContext servletContext;
    /**
     * 
     */
    protected Map<String, Object> session;

    /**
     * 
     * @return
     */
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }
    private String errorMessage;

    /**
     * 
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @param map
     */
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    //public Map<String, Object> getSession() {
    //    return this.session;
    //}

    /**
     * 
     * @return
     */
    protected boolean isValidSession() {
        if (session==null) return false;
        String userId = (String) session.get(Constant.C_IDUSER);
        if (userId==null || "".equals(userId)) return false;
        return true;
    }

    /**
     * 
     * @return
     */
    protected String logout() {

        return ActionSupport.NONE;
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

    /**
     * 
     * @return
     */
    public String doInput() {
        return ActionSupport.INPUT;
    }

    /**
     * 
     * @return
     */
    public abstract String exec();
    /**
     * 
     * @return
     */
    public abstract String doAdd();
    /**
     * 
     * @return
     */
    public abstract String doEdit();
    /**
     * 
     * @return
     */
    public abstract String doDelete();

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
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

}
