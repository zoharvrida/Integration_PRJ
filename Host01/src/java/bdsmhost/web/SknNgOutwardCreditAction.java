package bdsmhost.web;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;


import bdsm.model.SknNgInOutCreditDetail;
import bdsm.model.SknNgInOutCreditFooter;
import bdsm.model.SknNgInOutCreditHeader;
import bdsm.model.SknNgPK;
import bdsm.model.SknNgWSAuditTrailBatch;
import bdsm.model.SknNgWSAuditTrailHeader;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.service.SknNgCreditService;
import bdsm.service.SknNgOutwardCreditService;
import bdsm.service.SknNgService;
import bdsm.util.CRC16;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.SknNgInOutCreditDetailDAO;
import bdsmhost.dao.SknNgInOutCreditFooterDAO;
import bdsmhost.dao.SknNgInOutCreditHeaderDAO;
import bdsmhost.dao.SknNgWSAuditTrailBatchDAO;
import bdsmhost.dao.SknNgWSAuditTrailDetailDAO;
import bdsmhost.dao.SknNgWSAuditTrailHeaderDAO;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgOutwardCreditAction extends BaseHostAction implements org.apache.struts2.interceptor.ApplicationAware {
	private Map<String, Object> applicationContext;
	private Map<String, Object> sessionMap;
	private String webSessionId;
	private String branchNo;
	private String batchNo;
	private String batchNoWS;
	private Integer recordId;
	private String idUser;
	private String status;
	private Map<String, String> data;
	private List<SknNgWSAuditTrailBatch> listAuditBatch;
	private List<Object[]> listHeader;
	private List<Object[]> listDetail;
	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doUpdate() {
		this.getLogger().debug("data: " + this.data);
		
		/* get from session */
		this.sessionMap = (Map<String, Object>) this.applicationContext.get(this.webSessionId);
		Map<SknNgPK, Object> mapDetail = (Map<SknNgPK, Object>) this.sessionMap.get("mapDetail");
		
		SknNgInOutCreditDetail detail = (SknNgInOutCreditDetail) ((Object[]) mapDetail.get(new SknNgPK(this.batchNo, this.recordId)))[1];
		detail.setIdUpdatedBy((String) this.idUser);
		detail.setDtmUpdated(SchedulerUtil.getTime());
		
		/* Set property with updated value */
		Iterator<String> it = this.data.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			try {
				BeanUtils.setProperty(detail, key, this.data.get(key));
			} catch (Exception e) {
				this.getLogger().warn("No property of Object \"" + SknNgInOutCreditDetail.class + "\" with name: \"" + key + "\"");
			}
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}
	
	
    /**
     * 
     * @return
     */
    public String listFilename() {
		SknNgWSAuditTrailBatchDAO batchDAO = new SknNgWSAuditTrailBatchDAO(this.getHSession());
		String status = null;
		
		
		this.applicationContext.remove(this.webSessionId);
		
		if (this.status != null) {
			if (StatusDefinition.REJECT.equalsIgnoreCase(this.status.trim()))
				status = SknNgService.REJECT_CODE;
			else if (StatusDefinition.AUTHORIZED.equalsIgnoreCase(this.status.trim()))
				status = SknNgService.ACCEPT_CODE;
		}
		
		this.listAuditBatch = batchDAO.listByBatchNoTypeAndStatus(
				null,
				Integer.valueOf(SknNgCreditService.CODE_DKE), 
				status, 
				new java.util.Date()
			);
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String listHeader() {
		SknNgWSAuditTrailHeaderDAO headerDAO = new SknNgWSAuditTrailHeaderDAO(this.getHSession());
		SknNgWSAuditTrailDetailDAO detailDAO = new SknNgWSAuditTrailDetailDAO(this.getHSession());
		String status = null;
		
		
		this.applicationContext.remove(this.webSessionId);
		
		if (this.status != null) {
			if (StatusDefinition.REJECT.equalsIgnoreCase(this.status.trim()))
				status = SknNgService.REJECT_CODE;
			else if (StatusDefinition.AUTHORIZED.equalsIgnoreCase(this.status.trim()))
				status = SknNgService.ACCEPT_CODE;
		}
		this.listHeader = headerDAO.listCreditByBatchNoAndStatus(this.batchNo, status);
		
		if (this.listHeader.size() > 0) {
			/* Get the size Rejected */
			String[] keys = new String[this.listHeader.size()];
			for (int i=0; i<this.listHeader.size(); i++) {
				SknNgPK pk = ((SknNgWSAuditTrailHeader) ((Object[]) this.listHeader.get(i))[0]).getCompositeId(); 
				keys[i] = pk.getBatchNo() + pk.getRecordId();
			}
			List<Object[]> countReject = (List<Object[]>) detailDAO.countByStatus(java.util.Arrays.asList(keys), status);
			for (int i=0; i<this.listHeader.size(); i++) {
				Object[] arr1 = (Object[]) this.listHeader.get(i);
				SknNgPK pk = ((SknNgWSAuditTrailHeader) arr1[0]).getCompositeId();
				
				for (int j=0; j<countReject.size(); j++) {
					Object[] arr2 = countReject.get(j);
					
					if ((pk.getBatchNo().equals(arr2[0])) && (pk.getRecordId().equals(arr2[1]))) {
						Map<String, Object> tempMap = new HashMap<String, Object>(1);
						tempMap.put("errorDescription", arr2[2]); // SPK Error Description
						tempMap.put("totalError", arr2[3]); // Add Count Reject
						
						Object[] arr3 = new Object[arr1.length + 1];
						arr3[0] = arr1[0]; // SknNgWSAuditTrailHeader 
						arr3[1] = arr1[1]; // SknNgInOutCreditHeader
						arr3[2] = tempMap; // Add tempMap to existing listHeader
						
						this.listHeader.set(i, arr3);
					}
				}
			}
		}
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String listDetail() {
		/* session management */
		if (this.applicationContext.containsKey(this.webSessionId) == false)
			this.applicationContext.put(this.webSessionId, new HashMap<String, Object>());
		this.sessionMap = (Map<String, Object>) this.applicationContext.get(this.webSessionId);
		
		
		Map<SknNgPK, Object> mapDetail;
		if (this.batchNo.equals(this.sessionMap.get("batchNo")) == false) {
			/* get data from Database */
			String status = null;
			if (this.status != null) {
				if (StatusDefinition.REJECT.equalsIgnoreCase(this.status.trim()))
					status = SknNgService.REJECT_CODE;
				else if (StatusDefinition.AUTHORIZED.equalsIgnoreCase(this.status.trim()))
					status = SknNgService.ACCEPT_CODE;
			}
			
			SknNgWSAuditTrailDetailDAO detailDAO = new SknNgWSAuditTrailDetailDAO(this.getHSession());
			this.listDetail = detailDAO.listCreditByBatchNoAndStatus(this.batchNo, Integer.valueOf(this.recordId), status); 
			/* [0] = SknNgWSAuditTrailDetail; [1] = SknNgInOutCreditDetail; [2] = SPK Error Description */
			
			/* listDetail to mapDetail */
			mapDetail = new java.util.LinkedHashMap<SknNgPK, Object>(this.listDetail.size());
			for (Object o : this.listDetail) {
				detailDAO.evictObjectFromPersistenceContext(((Object[]) o)[0]);
				detailDAO.evictObjectFromPersistenceContext(((Object[]) o)[1]);
				
				SknNgInOutCreditDetail dOri = (SknNgInOutCreditDetail) ((Object[]) o)[1];
				mapDetail.put(dOri.getCompositeId(), o);
			}
			
			this.sessionMap.put("mapDetail", mapDetail);
			this.sessionMap.put("batchNo", this.batchNo);
		}
		else {
			/* get data from session */
			/* mapDetail to listDetail */
			mapDetail = (Map<SknNgPK, Object>) this.sessionMap.get("mapDetail");
			this.listDetail = new java.util.ArrayList<Object[]>(mapDetail.size());
			
			Iterator<SknNgPK> it = mapDetail.keySet().iterator();
			while (it.hasNext())
				this.listDetail.add((Object[]) mapDetail.get(it.next()));
		}
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String sendToSPKWebService() {
		SknNgInOutCreditHeaderDAO headerDAO = new SknNgInOutCreditHeaderDAO(this.getHSession());
		SknNgInOutCreditDetailDAO detailDAO = new SknNgInOutCreditDetailDAO(this.getHSession());
		SknNgInOutCreditFooterDAO footerDAO = new SknNgInOutCreditFooterDAO(this.getHSession());
		SknNgWSAuditTrailHeaderDAO headerWSDAO = new SknNgWSAuditTrailHeaderDAO(this.getHSession());
		
		try {
			SknNgOutwardCreditService service = new SknNgOutwardCreditService();
			List<Integer> listRecordId = new java.util.ArrayList<Integer>();
			StringBuilder sb = new StringBuilder();
			BigDecimal totalNominal = BigDecimal.ZERO;
			
			
			/* get data header from DB */
			SknNgInOutCreditHeader modelHeader = headerDAO.get(new SknNgPK(this.batchNo, this.recordId));
			headerDAO.evictObjectFromPersistenceContext(modelHeader);
			listRecordId.add(modelHeader.getCompositeId().getRecordId());
			
		
			/* get data detail from session */
			this.sessionMap = (Map<String, Object>) this.applicationContext.get(this.webSessionId);
			Map<SknNgPK, Object> mapDetail = (Map<SknNgPK, Object>) this.sessionMap.get("mapDetail");
			
			Iterator<SknNgPK> it = mapDetail.keySet().iterator();
			while (it.hasNext()) {
				SknNgInOutCreditDetail modelDetail, modelDetailPersist;
				
				SknNgPK pk = it.next();
				modelDetail = (SknNgInOutCreditDetail) ((Object[]) mapDetail.get(pk))[1];
				listRecordId.add(modelDetail.getCompositeId().getRecordId());
				
				modelDetailPersist = (SknNgInOutCreditDetail) detailDAO.merge(modelDetail);
				BigDecimal nominal = SknNgService.parseNominalToBigDecimal(modelDetailPersist.getNominal());
				totalNominal = totalNominal.add(nominal);
				modelDetailPersist.setNominal(nominal.toString());
				
				sb.append(service.formatFromInOutDetail(modelDetailPersist));
				sb.append(SknNgService.newLine());
			}
			
			
			/* change header data */
			modelHeader.setJumlahRecords(String.valueOf(mapDetail.size()));
			modelHeader.setTotalNominal(totalNominal.toString().replace(".", ""));
			
			
			/* get data footer from DB */
			SknNgInOutCreditFooter modelFooter = footerDAO.getByBatchNoAndParentRecordId(this.batchNo, this.recordId);
			footerDAO.evictObjectFromPersistenceContext(modelFooter);
			
			listRecordId.add(modelFooter.getCompositeId().getRecordId());
			sb.append(service.formatFromInOutFooter(modelFooter, CRC16.CRC16(sb.toString(), 0)));

			
			sb.insert(0, service.formatFromInOutHeader(modelHeader) + SknNgService.newLine());
			
			
			/* Request to SPK Web Service in a new thread */
			service.createProcessAndRun(
					this.batchNoWS, 
					this.recordId, 
					sb.toString(), 
					listRecordId
			);
			
			do {
				Thread.sleep(5000); // sleep for 5 seconds
				
				SknNgWSAuditTrailHeader headerWS = headerWSDAO.get(new SknNgPK(this.batchNoWS, this.recordId));
				headerWSDAO.evictObjectFromPersistenceContext(headerWS);
				if (headerWS.getDtmFinish() != null)
					break;
			}
			while (true);
			
			this.applicationContext.remove(this.webSessionId);
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
		}
		
		return SUCCESS;
	}
	
	
	
    /**
     * 
     * @param appContext
     */
    @Override
	public void setApplication(Map<String, Object> appContext) {
		this.applicationContext = appContext;
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
    public String getBranchNo() {
		return this.branchNo;
	}
    /**
     * 
     * @param branchNo
     */
    public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	
    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return this.batchNo;
	}
    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
    /**
     * 
     * @return
     */
    public String getBatchNoWS() {
		return this.batchNoWS;
	}
    /**
     * 
     * @param batchNoWS
     */
    public void setBatchNoWS(String batchNoWS) {
		this.batchNoWS = batchNoWS;
	}
	
    /**
     * 
     * @return
     */
    public Integer getRecordId() {
		return this.recordId;
	}
    /**
     * 
     * @param recordId
     */
    public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
    /**
     * 
     * @return
     */
    public String getIdUser() {
		return this.idUser;
	}
    /**
     * 
     * @param idUser
     */
    public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
    /**
     * 
     * @return
     */
    public String getStatus() {
		return this.status;
	}
    /**
     * 
     * @param status
     */
    public void setStatus(String status) {
		this.status = status;
	}
	
    /**
     * 
     * @return
     */
    public Map<String, String> getData() {
		return this.data;
	}
    /**
     * 
     * @param data
     */
    public void setData(Map<String, String> data) {
		this.data = data;
	}
	
    /**
     * 
     * @return
     */
    public List<SknNgWSAuditTrailBatch> getListAuditBatch() {
		return this.listAuditBatch;
	}
	
    /**
     * 
     * @return
     */
    public List<Object[]> getListHeader() {
		return this.listHeader;
	}
	
    /**
     * 
     * @return
     */
    public List<Object[]> getListDetail() {
		return this.listDetail;
	}

}
