package bdsm.scheduler.processor;


import bdsm.service.VAAutoDebitService;
import bdsm.service.VAService;


/**
 * 
 * @author bdsm
 */
public class VAAutoDebitWorker extends VAWorker {
	private static final String EMAIL_NEED_APPROVAL = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your approval for Auto Debit processing is required for the attached file to be processed further. <br/>"
			+ "<br/>" + "Please reply this email with:<br/>"
			+ "<b>Ok</b>, if you approve the file to be processed, or<br/>"
			+ "<b>Not ok</b>, if otherwise.<br/>" + "<br/>"
			+ "Thanks & regards,<br/>" + "- BDSM -";
	private static final String EMAIL_REJECTED = "Dear Sir/Madam,<br/>"
			+ "<br/>"
			+ "Your requested process Auto Debit has been Rejected by Supervisor. <br/>"
			+ "<br/>" + "Thanks & regards,<br/>" + "- BDSM -";
	
	
    /**
     * 
     * @param context
     */
    public VAAutoDebitWorker(java.util.Map<String, Object> context) {
		super(context);
	}
	
	
    /**
     * 
     * @return
     */
    @Override
	protected String getNeedApprovalMessage() {
		return EMAIL_NEED_APPROVAL;
	}
	
    /**
     * 
     * @return
     */
    @Override
	protected String getRejectedMessage() {
		return EMAIL_REJECTED;
	}
	
    /**
     * 
     * @return
     */
    @Override
	protected VAService getService() {
		return new VAAutoDebitService();
	}
	
}
