/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.service;

import bdsm.model.*;
import bdsm.scheduler.ErrorPersister;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.ErrorCodeDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.DefinitionConstant;
import bdsmhost.dao.CifCasaExtOpeningDao;
import bdsmhost.dao.FcrCiCustmastDao;
import bdsmhost.dao.MenuRedDao;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author SDM
 */
public class SingleScreenOPService {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SingleScreenOPService.class);
    private BaBankMastDAO bankDao;
    private CifCasaExtOpeningDao cifAcctExtDao;
    private ParameterDao paramDao;
    private MenuRedDao menuDao;
    private FixSchedulerImportDao importDao;
    private FixSchedulerXtractDao xtractDao;
    private ErrorCodeDao errDao;
    private FixQXtractDao fixQDao;
    private FcrCiCustmastDao customerMasterDAO;
    private Session session;
    private String errFlag;
    private String errValue;
    private String errID;

    /**
     * @param cifAcctExtDao the cifAcctExtDao to set
     */
    public void setCifAcctExtDao(CifCasaExtOpeningDao cifAcctExtDao) {
        logger.info("[ Dao Service Ext Account] " + cifAcctExtDao);
        this.cifAcctExtDao = cifAcctExtDao;
    }

    /**
     * @param paramDao the paramDao to set
     */
    public void setParamDao(ParameterDao paramDao) {
        logger.info("[ Dao Service Parameter] " + paramDao);
        this.paramDao = paramDao;
    }

    /**
     * 
     * @param pk
     * @return
     */
    public List<CoCiCustmast> listCasa(CoCiCustmast pk) {
        logger.info("pk " + pk);
        //String taxAmnesty = PropertyPersister.taxAmnesty;
        return this.customerMasterDAO.list(pk);
    }

    /**
     * 
     * @param noCard
     * @return
     */
    public List listCasaByNoCard(String noCard) {
        logger.info("noCard Service " + noCard);
        return this.customerMasterDAO.getByNocard(noCard);
    }

    /**
     * 
     * @param extMethod
     * @param os
     * @return
     */
    public List listGenExtChannel(String extMethod, Object[] os) {
        List channelList = null;
        try {
            Method m = this.cifAcctExtDao.getClass().getMethod(extMethod, new Class[]{});
            try {
                m.invoke(channelList, os);
            } catch (IllegalAccessException ex) {
                logger.info("ILLEGAL ACCESS :" + ex, ex);
            } catch (IllegalArgumentException ex) {
                logger.info("ILLEGAL ARGUMENT :" + ex, ex);
            } catch (InvocationTargetException ex) {
                logger.info("INVOCATION EXCEPTION :" + ex, ex);
            }
        } catch (NoSuchMethodException ex) {
            logger.info("NO SUCH METHOD :" + ex, ex);
        } catch (SecurityException ex) {
            logger.info("SECURITY EXCP :" + ex, ex);
        }
        return channelList;
    }

    /**
     * 
     * @param so
     * @return
     */
    public List listPreCasabyChannel(ScreenOpening so) {
        logger.info("PASSAGE :" + so.toString());
        return this.cifAcctExtDao.getList(so);
    }

    /**
     * 
     * @param pk
     * @return
     */
    public List listCasaByExtChannel(CoCiCustmast pk) {
        logger.info("Channel Service " + pk.toString());
        DateFormat formatterDate = new SimpleDateFormat(DefinitionConstant.DBSingleScreenDate);
        List<CoCiCustmast> ciMast = new ArrayList();
        List extChannel = new ArrayList();
        extChannel = this.cifAcctExtDao.getByExtChannelDistinct(pk);
        //extChannel = this.cifAcctExtDao.getByExtChannel(pk);
        logger.info("LIST CHECK : " + extChannel);

        for (Object op : extChannel) {
            if (op != null) {
                CoCiCustmast cim = new CoCiCustmast();
                CoCiCustmastPK cimpk = new CoCiCustmastPK();

                Map objCif = (Map) op;
                logger.info("MAP EXT :" + objCif);
                try {
                    cimpk.setDateofBirth(formatterDate.parse(objCif.get("birthDate").toString()));
                } catch (Exception ex) {
                    logger.info("EXCEPTION DOB :" + ex, ex);
                    logger.info("DATBIRTH :" + objCif.get("birthDate").toString());
                }
                cimpk.setNik(objCif.get("nik").toString());
                cim.setExtFlag(objCif.get("extUser").toString());
                cim.setNamCustFull(objCif.get("fullName").toString());
                cim.setNamCustFirst(objCif.get("custFirstName").toString());
                cim.setCodCustId(Integer.parseInt(objCif.get("cifNumber").toString()));
                try {
                    cim.setAddress(objCif.get("mailAddrs1").toString());
                } catch (Exception e) {
                    logger.info("EX :" + e, e);
                    cim.setAddress("-");
                }
                cim.setCompositeId(cimpk);
                ciMast.add(cim);
            }
        }
        return ciMast;
    }

    /**
     * 
     * @param pk
     * @param fieldNumber
     * @param TypeFix
     * @return
     */
    public List getDaoParameter(MenuRedirectionPK pk, Integer fieldNumber, String TypeFix) {
        List completeParam = new ArrayList();
        List paramComp = new ArrayList();
        MenuRedirect menuParam = this.menuDao.get(pk);
        logger.info("MENU :" + menuParam);

        if (menuParam != null) {
            completeParam.add(menuParam);
            List getRealParam = this.paramDao.getFieldParameterbyModuleName(0, TypeFix, fieldNumber, menuParam.getMethodInvocation());
            logger.info("LIST PARAMETER :" + getRealParam);
            if (!getRealParam.isEmpty()) {

                for (Object rP : getRealParam) {
                    if (rP != null) {
                        Map valueField = new HashMap();
                        Map innerValue = (Map) rP;
                        valueField.put("fieldName", innerValue.get("fieldName").toString());
                        valueField.put("formatData", innerValue.get("formatData").toString());
                        paramComp.add(valueField);
                    }
                }
                completeParam.add(paramComp);
            }
        }
        return completeParam;
    }

    /**
     * 
     * @param args
     * @param invocLass
     * @return
     */
    public List respList(List args, Class invocLass) {
        List replistComplete = new ArrayList();
        if (!args.isEmpty()) {
            MenuRedirect menu = (MenuRedirect) args.get(0);
            List inList = (List) args.get(1);

            if (!inList.isEmpty()) {
                try {
                    Object[] parameter = new Object[args.size()];
                    int count = 0;
                    for (Object mapArg : inList) {
                        if (mapArg != null) {
                            Map tempArg = (Map) mapArg;
                            logger.info("CLASS :" + invocLass.getDeclaredFields());
                            Field tempField = invocLass.getClass().getDeclaredField(tempArg.get(DefinitionConstant.fieldName).toString());
                            try {
                                parameter[count] = tempField.get(tempArg.get(DefinitionConstant.formatData).toString().getClass());
                                logger.info("INVOKE :" + count + " " + parameter[count]);
                                count++;
                            } catch (IllegalArgumentException ex) {
                                logger.info("ILLEGAL ARGUMENT FIELD :" + ex, ex);
                            } catch (IllegalAccessException x) {
                                logger.info("ILLEGAL ACCESS FIELD :" + x, x);
                            }
                        }
                    }
                    replistComplete = listGenExtChannel(menu.getMethodInvocation(), parameter);
                } catch (NoSuchFieldException ex) {
                    logger.info("NO SUCH FIELD :" + ex, ex);
                } catch (SecurityException ex) {
                    logger.info("SECURITY :" + ex, ex);
                }
            }
        }
        return replistComplete;
    }

    /**
     * 
     * @param file
     * @param context
     * @param delimiter
     * @param conter
     * @return
     */
    public boolean DDFTxtReader(String file, Map context, String delimiter, Integer conter) {
        BufferedReader br = null;
        logger.info("MAP CONTEXT :" + context);
        int var = 0; // numbering flag for Header detection
        SimpleDateFormat df = new SimpleDateFormat(StatusDefinition.Patternyyyymmdd);
        SimpleDateFormat dbC = new SimpleDateFormat(StatusDefinition.dateBirthConvert);
        boolean procFlag = true;
        String filename = context.get(MapKey.fileName).toString();
        String lineDetails;

        Integer rowCount = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            // get Properties
            try {
                String pattern = context.get(MapKey.templateName).toString();
                Integer idImport = this.importDao.get(pattern).getFixSchedulerPK().getIdTemplate();
                logger.info("IDIMPORT :" + idImport);
                List<Map> HeaderProp = this.paramDao.getFieldParameterbyModuleName(idImport, "I", 0, filename.substring(0, 6));
                List<Map> DetailProp = this.paramDao.getFieldParameterbyModuleName(idImport, "D", 1, filename.substring(0, 6));

                try {
                    while ((lineDetails = br.readLine()) != null) {
                        if (DefinitionConstant.HEADER_LINE.equals(lineDetails.trim())) { // detect end of line
                            break;
                        } else if ("".equals(lineDetails.trim())) {
                            break;
                        } else {
                            rowCount++;
                            lineDetails = (lineDetails.replaceAll("\\" + delimiter + "\\" + delimiter, "\\"
                                    + delimiter + PropertyPersister.DEFAULT_REPLACEMENT + "\\" + delimiter)).replaceAll("\\" + delimiter + "\\" + delimiter, "\\" + delimiter + PropertyPersister.DEFAULT_REPLACEMENT + "\\" + delimiter);
                            List values = SchedulerUtil.getParameter(lineDetails, "\\" + delimiter);
                            if (values.isEmpty()) {
                                logger.debug("HEADER FAILED :" + values.size());
                                procFlag = false;
                                break;
                            } else {
                                if ("1".equalsIgnoreCase(values.get(0).toString()) && var > 0) {
                                    // DETAILS 
                                    if (values.size() == DetailProp.size()) {
                                        // VALID PROCESS
                                        ScreenOpening cifAcct = new ScreenOpening();
                                        cifAcct.setProcStatus("1");
                                        cifAcct.setRecStat("P");
                                        String errorMessage = null;
                                        Map detailForInsert = new HashMap();
                                        int counter = 0;
                                        for (Map detail : DetailProp) {
                                            // mandatory reject if null
                                            logger.debug("MAP INNER :" + detail);
                                            if (detail != null) {

                                                // check mandatory field
                                                //if ("Y".equalsIgnoreCase(detail.get("flgMandatory").toString())) {
                                                if ("null".equalsIgnoreCase(values.get(counter).toString())) {
                                                    // Check If Mandatory Field have default
                                                    // get Mapped Default value from parameter

                                                    Object tempData = null;
                                                    String value = null;
                                                    try {
                                                        value = detail.get(DefinitionConstant.defValParam) == null ? "" : detail.get(DefinitionConstant.defValParam).toString();
                                                    } catch (Exception ex) {
                                                        logger.debug("FAILED GET VALUE fieldparameter :" + ex, ex);
                                                        logger.debug("COUNTER 2 :" + counter);
                                                        value = "";
                                                    }
                                                    if ("".equalsIgnoreCase(value)) {
                                                        try {
                                                            Map getVal = (Map) PropertyPersister.getRealParam(session, Integer.parseInt(context.get(MapKey.idScheduler).toString()), "X").get(0);
                                                            tempData = getVal.get(detail.get(DefinitionConstant.nameOfField).toString());
                                                            logger.info("TRACKING :" + detail.get(DefinitionConstant.nameOfField) + " VAL :" + tempData);
                                                            detailForInsert.put(detail.get(DefinitionConstant.nameOfField), tempData);

                                                        } catch (Exception e) {
                                                            logger.debug("FAILED GET VALUE :" + e, e);
                                                            logger.debug("COUNTER 1 :" + counter);
                                                            // CHECK on table FIeldParamater
                                                        }

                                                    } else {
                                                        //logger.debug("PUTINSERT VALUE :" + value);
                                                        String mirFlag = detail.get(DefinitionConstant.formatData).toString();
                                                        if (DefinitionConstant.MIRROR.equalsIgnoreCase(mirFlag)) {
                                                            // this is Mirroring Field,only applied when Field is Null
                                                            // Mandatory of Not Mandatory are not related
                                                            //logger.debug("MIRROR VALUE :" + detailForInsert.get(detail.get(DefinitionConstant.defValParam)).toString());
                                                            String mirValue = detailForInsert.get(detail.get(DefinitionConstant.defValParam)).toString();
                                                            //logger.info("MIRROR :" + mirValue);
                                                            detailForInsert.put(detail.get(DefinitionConstant.nameOfField), mirValue);
                                                        } else {
                                                            detailForInsert.put(detail.get(DefinitionConstant.nameOfField), value);
                                                        }
                                                    }

                                                    //logger.debug("TEMPORARY :" + tempData);
                                                    //logger.debug("TEMP VAL :" + value);
                                                    if ("Y".equalsIgnoreCase(detail.get(DefinitionConstant.flgMandatory).toString()) && tempData == null && "".equalsIgnoreCase(value)) {
                                                        logger.debug("ERROR FIELD");
                                                        cifAcct.setProcStatus("5");
                                                        cifAcct.setStatus("3");
                                                        cifAcct.setRecStat("R");
                                                        if (errorMessage == null) {
                                                            errorMessage = errDao.get(ErrorPersister.Mandatorynull, detail.get(DefinitionConstant.nameOfField).toString(), "S");
                                                        } else {
                                                            errorMessage = errorMessage + "," + detail.get(DefinitionConstant.nameOfField).toString();
                                                        }
                                                        cifAcct.setRecComment(errorMessage);
                                                    }
                                                } else {
                                                    detailForInsert.put(detail.get(DefinitionConstant.nameOfField), values.get(counter));
                                                }
                                                //}
                                                // mandatory set default if null
                                                Map refMap = getReferenceValue(1, filename.substring(0, 6), detail.get(DefinitionConstant.nameOfField).toString(), detail.get(DefinitionConstant.formatData).toString(), values.get(counter).toString());
                                                if (refMap != null) {
                                                    logger.debug("MAPREF :" + refMap);
                                                    detailForInsert.putAll(refMap);
                                                }
                                                counter++;
                                            }

                                        }
                                        logger.debug("MAP LAST :" + detailForInsert);
                                        try {
                                            logger.debug("DATE BIRTH :" + detailForInsert.get(DefinitionConstant.datBirth).toString());
                                            java.util.Date birthDates = dbC.parse(detailForInsert.get(DefinitionConstant.datBirth).toString());
                                            logger.debug("DATE BIRTH CONVERT :" + birthDates);
                                            detailForInsert.putAll(cifAcctExtDao.valExistingData(detailForInsert.get(DefinitionConstant.fullName).toString(),
                                                    birthDates,
                                                    detailForInsert.get(DefinitionConstant.nik).toString()));
                                        } catch (Exception e) {
                                            logger.info("FAILED TO VALIDATE NIK :" + e,e);
                                        }
                                        ClassConverterUtil.MapToClass(detailForInsert, cifAcct);
                                        cifAcct.setExtUser(filename.substring(0, 6));
                                        try {
                                            cifAcct.setBatchId(context.get(MapKey.batchNo).toString());
                                            cifAcctExtDao.insert(cifAcct);
                                        } catch (Exception e) {
                                            logger.debug("FAILED TO INSERT :" + e, e);
                                            this.errValue = e.getMessage();
                                            // failed to Insert mainly UNIQUE CONSTRAINT
                                            return false;
                                        }
                                    } else {
                                        logger.info("FIELD NOT SYNCH");
                                    }
                                } else if (values.get(0).toString().equalsIgnoreCase("2")) {
                                    // footer
                                } else if (values.get(0).toString().equalsIgnoreCase("0")) {
                                    // header
                                    if (values.size() == HeaderProp.size()) {
                                        String dateF = df.format(Date.valueOf(values.get(1).toString()));
                                        String dateFcomp = df.format(bankDao.get().getDatProcess());
                                        if (!dateF.equals(dateFcomp)) {
                                            // different date reject whole
                                            logger.debug("DIFF DATE :" + values.get(1));
                                            this.errValue = errDao.get(ErrorPersister.invDateCIF, values.get(1).toString(), "S");
                                            procFlag = false;
                                            break;
                                        }
                                        if (conter.compareTo(Integer.parseInt(values.get(2).toString())) > 0) {
                                            // different total record count more than
                                            logger.debug("DIFF DATE MORE:" + values.get(2));
                                            this.errValue = errDao.get(ErrorPersister.recordNotsame, values.get(2).toString(), "S");
                                            procFlag = false;
                                            break;
                                        } else if (conter.compareTo(Integer.parseInt(values.get(2).toString())) < 0) {
                                            // different total record count less than
                                            logger.debug("DIFF DATE LESS:" + values.get(2));
                                            this.errValue = errDao.get(ErrorPersister.recordNotsame, values.get(2).toString(), "S");
                                            procFlag = false;
                                            break;
                                        }
                                        var = 1;
                                    } else {
                                        logger.debug("INVALID FORMAT :" + values.get(0));
                                        procFlag = false;
                                        this.setErrFlag(StatusDefinition.YES); // failed to finish read file
                                        this.setErrValue("INVALID FORMAT");
                                        this.setErrID(ErrorPersister.fileClosed);
                                    }
                                } else {
                                    // unindentified
                                    logger.debug("INVALID FORMAT :" + values.get(0));
                                    procFlag = false;
                                    this.setErrFlag(StatusDefinition.YES); // failed to finish read file
                                    this.setErrValue("INVALID FORMAT");
                                    this.setErrID(ErrorPersister.fileClosed);
                                }
                            }
                        }
                    }
                } catch (IOException iOException) {
                    logger.debug("FILE NOT FOUND :" + file);
                    logger.info("FILE IO EXCEPTION :" + iOException, iOException);
                    procFlag = false;
                    this.setErrFlag(StatusDefinition.YES); // failed to finish read file
                    this.setErrValue(iOException.toString());
                    this.setErrID(ErrorPersister.fileClosed);
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
                        this.setErrFlag(StatusDefinition.YES); // failed to finish read file
                        this.setErrValue(iOException.toString());
                        this.setErrID(ErrorPersister.fileClosed);
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
     * @param context
     * @param details
     * @param Flag
     * @return
     */
    public StringBuilder ExtDDFRespons(Map context, Map details, Integer Flag) {

        FixQXtract fixQXtract = (FixQXtract) context.get(MapKey.fixQXtract);
        fixQXtract = fixQDao.get(fixQXtract.getqId());

        logger.debug("FIXQXTRACT :" + fixQXtract.toString());
        Integer idTemplate = xtractDao.get(fixQXtract.getIdScheduler()).getFixSchedulerPK().getIdTemplate();
        StringBuilder sBuild = new StringBuilder();

        List<Map> DetailProp = this.paramDao.getFieldParameterbyModuleName(idTemplate, "R", Flag, fixQXtract.getParam4());
        logger.debug("LIST :" + DetailProp);
        int count = 0;
        for (Map stringConvert : DetailProp) {
            //logger.debug("MAP RESP :" + stringConvert);
            // update table for sending Responds
            //logger.debug("FIELD RESP :" + details.get(stringConvert.get(DefinitionConstant.nameOfField)));
            String mapTemp = null;
            try {
                mapTemp = details.get(stringConvert.get(DefinitionConstant.nameOfField)).toString();
            } catch (Exception e) {
                mapTemp = "";
                logger.debug("MISSING FIELD :" + stringConvert.get(DefinitionConstant.nameOfField));
            }
            String lastMap = "";
            if ("Y".equalsIgnoreCase(stringConvert.get(DefinitionConstant.flgMandatory).toString())) {
                String convMap = "";

                try {
                    convMap = stringConvert.get(DefinitionConstant.defValParam).toString();
                } catch (Exception e) {
                    convMap = "";
                }
                if ("".equalsIgnoreCase(mapTemp)) {
                    if ("".equalsIgnoreCase(convMap)) {
                        lastMap = "";
                    } else {
                        lastMap = convMap;
                    }
                } else {
                    lastMap = mapTemp;
                }
                //logger.debug("LOGGER LastMap :" + lastMap);
                sBuild.append(lastMap).append(fixQXtract.getParam2());
            } else {
                if (mapTemp.equalsIgnoreCase("")) {
                    lastMap = "";
                } else {
                    lastMap = mapTemp.concat(fixQXtract.getParam2());
                }
                sBuild.append(lastMap);
            }
            count++;
        }
        logger.debug("STRING BUILD :" + sBuild);
        if (details.get("applicationID") != null) {
            ScreenOpening so = cifAcctExtDao.getNumber(details.get("batchId").toString(), Integer.parseInt(details.get("recId").toString())).get(0);
            if ("1".equalsIgnoreCase(so.getStatus())) {
                so.setStatus("2");
            }
            so.setDtmProc(SchedulerUtil.getTime());
        }
        return sBuild;
    }

    /**
     * 
     * @param fieldId
     * @param moduleName
     * @param fieldName
     * @param formatValue
     * @param value
     * @return
     */
    public Map getReferenceValue(Integer fieldId, String moduleName, String fieldName, String formatValue, String value) {
        // get Reference value of replicated value from fieldParameter
        Map newMap = new HashMap();
        //logger.debug("FORMAT VALUE :" + formatValue);
        List formatter = SchedulerUtil.getParameter(formatValue, ":");
        //logger.debug("FORMAT length :" + formatter.size());
        if (formatter.isEmpty() || formatter.size() == 1) {
            return newMap;
        } else if (formatter.size() == 2) {
            List referenceField = this.paramDao.getFieldParameterbyTemplate(fieldId, Integer.parseInt(formatter.get(1).toString()), Integer.parseInt(formatter.get(0).toString()), moduleName);
            if (referenceField.isEmpty()) {
                return newMap;
                // Reference not found
            } else {
                Map inheret = (Map) referenceField.get(0);
                StringBuilder queryreconstruct = new StringBuilder();
                queryreconstruct.append("SELECT ").append(inheret.get(DefinitionConstant.formatData).toString()).append(" FROM ").append(inheret.get(DefinitionConstant.destTable));
                if (inheret.get(DefinitionConstant.defValParam) != null) {
                    String inquiry = inheret.get(DefinitionConstant.defValParam).toString().replaceFirst(fieldName, value);
                    queryreconstruct.append(" WHERE ").append(inquiry);
                }
                logger.debug("QUERY REC :" + queryreconstruct);
                Map lastStand = (Map) this.paramDao.getValueFromTable(queryreconstruct.toString()).get(0);
                newMap.put(inheret.get(DefinitionConstant.fieldName).toString(), lastStand.get(inheret.get(DefinitionConstant.formatData)));
            }
        }
        return newMap;
    }

    /**
     * @param menuDao the menuDao to set
     */
    public void setMenuDao(MenuRedDao menuDao) {
        logger.info("[ Dao Service MENU] " + menuDao);
        this.menuDao = menuDao;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @param bankDao the bankDao to set
     */
    public void setBankDao(BaBankMastDAO bankDao) {
        this.bankDao = bankDao;
    }

    /**
     * @param importDao the importDao to set
     */
    public void setImportDao(FixSchedulerImportDao importDao) {
        this.importDao = importDao;
    }

    /**
     * @return the errFlag
     */
    public String getErrFlag() {
        return errFlag;
    }

    /**
     * @param errFlag the errFlag to set
     */
    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag;
    }

    /**
     * @return the errValue
     */
    public String getErrValue() {
        return errValue;
    }

    /**
     * @param errValue the errValue to set
     */
    public void setErrValue(String errValue) {
        this.errValue = errValue;
    }

    /**
     * @return the errID
     */
    public String getErrID() {
        return errID;
    }

    /**
     * @param errID the errID to set
     */
    public void setErrID(String errID) {
        this.errID = errID;
    }

    /**
     * @param errDao the errDao to set
     */
    public void setErrDao(ErrorCodeDao errDao) {
        this.errDao = errDao;
    }

    /**
     * @param fixQDao the fixQDao to set
     */
    public void setFixQDao(FixQXtractDao fixQDao) {
        this.fixQDao = fixQDao;
    }

    /**
     * @param xtractDao the xtractDao to set
     */
    public void setXtractDao(FixSchedulerXtractDao xtractDao) {
        this.xtractDao = xtractDao;
    }

    /**
     * @param customerMasterDAO the customerMasterDAO to set
     */
    public void setCustomerMasterDAO(FcrCiCustmastDao customerMasterDAO) {
        this.customerMasterDAO = customerMasterDAO;
    }
}
