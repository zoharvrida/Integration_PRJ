/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author bdsm
 */
public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class);
    private static String CHARSET = "UTF-8";

    public static String request(String pUrl, Map<String, String> params) {
        logger.info("[Begin] HttpUtil.request()");
        URI uri = null;
        try {
            logger.debug("URL: " + pUrl);
            uri = new URI(pUrl);
        } catch (URISyntaxException ex) {
            logger.fatal(ex, ex);
        }
        
        logger.debug("--- Request Begin --- ");
        logger.debug("Data: " + params);
        logger.debug("--- Request End --- ");

        HttpClient httpclient = null;
        if ("https".equals(uri.getScheme())) {
            logger.debug("HTTPS URI: " + uri);
            try {
                SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy() {

                    //@Override
                    public boolean isTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                        logger.debug("HTTPS IS TRUSTED : " + authType + "CERT LENGTH :" + chain.length);
                        return true;
                    }
                }, new AllowAllHostnameVerifier());
                
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", uri.getPort(), sf));
                ClientConnectionManager ccm = new ThreadSafeClientConnManager(registry);
                httpclient = new DefaultHttpClient(ccm);

            } catch (Exception e) {
                logger.fatal(e, e);
            }
        } else {
            httpclient = new DefaultHttpClient();
        }

        try {
            String host = uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort();
            String path = uri.getPath();
            logger.debug("Host: " + host);
            logger.debug("Path: " + path);
			
            HttpPost httpPost = new HttpPost(uri);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = httpclient.execute(httpPost);
            if (response == null) {
                return null;
            }
            String result = EntityUtils.toString(response.getEntity());
            logger.debug("--- Response Begin --- ");
            logger.debug(result);
            logger.debug("--- Response End   --- ");
            return result;
        } catch (IOException ex) {
            logger.fatal(ex, ex);
        } catch (Exception es){
            logger.fatal(es, es);
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
            logger.info("[ End ] HttpUtil.request()");
        }
        return null;
    }
    public static String request(String pUrl, Map<String, String> params, int timeout) {
        logger.info("[Begin] HttpUtil.request()");
        URI uri = null;
        try {
            logger.debug("URL: " + pUrl);
            uri = new URI(pUrl);
        } catch (URISyntaxException ex) {
            logger.fatal(ex, ex);
        }

        HttpClient httpclient = null;
        if ("https".equals(uri.getScheme())) {
            try {
                SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy() {

                    //@Override
                    public boolean isTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                        return true;
                    }
                }, new AllowAllHostnameVerifier());

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", uri.getPort(), sf));
                ClientConnectionManager ccm = new ThreadSafeClientConnManager(registry);
                httpclient = new DefaultHttpClient(ccm);

            } catch (Exception e) {
                logger.fatal(e, e);
            }
        } else {
            httpclient = new DefaultHttpClient();
        }

        try {
            String host = uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort();
            String path = uri.getPath();
            logger.debug("Host: " + host);
            logger.debug("Path: " + path);

			HttpParams httpParams = httpclient.getParams();
            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            HttpPost httpPost = new HttpPost(uri);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = httpclient.execute(httpPost);
            if (response == null) {
                return null;
            }
            String result = EntityUtils.toString(response.getEntity());
            logger.debug("--- Response Begin --- ");
            logger.debug(result);
            logger.debug("--- Response End   --- ");
            return result;
        } catch (IOException ex) {
            logger.fatal(ex, ex);
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
            logger.info("[ End ] HttpUtil.request()");
        }
        return null;
    }
    public String commonRequest(Map Request, String SC, String Command, String Token, String TzToken){
        String result = null;
        HashMap resultMap = null;
        Request.put("token", BdsmUtil.generateToken(Token, TzToken));
        result = HttpUtil.request(SC + Command, Request);
        return result;
    }
}
