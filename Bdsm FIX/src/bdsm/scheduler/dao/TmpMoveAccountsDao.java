/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpMoveAccounts;
import bdsmhost.dao.BaseDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00020841
 */
public class TmpMoveAccountsDao extends BaseDao implements Work{

    /**
     * 
     * @param session
     */
    public TmpMoveAccountsDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param param
     * @return
     */
    public List<TmpMoveAccounts> getByBatchNo(String param){
        Query query = getSession().createQuery("from TmpMoveAccounts where batchNo = :paramBatch");
        query.setString("paramBatch", param);
        return query.list();
    }
    private Logger logger = Logger.getLogger(TmpMoveAccountsDao.class);

    /**
     * 
     * @return
     */
    public Integer countTable() {
        Date tgl, tglP1;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 00);
        cal.set(Calendar.MINUTE, 01);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 000);
        tgl = cal.getTime();
        cal.add(Calendar.DATE, 1);
        tglP1 = cal.getTime();
        
        int count = 0;
        Criteria crt = this.getSession().createCriteria(TmpMoveAccounts.class);
        crt.setProjection(Projections.rowCount());
        crt.add(Restrictions.between("dtmCreated", tgl, tglP1));
        crt.add(Restrictions.eq("flagStatus", "D"));
        List countData = crt.list();
        if (countData != null) {
            count = (Integer) countData.get(0);
        }
        return count;
    }
    
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((TmpMoveAccounts)model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((TmpMoveAccounts) model);
        return true;
    }

    /**
     * 
     * @param cnctn
     * @throws SQLException
     */
    public void execute(Connection cnctn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
