/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MasterTemplate;
import bdsm.model.MasterUser;
import bdsm.model.MasterUserComp;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author user
 */
public class MasterUserCompDao extends BaseDao {
    public MasterUserCompDao(Session session) {
        super(session);
    }
    
    public List list(String namUser) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT A, B ");
        queryString.append("FROM MasterUser A, MasterTemplate B ");
        queryString.append("WHERE A.idTemplate = B.idTemplate ");
        queryString.append("AND A.namUser LIKE'").append(namUser).append("%'");
        queryString.append(" order by namUser ");
        
        Query q = getSession().createQuery(queryString.toString());
        
        return getListUser(q.list());
    }
    
    private List getListUser(List listData) {
        MasterUser user;
        MasterTemplate template;
        MasterUserComp userComp;
        Object[] objectJoin;
        List listUser = new ArrayList<MasterUserComp>();
        
        for(int i = 0; i < listData.size(); i++) {
            objectJoin = (Object[])listData.get(i);
            user = (MasterUser)objectJoin[0];
            template = (MasterTemplate)objectJoin[1];
            
            userComp = new MasterUserComp();
            userComp.setIdUser(user.getIdUser());
            userComp.setNamUser(user.getNamUser());
            userComp.setCdBranch(user.getCdBranch());
            userComp.setIdTemplate(template.getIdTemplate());
            userComp.setNamTemplate(template.getNamTemplate());
            
            listUser.add(userComp);
        }
        
        return listUser;
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
