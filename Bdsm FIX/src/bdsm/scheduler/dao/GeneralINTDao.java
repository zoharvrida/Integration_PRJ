/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.*;
import bdsm.scheduler.util.SchedulerUtil;
import bdsmhost.dao.BaseDao;
import bdsmhost.dao.ParameterDao;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author SDM
 */
public class GeneralINTDao extends BaseDao implements Work {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GeneralINTDao.class);
    private List<Map<String, Object>> mapField = new ArrayList<Map<String, Object>>();
    private List tempParam = new ArrayList();
    private Map mapVal = new HashMap();
    private String query = "INSERT INTO ";
    private String queryRet = "SELECT 2 FROM 0 WHERE &";
    private int flag;
    private String callFunc;
    private String batchID;
    private String tableName;
    private String queryRest;
    private String hField;
    private String valOps = "{call AutoReply_BDSM.operator(?,?,?,?,?,?,?)}";
    private String valSpv = "{call AutoReply_BDSM.supervisor(?,?,?,?,?,?,?)}";
    private String valFlag;
    private String clsType;
    private boolean retFunc;
    private int monthInquiry;
    private int yearInquiry;
    private Date dateBegin;
    private Date dateEnd;
    private String codBranch;
    private String typRpt;
    private int typSt;

    /**
     * 
     * @param session
     */
    public GeneralINTDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param idScheduler
     * @param flgIdentifier
     * @return
     */
    public List<ARParamDetailsInterface> detailsList(Integer idScheduler, String flgIdentifier) {
        List<ARParamDetailsInterface> paramList = new ArrayList<ARParamDetailsInterface>();
        Criteria critQuery = getSession().createCriteria(ARParamDetailsInterface.class);
        critQuery.add(Restrictions.eq("idScheduler", idScheduler));
        critQuery.add(Restrictions.eq("typTrx", flgIdentifier));
        critQuery.addOrder(Order.asc("idOrder"));
        paramList = critQuery.list();
        return paramList;
    }

    /**
     * 
     * @param idScheduler
     * @param flgIdentifier
     * @param fieldType
     * @param fieldName
     * @return
     */
    public List<ARParamDetailsInterface> detailsListIndividual(Integer idScheduler, String flgIdentifier, String fieldType, String fieldName) {
        List<ARParamDetailsInterface> paramList = new ArrayList<ARParamDetailsInterface>();
        Criteria critQuery = getSession().createCriteria(ARParamDetailsInterface.class);
        critQuery.add(Restrictions.eq("idScheduler", idScheduler));
        critQuery.add(Restrictions.eq("typTrx", flgIdentifier));
        critQuery.add(Restrictions.eq("fieldName", fieldName));
        critQuery.add(Restrictions.ilike("type", fieldType, MatchMode.ANYWHERE));
        critQuery.addOrder(Order.asc("idOrder"));
        paramList = critQuery.list();
        return paramList;
    }

    /**
     * 
     * @param errnum
     * @return
     */
    public ErrorProfileDictionary getGenErr(Integer errnum) {
        List<ErrorProfileDictionary> error;
        // check for mapping if exist

        Criteria criteriaQuery = getSession().createCriteria(ErrorProfileDictionary.class);
        criteriaQuery.add(Restrictions.eq("errorNumb", errnum));
        error = criteriaQuery.list();
        return ((error.size() > 0) ? error.get(0) : null);
    }

    /**
     * 
     * @param errnum
     * @param profile
     * @return
     */
    public IntErrorMapping getErrStat(Integer errnum, String profile) {
        List<IntErrorMapping> error;
        IntErrorMapping itMap = new IntErrorMapping(errnum, profile);
        // check for mapping if exist

        Criteria criteriaQuery = getSession().createCriteria(IntErrorMapping.class);
        criteriaQuery.add(Restrictions.eq("errNumber", errnum));
        criteriaQuery.add(Restrictions.eq("profileName", profile));
        error = criteriaQuery.list();
        return ((error.size() > 0) ? error.get(0) : error.set(0, itMap));
    }

    /**
     * 
     * @param idScheduler
     * @return
     */
    public ARParamMaster paramProfile(Integer idScheduler) {
        List<ARParamMaster> paramList;
        Criteria criteriaQuery = getSession().createCriteria(ARParamMaster.class);
        criteriaQuery.add(Restrictions.eq("idScheduler", idScheduler));
        paramList = criteriaQuery.list();
        logger.debug("LIST DAO : " + paramList);

        return ((paramList.size() > 0) ? paramList.get(0) : null);
    }

    /**
     * 
     * @param idScheduler
     * @return
     */
    public List<ARParamMasterInterface> listparamProfile(Integer idScheduler) {
        return listparamProfile(idScheduler, "I");
    }

    /**
     * 
     * @param idScheduler
     * @param typeSched
     * @return
     */
    public List<ARParamMasterInterface> listparamProfile(Integer idScheduler, String typeSched) {
        List<ARParamMasterInterface> paramList;
        Criteria criteriaQuery = getSession().createCriteria(ARParamMasterInterface.class);
        criteriaQuery.add(Restrictions.eq("idScheduler", idScheduler));
        criteriaQuery.add(Restrictions.eq("typScheduler", typeSched));
        criteriaQuery.addOrder(Order.asc("orderNo"));
        paramList = criteriaQuery.list();
        logger.debug("LIST DAO : " + paramList);
        return paramList;
    }

    /**
     * 
     * @param idScheduler
     * @return
     */
    public List<ARParamProfileInterface> listparamProfileInterface(Integer idScheduler) {
        List<ARParamProfileInterface> paramList;
        Criteria criteriaQuery = getSession().createCriteria(ARParamProfileInterface.class);
        criteriaQuery.add(Restrictions.eq("idSchedulerImport", idScheduler));
        paramList = criteriaQuery.list();
        logger.debug("LIST DAO : " + paramList);
        return paramList;
    }

    /**
     * 
     * @param idScheduler
     * @param type
     * @param typField
     * @param flags
     * @return
     */
    public List<ARParamDetailsInterface> paramDetailsInterface(Integer idScheduler, String type, String typField, int flags) {
        return paramDetailsInterface(idScheduler, type, typField, flags, "I");
    }

    /**
     * 
     * @param idScheduler
     * @param type
     * @param typField
     * @param flags
     * @param typScheduler
     * @return
     */
    public List<ARParamDetailsInterface> paramDetailsInterface(Integer idScheduler, String type, String typField, int flags, String typScheduler) {
        List<ARParamDetailsInterface> paramList = new ArrayList<ARParamDetailsInterface>();
        logger.info("CHECK SESSION : " + getSession());
        logger.debug("PARAM :" + idScheduler + " " + typField + " " + type + " " + typScheduler);
        Query q;
        try {
            if ("".equalsIgnoreCase(typField)) {
                q = this.getSession().getNamedQuery("ARDetails#listDetailsNoParse");
            } else {
                String fType = typField;
                if (flags == 0) {
                    q = this.getSession().getNamedQuery("ARDetails#listDetailsInt");
                } else if (flags == 1) {
                    q = this.getSession().getNamedQuery("ARDetails#listDetailsIntNegasi");
                } else {
                    if (flags == 2) {
                        q = this.getSession().getNamedQuery("ARDetails#listDetailsIntAll");
                    } else if (flags == 3) {
                        q = this.getSession().getNamedQuery("ARDetails#listDetailsIntAllLike");
                    } else {
                        q = this.getSession().getNamedQuery("ARDetails#listDetailsIntOutput");
                    }
                    List strList = SchedulerUtil.getParameter(typField, "|");
                    String fTypeL = null;
                    if (strList != null) {
                        fTypeL = strList.get(0).toString();
                        fType = strList.get(1).toString();
                        q.setString("pTypeL", fTypeL);
                    }
                }
                q.setString("pType", fType);
            }

            q.setInteger("pIdscheduler", idScheduler);
            q.setString("pFlag", type);
            q.setString("pTypSched", typScheduler);
            logger.info("QUERY CONVERTED SUCCESSFULLY :" + q.getQueryString());
            paramList = q.list();
            logger.debug("LIST DAO : " + paramList);
            return paramList;
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return paramList;
        }
    }

    /**
     * 
     * @param idScheduler
     * @param type
     * @param batchFlag
     * @return
     */
    public List<ARParamDetails> paramDetailsReader(Integer idScheduler, String type, String batchFlag) {
        List<ARParamDetails> paramList = new ArrayList<ARParamDetails>();
        logger.info("CHECK SESSION : " + getSession());
        try {
            Query q = this.getSession().getNamedQuery("ARDetailsReader#listDetails");
            q.setInteger("pIdscheduler", idScheduler);
            q.setString("pFlag", batchFlag);
            q.setString("pType", type);
            logger.info("QUERY CONVERTED SUCCESSFULLY");

            paramList = q.list();
            logger.debug("LIST DAO : " + paramList);
            return paramList;
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return paramList;
        }
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param paramList
     * @param flgpr
     */
    public void checkMark(List paramList, String flgpr) {
        Session session = getSession();
        this.flag = 4;
        this.valFlag = flgpr;
        this.tempParam = paramList;
        session.doWork(this);
    }

    /**
     * 
     * @param tableName
     * @param queryOri
     * @param headerField
     * @return
     */
    public List resultCall(String tableName, String queryOri, String headerField) {
        Session session = getSession();
        List resList = new ArrayList();
        this.flag = 3;
        this.tableName = tableName;
        this.queryRest = queryOri;
        this.hField = headerField;
        logger.debug("table :" + this.tableName + " queryORI :" + this.queryRest + " header :" + this.hField);
        session.doWork(this);
        return tempParam;
    }

    /**
     * 
     * @param queryOri
     * @return
     */
    public List queryCall(String queryOri) {
        Session session = getSession();
        this.flag = 6;
        this.queryRet = queryOri;
        session.doWork(this);
        return tempParam;
    }

    /**
     * 
     * @param packageName
     * @param batchID
     */
    public void callPackage(String packageName, String batchID) {
        Session session = getSession();
        this.flag = 2;
        this.callFunc = packageName;
        this.batchID = batchID;
        session.doWork(this);
    }

    /**
     * 
     * @param packageName
     * @param batchID
     * @return
     */
    public boolean callFunction(String packageName, String batchID) {
        Session session = getSession();
        this.flag = 5;
        this.callFunc = packageName;
        this.batchID = batchID;
        session.doWork(this);
        return this.retFunc;
    }

    /**
     * 
     * @param fieldMap
     */
    public void putLog(Map fieldMap) {
        Session session = getSession();
        String querySTring = "INSERT INTO EXCEPTION_LOG (DTMLOG,PROCESSTYPE,PROFILE,LOG,STATE,FILENAME) VALUES (?,?,?,?,?,?)";
        this.flag = 100;
        this.query = querySTring;
        this.mapVal = fieldMap;
        session.doWork(this);
    }

    /**
     * 
     * @param mapField
     * @param tableName
     * @return
     */
    public String restructure(List<ARParamDetails> mapField, String tableName) {;
        StringBuilder queryRestruct = new StringBuilder(query).append(tableName).append(" (");
        StringBuilder valueRestraint = new StringBuilder(" (");

        int i = 0;
        for (ARParamDetails newMap : mapField) {
            if (newMap != null) {
                if (i == mapField.size() - 1) {
                    queryRestruct.append(newMap.getFieldName());
                    queryRestruct.append(",STATUS)");
                    valueRestraint.append("?,?)");
                } else {
                    queryRestruct.append(newMap.getFieldName()).append(",");
                    valueRestraint.append("?,");
                }
            }
            i++;
        }

        queryRestruct.append("VALUES").append(valueRestraint);
        this.query = queryRestruct.toString();
        return this.query;
    }

    /**
     * 
     * @param mapField
     * @param query
     */
    public void dumpTable(List<Map<String, Object>> mapField, String query) {
        dumpTable(mapField, query, "AR");
    }

    /**
     * 
     * @param mapField
     * @param query
     * @param classType
     */
    public void dumpTable(List<Map<String, Object>> mapField, String query, String classType) {
        Session session = getSession();
        this.clsType = classType;
        this.flag = 1;
        this.query = query;
        this.mapField = mapField;
        session.doWork(this);
    }

    /**
     * 
     * @param mapField
     * @param tableName
     * @return
     */
    public String restructureInt(List<ARParamDetailsInterface> mapField, String tableName) {;
        StringBuilder queryRestruct = new StringBuilder(query).append(tableName).append(" (");
        StringBuilder valueRestraint = new StringBuilder(" (");
        int i = 0;
        for (ARParamDetailsInterface newMap : mapField) {
            // start check for condition
            // I = for input, R = for reader, D = for Reader then Delete
            if (newMap != null) {
                logger.debug("FIELD : " + newMap);
                if (newMap.getType().contains("I")) {
                    if(!newMap.getType().contains("D")){
						// THIS field need to be removed
					
						if (i == mapField.size() - 1) {
							queryRestruct.append(newMap.getFieldName());
							queryRestruct.append(") ");
							valueRestraint.append("?)");
						} else {
							queryRestruct.append(newMap.getFieldName()).append(",");
							valueRestraint.append("?,");
						}
					}
					
                }
            }
            i++;
        }
        queryRestruct.append(" VALUES").append(valueRestraint);
        //this.query = 
        return queryRestruct.toString();
    }

    /**
     * 
     * @param connection
     * @throws SQLException
     */
    public void execute(Connection connection) throws SQLException {
        if (connection.getAutoCommit() == true) {
            connection.setAutoCommit(false);
        }
        logger.debug("Flag : " + flag);
        if (flag == 1) { // Dump Table
            logger.debug("Query " + query);
            PreparedStatement stmt = connection.prepareStatement(query);
            int i = 1;
            ParameterDao pmDao = new ParameterDao(getSession());
            Parameter fword = pmDao.get("FORBIDDEN.Keyword");
            for (Map<String, Object> inner : mapField) {
                logger.debug("MAP : " + inner);
                if (!inner.isEmpty()) {
                    //Map inner = (Map) param.get(i);
                    String flagger = inner.get("fieldType").toString();
                    String valField = "";


                    if (inner.get("fieldVal") == null) {
                        if (inner.get("defVal") == null) {
                            flagger = flagger + "|NULL";
                        } else if (!inner.get("defVal").toString().contains(fword.getStrVal())) {
                            inner.put("fieldVal", inner.get("defVal"));
                        }
                    } else if ("null".equalsIgnoreCase(inner.get("fieldVal").toString())) {
                        valField = "";
                    } else {
                        valField = inner.get("fieldVal").toString();
                    }

                    if ("INTEGER".equalsIgnoreCase(flagger)) {
                        try {
                            Integer numVal = Integer.parseInt(valField.trim());
                            logger.debug("Set Int [" + i + "] " + numVal);
                            stmt.setInt(i, numVal);
                        } catch (Exception ex) {
                            logger.debug("Integer : " + ex,ex);
                            stmt.setString(i, valField);
                        }
                    } else if ("STRING".equalsIgnoreCase(flagger)) {
                        String varVal = valField;
                        logger.debug("Set String [" + i + "] " + varVal);
                        stmt.setString(i, varVal);
                    } else if ("BIGDECIMAL".equalsIgnoreCase(flagger)) {
                        String bigVal = valField.trim();
                        logger.debug("Set Big Decimal [" + i + "] " + bigVal);
                        try {
                            stmt.setBigDecimal(i, new BigDecimal(bigVal));
                        } catch (Exception ex) {
                            logger.debug("Bigdecimal : " + ex,ex);
                            stmt.setString(i, bigVal);
                        }
                    } else if ("DATE".equalsIgnoreCase(flagger)) {

                        String datVal = valField;
                        logger.debug("DATE VAL :" + datVal);
                        String validation = inner.get("fieldformat") == null ? "dd-MM-yyyy" : inner.get("fieldformat").toString();
                        logger.debug("VALIDATION :" + validation);
                        SimpleDateFormat TANGGAL_BATCH_FORMAT = new SimpleDateFormat(validation);
                        try {
                            synchronized (TANGGAL_BATCH_FORMAT) {
                                Date datConv = new Date(TANGGAL_BATCH_FORMAT.parse(datVal).getTime());
                                logger.debug("Set Date [" + i + "] " + datConv);
                                stmt.setDate(i, datConv);
                            }
                        } catch (ParseException ex) {
                            logger.error("ERROR : Failed set Date value" + ex, ex);
                            //Logger.getLogger(GeneralARDao.class.getName()).log(Level.SEVERE, null, ex);
                            try {
                                logger.debug("Mandatory Flag :" + inner.get("flgMandatory"));
                                if ("N".equalsIgnoreCase(inner.get("flgMandatory").toString())) {
                                    stmt.setString(i, "");
                                }
                            } catch (Exception ec) {
                                logger.error("ERROR : Failed get Mandatory" + ex, ex);
                                stmt.setString(i, "");
                            }
                        }

                    } else if ("DOUBLE".equalsIgnoreCase(flagger)) {
                        try {
                            Double doubVal = Double.parseDouble(valField.trim());
                            logger.debug("Set Double [" + i + "] " + doubVal);
                            stmt.setDouble(i, doubVal);
                        } catch (Exception ex) {
                            logger.debug("Double : " + ex,ex);
                            stmt.setString(i, valField);
                        }
                    } else if ("TIMESTAMP".equalsIgnoreCase(flagger)) {

                        String stmVal = valField;
                        String validation = inner.get("fieldformat") == null ? "dd-MM-yy HH.mm.SSS" : inner.get("fieldformat").toString();
                        SimpleDateFormat TANGGAL_BATCH_FORMAT = new SimpleDateFormat(validation);
                        try {
                            synchronized (TANGGAL_BATCH_FORMAT) {
                                Timestamp datConv = new java.sql.Timestamp(TANGGAL_BATCH_FORMAT.parse(stmVal).getTime());
                                logger.debug("Set Timestamp [" + i + "] " + datConv);
                                stmt.setTimestamp(i, datConv);
                            }
                        } catch (Exception ex) {
                            logger.error("ERROR : Failed set Timestamp value", ex);
                            stmt.setTimestamp(i, SchedulerUtil.getTime());
                            //Logger.getLogger(GeneralARDao.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        logger.debug("Set Object [" + i + "] " + inner.get("fieldVal"));
                        stmt.setObject(i, valField);
                    }
                }
                i++;
            }
            //stmt.executeQuery();
            // set default status
            if ("AR".equalsIgnoreCase(clsType)) {
                String defStatus = "";
                logger.debug("[STATUS] Set String [" + i + "] " + defStatus);
                stmt.setString(i, defStatus);
            }

            logger.debug("Try statement execute update");
            stmt.executeUpdate();
            stmt.close();
            connection.commit();
            logger.debug("Statement execute update success");
        } else if (flag == 2) { // call package
            String query = "{call " + this.callFunc + "(?)}";
            CallableStatement stmt = connection.prepareCall(query);
            logger.debug("Query Call Package : " + query);
            logger.debug("Batch ID : " + batchID);
            stmt.setString(1, batchID);
            stmt.executeUpdate();
            //result = stmt.getInt(1);
            stmt.close();
        } else if (flag == 3) { // retreive data
            logger.debug("QUERY BEFORE :" + this.queryRet);
            this.queryRet = "SELECT 2 FROM 0 WHERE &";
            logger.debug("QUERY RETURN :" + this.queryRet);
            this.queryRet = this.queryRet.replaceFirst("0", tableName); // table name
            this.queryRet = this.queryRet.replaceFirst("&", queryRest); // clause
            this.queryRet = this.queryRet.replaceFirst("2", hField);
            logger.debug("Table Name : " + tableName);
            logger.debug("Query Rest : " + queryRest);
            logger.debug("Query : " + queryRet);
            Query query = getSession().createSQLQuery(queryRet);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            tempParam = query.list();
        } else if (flag == 4) { // to Validate User Email
            String query = null;
            if ("OPERATOR".equalsIgnoreCase(valFlag)) {
                query = this.valOps;
            } else {
                query = this.valSpv;
            }
            logger.debug("Query Validate User Email : " + query);
            CallableStatement stmt = connection.prepareCall(query);
            for (int i = 0; i < 7; i++) {
                logger.debug("Param[" + i + "] : " + tempParam.get(i).toString());
                stmt.setString(i + 1, tempParam.get(i).toString());
            }
            logger.debug("Try statement execute update");
            stmt.executeUpdate();
            //result = stmt.getInt(1);
            stmt.close();
            logger.debug("Statement execute update success");
        } else if (flag == 5) {
            String query = "{? = call " + this.callFunc + "(?)}";
            CallableStatement stmt = connection.prepareCall(query);
            logger.debug("Query Call Package : " + query);
            logger.debug("Batch ID : " + batchID);
            stmt.setString(2, batchID);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int numFlag = stmt.getInt(1);
            if (numFlag == 0) {
                this.retFunc = false;
            } else {
                this.retFunc = true;
            }
            //result = stmt.getInt(1);
            stmt.close();
        } else if (flag == 6) {
            // Pure query run
            logger.debug("QUERY 6 :" + queryRet);
            Query query = getSession().createSQLQuery(queryRet);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            tempParam = query.list();
        } else if (flag == 100) {
            CallableStatement stmt = connection.prepareCall(query);
            stmt.setTimestamp("DTM_LOG", SchedulerUtil.getTime());
            stmt.setString("PROCESSTYPE", mapVal.get("PROCESSTYPE").toString());
            stmt.setString("PROFILE", mapVal.get("PROFILE").toString());
            stmt.setString("LOG", mapVal.get("LOG").toString());
            stmt.setInt("STATE", Integer.parseInt(mapVal.get("STATE").toString()));
            stmt.setString("FILENAME", mapVal.get("FILENAME").toString());
            stmt.executeUpdate();
            stmt.close();
        }

    }
}