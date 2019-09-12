package bdsm.scheduler.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpVADetail;
import bdsm.scheduler.model.TmpVAPK;
import bdsm.scheduler.model.TmpVAFooter;
import bdsm.scheduler.model.TmpVAHeader;
import bdsmhost.dao.BaseDao;


public class TmpVADao extends BaseDao {
	
	public TmpVADao(Session session) {
		super(session);
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}
	
	
	public TmpVAHeader getHeader(String batchNo, int rowNo) {
		return (TmpVAHeader) this.getHeader(new TmpVAPK(batchNo, rowNo));
	}
	
	public TmpVAHeader getHeader(TmpVAPK id) {
		return (TmpVAHeader) this.getSession().get(TmpVAHeader.class, id);
	}
	
	public List<TmpVAHeader> getHeadersByBatchNo(String batchNo) {
		Criteria crt = this.getSession().createCriteria(TmpVAHeader.class);
		crt.add(Restrictions.eq("id.batchNo", batchNo));
		crt.addOrder(Order.asc("id.rowNo"));
		
		return crt.list();
	}
	
	public List<TmpVAHeader> getHeadersByChecksum(String checksum) {
		Query query = this.getSession().getNamedQuery("TmpVA#getVAHeaderByChecksum");
		query.setString("checksum", checksum);
		
		return (List<TmpVAHeader>) query.list();
	}
	
	
	public TmpVADetail getDetail(String batchNo, int rowNo) {
		return (TmpVADetail) this.getDetail(new TmpVAPK(batchNo, rowNo));
	}
	
	public TmpVADetail getDetail(TmpVAPK id) {
		return (TmpVADetail) this.getSession().get(TmpVADetail.class, id);
	}
	
	public List<TmpVADetail> getDetailsByBatchNo(String batchNo) {
		return this.getDetailsByBatchNoAndStatus(batchNo, (Character)null);
	}
	
	public List<TmpVADetail> getDetailsByBatchNoAndStatus(String batchNo, String status) {
		return this.getDetailsByBatchNoAndStatus(batchNo, status.charAt(0));
	}
	
	public List<TmpVADetail> getDetailsByBatchNoAndStatus(String batchNo, Character status) {
		return this.getDetailsByBatchNoAndStatus(batchNo, status, false);
	}
	
	public List<TmpVADetail> getDetailsByBatchNoAndInverseStatus(String batchNo, String status) {
		return this.getDetailsByBatchNoAndInverseStatus(batchNo, status.charAt(0));
	}
	
	public List<TmpVADetail> getDetailsByBatchNoAndInverseStatus(String batchNo, Character status) {
		return this.getDetailsByBatchNoAndStatus(batchNo, status, true);
	}
	
	private List<TmpVADetail> getDetailsByBatchNoAndStatus(String batchNo, Character status, boolean inverseStatus) {
		Criteria crt = this.getSession().createCriteria(TmpVADetail.class);
		crt.add(Restrictions.eq("id.batchNo", batchNo));
		if (status != null)
			if (inverseStatus)
				crt.add(Restrictions.ne("statusDB", status));
			else
				crt.add(Restrictions.eq("statusDB", status));
		crt.addOrder(Order.asc("id.rowNo"));
		
		return crt.list();
	}
	
	public List<TmpVADetail> getDetailsByHeader(TmpVAHeader header) {
		return this.getDetailsByHeaderAndStatus(header, (Character)null);
	}
	
	public List<TmpVADetail> getDetailsByHeaderAndStatus(TmpVAHeader header, String status) {
		return this.getDetailsByHeaderAndStatus(header, status.charAt(0));
	}
	
	public List<TmpVADetail> getDetailsByHeaderAndStatus(TmpVAHeader header, Character status) {
		return this.getDetailsByHeaderAndStatus(header, status, false);
	}
	
	public List<TmpVADetail> getDetailsByHeaderAndInverseStatus(TmpVAHeader header, String status) {
		return this.getDetailsByHeaderAndInverseStatus(header, status.charAt(0));
	}
	
	public List<TmpVADetail> getDetailsByHeaderAndInverseStatus(TmpVAHeader header, Character status) {
		return this.getDetailsByHeaderAndStatus(header, status, true);
	}
	
	private List<TmpVADetail> getDetailsByHeaderAndStatus(TmpVAHeader header, Character status, boolean inverseStatus) {
		Criteria crt = this.getSession().createCriteria(TmpVADetail.class);
		crt.add(Restrictions.eq("id.batchNo", header.getBatchNo()));
		crt.add(Restrictions.eq("rowNoHeader", header.getRowNo()));
		if (status != null)
			if (inverseStatus)
				crt.add(Restrictions.ne("statusDB", status));
			else
				crt.add(Restrictions.eq("statusDB", status));
		crt.addOrder(Order.asc("id.rowNo"));
		
		return crt.list();
	}
	
	
	public TmpVAFooter getFooter(String batchNo, int rowNo) {
		return (TmpVAFooter) this.getFooter(new TmpVAPK(batchNo, rowNo));
	}
	
	public TmpVAFooter getFooter(TmpVAPK id) {
		return (TmpVAFooter) this.getSession().get(TmpVAFooter.class, id);
	}
	
	public TmpVAFooter getFooterByHeader(TmpVAHeader header) {
		Criteria crt = this.getSession().createCriteria(TmpVAFooter.class);
		crt.add(Restrictions.eq("id.batchNo", header.getBatchNo()));
		crt.add(Restrictions.eq("rowNoHeader", header.getRowNo()));
		
		return (TmpVAFooter) crt.uniqueResult();
	}
	
	public List<TmpVAFooter> getFootersByBatchNo(String batchNo) {
		Criteria crt = this.getSession().createCriteria(TmpVAFooter.class);
		crt.add(Restrictions.eq("id.batchNo", batchNo));
		crt.addOrder(Order.asc("id.rowNo"));
		
		return crt.list();
	}
	
	
	public Object[] getBinDetailByBinNo(String binNo) {
		Query query = this.getSession().getNamedQuery("TmpVA#getBinDetailByBinNo");
		query.setString("binNo", binNo);
		
		return (Object[]) query.uniqueResult();
	}
	
	public Object[] getVirtualAccountFromRealAccount(String realAccountNo, String binDummyNo) {
		Query query = this.getSession().getNamedQuery("TmpVA#getVirtualAccountFromRealAccount");
		query.setString("binDummy", binDummyNo);
		query.setString("realAcctNo", realAccountNo);
		
		return (Object[]) query.uniqueResult();
	}
	
	public Object[] getRealAccountFromVirtualAccount(String virtualAccountNo) {
		Query query = this.getSession().getNamedQuery("TmpVA#getRealAccountFromVirtualAccount");
		query.setString("vaNo", virtualAccountNo);
		
		return (Object[]) query.uniqueResult();
	}

}
