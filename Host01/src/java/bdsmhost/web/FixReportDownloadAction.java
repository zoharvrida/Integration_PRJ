/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.Parameter;
import bdsm.rpt.dao.FixMasterReportDao;
import bdsm.rpt.dao.FixReportDownloadDao;
import bdsm.rpt.dao.FixReportReqMasterDao;
import bdsm.rpt.model.FixMasterReport;
import bdsm.rpt.model.FixReportReqMaster;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.AuditlogDao;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Transaction;

/**
 *
 * @author v00019722
 */
public class FixReportDownloadAction extends BaseHostAction {

	private final String download = "Download";
	private final String errdownload = "Download Error";
	private FixReportReqMaster model;
	private List<FixReportReqMaster> modelList;
	private List<FixReportReqMaster> modelQ;
	private List<FixReportReqMaster> modelMaster;
	private Parameter path;
	private String realPath;
	private String reason;
	private String time;
	private String fullPath;
	private ArrayList value = new ArrayList();
	private ArrayList oldValue = new ArrayList();
	private ArrayList fieldName = new ArrayList();
		private String flag;

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
        @Override
	public String doList() {

		DateFormat format = new SimpleDateFormat(StatusDefinition.convPatternTime);
		getLogger().info(" REQUEST : " + model.getIdRequest());
		getLogger().info(" FLAG : " + this.flag);
		FixReportDownloadDao dao = new FixReportDownloadDao(getHSession());
		Timestamp calDate = SchedulerUtil.getTime();
		Timestamp yesDate = SchedulerUtil.getYesterday();

		getLogger().info("NOW :" + calDate);
		getLogger().info("YESTERDAY :" + yesDate);
		if (flag.equalsIgnoreCase("1")) {
			setModelList(dao.list(model.getIdRequest(), calDate, yesDate));
		} else {
			setModelList(dao.listDownload(model.getIdRequest(), calDate, yesDate));
		}

		getLogger().info("JUMLAH DOWNLOAD FIXREPORT: " + modelList.size());
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		super.setErrorCode("error.6");
		return ERROR;
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
		//getLogger().info("model IDBATCH :" + model.getCompositeId().getIdBatch());
		Writer writer = null;
		String sTime = time.replace("-", "").replace(":", "").replace(" ", "").replace(".", "");
		getLogger().info("Time :" + time);
		getLogger().info("Begin Writing  FIle");
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(realPath + "log" + sTime + ".txt"), "utf-8"));
			writer.write("ERROR :" + getReason());
			getLogger().info(realPath + "log" + sTime + ".txt");
			fullPath = realPath + "log" + sTime + ".txt";
		} catch (IOException ex) {
			getLogger().error(this.getClass().getSimpleName() + ":File creation failed");
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				getLogger().info(ex);
			}
		}
		FixReportReqMasterDao dao = new FixReportReqMasterDao(getHSession());
		AuditlogDao auditdao = new AuditlogDao(getHSession());
		FixReportReqMaster persistence = dao.get(getModel().getCompositeId());
		getLogger().info("PERSISTENCE : " + persistence.getCompositeId().getIdBatch());

		if (persistence != null) { // yang perlu diubah
			persistence.setStatus(errdownload);
			persistence.setDtmFinish(SchedulerUtil.getTime());
			//persistence.setNamTemplate(getModel().getNamTemplate());
			persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
			persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());
			getLogger().info("check Stat Change ...");

			oldValue.add(persistence.getStatus());
			oldValue.add(persistence.getDtmFinish());
			oldValue.add(persistence.getIdMaintainedBy());
			oldValue.add(persistence.getIdMaintainedSpv());
			value.add(errdownload);
			value.add(SchedulerUtil.getTime().toString());
			value.add(getModel().getIdMaintainedBy());
			value.add(getModel().getIdMaintainedSpv());
			fieldName.add("STATUS");
			fieldName.add("DTMFINISH");
			fieldName.add("IDUSER");
			fieldName.add("IDUSERSPV");

			dao.update(persistence);
			try {
				auditdao.runPackage(getNamMenu(),
						"Fixreportreqmaster",
						getModel().getIdMaintainedBy(),
						getModel().getIdMaintainedSpv(),
						"Edit", "Edit", value, oldValue, fieldName);
			} catch (SQLException ex) {
				Logger.getLogger(FixReportDownloadAction.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		getLogger().info("START UPDATING");
		FixReportReqMasterDao dao = new FixReportReqMasterDao(getHSession());
		FixQXtractDao qDao = new FixQXtractDao(getHSession());
		FixMasterReportDao mDao = new FixMasterReportDao(getHSession());
		getLogger().info("MODEL ID REPORT : " + model.getIdReport());
		getLogger().info("PKMR : " + model.getIdReport().toString());
		FixMasterReport fixM = mDao.get(model.getIdReport().toString());
		getLogger().info("MIDDLE UPDATING " + fixM);
		AuditlogDao auditdao = new AuditlogDao(getHSession());
		FixReportReqMaster persistence = dao.get(getModel().getCompositeId());

		/*
		 * try { FileUtils.deleteQuietly(new File(persistence.getFilePath())); }
		 * catch (Exception e) { getLogger().info("FILE : " +
		 * persistence.getFilePath()); }
		 */

		//getLogger().info("PERSISTENCE :" + persistence);
		FixQXtract fixQ = qDao.get(persistence.getQid());
		//getLogger().info("FIXQ :" + fixQ);

		if (persistence != null) { // yang perlu diubah
			persistence.setStatus(download);
			persistence.setQid(fixQ.getqId());
			persistence.setDtmFinish(SchedulerUtil.getTime());
			//persistence.setNamTemplate(getModel().getNamTemplate());
			persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
			persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());

			oldValue.add(persistence.getStatus());
			oldValue.add(persistence.getQid());
			oldValue.add(persistence.getDtmFinish());
			oldValue.add(persistence.getIdMaintainedBy());
			oldValue.add(persistence.getIdMaintainedSpv());
			value.add(download);
			value.add(fixQ.getqId());
			value.add(SchedulerUtil.getTime().toString());
			value.add(getModel().getIdMaintainedBy());
			value.add(getModel().getIdMaintainedSpv());
			fieldName.add("STATUS");
			fieldName.add("QID");
			fieldName.add("DTMFINISH");
			fieldName.add("IDUSER");
			fieldName.add("IDUSERSPV");

			dao.update(persistence);
			try {
				auditdao.runPackage(getNamMenu(),
						"Fixreportreqmaster",
						getModel().getIdMaintainedBy(),
						getModel().getIdMaintainedSpv(),
						"Edit", "Edit", value, oldValue, fieldName);
			} catch (SQLException ex) {
				Logger.getLogger(FixReportDownloadAction.class.getName()).log(Level.SEVERE, null, ex);
			}
			super.setErrorCode("success.1");
			return SUCCESS;
		} else {
			super.setErrorCode("error.1");
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
	 * @return the model
	 */
	public FixReportReqMaster getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(FixReportReqMaster model) {
		this.model = model;
	}

	/**
	 * @return the modelList
	 */
	public List<FixReportReqMaster> getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<FixReportReqMaster> modelList) {
		this.modelList = modelList;
	}

	/**
	 * @return the modelQ
	 */
	public List getModelQ() {
		return modelQ;
	}

	/**
	 * @param modelQ the modelQ to set
	 */
	public void setModelQ(List modelQ) {
		this.modelQ = modelQ;
	}

	/**
	 * @return the path
	 */
	public Parameter getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(Parameter path) {
		this.path = path;
	}

	/**
	 * @return the realPath
	 */
	public String getRealPath() {
		return realPath;
	}

	/**
	 * @param realPath the realPath to set
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the modelMaster
	 */
	public List<FixReportReqMaster> getModelMaster() {
		return modelMaster;
	}

	/**
	 * @param modelMaster the modelMaster to set
	 */
	public void setModelMaster(List<FixReportReqMaster> modelMaster) {
		this.modelMaster = modelMaster;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
