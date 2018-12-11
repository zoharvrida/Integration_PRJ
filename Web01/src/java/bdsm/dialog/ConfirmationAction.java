/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.apache.struts2.util.ServletContextAware;

/**
 *
 * @author v00019722
 */
public class ConfirmationAction extends ActionSupport implements ServletContextAware  {

    private ServletContext servletContext; 
    private static Logger logger = Logger.getLogger(ConfirmationAction.class);
    private int process = 0;
    private String errorMessage = "";
    
    /**
     * 
     * @return
     */
    @Override
    public String execute() {
        logger.info("[Begin] execute()");
        try {
            setProcess(1);
            setErrorMessage("");
            
            return ActionSupport.SUCCESS;
        } catch (Throwable ex) {
            logger.fatal(ex, ex);
            setErrorMessage(ex.getMessage());
            addActionError(getErrorMessage());
            return ActionSupport.INPUT;
        } finally {
            logger.info("[ End ] execute()");
        }
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
        return getServletContext().getInitParameter("bdsmHost");
    }

    /**
     * 
     * @return
     */
    protected String getTokenKey() {
        return getServletContext().getInitParameter("tokenKey");
    }

    /**
     * 
     * @return
     */
    protected String getTzToken() {
        return getServletContext().getInitParameter("tzToken");
    }

    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @return the process
     */
    public int getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(int process) {
        this.process = process;
    }

    /**
     * @return the errorMessage
     */
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
