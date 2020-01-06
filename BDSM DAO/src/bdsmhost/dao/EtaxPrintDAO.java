/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.EtaxPrint;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionImpl;

/**
 *
 * @author v00024535
 */
public class EtaxPrintDAO extends BaseDao {

    private static final String cleanse = "{? = call KONVERTSTR(?)}";
    private int workResult = 0;
    private String hasil;

    public String cleans(BigDecimal param) throws SQLException {
        Connection cnctn = ((SessionImpl) super.getSession()).connection();
        String query = null;

        query = cleanse;
        CallableStatement stmt = cnctn.prepareCall(query);
        stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
        stmt.setBigDecimal(2, param);
        workResult = stmt.executeUpdate();
        hasil = stmt.getString(1);
        stmt.close();

        return hasil;
    }

    public EtaxPrintDAO(Session session) {
        super(session);
    }

    public EtaxPrint findAll(String idTrx) {
        Criteria criteriaQuery = getSession().createCriteria(EtaxPrint.class);
        criteriaQuery.add(Restrictions.eq("idTrx", idTrx));

        return (EtaxPrint) criteriaQuery.uniqueResult();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
