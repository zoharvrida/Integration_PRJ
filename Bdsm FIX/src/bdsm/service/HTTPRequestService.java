package bdsm.service;


import bdsm.scheduler.PropertyPersister;
import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


/**
 * @author v00019372
 */
public class HTTPRequestService {
	
    /**
     * 
     * @param URL
     * @param parameters
     * @param sessionId
     * @return
     * @throws Exception
     */
    public static Object[] sendGETRequest(String URL, Map<String, String> parameters, String sessionId) throws Exception {
		return sendGETRequest(URL, parameters, sessionId, true);
	}
	
    /**
     * 
     * @param URL
     * @param parameters
     * @param sessionId
     * @param closeResource
     * @return
     * @throws Exception
     */
    public static Object[] sendGETRequest(String URL, Map<String, String> parameters, String sessionId, boolean closeResource) throws Exception {
		Object[] result = new Object[2];
		HttpGet httpGet = new HttpGet(URL);
		
		URIBuilder builder = new URIBuilder(httpGet.getURI());
		if ((parameters != null) && (!parameters.isEmpty())) {
			Iterator<String> paramNames = parameters.keySet().iterator();
			
			while (paramNames.hasNext()) {
				String paramName = paramNames.next();
				builder.addParameter(paramName, parameters.get(paramName));
			}
		}
		httpGet.setURI(builder.build());
		
		if (!StringUtils.isBlank(sessionId))
			httpGet.addHeader("Cookie", "JSESSIONID=" + sessionId);
		
		HttpClient httpClient = buildClient(httpGet);
		HttpResponse response = httpClient.execute(httpGet);
		if (closeResource) {
			result[0] = response.getStatusLine();
			result[1] = EntityUtils.toString(response.getEntity());
			httpClient.getConnectionManager().shutdown();
		}
		else {
			result[0] = httpClient;
			result[1] = response;
		}
		
		return result;
	}
	
    /**
     * 
     * @param URL
     * @param parameters
     * @return
     * @throws Exception
     */
    public static String getJSessionId(String URL, Map<String, String> parameters) throws Exception {
		String sessionId = null;
		Object[] result = sendGETRequest(URL, parameters, null, false);
		HttpResponse response = (HttpResponse) result[1];
		
		if ((response != null) && (response.containsHeader("Set-Cookie"))) {
			Header setCookieHeader = response.getHeaders("Set-Cookie")[0];
			String[] setCookieValue = setCookieHeader.getValue().split(";");
			
			for (int i = 0; i < setCookieValue.length; i++)
				if (setCookieValue[i].contains("JSESSIONID"))
					sessionId = setCookieValue[i].substring(setCookieValue[i].indexOf('=') + 1);
			
			((HttpClient) result[0]).getConnectionManager().shutdown();
		}
		
		return sessionId;
	}
	
	
    /**
     * 
     * @param URL
     * @param parameters
     * @param sessionId
     * @return
     * @throws Exception
     */
    public static Object[] sendPOSTRequest(String URL, Map<String, String> parameters, String sessionId) throws Exception {
		return sendPOSTRequest(URL, parameters, sessionId, true);
	}
	
    /**
     * 
     * @param URL
     * @param parameters
     * @param sessionId
     * @param closeResource
     * @return
     * @throws Exception
     */
    public static Object[] sendPOSTRequest(String URL, Map<String, String> parameters, String sessionId, boolean closeResource) throws Exception {
		Object[] result = new Object[2];
		HttpPost httpPost = new HttpPost(URL);
		
		if ((parameters != null) && (!parameters.isEmpty())) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator<String> paramNames = parameters.keySet().iterator();
			
			while (paramNames.hasNext()) {
				String paramName = paramNames.next();
				nvps.add(new BasicNameValuePair(paramName, parameters.get(paramName)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		}
		
		if (!StringUtils.isBlank(sessionId))
			httpPost.addHeader("Cookie", "JSESSIONID=" + sessionId);
		
		HttpClient httpClient = buildClient(httpPost);
		HttpResponse response = httpClient.execute(httpPost);
		if (closeResource) {
			result[0] = response.getStatusLine();
			result[1] = EntityUtils.toString(response.getEntity());
			httpClient.getConnectionManager().shutdown();
		}
		else {
			result[0] = httpClient;
			result[1] = response;
		}
		
		return result;
	}
	
    /**
     * 
     * @param URL
     * @param files
     * @param parameters
     * @param sessionId
     * @return
     * @throws Exception
     */
    public static Object[] sendPOSTRequest(String URL, Map<String, File> files, Map<String, String> parameters, String sessionId) throws Exception {
		return sendPOSTRequest(URL, files, parameters, sessionId, true);
	}
	
    /**
     * 
     * @param URL
     * @param files
     * @param parameters
     * @param sessionId
     * @param closeResource
     * @return
     * @throws Exception
     */
    public static Object[] sendPOSTRequest(String URL, Map<String, File> files, Map<String, String> parameters, String sessionId, boolean closeResource) throws Exception {
		Object[] result = new Object[2];
		HttpPost httpPost = new HttpPost(URL);
		
		if ((files == null) || (files.isEmpty()))
			return sendPOSTRequest(URL, parameters, sessionId, closeResource);
		
		MultipartEntity mpe = new MultipartEntity();
		
		// files
		Iterator<String> keyFilenames = files.keySet().iterator();
		while (keyFilenames.hasNext()) {
			String keyFilename = keyFilenames.next();
			mpe.addPart(keyFilename, new FileBody(files.get(keyFilename)));
		}
		
		// parameters
		if ((parameters != null) && (!parameters.isEmpty())) {
			Iterator<String> paramNames = parameters.keySet().iterator();
			
			while (paramNames.hasNext()) {
				String paramName = paramNames.next();
				mpe.addPart(paramName, new StringBody(parameters.get(paramName)));
			}
		}
		httpPost.setEntity(mpe);
		
		if (!StringUtils.isBlank(sessionId))
			httpPost.addHeader("Cookie", "JSESSIONID=" + sessionId);
		
		HttpClient httpClient = buildClient(httpPost);
		HttpResponse response = httpClient.execute(httpPost);
		if (closeResource) {
			result[0] = response.getStatusLine();
			result[1] = EntityUtils.toString(response.getEntity());
			httpClient.getConnectionManager().shutdown();
		}
		else {
			result[0] = httpClient;
			result[1] = response;
		}
		
		return result;
	}
	
	
    /**
     * 
     * @param httpRequest
     * @return
     * @throws Exception
     */
    public static HttpClient buildClient(HttpRequestBase httpRequest) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		
		if (httpRequest.getURI().getScheme().equals("https")) {
			SSLSocketFactory ssf = new SSLSocketFactory(new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}, new AllowAllHostnameVerifier());
			
			SchemeRegistry sr = new SchemeRegistry();
			sr.register(new Scheme("https", httpRequest.getURI().getPort(), ssf));
			ClientConnectionManager ccm = new PoolingClientConnectionManager(sr);
			
			httpClient = new DefaultHttpClient(ccm);
		}
		else {
			httpClient = new DefaultHttpClient();
		}
		
		HttpParams httpParams = httpClient.getParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, PropertyPersister.timeoutHttp * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, PropertyPersister.timeoutHttp * 1000);
		return httpClient; 
	}
	
}
