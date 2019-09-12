/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

/**
 *
 * @author v00013493
 */
public class BdsmException extends RuntimeException {
    
    /**
     * 
     * @param root
     */
    public BdsmException(Throwable root) {
        super(root);
    }

    /**
     * 
     * @param string
     * @param root
     */
    public BdsmException(String string, Throwable root) {
        super(string, root);
    }

    /**
     * 
     * @param s
     */
    public BdsmException(String s) {
        super(s);
    }
}
