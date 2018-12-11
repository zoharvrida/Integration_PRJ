/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.model.*;
import bdsm.util.SchedulerUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 *
 * @author bdsm
 */
public class NtfsFcyRateReaderWorker extends BaseProcessor {

    /**
     * 
     * @param context
     */
    public NtfsFcyRateReaderWorker(Map context) {
		super(context);
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String bdsmBatch = (String) context.get(MapKey.batchNo);
		String filename = (String) context.get(MapKey.param5);
		String status = context.get(MapKey.status).toString();
		String sourceProcess = context.get(MapKey.processSource).toString();
		if (status.equals(StatusDefinition.UNAUTHORIZED)) {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			NtfsFcyRateReaderWorkerDao workerDao = null;
			String line = "";
			String[] theData = null;
			getLogger().info("Start Loop textfile Data");
			while ((line = br.readLine()) != null) {
				getLogger().info("Line : " + line);
				if (!"".equalsIgnoreCase(line)) {
					theData = line.split("\\|");
					if (theData[0].equalsIgnoreCase("0")) {
						doImportHeader(theData, bdsmBatch);
					}
					if (theData[0].equalsIgnoreCase("1")) {
						doImportDetails(theData, bdsmBatch);
					}
					if (theData[0].equalsIgnoreCase("9")) {
						doImportFooter(theData, bdsmBatch);
					}
				}
			}
			getLogger().info("End loop data and start commit");
			this.tx.commit();
			getLogger().info("Begin validate data");
			this.tx = this.session.beginTransaction();
			workerDao = new NtfsFcyRateReaderWorkerDao(session);
			workerDao.doProcessRate(bdsmBatch);

			getLogger().info("Begin register fixqxtract for respons");
			//[TODO] : register fixqxtraxt to execute respons worker (NtfsFcyRateResponsWorker)
			FixClassConfigDao cfgDao = new FixClassConfigDao(session);
			Integer idsch = cfgDao.get(getClass().getName(), sourceProcess, StatusDefinition.UNAUTHORIZED).get(0).getIdScheduler();
			fixQXtract = new FixQXtract();
			fixQXtract.setIdScheduler(idsch);
			fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
			fixQXtract.setDtmRequest(SchedulerUtil.getTime());
			fixQXtract.setParam5(filename);
			fixQXtract.setParam6(bdsmBatch);
		}
		return true;
	}

	private void doImportHeader(String[] theData, String bdsmBatch) {
		TmpNtfsFcyRateHdrDao daoHdr = new TmpNtfsFcyRateHdrDao(session);
		TmpNtfsFcyRateHdr model = new TmpNtfsFcyRateHdr();
		model.setBdsmBatch(bdsmBatch);
		model.setIdentifier(theData[0]);
		model.setBatchId(theData[1]);
		daoHdr.insert(model);
	}

	private void doImportDetails(String[] theData, String bdsmBatch) {
		TmpNtfsFcyRateDtlDao dtlDao = new TmpNtfsFcyRateDtlDao(session);
		TmpNtfsFcyRateDtl model = new TmpNtfsFcyRateDtl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		model.setBdsmBatch(bdsmBatch);
		model.setIdentifier(theData[0]);
		model.setRefTxnNo(theData[1]);
		try {
			model.setCodOrgBrn(Integer.parseInt(theData[2].trim()));
			model.setRefSysTrAudNo(Integer.parseInt(theData[3].trim()));
			model.setRefSubseqNo(Integer.parseInt(theData[4].trim()));
			model.setDatValue(sdf.parse(theData[5].trim()));
			model.setDatPost(sdf.parse(theData[6].trim()));
			model.setRateConvTxn(Double.parseDouble(theData[8].trim()));
			model.setRatConvBds(Double.parseDouble(theData[9].trim()));
			model.setCodTxnMnemonic(Integer.parseInt(theData[11].trim()));
			model.setBuyAmt(Double.parseDouble(theData[13].trim()));
			model.setCodLob(Integer.parseInt(theData[16].trim()));
		} catch (Exception ex) {
			getLogger().error("Invalid Date/Number Format");
			getLogger().error(ex, ex);
			model.setRespCode("01");
		}
		model.setInfoType(theData[7]);
		model.setCodTask(theData[10]);
		model.setBuyCcyShortNam(theData[12]);
		model.setSoldCcyShortNam(theData[14]);
		model.setCodPurOrSell(theData[15]);
		model.setNamCustShrt(theData[17]);
		dtlDao.insert(model);
	}

	private void doImportFooter(String[] theData, String bdsmBatch) {
		TmpNtfsFcyRateFtrDao daoFtr = new TmpNtfsFcyRateFtrDao(session);
		TmpNtfsFcyRateFtr model = new TmpNtfsFcyRateFtr();
		model.setBdsmBatch(bdsmBatch);
		model.setIdentifier(theData[0]);
		model.setCounter(Integer.parseInt(theData[1].trim()));
		daoFtr.insert(model);
	}
}
