package bdsmhost.web;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;

import bdsm.model.FCRCustomerCardMaster;
import bdsm.model.FcrChAcctMast;
import bdsm.model.FcrCiCustmast;
import bdsm.model.TPIN;
import bdsm.model.TPINStatusMaster;
import bdsm.scheduler.PropertyPersister;
import bdsm.service.FCRCustomerCardService;
import bdsm.service.FCRCustomerService;
import bdsm.service.TPINService;
import bdsm.util.BdsmUtil;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.FCRCustomerCardMasterDAO;
import bdsmhost.dao.FcrCiCustmastDao;
import bdsmhost.dao.TPINDao;
import bdsmhost.dao.TPINStatusMasterDAO;


/**
 * 
 * @author bdsm
 */
public class TPINHostAction extends BaseHostAction {
	private static final DateFormat formatterTokenTime = new SimpleDateFormat("yyyyMMddhhmm");
	private static final DateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
	
	private static String adminEmail = PropertyPersister.adminEmail; // load PropertyPersister class
	private String cardNo;
	private TPIN model;
	private List<Map<String, Object>> modelList;
    /**
     * 
     */
    protected Session session;
	

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
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		this.getLogger().info("[Begin] doGet()");
		
		TPINService tpinService = new TPINService();
		tpinService.setTpinDAO(new TPINDao(this.getHSession()));
		this.model = tpinService.getTPINByCardNo(this.cardNo);
		
		this.getLogger().debug("TPIN model: " + this.model);
		this.getLogger().info("[ End ] doGet()");
		
		return TPINHostAction.SUCCESS;
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
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	/* Begin - getAccounts */
    /**
     * 
     * @return
     */
    public String retrieveAccounts() {
		this.getLogger().info("[Begin] retrieveAccounts()");

		try {
			if ((this.getToken() == null || "".equals(this.getToken())) && (!"0".equals(getTokenKey()))) {
				this.setJsonStatus(ActionSupport.ERROR);
				
				return ActionSupport.SUCCESS;
			}

			if (this.isTokenValid(this.getToken())) {
				try {
					this.session = HibernateUtil.getSession();
					this.setJsonStatus(doRetrieveAccounts());
					
					return ActionSupport.SUCCESS;
				} catch (Throwable e) {
					this.getLogger().fatal(e, e);
				} finally {
					HibernateUtil.closeSession(this.session);
				}
			}

			this.setJsonStatus(ActionSupport.ERROR);
		} finally {
			this.getLogger().info("[ End ] retrieveAccounts()");
		}

		return ActionSupport.SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    protected String doRetrieveAccounts() {
		this.modelList = new ArrayList<Map<String, Object>>();
		this.getLogger().info("[Begin] doRetrieveAccounts()");
		
		/* Customer Card */
		FCRCustomerCardService custCardService = new FCRCustomerCardService();
		custCardService.setFCRCustomerCardMasterDAO(new FCRCustomerCardMasterDAO(this.session));
		FCRCustomerCardMaster customerCard = custCardService.getByCardNo(this.cardNo);
		
		if (customerCard == null) {
			customerCard = new FCRCustomerCardMaster(this.cardNo, "A", PropertyPersister.codEntityVPD);
		}
		
		
		/* TPIN */
		TPINService tpinService = new TPINService();
		tpinService.setTpinDAO(new TPINDao(this.session));
		tpinService.setTpinStatusMasterDAO(new TPINStatusMasterDAO(this.session));
		
		TPIN tpin = tpinService.getTPINByCardNo(this.cardNo);
		TPINStatusMaster tpinStatus = null;
		
		if (tpin != null)
			tpinStatus = tpinService.getTPINStatusByCode(tpin.getIVRStatus());
		
		
		/* Customer Master */
		FCRCustomerService customerService = new FCRCustomerService();
		customerService.setFCRCustomerMasterDAO(new FcrCiCustmastDao(this.session));
		FcrCiCustmast customer = null; 
		
		/* Accounts */
		List<FcrChAcctMast> accounts = custCardService.getAccounts(customerCard);
		
		Map<String, Object> obj;
		if (accounts.size() == 0) {
			accounts = new java.util.ArrayList<FcrChAcctMast>();
			accounts.add(new FcrChAcctMast());
		}
		
		Iterator<FcrChAcctMast> it = accounts.iterator();
		while (it.hasNext()) {
			FcrChAcctMast acct = it.next();
			
			obj = new HashMap<String, Object>();
			obj.put("cardNo", this.cardNo); // card No
			obj.put("tpinStatus", 
					((tpin!=null) && (tpin.getIVRStatus()!=null))? 
							((tpinStatus!=null)? tpinStatus.getDescription(): tpin.getIVRStatus()): 
							"-"
					); // TPIN Status
			obj.put("tpinUpdatedDate", 
					((tpin!=null) && (tpin.getB24UpdatedTime()!=null))? 
							formatterDate.format(tpin.getB24UpdatedTime()): 
							"-"
					); // TPIN updated Date/Time
			obj.put("tpinUpdatedTime", 
					((tpin!=null) && (tpin.getB24UpdatedTime()!=null))? 
							formatterTime.format(tpin.getB24UpdatedTime()): 
							"-"
					);
			
			customer = (acct.getCodCust()!=null)? customerService.getByCustomerId(acct.getCodCust()): null;
			
			obj.put("cifNo", (customer!=null)? customer.getCompositeId().getCodCustId(): "-"); // CIF No
			obj.put("cifName", (customer!=null)? customer.getNamCustFull(): "-"); // Customer Name
			obj.put("accountNo", (acct.getCompositeId()!=null)? acct.getCompositeId().getCodAcctNo(): "-"); // Account No
			obj.put("accountName", (acct.getCodAcctTitle()!=null)? acct.getCodAcctTitle(): "-"); // Account Name
			
			this.modelList.add(obj);
		}
		
		this.getLogger().info("[ End ] doRetrieveAccounts()");
		
		return TPINHostAction.SUCCESS;
	}
	/* End - getAccounts */
	
	
    /**
     * 
     * @param token
     * @return
     */
    protected boolean isTokenValid(String token) {
		String tokenKey = getTokenKey();
		if (((token == null) || ("".equals(token))) && (!"0".equals(tokenKey)))
			return false;

		if (("0".equals(tokenKey)) && ("".equals(token)))
			return true;
		else {
			Calendar cal = Calendar.getInstance();
			Date dt = BdsmUtil.parseToken(tokenKey, token);
			Date sysdt = cal.getTime();
			long ndt = Long.parseLong(formatterTokenTime.format(dt));
			long nsysdt = Long.parseLong(formatterTokenTime.format(sysdt));
			long diff = Math.abs(ndt - nsysdt);

			getLogger().debug("dt : " + formatterTokenTime.format(dt));
			getLogger().debug("sysdt   : " + formatterTokenTime.format(sysdt));
			getLogger().debug("timezone: " + cal.getTimeZone());

			return diff < 3L;
		}
	}
	
	
    /**
     * 
     * @return
     */
    public String getCardNo() {
		return this.cardNo;
	}
    /**
     * 
     * @param cardNo
     */
    public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
    /**
     * 
     * @return
     */
    public TPIN getModel() {
		return this.model;
	}
    /**
     * 
     * @param model
     */
    public void setModel(TPIN model) {
		this.model = model;
	}
	
    /**
     * 
     * @return
     */
    public List<Map<String, Object>> getModelList() {
		return this.modelList;
	}
    /**
     * 
     * @param modelList
     */
    public void setModelList(List<Map<String, Object>> modelList) {
		this.modelList = modelList;
	}
	
}
