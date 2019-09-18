/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MasterLimitEtax;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.impl.SessionImpl;
/**
 *
 * @author rptuatdrsuperid
 */

public class MasterLimitEtaxDao extends BaseDao {
    private static final String cleanse = "{? = call PK_BDSM_MANAGEMENT.VALID_LIM_TRX(?,?)}";   
    private int workResult = 0;
    private int ErrGenerated = 0;
    
    public MasterLimitEtaxDao(Session session) {
		super(session);
	}
    
	public MasterLimitEtax get(String idUser) {
        Criteria criteriaQuery = getSession().createCriteria(MasterLimitEtax.class);
        criteriaQuery.add(Restrictions.eq("idUser", idUser));  
        
        return (MasterLimitEtax) criteriaQuery.uniqueResult();
    }
        
        public int cleans(String param,int param2) throws SQLException {
        int modCode;
        StringBuilder mod_val = new StringBuilder();
        Connection cnctn = ((SessionImpl) super.getSession()).connection();
        String query = null;
        
        query = cleanse;
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setString(2, param);
            stmt.setInt(3, param2);
            workResult = stmt.executeUpdate();
            ErrGenerated = stmt.getInt(1);
            stmt.close();
        //logger.debug(stmt.getInt(1));
        modCode = ErrGenerated;
        return modCode;
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
    
}
