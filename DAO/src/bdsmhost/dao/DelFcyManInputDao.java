/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;


import bdsm.model.BaseModel;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;



/**
 *
 * @author 00030663
 */
public class DelFcyManInputDao extends BaseDao {
    

    public DelFcyManInputDao(Session session) {
        super(session);
    }

    public int execute (
	final String cifNo,
	final String txnRefNo,
	final String loginUserId,
	final String spvUserId,
	final String udNo,
	final String udCcy,
	final Date txnDate,
	final String txnCcy,
	final BigDecimal txnAmount,
	final BigDecimal lcyAmount,
	final BigDecimal usdIdrRate,
	final BigDecimal usdTxnAmount,
	final String description) {

        final int res[] = {0};
        
        getSession().doWork(new Work() {
          //@Override
          public void execute(Connection connection) throws java.sql.SQLException {
            String sqlstmn = "CALL spDelFcyManInput(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
            CallableStatement call = connection.prepareCall(sqlstmn);
            call.registerOutParameter(1, Types.INTEGER);
            call.setString(2, cifNo);
            call.setString(3, txnRefNo);
            call.setString(3, loginUserId);
            call.setString(4, spvUserId);
            call.setString(5, udNo);
            call.setString(6, udCcy);
            call.setDate(7, txnDate);
            call.setString(8, txnCcy);
	    call.setBigDecimal(9, txnAmount);
	    call.setBigDecimal(10, lcyAmount);
	    call.setBigDecimal(11, usdIdrRate);
	    call.setBigDecimal(12, usdTxnAmount);
	    call.setString(13, description);

            call.execute();
            res[0] = call.getInt(1); // propagate this back to enclosing class
          }
        });
 
        return res[0];
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
