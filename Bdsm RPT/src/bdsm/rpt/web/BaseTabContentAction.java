/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.web;

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
    protected Map<String, Object> session;

    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }
    private String errorMessage;

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
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] input()");
        }
    }

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
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] execute()");
        }
    }

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
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] add()");
        }
    }

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
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] edit()");
        }
    }

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
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] delete()");
        }
    }

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
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] title()");
        }
    }

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
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] buttons()");
        }
    }

    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    //public Map<String, Object> getSession() {
    //    return this.session;
    //}

    protected boolean isValidSession() {
        if (session==null) return false;
        String userId = (String) session.get(Constant.C_IDUSER);
        if (userId==null || "".equals(userId)) return false;
        return true;
    }

    protected String logout() {

        return ActionSupport.NONE;
    }

    protected String getBdsmHost() {
        return servletContext.getInitParameter("bdsmHost");
    }

    protected String getTokenKey() {
        return servletContext.getInitParameter("tokenKey");
    }

    protected String getTzToken() {
        return servletContext.getInitParameter("tzToken");
    }

    public String doInput() {
        return ActionSupport.INPUT;
    }

    public abstract String exec();
    public abstract String doAdd();
    public abstract String doEdit();
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
