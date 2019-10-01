package bdsm.service;

import bdsm.model.BdsmEtaxPaymXref;
import bdsmhost.dao.BdsmEtaxPaymXrefDao;
import bdsm.model.ETaxBillingInfo;
import bdsm.model.ETaxBillingType;
import bdsm.model.ETaxInquiryBillingReq;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.ETaxWSAuditTrail;
import bdsm.model.MasterLimitEtax;
import bdsm.util.BdsmUtil;
import static bdsm.util.EncryptionUtil.getAES;
import static bdsm.util.EncryptionUtil.hashSHA256;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.ETaxWSAuditTrailDAO;
import bdsmhost.dao.ParameterDao;
import bdsmhost.dao.TransactionParameterDao;
import bdsmhost.fcr.dao.BaCcyCodeDAO;
import com.google.gson.Gson;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author v00017250
 */
public class ETaxService {

    private final static Logger LOGGER = Logger.getLogger(ETaxService.class);
    private BaCcyCodeDAO ccyDAO;
    private String[] BPKHAccounts;
    private String URL;
    private String username;
    private String password;
    private String authKey;
    private DecimalFormat decF = new DecimalFormat("#");
    private SimpleDateFormat datF1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat datF2 = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
    private BdsmEtaxPaymXref epv;
    private MasterLimitEtax limVal;
    private ETaxInquiryBillingReq inquiryReq;
    //private ETaxInquiryBillingResp etax;

    public ETaxService(Session session) throws Exception {
        this.ccyDAO = new BaCcyCodeDAO(session);
        ParameterDao paramDAO = new ParameterDao(session);
        TransactionParameterDao trxParamDAO = new TransactionParameterDao(session);

        Map<String, ? extends Object> WS = BdsmUtil.parseKeyAndValueToMap(paramDAO.get("MDLWR_ETAX_WS").getStrVal());
        this.URL = WS.get("URL").toString();
        this.username = WS.get("username").toString();
        this.password = getAES(WS.get("password").toString(), BdsmUtil.rightPad("ETAX", 16, '@'), Cipher.DECRYPT_MODE);
        this.authKey = paramDAO.get("MDLWR_ETAX_AUTH_TOKEN_KEY").getStrVal();

        //this.BPKHAccounts = trxParamDAO.getByModuleName("SISKOHAT").getAccountNo().split(";");
        //LOGGER.debug("Siskohat GL: " + java.util.Arrays.asList(this.BPKHAccounts));
    }

   
    public BdsmEtaxPaymXref paymentBilling(ETaxInquiryBillingResp etax) throws Exception
    {
            epv = new BdsmEtaxPaymXref();
            Date dt =  new Date();
            epv.setDtmRequest(dt);
            Map<String, Object> mapData = this.buildInitialDataPaym(etax,"ETAX_PMT", null); 
            LOGGER.debug("ETax WS Endpoin: " + this.URL);
            //LOGGER.debug("ETax Request: " + epv.toString());
            LOGGER.debug("ETax mapData: " + mapData);
            Map<String, Object> result;            
            try {
            LOGGER.debug("LOGGER requestWebService: " + mapData);
            result = this.requestWebService(mapData, "payment");
            LOGGER.debug("LOGGER responWebService: " + result);
        } catch (RuntimeException ex) {
            result = new LinkedHashMap<String, Object>(0);
            result.put("code_status", "99999");
            result.put("desc_status", "BDSM - " + ex.getMessage());
        }    
            
            LOGGER.debug("LOGGER responWebService: " + result.get("user_ref_no").toString());
//            epv.getInqBillResp().setRefNo(result.get("user_ref_no").toString());
//            epv.setDtmResp(datF1.parse(result.get("response_time").toString()));
//            epv.setServiceCode(result.get("service_code").toString());
//            epv.setChannelId(result.get("channel_id").toString());
//            epv.setBinNo(result.get("bin_no").toString());
//            epv.setCodAuthId(result.get("auth_token").toString());
//            epv.setcodAcctType(result.get("acct_type").toString());
//            epv.setCodAcctNo(result.get("acct_no").toString());
//            try
//            {
//            epv.getInqBillResp().setAmount(new BigDecimal(result.get("acct_no").toString()));
//            }catch(Exception ed)
//            {
//                epv.getInqBillResp().setAmount(BigDecimal.ZERO);
//            }
//            epv.getInqBillResp().setCcy(result.get("ccy").toString());
//            epv.getBillInfo().setBillingId(result.get("billing_id").toString());
//            epv.getInqBillResp().setBranchCode(result.get("branch_code").toString());
//            epv.getInqBillResp().setCostCenter(result.get("cost_center").toString());
//            epv.getInqBillResp().setUserId(result.get("user_id").toString());
//            ETaxBillingType type = epv.getInqBillResp().getBillingInfo().getType();
//            if( type == ETaxBillingType.DJP)
//            {
//                epv.getBillInfo().setNpwp(result.get("npwp").toString());
//                epv.getBillInfo().setWpName(result.get("wp_name").toString());
//                epv.getBillInfo().setWpAddress(result.get("wp_address").toString());
//                epv.getBillInfo().setAkun(result.get("akun").toString());
//                epv.getBillInfo().setKodeJenisSetoran(result.get("kode_jenis_setoran").toString());
//                epv.getBillInfo().setMasaPajak(result.get("masa_pajak").toString());
//                epv.getBillInfo().setNomorSK(result.get("nomor_sk").toString());
//                epv.getBillInfo().setNop(result.get("nop").toString());
//            } else if (type == ETaxBillingType.DJBC)
//            {
//                epv.getBillInfo().setWpName(result.get("wp_name").toString());
//                epv.getBillInfo().setIdWajibBayar(result.get("id_wajib_bayar").toString());
//                epv.getBillInfo().setJenisDokumen(result.get("jenis_dokumen").toString());
//                epv.getBillInfo().setNomorDokumen(result.get("nomor_dokumen").toString());
//                epv.getBillInfo().setTanggalDokumen(result.get("tanggal_dokumen").toString());
//                epv.getBillInfo().setKodeKPBC(result.get("kode_kpbc").toString());
//            } else if (type == ETaxBillingType.DJA)
//            {
//                epv.getBillInfo().setWpName(result.get("wp_name").toString());
//                epv.getBillInfo().setKl(result.get("kl").toString());
//                epv.getBillInfo().setUnitEselon1(result.get("unit_eselon1").toString());
//                epv.getBillInfo().setKodeSatker(result.get("kode_satker").toString());
//            }
//            try{
//            epv.getInqBillResp().setDjpTS(result.get("djp_ts").toString());
//            }
//            catch(Exception e)
//            {
//                epv.getInqBillResp().setDjpTS("");
//            }
            
                        
            dt =  new Date();
            try
            {
               epv.setRefUsrNo(etax.getRefNo());
            }catch(Exception e)
            {
                 epv.setRefUsrNo("#####");
            }
            try
            {
                epv.setBillCode(etax.getBillingInfo().getBillingId());
            }catch(Exception e)
            {
                epv.setBillCode("");
            }
            try
            {
                epv.setPaymentType(etax.getPmtType());
            }catch(Exception e)
            {
                epv.setPaymentType("");
            }
            
            try
            {
                epv.setPaymentType(etax.getPmtType());
            }catch(Exception e)
            {
                epv.setPaymentType("");
            }
            
            try
            {
                epv.setTaxCcy(result.get("ccy").toString());
            }catch(Exception e)
            {
                epv.setTaxCcy("");
            }
            
            try
            {
                epv.setTaxAmount(etax.getAmount());
            }catch(Exception e)
            {
                epv.setTaxAmount(BigDecimal.ZERO);
            }
            
            try
            {
                epv.setCodAcctNo(result.get("acct_no").toString());
            }catch(Exception e)
            {
                epv.setCodAcctNo("");
            }
            
            try
            {
                epv.setCodAcctCcy(result.get("ccy").toString());
            }catch(Exception e)
            {
                epv.setCodAcctCcy("");
            }
            
            try
            {
                epv.setCodTrxBrn(etax.getBranchCode());
            }catch(Exception e)
            {
                epv.setCodTrxBrn("");
            }
            
            try
            {
                epv.setCodCcBrn(etax.getBranchCode());
            }catch(Exception e)
            {
                epv.setCodCcBrn("");
            }
            
            try
            {
                epv.setCodUserId(etax.getBranchCode());
            }catch(Exception e)
            {
                epv.setCodUserId("");
            }
            
            try
            {
                epv.setCodAuthId(etax.getBranchCode());
            }catch(Exception e)
            {
                epv.setCodAuthId("");
            }
            
            
            epv.setDtmTrx(dt);
            epv.setDtmResp(dt);
            epv.setDtmPost(datF2.parse(datF2.format(dt)));
            
            try
            {
                epv.setRefNtb(result.get("ntb").toString());
            }catch(Exception e)
                    {
                        epv.setRefNtb("");
                    }
            
             try
            {
                epv.setRefNtpn(result.get("ntpn").toString());
            }catch(Exception e)
                    {
                        epv.setRefNtpn("");
                    }
            
             try
            {
                epv.setCodStanId(result.get("stan").toString());
            }catch(Exception e)
                    {
                        epv.setCodStanId("");
                    }
            try
            {
                epv.setCodSspcpNo(result.get("sspcp_no").toString());
            }catch(Exception e)
                    {
                        epv.setCodSspcpNo("");
                    }
            
            try
            {
                epv.setErrCode(result.get("code_status").toString());
            }catch(Exception e)
                    {
                        epv.setErrCode("");
                    }
           
            try
            {
                epv.setErrDesc(result.get("desc_status").toString());
            }catch(Exception e)
                    {
                        epv.setErrDesc("");
                    }
            
            try
            {
                epv.setDtmPost(datF1.parse(dt.toString()));
            }catch(Exception e)
            {
                
            }
            if(epv.getErrCode().toString().equalsIgnoreCase("000000"))
            {
                (new SaveData(new BdsmEtaxPaymXrefDao(null), epv)).start();
               
            }
            
            
            return epv;
            
    }

    
    public ETaxInquiryBillingResp inquiryBilling(ETaxInquiryBillingReq request) throws Exception {
        Map<String, Object> mapData = this.buildInitialData(request, "ETAX_INQ", null);
        LOGGER.debug("ETax WS Endpoin: " + this.URL);
        LOGGER.debug("ETax Request: " + request.toString());
        LOGGER.debug("ETax mapData: " + mapData);
        Map<String, Object> result;

        try {
            LOGGER.debug("LOGGER requestWebService: " + mapData);
            result = this.requestWebService(mapData, "inquiryidbilling");
            LOGGER.debug("LOGGER responWebService: " + result);
        } catch (RuntimeException ex) {
            result = new LinkedHashMap<String, Object>(0);
            result.put("code_status", "99999");
            result.put("desc_status", "BDSM - " + ex.getMessage());
        }

        ETaxInquiryBillingResp resp = new ETaxInquiryBillingResp();
        resp.setRefNo(result.get("user_ref_no").toString());
        resp.setResponseCode(result.get("code_status").toString());
        resp.setResponseDesc(result.get("desc_status").toString());
        ;
        

        if (Integer.parseInt(resp.getResponseCode()) == 0) {
            // billing_info map
            Map<String, Object> billingInfoMap = (Map<String, Object>) result.get("billing_info");

            String billingId = getAsString(result, "billing_id");
            LOGGER.debug("Response Billing ID: " + billingId);
            ETaxBillingInfo billingInfo = createBillingInfo(billingId);
            ETaxBillingType type = billingInfo.getType();
            LOGGER.debug("Response Billing Type: " + type);
            if(billingInfoMap != null) {
                if (type == ETaxBillingType.DJP) {
                    billingInfo.setNpwp((String) billingInfoMap.get("npwp"));
                    billingInfo.setWpName((String) billingInfoMap.get("wp_name"));
                    billingInfo.setWpAddress((String) billingInfoMap.get("wp_address"));
                    billingInfo.setAkun((String) billingInfoMap.get("akun"));
                    billingInfo.setKodeJenisSetoran((String) billingInfoMap.get("kode_jenis_setoran"));
                    billingInfo.setMasaPajak((String) billingInfoMap.get("masa_pajak"));
                    billingInfo.setNomorSK((String) billingInfoMap.get("nomor_sk"));
                    billingInfo.setNop((String) billingInfoMap.get("nop"));
                } else if (type == ETaxBillingType.DJBC) {
                    billingInfo.setWpName((String) billingInfoMap.get("wp_name"));
                    billingInfo.setIdWajibBayar((String) billingInfoMap.get("id_wajib_bayar"));
                    billingInfo.setJenisDokumen((String) billingInfoMap.get("jenis_dokumen"));
                    billingInfo.setNomorDokumen((String) billingInfoMap.get("nomor_dokumen"));
                    billingInfo.setTanggalDokumen((String) billingInfoMap.get("tanggal_dokumen"));
                    billingInfo.setKodeKPBC((String) billingInfoMap.get("kode_kpbc"));
                } else if (type == ETaxBillingType.DJA) {
                    billingInfo.setWpName((String) billingInfoMap.get("wp_name"));
                    billingInfo.setKl((String) billingInfoMap.get("kl"));
                    billingInfo.setUnitEselon1((String) billingInfoMap.get("unit_eselon_1"));
                    billingInfo.setKodeSatker((String) billingInfoMap.get("kode_satker"));
                }
            }
            resp.setBillingInfo(billingInfo);
            resp.setBranchCode(getAsString(result, "branch_code"));
            resp.setCostCenter(getAsString(result, "cost_center"));
            resp.setUserId(request.getUserId());
            resp.setCcy(getAsString(result, "ccy"));
            resp.setAmount(getAsBigDecimal(result, "amount"));
            resp.setResponseTime(datF1.parse(getAsString(result, "response_time")));
            if(resp.getResponseTime() != null) {
                resp.setResponseTimeString(datF2.format(resp.getResponseTime()));
            }
        }

        return resp;
    }

    private ETaxBillingInfo createBillingInfo(String billingId) {
        ETaxBillingInfo info = null;
        ETaxBillingType type = ETaxBillingType.fromBillingIdPrefix(billingId);
        LOGGER.debug("createBillingInfo#type: " + type + ", billingId: " + billingId);
        info = new ETaxBillingInfo();
        info.setType(type);
        info.setBillingId(billingId);
        return info;
    }

    private String getAsString(Map map, String key) {
        return (String) map.get(key);
    }

    private BigDecimal getAsBigDecimal(Map map, String key) throws Exception {
        return this.parseToBigDecimal(getAsString(map, key));
    }

    public String generateRefNo(String billingId) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        return (billingId + dateFormatter.format(new Date()));
    }

    @SuppressWarnings({"unchecked"})
    private Map<String, Object> requestWebService(Map<String, ? extends Object> requestData, String pathName) {
        ETaxWSAuditTrail out;
        ETaxWSAuditTrail in;
        Map<String, Object> mapResult = null;
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(requestData);

        LOGGER.debug("Web Service request: " + jsonRequest);
        try {
            // insert WS out
            out = new ETaxWSAuditTrail();
            out.setRefNo(requestData.get("user_ref_no").toString());
            out.setProfileName("ETAX");
            out.setMethodName(pathName);
            out.setTypeReq("O");
            out.setContent(jsonRequest);
            (new SaveData(new ETaxWSAuditTrailDAO(null), out)).start();
            LOGGER.debug("OUT result: " + out.toString());
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(this.username, this.password);
            Client client = ClientBuilder.newClient().register(feature);

            Response response = client.target(URL).path(pathName).request(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(jsonRequest));
            
            LOGGER.debug("RESPONSE " + response);
            
            if ((response.getStatus() == Response.Status.OK.getStatusCode())
                    && isEqualMediaType(response.getMediaType(), MediaType.APPLICATION_JSON_TYPE)) {

                String jsonResponse = response.readEntity(String.class);
                mapResult = (Map<String, Object>) gson.fromJson(jsonResponse, Map.class);

                LOGGER.debug("Web Service response: " + jsonResponse);

                in = new ETaxWSAuditTrail();
                try {
                    in.setRefNo(mapResult.get("user_ref_no").toString());
                } catch (Exception e) {
                    LOGGER.debug("reff NOT fOUND: " + e, e);
                    in.setRefNo(out.getRefNo());
                }
                in.setProfileName("ETAX");
                in.setMethodName(pathName);
                in.setTypeReq("I");
                in.setContent(jsonResponse);
                (new SaveData(new ETaxWSAuditTrailDAO(null), in)).start();
            } else {
                throw new RuntimeException(response.getStatus() + " - " + Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase());
            }
        } catch (RuntimeException ex) {
            LOGGER.debug("Exception result: " + ex, ex);
            throw ex;
        }

        return mapResult;
    }
    
    private Map<String, Object> buildInitialDataPaym(ETaxInquiryBillingResp etax,String middlewareServiceCode, String specificDataMethod) throws Exception {
                    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String, Object> mapData = new LinkedHashMap<String, Object>(0);
        Map<String, Object> map;
        mapData.put("channel_id", "BDSM");
        mapData.put("user_ref_no",etax.getRefNo());
        mapData.put("request_time", sdf.format(new Date()));
        mapData.put("service_code",middlewareServiceCode);
        mapData.put("bin_no", "0000");
        mapData.put("acct_type", etax.getPmtType());        
        if(etax.getPmtType().equalsIgnoreCase("20"))
        {
            mapData.put("acct_no", etax.getDebitAccountNo());
        }else if(etax.getPmtType().equalsIgnoreCase("40"))
        {
            mapData.put("acct_no", etax.getGlAccountNo());
        }
        mapData.put("amount", formatNumericString("0", 12 - (etax.getAmount().toString().replace(".", "").length()))+etax.getAmount().toString().replace(".", ""));
        mapData.put("ccy", etax.getCcy());
        mapData.put("billing_id",etax.getBillingInfo().getBillingId());
        StringBuilder sb = new StringBuilder()
                .append((String) mapData.get("channel_id"))
                .append(hashSHA256(this.authKey))
                .append((String) mapData.get("bin_no"))
                .append((String) mapData.get("user_ref_no"))
                .append((String) mapData.get("acct_no"))
                .append((String) mapData.get("billing_id"));
        mapData.put("auth_token", hashSHA256(sb.toString()));
        map = createNewMap(mapData, "billing_info");
        
        String billingId = etax.getBillingInfo().getBillingId();
            LOGGER.debug("Response Billing ID: " + billingId);
            ETaxBillingInfo billingInfo = createBillingInfo(billingId);
            ETaxBillingType type = billingInfo.getType();
        if (type.toString().equalsIgnoreCase(etax.getBillingInfo().getType().DJA.toString()))
        {            
            map.put("wp_name", etax.getBillingInfo().getWpName());
            map.put("kl", etax.getBillingInfo().getKl());
            map.put("unit_eselon_1", etax.getBillingInfo().getUnitEselon1());
            map.put("kode_satker", etax.getBillingInfo().getKodeSatker());
        }
        else if (type.toString().equalsIgnoreCase(etax.getBillingInfo().getType().DJP.toString()))
        {
            map.put("npwp", etax.getBillingInfo().getNpwp());
            map.put("wp_name", etax.getBillingInfo().getWpName());
            map.put("wp_address", etax.getBillingInfo().getWpAddress());
            map.put("akun", etax.getBillingInfo().getAkun());
            map.put("kode_jenis_setoran", etax.getBillingInfo().getKodeJenisSetoran());
            map.put("masa_pajak", etax.getBillingInfo().getMasaPajak());
            map.put("nomor_sk", etax.getBillingInfo().getNomorSK());
            map.put("nop", etax.getBillingInfo().getNop());
        }
        else if (type.toString().equalsIgnoreCase(etax.getBillingInfo().getType().DJBC.toString()))
        {
            map.put("npwp", etax.getBillingInfo().getNpwp());
            map.put("wp_name", etax.getBillingInfo().getWpName());
            map.put("id_wajib_bayar", etax.getBillingInfo().getIdWajibBayar());
            map.put("jenis_dokumen", etax.getBillingInfo().getJenisDokumen());
            map.put("nomor_dokumen", etax.getBillingInfo().getNomorDokumen());
            map.put("tanggal_dokumen", etax.getBillingInfo().getTanggalDokumen());
            map.put("kode_kpbc", etax.getBillingInfo().getKodeKPBC());
        }
        mapData.put("branch_code",etax.getBranchCode());
        mapData.put("cost_center",etax.getCostCenter());
        mapData.put("user_id",etax.getUserId());
        return mapData;
    }

    @SuppressWarnings("rawtypes")
    private Map<String, Object> buildInitialData(Object input, String middlewareServiceCode, String specificDataMethod) throws Exception {
        String refNo, billingId, branchCode, costCenter, userId;
        Class[] bc = new Class[0];
        Object[] bo = new Object[0];
        Method method;

        try {
            method = input.getClass().getMethod("getRefNo", bc);
            refNo = method.invoke(input, bo).toString();
        } catch (Exception ex) {
            refNo = "";
        }
        try {
            method = input.getClass().getMethod("getBillingId", bc);
            billingId = method.invoke(input, bo).toString();
        } catch (Exception ex) {
            billingId = "";
        }
        try {
            method = input.getClass().getMethod("getBranchCode", bc);
            branchCode = method.invoke(input, bo).toString();
        } catch (Exception ex) {
            branchCode = "";
        }
        try {
            method = input.getClass().getMethod("getCostCenter", bc);
            costCenter = method.invoke(input, bo).toString();
        } catch (Exception ex) {
            costCenter = "";
        }
        try {
            method = input.getClass().getMethod("getUserId", bc);
            userId = method.invoke(input, bo).toString();
        } catch (Exception ex) {
            userId = "";
        }
        if("".equals(refNo)) {
            refNo = generateRefNo("");
        }
        

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String, Object> mapData = new LinkedHashMap<String, Object>(0);
        mapData.put("channel_id", "BDSM");
        mapData.put("bin_no", this.formatNumericString("0", 4));
        mapData.put("user_ref_no", refNo);
        mapData.put("request_time", sdf.format(new Date()));
        mapData.put("service_code", middlewareServiceCode);
        mapData.put("billing_id", billingId);
        mapData.put("branch_code", branchCode);
        mapData.put("cost_center", costCenter);
        mapData.put("user_id", userId);

        StringBuilder sb = new StringBuilder()
                .append((String) mapData.get("channel_id"))
                .append(hashSHA256(this.authKey))
                .append((String) mapData.get("bin_no"))
                .append((String) mapData.get("user_ref_no"))
                .append((String) mapData.get("billing_id"));
        mapData.put("auth_token", hashSHA256(sb.toString()));

        if (specificDataMethod != null) {
            try {
                method = this.getClass().getDeclaredMethod(specificDataMethod, new Class[]{Map.class, Object.class});
                method.invoke(this, mapData, input);
            } catch (java.lang.reflect.InvocationTargetException ex) {
                Throwable t = ex;
                while (t.getCause() != null) {
                    t = ex.getCause();
                }

                throw new bdsm.scheduler.exception.FIXException(t.getMessage());
            }
        }

        return mapData;
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

    private BigDecimal parseToBigDecimal(String input) throws Exception {
        return this.parseToBigDecimal(input, 360);
    }

    private BigDecimal parseToBigDecimal(String input, Integer currencyCode) throws Exception {
        int dec = this.ccyDAO.getByCcyCod(currencyCode).getCtrCcyDec();
        BigDecimal tmp = new BigDecimal(this.decF.parse(input).toString());

        return tmp.divide(BigDecimal.TEN.pow(dec));
    }

    private String formatFromBigDecimal(BigDecimal input) {
        return this.formatFromBigDecimal(input, 360);
    }

    private String formatFromBigDecimal(BigDecimal input, Integer currencyCode) {
        int dec = this.ccyDAO.getByCcyCod(currencyCode).getCtrCcyDec();

        return this.formatNumericString(this.decF.format(input.multiply(BigDecimal.TEN.pow(dec))), 13);
    }

    private String formatNumericString(String input, int length) {
        return BdsmUtil.leftPad(input, length, '0');
    }

    /*
    protected void depositPrintRequest_(Map<String, Object> mapData, Object input) throws Exception {
        SkhtPrintReq request = (SkhtPrintReq) input;
        //mapData.put("settlement_date", this.getSettlementDate());
        mapData.put("jenis_haji", request.getHajiType());
        mapData.put("tanggal_transaksi", this.datF2.format(new Date()));
        mapData.put("nomor_validasi", request.getValidationNo());
    }
     */
    class SaveData extends Thread {

        private final Logger LOGGER = Logger.getLogger(SaveData.class);
        private bdsmhost.dao.BaseDao dao;
        private bdsm.model.BaseModel model;

        public SaveData(bdsmhost.dao.BaseDao dao, bdsm.model.BaseModel model) {
            this.dao = dao;
            this.model = model;
        }

        @Override
        public void run() {
            Session session = HibernateUtil.getSession();
            Transaction trx = session.beginTransaction();

            try {
                this.dao.setSession(session);
                this.dao.insert(this.model);
            } catch (Exception e) {
                LOGGER.debug("INSERT FAILED : " + e, e);
            }

            trx.commit();
            HibernateUtil.closeSession(session);
        }
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, Object> createNewMap(Object parent, Object... id) {
		Map<String, Object> map = new LinkedHashMap<String, Object>(0);
		if (parent instanceof Map)
			((Map) parent).put(id[0], map);
		else if (parent instanceof List)
			((List) parent).add(map);
		
		return map; 
	}

}

