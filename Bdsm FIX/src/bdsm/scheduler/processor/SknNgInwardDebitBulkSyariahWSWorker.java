package bdsm.scheduler.processor;


/**
 * @author v00019372
 */
public class SknNgInwardDebitBulkSyariahWSWorker extends SknNgInwardDebitBulkWSWorker {
    /**
     * 
     * @param context
     */
    public SknNgInwardDebitBulkSyariahWSWorker(java.util.Map<? extends String, ? extends Object> context) {
		super(context);
		
		this.BIC = bdsm.service.SknNgService.BIC_SYARIAH;
	}	
}
