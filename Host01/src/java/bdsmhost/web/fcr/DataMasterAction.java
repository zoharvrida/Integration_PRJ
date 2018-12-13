package bdsmhost.web.fcr;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class DataMasterAction extends bdsmhost.web.BaseHostAction {
	private String master;
	private String id;
	private String like;
	private Object[] data;
	private java.util.List<Object[]> dataList;
	
	
    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		this.getLogger().info("[Begin] doGet()");
		
		bdsm.fcr.service.DataMasterService fcrDataMasterService = new bdsm.fcr.service.DataMasterService();
		fcrDataMasterService.setDataMasterDAO(new bdsmhost.fcr.dao.DataMasterDAO(this.getHSession()));
		
		this.data = fcrDataMasterService.getDataById(this.master, this.id);

		this.getLogger().info("[ End ] doGet()");
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		this.getLogger().info("[Begin] doList()");
		
		bdsm.fcr.service.DataMasterService fcrDataMasterService = new bdsm.fcr.service.DataMasterService();
		fcrDataMasterService.setDataMasterDAO(new bdsmhost.fcr.dao.DataMasterDAO(this.getHSession()));
		
		this.dataList = fcrDataMasterService.getDataList(this.master, this.like);

		this.getLogger().info("[ End ] doList()");
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
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
     * @param master
     */
    public void setMaster(String master) {
		this.master = master;
	}
	
    /**
     * 
     * @param id
     */
    public void setId(String id) {
		this.id = id;
	}
	
    /**
     * 
     * @param like
     */
    public void setLike(String like) {
		this.like = like;
	}
	
	
    /**
     * 
     * @return
     */
    public Object[] getData() {
		return this.data;
	}
	
    /**
     * 
     * @return
     */
    public java.util.List<Object[]> getDataList() {
		return this.dataList;
	}

}
