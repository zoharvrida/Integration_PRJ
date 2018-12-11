package bdsm.web.login;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

/**
 * 
 * @author bdsm
 */
public class LogoutAction extends ActionSupport implements SessionAware {
    Logger logger = Logger.getLogger(LogoutAction.class);
    private Map<String, Object> session;

    /**
     * 
     * @return
     */
    @Override
    public String execute() {
        logger.info("[Begin] execute()");
        try {
            ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
        } catch (IllegalStateException e) {
            logger.fatal(e, e);
        } catch (Throwable e) {
            logger.fatal(e, e);
        } finally {
            logger.info("[ End ] execute()");
        }
        return ActionSupport.SUCCESS;
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
}
