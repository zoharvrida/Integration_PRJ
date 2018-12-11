/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.fcr.model.CytmRates;
import bdsm.model.BaseModel;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author v00022309
 */
public class MCRBuyRateDAO extends BaseDao {
    
    private Logger logger = Logger.getLogger(MCRBuyRateDAO.class);

    public MCRBuyRateDAO(Session session) {
        super(session);
    }
    
    public List<Map> getBuyRateByCcy(String codCcy1) {
        org.hibernate.Query query = this.getSession().getNamedQuery("CytmRates#getBuyRateByCcy");
        query.setString("codCcy1", codCcy1.toString());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); 
		List<Map> listResult = query.list();
        
        logger.info("Buy Rate :" + listResult);
		return listResult;
    }
    
//    protected List<Map> getEquivUSDAmount(BigDecimal amtTxn, String codCcy1) throws Exception {
//        Query query = this.getSession().getNamedQuery("MCRBuyRate#getEquivUSDAmount");
//        query.setBigDecimal("amtTxn", amtTxn);
//        query.setString("codCcy1", codCcy1);
//        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//		List<CytmRates> listResult = query.list();
//		return query.list();
//    }

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
