package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.UnauthTmp;
import bdsmhost.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class UnauthTmpDao extends BaseDao implements Work {
    private String parameter = null;
    private static final String queryValidate = "{call PK_Unauth.validateUnauth(?)}";
    private static final String queryUpdate = "{call PK_Unauth.updateUnauth(?)}";
    private static final String queryReject = "{call PK_Unauth.rejectUnauth(?)}";
    private int workResult1 = 0;
    private int workResult2 = 0;
    private int workResult3 = 0;
    private int workType = 0;

    public UnauthTmpDao(Session session) {
        super(session);
    }

    public List<UnauthTmp> get(String param) {
        Query query = getSession().createQuery("from UnauthTmp where nobatch = :prm_batch");
        query.setString("prm_batch", param);
        return query.list();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((UnauthTmp) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((UnauthTmp) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((UnauthTmp) model);
        return true;
    }

    public void execute(Connection cnctn) {
        try {
            String query = null;
            switch (workType) {
                case 1:
                    query = queryValidate;
                    break;
                case 2:
                    query = queryUpdate;
                    break;
                case 3:
                    query = queryReject;
                    break;
            }
            CallableStatement stmt = cnctn.prepareCall(query);
            stmt.setString(1, parameter);
            int workRes = stmt.executeUpdate();
            stmt.close();
            switch (workType) {
                case 1:
                    workResult1 = workRes;
                    break;
                case 2:
                    workResult2 = workRes;
                    break;
                case 3:
                    workResult3 = workRes;
                    break;
            }
        } catch (SQLException ex) {
            
        }
    }

    public int runValidate(String param) {
        Session session = getSession();
        workType = 1;
        parameter = param;
        session.doWork(this);
        return workResult1;
    }

    public int runUpdate(String param) {
        Session session = getSession();
        workType = 2;
        parameter = param;
        session.doWork(this);
        return workResult2;
    }

    public int runReject(String param) {
        Session session = getSession();
        workType = 3;
        parameter = param;
        session.doWork(this);
        return workResult3;
    }
}
