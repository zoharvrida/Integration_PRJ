/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.ARParamDetails;
import bdsm.model.ARParamMaster;
import bdsm.model.BaseModel;
import bdsm.model.EktpLog;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.DefinitionConstant;
import bdsmhost.dao.BaseDao;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.type.StringType;

/**
 *
 * @author SDM
 */
public class GeneralARDao extends BaseDao implements Work {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GeneralARDao.class);
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


    /**
     * 
     * @param session
     */
    public GeneralARDao(Session session) {
        super(session);

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
     * @param type
     * @return
     */
    public List<ARParamDetails> paramDetails(Integer idScheduler, String type) {
        List<ARParamDetails> paramList = new ArrayList<ARParamDetails>();
        logger.info("CHECK SESSION : " + getSession());
        try {
            Query q = this.getSession().getNamedQuery("ARDetails#listDetails");
            q.setInteger("pIdscheduler", idScheduler);
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

        Session session = getSession();
        this.flag = 1;
        this.query = query;
        this.mapField = mapField;
        session.doWork(this);

            // start check for condition
            // I = for input, R = for reader
        //this.query = 
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
            for (Map<String, Object> inner : mapField) {
                logger.debug("MAP : " + inner);
                if (!inner.isEmpty()) {
                    //Map inner = (Map) param.get(i);
                    String flagger = inner.get("fieldType").toString();
                    
                    
                    if (inner.get("fieldVal") == null) {
                        if (inner.get("defVal") == null)
                            flagger = flagger + "|NULL";
                        else
                            inner.put("fieldVal", inner.get("defVal"));
                    }

                    if ("INTEGER".equalsIgnoreCase(flagger)) {
                        Integer numVal = Integer.parseInt(inner.get("fieldVal").toString());
                            logger.debug("Set Int [" + i + "] " + numVal);
                            stmt.setInt(i, numVal);
                    } else if ("STRING".equalsIgnoreCase(flagger)) {
                        String varVal = inner.get("fieldVal").toString();
                        logger.debug("Set String [" + i + "] " + varVal);
                        stmt.setString(i, varVal);
                    } else if ("BIGDECIMAL".equalsIgnoreCase(flagger)) {
                        String bigVal = inner.get("fieldVal").toString();
                        logger.debug("Set Big Decimal [" + i + "] " + bigVal);
                            stmt.setBigDecimal(i, new BigDecimal(bigVal));
                    } else if ("DATE".equalsIgnoreCase(flagger)) {

                        String datVal = inner.get("fieldVal").toString();
                        String validation = inner.get("fieldformat") == null ? "dd-MM-yyyy" : inner.get("fieldformat").toString();
                        SimpleDateFormat TANGGAL_BATCH_FORMAT = new SimpleDateFormat(validation);
                        try {
                            Date datConv = new Date(TANGGAL_BATCH_FORMAT.parse(datVal).getTime());
                            logger.debug("Set Date [" + i + "] " + datConv);
                            stmt.setDate(i, datConv);
                        } catch (ParseException ex) {
                            logger.error("ERROR : Failed set Date value", ex);
                            //Logger.getLogger(GeneralARDao.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if ("DOUBLE".equalsIgnoreCase(flagger)) {
                        Double doubVal = Double.parseDouble(inner.get("fieldVal").toString());
                            logger.debug("Set Double [" + i + "] " + doubVal);
                            stmt.setDouble(i, doubVal);
                    } else if ("TIMESTAMP".equalsIgnoreCase(flagger)) {

                        String stmVal = inner.get("fieldVal").toString();
                        String validation = inner.get("fieldformat") == null ? "dd-MM-yy HH.mm.SSS" : inner.get("fieldformat").toString();
                        SimpleDateFormat TANGGAL_BATCH_FORMAT = new SimpleDateFormat(validation);
                        try {
                            Timestamp datConv = new java.sql.Timestamp(TANGGAL_BATCH_FORMAT.parse(stmVal).getTime());
                            logger.debug("Set Timestamp [" + i + "] " + datConv);
                            stmt.setTimestamp(i, datConv);
                        } catch (Exception ex) {
                            logger.error("ERROR : Failed set Timestamp value", ex);
                            //Logger.getLogger(GeneralARDao.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        logger.debug("Set Object [" + i + "] " + inner.get("fieldVal"));
                        stmt.setObject(i, inner.get("fieldVal"));
                    }
                }
                i++;
            }

            // set default status
                String defStatus = "";
                logger.debug("[STATUS] Set String [" + i + "] " + defStatus);
                stmt.setString(i, defStatus);
            

            logger.debug("Try statement execute update");
            stmt.executeUpdate();
            stmt.close();
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
            queryRet = queryRet.replaceFirst("0", tableName); // table name
            queryRet = queryRet.replaceFirst("&", queryRest); // clause
            queryRet = queryRet.replaceFirst("2", hField); 
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
            //result = stmt.getInt(1);
            // Pure query run
        }

    }
}