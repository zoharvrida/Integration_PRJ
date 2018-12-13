package bdsmhost.web;


import static bdsm.model.MPComponent.COMP_COMMITMENT;
import static bdsm.model.MPComponent.COMP_PARAMETER;
import static bdsm.model.MPComponent.COMP_READ_ONLY;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;

import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.ChProdMast;
import bdsm.fcr.service.AccountService;
import bdsm.model.MPAcctReg;
import bdsm.model.MPAcctRegDtls;
import bdsm.model.MPClass;
import bdsm.model.MPClassDetails;
import bdsm.model.MPComponent;
import bdsm.model.MPProductMIS;
import bdsm.util.BdsmUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.MPAcctRegDAO;
import bdsmhost.dao.MPClassDAO;
import bdsmhost.dao.MPClassDetailsDAO;
import bdsmhost.dao.MPComponentDAO;
import bdsmhost.dao.MPProductMISDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.ChProdMastDAO;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MitraPastiAction extends ModelDrivenBaseHostAction<MPAcctReg> implements ApplicationAware {
	private static Logger LOGGER = Logger.getLogger(MitraPastiAction.class); 
	
	private Map<String, Object> applicationContext;
	private String webSessionId;
	private MPAcctReg acctReg;
	private ChAcctMast account;
	private ChProdMast product;
	private MPProductMIS productMIS;
	private MPClass MPClass;
	private List<Object> listCompCommitment;
	private List<Map<String, ? extends Object>> listCompLogic;
	private Map<Integer, MPAcctRegDtls> mapAcctRegDtls;
	
	
    /**
     * 
     */
    public MitraPastiAction() {
		this.acctReg = new MPAcctReg();
	}
	
	
    /**
     * 
     * @return
     */
    public String validateAccount() {
		MPAcctRegDAO acctDAO = new MPAcctRegDAO(this.getHSession());
		MPProductMISDAO prodMISDAO = new MPProductMISDAO(this.getHSession());
		
		if (acctDAO.getByActiveAccountNo(this.acctReg.getNoAccount()) == null) {
			AccountService acctSvc = new AccountService();
			acctSvc.setFCRChAcctMastDAO(new ChAcctMastDAO(this.getHSession()));
			this.account = acctSvc.getByAccountNo(this.acctReg.getNoAccount());
			
			if ((this.account != null) && (!BdsmUtil.isContainsIn(this.account.getCodAcctStat().toString(), "1", "2", "5", "7"))) { // 1=Closed; 2=Blocked; 5=Closed Today; 7=Dormant
				/* Account Product */
				ChProdMastDAO prodDAO = new ChProdMastDAO(this.getHSession());
				this.product = prodDAO.getActiveProduct(this.account.getCodProd());
				
				List<MPProductMIS> l = prodMISDAO.listByProductCode(this.product.getCompositeId().getCodProd());
				if (l.size() == 0)
					this.product = null;
			}
		}
		else
			this.account = new ChAcctMast();
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String listComponentByClass() {
		MPComponentDAO compDAO = new MPComponentDAO(this.getHSession());
		MPClassDetailsDAO clsDtlsDAO = new MPClassDetailsDAO(this.getHSession());
		boolean isAddMode = (this.acctReg.getId() == null);
		MPAcctRegDtls detail;
		
		
		List<? extends Object> listComponent = clsDtlsDAO.listComponentByClass(Integer.valueOf(this.acctReg.getCodeClass()));
		this.listCompCommitment = new java.util.ArrayList<Object>();
		this.listCompLogic = new java.util.ArrayList<Map<String, ? extends Object>>();
		
		if (isAddMode == false) {
			/* here, must be after calling doGet method */
			this.mapAcctRegDtls = (Map<Integer, MPAcctRegDtls>) this.applicationContext.get(this.webSessionId);
			this.applicationContext.remove(this.webSessionId);
		}
		
		
		Map<Integer, Object> tempMap = new java.util.LinkedHashMap<Integer, Object>();
		for (Object obj: listComponent) {
			MPComponent comp = (MPComponent) ((Object[]) obj)[0];
			MPClassDetails classDetails = (MPClassDetails) ((Object[]) obj)[1];
			
			String dataType = comp.getDataType();
			Map<String, Object> map;
			
			if (COMP_COMMITMENT.equals(comp.getType())) {
				map = new java.util.HashMap<String, Object>();
				map.put("component", comp);
				map.put("classDetails", classDetails);
				
				if ((isAddMode == false) && ((detail = this.mapAcctRegDtls.get(comp.getCode())) != null))
					map.put("value", 
							("M".equals(dataType))? detail.getAmountValue(): 
							("N".equals(dataType))? detail.getCounterValue(): 
							detail.getStringValue()
					);
				
				this.listCompCommitment.add(map);
			}
			else if (bdsm.util.BdsmUtil.isContainsIn(comp.getType(), COMP_PARAMETER, COMP_READ_ONLY)) {
				String strCompCode = comp.getCode().toString().trim();
				Integer parentCompCode = Integer.valueOf(strCompCode.substring(0, strCompCode.length() - 2));
				
				if (tempMap.containsKey(parentCompCode)) {
					map = (Map) tempMap.get(parentCompCode);
				}
				else {
					map = new java.util.HashMap<String, Object>();
					map.put("logic", compDAO.get(parentCompCode));
					map.put("parameters", new java.util.ArrayList<Map<String, Object>>(1));
					map.put("isLogicChecked", (isAddMode? "true": "false"));
					
					tempMap.put(parentCompCode, map);
				}
				
				List list = ((List<Map<String, Object>>) map.get("parameters"));
				Map<String, Object> m = new java.util.HashMap<String, Object>();
				m.put("component", comp);
				m.put("classDetails", classDetails);
				
				if ((isAddMode == false) && ((detail = this.mapAcctRegDtls.get(comp.getCode())) != null)) {
					m.put("value", 
							("M".equals(comp.getDataType()))? detail.getAmountValue(): 
							("N".equals(comp.getDataType()))? detail.getCounterValue():
							detail.getStringValue()
					);
					map.put("isLogicChecked", "true");
				}
				
				list.add(m);
			}
		}
		
		for(Integer i : tempMap.keySet())
			this.listCompLogic.add((Map<String, Object>) tempMap.get(i));
		
		return SUCCESS;
	}
	

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
		/* inquiry, edit or delete mode */
		MPAcctRegDAO acctDAO = new MPAcctRegDAO(this.getHSession());
		
		if ((this.acctReg = acctDAO.getByActiveAccountNo(this.acctReg.getNoAccount())) != null) {
			AccountService acctSvc = new AccountService();
			acctSvc.setFCRChAcctMastDAO(new ChAcctMastDAO(this.getHSession()));
			ChProdMastDAO prodDAO = new ChProdMastDAO(this.getHSession());
			MPClassDAO mpClassDAO = new MPClassDAO(this.getHSession());
			MPProductMISDAO mpProdMISDAO = new MPProductMISDAO(this.getHSession());
			
			
			this.account = acctSvc.getByAccountNo(this.acctReg.getNoAccount());
			this.product = prodDAO.getActiveProduct(this.account.getCodProd());
			this.MPClass = mpClassDAO.get(acctReg.getCodeClass());
			this.productMIS = mpProdMISDAO.get(new MPProductMIS(this.MPClass.getCodeProduct(), this.MPClass.getCodeMIS()));
			
			
			Set<MPAcctRegDtls> details = this.acctReg.getDetails();
			this.mapAcctRegDtls = new java.util.LinkedHashMap<Integer, MPAcctRegDtls>(details.size());
			
			Iterator<MPAcctRegDtls> it = details.iterator();
			while(it.hasNext()) {
				MPAcctRegDtls item = it.next();
				this.mapAcctRegDtls.put(item.getCodeComponent(), item);
			}
			
			this.applicationContext.put(this.webSessionId, this.mapAcctRegDtls);
		}
		
		return SUCCESS;
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
	public synchronized String doInsert() {
		BaBankMastDAO bankDAO = new BaBankMastDAO(this.getHSession());
		MPAcctRegDAO acctRegDAO = new MPAcctRegDAO(this.getHSession());
		
		try {
			String businessDate = DateUtility.DATE_FORMAT_YYYYMMDD.format(bankDAO.get().getBusinessDate());
			Long generatedId = acctRegDAO.generateIdRegistration(businessDate);
			
			/* Account Registration */
			this.acctReg.setId(generatedId);
			
			
			/* Account Registration Details */
			Iterator<String> keys = this.getStrData().keySet().iterator();
			String key, codeComponent, value;
			MPAcctRegDtls detail;
			
			while (keys.hasNext()) {
				key = keys.next();
				if (key.startsWith("comp")) {
					value = this.getStrData().get(key).trim();
					codeComponent = key.substring("comp".length());
					detail = new MPAcctRegDtls(this.acctReg.getId(), Integer.valueOf(codeComponent));
					
					if ("M".equals(this.getStrData().get("type" + codeComponent))) {
						detail.setAmountValue(new BigDecimal(value));
					}
					else if ("N".equals(this.getStrData().get("type" + codeComponent))) {
						detail.setCounterValue(Double.valueOf(value));
					}
					else {
						detail.setStringValue(value);
					}
					
					if (this.acctReg.getDetails() == null)
						this.acctReg.setDetails(new java.util.HashSet<MPAcctRegDtls>());
					
					this.acctReg.getDetails().add(detail);
				}
			}
			
			
			/* Save and return */
			acctRegDAO.insert(this.acctReg);
			this.setJsonStatus(SUCCESS);
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
			this.setJsonStatus(ERROR);
			this.setErrorCode(ex.getMessage());
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		MPAcctRegDAO acctRegDAO = new MPAcctRegDAO(this.getHSession());
		String idCreatedBy = this.acctReg.getIdMaintainedBy();
		String idCreatedSpv = this.acctReg.getIdMaintainedSpv();
		
		/* Account registration from database */
		this.acctReg = acctRegDAO.getById(this.acctReg.getId()); // 
		
		/* Account registration details from database */
		Set<MPAcctRegDtls> acctRegDetails = this.acctReg.getDetails();
		
		
		/* Update or delete details */
		Set<MPAcctRegDtls> details = new java.util.HashSet<MPAcctRegDtls>(acctRegDetails);
		if (details != null) {
			Iterator<MPAcctRegDtls> it = details.iterator();
			
			while (it.hasNext()) {
				MPAcctRegDtls item = it.next();
				String compName = "comp" + item.getCodeComponent();
				
				if ((!this.getStrData().containsKey(compName)) || (StringUtils.isBlank(this.getStrData().get(compName))))
					acctRegDetails.remove(item);
				else {
					String value = this.getStrData().get(compName).trim();
					
					if ("M".equals(this.getStrData().get("type" + item.getCodeComponent()))) {
						if (!item.getAmountValue().equals(new BigDecimal(value)))
							item.setAmountValue(new BigDecimal(value));
					}
					else if ("N".equals(this.getStrData().get("type" + item.getCodeComponent()))) {
						if (!item.getCounterValue().equals(Integer.valueOf(value)))
							item.setCounterValue(Double.valueOf(value));
					}
					else {
						if (!item.getStringValue().equals(value))
							item.setStringValue(value);
					}
					
					this.getStrData().remove(compName);
				}
			}
		}
		
		
		/* Add to details based on user input */
		Iterator<String> keys = this.getStrData().keySet().iterator();
		String key, codeComponent, value;
		MPAcctRegDtls detail;
		
		while (keys.hasNext()) {
			key = keys.next();
			if (key.startsWith("comp")) {
				value = this.getStrData().get(key).trim();
				codeComponent = key.substring("comp".length());
				java.sql.Timestamp time = SchedulerUtil.getTime();
				
				detail = new MPAcctRegDtls(this.acctReg.getId(), Integer.valueOf(codeComponent));
				detail.setIdCreatedBy(idCreatedBy);
				detail.setDtmCreated(time);
				detail.setIdCreatedSpv(idCreatedSpv);
				detail.setDtmCreatedSpv(time);
				
				if ("M".equals(this.getStrData().get("type" + codeComponent))) {
					detail.setAmountValue(new BigDecimal(value));
				}
				else if ("N".equals(this.getStrData().get("type" + codeComponent))) {
					detail.setCounterValue(Double.valueOf(value));
				}
				else {
					detail.setStringValue(value);
				}
					
				
				if (this.acctReg.getDetails() == null)
					this.acctReg.setDetails(new java.util.HashSet<MPAcctRegDtls>());
				
				this.acctReg.getDetails().add(detail);
			}
		}
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    public String changePackage() {
		MPAcctRegDAO acctRegDAO = new MPAcctRegDAO(this.getHSession());
		
		/* old data */
		MPAcctReg acctReg_ = acctRegDAO.getById(this.acctReg.getId());
		acctReg_.setDateExpiry(DateUtils.addDays(this.acctReg.getDateCommitment(), -1));
		
		/* new data */
		return this.doInsert();
	}
	

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		MPAcctRegDAO acctRegDAO = new MPAcctRegDAO(this.getHSession());
		BaBankMastDAO baBankMastDAO = new BaBankMastDAO(this.getHSession());
		
		
		this.acctReg = acctRegDAO.getById(this.acctReg.getId());
		if (this.acctReg != null) {
			List<MPAcctReg> list = acctRegDAO.getAllByActiveAccountNo(this.acctReg.getNoAccount());
			MPAcctReg acctReg_; 
			
			if ((list != null) && (list.size() > 0)) {
				/* Delete Flag */
				acctReg_= list.get(0);
				acctReg_.setDateExpiry(baBankMastDAO.get().getBusinessDate());
				acctReg_.setDeleted(Boolean.TRUE);
				
				/* Activate before */
				if (list.size() > 1)
					list.get(1).setDateExpiry(null);
			}
		}
				
		return SUCCESS;
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
	public MPAcctReg getModel() {
		return this.acctReg;
	}
	
    /**
     * 
     * @return
     */
    public ChAcctMast getAccount() {
		return this.account;
	}
	
    /**
     * 
     * @return
     */
    public ChProdMast getProduct() {
		return this.product;
	}
	
    /**
     * 
     * @return
     */
    public MPProductMIS getProductMIS() {
		return this.productMIS;
	}
	
    /**
     * 
     * @return
     */
    public MPClass getMPClass() {
		return this.MPClass;
	}
	
    /**
     * 
     * @return
     */
    public List<Object> getListCompCommitment() {
		return this.listCompCommitment;
	}
	
    /**
     * 
     * @return
     */
    public List<Map<String, ? extends Object>> getListCompLogic() {
		return this.listCompLogic;
	}


    /**
     * 
     * @param applicationContext
     */
    @Override
	public void setApplication(Map<String, Object> applicationContext) {
		this.applicationContext = applicationContext;
	}
	
}
