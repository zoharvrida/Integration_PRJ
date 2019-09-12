
import java.util.ArrayList;
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
public class ListMapHash {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Map maptest = new HashMap<>();
        Map maptest1 = new HashMap<>();
        Map maptest2 = new HashMap<>();
        Map maptest3 = new HashMap<>();
        Map maptest4 = new HashMap<>();
        Map maptest5 = new HashMap<>();
        
        
        Map least = new HashMap();
        List<Map> obj = new ArrayList();
        least.put("A","VAL1");
        least.put("B","VAL2");
        
        obj.add(least);
        maptest.put("1",obj);
        maptest.put("2",obj);
        maptest.put("3",obj);
        
        maptest1.put("1A",obj);

        
        maptest2.put("1B",obj);

        
        maptest3.put("1C",obj);

        
        maptest4.put("1D",obj);

        
        maptest5.put("1E",obj);

        
        List<Map> inside = new ArrayList();
        inside.add(maptest);
        inside.add(maptest1);
        inside.add(maptest2);
        inside.add(maptest3);
        inside.add(maptest4);
        inside.add(maptest5);
       
        
        System.out.println("LISTALL : "+ inside);
        System.out.println("LISTINDEX : "+ inside.size());
        inside.remove(2);
        System.out.println("LISTALL AFTER : "+ inside);
        System.out.println("LISTINDEX AFTER : "+ inside.size());
        inside.remove(2);
        System.out.println("LISTALL AFTER1 : "+ inside);
        System.out.println("LISTINDEX AFTER1 : "+ inside.size());
        Map lastgit = (Map) maptest.get("1");
        
        System.out.println("OBJECT(1) :" + maptest.get("1"));
        System.out.println("OBJECT(1) :" + maptest.get("1"));
        System.out.println("OBJECT :" + maptest);
        List<List<Map>> test = new ArrayList();
        
    }
}
