/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.EktpUser;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author 00030663
 */
public class EktpUserDao {
    private Session hSession = null;
     private Logger logger = Logger.getLogger(EktpUserDao.class);
    public EktpUserDao(Session session) {
        hSession = session;
    }

    public EktpUser get(String idUser) {
        return (EktpUser) hSession.get(EktpUser.class, idUser);
    }

     public List getMaxQuota(String sysprofile, String menuprofile, int maxKtpQuery) {
        Query ektpQuota = hSession.getNamedQuery("ektp#getMinimumQuota");
        ektpQuota.setParameter("pSystem", sysprofile);
        ektpQuota.setParameter("pMenu",menuprofile);
        ektpQuota.setInteger("pktpMax",maxKtpQuery);
        ektpQuota.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List q = ektpQuota.list();
        logger.info("COUNT LIST :" + q.size());
        return q;
    }
}
