package bdsm.model;


import java.sql.Timestamp;

/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaseHistoryModel implements BaseHistoryInterface, java.io.Serializable {
	private Timestamp dtmCreatedOld;
    private Timestamp dtmUpdatedOld;
    
    
	/**
	 * @return the dtmCreatedOld
	 */
	public Timestamp getDtmCreatedOld() {
		return this.dtmCreatedOld;
	}

	/**
	 * @param dtmCreatedOld the dtmCreatedOld to set
	 */
	public void setDtmCreatedOld(Timestamp dtmCreatedOld) {
		this.dtmCreatedOld = dtmCreatedOld;
	}

	/**
	 * @return the dtmUpdatedOld
	 */
	public Timestamp getDtmUpdatedOld() {
		return this.dtmUpdatedOld;
	}

	/**
	 * @param dtmUpdatedOld the dtmUpdatedOld to set
	 */
	public void setDtmUpdatedOld(Timestamp dtmUpdatedOld) {
		this.dtmUpdatedOld = dtmUpdatedOld;
	}
}
