package bdsm.rpt.web;

import bdsm.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

public class HttpSessionListener implements javax.servlet.http.HttpSessionListener {
    
    private static Map<String, HttpSession> sessions;
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    
    static {
        sessions = new HashMap<String, HttpSession>();
    }
    
    public static HttpSession getSession(String id) {
        HttpSession ses = null;
        try {
            synchronized(HttpSessionListener.class) {
                ses = sessions.get(id);
            }
        } finally {
            return ses;
        }
    }

    private static HttpSession getRemoteSession(String url, String id) {
        HttpSession ses = null;
        try {
            Map<String, String> params = new HashMap<String, String>();
            String result = HttpUtil.request(url, params);
            Map resultMap = (Map) JSONUtil.deserialize(result);
            Map modelMap = (Map) resultMap.get("session");
            String modelJson = gson.toJson(modelMap);
            ses = (HttpSession) gson.fromJson(modelJson, HttpSession.class);
        } catch (JSONException ex) {
            Logger.getLogger(HttpSessionListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return ses;
        }
    }

    public void sessionCreated(HttpSessionEvent se) {
        HttpSession ses = se.getSession();
        String sesId = ses.getId();
        sessions.put(sesId, ses);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession ses = se.getSession();
        String sesId = ses.getId();
        sessions.remove(sesId);
    }
 
}
