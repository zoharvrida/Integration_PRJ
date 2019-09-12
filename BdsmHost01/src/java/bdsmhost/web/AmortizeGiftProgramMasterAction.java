package bdsmhost.web;


import static bdsmhost.web.AmortizeGiftProgramDetailAction.C_SESSION_PROGRAM_DETAIL_LIST;
import static bdsmhost.web.AmortizeGiftProgramDetailAction.C_SESSION_PROGRAM_DETAIL_ADDED_LIST;
import static bdsmhost.web.AmortizeGiftProgramDetailAction.C_SESSION_PROGRAM_DETAIL_DELETED_LIST;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import bdsm.fcr.model.ChProdMast;
import bdsm.model.AmortizeProgramDetail;
import bdsm.model.AmortizeProgramMaster;
import bdsm.model.AmortizeProgramMasterPK;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.AmortizeProgramDetailDAO;
import bdsmhost.dao.AmortizeProgramMasterDAO;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class AmortizeGiftProgramMasterAction extends BaseHostAction 
		implements com.opensymphony.xwork2.ModelDriven<AmortizeProgramMaster>, SessionAware, ApplicationAware {
	private AmortizeProgramMasterDAO programMasterDAO;
	private AmortizeProgramDetailDAO programDetailDAO;
	private AmortizeProgramMaster amortizeProgramMaster, amortizeProgramMasterDB;
	private Map<String, Object> application;
	private Map<String, Object> session;
	private List modelList;
	private Map<String, String> modelMap;
	private String mode;
	private String term;
	private String webSessionId;

	
    /**
     * 
     */
    public AmortizeGiftProgramMasterAction() {
		this.amortizeProgramMaster = new AmortizeProgramMaster();
		this.amortizeProgramMaster.setCompositeId(new AmortizeProgramMasterPK());
		this.modelList = new java.util.ArrayList();
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
	@SuppressWarnings("unchecked")
	public String doList() {
		if ("1".equals(this.mode)) { // mode add
			this.modelList = new AmortizeProgramMasterDAO(this.getHSession()).
					listProductNotInAmortizeProductMaster(this.getModel().getCompositeId().getGiftCode(), this.term);
			
			this.modelMap = new java.util.LinkedHashMap<String, String>();
			for (ChProdMast a : ((List<ChProdMast>) this.modelList)) {
				this.modelMap.put(a.getCompositeId().getCodProd().toString(), a.getCompositeId().getCodProd() + " - " + a.getNamProduct());
			}
			
			this.modelList.clear();
		}
		else {
			if (this.application.get(this.webSessionId) == null)
				this.application.put(this.webSessionId, this.session);
			
			Map<String, Object> session = (Map<String, Object>) this.application.get(this.webSessionId);
			session.put(C_SESSION_PROGRAM_DETAIL_LIST, null);
			session.put(C_SESSION_PROGRAM_DETAIL_ADDED_LIST, null);
			session.put(C_SESSION_PROGRAM_DETAIL_DELETED_LIST, null);
			
			this.modelList = new AmortizeProgramMasterDAO(this.getHSession())
				.listProductMasterAndProductByGiftCode("%" + this.amortizeProgramMaster.getCompositeId().getGiftCode() + "%");
		}
		
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
	public String doInsert() {
		this.programMasterDAO = new AmortizeProgramMasterDAO(this.getHSession());
		this.getModel().getCompositeId().setGiftCode(this.getModel().getCompositeId().getGiftCode().toUpperCase()); // set upper case
		this.programMasterDAO.insert(this.getModel());
		
		return SUCCESS;
	}

	
    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doUpdate() {
		this.programMasterDAO = new AmortizeProgramMasterDAO(this.getHSession());
		this.programDetailDAO = new AmortizeProgramDetailDAO(this.getHSession());
		this.amortizeProgramMasterDB = this.programMasterDAO.get(this.amortizeProgramMaster.getCompositeId());
		
		try {
			/* Update Program Master */
			if (!this.amortizeProgramMaster.equals(this.amortizeProgramMasterDB)) {
				this.amortizeProgramMaster.setIdCreatedBy(this.amortizeProgramMasterDB.getIdCreatedBy());
				this.amortizeProgramMaster.setIdCreatedSpv(this.amortizeProgramMasterDB.getIdCreatedSpv());
				this.amortizeProgramMaster.setDtmCreated(this.amortizeProgramMasterDB.getDtmCreated());
				PropertyUtils.copyProperties(this.amortizeProgramMasterDB, this.amortizeProgramMaster);
				this.amortizeProgramMasterDB.setIdUpdatedBy(this.amortizeProgramMaster.getIdMaintainedBy());
				this.amortizeProgramMasterDB.setIdUpdatedSpv(this.amortizeProgramMaster.getIdMaintainedSpv());
				this.amortizeProgramMasterDB.setDtmUpdated(SchedulerUtil.getTime());
			}
			else
				; // no change
			
			Map<String, Object> session = (Map<String, Object>) this.application.get(this.webSessionId);
			
			/* Update Program Detail */
			List<AmortizeProgramDetail> detailsAdd = (List<AmortizeProgramDetail>) session.get(C_SESSION_PROGRAM_DETAIL_ADDED_LIST);
			List<AmortizeProgramDetail> detailsDelete = (List<AmortizeProgramDetail>) session.get(C_SESSION_PROGRAM_DETAIL_DELETED_LIST);
			Integer id = this.programDetailDAO.generateId();
			AmortizeProgramDetail detail;
			
			for (int i=(detailsAdd.size())-1; i>=0; i--) {
				detail = detailsAdd.get(i);
				detail.setId(id++);
				detail.setIdMaintainedSpv(this.amortizeProgramMaster.getIdMaintainedSpv());
				this.programDetailDAO.insert(detail);
			}
			
			for (int i=0; i<detailsDelete.size(); i++) {
				detail = detailsDelete.get(i);
				this.programDetailDAO.copyToHistory(detail.getId(), this.amortizeProgramMaster.getIdMaintainedBy(), 
						this.amortizeProgramMaster.getIdMaintainedSpv());
				this.programDetailDAO.delete(detail);
			}
			
			
			/* Clear data and session */
			detailsAdd.clear();
			session.remove(C_SESSION_PROGRAM_DETAIL_LIST);
			session.remove(C_SESSION_PROGRAM_DETAIL_ADDED_LIST);
			session.remove(C_SESSION_PROGRAM_DETAIL_DELETED_LIST);
			
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
			return ERROR;
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}
	
	
    /**
     * 
     * @return
     */
    public List getModelList() {
		return this.modelList;
	}
    /**
     * 
     * @param modelList
     */
    public void setModelList(List modelList) {
		this.modelList = modelList;
	}
	
    /**
     * 
     * @return
     */
    public Map<String, String> getModelMap() {
		return this.modelMap;
	}
    /**
     * 
     * @param modelMap
     */
    public void setModelMap(Map<String, String> modelMap) {
		this.modelMap = modelMap;
	}
	
    /**
     * 
     * @return
     */
    public String getMode() {
		return this.mode;
	}
    /**
     * 
     * @param mode
     */
    public void setMode(String mode) {
		this.mode = mode;
	}
	
    /**
     * 
     * @return
     */
    public String getTerm() {
		return this.term;
	}
    /**
     * 
     * @param term
     */
    public void setTerm(String term) {
		this.term = term;
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
	public AmortizeProgramMaster getModel() {
		return this.amortizeProgramMaster;
	}

    /**
     * 
     * @param session
     */
    @Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
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
