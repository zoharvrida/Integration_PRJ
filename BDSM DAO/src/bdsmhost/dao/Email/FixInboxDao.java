package bdsmhost.dao.Email;

import bdsm.model.BaseModel;
import bdsm.model.Email.FixInbox;
import bdsm.util.SchedulerUtil;
import bdsm.util.StatusDefinition;
import bdsmhost.dao.BaseDao;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class FixInboxDao extends BaseDao {

    public FixInboxDao(Session session) {
        super(session);
    }

    public FixInbox get(String inboxId) {
        Criteria criteria = getSession().createCriteria(FixInbox.class);
        criteria.add(Restrictions.eq("fixInboxPK.inboxId", inboxId));
        List list = criteria.list();
        if (list.size() == 1) {
            return (FixInbox) list.get(0);
        }
        return null;
    }

    @Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save((FixInbox) baseModel);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update((FixInbox) baseModel);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete((FixInbox) baseModel);
        return true;
    }

    public FixInbox getItemIdLink(String subject) {
        StringBuilder sb = new StringBuilder("Select A from FixInbox A, FixLog B where A.fixInboxPK.inboxId = B.fixLogPK.inboxId ");
        sb.append("and A.fixInboxPK.subject = :subject and B.fixLogPK.typFix = '" + StatusDefinition.IN + "' ");
        sb.append("and A.flgProcess = '" + StatusDefinition.DONE + "' and B.flgProcess = '" + StatusDefinition.REQUEST + "' ");
        sb.append("and A.fixInboxPK.dtPost = :pDtPost");
        Query query = getSession().createQuery(sb.toString());
        query.setString("subject", subject);
        query.setDate("pDtPost", SchedulerUtil.getTime());
        List<FixInbox> list = query.list();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public FixInbox getDuplicateSameDay(String subject, String inboxId) {
        Calendar calendar = Calendar.getInstance();
        Date dt = new java.sql.Date(calendar.getTime().getDate());
        StringBuilder sb = new StringBuilder("from FixInbox ");
        sb.append("WHERE fixInboxPK.subject = :subject ");
        sb.append("and flgProcess in ('" + StatusDefinition.DONE + "','" + StatusDefinition.ERROR + "',");
        sb.append("'" + StatusDefinition.REQUEST + "','" + StatusDefinition.PROCESS + "') ");
        sb.append("and fixInboxPK.inboxId <> '" + inboxId + "'");
        Query query = getSession().createQuery(sb.toString());
        query.setString("subject", subject);
        List<FixInbox> list = query.list();
        if (list.size() != 0) {
            return list.get(0);
        }
        return null;
    }
}
