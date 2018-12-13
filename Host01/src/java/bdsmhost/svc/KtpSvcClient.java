/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.svc;

import bdsm.util.EncryptionUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author 00030663
 */
public class KtpSvcClient {
	private static final long serialVersionUID = 1L;
    
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * 
     * @param context
     * @param param
     * @return
     */
    public String get(SvcContext context, String param) {
        try {
            Proxy proxy = Proxy.NO_PROXY;
            if (context.isViaProxy()) {
                SocketAddress sa = new InetSocketAddress(context.getProxyIp(), context.getProxyPort());
                proxy = new Proxy(Proxy.Type.HTTP, sa);
            }
            URL url = new URL(context.getWsUrl());        
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            logger.debug("[DEBUG] Using proxy=" + proxy);

            if (context.isViaProxy() && context.isIsProxyNeedAuth()) {
                    String encoded = new String(Base64.encodeBase64((context.getProxyUser() + ":" + context.getProxyPwd()).getBytes()));
                    conn.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
            }

            String key = context.getKtpUser();
            int m = key.length()%16;
            if (m!=0) {
                m=16-m;
                for (int i=0; i<m; i++) {
                    key = key + "_" ;
                }
            }
            if (key.length()>16) key = key.substring(0, 16);
            String pwd = "";
            try {
                pwd = EncryptionUtil.getAES(context.getKtpPwd(), key, Cipher.DECRYPT_MODE);
            } catch (Exception e) {
                logger.fatal("[FATAL] Decrypt password " + context.getKtpPwd() + " with " + key, e);
            }
		/*
			Revision : Inquiry KK
			Enhance Date : 21-Januari-2016
			Enhancer : 00030663
			Begin 1
		*/
            String input = String.format("{\"%s\":\"%s\",\"ip_address\":\"%s\",\"user_id\":\"%s\",\"password\":\"%s\"}", context.getInputName(), param, context.getKtpIpAddr(), context.getKtpUser(), pwd);
/*End 1*/
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            logger.info("[BEGIN] Open connection " + url);
            StringBuilder sb = new StringBuilder();
            try {
                conn.connect();

                logger.debug("[DEBUG] input=" + input);
                logger.debug("[DEBUG] responseCode=" + conn.getResponseCode());
                if (conn.getResponseCode() != 200) {
                    logger.info("[ INFO] responseCode != 200, returning null");
                    return null;
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                try {
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                } finally {
                    br.close();
                }
            } finally {
                conn.disconnect();
            }

            logger.debug("[DEBUG] response=" + sb);
            return sb.toString();
        } catch (Exception e) {
            logger.fatal("[ERROR] " + e.getMessage(), e);
        }
        logger.info("[ INFO] last resort, returning null");
        return null;
    }
    
}
