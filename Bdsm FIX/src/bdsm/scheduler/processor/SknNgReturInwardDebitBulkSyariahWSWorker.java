package bdsm.scheduler.processor;


/**
 * @author v00019372
 */
public class SknNgReturInwardDebitBulkSyariahWSWorker extends SknNgReturInwardDebitBulkWSWorker {
    /**
     * 
     * @param context
     */
    public SknNgReturInwardDebitBulkSyariahWSWorker(java.util.Map<? extends String, ? extends Object> context) {
		super(context);
		
		this.BIC = bdsm.service.SknNgService.BIC_SYARIAH;
	}
	
}
