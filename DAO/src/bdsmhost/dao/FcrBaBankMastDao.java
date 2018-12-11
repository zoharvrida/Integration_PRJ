/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrBaBankMast;
import bdsm.model.FcrBaBankMastPK;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;

/**
 *
 * @author 00030663
 */
public class FcrBaBankMastDao extends BaseDao {
    public FcrBaBankMastDao(Session session) {
        super(session);
    }
    
    public FcrBaBankMast get() {
        FcrBaBankMastPK pk = new FcrBaBankMastPK();
        pk.setCodBank(11);
        pk.setFlgMntStatus("A");
        return (FcrBaBankMast) getSession().get(FcrBaBankMast.class, pk);
    }

    public Timestamp getFCRtime(){
        FcrBaBankMast FCR = get();
        Calendar calendar = Calendar.getInstance();
        Calendar calendarFCR = Calendar.getInstance();
        calendarFCR.setTime(FCR.getDatProcess());
        calendarFCR.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY));
        calendarFCR.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE));
        calendarFCR.set(Calendar.SECOND,calendar.get(Calendar.SECOND));      
        return new Timestamp(calendarFCR.getTime().getTime());
    }

    public Date getDayMonth(String flag) {
        FcrBaBankMast FCR = get();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(FCR.getDatProcess());
        if ("LAST".equalsIgnoreCase(flag)) {
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
        } else if("FIRST".equalsIgnoreCase(flag)){
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.getTime();
    }
    public Timestamp getFCRnextProc(){
        FcrBaBankMast FCR = get();
        Calendar calendar = Calendar.getInstance();
        Calendar calendarFCR = Calendar.getInstance();
        calendarFCR.setTime(FCR.getDatNextProcess());
        calendarFCR.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY));
        calendarFCR.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE));
        calendarFCR.set(Calendar.SECOND,calendar.get(Calendar.SECOND));      
        return new Timestamp(calendarFCR.getTime().getTime());
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
