package bdsm.service;


import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import bdsm.scheduler.PropertyPersister;
import bdsm.util.EncryptionUtil;

import com.msn.sknbi.tpk.webservice.Spkintf;


/**
 * @author v00019372
 */
public class SknNgSPKWebService {
	private static Logger LOGGER = Logger.getLogger(SknNgSPKWebService.class);
	
	protected static javax.xml.namespace.QName QNAME;
	
	public static Spkintf CONVENT_SERVICE;
	public static String CONVENT_BIC;
	public static String CONVENT_AUTH;
	public static Spkintf SYARIAH_SERVICE;
	public static String SYARIAH_BIC;
	public static String SYARIAH_AUTH;
	public static String[] ENCRYPTION;
	
	
	static {
		refreshConfiguration();
	}
	
	
	public static void refreshConfiguration() {
		try {
			QNAME = new javax.xml.namespace.QName("http://webservice.tpk.sknbi.msn.com/", "spkintf");
			
			String CONVENT_URL = PropertyPersister.SKNNG_SPK_WS_CONVENT.get("URL");
			CONVENT_BIC = PropertyPersister.SKNNG_SPK_WS_CONVENT.get("BIC");
			CONVENT_AUTH = PropertyPersister.SKNNG_SPK_WS_CONVENT.get("auth");
			
			String SYARIAH_URL = PropertyPersister.SKNNG_SPK_WS_SYARIAH.get("URL");
			SYARIAH_BIC = PropertyPersister.SKNNG_SPK_WS_SYARIAH.get("BIC");
			SYARIAH_AUTH = PropertyPersister.SKNNG_SPK_WS_SYARIAH.get("auth");
			
			ENCRYPTION = new String[] {PropertyPersister.SKNNG_SPK_WS_ENCRYPTION.get("algo"), PropertyPersister.SKNNG_SPK_WS_ENCRYPTION.get("key")};
			
			CONVENT_SERVICE = new Spkintf(new java.net.URL(CONVENT_URL), QNAME);
			SYARIAH_SERVICE = new Spkintf(new java.net.URL(SYARIAH_URL), QNAME);
		}
		catch (Throwable e) {
			LOGGER.error(e, e);
		}
	}
	
	
	public static String getDaftarAlasanPenolakan(Spkintf interface_, String BIC, String auth) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarAlasanPenolakan() start");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDaftarAlasanPenolakan(BIC, auth);
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarAlasanPenolakan() result: " + result);
		
		return result;
	}
	
	public static String getDaftarKota(Spkintf interface_, String BIC, String auth) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarKota() start");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDaftarKota(BIC, auth);
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarKota() result: " + result);
		
		return result;
	}
	
	public static String getDaftarPeserta(Spkintf interface_, String BIC, String auth) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarPeserta() start");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDaftarPeserta(BIC, auth);
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarPeserta() result: " + result);
		
		return result;
	}

	public static String getDaftarPesertaWilayah(Spkintf interface_, String BIC, String auth) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarPesertaWilayah() start");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDaftarPesertaWilayah(BIC, auth);
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarPesertaWilayah() result: " + result);
		
		return result;
	}
	
	public static String getDaftarPropinsi(Spkintf interface_, String BIC, String auth) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarPropinsi() start");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDaftarPropinsi(BIC, auth);
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarPropinsi() result: " + result);
		
		return result;
	}
	
	public static String getDaftarWilayahKliring(Spkintf interface_, String BIC, String auth) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarWilayahKliring() start");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDaftarWilayahKliring(BIC, auth);
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		
		LOGGER.debug("[DEBUG] SknWsClient.getDaftarWilayahKliring() result: " + result);
		
		return result;
	}

	public static String getDKEKreditIndividual(Spkintf interface_, String BIC, String auth, String period, String cityCode) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDKEKreditIndividual() start\n");
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDKEKreditInwardIndividual(BIC, auth, BIC, period, cityCode);
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		LOGGER.debug("[DEBUG] SknWsClient.getDKEKreditIndividual() result: \n" + result);
		return result;
	}
	public static String sendDKEKreditIndividual(Spkintf interface_, String BIC, String auth, String data) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEKreditIndividual() start:\n\"" + data + "\"\n");
		byte[] message = isEncrypted()? encryptMessage(data, ENCRYPTION): data.getBytes();
		LOGGER.debug("encrypted: \"" + bdsm.util.EncryptionUtil.byteArrayToHexString(message) + "\"");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().sendDKEKreditIndividual(BIC, auth, message);
		
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEKreditIndividual() result: \n" + result);
		
		return result;
	}
	
	public static String sendDKEPenyerahanIndividual(Spkintf interface_, String BIC, String auth, String data) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEPenyerahanIndividual() start: \n" + data);
		byte[] message = isEncrypted()? encryptMessage(data, ENCRYPTION): data.getBytes();
		LOGGER.debug("encrypted: \"" + bdsm.util.EncryptionUtil.byteArrayToHexString(message) + "\"");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().sendDKEPenyerahanIndividual(BIC, auth, message);
		
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEPenyerahanIndividual() result: \n" + result);
		
		return result;
	}
	
	public static String sendDKEPengembalianIndividual(Spkintf interface_, String BIC, String auth, String data) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEPengembalianIndividual() start: \n" + data);
		byte[] message = isEncrypted()? encryptMessage(data, ENCRYPTION): data.getBytes();
		LOGGER.debug("encrypted: \"" + bdsm.util.EncryptionUtil.byteArrayToHexString(message) + "\"");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().sendDKEPengembalianIndividual(BIC, auth, message);
		
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEPengembalianIndividual() result: \n" + result);
		
		return result;
	}
	
	public static String getDKEPenyerahanInwardBulk(Spkintf interface_, String BIC, String auth, String benefBIC) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.getDKEPenyerahanInwardBulk() start\n");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().getDKEPenyerahanInwardBulk(BIC, auth, benefBIC);
		
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		LOGGER.debug("[DEBUG] SknWsClient.getDKEPenyerahanInwardBulk() result: \n" + result);
		
		return result;
	}
	
	public static String sendDKEPengembalianBulk(Spkintf interface_, String BIC, String auth, String data) throws Exception {
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEPengembalianBulk() start: \n" + data);
		byte[] message = isEncrypted()? encryptMessage(data, ENCRYPTION): data.getBytes();
		LOGGER.debug("encrypted: \"" + bdsm.util.EncryptionUtil.byteArrayToHexString(message) + "\"");
		
		byte[] bResult = interface_.getTPKWebServiceImplPort().sendDKEPengembalianBulk(BIC, auth, message);
		
		String result = isEncrypted()? decryptMessage(bResult, ENCRYPTION): new String(bResult);
		LOGGER.debug("[DEBUG] SknWsClient.sendDKEPengembalianBulk() result: \n" + result);
		
		return result;
	}
	
	
	
	protected static boolean isEncrypted() {
		return PropertyPersister.SKNNG_SPK_WS_IS_ENCRYPT;
	}
	
	protected static byte[] encryptMessage(String message, String[] encryption) throws Exception {
		// length of characters must be multiple of 8
		message = bdsm.util.BdsmUtil.rightPad(
						message, 
						(int) (Math.round((message.length() * 1.0 / 8) + 0.4999) * 8), 
						' '
					);
		
		if (EncryptionUtil.DES3ALGO.equals(encryption[0]))
			return (byte[]) EncryptionUtil.getDES3Algo(message, encryption[1], Cipher.ENCRYPT_MODE, false);
		else
			return null;
	}
	
	protected static String decryptMessage(byte[] message, String[] encryption) throws Exception {
		if (EncryptionUtil.DES3ALGO.equals(encryption[0]))
			return (String) EncryptionUtil.getDES3Algo(message, encryption[1], Cipher.DECRYPT_MODE, false);
		else
			return null;
	}
	
}
