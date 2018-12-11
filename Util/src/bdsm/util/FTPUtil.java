/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 *
 * @author bdsm
 */
public class FTPUtil {

    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    public static String FTPConnect(String message, Map parameter) {
        FTPClient ftp = new FTPClient();
        String serverAddress = null;
        String userId = null;
        String password = null;
        String remoteDirectory = null;
        String fileToFTP = null;
        logger.debug("PARAM TO CONNECT :" + remoteDirectory);

        try {
            ftp.connect(serverAddress);
            try {
                if (!ftp.login(userId, password)) {
                    ftp.logout();
                    return "1";
                }
                try {
                    int reply = ftp.getReplyCode();
                    if (!FTPReply.isPositiveCompletion(reply)) {
                        try {
                            ftp.disconnect();
                        } catch (IOException iOException) {
                            logger.info("2 :" + iOException,iOException);
                        }
                        return "2";
                    }
                    try {
                        ftp.enterLocalPassiveMode();
                        ftp.changeWorkingDirectory(remoteDirectory);
                        int query = 0;
                        // Loop here
                        for (int i = 0; i < query; i++) {
                            // SMS content
                            InputStream input = new ByteArrayInputStream(message.getBytes());
                            ftp.storeFile(fileToFTP, input);
                            input.close();
                        }
                        //enter passive mode
                        try {
                            ftp.logout();
                            ftp.disconnect();
                        } catch (IOException iOException) {
                            logger.info("7 :" + iOException,iOException);
                            return "7";
                        }
                        return "0";
                    } catch (IOException iOException) {
                        logger.info("3 :" + iOException,iOException);
                        return "3";
                    }
                } catch (Exception e) {
                    logger.info("4 :" + e,e);
                    return "4";
                }
            } catch (IOException iOException) {
                logger.info("5 :" + iOException,iOException);
                return "5";
            }
        } catch (IOException iOException) {
            return "6";
        }
    }
}
