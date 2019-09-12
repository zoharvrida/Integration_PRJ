/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.exception;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class SkipProcessException extends Exception {
    private boolean deleteSourceData;

    public SkipProcessException(String ex){
        super(ex);
        this.deleteSourceData = true;
    }
    public SkipProcessException(Throwable ex){
        super(ex);
        this.deleteSourceData = true;
    }
	public SkipProcessException(Throwable ex, String check){
        super(ex);
    }
}
