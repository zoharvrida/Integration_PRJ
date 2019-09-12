
import bdsm.scheduler.dao.CiChufCASAHeaderDao;
//import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import java.sql.Date;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class dateParser {
    private static Session session;

    /**
     * @param args the command line arguments
     */
    
        
    
    public static void main(String[] args) {
        
        CiChufCASAHeaderDao dao = new CiChufCASAHeaderDao(session);
        
        // TODO code application logic here
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateNov = "2015-10-21 00:00:00.0";
        
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      //  String dateNov = "2015-10-21 00:00:00.0";
        try {
            String get = sdf.format(Date.valueOf(dateNov));
            //String get = sdf.format(Date.valueOf(dateNov));
            System.out.println("DATE :" + get);
        } catch (Exception parseException) {
            System.out.println("EX :" + parseException);
        }
    }
}
