/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.SMSBallance;
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
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00019722
 */
public class SMSBallanceDao extends BaseDao implements Work{

    private int result = 0;
    private int flag;
    private List columnName;
    private String tableName;
    private Logger logger = Logger.getLogger(SMSBallanceDao.class);
    
    public SMSBallanceDao(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int runPopulate(int deFlag) {
        Session session = getSession();
        this.flag = deFlag;
        session.doWork(this);
        return result;
    }
    public List getColumnName(int deFlag,String tableName){
        Session session = getSession();
        this.flag = deFlag;
        this.tableName = tableName;
        session.doWork(this);
        return columnName;
    }
    public void execute(Connection cnctn) throws SQLException {
        Statement labelSt = null;
        ResultSet rs = null;
    
        try {
            if (this.flag == 1) {
            String query = queryPopulate;
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.executeUpdate();
            //result = stmt.getInt(1);
            stmt.close();
            } else {
                String query = "SELECT * FROM " + this.tableName;
                logger.info("QUERY :" + query);
                labelSt = (Statement) cnctn.createStatement();
                rs = labelSt.executeQuery(query);
                ResultSetMetaData meta = rs.getMetaData();
                
                this.columnName = new ArrayList();
                int numberOfColumn = meta.getColumnCount();
                logger.info("NUMBER :" + numberOfColumn);
                for(int i=2;i<numberOfColumn+1;i++){
                    columnName.add(meta.getColumnName(i));
                    logger.info("COLUMN :" + meta.getColumnName(i));
                }
            }

        } catch (SQLException e) {
            logger.info("SQL EXCEPTION :" + e,e);
        } catch (Exception e) {
            logger.info("EXCEPTION :" + e,e);
        }
    }
    
    public int countRowTable() {
        int count = 0;
        Criteria crt = this.getSession().createCriteria(SMSBallance.class);
        crt.setProjection(Projections.rowCount());
        List SMSBall = crt.list();
        if(SMSBall != null){
            count = (Integer)SMSBall.get(0);
        }
        logger.info("COUNT :" + count);
        return count;
    }

    public List<SMSBallance> SMSgreatData(BigDecimal Begin, BigDecimal End, int fetchSize) {
        Query SMSq = this.getSession().getNamedQuery("SMSBallance#getGreatData");
        //SMSq.setBigDecimal("Begin", Begin);
        //SMSq.setBigDecimal("End", End);
        int BeginInt = Integer.parseInt(Begin.toString());
        int EndInt = Integer.parseInt(End.toString());
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
    private static final String queryPopulate = "{call PK_BDSM_SMS_BALL.populateTable}";
}
