/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.EktpLog;
import bdsm.util.DefinitionConstant;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.lob.ClobImpl;

/**
 *
 * @author 00030663
 */
public class EktpLogDao implements Work {

    private Session hSession = null;
    private List columnName;
    private String tableName;
    private Logger logger = Logger.getLogger(EktpLogDao.class);
    private int monthInquiry;
    private int yearInquiry;
    private Date dateBegin;
    private Date dateEnd;
    private String codBranch;
    private String typRpt;
    private int typSt;
    private int flag = 0;
    private String query = "INSERT INTO ";
    private List tempParam = new ArrayList();

    public EktpLogDao(Session session) {
        hSession = session;
    }

    public EktpLog get(String idLog) {
        return (EktpLog) hSession.get(EktpLog.class, idLog);
    }

    public int count(Date datLog, String idUser) {
        Query q = hSession.createQuery("select count(*) as cnt from EktpLog where datPost= :p1 and idUser = :p2");
        q.setDate("p1", datLog);
        q.setString("p2", idUser);
        Number n = (Number) q.uniqueResult();
        if (n == null) {
            return 0;
        }
        return n.intValue();
    }

    public boolean insert(EktpLog model) {
        Transaction t = hSession.beginTransaction();
        boolean result = false;
        try {
            /*
             * Enhancement : KK Inquiry Date Enhance : 21-Januari-2016 Enhancer
             * : 00030663 Begin 1
             */
            String s = model.getResponse();
            if (s.length() > 2000) {
                Clob c = new ClobImpl(s);
                model.setLargeResp(c);
                model.setResponse("#Response is too large. Please see it in LargeResp.");
            }
            /*
             * End 1
             */
            hSession.save(model);
            t.commit();
            result = true;
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }
        return result;
    }

    public List<EktpLog> listKkInquiry(BigDecimal Begin, BigDecimal End, Date startDat, Date endDat, String idBranch, String flag) {
        String Branch = "".equalsIgnoreCase(flag) ? "" : "CDBRANCH";

        Criteria crt = hSession.createCriteria(EktpLog.class);
        crt.add(Restrictions.between("datLog", startDat, endDat));
        //crt.add(Restrictions.le("datLog", endDat));
        crt.add(Restrictions.eq("MenuSrc", "30502"));
        if (!Branch.equalsIgnoreCase("") && !idBranch.equalsIgnoreCase("ALL")) {
            crt.add(Restrictions.eq("cdBranch", idBranch));
        }
        int BeginInt = Integer.parseInt(Begin.toString());
        int EndInt = Integer.parseInt(End.toString());
        if (BeginInt != 0) {
            crt.setFirstResult(BeginInt);
        }
        if (EndInt != 0) {
            crt.setMaxResults(EndInt);
        }
        logger.info("CRITERION DATA:" + crt);
        List listKkLog = crt.list();

        return listKkLog;
    }

    public List getColumnName(String tableName) {
        this.tableName = tableName;
        logger.info("TABLE :" + this.tableName);
        this.flag = 0;
        hSession.doWork(this);
        return columnName;
    }

    public List getDataEktpLog(int monthLog, int yearlog, String idBranch, String Flag, String addMenu, int stType) {
        Session session = hSession;
        this.monthInquiry = monthLog;
        this.yearInquiry = yearlog;
        this.codBranch = idBranch;
        this.typSt = stType;
        String querySTring = null;
        String mainSel = null;
        StringBuilder sb = new StringBuilder();
        if (stType == 1) { // count
            mainSel = "select count(1) CNT from ";
        } else if (stType == 2) {
            mainSel = "select * from ";
        }
        sb.append(mainSel);
        querySTring = "( SELECT * FROM EKTP_LOG A WHERE EXTRACT(MONTH FROM a.DATLOG) = :pMonth"
                + " and EXTRACT(YEAR FROM A.DATLOG) = :pYear ";
        sb.append(querySTring);
        if (!"".equalsIgnoreCase(idBranch) || !"ALL".equalsIgnoreCase(idBranch)) {
            sb.append(" and A.CDBRANCH = :pBranch ");
        }
        querySTring = " UNION ALL";
        sb.append(querySTring);
        querySTring = " SELECT * FROM EKTP_LOG_HIST B WHERE EXTRACT(MONTH FROM B.DATLOG) = :pMonth"
                + " and EXTRACT(YEAR FROM B.DATLOG) = :pYear ";
        sb.append(querySTring);
        if (!"".equalsIgnoreCase(idBranch) || !"ALL".equalsIgnoreCase(idBranch)) {
            sb.append(" and B.CDBRANCH = :pBranch ");
        }
        querySTring = ") histTab ";
        sb.append(querySTring);
        if ("SYSEKTP_LOG_RPT".equalsIgnoreCase(Flag)) {
            sb.append(addMenu);
        }
        logger.info("QUERY :" + sb.toString());
        this.flag = 1;
        this.typRpt = Flag;
        this.query = sb.toString();
        session.doWork(this);
        sb.delete(0, sb.length());
        return tempParam;
    }
    
    public List getDataKKLog(Date dateBegin, Date dateEnd, String idBranch, String Flag,String addMenu, int stType) {
        Session session = hSession;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.codBranch = idBranch;
        this.typSt = stType;
        String querySTring = null;
        String mainSel = null;
        StringBuilder sb = new StringBuilder();
        if(stType == 1){ // count
            mainSel = "select count(1) CNT from ";
        } else if(stType == 2){
            mainSel = "select * from ";
        } 
        sb.append(mainSel);
        querySTring = "( SELECT * FROM EKTP_LOG A WHERE DATLOG >= :pMonth"
                + " and A.DATLOG <= :pYear and A.MENU_SRC IN (SELECT * FROM TABLE ("
                + "string_to_array((SELECT strval FROM PARAMETER WHERE cdparam = 'KK.menu.Inquiry'),','))) ";
        sb.append(querySTring);
        if (!"".equalsIgnoreCase(idBranch)) {
            sb.append(" and A.CDBRANCH = :pBranch ");
        }
        querySTring = " UNION ALL";
        sb.append(querySTring);
        querySTring = " SELECT * FROM EKTP_LOG_HIST B WHERE DATLOG >= :pMonth"
                + " and B.DATLOG <= :pYear and B.MENU_SRC IN (SELECT * FROM TABLE ("
                + "string_to_array((SELECT strval FROM PARAMETER WHERE cdparam = 'KK.menu.Inquiry'),','))) ";
        sb.append(querySTring);
        if (!"".equalsIgnoreCase(idBranch)) {
            sb.append(" and B.CDBRANCH = :pBranch ");
        }
        querySTring = ") histTab ";
        sb.append(querySTring);
        if ("SYSEKK_LOG_RPT".equalsIgnoreCase(Flag)) {
            sb.append(addMenu);
        }
        logger.info("QUERY :" + sb.toString());
        this.flag = 2;
        this.typRpt = Flag;
        this.query = sb.toString();
        session.doWork(this);
        sb.delete(0, sb.length());
        return tempParam;
    }
    //@Override

    public void execute(Connection cnctn) throws SQLException {
        Statement labelSt = null;
        ResultSet rs = null;
        
        logger.info("FLAG :" + flag);
        if (flag == 1) {
            EktpLog ktp = new EktpLog();
            Query queryS = null;
            if(typSt == 1){
                queryS = hSession.createSQLQuery(query);
            } else {
                queryS = hSession.createSQLQuery(query).addEntity(ktp.getClass());
            }
            
            queryS.setInteger("pMonth", monthInquiry);
            queryS.setInteger("pYear", yearInquiry);
            if(!"".equalsIgnoreCase(codBranch)){
                queryS.setString("pBranch", codBranch);
            }
            
            if(typSt == 1){
              queryS.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);  
            }
            //
            
            tempParam = queryS.list();
        } else if(flag == 2) {
            EktpLog ktp = new EktpLog();
            Query queryS = null;
            if(typSt == 1){
                queryS = hSession.createSQLQuery(query);
            } else {
                queryS = hSession.createSQLQuery(query).addEntity(ktp.getClass());
            }
            
            queryS.setDate("pMonth", dateBegin);
            queryS.setDate("pYear", dateEnd);
            if(!"".equalsIgnoreCase(codBranch)){
                queryS.setString("pBranch", codBranch);
            }
            
            if(typSt == 1){
              queryS.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);  
            }
            // 
            tempParam = queryS.list();
        } else {
            try {
                String query = "SELECT * FROM " + this.tableName;
                logger.info("QUERY :" + query);
                labelSt = (Statement) cnctn.createStatement();
                rs = labelSt.executeQuery(query);
                ResultSetMetaData meta = rs.getMetaData();

                this.columnName = new ArrayList();
                int numberOfColumn = meta.getColumnCount();
                logger.info("NUMBER :" + numberOfColumn);
                for (int i = 1; i < numberOfColumn + 1; i++) {
                    columnName.add(meta.getColumnName(i));
                    logger.info("COLUMN :" + meta.getColumnName(i));
                }

            } catch (SQLException e) {
                logger.info("SQL EXCEPTION :" + e, e);
            } catch (Exception e) {
                logger.info("EXCEPTION :" + e, e);
            }
        }

    }
}

@SuppressWarnings("serial")
class MyAliasToBeanResultTransformer<T> extends org.hibernate.transform.AliasToBeanResultTransformer {

    private static Logger LOGGER = Logger.getLogger(MyAliasToBeanResultTransformer.class);
    private Map<String, String> propertyColumnMap;

    public MyAliasToBeanResultTransformer(Class<T> resultClass) {
        this(resultClass, null);
    }

    public MyAliasToBeanResultTransformer(Class<T> resultClass, Map<String, String> propertyColumnMap) {
        super(resultClass);
        this.propertyColumnMap = propertyColumnMap;

    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        String[] newAliases = new String[aliases.length];

        for (int i = 0; i < aliases.length; i++) {
            newAliases[i] = this.propertyColumnMap.get(aliases[i].toLowerCase());
            LOGGER.info(aliases[i].toLowerCase() + " - " + newAliases[i]);
        }

        return super.transformTuple(tuple, newAliases);
    }
}
