/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.SknNgInOutDebitDetail;
import bdsm.model.SknNgInOutDebitFooter;
import bdsm.model.SknNgInOutDebitHeader;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.SknNgInwardDebitWorkerDAO;
import bdsm.util.BdsmUtil;
import bdsm.util.CRC16;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.SknNgInOutDebitDetailDAO;
import bdsmhost.dao.SknNgInOutDebitFooterDAO;
import bdsmhost.dao.SknNgInOutDebitHeaderDAO;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author v00019722
 */
public class SKNSMOTXTMapping extends SKNRespGenerator {

	//private String clearingDate;
	private String JakartaSubDate;
	
	private List textSetting;
	private List HeaderContainer = new ArrayList();
	private List DetailContainer = new ArrayList();
	private List FooterContainer = new ArrayList();
	private List<SknNgInOutDebitHeader> SMOHEader = new ArrayList();
	private List<SknNgInOutDebitDetail> SMODetails = new ArrayList();
	private List<SknNgInOutDebitFooter> SMOFooter = new ArrayList();
	private static final String FORMAT_G1_FILENAME = "sknng_in_out_debit.properties";
	private StringBuilder SknCRC = new StringBuilder();
	
	public SKNSMOTXTMapping(Map context) {
		super(context);
	}

	private StringBuilder SKNTXTSMOHeader(String Count) {
		StringBuilder sbHeader = new StringBuilder();
		Integer stringToCount = Integer.parseInt(Count);

		getLogger().debug("(SKN SMO)HEADER INDEX :" + Count);
		getLogger().debug("(SKN SMO)HEADER ID_BATCH reps Details :" + BatchID);

		Integer count = 0;
		Integer coordinate = 0;
		for (SknNgInOutDebitHeader header : SMOHEader) {
			if (header.getCompositeId().getRecordId().equals(stringToCount)) {
				coordinate = count;
				//getLogger().info("COORDINATE H :" + coordinate);
			}
			count++;
		}
		List cleanH = getcleanStringHeaders(SMOHEader.get(coordinate));
		Iterator cleanHeader = cleanH.iterator();
		try {
			loadTextSettingHeader();
			Iterator textConfig = textSetting.iterator();
			sbHeader = getAllDetails(cleanHeader, textConfig);
		} catch (Exception exception) {
			getLogger().debug("FAILED TO GET PROPERTY :" + exception, exception);
			sbHeader.append("FAILED TO GET HEADER PROPERTY");
		}
		return sbHeader;
	}

	private StringBuilder SKNTXTSMODetails(String Count) {

		Integer stringToCount = Integer.parseInt(Count);
		//getLogger().debug("(SKN SMO)IndexLoop :" + Count);
		//getLogger().debug("(SKN SMO)DETAILS ID_BATCH reps Details :" + BatchID);
		StringBuilder sbDetails = new StringBuilder();

		Integer count = 0;
		Integer coordinate = 0;
		for (SknNgInOutDebitDetail details : SMODetails) {
			if (details.getCompositeId().getRecordId().equals(stringToCount)) {
				coordinate = count;
				//getLogger().info("COORDINATE D :" + coordinate);
			}
			count++;
		}
		List cleanD = getcleanStringDetails(SMODetails.get(coordinate));
		Iterator cleanDetail = cleanD.iterator();
		try {
			loadTextSettingDetails();
			Iterator textConfig = textSetting.iterator();
			sbDetails = getAllDetails(cleanDetail, textConfig);
		} catch (Exception ex) {
			getLogger().debug("FAILED TO GET PROPERTY :" + ex, ex);
			sbDetails.append("FAILED TO GET DETAILS PROPERTY");
		}
		SknCRC.append(sbDetails);
		return sbDetails;
	}

	private StringBuilder SKNTXTSMOFooters(String Count) {
		StringBuilder sbFooters = new StringBuilder();
		Integer stringToCount = Integer.parseInt(Count);
		SknNgInOutDebitFooterDAO footerDao = new SknNgInOutDebitFooterDAO(session);
		//getLogger().debug("(SKN SMO)Parent ID :" + Count);
		//getLogger().debug("(SKN SMO)Footers ID_BATCH reps Details :" + BatchID);

		Integer count = 0;
		Integer coordinate = 0;
		for (SknNgInOutDebitFooter footer : SMOFooter) {
			if (footer.getCompositeId().getRecordId().equals(stringToCount)) {
				coordinate = count;
				//getLogger().info("COORDINATE F :" + coordinate);
			}
			count++;
		}
		Iterator cleanFooter = getcleanStringFooters(SMOFooter.get(coordinate)).iterator();
		try {
			loadTextSettingFooter();
			Iterator textConfig = textSetting.iterator();
			sbFooters = getAllDetails(cleanFooter, textConfig);
		} catch (Exception ex) {
			getLogger().debug("FAILED TO GET PROPERTY :" + ex, ex);
			sbFooters.append("FAILED TO GET FOOTER PROPERTY");
		}
		Integer CRCnya = 0;
		CRCnya = CRC16.CRC16(SknCRC.toString(), 0);
		
		Integer footeRs = sbFooters.length();
		if(footeRs.compareTo(5)>=0){
			Integer CRCfooter = null;
			try {
				CRCfooter = Integer.parseInt(sbFooters.substring(footeRs - 5, footeRs));
			} catch (NumberFormatException numberFormatException) {
				CRCfooter = Integer.parseInt(sbFooters.substring(footeRs - 5, footeRs).trim());
			}
			//getLogger().info("GETCRC : " + CRCfooter);
			
			if(CRCfooter.compareTo(CRCnya)!=0){
				getLogger().info("COMPARE :" + CRCfooter + "," + CRCnya);
				sbFooters.delete(sbFooters.length()-5,sbFooters.length());
				sbFooters.append(BdsmUtil.leftPad(CRCnya.toString(),5,'0'));
				SMOFooter.get(coordinate).setCrc(CRCnya.toString());
				footerDao.update(SMOFooter.get(coordinate));
			}
		}
		SknCRC.delete(0, SknCRC.length());
		DetailContainer.clear();
		return sbFooters;
	}

	@Override
	public Integer checkNumHeader() {
		Integer num;

		SknNgInOutDebitHeaderDAO detailDao = new SknNgInOutDebitHeaderDAO(session);
		getLogger().info("BATCH ID :" + BatchID);
		//getLogger().info("Clearing Date:" + clearingDate);
		SMOHEader = detailDao.getListbySMO(BatchID);
		//getLogger().info("LIST :" + SMOHEader);
		num = SMOHEader.size();
		Integer countHeader = 0;
		for (SknNgInOutDebitHeader header : SMOHEader) {
			if (header != null) {
				HeaderContainer.add(header.getCompositeId().getRecordId());
				//getLogger().info("HEADER CONTAINER :" + HeaderContainer.get(countHeader));
				countHeader++;
			}
		}
		//getLogger().info("NUMBER OF SKN SMO HEADER : " + num);
		return num;
	}

	@Override
	public Integer checkNumDetail(Integer count) {
		Integer num;
		getLogger().info("COUNT : " + count);
		Integer coordinate = Integer.parseInt(HeaderContainer.get(count - 1).toString());
		SknNgInOutDebitDetailDAO detailDao = new SknNgInOutDebitDetailDAO(session);
		getLogger().info("BATCH ID :" + BatchID);
		getLogger().info("Clearing Date:" + coordinate);
		SMODetails = detailDao.listByParentSMO(BatchID, coordinate);
		//getLogger().info("LIST :" + SMODetails);
		num = SMODetails.size();
		Integer countHeader = 0;
		for (SknNgInOutDebitDetail detail : SMODetails) {
			if (detail != null) {
				DetailContainer.add(detail.getCompositeId().getRecordId());
				//getLogger().info("DETAIL CONTAINER :" + DetailContainer.get(countHeader));
				countHeader++;
			}
		}
		getLogger().info("NUMBER OF SKN SMO DETAILS : " + num);
		return num;
	}

	@Override
	public Integer checkHeaderFooterSum() {
		return 0;
	}

	@Override
	public Integer checkNumFooter() {
		Integer num;

		SknNgInOutDebitFooterDAO detailDao = new SknNgInOutDebitFooterDAO(session);
		getLogger().info("BATCH ID :" + BatchID);
		//getLogger().info("Clearing Date:" + clearingDate);

		SMOFooter = detailDao.listOutgoing(BatchID);
		//getLogger().info("LIST :" + SMOFooter);
		num = SMOFooter.size();
		Integer countHeader = 0;
		for (SknNgInOutDebitFooter footer : SMOFooter) {
			if (footer != null) {
				FooterContainer.add(footer.getCompositeId().getRecordId());
				//getLogger().info("FOOTER CONTAINER :" + FooterContainer.get(countHeader));
				countHeader++;
			}
		}
		getLogger().info("NUMBER OF SKN FOOTER : " + num);
		return num;
	}

	@Override
	public boolean validate(Map context) {
		// check code branch input and template
		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
		SimpleDateFormat scf = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);
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
		Date dateJakarta = null;
		FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
		SknNgInwardDebitWorkerDAO endPointDao = new SknNgInwardDebitWorkerDAO(session);
		try {
			dateTime = sdf.format(fcrDao.get().getDatProcess()).toString();
			dateJakarta = endPointDao.getClearingBranchNextWorkingDate(Integer.parseInt(context.get(MapKey.hdcdBranch).toString()),fcrDao.get().getDatProcess());
			JakartaSubDate = scf.format(dateJakarta).toString();
		} catch (Exception ex) {
			getLogger().info("FAILED GET ENDPOINT :"+ ex,ex);
		}
		StringBuilder batchNo = new StringBuilder();
		StringBuilder batchNew = new StringBuilder();

		batchNo.append(BdsmUtil.leftPad(context.get(MapKey.hdcdBranch).toString(), 5, '0')) //batchNo.append("07728-")
				.append(dateTime).append(context.get(MapKey.param6));
		getLogger().info("BATCH NO :" + batchNo);

		BatchID = batchNo.toString();
		return true;
	}

	@Override
	public StringBuilder headerS(Integer count) {
		StringBuilder header = new StringBuilder();
		getLogger().info("ASSIGN INTO MAPPER HEADER");
		header = SKNTXTSMOHeader(HeaderContainer.get(count - 1).toString());
		return header;
	}

	@Override
	public StringBuilder detailS(Integer count) {
		StringBuilder Details = new StringBuilder();
		//getLogger().info("ASSIGN INTO MAPPER DETAILS");
		Details = SKNTXTSMODetails(DetailContainer.get(count - 1).toString());
		return Details;
	}

	@Override
	public StringBuilder footerS(Integer count) {
		StringBuilder footer = new StringBuilder();
		//getLogger().info("ASSIGN INTO MAPPER FOOTER");
		footer = SKNTXTSMOFooters(FooterContainer.get(count-1).toString());
		return footer;
	}

	private List getcleanStringHeaders(SknNgInOutDebitHeader hdr) {
		List cleanHeader = new ArrayList();
		//getLogger().info("HEADERNYA : " + hdr);
		cleanHeader.add(hdr.getTipeRecord());
		cleanHeader.add(hdr.getKodeDke());
		cleanHeader.add(hdr.getBatchId());
		cleanHeader.add(hdr.getJamTanggalMessage());
		cleanHeader.add(hdr.getJumlahRecords());
		cleanHeader.add(hdr.getTotalNominal());
		// check jakarta surabaya
		List cityCode = new ArrayList();
		StringTokenizer token = new StringTokenizer(PropertyPersister.CITYCODE,":");
		while(token.hasMoreTokens()){
			cityCode.add(token.nextToken());
		}
		//Integer BranchCode = Integer.parseInt(context.get(MapKey.hdcdBranch).toString());
		//List<BaCcBrnMast> JS = branchDao.getSpecificRegion(cityCode, BranchCode);
		if(cityCode.indexOf(context.get(MapKey.hdcdBranch).toString())==-1){
			cleanHeader.add(hdr.getTanggalBatch());
		} else {
			if(JakartaSubDate != null){
				cleanHeader.add(JakartaSubDate);
			} else {
				getLogger().info("NOT HPLUS1");
				cleanHeader.add(hdr.getTanggalBatch());
			}
		}
		cleanHeader.add(hdr.getJenisSetelmen());
		cleanHeader.add(hdr.getIdentitasPesertaPengirim());
		cleanHeader.add(hdr.getSandiKotaPengirim());
		//getLogger().info("HEADERNYA LIST : " + cleanHeader);
		return cleanHeader;
	}

	private List getcleanStringDetails(SknNgInOutDebitDetail dtls) {
		List cleanDetails = new ArrayList();
		cleanDetails.add(dtls.getTipeRecord());
		cleanDetails.add(dtls.getKodeDke());
		cleanDetails.add(dtls.getIdentitasPesertaPenerima());
		cleanDetails.add(dtls.getSandiKotaPenerbit());
		cleanDetails.add(dtls.getNamaNasabahPemegang());
		cleanDetails.add(dtls.getDestCreditAccount().trim());
		cleanDetails.add(dtls.getNomorIdentitasPemegang().replaceAll(" ", ""));
		cleanDetails.add(dtls.getDebitAccount());
		getLogger().info("TRANSAKSI :" + dtls.getJenisTransaksi());
		if(dtls.getJenisTransaksi().trim().equalsIgnoreCase("00") || 
				dtls.getJenisTransaksi().trim().equalsIgnoreCase("10") 
				|| dtls.getJenisTransaksi().trim().equalsIgnoreCase("40")){
			cleanDetails.add(dtls.getJenisTransaksi());
		} else {
			cleanDetails.add("  10");
		}
		cleanDetails.add(dtls.getNomorWarkat());
		cleanDetails.add(dtls.getNominal());
		cleanDetails.add(dtls.getNomorUrut());
		cleanDetails.add(dtls.getNomorReferensi());
		cleanDetails.add(dtls.getBebanBiaya());
		try {
			if(StringUtils.isBlank(dtls.getSor()) || StringUtils.isEmpty(dtls.getSor())){
				cleanDetails.add("");
			} else {
				cleanDetails.add(dtls.getSor());
			}
		} catch (Exception e) {
			cleanDetails.add("");
		}
		return cleanDetails;
	}

	private List getcleanStringFooters(SknNgInOutDebitFooter ftr) {
		List cleanHeader = new ArrayList();
		cleanHeader.add(ftr.getTipeRecord());
		cleanHeader.add(ftr.getKodeDke());
		cleanHeader.add(ftr.getCrc());
		return cleanHeader;
	}

	private void loadTextSettingHeader() throws Exception {
		Properties properties = new Properties();
		InputStream in = SKNSMOTXTMapping.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME);
		properties.load(in);
		textSetting = new ArrayList();
		textSetting.add(properties.getProperty("TIPE_RECORD"));
		textSetting.add(properties.getProperty("KODE_DKE"));
		textSetting.add(properties.getProperty("BATCH_ID"));
		textSetting.add(properties.getProperty("JAM_TANGGAL_MESSAGE"));
		textSetting.add(properties.getProperty("JUMLAH_RECORDS"));
		textSetting.add(properties.getProperty("TOTAL_NOMINAL"));
		textSetting.add(properties.getProperty("TANGGAL_BATCH"));
		textSetting.add(properties.getProperty("JENIS_SETELMEN"));
		textSetting.add(properties.getProperty("IDENTITAS_PESERTA_PENGIRIM"));
		textSetting.add(properties.getProperty("SANDI_KOTA_PENGIRIM"));
		in.close();
	}

	private void loadTextSettingDetails() throws Exception {
		Properties properties = new Properties();
		InputStream in = SKNSMOTXTMapping.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME);
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

	private void loadTextSettingFooter() throws Exception {
		Properties properties = new Properties();
		InputStream in = SKNSMOTXTMapping.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME);
		properties.load(in);
		textSetting = new ArrayList();
		textSetting.add(properties.getProperty("TIPE_RECORD"));
		textSetting.add(properties.getProperty("KODE_DKE"));
		textSetting.add(properties.getProperty("CRC"));
		in.close();
	}
	private String getSplit(String split, Integer coordinate) {
		String Maxlengths;
		Integer countReal = 0;
		List getProp = new ArrayList();
		//getLogger().info("DATA SPLIT:" + split);
		StringTokenizer token = new StringTokenizer(split, ":");
		while (token.hasMoreTokens()) {
			getProp.add(token.nextToken().toString());
			countReal++;
		}
		//getLogger().info("TEXT to Split :" + split);
		try {
			Maxlengths = getProp.get(coordinate).toString();
			//getLogger().info("MAX : " + Maxlengths);
		} catch (Exception numberFormatException) {
			Maxlengths = "FALSE";
		}
		return Maxlengths;
	}

	private StringBuilder getAllDetails(Iterator cleanDetails, Iterator textConfig) {
		StringBuilder sbDetails = new StringBuilder();
		while (cleanDetails.hasNext()) {
			String ListToIterate = null;
			try {
				ListToIterate = cleanDetails.next().toString();
			} catch (Exception e) {
				ListToIterate = "";
			}
			String ListToConfig = textConfig.next().toString();
			//getLogger().info("LIST1 :" + ListToIterate);
			//getLogger().info("LIST2 :" + ListToConfig);
			String textAll = null;

			try {
				textAll = getSplit(ListToConfig, 1);
				if (textAll.equalsIgnoreCase("FALSE")) {
					textAll = "L";
				}
			} catch (Exception e) {
				//getLogger().info("DEFAULT LEFT : " + e);
				textAll = "L";
			}
			String Coordinate = getSplit(ListToConfig, 0);
			//getLogger().debug("TEXTALL : " + textAll);
			//getLogger().debug("COORDINATE : " + Coordinate);
			if (textAll.equalsIgnoreCase("L")) {
				String leading = getSplit(ListToConfig, 2);
				if(leading.equalsIgnoreCase("FALSE")){
					sbDetails.append(BdsmUtil.rightPad(ListToIterate, Integer.parseInt(Coordinate), ' '));
				} else {
					sbDetails.append(BdsmUtil.rightPad(ListToIterate, Integer.parseInt(Coordinate), '0'));
				}
			} else {
				String leading = getSplit(ListToConfig, 2);
				if(leading.equalsIgnoreCase("FALSE")){
					sbDetails.append(BdsmUtil.leftPad(ListToIterate, Integer.parseInt(Coordinate), ' '));
				} else {
					sbDetails.append(BdsmUtil.leftPad(ListToIterate, Integer.parseInt(Coordinate), '0'));
				}				
			}
		}
		return sbDetails;
	}
}
