/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author v00019722
 */
public class FixPatternTable extends BaseModel{
    private Integer idScheduler;
    private String table_Name;
    private String table_Code;
    private String table_type;
    private String properties;
    private Timestamp dtm_Create;
    private String delimiter;

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
     * @return the table_Name
     */
    public String getTable_Name() {
        return table_Name;
    }

    /**
     * @param table_Name the table_Name to set
     */
    public void setTable_Name(String table_Name) {
        this.table_Name = table_Name;
    }

    /**
     * @return the table_Code
     */
    public String getTable_Code() {
        return table_Code;
    }

    /**
     * @param table_Code the table_Code to set
     */
    public void setTable_Code(String table_Code) {
        this.table_Code = table_Code;
    }

    /**
     * @return the table_type
     */
    public String getTable_type() {
        return table_type;
    }

    /**
     * @param table_type the table_type to set
     */
    public void setTable_type(String table_type) {
        this.table_type = table_type;
    }

    /**
     * @return the properties
     */
    public String getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * @return the dtm_Create
     */
    public Timestamp getDtm_Create() {
        return dtm_Create;
    }

    /**
     * @param dtm_Create the dtm_Create to set
     */
    public void setDtm_Create(Timestamp dtm_Create) {
        this.dtm_Create = dtm_Create;
    }

    /**
     * @return the delimiter
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
