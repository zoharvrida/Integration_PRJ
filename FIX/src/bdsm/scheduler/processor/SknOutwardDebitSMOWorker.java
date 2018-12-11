/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.*;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixLogPK;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.BdsmUtil;
import bdsm.util.CRC16;
import bdsmhost.dao.*;
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
public class SknOutwardDebitSMOWorker extends BaseProcessor {

	private static String FORMAT_G1_FILENAME = "skn_out_gen1_debit.properties";
    private static String YES = "Y";
    private static String DES = "3DES/CBC";
	private Map textSetting;

    /**
     * 
     * @param context
     */
    public SknOutwardDebitSMOWorker(Map context) {
		super(context);
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {

		if (StatusDefinition.AUTHORIZED.equals(context.get(MapKey.status))) {
			return true;
		}
		getLogger().info("Begin Read Outward SMO Debit");
		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
		this.tx = session.beginTransaction();

		String userBatchno = "";
		String FixlogName = "";

		String batchNumber = null;

		Integer countDetailSuccess = 0;
		Integer countDetailReject = 0;
		Boolean FlagProcess = true;

		Boolean headerFlag = true;
		Boolean detailFlag = true;

		StringBuilder parentID = new StringBuilder();

		BigDecimal totalRec = new BigDecimal(0.0);
		BigDecimal headerTotal = new BigDecimal(0.0);
		StringBuilder sbCrcCheck = new StringBuilder();
		StringBuilder flName = new StringBuilder();
        
        getLogger().debug("BATCHID :" + context.get(MapKey.batchNo).toString());
        getLogger().debug("FILENAME :" + context.get(MapKey.param5).toString());
        //getLogger().debug("DECRYPT :" + context.get(MapKey.keyDecrypt).toString());
        // get Param from fixlog
        FixLogDao logDao = new FixLogDao(session);
        FixLog fixLogs = logDao.get(context.get(MapKey.batchNo).toString());

		String param5 = context.get(MapKey.param5).toString();
		//getLogger().info("CHECK_PARAM5 :" + param5);
        String[] param5Arr = fixLogs.getFixLogPK().getFileName().split("_");
		Integer paramLength = param5Arr.length;
		
		StringBuilder fcrname = new StringBuilder();
		
		for(int incr=3 ;incr < paramLength;incr++){
			fcrname.append(param5Arr[incr]);
			if(incr != paramLength-1){
				fcrname.append("_");
			}
		}

		List AcctNo = new ArrayList();
		StringTokenizer tokenAcct = new StringTokenizer(param5Arr[2], ":");
		while (tokenAcct.hasMoreTokens()) {
			AcctNo.add(tokenAcct.nextToken());
		}

		FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
		userBatchno = param5Arr[1];
		flName.append(param5Arr[0]).append("_").append(userBatchno).append("_").append(AcctNo.get(0)).append("_").append(param5Arr[3]);
		FixlogName = flName.toString();
		getLogger().info("FIRST BATCH :" + userBatchno);

		// getting account number

		SknngInOutDebitGen1HeaderDao headerDao = new SknngInOutDebitGen1HeaderDao(session);
		SknngInOutDebitGen1DetailsDao detailDao = new SknngInOutDebitGen1DetailsDao(session);
		SknngInOutDebitGen1FooterDao footerDao = new SknngInOutDebitGen1FooterDao(session);

		FcrBaBankMast fcrDates;
		SknngInOutDebitGen1Header modelHeader = null;
		SknngInOutDebitGen1Details modelDetail;
		SknngInOutDebitGen1Footer modelFooter;
		SknngInOutDebitHPK modelPk = new SknngInOutDebitHPK();
		SknngInOutDebitPK detailPk = new SknngInOutDebitPK();


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
		StringBuilder errMessage = new StringBuilder();
		try {
			while ((line = rd.readLine()) != null) {
				if (line.trim().length() == 0) {
					continue;
				}
				//}
				//for (String line : lines) {

				if (line.charAt(0) == '0') {
					modelHeader = new SknngInOutDebitGen1Header();
					loadTextSettingHeader();
					//CountFalse = 0;
					//getLogger().info("[START] Header Data Processing for File : " + param5);
					ctrColumn = 0;

					List Validate = new ArrayList();
					//getLogger().info("LINE LENGTH :" + line.length());
					//getLogger().info("HEADER LENGTH :" + textSetting.get("Total_Line").toString());

                    Integer lengthnumber = 0;
                    // check if file Encrypted
                    if (context.get(MapKey.flgEncrypt).toString().equals(YES) && context.get(MapKey.modDecrypt).toString().equalsIgnoreCase(DES)) {
                        //lengthnumber = Integer.parseInt(textSetting.get("Total_LineENC").toString());
						line = line.substring(0,Integer.parseInt(textSetting.get("Total_Line").toString()));
                    }
					lengthnumber = Integer.parseInt(textSetting.get("Total_Line").toString());
                    if (lengthnumber.compareTo(line.length()) == 0) {
						// 01000012014-03-21T08:01:45.345+07:00005000000227602850,002014-03-21CLRGBRINIDJA0300
						//getLogger().info("LINE LENGTH HEADER MATCH");
						SettingContainerHeader();
						headerFlag = true;
						FlagProcess = true;
						//getLogger().info("HEADER SPLIT SIZE :" + SettingContainerHeader().size());
						String tmpNominal = null;
						String nominalTOlast = null;
						String tmpTotalRec = null;
						try {
							for (int i = 0; i < SettingContainerHeader().size(); i++) {
								//getLogger().info("HEADER INSERT :" + line.substring(Integer.parseInt(getSplit(SettingContainerHeader().get(i).toString(), 1).toString()), Integer.parseInt(getSplit(SettingContainerHeader().get(i).toString(), 2).toString())));

								Validate.add(line.substring(Integer.parseInt(getSplit(SettingContainerHeader().get(i).toString(), 1).toString()), Integer.parseInt(getSplit(SettingContainerHeader().get(i).toString(), 2).toString())));
								//getLogger().info("CTR :" + Integer.parseInt(getSplit(SettingContainerHeader().get(i).toString(), 2).toString()));
							}
							try {
								tmpNominal = line.substring(getSplit(textSetting.get("TotalNominal").toString(), 1), getSplit(textSetting.get("TotalNominal").toString(), 2));
								nominalTOlast = tmpNominal;
								tmpTotalRec = line.substring(getSplit(textSetting.get("TotalItem").toString(), 1), getSplit(textSetting.get("TotalItem").toString(), 2));
                                nominalTOlast = tmpNominal.replace(",", ".").trim();
							} catch (Exception exception) {
								getLogger().debug("EXCEPTION :" + exception,exception);
							}
						} catch (Exception exception) {
							getLogger().debug("EXCEPTION :" + exception,exception);
						}
						modelHeader.setTipeRecord(Validate.get(0).toString());
						modelHeader.setKodeDKE(Validate.get(1).toString());
						modelHeader.setSandiBankPengirim(Validate.get(2).toString());
						modelHeader.setTransactionDate(Validate.get(3).toString());

						modelHeader.setBatchNo(Validate.get(4).toString().trim());
                        modelHeader.setTotalNominal(nominalTOlast);
						headerTotal = headerTotal.add(BigDecimal.valueOf(Double.parseDouble(modelHeader.getTotalNominal().trim())));
						//getLogger().info("HEADER TOTAL :" + modelHeader.getTotalNominal());
						modelHeader.setTotalRecord(Integer.parseInt(tmpTotalRec.trim()));
						modelHeader.setSandiKBI(Validate.get(5).toString().trim());
						modelHeader.setSandiPenyelenggaraPengirim(Validate.get(6).toString());
						// validate value in Header
						FcrChAcctMastDao chAcctDao = new FcrChAcctMastDao(session);
						FcrCiCustmastDao ciCustDao = new FcrCiCustmastDao(session);
						FcrChAcctMastPK pk = new FcrChAcctMastPK();

						//getLogger().debug("ACCTNO = " + AcctNo);
						pk.setCodAcctNo(BdsmUtil.rightPad(AcctNo.get(1).toString(), 16));
						pk.setFlgMntStatus("A");
						FcrChAcctMast chacct = chAcctDao.getFull(pk);
						FcrCiCustmast ciCust = ciCustDao.get(chacct.getCodCust());

						modelHeader.setAcctTitle(chacct.getCodAcctTitle());
						modelHeader.setAcctNo(chacct.getCompositeId().getCodAcctNo());
						modelHeader.setCodNatlId(ciCust.getCodCustNatlID());

						Pm_fin_inst_dir_mast modelPm = new Pm_fin_inst_dir_mast();

						try {
							//getLogger().info("SANDI BANK : " + modelHeader.getSandiBankPengirim());
							modelPm.setBi_Off_code(headerDao.getCod_Bi(modelHeader.getSandiBankPengirim()));
							modelHeader.setCod_Bi_off(modelPm.getBi_Off_code());
						} catch (Exception e) {
							//getLogger().info("BI_OFF_CODE NOT FOUND " + e);
							errMessage.append("BI_OFF_CODE NOT FOUND ");
							headerFlag = false;
						}
						try {
                            //getLogger().info("SANDI PENYELANGGARA PENGIRIM : " + modelHeader.getSandiBankPengirim());
                            modelPm.setCod_Sector(headerDao.getCode_sector(modelHeader.getSandiBankPengirim().trim()));
							modelHeader.setCod_Sector(modelPm.getCod_Sector());
						} catch (Exception e) {
							//getLogger().info("SECTOR_CODE NOT FOUND " + e);
							errMessage.append("SECTOR_CODE NOT FOUND ");
							headerFlag = false;
						}
						if (!headerFlag) {
							//getLogger().info("TOREJECT :" + errMessage);
							modelHeader.setFlgReject("T");
						} else {
							modelHeader.setFlgReject("F");
						}

						HeaderComment.append("VALID HEADER");
						modelPk = new SknngInOutDebitHPK();
						modelPk.setParentId(parentRecordId);
						modelPk.setFileId(userBatchno);

						modelHeader.setCompositeId(modelPk);
						//modelHeader.setType("I");
						modelHeader.setIdMaintainedBy(AcctNo.get(0).toString());
						headerDao.insert(modelHeader);
					} else {
						// Length not Match Skip detail
						FlagProcess = false;
						//getLogger().info("LENGTH HEADER MISSMATCH");
						modelHeader.setFlgReject("T");
						HeaderComment.append("Header Length Missmatch");
					}
					//getLogger().info("[END] Header Data Processing for File : " + param5);
					textSetting.clear();

				} else if (line.startsWith("1")) {
					ctrRow++;
					ctrPArentNo++;
					ctrRecParent++;

					loadTextSettingDetails();
					//getLogger().info("[START] Detail Data Processing for File : " + param5);
					//getLogger().info("LENGTH LINE :" + line.length());
                    int TotalLength = 0;
                    
                    if (context.get(MapKey.flgEncrypt).toString().equals(YES) && context.get(MapKey.modDecrypt).toString().equalsIgnoreCase(DES)) {
                        line = line.substring(0,Integer.parseInt(textSetting.get("Total_LineD").toString()));
                    } 
                        TotalLength = Integer.parseInt(textSetting.get("Total_LineD").toString());
					modelDetail = new SknngInOutDebitGen1Details();
					StringBuilder Detailvalidation = new StringBuilder();
					//Integer TempCount = CountFalse;
					String tmpNominal = null;
					String nominal = null;
					//textSetting.clear();
					List ValidateDetails = new ArrayList();

					StringBuilder errMessageDtls = new StringBuilder();
					if (TotalLength == line.length() && FlagProcess) {
						//loadTextSettingDetails();
						//getLogger().info("LINE LENGTH DETAIL MATCH");
						SettingContainerDetails();
						FlagProcess = true;
						detailFlag = true;
						//getLogger().info("DETAILS SPLIT SIZE :" + SettingContainerDetails().size());

						for (int i = 0; i < SettingContainerDetails().size(); i++) {
							//getLogger().debug("DETAILS INSERT :" + line.substring(getSplit(SettingContainerDetails().get(i).toString(), 1), getSplit(SettingContainerDetails().get(i).toString(), 2)));
							ValidateDetails.add(line.substring(getSplit(SettingContainerDetails().get(i).toString(), 1), getSplit(SettingContainerDetails().get(i).toString(), 2)).trim());
						}
						try {
							tmpNominal = line.substring(getSplit(textSetting.get("Nominal").toString(), 1), getSplit(textSetting.get("Nominal").toString(), 2));
							nominal = tmpNominal.replace(",", ".").trim();
						} catch (Exception exception) {
							getLogger().info("EXCEPTION :" + exception);
						}

						//getLogger().info(nominal);
						totalRec = totalRec.add(BigDecimal.valueOf(Double.parseDouble(nominal)));

						String TipeRecord = ValidateDetails.get(0).toString();
						String KodeDKED = ValidateDetails.get(1).toString();
						String NoReferensi = ValidateDetails.get(2).toString();//3
						String NoUrut = ValidateDetails.get(3).toString();//12
						String NoWarkat = ValidateDetails.get(4).toString();//11
						String SandiKliringTertarik = ValidateDetails.get(5).toString();//3
						String AccountNo = ValidateDetails.get(6).toString();//0
						String SandiTransaksi = ValidateDetails.get(7).toString();//1
						String BankType = ValidateDetails.get(8).toString();//4
						String SandiKliringAsal = ValidateDetails.get(9).toString();//5
						String SandiPenyelenggaraAsal = ValidateDetails.get(10).toString();//6
						//String SOR = ValidateDetails.get(11).toString();//7
						//String ErrorCode = ValidateDetails.get(12).toString();//8

						// validate each field representative to its tabl
                        //SknngInOutDebitGen1DetailsDao G1dao = new SknngInOutDebitGen1DetailsDao(session);
						Pm_fin_inst_dir_mast modelPm = new Pm_fin_inst_dir_mast();

						try {
							modelPm.setBi_Off_code(detailDao.getCod_Bi(SandiKliringTertarik));
							modelDetail.setCod_Bi_off(modelPm.getBi_Off_code());
						} catch (Exception e) {
							//getLogger().info("BI_OFF_CODE NOT FOUND " + e);
							errMessageDtls.append("Identitas Peserta Penerima NOT FOUND ");
							//detailFlag = false;
						}
						try {
							modelPm.setCod_Sector(detailDao.getCode_sector(SandiKliringTertarik));
							modelDetail.setCod_Sector(modelPm.getCod_Sector());
						} catch (Exception e) {
							//getLogger().info("SECTOR CODE NOT FOUND " + e);
							errMessageDtls.append("Sandi Kota Penerbit NOT FOUND ");
							//detailFlag = false;
						}

						try {
							modelDetail.setTipeRecord(TipeRecord);
							modelDetail.setKodeDKE(KodeDKED);
							modelDetail.setRefNo(NoReferensi);
							modelDetail.setNoUrut(NoUrut);
							modelDetail.setNoWarkat(NoWarkat);
							modelDetail.setSandiKliringtertarik(SandiKliringTertarik);
							modelDetail.setAccountNo(AccountNo);
							modelDetail.setTransactionCode(SandiTransaksi);
							modelDetail.setBankType(BankType);
							modelDetail.setAmount(nominal);
							modelDetail.setSandiKliringAsal(SandiKliringAsal);
							modelDetail.setSandiPenyelanggaraAsal(SandiPenyelenggaraAsal);
							//modelDetail.setMessage(message);
							//modelDetail.setSOR(SOR);
							//modelDetail.setErrCode(ErrorCode);
						} catch (Exception e) {
							//getLogger().info("FAILED TO PROCESS DETAILS :" + ctrRow);
						}
						detailPk = new SknngInOutDebitPK();

						detailPk.setRecordId(ctrRow);
						detailPk.setParentId(parentRecordId);
						detailPk.setFileId(userBatchno);
						detailPk.setParentRecordId(ctrRecParent);

						modelDetail.setErrCode(errMessageDtls.toString());
						modelDetail.setCompositeId(detailPk);
						//modelDetail.setType("I");
						modelDetail.setIdMaintainedBy(AcctNo.get(0).toString());
						detailDao.insert(modelDetail);

						if (!detailFlag) {
							modelDetail.setFlgReject("T");
							countDetailReject++;
						} else {
							modelDetail.setFlgReject("F");
							countDetailSuccess++;
						}
					} else {
						getLogger().info(" Details Length Missmatch");
						Detailvalidation.append(" Detail Length Missmatch");
						modelDetail.setFlgReject("T");
						countDetailReject++;
					}
					//Countline = line.length() - TotalLength;
					sbCrcCheck.append(line);
					FlagProcess = true;
					//getLogger().info("Parsing Done");
					errMessageDtls.delete(0, errMessageDtls.length());

					//getLogger().info("[END] Detail Data Processing for File : " + param5);
					textSetting.clear();

				} else if (line.startsWith("9")) {
					//getLogger().info("[START] Footer Data Processing for File : " + param5);
					ctrRecParent = 0;
					fcrDates = fcrDao.get();
					textSetting.clear();
					loadTextSettingFooter();
					crcValue = 0;
					if (!"".equals(sbCrcCheck.toString())) {
						crcValue = CRC16.CRC16(sbCrcCheck.toString(), crcValue);
						//getLogger().info("CRC_CHECK : " + crcValue);
					}

					modelFooter = new SknngInOutDebitGen1Footer();
					ctrColumn = 0;
					modelFooter.setRecordType(line.substring(getSplit(textSetting.get("TipeRecordF").toString(), 1), getSplit(textSetting.get("TipeRecordF").toString(), 2)));

					modelFooter.setDKE_Code(line.substring(getSplit(textSetting.get("KodeDKEF").toString(), 1), getSplit(textSetting.get("KodeDKEF").toString(), 2)));
					modelFooter.setCrcCheck(line.substring(getSplit(textSetting.get("CRC").toString(), 1), getSplit(textSetting.get("CRC").toString(), 2)));

					//getLogger().info("CRCNYA : " + modelFooter.getCrcCheck());


					StringBuilder reason = new StringBuilder();

					if (crcValue != Integer.parseInt(modelFooter.getCrcCheck().trim())) {
						modelFooter.setTReject(ctrPArentNo);
						modelHeader.setFlgReject("T");
						reason.append("Invalid CRC");
						//getLogger().info("CRC NOT VALID");

					} else {
						if (StringUtils.isBlank(modelHeader.getFlgReject())) {
							modelHeader.setFlgReject("F");
						}
					}

					if ("VALID HEADER".equals(HeaderComment.toString())) {
						// Date Compare

						if (StringUtils.isBlank(modelHeader.getFlgReject())) {
							modelHeader.setFlgReject("F");
						}
						//}
						Integer HeaderCount = modelHeader.getTotalRecord();
						// Num Of Record Header and Detail
						if (HeaderCount != ctrPArentNo) {
							modelFooter.setTReject(ctrPArentNo);
							modelHeader.setFlgReject("T");
							reason.append(" Total Records in Header Must Equals With Number of Details");
						} else {
							if (StringUtils.isBlank(modelHeader.getFlgReject())) {
								modelHeader.setFlgReject("F");
							}
						}
						//getLogger().info("header TOTAL :" + headerTotal);

						// Amount Header and Detail
						if (headerTotal.compareTo(totalRec) != 0) {
							reason.append(" Total Amount detail and Header Missmatch");
							modelFooter.setTReject(ctrPArentNo);
							modelHeader.setFlgReject("T");
						} else {
							if (StringUtils.isBlank(modelHeader.getFlgReject())) {
								modelHeader.setFlgReject("F");
							}
						}

					} else if ("Header Length Missmatch".equalsIgnoreCase(HeaderComment.toString())) {
						modelHeader.setFlgReject("T");
						modelFooter.setTReject(ctrPArentNo);
						reason.append(" Header Length Missmatch");
					}

					if ("F".equals(modelHeader.getFlgReject())) {
						modelFooter.setTSuccess(ctrPArentNo);
					}
					//getLogger().info("ERROR :" + reason + errMessage);
					totalRec = new BigDecimal(0.0);
					modelHeader.setExtractStatus("N");
					if (StringUtils.isNotBlank(errMessage.toString())) {
						if (StringUtils.isNotBlank(reason.toString())) {
							//getLogger().info("SUM ERROR :" + reason + errMessage);
							modelHeader.setMessage(reason.append(errMessage).toString());
						} else {
							//getLogger().info("ERROR VALIDATION : " + errMessage);
							modelHeader.setMessage(errMessage.toString());
						}
					} else {
						modelHeader.setMessage(reason.toString());
					}
					modelFooter.setErrMessage(reason.toString());
					//headerDao.update(modelHeader);
					modelPk = new SknngInOutDebitHPK();
					modelPk.setFileId(userBatchno);
					modelPk.setParentId(parentRecordId);
					modelFooter.setCompositeId(modelPk);
					//modelFooter.setType("I");

					modelFooter.setIdMaintainedBy(AcctNo.get(0).toString());
					modelFooter.setTSuccess(countDetailSuccess);
					totalRec = new BigDecimal("0.0");
					headerTotal = new BigDecimal("0.0");
					if (modelFooter.getTReject() != null) {
						modelFooter.setTReject(countDetailReject);
						modelFooter.setTSuccess(countDetailSuccess);
					}

					footerDao.insert(modelFooter);
					//getLogger().info("[END] Footer Data Processing for File : " + param5);
					parentID.append(parentRecordId).append(",");
					parentRecordId++;
					ctrPArentNo = 0;

					countDetailReject = 0;
					countDetailSuccess = 0;

					sbCrcCheck.delete(0, sbCrcCheck.length());
					errMessage.delete(0, errMessage.length());
					HeaderComment.delete(0, HeaderComment.length());
					//getLogger().info("CHECK CLEAR : " + sbCrcCheck);
				} else {
					getLogger().info("SKIP PROCESS : " + line);
				}
			}

			String IDParent = parentID.substring(0, parentID.length() - 1);
			//getLogger().info("ID PARENT : " + IDParent);
			batchNumber = context.get(MapKey.batchNo).toString();
			//getLogger().info("BATCH LAST :" + batchNumber);
			context.put(MapKey.param1, IDParent);
			context.put(MapKey.batchNo, userBatchno);

			getLogger().info("ID_PARENT :" + context.get(MapKey.param1));
			getLogger().info("BATCH NO :" + context.get(MapKey.batchNo));

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

		getLogger().info("End Read Outgoing SMO");
		return true;
	}

	private List SettingContainerHeader() throws Exception {
		List Setting = new ArrayList();
		Setting.add(textSetting.get("TipeRecord").toString());
		Setting.add(textSetting.get("KodeDKE").toString());
		Setting.add(textSetting.get("SandiBankPengirim").toString());
		Setting.add(textSetting.get("TanggalTransaksi").toString());
		Setting.add(textSetting.get("NomorBatch").toString());
		Setting.add(textSetting.get("SandiKliringKBIPengirim").toString());
		Setting.add(textSetting.get("SandiPenyelenggaraPengirim").toString());
		return Setting;
	}

	private void loadTextSettingHeader() throws Exception {
		Properties properties = new Properties();
		InputStream in = SknOutwardDebitSMOWorker.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME);
		properties.load(in);
		textSetting = new HashMap();
		textSetting.put("TipeRecord", properties.getProperty("TipeRecord"));
		textSetting.put("KodeDKE", properties.getProperty("KodeDKE"));
		textSetting.put("SandiBankPengirim", properties.getProperty("SandiBankPengirim"));
		textSetting.put("TanggalTransaksi", properties.getProperty("TanggalTransaksi"));
		textSetting.put("NomorBatch", properties.getProperty("NomorBatch"));
		textSetting.put("SandiKliringKBIPengirim", properties.getProperty("SandiKliringKBIPengirim"));
		textSetting.put("SandiPenyelenggaraPengirim", properties.getProperty("SandiPenyelenggaraPengirim"));
		textSetting.put("TotalNominal", properties.getProperty("TotalNominal"));
		textSetting.put("TotalItem", properties.getProperty("TotalItem"));
		textSetting.put("Total_Line", properties.getProperty("Total_Line"));
		in.close();
	}

	private void loadTextSettingDetails() throws Exception {
		Properties properties = new Properties();
		InputStream in = SknOutwardDebitSMOWorker.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME);
		properties.load(in);
		textSetting = new HashMap();

		textSetting.put("TipeRecordD", properties.getProperty("TipeRecordD"));
		textSetting.put("KodeDKED", properties.getProperty("KodeDKED"));
		textSetting.put("NoReferensi", properties.getProperty("NoReferensi"));
		textSetting.put("NoUrut", properties.getProperty("NoUrut"));
		textSetting.put("NoWarkat", properties.getProperty("NoWarkat"));
		textSetting.put("SandiKliringTertarik", properties.getProperty("SandiKliringTertarik"));
		textSetting.put("NomorRekening", properties.getProperty("NomorRekening"));
		textSetting.put("SandiTransaksi", properties.getProperty("SandiTransaksi"));
		textSetting.put("JenisBank", properties.getProperty("JenisBank"));
		textSetting.put("Nominal", properties.getProperty("Nominal"));
		textSetting.put("SandiKliringAsal", properties.getProperty("SandiKliringAsal"));
		textSetting.put("SandiPenyelenggaraAsal", properties.getProperty("SandiPenyelenggaraAsal"));
		//textSetting.put("SOR", properties.getProperty("SOR"));
		//textSetting.put("KodeError", properties.getProperty("KodeError"));
		textSetting.put("Total_LineD", properties.getProperty("Total_LineD"));
		in.close();
	}

	private void loadTextSettingFooter() throws Exception {
		Properties properties = new Properties();
		InputStream in = SknOutwardDebitSMOWorker.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME);
		properties.load(in);
		textSetting = new HashMap();
		textSetting.put("TipeRecordF", properties.getProperty("TipeRecordF"));
		textSetting.put("KodeDKEF", properties.getProperty("KodeDKEF"));
		textSetting.put("CRC", properties.getProperty("CRC"));
		in.close();
	}

	private List SettingContainerDetails() throws Exception {
		List Setting = new ArrayList();

		Setting.add(textSetting.get("TipeRecordD").toString());//0
		Setting.add(textSetting.get("KodeDKED").toString());//1
		Setting.add(textSetting.get("NoReferensi").toString());//2
		Setting.add(textSetting.get("NoUrut").toString());//3
		Setting.add(textSetting.get("NoWarkat").toString());//4
		Setting.add(textSetting.get("SandiKliringTertarik").toString());//5
		Setting.add(textSetting.get("NomorRekening").toString());//6
		Setting.add(textSetting.get("SandiTransaksi").toString());//7
		Setting.add(textSetting.get("JenisBank").toString());//8
		Setting.add(textSetting.get("SandiKliringAsal").toString());//9
		Setting.add(textSetting.get("SandiPenyelenggaraAsal").toString());//10
		//Setting.add(textSetting.get("SOR").toString());//11
		//Setting.add(textSetting.get("KodeError").toString());//12
		return Setting;
	}

	private Integer getSplit(String split, Integer coordinate) {
		Integer Maxlengths;
		Integer countToken;
		Integer countReal = 0;
		List getProp = new ArrayList();
		StringTokenizer token = new StringTokenizer(split, ":");
		while (token.hasMoreTokens()) {
			getProp.add(token.nextToken().toString());
			countReal++;
		}
		//getLogger().info("TEXT to Split :" + split);
		try {
			Maxlengths = Integer.parseInt(getProp.get(coordinate).toString());
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
