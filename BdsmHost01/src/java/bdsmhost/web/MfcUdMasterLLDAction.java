package bdsmhost.web;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.service.CustomerService;
import bdsm.model.FcrBaBankMast;
import bdsm.model.FcrCiCustmast;
import bdsm.model.MfcUdMaster_LLD;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.FcrCiCustmastDao;
import bdsmhost.dao.LLDDAO;
import bdsmhost.dao.MfcUdMasterDao;
import bdsmhost.dao.MfcUdMaster_LLDDAO;
import bdsmhost.fcr.dao.CiCustMastDao;


/**
 * @author v00020800
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class MfcUdMasterLLDAction extends BaseHostAction {
	private List modelList;
	private MfcUdMaster_LLD model;

	/**
	 * Input: HTTP Request parameters: "model.compositeId"
	 * 
	 * Output: JSON Object: modelList
     * 
     * @return 
     */
	public String doList() {
		try {
			MfcUdMaster_LLDDAO dao = new MfcUdMaster_LLDDAO(getHSession());
			this.modelList = dao.listByCifNo(this.model.getCompositeId().getNoCif());
		}
		catch (Exception ex) {
			return ERROR;
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String listByCodAcctNo() {
		try {
			LLDDAO dao = new LLDDAO(getHSession());
			CustomerService custService = new CustomerService();
			custService.setCiCustMastDAO(new CiCustMastDao(this.getHSession()));
			
			Object[] result = dao.listOfUdWithAcctCustRelation(this.model.getRemarks()); // remarks contain codAcctNo
			this.modelList = (List) result[0];
			
			if (this.modelList.size() > 0) {
				int counter = 0;
				
				while (counter < this.modelList.size()) {
					MfcUdMaster_LLD udMaster = (MfcUdMaster_LLD) this.modelList.get(counter); 
					Map<String, Object> tempMap = new java.util.HashMap<String, Object>(0);
					
					
					tempMap.put("COD_CUST", udMaster.getCompositeId().getNoCif());
					tempMap.put("NOUD", udMaster.getCompositeId().getNoUd());
					tempMap.put("CCYUD", udMaster.getCcyUd());
					tempMap.put("AMTLIMIT", udMaster.getAmtLimit());
					tempMap.put("AMTAVAIL", udMaster.getAmtAvail());
					tempMap.put("CDBRANCH", udMaster.getCdBranch());
					tempMap.put("DTEXPIRY", udMaster.getDtExpiry());
					tempMap.put("AMTLIMITUSD", udMaster.getAmtLimitUsd());
					tempMap.put("AMTAVAILUSD", udMaster.getAmtAvailUsd());
					tempMap.put("COD_ACCT_CUST_REL", ((List) result[1]).get(counter));
					
					CiCustMast customer = custService.getByCustomerId(udMaster.getCompositeId().getNoCif());
					if (customer != null)
						tempMap.put("NAM_CUST_FULL", customer.getNamCustFull());
					
					this.modelList.set(counter++, tempMap);
				}
			}
		}
		catch (Exception ex) {
			return ERROR;
		}

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String listByCIFNo() {
		try {
			List lstTemp = new java.util.ArrayList<String>(0);
			LLDDAO dao = new LLDDAO(getHSession());
			CustomerService custService = new CustomerService();
			custService.setCiCustMastDAO(new CiCustMastDao(this.getHSession()));
			
            getLogger().debug("BEFORE PACKAGE :" + this.model);
			         Object[] result = null;
            try {
                result = dao.listOfUdWithAcctCustRelation(this.model.getCompositeId().getNoCif());
            } catch (Exception exception) {
                getLogger().debug("EXCIF :" + exception,exception);
            }
			this.modelList = (List) result[0];
			
			if (this.modelList.size() > 0) {
				int counter = 0;
				
				while (counter < this.modelList.size()) {
					MfcUdMaster_LLD udMaster = (MfcUdMaster_LLD) this.modelList.get(counter);
					if (lstTemp.contains(udMaster.getCompositeId().getNoUd())) {
						this.modelList.remove(counter);
						continue;
					}
						
					
					Map<String, Object> tempMap = new java.util.HashMap<String, Object>(0);
					tempMap.put("COD_CUST_ID", udMaster.getCompositeId().getNoCif());
					tempMap.put("NOUD", udMaster.getCompositeId().getNoUd());
					tempMap.put("CCYUD", udMaster.getCcyUd());
					tempMap.put("AMTLIMIT", udMaster.getAmtLimit());
					tempMap.put("AMTAVAIL", udMaster.getAmtAvail());
					tempMap.put("CDBRANCH", udMaster.getCdBranch());
					tempMap.put("DT_ISSUED", udMaster.getDtIssued());
					tempMap.put("DTEXPIRY", udMaster.getDtExpiry());
					tempMap.put("AMTLIMITUSD", udMaster.getAmtLimitUsd());
					tempMap.put("AMTAVAILUSD", udMaster.getAmtAvailUsd());
					tempMap.put("CATEGORY_UD", udMaster.getUdTypeCategory());
					tempMap.put("TYPE_UD", udMaster.getUdTypeValue());
					tempMap.put("PAYEE_NAME", udMaster.getPayeeName());
					tempMap.put("PAYEE_COUNTRY", udMaster.getPayeeCountry());
					tempMap.put("TRADING_PRODUCT", udMaster.getTradingProduct());
					tempMap.put("REMARKS", udMaster.getRemarks());
					tempMap.put("STATUS", udMaster.getStatus());
					tempMap.put("RATFCYLIM", udMaster.getRatFcyLim());
					tempMap.put("RATUSDLIM", udMaster.getRatUsdLim());
					
					
					CiCustMast customer = custService.getByCustomerId(udMaster.getCompositeId().getNoCif());
					if (customer != null)
						tempMap.put("NAM_CUST_FULL", customer.getNamCustFull());
					
					lstTemp.add(tempMap.get("NOUD"));
					this.modelList.set(counter++, tempMap);
				}
			}
		}
		catch (Exception ex) {
			return ERROR;
		}

		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    public String listByUDNo() {
		MfcUdMasterDao dao = new MfcUdMasterDao(getHSession());
		this.modelList = dao.listByNoUD(this.model.getCompositeId().getNoUd());
		
		return SUCCESS;
	}

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
	public String doGet() {
		throw new UnsupportedOperationException("Not supported yet.");
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
		FcrCiCustmastDao cicustmastDao = new FcrCiCustmastDao(this.getHSession());
		FcrBaBankMastDao fcrBaBankMastDao = new FcrBaBankMastDao(this.getHSession());
		MfcUdMaster_LLDDAO udMaster_LLDDAO = new MfcUdMaster_LLDDAO(this.getHSession());
		AuditlogDao auditDao = new AuditlogDao(this.getHSession());
		
		FcrCiCustmast customer = cicustmastDao.get(this.model.getCompositeId().getNoCif());
		FcrBaBankMast fcrBaBankMast = fcrBaBankMastDao.get();
		MfcUdMaster_LLD udMaster = udMaster_LLDDAO.get(this.model.getCompositeId());
		List<MfcUdMaster_LLD> listOfSameUDNo = udMaster_LLDDAO.listByNoUD(this.model.getCompositeId().getNoUd());

		if (customer == null) {
			super.setErrorCode("20302.0");
			return ERROR;
		}

		if (this.model.getDtExpiry().compareTo(fcrBaBankMast.getDatProcess()) < 0) {
			super.setErrorCode("20302.3");
			return ERROR;
		}

		if (udMaster == null) {
			for (MfcUdMaster_LLD m : listOfSameUDNo)
				if (this.model.equals(m)) {
					super.setErrorCode("error.0");
					return ERROR;
				}

			udMaster_LLDDAO.insert(this.model);
			auditDao.insert(this.model.getIdMaintainedBy(), this.model.getIdMaintainedSpv(), "MFCUDMASTER_LLD", this.getNamMenu(), "Add", "Insert");
			super.setErrorCode("success.0");
			
			return SUCCESS;
		}
		else {
			super.setErrorCode("error.0");
			return ERROR;
		}
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		MfcUdMaster_LLDDAO udMaster_LLDDAO = new MfcUdMaster_LLDDAO(this.getHSession());
		FcrBaBankMastDao fcrBaBankMastDao = new FcrBaBankMastDao(this.getHSession());
		AuditlogDao auditdao = new AuditlogDao(this.getHSession());
		MfcUdMaster_LLD udMaster = udMaster_LLDDAO.get(this.model.getCompositeId());

		if (udMaster != null) {
			FcrBaBankMast fcrBaBankMast = fcrBaBankMastDao.get();
			if (this.model.getDtExpiry().compareTo(fcrBaBankMast.getDatProcess()) < 0) {
				super.setErrorCode("28302.3");
				return ERROR;
			}

			if (this.model.getStatus().equals("Inactive")) {
				super.setErrorCode("28302.1");
				return ERROR;
			}
			udMaster.setUdTypeValue(this.model.getUdTypeValue());
			udMaster.setUdTypeCategory(this.model.getUdTypeCategory());
			udMaster.setPayeeName(this.model.getPayeeName());
			udMaster.setPayeeCountry(this.model.getPayeeCountry());
			udMaster.setTradingProduct(this.model.getTradingProduct());
			udMaster.setDtIssued(this.model.getDtIssued());
			udMaster.setDtExpiry(this.model.getDtExpiry());
			udMaster.setCcyUd(this.model.getCcyUd());

			BigDecimal tmpAmtLimit = new BigDecimal(String.valueOf(udMaster.getAmtLimit()));
			BigDecimal tmpAmtLimitUSD = new BigDecimal(String.valueOf(udMaster.getAmtLimitUsd()));
			BigDecimal tmpAmtAvail = new BigDecimal(String.valueOf(udMaster.getAmtAvail()));
			BigDecimal tmpAmtAvailUSD = new BigDecimal(String.valueOf(udMaster.getAmtAvailUsd()));
			BigDecimal amtUsed = tmpAmtLimit.subtract(tmpAmtAvail);
			BigDecimal amtUsedUSD = tmpAmtLimitUSD.subtract(tmpAmtAvailUSD);
			BigDecimal tmpNewLimit = new BigDecimal(String.valueOf(this.model.getAmtLimit()));
			BigDecimal tmpNewLimitUSD = new BigDecimal(String.valueOf(this.model.getAmtLimitUsd()));
			if (tmpNewLimit.compareTo(amtUsed) == -1) {
				super.setErrorCode("20302.4");
				return ERROR;
			}
			if (tmpNewLimitUSD.compareTo(amtUsedUSD) == -1) {
				super.setErrorCode("20302.4");
				return ERROR;
			}
			
			udMaster.setAmtLimit(this.model.getAmtAvail());
			udMaster.setAmtLimitUsd(this.model.getAmtLimitUsd());
			udMaster.setAmtAvail(tmpNewLimit.subtract(amtUsed));
			udMaster.setAmtAvailUsd(tmpNewLimitUSD.subtract(amtUsedUSD));
			udMaster.setRatFcyLim(this.model.getRatFcyLim());
			udMaster.setRatUsdLim(this.model.getRatUsdLim());
			udMaster.setRemarks(this.model.getRemarks());
			udMaster.setIdMaintainedBy(this.model.getIdMaintainedBy());
			udMaster.setIdMaintainedSpv(this.model.getIdMaintainedSpv());

			udMaster_LLDDAO.update(udMaster);
			auditdao.insert(this.model.getIdMaintainedBy(), model.getIdMaintainedSpv(), "MFCUDMASTER_LLD", this.getNamMenu(), "Edit", "Edit");
			super.setErrorCode("success.1");
			
			return SUCCESS;
		}
		else {
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
		MfcUdMaster_LLDDAO udMaster_LLDDAO = new MfcUdMaster_LLDDAO(this.getHSession());
		AuditlogDao auditDAO = new AuditlogDao(this.getHSession());
		MfcUdMaster_LLD udMaster = udMaster_LLDDAO.get(this.model.getCompositeId());

		if (udMaster != null) {
			if (this.model.getStatus().equals("Active")) {
				super.setErrorCode("20302.2");
				return ERROR;
			}

			udMaster_LLDDAO.delete(udMaster);
			auditDAO.insert(this.model.getIdMaintainedBy(), this.model.getIdMaintainedSpv(), "MFCUDMASTER_LLD", this.getNamMenu(), "Delete", "Delete");
			super.setErrorCode("success.2");
			
			return SUCCESS;
		}
		else {
			super.setErrorCode("error.2");
			return ERROR;
		}
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
     * @return
     */
    @JSON(serialize=false)
	public MfcUdMaster_LLD getModel() {
		return this.model;
	}
    /**
     * 
     * @param model
     */
    public void setModel(MfcUdMaster_LLD model) {
		this.model = model;
	}
}