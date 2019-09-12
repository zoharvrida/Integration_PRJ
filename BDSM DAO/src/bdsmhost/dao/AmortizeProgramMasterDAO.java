package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import bdsm.model.AmortizeProgramMaster;
import bdsm.model.AmortizeProgramMasterPK;
import bdsm.model.BaseModel;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AmortizeProgramMasterDAO extends BaseDao {

	public AmortizeProgramMasterDAO(Session session) {
		super(session);
	}
	
	
	public AmortizeProgramMaster get(AmortizeProgramMasterPK id) {
		return (AmortizeProgramMaster) this.getSession().get(AmortizeProgramMaster.class, id);
	}
	
	public List<AmortizeProgramMaster> getAll() {
		return this.list();
	}
	
	public List<AmortizeProgramMaster> list() {
		Criteria crt = this.getSession().createCriteria(AmortizeProgramMaster.class);
		crt.addOrder(Order.asc("compositeId.giftCode"));
		crt.addOrder(Order.asc("compositeId.productCode"));
		
		return crt.list();
	}
	
	public List listProductMasterAndProductByGiftCode(String giftCode) {
		Query query = this.getSession().getNamedQuery("AmortizeProgramMaster#listProgramMasterAndProductByGiftCode");
		query.setString("giftCode", giftCode);
		
		return query.list();
	}
	
	public List listProductNotInAmortizeProductMaster(String giftCode, String strProductNameLike) {
		Query query1 = this.getSession().getNamedQuery("AmortizeProgramMaster#listProductNotInAmortizeProductMaster");
		Query query2;
		
		StringBuilder strSQL = new StringBuilder(query1.getQueryString()).append(" ");
		if (strProductNameLike != null) {
			try {
				Integer.parseInt(strProductNameLike);
				strSQL.append("ORDER BY prod.compositeId.codProd");
			}
			catch (NumberFormatException ex) {
				strSQL.append("ORDER BY prod.namProduct");
			}
		}
		
		query2 = this.getSession().createQuery(strSQL.toString());
		query2.setString("giftCode", giftCode);
		query2.setString("strProductNameLike", (strProductNameLike!=null)? ("%" + strProductNameLike + "%"): null);
		
		return query2.list();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		AmortizeProgramMaster apm = (AmortizeProgramMaster) model;
		apm.setIdUpdatedBy(null); apm.setIdUpdatedSpv(null);
		apm.setDtmUpdated(null); apm.setDtmUpdatedSpv(null);
		
		this.getSession().save(apm);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update((AmortizeProgramMaster) model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete((AmortizeProgramMaster) model);
		return true;
	}

}
