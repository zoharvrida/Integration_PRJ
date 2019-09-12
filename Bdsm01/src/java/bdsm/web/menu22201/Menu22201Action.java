package bdsm.web.menu22201;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;


/**
 * 
 * @author bdsm
 */
public class Menu22201Action extends bdsm.web.BaseContentAction {
	private static final String GET_FCRCUSTOMERCARD = "FCRCustomerCardHost_get.action";
	private static final String LIST_FCRCARDINVENTORY = "FCRCardInventory_list.action";
	private static final String GET_TPIN = "TPINHost_get";
	private String cardNo;
	private Integer flagCard;
	
    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		return Menu22201Action.SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
    /**
     * 
     * @return
     */
    public String validateCardNo() {
		this.getLogger().info("[Begin] validateCardNo()");
		this.getLogger().debug("cardNo: " + this.cardNo);
		try {
			if (isValidSession()) {
				Map<String, String> requestMap = new HashMap<String, String>();
				requestMap.put("model.cardNo", this.cardNo);
				requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));

				String result = bdsm.util.HttpUtil.request(this.getBdsmHost() + GET_FCRCUSTOMERCARD, requestMap);
				Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);

				this.flagCard = 0;
				if (resultMap.get("model") == null) {
					requestMap.clear();
					requestMap.put("model.cardNo", this.cardNo);
					requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
					
					result = bdsm.util.HttpUtil.request(this.getBdsmHost() + LIST_FCRCARDINVENTORY, requestMap);
					resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
					
					if (((List) resultMap.get("modelList")).size() == 0)
						this.flagCard = -1;
				}
				
				if (this.flagCard == 0) {
					requestMap.clear();
					requestMap.put("cardNo", this.cardNo);
					requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
					
					result = bdsm.util.HttpUtil.request(this.getBdsmHost() + GET_TPIN, requestMap);
					resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
					
					if (resultMap.get("model") == null)
						this.flagCard = -2;
				}

				return "validateCardNo";
			} else {
				return logout();
			}
		}
		catch (Throwable e) {
			getLogger().fatal(e, e);
			
			return Menu22201Action.ERROR;
		}
		finally {
			getLogger().info("[ End ] validateCardNo()");
		}
	}
	
	
	/* request parameter property */
	
    /**
     * 
     * @return
     */
    public String getCardNo() {
		return this.cardNo;
	}
    /**
     * 
     * @param cardNo
     */
    public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
    /**
     * 
     * @return
     */
    public Integer getFlagCard() {
		return this.flagCard;
	}
    /**
     * 
     * @param flagCard
     */
    public void setFlagCard(Integer flagCard) {
		this.flagCard = flagCard;
	}

}
