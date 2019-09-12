
import java.util.HashMap;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class long_Convert {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Object KODE_POS = "5";
        Map<String,Object> map = new HashMap();
        map.put("KODE_POS",KODE_POS);
        Long l = (Long) map.get("KODE_POS");
        if (l != null) {
            System.out.println("not null" + l);
        }
        System.out.println(l);
    }
}