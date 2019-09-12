package bdsmhost.web.fcr;


import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import bdsm.fcr.model.BDSMBICloseRate;
import bdsm.fcr.model.BaCcyCode;
import bdsmhost.dao.LLDDAO;
import bdsmhost.fcr.dao.BDSMBICloseRateDAO;
import bdsmhost.fcr.dao.BaCcyCodeDAO;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BICloseRateAction extends bdsmhost.web.BaseHostAction {
	private String namCcy;
	private Integer codCcy;
	private java.util.Date closeDate;
	private BDSMBICloseRate model;
	private java.util.List<BDSMBICloseRate> modelList;
	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		BDSMBICloseRateDAO dao = new BDSMBICloseRateDAO(this.getHSession());
		
		if ((StringUtils.isBlank(this.namCcy)) && (this.codCcy == null)) // Get USD Rate
			this.model = dao.get();
		else {
			if (this.codCcy == null) {
				BaCcyCodeDAO ccyDAO = new BaCcyCodeDAO(this.getHSession());
				BaCcyCode ccyCode = ccyDAO.getByNamCcyShort(this.namCcy);
				
				if (ccyCode != null)
					this.codCcy = ccyCode.getCompositeId().getCodCcy(); 
			}
			
			if (this.codCcy != null)
				this.model = dao.get(this.codCcy);
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @JSON(serialize=false)
	public String getByCloseDate() {
		BDSMBICloseRateDAO dao = new BDSMBICloseRateDAO(this.getHSession());
		
		if ((StringUtils.isBlank(this.namCcy)) && (this.codCcy == null)) // Get USD Rate
			this.namCcy = "USD";
		
		if (this.codCcy == null) {
			BaCcyCodeDAO ccyDAO = new BaCcyCodeDAO(this.getHSession());
			BaCcyCode ccyCode = ccyDAO.getByNamCcyShort(this.namCcy);
			
			if (ccyCode != null)
				this.codCcy = ccyCode.getCompositeId().getCodCcy(); 
		}
		
		if (this.codCcy != null)
			this.modelList = dao.getByCloseDate(this.codCcy, this.closeDate);
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @JSON(serialize=false)
	public String getLastEOMRate() {
		LLDDAO dao = new LLDDAO(this.getHSession());
		this.model = new BDSMBICloseRate();
		
		try {
			java.math.BigDecimal value = new java.math.BigDecimal("0.0");
			if ((StringUtils.isBlank(this.namCcy)) && (this.codCcy == null)) { // Get USD rate
				value = dao.getUSDRate();
			}
			else {
				if (this.codCcy != null) // Get fcy rate by code ccyx
					value = dao.getRateByCodCcy(this.codCcy);
				else  // Get fcy rate by code ccy
					value = dao.getRateByNamCcy(this.namCcy);
			}
			
			this.model.setMidRate(value.doubleValue());
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}


    /**
     * 
     * @param namCcy
     */
    public void setNamCcy(String namCcy) {
		this.namCcy = namCcy;
	}

    /**
     * 
     * @param codCcy
     */
    public void setCodCcy(Integer codCcy) {
		this.codCcy = codCcy;
	}

    /**
     * 
     * @param closeDate
     */
    public void setCloseDate(java.util.Date closeDate) {
		this.closeDate = closeDate;
	}

    /**
     * 
     * @return
     */
    public BDSMBICloseRate getModel() {
		return this.model;
	}

    /**
     * 
     * @return
     */
    public java.util.List<BDSMBICloseRate> getModelList() {
		return this.modelList;
	}

}
