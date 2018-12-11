/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.CasaOpening;
import bdsm.model.CifOpening;
import java.sql.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00020800
 */
public class CasaOpeningDao extends BaseDao implements Work{

    private Integer CodCIF;
    private Integer codProd;
	private Integer cdBranch;
	private String idBatch;
    private String result;
    private int flag;
    private Logger logger = Logger.getLogger(CasaOpeningDao.class);
    
public CasaOpeningDao(Session session) {
		super(session);
	}
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((CasaOpening) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public CasaOpening get(String pk) {
        return (CasaOpening)getSession().get(CasaOpening.class, pk);
    }

    public String runCASA(Integer codCif, Integer codProd, Integer branch, String idBatch) {
        Session session = getSession();
        this.flag = 1;
        this.CodCIF = codCif;
        this.codProd = codProd;
		this.idBatch = idBatch;
        session.doWork(this);
        return result;
	}

    public void execute(Connection cnctn) throws SQLException {
        Statement labelSt = null;
        ResultSet rs = null;
   
        try {
            if (this.flag == 1) {
                String query = queryCASA;
                CallableStatement stmt = cnctn.prepareCall(query);
                stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                stmt.setInt(2, CodCIF);
                stmt.setInt(3, codProd);
				stmt.setString(4, idBatch);
				//stmt.setInt(4, cdBranch);
				
                stmt.executeUpdate();
                result = stmt.getString(1);
                stmt.close();
            } else {
	
            }

        } catch (SQLException e) {
            logger.info("SQL EXCEPTION :" + e, e);
        } catch (Exception e) {
            logger.info("EXCEPTION :" + e, e);
        }
    }
    //private static final String queryCASA = "{? = call PK_BDSM_OPENING.ADD_CASA(?,?)}";
     private static final String queryCASA = "{? = call PK_BDSM_OPENING.ADD_CASA(?,?,?)}";
}
