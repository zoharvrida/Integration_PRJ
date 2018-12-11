package bdsm.service;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.model.SknNgInOutDebitDetail;
import bdsm.model.SknNgInOutDebitFooter;
import bdsm.model.SknNgInOutDebitHeader;
import bdsm.model.SknNgPK;
import bdsm.scheduler.dao.SknNgInwardDebitWorkerDAO;
import bdsm.scheduler.dao.TmpIncDrNoteDetailDAO;
import bdsm.scheduler.dao.TmpIncDrNoteFooterDAO;
import bdsm.scheduler.model.TmpIncDrNoteDetail;
import bdsm.scheduler.model.TmpIncDrNoteFooter;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;


/**
 * @author v00019372
 */
public class SknNgInwardDebitService extends SknNgDebitService {
	public static final String TYPE_IN = "I";

	
	public SknNgInOutDebitHeader parseToInOutHeader(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = {0};
		SknNgInOutDebitHeader header = new SknNgInOutDebitHeader();
		
		header.setCompositeId(new SknNgPK(batchNo, recordId));
		header.setType(TYPE_IN);
		header.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		header.setKodeDke(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		header.setBatchId(parseWithG2Format(line, "BATCH_ID", ctrColumn));
		header.setJamTanggalMessage(parseWithG2Format(line, "JAM_TANGGAL_MESSAGE", ctrColumn));
		header.setJumlahRecords(parseWithG2Format(line, "JUMLAH_RECORDS", ctrColumn));
		header.setTotalNominal(parseWithG2Format(line, "TOTAL_NOMINAL", ctrColumn));
		header.setTanggalBatch(parseWithG2Format(line, "TANGGAL_BATCH", ctrColumn));
		header.setJenisSetelmen(parseWithG2Format(line, "JENIS_SETELMEN", ctrColumn));
		header.setIdentitasPesertaPengirim(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM", ctrColumn));
		header.setSandiKotaPengirim(parseWithG2Format(line, "SANDI_KOTA_PENGIRIM", ctrColumn));
		
		return header;
	}
	
	public SknNgInOutDebitDetail parseToInOutDetail(String line, String batchNo, Integer recordId, SknNgInOutDebitHeader header) {
		int ctrColumn[] = {0};
		SknNgInOutDebitDetail detail = new SknNgInOutDebitDetail();
		
		detail.setCompositeId(new SknNgPK(batchNo, recordId));
		detail.setType(TYPE_IN);
		detail.setParentRecordId(header.getCompositeId().getRecordId());
		detail.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		detail.setKodeDke(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		detail.setIdentitasPesertaPenerima(parseWithG2Format(line, "IDENTITAS_PESERTA_PENERIMA", ctrColumn));
		detail.setSandiKotaPenerbit(parseWithG2Format(line, "SANDI_KOTA_PENERBIT", ctrColumn));
		detail.setNamaNasabahPemegang(parseWithG2Format(line, "NAMA_NASABAH_PEMEGANG", ctrColumn));
		detail.setDestCreditAccount(parseWithG2Format(line, "DEST_CREDIT_ACCOUNT", ctrColumn));
		detail.setNomorIdentitasPemegang(parseWithG2Format(line, "NOMOR_IDENTITAS_PEMEGANG", ctrColumn));
		detail.setDebitAccount(parseWithG2Format(line, "DEBIT_ACCOUNT", ctrColumn).replaceAll("[^0-9 ]", "0"));
		detail.setJenisTransaksi(parseWithG2Format(line, "JENIS_TRANSAKSI", ctrColumn));
		detail.setNomorWarkat(parseWithG2Format(line, "NOMOR_WARKAT", ctrColumn));
		detail.setNominal(parseWithG2Format(line, "NOMINAL", ctrColumn));
		detail.setNomorUrut(parseWithG2Format(line, "NOMOR_URUT", ctrColumn));
		detail.setNomorReferensi(parseWithG2Format(line, "NOMOR_REFERENSI", ctrColumn));
		detail.setBebanBiaya(parseWithG2Format(line, "BEBAN_BIAYA", ctrColumn));
		detail.setSor(parseWithG2Format(line, "SOR", ctrColumn));
		
		return detail;
	}
	
	public SknNgInOutDebitFooter parseToInOutFooter(String line, String batchNo, Integer recordId, SknNgInOutDebitHeader header) {
		int ctrColumn[] = {0};
		SknNgInOutDebitFooter footer = new SknNgInOutDebitFooter();
		
		footer.setCompositeId(new SknNgPK(batchNo, recordId));
		footer.setType(TYPE_IN);
		footer.setParentRecordId(header.getCompositeId().getRecordId());
		footer.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		footer.setKodeDke(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		footer.setCrc(parseWithG2Format(line, "CRC", ctrColumn).trim());
		
		return footer;
	}
	
	public void createProcessG2ToG1AndRun(Integer HPlus, String batchNo, String branchNo, String idUser) {
		G2ProcessToG1 process = new G2ProcessToG1(
				HPlus,
				batchNo,
				(branchNo == null)? String.valueOf(Integer.parseInt(batchNo.split("-")[0])): branchNo,
				(idUser == null)? "SYSTEM": idUser
		);
		
		process.start();
	}
	
	
	
	
	
	/* Inner Class */
	protected class G2ProcessToG1 extends Thread {
		private Logger logger = Logger.getLogger(G2ProcessToG1.class);
		private Integer HPlus;
		private String batchNo;
		private String branchNo;
		private String idUser;
		
		
		G2ProcessToG1(Integer HPlus, String batchNo, String branchNo, String idUser) {
			this.HPlus = HPlus;
			this.batchNo = batchNo;
			this.branchNo = branchNo;
			this.idUser = idUser;
		}
		
		
		@Override
		public void run() {
			if (StringUtils.isNotBlank(branchNo)) {
				Session session = HibernateUtil.getSession();
				Transaction trx = session.beginTransaction();
				SknNgInwardDebitWorkerDAO inwardDAO = new SknNgInwardDebitWorkerDAO(session);
				TmpIncDrNoteDetailDAO incDetailDAO = new TmpIncDrNoteDetailDAO(session);
				TmpIncDrNoteFooterDAO incFooterDAO = new TmpIncDrNoteFooterDAO(session);
				String fileId;
				StringBuilder sbCrcCheck = new StringBuilder();
				
				try {
					fileId = SchedulerUtil.generateUUID2();
					this.logger.info("[START] Preprocess Incoming, translate to old format for " + "batch file no.: " + this.batchNo 
							+ " (H+" + this.HPlus + ")");
					inwardDAO.preProcessIncoming(fileId, this.batchNo, this.HPlus);
					this.logger.info("[END] Preprocess Incoming, translate to old format for " + "batch file no.: " + this.batchNo 
							+ " (H+" + this.HPlus + ")");
					
					
					loadInDebitG1Format();
					
					/* [BEGIN] Generate Fix Length Data */
					List<TmpIncDrNoteDetail> details = incDetailDAO.getDetailsByFileId(fileId);
					if (details.size() > 0) {
						for (TmpIncDrNoteDetail detail: details) {
							String data = null;
							try {
								data = this.generateG1DetailData(detail);
								
								detail.setData(data);
								detail.setLength((short) detail.getData().length());
								sbCrcCheck.append(detail.getData());
							}
							catch (Exception ex) {
								detail.setComments(ex.getMessage());
							}
						}
						
						TmpIncDrNoteFooter footer = incFooterDAO.getFooterByFileId(fileId);
						footer.setChecksum(bdsm.util.CRC16.CRC16(sbCrcCheck.toString(), 0));
						footer.setData(this.generateG1FooterData(footer));
						footer.setLength((short) footer.getData().length());
						
						trx.commit();
						
						logger.info("[START] Processing Approval or Reject Message");
						inwardDAO.processIncoming(fileId, this.batchNo, this.branchNo, this.idUser, SchedulerUtil.getDate("ddMMyyyyHHmmss"));
						logger.info("[END] Processing Approval or Reject Message");
					}
					else {
						logger.info("No today batch for batch file no. : " + this.batchNo + " (H+" + HPlus + ")");
					}
					/* [END] */
				}
				catch (Exception ex) {
					logger.error(ex, ex);
					trx.rollback();
				}
				finally {
					HibernateUtil.closeSession(session);
				}
			}
			else {
				logger.info("Branch no. is blank");
			}
		}
		
		private String generateG1DetailData(TmpIncDrNoteDetail model) {
			StringBuilder data = new StringBuilder();
			
			formatWithG1Format(data, model.getClearingDate(), "CLEARING_DATE");
			formatWithG1Format(data, model.getChequeNo(), "CHEQUE_NO");
			formatWithG1Format(data, model.getPayersClearingCode(), "PYRS_CLEARING_CODE");
			formatWithG1Format(data, model.getPayersAccountNo(), "PYRS_ACCT_NO");
			formatWithG1Format(data, model.getTransactionCode(), "TXN_CODE");
			formatWithG1Format(data, model.getAmount(), "AMOUNT");
			formatWithG1Format(data, model.getPayeeClearingCode(), "PAYEE_CLEARING_CODE");
			formatWithG1Format(data, model.getSOR(), "SOR");
			
			return data.toString();
		}
		
		private String generateG1FooterData(TmpIncDrNoteFooter model) {
			StringBuilder data = new StringBuilder();
			formatWithG1Format(data, model.getChecksum(), "CHECKSUM");
			
			return data.toString();
		}
	}
	
}