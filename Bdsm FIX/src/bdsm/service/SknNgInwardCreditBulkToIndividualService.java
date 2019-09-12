package bdsm.service;


import org.hibernate.Session;


/**
 * @author v00019372
 */
public class SknNgInwardCreditBulkToIndividualService extends SknNgBulkService {

    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Header() {
		return 0;
	}

    /**
     * 
     * @return
     */
    @Override
	public int getLenG2DKE() {
		return 0;
	}

    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Detail() {
		return 0;
	}

    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Footer() {
		return 0;
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
		return (new SknNgWSProcessInwardCreditBulk(session, BIC, period, cityCode).requestInwardWSToSPK());
	}
	
	
    /**
     * 
     */
    protected class SknNgWSProcessInwardCreditBulk extends SknNgInwardWSProcess {
        /**
         * 
         * @param session
         * @param BIC
         * @param period
         * @param cityCode
         */
        public SknNgWSProcessInwardCreditBulk(Session session, String BIC, String period, String cityCode) {
			super(session, BIC, period, cityCode);
		}
		

        /**
         * 
         * @return
         */
        @Override
		public String getWebServiceOperationName() {
			return "getDKEKreditInwardBulk";
		}
	}


}
