/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsmhost.dao.SknNgInOutDebitHeaderDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixLog;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "serial", "unchecked" })
public class SknNgInwardDebitAction extends BaseHostAction {
	private static final Logger LOGGER = Logger.getLogger(SknNgInwardDebitAction.class);
	private List<? extends Object> modelList;
	private SknNgInOutDebitHeader modelHeader;
	private SknNgInOutDebitFooter modelFooter;
	private SknNgPK modelPk;
	private int mode;
	private String cdBranch;
	private String recIds;
	private String idUser;
    private String nameMenu;
	
    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		LOGGER.debug("List SKN Incoming for Batch : " + this.modelPk.getBatchNo());
        getLogger().info("MODE userd :" + this.mode);
		SknNgInOutDebitHeaderDAO daoHeader = new SknNgInOutDebitHeaderDAO(this.getHSession());
        if (nameMenu == null) {
            nameMenu = "23201";
		this.modelList = daoHeader.listByBatchNoGrid(this.modelPk.getBatchNo(), this.mode);
        } else {
            // Approve all batch display
            if (nameMenu.equalsIgnoreCase("23205") && this.mode == 2) {
                getLogger().info("BATCH ID AFTER :" + this.modelPk.getBatchNo());
                FixLogDao logDao = new FixLogDao(getHSession());
                List<FixLog> logScreen = logDao.getByFilenameAndDatePost("%" + this.modelPk.getBatchNo() + "%", null);

                List<SknIncomingCreditScreen> Screen = new ArrayList<SknIncomingCreditScreen>();
                List<SknNgInOutDebitHeader> temporar = daoHeader.SingleApprove(this.modelPk.getBatchNo());

                getLogger().info("LIST_count :" + temporar.size());
                BigDecimal totalAmount = new BigDecimal(0.0);
                Integer totalRecord = 0;
                Integer totalBatch = 0;
                String tanggalBatch = null;
                Integer count = 0;
                for (SknNgInOutDebitHeader restructure : temporar) {
                    if (restructure != null) {
                        totalAmount = totalAmount.add(BigDecimal.valueOf(Double.parseDouble(restructure.getTotalNominal().trim().replaceAll(",", "."))));
                        totalRecord = totalRecord + Integer.parseInt(restructure.getJumlahRecords().trim());
                        tanggalBatch = restructure.getTanggalBatch();
                        count++;
                    }
                }
                
                SknIncomingCreditScreen screenCast = new SknIncomingCreditScreen();
                screenCast.setTanggalBatch(tanggalBatch);
                screenCast.setTotalAmount(totalAmount.toPlainString());
                screenCast.setTotalBatch(count.toString());
                screenCast.setTotalRecord(totalRecord.toString());
                if (logScreen != null) {
                    screenCast.setFilename(logScreen.get(0).getFcrFileName());
                }
                Screen.add(screenCast);
                setModelListDetail(Screen);
            }
            //this.modelList = daoHeader.listByBatchNoGrid(this.modelPk.getBatchNo(), this.mode);
        }

		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		SknNgInOutDebitHeaderDAO daoHeader = new SknNgInOutDebitHeaderDAO(this.getHSession());
		BaBankMastDAO bankMastDAO = new BaBankMastDAO(this.getHSession());
		this.modelList = daoHeader.listBatchNoByBranchNo(this.cdBranch, bankMastDAO.get().getBusinessDate(), this.mode);
		
        /*
         * Sorting descending
         */
		List<String> temp = (List<String>) this.modelList;
		java.util.Collections.sort(temp, new java.util.Comparator<Object>() {

			public int compare(Object o1, Object o2) {
				return ((String) o2).compareTo((String) o1);
			}
		});
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		String batchNo = this.modelPk.getBatchNo(); 
		
		LOGGER.info("batchNo : " + batchNo);
		LOGGER.info("cdBranch : " + this.cdBranch);
		LOGGER.info("recIds : " + this.recIds);
		LOGGER.info("mode : " + this.mode);
		LOGGER.info("idUser : " + this.idUser);
		
		SknNgInOutDebitHeaderDAO daoHeader = new SknNgInOutDebitHeaderDAO(this.getHSession());

        if (nameMenu == null) {
		String[] arrRecId = this.recIds.split(",");
		List<Integer> listRecId = new java.util.ArrayList<Integer>(arrRecId.length);
            for (int i = 0; i < arrRecId.length; i++) {
			listRecId.add(Integer.valueOf(arrRecId[i]));
            }
            daoHeader.updateApprovedByBatchNoAndRecordIdCollection(batchNo, (this.mode == 1), listRecId, this.idUser);
        } else {
            daoHeader.updateApprovedByBatchNo(batchNo, (this.mode == 1), this.idUser);
        }
		
		
		if (daoHeader.isApprovalCompleted(batchNo)
				&& (daoHeader.countByBatchNoAndApprovedStatus(batchNo, "true") > 0)) {
			FixClassConfigDao fccDAO = new FixClassConfigDao(this.getHSession());
			Integer idScheduler = fccDAO.get(this.getClass().getName(), "WEB", StatusDefinition.AUTHORIZED).get(0).getIdScheduler();
			
			FixQXtractDao xtractDao = new FixQXtractDao(this.getHSession());
			FixQXtract modelXtract = new FixQXtract();
			modelXtract.setIdScheduler(idScheduler);
			modelXtract.setDtmRequest(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			modelXtract.setFlgProcess(StatusDefinition.REQUEST);
			modelXtract.setParam1("0");
			modelXtract.setParam2(this.cdBranch);
			modelXtract.setParam3(this.idUser);
			modelXtract.setParam6(batchNo);
			xtractDao.insert(modelXtract);
		}
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

	/**
	 * @return the modelListDetail
	 */
	public List<? extends Object> getModelList() {
		return this.modelList;
	}

	/**
     * 
     * @param modelList 
     */
	public void setModelListDetail(List<? extends Object> modelList) {
		this.modelList = modelList;
	}


	/**
	 * @return the modelHeader
	 */
	public SknNgInOutDebitHeader getModelHeader() {
		return this.modelHeader;
	}

	/**
	 * @param modelHeader the modelHeader to set
	 */
	public void setModelHeader(SknNgInOutDebitHeader modelHeader) {
		this.modelHeader = modelHeader;
	}

	/**
	 * @return the modelFooter
	 */
	public SknNgInOutDebitFooter getModelFooter() {
		return this.modelFooter;
	}

	/**
	 * @param modelFooter the modelFooter to set
	 */
	public void setModelFooter(SknNgInOutDebitFooter modelFooter) {
		this.modelFooter = modelFooter;
	}

	/**
	 * @return the modelPk
	 */
	public SknNgPK getModelPk() {
		return this.modelPk;
	}

	/**
	 * @param modelPk the modelPk to set
	 */
	public void setModelPk(SknNgPK modelPk) {
		this.modelPk = modelPk;
	}

	/**
	 * @return the mode
	 */
	public int getMode() {
		return this.mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return the cdBranch
	 */
	public String getCdBranch() {
		return this.cdBranch;
	}

	/**
	 * @param cdBranch the cdBranch to set
	 */
	public void setCdBranch(String cdBranch) {
		this.cdBranch = cdBranch;
	}
	
	/**
	 * @return the recIds
	 */
	public String getRecIds() {
		return this.recIds;
	}

	/**
	 * @param recIds the recIds to set
	 */
	public void setRecIds(String recIds) {
		this.recIds = recIds;
	}
	
	/**
	 * @return the idUser
	 */
	public String getIdUser() {
		return this.idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

    /**
     * @return the nameMenu
     */
    public String getNameMenu() {
        return nameMenu;
    }

    /**
     * @param nameMenu the nameMenu to set
     */
    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }
}
