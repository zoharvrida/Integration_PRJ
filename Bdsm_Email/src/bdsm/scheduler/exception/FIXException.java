package bdsm.scheduler.exception;

import bdsm.scheduler.ScheduleDefinition;
import microsoft.exchange.webservices.data.ExchangeService;

public class FIXException extends Exception{

    private boolean deleteSourceData;
    private int idSchedulerXtractReporter;

    public FIXException(String ex){
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }
    
    public FIXException(ExchangeService srv,String ex){
        super(ex);
        // do something on Exception;
        
    }

    public FIXException(String ex, boolean deleteSource) {
        super(ex);
        this.deleteSourceData = deleteSource;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }
    
    public FIXException(String ex, int idScheduler){
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = idScheduler;
    }
    
    public FIXException(String ex, boolean deleteSource, int idScheduler) {
        super(ex);
        this.deleteSourceData = deleteSource;
        this.idSchedulerXtractReporter = idScheduler;        
    }
    
    public FIXException(Throwable ex){
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }

    public FIXException(Throwable ex, boolean deleteSource) {
        super(ex);
        this.deleteSourceData = deleteSource;
        this.idSchedulerXtractReporter = ScheduleDefinition.emailOnly;
    }
    
    public FIXException(Throwable ex, int idScheduler) {
        super(ex);
        this.deleteSourceData = true;
        this.idSchedulerXtractReporter = idScheduler;
    }    
    
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
