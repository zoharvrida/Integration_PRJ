/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixFolderWatcher;
import bdsm.scheduler.model.FixFolderWatcherPK;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author NCBS
 */
public class FixFolderWatcherDao extends BaseDao{
    
    public FixFolderWatcherDao(Session session){
        super(session);
    }
    public FixFolderWatcher get(FixFolderWatcherPK pk){
        return (FixFolderWatcher) getSession().get(FixFolderWatcher.class, pk);
    }
    public List<FixFolderWatcher> list(){
        Query q = getSession().createQuery("from FixFolderWatcher where flgStat='A'");
        return q.list();
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
