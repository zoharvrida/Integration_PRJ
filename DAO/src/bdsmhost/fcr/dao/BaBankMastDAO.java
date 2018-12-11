/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;


import org.hibernate.Session;

import bdsm.fcr.model.BaBankMast;
import bdsm.fcr.model.BaBankMastPK;
import bdsm.model.BaseModel;


/**
 * 
 * @author v00019372
 */
public class BaBankMastDAO extends bdsmhost.dao.BaseDao {
	private static final int COD_BANK = 11;
	private static final int COD_ENTITY_VPD = 11;
	private int codEntityVPD;
	
	
	public BaBankMastDAO(Session session) {
		this(session, COD_ENTITY_VPD);
	}
	
	public BaBankMastDAO(Session session, int codEntityVPD) {
		super(session);
		this.codEntityVPD = codEntityVPD;
	}
	
	
	public BaBankMast get() {
		return this.getByBankCode(COD_BANK);
	}

	public BaBankMast getByBankCode(int bankCode) {
		BaBankMastPK pk = new BaBankMastPK(bankCode, "A", this.codEntityVPD);
		return this.get(pk);
	}
	
	public BaBankMast get(BaBankMastPK pk) {
		return (BaBankMast) this.getSession().get(BaBankMast.class, pk);
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Insert Operation");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Update Operation");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Delete Operation");
	}
}
