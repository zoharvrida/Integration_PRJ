/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog;

import bdsm.web.Constant;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.apache.log4j.Logger;

/**
 *
 * @author user
 */
public class DialogAction extends ActionSupport implements SessionAware, ServletContextAware {
    private String dialog;
    private ServletContext servletContext;
    /**
     * 
     */
    protected Map<String, Object> session;
    private static Logger logger = Logger.getLogger(DialogAction.class);

    /**
     * 
     * @return
     */
    @Override
    public String execute() {
        if (isValidSession()) {
            return dialog;            
        } else {
            return "none";
        }
    }

    /**
     * @return the dialog
     */
    public String getDialog() {
        return dialog;
    }

    /**
     * @param dialog the dialog to set
     */
    public void setDialog(String dialog) {
        this.dialog = dialog;
    }
    
    /**
     * 
     * @param servletContext
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
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
    protected boolean isValidSession() {
        try {
            if (session == null) {
                logger.info("SESSION : " + session);
                return false;
            }            
            
            String userId = (String) session.get(Constant.C_IDUSER);
            if (userId == null || "".equals(userId)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.info("SCREEN :" + e,e);
            return true;
        }
    }
}    
