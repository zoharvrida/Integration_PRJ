package bdsm.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;

/**
 * 
 * @author bdsm
 */
public class TimerInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param invocation
     * @return
     * @throws Exception
     */
    public String intercept(ActionInvocation invocation) throws Exception {

        String className = invocation.getAction().getClass().getName();
        
        long startTime = System.currentTimeMillis();
        System.out.println("[Begin] " + className);
        
        ActionContext context = (ActionContext) invocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        String result = invocation.invoke();

        long endTime = System.currentTimeMillis();
        System.out.println("[ End ] " + className + " (result: " + result + ", " + (endTime - startTime) + " ms)");

        return result;
    }

    /**
     * 
     */
    public void destroy() {
        System.out.println("TimerInterceptor destroyed.");
    }

    /**
     * 
     */
    public void init() {
        System.out.println("TimerInterceptor initialized.");
    }
}