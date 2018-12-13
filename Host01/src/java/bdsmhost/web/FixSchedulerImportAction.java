/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.model.FixEmailAccess;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixSchedulerPK;
import bdsm.scheduler.StatusDefinition;
import bdsmhost.dao.AuditlogDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public class FixSchedulerImportAction extends BaseHostAction {

	private static final long serialVersionUID = 5078264277068533593L;
	private List modelList;
	private List modelListS;
	private FixSchedulerImport model;
	private FixSchedulerImport modelImport;
	private FixEmailAccess modelG;
	private FixSchedulerPK fixSchedulerPK;
	private String oldVal;
	private ArrayList value = new ArrayList();
	private ArrayList oldValue = new ArrayList();
	private ArrayList fieldName = new ArrayList();
	private String checkPattern;
	private List<FixSchedulerImport> modelCheck;

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    public String doList() {
		FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());
		fixSchedulerPK = new FixSchedulerPK();
		fixSchedulerPK.setNamScheduler(getModel().getFixSchedulerPK().getNamScheduler());

		modelList = dao.list(fixSchedulerPK);

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    public String Stat() {
		FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());

		modelListS = dao.list(getModel().getFixSchedulerPK().getNamScheduler());

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    public String group() {
		FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());

		modelListS = dao.list(getModelG().getGrpId());

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		getLogger().info(checkPattern);
		FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());
		setModelCheck(dao.listbyPattern(checkPattern));
		modelList = new ArrayList();
		for(FixSchedulerImport impor : modelCheck){
			if (impor != null){
				modelList.add(impor);
			}
		}
		try {
			checkPattern = getModelCheck().get(0).getFileExtension();
			if(checkPattern == null){
				if(modelCheck == null){
					// UNregistered
					checkPattern = "";
				} else {
					checkPattern = StatusDefinition.multiEXT;
				}
			}
			else if (checkPattern.contains(";"))
				checkPattern = StatusDefinition.multiEXT;
		} catch (Exception e) {
			getLogger().info("PATTERN NULL :" + e,e);
			checkPattern = "";
		}
		getLogger().info(getModelCheck().get(0).getFileExtension());
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doUpdate() {
		getLogger().info(" Id template : " + getModelImport().getFixSchedulerPK().getIdTemplate());
		getLogger().info("Id Scheduler : " + getModelImport().getFixSchedulerPK().getIdScheduler());
		getLogger().info("Scheduler Name : " + getModelImport().getFixSchedulerPK().getNamScheduler());

		FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());
		AuditlogDao auditdao = new AuditlogDao(getHSession());

		FixSchedulerImport persistence = dao.get(getModelImport().getFixSchedulerPK());

		if (persistence != null) {

			oldValue.add(persistence.getFlgStatus());
			oldValue.add(persistence.getIdMaintainedBy());
			oldValue.add(persistence.getIdMaintainedSpv());
			value.add(getModelImport().getFlgStatus());
			value.add(getModelImport().getIdMaintainedBy());
			value.add(getModelImport().getIdMaintainedSpv());
			fieldName.add("FLGSTATUS");
			fieldName.add("IDUSER");
			fieldName.add("IDUSERSPV");

			persistence.setFlgStatus(getModelImport().getFlgStatus());
			getLogger().info("Flg Status = " + persistence.getFlgStatus());

			persistence.setIdMaintainedBy(getModelImport().getIdMaintainedBy());
			persistence.setIdMaintainedSpv(getModelImport().getIdMaintainedSpv());

			dao.update(persistence);

			//auditdao.insert(getModelImport().getIdMaintainedBy(),getModelImport().getIdMaintainedSpv(), 
			//        "FixSchedulerImport", getNamMenu(),"Edit", "Edit"); 
			getLogger().info(" Test hingga persistance Import");
			try {
				auditdao.runPackage(getNamMenu(),
						"FixSchedulerImport",
						getModelImport().getIdMaintainedBy(),
						getModelImport().getIdMaintainedSpv(),
						"Edit", "Edit", value, oldValue, fieldName);
			} catch (SQLException ex) {
				Logger.getLogger(FixSchedulerImportAction.class.getName()).log(Level.SEVERE, null, ex);
			}
			super.setErrorCode("success.6");
			return SUCCESS;
		} else {
			super.setErrorCode("error.4");
			return ERROR;
		}
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * @return the modelList
	 */
	public List getModelList() {
		return modelList;
	}

	private FixSchedulerImport getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(FixSchedulerImport model) {
		this.model = model;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List modelList) {
		this.modelList = modelList;
	}

	/**
	 * @return the modelListS
	 */
	public List getModelListS() {
		return modelListS;
	}

	/**
	 * @param modelListS the modelListS to set
	 */
	public void setModelListS(List modelListS) {
		this.modelListS = modelListS;
	}

	/**
	 * @return the modelG
	 */
	public FixEmailAccess getModelG() {
		return modelG;
	}

	/**
	 * @param modelG the modelG to set
	 */
	public void setModelG(FixEmailAccess modelG) {
		this.modelG = modelG;
	}

	/**
	 * @return the ModelImport
	 */
	public FixSchedulerImport getModelImport() {
		return modelImport;
	}

	/**
     * 
     * @param modelImport 
     */
	public void setModelImport(FixSchedulerImport modelImport) {
		this.modelImport = modelImport;
	}

	/**
	 * @return the checkPattern
	 */
	public String getCheckPattern() {
		return checkPattern;
	}

	/**
	 * @param checkPattern the checkPattern to set
	 */
	public void setCheckPattern(String checkPattern) {
		this.checkPattern = checkPattern;
	}

	/**
	 * @return the modelCheck
	 */
	public List<FixSchedulerImport> getModelCheck() {
		return modelCheck;
	}

	/**
	 * @param modelCheck the modelCheck to set
	 */
	public void setModelCheck(List<FixSchedulerImport> modelCheck) {
		this.modelCheck = modelCheck;
	}
}
