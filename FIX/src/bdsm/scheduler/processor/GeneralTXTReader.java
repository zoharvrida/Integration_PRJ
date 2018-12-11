/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.GeneralINTDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.util.FileUtil;
import bdsm.service.GeneralTXTService;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author 00110310
 */
public class GeneralTXTReader extends NewTxtReaderWorker {

    /**
     * 
     * @param context
     */
    public GeneralTXTReader(Map context) {
        super(context);
    }
    
    /**
     * 
     * @return
     */
    @Override
    protected Map rejectAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override

    protected FixQXtract registerQR() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    protected FixQXtract registerQA() {
        return registerQU();
    }

    /**
     * 
     * @param file
     * @param conter
     * @return
     */
    @Override
    protected Boolean process(String file, Integer conter) {
        String batchID = null;
        getLogger().debug("PROCESS START");
        batchID = SchedulerUtil.generateUUID();
        
        ParameterDao pDao = new ParameterDao(session);
        BaBankMastDAO baDao = new BaBankMastDAO(session);
        FixSchedulerImportDao impDao = new FixSchedulerImportDao(session);
        FixSchedulerXtractDao xtractDao = new FixSchedulerXtractDao(session);
        GeneralINTDao genDao = new GeneralINTDao(session);
        GeneralTXTService serv = new GeneralTXTService(impDao, xtractDao, baDao, pDao, genDao, session, context);
        
        Boolean hasil = serv.TxtReader(file, context, delimiter, conter);
        this.context = serv.getContext();
        return hasil;
    }
    
    /**
     * 
     * @return
     */
    @Override
    protected FixQXtract registerQU() {
        getLogger().info("Set FixQXtract for Information to Sender VIA SFTP(U)");
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);
        String FileName = null; // filename
        String fileResp = null;
        String patern = null;
        try{
            fileResp = context.get("fileResponse").toString();
            if(!"".equalsIgnoreCase(fileResp)){
                FileName = fileResp;
            } else {
                patern = context.get(MapKey.fileName).toString();
                if(patern != null){
                try{
                    FileName = FileUtil.getDateTimeFormatedFileName(FilenameUtils.getBaseName(pattern));
                } catch(Exception ex) {
                    FileName = pattern;
                }
                }    
            }
        } catch(Exception ex) {
            FileName = context.get(MapKey.fileName).toString();
        }
        String namTemplate = context.get(MapKey.templateName).toString();
        
        FixSchedulerImportDao fiximpDao = new FixSchedulerImportDao(session);
        FixSchedulerXtract xtracT = fixSchedulerXtractDao.get(namTemplate);
        FixSchedulerImport imporT =fiximpDao.get(namTemplate);
        
        
        getLogger().debug("PATTERN :" + namTemplate);
        Integer impSched = imporT.getFixSchedulerPK().getIdScheduler();
        this.schedID = xtracT.getFixSchedulerPK().getIdScheduler();
        getLogger().debug("IMPORT :" + impSched);
        //this.context.put(MapKey.idScheduler,impSched);
        if (StatusDefinition.YES.equals(this.ErrFlag)) {
            this.fixQXtract.setParam1(StatusDefinition.ERR); // flag Error
            this.fixQXtract.setParam3(this.errValue); // flag Error
            this.fixQXtract.setParam4(namTemplate);
            this.fixQXtract.setParam5(errFile);
        } else {
            this.fixQXtract.setParam1(StatusDefinition.SUCCESS + "|" + impSched); // flag Error
            Map cleanMap = context;
            cleanMap.remove(MapKey.session);
            cleanMap.remove(MapKey.fixQXtract);
            this.fixQXtract.setParam3(cleanMap.toString());
            this.fixQXtract.setParam4(namTemplate);
            try {
                String optExt = (String) context.get(MapKey.extFile);
                if (StringUtils.isBlank(optExt)) {
                    this.fixQXtract.setParam5(FileName + ".resp");
                } else {
                    StringBuffer sb = new StringBuffer(optExt);
                    Date dt = new Date();
                    java.text.DateFormat df;
                    String datePattern;
                    
                    Pattern p = Pattern.compile("[$]D[{]([^}]+)[}]");
                    Matcher m = p.matcher(sb);
                    while (m.find()) {
                        datePattern = m.group().replace("$D{", "").replace("}", "");
                        df = new java.text.SimpleDateFormat(datePattern);
                        sb.replace(m.start(), m.end(), df.format(dt));
                        m = p.matcher(sb);
                    }
                    optExt = sb.toString();
                    
                    String filenameWithoutExt = FilenameUtils.removeExtension(FileName);
                    String originalExt = FilenameUtils.getExtension(FileName);
                    optExt = optExt.replace("$P{orgExt}", originalExt);
                    this.fixQXtract.setParam5(filenameWithoutExt + optExt);
                }
            } catch (Exception e) {
               getLogger().info("EX_FILE :" + e,e);
               this.fixQXtract.setParam5(FileName + ".resp"); 
            }
            
        }
        this.fixQXtract.setParam2("|"); // ErrID
        
        return this.fixQXtract;
    }

    /**
     * 
     * @return
     */
    @Override
    protected boolean valueInterface() {
        return callPackage(1);
    }

    /**
     * 
     * @return
     */
    @Override
    protected boolean valueProcess() {
        return callPackage(2);
    }

    /**
     * 
     * @return
     */
    @Override
    protected boolean valueReject() {
        return callPackage(3);
    }
}