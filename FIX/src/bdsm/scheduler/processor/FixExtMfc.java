/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import org.apache.commons.lang.StringUtils;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.ExtMfcDetailsDao;
import bdsm.scheduler.dao.ExtMfcHeaderDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.ExtMfcDetails;
import bdsm.scheduler.model.ExtMfcHeader;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.FcrBaCcyCodeDao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author v00013493
 */
public class FixExtMfc extends BaseProcessor {
    final int MAX_ROW_DETAILS = 100000;
    
    /**
     * 
     * @param context
     */
    public FixExtMfc(Map context) {
        super(context);
        getLogger().info("Class Instantiated");
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    protected boolean doExecute() throws Exception {
        String BatchID = context.get(MapKey.batchNo).toString();
        String status = context.get(MapKey.status).toString();
        String lineHeader;
        String lineDetails;
        Scanner headerScan;
        String delimiter = "\\|";
        String recTypeHeader = null;
        String srcSystem = null;
        String noBatch = null;
        String recCount = null;
        boolean hasFooter = false;
        int detailsCount = 0;
        String respCodeHeader = null;
        StringBuilder commentHeader = new StringBuilder();

        if (status.equals(StatusDefinition.UNAUTHORIZED)) {
            BufferedReader br = null;
            String param5 = context.get(MapKey.param5).toString();
            
            try {
                ExtMfcHeaderDao headerDao = new ExtMfcHeaderDao(session);
                br = new BufferedReader(new FileReader(param5));
                lineHeader = br.readLine();
                headerScan = new Scanner(lineHeader);
                headerScan.useDelimiter(delimiter);
                
                try {
                    recTypeHeader = headerScan.next();
                    srcSystem = headerScan.next();
                    noBatch = headerScan.next();
                    recCount = headerScan.next();
                } catch(NoSuchElementException e) {

                } finally {
                    if(headerScan != null) {
                        headerScan.close();
                    }
                }
                
                // count number of details record
                while((lineDetails = br.readLine()) != null) {
                    if(lineDetails.trim().equals("9")) {
                        hasFooter = true;
                        break;
                    } else if(lineDetails.trim().equals("")) {
                        
                    } else {
                        detailsCount++;
                    }
                }
                
                // header validation
                if(!headerDao.get(noBatch).isEmpty()) {
                    if(respCodeHeader == null) {
                        respCodeHeader = "2";
                    }
                    commentHeader.append("Duplicate noBatch : ").append(noBatch).append("; ");
                }
                
                try {
                    if(Integer.parseInt(recCount) != detailsCount) {
                        if(respCodeHeader == null) {
                            respCodeHeader = "3";
                        }
                        commentHeader.append("Invalid RecCount in header : ").append(recCount);
                        commentHeader.append("  while RecCount in details : ").append(detailsCount).append("; ");
                    }
                } catch(NullPointerException e) {
                    if(respCodeHeader == null) {
                        respCodeHeader = "3";
                    }
                    commentHeader.append("Invalid RecCount : ").append(recCount).append("; ");
                } catch(NumberFormatException e) {
                    if(respCodeHeader == null) {
                        respCodeHeader = "3";
                    }
                    commentHeader.append("Invalid RecCount : ").append(recCount).append("; ");
                }

                if(!hasFooter) {
                    if(respCodeHeader == null) {
                        respCodeHeader = "4";
                    }
                    commentHeader.append("No footer in file");
                }
                
                if(detailsCount > MAX_ROW_DETAILS) {
                    if(respCodeHeader == null) {
                        respCodeHeader = "5";
                    }
                    commentHeader.append("Can't process more than ").append(MAX_ROW_DETAILS).append(" records");
                }
                    
                ExtMfcHeader header = new ExtMfcHeader();
                header.setBatchId(BatchID);
                header.setRecType(recTypeHeader);
                header.setSrcSystem(srcSystem);
                header.setNoBatch(noBatch);
                header.setRecCount(recCount);
                header.setData(lineHeader);
                header.setRespCode(respCodeHeader == null ? "0" : respCodeHeader);
                header.setComments(respCodeHeader == null ? "Successfully processed" : commentHeader.toString());
                headerDao.insert(header);
            } catch(Exception e) {
                return false;
            } finally {
                if(br != null) {                
                    br.close();
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Scanner detailsScan;
            String recTypeDetails = null;
            String noCif = null;
            String noAcct = null;
            String refTxn = null;
            String typMsg = null;
            String typAcct = null;
            String dtPost = null;
            String dtValue = null;
            String dtmTxn = null;
            String ccyTxn = null;
            String amtTxn = null;
            String ratFcyIdr = null;
            String amtTxnLcy = null;
            String txnDesc = null;
            String txnBranch = null;
            String noUd = null;
            String udLmtAmt = null;
            String udDtExp = null;
            String respCodeDetails = null;
            StringBuilder commentDetails = new StringBuilder();
            ExtMfcDetails details;

            try {
                br = new BufferedReader(new FileReader(param5));
                br.readLine();
                ExtMfcDetailsDao detailDao = new ExtMfcDetailsDao(session);
                FcrBaCcyCodeDao fcrBaCcyCodeDao = new FcrBaCcyCodeDao(session);
                List listCurr = fcrBaCcyCodeDao.list();
                detailsCount = 0;

                while((lineDetails = br.readLine()) != null) {
                    if(lineDetails.trim().equals("9")) {
                        break;
                    } else if(lineDetails.trim().equals("")) {

                    } else {
                        detailsCount++;
                        detailsScan = new Scanner(lineDetails);
                        detailsScan.useDelimiter(delimiter);
                        try {
                            recTypeDetails = detailsScan.next();
                            noCif = detailsScan.next();
                            noAcct = detailsScan.next();
                            refTxn = detailsScan.next();
                            typMsg = detailsScan.next();
                            typAcct = detailsScan.next();
                            dtPost = detailsScan.next();
                            dtValue = detailsScan.next();
                            dtmTxn = detailsScan.next();
                            ccyTxn = detailsScan.next();
                            amtTxn = detailsScan.next();
                            ratFcyIdr = detailsScan.next();
                            amtTxnLcy = detailsScan.next();
                            txnDesc = detailsScan.next();
                            txnBranch = detailsScan.next();
                            noUd = detailsScan.next();
                            udLmtAmt = detailsScan.next();
                            udDtExp = detailsScan.next();
                        } catch(NoSuchElementException e) {

                        } finally {
                            if(detailsScan != null) {
                                detailsScan.close();
                            }
                        }

                        // simple validation on details
                        try {
                            if(!typMsg.toUpperCase().equals("O") && !typMsg.toUpperCase().equals("R")) {
                                if(respCodeDetails == null) {
                                    respCodeDetails = "2";
                                }
                                commentDetails.append("Invalid TypMsg : ").append(typMsg).append("; ");
                            }
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "2";
                            }
                            commentDetails.append("Invalid TypMsg : ").append(typMsg).append("; ");
                        }

                        try {
                            if(!typAcct.toUpperCase().equals("C") && !typAcct.toUpperCase().equals("T")) {
                                if(respCodeDetails == null) {
                                    respCodeDetails = "3";
                                }
                                commentDetails.append("Invalid TypAcct : ").append(typAcct).append("; ");
                            }
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "3";
                            }
                            commentDetails.append("Invalid TypAcct : ").append(typAcct).append("; ");
                        }

                        sdf.applyPattern("yyyy-MM-dd");
                        try {
                            sdf.parse(dtPost);
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "5";
                            }
                            commentDetails.append("Invalid DtPost : ").append(dtPost).append("; ");
                        } catch(ParseException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "5";
                            }
                            commentDetails.append("Invalid DtPost : ").append(dtPost).append("; ");
                        }

                        try {
                            sdf.parse(dtValue);
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "5";
                            }
                            commentDetails.append("Invalid DtValue : ").append(dtValue).append("; ");
                        } catch(ParseException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "5";
                            }
                            commentDetails.append("Invalid DtValue : ").append(dtValue).append("; ");
                        }

                        sdf.applyPattern("yyyy-MM-dd hh:mm:ss");
                        try {
                            sdf.parse(dtmTxn);
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "5";
                            }
                            commentDetails.append("Invalid DtmTxn : ").append(dtmTxn).append("; ");
                        } catch(ParseException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "5";
                            }
                            commentDetails.append("Invalid DtmTxn : ").append(dtmTxn).append("; ");
                        }

                        if(!checkCurr(listCurr, ccyTxn)) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "6";
                            }
                            commentDetails.append("Invalid CcyTxn : ").append(ccyTxn).append("; ");
                        }

                        try {
                            Double.parseDouble(amtTxn);
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid AmtTxn : ").append(amtTxn).append("; ");
                        } catch(NumberFormatException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid AmtTxn : ").append(amtTxn).append("; ");
                        }

                        try {
                            Double.parseDouble(ratFcyIdr);
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid RatFcyIdr : ").append(ratFcyIdr).append("; ");
                        } catch(NumberFormatException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid RatFcyIdr : ").append(ratFcyIdr).append("; ");
                        }

                        try {
                            Double.parseDouble(amtTxnLcy);
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid AmtTxnLcy : ").append(amtTxnLcy).append("; ");
                        } catch(NumberFormatException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid AmtTxnLcy : ").append(amtTxnLcy).append("; ");
                        }

                        try {
                            Integer.parseInt(txnBranch);
                        } catch(NullPointerException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid TxnBranch : ").append(txnBranch).append("; ");
                        } catch(NumberFormatException e) {
                            if(respCodeDetails == null) {
                                respCodeDetails = "7";
                            }
                            commentDetails.append("Invalid TxnBranch : ").append(txnBranch).append("; ");
                        }

                        //if(noUd != null && !noUd.equalsIgnoreCase("UD DUMMY")) {
						if(StringUtils.isNotBlank(noUd) && !noUd.equalsIgnoreCase("UD DUMMY")) {
                            try {
                                Double.parseDouble(udLmtAmt);
                            } catch(NullPointerException e) {
                                if(respCodeDetails == null) {
                                    respCodeDetails = "7";
                                }
                                commentDetails.append("Invalid UdLmtAmt : ").append(udLmtAmt).append("; ");
                            } catch(NumberFormatException e) {
                                if(respCodeDetails == null) {
                                    respCodeDetails = "7";
                                }
                                commentDetails.append("Invalid UdLmtAmt : ").append(udLmtAmt).append("; ");
                            }

                            try {
                                sdf.applyPattern("yyyy-MM-dd");
                                sdf.parse(udDtExp);
                            } catch(NullPointerException e) {
                                if(respCodeDetails == null) {
                                    respCodeDetails = "5";
                                }
                                commentDetails.append("Invalid UdDtExp : ").append(udDtExp).append("; ");
                            } catch(ParseException e) {
                                if(respCodeDetails == null) {
                                    respCodeDetails = "5";
                                }
                                commentDetails.append("Invalid UdDtExp : ").append(udDtExp).append("; ");
                            }
                            if(udLmtAmt != null) {
                                try {
                                    Double.parseDouble(udLmtAmt);
                                } catch(NumberFormatException e) {
                                    if(respCodeDetails == null) {
                                        respCodeDetails = "7";
                                    }
                                    commentDetails.append("Invalid UdLmtAmt : ").append(udLmtAmt).append("; ");
                                }
                            }

                            if(udDtExp != null) {
                                try {
                                    sdf.applyPattern("yyyy-MM-dd");
                                    sdf.parse(udDtExp);
                                } catch(ParseException e) {
                                    if(respCodeDetails == null) {
                                        respCodeDetails = "5";
                                    }
                                    commentDetails.append("Invalid UdDtExp : ").append(udDtExp).append("; ");
                                }
                            }
                        }

                        details = new ExtMfcDetails();
                        details.setBatchId(BatchID);
                        details.setRecType(recTypeDetails);
                        details.setNoCif(noCif);
                        details.setNoAcct(noAcct);
                        details.setRefTxn(refTxn);
                        details.setTypMsg(typMsg);
                        details.setTypAcct(typAcct);
                        details.setDtPost(dtPost);
                        details.setDtValue(dtValue);
                        details.setDtmTxn(dtmTxn);
                        details.setCcyTxn(ccyTxn);
                        details.setAmtTxn(amtTxn);
                        details.setRatFcyIdr(ratFcyIdr);
                        details.setAmtTxnLcy(amtTxnLcy);
                        details.setTxnDesc(txnDesc);
                        details.setTxnBranch(txnBranch);
                        details.setAmtTxnLcy(amtTxnLcy);
                        details.setNoUd(noUd);
                        details.setUdLmtAmt(udLmtAmt);
                        details.setUdDtExp(udDtExp);
                        details.setRespCode(respCodeDetails);
                        details.setComments(commentDetails.toString());
                        details.setData(lineDetails);
                        detailDao.insert(details);

                        recTypeDetails = null;
                        noCif = null;
                        noAcct = null;
                        refTxn = null;
                        typMsg = null;
                        typAcct = null;
                        dtPost = null;
                        dtValue = null;
                        dtmTxn = null;
                        ccyTxn = null;
                        amtTxn = null;
                        ratFcyIdr = null;
                        amtTxnLcy = null;
                        txnDesc = null;
                        txnBranch = null;
                        noUd = null;
                        udLmtAmt = null;
                        udDtExp = null;
                        respCodeDetails = null;
                        commentDetails.delete(0, commentDetails.length());

                        if(detailsCount % 1000 == 0) {
                            System.out.println("Details Proceed : " + detailsCount);
                        }
                    }
                }
            } catch(Exception e) {
                return false;
            } finally {
                if(br != null) {                
                    br.close();
                }
            }
            
            return true;
        } else if(status.equals(StatusDefinition.AUTHORIZED)) {
            String fileName = context.get(MapKey.fileName).toString();
            String namTemplate = context.get(MapKey.templateName).toString();
            
            ExtMfcDetailsDao detailDao = new ExtMfcDetailsDao(session);
            detailDao.getDetails(BatchID);
            int runPackageStatus = detailDao.runPackage(BatchID);
            getLogger().info("runPackageStatus : " + runPackageStatus);

            if(runPackageStatus == 1) {
                FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
                FixSchedulerXtract fixSchedulerXtract = fixSchedulerXtractDao.get(namTemplate);

                if(fixSchedulerXtract != null) {
                    fixQXtract = new FixQXtract();
                    fixQXtract.setIdScheduler(fixSchedulerXtract.getFixSchedulerPK().getIdScheduler());
                    fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
                    fixQXtract.setDtmRequest(SchedulerUtil.getTime());
                    fixQXtract.setParam5(fileName);
                    fixQXtract.setParam6(BatchID);
                }
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    private boolean checkCurr(List listCurr, String curr) {
        boolean found = false;
        
        for(int i = 0; i < listCurr.size(); i++) {
            if(((String)listCurr.get(i)).equalsIgnoreCase(curr)) {
                found = true;
                return found;
            }
        }
        
        return found;
    }
}
