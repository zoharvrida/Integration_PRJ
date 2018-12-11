package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.CustomResult1;
import bdsm.model.FixTemplateMaster;
import bdsm.model.FixTemplateMasterPK;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class FixTemplateMasterDao extends BaseDao {

    public FixTemplateMasterDao(Session session) {
        super(session);
    }

    public FixTemplateMaster get(FixTemplateMasterPK fixTemplateMasterPK) {
        Criteria criteria = getSession().createCriteria(FixTemplateMaster.class);
        if (fixTemplateMasterPK.getIdTemplate() != null) {
            criteria.add(Restrictions.eq("fixTemplateMasterPK.idTemplate", fixTemplateMasterPK.getIdTemplate()));
        }
        if (fixTemplateMasterPK.getNamTemplate() != null) {
            criteria.add(Restrictions.eq("fixTemplateMasterPK.namTemplate", fixTemplateMasterPK.getNamTemplate()));
        }
        List<FixTemplateMaster> list = criteria.list();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    //For FixImportMail
    public CustomResult1 getAccessTemplate(String sender, String namScheduler, String typFix) {
        CustomResult1 customResult1 = null;
        Query q = getSession().getNamedQuery("fixTemplateMaster#getAccessTemplate1");
        q.setString("typFix", typFix);
        q.setString("sender", sender.toLowerCase());
        q.setString("namScheduler", namScheduler);

        Object[] customResult = (Object[]) q.uniqueResult();

        if (customResult != null) {
            customResult1 = new CustomResult1();
            customResult1.setGrpId(customResult[0].toString());
            customResult1.setCdAccess(customResult[1].toString());
            customResult1.setIdTemplate(Integer.parseInt(customResult[2].toString()));
            customResult1.setJavaClass(customResult[3].toString());
            customResult1.setNamTemplate(customResult[4].toString());
            customResult1.setIdScheduler(Integer.parseInt(customResult[5].toString()));
            customResult1.setFilePattern(customResult[6].toString());
            customResult1.setFileExtension((customResult[7]==null)? "": customResult[7].toString());
            customResult1.setFlgEncrypt(customResult[8].toString());
            customResult1.setModDecrypt(customResult[9].toString());
            customResult1.setKeyDecrypt(customResult[10].toString());
            customResult1.setSpvAuth(customResult[11].toString());
            customResult1.setFlgChecksum(customResult[12].toString());
            customResult1.setFlgStatus(customResult[13].toString());
        }

        return customResult1;
    }

    @Override
    protected boolean doInsert(BaseModel baseModel) {
        getSession().save(baseModel);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel baseModel) {
        getSession().update(baseModel);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel baseModel) {
        getSession().delete(baseModel);
        return true;
    }
}
