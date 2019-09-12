/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.BOKprLebih;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionImpl;

/**
 *
 * @author v00019722
 */
public class getKPRDao extends BaseDao{

    public getKPRDao(Session session) {
        super(session);
    }
    private Integer workResult1 = 0;
    private Integer workResult = 0;
    private Integer workType;
    private String ID_Batch = null;
    private String bankmast;
    
    private Logger logger = Logger.getLogger(getKPRDao.class);
    
    public List<BOKprLebih> get(Date Today) {
        DateFormat sdf = new SimpleDateFormat(StatusDefinition.PPFKPRDate);
        Criteria criteriaQuery = getSession().createCriteria(BOKprLebih.class);
        criteriaQuery.add(Restrictions.eq("datProcess",sdf.format(Today)));
        return criteriaQuery.list();
    }
    
    public void runKPRBO() throws SQLException {
        try {
            Connection cnctn = ((SessionImpl) super.getSession()).connection();
            StringBuilder qryStr = new StringBuilder();

            int workRes = 0;

            String query = null;
            if (workType == 1)
            {
                query = ppfStart;
            } else if (workType == 2){
                query = genKPR;
            } 
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            
            workResult = stmt.executeUpdate();
            logger.debug(stmt.getInt(1));
            workResult1 = stmt.getInt(1);
            stmt.close();

            logger.debug("RESULT :" + workResult1);
            //workType = 1;
        } catch (SQLException e) {
            //e.printStackTrace();
            logger.debug("EXCEPTION CLOSING SQL : " + e);
            //workType = 2;
        } catch (Exception e) {
            //e.printStackTrace();
            logger.debug("EXCEPTION CLOSING : " + e);
            //workType = 2;
        }
    }
    public Integer KPRgen() throws SQLException {
        Session session = getSession();
        
        workType = 2;
        runKPRBO();
        return workResult1;
    }
    
    public Integer insertGEFU(String ID_Batch, String inboxID) throws SQLException {
        Session session = getSession();
        this.ID_Batch = ID_Batch;

        logger.debug("ID_BATCH TO SEND:" + ID_Batch);
        workType = 1;
        runKPRBO();
        return workResult1;
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((BOKprLebih) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((BOKprLebih) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private static final String ppfStart = "{? = call PK_BDSM_PPFKPR.kprStart}";
    private static final String genKPR = "{? = call PK_BDSM_PPFKPR.genKPR}";
}
