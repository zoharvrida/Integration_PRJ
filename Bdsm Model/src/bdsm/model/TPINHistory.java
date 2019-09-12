package bdsm.model;


public class TPINHistory extends TPIN implements BaseHistoryInterface, java.io.Serializable {
	private BaseHistoryModel historyModel = new BaseHistoryModel();
	
	
	public TPINHistory() {}
	
	public TPINHistory(TPIN tpin) {
		this.setCardNo(tpin.getCardNo());
		this.setB24UpdatedTime(tpin.getB24UpdatedTime());
		this.setIVRStatus(tpin.getIVRStatus());
		this.setFiller(tpin.getFiller());
	}
	
	
	public java.sql.Timestamp getDtmCreatedOld() {
		return this.historyModel.getDtmCreatedOld();
	}
	public void setDtmCreatedOld(java.sql.Timestamp dtmCreatedOld) {
		this.historyModel.setDtmCreatedOld(dtmCreatedOld);
	}
	
	public java.sql.Timestamp getDtmUpdatedOld() {
		return this.historyModel.getDtmUpdatedOld();
	}
	public void setDtmUpdatedOld(java.sql.Timestamp dtmUpdatedOld) {
		this.historyModel.setDtmUpdatedOld(dtmUpdatedOld);
	}
	
}
