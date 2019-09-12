/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.fcr.model.BaBankMast;
import bdsm.model.HostSknngInOutCreditHeader;
import bdsm.model.Pm_fin_inst_dir_mast;
import bdsm.model.SknIncomingCreditScreen;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.*;
import bdsm.scheduler.model.*;
import bdsm.service.SknNgService;
import bdsm.util.BdsmUtil;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.SknngInOutGen2CreditHeaderDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Jeffri Tambunan
 */
public class SknNgIncomingCreditAction extends BaseHostAction {

    private HostSknngInOutCreditHeader modelHeader;
//    private TmpSknngInOutCreditFooter modelFooter;
    private TmpSknngInOutCreditPK modelPk;
    //private SknNgIncomingCreditPK modelCPK;
    private List<? extends Object> modelList;
    private final String FIRST = "FIRST";
    private final String LAST = "LAST";
    private final String APPROVEALL = "T";
    private static final String FORMAT_G2_FILENAME = "skn_gen2_detail_credit.properties";
    private List ListLength;
    private List ListPad;
    private List ListAdd;
    private String mode;
    private String modes;
    private String batchNo;
    private String parentIds;
    private String cdBranch;
    private String idMaintainedBy;
    private String nameMenu;

    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String doList() {
        SknngInOutGen2CreditHeaderDAO daoHeader = new SknngInOutGen2CreditHeaderDAO(getHSession());
        FixLogDao logDao = new FixLogDao(getHSession());
        getLogger().info("MODE :" + mode);
        getLogger().info("MODES :" + getModes());
        getLogger().info("BATCH ID :" + modelPk.getBatchNo());
        getLogger().info("BATCH NO :" + batchNo);
        String batch_No = modelPk.getBatchNo();
        if (nameMenu == null) {
            nameMenu = "23202";
        }

        if (modes.equalsIgnoreCase("inquiry")) {
            mode = "inquiry";
            setModelList((List<? extends Object>) daoHeader.listGrid(batch_No, mode));

        } else if (modes.equalsIgnoreCase("authorize")) {
            mode = "authorize";
            setModelList((List<? extends Object>) daoHeader.listGrid(batch_No, mode));
        } else {
            if (mode.equalsIgnoreCase("1")) {
                setModelList((List<? extends Object>) daoHeader.listGrid(batch_No, mode));
            } else {

                if (nameMenu.equalsIgnoreCase("23204")) {
                    getLogger().info("BATCH ID AFTER :" + batch_No);
                    List<FixLog> logScreen = logDao.getByFilenameAndDatePost("%" + batch_No + "%", null);

                    List<SknIncomingCreditScreen> Screen = new ArrayList<SknIncomingCreditScreen>();
                    List<HostSknngInOutCreditHeader> temporar = daoHeader.SingleApprove(batch_No);

                    BigDecimal totalAmount = new BigDecimal(0.0);
                    Integer totalRecord = 0;
                    Integer totalBatch = 0;
                    String tanggalBatch = null;
                    for (HostSknngInOutCreditHeader restructure : temporar) {
                        if (restructure != null) {
                            totalAmount = totalAmount.add(BigDecimal.valueOf(Double.parseDouble(restructure.getTotalNominal().trim())));
                            totalRecord = totalRecord + restructure.getJumlahRecords();
                            if (totalBatch.compareTo(restructure.getCompositeId().getParentRecordId()) == -1) {
                                totalBatch = restructure.getCompositeId().getParentRecordId();
                            }
                            tanggalBatch = restructure.getTanggalBatch();
                        }
                    }
                    SknIncomingCreditScreen screenCast = new SknIncomingCreditScreen();
                    screenCast.setTanggalBatch(tanggalBatch);
                    screenCast.setTotalAmount(totalAmount.toPlainString());
                    screenCast.setTotalBatch(totalBatch.toString());
                    screenCast.setTotalRecord(totalRecord.toString());
                    if (logScreen != null) {
                        screenCast.setFilename(logScreen.get(0).getFcrFileName());
                    }
                    Screen.add(screenCast);
                    setModelList(Screen);
                } else {
                    setModelList((List<? extends Object>) daoHeader.listGrid(batch_No, mode));
                }
            }
        }
        //setModelList((List<? extends Object>) daoHeader.listGrid(batch_No, mode));
        //getLogger().info("CHECK_LIST :" + getModelList().toString());
        return SUCCESS;
    }

    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doInsert() {
        SknngInOutGen2CreditHeaderDAO dao = new SknngInOutGen2CreditHeaderDAO(getHSession());
        String[] parrentIdArray = parentIds.split(",");
        //String batchNos = patchbankMast(batchNo);
        String batchNos = batchNo;
        //BatchNo.append(cdBranch).append(dateL).append(batchNo);
        for (int i = 0; i < parrentIdArray.length; i++) {
            String parentId = parrentIdArray[i];
            if (parentId != null && !"".equals(parentId.trim())) {
                List<HostSknngInOutCreditHeader> headers = dao.getData(batchNos, Integer.parseInt(parentId));

                for (HostSknngInOutCreditHeader header : headers) {
                    if (header != null) {
                        header.setExtractStatus("R");
                        header.setFlgReject("T");
                        header.setIdMaintainedBy(idMaintainedBy);
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

    @Override
    public String doUpdate() {
        SknngInOutGen2CreditHeaderDAO dao = new SknngInOutGen2CreditHeaderDAO(getHSession());
        Integer countT = 0;
        getLogger().info("PARENT : " + parentIds);
        getLogger().info("LOGGER : " + batchNo);
        if (parentIds.equalsIgnoreCase("T")) {
            StringTokenizer token = new StringTokenizer(parentIds, ",");
            countT = token.countTokens() + 1;
        }
        String batchNos = batchNo;
        if (Gen2Gen1Conversion(parentIds, batchNos, countT)) {
            return SUCCESS;
        } else {
            return ERROR;
        }
        /*
         */
//		AuditlogDao auditdao = new AuditlogDao(getHSession());
//		auditdao.insert(getModelHeader().getIdMaintainedBy(), getModelHeader().getIdMaintainedSpv(), "SKN Incoming Credit",
//				getNamMenu(), "Approve","Approve");

    }

    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); // To
    }

    public List<Map> biCoderefer(String batchNos, String mode) {
        List<Map> mapList = new ArrayList<Map>();
        TmpSknngInOutCreditDetailDAO dDao = new TmpSknngInOutCreditDetailDAO(getHSession());
        mapList = dDao.getQueryBICODE(batchNos, mode);
        return mapList;
    }

    public List<Map> datagetter(List<Map> mapper, TmpSknngInOutCreditHeader header) {
        List<Map> detailPM = new ArrayList();
        Integer countToken = 0;
        for (Map ipProxy : mapper) {
            if (ipProxy != null) {
                // get required only header.
                if (header.getCompositeId().getParentRecordId().toString().equalsIgnoreCase(ipProxy.get("PRI").toString())) {
                    detailPM.add(ipProxy);
                    // remove Index by recalculate presedence list
                    mapper.remove(countToken);
                } else {
                    countToken++;
                }
            }

        }
        return detailPM;
    }

    public boolean Gen2Gen1Conversion(String parentIds, String batchNo, Integer Total) {
        TmpSknngInOutCreditHeaderDAO dao = new TmpSknngInOutCreditHeaderDAO(getHSession());
        TmpSknngInOutCreditDetailDAO dDao = new TmpSknngInOutCreditDetailDAO(getHSession());
        TmpSknngInOutCreditFooterDAO fDaog2 = new TmpSknngInOutCreditFooterDAO(getHSession());
        BaBankMastDAO bankdao = new BaBankMastDAO(getHSession());
        
        BaBankMast bank = bankdao.get();
        Integer totalCount = 0;
        Integer rejectCount = 0;
        Integer successCount = 0;
        Integer succCOunt = 0;
        Integer rejCount = 0;
        Integer partial_Inc = 0;

        Integer noCOunt = 0;
        Integer countToken = 0;
        Integer counter = 0;
        Integer startcount = 1;
        // get all parent id and reformatting
        List<Integer> parrentIdArray = new ArrayList();
        counter = 0;
        
        long startTime = System.nanoTime();
        getLogger().info("START CONVERTING.. :" + startTime/1000000);
        List<TmpSknngInOutCreditHeader> fullHeader = new ArrayList<TmpSknngInOutCreditHeader>();
        List<TmpSknngInOutCreditDetail> fullDetails = new ArrayList<TmpSknngInOutCreditDetail>();

        if (APPROVEALL.equalsIgnoreCase(parentIds)) {
            getLogger().info("BEGIN CONVERTING..");
            List<TmpSknngInOutCreditHeader> idArray = dao.list(batchNo);
            for (TmpSknngInOutCreditHeader idHeader : idArray) {
                parrentIdArray.add(idHeader.getCompositeId().getParentRecordId());
            }
            getLogger().debug("SUCCESS GETTING ALL ID..");
            fullHeader = idArray;
            fullDetails = dDao.getQueryFullData(batchNo);
        } else {
            StringTokenizer token = new StringTokenizer(parentIds.toString(), ",");
            while (token.hasMoreTokens()) {
                countToken = token.countTokens();
                parrentIdArray.add(Integer.parseInt(token.nextElement().toString()));
                counter++;
            }
            fullHeader = dao.getFullData(batchNo, parrentIdArray);
            fullDetails = dDao.getFullListDetails(batchNo, parrentIdArray);
        }

        StringBuilder crcCheck = new StringBuilder();
        getLogger().debug("ARRAY : " + parrentIdArray);

        SKNIncomingCreditDetailsDao Gfirstdao = new SKNIncomingCreditDetailsDao(getHSession());

        Map maxIDmap;
        List maxID = Gfirstdao.getMaxId(batchNo);

        maxIDmap = (Map) maxID.get(0);
        Object lastMax = null;
        try {
            lastMax = maxIDmap.get("MAXRECORD");
            getLogger().debug("LASTmAX :" + lastMax);
            if (lastMax == null) {
                lastMax = 0;
            }
        } catch (Exception e) {
            getLogger().info("EXCEPTION :" + e);
            lastMax = 0;
        }
        Integer maxIDs = Integer.parseInt(lastMax.toString()) + 1;
        getLogger().info("CURRENT MAXID :" + maxIDs);

        getLogger().info("LIST HEADER :" + fullHeader.size());
        getLogger().info("LIST DETAIL :" + fullDetails.size());

        try {
            loadText();
            getLogger().debug("BATCH NO : " + batchNo);
           
            // get all available of sandi KOta dan BI Code refer or list (search)
            // List of Sandi Kota
			
			List<TmpSknngInOutCreditHeader> headerQuery = dao.getQueryHeader(batchNo);
            List<TmpSknngInOutCreditFooter> footerQuery = fDaog2.getQueryFooter(batchNo);
            List<Map> ipPengirim = biCoderefer(batchNo, "IPPengirim");
            List<Map> ipPengirimSKA = biCoderefer(batchNo, "IPPengirimSKA");
            List<Map> ipPenerima = biCoderefer(batchNo, "IPPenerima");
            List<Map> ipPenerimaSKT = biCoderefer(batchNo, "IPPenerimaSKT");
            List<Map> biCode = Gfirstdao.getCod_Bi(batchNo);

           // for (TmpSknngInOutCreditHeader header : fullHeader) {
                //List<TmpSknngInOutCreditDetail> detailBasedHeader = new ArrayList<TmpSknngInOutCreditDetail>();
                //getLogger().debug("HEADERPARENT :" + header.getCompositeId().getParentRecordId());
                // TUNING SKN
                // IP Pengirim 
                List<Map> detailIPP = ipPengirim; //datagetter(ipPengirim,header);
                List<Map> detailSKA = ipPengirimSKA; //,header);
                List<Map> detailIPA = ipPenerima; //,header);
                List<Map> detailSKT = ipPenerimaSKT; //,header);
                
                List<TmpSknngInOutCreditDetail> lData = dDao.getQueryDatabyParent(batchNo);
                noCOunt = lData.size();
                getLogger().debug("RECCOUNT :" + noCOunt);
                for (int i = 0; i < noCOunt;) {
                    //for (int i = 0; i < fullDetails.size();) {
                    TmpSknngInOutCreditDetail detail = lData.get(i);
					TmpSknngInOutCreditHeader headers = headerQuery.get(i);
                    //if (header.getCompositeId().getParentRecordId().compareTo(detail.getCompositeId().getParentRecordId()) == 0) {
                        //}
                        if (detail != null) {
                            StringBuilder Detailvalidation = new StringBuilder();
                            if (!"R".equalsIgnoreCase(detail.getFlgAuth())) {
                                SKNIncomingCreditPK modelPk = new SKNIncomingCreditPK(); // gen1
                                TmpSknngInOutCreditPK modelG2Pk = new TmpSknngInOutCreditPK();// gen2
                                TmpSknngInoutHFPK modelhPk = new TmpSknngInoutHFPK();
                                Pm_fin_inst_dir_mast modelPm = new Pm_fin_inst_dir_mast();

                                totalCount++;
                                modelG2Pk.setBatchNo(batchNo);
                                modelG2Pk.setParentRecordId(detail.getCompositeId().getParentRecordId());
                                modelG2Pk.setRecordId(detail.getCompositeId().getRecordId());
                                modelG2Pk.setRecordbyParent(detail.getCompositeId().getRecordbyParent());

                                modelhPk.setBatchNo(batchNo);
                                modelhPk.setParentRecordId(detail.getCompositeId().getParentRecordId());

                                SKNIncomingCreditDetailsDao G1dao = new SKNIncomingCreditDetailsDao(getHSession());
                                SKNIncomingCreditDetails detailsG1 = new SKNIncomingCreditDetails();

                                detail.setMessage(Detailvalidation.toString());
                                modelPk.setRecordId(maxIDs);
                                modelPk.setBatchNo(batchNo);
                                detailsG1.setPk(modelPk);

                                // get Value using LIst
                                // Pengirim BI_OFF_CODE
                                try {
                                    if("".equalsIgnoreCase(detailIPP.get(i).get("COD_BI_OFF").toString())){
                                        modelPm.setBi_Off_code(detailSKA.get(i).get("COD_BI_OFF").toString());
                                    } else {
                                        modelPm.setBi_Off_code(detailIPP.get(i).get("COD_BI_OFF").toString());
                                    }
                                } catch (Exception e) {
                                    getLogger().debug("BI_OFF_CODE EX :" + e,e);
                                    modelPm.setBi_Off_code(detailSKA.get(i).get("COD_BI_OFF").toString());
                                }
                                // Pengirim COD_FIN_INST
                                try {
                                    if("".equalsIgnoreCase(detailIPP.get(i).get("COD_FIN_INST").toString())){
                                        modelPm.setBi_Off_code(detailSKA.get(i).get("COD_FIN_INST").toString());
                                    } else {
                                        modelPm.setCod_fin_inst_id(detailIPP.get(i).get("COD_FIN_INST").toString());
                                    }                           
                                } catch (Exception e) {
                                    getLogger().debug("COD_FIN_INST EX :" + e,e);
                                    modelPm.setCod_fin_inst_id(detailIPP.get(i).get("COD_FIN_INST").toString());
                                }
                                // Penerima COD_PROVINCE
                                try {
                                    if("".equalsIgnoreCase(detailIPA.get(i).get("COD_PROVINCE").toString())){
                                        modelPm.setCod_province(detailSKT.get(i).get("COD_PROVINCE").toString());
                                    } else {
                                        modelPm.setCod_province(detailIPA.get(i).get("COD_PROVINCE").toString());
                                    }
                                } catch (Exception e) {
                                    getLogger().debug("COD_PROVINCE EX :" + e,e);
                                    modelPm.setCod_province(detailSKT.get(i).get("COD_PROVINCE").toString());
                                }
                                // Penerima COD_CITY
                                try {
                                    if("".equalsIgnoreCase(detailIPA.get(i).get("COD_CITY").toString())){
                                        modelPm.setCod_province(detailSKT.get(i).get("COD_CITY").toString());
                                    } else {
                                        modelPm.setCod_province(detailIPA.get(i).get("COD_CITY").toString());
                                    }
                                } catch (Exception e) {
                                    getLogger().debug("COD_CITY EX :" + e,e);
                                    modelPm.setCod_province(detailSKT.get(i).get("COD_CITY").toString());
                                }

                                try {
                                    modelPm.setCod_bank(biCode.get(i).get(detail.getIdentitasPesertaPenerimaAkhir()).toString());
                                } catch (Exception e) {
                                    getLogger().info("CODBANK : " + detail.getIdentitasPesertaPenerimaAkhir() + " NOT FOUND");
                                    modelPm.setCod_bank(BdsmUtil.leftPad(bank.getCodBankCentral(), 3, '0'));
                                }
                                /*try {
                                    modelPm.setBi_Off_code(G1dao.getAll_cod(header.getIdentitasPesertaPengirim(), detail.getSandiKotaAsal(), 1, FIRST));
                                } catch (Exception e) {
                                    modelPm.setBi_Off_code(G1dao.getAll_cod(header.getIdentitasPesertaPengirim(), detail.getSandiKotaAsal(), 1, LAST));
                                }
                                try {
                                    modelPm.setCod_fin_inst_id(G1dao.getAll_cod(header.getIdentitasPesertaPengirim(), detail.getSandiKotaAsal(), 4, FIRST));
                                } catch (Exception e) {
                                    modelPm.setCod_fin_inst_id(G1dao.getAll_cod(header.getIdentitasPesertaPengirim(), detail.getSandiKotaAsal(), 4, LAST));
                                }
                                try {
                                    modelPm.setCod_province(G1dao.getAll_cod(detail.getIdentitasPesertaPenerimaAkhir(), detail.getSandiKotaTujuan(), 2, FIRST));
                                } catch (Exception e) {
                                    modelPm.setCod_province(G1dao.getAll_cod(detail.getIdentitasPesertaPenerimaAkhir(), detail.getSandiKotaTujuan(), 2, LAST));
                                }
                                try {
                                    modelPm.setCod_city(G1dao.getAll_cod(detail.getIdentitasPesertaPenerimaAkhir(), detail.getSandiKotaTujuan(), 3, FIRST));
                                } catch (Exception e) {
                                    modelPm.setCod_city(G1dao.getAll_cod(detail.getIdentitasPesertaPenerimaAkhir(), detail.getSandiKotaTujuan(), 3, LAST));
                                }*/
                                //getLogger().debug("TOTAL COUNT PER-ACTION : " + totalCount);
                                try {
                                    detailsG1.setClearingDate((headers.getTanggalBatch()).substring(2, 10).replaceAll("-", "")); // header 4
                                    detailsG1.setParentRecid(detail.getCompositeId().getParentRecordId());
                                    detailsG1.setIdgen2(detail.getCompositeId().getRecordbyParent().toString());
                                    detailsG1.setReferenceNo(detail.getNomorReferensi());
                                    detailsG1.setRemitterName(detail.getSenderName());
                                    detailsG1.setBeneficiaryName(detail.getNamaNasabahPenerima());
                                    detailsG1.setBeneficiaryAccount(detail.getDestinationAccount());
                                    detailsG1.setRemarks(detail.getKeterangan());
                                    detailsG1.setAmount(detail.getNominal());
                                    detailsG1.setReceiverBankCode(paddingPM(modelPm.getCod_bank(), 0));
                                    detailsG1.setSenderBICode(paddingPM(modelPm.getBi_Off_code(), 1));
                                    detailsG1.setSenderSknCoordinatorCode(detail.getSandiKotaAsal());

                                    /*
                                     * If from bulk-to-individual-conversion
                                     * transaction
                                     */
                                    if ((detail.getJenisTransaksi().trim().length() == 2) && (detail.getJenisTransaksi().trim().charAt(0) == '7')) {
                                        detailsG1.setTransactionCode(detail.getJenisTransaksi().replaceFirst("7\\d{1}", "50"));
                                    } else {
                                        detailsG1.setTransactionCode(detail.getJenisTransaksi());
                                    }

                                    String bankType = SknNgService.BIC_SYARIAH.equalsIgnoreCase(detail.getIdentitasPesertaPenerimaAkhir()) ? "2" : "1";
                                    detailsG1.setBankType(bankType);

                                    // convert Value 2 to 0
                                    String residence = "1";
                                    if ("2".equals(detail.getStatusPendudukNasabahPengirim())) {
                                        residence = "2";
                                    }
                                    detailsG1.setRemitterResidencyStatus(residence);
                                    detailsG1.setRemitterCitizenshipStatus("0");
                                    detailsG1.setBeneficiaryProvinceCode(paddingPM(modelPm.getCod_province(), 2));
                                    detailsG1.setBeneficiaryCityCode(paddingPM(modelPm.getCod_city(), 3));
                                    detailsG1.setReceiverSknCoordinatorCode(detail.getSandiKotaTujuan());
                                    detailsG1.setSenderClearingCode(paddingPM(modelPm.getCod_fin_inst_id(), 4));
                                    detailsG1.setSor((detail.getSORToFCR() == null) ? detail.getSor() : detail.getSORToFCR());
                                    detailsG1.setStatusExtract("Y");
                                    detailsG1.setLength(crcCheck.toString().length());
                                } catch (NumberFormatException numberFormatException) {
                                    getLogger().info("TEST NULL : " + numberFormatException);
                                }
                                String crecTsring = null;
                                crcCheck.append(detailsG1.getClearingDate());
                                crcCheck.append(detailsG1.getReferenceNo());
                                crcCheck.append(detailsG1.getRemitterName());
                                crcCheck.append(detailsG1.getBeneficiaryName());
                                crcCheck.append(detailsG1.getBeneficiaryAccount());
                                crcCheck.append(detailsG1.getRemarks());
                                crcCheck.append(detailsG1.getAmount().toString());
                                crcCheck.append(detailsG1.getReceiverBankCode());
                                crcCheck.append(detailsG1.getSenderBICode());
                                crcCheck.append(detailsG1.getSenderSknCoordinatorCode());
                                crcCheck.append(detailsG1.getTransactionCode());
                                crcCheck.append("1");
                                crcCheck.append(detailsG1.getRemitterResidencyStatus());
                                crcCheck.append("0");
                                crcCheck.append(detailsG1.getBeneficiaryProvinceCode());
                                crcCheck.append(detailsG1.getBeneficiaryCityCode());
                                crcCheck.append(detailsG1.getReceiverSknCoordinatorCode());
                                crcCheck.append(detailsG1.getSenderClearingCode());
                                crcCheck.append(detailsG1.getSor());
                                crecTsring = crcCheck.toString();
                                detailsG1.setLength(crecTsring.length());
                                detailsG1.setjData(crecTsring);
                                crcCheck.delete(0, crcCheck.toString().length());
                                try {
                                    G1dao.insert(detailsG1);
                                } catch (Exception e) {
                                    getLogger().info("FAIL TO INSERT :" + e);
                                }
                                if (!org.apache.commons.lang3.StringUtils.isBlank(detail.getMessage())) {
                                    rejectCount++;
                                } else {
                                    successCount++;
                                }
                                maxIDs++;
                                //fullDetails.remove(i);
                            }
                        }
                    //}
                    i++;


                    if (rejectCount == null) {
                        rejectCount = 0;
                    }
                try {
                    getLogger().debug("BATCH :" + batchNo);
                    getLogger().debug("PARENT :" + headers.getCompositeId().getParentRecordId());
                    
                    TmpSknngInOutCreditFooter getFooter = null;
                    try {
                        getFooter = footerQuery.get(i);
                            if(headers.getCompositeId().getParentRecordId().compareTo(getFooter.getCompositeId().getParentRecordId()) == 0){
                            // if match
                            succCOunt = successCount-1;
                            rejCount = rejectCount;
                            partial_Inc = i;
                        } else {
                        // not match (Already increment)
                            if(partial_Inc.compareTo(i)==0){
                            getFooter.setTreject(rejCount);
                            getFooter.setTsuccess(succCOunt);
                            fDaog2.update(getFooter); 
                            }
                        }
                    } catch (Exception e) {
                        // Index of outbound 
                        getLogger().debug("INDEX_OUT_OFBOUND :" + e,e);
                        getFooter = footerQuery.get(i-1);
                        getFooter.setTreject(rejCount);
                            getFooter.setTsuccess(succCOunt);
                            fDaog2.update(getFooter);
                    }
                    //getLogger().info("LOGGER :" + rejectCount + " " + successCount);
                    
                    
                } catch (Exception e) {
                    getLogger().debug("FAILED UPDATE LOGGER :" + rejectCount + " " + successCount);
                }
                }
                
            //}

            getLogger().info("GENERATE FOOTER: " + batchNo);
            SknNgIncomingCreditFPK modelCPK = new SknNgIncomingCreditFPK();
            getLogger().debug("BATCH ID :" + batchNo);
            modelCPK.setBatchNo(batchNo);
            SKNIncomingCreditFooterDao fDao = new SKNIncomingCreditFooterDao(getHSession());
            SKNIncomingCreditFooter footer = null;
            try {
                footer = fDao.get(modelCPK);
            } catch (Exception e) {
                getLogger().info("EXCEPTION FOOTER : " + e, e);
                footer = null;
            }
            getLogger().debug("TEST OBJETC");
            getLogger().debug("OBJECT DAO : " + footer);
            Integer CRC;
            //getLogger().debug("CRC LAST :" + crcCheck);

            if (footer == null) {
                footer = new SKNIncomingCreditFooter();
                //CRC = Integer.valueOf(CRC16.CRC16(crcCheck.toString(), 0));
                footer.setCheckSum("0");
                footer.setCompositeId(modelCPK);
                footer.setTotalRec(totalCount);
                footer.setRecordType("I");
                footer.setRecordName("FOOTER");
                fDao.insert(footer);
            }

            for (TmpSknngInOutCreditHeader header : fullHeader) {
                if (header != null) {
                    header.setExtractStatus("A");
                    header.setFlgReject("F");
                    header.setIdMaintainedBy(idMaintainedBy);
                    dao.update(header);
                }
            }
            
            long endTime = System.nanoTime();
            getLogger().info("END CONVERTING.. :" + endTime/1000000);
            getLogger().info("DURATION :" + (endTime-startTime));
        } catch (Exception exception) {
            getLogger().info("GETPARAM FAILED :" + exception, exception);
            return false;
        }
        return true;
    }

    private void loadText() throws Exception {
        Properties properties = new Properties();
        InputStream in = SknNgOutwardSMODebitAction.class.getClassLoader().getResourceAsStream(FORMAT_G2_FILENAME);
        properties.load(in);
        ListLength = new ArrayList();
        ListPad = new ArrayList();
        ListAdd = new ArrayList();
        List g1List = new ArrayList();
        g1List.add(properties.getProperty("modelPmCodBank"));
        g1List.add(properties.getProperty("modelPmBiOffCode"));
        g1List.add(properties.getProperty("modelPmCodProv"));
        g1List.add(properties.getProperty("modelPmCodCity"));
        g1List.add(properties.getProperty("modelPmCodFin"));

        for (int i = 0; i < g1List.size(); i++) {
            StringTokenizer tokens = new StringTokenizer(g1List.get(i).toString(), ":");
            while (tokens.hasMoreTokens()) {
                ListLength.add(tokens.nextToken());
                ListAdd.add(tokens.nextToken());
                ListPad.add(tokens.nextToken());
            }
        }
        in.close();
    }

    private String paddingPM(String modelpm, Integer seq) {
        String multipadding = null;
        if (ListPad.get(seq).toString().equalsIgnoreCase("L")) {
            multipadding = BdsmUtil.leftPad(modelpm, Integer.parseInt(ListLength.get(seq).toString()), ListAdd.get(seq).toString().charAt(0));
        } else {
            multipadding = BdsmUtil.rightPad(modelpm, Integer.parseInt(ListLength.get(seq).toString()), ListAdd.get(seq).toString().charAt(0));
        }
        return multipadding;
    }

    public String patchbankMast(String Batch) {
        SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);

        StringBuilder BatchNo = new StringBuilder();

        FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(getHSession());
        String FCRDay = sdf.format(fcrDao.get().getDatProcess());

        String Awal = Batch.substring(0, Batch.length() - 6);
        String Branch = Awal.substring(0, 5);
        String Days = Awal.substring(Awal.length() - 6, Awal.length()); // days
        String Clock = Batch.substring(Batch.length() - 6, Batch.length());// hour

        String userBatchno = BatchNo.append(Branch).append(FCRDay).append(Clock).toString();
        getLogger().info("FIRST BATCH :" + userBatchno);
        return userBatchno;
    }

    public String getIdMaintainedBy() {
        return idMaintainedBy;
    }

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
     * @return the modelPk
     */
    public TmpSknngInOutCreditPK getModelPk() {
        return modelPk;
    }

    /**
     * @param modelPk the modelPk to set
     */
    public void setModelPk(TmpSknngInOutCreditPK modelPk) {
        this.modelPk = modelPk;
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

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getBatchNo() {
        return batchNo;
    }

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
     * @return the nameMenu
     */
    public String getNameMenu() {
        return nameMenu;
    }

    /**
     * @param nameMenu the nameMenu to set
     */
    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }
}
