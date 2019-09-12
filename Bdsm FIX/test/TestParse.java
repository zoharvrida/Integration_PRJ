
import java.math.BigDecimal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class TestParse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Integer numVal = Integer.parseInt("20170317000065266426");
//        System.out.println(numVal);
        
        String bigVal = "20170317000065266426";
        BigDecimal a =new BigDecimal(bigVal);
        System.out.println(a);
    }
}
