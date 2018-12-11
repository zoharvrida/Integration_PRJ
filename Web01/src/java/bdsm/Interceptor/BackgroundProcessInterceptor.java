package bdsm.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.BackgroundProcess;

/**
 * 
 * @author bdsm
 */
public class BackgroundProcessInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;
    private Logger LOG = Logger.getLogger(this.getClass().getName());

    /**
     * 
     * @param invocation
     * @return
     * @throws Exception
     */
    public String intercept(ActionInvocation invocation) throws Exception {
		LOG.info("[BEGIN] intercept()");
		try {
	        String className = invocation.getAction().getClass().getName();
	        String name = invocation.getProxy().getActionName();
			HttpSession httpSession = ServletActionContext.getRequest().getSession();
			LOG.info("[DEBUG] intercept() " + className + ", " + name);
	        if (httpSession==null) return "none";
	        synchronized (httpSession) {
		        BackgroundProcess bp = (BackgroundProcess)httpSession.getAttribute("__background_" + name);
		        if (bp == null) {
		        	bp = new BackgroundProcess(name + "_bgthread", invocation, 5);
		        	httpSession.setAttribute("__background_" + name, bp);
		        }
		        for (int i=0; i<20; i++) {
		        	if (bp.isDone()) break;
		        	try { Thread.sleep(500); } catch(Exception e) {}
		        }
		        if (bp.isDone()) {
		        	httpSession.removeAttribute("__background_" + name);
		        	invocation.getStack().push(bp.getAction());
					if (bp.getException() != null) {
						throw bp.getException();
					}
					return bp.getResult();
		        } else {
		        	invocation.getStack().push(bp.getAction());
		        	return "wait";
		        }
		    }
	    } finally {
			LOG.info("[  END] intercept()");
	    }
    }

    /**
     * 
     */
    public void destroy() {
        LOG.info("BackgroundProcessInterceptor destroyed.");
    }

    /**
     * 
     */
    public void init() {
        LOG.info("BackgroundProcessInterceptor initialized.");
    }
}