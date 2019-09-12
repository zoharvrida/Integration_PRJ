/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.fcr.model.ChChqbkIssue;
import bdsm.fcr.model.ChChqbkIssuePK;
import bdsm.model.*;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.SMIFooterDao;
import bdsm.scheduler.dao.SknSMIDetailDao;
import bdsm.scheduler.dao.SknSMIHeaderDao;
import bdsm.scheduler.model.*;
import bdsm.util.BdsmUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.*;
import bdsmhost.fcr.dao.ChChqbkIssueDAO;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author v00019722
 */
public class SKNTXTSMImapping extends SKNRespGenerator {

	private String clearingDate;
	private String printDate;
	private List HeaderContainer = new ArrayList();
	private List DetailContainer = new ArrayList();
	private List FooterContainer = new ArrayList();
	private List<SknNgInOutDebitHeader> SMIHEADER = new ArrayList<SknNgInOutDebitHeader>();
	private List<SknNgInOutDebitDetail> SMIDETAIL = new ArrayList<SknNgInOutDebitDetail>();
	private List<SknNgInOutDebitFooter> SMIFOOTER = new ArrayList<SknNgInOutDebitFooter>();
	private BigDecimal Amount = new BigDecimal("0");
	private Integer TotalRecords;
	private final String IncomingDEbit = "SKNNGINCDB";
	private final String getProperties = "skn_smi_debit.properties";
	private HashMap textSetting;
	private String batchGen2;

	public SKNTXTSMImapping(Map context) {
		super(context);
	}

	private StringBuilder SKNSMIheaderTXT_gen(String count) {

		Integer stringToCount = Integer.parseInt(count);
		getLogger().debug("(SKN SMI)HEADER ID_BATCH reps Details :" + batchGen2);
		StringBuilder sbDetails = new StringBuilder();

		SknSMIHeaderDao SMIDAO = new SknSMIHeaderDao(session);
		TmpSknSMIHeader SMIheader = new TmpSknSMIHeader();
		TmpSKNSMIPK pkHeader = new TmpSKNSMIPK();

		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
		FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
		String Dates = sdf.format(fcrDao.get().getDatProcess());

		//if (stringToCount == Integer.parseInt(HeaderContainer.get(0).toString())) {
		// firrst record write to file
		try {
			sbDetails.append("0").append(Dates);
			//crcCheck.append(sbDetails);
			//getLogger().debug("HEADER :" + sbDetails);
		} catch (Exception e) {
			getLogger().error("ERROR PARSING DETAIL SKN_SMI:" + e);
		}
		pkHeader.setBatchNo(BatchID);
		pkHeader.setBacthNogen2(batchGen2);

		SMIheader.setTanggalBatch(Dates);
        SMIheader.setExtractStatus("Y");
		SMIheader.setPk(pkHeader);
		SMIDAO.insert(SMIheader);

		//DetailContainer.clear();
		return sbDetails;
	}

	private StringBuilder SKNSMIdetailsTXT_gen(String count) {

		//Integer stringToCount = Integer.parseInt(count); // Container Record
		TotalRecords = 0;
		//getLogger().debug("(SKN SMI) DETAIL ID_BATCH reps Details :" + batchGen2);
		StringBuilder sbDetails = new StringBuilder();

		SknSMIDetailDao SMIDAO = new SknSMIDetailDao(session);
		//SKNIncomingCreditDetailsDao joinDao = new SKNIncomingCreditDetailsDao(session);

		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
		SimpleDateFormat sdft = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);

		try {
			List<SknNgInOutDebitDetail> d = new ArrayList();
			List<SknNgInOutDebitHeader> h = new ArrayList();
			//getLogger().debug("BEFORE PARSING DETAILS SKN_SMI(DETAILS) :" + count);
			try {
				d = SMIDETAIL;
			} catch (Exception e) {
				//getLogger().info("GET ERROR FAILED XTRACT DETAIL:" + e);
			}
			try {
				h = SMIHEADER;
			} catch (Exception e) {
				//getLogger().info("GET ERROR FAILED XTRACT HEADER :" + e);
			}
			//getLogger().info("CHECKlist_HSIZE :" + h.size());

			loadTextSetting();
			String ReceiverRouting = null;
			String Account = null;
			String SenderRouting = null;
			String cod_LOB = null;
			String cod_Branch = null;
			String nominal = null;
			String hIdentitas = null;
			String hSandiKota = null;
			//getLogger().info("CHECKlist_SIZE :" + d.size());
			for (Integer d1 = 0; d1 < d.size(); d1++) {
				// cleanning the inquiry
				StringBuilder ErrorBuild = new StringBuilder();
				ErrorBuild.append("");
				TmpSknSMIDetails SMIDetails = new TmpSknSMIDetails();
				TmpSKNSMIpkDtls SMIpk = new TmpSKNSMIpkDtls();
				try {
					Account = "00".concat((d.get(d1).getDebitAccount()).trim());
					//getLogger().info("ACCOUNT NO :" + Account);
				} catch (Exception e) {
					//getLogger().info("ACCOUNT CANNOT BE FOUND :" + e);
					ErrorBuild.append("ACCOUNT CANNOT BE FOUND ");
					Account = BdsmUtil.leftPad("", Integer.parseInt(textSetting.get("Account").toString()), ' ');
				}
				try {
					//getLogger().info(" IDENTITAS PENERIMA : " + d.get(d1).getIdentitasPesertaPenerima());
					//getLogger().info(" SANDI KOTA PENERBIT : " + d.get(d1).getSandiKotaPenerbit());
					ReceiverRouting = SMIDAO.getAll_cod(d.get(d1).getIdentitasPesertaPenerima(), d.get(d1).getSandiKotaPenerbit(), 4);
				} catch (Exception e) {
					//getLogger().info("RECEIVER ROUTING CANNOT BE FOUND :" + e);
					ErrorBuild.append("RECEIVER ROUTING CANNOT BE FOUND ");
					ReceiverRouting = BdsmUtil.leftPad("", Integer.parseInt(textSetting.get("ReceiverRouting").toString()), ' ');;
				}
				try {
					for (int h1 = 0; h1 < h.size(); h1++) {
						//getLogger().info(" IDENTITAS PENERIMA : " + h.get(h1).getIdentitasPesertaPengirim());
						//getLogger().info(" SANDI KOTA PENERBIT : " + h.get(h1).getSandiKotaPengirim());
						if (h.get(h1).getCompositeId().getRecordId().compareTo(d.get(d1).getParentRecordId()) == 0) {
							hIdentitas = h.get(h1).getIdentitasPesertaPengirim();
							hSandiKota = h.get(h1).getSandiKotaPengirim();
						}
					}
					if (hIdentitas == null) {
						hIdentitas = "";
					}
					if (hSandiKota == null) {
						hSandiKota = "";
					}
					SenderRouting = SMIDAO.getAll_cod(hIdentitas, hSandiKota, 4);
				} catch (Exception e) {
					//getLogger().info("SENDER ROUTING CANNOT BE FOUND :" + e);
					ErrorBuild.append("SENDER ROUTING CANNOT BE FOUND ");
					SenderRouting = BdsmUtil.leftPad("", Integer.parseInt(textSetting.get("SenderRouting").toString()), ' ');
				}
				try {
					cod_LOB = SMIDAO.getCod_LOB(Account);
					if (cod_LOB == null) {
						cod_LOB = BdsmUtil.leftPad("", Integer.parseInt(textSetting.get("LOB").toString()), ' ');
						ErrorBuild.append("COD LOB CANNOT BE FOUND ");
					}
				} catch (Exception e) {
					getLogger().info("COD LOB CANNOT BE FOUND :" + e);
					ErrorBuild.append("COD LOB CANNOT BE FOUND");
				}
				try {
					cod_Branch = SMIDAO.getCC_BRN(Account);
					if (cod_Branch == null) {
						cod_Branch = BdsmUtil.leftPad("", Integer.parseInt(textSetting.get("branchCode").toString()), ' ');
						ErrorBuild.append("BRANCH CODE CANNOT BE FOUND ");
					}
				} catch (Exception e) {
					getLogger().info("BRANCH CODE CANNOT BE FOUND :" + e);
					ErrorBuild.append("BRANCH CODE CANNOT BE FOUND");
					cod_Branch = BdsmUtil.leftPad("", Integer.parseInt(textSetting.get("branchCode").toString()), ' ');
				}
				try {
					nominal = d.get(d1).getNominal().replace(",", ".").substring(1, d.get(d1).getNominal().length());
					getLogger().debug("AMOUNT : " + nominal);
				} catch (Exception e) {
					getLogger().info("INVALID AMOUNT :" + e);
					ErrorBuild.append("INVALID AMOUNT");
					nominal = d.get(d1).getNominal();
				}
				//getLogger().info("BEGIN CHECK CODE_REJECT");
				String codeReject = RejectValidation(Account, d.get(d1).getNomorWarkat(), nominal);

				//getLogger().info("TANGGAL :" + h.get(0).getTanggalBatch());
				
				//Date BatchDate = sdft.parse
				FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
				Date BatchDate = fcrDao.get().getDatProcess();
				//Date BatchDate = sdft.parse(h.get(0).getTanggalBatch());
				String CleanAccount = d.get(d1).getDebitAccount().trim();

				//getLogger().info("TANGGAL _PARSE :" + sdf.format(BatchDate));
				String NominalforAppend = nominal.trim().replace(".", "");
				sbDetails.append("1");
				sbDetails.append(sdf.format(BatchDate));
				sbDetails.append(BdsmUtil.leftPad(d.get(d1).getNomorWarkat(), Integer.parseInt(textSetting.get("NomorWarkat").toString())));
				sbDetails.append(BdsmUtil.leftPad(ReceiverRouting, Integer.parseInt(textSetting.get("ReceiverRouting").toString()), '0'));
				//sbDetails.append(BdsmUtil.leftPad(CleanAccount, Integer.parseInt(textSetting.get("Account").toString()), '0'));
				sbDetails.append(BdsmUtil.leftPad(CleanAccount.length()<=Integer.parseInt(textSetting.get("Account").toString())?CleanAccount:
						CleanAccount.substring(CleanAccount.length()-Integer.parseInt(textSetting.get("Account").toString()),CleanAccount.length())
						, Integer.parseInt(textSetting.get("Account").toString()),'0'));
				sbDetails.append(BdsmUtil.leftPad(d.get(d1).getJenisTransaksi().trim(), Integer.parseInt(textSetting.get("JenisTransaksi").toString())));
				sbDetails.append(BdsmUtil.leftPad(NominalforAppend.substring(0, NominalforAppend.length()), Integer.parseInt(textSetting.get("nominal").toString()), '0'));
				sbDetails.append(BdsmUtil.leftPad(SenderRouting, Integer.parseInt(textSetting.get("SenderRouting").toString()), '0'));
				sbDetails.append(BdsmUtil.rightPad(d.get(d1).getSor().trim(), Integer.parseInt(textSetting.get("SOR").toString()), ' '));
				sbDetails.append(BdsmUtil.leftPad(cod_LOB, Integer.parseInt(textSetting.get("LOB").toString()), '0'));
				try {
					if (cod_Branch.length() > 3) {
						sbDetails.append(BdsmUtil.leftPad(cod_Branch, Integer.parseInt(textSetting.get("branchCode").toString()), '0'));
						SMIDetails.setBranchCode(cod_Branch);
						//getLogger().info("BRANCH :" + cod_Branch);
						if (StringUtils.isBlank(cod_Branch)) {
							SMIDetails.setSubBranch(" ");
						} else {
							SMIDetails.setSubBranch(cod_Branch.substring(cod_Branch.length() - 1));
						}
					} else {
						sbDetails.append(BdsmUtil.leftPad(cod_Branch, Integer.parseInt(textSetting.get("branchNonCode").toString()), '0'));
						sbDetails.append(" ");
						SMIDetails.setBranchCode(cod_Branch);
						SMIDetails.setSubBranch(" ");
					}
				} catch (Exception e) {
					getLogger().info("NULL VALUE : " + e);
				}
				sbDetails.append(codeReject);

				getLogger().info("BEGIN INSERT TABLE");
				// insert to table for Audit
				//getLogger().info("TANGGAL :" + h.get(0).getTanggalBatch());

				try {
					SMIDetails.setTipe_record("1");
					SMIDetails.setClearing_Date(h.get(0).getTanggalBatch());
					SMIDetails.setInstNumber(d.get(d1).getNomorWarkat());
					SMIDetails.setReceivingRouteCode(ReceiverRouting);//
					SMIDetails.setAccountNo((d.get(d1).getDebitAccount()).trim());
					SMIDetails.setTrxCode(d.get(d1).getJenisTransaksi());
					SMIDetails.setAmount(nominal);//
					SMIDetails.setSenderRouteCode(SenderRouting);//
					SMIDetails.setSOR(d.get(d1).getSor());//
					SMIDetails.setLOB(cod_LOB);
					SMIDetails.setRejectCode(codeReject);
					SMIDetails.setOrderNo(TotalRecords);//
					SMIDetails.setParentID(d.get(d1).getParentRecordId().toString());
					SMIDetails.setMessage(ErrorBuild.toString());
					
					ErrorBuild.delete(0, ErrorBuild.toString().length());
					SMIpk.setBatchNo(BatchID);
					SMIpk.setBacthNogen2(batchGen2);
					SMIpk.setRecordId(d.get(d1).getCompositeId().getRecordId());

					SMIDetails.setPk(SMIpk);

					SMIDAO.insert(SMIDetails);
					//session.flush();
					if (d1.compareTo(d.size() - 1) != 0) {
						sbDetails.append(";");
					}
				} catch (Exception e) {
					getLogger().fatal(e, e.fillInStackTrace());
					getLogger().error("ERROR insert DETAILSG1:" + e);
				}

				try {
					//getLogger().info("NOMINA : " + NominalforAppend);
					Amount = Amount.add(BigDecimal.valueOf(Double.parseDouble(nominal.trim()))).setScale(2);
					//getLogger().debug("AMOUNT SKN_SMI1:" + Amount);
				} catch (NumberFormatException numberFormatException) {
					getLogger().info("FAILED TO ADD AMOUNT " + numberFormatException);
				}
				TotalRecords++;
			}
			//getLogger().debug("DETAILS SKN_SMI1:" + sbDetails.toString());
		} catch (Exception e) {
			//getLogger().fatal(e, e.fillInStackTrace());
			getLogger().error("ERROR PARSING DETAIL SKN_GEN1:" + e);
			e.printStackTrace();
		}
		return sbDetails;
	}

	private StringBuilder SKNfooterTXT_gen(String count) {


		Integer stringToCount = Integer.parseInt(count);
		//getLogger().debug("(SMI)Footer ID_BATCH reps Details :" + batchGen2);

		//getLogger().info("AMOUNT FOOTER: " + Amount);
		//getLogger().info("TotalRecord FOOTER: " + TotalRecords);

		SMIFooterDao footerDao = new SMIFooterDao(session);

		TmpSKNSMIFooter smiFooter = new TmpSKNSMIFooter();
		TmpSKNSMIPK smIpk = new TmpSKNSMIPK();

		StringBuilder sbDetails = new StringBuilder();

		//if (stringToCount == Integer.parseInt(FooterContainer.get(FooterContainer.size() - 1).toString())) {
		// last record record write to file
                String fieldAmount = Amount.toString().trim().replace(".","");
		try {
			loadTextSetting();
			sbDetails.append("9");
			sbDetails.append(BdsmUtil.leftPad(TotalRecords.toString(), Integer.parseInt(textSetting.get("TotalRecords").toString()), '0'));
			sbDetails.append(BdsmUtil.leftPad(fieldAmount, Integer.parseInt(textSetting.get("Amount").toString()), '0'));
			//crcCheck.append(sbDetails);
			//getLogger().debug("FOOTER :" + sbDetails);
		} catch (Exception e) {
			getLogger().error("ERROR PARSING FOOTER SKN_SMI:" + e);
		}
		smIpk.setBatchNo(BatchID);
		smIpk.setBacthNogen2(batchGen2);
		smiFooter.setPk(smIpk);
		smiFooter.setTotalNominal(Amount.toString());
		footerDao.insert(smiFooter);

		//} else {
		//sbDetails.delete(0, sbDetails.length());
		//}

		return sbDetails;
	}

	@Override
	public Integer checkNumHeader() {
		Integer num;

		SknNgInOutDebitHeaderDAO detailDao = new SknNgInOutDebitHeaderDAO(session);
		getLogger().info("BATCH ID :" + batchGen2);
		getLogger().info("Clearing Date:" + clearingDate);
		SMIHEADER = detailDao.getListbySMII(batchGen2, clearingDate);
		//getLogger().info("LIST :" + SMIHEADER);
		num = SMIHEADER.size();
		Integer countHeader = 0;
		for (SknNgInOutDebitHeader header : SMIHEADER) {
			if (header != null) {
				this.printDate = header.getTanggalBatch();
				HeaderContainer.add(header.getCompositeId().getRecordId());
				//getLogger().info("HEADER CONTAINER :" + HeaderContainer.get(countHeader));
				countHeader++;
			}
		}
		//getLogger().info("NUMBER OF SKN HEADER : " + num);

		this.checknumHeader = num;
		if (num == 0) {
			return 0;
		} else {
			return 1;
		}

	}

	@Override
	public Integer checkNumDetail(Integer count) {
		Integer num;
		Integer ParentCount = 0;

		boolean HeaderExist = true;
		try {
			ParentCount = HeaderContainer.size();
		} catch (NumberFormatException numberFormatException) {
			getLogger().info("FAILED TO CONVERT NUMBER" + numberFormatException);
		} catch (ArrayIndexOutOfBoundsException e) {
			getLogger().info("HEADER NOT FOUND" + e);
			HeaderExist = false;
			ParentCount = 0;
		}

		if (HeaderExist) {
			SknNgInOutDebitDetailDAO detailDao = new SknNgInOutDebitDetailDAO(session);
			getLogger().info("BATCH ID :" + batchGen2);
			getLogger().info("PARENT ID :" + ParentCount);
			int coordinate = 0;
			for (SknNgInOutDebitHeader Headers : SMIHEADER) {
				if (Headers != null) {
					ParentCount = Integer.parseInt(HeaderContainer.get(coordinate).toString());
					//getLogger().info("PARENT :" + ParentCount);
					SMIDETAIL.addAll(detailDao.listByParentRecordSMI(batchGen2, ParentCount));
					coordinate++;
				}
			}
			//getLogger().info("LIST :" + SMIDETAIL);
			num = SMIDETAIL.size();
			Integer countDetails = 0;
			for (SknNgInOutDebitDetail detail : SMIDETAIL) {
				if (detail != null) {
					DetailContainer.add(detail.getCompositeId().getRecordId());
					//getLogger().info("DETAIL CONTAINER :" + DetailContainer.get(countDetails));
					countDetails++;
				}
			}
			getLogger().info("NUMBER OF SKN DETAIL : " + num);
		} else {
			num = 0;
		}
		this.checknumDetails = num;
		this.reason = "SINGLE";
		if (num.compareTo(0) == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public Integer checkNumFooter() {

		Integer num;

		SknNgInOutDebitFooterDAO detailDao = new SknNgInOutDebitFooterDAO(session);
		getLogger().info("BATCH ID :" + batchGen2);
		SMIFOOTER = detailDao.list(batchGen2);
		//getLogger().info("LIST :" + SMIFOOTER);
		num = SMIFOOTER.size();
		if (num != 0) {
			for (SknNgInOutDebitFooter footer : SMIFOOTER) {
				if (footer != null) {
					FooterContainer.add(footer.getParentRecordId());
				} else {
					FooterContainer.add("0");
				}
			}
		}
		getLogger().info("NUMBER OF SKN FOOTER : " + num);

		this.checknumFooter = num;
		if (num == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public StringBuilder headerS(Integer count) {
		StringBuilder detail = new StringBuilder();

		Integer coordinate = count - 1;
		//getLogger().info("ASSIGN INTO MAPPER");
		detail = SKNSMIheaderTXT_gen(HeaderContainer.get(coordinate).toString());
		return detail;
	}

	@Override
	public StringBuilder detailS(Integer count) {
		StringBuilder detail = new StringBuilder();

		Integer coordinate = count - 1;
		//getLogger().info("ASSIGN INTO MAPPER");
		detail = SKNSMIdetailsTXT_gen(DetailContainer.get(coordinate).toString());
		return detail;
	}

	@Override
	public StringBuilder footerS(Integer count) {
		StringBuilder footer = new StringBuilder();
		getLogger().info("ASSIGN INTO MAPPER");
		footer = SKNfooterTXT_gen(count.toString());
		return footer;
	}

	@Override
	public Integer checkHeaderFooterSum() {
		return 1;
	}

	@Override
	public boolean validate(Map context) {
		// check code branch input and template
		DateFormat sdf = DateUtility.DATE_FORMAT_YYYYMMDD;
		SimpleDateFormat sbf = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);
		SimpleDateFormat sff = new SimpleDateFormat(StatusDefinition.patternJoin);
		getLogger().info("Start Validate File Response" + context.get(MapKey.reportFileName));
		getLogger().debug("Get Parameter1 :" + context.get(MapKey.param1));
		getLogger().debug("Get Parameter2 :" + context.get(MapKey.param2)); // clearing Date 
		getLogger().debug("Get Parameter3 :" + context.get(MapKey.param3));
		getLogger().debug("Get Parameter4 :" + context.get(MapKey.param4));
		getLogger().debug("Get Parameter5 :" + context.get(MapKey.param5));
		getLogger().debug("Get parameter6 :" + context.get(MapKey.param6)); // idbatch
		getLogger().debug("Get Hidden Parameter 1 :" + context.get(MapKey.hdUserid));
		getLogger().debug("Get Hidden Parameter 2 :" + context.get(MapKey.hdcdBranch));
		getLogger().debug("Get Hidden Parameter 3 :" + context.get(MapKey.hddtProcess));
		getLogger().debug("Get Hidden Parameter 4 :" + context.get(MapKey.hdidTemplate));
		getLogger().debug("Get Hidden Parameter 5 :" + context.get(MapKey.hdnamUser));
		getLogger().debug("Get Java Class :" + context.get(MapKey.javaClass));
		getLogger().debug("Get Report ID  :" + context.get(MapKey.reportId));
		getLogger().debug("Get Report Format:" + context.get(MapKey.reportFormat));
		getLogger().debug("Get Type Fix :" + context.get(MapKey.typeFix));
		String dateTime = null;
		String dateNewTime = null;
		Timestamp allTime = null;
		FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
		try {
			dateTime = sdf.format(fcrDao.get().getDatProcess()).toString();
			dateNewTime = sbf.format(fcrDao.get().getDatNextProcess()).toString();
		} catch (Exception ex) {
			Logger.getLogger(SKNTXTSMImapping.class.getName()).log(Level.SEVERE, null, ex);
		}
		StringBuilder batchNo = new StringBuilder();
		StringBuilder batchNew = new StringBuilder();
		batchNew.append(BdsmUtil.leftPad(context.get(MapKey.hdcdBranch).toString(), 5, '0')).append(dateNewTime).append(fcrDao.getFCRtime().getHours()).append(fcrDao.getFCRtime().getMinutes()).append(fcrDao.getFCRtime().getSeconds());
		batchNo.append(BdsmUtil.leftPad(context.get(MapKey.hdcdBranch).toString(), 5, '0')).append("-") //batchNo.append("07728-")
				.append(dateTime).append("-").append(context.get(MapKey.param6)).append("-").append(IncomingDEbit);
		//getLogger().info("BATCH NO :" + batchNo);

		BatchID = batchNew.toString();
		batchGen2 = batchNo.toString();
		clearingDate = sbf.format(fcrDao.get().getDatProcess()).toString();
		return true;
	}

	private void loadTextSetting() throws Exception {
		Properties properties = new Properties();
		InputStream in = SKNTXTmapping.class.getClassLoader().getResourceAsStream(getProperties);
		properties.load(in);
		textSetting = new HashMap();
		textSetting.put("NomorWarkat", properties.getProperty("NomorWarkat"));
		textSetting.put("ReceiverRouting", properties.getProperty("ReceiverRouting"));
		textSetting.put("Account", properties.getProperty("Account"));
		textSetting.put("JenisTransaksi", properties.getProperty("JenisTransaksi"));
		textSetting.put("nominal", properties.getProperty("nominal"));
		textSetting.put("SenderRouting", properties.getProperty("SenderRouting"));
		textSetting.put("SOR", properties.getProperty("SOR"));
		textSetting.put("LOB", properties.getProperty("LOB"));
		textSetting.put("branchCode", properties.getProperty("branchCode"));
		textSetting.put("branchNonCode", properties.getProperty("branchNonCode"));
		textSetting.put("TotalRecords", properties.getProperty("TotalRecords"));
		textSetting.put("Amount", properties.getProperty("Amount"));
		in.close();
	}

	private String RejectValidation(String cod_acct, String noWarkat, String Nominal) {
		String toReturn = "0";
		//getLogger().info("CHECK REJECT");
		//getLogger().info("CHECK VALUE 1:" + cod_acct);
		//getLogger().info("CHECK VALUE 2:" + noWarkat);
		FcrChAcctMast acctMast = new FcrChAcctMast();
		FcrChAcctMastPK acctPK = new FcrChAcctMastPK();
		FcrChAcctMastDao chacctDao = new FcrChAcctMastDao(session);
		ChChqbkIssueDAO chqDao = new ChChqbkIssueDAO(session);
		ChChqbkIssuePK pk = new ChChqbkIssuePK();

		acctPK.setCodAcctNo(BdsmUtil.rightPad(cod_acct.trim(), 16, ' '));
		acctPK.setFlgMntStatus("A");
		//getLogger().info("CHECK VALUE 3:" + acctPK.getCodAcctNo());
		acctMast = chacctDao.getFull(acctPK);
		try {
			if (acctMast.getCodAcctStat().compareTo(1) == 0 || acctMast.getCodAcctStat().compareTo(5) == 0) {
				//getLogger().info("CHCKstat " + acctMast.getCodAcctStat());
				return "3";
			} else if (acctMast.getCodAcctStat().compareTo(3) == 0) {
				//getLogger().info("CHCKstat " + acctMast.getCodAcctStat());
				return "6";
			} else {
				//getLogger().info("CALCULATE :" + Nominal);

				BigDecimal Balance = new BigDecimal(0.0);
				BigDecimal BalanceCompare = new BigDecimal(Double.parseDouble(Nominal));

				//getLogger().info("BAL AVAILABLE : " + acctMast.getBalAvail());
				//getLogger().info("BAL AMOUNT OD LIMIT : " + acctMast.getAmtOdLmt());
				//getLogger().info("BAL ACCT : " + acctMast.getBalacct());
				//getLogger().info("BAL AMOUNT HOLD: " + acctMast.getAmtHld());

				Balance = Balance.add(acctMast.getBalAvail().add(acctMast.getAmtOdLmt()).subtract(acctMast.getBalacct()).subtract(acctMast.getAmtHld()));
				//getLogger().info("BAL BALANCE : " + Balance);
				if (Balance.compareTo(BalanceCompare) <= 0) {
					getLogger().info("CHCKstat " + Balance);
					return "4";
				}
			}
		} catch (Exception e) {
			//getLogger().info("EXCEPTION ACCOUNT :" + e);
			return "1";
		}
		//getLogger().info("START CHEQUE BOOK");
		List<ChChqbkIssue> listChq = new ArrayList();
		pk.setCodAcct(cod_acct);
		pk.setChqkStart(noWarkat);
		pk.setChqkEnd(noWarkat);
		pk.setCodEntityVPD(11);
		pk.setFlgMntStatus("A");
		try {
			//getLogger().info("ACCT : " + pk);
			listChq = chqDao.getDetails(pk);
			//getLogger().info("LIST :" + listChq);
			Integer count = Integer.parseInt(noWarkat) - Integer.parseInt(listChq.get(0).getCompositeId().getChqkStart());

			//getLogger().info("COORDINATE :" + count);
			if ("P".equals(listChq.get(0).getChqkPaid().substring(count, count + 1))) {
				getLogger().info("EXCEPTION CHECKP :" + listChq.get(0).getChqkPaid().substring(count, count + 1));
				toReturn = "7";
			} else if ("S".equals(listChq.get(0).getChqkPaid().substring(count, count + 1))) {
				getLogger().info("EXCEPTION CHECKS :" + listChq.get(0).getChqkPaid().substring(count, count + 1));
				toReturn = "5";
			}
		} catch (Exception e) {
			getLogger().info("EXCEPTION CHQ :" + e);
			return "2";
		}
		return toReturn;
	}
}
