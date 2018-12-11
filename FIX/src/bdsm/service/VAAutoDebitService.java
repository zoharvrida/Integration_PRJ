package bdsm.service;


import static bdsm.util.BdsmUtil.convertStringToMoney;

import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.TmpVAHeader;


/**
 * 
 * @author bdsm
 */
public class VAAutoDebitService extends VAService {
	
    /**
     * 
     */
    public VAAutoDebitService() {
		this.setNonRegisteredVAAllowed(false);
	}
	
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     * @param inputString
     * @param oCharRead
     * @return
     * @throws Exception
     */
    @Override
	protected bdsm.scheduler.model.TmpVAHeader parseToVAHeader(String batchNo, int rowNo, String inputString, int[] oCharRead) throws Exception {
		int ctrColumn = 0;
		
		ctrColumn+=2; // Skip line indicator
		TmpVAHeader vaHeader = new TmpVAHeader(batchNo, rowNo);
		vaHeader.setProfileName(inputString.substring(ctrColumn, ctrColumn += 8).trim());
		vaHeader.setBusinessDate(VAService.formatter.parse(inputString.substring(ctrColumn, ctrColumn += 8).trim()));
		vaHeader.setInterfaceType(inputString.substring(ctrColumn, ctrColumn += 2).trim());
		vaHeader.setGLNo(inputString.substring(ctrColumn, ctrColumn += 12).trim());
		vaHeader.setCustomerCenter(inputString.substring(ctrColumn, ctrColumn += 5).trim());
		vaHeader.setApplicationCode(inputString.substring(ctrColumn, ctrColumn += 2).trim());
		vaHeader.setAccountNo(inputString.substring(ctrColumn, ctrColumn += 12).trim());
		vaHeader.setTransactionCurrency(inputString.substring(ctrColumn, ctrColumn += 3).trim());
		vaHeader.setAmount(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaHeader.setLocalCurrencyAmountOfTransaction(convertStringToMoney(inputString.substring(ctrColumn, ctrColumn += 16), 2));
		vaHeader.setAmountFee(convertStringToMoney("00", 2));
		vaHeader.setDescription1(inputString.substring(ctrColumn, ctrColumn += 30).trim());
		vaHeader.setDescription2(inputString.substring(ctrColumn, ctrColumn += 30).trim());
		vaHeader.setStatus(inputString.substring(ctrColumn, ctrColumn += 1).trim());
		vaHeader.setRejectCode(inputString.substring(ctrColumn, ctrColumn += 4).trim());
		vaHeader.setReferenceNo(inputString.substring(ctrColumn, ctrColumn += 20).trim());
		vaHeader.setX(inputString.charAt(ctrColumn++));
		vaHeader.setStatus(StatusDefinition.UNAUTHORIZED);
		
		oCharRead[0] = ctrColumn;
		
		return vaHeader;
	}
	
}
