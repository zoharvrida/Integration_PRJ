package bdsm.service;


import org.hibernate.Session;

import bdsm.model.SknNgInOutCreditDetail;
import bdsm.model.SknNgInOutCreditFooter;
import bdsm.model.SknNgInOutCreditHeader;


/**
 * @author v00019372
 */
public class SknNgInwardCreditService extends SknNgCreditService {
    /**
     * 
     */
    public static final String TYPE_IN = "I";


    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @return
     */
    public SknNgInOutCreditHeader parseToInOutHeader(String line, String batchNo, Integer recordId) {
		SknNgInOutCreditHeader header = (new SknNgOutwardCreditService()).parseToInOutHeader(line, batchNo, recordId);
		header.setType(TYPE_IN);
		
		return header;
	}

    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @param header
     * @return
     */
    public SknNgInOutCreditDetail parseToInOutDetail(String line, String batchNo, Integer recordId, SknNgInOutCreditHeader header) {
		SknNgInOutCreditDetail detail = (new SknNgOutwardCreditService()).parseToInOutDetail(line, batchNo, recordId, header);
		detail.setType(TYPE_IN);
		
		return detail;
	}

    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @param header
     * @return
     */
    public SknNgInOutCreditFooter parseToInOutFooter(String line, String batchNo, Integer recordId, SknNgInOutCreditHeader header) {
		SknNgInOutCreditFooter footer = (new SknNgOutwardCreditService()).parseToInOutFooter(line, batchNo, recordId, header);
		footer.setType(TYPE_IN);
		
		return footer;
	}


    /**
     * 
     * @param modelHeader
     * @return
     */
    public String formatFromInOutHeader(SknNgInOutCreditHeader modelHeader) {
		return (new SknNgOutwardCreditService()).formatFromInOutHeader(modelHeader);
	}

    /**
     * 
     * @param modelDetail
     * @return
     */
    public String formatFromInOutDetail(SknNgInOutCreditDetail modelDetail) {
		return (new SknNgOutwardCreditService()).formatFromInOutDetail(modelDetail);
	}

    /**
     * 
     * @param modelFooter
     * @param CRC
     * @return
     */
    public String formatFromInOutFooter(SknNgInOutCreditFooter modelFooter, int CRC) {
		return (new SknNgOutwardCreditService()).formatFromInOutFooter(modelFooter, CRC);
	}
	
    /**
     * 
     * @param session
     * @param BIC
     * @param period
     * @param cityCode
     * @return
     */
    public String getResultOfRequestInwardWSToSPK(Session session, String BIC, String period, String cityCode) {
		return (new SknNgWSProcessInwardCredit(session, BIC, period, cityCode).requestInwardWSToSPK());
	}

	
	
    /**
     * 
     */
    protected class SknNgWSProcessInwardCredit extends SknNgInwardWSProcess {
        /**
         * 
         * @param session
         * @param BIC
         * @param period
         * @param cityCode
         */
        public SknNgWSProcessInwardCredit(Session session, String BIC, String period, String cityCode) {
			super(session, BIC, period, cityCode);
		}

        /**
         * 
         * @return
         */
        @Override
		public String getWebServiceOperationName() {
			return "getDKEKreditIndividual";
		}
	}
	
}
