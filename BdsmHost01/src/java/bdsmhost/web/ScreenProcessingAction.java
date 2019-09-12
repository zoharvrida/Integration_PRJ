/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.*;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.DefinitionConstant;
import bdsmhost.dao.*;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class ScreenProcessingAction extends BaseHostAction {

	private ScreenOpening so = new ScreenOpening();
	private EktpCoreMappingPK pk = new EktpCoreMappingPK();
	private EktpCoreMapping cm = new EktpCoreMapping();
    private MenuRedirect red = new MenuRedirect();
    private MenuRedirectionPK pkRed = new MenuRedirectionPK();
	private CifOpening CIF = new CifOpening();
	private CasaOpening CASA = new CasaOpening();
	private static final DateFormat formatterTokenTime = new SimpleDateFormat("yyyyMMddhhmm");
	private List modelList = new ArrayList();
	private Map<String, String> params;
	// variable Inquiry
	private String inqParam;
	private String param;
	private String inqNames;
	private String cdBranch;
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
		if ("".equals(cdBranch) || cdBranch == null) {
			return SUCCESS;
		} else {
			FcrBaCcBrnMastDao dao = new FcrBaCcBrnMastDao(getHSession());
			getLogger().info("Branch Code " + cdBranch);
			FcrBaCcBrnMast branch = dao.get(Integer.parseInt(cdBranch));

			try {
				this.getLogger().info("result " + branch.getNamBranch());
				so.setBranchCodeName(branch.getNamBranch());

			} catch (Exception e) {
				this.getLogger().info("Error GET BRANCH " + e, e);
			}
			return SUCCESS;

		}
	}

    /**
     * 
     * @return
     */
    public String menuRed(){
        getLogger().info("menu :" + pkRed.getSourceMenu().toString());
        MenuRedDao rDao = new MenuRedDao(getHSession());
        Map DefaultValue = new HashMap();
        MenuRedirect redirection = rDao.get(pkRed);
        this.red = redirection;
        rDao.evictObjectFromPersistenceContext(this.red);
		getLogger().info("ACTION MENU :" + red.getDefaultData());
		getLogger().info("Log pkRed = " + pkRed);

        try {
            List cdParams = bdsm.util.SchedulerUtil.getParameter(this.red.getDefaultData() == null ? "" : this.red.getDefaultData(), ",");
            this.so.setCdParam(DefinitionConstant.nullStat.equalsIgnoreCase(cdParams.get(0).toString()) ? "" : cdParams.get(0).toString());
            this.so.setCdParamAcct(DefinitionConstant.nullStat.equalsIgnoreCase(cdParams.get(1).toString()) ? "" : cdParams.get(1).toString());
        } catch (Exception e) {
            this.so.setCdParam("");
            this.so.setCdParamAcct("");
        }

        this.red.setDefaultData(this.so.getCdParam());

        try{
            DefaultValue = (Map) PropertyPersister.class.getDeclaredField(red.getDefaultData()).get(red.getDefaultData());           
            getLogger().debug("DEFAULT DATA MAP :" + DefaultValue);
			ClassConverterUtil.MapToClass(DefaultValue, so);
        } catch(Exception e){
            getLogger().info("FAILED GET DEFAULT DATA :" + e,e);
            return SUCCESS;
        }
		getLogger().info("DEFAULT VAL :" + DefaultValue);
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String ktpValChecking() {
		getLogger().info("PK :" + pk.toString());
		EktpCoreMappingDao ktpDao = new EktpCoreMappingDao(getHSession());

		this.cm = ktpDao.get(pk);

		/* Revision : Slow Respond when Data Not Found
             Change Date : 07-Januari-2016   
             Changer : v00019722 
			 Begin 1 */
		if(this.cm == null){
			return SUCCESS;
		}
		/* End 1*/
		getLogger().info("INQPARAM :" + this.inqParam);
		getLogger().info("param :" + this.cm.getCoreVal());
		if (null == this.inqParam) {
			this.inqParam = "";
		}

		if (this.cm.getCoreVal() != null) {
			if (!inqParam.equalsIgnoreCase("")) {
				inqNames = "Mowdrowdown";
			}
			CifOpeningDao cifDao = new CifOpeningDao(getHSession());

			getLogger().info("names :" + inqNames);
			modelList = cifDao.getUdfVal(inqParam, this.cm.getCoreVal().trim(), inqNames);
			getLogger().info("LIST :" + modelList);
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
    public String casaCreation() {
		getLogger().info("IC TYPE :" + this.so.getFlgIctype());
        getLogger().debug("SINGLESCREEN : " + this.so.toString());
		this.CIF = (CifOpening) ClassConverterUtil.ReferenceClass(this.so, this.CIF);
		this.CASA = (CasaOpening) ClassConverterUtil.ReferenceClass(this.so, this.CASA);
        Integer retry = 0;
        boolean flagRetry = true;
        String Account = null;
		Map DefaultValue = new HashMap();


		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFBack = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat datenoPAdding = new SimpleDateFormat("yyyyMMdd");
		Date AccDate = null;
		try {
            synchronized (dateFormat) {
			AccDate = dateFormat.parse(CASA.getAccountOpenDate().toString());
            }
			this.CASA.setAccountOpenDateConvertion(AccDate);
            this.so.setAccountOpenDateConvertion(AccDate);
		} catch (ParseException ex) {
			getLogger().info("Failed Parse Date :" + ex);
			try {
				this.CASA.setAccountOpenDateConvertion((Date) SchedulerUtil.getTime());
                this.so.setAccountOpenDateConvertion(this.CASA.getAccountOpenDateConvertion());
				getLogger().info("CONVERT SUCCESSFULLY");
			} catch (Exception e) {
				getLogger().info("Failed Parse Date 2nd:" + ex);
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.add(Calendar.DATE, 0);
				this.CASA.setAccountOpenDateConvertion(calendar.getTime());
			}
		}

		CifOpeningDao cifDao = new CifOpeningDao(getHSession());
		CasaOpeningDao casaDao = new CasaOpeningDao(getHSession());
		Transaction tx = getHSession().beginTransaction();
		List<CifOpening> cifExisting = new ArrayList();
		String dateconverter = null;
		try {
			getLogger().info("flag :" + so.getFlagCIF());
			try {
				if (!getSo().getFlagCIF()) {
                    CIF.setIncr(1);
				} else {
					// cif existing
					getLogger().info("CIF EXISTING");
					try {
						cifExisting = cifDao.getNum(so.getCustId());
						if (cifExisting.get(0).getIncr() == null) {
							CIF.setIncr(1);
						} else {
							CIF.setIncr(cifExisting.get(0).getIncr());
						}
					} catch (Exception e) {
						getLogger().info("CIF EXISTING ex :"+e,e);
						CIF.setIncr(1);
					}
				}
				CIF.setFlgProcess(so.getFlgProcess());
				// -- convert date birth to number
				getLogger().info("DATE OF BIRTH :" + so.getBirthDate());
				try {
                    Date convDate = null;
                    synchronized (dateFBack) {
                        convDate = dateFBack.parse(so.getBirthDate());
					dateconverter = datenoPAdding.format(convDate);
                    }
					this.CIF.setBirthDate(dateconverter);
					getLogger().info("Date Of Birth CONVERTED:" + dateconverter);
				} catch (ParseException parseException) {
					getLogger().info("Failed Parse Date Of Birth:" + parseException, parseException);
					//-- convert manual
				}
				cifDao.insert(CIF);
				tx.commit();
			} catch (Exception e) {
				getLogger().info("FAILED INSERT CIF");
				getLogger().info("REASON :" + e, e);
                this.setJsonStatus(ERROR);
				this.setErrorCode("26301.error.insert");
				return ERROR;
			}
			try {
				tx = getHSession().beginTransaction();
				try {
					DefaultValue = (Map) PropertyPersister.class.getDeclaredField(so.getCdParamAcct()).get(so.getCdParamAcct());
					//DefaultValue = PropertyPersister.casaDefAcctMap;
					ClassConverterUtil.MapToClass(DefaultValue, CASA);
				} catch (Exception e) {
					getLogger().info("FAILED TO GET DEFAULT DATA");
					getLogger().debug(e, e);
				}
				CASA.setFlgProcess("N");
				casaDao.insert(CASA);
				tx.commit();
			} catch (Exception e) {
				getLogger().info("FAILED INSERT CASA");
				getLogger().info("REASON :" + e, e);
                this.setJsonStatus(ERROR);
				this.setErrorCode("26301.error.insert");
				return ERROR;
			}
			if (!getSo().getFlagCIF()) {
				// CIF Existing
				try {
					CIF.setCifNumber(cifDao.runCIF(so.getNik(), so.getApplicationID()));
					this.so.setCifNumber(CIF.getCifNumber());
					getLogger().info("CIF NUMBER :" + this.so.getCifNumber());
				} catch (HibernateException hibernateException) {
					getLogger().info("FAILED TO INSERT CIF :" + hibernateException, hibernateException);
                    this.setJsonStatus(ERROR);
					this.setErrorCode("26301.error.CallFunction");
                    return ERROR;
				}
			}
			getLogger().info("END DATE PARSE :" + this.CASA.getAccountOpenDateConvertion());
            while (retry.compareTo(PropertyPersister.maxRetry) < 0 && flagRetry) {
                FcrChAcctMastDao chDao = new FcrChAcctMastDao(getHSession());
                Account = casaDao.runCASA(so.getCifNumber(), so.getProductCode(), so.getBranchCodeCasa(), so.getApplicationID());
                retry++;
                Thread.sleep(2000);
                FcrChAcctMastPK pk = new FcrChAcctMastPK();
                pk.setCodAcctNo(Account);
                pk.setFlgMntStatus("A");
                if (chDao.get(pk) != null) {
                    flagRetry = false;
                    retry = PropertyPersister.maxRetry;
                }
            }
            if (flagRetry) {
                this.setJsonStatus(ERROR);
                this.setErrorCode("26301.error.CasaFailed");
                return ERROR;
            } else {
			getLogger().info("Account :" + Account);
			this.CASA = casaDao.get(CIF.getApplicationID());
			getLogger().info("MODEL :" + this.CASA.toString());
			this.so.setAcctNo(Account);
			this.so.setCifNumber(so.getCifNumber());
                this.so.setRecComment(CASA.getComments());
			getLogger().info("CIF :" + this.so.getCifNumber());
			getLogger().info("CASA :" + this.so.getAcctNo());
            getLogger().info("SO LAST :" + this.so.toString());
			if (!getSo().getFlagCIF()) {
				this.setErrorCode("26301.success.CIFCasa");
			} else {
				this.setErrorCode("26301.success.Casa");
			}

            if (!DefinitionConstant.singleScreenAlias.equalsIgnoreCase(so.getExtUser())) {
                try {
                    tx = getHSession().beginTransaction();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH.mm.SSS");
                    CifCasaExtOpeningDao extDao = new CifCasaExtOpeningDao(getHSession());
                    extDao.evictObjectFromPersistenceContext(CIF);
                    this.so.setStatus("4");
                    this.so.setRecStat("D");
                    this.so.setBirthDate(so.getBirthDate().replaceAll("/", ""));
                    this.so.setDtmFinishProc(SchedulerUtil.getTime());

                    try {
                        this.so.setDtmCreated(new java.sql.Timestamp(df.parse(this.so.getDtmStartConverter()).getTime()));
                        this.so.setDtmProc(new java.sql.Timestamp(df.parse(this.so.getDtmProcConverter()).getTime()));
                    } catch (ParseException parseException) {
                        getLogger().debug("PARSE FAILED :" + parseException,parseException);
                        this.so.setDtmCreated(null);
                        this.so.setDtmProc(null);
                    }

                    extDao.update(so);
                    tx.commit();
                    tx = getHSession().beginTransaction();
                    List<ScreenOpening> updateSO = extDao.getUpdateCIF(so.getNik());

                    try {
                        if (!updateSO.isEmpty()) {
                            for (ScreenOpening lso : updateSO) {
                                if (lso != null) {
                                    //lso.setCifNumber(this.so.getCifNumber());
                                }
                            }
                        }
                        tx.commit();
                    } catch (Exception ex) {
                        getLogger().info("FAILED LOOP TO CIF :" + ex,ex);
                    }
                } catch (Exception exception) {
                    getLogger().info("FAILED UPDATE TABLE CASA :" + exception, exception);
                }
            }
            }

            // update to table EXT_IB

		} catch (Exception e) {
			getLogger().info("EXCEPTION :" + e, e);
            this.setJsonStatus(ERROR);
            this.setErrorCode("26301.error.CallFunction");
			return SUCCESS;
		}
		return SUCCESS;
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
    public ScreenOpening getModel() {
		return this.getSo();
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
	 * @return the CIF
	 */
	public CifOpening getCIF() {
		return CIF;
	}

	/**
	 * @param CIF the CIF to set
	 */
	public void setCIF(CifOpening CIF) {
		this.CIF = CIF;
	}

	/**
	 * @return the CASA
	 */
	public CasaOpening getCASA() {
		return CASA;
	}

	/**
	 * @param CASA the CASA to set
	 */
	public void setCASA(CasaOpening CASA) {
		this.CASA = CASA;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the cdBranch
	 */
	public String getCdBranch() {
		return cdBranch;
	}

	/**
	 * @param cdBranch the cdBranch to set
	 */
	public void setCdBranch(String cdBranch) {
		this.cdBranch = cdBranch;
	}

	/**
	 * @return the inqParam
	 */
	public String getInqParam() {
		return inqParam;
	}

	/**
	 * @param inqParam the inqParam to set
	 */
	public void setInqParam(String inqParam) {
		this.inqParam = inqParam;
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * @return the inqNames
	 */
	public String getInqNames() {
		return inqNames;
	}

	/**
	 * @param inqNames the inqNames to set
	 */
	public void setInqNames(String inqNames) {
		this.inqNames = inqNames;
	}

	/**
	 * @return the modelList
	 */
	public List getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List modelList) {
		this.modelList = modelList;
	}

	/**
	 * @return the pk
	 */
	public EktpCoreMappingPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(EktpCoreMappingPK pk) {
		this.pk = pk;
	}

	/**
	 * @return the cm
	 */
	public EktpCoreMapping getCm() {
		return cm;
	}

	/**
	 * @param cm the cm to set
	 */
	public void setCm(EktpCoreMapping cm) {
		this.cm = cm;
	}

    /**
     * @return the red
     */
    public MenuRedirect getRed() {
        return red;
    }

    /**
     * @param red the red to set
     */
    public void setRed(MenuRedirect red) {
        this.red = red;
    }

    /**
     * @return the pkRed
     */
    public MenuRedirectionPK getPkRed() {
        return pkRed;
    }

    /**
     * @param pkRed the pkRed to set
     */
    public void setPkRed(MenuRedirectionPK pkRed) {
        this.pkRed = pkRed;
    }
}
