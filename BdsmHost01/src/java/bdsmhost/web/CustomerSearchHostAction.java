package bdsmhost.web;

import bdsm.model.CoCiCustmast;
import bdsm.model.CoCiCustmastPK;
import bdsm.model.ScreenOpening;
import bdsm.scheduler.PropertyPersister;
import bdsm.service.FCRCustomerService;
import bdsm.service.SingleScreenOPService;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.DefinitionConstant;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.CifCasaExtOpeningDao;
import bdsmhost.dao.CifOpeningDao;
import bdsmhost.dao.FcrCiCustmastDao;
import bdsmhost.dao.MenuRedDao;
import bdsmhost.dao.ParameterDao;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import oracle.sql.TIMESTAMP;
import org.hibernate.Session;

/**
 * 
 * @author bdsm
 */
public class CustomerSearchHostAction extends BaseHostAction {

	private static final DateFormat formatterTokenTime = new SimpleDateFormat("yyyyMMddhhmm");
	private static final DateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy");
	private String customerName;
	private Date dateofBirth;
	private String noKartuIden;
	private String noCard;
	private CoCiCustmast model;
	private CoCiCustmastPK modelPk;
	private List<CoCiCustmast> modelQ;
	private List<CoCiCustmast> modelMaster;
	private List<Map<String, Object>> modelList;
    /**
     * 
     */
    protected Session session;
	private String custName;
	private String cifNo;
	private String flagsCIF;
    private String prodExcluder;
    private String sourceMenu;
	private ScreenOpening so = new ScreenOpening();

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
    public String extList() {
        getLogger().info("APPLICATION NO :" + so.getApplicationID());
        getLogger().debug("SO APP 1:" + this.so.toString());
        this.so.setRecStat("P");
        CifCasaExtOpeningDao cDao = new CifCasaExtOpeningDao(getHSession());
        List<ScreenOpening> extChannel = cDao.getExtChannelData(so);
        if (!extChannel.isEmpty()) {
            this.so = extChannel.get(0);
            this.so.setFlagCIF(Boolean.FALSE);
            this.so.setDtmProc(SchedulerUtil.getTime());
            getLogger().debug("SO APP :" + this.so.toString());
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String inList() {
        getLogger().info("CIF NO :" + this.getCifNo());
        getLogger().info("PARAM PROD :" + this.prodExcluder);
        Integer realCIF = null;
        try {
            realCIF = Integer.parseInt(this.cifNo);
        } catch (NumberFormatException e) {
            getLogger().info("NON NUMBER :" + this.cifNo);
            getLogger().info("EX :" + e, e);
        }
        
        Map queryParam = PropertyPersister.casaMap;
        getLogger().info("MAPHASIL :" + queryParam);
        getLogger().info("[BEGIN] QUERY EXISTING NCBS DATA");
        CifOpeningDao cifDao = new CifOpeningDao(getHSession());
        if (DefinitionConstant.singleScreenAlias.equalsIgnoreCase(this.so.getExtUser())) {
            modelList = cifDao.getExixtingCIF(realCIF, queryParam, null);
        } else {
            modelList = cifDao.getExixtingCIF(realCIF, queryParam, so.getApplicationID());
        }
        if (!modelList.isEmpty()) {
        ClassConverterUtil.MapToClass(modelList.get(0), this.so, true);
        getLogger().info("Log SO = " + this.so);
        getLogger().info("Log Modelist = " + modelList.get(0));
        }
        this.so.setFlagCIF(true);
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		getLogger().info("CIF NO :" + this.getCifNo());
        getLogger().info("PARAM PROD :" + this.prodExcluder);
        Integer realCIF = null;
        try {
            realCIF = Integer.parseInt(this.cifNo);
        } catch (NumberFormatException e) {
            getLogger().info("NON NUMBER :" + this.cifNo);
            getLogger().info("EX :" + e,e);
        }
		CifOpeningDao cifDao = new CifOpeningDao(getHSession());


		if ("EXISTING".equalsIgnoreCase(flagsCIF)) {
			getLogger().info("[BEGIN] QUERY EXISTING");
            String DefaultValue;
            try {
                DefaultValue = PropertyPersister.class.getDeclaredField(this.prodExcluder).get(this.prodExcluder).toString();
                modelList = cifDao.getExisting(realCIF,DefaultValue);
            } catch (Exception e) {
                getLogger().info("FAILED GET DEFAULT Exclude PROD :" + e, e);
                modelList = cifDao.getExisting(realCIF,"0");
            }         
			getLogger().info("[END] QUERY EXISTING :" + modelList);
		} else {
            if (!DefinitionConstant.singleScreenAlias.equalsIgnoreCase(this.flagsCIF)) {
                CifCasaExtOpeningDao casaExtDao = new CifCasaExtOpeningDao(getHSession());
                ParameterDao paramDao = new ParameterDao(getHSession());
                MenuRedDao menuDao = new MenuRedDao(getHSession());

                SingleScreenOPService ssService = new SingleScreenOPService();
                ssService.setCifAcctExtDao(casaExtDao);
                ssService.setParamDao(paramDao);
                ssService.setMenuDao(menuDao);
                
                // check if Data is CIF Existing
                if (!"0".equalsIgnoreCase(this.cifNo)) {
                    // cifExisting
                    getLogger().debug("CIF :" + cifNo);
                    Map queryParam = PropertyPersister.casaMap;
                    modelList = casaExtDao.getLeftOverCIF(realCIF, queryParam, so.getApplicationID());
                } else {
                    this.so.setCifNumber(this.so.getCifNumber() == null?0:this.so.getCifNumber());
                    getLogger().debug("MENU :" + sourceMenu + " - " + this.flagsCIF);
                modelList = ssService.listPreCasabyChannel(so);
                    getLogger().debug("MODEL LIST :" + modelList.size());
                }
            }
			getLogger().info("[END] QUERY EXISTING END :" + modelList);
            if(!modelList.isEmpty()){
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH.mm.sss");
                Object[] listObj = {modelList.get(0)};
                String[] nameless = {""};
                String[] prefix = {"", ""};

                Map listScreen = ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1);
                ClassConverterUtil.MapToClass(listScreen, this.so);

                Date dtmProcess = this.so.getDtmProc();

                getLogger().info("[END] SO :" + this.so);
            }
			this.so.setFlagCIF(true);
		}
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

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
        throw new UnsupportedOperationException("Not supported yet.");
	}


	/*
	 * Begin - getAccounts
	 */
    /**
     * 
     * @return
     */
    public String customerSearch() {
		this.getLogger().info("[Begin] CustomerSearch()");

		getLogger().info("DATE BIRTH " + dateofBirth);
		getLogger().info("CARD ATM " + getNoCard());
        getLogger().info("NIK CARD " + this.getNoKartuIden());

		getLogger().info("TOKEN " + this.getToken());
		try {
			if ((this.getToken() == null || "".equals(this.getToken())) && (!"0".equals(getTokenKey()))) {
				this.setJsonStatus(ActionSupport.ERROR);

				return ActionSupport.SUCCESS;
			}

			if (this.isTokenValid(this.getToken())) {
				try {
					this.session = HibernateUtil.getSession();

					this.setJsonStatus(doCustomerSearch());
					return ActionSupport.SUCCESS;
				} catch (Throwable e) {
					this.getLogger().fatal(e, e);
				} finally {
					HibernateUtil.closeSession(this.session);
				}
			}
			this.setJsonStatus(ActionSupport.ERROR);
		} finally {
			this.getLogger().info("[ End ] CustomerSearch()");
		}

		return ActionSupport.SUCCESS;
	}

    /**
     * 
     * @return
     * @throws ParseException
     */
    protected String doCustomerSearch() throws ParseException {
		this.setModelList(new ArrayList<Map<String, Object>>());
		this.getLogger().info("[Begin] doCustomerSearch()");

		/*
		 * Customer Card
		 */

        //FCRCustomerService customerService = new FCRCustomerService();
        SingleScreenOPService ssOpService = new SingleScreenOPService();
        
        ssOpService.setCifAcctExtDao(new CifCasaExtOpeningDao(this.session));
        ssOpService.setCustomerMasterDAO(new FcrCiCustmastDao(this.session));

		modelPk = new CoCiCustmastPK();
		model = new CoCiCustmast();
        /* Revision : Slow Respond when Data Inquiry by Name and DOB
         Change Date : 07-Januari-2016   
         Changer : v00019722 
         Begin 1 */
		try {
			this.getLogger().info(this.getCustomerName().toString());

			model.setNamCustFirst(this.getCustomerName());
            if (this.getNoKartuIden() != null) {
                modelPk.setNik(this.getNoKartuIden());
            }
		} catch (Exception ex) {
            getLogger().info("NO CUSTOMER NAME");
			try {
				modelPk.setNik(this.getNoKartuIden());
				this.getLogger().info("NIK " + this.getNoKartuIden());
			} catch (Exception e) {
                getLogger().info("NIK UNAVAILABLE");
            }
			}
        try {
            modelPk.setDateofBirth(this.getDateofBirth());
        } catch (Exception e) {
            getLogger().info("NO DATE OF BIRTH");
		}
        /* End 1*/
		this.model.setCompositeId(modelPk);

		List<CoCiCustmast> customer = new ArrayList<CoCiCustmast>();
		List<CoCiCustmast> custByNoCard = new ArrayList<CoCiCustmast>();
        List<CoCiCustmast> custByExt = new ArrayList<CoCiCustmast>();

        customer = ssOpService.listCasa(model);
		getLogger().info("Customer " + customer.size());

		if (customer.isEmpty()) {
            custByNoCard = ssOpService.listCasaByNoCard(getNoCard());
			getLogger().info("CustCard " + custByNoCard);
		}
		getLogger().debug("MODEL NIKS :" + model.toString());
        //custByExt = customerService.listCasaByExtChannel(model);

        if(this.getNoKartuIden() == null){
            custByExt = ssOpService.listCasaByExtChannel(model);
            getLogger().info("CUST EXT " + custByExt.size());
        }

        /*if (model.getCompositeId().getDateofBirth() != null) {
            if (customer.isEmpty()) {
            custByExt = ssOpService.listCasaByExtChannel(model);
            } else {
                List<CoCiCustmast> tmpcustByExt = new ArrayList<CoCiCustmast>();
                List<CoCiCustmast> tmpcustomer = new ArrayList<CoCiCustmast>();


                tmpcustByExt = ssOpService.listCasaByExtChannel(model);
                // check if Customer already exist in CoreBanking
                /*if (!tmpcustByExt.isEmpty()) {
                    for (CoCiCustmast co : tmpcustByExt) {
                        if (co != null) {
                            // checking for exact nik and Flagback

                            getLogger().info("EXT HOST :" + co.getExtFlag());
                            for (int i = 0;i<customer.size();) {
                                if (customer.get(i) != null) {
                                    if (co.getCompositeId().getNik().equalsIgnoreCase(customer.get(i).getCompositeId().getNik())) {
                                        CoCiCustmast ci = co;
                                        getLogger().info("LIST NUMBER :" + i);
                                        getLogger().info("EXT INNER :" + ci.getExtFlag());
                                        ci.setExtFlag(co.getExtFlag());

                                        tmpcustomer.add(ci);
                                        customer.remove(i);
                                    }
                                    i++;
                                }
                            }
                        }
                    }
                    customer.addAll(tmpcustomer);
                }

            }

        }*/

        
        getLogger().info("CUST EXT " + custByExt.size());

		/*
		 * Customer Master
		 */
		Map<String, Object> obj;

		Iterator<CoCiCustmast> it = null;
		try {
			if (customer.isEmpty()) {
				try {
                    if(custByNoCard.isEmpty()){
                        getLogger().debug("CUSTOMERS :" + custByExt.size());
                        it = custByExt.iterator();
                    } else {
                        if(custByExt.isEmpty()){
                            it = custByNoCard.iterator();
                        } else {
                            getLogger().debug("CUSTOMERS DOUBLE :" + custByExt.size());
                            customer.addAll(custByExt);
                            customer.addAll(custByNoCard);
                            it = customer.iterator();
                        }
                    }
				} catch (Exception e) {
                    this.getLogger().info("EXCEPTION FOR DDF" + e, e);
                    // DDF FOR IT
                    //it = custByExt.iterator();
				}
			} else {
                if (!custByExt.isEmpty()) {
					getLogger().info("CUSTEXT :" + custByExt.size());
                    customer.addAll(custByExt);
                } else {
					getLogger().info("??????? :" + custByExt.size());
				}
				it = customer.iterator();
			}
		} catch (Exception e) { 
			this.getLogger().info("Error Iterator" + e, e);
			if (custByNoCard.isEmpty()) {
				getLogger().info("Size card 1" + custByNoCard.size());
				custByNoCard = new java.util.ArrayList<CoCiCustmast>();
				custByNoCard.add(new CoCiCustmast());
			} else {
				getLogger().info("Size card 2" + custByNoCard.size());
				it = custByNoCard.iterator();
				getLogger().info("Iterator " + it);
			}
		}
		try {
			CoCiCustmast cust = new CoCiCustmast();
            Integer count = 0;
			while (it.hasNext()) {
				this.getLogger().info("customerName " + it.getClass());
				cust = it.next();
                this.getLogger().info("CLASS : " + cust.toString());
				obj = new HashMap<String, Object>();
				try {
                    count++;
					this.getLogger().info("Data " + cust.getNamCustFull());
                    obj.put("no", count.toString());
					obj.put("customerName", this.getCustomerName()); // card No
					obj.put("fullName", (cust.getNamCustFull() != null) ? cust.getNamCustFull() : "-");
					obj.put("address", (cust.getAddress() != null) ? cust.getAddress() : "-");
					obj.put("custId", (cust.getCodCustId() != null) ? cust.getCodCustId() : "-");
					obj.put("nik", (cust.getCompositeId().getNik() != null) ? cust.getCompositeId().getNik() : "-");
					obj.put("dob", (cust.getCompositeId().getDateofBirth() != null) ? cust.getCompositeId().getDateofBirth() : "-");
                    obj.put("extFlag", (cust.getExtFlag() != null) ? cust.getExtFlag() : DefinitionConstant.singleScreenAlias);

					this.getModelList().add(obj);
				} catch (Exception e) {
					getLogger().info("EXCEPTION :" + e,e);
					getLogger().info("DATA NOT FOUND");
					this.getModelList().clear();
				}
			}
		} catch (Exception e) {
			getLogger().info("Mapping data" + e, e);
		}

		this.getLogger().info("Model List " + modelList.size());
		this.getLogger().info("[ End ] doCustomerSearch()");

		return CustomerSearchHostAction.SUCCESS;
	}

    /**
     * 
     * @param token
     * @return
     */
    protected boolean isTokenValid(String token) {
		String tokenKey = getTokenKey();
		if (((token == null) || ("".equals(token))) && (!"0".equals(tokenKey))) {
			return false;
		}

		if (("0".equals(tokenKey)) && ("".equals(token))) {
			return true;
		} else {
			Calendar cal = Calendar.getInstance();
			Date dt = (Date) BdsmUtil.parseToken(tokenKey, token);
			Date sysdt = (Date) cal.getTime();
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
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the modelList
	 */
	public List<Map<String, Object>> getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<Map<String, Object>> modelList) {
		this.modelList = modelList;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return the dateofBirth
	 */
	public Date getDateofBirth() {
		return dateofBirth;
	}

	/**
	 * @param dateofBirth the dateofBirth to set
	 */
	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	/**
	 * @return the noKartuNik
	 */
	public String getNoKartuIden() {
		return noKartuIden;
	}

	/**
     * @param noKartuIden 
	 */
	public void setNoKartuIden(String noKartuIden) {
		this.noKartuIden = noKartuIden;
	}

	/**
	 * @return the model
	 */
	public CoCiCustmast getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(CoCiCustmast model) {
		this.model = model;
	}

	/**
	 * @return the modelQ
	 */
	public List<CoCiCustmast> getModelQ() {
		return modelQ;
	}

	/**
	 * @param modelQ the modelQ to set
	 */
	public void setModelQ(List<CoCiCustmast> modelQ) {
		this.modelQ = modelQ;
	}

	/**
	 * @return the modelMaster
	 */
	public List<CoCiCustmast> getModelMaster() {
		return modelMaster;
	}

	/**
	 * @param modelMaster the modelMaster to set
	 */
	public void setModelMaster(List<CoCiCustmast> modelMaster) {
		this.modelMaster = modelMaster;
	}

	/**
	 * @return the modelPk
	 */
	public CoCiCustmastPK getModelPk() {
		return modelPk;
	}

	/**
	 * @param modelPk the modelPk to set
	 */
	public void setModelPk(CoCiCustmastPK modelPk) {
		this.modelPk = modelPk;
	}

	/**
	 * @return the cifNo
	 */
	public String getCifNo() {
		return cifNo;
	}

	/**
	 * @param cifNo the cifNo to set
	 */
	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}

	/**
	 * @return the flagsCIF
	 */
	public String getFlagsCIF() {
		return flagsCIF;
	}

	/**
	 * @param flagsCIF the flagsCIF to set
	 */
	public void setFlagsCIF(String flagsCIF) {
		this.flagsCIF = flagsCIF;
	}

	/**
	 * @return the so
	 */
	public ScreenOpening getSo() {
		return so;
	}

	/**
	 * @param so the so to set
	 */
	public void setSo(ScreenOpening so) {
		this.so = so;
	}

	/**
	 * @return the noCard
	 */
	public String getNoCard() {
		return noCard;
	}

	/**
	 * @param noCard the noCard to set
	 */
	public void setNoCard(String noCard) {
		this.noCard = noCard;
	}

	/**
	 * @return the prodExcluder
	 */
	public String getProdExcluder() {
		return prodExcluder;
	}

	/**
	 * @param prodExcluder the prodExcluder to set
	 */
	public void setProdExcluder(String prodExcluder) {
		this.prodExcluder = prodExcluder;
	}

    /**
     * @return the sourceMenu
     */
    public String getSourceMenu() {
        return sourceMenu;
    }

    /**
     * @param sourceMenu the sourceMenu to set
     */
    public void setSourceMenu(String sourceMenu) {
        this.sourceMenu = sourceMenu;
    }
}
