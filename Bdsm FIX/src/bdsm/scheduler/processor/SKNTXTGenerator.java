/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.SKNIncomingCreditDetailsDao;
import bdsm.scheduler.dao.SKNIncomingCreditFooterDao;
import bdsm.scheduler.dao.TmpSknngInOutCreditDetailDAO;
import bdsm.scheduler.model.SKNIncomingCreditDetails;
import bdsm.scheduler.model.SKNIncomingCreditFooter;
import bdsm.scheduler.model.SknNgIncomingCreditFPK;
import bdsm.scheduler.util.FileUtil;
import bdsm.util.BdsmUtil;
import bdsm.util.CRC16;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author bdsm
 */
public class SKNTXTGenerator extends SKNOptGenerator {

    private String clearingDate;
    private HashMap textSetting;
    private static String FORMAT_G1_FILENAME_HEADER = "skn_gen1_credit.properties";

    public SKNTXTGenerator(Map context) {
        super(context);
    }

    @Override
    public boolean validate(Map context) {
        // check code branch input and template
        SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
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
        FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
        try {
            dateTime = sdf.format(fcrDao.get().getDatProcess()).toString();
            //dateTime  = SchedulerUtil.getDate("yyMMdd");
        } catch (Exception ex) {
            getLogger().error("CANT get DATE :" + ex, ex);
        }
        StringBuilder batchNo = new StringBuilder();
        batchNo.append(BdsmUtil.leftPad(context.get(MapKey.hdcdBranch).toString(), 5, '0')).append(dateTime).append(context.get(MapKey.param6));
        getLogger().info("BATCH NO :" + batchNo);
        BatchID = batchNo.toString();
        try {
            clearingDate = context.get(MapKey.param2).toString();
        } catch (Exception e) {
            clearingDate = dateTime;
        }
        return true;
    }


    @Override
    public StringBuilder detailS() {
        getLogger().debug("(SKN)DETAIL ID_BATCH reps Details :" + BatchID);
        SKNIncomingCreditDetailsDao detailDao = new SKNIncomingCreditDetailsDao(session);
        TmpSknngInOutCreditDetailDAO detailGen2Dao = new TmpSknngInOutCreditDetailDAO(session);

        SKNIncomingCreditFooterDao footerDao = new SKNIncomingCreditFooterDao(session);

        SKNIncomingCreditFooter footer = new SKNIncomingCreditFooter();
        SknNgIncomingCreditFPK pk = new SknNgIncomingCreditFPK();
        pk.setBatchNo(BatchID);
        String buffer = null;
        StringBuilder sbDetails = new StringBuilder();
        String Dates = null;
        String filterAmount = "";
        Integer checkSum = 0;
        Integer loopings = 0;
        
        StringWriter write = new StringWriter();
        //detailGen2Dao.get(null);
        try {
            List<SKNIncomingCreditDetails> d = new ArrayList();
            List<SKNIncomingCreditFooter> f = new ArrayList();
            List<String> s = new ArrayList();
            StringBuilder AmountToString = new StringBuilder();
            StringTokenizer tokenFilter = null;
            StringTokenizer tokenSAmount = null;
            try {
                d = detailDao.getDetails(BatchID); // get all data;
                loadTextSetting();
                for (SKNIncomingCreditDetails restructure : d) {
                    if (AmountToString != null) {
                        AmountToString.delete(0, AmountToString.length());
                    }

                    sbDetails.append(nullTo(restructure.getClearingDate(), "ClearingDate"));
                    sbDetails.append(nullTo(restructure.getReferenceNo(), "ReferenceNo"));
                    sbDetails.append(nullTo(restructure.getRemitterName(), "SenderName"));
                    sbDetails.append(nullTo(restructure.getBeneficiaryName(), "BeneficiaryName"));
                    sbDetails.append(nullTo(restructure.getBeneficiaryAccount(), "BeneficiaryAccountNo"));
                    sbDetails.append(nullTo(restructure.getRemarks(), "Remarks"));
                    filterAmount = nullTo(restructure.getAmount(), "Amount");
                    tokenFilter = new StringTokenizer(filterAmount, ".");

                    AmountToString.append(tokenFilter.nextToken());
                    AmountToString.append(tokenFilter.nextToken());
                    tokenSAmount = new StringTokenizer(textSetting.get("Amount").toString(), ":");

                    sbDetails.append(BdsmUtil.leftPad(AmountToString.toString(), Integer.parseInt(tokenSAmount.nextToken()), '0'));
                    sbDetails.append(nullTo(restructure.getReceiverBankCode(), "BeneficiaryBankCode")).append(nullTo(restructure.getSenderBICode(), "SenderBIOfficeCode"));
                    sbDetails.append(nullTo(restructure.getSenderSknCoordinatorCode(), "SenderSectorCode")).append(nullTo(restructure.getTransactionCode(), "TransactionCode")).append(nullTo(restructure.getBankType(), "BankType"));
                    sbDetails.append(nullTo(restructure.getRemitterResidencyStatus(), "SenderResidencyStatus")).append(nullTo(restructure.getRemitterCitizenshipStatus(), "SenderCitizenshipStatus")).append(nullTo(restructure.getBeneficiaryProvinceCode(), "BeneficiaryProvinceCode"));
                    sbDetails.append(nullTo(restructure.getBeneficiaryCityCode(), "BeneficiaryCityCode")).append(nullTo(restructure.getReceiverSknCoordinatorCode(), "BeneficiarySectorCode")).append(nullTo(restructure.getSenderClearingCode(), "SenderRoutingCode"));
                    sbDetails.append(nullTo(restructure.getSor(), "SOR"));
                    sbDetails.append(System.getProperty("line.separator"));
                    CrcCheck.append(sbDetails);
                    buffer = CrcCheck.toString();
                    write.write(buffer);
                    write.flush();
                    FileUtil.writeWithFileChannerDMA(write.getBuffer().toString(), PropertyPersister.dirFixOut + outputFile);   
                    s.add(buffer);
                    sbDetails.delete(0, sbDetails.length());
                    //out.close();
                    write.getBuffer().delete(0, write.getBuffer().length());
                    
                    d.remove(loopings);
                    loopings++;
                }
                CrcCheck.delete(0, CrcCheck.length());    
                try {
                    getLogger().debug("BEFORE PARSING FOOTER SKN_GEN1 :" + d.size());
                    try {
                        try {
                            f = footerDao.getModel(BatchID);
                            getLogger().debug("CRC C :" + CrcCheck);
                            try {
                                Iterator<String> iterator = s.iterator();
                                while(iterator.hasNext()){
                                CrcCheck.append(iterator.next());
                                }
                                checkSum = CRC16.CRC16(CrcCheck.toString(), 0);
                                if (checkSum.toString().equalsIgnoreCase(f.get(0).getCheckSum())) {
                                    sbDetails.append(f.get(0).getCheckSum());
                                } else {
                                    try {
                                        sbDetails.append(StringUtils.leftPad(checkSum.toString(), 5, '0'));
                                        footer.setCompositeId(pk);
                                        footer.setCheckSum(checkSum.toString());
                                        footerDao.update(footer);
                                    } catch (Exception e) {
                                         getLogger().debug("APPEND failed :" + e, e);
                                    }        
                                }
                                write.write(sbDetails.toString());
                                write.flush();
                                FileUtil.writeWithFileChannerDMA(write.getBuffer().toString(), PropertyPersister.dirFixOut + outputFile);   
      
                            } catch (Exception e) {
                                getLogger().debug("CRC FAILED failed :" + e, e);
                            }
                        } catch (Exception e) {
                            getLogger().debug("get Footer failed :" + e, e);
                        }
                        //getLogger().info(d);
                    } catch (Exception e) {
                        getLogger().info("GET ERROR FOOTER:" + e, e);
                    }
                    // recalculate checkSum
                } catch (Exception e) {
                    getLogger().error("ERROR PARSING FOOTER SKN_GEN1:" + e, e);
                }

            } catch (Exception e) {
                getLogger().error("ERROR AFTER PARSING DETAIL SKN_GEN1:" + e, e);
            }
        } catch (Exception e) {
            getLogger().error("ERROR BEFORE PARSING DETAIL SKN_GEN1:" + e, e);
        } finally {
            try {
                write.close();
            } catch (IOException ex) {
                getLogger().error("CAN'T CLOSE:" + ex, ex);
            }
        }
       
        return sbDetails;
    }

    private String nullTo(String tonull, String Mapping) {

        String nullval = "";
        String prop = "";
        String padding = "";
        String[] getProp = new String[5];
        Integer countToken = 0;
        Integer countReal = 0;
        HashMap textLength = textSetting;
        //getLogger().info("Mapping :" + Mapping);
        StringTokenizer token = new StringTokenizer(textLength.get(Mapping).toString(), ":");
        //getLogger().info("CHECKcOUNT :" + token.countTokens());
        while (token.hasMoreTokens()) {
            countToken = token.countTokens();
            getProp[countReal] = token.nextToken().toString();
            countReal++;
            //getLogger().info("CHECK TOKEN :" + getProp[countToken]);
        }
        int maxleng;
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
                String trimmer = (tonull).trim();
                if (trimmer.length() < maxleng) {
                    if ("L".equals(getProp[1])) {
                        if ("S".equals(getProp[2])) {
                            nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), ' ');
                        } else {
                            nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), '0');
                        }
                    } else {
                        if ("S".equals(getProp[2])) {
                            nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), ' ');
                        } else {
                            nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), '0');
                        }
                    }
                } else if (trimmer.length() == maxleng) {
                    nullval = tonull;
                } else {
                    nullval = trimmer.substring(0, maxleng);
                }
                //getLogger().info("samesize :" + tonull.length());
            } else if (tonull.length() < maxleng) {
                StringBuilder sb = new StringBuilder();
                //getLogger().info("CHECK INDEX : ");
                prop = getProp[1];

                if ("L".equals(getProp[1])) {
                    if ("S".equals(getProp[2])) {
                        nullval = BdsmUtil.rightPad(tonull, Integer.parseInt(getProp[0]), ' ');
                    } else {
                        nullval = BdsmUtil.rightPad(tonull, Integer.parseInt(getProp[0]), '0');
                    }
                } else {
                    if ("S".equals(getProp[2])) {
                        nullval = BdsmUtil.leftPad(tonull, Integer.parseInt(getProp[0]), ' ');
                    } else {
                        nullval = BdsmUtil.leftPad(tonull, Integer.parseInt(getProp[0]), '0');
                    }
                }
                //getLogger().info("CHECK VALUE :" + nullval);
            } else if (tonull.length() > maxleng) {
                //getLogger().info("more :" + tonull.length());
                try {
                    String trimmer = (tonull).trim();
                    if (trimmer.length() < maxleng) {
                        if ("L".equals(getProp[1])) {
                            if ("S".equals(getProp[2])) {
                                nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), ' ');
                            } else {
                                nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), '0');
                            }
                        } else {
                            if ("S".equals(getProp[2])) {
                                nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), ' ');
                            } else {
                                nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), '0');
                            }
                        }
                    } else {
                        nullval = trimmer.substring(0, maxleng);
                    }
                    //getLogger().info("OVERFLOW :" + tonull);
                } catch (Exception e) {
                    //getLogger().info("INDEX BOUND :" + e.getLocalizedMessage());
                }
            }

        }
        return nullval;
    }

    private void loadTextSetting() throws Exception {
        Properties properties = new Properties();
        InputStream in = SKNTXTmapping.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME_HEADER);
        properties.load(in);
        textSetting = new HashMap();
        textSetting.put("ClearingDate", properties.getProperty("ClearingDate"));
        textSetting.put("ReferenceNo", properties.getProperty("ReferenceNo"));
        textSetting.put("SenderName", properties.getProperty("SenderName"));
        textSetting.put("BeneficiaryName", properties.getProperty("BeneficiaryName"));
        textSetting.put("BeneficiaryAccountNo", properties.getProperty("BeneficiaryAccountNo"));
        textSetting.put("Remarks", properties.getProperty("Remarks"));
        textSetting.put("Amount", properties.getProperty("Amount"));
        textSetting.put("BeneficiaryBankCode", properties.getProperty("BeneficiaryBankCode"));
        textSetting.put("SenderBIOfficeCode", properties.getProperty("SenderBIOfficeCode"));
        textSetting.put("SenderSectorCode", properties.getProperty("SenderSectorCode"));
        textSetting.put("TransactionCode", properties.getProperty("TransactionCode"));
        textSetting.put("BankType", properties.getProperty("BankType"));
        textSetting.put("SenderResidencyStatus", properties.getProperty("SenderResidencyStatus"));
        textSetting.put("SenderCitizenshipStatus", properties.getProperty("SenderCitizenshipStatus"));
        textSetting.put("BeneficiaryProvinceCode", properties.getProperty("BeneficiaryProvinceCode"));
        textSetting.put("BeneficiaryCityCode", properties.getProperty("BeneficiaryCityCode"));
        textSetting.put("BeneficiarySectorCode", properties.getProperty("BeneficiarySectorCode"));
        textSetting.put("SenderRoutingCode", properties.getProperty("SenderRoutingCode"));
        textSetting.put("SOR", properties.getProperty("SOR"));
        in.close();
    }

}
