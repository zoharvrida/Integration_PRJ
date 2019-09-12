package bdsm.scheduler.processor;


import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import bdsm.model.EKTPReaderDevice;
import bdsm.model.EKTPReaderUser;
import bdsm.model.MasterUser;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.FixClassConfigDao;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.util.SchedulerUtil;
import bdsm.util.BdsmUtil;
import bdsm.util.EncryptionUtil;
import bdsmhost.dao.EKTPReaderDeviceDAO;
import bdsmhost.dao.EKTPReaderUserDAO;
import bdsmhost.dao.MasterUserDao;


/**
 * @author v00019372
 */
public class EKTPReaderWorker extends BaseProcessor {
	private static final Logger LOGGER = Logger.getLogger(EKTPReaderWorker.class);
	protected static String ROOT_SERVICE;
	protected static Map<String, String> PROTOCOLS;


	static {
		refreshConfiguration();
	}

	public EKTPReaderWorker(Map<String, ? extends Object> context) {
		super(context);
	}


	public static void refreshConfiguration() {
		ROOT_SERVICE = PropertyPersister.EKTP_READER_BASE_RESOURCE_URI;
		PROTOCOLS = PropertyPersister.EKTP_READER_BASE_PROTOCOLS;
	}

	@Override
	protected boolean doExecute() throws Exception {
		String batchNo = this.context.get(MapKey.param6).toString();
		String param5 = this.context.get(MapKey.param5).toString();
		
		EKTPReaderDeviceDAO EKTPDeviceDAO = new EKTPReaderDeviceDAO(this.session);
		EKTPReaderUserDAO EKTPUserDAO = new EKTPReaderUserDAO(this.session);
		MasterUserDao masterUserDAO = new MasterUserDao(this.session);
		FixClassConfigDao fccDAO = new FixClassConfigDao(this.session);
		FixQXtractDao fqxDAO = new FixQXtractDao(this.session);
		List<Object[]> listTempData;
		
		String idUser;
		String role;
		String password, passwordEncrypted;
		String operation;
		String idCreatedBy;
		String idCreatedSpv;
		String serviceName = "";
		MediaType mediaType;
		StringBuffer reason = new StringBuffer();
		Integer codeCCBranch = -1;
		MasterUser user;
		int failed;
		List<EKTPReaderDevice> deviceList = new java.util.ArrayList<EKTPReaderDevice>(0);
		
		
		FixQXtract currentFQX = (FixQXtract) this.context.get(MapKey.fixQXtract);
		String profileName = currentFQX.getReason().split("\\|")[1]; 
		
		
		if ("EKTPRD".equals(profileName)) { // EKTP Reader Device
			listTempData = EKTPDeviceDAO.listTemporaryByBatchNo(batchNo, "P"); // Pending device status
			
			for (Object[] tempDevice: listTempData) {
				codeCCBranch = Integer.valueOf((String) tempDevice[0]);
				String deviceIP = (String) tempDevice[1];
				String deviceName = (String) tempDevice[2];
				idUser = (String) tempDevice[3];
				password = (String) tempDevice[4];
				operation = String.valueOf((Character) tempDevice[5]).toUpperCase();
				idCreatedBy = (String) tempDevice[6];
				idCreatedSpv = (String) tempDevice[7];
				reason.delete(0, reason.length());
				
				if ("U".equals(operation)) {
					passwordEncrypted = EncryptionUtil.getAES(password, BdsmUtil.rightPad(idUser, 16, '_'), javax.crypto.Cipher.ENCRYPT_MODE);
					EKTPReaderDevice device = null;
					
					if (StringUtils.isBlank(deviceIP))
						device = EKTPDeviceDAO.getByCCandName(codeCCBranch, deviceName);
					else
						device = EKTPDeviceDAO.getByCCandIP(codeCCBranch, deviceIP);
					
					
					Form form = new Form();
					serviceName = "changePass";
					form.param("username", device.getSAUsername());
					form.param("password", device.getSAPassword());
					form.param("user", device.getSAUsername());
					form.param("newpass", passwordEncrypted);
					
					try {
						Response response = this.requestWSRest(device.getProtocol(), device.getIP(), serviceName, form);
						if (response.getStatus() == Response.Status.OK.getStatusCode()) {
							LOGGER.debug("EKTPRD OK Response");
							mediaType = response.getMediaType();
							String message = "";
							
							if (mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
								LOGGER.debug("EKTPRD OK Response");
								message = response.readEntity(String.class);
								@SuppressWarnings("unchecked")
								Map<String, Object> map = new Gson().fromJson(message, Map.class);
								message = (String) map.get("messagereturn");
								LOGGER.debug("messagereturn: " + message);
								
								if (((Double) map.get("errornumber")).intValue() == 0) {
									java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());
									device.setSAPassword(passwordEncrypted);
									device.setIdUpdatedBy(idCreatedBy);
									device.setDtmUpdated(currentTime);
									device.setIdUpdatedSpv(idCreatedSpv);
									device.setDtmUpdatedSpv(currentTime);
								}
								else
									throw new Exception(message);
							}
						}
						else
							throw new Exception("RespCode:" + response.getStatus());
					}
					catch (Exception ex) {
						LOGGER.error("device '" + device + ": " + ex.getMessage(), ex);
						reason.append(device.getName() + ":" + ex.getMessage());
					}
				}
				
				EKTPDeviceDAO.updateTemporary(batchNo, codeCCBranch, deviceName, deviceIP, (reason.length() > 0)? "E": "D", reason.toString());
			}
		}
		else if ("EKTPRU".equals(profileName)) { // EKTP Reader User
			listTempData = EKTPUserDAO.listTemporaryByBatchNo(batchNo, "P"); // Pending user status
			
			for (Object[] tempUser : listTempData) {
				idUser = (String) tempUser[0];
				role = ((String) tempUser[1]).toUpperCase();
				password = (String) tempUser[2];
				operation = String.valueOf((Character) tempUser[3]).toUpperCase();
				idCreatedBy = (String) tempUser[4];
				idCreatedSpv = (String) tempUser[5];
				reason.delete(0, reason.length());
				failed = 0;
				user = masterUserDAO.get(idUser);
				
				if (!codeCCBranch.equals(user.getCdBranch())) {
					codeCCBranch = user.getCdBranch();
					deviceList = EKTPDeviceDAO.listByCodeCCBranch(codeCCBranch);
				}
				
				LOGGER.info("User: " + idUser + ", Role: " + role + ", Operation: " + operation);
				
				for (EKTPReaderDevice device : deviceList) {
					Form form = new Form();
					form.param("username", device.getSAUsername());
					form.param("password", device.getSAPassword());
					
					if ("OPR".equals(role)) {
						if ("I".equals(operation)) { // Insert
							passwordEncrypted = EncryptionUtil.getAES(password, BdsmUtil.rightPad(idUser, 16, '_'), javax.crypto.Cipher.ENCRYPT_MODE);
							serviceName = "createUser";
							form.param("nama_to_create", user.getNamUser());
							form.param("username_to_create", idUser);
							form.param("password_to_create", passwordEncrypted);
							form.param("roleid", "2");
							
							try {
								Response response = this.requestWSRest(device.getProtocol(), device.getIP(), serviceName, form);
								
								if (response.getStatus() == Response.Status.OK.getStatusCode()) {
									mediaType = response.getMediaType();
									String message = "";
									
									if (mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
										message = response.readEntity(String.class);
										@SuppressWarnings("unchecked")
										Map<String, Object> map = new Gson().fromJson(message, Map.class);
										message = (String) map.get("messagereturn");
										
										if (((Double) map.get("errornumber")).intValue() == 0) {
											EKTPReaderUser u = new EKTPReaderUser();
											u.setCodeCCBranch(codeCCBranch);
											u.setIdUser(user.getIdUser());
											u.setDeviceName(device.getName());
											u.setRole(role);
											u.setPassword(passwordEncrypted);
											u.setIdMaintainedBy(idCreatedBy);
											u.setIdMaintainedSpv(idCreatedSpv);
											EKTPUserDAO.insert(u);
										}
										else
											throw new Exception(message);
									}
								}
								else
									throw new Exception("RespCode:" + response.getStatus());
							}
							catch (Exception ex) {
								LOGGER.error("device '" + device + ": " + ex.getMessage(), ex);
								reason.append(device.getName() + ":" + ex.getMessage() + "\n");
								failed++;
							}
						}
						else { // Delete
							serviceName = "hapusUser";
							form.param("username_to_delete", idUser);
							
							try {
								Response response = this.requestWSRest(device.getProtocol(), device.getIP(), serviceName, form);
								if (response.getStatus() == Response.Status.OK.getStatusCode()) {
									mediaType = response.getMediaType();
									String message = "";
									
									if (mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
										message = response.readEntity(String.class);
										@SuppressWarnings("unchecked")
										Map<String, Object> map = new Gson().fromJson(message, Map.class);
										message = (String) map.get("messagereturn");
										
										if (((Double) map.get("errornumber")).intValue() == 0) {
											EKTPReaderUser u = EKTPUserDAO.get(new EKTPReaderUser(user.getIdUser(), codeCCBranch, device.getName()));
											if (u != null) 
												EKTPUserDAO.delete(u);
											else
												throw new Exception("user doesn't exist in table");
										}
										else
											throw new Exception(message);
									}
								}
								else
									throw new Exception("RespCode:" + response.getStatus());
							}
							catch (Exception ex) {
								LOGGER.error("device '" + device + ": " + ex.getMessage(), ex);
								reason.append(device.getName() + ":" + ex.getMessage() + "\n");
								failed++;
							}
						}
					}
					else {
						if ("I".equals(operation)) { // Insert
							passwordEncrypted = EncryptionUtil.getAES(password, BdsmUtil.rightPad(idUser, 16, '_'), javax.crypto.Cipher.ENCRYPT_MODE);
							serviceName = "addBypassAuth";
							form.param("nama", user.getNamUser());
							form.param("nrp", idUser);
							form.param("jabatan", "supervisor");
							form.param("pin", passwordEncrypted);
							
							try {
								Response response = this.requestWSRest(device.getProtocol(), device.getIP(), serviceName, form);
								if (response.getStatus() == Response.Status.OK.getStatusCode()) {
									mediaType = response.getMediaType();
									String message = "";
									
									if (mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
										message = response.readEntity(String.class);
										@SuppressWarnings("unchecked")
										Map<String, Object> map = new Gson().fromJson(message, Map.class);
										message = (String) map.get("messagereturn");
										
										if (((Double) map.get("errornumber")).intValue() == 0) {
											EKTPReaderUser u = new EKTPReaderUser();
											u.setCodeCCBranch(codeCCBranch);
											u.setIdUser(user.getIdUser());
											u.setDeviceName(device.getName());
											u.setRole(role);
											u.setPassword(passwordEncrypted);
											u.setIdMaintainedBy(idCreatedBy);
											u.setIdMaintainedSpv(idCreatedSpv);
											EKTPUserDAO.insert(u);
										}
										else
											throw new Exception(message);
									}
								}
								else
									throw new Exception("RespCode:" + response.getStatus());
							}
							catch (Exception ex) {
								LOGGER.error("device '" + device + ": " + ex.getMessage(), ex);
								reason.append(device.getName() + ":" + ex.getMessage() + "\n");
								failed++;
							}
						}
						else { // Delete
							serviceName = "deleteBypassAuth";
							form.param("nrp", idUser);
							
							try {
								Response response = this.requestWSRest(device.getProtocol(), device.getIP(), serviceName, form);
								if (response.getStatus() == Response.Status.OK.getStatusCode()) {
									mediaType = response.getMediaType();
									String message = "";
									
									if (mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
										message = response.readEntity(String.class);
										@SuppressWarnings("unchecked")
										Map<String, Object> map = new Gson().fromJson(message, Map.class);
										message = (String) map.get("messagereturn");
										
										if (((Double) map.get("errornumber")).intValue() == 0) {
											EKTPReaderUser u = EKTPUserDAO.get(new EKTPReaderUser(user.getIdUser(), codeCCBranch, device.getName()));
											if (u != null) 
												EKTPUserDAO.delete(u);
											else
												throw new Exception("user doesn't exist in table");
										}
										else
											throw new Exception(message);
									}
								}
								else
									throw new Exception("RespCode:" + response.getStatus());
							}
							catch (Exception ex) {
								LOGGER.error("device '" + device + ": " + ex.getMessage(), ex);
								reason.append(device.getName() + ":" + ex.getMessage() + "\n");
								failed++;
							}
						}
					}
				}
				
				if (reason.length() == 0)
					reason.append(" ");
				
				EKTPUserDAO.updateTemporary(batchNo, idUser, role, 
						(failed == deviceList.size()? "E": "D"), 
						(((failed > 0) && (failed < deviceList.size()))? "Partial Success: ": "") + reason.deleteCharAt(reason.length() - 1).toString()
				);
			}
		}
		
		this.fixQXtract = new FixQXtract();
		this.fixQXtract.setIdScheduler(fccDAO.get(this.getClass().getName() + "_" + profileName, "EMAIL", "A", "X").get(0).getIdScheduler());
		this.fixQXtract.setParam1(currentFQX.getParam1());
		this.fixQXtract.setParam2(currentFQX.getParam2());
		this.fixQXtract.setParam3(currentFQX.getParam3());
		this.fixQXtract.setParam4(currentFQX.getParam4());
		this.fixQXtract.setParam5(FilenameUtils.getName(param5));
		this.fixQXtract.setParam6(currentFQX.getParam6());
		this.fixQXtract.setReason(currentFQX.getReason());
		this.fixQXtract.setFlgProcess(StatusDefinition.REQUEST);
		this.fixQXtract.setDtmRequest(SchedulerUtil.getTime());
		fqxDAO.insert(this.fixQXtract);
		
		currentFQX.setReason(null);
		
		return true;
	}


	protected Response requestWSRest(String protocol, String IPAddress, String serviceName, Form form) {
		String strURL = ROOT_SERVICE;
		strURL = strURL.replace("{protocol}", protocol);
		strURL = strURL.replace("{ip}", IPAddress);
		strURL = strURL.replace("{port}", PROTOCOLS.get(protocol));
		
		Client client = ClientBuilder.newClient();
		
		Response response = 
			client
				.target(strURL).path(serviceName)
				.request(MediaType.APPLICATION_JSON_TYPE, MediaType.TEXT_HTML_TYPE)
				.post(Entity.form(form));
		
		LOGGER.debug("URL: " + strURL);
		LOGGER.debug("HEADER: " + response.getHeaders());
		LOGGER.debug("ALLOW: " + response.getAllowedMethods());
		LOGGER.debug("LOCATION: " + response.getLocation());
		LOGGER.debug("STATUS: " + response.getStatus());
		LOGGER.debug("METADATA: " + response.getMetadata());
		
		return response;
	}

}
