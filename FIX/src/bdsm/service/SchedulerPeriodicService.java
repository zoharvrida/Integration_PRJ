package bdsm.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import bdsm.model.Parameter;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralSchedulerDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.util.SchedulerUtil;
import bdsmhost.dao.ParameterDao;


/**
 * 
 * @author bdsm
 */
public class SchedulerPeriodicService {
	private static final Logger LOGGER = Logger.getLogger(SchedulerPeriodicService.class);
	private String namScheduler = "";
	private Integer idScheduler;
	private Session session;
	private Map context;
	private ParameterDao parameterDAO;
	private GeneralSchedulerDao generalSchedulerDAO;
	private FixSchedulerXtractDao xtractDAO;


    /**
     * 
     */
    public SchedulerPeriodicService() {}

    /**
     * 
     * @param namScheduler
     * @param idScheduler
     * @param session
     * @param context
     * @param parameterDAO
     * @param generalSchedulerDAO
     * @param xtractDAO
     */
    public SchedulerPeriodicService(String namScheduler, Integer idScheduler, Session session, Map context, ParameterDao parameterDAO, 
			GeneralSchedulerDao generalSchedulerDAO, FixSchedulerXtractDao xtractDAO) {
		this.namScheduler = namScheduler;
		this.idScheduler = idScheduler;
		this.session = session;
		this.context = context;
		this.parameterDAO = parameterDAO;
		this.generalSchedulerDAO = generalSchedulerDAO;
		this.xtractDAO = xtractDAO;
	}

    /**
     * 
     * @param session
     * @param context
     * @param parameterDAO
     * @param generalSchedulerDAO
     * @param xtractDAO
     */
    public SchedulerPeriodicService(Session session, Map context, ParameterDao parameterDAO, GeneralSchedulerDao generalSchedulerDAO, 
			FixSchedulerXtractDao xtractDAO) {
		this.session = session;
		this.context = context;
		this.parameterDAO = parameterDAO;
		this.generalSchedulerDAO = generalSchedulerDAO;
		this.xtractDAO = xtractDAO;
	}


    /**
     * 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void parameterRetrieval() {
		LOGGER.debug("CONTEXT_MAP :" + this.context);
		String typeFix = this.context.get(MapKey.typeFix).toString();
		
		try {
			this.namScheduler = this.context.get(MapKey.templateName).toString();
		}
		catch (Exception e) {
			if (this.idScheduler == null) {
				if (this.context.get(MapKey.fixQXtract) != null)
					this.idScheduler = ((FixQXtract) this.context.get(MapKey.fixQXtract)).getIdScheduler();
				else if (this.context.get(MapKey.idScheduler) != null)
					this.idScheduler = Integer.parseInt(this.context.get(MapKey.idScheduler).toString()); 
			}
			
			if (this.context.containsKey(MapKey.reportId))
				this.namScheduler = this.xtractDAO.get(this.idScheduler).getFixSchedulerPK().getNamScheduler();
			else if (StringUtils.isNotBlank((String) this.context.get(MapKey.param6)))
				this.namScheduler = this.context.get(MapKey.param6).toString();
		}

		List<Map> parameter = new ArrayList();
		List<Map> paramList = new ArrayList();

		parameter = this.parameterDAO.getFieldParameterbyModuleName(this.idScheduler, typeFix, 28, this.namScheduler);
		if (!parameter.isEmpty()) {
			for (Map o : parameter) {
				char flgMandatory = o.get("flgMandatory").toString().toUpperCase().charAt(0);
				String fieldName = o.get("fieldName").toString();
				String formatRule = o.get("formatRule").toString();
				
				switch(flgMandatory) {
					case 'A': {
							// hardcode
							o.put(fieldName, formatRule);
						}
						break;
					case 'B': {
							// java context parameter
							o.put(fieldName, this.context.get(formatRule));
						}
						break;
					case 'C': {
							// pure query database
							List<Map> data = new ArrayList<Map>();
							String queryData = o.get("query").toString();
							data = this.generalSchedulerDAO.pureQuery(queryData);
							if (!data.isEmpty())
								o.put(fieldName, data.get(0).get(fieldName));
						}
						break;
					case 'D': {
							// query database with field from context
							String dataQuery;
							String paramResult = null;
							if (StringUtils.isNotBlank((String) o.get("query"))) {
								dataQuery = o.get("query").toString();
								paramResult = individualParam(dataQuery, o.get("fieldName").toString());
							}
							// params.append(paramResult);
							o.put(fieldName, paramResult);
						}
						break;
					case 'E': {
							// query database with field from strval / value table
							String dataQuery;
							String paramResult = null;
							if (StringUtils.isNotBlank((String) o.get("query"))) {
								dataQuery = o.get("query").toString();
								paramResult = individualdbParam(dataQuery, o.get("fieldName").toString());
							}
							// params.append(paramResult);
							o.put(fieldName, paramResult);
						}
				}
				
				LOGGER.debug("MAP :" + o);
				paramList.add(o);
			}
			// all parameter has been completed.
		}
		
		parameter = this.parameterDAO.getFieldParameterbyModuleName(this.idScheduler, typeFix, 27, this.namScheduler);
		if (!parameter.isEmpty()) {
			String callfunc = this.generalSchedulerDAO.restructureProc(parameter.get(0).get("fieldName").toString(), paramList);
			this.generalSchedulerDAO.contextQuery(callfunc, paramList);
		}

	}

    /**
     * 
     * @param query
     * @param namefield
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String individualParam(String query, String namefield) {
		List<Map> tempParam = new ArrayList();
		String output = null;

		Object[] arrayVal = this.context.entrySet().toArray();
		LOGGER.debug("arrayVal.length: " + arrayVal.length);
		
		for (Object arrayVal1 : arrayVal) {
			LOGGER.debug("arrayVal1: " + arrayVal1);
			List keyOne = SchedulerUtil.getParameter(arrayVal1.toString(), "=");
			if (!keyOne.isEmpty()) {
				try {
					String replace = "\\{" + keyOne.get(0) + "\\}";
					if (String.class.isAssignableFrom(arrayVal1.getClass()))
						query = query.replaceAll(replace, "'" + keyOne.get(1).toString() + "'");
					else if (Number.class.isAssignableFrom(arrayVal1.getClass()))
						query = query.replaceAll(replace, keyOne.get(1).toString());
				}
				catch (Exception e) {
					LOGGER.debug("EXCEPTION_QUERY " + e, e);
				}
			}
		}
		tempParam = this.generalSchedulerDAO.pureQuery(query);
		if (!tempParam.isEmpty()) {
			try {
				output = tempParam.get(0).get(namefield).toString();
			}
			catch (Exception e) {
				LOGGER.debug("FAILED TO GET MAP " + e, e);
			}
		}
		return output;
	}

    /**
     * 
     * @param query
     * @param namefield
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String individualdbParam(String query, String namefield) {
		List<Parameter> tempParam = new ArrayList();
		List<Map> queryParam = new ArrayList<Map>();
		String output = null;
		
		tempParam = this.parameterDAO.getList();
		for (Parameter arrayVal1 : tempParam) {
			if (arrayVal1 != null) {
				try {
					String replace = "\\{" + arrayVal1.getCdParam() + "\\}";
					if (!"N".equalsIgnoreCase(arrayVal1.getTypParam())) {
						if (String.class.isAssignableFrom(arrayVal1.getClass()))
							query = query.replaceAll(replace, "'" + arrayVal1.getStrVal() + "'");
						else if (Number.class.isAssignableFrom(arrayVal1.getClass()))
							query = query.replaceAll(replace, arrayVal1.getCdParam());
					}
					else {
						if (Number.class.isAssignableFrom(arrayVal1.getClass()))
							query = query.replaceAll(replace, arrayVal1.getValue().toString());
					}

				}
				catch (Exception e) {
					LOGGER.debug("EXCEPTION_QUERY " + e, e);
				}
			}
		}
		queryParam = this.generalSchedulerDAO.pureQuery(query);
		if (!tempParam.isEmpty()) {
			try {
				output = queryParam.get(0).get(namefield).toString();
			}
			catch (Exception e) {
				LOGGER.debug("FAILED TO GET MAP " + e, e);
			}
		}
		
		return output;
	}

	/**
	 * @return the namScheduler
	 */
	public String getNamScheduler() {
		return namScheduler;
	}

	/**
	 * @param namScheduler the namScheduler to set
	 */
	public void setNamScheduler(String namScheduler) {
		this.namScheduler = namScheduler;
	}

	/**
	 * @return the idScheduler
	 */
	public Integer getIdScheduler() {
		return idScheduler;
	}

	/**
	 * @param idScheduler the idScheduler to set
	 */
	public void setIdScheduler(Integer idScheduler) {
		this.idScheduler = idScheduler;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * @return the context
	 */
	public Map getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Map context) {
		this.context = context;
	}
}
