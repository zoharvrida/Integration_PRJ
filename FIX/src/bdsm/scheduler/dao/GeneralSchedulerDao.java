package bdsm.scheduler.dao;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import bdsm.model.Parameter;
import bdsm.scheduler.util.SchedulerUtil;
import bdsmhost.dao.BaseAdapterDAO;
import bdsmhost.dao.ParameterDao;


/**
 * 
 * @author bdsm
 */
public class GeneralSchedulerDao extends BaseAdapterDAO implements Work {
	private static final Logger LOGGER = Logger.getLogger(GeneralSchedulerDao.class);
	private List resultList = new ArrayList();
	private List<Map> paramList = new ArrayList();
	private String query;
	private int flag;
	private String callProc;


    /**
     * 
     * @param session
     */
    public GeneralSchedulerDao(Session session) {
		super(session);
	}


    /**
     * 
     * @param query
     * @return
     */
    public List pureQuery(String query) {
		Session session = this.getSession();
		this.flag = 1;
		this.query = query;
		session.doWork(this);
		
		return this.resultList;
	}

    /**
     * 
     * @param procName
     * @param paramList
     * @return
     */
    public List contextQuery(String procName, List paramList) {
		boolean validState = false;
		Session session = this.getSession();
		this.flag = 2;
		this.callProc = procName;
		this.paramList = paramList;
		
		while (validState == false) {
			try {
				session.doWork(this);
				validState = true;
			}
			catch (org.hibernate.exception.GenericJDBCException ex) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw, true);
				ex.printStackTrace(pw);
				
				String str = sw.getBuffer().toString();
				if ((str.contains("existing state of package")) && ((str.contains("discarded")) || (str.contains("invalidated"))));
				else
					throw ex;
			}
		}
		
		return this.resultList;
	}

    /**
     * 
     * @param query
     * @param mapField
     * @return
     */
    public String restructureProc(String query, List<Map> mapField) {
		StringBuilder queryRestruct = new StringBuilder(query);
		
		if ((mapField != null) && (mapField.size() > 0)) {
			StringBuilder valueRestraint = new StringBuilder("(");
			
			for (Map newMap : mapField) {
				if (newMap != null) {
					LOGGER.debug("FIELD : " + newMap);
					valueRestraint.append("?,");
				}
			}
			
			valueRestraint
				.deleteCharAt(valueRestraint.length() - 1) // delete last comma
				.append(')');
			
			queryRestruct.append(valueRestraint);
		}
		
		return queryRestruct.toString();
	}

    /**
     * 
     * @param connection
     * @throws SQLException
     */
    public void execute(Connection connection) throws SQLException {
		LOGGER.debug("QUERY BEFORE :" + this.query);
		if (this.flag == 1) {
			Query query = getSession().createSQLQuery(this.query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			this.resultList = query.list();
		}
		else if (this.flag == 2) {
			int i = 1;
			String query = "{call " + this.callProc + "}";
			LOGGER.info("Running DB Procedure: " + query);
			CallableStatement stmt = connection.prepareCall(query);

			ParameterDao pmDao = new ParameterDao(this.getSession());
			Parameter fword = pmDao.get("FORBIDDEN.Keyword");
			
			for (Map<String, Object> inner : this.paramList) {
				LOGGER.debug("MAP: " + inner);
				if (!inner.isEmpty()) {
					// Map inner = (Map) param.get(i);
					String fieldName = inner.get("fieldName").toString();
					String fieldValue = inner.get(fieldName).toString();
					String valueType = inner.get("formatData").toString();

					if ("STRING".equalsIgnoreCase(valueType)) {
						LOGGER.debug("Set String [" + i + "]: " + fieldValue);
						stmt.setString(i, fieldValue);
					}
					else if ("INTEGER".equalsIgnoreCase(valueType)) {
						try {
							Integer numVal = Integer.parseInt(fieldValue);
							LOGGER.debug("Set Int [" + i + "]: " + numVal);
							stmt.setInt(i, numVal);
						}
						catch (Exception ex) {
							stmt.setString(i, fieldValue);
						}
					}
					else if ("DOUBLE".equalsIgnoreCase(valueType)) {
						try {
							Double doubleValue = Double.parseDouble(fieldValue);
							LOGGER.debug("Set Double [" + i + "]: " + doubleValue);
							stmt.setDouble(i, doubleValue);
						}
						catch (Exception ex) {
							stmt.setString(i, fieldValue);
						}
					}
					else if ("BIGDECIMAL".equalsIgnoreCase(valueType)) {
						try {
							LOGGER.debug("Set Big Decimal [" + i + "]: " + fieldValue);
							stmt.setBigDecimal(i, new BigDecimal(fieldValue));
						}
						catch (Exception ex) {
							stmt.setString(i, fieldValue);
						}
					}
					else if ("DATE".equalsIgnoreCase(valueType)) {
						String formatPattern = (inner.get("fieldformat") == null)? "dd-MM-yyyy": inner.get("fieldformat").toString();
						SimpleDateFormat dateFormatter = new SimpleDateFormat(formatPattern);
						
						LOGGER.debug("DATE VALUE: " + fieldValue);
						LOGGER.debug("FORMAT PATTERN: " + formatPattern);
						
						try {
							java.sql.Date date = new java.sql.Date(dateFormatter.parse(fieldValue).getTime());
							LOGGER.debug("Set Date [" + i + "]: " + date);
							stmt.setDate(i, date);
						}
						catch (ParseException ex) {
							LOGGER.error("ERROR : Failed set Date value" + ex, ex);
							
							try {
								LOGGER.debug("Mandatory Flag :" + inner.get("flgMandatory"));
								if ("N".equalsIgnoreCase(inner.get("flgMandatory").toString())) {
									stmt.setString(i, "");
								}
							}
							catch (Exception ec) {
								LOGGER.error("ERROR: Failed get Mandatory" + ex, ex);
								stmt.setString(i, "");
							}
						}

					}
					else if ("TIMESTAMP".equalsIgnoreCase(valueType)) {
						String formatPattern = inner.get("fieldformat") == null ? "dd-MM-yyyy HH:mm:ss" : inner.get("fieldformat").toString();
						SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(formatPattern);
						
						LOGGER.debug("DATE TIME VALUE: " + fieldValue);
						LOGGER.debug("FORMAT PATTERN: " + formatPattern);
						
						try {
							Timestamp dateTime = new java.sql.Timestamp(dateTimeFormatter.parse(fieldValue).getTime());
							LOGGER.debug("Set Timestamp [" + i + "]: " + dateTime);
							stmt.setTimestamp(i, dateTime);
						}
						catch (Exception ex) {
							LOGGER.error("ERROR : Failed set Timestamp value, so will set to current date time", ex);
							stmt.setTimestamp(i, SchedulerUtil.getTime());
						}

					}
					else {
						Object value = inner.get("fieldVal");
						LOGGER.info("valueType: " + value.getClass().getName());
						LOGGER.debug("Set Object [" + i + "]: " + inner.get("fieldVal"));
						stmt.setObject(i, fieldValue);
					}
				}
				i++;
			}
			
			stmt.executeUpdate();
		}

	}

}
