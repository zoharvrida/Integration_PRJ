/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author bdsm
 */
public class FileUtil {
    public static String getDateTimeFormatedString(String fileformat) {
        String[] a = fileformat.replaceAll("(\\{|\\})", ",").split(",");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(a[1]);
        Date date = new Date();
        return a[0] + sdf.format(date);
    }
}
