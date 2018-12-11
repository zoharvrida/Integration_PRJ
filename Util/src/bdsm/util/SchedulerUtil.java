/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author bdsm
 */
public class SchedulerUtil {
	
    public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String generateUUID2() {
		return generateUUID().replace("-", "").toUpperCase();
	}

	public static Timestamp getTime() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String getDate(String format) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.format(new Timestamp(calendar.getTimeInMillis()));
	}
	public static Timestamp getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.add(Calendar.DATE, -1);
		
		return new Timestamp(calendar.getTimeInMillis());
	}
	    public static List getParameter(String props, String delimiter){
        List StringParameter = new ArrayList();
        StringTokenizer token = new StringTokenizer(props.toString(),delimiter);
        
        while(token.hasMoreTokens()){
            StringParameter.add(token.nextToken());
        }
        return StringParameter;
    }
}
