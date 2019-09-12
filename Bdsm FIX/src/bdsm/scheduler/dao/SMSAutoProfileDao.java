/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpSMSTrans;
import bdsmhost.dao.BaseDao;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00019722
 */
public class SMSAutoProfileDao extends BaseDao implements Work {

    private String result = null;
    private int resultNumber = 0;
    private int flag;
    private int idTrack;
    private List columnName;
    private String tableName;
    private String callFunc;
    private Integer paramnumber;
    private List refparam = new ArrayList();
    private List paramCollection = new ArrayList();
    private List returnCollection = new ArrayList();
    private Logger logger = Logger.getLogger(SMSAutoProfileDao.class);

    /**
     * 
     * @param session
     */
    public SMSAutoProfileDao(Session session) {
        super(session);
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
     * @return
     */
    public Integer getSequence() {
        Session session = getSession();
        this.flag = 99;
        session.doWork(this);
        return resultNumber;
    }

    /**
     * 
     * @param deFlag
     * @param pattern
     * @return
     */
    public String runQuery(int deFlag, String pattern) {
        Session session = getSession();
        this.flag = deFlag;
        this.callFunc = pattern;
        session.doWork(this);
        return result;
    }

    /**
     * 
     * @param deFlag
     * @param pattern
     * @param value
     * @param type
     * @param noParam
     * @param delimiter
     * @return
     */
    public String runQueryWithParam(int deFlag, String pattern, String value, String type, Integer noParam, String delimiter) {
        Session session = getSession();
        this.flag = deFlag;
        this.callFunc = pattern;
        this.paramnumber = noParam;
        paramCollection = bdsm.scheduler.util.SchedulerUtil.getParameter(value, delimiter);
        refparam = bdsm.scheduler.util.SchedulerUtil.getParameter(type, delimiter);
        logger.info("VALUE:" + value + "| TYPE :" + type);
        session.doWork(this);
        return result;
    }

    /**
     * 
     * @param idTrack
     * @param pattern
     * @return
     */
    public int runStamp(int idTrack, String pattern) {
        Session session = getSession();
        this.idTrack = idTrack;
        this.flag = 3;
        this.callFunc = pattern;
        session.doWork(this);
        return resultNumber;
    }

    /**
     * 
     * @param deFlag
     * @param tableName
     * @return
     */
    public List getColumnName(int deFlag, String tableName) {
        Session session = getSession();
        this.flag = deFlag;
        this.tableName = tableName;
        session.doWork(this);
        return columnName;
    }

    /**
     * 
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
        Statement labelSt = null;
        ResultSet rs = null;
        try {
            if (this.flag == 1) {
                String query = "{" + queryStore + this.callFunc + "}";
                CallableStatement stmt = cnctn.prepareCall(query);
                stmt.executeUpdate();
                //result = stmt.getInt(1);
                stmt.close();
            } else if (this.flag == 2) {
                String query = "{? = " + queryStore + this.callFunc + "}";
                CallableStatement stmt = cnctn.prepareCall(query);
                stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                stmt.executeUpdate();
                //result = stmt.getInt(1);
                result = stmt.getString(1);
                stmt.close();

            } else if (this.flag == 3) {
                String query = "{" + queryStore + this.callFunc + " (?,?)}";
                CallableStatement stmt = cnctn.prepareCall(query);
                stmt.setInt(1, idTrack);
                stmt.setInt(2, flag);
                stmt.executeUpdate();
                //result = stmt.getInt(1);
                stmt.close();
            } else if (this.flag == 5) {
                // count number of param 
                StringBuilder param = new StringBuilder();
                param.append("(");
                for (int i = 0; i < this.paramnumber; i++) {
                    if (i == 0) {
                        param.append("?");
                    } else {
                        param.append(",?");
                    }
                }
                param.append(") ");

                String query = "{" + queryStore + this.callFunc + param.toString() + "}";
                logger.info("PARAM PACKAGE:" + param + "| QUERY :" + query);
                CallableStatement stmt = cnctn.prepareCall(query);
                int x = 0;
                for (int i = 1; i < (paramnumber + 1); i++) {
                    logger.info("TYPE PARAM:" + refparam.get(x).toString());
					logger.info("VALUE PARAM:" + paramCollection.get(x).toString());
                    if ("STRING".equalsIgnoreCase(refparam.get(x).toString())) {
                        stmt.setString(i, paramCollection.get(x).toString());
                    } else if ("NUMBER".equalsIgnoreCase(refparam.get(x).toString())) {
                        stmt.setInt(i, Integer.parseInt(paramCollection.get(i - 1).toString()));
                    } else if ("DATE".equalsIgnoreCase(refparam.get(x).toString())) {
                        stmt.setDate(i, null);
                    } else if ("INSTRING".equalsIgnoreCase(refparam.get(x).toString())) {
                        stmt.registerOutParameter(i, java.sql.Types.VARCHAR);
                        returnCollection.add(stmt.getString(i));
                    }
                    x++;
                }
				stmt.executeUpdate();
				stmt.close();
            } else if (this.flag == 99) {
                String query = "SELECT SMSTRX_OUTPUT_SEQ.NEXTVAL FROM DUAL";
                labelSt = (Statement) cnctn.createStatement();
                rs = labelSt.executeQuery(query);
                if (rs.next()) {
                    resultNumber = rs.getInt(1);
                } else {
                    logger.info("COULDNT GET SEQUENCE : " + rs);
                    resultNumber = 1;
                }
            } else {
                String query = "SELECT * FROM " + this.tableName;
                logger.info("QUERY :" + query);
                labelSt = (Statement) cnctn.createStatement();
                rs = labelSt.executeQuery(query);
                ResultSetMetaData meta = rs.getMetaData();

                this.columnName = new ArrayList();
                int numberOfColumn = meta.getColumnCount();
                logger.info("NUMBER :" + numberOfColumn);
                for (int i = 2; i < numberOfColumn + 1; i++) {
                    columnName.add(meta.getColumnName(i));
                    logger.info("COLUMN :" + meta.getColumnName(i));
                }
            }

        } catch (SQLException e) {
            logger.info("SQL EXCEPTION FROM:" + this.flag);
            logger.info("SQL EXCEPTION :" + e, e);
        } catch (Exception e) {
            logger.info("EXCEPTION FROM:" + this.flag);
            logger.info("EXCEPTION :" + e, e);
        }
    }

    /**
     * 
     * @param profile
     * @param BatchID
     * @return
     */
    public int countRowTable(String profile, String BatchID) {
        int count = 0;
        Criteria crt = this.getSession().createCriteria(TmpSMSTrans.class);
        crt.add(Restrictions.eq("compositeID.profile", profile));
        crt.add(Restrictions.eq("compositeID.batchID", BatchID));
        crt.setProjection(Projections.rowCount());
        List SMSTrans = crt.list();
        if (SMSTrans != null) {
            count = (Integer) SMSTrans.get(0);
        }
        logger.info("COUNT :" + count);
        return count;
    }

    /**
     * 
     * @param Begin
     * @param End
     * @param profile
     * @param BatchID
     * @return
     */
    public List<TmpSMSTrans> SMSgreatData(BigDecimal Begin, BigDecimal End, String profile, String BatchID) {
        Query SMSq = this.getSession().getNamedQuery("SMSTrans#getGreatData");
        //SMSq.setBigDecimal("Begin", Begin);
        //SMSq.setBigDecimal("End", End);
        logger.info("PROFILE :" + profile + ",BATCH :" + BatchID);
        SMSq.setParameter("pProfile", profile);
        SMSq.setParameter("pBatch", BatchID);
        int BeginInt = Integer.parseInt(Begin.toString());
        int EndInt = Integer.parseInt(End.toString());
        logger.info("BEGIN COUNT :" + BeginInt + ",END COUNT :" + EndInt);
        if (BeginInt != 0) {
            SMSq.setFirstResult(BeginInt);
        }
        if (EndInt != 0) {
            SMSq.setMaxResults(EndInt);
        }
        //SMSq.setFetchSize(fetchSize);
        List q = SMSq.list();
        logger.info("COUNT LIST :" + q.size());
        return q;
    }
    private static final String queryStore = "call PK_BDSM_SMS_TRANS.";
}
