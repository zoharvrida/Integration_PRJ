/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.ErrorCodeDao;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.service.SingleScreenOPService;
import bdsmhost.dao.CifCasaExtOpeningDao;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 *
 * @author SDM
 */
public class ExtDDFProcessWorker extends TXTReaderWorker {
    
    /**
     * 
     * @param context
     */
    public ExtDDFProcessWorker(Map context) {
        super(context);
    }

    /**
     * 
     * @return
     */
    @Override
    protected Map rejectAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    protected FixQXtract registerQA() {
        getLogger().info("Set FixQXtract for Information to Sender VIA SFTP(A)");
        return valueQxtract();
        
    }

    /**
     * 
     * @return
     */
    @Override
    protected FixQXtract registerQR() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    protected FixQXtract registerQU() {
        getLogger().info("Set FixQXtract for Information to Sender VIA SFTP(U)");
        return valueQxtract();
    }

    /**
     * 
     * @param file
     * @param conter
     * @return
     */
    @Override
    protected Boolean process(String file, Integer conter) {
        // read all linefeed and insert into CIF And Acct Table
        
        ParameterDao pDao = new ParameterDao(session);
        BaBankMastDAO baDao = new BaBankMastDAO(session);
        CifCasaExtOpeningDao caDao = new CifCasaExtOpeningDao(session);
        FixSchedulerImportDao imDao = new FixSchedulerImportDao(session);
        ErrorCodeDao errDao = new ErrorCodeDao(session);
        
        SingleScreenOPService ddfServ = new SingleScreenOPService();
        ddfServ.setCifAcctExtDao(caDao);
        ddfServ.setParamDao(pDao);
        ddfServ.setBankDao(baDao);
        ddfServ.setImportDao(imDao);
        ddfServ.setErrDao(errDao);
        ddfServ.setSession(session);
        
        Integer idScheduler = Integer.parseInt(context.get(MapKey.idScheduler).toString());
        String tempLname = context.get(MapKey.templateName).toString();
        
        getLogger().info("GET DELIMITER ");
        this.delimiter = errDao.get(idScheduler, "1", tempLname, "I").getDelimiter();
        getLogger().info("DELIMITER :" + this.delimiter);

        Boolean sbDet = ddfServ.DDFTxtReader(file, context, this.delimiter, conter);
        this.errValue = ddfServ.getErrValue();
        getLogger().info("ERROR :" + this.errValue);
        try {
            this.tx.commit();
            this.tx = this.session.beginTransaction();
        } catch (HibernateException hibernateException) {
            getLogger().error("ERROR HIBERNATE :" + hibernateException,hibernateException);
            sbDet = false;
            this.errValue = hibernateException.getLocalizedMessage();
        }
        return sbDet;
    }
    private FixQXtract valueQxtract(){
        String namTemplate = context.get(MapKey.templateName).toString();
        FixSchedulerXtractDao fixSchedulerXtractDao = new FixSchedulerXtractDao(session);

        String FileName = FilenameUtils.getBaseName(this.pattern); // filename
        //String patern = fixSchedulerXtractDao.get(namTemplate).getFilePattern();
        this.pattern = namTemplate;
        FixSchedulerXtract xtracT = fixSchedulerXtractDao.get(namTemplate);
        this.schedID = xtracT.getFixSchedulerPK().getIdScheduler();

        getLogger().info("ERRFLAG :" + this.ErrFlag);
        getLogger().debug("XTRACT :" + this.schedID);
        if (StatusDefinition.YES.equals(this.ErrFlag)) {
            this.fixQXtract.setParam1(StatusDefinition.ERR); // flag Error
            this.fixQXtract.setReason(this.errValue);
            //this.fixQXtract.setParam3(this.errValue); // flag Error
            this.fixQXtract.setParam5(errFile);
        } else {
            getLogger().info("Process SUccess Prepare sent to SFTP");

            this.fixQXtract.setParam1(StatusDefinition.SUCCESS); // flag Error
            this.fixQXtract.setParam4(namTemplate);
            this.fixQXtract.setParam5(FileName + ".resp");
        }
        this.fixQXtract.setParam2(this.delimiter); // ErrID
        return this.fixQXtract;
    }

    /**
     * 
     * @return
     */
    @Override
    protected Integer valueInterface() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
