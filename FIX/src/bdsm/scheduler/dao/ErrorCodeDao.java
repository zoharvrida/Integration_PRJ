/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixPatternTable;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionImpl;

/**
 *
 * @author v00019722
 */
public class ErrorCodeDao extends BaseDao {

    private int workResult = 0;
    private String ErrGenerated = null;
    private Logger logger = Logger.getLogger(ErrorCodeDao.class);
    /**
     * 
     * @param session
     */
    public ErrorCodeDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param mod
     * @param params
     * @param type
     * @return
     * @throws SQLException
     */
    public String get(String mod, String params, String type) throws SQLException {
        String modCode;
        StringBuilder mod_val = new StringBuilder();
        Connection cnctn = ((SessionImpl) super.getSession()).connection();
        String query = null;
        
        query = singleErr;
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            stmt.setString(2, mod.toString());
            stmt.setString(3, params);
            stmt.setString(4, type);
            workResult = stmt.executeUpdate();
            ErrGenerated = stmt.getString(1);
            stmt.close();
        //logger.debug(stmt.getInt(1));
        modCode = ErrGenerated.toString();
        return modCode;
    }
    /**
     * 
     * @param idScheduler
     * @param type
     * @param namTemplate
     * @param typeSched
     * @return
     */
    public FixPatternTable get(Integer idScheduler,String type,String namTemplate, String typeSched){
        Criteria criteriaQuery = getSession().createCriteria(FixPatternTable.class);
        criteriaQuery.add(Restrictions.eq("idScheduler", idScheduler));
        criteriaQuery.add(Restrictions.eq("table_type", type));
        criteriaQuery.add(Restrictions.eq("properties", namTemplate));
        criteriaQuery.add(Restrictions.eq("table_Code", typeSched));
        logger.debug("RESULT :" + (FixPatternTable)criteriaQuery.uniqueResult());
        return (FixPatternTable)criteriaQuery.uniqueResult();
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
    private static final String singleErr = "{? = call PK_BDSM_ERR.get_error_verification(?,?,?)}";
}
