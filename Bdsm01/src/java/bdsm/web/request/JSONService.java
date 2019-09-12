/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.request;

/**
 *
 * @author bdsm
 */
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
public class JSONService extends JSONUtil {
    /**
     * 
     * @param toSerial
     * @return
     * @throws JSONException
     */
    public static String serialize(Object toSerial) throws JSONException{
        return ""; //JSONUtil.serialize(toSerial, CACHE_BEAN_INFO_DEFAULT);
    }
}
