/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.SknngInOutDebitGen1Footer;
import bdsm.model.SknngInOutDebitGen1Header;
import bdsm.model.SknngInOutDebitHPK;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */
public class SknngInOutDebitGen1FooterDao extends BaseDao {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SknngInOutDebitGen1FooterDao.class);

    public SknngInOutDebitGen1FooterDao(Session session) {
        super(session);
    }

    public SknngInOutDebitGen1Footer get(SknngInOutDebitHPK pk) {
        return (SknngInOutDebitGen1Footer) this.getSession().get(SknngInOutDebitGen1Footer.class, pk);
    }
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
		return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
		return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
		return true;
    }
}
