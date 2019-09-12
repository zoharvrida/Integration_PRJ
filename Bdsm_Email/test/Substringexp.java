
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class Substringexp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String ldString = "01020100310301093109310039103910391031001";
        List values = new ArrayList();
        System.out.println(ldString);
        System.out.println(ldString.substring(0,10));
        System.out.println(ldString.substring(10,15));
        System.out.println(ldString);
    }
}
