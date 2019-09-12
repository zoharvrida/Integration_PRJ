package bdsm.service;

import bdsm.model.FCR_CMPreembossCardInventory;
import bdsmhost.dao.FCR_CMPreembossCardInventoryDAO;

public class FCR_CMPreembossCardInventoryService {
	FCR_CMPreembossCardInventoryDAO dao;
	
	/* == Setter Injection == */
	public void setFCR_CMPreembossCardInventoryDAO(FCR_CMPreembossCardInventoryDAO dao) {
		this.dao = dao;
	}
	
	public java.util.List<FCR_CMPreembossCardInventory> getCards(String cardNo) {
		java.util.List<FCR_CMPreembossCardInventory> list = this.dao.getByCardNo(cardNo);
		
		if (list.size() > 0)
			for (FCR_CMPreembossCardInventory cardInventory : list)
				this.dao.evictObjectFromPersistenceContext(cardInventory);
		
		return list;
	}
}
