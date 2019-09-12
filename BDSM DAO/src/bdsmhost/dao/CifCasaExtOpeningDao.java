/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.CoCiCustmast;
import bdsm.model.FixPatternTable;
import bdsm.model.ScreenOpening;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;

/**
 *
 * @author SDM
 */
public class CifCasaExtOpeningDao extends BaseDao {

    private Logger logger = Logger.getLogger(CifCasaExtOpeningDao.class);
    private DateFormat formatterDate = new SimpleDateFormat("ddMMyyyy");
    
    public CifCasaExtOpeningDao(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((ScreenOpening) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List getList(ScreenOpening so) {
        List soList = new ArrayList();
        Criteria criteriaQuery = getSession().createCriteria(ScreenOpening.class);
        criteriaQuery.add(Restrictions.eq("fullName", so.getFullName()));
        criteriaQuery.add(Restrictions.eq("birthDate", so.getBirthDate()));
        criteriaQuery.add(Restrictions.eq("cifNumber",so.getCifNumber()));
        criteriaQuery.add(Restrictions.eq("nik", so.getNik()));
        criteriaQuery.add(Restrictions.eq("recStat", "P"));
        soList = criteriaQuery.list();
        if (soList.isEmpty()) {
            criteriaQuery = getSession().createCriteria(ScreenOpening.class);
            criteriaQuery.add(Restrictions.eq("custFirstName", so.getFullName()));
            criteriaQuery.add(Restrictions.eq("birthDate", so.getBirthDate()));
            criteriaQuery.add(Restrictions.eq("cifNumber",so.getCifNumber()));
            criteriaQuery.add(Restrictions.eq("nik", so.getNik()));
            criteriaQuery.add(Restrictions.eq("recStat", "P"));
            soList = criteriaQuery.list();
        }
        return soList;
    }

    public Map valExistingData(String custName, Date datbirth, String nik){
        List<CoCiCustmast> dobList = new ArrayList();
        List nikList = new ArrayList();
        Map conclusionData = new HashMap();

		logger.debug("CHECK PARAMETER : " + custName + " " + datbirth + " " + nik);
        // nik exist on core banking
        Criteria criteriaQuery = getSession().createCriteria(CoCiCustmast.class);
        criteriaQuery.add(Restrictions.eq("compositeId.nik", nik));
        criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus","A"));
        nikList = criteriaQuery.list();

		logger.debug("CHECK NIK : " + nikList.size());
		
        // cek nik based on Name and DOB
        criteriaQuery = getSession().createCriteria(CoCiCustmast.class);
        criteriaQuery.add(Restrictions.eq("namCustFull", custName));
        criteriaQuery.add(Restrictions.eq("compositeId.dateofBirth", datbirth));
        criteriaQuery.add(Restrictions.eq("compositeId.nik", nik));
        criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus","A"));
        dobList = criteriaQuery.list();

        if(nikList.isEmpty()){
            // nik Not exist on Core Banking
            conclusionData = null;
        } else {
            // nik Exist on Core Banking
            if(dobList.isEmpty()){
                // different name DOB with NIK sent warning message
                conclusionData.put("recComment","Different name DOB with NIK");
                conclusionData.put("status", "1");
				conclusionData.put("recStat", "P");
            } else if(dobList.size() == 1){
                // Same name name DOB with NIK prepare to patch CIF number
                conclusionData.put("recComment","CIF Already Existing");
                conclusionData.put("status", "1");
				conclusionData.put("recStat", "P");
                conclusionData.put("cifNumber", dobList.get(0).getCodCustId());
            } else {
                // multiple NIK Error
                conclusionData.put("recComment","Multiple NIK Exist");
                conclusionData.put("status", "1");
				conclusionData.put("recStat", "R");
            }
        }
        return conclusionData;
    }

    public List getByExtChannelDistinct(CoCiCustmast pk) {
        List newlist = new ArrayList();
        logger.info("CHECK SESSION : " + getSession());
        logger.info("CHECK PK :" + pk.toString());
        logger.info("CHECK DATE :" + pk.getCompositeId().getDateofBirth());
        logger.info("DATE CONVERTER :" + formatterDate.format(pk.getCompositeId().getDateofBirth()));
        
        try {
            Query q = this.getSession().getNamedQuery("Cif#getDistinctExtData");
            q.setString("pFullName", pk.getNamCustFirst());
            q.setString("pDateBirth", formatterDate.format(pk.getCompositeId().getDateofBirth()));
            q.setString("pStatus", "2");
            q.setString("pRecStat", "P");
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            newlist = q.list();
            return newlist;
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return newlist;
        }
    }
    
    public List getByExtChannel(CoCiCustmast pk) {
        
        List extCimast = new ArrayList();
        ProjectionList prjList = Projections.projectionList();
        prjList.add(Projections.property("fullName"),"fullName");
        prjList.add(Projections.property("birthDate"));
        prjList.add(Projections.property("extUser"),"extUser");
        prjList.add(Projections.property("custFirstName"),"custFirstName");
        prjList.add(Projections.property("nik"),"nik");
        prjList.add(Projections.property("mailAddrs1"),"mailAddrs1");

        logger.info("CHECK SESSION : " + getSession());
        logger.info("CHECK PK :" + pk.toString());
        logger.info("CHECK DATE :" + pk.getCompositeId().getDateofBirth());
        logger.info("DATE CONVERTER :" + formatterDate.format(pk.getCompositeId().getDateofBirth()));
        
        Criteria criteriaQuery = getSession().createCriteria(ScreenOpening.class);
        
        criteriaQuery.add(Restrictions.eq("fullName", pk.getNamCustFirst())).getAlias();
        criteriaQuery.add(Restrictions.eq("birthDate", formatterDate.format(pk.getCompositeId().getDateofBirth())));
        
        criteriaQuery.setProjection(Projections.distinct(prjList));
        //criteriaQuery.setResultTransformer(new AliasToBeanResultTransformer(ScreenOpening.class));
        //.setProjection(Projections.distinct(prjList));
        //criteriaQuery.setResultTransformer(Criteria.ROOT_ENTITY);
        criteriaQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        extCimast = criteriaQuery.list();
        
        logger.info("LIST EXT :" + extCimast.size());
        if (extCimast.isEmpty()) {
            criteriaQuery = null;
            extCimast = new ArrayList();
            criteriaQuery = getSession().createCriteria(ScreenOpening.class);
            criteriaQuery.add(Restrictions.eq("custFirstName", pk.getNamCustFirst()));
            criteriaQuery.add(Restrictions.eq("birthDate", formatterDate.format(pk.getCompositeId().getDateofBirth())))
                    .setProjection(Projections.distinct(prjList));
            criteriaQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            extCimast = criteriaQuery.list();
            logger.info("LIST EXT 2:" + extCimast.size());
            logger.info("LISTMAST : " + extCimast);
        }
        return extCimast;
    }

    public List<ScreenOpening> getNumber(String batchId, Integer record_id) {
        Criteria criteriaQuery = getSession().createCriteria(ScreenOpening.class);
        criteriaQuery.add(Restrictions.eq("batchId", batchId));
        if (record_id.compareTo(0) > 0) {
            criteriaQuery.add(Restrictions.eq("recId", record_id));
        }
        return criteriaQuery.list();
    }

public List<ScreenOpening> getEntityByStat(String batchId, String flag) {
        Criteria criteriaQuery = getSession().createCriteria(ScreenOpening.class);
        criteriaQuery.add(Restrictions.eq("batchId", batchId));
        if (!"".equalsIgnoreCase(flag)) {
            criteriaQuery.add(Restrictions.eq("recStat", flag));
        }
        return criteriaQuery.list();
    }

    public List getExtChannelData(ScreenOpening so) {
        List newlist = new ArrayList();
        logger.info("CHECK SESSION : " + getSession());

        try {
            Query q = this.getSession().getNamedQuery("Cif#getExternalData");
            q.setString("pBatchNo",so.getApplicationID());
            q.setString("pRecStat",so.getRecStat());
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            newlist = q.list();
            return newlist;
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return newlist;
        }
    }
    public List getUpdateCIF(String nik){
        Criteria criteriaQuery = getSession().createCriteria(ScreenOpening.class);
        criteriaQuery.add(Restrictions.eq("nik", nik));
        criteriaQuery.add(Restrictions.eq("recStat", "P"));
        criteriaQuery.add(Restrictions.eq("status", "1"));
        return criteriaQuery.list();
    }
    public List getLeftOverCIF(Integer cod_cust, Map parameter, String appID) {
        List newlist = null;
        List repList = null;
        try {
            Query q = this.getSession().getNamedQuery("Cif#getExistingChannelCIF");
            q.setInteger("pcif", cod_cust);
            q.setString("pappId", appID);

            q.setString("pEducation", parameter.get("lastEdu").toString());
            q.setString("pMarstat", parameter.get("marStat").toString());
            q.setString("pReligion", parameter.get("religion").toString());
            q.setString("pAlias", parameter.get("aliasName").toString());
            q.setString("pMother", parameter.get("namMother").toString());
            q.setString("pResidence", parameter.get("residenceType").toString());
            q.setString("piDcardType", parameter.get("iDcardType").toString());
            q.setString("pExpDate", parameter.get("iCExpiryDate").toString());
            q.setString("pBirthPlace", parameter.get("birthPlace").toString());
            q.setString("pIncome", parameter.get("monthLyIncome").toString());
            q.setString("pDataExtract", parameter.get("dataTransXtractFlagDesc").toString());
            q.setString("pHomeStatus", parameter.get("homeStatusDesc").toString());
            q.setString("pNoDepend", parameter.get("noOfDependant").toString());
            q.setString("pTin", parameter.get("tin").toString());
            q.setString("pGreenCard", parameter.get("greenCard").toString());
            q.setString("pAlamatAS", parameter.get("alamatAS").toString());
            q.setString("pBadanAS", parameter.get("badanAS").toString());
            q.setString("pFatca", parameter.get("fatca").toString());
            q.setString("pExpectedLimit", parameter.get("expectedLimit").toString());
            q.setString("pCommunication", parameter.get("communication").toString());
            q.setString("pSumberDanaLain", parameter.get("sumberDanaLain").toString());


            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            logger.info("QUERY CONVERTED SUCCESSFULLY");
            newlist = q.list();
            if (newlist.isEmpty()) {
                q = this.getSession().getNamedQuery("Cif#getReplicatedCIFNoDetlChannel");
                q.setInteger("pcif", cod_cust);
                q.setString("pappId", appID);

                q.setString("pEducation", parameter.get("lastEdu").toString());
                q.setString("pMarstat", parameter.get("marStat").toString());
                q.setString("pReligion", parameter.get("religion").toString());
                q.setString("pAlias", parameter.get("aliasName").toString());
                q.setString("pMother", parameter.get("namMother").toString());
                q.setString("pResidence", parameter.get("residenceType").toString());
                q.setString("piDcardType", parameter.get("iDcardType").toString());
                q.setString("pExpDate", parameter.get("iCExpiryDate").toString());
                q.setString("pBirthPlace", parameter.get("birthPlace").toString());
                q.setString("pIncome", parameter.get("monthLyIncome").toString());
                q.setString("pDataExtract", parameter.get("dataTransXtractFlagDesc").toString());
                q.setString("pHomeStatus", parameter.get("homeStatusDesc").toString());
                q.setString("pNoDepend", parameter.get("noOfDependant").toString());
                q.setString("pTin", parameter.get("tin").toString());
                q.setString("pGreenCard", parameter.get("greenCard").toString());
                q.setString("pAlamatAS", parameter.get("alamatAS").toString());
                q.setString("pBadanAS", parameter.get("badanAS").toString());
                q.setString("pFatca", parameter.get("fatca").toString());
                q.setString("pExpectedLimit", parameter.get("expectedLimit").toString());
                q.setString("pCommunication", parameter.get("communication").toString());
                q.setString("pSumberDanaLain", parameter.get("sumberDanaLain").toString());


                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                logger.info("QUERY CONVERTED SUCCESSFULLY");
                repList = q.list();
                if(repList.isEmpty()){
                    q = this.getSession().getNamedQuery("Cif#getExternalData");
                    q.setString("pBatchNo", appID);
                    q.setString("pRecStat", "2");
                    return q.list();
                } else {
                    return repList;
                }
            } else {
                return newlist;
            }
        } catch (HibernateException hibernateException) {
            logger.info("HIBERNATE :" + hibernateException, hibernateException);
            return newlist;
        }
    }
}
