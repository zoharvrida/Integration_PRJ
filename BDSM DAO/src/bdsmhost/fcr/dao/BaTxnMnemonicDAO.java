package bdsmhost.fcr.dao;

import org.hibernate.Session;

import bdsm.fcr.model.BaTxnMnemonic;
import bdsm.fcr.model.BaTxnMnemonicPK;
import bdsm.model.BaseModel;


/**
 * @author v00019372
 */
public class BaTxnMnemonicDAO extends bdsmhost.dao.BaseDao {

	public BaTxnMnemonicDAO(Session session) {
		super(session);
	}
	
	
	public BaTxnMnemonic getByCodTxnMnemonic(Integer codTxnMnemonic) {
		BaTxnMnemonicPK pk = new BaTxnMnemonicPK(codTxnMnemonic);
		
		return (BaTxnMnemonic) this.getSession().get(BaTxnMnemonic.class, pk);
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

}
