package bdsmhost.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import bdsm.model.AmortizeAccount;
import bdsm.model.AmortizeProgramDetail;
import bdsmhost.dao.AmortizeAccountDAO;
import bdsmhost.dao.AmortizeProgramDetailDAO;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeGiftProgramDetailAction extends BaseHostAction 
		implements com.opensymphony.xwork2.ModelDriven<AmortizeProgramDetail>, org.apache.struts2.interceptor.ApplicationAware {
	static final String C_SESSION_PROGRAM_DETAIL_LIST = "session_program_detail_list";
	static final String C_SESSION_PROGRAM_DETAIL_ADDED_LIST = "session_program_detail_added_list";
	static final String C_SESSION_PROGRAM_DETAIL_DELETED_LIST = "session_program_detail_deleted_list";
	
	private AmortizeProgramDetailDAO programDetailDAO;
	private AmortizeAccountDAO accountDAO;
	private Map<String, Object> application;
	private Map<String, Object> session;
	private AmortizeProgramDetail amortizeProgramDetail;
	private List<AmortizeProgramDetail> modelList;
	private String webSessionId;
	
	
    /**
     * 
     */
    public AmortizeGiftProgramDetailAction() {
		this.amortizeProgramDetail = new AmortizeProgramDetail();
	}
	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings({ "unchecked" })
	public String doList() {
		this.session = (Map<String, Object>) this.application.get(this.webSessionId);
		
		List<AmortizeProgramDetail> existing = (List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_LIST); 
		if (existing == null) {
			this.programDetailDAO = new AmortizeProgramDetailDAO(this.getHSession());
			existing = this.programDetailDAO.listByGiftCodeAndProductCode(this.amortizeProgramDetail.getGiftCode(), 
							this.amortizeProgramDetail.getProductCode());
			
			this.session.put(C_SESSION_PROGRAM_DETAIL_LIST, existing);
			this.session.put(C_SESSION_PROGRAM_DETAIL_ADDED_LIST, new ArrayList<AmortizeProgramDetail>());
			this.session.put(C_SESSION_PROGRAM_DETAIL_DELETED_LIST, new ArrayList<AmortizeProgramDetail>());
		}
		
		this.modelList = new ArrayList<AmortizeProgramDetail>();
		this.modelList.addAll((List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_ADDED_LIST));
		this.modelList.addAll((List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_LIST));
		
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

	
    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doInsert() {
		this.session = (Map<String, Object>) this.application.get(this.webSessionId);
		this.getLogger().info("AmortizeProgramDetail: " + this.amortizeProgramDetail);
		
		this.modelList = new ArrayList<AmortizeProgramDetail>();
		this.modelList.addAll((List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_ADDED_LIST));
		this.modelList.addAll((List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_LIST));
		
		AmortizeProgramDetail pd;
		boolean exist = false;
		
		for (int i=0; i<this.modelList.size(); i++) {
			pd = this.modelList.get(i);
			if (
					pd.getGiftCode().equals(this.amortizeProgramDetail.getGiftCode()) 
					&& pd.getProductCode().equals(this.amortizeProgramDetail.getProductCode())
					&& DateUtils.isSameDay(pd.getEffectiveDate(), this.amortizeProgramDetail.getEffectiveDate())
					&& pd.getTerm().equals(this.amortizeProgramDetail.getTerm())
				) {
					exist = true;
					break;
			}
		}
		
		if (!exist) {
			this.modelList = (List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_ADDED_LIST);
			
			Integer generatedId = this.getTemporaryId(this.modelList);
			this.amortizeProgramDetail.setId(generatedId);
			this.modelList.add(0, this.amortizeProgramDetail); // Added in the first element position
		}
		
		this.modelList = null;
		
		return (exist? ERROR: SUCCESS);
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doDelete() {
		this.session = (Map<String, Object>) this.application.get(this.webSessionId);
		
		if (this.amortizeProgramDetail.getId() > -1) { // from original Database
			this.accountDAO = new AmortizeAccountDAO(this.getHSession());
			List<AmortizeAccount> openedAccount = 
					this.accountDAO.getByProgramDetailIdAndStatus(this.amortizeProgramDetail.getId(), AmortizeAccount.STATUS_OPENED);
			
			if (openedAccount.size() == 0) {
				this.modelList = (List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_LIST);
				List<AmortizeProgramDetail> deletedList = (List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_DELETED_LIST);
				
				for (int i=0; i<this.modelList.size(); i++)
					if (this.modelList.get(i).getId().equals(this.amortizeProgramDetail.getId())) {
						deletedList.add(this.modelList.remove(i)); // Move to deleted list
						break;
					}
			}
			else {
				return ERROR;
			}
		}
		else { // from temporary added
			this.modelList = (List<AmortizeProgramDetail>) this.session.get(C_SESSION_PROGRAM_DETAIL_ADDED_LIST);
			
			for (int i=0; i<this.modelList.size(); i++)
				if (this.modelList.get(i).getId().equals(this.amortizeProgramDetail.getId())) {
					this.modelList.remove(i);
					break;
				}
		}
		
		this.modelList = null;
		return SUCCESS;
	}
	
	private Integer getTemporaryId(List<AmortizeProgramDetail> list) {
		// For Added row, the generated temporary id always negative
		Integer retValue = -1;
		if ((list != null) && (list.size() > 0)) {
			AmortizeProgramDetail elem = (AmortizeProgramDetail) list.get(0);
			if (elem.getId() < 1)
				retValue = elem.getId() - 1;
		}
		
		return retValue;
	}
	
	
    /**
     * 
     * @return
     */
    public List<AmortizeProgramDetail> getModelList() {
		return this.modelList;
	}
    /**
     * 
     * @param modelList
     */
    public void setModelList(List<AmortizeProgramDetail> modelList) {
		this.modelList = modelList;
	}
	
    /**
     * 
     * @return
     */
    public String getWebSessionId() {
		return this.webSessionId;
	}
    /**
     * 
     * @param webSessionId
     */
    public void setWebSessionId(String webSessionId) {
		this.webSessionId = webSessionId;
	}
	

    /**
     * 
     * @return
     */
    @Override
	public AmortizeProgramDetail getModel() {
		return this.amortizeProgramDetail;
	}

    /**
     * 
     * @param application
     */
    @Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}




}
