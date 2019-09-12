/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.web.Constant;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

/**
 *
 * @author bdsm
 */
public abstract class BaseDialogAction extends ActionSupport implements SessionAware, ServletContextAware {
    private ServletContext servletContext;
    /**
     * 
     */
    protected Map<String, Object> session;
    private String errorMessage;

    /**
     * 
     * @return
     */
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    /**
     * 
     * @return
     */
    public final String list() {
        getLogger().info("[Begin] list()");
        try {
            if (isValidSession()) {
                int to = (rows * page); 
                int from = to - rows;
                try {
                    List list = doList();

                    setGridTemplate(genGridModel(list, from, to));
                    records = list.size();
                    total =(int) Math.ceil((double)records / (double)rows);
                    errorMessage = "";
                } catch (Throwable ex) {
                    getLogger().fatal(ex, ex);
                    errorMessage = ex.getMessage();
                    return ActionSupport.ERROR;
                }

                return ActionSupport.SUCCESS;
            } else {
                return logout();
            }
        } finally {
            getLogger().info("[ End ] list()");
        }
        
    }

    private List genGridModel(List listRet, int from, int to) {
        
        List listGrid;
        listGrid = new ArrayList();
        int records = listRet.size();
        
        if(from > to || from > records) {
            return listGrid;
        } 
        
        if(to > records) {
            to = records;
        }
        
        for(int i = from; i < to; i++) {
            listGrid.add(listRet.get(i));
        }
        
        return listGrid;
    }

    /**
     * 
     * @return
     */
    @Override
    public final String input() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public final String execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public final String add() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public final String edit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public final String delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param map
     */
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    //public Map<String, Object> getSession() {
    //    return this.session;
    //}

    /**
     * 
     * @return
     */
    protected String getBdsmHost() {
        return servletContext.getInitParameter("bdsmHost");
    }

    /**
     * 
     * @return
     */
    protected String getTokenKey() {
        return servletContext.getInitParameter("tokenKey");
    }

    /**
     * 
     * @return
     */
    protected String getTzToken() {
        return servletContext.getInitParameter("tzToken");
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public abstract List doList() throws Exception;

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    //Your result List
    private List gridTemplate;

    //get how many rows we want to have into the grid - rowNum attribute in the grid
    private Integer             rows             = 0;

    //Get the requested page. By default grid sets this to 1.
    private Integer             page             = 0;

    // sorting order - asc or desc
    private String              sord;

    // get index row - i.e. user click to sort.
    private String              sidx;

    // Search Field
    private String              searchField;

    // The Search String
    private String              searchString;

    // he Search Operation ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
    private String              searchOper;

    // Your Total Pages
    private Integer             total            = 0;

    // All Record
    private Integer             records          = 0;


    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the sord
     */
    public String getSord() {
        return sord;
    }

    /**
     * @param sord the sord to set
     */
    public void setSord(String sord) {
        this.sord = sord;
    }

    /**
     * @return the sidx
     */
    public String getSidx() {
        return sidx;
    }

    /**
     * @param sidx the sidx to set
     */
    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    /**
     * @return the searchField
     */
    public String getSearchField() {
        return searchField;
    }

    /**
     * @param searchField the searchField to set
     */
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the searchOper
     */
    public String getSearchOper() {
        return searchOper;
    }

    /**
     * @param searchOper the searchOper to set
     */
    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the records
     */
    public Integer getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(Integer records) {
        this.records = records;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the gridTemplate
     */
    public List getGridTemplate() {
        return gridTemplate;
    }

    /**
     * @param gridTemplate the gridTemplate to set
     */
    public void setGridTemplate(List gridTemplate) {
        this.gridTemplate = gridTemplate;
    }
    
    /**
     * 
     * @return
     */
    protected boolean isValidSession() {
        if (session==null) return false;
        String userId = (String) session.get(Constant.C_IDUSER);
        if (userId==null || "".equals(userId)) return false;
        return true;
    }

    /**
     * 
     * @return
     */
    protected String logout() {
        return ActionSupport.NONE;
    }
    
}
