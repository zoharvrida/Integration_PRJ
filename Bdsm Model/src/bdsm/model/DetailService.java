/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00024535
 */
public class DetailService extends BaseModel {

    private Integer idScheduler;
    private String type;
    private String serviceName;
    private String fieldName;
    private String head;
    private String requestSource;
    private String dbFieldName;
    private String tableName;
    private String valueType;
    private String orderData;
    private String defaultValue;
    private String levelData;
    private String wsType;
    private String recType;
    private String parent;
    private String appender;
    private String orientation;
    private String valueAppend;
    private String valueLength;
    private String valueOrientation;
    private String formatter;
    private String patternFormatter;
    private String tokenOrder;
    private String apostrhope;
    private String query;

    @Override
    public String toString() {
        return "DetailService{" + "idScheduler=" + idScheduler + ", type=" + type + ", serviceName=" + serviceName + ", fieldName=" + fieldName + ", head=" + head + ", requestSource=" + requestSource + ", dbFieldName=" + dbFieldName + ", tableName=" + tableName + ", valueType=" + valueType + ", orderData=" + orderData + ", defaultValue=" + defaultValue + ", levelData=" + levelData + ", wsType=" + wsType + ", recType=" + recType + ", parent=" + parent + ", appender=" + appender + ", orientation=" + orientation + ", valueAppend=" + valueAppend + ", valueLength=" + valueLength + ", valueOrientation=" + valueOrientation + ", formatter=" + formatter + ", patternFormatter=" + patternFormatter + ", tokenOrder=" + tokenOrder + ", apostrhope=" + apostrhope + ", query=" + query + '}';
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
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the head
     */
    public String getHead() {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(String head) {
        this.head = head;
    }

    /**
     * @return the requestSource
     */
    public String getRequestSource() {
        return requestSource;
    }

    /**
     * @param requestSource the requestSource to set
     */
    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    /**
     * @return the dbFieldName
     */
    public String getDbFieldName() {
        return dbFieldName;
    }

    /**
     * @param dbFieldName the dbFieldName to set
     */
    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the valueType
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * @param valueType the valueType to set
     */
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    /**
     * @return the orderData
     */
    public String getOrderData() {
        return orderData;
    }

    /**
     * @param orderData the orderData to set
     */
    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the levelData
     */
    public String getLevelData() {
        return levelData;
    }

    /**
     * @param levelData the levelData to set
     */
    public void setLevelData(String levelData) {
        this.levelData = levelData;
    }

    /**
     * @return the wsType
     */
    public String getWsType() {
        return wsType;
    }

    /**
     * @param wsType the wsType to set
     */
    public void setWsType(String wsType) {
        this.wsType = wsType;
    }

    /**
     * @return the recType
     */
    public String getRecType() {
        return recType;
    }

    /**
     * @param recType the recType to set
     */
    public void setRecType(String recType) {
        this.recType = recType;
    }

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * @return the appender
     */
    public String getAppender() {
        return appender;
    }

    /**
     * @param appender the appender to set
     */
    public void setAppender(String appender) {
        this.appender = appender;
    }

    /**
     * @return the orientation
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    /**
     * @return the valueAppend
     */
    public String getValueAppend() {
        return valueAppend;
    }

    /**
     * @param valueAppend the valueAppend to set
     */
    public void setValueAppend(String valueAppend) {
        this.valueAppend = valueAppend;
    }

    /**
     * @return the valueLength
     */
    public String getValueLength() {
        return valueLength;
    }

    /**
     * @param valueLength the valueLength to set
     */
    public void setValueLength(String valueLength) {
        this.valueLength = valueLength;
    }

    /**
     * @return the valueOrientation
     */
    public String getValueOrientation() {
        return valueOrientation;
    }

    /**
     * @param valueOrientation the valueOrientation to set
     */
    public void setValueOrientation(String valueOrientation) {
        this.valueOrientation = valueOrientation;
    }

    /**
     * @return the formatter
     */
    public String getFormatter() {
        return formatter;
    }

    /**
     * @param formatter the formatter to set
     */
    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    /**
     * @return the patternFormatter
     */
    public String getPatternFormatter() {
        return patternFormatter;
    }

    /**
     * @param patternFormatter the patternFormatter to set
     */
    public void setPatternFormatter(String patternFormatter) {
        this.patternFormatter = patternFormatter;
    }

    /**
     * @return the tokenOrder
     */
    public String getTokenOrder() {
        return tokenOrder;
    }

    /**
     * @param tokenOrder the tokenOrder to set
     */
    public void setTokenOrder(String tokenOrder) {
        this.tokenOrder = tokenOrder;
    }

    /**
     * @return the apostrhope
     */
    public String getApostrhope() {
        return apostrhope;
    }

    /**
     * @param apostrhope the apostrhope to set
     */
    public void setApostrhope(String apostrhope) {
        this.apostrhope = apostrhope;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }
}
