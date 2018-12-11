package bdsm.scheduler.exception;

import bdsm.scheduler.ScheduleDefinition;
import microsoft.exchange.webservices.data.ExchangeService;

/**
 * 
 * @author bdsm
 */
public class FIXException extends Exception{

    private boolean deleteSourceData;
    private int idSchedulerXtractReporter;

    /**
     * 
     * @param ex
     */
    public FIXException(String ex){
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }
    
    /**
     * 
     * @param srv
     * @param ex
     */
    public FIXException(ExchangeService srv,String ex){
        super(ex);
        // do something on Exception;
        
    }

    /**
     * 
     * @param ex
     * @param deleteSource
     */
    public FIXException(String ex, boolean deleteSource) {
        super(ex);
        this.deleteSourceData = deleteSource;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }
    
    /**
     * 
     * @param ex
     * @param idScheduler
     */
    public FIXException(String ex, int idScheduler){
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = idScheduler;
    }
    
    /**
     * 
     * @param ex
     * @param deleteSource
     * @param idScheduler
     */
    public FIXException(String ex, boolean deleteSource, int idScheduler) {
        super(ex);
        this.deleteSourceData = deleteSource;
        this.idSchedulerXtractReporter = idScheduler;        
    }
    
    /**
     * 
     * @param ex
     */
    public FIXException(Throwable ex){
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }

    /**
     * 
     * @param ex
     * @param deleteSource
     */
    public FIXException(Throwable ex, boolean deleteSource) {
        super(ex);
        this.deleteSourceData = deleteSource;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }
    
    /**
     * 
     * @param ex
     * @param idScheduler
     */
    public FIXException(Throwable ex, int idScheduler) {
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = idScheduler;
    }    
    
    /**
     * 
     * @param ex
     * @param deleteSource
     * @param idScheduler
     */
    public FIXException(Throwable ex, boolean deleteSource, int idScheduler) {
        super(ex);
        this.deleteSourceData = deleteSource;
        this.idSchedulerXtractReporter = idScheduler;
    }

    /**
     * @return the deleteSourceData
     */
    public boolean isDeleteSourceData() {
        return deleteSourceData;
    }

    /**
     * @param deleteSourceData the deleteSourceData to set
     */
    public void setDeleteSourceData(boolean deleteSourceData) {
        this.deleteSourceData = deleteSourceData;
    }

    /**
     * @return the idSchedulerXtractReporter
     */
    public int getIdSchedulerXtractReporter() {
        return idSchedulerXtractReporter;
    }

    /**
     * @param idSchedulerXtractReporter the idSchedulerXtractReporter to set
     */
    public void setIdSchedulerXtractReporter(int idSchedulerXtractReporter) {
        this.idSchedulerXtractReporter = idSchedulerXtractReporter;
    }
}
