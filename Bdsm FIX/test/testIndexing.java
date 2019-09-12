/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bdsm
 */
public class testIndexing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String delim = "|";
        String text = "asiuahdahduwhudh|uihduwihda";
        System.out.println(text.indexOf(delim));
        System.out.println(text.substring(0,text.indexOf(delim)));
    }
}
