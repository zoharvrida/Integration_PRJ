package bdsm.fcr.model;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
*
* @author v00019372
*/
@SuppressWarnings("serial")
public class BaBankMast implements java.io.Serializable {
	private BaBankMastPK compositeId;
	private String namBank;
	private String namBankShrt;
	private Date datLastProcess;
	private Date datProcess;
	private Date datNextProcess;
    private String codBankCentral;

    public String getCodBankCentral() {
        return codBankCentral;
    }

    public void setCodBankCentral(String codBankCentral) {
        this.codBankCentral = codBankCentral;
    }
	
	
	public BaBankMast() {}
	
	
	public Date getBusinessDate() {
		return this.datProcess;
	}
	
	public Timestamp getBusinessTime() {
		Date systemDate = new Date();
		Calendar businessCal = Calendar.getInstance();
		Calendar systemCal = Calendar.getInstance();

		systemCal.setTime(systemDate);
		businessCal.setTime(this.getBusinessDate());
		businessCal.set(Calendar.HOUR_OF_DAY, systemCal.get(Calendar.HOUR_OF_DAY));
		businessCal.set(Calendar.MINUTE, systemCal.get(Calendar.MINUTE));
		businessCal.set(Calendar.SECOND, systemCal.get(Calendar.SECOND));
		
		return new Timestamp(businessCal.getTimeInMillis());
	} 
	
	
	public BaBankMast(BaBankMastPK compositeId) {
		this.compositeId = compositeId;
	}
	
	
	public BaBankMastPK getCompositeId() {
		return compositeId;
	}
	public void setCompositeId(BaBankMastPK compositeId) {
		this.compositeId = compositeId;
	}

	public String getNamBank() {
		return this.namBank;
	}
	public void setNamBank(String namBank) {
		this.namBank = namBank;
	}

	public String getNamBankShrt() {
		return this.namBankShrt;
	}
	public void setNamBankShrt(String namBankShrt) {
		this.namBankShrt = namBankShrt;
	}

	public Date getDatLastProcess() {
		return this.datLastProcess;
	}
	public void setDatLastProcess(Date datLastProcess) {
		this.datLastProcess = datLastProcess;
	}

	public Date getDatProcess() {
		return this.datProcess;
	}
	public void setDatProcess(Date datProcess) {
		this.datProcess = datProcess;
	}

	public Date getDatNextProcess() {
		return this.datNextProcess;
	}
	public void setDatNextProcess(Date datNextProcess) {
		this.datNextProcess = datNextProcess;
	}

}
