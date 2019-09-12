package bdsm.dialog.json;

import bdsm.model.ScreenOpening;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.DefinitionConstant;
import bdsm.util.HttpUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
public class Dialog26301Action extends BaseDialogAction {

    private static final String EXISTING_SEARCH_HOST = "CustomerSearchHost_list";
    private static final String CUSTOMER_SEARCH_HOST = "CustomerSearchHost_customerSearch.action";
    private static final DateFormat formatterDate = new SimpleDateFormat(DefinitionConstant.DBSingleScreenDate);
    private static final DateFormat parserDate = new SimpleDateFormat(DefinitionConstant.SingleScreenDateParse);
    private static String prodExcluder = "singleScreenprodExclude";
    private String customerName;
    private String dateofBirth;
    private String noKartuIden;
    private String cifNo;
    private String flagsCIF;
    private String extFlag;
    private ScreenOpening so = new ScreenOpening();

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception{
        this.getLogger().info("customerName : " + this.getCustomerName());
        this.getLogger().info("dateofBirth : " + this.getDateofBirth());
		this.getLogger().info("ProdExclude : " + Dialog26301Action.getProdExcluder());
        this.getLogger().info("cifNO : " + this.cifNo);
        this.getLogger().info("EXTFLAG :" + this.extFlag);

        String result = null;
        Map<String, String> params = new HashMap<String, String>();
        this.so.setFullName(this.getCustomerName());
        try {
            if (this.getDateofBirth() != null) {
                getLogger().info("PARSE :" + this.parserDate.parse(this.getDateofBirth()));
                this.so.setBirthDate(this.formatterDate.format(this.parserDate.parse(this.getDateofBirth())));
            }
        } catch (Exception parseException) {
            getLogger().info("PARSE FAILED :" + this.getDateofBirth());
            this.so.setBirthDate(this.getDateofBirth());
        }
        this.so.setNik(this.noKartuIden);
        this.so.setExtUser(this.extFlag);
        this.so.setMenuAccount("26301");
        getLogger().info("SO :" + this.so.toString());
        //params.put("noKartuIden",this.noKartuIden);
        Object[] listObj = {this.so};
        String[] nameless = {"so"};
        String[] prefix = {""};

        params = (Map<String, String>) ClassConverterUtil.ClassToMap(listObj, nameless, prefix, 1);
        
        params.put("prodExcluder", this.prodExcluder);
        if (this.cifNo == null) {
            params.put("customerName", this.getCustomerName());        
        try {
            params.put("dateofBirth", this.getDateofBirth());
        } catch (Exception ex) {
            getLogger().info("Parse Date "+ ex,ex);
        }
        } else {
            params.put("cifNo", this.cifNo);
        }
        
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        if (extFlag == null) {
            extFlag = DefinitionConstant.singleScreenAlias;
        }

        if (this.cifNo == null) {
            if (DefinitionConstant.singleScreenAlias.equalsIgnoreCase(extFlag)) {
           result = HttpUtil.request(getBdsmHost() + CUSTOMER_SEARCH_HOST, params);
        } else {
                params.put("sourceMenu", "26301");
                params.put("flagsCIF", extFlag);
                result = HttpUtil.request(getBdsmHost() + EXISTING_SEARCH_HOST, params);
            }
        } else {
            if (!DefinitionConstant.singleScreenAlias.equalsIgnoreCase(extFlag)) {
                params.put("sourceMenu", "26301");
                params.put("flagsCIF", extFlag);
            } else {
           params.put("flagsCIF","EXISTING"); 
            }
           result = HttpUtil.request(getBdsmHost() + EXISTING_SEARCH_HOST, params); 
            this.getLogger().info("26301-node");
        }
        
        this.getLogger().info(result);
        this.getLogger().info("RESULT "+ result);

        this.flagsCIF = "CIF";
        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        this.getLogger().info("Ret "+ ret);
        List listRet = (List) ret.get("modelList");
        String statusRet = (String) ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the noKartuIden
     */
    public String getNoKartuIden() {
        return noKartuIden;
    }

    /**
     * @param noKartuIden the noKartuIden to set
     */
    public void setNoKartuIden(String noKartuIden) {
        this.noKartuIden = noKartuIden;
    }

    /**
     * @return the dateofBirth
     */
    public String getDateofBirth() {
        return dateofBirth;
    }

    /**
     * @param dateofBirth the dateofBirth to set
     */
    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    /**
     * @return the cifNo
     */
    public String getCifNo() {
        return cifNo;
    }
   
    /**
     * @param cifNo the cifNo to set
     */
    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    /**
     * @return the flagsCIF
     */
    public String getFlagsCIF() {
        return flagsCIF;
    }

    /**
     * @param flagsCIF the flagsCIF to set
     */
    public void setFlagsCIF(String flagsCIF) {
        this.flagsCIF = flagsCIF;
    }

    /**
     * @return the extFlag
     */
    public String getExtFlag() {
        return extFlag;
    }

    /**
     * @param extFlag the extFlag to set
     */
    public void setExtFlag(String extFlag) {
        this.extFlag = extFlag;
    }

    /**
     * @return the prodExcluder
     */
    public static String getProdExcluder() {
        return prodExcluder;
    }

    /**
     * @param aProdExcluder the prodExcluder to set
     */
    public static void setProdExcluder(String aProdExcluder) {
        prodExcluder = aProdExcluder;
    }

    /**
     * @return the so
     */
    public ScreenOpening getSo() {
        return so;
    }

    /**
     * @param so the so to set
     */
    public void setSo(ScreenOpening so) {
        this.so = so;
    }
}
