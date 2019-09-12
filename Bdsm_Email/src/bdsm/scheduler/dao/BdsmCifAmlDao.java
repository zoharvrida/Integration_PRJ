/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.BdsmCifAml;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author v00020841
 */
public class BdsmCifAmlDao extends BaseDao implements Work{

    private static final String queryUpload = "{call PK_BDSM_AML.runInsert}";
    private int workResult1 = 0;
    private Logger logger = Logger.getLogger(BdsmCifAmlDao.class);
    
    public BdsmCifAmlDao(Session session) {
        super(session);
    }

     public List<BdsmCifAml> getListCifAml(){
        Query query = this.getSession().createQuery("from BdsmCifAml");
        List<BdsmCifAml> list = query.list();
        return  list;
    }
    
    public void execute(Connection cnctn) {
        try {
            String query = queryUpload;
            CallableStatement stmt = cnctn.prepareCall(query);
            int workRes = stmt.executeUpdate();
            stmt.close();
			this.logger.info("query run : "+query);
            workResult1 = workRes;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
		
    }

    public int runUpload() {
        Session session = getSession();
        session.doWork(this);
        return workResult1;
    }
    
     
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");}

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");}

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");}

    public List<BdsmCifAml> getListToday12() {
        Date yesterday16, today12;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        today12 = cal.getTime();
        this.logger.info("end today : "+today12);
        cal.add(Calendar.DATE, -1);
        cal.add(Calendar.HOUR, 4);
        cal.set(Calendar.MINUTE, 01);
        cal.set(Calendar.SECOND, 0);
        yesterday16 = cal.getTime();
        this.logger.info("start     : "+yesterday16);

        Criteria crt = this.getSession().createCriteria(BdsmCifAml.class);
        crt.add(Restrictions.between("datInsert", yesterday16, today12));
        List<BdsmCifAml> list = crt.list();
        this.logger.info("list data  : "+list.size());

        return list;
    }

    public List<BdsmCifAml> getListToday16() {
        Date today16, today12;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 1);
        today12 = cal.getTime();
        cal.add(Calendar.HOUR, 4);
        cal.set(Calendar.SECOND, 00);
        today16 = cal.getTime();

        Criteria crt = this.getSession().createCriteria(BdsmCifAml.class);
        crt.add(Restrictions.between("datInsert", today12, today16));
        List<BdsmCifAml> list = crt.list();

        return list;
    }
   
}
