/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bdsm.model.Parameter;
import bdsm.model.ScreenMsg;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.util.SchedulerUtil;
import bdsmhost.dao.ParameterDao;
import bdsmhost.dao.ScreenMsgDao;


/**
 * 
 * @author 00110310
 */
public class ScreenMessageAction extends BaseHostAction {
	private String tag;
	private String values;
	private Integer valuesInt;
	private ScreenMsg model;
	private String SINGLE = "S";
	private String MULTI = "M";

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		this.getLogger().debug("TAG :" + getTag());
		
		try {
			this.values = PropertyPersister.class.getDeclaredField(tag).get(null).toString();
		} catch (Exception e) {
			this.getLogger().debug(e);
			this.getLogger().debug("NOT PROPERTY PERSISTER");
			
			ParameterDao paramDao = new ParameterDao(getHSession());
			try {
				Parameter param = paramDao.get(tag);
				if ("S".equalsIgnoreCase(param.getTypParam()))
					this.values = param.getStrVal();
				else if ("N".equalsIgnoreCase(param.getTypParam()))
					this.valuesInt = param.getValue();
				else
					doGetMessage();
			}
			catch (Exception eX) {
				this.getLogger().debug(eX);
				this.getLogger().debug("NOT REALLY PROPERTY");
				
				try {
					doGetMessage();
				}
				catch (Exception es) {
					this.getLogger().debug("ERR :" + es, es);
				}
			}
		}
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String doGetMessage() {
		this.getLogger().debug("TAG : " + getTag());
		
		ScreenMsgDao dao = new ScreenMsgDao(getHSession());
		String fieldParam = null;
		List<String> paramlist = new ArrayList();
		
		
		this.setModel(dao.get(getTag()));
		Map fieldMap = new HashMap();
		int incrDt = 1;
		String appendMessage = null;
		getLogger().debug("MODEL : " + this.model);
		
		if (this.getModel() != null) {
			if (this.getModel().getNumParam() == null) {
				return SUCCESS;
			}
			else {
				if (SINGLE.equalsIgnoreCase(this.model.getTypParam())) {
					// using single parameter
					try {
						try {
							// Delimiter non Map
							fieldParam = PropertyPersister.class.getDeclaredField(this.model.getCdParam()).get(null).toString();
							paramlist = SchedulerUtil.getParameter(fieldParam, this.model.getDelimiter());
							
							appendMessage = this.model.getMessage();
							for (String objMap : paramlist) {
								if (objMap != null) {
									appendMessage = appendMessage.replaceAll("\\{[P|p][A|a][R|r][A|a][M|m]" + incrDt + "\\}", objMap);
									incrDt++;
								}
							}
						}
						catch (Exception ex) {
							// Map type delimiter
							paramlist = new ArrayList();
							incrDt = 1;
							this.getLogger().debug("NOT String Property Persister");
							this.getLogger().debug(ex);
							
							ParameterDao pDao = new ParameterDao(getHSession());
							Parameter param = pDao.get(this.model.getCdParam());
							this.getLogger().debug("PARAMETER :" + param);
							if ("S".equalsIgnoreCase(param.getTypParam())) {
								try {
									paramlist = SchedulerUtil.getParameter(param.getStrVal(), this.model.getDelimiter());
								} catch (Exception e) {
									getLogger().debug("EXLIST :" + e,e);
									paramlist.add(param.getValue().toString());
								}
							}
							else if ("N".equalsIgnoreCase(param.getTypParam())) {
								paramlist.add(param.getValue().toString());
							}
							
							appendMessage = null;
							appendMessage = this.model.getMessage();
							this.getLogger().debug("MESSAGE :" + appendMessage);
							for (String objMap : paramlist) {
								if (objMap != null) {
									appendMessage = appendMessage.replaceFirst("\\{[P|p][A|a][R|r][A|a][M|m]" + incrDt + "\\}", objMap);
									incrDt++;
								}
							}
							this.getLogger().debug("MESSAGE after :" + appendMessage);
							this.model.setMessage(appendMessage);
						}
					}
					catch (Exception ex) {
						this.getLogger().debug("NOT Property Persister");
						this.getLogger().debug(ex, ex);
						
						ParameterDao pDao = new ParameterDao(getHSession());
						Parameter param = pDao.get(this.model.getCdParam());
						if (param != null) {
							fieldParam = "N".equals(param.getTypParam()) ? param.getValue().toString() : param.getStrVal();
							appendMessage = appendMessage + " " + fieldParam;
						}
					}
				}
				else if (MULTI.equalsIgnoreCase(this.model.getTypParam())) { }
			}
		}
		else {
			this.model = new ScreenMsg();
			this.model.setMessage("Failed to Retreive Message");
			this.getLogger().debug("FAILED TO RETREIVE");
		}
		dao.evictObjectFromPersistenceContext(this.model);
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the values
	 */
	public String getValues() {
		return this.values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(String values) {
		this.values = values;
	}

	/**
	 * @return the valuesInt
	 */
	public Integer getValuesInt() {
		return this.valuesInt;
	}

	/**
	 * @param valuesInt the valuesInt to set
	 */
	public void setValuesInt(Integer valuesInt) {
		this.valuesInt = valuesInt;
	}

    /**
     * 
     * @return
     */
    public ScreenMsg getModel() {
		return this.model;
	}

    /**
     * 
     * @param model
     */
    public void setModel(ScreenMsg model) {
		this.model = model;
	}
}
