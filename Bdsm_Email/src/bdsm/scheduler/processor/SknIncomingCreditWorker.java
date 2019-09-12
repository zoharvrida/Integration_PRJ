/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.FcrBaBankMast;
import bdsm.model.Pm_fin_inst_dir_mast;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.*;
import bdsm.util.BdsmUtil;
import bdsm.util.CRC16;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Jeffri Tambunan
 */
public class SknIncomingCreditWorker extends BaseProcessor {

	private static final String IN = "I";
	private static String FORMAT_G2_FILENAME_HEADER = "skn_in_out_credit.properties";
	private static String FORMAT_G2_FILENAME_DETAIL = "skn_gen2_detail_credit.properties";
    private static String YES = "Y";
    private static String DES = "3DES/CBC";
	private Map textSetting;

	public SknIncomingCreditWorker(Map context) {
		super(context);
	}

	@Override
	protected boolean doExecute() throws Exception {

		if (StatusDefinition.AUTHORIZED.equals(context.get(MapKey.status))) {
			return true;
		}
		getLogger().info("Begin Read Incoming DKI");
		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
		this.tx = session.beginTransaction();
		String userBatchno = "";
		String FixlogName = "";
		//getLogger().info("Cek for Approval or Reject Message");
		String approval = "";
		String Flagging = null;
		
		String batchNumber = null;
		Integer Countline = 0;
		Integer CountFalse = 0;
		Integer CountTrue = 0;
		Boolean FlagProcess = true;

		Boolean CRCFlag = true;
		Boolean AmounFlag = true;

		StringBuilder parentID = new StringBuilder();
        BigDecimal bHeaderTotal = new BigDecimal(0.0);
        BigDecimal bTotalRec = new BigDecimal(0.0);

		StringBuilder sbCrcCheck = new StringBuilder();
		StringBuilder flName = new StringBuilder();
        
        getLogger().debug("BATCHID :" + context.get(MapKey.batchNo).toString());
        getLogger().debug("FILENAME :" + context.get(MapKey.param5).toString());
        //getLogger().debug("DECRYPT :" + context.get(MapKey.keyDecrypt).toString());
        // get Param from fixlog
        FixLogDao logDao = new FixLogDao(session);
        FixLog fixLogs = logDao.get(context.get(MapKey.batchNo).toString());
        
        String param5 = context.get(MapKey.param5).toString();
		getLogger().info("CHECK_PARAM5 :" + param5);
        String[] param5Arr = fixLogs.getFixLogPK().getFileName().split("_");
		int paramLength = param5Arr.length;
		
		StringBuilder fcrname = new StringBuilder();
		
		for(int incr=3 ;incr < paramLength;incr++){
				fcrname.append(param5Arr[incr]);
			if(incr != paramLength-1){
				fcrname.append("_");
			}
		}
		FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
		String FCRDay = sdf.format(fcrDao.get().getDatProcess());
		userBatchno = param5Arr[1];
		//userBatchno = batch.append(Branch).append(FCRDay).append(Clock).toString();
		flName.append(param5Arr[0]).append("_")
			.append(userBatchno).append("_")
				.append(param5Arr[2]).append("_")
				.append(fcrname);
		FixlogName = flName.toString();
		getLogger().info("FIRST BATCH :" + userBatchno);
		
		TmpSknngInOutCreditHeaderDAO headerDao = new TmpSknngInOutCreditHeaderDAO(session);
		TmpSknngInOutCreditDetailDAO detailDao = new TmpSknngInOutCreditDetailDAO(session);
		TmpSknngInOutCreditFooterDAO footerDao = new TmpSknngInOutCreditFooterDAO(session);
		
		FcrBaBankMast fcrDates;
		TmpSknngInOutCreditHeader modelHeader = null;
		TmpSknngInOutCreditDetail modelDetail;
		TmpSknngInOutCreditFooter modelFooter;
		TmpSknngInoutHFPK modelPk = new TmpSknngInoutHFPK();
		TmpSknngInOutCreditPK detailPk = new TmpSknngInOutCreditPK();


		File file = null;
		int crcValue = 0;
		int ctrColumn = 0;
		int ctrRow = 0;
		int ctrPArentNo = 0;
		int ctrRecParent = 0;
		Integer parentRecordId = 1;
		StringBuilder HeaderComment = new StringBuilder();

		if (StringUtils.isEmpty(param5) == false) {
			file = new File(param5);
		}
		String line = "";

		BufferedReader rd = new BufferedReader(new FileReader(param5));

		//List<String> lines = getFileLines(new FileInputStream(param5));
		try {
			StringBuilder CRCNoCheck = new StringBuilder();
			StringBuilder AmountCheck = new StringBuilder();

			while ((line = rd.readLine()) != null) {
				if (line.trim().length() == 0) {
					continue;
				}
				//}
				//for (String line : lines) {

				if (line.charAt(0) == '0') {
                    bHeaderTotal = new BigDecimal(0.0);
                    bTotalRec = new BigDecimal(0.0);

					modelHeader = new TmpSknngInOutCreditHeader();
					loadTextSetting();
					CountFalse = 0;
					//getLogger().info("[START] Header Data Processing for File : " + param5);

					// 01000012014-03-21T08:01:45.345+07:00005000000227602850,002014-03-21CLRGBRINIDJA0300
					ctrColumn = 0;

					List Validate = new ArrayList();
                    //getLogger().debug("LINE LENGTH :" + line.length());
					//getLogger().info("HEADER LENGTH :" + textSetting.get("TotalLine").toString());
                    Integer lengthnumber = 0;
                    // check if file Encrypted
                    if (context.get(MapKey.flgEncrypt).toString().equals(YES) && context.get(MapKey.modDecrypt).toString().equalsIgnoreCase(DES)) {
                        //lengthnumber = Integer.parseInt(textSetting.get("TotalLineENC").toString());
                        line = line.substring(0,Integer.parseInt(textSetting.get("TotalLine").toString()));
                    }
					lengthnumber = Integer.parseInt(textSetting.get("TotalLine").toString());
					//getLogger().info("HEADER LENGTH2 :" + lengthnumber);
                    if (lengthnumber.compareTo(line.length()) == 0) {
						// 01000012014-03-21T08:01:45.345+07:00005000000227602850,002014-03-21CLRGBRINIDJA0300
						//getLogger().info("LINE LENGTH HEADER MATCH");
						SettingContainer();
						FlagProcess = true;
						//getLogger().debug("HEADER SPLIT SIZE :" + SettingContainer().size());
						try {
							for (int i = 0; i < SettingContainer().size(); i++) {
								//getLogger().info("HEADER INSERT :" + line.substring(ctrColumn, ctrColumn + Integer.parseInt(SettingContainer().get(i).toString())));
								Validate.add(line.substring(ctrColumn, ctrColumn + Integer.parseInt(SettingContainer().get(i).toString())));
								//getLogger().info("CTR :" + Integer.parseInt(SettingContainer().get(i).toString()));
								ctrColumn = ctrColumn + Integer.parseInt(SettingContainer().get(i).toString());
							}
						} catch (Exception exception) {
							getLogger().info("EXCEPTION :" + exception);
						}
						modelHeader.setTipeRecord(Validate.get(0).toString());
						modelHeader.setKodeDKE(Validate.get(1).toString());
						modelHeader.setBatchId(Validate.get(2).toString());
						try {
							modelHeader.setJamTanggalMessage(Validate.get(3).toString());
						} catch (Exception e) {
							//getLogger().info("JAM Tanggal Message :" + e);
							modelHeader.setJamTanggalMessage(nullTo(Validate.get(3).toString(), "jamTanggalMessage"));
						}
						modelHeader.setJumlahRecords(Validate.get(4).toString());
						modelHeader.setTotalNominal(Validate.get(5).toString().replace(",", "."));
						//headerTotal = Double.parseDouble(modelHeader.getTotalNominal().trim());
                        bHeaderTotal = bHeaderTotal.add(new BigDecimal(modelHeader.getTotalNominal().trim()));
						//getLogger().debug("HEADER TOTAL :" + modelHeader.getTotalNominal());
						modelHeader.setTanggalBatch(Validate.get(6).toString());
						modelHeader.setJenisSetelmen(Validate.get(7).toString());
						modelHeader.setIdentitasPesertaPengirim(Validate.get(8).toString());
						modelHeader.setSandiKotaPengirim(Validate.get(9).toString());
						modelHeader.setFlgReject("F");
						HeaderComment.append("VALID HEADER");
						modelPk = new TmpSknngInoutHFPK();
						modelPk.setParentRecordId(parentRecordId);
						modelPk.setBatchNo(userBatchno);
						modelHeader.setCompositeId(modelPk);
						modelHeader.setType("I");
						modelHeader.setIdMaintainedBy(param5Arr[2]);
						headerDao.insert(modelHeader);
					} else {
						// Length not Match Skip detail
						FlagProcess = false;
						getLogger().info("LENGTH HEADER MISSMATCH");
						modelHeader.setBatchId(BdsmUtil.leftPad(parentRecordId.toString(), 5, '0'));
						modelHeader.setFlgReject("T");
						HeaderComment.append("Header Length Missmatch");
					}
					//String sandiKotaPengirim = line.substring(79, 83);
					//modelHeader.setSandiKotaPengirim(sandiKotaPengirim);
					//getLogger().info("TEST BEFORE HEADER");


					//getLogger().info("[END] Header Data Processing for File : " + param5);
					textSetting.clear();

				} else if (line.startsWith("1")) {
					ctrRow++;
					ctrPArentNo++;
					ctrRecParent++;

					loadTextSettingDetails();
                    //getLogger().info("LENGTH LINE :" + line.length());
                    Integer TotalLength = 0;
                    
                    if (context.get(MapKey.flgEncrypt).toString().equals(YES) && context.get(MapKey.modDecrypt).toString().equalsIgnoreCase(DES)) {
                        line = line.substring(0,Integer.parseInt(getSplit(textSetting.get("TotalLine").toString(), 0).toString()));
                    }
					TotalLength = Integer.parseInt(getSplit(textSetting.get("TotalLine").toString(), 0).toString());
					modelDetail = new TmpSknngInOutCreditDetail();
					StringBuilder Detailvalidation = new StringBuilder();
					Integer TempCount = CountFalse;
					String tmpNominal = null;
					String nominal = null;
					//textSetting.clear();
					List ValidateDetails = new ArrayList();
					if (TotalLength.compareTo(line.length()) == 0 && FlagProcess) {
						//loadTextSettingDetails();
						//getLogger().info("LINE LENGTH DETAIL MATCH");

						SettingContainerDetails();
						FlagProcess = true;
						//getLogger().info("DETAILS SPLIT SIZE :" + SettingContainerDetails().size());

						modelDetail.setFlgAuth("N");
						for (int i = 0; i < SettingContainerDetails().size(); i++) {
							//getLogger().debug("DETAILS INSERT :" + line.substring(getSplit(SettingContainerDetails().get(i).toString(), 1), getSplit(SettingContainerDetails().get(i).toString(), 2)));
							ValidateDetails.add(line.substring(getSplit(SettingContainerDetails().get(i).toString(), 1), getSplit(SettingContainerDetails().get(i).toString(), 2)).trim());
						}
						try {
							tmpNominal = line.substring(getSplit(textSetting.get("nominal").toString(), 1), getSplit(textSetting.get("nominal").toString(), 2));
							nominal = tmpNominal.replace(",", ".").trim();
						} catch (Exception exception) {
							//getLogger().info("EXCEPTION :" + exception);
						}

						//getLogger().info(nominal);
						//totalRec += Double.parseDouble(nominal);
                        bTotalRec = bTotalRec.add(new BigDecimal(nominal));

						String sandiKotaAsal = null;
						try {
							sandiKotaAsal = ValidateDetails.get(3).toString();//3
						} catch (Exception e) {
                            //getLogger().info("EXCEPTION : SandikotaAsal tier1 " + e, e);
                            sandiKotaAsal = BdsmUtil.leftPad("",5,'0');
						}
						String sandiKotaTujuan = null;
						try {
							sandiKotaTujuan = ValidateDetails.get(12).toString();//12
						} catch (Exception e) {
                            //getLogger().info("EXCEPTION : sandiKotaTujuan tier1 " + e, e);
                            sandiKotaTujuan = BdsmUtil.leftPad("",5,'0');
						}
						String identitasPesertaPenerimaAkhir = null;
						try {
							identitasPesertaPenerimaAkhir = ValidateDetails.get(11).toString();//11
						} catch (Exception e) {
                            //getLogger().info("EXCEPTION : identitasPesertaPenerimaAkhir tier1 " + e, e);
                            identitasPesertaPenerimaAkhir = BdsmUtil.leftPad("",5,'0');
						}
						String identitasPesertaPengirimAsal = null;
						try {
							identitasPesertaPengirimAsal = ValidateDetails.get(2).toString();//3
						} catch (Exception e) {
                            //getLogger().info("EXCEPTION : identitasPesertaPengirimAsal tier1 " + e, e);
                            identitasPesertaPengirimAsal = BdsmUtil.leftPad("",5,'0');
						}
						String tipeRecords = ValidateDetails.get(0).toString();//0
						String kodeDke1 = ValidateDetails.get(1).toString();//1
						String senderName = ValidateDetails.get(4).toString();//4
						String sourceAccount = ValidateDetails.get(5).toString();//5
						String alamatNasabahPengirim = ValidateDetails.get(6).toString();//6
						String jenisNasabahPengirim = ValidateDetails.get(7).toString();//7
						String statusPendudukNasabahPengirim = ValidateDetails.get(8).toString();//8
						String identitasNasabahPengirim = ValidateDetails.get(9).toString();//9
						String identitasPesertaPenerimaPenerus = ValidateDetails.get(10).toString();//10
						String namaNasabahPenerima = ValidateDetails.get(13).toString();//13
						String destinationAccount = ValidateDetails.get(14).toString();//14
						String alamatNasabahPenerima = ValidateDetails.get(15).toString();//15
						String jenisNasabahPenerima = ValidateDetails.get(16).toString();//16
						String statusPendudukNasabahPenerima = ValidateDetails.get(17).toString();//17
						String identitasNasabahPenerima = ValidateDetails.get(18).toString();//18
						String jenisTransaksi = ValidateDetails.get(19).toString();//19
						String jenisSaranaTransaksi = ValidateDetails.get(20).toString();//20
						String nomorUrut = ValidateDetails.get(21).toString();//21
						String nomorReferensi = ValidateDetails.get(22).toString();//22
						String nomorReferensiTrasaksiAsal = ValidateDetails.get(23).toString();//23
						String bebanBiaya = ValidateDetails.get(24).toString(); //24
						String message = ValidateDetails.get(25).toString(); //25
						String sor = ValidateDetails.get(26).toString(); //26

						// validate each field representative to its tabl
                        destinationAccount = destinationAccount.replaceAll("[^0-9 ]", "");
                        destinationAccount = destinationAccount.equals("")?"0":destinationAccount;
                        sourceAccount = sourceAccount.replaceAll("[^0-9 ]", "");
                        sourceAccount = sourceAccount.equals("")?"0":sourceAccount;
						
						//getLogger().info("DEST ACCOUNT :" + destinationAccount);
						//getLogger().info("SOURCE ACCOUNT :" + sourceAccount);
						SKNIncomingCreditDetailsDao G1dao = new SKNIncomingCreditDetailsDao(session);
						Pm_fin_inst_dir_mast modelPm = new Pm_fin_inst_dir_mast();

						//getLogger().debug("Identitas Penerima Asal HEADER" + modelHeader.getIdentitasPesertaPengirim());
						//getLogger().debug("Sandi Kota Asal" + sandiKotaAsal);
						//getLogger().debug("Sandi Kota Tujuan " + sandiKotaTujuan);
						//getLogger().debug("Identitas Penerima Tujuan" + identitasPesertaPenerimaAkhir);
						//getLogger().debug("Identitas Penerima Asal DETAIL" + identitasPesertaPengirimAsal);
                        /*
                         * }
                         */
						try {
							modelDetail.setTipeRecord(tipeRecords);
							modelDetail.setKodeDKE(kodeDke1);
							modelDetail.setIdentitasPesertaPengirimAsal(identitasPesertaPengirimAsal);
							modelDetail.setSandiKotaAsal(sandiKotaAsal);
							modelDetail.setSenderName(senderName);
							modelDetail.setSourceAccount(sourceAccount);
							modelDetail.setAlamatNasabahPengirim(alamatNasabahPengirim);
							modelDetail.setJenisNasabahPengirim(jenisNasabahPengirim);
							modelDetail.setStatusPendudukNasabahPengirim(statusPendudukNasabahPengirim);
							modelDetail.setIdentitasNasabahPengirim(identitasNasabahPengirim);
							modelDetail.setIdentitasPesertaPenerimaPenerus(identitasPesertaPenerimaPenerus);
							modelDetail.setIdentitasPesertaPenerimaAkhir(identitasPesertaPenerimaAkhir);
							modelDetail.setSandiKotaTujuan(sandiKotaTujuan);
							modelDetail.setNamaNasabahPenerima(namaNasabahPenerima);
							modelDetail.setDestinationAccount(destinationAccount);
							modelDetail.setAlamatNasabahPenerima(alamatNasabahPenerima);
							modelDetail.setJenisNasabahPenerima(jenisNasabahPenerima);
							modelDetail.setStatusPendudukNasabahPenerima(statusPendudukNasabahPenerima);
							modelDetail.setIdentitasNasabahPenerima(identitasNasabahPenerima);
							modelDetail.setJenisTransaksi(jenisTransaksi);
							modelDetail.setJenisSaranaTransaksi(jenisSaranaTransaksi);
							modelDetail.setNominal(nominal);
							modelDetail.setNomorUrut(nomorUrut);
							modelDetail.setNomorReferensi(nomorReferensi);
							modelDetail.setNomorReferensiTrasaksiAsal(nomorReferensiTrasaksiAsal);
							modelDetail.setBebanBiaya(bebanBiaya);
							modelDetail.setKeterangan(message);
							modelDetail.setSor(sor);
							
							if (sor.trim().length() > 20) {
								// Generate new SOR
								StringBuffer sb = new StringBuffer()
									.append(modelHeader.getTanggalBatch().replace("-", ""))
									.append(BdsmUtil.leftPad(modelHeader.getBatchId().trim(), 5, '0'))
									.append(BdsmUtil.leftPad(jenisTransaksi.trim(), 4, '0'))
									.append(BdsmUtil.leftPad(nomorUrut.trim(), 3, '0'))
								;
							
								modelDetail.setSORToFCR(sb.toString());
							}
							
							if (Countline != 0) {
								Detailvalidation.append(" LINE LENGTH INVALID");
								modelDetail.setMessage(Detailvalidation.toString());
								if (!"R".equals(modelDetail.getFlgAuth())) {
									modelDetail.setFlgAuth("R");
								}
							}
						} catch (Exception e) {
							this.throwFIXException("LINE LENGTH ERROR", ctrRow);
						}
						detailPk = new TmpSknngInOutCreditPK();
						detailPk.setRecordId(ctrRow);
						detailPk.setParentRecordId(parentRecordId);
						detailPk.setBatchNo(userBatchno);
						detailPk.setRecordbyParent(ctrRecParent);
						modelDetail.setMessage(Detailvalidation.toString());
						modelDetail.setCompositeId(detailPk);
						modelDetail.setType("I");
						modelDetail.setIdMaintainedBy(param5Arr[2]);
						detailDao.insert(modelDetail);

						
						if(modelDetail.getFlgAuth().equals("R")){
							CountFalse++;
						} else {
							modelDetail.setFlgAuth("A");
						}
						if (CountFalse.equals(TempCount)) {
							CountTrue++;
						}
					} else {
						getLogger().info(" Details Length Missmatch");
						Detailvalidation.append(" Detail Length Missmatch");
						modelDetail.setFlgAuth("R");
					}
					//Countline = line.length() - TotalLength;
					sbCrcCheck.append(line);
					FlagProcess = true;
					//getLogger().info("Parsing Done");
					Detailvalidation.delete(0, Detailvalidation.length());

					//getLogger().info("[END] Detail Data Processing for File : " + param5);
					textSetting.clear();

				} else if (line.startsWith("3")) {
					//getLogger().info("[START] Footer Data Processing for File : " + param5);
					fcrDates = fcrDao.get();
					ctrRecParent = 0;
					textSetting.clear();
					loadTextSetting();
					crcValue = 0;
					if (!"".equals(sbCrcCheck.toString())) {
						crcValue = CRC16.CRC16(sbCrcCheck.toString(), crcValue);
						//getLogger().info("CRC_CHECK : " + crcValue);
					}

					modelFooter = new TmpSknngInOutCreditFooter();
					ctrColumn = 0;
					modelFooter.setTipeRecord(line.substring(ctrColumn, Integer.parseInt(textSetting.get("tipe_Record").toString())));
					ctrColumn += Integer.parseInt(textSetting.get("tipe_Record").toString());

					modelFooter.setKodeDke(line.substring(ctrColumn, ctrColumn
							+ Integer.parseInt(textSetting.get("kode_Dke").toString())));
					ctrColumn += Integer.parseInt(textSetting.get("kode_Dke").toString());

					modelFooter.setCrc(line.substring(ctrColumn, ctrColumn
							+ Integer.parseInt(textSetting.get("CRC").toString())));
					Integer Lastco = ctrColumn + Integer.parseInt(textSetting.get("CRC").toString());
					//getLogger().info("CRCNYA : " + line.substring(ctrColumn, ctrColumn + Integer.parseInt(textSetting.get("CRC").toString())));


					StringBuilder reason = new StringBuilder();

					Date TanggalValidasi = sdf.parse(modelHeader.getTanggalBatch());
					Date FCRDates = fcrDates.getDatProcess();
					String DateProcess = fcrDates.getDatProcess().toString();
					String TanggalProcess = modelHeader.getTanggalBatch();
                    //getLogger().debug("TANGGAL VALIDASI ModelHeader : " + modelHeader.getTanggalBatch());
                    //getLogger().debug("TANGGAL VALIDASI DateProcess : " + DateProcess);
                    //getLogger().debug("TANGGAL VALIDASI : " + TanggalValidasi);
                    //getLogger().debug("TANGGAL FCR : " + FCRDates);

					if ("VALID HEADER".equals(HeaderComment.toString())) {
						// Date Compare
						if (!DateProcess.equalsIgnoreCase(TanggalProcess)) {
							modelFooter.setTreject(ctrPArentNo);
							modelHeader.setFlgReject("T");
							reason.append(" Date must be Equal with process Date");
						} else {
							if (StringUtils.isBlank(modelHeader.getFlgReject())) {
								modelHeader.setFlgReject("F");
							}
						}
						Integer HeaderCount = Integer.parseInt(modelHeader.getJumlahRecords().trim());
						// Num Of Record Header and Detail
						if (HeaderCount != ctrPArentNo) {
							modelFooter.setTreject(ctrPArentNo);
							modelHeader.setFlgReject("T");
							reason.append(" Total Records in Header Must Equals With Number of Details");
						} else {
							if (StringUtils.isBlank(modelHeader.getFlgReject())) {
								modelHeader.setFlgReject("F");
							}
						}
						//getLogger().info("header TOTAL :" + headerTotal);
						
						// Amount Header and Detail
                        if(bHeaderTotal.compareTo(bTotalRec) != 0) {
                        //if (headerTotal.compareTo(totalRec) != 0) {
							reason.append(" Total Amount detail and Header Missmatch");
							modelFooter.setTreject(ctrPArentNo);
							modelHeader.setFlgReject("T");
						} else {
							if (StringUtils.isBlank(modelHeader.getFlgReject())) {
								modelHeader.setFlgReject("F");
							}
						}

					} else if ("Header Length Missmatch".equalsIgnoreCase(HeaderComment.toString())) {
						modelHeader.setFlgReject("T");
						modelFooter.setTreject(ctrPArentNo);
						reason.append(" Header Length Missmatch");
					}
					if (crcValue != Integer.parseInt(modelFooter.getCrc().trim())) {
						modelFooter.setTreject(ctrPArentNo);
						modelHeader.setFlgReject("T");
						reason.append("Invalid CRC");
					} else {
						if (StringUtils.isBlank(modelHeader.getFlgReject())) {
							modelHeader.setFlgReject("F");
						}
					}

					if ("F".equals(modelHeader.getFlgReject())) {
						modelFooter.setTsuccess(ctrPArentNo);
					}
					//totalRec = 0.0;
					modelHeader.setExtractStatus("N");
					modelHeader.setMessage(reason.toString());
					modelFooter.setMessage(reason.toString());
					//headerDao.update(modelHeader);
					modelPk = new TmpSknngInoutHFPK();
					modelPk.setBatchNo(userBatchno);
					modelPk.setParentRecordId(parentRecordId);
					modelFooter.setCompositeId(modelPk);
					modelFooter.setType("I");
					modelFooter.setIdMaintainedBy(param5Arr[2]);
					modelFooter.setTsuccess(CountTrue);
					if (modelFooter.getTreject() != null) {
						modelFooter.setTreject(CountFalse);
						modelFooter.setTsuccess(CountTrue);
					}

					footerDao.insert(modelFooter);
					//getLogger().info("[END] Footer Data Processing for File : " + param5);
					parentID.append(parentRecordId).append(",");
					parentRecordId++;
					ctrPArentNo = 0;

					CountFalse = 0;
					CountTrue = 0;
					sbCrcCheck.delete(0, sbCrcCheck.length());
					HeaderComment.delete(0, HeaderComment.length());
					//getLogger().info("CHECK CLEAR : " + sbCrcCheck);
				}
			}

			String IDParent = parentID.substring(0, parentID.length() - 1);
			batchNumber = context.get(MapKey.batchNo).toString();
			getLogger().info("BATCH LAST :" + batchNumber);
			context.put(MapKey.param1, IDParent);
			context.put(MapKey.batchNo, userBatchno);

			//getLogger().info("ID_PARENT :" + context.get(MapKey.param1));
			//getLogger().info("BATCH NO :" + context.get(MapKey.batchNo));

			if (modelHeader == null) {
				this.throwFIXException("BLANK FILE", ctrRow);
			}
		} catch (IndexOutOfBoundsException ie) {
			this.throwFIXException("LINE LENGTH ERROR", ctrRow);
		} catch (java.text.ParseException pe) {
			this.throwFIXException("INVALID DATE FORMAT", ctrRow);
		} finally {
			rd.close();
		}
		FixLogDao fixLogDAO = new FixLogDao(session);
		FixLogPK flogPK = new FixLogPK();
		flogPK.setFileName(FixlogName);
		FixLog fixLog = fixLogDAO.get(batchNumber);
		
		fixLog.setFcrFileName(fcrname.toString()); 
		//fixLog.setFixLogPK(flogPK);
		/*
		 * [START] Preprocess Incoming, translate to old format
		 */
		//this.tx.commit();

		getLogger().info("End Read Incoming DKI");
		return true;
	}

	private void loadTextSetting() throws Exception {
		Properties properties = new Properties();
		InputStream in = SknIncomingCreditWorker.class.getClassLoader().getResourceAsStream(FORMAT_G2_FILENAME_HEADER);
		properties.load(in);
		textSetting = new HashMap();
		textSetting.put("tipe_Record", properties.getProperty("tipe_Record"));
		textSetting.put("kode_Dke", properties.getProperty("kode_Dke"));
		textSetting.put("batch_Id", properties.getProperty("batch_Id"));
		textSetting.put("jamTanggalMessage", properties.getProperty("jamTanggalMessage"));
		textSetting.put("jumlahRecords", properties.getProperty("jumlahRecords"));
		textSetting.put("totalNominal", properties.getProperty("totalNominal"));
		textSetting.put("tanggalBatch", properties.getProperty("tanggalBatch"));
		textSetting.put("jenisSetelmen", properties.getProperty("jenisSetelmen"));
		textSetting.put("identitasPesertaPengirim", properties.getProperty("identitasPesertaPengirim"));
		//textSetting.put("identitasPesertaPengirimAsal", properties.getProperty("identitasPesertaPengirimAsal"));
		textSetting.put("sandiKotaPengirim", properties.getProperty("sandiKotaPengirim"));
		textSetting.put("TotalLine", properties.getProperty("TotalLine"));
		textSetting.put("CRC", properties.getProperty("CRC"));
		in.close();
	}

	private void loadTextSettingDetails() throws Exception {
		Properties properties = new Properties();
		InputStream in = SknIncomingCreditWorker.class.getClassLoader().getResourceAsStream(FORMAT_G2_FILENAME_DETAIL);
		properties.load(in);
		textSetting = new HashMap();

		textSetting.put("tipeRecords", properties.getProperty("tipeRecords"));
		textSetting.put("kodeDKE", properties.getProperty("kodeDKE"));
		textSetting.put("identitasPesertaPengirimAsal", properties.getProperty("identitasPesertaPengirimAsal"));
		textSetting.put("sandiKotaAsal", properties.getProperty("sandiKotaAsal"));
		textSetting.put("senderName", properties.getProperty("senderName"));
		textSetting.put("sourceAccount", properties.getProperty("sourceAccount"));
		//textSetting.put("IdentitasPesertaPenerima", properties.getProperty("IdentitasPesertaPenerima"));
		textSetting.put("alamatNasabahPengirim", properties.getProperty("alamatNasabahPengirim"));
		textSetting.put("jenisNasabahPengirim", properties.getProperty("jenisNasabahPengirim"));
		textSetting.put("statusPendudukNasabahPengirim", properties.getProperty("statusPendudukNasabahPengirim"));
		textSetting.put("identitasNasabahPengirim", properties.getProperty("identitasNasabahPengirim"));
		textSetting.put("identitasPesertaPenerimaPenerus", properties.getProperty("identitasPesertaPenerimaPenerus"));
		//textSetting.put("identitasPenerus", properties.getProperty("identitasPenerus"));
		textSetting.put("identitasPesertaPenerimaAkhir", properties.getProperty("identitasPesertaPenerimaAkhir"));
		textSetting.put("sandiKotaTujuan", properties.getProperty("sandiKotaTujuan"));
		textSetting.put("namaNasabahPenerima", properties.getProperty("namaNasabahPenerima"));
		textSetting.put("destinationAccount", properties.getProperty("destinationAccount"));
		textSetting.put("alamatNasabahPenerima", properties.getProperty("alamatNasabahPenerima"));
		textSetting.put("jenisNasabahPenerima", properties.getProperty("jenisNasabahPenerima"));
		textSetting.put("statusPendudukNasabahPenerima", properties.getProperty("statusPendudukNasabahPenerima"));
		textSetting.put("identitasNasabahPenerima", properties.getProperty("identitasNasabahPenerima"));
		textSetting.put("jenisTransaksi", properties.getProperty("jenisTransaksi"));
		textSetting.put("jenisSaranaTransaksi", properties.getProperty("jenisSaranaTransaksi"));
		textSetting.put("nominal", properties.getProperty("nominal"));
		textSetting.put("nomorUrut", properties.getProperty("nomorUrut"));
		textSetting.put("nomorReferensi", properties.getProperty("nomorReferensi"));
		textSetting.put("nomorReferensiTrasaksiAsal", properties.getProperty("nomorReferensiTrasaksiAsal"));
		textSetting.put("bebanBiaya", properties.getProperty("bebanBiaya"));
		textSetting.put("sor", properties.getProperty("sor"));
		textSetting.put("message", properties.getProperty("message"));
        textSetting.put("TotalLine", properties.getProperty("TotalLine"));
		//textSetting.put("CRC", properties.getProperty("CRC"));
		in.close();
	}

	private List SettingContainer() throws Exception {
		List Setting = new ArrayList();
		Setting.add(textSetting.get("tipe_Record").toString());
		Setting.add(textSetting.get("kode_Dke").toString());
		Setting.add(textSetting.get("batch_Id").toString());
		Setting.add(textSetting.get("jamTanggalMessage").toString());
		Setting.add(textSetting.get("jumlahRecords").toString());
		Setting.add(textSetting.get("totalNominal").toString());
		Setting.add(textSetting.get("tanggalBatch").toString());
		Setting.add(textSetting.get("jenisSetelmen").toString());
		Setting.add(textSetting.get("identitasPesertaPengirim").toString());
		Setting.add(textSetting.get("sandiKotaPengirim").toString());
		return Setting;
	}

	private List SettingContainerDetails() throws Exception {
		List Setting = new ArrayList();

		Setting.add(textSetting.get("tipeRecords").toString());//0
		Setting.add(textSetting.get("kodeDKE").toString());//1
		Setting.add(textSetting.get("identitasPesertaPengirimAsal").toString());//2
		Setting.add(textSetting.get("sandiKotaAsal").toString());//3
		Setting.add(textSetting.get("senderName").toString());//4
		Setting.add(textSetting.get("sourceAccount").toString());//5
		Setting.add(textSetting.get("alamatNasabahPengirim").toString());//6
		Setting.add(textSetting.get("jenisNasabahPengirim").toString());//7
		Setting.add(textSetting.get("statusPendudukNasabahPengirim").toString());//8
		Setting.add(textSetting.get("identitasNasabahPengirim").toString());//9
		Setting.add(textSetting.get("identitasPesertaPenerimaPenerus").toString());//10
		Setting.add(textSetting.get("identitasPesertaPenerimaAkhir").toString());//11
		Setting.add(textSetting.get("sandiKotaTujuan").toString());//12
		Setting.add(textSetting.get("namaNasabahPenerima").toString());//13
		Setting.add(textSetting.get("destinationAccount").toString());//14
		Setting.add(textSetting.get("alamatNasabahPenerima").toString());//15
		Setting.add(textSetting.get("jenisNasabahPenerima").toString());//16
		Setting.add(textSetting.get("statusPendudukNasabahPenerima").toString());//17
		Setting.add(textSetting.get("identitasNasabahPenerima").toString());//18
		Setting.add(textSetting.get("jenisTransaksi").toString());//19
		Setting.add(textSetting.get("jenisSaranaTransaksi").toString());//20
		Setting.add(textSetting.get("nomorUrut").toString());//21
		Setting.add(textSetting.get("nomorReferensi").toString());//22
		Setting.add(textSetting.get("nomorReferensiTrasaksiAsal").toString());//23
		Setting.add(textSetting.get("bebanBiaya").toString());//24
		Setting.add(textSetting.get("message").toString());//25
		Setting.add(textSetting.get("sor").toString());//26
        Setting.add(textSetting.get("TotalLine").toString());//27
		return Setting;
	}

	private String nullTo(String tonull, String Mapping) {

		String nullval = "";
		String prop = "";
		String padding = "";
		String[] getProp = new String[5];
		Integer countToken = 0;
		Integer countReal = 0;
		Map textLength = textSetting;
		//getLogger().info("Mapping :" + Mapping);
		StringTokenizer token = new StringTokenizer(textLength.get(Mapping).toString(), ":");
		//getLogger().info("CHECKcOUNT :" + token.countTokens());
		while (token.hasMoreTokens()) {
			countToken = token.countTokens();
			getProp[countReal] = token.nextToken().toString();
			countReal++;
			//getLogger().info("CHECK TOKEN :" + getProp[countToken]);
		}
		Integer maxleng;
		//getLogger().info("PROPERTY MAP :" + textLength.get(Mapping).toString());
		getProp = textLength.get(Mapping).toString().split(":");
		maxleng = Integer.parseInt(getProp[0]);
		//getLogger().info("maxlength :" + maxleng);
		if (tonull == null) {
			for (int k = 0; k < maxleng; k++) {
				nullval = nullval + " ";
			}
		} else {
			//getLogger().info("field :" + tonull);
			if (tonull.length() == maxleng) {
				//getLogger().info("samesize :" + tonull.length());
				nullval = tonull;
			} else if (tonull.length() < maxleng) {
				StringBuilder sb = new StringBuilder();
				//getLogger().info("CHECK INDEX : ");
				prop = getProp[1];
				if ("S".equals(getProp[2])) {
					padding = " ";
				} else {
					padding = "0";
				}
				//getLogger().info("Prop :" + prop);
				for (int k = 0; k < maxleng - tonull.length(); k++) {
					nullval = nullval + padding;
					//getLogger().info("ITERATE :" + k);
				}
				//getLogger().info("less :" + tonull.length());
				if (prop.equalsIgnoreCase("L")) {
					sb.append(tonull).append(nullval);
				} else if (prop.equalsIgnoreCase("R")) {
					sb.append(nullval).append(tonull);
				}
				nullval = sb.toString();
				//getLogger().info("CHECK VALUE :" + nullval);
			} else if (tonull.length() > maxleng) {
				//getLogger().info("more :" + tonull.length());
				try {
					nullval = tonull.substring(0, maxleng);
					//getLogger().info("OVERFLOW :" + tonull);
				} catch (Exception e) {
					//getLogger().info("INDEX BOUND :" + e.getLocalizedMessage());
				}
			}

		}
		return nullval;
	}

	private Integer getSplit(String split, Integer coordinate) {
		Integer Maxlengths;
		Integer countToken;
		Integer countReal = 0;
		String[] getProp = new String[10];
		//getLogger().info("DATA :" + split);
		StringTokenizer token = new StringTokenizer(split, ":");
		while (token.hasMoreTokens()) {
			getProp[countReal] = token.nextToken().toString();
			countReal++;
		}
		//getLogger().info("TEXT to Split :" + split);
		try {
			Maxlengths = Integer.parseInt(getProp[coordinate]);
			//getLogger().info("MAX : " + Maxlengths);
		} catch (NumberFormatException numberFormatException) {
			//getLogger().info("NOT NUMBER :" + numberFormatException);
			Maxlengths = 0;
		}
		return Maxlengths;
	}
	
	private void throwFIXException(String message, int line) throws Exception {
		throw new FIXException(message + " (Line " + line + ")");
	}
}
