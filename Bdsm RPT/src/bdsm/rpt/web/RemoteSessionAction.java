package bdsm.rpt.web;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

public class RemoteSessionAction extends ActionSupport {
    private String sessionId;
    private HttpSession session;
    
    @Override
    public final String execute() {
        getLogger().info("[Begin] execute()");
        try {
            session = HttpSessionListener.getSession(sessionId);
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] execute()");
            return ActionSupport.SUCCESS;
        }
    }
    
    @JSON(serialize=false)
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the session
     */
    public HttpSession getSession() {
        return session;
    }
}
