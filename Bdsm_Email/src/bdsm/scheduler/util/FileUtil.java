/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.util;

import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.model.FixLog;
import bdsm.scheduler.model.FixLogPK;
import bdsm.util.BdsmUtil;
import bdsm.util.EncryptionUtil;
import bdsm.util.Hashf;
import bdsm.util.SchedulerUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import javax.crypto.Cipher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author bdsm
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    public static String getDateTimeFormatedFileName(String fileformat) {
        String[] a = fileformat.replaceAll("(\\{|\\})", ",").split(",");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(a[1]);
        Date date = new Date();
        return a[0] + sdf.format(date) + a[2];
    }
    public static String getDateTimeFormatedString(String fileformat) {
        String[] a = fileformat.replaceAll("(\\{|\\})", ",").split(",");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(a[1]);
        Date date = new Date();
        return a[0] + sdf.format(date);
    }    

    public static String getDateTimeFormatedFileNameWithPath(String filePath) {
        File file = new File(filePath);
        String a[] = file.getName().replaceAll("(\\{|\\})", ",").split(",");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(a[1]);
        Date date = new Date();
        return file.getParent() + System.getProperty("file.separator") + a[0] + sdf.format(date);
    }

    public static String getTemplateFromFileName(String filename) {
        String[] a = filename.replace(System.getProperty("file.separator"), "\"").split("\"");
        filename = a[a.length - 1].substring(0, 6);
        return filename;//.replaceAll("\\d{1,}.{1,}", "");
    }

    public static String getOriginalName(String zipname){
        StringBuilder name = new StringBuilder();
        List StringToken = new ArrayList();
        StringTokenizer myToken = new StringTokenizer(zipname, ".");
        while(myToken.hasMoreTokens()){
            StringToken.add(myToken.nextToken());
        }
        name.append(StringToken.get(0)).append(".").append(StringToken.get(1));
        return name.toString();
    }
    
    public static String getRealFileName(String fullpathName){
        StringBuilder name = new StringBuilder();
        List StringToken = new ArrayList();
        StringTokenizer myToken = new StringTokenizer(fullpathName, "/");
        while(myToken.hasMoreTokens()){
            StringToken.add(myToken.nextToken());
        }
        name.append(StringToken.get(StringToken.size()-1));
        return name.toString();
    }
    
    public static FixLog sftpToFCR(String ip, String port,
            String userid, String password, String privateKey,
            String srcFilepath, String destFilepath, int idScheduler) {
        logger.info("Starting SFTP Transfer");
        ChannelSftp sftpChannel = null;
        Session session = null;
        FixLog fixLog = new FixLog();
        try {
            logger.info("Collect SFTP Parameter");
            fixLog.setFixLogPK(new FixLogPK(srcFilepath, SchedulerUtil.getDate("dd/MM/yyyy")));
            File file = new File(PropertyPersister.dirFixOut+srcFilepath);
            fixLog.setFileSize(Integer.valueOf((int) file.length()));
            fixLog.setFcrFileName(srcFilepath);
            fixLog.setFlgAuth(StatusDefinition.AUTHORIZED);
            fixLog.setFlgProcess(StatusDefinition.PROCESS);
            fixLog.getFixLogPK().setTypFix(StatusDefinition.XTRACT);
            fixLog.setDtmStartProcess(SchedulerUtil.getTime());
            fixLog.setIdScheduler(idScheduler);

            JSch jsch = new JSch();
            logger.info("Initiate SFTP Private Key");
            if (!StringUtils.isBlank(privateKey)) {
                jsch.addIdentity(privateKey);
            }
            logger.info(("Initiate SFTP Session"));
            session = jsch.getSession(userid, ip, Integer.valueOf(port));
            
            logger.info("Initiate SFTP Password");
            if ((StringUtils.isBlank(privateKey)) && (!StringUtils.isBlank(password))) {
                password = EncryptionUtil.getAES(password, (userid + "@@@@@@@@@@@@@@@@").substring(0, 16), Cipher.DECRYPT_MODE);
                session.setPassword(password);
            }
            
            logger.info("Try to Session connect SFTP Server");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            
            logger.info("Try to Channel connect SFTP Server");
            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftpChannel = (ChannelSftp) channel;
            
            logger.info("Begin transfer file to SFTP Server");
            logger.debug ("ALL VALUE :" + PropertyPersister.dirFixOut+srcFilepath + " " + destFilepath + " " + ip + " " + privateKey);
            sftpChannel.put(PropertyPersister.dirFixOut+srcFilepath, destFilepath);
            fixLog.setDtmEndProcess(SchedulerUtil.getTime());
            fixLog.setFlgProcess(StatusDefinition.DONE);
            moveFile(PropertyPersister.dirFixOut+srcFilepath, PropertyPersister.dirFixOutOk, "Enc");
        } catch (Exception ex) {
            fixLog.setFlgProcess(StatusDefinition.ERROR);
            fixLog.setDtmEndProcess(SchedulerUtil.getTime());
            fixLog.setReason(ex.getMessage());
            logger.error(ex,ex);
            try {
                moveFile(PropertyPersister.dirFixOut+srcFilepath, PropertyPersister.dirFixOutErr, "Enc");
            } catch (Exception ex1) {
                logger.error("Error Moving file to " + PropertyPersister.dirFixOutErr);
                logger.error(ex1, ex1);
            }
        } finally {
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return fixLog;
    }

    //decrypt to orginal filepath
    public static String decrypt(String filename, String algo, String password, String prefix) throws Exception {
        String newFileName;
        String keyDES = BdsmUtil.leftPad(algo, 48, '@');
        try {
            if (algo.equalsIgnoreCase(EncryptionUtil.ZIPALGO) && (StringUtils.isBlank(password))) {
                password = null;
            } else {
                password = EncryptionUtil.getAES(password,(algo+"@@@@@@@@@@@@@@@@").substring(0,16),Cipher.DECRYPT_MODE);
            }
            newFileName = filename;
            String temp = filename + ".temp";
            logger.info("Decrypt Using : " + algo);
            if (algo.toUpperCase().equals(EncryptionUtil.HASHFALGO)) {
                Hashf.Decrypt(filename, temp, password);
            } else if (algo.toUpperCase().equals(EncryptionUtil.RC4ALGO)) {
                EncryptionUtil.RC4Algo(filename, temp, password);
            }else if (algo.toUpperCase().equals(EncryptionUtil.RC4ALGO2)) {
                EncryptionUtil.RC4Algo2(filename, temp, password);
            } else if (algo.toUpperCase().equals(EncryptionUtil.AESALGO)) {
                EncryptionUtil.AESAlgo(filename, temp, password, Cipher.DECRYPT_MODE);
            } else if (algo.toUpperCase().equals(EncryptionUtil.DESALGO)) {
                EncryptionUtil.DESAlgo(filename, temp, password, Cipher.DECRYPT_MODE);
            } else if (algo.toUpperCase().equals(EncryptionUtil.ZIPALGO)) {
                File file = new File(filename);
                newFileName = EncryptionUtil.ZIPdecompress(filename, file.getParent(), password);
                moveFile(filename, PropertyPersister.dirFixInOk, prefix);
                return newFileName;
            } else if (algo.toUpperCase().equals(EncryptionUtil.DES3ALGO)) {
                EncryptionUtil.DES3AlgoDec(filename, temp, password);
            }
            moveFile(filename, PropertyPersister.dirFixInOk, prefix);
            File file = new File(temp);
            if (file.renameTo(new File(filename)) == false){
                logger.info("FAILED TO RENAME");
                FileUtils.moveFile(file, new File(filename));
            } 
            //file.renameTo(new File(filename));
            newFileName = file.getName().replaceAll(".temp", "");
        } catch (Exception ex) {
            logger.info("Error Decrypting " + filename);
            logger.error(ex);
            moveFile(filename, PropertyPersister.dirFixInErr, prefix);
            throw new Exception(ex);
        }
        return newFileName;
    }
    public static String encrypt(String filename, String zipName ,String algo, String password) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(filename).append("|").append(zipName);
        return encrypt(sb.toString(), algo, password, true);
    }

    public static String encrypt(String filename, String algo, String password) throws Exception {
        return encrypt(filename, algo, password, true);
    }

    //encrypt to original filepath
    public static String encrypt(String filename, String algo, String password, boolean backup) throws Exception {
        String newFileName;
        String keyDES = BdsmUtil.leftPad(algo, 48, '@');
        try {

            password = EncryptionUtil.getAES(password,(algo+"@@@@@@@@@@@@@@@@").substring(0,16),Cipher.DECRYPT_MODE);
            String temp = filename + ".temp";
            logger.info("Encrypt Using " + algo);
            if (algo.toUpperCase().equals(EncryptionUtil.HASHFALGO)) {
                Hashf.Encrypt(filename, temp, password);
            } else if (algo.toUpperCase().equals(EncryptionUtil.RC4ALGO)) {
                EncryptionUtil.RC4Algo(filename, temp, password);
            }else if (algo.toUpperCase().equals(EncryptionUtil.RC4ALGO2)) {
                EncryptionUtil.RC4Algo2(filename, temp, password);
            } else if (algo.toUpperCase().equals(EncryptionUtil.AESALGO)) {
                EncryptionUtil.AESAlgo(filename, temp, password, Cipher.ENCRYPT_MODE);
            } else if (algo.toUpperCase().equals(EncryptionUtil.DESALGO)) {
                EncryptionUtil.DESAlgo(filename, temp, password, Cipher.ENCRYPT_MODE);
            } else if (algo.toUpperCase().equals(EncryptionUtil.ZIPALGO)) {
                List fileNameList = SchedulerUtil.getParameter(filename, ";");
                String newFilename = null;
                if(fileNameList.size()==1){
                    newFilename = EncryptionUtil.ZIPcompress(filename, filename + ".zip", password);
                } else {
                    newFilename = EncryptionUtil.ZIPcompress(filename, filename , password);
                    logger.debug("ZIP ARR :" + filename);
                }
                             
                if (backup)
                    if(fileNameList.size()==1){
                        moveFile(filename, PropertyPersister.dirFixOutOk, "");
                    } 
                return newFilename;
            } else if (algo.toUpperCase().equals(EncryptionUtil.DES3ALGO)) {
                EncryptionUtil.DES3AlgoEnc(filename, temp, password);
            }
            if (backup)
                moveFile(filename, PropertyPersister.dirFixOutOk, "");
            else
                FileUtils.deleteQuietly(new File(filename));
            File file = new File(temp);
            file.renameTo(new File(filename));
            newFileName = file.getName().replaceAll(".temp", "");
            logger.info("HASIL FILENAME : " + newFileName);
        } catch (Exception ex) {
            logger.info("Error Encrypting " + filename);
            logger.error(ex, ex);
            if (backup)
                moveFile(filename, PropertyPersister.dirFixOutErr, "");
            throw new Exception(ex);
        }
        return newFileName;
    }

    public static void moveFile(String filename, String dest, String prefix) throws Exception {
        File file = new File(filename);
        File file2 = new File(getDateTimeFormatedFileNameWithPath(filename + ".{yyyyMMddHHmmss}"));
        FileUtils.moveFile(file, new File(dest + prefix + file2.getName()));
    }
    public static void delFile(String filename)throws Exception {
        File file = new File(filename);
        FileUtils.deleteQuietly(file);
    }
    public static void writeWithFileChannerDMA(String ParamString, String outputFile) {

        long count = ParamString.getBytes().length;
        logger.info("COUNT :" + count);
        InputStream inputStream = new ByteArrayInputStream(ParamString.getBytes(Charset.forName("UTF-8")));
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(new File(outputFile), "rw");
            // move the cursor to the end of the file
            randomAccessFile.seek(randomAccessFile.length());

            // obtain the a file channel from the RandomAccessFile
            try {
                FileChannel fileChannel = randomAccessFile.getChannel();
                ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
                fileChannel.position(randomAccessFile.length());
                fileChannel.transferFrom(inputChannel,randomAccessFile.length(),count);

            } catch (IOException e) {
                logger.info("FAILED READ TO CHANNEL " + e, e);
            }
        } catch (IOException e) {
            logger.info("FAILED READ TO RAM " + e, e);
        } finally{
            if(randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException ex) {
                    logger.info("FAILED READ TO CLOSE " + ex,ex);
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.info("FAILED INPUT STREAM TO CLOSE " + ex,ex);
                }
            }
}
    }
}

