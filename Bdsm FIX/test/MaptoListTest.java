
import bdsm.scheduler.util.SchedulerUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class MaptoListTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Map context = new HashMap();
        context.put("codacct","testing");
        context.put("key3","testing1");
        context.put("key2","testing2");
        
        String query = "SELECT * from ch_acct_mast a where a.cod_acct_no = {codacct}";
        System.out.println(query);
        Object[] arrayVal = context.entrySet().toArray();
        System.out.println(arrayVal.length);
        for (Object arrayVal1 : arrayVal) {
            System.out.println(arrayVal1);
            List keyOne = SchedulerUtil.getParameter(arrayVal1.toString(), "=");
            if(!keyOne.isEmpty()){
                try {
                    String replace = "\\{" + keyOne.get(0) + "\\}";
                    query = query.replaceAll(replace, keyOne.get(1).toString());
                } catch (Exception e) {
                   System.out.println(e);
                }
            }    
        }
        System.out.println(query);
    }
}
