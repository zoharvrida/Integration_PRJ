/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixClassConfig;
import bdsmhost.dao.BaseDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 *
 * @author USER
 */
public class FixClassConfigDao extends BaseDao implements Work{
        
    /**
     * 
     * @param session
     */
    public FixClassConfigDao(Session session){
        super(session);
    }
    /**
     * 
     * @param classname
     * @param pSource
     * @param param
     * @return
     */
    public List<FixClassConfig> get(String classname, String pSource, String param){
        Query query = getSession().createQuery("from FixClassConfig where Classname = :classname and Source = :source and Param = :param");
        query.setString("classname", classname);
        query.setString("source", pSource);
        query.setString("param", param);
        return query.list();
    }
	
    /**
     * 
     * @param className
     * @param pSource
     * @param param
     * @param fixType
     * @return
     */
    public List<FixClassConfig> get(String className, String pSource, String param, String fixType) {
		Criteria crt = getSession().createCriteria(FixClassConfig.class);
		
		crt.add(Restrictions.eq("Classname", className));
		if (!StringUtils.isEmpty(pSource))
			crt.add(Restrictions.eq("Source", pSource));
		if (!StringUtils.isEmpty(param))
			crt.add(Restrictions.eq("Param", param));
		if (!StringUtils.isEmpty(fixType))
			crt.add(Restrictions.eq("FixType", fixType));
		
		return crt.list();
	}

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
