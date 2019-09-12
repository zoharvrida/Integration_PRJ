/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.CoCiCustmast;
import bdsm.model.CoCiCustmastPK;
import bdsm.model.BaseModel;
import bdsm.model.FcrCiCustmast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;

/**
 *
 * @author user
 */
public class FcrCiCustmastDao extends BaseDao {

	private static final DateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy");
	private static final DateFormat formatterDate1 = new SimpleDateFormat("dd/MM/yyyy");

	public FcrCiCustmastDao(Session session) {
		super(session);
	}

	protected Logger getLogger() {
		return Logger.getLogger(getClass().getName());
	}

	public FcrCiCustmast get(Integer codCustId) {
		Criteria criteriaQuery = getSession().createCriteria(FcrCiCustmast.class);
		criteriaQuery.add(Restrictions.eq("compositeId.codCustId", codCustId));
		criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus", "A"));
		criteriaQuery.add(Restrictions.eq("codEntityVpd", new Integer(11)));
		return (FcrCiCustmast) criteriaQuery.uniqueResult();
	}

	public List<CoCiCustmast> getByNocard(String noCard) {
		this.getLogger().info("List 4");
		List<CoCiCustmast> custCard = new ArrayList<CoCiCustmast>();
		Query query = this.getSession().getNamedQuery("fcrCiCustMast#getByNoCard");
		query.setString("noCard", noCard);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		int obj = 0;
		this.getLogger().info(list);
		for (Map getBycard : list) {
			if (getBycard == null) {
				break;
			} else {
				CoCiCustmast ci = new CoCiCustmast();
				CoCiCustmastPK cipk = new CoCiCustmastPK();

				ci.setAddress(getBycard.get("address").toString());
				ci.setNamCustFull(getBycard.get("fullname").toString());
				ci.setCodCustId(Integer.parseInt(getBycard.get("cif").toString()));
				this.getLogger().info("CARD ATM Dao" + getBycard.get("nik").toString());
				cipk.setNik(getBycard.get("nik").toString());
				try {
					cipk.setDateofBirth(formatterDate1.parse(getBycard.get("dob").toString()));
				} catch (ParseException ex) {
					java.util.logging.Logger.getLogger(FcrCiCustmastDao.class.getName()).log(Level.SEVERE, null, ex);
				}
				ci.setCompositeId(cipk);
				custCard.add(ci);
			}
		}

		return custCard;
	}

    public List<CoCiCustmast> list(CoCiCustmast pk, String tax) {
		StringBuilder sb = new StringBuilder();
		List<CoCiCustmast> cicustmast = new ArrayList<CoCiCustmast>();
		this.getLogger().info("Customer " + pk.getNamCustFirst());
		if (pk.getNamCustFirst() != null && pk.getCompositeId().getDateofBirth() != null) {
            
			sb.delete(0, sb.length());
			this.getLogger().info("List 1");
			sb.append(" from CoCiCustmast where");
            sb.append(" namCustFirst=:pCustNamFisrt and");
			sb.append(" compositeId.dateofBirth =:pDateofBirth and");
	    if(!"".equalsIgnoreCase(tax)){
                sb.append(" compositeId.nik not like '%'||:pTax "
                        + " and");
            }
            sb.append(" compositeId.flgMntStatus='A' and compositeId.codEntityVpd=11 ");
			Query s = null; //"MM/dd/yyyy"
			
			s = getSession().createQuery(sb.toString());
			s.setString("pCustNamFisrt", pk.getNamCustFirst());
			try {
				s.setDate("pDateofBirth", pk.getCompositeId().getDateofBirth());
				getLogger().info("Date " + formatterDate.format(pk.getCompositeId().getDateofBirth()));
			} catch (Exception ex) {
				this.getLogger().info("Date Parse " + ex, ex);
			}
            if(!"".equalsIgnoreCase(tax)){
                s.setString("pTax", tax);
            }
			cicustmast = s.list();	
			this.getLogger().info("Check Data " + cicustmast.size());
			if (cicustmast.isEmpty()) {
				this.getLogger().info("List 2");
				sb.delete(0, sb.length());
				sb.append("from CoCiCustmast where");
				sb.append(" namCustFull=:pCustNamFull and");
				sb.append(" compositeId.dateofBirth =:pDateofBirth and");
                if(!"".equalsIgnoreCase(tax)){
                    sb.append(" compositeId.nik not like '%'||:pTax "
                            + " and");
                }
				sb.append(" compositeId.flgMntStatus='A' and compositeId.codEntityVpd=11 ");
				s = getSession().createQuery(sb.toString());
				s.setString("pCustNamFull", pk.getNamCustFirst());
				try {
					s.setDate("pDateofBirth", pk.getCompositeId().getDateofBirth());
					getLogger().info("Date " + formatterDate.format(pk.getCompositeId().getDateofBirth()));
				} catch (Exception ex) {
					this.getLogger().info("Date Parse " + ex, ex);
				}
                if(!"".equalsIgnoreCase(tax)){
                    s.setString("pTax", tax);
                }
				cicustmast = s.list();
			}
		} else if (pk.getCompositeId().getNik() != null) {
			this.getLogger().info("NIK " + pk.getCompositeId().getNik());

			this.getLogger().info("List 3");
			//sb.append("select coalesce(compositeId.namCustFirst,'') as compositeId.namCustFirst,coalesce(namCustFull,'') as namCustFull,address,codCustId,compositeId.nik,compositeId.dateofBirth ");
			sb.append("from CoCiCustmast where compositeId.nik =:pNik and");
			sb.append(" compositeId.flgMntStatus='A' and compositeId.codEntityVpd=11 ");
			Query s = null; //"MM/dd/yyyy"
			s = getSession().createQuery(sb.toString());
			s.setString("pNik", pk.getCompositeId().getNik());
			cicustmast = s.list();
			getLogger().info("ISI LIST 1 " + cicustmast);
		}
		getLogger().info("ISI LIST 2" + cicustmast);
		getLogger().info("LIST " + cicustmast.size());
		
		return cicustmast;
	}

    public List<CoCiCustmast> list(CoCiCustmast pk) {
        StringBuilder sb = new StringBuilder();
        List<CoCiCustmast> cicustmast = new ArrayList<CoCiCustmast>();
        this.getLogger().info("Customer " + pk.getNamCustFirst());
        if (pk.getNamCustFirst() != null && pk.getCompositeId().getDateofBirth() != null) {

            Query s = this.getSession().getNamedQuery("CiCustMast#getFirstName");
            String firsQuery =  s.getQueryString();
            
            String testQuery  = "SELECT * FROM CI_CUSTMAST A "
                    + "WHERE A.NAM_CUST_FULL = :pCustNamFisrt "
                    + "AND A.DAT_BIRTH_CUST = :pDateofBirth "
                    + "AND A.FLG_MNT_STATUS = 'A' "
                    + "AND A.COD_ENTITY_VPD = 11 "
                    + "AND EXISTS (SELECT 1 FROM SM_ACCESS_CODE_CUST_XREF C "
                    + "WHERE C.COD_CUST_ID = A.COD_CUST_ID "
                    + "AND C.FLG_MNT_STATUS = 'A' "
                    + "AND C.COD_ACCESS IN (SELECT B.COD_ACCESS FROM SM_ACCESS_CODES B "
                    + "WHERE B.COD_ACCESS IN (select regexp_substr((Select strval from parameter where cdparam='TAX.Access.Param'),"
                    + "'[0-9]+|[a-z]+|[A-Z]+',1,lvl) from (select (Select strval from parameter where cdparam='TAX.Access.Param'), level lvl "
                    + "from dual "
                    + "connect by level <= length((Select strval from parameter where cdparam='TAX.Access.Param')) - "
                    + "length(replace((Select strval from parameter where cdparam='TAX.Access.Param'), ',')) + 1))))";
            
            String fullQuery  = "SELECT * FROM CI_CUSTMAST A "
                    + "WHERE A.NAM_CUST_FULL = :pCustNamFull "
                    + "AND A.DAT_BIRTH_CUST = :pDateofBirth "
                    + "AND A.FLG_MNT_STATUS = 'A' "
                    + "AND A.COD_ENTITY_VPD = 11 "
                    + "AND EXISTS (SELECT 1 FROM SM_ACCESS_CODE_CUST_XREF C "
                    + "WHERE C.COD_CUST_ID = A.COD_CUST_ID "
                    + "AND C.FLG_MNT_STATUS = 'A' "
                    + "AND C.COD_ACCESS IN (SELECT B.COD_ACCESS FROM SM_ACCESS_CODES B "
                    + "WHERE B.COD_ACCESS IN (select regexp_substr((Select strval from parameter where cdparam='TAX.Access.Param'),"
                    + "'[0-9]+|[a-z]+|[A-Z]+',1,lvl) from (select (Select strval from parameter where cdparam='TAX.Access.Param'), level lvl "
                    + "from dual "
                    + "connect by level <= length((Select strval from parameter where cdparam='TAX.Access.Param')) - "
                    + "length(replace((Select strval from parameter where cdparam='TAX.Access.Param'), ',')) + 1))))";
            
            s = this.getSession().createSQLQuery(firsQuery);
            ((org.hibernate.SQLQuery) s).addEntity(CoCiCustmast.class);

            this.getLogger().info("List 1");

            s.setString("pCustNamFisrt", pk.getNamCustFirst());
            try {
                s.setDate("pDateofBirth", pk.getCompositeId().getDateofBirth());
                getLogger().info("Date " + formatterDate.format(pk.getCompositeId().getDateofBirth()));
            } catch (Exception ex) {
                this.getLogger().info("Date Parse " + ex, ex);
            }
            cicustmast = s.list();
            this.getLogger().info("Check Data " + cicustmast.size());
            if (cicustmast.isEmpty()) {
                this.getLogger().info("List 2");

                s = this.getSession().getNamedQuery("CiCustMast#getFullName");
                String lastQuery =  s.getQueryString();
                s = this.getSession().createSQLQuery(lastQuery);
                
                ((org.hibernate.SQLQuery) s).addEntity(CoCiCustmast.class);
                
                s.setString("pCustNamFull", pk.getNamCustFirst());
                try {
                    s.setDate("pDateofBirth", pk.getCompositeId().getDateofBirth());
                    getLogger().info("Date " + formatterDate.format(pk.getCompositeId().getDateofBirth()));
                } catch (Exception ex) {
                    this.getLogger().info("Date Parse " + ex, ex);
                }
                cicustmast = s.list();
            }
        } else if (pk.getCompositeId().getNik() != null) {
            this.getLogger().info("NIK " + pk.getCompositeId().getNik());

            this.getLogger().info("List 3");
            Query s = this.getSession().getNamedQuery("CiCustMast#getNikQuery");
            String firsQuery =  s.getQueryString();
            
            s = getSession().createSQLQuery(firsQuery);
            ((org.hibernate.SQLQuery) s).addEntity(CoCiCustmast.class);
            
            s.setString("pNik", pk.getCompositeId().getNik());       
            cicustmast = s.list();
            getLogger().info("ISI LIST 1 " + cicustmast);
        }
        getLogger().info("ISI LIST 2" + cicustmast);
        getLogger().info("LIST " + cicustmast.size());

        return cicustmast;
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
