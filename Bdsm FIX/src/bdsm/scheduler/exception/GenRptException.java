/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.exception;

/**
 *
 * @author v00013493
 */
public class GenRptException extends RuntimeException {
    /**
     * 
     * @param root
     */
    public GenRptException(Throwable root) {
        super(root);
    }

    /**
     * 
     * @param string
     * @param root
     */
    public GenRptException(String string, Throwable root) {
        super(string, root);
    }

    /**
     * 
     * @param s
     */
    public GenRptException(String s) {
        super(s);
    }    
}
