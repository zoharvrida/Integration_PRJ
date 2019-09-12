/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util.test;

import bdsm.util.SchedulerUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author bdsm
 */
public class DatesTest {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    
    public static void main(String[] args) {
     
        String dateString = "10-Jan-1900";
        Date dates = new Date();
            try {
                dates = dateFormat.parse(dateString);
			} catch (ParseException parseException) {
                System.out.printf("DATEPARSE FAILED :" + parseException, parseException);
                dates = SchedulerUtil.getTime();
            }
        System.out.println(" Date: " +dates);
    }
}
