/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.*;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.BdsmUtil;
import bdsm.util.CRC16;
import bdsmhost.dao.*;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeffri Tambunan
 */
public class SknNgOutwardSMODebitAction extends BaseHostAction {

	private static final String FORMAT_G1_FILENAME = "skn_in_out_debit.properties";
	private HostSknngInOutCreditHeader modelHeader;
//    private TmpSknngInOutCreditFooter modelFooter;
	private SknNgPK modelPk;
	//private SknNgIncomingCreditPK modelPKGen1;
	private List<? extends Object> modelList;
	private String mode;
	private String modes;
	private String batchNo;
	private String parentIds;
	private String cdBranch;
	private String idMaintainedBy;
	private String idMaintainedSpv;
	private String Account;
	private List textSetting;
	private FcrChAcctMastPK modelPks;
	private FcrChAcctMast model;

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    public String doList() {
		SknngInOutDebitGen1HeaderDao daoHeader = new SknngInOutDebitGen1HeaderDao(getHSession());
		getLogger().debug("MODE :" + mode);
		getLogger().debug("MODES :" + getModes());
		getLogger().debug("BATCH ID :" + getModelPk().getBatchNo());
		getLogger().debug("BATCH NO :" + batchNo);
		String batch_No;

		if (modes.equalsIgnoreCase("inquiry")) {
			batch_No = getModelPk().getBatchNo();
			mode = "inquiry";
		} else if (modes.equalsIgnoreCase("authorize")) {
			batch_No = getModelPk().getBatchNo();
			mode = "authorize";
		} else {
			batch_No = getModelPk().getBatchNo();
		}

		getLogger().debug("BATCH ID AFTER :" + batch_No);
		setModelList((List<? extends Object>) daoHeader.listGrid(batch_No, mode));
		getLogger().debug("CHECK_LIST :" + getModelList().toString());
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		getLogger().info("ACCOUNT :" + modelPks.getCodAcctNo());
		getLogger().info("FLAG :" + modelPks.getFlgMntStatus());
		modelPks.setCodAcctNo(BdsmUtil.rightPad(modelPks.getCodAcctNo(), 16));
		FcrChAcctMastDao dao = new FcrChAcctMastDao(getHSession());
		model = dao.getFull(modelPks);
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		SknngInOutDebitGen1HeaderDao dao = new SknngInOutDebitGen1HeaderDao(getHSession());
		//String batchNos = patchbankMast(batchNo);
		getLogger().info("SET REJECT");
		String batchNos = batchNo;
		//BatchNo.append(cdBranch).append(dateL).append(batchNo);
		StringTokenizer token = new StringTokenizer(parentIds, ",");
		List parentIdArray = new ArrayList();

		while (token.hasMoreTokens()) {
			parentIdArray.add(token.nextToken());
		}
		for (int i = 0; i < parentIdArray.size(); i++) {
			String parentId = parentIdArray.get(i).toString();
			getLogger().debug("PARENT : " + parentId);
			if (parentId != null && !"".equals(parentId.trim())) {
				List<SknngInOutDebitGen1Header> headers = dao.getList(batchNo, Integer.parseInt(parentId));

				for (SknngInOutDebitGen1Header header : headers) {
					if (header != null) {
						header.setExtractStatus("R");
						header.setFlgReject("T");
						header.setIdMaintainedSpv(idMaintainedSpv);
						header.setDtmUpdatedSpv(SchedulerUtil.getTime());
						dao.update(header);
					}
				}
			}
		}
//		AuditlogDao auditdao = new AuditlogDao(getHSession());
//		auditdao.insert(getModelHeader().getIdMaintainedBy(), getModelHeader().getIdMaintainedSpv(), "SKN Incoming Credit",
//				getNamMenu(), "Approve","Approve");
		return SUCCESS;

	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		SknngInOutDebitGen1HeaderDao dao = new SknngInOutDebitGen1HeaderDao(getHSession());

		getLogger().debug("PARENT : " + parentIds);
		getLogger().debug("LOGGER : " + batchNo);
		getLogger().debug("ACCOUNT :" + getAccount());
		getLogger().info("SET APPROVE");
		try {
			Gen1Gen2Conversion(parentIds, batchNo);
			// All Parents Detail Processed
			// generate Footer
			StringTokenizer token = new StringTokenizer(parentIds, ",");
			List parentIdArray = new ArrayList();

			while (token.hasMoreTokens()) {
				parentIdArray.add(token.nextToken());
			}
			for (int i = 0; i < parentIdArray.size(); i++) {
				String parentId = parentIdArray.get(i).toString();
				getLogger().debug("PARENT : " + parentId);
				if (parentId != null && !"".equals(parentId.trim())) {
					List<SknngInOutDebitGen1Header> headers = dao.getList(batchNo, Integer.parseInt(parentId));

					for (SknngInOutDebitGen1Header header : headers) {
						if (header != null) {
							header.setExtractStatus("A");
							header.setFlgReject("F");
							header.setIdMaintainedSpv(idMaintainedSpv);
							header.setDtmUpdatedSpv(SchedulerUtil.getTime());
							dao.update(header);
						}
					}
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(SknNgOutwardSMODebitAction.class.getName()).log(Level.SEVERE, null, ex);
		}
		/*
		 */
//		AuditlogDao auditdao = new AuditlogDao(getHSession());
//		auditdao.insert(getModelHeader().getIdMaintainedBy(), getModelHeader().getIdMaintainedSpv(), "SKN Incoming Credit",
//				getNamMenu(), "Approve","Approve");
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet."); // To
	}

    /**
     * 
     * @param parentIds
     * @param batchNo
     * @return
     */
    public boolean Gen1Gen2Conversion(String parentIds, String batchNo) {

		SknngInOutDebitGen1HeaderDao dao = new SknngInOutDebitGen1HeaderDao(getHSession());
		SknngInOutDebitGen1DetailsDao dDao = new SknngInOutDebitGen1DetailsDao(getHSession());
		SknngInOutDebitGen1FooterDao fDaog2 = new SknngInOutDebitGen1FooterDao(getHSession());

		Integer totalCount = 0;
		Integer rejectCount = 0;
		Integer successCount = 0;

		Integer noCOunt = 0;
		Integer counter = 0;

		Integer gen2Parent = 0;
		Integer trueParent = 0;

		List parrentIdArray = new ArrayList();
		counter = 0;
		StringTokenizer token = new StringTokenizer(parentIds.toString(), ",");
		while (token.hasMoreTokens()) {
			parrentIdArray.add(token.nextToken().toString());
		}

		getLogger().debug("ARRAY : " + parrentIdArray.size());

		getLogger().debug("LIST ARRAY :" + parentIds);
		for (int i = parrentIdArray.size(); i > 0; i--) {
			String parentId;
			if (i != 0) {
				parentId = parrentIdArray.get(i - 1).toString();
			} else {
				parentId = parrentIdArray.get(i).toString();
			}
			getLogger().info("PARENT ID : " + parentId);
			getLogger().info("BATCH NO : " + batchNo);
			if (parentId != null && !"".equals(parentId.trim())) {
				List<SknngInOutDebitGen1Header> headers = dao.getList(batchNo, Integer.parseInt(parentId));
				// get detail from all header
				getLogger().debug("HEADER : " + headers);
				for (SknngInOutDebitGen1Header header : headers) {
					StringBuilder crcCheck = new StringBuilder();
					if (header != null) {
						getLogger().debug("HEADER A : " + header);
						getLogger().info("HEADER COUNT : " + headers.size());
						if (!"T".equalsIgnoreCase(header.getFlgReject())) {
							SknNgPK modelPkgen2 = new SknNgPK(); // gen1
							SknngInOutDebitHPK modelG1debitHpk = new SknngInOutDebitHPK();

							SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.sknDategen1);
							SimpleDateFormat sdfT = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);
							gen2Parent++;
							modelG1debitHpk.setFileId(batchNo);
							modelG1debitHpk.setParentId(Integer.parseInt(parentId));

							SknngInOutDebitGen1HeaderDao g1HDebitDao = new SknngInOutDebitGen1HeaderDao(getHSession());
							SknNgInOutDebitHeaderDAO G2DebitHdao = new SknNgInOutDebitHeaderDAO(getHSession());

							SknngInOutDebitGen1Header headerG1Debit = g1HDebitDao.get(modelG1debitHpk);
							SknNgInOutDebitHeader headerG2Debit = new SknNgInOutDebitHeader();
							//getLogger().info("TIPERECORD :" + headerG1Debit.getTipeRecord());
							//getLogger().info("KODE DKE :" + headerG1Debit.getKodeDKE());
							//getLogger().info("BATCH NO:" + headerG1Debit.getBatchNo());
							String JamTanggal = null;
							String PaddingTanggal = null;
							try {
								JamTanggal = SchedulerUtil.getDate(StatusDefinition.SknTanggalMessage);
								//getLogger().info("JAM TANGGAL :" + JamTanggal);
							} catch (ParseException parseException) {
								//getLogger().info("FAILED PARSE :" + parseException);
								//JamTanggal = 
							}
							PaddingTanggal = JamTanggal.substring(0, JamTanggal.length() - 2).concat(":").concat(JamTanggal.substring(JamTanggal.length() - 2, JamTanggal.length()));
							//getLogger().info("PADDING TANGGAL : " + PaddingTanggal);
							try {
								headerG2Debit.setTipeRecord(headerG1Debit.getTipeRecord());
								headerG2Debit.setKodeDke(headerG1Debit.getKodeDKE());
								headerG2Debit.setBatchId(headerG1Debit.getBatchNo());
								headerG2Debit.setJamTanggalMessage(PaddingTanggal);
								headerG2Debit.setJumlahRecords(headerG1Debit.getTotalRecord().toString());
								headerG2Debit.setTotalNominal(removeLeadingString(headerG1Debit.getTotalNominal(), "T"));
								headerG2Debit.setTanggalBatch(sdfT.format(sdf.parse(headerG1Debit.getTransactionDate())));
								headerG2Debit.setJenisSetelmen("CLRG");
								headerG2Debit.setIdentitasPesertaPengirim(headerG1Debit.getCod_Bi_off());
								headerG2Debit.setSandiKotaPengirim(headerG1Debit.getSandiPenyelenggaraPengirim());
							} catch (Exception e) {
								getLogger().info("FAILED TO MAPPING HEADER: " + e);
							}
							modelPkgen2.setBatchNo(batchNo);
							modelPkgen2.setRecordId(gen2Parent);
							trueParent = gen2Parent;
							headerG2Debit.setCompositeId(modelPkgen2);
							headerG2Debit.setType("O");
							G2DebitHdao.insert(headerG2Debit);
						}
						List<SknngInOutDebitGen1Details> details = dDao.getList(batchNo, Integer.parseInt(parentId));
						getLogger().debug("HEADER B : " + details);
						for (SknngInOutDebitGen1Details detail : details) {
							// start Generate Details
							if (detail != null) {
								//getLogger().debug("DETAILS : " + details);

								StringBuilder Detailvalidation = new StringBuilder();
								gen2Parent++;
								noCOunt++;
								//if (!"R".equalsIgnoreCase(detail.getFlgAuth())) {
								SknngInOutDebitPK modelG1debitPK = new SknngInOutDebitPK();// gen2
								SknngInOutDebitHPK modelG1debitHpk = new SknngInOutDebitHPK();
								Pm_fin_inst_dir_mast modelPm = new Pm_fin_inst_dir_mast();

								totalCount++;
								getLogger().info("TOTAL COUNT Detail : " + totalCount);
								modelG1debitHpk.setFileId(batchNo);
								modelG1debitHpk.setParentId(Integer.parseInt(parentId));

								modelG1debitPK.setFileId(batchNo);
								modelG1debitPK.setParentId(Integer.parseInt(parentId));
								modelG1debitPK.setRecordId(detail.getCompositeId().getRecordId());
								modelG1debitPK.setParentRecordId(detail.getCompositeId().getParentRecordId());

								SknngInOutDebitGen1HeaderDao g1HDebitDao = new SknngInOutDebitGen1HeaderDao(getHSession());
								SknngInOutDebitGen1DetailsDao g1DDebitDao = new SknngInOutDebitGen1DetailsDao(getHSession());

								//getLogger().debug("modelPk.parent " + modelG1debitPK.getParentId());
								//getLogger().debug("modelPk.batchno " + modelG1debitPK.getFileId());
								//getLogger().debug("modelPk.recordID " + modelG1debitPK.getRecordId());
								//getLogger().debug("modelPk.parentRecordId " + modelG1debitPK.getParentRecordId());

								SknngInOutDebitGen1Header headerG1Debit = g1HDebitDao.get(modelG1debitHpk);
								// insert after Header get
								SknngInOutDebitGen1Details detailsG1Debit = g1DDebitDao.get(modelG1debitPK);

								SknNgInOutDebitDetailDAO G2Debitdao = new SknNgInOutDebitDetailDAO(getHSession());
								SknNgInOutDebitDetail detailsG2Debit = new SknNgInOutDebitDetail();
								SknNgPK modelPkgen2 = new SknNgPK();

								//getLogger().debug("headerG1 Debit SMO " + headerG1Debit);
								//getLogger().debug("detailsG1 Debit SMO " + detailsG1Debit);

								//getLogger().debug("Sandi Kliring Tertarik " + detailsG1Debit.getSandiKliringtertarik());
								//getLogger().debug("COD_BI " + headerG1Debit.getCod_Bi_off());
								//getLogger().debug("COD_SECTOR " + headerG1Debit.getCod_Sector());

								//detailsG1Debit.setMessage(Detailvalidation.toString());
								modelPkgen2.setBatchNo(batchNo);
								modelPkgen2.setRecordId(gen2Parent);
								detailsG2Debit.setCompositeId(modelPkgen2);
								// -- begin Reformatting --

								try {
									loadTextSettingDetails();
								} catch (Exception exception) {
									getLogger().info("FAILED LOAD STREAM");
								}

								getLogger().debug("TOTAL COUNT 2: " + totalCount);
								try {
									detailsG2Debit.setTipeRecord(detailsG1Debit.getTipeRecord());
									detailsG2Debit.setKodeDke(detailsG1Debit.getKodeDKE());
									detailsG2Debit.setIdentitasPesertaPenerima(detailsG1Debit.getCod_Bi_off());
									detailsG2Debit.setSandiKotaPenerbit(detailsG1Debit.getCod_Sector());
									detailsG2Debit.setNamaNasabahPemegang(headerG1Debit.getAcctTitle());
									detailsG2Debit.setDestCreditAccount(headerG1Debit.getAcctNo());
									detailsG2Debit.setNomorIdentitasPemegang(headerG1Debit.getCodNatlId());
									detailsG2Debit.setDebitAccount(detailsG1Debit.getAccountNo());
									detailsG2Debit.setJenisTransaksi(detailsG1Debit.getTransactionCode());
									detailsG2Debit.setNomorWarkat(detailsG1Debit.getNoWarkat());
									detailsG2Debit.setNominal(removeLeadingString(detailsG1Debit.getAmount(), "T"));
									detailsG2Debit.setNomorUrut(removeLeadingString(detailsG1Debit.getNoUrut(), "F"));
									detailsG2Debit.setNomorReferensi(detailsG1Debit.getRefNo());
									detailsG2Debit.setBebanBiaya("SLEV");
									// header 4
									// convert Value 2 to 0
								} catch (Exception numberFormatException) {
									getLogger().debug("TEST NULL : " + numberFormatException);
								}

								//getLogger().info("ARRAYLIST : " + getAllString(detailsG2Debit));

								Iterator debitG2Detail = getAllString(detailsG2Debit).iterator();
								Iterator textConfig = textSetting.iterator();

								//getLogger().info("ITERATOR : " + debitG2Detail);
								try {
									while (debitG2Detail.hasNext()) {
										String ListToIterate = null;
										try {
											ListToIterate = debitG2Detail.next().toString();
										} catch (Exception e) {
											ListToIterate = "";
										}
										String hasnext = textConfig.next().toString();
										String textAll = null;
										try {
											textAll = getSplit(hasnext, 1);
										} catch (Exception e) {
											getLogger().info("DEFAULT LEFT : " + e);
											textAll = "L";
										}
										String Coordinate = getSplit(hasnext, 0);

										//getLogger().info("ISI LIST :" + ListToIterate);
										//getLogger().debug("TEXTALL : " + textAll);
										//getLogger().debug("COORDINATE : " + Coordinate);
										try {
											if (textAll.toString().equalsIgnoreCase("L")) {
												crcCheck.append(BdsmUtil.rightPad(ListToIterate, Integer.parseInt(Coordinate), ' '));
											} else {
												crcCheck.append(BdsmUtil.leftPad(ListToIterate, Integer.parseInt(Coordinate), ' '));
											}
										} catch (Exception numberFormatException) {
											getLogger().info("LOOP EXCEPTION" + numberFormatException);
										}

									}
								} catch (Exception e) {
									getLogger().info("FAIL ON WHILE :" + e, e);
								}
								getLogger().info("FINISH CRC CHECKING");
								// -- end Reformatting
								try {
									detailsG2Debit.setType("O");
									detailsG2Debit.setDtmCreatedSpv(SchedulerUtil.getTime());
									detailsG2Debit.setIdCreatedSpv(idMaintainedBy);
									detailsG2Debit.setParentRecordId(trueParent);
									G2Debitdao.insert(detailsG2Debit);
								} catch (Exception e) {
									getLogger().info("Exception : " + e, e);
								}
								if (!org.apache.commons.lang3.StringUtils.isBlank(detailsG1Debit.getMessage())) {
									rejectCount++;
								} else {
									successCount++;
								}
								getLogger().debug("SUCCESS INSERT: " + totalCount);
								//}
							}
						}
						// Start Generate Footer
						getLogger().debug("GENERATE FOOTER: " + batchNo);

						SknngInOutDebitHPK modelPKGen1 = new SknngInOutDebitHPK();
						SknNgPK modelPKGen2 = new SknNgPK();
						getLogger().debug("BATCH ID :" + batchNo);
						gen2Parent++;
						modelPKGen1.setFileId(batchNo);
						modelPKGen1.setParentId(Integer.parseInt(parentId));
						SknNgInOutDebitFooterDAO fDao = new SknNgInOutDebitFooterDAO(getHSession());
						SknNgInOutDebitFooter footer = new SknNgInOutDebitFooter();
						getLogger().debug("TEST OBJETC");
						String FooterModel = null;
						try {
							getLogger().debug("OBJECT DAO : " + fDao.getOutgoing(modelPKGen2));
						} catch (Exception e) {
							FooterModel = "EMPTY";

						}
						Integer CRC;

						if (FooterModel.equals("EMPTY")) {
							try {
								CRC = CRC16.CRC16(crcCheck.toString(), 0);
								getLogger().info("CRC CHECK :" + CRC);
								footer.setCrc(CRC.toString());
								modelPKGen2.setBatchNo(batchNo);
								modelPKGen2.setRecordId(gen2Parent);
								crcCheck.delete(0, crcCheck.length());
								footer.setCompositeId(modelPKGen2);
								footer.setParentRecordId(trueParent);
								footer.setType("O");
								footer.setTipeRecord("3");
								footer.setKodeDke("2");
								getLogger().debug("DAO FOOTER: " + CRC);
								fDao.insert(footer);
							} catch (Exception e) {
								getLogger().info("FORMING FOOTER :" + e, e);
							}
						}
					}
				}
			}
		}
		return true;
	}

	private List getAllString(SknNgInOutDebitDetail dtls) {
		List cleanDetails = new ArrayList();
		cleanDetails.add(dtls.getTipeRecord());
		cleanDetails.add(dtls.getKodeDke());
		cleanDetails.add(dtls.getIdentitasPesertaPenerima());
		cleanDetails.add(dtls.getSandiKotaPenerbit());
		cleanDetails.add(dtls.getNamaNasabahPemegang());
		cleanDetails.add(dtls.getDestCreditAccount());
		cleanDetails.add(dtls.getNomorIdentitasPemegang());
		cleanDetails.add(dtls.getDebitAccount());
		cleanDetails.add(dtls.getJenisTransaksi());
		cleanDetails.add(dtls.getNomorWarkat());
		cleanDetails.add(dtls.getNominal());
		cleanDetails.add(dtls.getNomorUrut());
		cleanDetails.add(dtls.getNomorReferensi());
		cleanDetails.add(dtls.getBebanBiaya());
		cleanDetails.add(dtls.getSor());
		return cleanDetails;
	}

	private void loadTextSettingDetails() throws Exception {
		Properties properties = new Properties();
		InputStream in = SknNgOutwardSMODebitAction.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME);
		properties.load(in);
		textSetting = new ArrayList();
		textSetting.add(properties.getProperty("TIPE_RECORD"));
		textSetting.add(properties.getProperty("KODE_DKE"));

		textSetting.add(properties.getProperty("IDENTITAS_PESERTA_PENERIMA"));
		textSetting.add(properties.getProperty("SANDI_KOTA_PENERBIT"));
		textSetting.add(properties.getProperty("NAMA_NASABAH_PEMEGANG"));
		textSetting.add(properties.getProperty("DEST_CREDIT_ACCOUNT"));
		textSetting.add(properties.getProperty("NOMOR_IDENTITAS_PEMEGANG"));
		textSetting.add(properties.getProperty("DEBIT_ACCOUNT"));
		textSetting.add(properties.getProperty("JENIS_TRANSAKSI"));
		textSetting.add(properties.getProperty("NOMOR_WARKAT"));
		textSetting.add(properties.getProperty("NOMINAL"));
		textSetting.add(properties.getProperty("NOMOR_URUT"));
		textSetting.add(properties.getProperty("NOMOR_REFERENSI"));
		textSetting.add(properties.getProperty("BEBAN_BIAYA"));
		textSetting.add(properties.getProperty("SOR"));
		in.close();
	}

	private String getSplit(String split, Integer coordinate) {
		String Maxlengths;
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
			Maxlengths = getProp[coordinate];
			//getLogger().info("MAX : " + Maxlengths);
		} catch (Exception numberFormatException) {
			getLogger().info("EXCEPTION :" + numberFormatException);
			Maxlengths = "0";
		}
		return Maxlengths;
	}

	private String removeLeadingString(String chartoRemove, String flagLeading) {
		String allString;
		String getSen;
		StringBuilder newString = new StringBuilder();
		int index = 0;
		if (chartoRemove.equalsIgnoreCase("")) {

			return "";
		} else {
			char[] chars = chartoRemove.toCharArray();

			for (; index < chartoRemove.length(); index++) {
				if (chars[index] != '0') {
					break;
				}
			}
		}
		if (flagLeading.equalsIgnoreCase("T")) {
			allString = chartoRemove.substring(index, chartoRemove.length() - 2);
			getSen = chartoRemove.substring(chartoRemove.length() - 2, chartoRemove.length());
			newString.append(allString).append(",").append(getSen);
		} else {
			allString = chartoRemove.substring(index);
			newString.append(allString);
		}
		return newString.toString();
	}

    /**
     * 
     * @return
     */
    public String getIdMaintainedBy() {
		return idMaintainedBy;
	}

    /**
     * 
     * @param idMaintainedBy
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
		this.idMaintainedBy = idMaintainedBy;
	}

	/**
	 * @return the modelHeader
	 */
	public HostSknngInOutCreditHeader getModelHeader() {
		return modelHeader;
	}

	/**
	 * @param modelHeader the modelHeader to set
	 */
	public void setModelHeader(HostSknngInOutCreditHeader modelHeader) {
		this.modelHeader = modelHeader;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the batchNo
	 */
//    public String getBatchNo() {
//        return batchNo;
//    }
//
//    /**
//     * @param batchNo the batchNo to set
//     */
//    public void setBatchNo(String batchNo) {
//        this.batchNo = batchNo;
//    }
	/**
	 * @return the cdBranch
	 */
	public String getCdBranch() {
		return cdBranch;
	}

	/**
	 * @param cdBranch the cdBranch to set
	 */
	public void setCdBranch(String cdBranch) {
		this.cdBranch = cdBranch;
	}

    /**
     * 
     * @return
     */
    public String getParentIds() {
		return parentIds;
	}

    /**
     * 
     * @param parentIds
     */
    public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return batchNo;
	}

    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the modelList
	 */
	public List<? extends Object> getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<? extends Object> modelList) {
		this.modelList = modelList;
	}

	/**
	 * @return the modes
	 */
	public String getModes() {
		return modes;
	}

	/**
	 * @param modes the modes to set
	 */
	public void setModes(String modes) {
		this.modes = modes;
	}

	/**
	 * @return the Account
	 */
	public String getAccount() {
		return Account;
	}

	/**
	 * @param Account the Account to set
	 */
	public void setAccount(String Account) {
		this.Account = Account;
	}

	/**
	 * @return the modelPk
	 */
	public SknNgPK getModelPk() {
		return modelPk;
	}

	/**
	 * @param modelPk the modelPk to set
	 */
	public void setModelPk(SknNgPK modelPk) {
		this.modelPk = modelPk;
	}

	/**
	 * @return the idMaintainedSpv
	 */
	public String getIdMaintainedSpv() {
		return idMaintainedSpv;
	}

	/**
	 * @param idMaintainedSpv the idMaintainedSpv to set
	 */
	public void setIdMaintainedSpv(String idMaintainedSpv) {
		this.idMaintainedSpv = idMaintainedSpv;
	}
}
