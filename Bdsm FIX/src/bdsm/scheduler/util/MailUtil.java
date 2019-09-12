package bdsm.scheduler.util;

import bdsm.util.SchedulerUtil;
import bdsm.util.PatternUtil;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.dao.FixLogDao;
import bdsm.scheduler.dao.FixSentBoxDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.fix.MailServiceInstance;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixLogPK;
import bdsm.scheduler.model.FixSentBox;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.MessageBody;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * 
 * @author bdsm
 */
public class MailUtil {

    private static Logger logger = Logger.getLogger(MailUtil.class);
    private static final PatternUtil pattern = new PatternUtil(".{0,};.{0,}");

    /**
     * 
     * @param recipient
     * @param cc
     * @param subject
     * @param body
     * @param filename
     * @param session
     * @param idScheduler
     * @throws Exception
     */
    public static void sentNewMessage(String recipient, String cc, String subject, String body, String filename, Session session, int idScheduler) throws Exception {
        logger.info("Sending New Message to " + recipient);
        logger.debug("Sending New Message to cc " + cc);
        logger.debug("Subject " + subject);
        EmailMessage msg = null;
        FixSentBox fixSentBox = new FixSentBox();
        FixLog fixLog = new FixLog();
        Transaction transaction;
        String filepath = PropertyPersister.dirFixOut;
        try {
            fixLog.setFixLogPK(new FixLogPK(null, SchedulerUtil.getDate("dd/MM/yyyy")));
        } catch (ParseException ex) {
            logger.error(ex);
        }

        boolean hasAttachment = false;

        try {
            fixSentBox.setSentBoxId(SchedulerUtil.generateUUID());
            fixLog.getFixLogPK().setInboxId(fixSentBox.getSentBoxId());
            fixLog.setDtmStartProcess(SchedulerUtil.getTime());
            fixLog.getFixLogPK().setTypFix(StatusDefinition.XTRACT);
            fixLog.setFlgAuth(StatusDefinition.AUTHORIZED);
            fixLog.setSender(PropertyPersister.username);
            fixLog.setFlgProcess(StatusDefinition.PROCESS);
            
            msg = new EmailMessage(MailServiceInstance.getService());
            msg.setSubject(subject);
            fixSentBox.setSubject(subject);

            if (filename != null && !filename.equals("")) {
                hasAttachment = true;
                //checkAttachment(filepath+filename);
                logger.info("Attach file " + filename);
                File file = new File(filepath + filename);
                msg.getAttachments().addFileAttachment(filepath + filename);
                fixSentBox.setEmailAttachment(filename);
                fixLog.getFixLogPK().setFileName(filename);
                fixLog.setFileSize(Integer.valueOf((int) file.length()));
                logger.info("Finish Attach file " + filename);
            }

            if (pattern.match(recipient)) {
                String[] a = recipient.split(";");
                String temp;
                for (int i = 0; i < a.length; i++) {
                    temp = a[i].trim();
                    if (!temp.equals("")) {
                        msg.getToRecipients().add(temp);
                    }
                }
            } else {
                msg.getToRecipients().add(recipient);
            }

            fixSentBox.setEmailTo(recipient);

            if (body != null) {
                msg.setBody(MessageBody.getMessageBodyFromText(body));
                fixSentBox.setEmailBody(body);
            }

            if (cc != null && !cc.equals("")) {
                if (pattern.match(cc)) {
                    String[] a = cc.split(";");
                    String temp;
                    for (int i = 0; i < a.length; i++) {
                        temp = a[i].trim();
                        if (!temp.equals("")) {
                            msg.getCcRecipients().add(temp);
                        }
                    }
                } else {
                    msg.getCcRecipients().add(cc);
                }
                fixSentBox.setEmailCc(cc);
            }
logger.info("MESSAGE SEND" + msg.toString() );
            msg.send();
logger.info("ERROR MESSAGE SEND");
            if (hasAttachment) {
                FileUtil.moveFile(filepath + filename, PropertyPersister.dirFixOutOk, "");
            }
            fixSentBox.setDtmSend(SchedulerUtil.getTime());
            fixLog.setDtmEndProcess(SchedulerUtil.getTime());
            fixLog.setFlgProcess(StatusDefinition.DONE);
        } catch (Exception ex) {
            fixLog.setFlgProcess(StatusDefinition.ERROR);
            fixLog.setReason(ex.getMessage());
            if (hasAttachment) {
                try {
                    FileUtil.moveFile(filepath + filename, PropertyPersister.dirFixOutErr, "");
                } catch (IOException ex1) {
                    logger.error(ex1);
                }
            }
            logger.error(ex, ex);
            Map detLog = new HashMap();
            detLog.put("PROFILE",filename.substring(0, 5));
            detLog.put("FILENAME",filename);
            detLog.put("PROCESS_TYPE",subject);
            
            MailServiceInstance.setServMap(detLog);
            
            /*StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String loggStream = sw.toString();
            if(loggStream.length() >= 4000){
                // loop for split
                
                int logNum = loggStream.length();
                for(int i=0;i<logNum/4000;i++){
                    int sequence = 0;
                    int f = 4000 * i+1;
                    int d = 4000 * i;
                    if (d != 0) {
                        sequence = d+1;
                    } else {
                        sequence = d;
                    }
                    Map detLog = new HashMap();
                    detLog.put("PROFILE",filename.substring(0, 5));
                    detLog.put("FILENAME",filename);
                    detLog.put("LOG",loggStream.substring(sequence,f));
                }
            } else {
                Map detLog = new HashMap();
                detLog.put("PROFILE",filename.substring(0, 5));
                detLog.put("FILENAME",filename);
                detLog.put("LOG",loggStream);
            }
            */
            // Details logger
            logger.info("PROFILE :" + filename.substring(0, 5) + "\n FILENAME :" + filename + "\n TIME :" + SchedulerUtil.getTime());
            logger.info("PROCESS STEP :" + subject);
            
            MailServiceInstance.setService(null);
            throw new Exception("Message is not Sent");
        }
        FixLogDao fixLogDao = new FixLogDao(session);
        FixSentBoxDao fixSentBoxDao = new FixSentBoxDao(session);
        transaction = session.beginTransaction();
        fixLogDao.insert(fixLog);
        fixSentBoxDao.insert(fixSentBox);
        transaction.commit();

        logger.info("Message sent successfully");
    }

    private static void checkAttachment(String filePath) throws FIXException {
        logger.info("Validating Attachment " + filePath);
        File f = new File(filePath);
        /*
        if (f.length() > PropertyPersister.attachmentSizeLimit) {
            throw new FIXException("Attachment exceed limit size");
        }*/
        logger.info("Finish Validating Attachment " + filePath);

    }

    /**
     * 
     * @param subject
     * @return
     */
    public static String createEmailSubject(String subject) {
        return "RE: " + subject.replaceAll("RE: ", "");
    }
}
