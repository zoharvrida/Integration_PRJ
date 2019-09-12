/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.host;

import bdsm.util.BdsmUtil;
import bdsm.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author bdsm
 */
public abstract class BaseHostAction extends ActionSupport implements ServletContextAware {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    private ServletContext servletContext = null;
    private Session hsession = null;
    private String jsonStatus;
    private String errorCode;
    private String namMenu;

    @JSON(serialize=true)
    public String getJsonStatus() {
        return jsonStatus;
    }

    public void setJsonStatus(String jsonStatus) {
        this.jsonStatus = jsonStatus;
    }

    @JSON(serialize=true)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @JSON(serialize=true)
    public String getNamMenu() {
        return namMenu;
    }

    public void setNamMenu(String namMenu) {
        this.namMenu = namMenu;
    }

    @JSON(serialize=false)
    protected Session getHSession() {
        return hsession;
    }

    @JSON(serialize=false)
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    private String token = null;

    @Override
    public final String execute() {
        getLogger().info("[Begin] execute()");

        getLogger().debug("token: " + token);
        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            try {
                if (isValidToken(token)) {
                    setJsonStatus(exec());
                    return ActionSupport.SUCCESS;
                }
            } catch (Throwable e) {
                getLogger().fatal(e, e);
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().debug("jsonStatus: " + jsonStatus);
            getLogger().debug("errorCode : " + errorCode);
            getLogger().info("[ End ] execute()");
        }

        return ActionSupport.SUCCESS;
    }

    public final String list() {
        getLogger().info("[Begin] list()");

        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            if (isValidToken(token)) {
                try {
                    hsession = HibernateUtil.getSession();
                    setJsonStatus(doList());
                    return ActionSupport.SUCCESS;
                } catch (Throwable e) {
                        getLogger().fatal(e, e);
                } finally {
                    HibernateUtil.closeSession(hsession);
                }
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().info("[ End ] list()");
        }

        return ActionSupport.SUCCESS;
    }

    @JSON(serialize=false)
    public final String get() {
        getLogger().info("[Begin] get()");

        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            if (isValidToken(token)) {
                try {
                    hsession = HibernateUtil.getSession();
                    setJsonStatus(doGet());
                    return ActionSupport.SUCCESS;
                } catch (Throwable e) {
                        getLogger().fatal(e, e);
                } finally {
                    HibernateUtil.closeSession(hsession);
                }
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().info("[ End ] get()");
        }

        return ActionSupport.SUCCESS;
    }

    public final String save() {
        getLogger().info("[Begin] save()");

        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            if (isValidToken(token)) {
                Transaction tx = null;
                try {
                    hsession = HibernateUtil.getSession();
                    tx = hsession.beginTransaction();
                    String result = doSave();
                    tx.commit();
                    setJsonStatus(result);
                    return ActionSupport.SUCCESS;
                } catch (HibernateException he) {
                    Throwable e = he.getCause();
                    if (e != null && e instanceof SQLException) {
                        SQLException se = (SQLException)e;
                        setErrorCode(String.format("error.ora-%05d", se.getErrorCode()));
                        getLogger().error("[" + String.format("error.ora-%05d", se.getErrorCode()) + "] " + he.getMessage(), he);
                    }
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } catch (Throwable e) {
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } finally {
                    HibernateUtil.closeSession(hsession);
                }
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().info("[ End ] save()");
        }

        return ActionSupport.SUCCESS;
    }

    public final String insert() {
        getLogger().info("[Begin] insert()");

        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            if (isValidToken(token)) {
                Transaction tx = null;
                try {
                    hsession = HibernateUtil.getSession();
                    tx = hsession.beginTransaction();
                    String result = doInsert();
                    tx.commit();
                    setJsonStatus(result);
                    return ActionSupport.SUCCESS;
                } catch (HibernateException he) {
                    Throwable e = he.getCause();
                    if (e != null && e instanceof SQLException) {
                        SQLException se = (SQLException)e;
                        setErrorCode(String.format("error.ora-%05d", se.getErrorCode()));
                        getLogger().error("[" + String.format("error.ora-%05d", se.getErrorCode()) + "] " + he.getMessage(), he);
                    }
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } catch (Throwable e) {
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } finally {
                    HibernateUtil.closeSession(hsession);
                }
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().info("[ End ] insert()");
        }

        return ActionSupport.SUCCESS;
    }

    public final String update() {
        getLogger().info("[Begin] update()");

        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            if (isValidToken(token)) {
                Transaction tx = null;
                try {
                    hsession = HibernateUtil.getSession();
                    tx = hsession.beginTransaction();
                    String result = doUpdate();
                    tx.commit();
                    setJsonStatus(result);
                    return ActionSupport.SUCCESS;
                } catch (HibernateException he) {
                    Throwable e = he.getCause();
                    if (e != null && e instanceof SQLException) {
                        SQLException se = (SQLException)e;
                        setErrorCode(String.format("error.ora-%05d", se.getErrorCode()));
                        getLogger().error("[" + String.format("error.ora-%05d", se.getErrorCode()) + "] " + he.getMessage(), he);
                    }
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } catch (Throwable e) {
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } finally {
                    HibernateUtil.closeSession(hsession);
                }
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().info("[ End ] update()");
        }

        return ActionSupport.SUCCESS;
    }

    public final String approve() {
        getLogger().info("[Begin] approve()");

        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            if (isValidToken(token)) {
                Transaction tx = null;
                try {
                    hsession = HibernateUtil.getSession();
                    tx = hsession.beginTransaction();
                    String result = doApprove();
                    tx.commit();
                    setJsonStatus(result);
                    return ActionSupport.SUCCESS;
                } catch (HibernateException he) {
                    Throwable e = he.getCause();
                    if (e != null && e instanceof SQLException) {
                        SQLException se = (SQLException)e;
                        setErrorCode(String.format("error.ora-%05d", se.getErrorCode()));
                        getLogger().error("[" + String.format("error.ora-%05d", se.getErrorCode()) + "] " + he.getMessage(), he);
                    }
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } catch (Throwable e) {
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } finally {
                    HibernateUtil.closeSession(hsession);
                }
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().info("[ End ] approve()");
        }

        return ActionSupport.SUCCESS;
    }
    
    public final String delete() {
        getLogger().info("[Begin] delete()");

        try {
            if ((token == null || "".equals(token)) && (!"0".equals(getTokenKey()))) {
                setJsonStatus(ActionSupport.ERROR);
                return ActionSupport.SUCCESS;
            }

            if (isValidToken(token)) {
                Transaction tx = null;
                try {
                    hsession = HibernateUtil.getSession();
                    tx = hsession.beginTransaction();
                    String result = doDelete();
                    tx.commit();
                    setJsonStatus(result);
                    return ActionSupport.SUCCESS;
                } catch (HibernateException he) {
                    Throwable e = he.getCause();
                    if (e != null && e instanceof SQLException) {
                        SQLException se = (SQLException)e;
                        setErrorCode(String.format("error.ora-%05d", se.getErrorCode()));
                        getLogger().error("[" + String.format("error.ora-%05d", se.getErrorCode()) + "] " + he.getMessage(), he);
                    }
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } catch (Throwable e) {
                    getLogger().fatal(e, e);
                    if (tx != null) tx.rollback();
                } finally {
                    HibernateUtil.closeSession(hsession);
                }
            }

            setJsonStatus(ActionSupport.ERROR);
        } finally {
            getLogger().info("[ End ] delete()");
        }

        return ActionSupport.SUCCESS;
    }

    public abstract String exec();
    public abstract String doList();
    public abstract String doGet();
    public abstract String doSave();
    public abstract String doInsert();
    public abstract String doUpdate();
    public abstract String doDelete();
    public String doApprove(){
        return SUCCESS;
    }

    /**
     * @return the token
     */
    @JSON(serialize=false)
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }


    private boolean isValidToken(String token) {
        if (token==null || "".equals(token)) return false;
        String tokenKey = getTokenKey();
        if ("0".equals(tokenKey) && "".equals(token)) {
        return true;
        } else {
            //Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(getTzToken()));
            Calendar cal = Calendar.getInstance();
            Date dt = BdsmUtil.parseToken(tokenKey, token);
            Date sysdt = cal.getTime();
            long ndt = Long.parseLong(sdf.format(dt));
            long nsysdt = Long.parseLong(sdf.format(sysdt));
            long diff = Math.abs(ndt-nsysdt);

            getLogger().debug("dt : " + sdf.format(dt));
            getLogger().debug("sysdt   : " + sdf.format(sysdt));
            getLogger().debug("timezone: " + cal.getTimeZone());
            return diff < 3L;
        }
    }
  
    protected String getBdsmHost() {
        return servletContext.getInitParameter("bdsmHost");
    }

    protected String getTokenKey() {
        return servletContext.getInitParameter("tokenKey");
    }

    protected String getadUser(){
        return servletContext.getInitParameter("adUser");
    }

    protected String getadPassword(){
        return BdsmUtil.dec(servletContext.getInitParameter("adPassword"));
    }

    protected String getadRootDN(){
        return servletContext.getInitParameter("adRootDN");
    }

    protected String getadServer(){
        return servletContext.getInitParameter("adServer");
    }

    protected String getadSearchBase(){
        return servletContext.getInitParameter("adSearchBase");
    }

    protected String getTzToken() {
        return servletContext.getInitParameter("tzToken");
    }

    public void setServletContext(ServletContext sc) {
        servletContext = sc;
    }

}
