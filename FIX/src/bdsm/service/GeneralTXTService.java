/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.service;

import bdsm.model.*;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralINTDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.util.FileUtil;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.BdsmUtil;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.apache.commons.*;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author 00110310
 */
public class GeneralTXTService {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GeneralTXTService.class);
    private FixSchedulerImportDao importDao;
    private FixSchedulerXtractDao xtractDao;
    private BaBankMastDAO bankDao;
    private ParameterDao paramDao;
    private GeneralINTDao genDao;
    private Session session;
    private String errFlag;
    private String errValue;
    private String errID;
    private FixQXtract fixQXtract;
    private String batchNo;
    List<ARParamDetails> record = new ArrayList<ARParamDetails>();
    private Map context;
    private Integer numDetail;
    private String accrossStatus;
    private String accrossStatRespond;
    StringBuilder accrossStatusResp = new StringBuilder();
    private boolean flagProc = false;

    /**
     * 
     */
    public GeneralTXTService() {
    }

    /**
     * 
     * @param importDao
     * @param xtractDao
     * @param bankDao
     * @param paramDao
     * @param genDao
     * @param session
     * @param context
     */
    public GeneralTXTService(FixSchedulerImportDao importDao, FixSchedulerXtractDao xtractDao, BaBankMastDAO bankDao, ParameterDao paramDao, GeneralINTDao genDao, Session session, Map context) {
        this.importDao = importDao;
        this.xtractDao = xtractDao;
        this.bankDao = bankDao;
        this.paramDao = paramDao;
        this.genDao = genDao;
        this.session = session;
        this.context = context;
    }

    /**
     * 
     * @param dt
     * @return
     */
    public String whereclause(List<ARParamDetailsInterface> dt) {
        // parse based on query ret
        String query = null;
        List<ARParamDetailsInterface> listing = new ArrayList<ARParamDetailsInterface>();
        for (ARParamDetailsInterface it : dt) {
            if (it != null) {
                // check which field have Query clause

                if (it.getType().contains("Q")) {
                    // field for query
                    logger.debug("det_Q :" + it);
                    listing.add(it);
                }
            }
        }

        //Collections.sort(listing);
        // looping again based on query identifier
        logger.debug("LISTING :" + listing);
        int counter_rate = 0;
        for (ARParamDetailsInterface lt : listing) {
            logger.debug("QUUERY LIST :" + query);
            if (lt != null) {
                if (counter_rate == 0) {
                    query = lt.getFieldName() + lt.getIdenRel();
                } else {
                    query = query + lt.getIdenOpr() + lt.getFieldName() + lt.getIdenRel();
                }
                String tempString = null;
                if ("F".equalsIgnoreCase(lt.getSuffix())) {
                    tempString = "%" + getReferral(lt);
                } else if ("L".equalsIgnoreCase(lt.getSuffix())) {
                    tempString = getReferral(lt) + "%";
                } else if ("FL".equalsIgnoreCase(lt.getSuffix())) {
                    tempString = "%" + getReferral(lt) + "%";
                } else {
                    tempString = getReferral(lt);
                }
                query = query + "'" + tempString + "' ";
            }
        }
        logger.debug("LAST query :" + query);
        return query;
    }

    /**
     * 
     * @param d
     * @return
     */
    public String getFieldReferral(ARParamDetailsInterface d) {
        String value = null;
        if (d.getContextRef() != null) {
            value = d.getContextRef().toString();
        } else {
            // check another ref
            if (d.getParamRef() != null) {
                // check on property persister first
                try {
                    String values = PropertyPersister.class.getDeclaredField(d.getParamRef()).get(null).toString();
                    value = d.getParamRef().toString();
                } catch (Exception e) {
                    logger.debug(e);
                    logger.debug("NOT PROPERTY PERSISTER");

                    ParameterDao paramDao = new ParameterDao(this.session);
                    try {
                        Parameter param = paramDao.get(d.getParamRef());
                        if (param != null) {
                            value = d.getParamRef().toString();
                        }
                    } catch (Exception eX) {
                        logger.debug(eX);
                        logger.debug("NOT REALLY PROPERTY");
                        value = null;
                    }
                }
            } else {
                if (d.getCustomRef() != null) {
                    logger.debug("CUSTOM QUERY :" + d.getCustomRef());
                    if ("QUERY".equalsIgnoreCase(d.getDeffVal())) {
                        value = "(" + d.getCustomRef().toString() + ") as " + d.getFieldName();
                    }
                } else {
                    // fixing bug R flag on Output
                    value = d.getFieldName();
                }
            }
        }
        return value;
    }

    /**
     * 
     * @param im
     * @param ld
     * @param flagSelect
     * @return
     */
    public List<Map> queryDataBatch(ARParamMasterInterface im, List<ARParamDetailsInterface> ld, boolean flagSelect) {
        logger.debug("RESP CONTEXT :" + this.context);
        if (flagSelect) {
            return queryDataSelect(im, ld, 0);
        } else {
            return queryDataCount(im, ld, 0);
        }
    }

    /**
     * 
     * @param im
     * @param ld
     * @param number
     * @return
     */
    public List<Map> queryDataCount(ARParamMasterInterface im, List<ARParamDetailsInterface> ld, Integer number) {
        return queryData(im, ld, number, false);
    }

    /**
     * 
     * @param im
     * @param ld
     * @param number
     * @return
     */
    public List<Map> queryDataSelect(ARParamMasterInterface im, List<ARParamDetailsInterface> ld, Integer number) {
        return queryData(im, ld, number, true);
    }

    /**
     * 
     * @param im
     * @param ld
     * @param number
     * @param flagSelect
     * @return
     */
    public List<Map> queryData(ARParamMasterInterface im, List<ARParamDetailsInterface> ld, Integer number, boolean flagSelect) {

        logger.debug("DI :" + ld);
        List<Map> qResult = new ArrayList();
        StringBuilder detSelect = new StringBuilder();
        int counter = 0;
        if (flagSelect) {
            for (ARParamDetailsInterface id : ld) {
                if (id != null) {
                    if (id.getType().contains("O")) {
                        // fixing for reader Flag set to Output
                        String fieldName = id.getFieldName();
                        if (id.getType().contains("R")) {
                            logger.debug("READ :" + id);
                            fieldName = getFieldReferral(id);
                        } else if (id.getType().contains("D")) {
                            fieldName = id.getDefaultVal();
                        }

                        // fix for date converter SQL to Java
                        if (id.getFieldFormat() != null) {
                            if (id.getFieldType().equalsIgnoreCase("DATE") || id.getFieldType().equalsIgnoreCase("TIMESTAMP")) {

                                if (id.getFieldFormatOut() != null) {
                                    fieldName = "to_char(to_date(" + fieldName + ",'dd/MM/yy'),'" + id.getFieldFormatOut() + "')";
                                } else {
                                    fieldName = "to_char(to_date(" + fieldName + ",'dd/MM/yy'),'" + id.getFieldFormat() + "')";
                                }
                                fieldName = fieldName + " as " + id.getFieldName();
                            }
                        }

                        if (counter == ld.size() - 1) {
                            detSelect.append(fieldName).append(" ");
                        } else {
                            detSelect.append(fieldName).append(",");
                        }

                        // check formatter for date


                    }
                }
                counter++;
            }
            try {
                detSelect.deleteCharAt(detSelect.length() - 1);
            } catch (Exception e) {
                logger.debug("detSelect : " +e, e);
                detSelect = null;
            }
        } else {
            detSelect.append("COUNT(1) AS COUNTER ");
        }
        String queryRespective = null;
        queryRespective = whereclause(ld);
        logger.debug("QRESP :" + queryRespective);
        logger.debug("MI :" + im);
        logger.debug("SELECT :" + detSelect);
        try {
          qResult = this.genDao.resultCall(im.getTableName(), queryRespective, detSelect.toString());   
        } catch (Exception e) {
            logger.debug("qResult : " +e, e);
            qResult = new ArrayList<Map>();
        }
        return qResult;
    }

    /**
     * 
     * @param im
     * @param ld
     * @param number
     * @return
     */
    public String txtWriter(ARParamMasterInterface im, List<ARParamDetailsInterface> ld, Integer number) {
        List<Map> letsWrite = queryDataSelect(im, ld, number);
        StringBuilder sb = new StringBuilder();
        for (Map a : letsWrite) {
            if (a != null) {
                int f = 0;
                logger.debug("MAPWRITE :" + a);
                for (ARParamDetailsInterface d : ld) {
                    if (d != null) {
                        if (d.getType().contains("O")) {
                            //if (!d.getType().contains("Q")) {
                                String delims = im.getDelimiter();
                                String padds = d.getPaddType();
								if (padds == null){
									padds = "";
								}
								if(delims == null){
                                    delims = "";
                                }
                                if ("".equalsIgnoreCase(delims)) {
                                    String padd = null;
                                    try {
                                        padd = a.get(d.getFieldName()).toString();
                                    } catch (Exception e) {
                                        logger.debug("NOPAD :" + e, e);
                                        padd = null;
                                    }
                                    if (padd == null) {
                                        padd = "";
                                    }
                                    if ("L".equalsIgnoreCase(d.getPaddType())) {
                                        padd = BdsmUtil.leftPad(padd, d.getMaxLength(), d.getPadding().charAt(0));
                                    } else if ("R".equalsIgnoreCase(d.getPaddType())) {
                                        padd = BdsmUtil.rightPad(padd, d.getMaxLength(), d.getPadding().charAt(0));
                                    }
                                    sb.append(padd);
									logger.debug("NO_delim :" + sb);
                                } else {
                                    if (d.getMaxLength() != null) {
                                        String nopadd = null;
                                        try {
                                            nopadd = a.get(d.getFieldName()).toString();
                                        } catch (Exception e) {
                                            logger.debug("PAD_EX :" + e, e);
                                            nopadd = "";
                                        }

                                        if (nopadd == null) {
                                            nopadd = "";
                                        }
                                        if (f == ld.size() - 1) {
                                            sb.append(nopadd);
                                        } else {
                                            sb.append(nopadd).append(delims);
                                        }
                                    } else {
                                        String padd = "";
                                        Object paddObj = a.get(d.getFieldName());
                                        if (paddObj != null) {
                                            padd = paddObj.toString();
                                        } else {
                                            padd = "";
                                        }
                                        if (d.getPaddType() != null) {
                                            if (d.getPaddType().equalsIgnoreCase("L")) {
                                                padd = BdsmUtil.leftPad(padd, d.getMaxLength(), d.getPadding().charAt(0));
                                            } else if (d.getPaddType().equalsIgnoreCase("R")) {
                                                padd = BdsmUtil.rightPad(padd, d.getMaxLength(), d.getPadding().charAt(0));
                                            }
                                        }
                                        if (f == ld.size() - 1) {
                                            sb.append(padd);
                                        } else {
                                            sb.append(padd).append(delims);
                                        }
                                    }
                                    // check format
									logger.debug("WITH_delim :" + sb);
                               // }
                            }
                        }
                        f++;
                    }
                }
                //if(letsWrite.size() > 1){
                logger.debug("LAST CHAR :" + sb.charAt(sb.length() - 1));
				try {
					logger.debug("DELIM CHAR :" + im.getDelimiter().charAt(0));
				} catch (Exception e) {
					logger.debug("delim char :" + e,e);
				}
                char atIndex = sb.charAt(sb.length() - 1);
                // fixing for last element Delimiter
                if (im.getDelimiter() != null) {
                    if (atIndex == im.getDelimiter().charAt(0)) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                }

                sb.append(System.getProperty("line.separator"));
                //}
            }
        }
        logger.debug("STRING :" + sb.toString());
        return sb.toString();
    }

    /**
     * 
     * @param file
     * @param context
     * @param delimiter
     * @param conter
     * @return
     */
    public boolean TxtReader(String file, Map context, String delimiter, Integer conter) {
        BufferedReader br = null;
        String headerSymbol = null;
        String detailSymbol = null;
        String footerSymbol = null;
        String flgHeader = null;
        String flgDetail = null;
        String flgFooter = null;
        String Hquery = null;
        String Dquery = null;
        String Fquery = null;

        String hDelimiter = null;
        String dDelimiter = null;
        String fDelimiter = null;

        String tag;

        this.numDetail = conter;
        logger.info("MAP CONTEXT :" + context);
        int var = 0; // numbering flag for Header detection
        SimpleDateFormat df = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);
        SimpleDateFormat dbC = new SimpleDateFormat(StatusDefinition.dateBirthConvert);
        boolean procFlag = true;
        boolean autoValidation = false;
        boolean usingSameName = false;
        String filename = context.get(MapKey.fileName).toString();
        String lineDetails;


        Integer rowCount = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            // get Properties
            try {
                String pattern = context.get(MapKey.templateName).toString();
                Integer idImport = this.importDao.get(pattern).getFixSchedulerPK().getIdScheduler();
                List<ARParamProfileInterface> masterRec = genDao.listparamProfileInterface(idImport);
                List<ARParamMasterInterface> masters = genDao.listparamProfile(idImport);
                logger.info("IDIMPORT :" + idImport);
                logger.info("MASTER :" + masterRec);

                if (masterRec != null) {
                    for (ARParamProfileInterface pInt : masterRec) {
                        if (pInt != null) {
                            if ("Y".equalsIgnoreCase(pInt.getFlgSameName())) {
                                usingSameName = true;
                                this.context.put("fileResponse", file);
                            } else {
                                this.context.put("fileResponse", "");
                            }
                            if ("Y".equalsIgnoreCase(pInt.getValidateFlag())) {
                                autoValidation = true;
                            }
                            try {
                                this.context.put(MapKey.extFile, pInt.getOptExt());
                            } catch (Exception e) {
                                this.logger.debug("NO EXT :" + e, e);
                                this.context.put(MapKey.extFile, "");
                            }
                        }
                    }
                    LinkedList<String> fieldOPR = new LinkedList<String>();
                    LinkedList<String> fieldSPV = new LinkedList<String>();
                    String fieldBatch, fieldStatusReason;
                    setFixQXtract(new FixQXtract());

                    fieldBatch = fieldStatusReason = "";

                    String outFileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(context.get(MapKey.param1).toString().replace(context.get(MapKey.templateName) + " ", "").replace("RE: ", "")) + "{hhmmss}.xls");

                    FixSchedulerImport imp = importDao.get(idImport);
                    logger.debug("QUERY IMPORT :" + imp);
                }

                List<ARParamMasterInterface> mit = this.genDao.listparamProfile(idImport);
                logger.debug("QUERY list :" + mit);
                if (mit != null) {
                    for (ARParamMasterInterface pi : mit) {
                        if (pi.getOrderNo().compareTo(1) == 0) {
                            headerSymbol = pi.getTypProcess();
                            flgHeader = pi.getFlgValidation();
                        } else if (pi.getOrderNo().compareTo(2) == 0) {
                            detailSymbol = pi.getTypProcess();
                            flgDetail = pi.getFlgValidation();
                        } else if (pi.getOrderNo().compareTo(3) == 0) {
                            footerSymbol = pi.getTypProcess();
                            flgFooter = pi.getFlgValidation();
                        }

                    }
                }
                // clean list all condition
                List<ARParamDetailsInterface> HeaderProp = this.genDao.paramDetailsInterface(idImport, headerSymbol, "I", 0);
                List<ARParamDetailsInterface> DetailProp = this.genDao.paramDetailsInterface(idImport, detailSymbol, "I", 0);
                List<ARParamDetailsInterface> FooterProp = this.genDao.paramDetailsInterface(idImport, footerSymbol, "I", 0);

                // prepare Variable

                Hquery = genDao.restructureInt(HeaderProp, masters.get(0).getTableName());
                Dquery = genDao.restructureInt(DetailProp, masters.get(1).getTableName());
                Fquery = genDao.restructureInt(FooterProp, masters.get(2).getTableName());

                hDelimiter = mit.get(0).getDelimiter();
                dDelimiter = mit.get(1).getDelimiter();
                fDelimiter = mit.get(2).getDelimiter();

                logger.debug("QUERY hEADER :" + Hquery);
                logger.debug("QUERY DETAILS :" + Dquery);
                logger.debug("QUERY FOOTER :" + Fquery);

                logger.debug("hEADER :" + HeaderProp);
                logger.debug("DETAILS :" + DetailProp);
                logger.debug("FOOTER :" + FooterProp);

                HeaderProp = this.genDao.paramDetailsInterface(idImport, headerSymbol, "I|R", 3);
                DetailProp = this.genDao.paramDetailsInterface(idImport, detailSymbol, "I|R", 3);
                FooterProp = this.genDao.paramDetailsInterface(idImport, footerSymbol, "I|R", 3);
                Integer detCount = 0;
                try {
                    this.numDetail = 0;
                    this.accrossStatRespond = "0";

                    while ((lineDetails = br.readLine()) != null) {
                        if (footerSymbol.equals(lineDetails.trim())) { // detect end of line
                            break;
                        } else if ("".equals(lineDetails.trim())) {
                            break;
                        } else {
                            detCount++;
                            var = 0;
                            logger.debug("DATA :" + lineDetails);
                            // check on first character determine Header, detail, footer
                            // rowCount++;
                            //String firstRow = String.valueOf(lineDetails.charAt(0));
							String firstRow  = strToCompare(mit,lineDetails);
                            List values = new ArrayList();
                            if (headerSymbol.equalsIgnoreCase(firstRow)) {
                                //
                                detCount = 0;
                                logger.debug("CHAR AT H:" + String.valueOf(lineDetails.charAt(0)));
                                try {
                                    if (!HeaderProp.isEmpty()) {
                                        //logger.debug("QUERY READY header:" + Hquery);
                                        rowCount++;
                                        if (hDelimiter != null) {
                                            values = cleanVal(hDelimiter, lineDetails, HeaderProp);
                                        } else {
                                            values = fixLtoList(lineDetails, HeaderProp);
                                        }

                                        logger.debug("DATA HEADER:" + values);
                                        var = 1;
                                    }
                                } catch (Exception e) {
                                    logger.debug("EXCEPTION :" + e, e);
                                    values = null;
                                }
                            } else if (detailSymbol.equalsIgnoreCase(firstRow)) {
                                //
                                logger.debug("CHAR AT D :" + String.valueOf(lineDetails.charAt(0)));
                                try {
                                    if (!DetailProp.isEmpty()) {
                                        //logger.debug("QUERY READY Details:" + Dquery);
                                        rowCount++;
                                        if (dDelimiter != null) {
                                            values = cleanVal(dDelimiter, lineDetails, DetailProp);
                                        } else {
                                            values = fixLtoList(lineDetails, DetailProp);
                                        }

                                        logger.debug("DATA DETAILS:" + values);
                                        var = 1;
                                    }
                                } catch (Exception e) {
                                    logger.debug("EXCEPTION :" + e, e);
                                    values = null;
                                }

                            } else if (footerSymbol.equalsIgnoreCase(firstRow)) {
                                //
                                this.numDetail = detCount - 1;
                                detCount = 0;

                                logger.debug("CHAR AT F:" + String.valueOf(lineDetails.charAt(0)));
                                try {
                                    if (!FooterProp.isEmpty()) {
                                        //logger.debug("QUERY READY footer:" + Fquery);
                                        if (dDelimiter != null) {
                                            values = cleanVal(fDelimiter, lineDetails, FooterProp);
                                        } else {
                                            values = fixLtoList(lineDetails, FooterProp);
                                        }

                                        rowCount++;
                                        logger.debug("DATA FOOTER:" + values);
                                        var = 1;
                                    }
                                } catch (Exception e) {
                                    logger.debug("EXCEPTION :" + e, e);
                                    values = null;
                                }
                            } else {
                                logger.debug("CHAR AT :" + String.valueOf(lineDetails.charAt(0)));
                            }

                            if (values.isEmpty() || values == null) {
                                logger.debug("PARSE FAILED :" + values.size());
                                procFlag = false;
                                break;
                            } else {
                                if (!StatusDefinition.REJECT.equalsIgnoreCase(accrossStatus)) {
                                    this.accrossStatus = context.get(MapKey.status).toString();
                                }
                                //this.accrossStatus = "A";
                                if (firstRow.toString().equalsIgnoreCase(detailSymbol) && var > 0) {
                                    // DETAILS 

                                    logger.debug("DETAILS");
                                    // if Header failed set default status to reject respond
                                    if (!"0".equalsIgnoreCase(accrossStatRespond)) {
                                        this.accrossStatusResp.append(this.genDao.getGenErr(999).getErrorDesc());
                                    }
                                    procText(values, DetailProp, Dquery, autoValidation, detCount);
                                    procFlag = true;
                                } else if (firstRow.equalsIgnoreCase(footerSymbol)) {
                                    // footer
                                    logger.debug("FOOTER");
                                    if (!"0".equalsIgnoreCase(accrossStatRespond)) {
                                        this.accrossStatusResp.append(this.genDao.getGenErr(999).getErrorDesc());
                                    }
                                    procText(values, FooterProp, Fquery, autoValidation);
                                    procFlag = true;
                                } else if (firstRow.equalsIgnoreCase(headerSymbol)) {
                                    // header
                                    logger.debug("HEADER");
                                    logger.debug("VALUES :" + values.size());
                                    logger.debug("HEADER_size :" + HeaderProp.size());
                                    procFlag = true;
                                    procText(values, HeaderProp, Hquery, autoValidation);
                                } else {
                                    logger.debug("INVALID FORMAT :" + values.get(0));
                                    procFlag = false;
                                }
                                logger.debug("STATUS RESPOND : " + this.accrossStatusResp);
                                if (!"".equalsIgnoreCase(this.accrossStatusResp.toString())) {
                                    logger.debug("ST : " + this.accrossStatusResp);
                                    this.accrossStatusResp.delete(0, this.accrossStatusResp.length());
                                } else {
                                    this.accrossStatusResp.append(this.genDao.getGenErr(0).getErrorDesc());
                                }


                            }
                        }
                    }

                } catch (IOException iOException) {
                    logger.debug("FILE NOT FOUND :" + file);
                    logger.info("FILE IO EXCEPTION :" + iOException, iOException);
                    procFlag = false;
                    //this.setErrFlag(StatusDefinition.YES); // failed to finish read file
                    //this.setErrValue(iOException.toString());
                    //this.setErrID(ErrorPersister.fileClosed);
                }
            } catch (Exception ex) {
                logger.info("FAILED GET IDSCHEDULER :" + ex, ex);
                procFlag = false;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException iOException) {
                        logger.info("IO EXCEPTION :" + iOException, iOException);
                        //this.setErrFlag(StatusDefinition.YES); // failed to finish read file
                        //this.setErrValue(iOException.toString());
                        //this.setErrID(ErrorPersister.fileClosed);
                    }
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            logger.debug("FILE NOT FOUND :" + file);
            logger.info("FILE NOT FOUND :" + fileNotFoundException, fileNotFoundException);
            procFlag = false;
        }
        return procFlag;
    }

    /**
     * 
     * @param values
     * @param DetailProp
     * @param query
     * @param flagAuto
     * @return
     */
    public boolean procText(List values, List<ARParamDetailsInterface> DetailProp, String query, boolean flagAuto) {
        return procText(values, DetailProp, query, flagAuto, 0);
    }

    /**
     * 
     * @param values
     * @param DetailProp
     * @param query
     * @param flagAuto
     * @param recCount
     * @return
     */
    public boolean procText(List values, List<ARParamDetailsInterface> DetailProp, String query, boolean flagAuto, Integer recCount) {
        List detailForInsert = new ArrayList();
        List newInterface = new ArrayList();
        String statusFlag = null;
        Map dtlsIns = null;
        int counter = 0;
        logger.debug("LIST VAL : " + values);
        for (ARParamDetailsInterface detail : DetailProp) {
            // mandatory reject if null
            logger.debug("MAP INNER :" + detail);
            if (detail != null) {
                dtlsIns = new HashMap();
                if (detail.getType().contains("I")) {
                    // Clean prop only for I and Read
                    if (!detail.getType().contains("D")) {
                        if (values.get(counter) != null) {
                            // if value really null check custom 
                            logger.debug("VALUES GET :" + values.get(counter));
                            if ("null".equalsIgnoreCase(values.get(counter).toString())) {
                                // Check If Mandatory Field have default
                                // get Mapped Default value from parameter

                                Object tempData = null;
                                String value = "";
                                try {
                                    // check Mandatory or not
                                    if ("Y".equalsIgnoreCase(detail.getFlgMandatory())) {
                                        value = detail.getDefaultVal() == null ? values.get(counter).toString() : detail.getDefaultVal();
                                    }
                                } catch (Exception ex) {
                                    logger.debug("FAILED GET VALUE fieldparameter :" + ex, ex);
                                    logger.debug("COUNTER 2 :" + counter);
                                    value = "";
                                }
                                if ("null".equalsIgnoreCase(value)) {
                                    // check wether have params or not
                                    value = getReferral(detail);
                                }
                                tempData = value;
                                if (flagAuto) {
                                    dtlsIns = autovalidation(detail, values, counter, recCount);
                                } else {
                                    dtlsIns.put(detail.getFieldName(), tempData);
                                }
                                logger.debug("DTLSLIST :" + dtlsIns);
                                if (!detail.getType().contains("D")) {
                                    detailForInsert.add(dtlsIns);
                                }


                                //dtlsIns.clear();
                            } else {
                                if (flagAuto) {
                                    dtlsIns = autovalidation(detail, values, counter, recCount);
                                } else {
                                    dtlsIns.put(detail.getFieldName(), values.get(counter));
                                }
                                if (!detail.getType().contains("D")) {
                                    detailForInsert.add(dtlsIns);
                                }
                                //dtlsIns.clear();
                            }
                            logger.debug("REFF_LAIN:" + detail);
                        } else {
                            String value = getReferral(detail);
                            logger.debug("REFF:" + value);
                            if (flagAuto) {
                                dtlsIns = autovalidation(detail, values, counter, recCount);
                            } else {
                                dtlsIns.put(detail.getFieldName(), value);
                            }
                            if (!detail.getType().contains("D")) {
                                detailForInsert.add(dtlsIns);
                            }
                        }
                    }
                    if (!detail.getType().contains("D")) {
                        newInterface.add(detail);
                        counter++;
                    }

                }
            }
        }
        logger.debug("DTLS :" + dtlsIns);
        logger.debug("LIST LAST :" + detailForInsert);
        try {
            genDao.dumpTable(putintoMap(detailForInsert, newInterface), query, "INT");
        } catch (Exception e) {
            // skip for each exception
            logger.debug("DAO :" + e, e);
        }

        return true;
    }

    /**
     * 
     * @param detail
     * @param values
     * @param counter
     */
    public void autoCheckingType(ARParamDetailsInterface detail, List values, Integer counter) {
        GeneralINTDao intDao = new GeneralINTDao(session);
        String flagReject = "A";

        if ("INTEGER".equalsIgnoreCase(detail.getFieldType())) {
            try {
                Integer.parseInt(values.get(counter).toString());
            } catch (Exception exd) {
                flagReject = "R";
            }
        } else if ("DOUBLE".equalsIgnoreCase(detail.getFieldType())) {
            try {
                Double.parseDouble(values.get(counter).toString());
            } catch (Exception exd) {
                flagReject = "R";
            }
        } else if ("BIGDECIMAL".equalsIgnoreCase(detail.getFieldType())) {
            try {
                BigDecimal dec = new BigDecimal(values.get(counter).toString());
            } catch (Exception exd) {
                flagReject = "R";
            }
        } else if ("STRING".equalsIgnoreCase(detail.getFieldType())) {
            try {
                values.get(counter).toString();
            } catch (Exception exd) {
                flagReject = "R";
            }
        }

        if ("R".equalsIgnoreCase(flagReject)) {
            if ("Y".equalsIgnoreCase(detail.getFlgMandatory())) {
                this.accrossStatus = "R";
            }
            try {
                this.accrossStatRespond = intDao.getErrStat(981, context.get(MapKey.templateName).toString()).getErrCode();
            } catch (Exception e) {
                this.accrossStatRespond = "981";
            }
            this.accrossStatusResp.append(intDao.getGenErr(981).getErrorDesc()).append(" ").append(detail.getIdOrder()).append(";");
        }
    }

    /**
     * 
     * @param detail
     * @param values
     * @param counter
     * @return
     */
    public Map autovalidation(ARParamDetailsInterface detail, List values, Integer counter) {
        return autovalidation(detail, values, counter, 0);
    }

    /**
     * 
     * @param detail
     * @param values
     * @param counter
     * @param rowCount
     * @return
     */
    public Map autovalidation(ARParamDetailsInterface detail, List values, Integer counter, Integer rowCount) {
        Map dtlsIns = new HashMap();
        //String statusFlag = "A";
        //StringBuilder statusReason = new StringBuilder();
        GeneralINTDao intDao = new GeneralINTDao(session);
        logger.debug("current :" + values.get(counter));
        logger.debug("LIST VALS:" + values);
        logger.debug("DI :" + detail);

        if ("VALIDATION".equalsIgnoreCase(detail.getDeffVal())) {
            if ("SUMMARY".equalsIgnoreCase(detail.getCustomRef())) {
                logger.debug("NUMCountDIFF :" + numDetail + " : " + values.get(counter).toString());
                logger.debug("CONTEXTS :" + this.context);
                try {
                    if (numDetail.compareTo(Integer.parseInt(values.get(counter).toString())) > 0) {
                        this.accrossStatusResp.append(intDao.getGenErr(991).getErrorDesc());
                        logger.debug("DIFF SUM MORE:" + values.get(counter));
                        this.accrossStatus = "R";
                        try {
                            this.accrossStatRespond = intDao.getErrStat(991, context.get(MapKey.templateName).toString()).getErrCode();
                        } catch (Exception e) {
                            this.accrossStatRespond = "991";
                        }
                    } else if (numDetail.compareTo(Integer.parseInt(values.get(counter).toString())) < 0) {
                        this.accrossStatusResp.append(intDao.getGenErr(992).getErrorDesc());
                        logger.debug("DIFF SUM LESS:" + values.get(counter));
                        this.accrossStatus = "R";
                        try {
                            this.accrossStatRespond = intDao.getErrStat(992, context.get(MapKey.templateName).toString()).getErrCode();
                        } catch (Exception e) {
                            this.accrossStatRespond = "992";
                        }
                    }
                } catch (Exception exception) {
                    logger.debug("DIFF EXCEPTION:" + exception, exception);
                }
            } else if ("DATE_BANK".equalsIgnoreCase(detail.getCustomRef())) {
                String format = detail.getFieldFormat();
                logger.debug("format :" + format);
                DateFormat df = new SimpleDateFormat(format);
                String dateF = null;
                try {
                    synchronized (df) {
                        dateF = df.format(df.parse(values.get(counter).toString()));
                    }
                    String dateFcomp = df.format(bankDao.get().getDatProcess());
                    logger.debug("DATEBANK :" + dateFcomp + "TEXTDATE :" + dateF);
                    if (!dateF.equalsIgnoreCase(dateFcomp)) {
                        this.accrossStatusResp.append(intDao.getGenErr(993).getErrorDesc()).append(dateFcomp);
                        this.accrossStatus = "R";
                        try {
                            this.accrossStatRespond = intDao.getErrStat(993, context.get(MapKey.templateName).toString()).getErrCode();
                        } catch (Exception e) {
                            this.accrossStatRespond = "993";
                        }
                        logger.debug("DIFF DATE :" + values.get(counter));
                    }
                } catch (ParseException parseException) {
                    logger.debug("parse Failed :" + values.get(counter));
                    this.accrossStatus = "R";
                    this.accrossStatusResp.append(intDao.getGenErr(980).getErrorDesc()).append(values.get(counter)).append(";");
                    try {
                        this.accrossStatRespond = intDao.getErrStat(980, context.get(MapKey.templateName).toString()).getErrCode();
                    } catch (Exception e) {
                        this.accrossStatRespond = "980";
                    }
                } catch (Exception ex) {
                    logger.debug("Exception Occured:" + ex, ex);
                    this.accrossStatRespond = "999";
                }
            } else if ("DATE_REAL".equalsIgnoreCase(detail.getCustomRef())) {
            }
            dtlsIns.put(detail.getFieldName(), values.get(counter));
        } else if ("STATUSRESP".equalsIgnoreCase(detail.getDeffVal())) {
            if (this.accrossStatusResp.length() == 0) {
                accrossStatusResp.append("Record Parsed Successfully");
            }
            dtlsIns.put(detail.getFieldName(), this.accrossStatusResp.toString());
        } else if ("STATUS".equalsIgnoreCase(detail.getDeffVal())) {
            dtlsIns.put(detail.getFieldName(), this.accrossStatus);
        } else if ("STATCODE".equalsIgnoreCase(detail.getDeffVal())) {
            dtlsIns.put(detail.getFieldName(), this.accrossStatRespond);
        } else if ("DTMPROCESS".equalsIgnoreCase(detail.getDeffVal())) {
            dtlsIns.put(detail.getFieldName(), SchedulerUtil.getTime());
            logger.debug("PROCESS DTM :" + values.get(counter));
        } else if ("INCREMENT".equalsIgnoreCase(detail.getDeffVal())) {
            dtlsIns.put(detail.getFieldName(), rowCount);
        } else {
            dtlsIns.put(detail.getFieldName(), values.get(counter));
        }
        logger.debug("DETAILS :" + dtlsIns);
        return dtlsIns;
    }

    /**
     * 
     * @param d
     * @param lineOfField
     * @return
     */
    public String queryClob(ARParamDetailsInterface d, Map lineOfField) {
        String value = null;
        GeneralINTDao intDao = new GeneralINTDao(session);
        Integer idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());
        if ("QUERY".equalsIgnoreCase(d.getDeffVal())) {
            try {
                InputStream input = d.getClobQuery().getAsciiStream();
                StringWriter w = new StringWriter();
                IOUtils.copy(input, w);
                String writMap = w.toString();
                Object[] arrayVal = lineOfField.entrySet().toArray();
                // loop to whole map, replacing key and value
                for (int i = 0; i < arrayVal.length; i++) {
                    List mapSeparator = SchedulerUtil.getParameter(arrayVal[i].toString(), "=");
                    // check condition of query
                    String valClob = mapSeparator.get(1).toString();
                    List<ARParamDetailsInterface> oneFfield = genDao.detailsListIndividual(idScheduler, "D", "G", mapSeparator.get(0).toString());
                    if (!oneFfield.isEmpty()) {
                        if ("DATE".equalsIgnoreCase(oneFfield.get(0).getFieldType())
                                || "TIMESTAMP".equalsIgnoreCase(oneFfield.get(0).getFieldType())) {
                            valClob = "to_date(" + mapSeparator.get(1).toString() + ",'" + oneFfield.get(0).getFieldFormat() + "')";
                        }
                        writMap = writMap.replaceAll(":" + mapSeparator.get(0).toString(), "'" + valClob + "'");
                    }
                }
                List getData = this.genDao.queryCall(writMap);
            } catch (IOException ex) {
                logger.debug("FAILED TO PARSE IO :" + ex, ex);
            } catch (SQLException e) {
                logger.debug("FAILED TO GET CLOB :" + e, e);
            }
        }
        return value;
    }

    /**
     * 
     * @param d
     * @return
     */
    public String getReferral(ARParamDetailsInterface d) {
        return getReferral(d, null);
    }

    /**
     * 
     * @param d
     * @param lineOfField
     * @return
     */
    public String getReferral(ARParamDetailsInterface d, Map lineOfField) {
        String value = null;
        Integer valuesInt;
        logger.debug("detail interface :" + d);
        if (d.getType().contains("G") && lineOfField != null) {
            // field to get Reff from another field
            value = queryClob(d, lineOfField);
        } else if (!d.getType().contains("D")) {
            if (d.getContextRef() != null) {
                logger.debug("ctx :" + d.getContextRef());
                value = getContext().get(d.getContextRef()).toString();
            } else {
                // check another ref
                if (d.getParamRef() != null) {
                    // check on property persister first
                    try {
                        value = PropertyPersister.class.getDeclaredField(d.getParamRef()).get(null).toString();
                    } catch (Exception e) {
                        logger.debug(e);
                        logger.debug("NOT PROPERTY PERSISTER");

                        ParameterDao paramDao = new ParameterDao(this.session);
                        try {
                            Parameter param = paramDao.get(d.getParamRef());
                            if ("S".equalsIgnoreCase(param.getTypParam())) {
                                value = param.getStrVal();
                            } else if ("N".equalsIgnoreCase(param.getTypParam())) {
                                valuesInt = param.getValue();
                                value = valuesInt.toString();
                            }
                        } catch (Exception eX) {
                            logger.debug(eX);
                            logger.debug("NOT REALLY PROPERTY");
                            value = null;
                        }
                    }
                } else {
                    if (d.getDeffVal() != null) {
                        // check for custom Pointer
                        if ("QUERY".equalsIgnoreCase(d.getDeffVal())) {
                            List checked = this.genDao.queryCall(d.getCustomRef());
                            logger.debug("LISTQUERY :" + checked);
                            if (!checked.isEmpty() || checked != null) {
                                value = checked.get(0).toString();
                            }
                        } else if ("DIRECT".equalsIgnoreCase(d.getDeffVal())) {
                            value = d.getCustomRef();
                        }
                    }
                }
            }
        }

        logger.debug("VAL :" + value);
        return value;
    }

    private List fixLtoList(String lineDetails, List<ARParamDetailsInterface> dt) {
        List values = new ArrayList();
        Integer nextval = 0;
        for (ARParamDetailsInterface d : dt) {
            if (d.getIdOrder().compareTo(1) == 0) {
                logger.debug("1a");
                values.add(lineDetails.substring(0, d.getMaxLength()));
                nextval = d.getMaxLength();
            } else {
                logger.debug("1b");
                if (d.getType().contains("R")) {
                    values.add(getReferral(d));
                    logger.debug("2a");
                } else {
                    logger.debug("2b");
                    if ("".equalsIgnoreCase(d.getCustomRef()) && "".equalsIgnoreCase(d.getContextRef()) && "".equalsIgnoreCase(d.getDeffVal())) {
                        logger.debug("3a");
                        values.add(lineDetails.substring(nextval, nextval + d.getMaxLength()));
                    } else {
                        logger.debug("3b");
                        if (d.getCustomRef() == null && d.getContextRef() == null) {
                            logger.debug("4a");
                            if (d.getDeffVal() == null) {
                                logger.debug("5a");
                                values.add(lineDetails.substring(nextval, nextval + d.getMaxLength()));
                            } else {
                                logger.debug("5b");
                                if ("QUERY".equalsIgnoreCase(d.getDeffVal())) {
                                    values.add(getReferral(d));
                                    logger.debug("6a");
                                } else {
                                    logger.debug("6b");
                                    values.add(lineDetails.substring(nextval, nextval + d.getMaxLength()));
                                }
                            }
                        } else {
                            logger.debug("4b");
                            if ("VALIDATION".equalsIgnoreCase(d.getDeffVal())) {
                                logger.debug("4b1");
                                values.add(lineDetails.substring(nextval, nextval + d.getMaxLength()));
                            } else {
                                logger.debug("4b2");
                                values.add(getReferral(d));
                            }

                        }
                    }

                }
				if(d.getMaxLength() != null){
                nextval = nextval + d.getMaxLength();
				} else {
				nextval = nextval;	
				}
            }
            try {
                logger.debug("VALUES :" + nextval + " " + lineDetails.substring(nextval, nextval + d.getMaxLength()));
            } catch (Exception ex) {
                logger.debug("NOT DATA");
            }
        }
        return values;
    }

    private List cleanVal(String delimiter, String lineDetails, List<ARParamDetailsInterface> dt) {
        List values = new ArrayList();
        // padding to last 
        this.logger.debug("value before :" + lineDetails);
        lineDetails = (lineDetails.replaceAll("\\" + delimiter + "\\" + delimiter, "\\"
                + delimiter + PropertyPersister.DEFAULT_REPLACEMENT + "\\" + delimiter)).replaceAll("\\" + delimiter + "\\" + delimiter, "\\" + delimiter + PropertyPersister.DEFAULT_REPLACEMENT + "\\" + delimiter);
        this.logger.debug("value After :" + lineDetails);
        values = SchedulerUtil.getParameter(lineDetails, "\\" + delimiter);
        List lastValue = new ArrayList();
        this.logger.debug("FIELDVALUE :" + values);
        int z = 0;
        for (Object val : values) {
            if (val != null) {
                // check if value need to insert
                if (dt.get(z).getType().contains("I")) {
                    if (!dt.get(z).getType().contains("D")) {
                        lastValue.add(val);
                        logger.debug("field :" + dt.get(z).getFieldName());
                        logger.debug("values :" + val);
                    }
                }
                z++;
            }
        }
        // check value and available parameter
        //int diff = Math.abs(dt.size() - values.size());
        for (int i = values.size(); i < dt.size(); i++) {
            if (dt.get(i).getType().contains("I")) {
                // ckeck if values have reveral
                lastValue.add(getReferral(dt.get(i)));
                logger.debug("values 2 :" + lastValue);
            }
        }
        return lastValue;
    }

    private String interrorMapping(Integer number) {

        return "1";
    }

    private List<Map<String, Object>> putintoMap(List<Map> arrRecord, List<ARParamDetailsInterface> recParam) {
        logger.debug("arrRecord : " + arrRecord);
        logger.debug("FIELDRec :" + recParam);
        Map<String, Object> typeMap;
        List<Map<String, Object>> recordList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < arrRecord.size(); i++) {
            ARParamDetailsInterface detail = recParam.get(i);


            logger.debug("arrRecord index : " + i);
            logger.debug("fieldValue : " + arrRecord.get(i));
            logger.debug("fieldName : " + detail.getFieldName());
            //logger.debug("fieldType : " + (detail.getFieldType() == null ? "" : detail.getFieldType()));
            //logger.debug("fieldFormat : " + (detail.getFieldFormat() == null ? "" : detail.getFieldFormat()));
            //logger.debug("maxLength :" + detail.getMaxLength());
            //logger.debug("defVal : " + detail.getDeffVal());
            //logger.debug("defaultVal : " + detail.getDefaultVal());

            typeMap = new HashMap<String, Object>();

            try {
                typeMap.put("fieldVal", (arrRecord.get(i).get(detail.getFieldName()).toString()));
            } catch (Exception e) {
                typeMap.put("fieldVal", "");
            }
            typeMap.put("fieldName", detail.getFieldName());
            typeMap.put("fieldType", detail.getFieldType() == null ? "" : detail.getFieldType());
            typeMap.put("fieldformat", detail.getFieldFormat() == null ? "" : detail.getFieldFormat());
            typeMap.put("maxLength", detail.getMaxLength());
            typeMap.put("defVal", detail.getDeffVal());
            typeMap.put("defaultVal", detail.getDefaultVal());
            typeMap.put("flgMandatory", detail.getFlgMandatory());

            recordList.add(typeMap);
        }
        logger.debug("list :" + recordList);
        return recordList;
    }
    /**
     * 
     * @param mit
     * @param lineDetails
     * @return
     */
    public String strToCompare(List<ARParamMasterInterface> mit, String lineDetails){
		String identifier = mit.get(0).getDelimiter();
		Map<String,Object> paramGetter = new HashMap<String,Object>();
        paramGetter = null;
		String firstRow = "";
        String strCmp = "";
        String compDummy = "";
        
        logger.debug("mit :" +mit);
        logger.debug("linedetails :" +lineDetails);
        
        if (identifier == null) identifier = "";
		if(lineDetails.indexOf(identifier) == -1 || identifier == null){
		// check for fixlength.
			paramGetter = paramCheck(mit,lineDetails);
		} else {
			identifier = mit.get(1).getDelimiter();
            if (identifier == null) identifier = "";
			if(lineDetails.indexOf(identifier) == -1 || mit.get(1).getDelimiter() == null){
				paramGetter = paramCheck(mit,lineDetails);
               
			} else {
				identifier = mit.get(2).getDelimiter();
                if (identifier == null) identifier = "";
				if(lineDetails.indexOf(identifier) == -1 || mit.get(2).getDelimiter() == null){
					paramGetter = paramCheck(mit,lineDetails);
                    
				} else {
					strCmp = lineDetails.substring(0,lineDetails.indexOf(identifier));
					if(strCmp.equalsIgnoreCase(mit.get(0).getTypProcess())) {
						this.flagProc = true;
                       
                        
					} else {
						if(strCmp.equalsIgnoreCase(mit.get(1).getTypProcess().toString())){
							this.flagProc = true;
                           
						} else {
							if(strCmp.equalsIgnoreCase(mit.get(2).getTypProcess().toString())){
								this.flagProc = true;
                               
							} 
						}
					}
				}
			}	
		}
         
		if (paramGetter != null) {
			strCmp = paramGetter.get("strCmp").toString();
			flagProc = Boolean.valueOf(paramGetter.get("flagProc").toString());
		} 
		firstRow = flagProc ? strCmp : "";
       
		return firstRow;
	}
	
    /**
     * 
     * @param mit
     * @param lineDetails
     * @return
     */
    public Map paramCheck(List<ARParamMasterInterface> mit, String lineDetails){
		int countLength = 0;
		String strCmp = "";
		Map respond = new HashMap();
	
		countLength = mit.get(0).getTypProcess().length();
		strCmp = lineDetails.substring(0,countLength);
		if(strCmp.equalsIgnoreCase(mit.get(0).getTypProcess())) {
			this.flagProc = true;
		} else {
			countLength = mit.get(1).getTypProcess().length();
			strCmp = lineDetails.substring(0,countLength);
			if(strCmp.equalsIgnoreCase(mit.get(1).getTypProcess())){
				this.flagProc = true;
			} else {
				countLength = mit.get(2).getTypProcess().length();
				strCmp = lineDetails.substring(0,countLength);
				if(strCmp.equalsIgnoreCase(mit.get(2).getTypProcess())){
					this.flagProc = true;
				} 
			}
		}
		respond.put("flagProc",flagProc);
		respond.put("strCmp",strCmp);
		return respond;
	}
	
    /**
     * 
     * @param importDao
     */
    public void setImportDao(FixSchedulerImportDao importDao) {
        this.importDao = importDao;
    }

    /**
     * 
     * @param xtractDao
     */
    public void setXtractDao(FixSchedulerXtractDao xtractDao) {
        this.xtractDao = xtractDao;
    }

    /**
     * 
     * @param bankDao
     */
    public void setBankDao(BaBankMastDAO bankDao) {
        this.bankDao = bankDao;
    }

    /**
     * 
     * @param paramDao
     */
    public void setParamDao(ParameterDao paramDao) {
        this.paramDao = paramDao;
    }

    /**
     * 
     * @param genDao
     */
    public void setGenDao(GeneralINTDao genDao) {
        this.genDao = genDao;
    }

    /**
     * 
     * @return
     */
    public String getErrValue() {
        return errValue;
    }

    /**
     * 
     * @param errValue
     */
    public void setErrValue(String errValue) {
        this.errValue = errValue;
    }

    /**
     * 
     * @return
     */
    public String getErrID() {
        return errID;
    }

    /**
     * 
     * @param errID
     */
    public void setErrID(String errID) {
        this.errID = errID;
    }

    /**
     * 
     * @param fixQXtract
     */
    public void setFixQXtract(FixQXtract fixQXtract) {
        this.fixQXtract = fixQXtract;
    }

    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the context
     */
    public Map getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(Map context) {
        this.context = context;
    }
}