package bdsm.model;


import java.sql.Timestamp;


/**
 * 
 * @author v00019372
 */
public interface BaseHistoryInterface {
	Timestamp getDtmCreatedOld();
	void setDtmCreatedOld(Timestamp dtmCreatedOld);

	Timestamp getDtmUpdatedOld();
	void setDtmUpdatedOld(Timestamp dtmUpdatedOld);
}
