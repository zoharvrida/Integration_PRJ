/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author v00022309
 */
public class MCRCurrencyRateDAO extends BaseDao{
    private Logger logger = Logger.getLogger(MCRCurrencyRateDAO.class);

    public MCRCurrencyRateDAO(Session session) {
        super(session);
    }
    
    public List<Map> listByCurrencyCodeAndDate(String currencyCode, Date valueDate){
        org.hibernate.Query query = this.getSession().getNamedQuery("MCRCurrencyRate#listByCurrencyCodeAndDate");
        
        query.setString("currencyCode", currencyCode);
        query.setTimestamp("valueDate", valueDate);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
        
    }
    
    public List<Map> getDestinationRate(String currencyCode) {
        org.hibernate.Query query = this.getSession().getNamedQuery("MCRCurrencyRate#getRateByCcy");
        query.setString("currencyCode", currencyCode);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); 
		List<Map> listResult = query.list();
        
        logger.info("Rate :" + listResult);
		return listResult;
    }
    
    public BigDecimal MCR_getDestinationRateAfter(String currencyCode, String refNetworkNo) {
        org.hibernate.Query query = this.getSession().getNamedQuery("MCRCurrencyRate#getRateAfterByCcy");
        query.setString("currencyCode", currencyCode);
        query.setString("refNetworkNo", refNetworkNo);
		return (BigDecimal) query.uniqueResult();
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
