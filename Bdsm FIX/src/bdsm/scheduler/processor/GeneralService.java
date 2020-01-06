/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.model.LoggingService;
import bdsm.model.MasterService;
import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixSchedulerImport;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.util.EncryptionUtil;
import bdsmhost.dao.DetailServiceDAO;
import bdsmhost.dao.LoggingServiceDAO;
import bdsmhost.dao.MasterServiceDAO;
import com.google.gson.Gson;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author v00024535
 */
public class GeneralService {

    private static Logger LOGGER = Logger.getLogger(GeneralService.class);
    private String URL;
    private String username;
    private String password;
    private String authKey;
    private Integer idScheduler;
    private String type;
    private String serviceName;
    private MasterService masterService = new MasterService();
    private List<Map> modelList;
    private Session session;
    private MasterServiceDAO serviceDAO;
    private DetailServiceDAO detailServiceDAO;
    private FixSchedulerImportDao fixSchedulerImportDao;
    private FixSchedulerXtractDao fixSchedulerXtractDao;
    private Map<String, Object> req;

    public GeneralService(Map<String, Object> request, Integer idScheduler, String type, String serviceName, Session session) throws Exception {
        try {
            this.session = session;
            this.serviceDAO = new MasterServiceDAO(session);
            this.detailServiceDAO = new DetailServiceDAO(session);
            this.fixSchedulerImportDao = new FixSchedulerImportDao(session);
            this.fixSchedulerXtractDao = new FixSchedulerXtractDao(session);

            this.masterService = this.serviceDAO.getService(idScheduler, type);
            this.URL = masterService.getUrl();
            this.username = masterService.getUserName();
            this.password = masterService.getPassword();
            this.idScheduler = idScheduler;
            this.serviceName = serviceName;
            this.type = type;
            this.req = request;
            this.authKey = generateKey();
        } catch (Exception e) {
            LOGGER.info("LOG ERROR - GeneralService ", e);
            e.printStackTrace();
        }
    }

    protected String generateKey() throws Exception {
        String keyAuth = "";
        List<String> keyDb = new LinkedList<>();
        int i = 0;
        StringBuilder st = new StringBuilder();
        try {
            this.setMasterService(getServiceDAO().getService(this.getIdScheduler(), this.getType()));
            this.setModelList((List<Map>) getDetailServiceDAO().getDetails(this.getIdScheduler(), this.getType(), this.getServiceName()));
            if ("COMBINATION".equalsIgnoreCase(getMasterService().getTypeKey())) {
                if (getMasterService().getFlag().equalsIgnoreCase("Y")) {
                    st.append(getMasterService().getKeyAuth());
                }
                for (Map result : getModelList()) {
                    if ("DB".equalsIgnoreCase((String) result.get("requestSource"))) {
                        keyDb.add((String) result.get("fieldName"));
                    }
                }
                for (Map result : getModelList()) {
                    if ("DB".equalsIgnoreCase((String) result.get("requestSource"))) {
                        String query = (String) result.get("query");
                        for (String m : keyDb) {
                            try {
                                query = query.replace("{" + keyDb.get(i) + "}", "'" + this.req.get(keyDb.get(i)) + "'");
                                String fieldName = (String) result.get("dbFieldName");
                                String tableName = (String) result.get("tableName");
                                String data = getDetailServiceDAO().getQuery(fieldName, tableName, query);
                                st.append(data);
                                break;
                            } catch (Exception e) {
                            }
                            i++;
                        }
                        i = 0;
                    } else {
                        st.append(this.req.get(result.get("fieldName")));
                    }

                }
            } else {
                st.append(getMasterService().getKeyAuth());
            }
            keyAuth = st.toString();
            //keyAuth = encrypt(keyAuth);
        } catch (Exception ex) {
            getLOGGER().info("LOG ERROR - generateKey ", ex);
            ex.printStackTrace();
        }
        return st.toString();
    }

    protected String encrypt(String source) {
        Class<?>[] fin = null;
        Method m = null;
        Object b = null;
        String resultEnc = null;
        String padding = null;
        Object[] obj = null;
        try {
            EncryptionUtil encryptionUtil = new EncryptionUtil();
            Class<?> cls = Class.forName(encryptionUtil.getClass().getName());
            Method[] cd = cls.getDeclaredMethods();
            if ("I".equalsIgnoreCase(getMasterService().getType())) {
                FixSchedulerImport fixSchedulerImport = getFixSchedulerImportDao().get(getMasterService().getIdScheduler());
                padding = encryptionUtil.LPad(fixSchedulerImport.getModDecrypt(), 16, '@');
                obj = new Object[]{fixSchedulerImport.getKeyDecrypt(), padding, 2};
            } else {
                FixSchedulerXtract fixSchedulerXtract = getFixSchedulerXtractDao().get(getMasterService().getIdScheduler());
                padding = encryptionUtil.LPad(fixSchedulerXtract.getKeyEncrypt(), 16, '@');
                obj = new Object[]{fixSchedulerXtract.getKeyEncrypt(), padding, 2};
            }
            for (Method md : cd) {
                Class<?>[] arr = md.getParameterTypes();
                if (arr.length > 0) {
                    if (getMasterService().getEncryptType().equalsIgnoreCase(md.getName())) {
                        if ("java.lang.string".equalsIgnoreCase(md.getReturnType().getName())) {
                            fin = arr;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (fin != null) {
                try {
                    if (fin.length == 1) {
                        m = cls.getMethod(getMasterService().getEncryptType(), fin);
                        resultEnc = (String) m.invoke(b, source);
                        getLOGGER().debug("HASHA256 " + resultEnc);
                    } else {
                        m = cls.getMethod("getAES", fin);
                        String decrypt = (String) m.invoke(b, obj);
                        getLOGGER().debug("RESULT DECRYPT " + decrypt);

                        Object[] obj2 = {source, decrypt, 1};
                        m = cls.getMethod(getMasterService().getEncryptType(), fin);
                        resultEnc = (String) m.invoke(b, obj2);
                        getLOGGER().debug("ENCRYPT " + resultEnc);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            LOGGER.info("LOG ERROR - encrypt ", e);
            e.printStackTrace();
        }
        return resultEnc;
    }

    public Map<String, Object> buildInitialData() throws Exception {
        Map<String, Object> mapResult = new HashMap<>();
        List<Object> list = new LinkedList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        int level = 1;
        try {
            this.setModelList(getDetailServiceDAO().getBuildData(this.getIdScheduler(), this.getType(), this.getServiceName()));
            mapResult = combineMap();
            for (Map reMap : getModelList()) {
                if ("PARENT".equalsIgnoreCase(reMap.get("valueType").toString())) {
                    if ("M".equalsIgnoreCase(reMap.get("recType").toString())) {
                        list = createNewList(dataMap, reMap.get("fieldName").toString());
                        list.add(this.req.get(reMap.get("fieldName")));
                    } else {
                        map = createNewMap(dataMap, reMap.get("fieldName"));
                    }
                    child(reMap.get("fieldName").toString(), map, dataMap, mapResult);
                } else {
                    if (Integer.parseInt(reMap.get("levelData").toString()) > level) {
                        break;
                    }
                    if ("M".equalsIgnoreCase(reMap.get("recType").toString())) {
                        list = createNewList(dataMap, reMap.get("fieldName").toString());
                        list.add(this.req.get(reMap.get("fieldName")));
                    } else {
                        dataMap.put((String) reMap.get("fieldName"), mapResult.get(reMap.get("fieldName")));
                    }
                }
            }
        } catch (Exception e) {
            getLOGGER().info("LOG ERROR - buildInitialData ", e);
            e.printStackTrace();
        }
        return dataMap;
    }

    protected void child(String parent, Map<String, Object> map, Map<String, Object> dataMap, Map<String, Object> request) {
        try {
            List<Map> modelList = getDetailServiceDAO().getChild(this.getIdScheduler(), this.getType(), this.getServiceName(), parent);
            Map<String, Object> map2 = new HashMap<>();
            List<Object> list;
            for (Map m : modelList) {
                if ("PARENT".equalsIgnoreCase(m.get("valueType").toString())) {
                    map2 = createNewMap(map, m.get("fieldName"));
                    child(m.get("fieldName").toString(), map2, dataMap, request);
                } else {
                    if ("M".equalsIgnoreCase(m.get("recType").toString())) {
                        list = createNewList(map, m.get("fieldName").toString());
                        list.add(request.get(m.get("fieldName")));
                    } else {
                        map.put((String) m.get("fieldName"), request.get(m.get("fieldName")));
                    }
                }
            }
        } catch (Exception e) {
            getLOGGER().info("LOG ERROR - child", e);
            e.printStackTrace();
        }
    }

    protected Map<String, Object> combineMap() {
        Map<String, Object> mapTemp = new HashMap<>();
        List<String> keyDb = new LinkedList<>();
        int i = 0;
        try {
            for (Map result : getModelList()) {
                if ("DB".equalsIgnoreCase((String) result.get("requestSource"))) {
                    keyDb.add((String) result.get("fieldName"));
                }
            }
            for (Map result : getModelList()) {
                if ("DB".equalsIgnoreCase((String) result.get("requestSource"))) {
                    String query = (String) result.get("query");
                    for (String m : keyDb) {
                        try {
                            query = query.replace("{" + keyDb.get(i) + "}", "'" + this.getReq().get(keyDb.get(i)) + "'");
                            String fieldName = (String) result.get("dbFieldName");
                            String tableName = (String) result.get("tableName");
                            String data = getDetailServiceDAO().getQuery(fieldName, tableName, query);
                            mapTemp.put((String) result.get("fieldName"), data);
                            break;
                        } catch (Exception e) {
                        }
                        i++;
                    }
                    i = 0;
                } else {
                    if (!"PARENT".equalsIgnoreCase(result.get("valueType").toString())) {
                        mapTemp.put((String) result.get("fieldName"), this.getReq().get(result.get("fieldName").toString()));
                    }

                }
            }
        } catch (Exception e) {
            getLOGGER().info("LOG ERROR - combineMap ", e);
            e.printStackTrace();
        }

        return mapTemp;
    }

    public Map<String, Object> requestWebService(Map<String, ? extends Object> requestData) {
        LoggingService out;
        LoggingService in;
        Map<String, Object> mapResult = null;
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(requestData);

        LOGGER.debug("Web Service request: " + jsonRequest);
        try {
            // insert WS out
            out = new LoggingService();
            out.setRefNo(requestData.get("USER_REF_NO").toString());
            out.setServiceName(this.serviceName);
            out.setType("O");
            out.setIdScheduler(this.idScheduler);
            out.setClob(jsonRequest);
            (new GeneralService.SaveData(new LoggingServiceDAO(this.session), out)).start();
            LOGGER.debug("OUT result: " + out.toString());
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(this.username, this.password);
            Client client = ClientBuilder.newClient().register(feature);

//            Response response = client.target(URL).path(pathName).request(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(jsonRequest));
//            
//            LOGGER.debug("RESPONSE " + response);
//            
//            if ((response.getStatus() == Response.Status.OK.getStatusCode())
//                    && isEqualMediaType(response.getMediaType(), MediaType.APPLICATION_JSON_TYPE)) {
//                
//                String jsonResponse = response.readEntity(String.class);
//                mapResult = (Map<String, Object>) gson.fromJson(jsonResponse, Map.class);
//                
//                LOGGER.debug("Web Service response: " + jsonResponse);
//                
//                in = new LoggingService();
//                try {
//                    in.setRefNo(mapResult.get("user_ref_no").toString());
//                } catch (Exception e) {
//                    LOGGER.debug("reff NOT fOUND: " + e, e);
//                    in.setRefNo(out.getRefNo());
//                }
//                in.setServiceName(mapResult.get("service_name").toString());
//                in.setType("I");
//                in.setIdScheduler(Integer.parseInt(mapResult.get("id_scheduler").toString()));
//                in.setClob(jsonResponse);
//                (new GeneralService.SaveData(new LoggingServiceDAO(null), in)).start();
//            } else {
//                throw new RuntimeException(response.getStatus() + " - " + Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase());
//            }
        } catch (RuntimeException ex) {
            LOGGER.debug("Exception result: " + ex, ex);
            throw ex;
        }

        return mapResult;
    }

    /**
     * @return the fixSchedulerXtractDao
     */
    public FixSchedulerXtractDao getFixSchedulerXtractDao() {
        return fixSchedulerXtractDao;
    }

    /**
     * @param fixSchedulerXtractDao the fixSchedulerXtractDao to set
     */
    public void setFixSchedulerXtractDao(FixSchedulerXtractDao fixSchedulerXtractDao) {
        this.fixSchedulerXtractDao = fixSchedulerXtractDao;
    }

    class SaveData extends Thread {

        private final Logger LOGGER = Logger.getLogger(GeneralService.SaveData.class);
        private bdsmhost.dao.BaseDao dao;
        private bdsm.model.BaseModel model;

        public SaveData(bdsmhost.dao.BaseDao dao, bdsm.model.BaseModel model) {
            this.dao = dao;
            this.model = model;
        }

        @Override
        public void run() {
            //Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();

            try {
                this.dao.setSession(session);
                this.dao.insert(this.model);
            } catch (Exception e) {
                LOGGER.debug("INSERT FAILED : " + e, e);
                e.printStackTrace();
            }

            trx.commit();
            //HibernateUtil.closeSession(session);
        }
    }

    private static boolean isEqualMediaType(MediaType m1, MediaType m2) {
        if ((m1 == null) && (m2 == null)) {
            return true;
        } else if ((m1 == null) || (m2 == null)) {
            return false;
        } else {
            return ((m1.getType().equals(m2.getType())) && (m1.getSubtype().equals(m2.getSubtype())));
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Map<String, Object> createNewMap(Object parent, Object... id) {
        Map<String, Object> map = new LinkedHashMap<String, Object>(0);
        if (parent instanceof Map) {
            ((Map) parent).put(id[0], map);
        } else if (parent instanceof List) {
            ((List) parent).add(map);
        }

        return map;
    }

    private static List<Object> createNewList(Map<String, Object> parentMap, String id) {
        List<Object> list = new ArrayList<Object>(0);
        parentMap.put(id, list);
        return list;
    }

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @param URL the URL to set
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the authKey
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     * @param authKey the authKey to set
     */
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(Integer idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the masterService
     */
    public MasterService getMasterService() {
        return masterService;
    }

    /**
     * @param masterService the masterService to set
     */
    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    /**
     * @return the modelList
     */
    public List<Map> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<Map> modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the serviceDAO
     */
    public MasterServiceDAO getServiceDAO() {
        return serviceDAO;
    }

    /**
     * @param serviceDAO the serviceDAO to set
     */
    public void setServiceDAO(MasterServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    /**
     * @return the detailServiceDAO
     */
    public DetailServiceDAO getDetailServiceDAO() {
        return detailServiceDAO;
    }

    /**
     * @param detailServiceDAO the detailServiceDAO to set
     */
    public void setDetailServiceDAO(DetailServiceDAO detailServiceDAO) {
        this.detailServiceDAO = detailServiceDAO;
    }

    /**
     * @return the fixSchedulerImportDao
     */
    public FixSchedulerImportDao getFixSchedulerImportDao() {
        return fixSchedulerImportDao;
    }

    /**
     * @param fixSchedulerImportDao the fixSchedulerImportDao to set
     */
    public void setFixSchedulerImportDao(FixSchedulerImportDao fixSchedulerImportDao) {
        this.fixSchedulerImportDao = fixSchedulerImportDao;
    }

    /**
     * @return the req
     */
    public Map<String, Object> getReq() {
        return req;
    }

    /**
     * @param req the req to set
     */
    public void setReq(Map<String, Object> req) {
        this.req = req;
    }

    /**
     * @return the LOGGER
     */
    public static Logger getLOGGER() {
        return LOGGER;
    }

    /**
     * @param aLOGGER the LOGGER to set
     */
    public static void setLOGGER(Logger aLOGGER) {
        LOGGER = aLOGGER;
    }
}
