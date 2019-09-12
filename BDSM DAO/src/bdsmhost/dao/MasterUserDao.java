/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MasterUser;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author 00030663
 */
public class MasterUserDao extends BaseDao {

    private Logger logger = Logger.getLogger(MasterUserDao.class);

    public MasterUserDao(Session session) {
        super(session);
    }

    public List<MasterUser> list(String namUser) {
        Query q = getSession().createQuery("from MasterUser where namUser like :p1 order by namUser");
        q.setString("p1", namUser + "%");
        return q.list();
    }

    public boolean isValidUser(BaseModel model) {
        MasterUser pModel = (MasterUser) model;
        MasterUser user = (MasterUser) getSession().get(MasterUser.class, pModel.getIdUser());

        if (user == null) {
            return false;
        }

        if (user.getIdUser().equalsIgnoreCase(pModel.getIdUser()) && user.getPassword().equals(pModel.getPassword())) {
            user.setDatLastSignOn(Calendar.getInstance().getTime());
            Transaction t = null;
            try {
                logger.debug("Save Date Sign");
                t = getSession().beginTransaction();
                update(user);
                t.commit();
                logger.debug("Save Date Sign Success");
            }catch(Exception e){
                t.rollback();
                logger.error("Save Date Sign On Fail");
                logger.error(e, e);
            }
            return true;
        }
        return false;
    }

    public boolean isAD(BaseModel model) {
        MasterUser pModel = (MasterUser) model;
        MasterUser user = (MasterUser) getSession().get(MasterUser.class, pModel.getIdUser());

        if (user == null) {
            return false;
        }

        if (user.getIdUser().equalsIgnoreCase(pModel.getIdUser()) && user.getIsAd().equals("Y")) {
            return true;
        }
        return false;
    }

    public MasterUser get(String idUser) {
        return (MasterUser) getSession().get(MasterUser.class, idUser);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((MasterUser) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((MasterUser) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((MasterUser) model);
        return true;
    }
}
