/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00019722
 */
public class MenuRedirect {
    private MenuRedirectionPK pk;
    private String sourceName;
    private String strutsAction;
    private String destMenu;
    private String inQuiry;
    private String defaultData;
    private String valDefaultData;
    private String methodInvocation;

    /**
     * @return the sourceName
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * @return the strutsAction
     */
    public String getStrutsAction() {
        return strutsAction;
    }

    /**
     * @param strutsAction the strutsAction to set
     */
    public void setStrutsAction(String strutsAction) {
        this.strutsAction = strutsAction;
    }

    /**
     * @return the destMenu
     */
    public String getDestMenu() {
        return destMenu;
    }

    /**
     * @param destMenu the destMenu to set
     */
    public void setDestMenu(String destMenu) {
        this.destMenu = destMenu;
    }

    /**
     * @return the inQuiry
     */
    public String getInQuiry() {
        return inQuiry;
    }

    /**
     * @param inQuiry the inQuiry to set
     */
    public void setInQuiry(String inQuiry) {
        this.inQuiry = inQuiry;
    }

    /**
     * @return the defaultData
     */
    public String getDefaultData() {
        return defaultData;
    }

    /**
     * @param defaultData the defaultData to set
     */
    public void setDefaultData(String defaultData) {
        this.defaultData = defaultData;
    }

    /**
     * @return the valDefaultData
     */
    public String getValDefaultData() {
        return valDefaultData;
    }

    /**
     * @param valDefaultData the valDefaultData to set
     */
    public void setValDefaultData(String valDefaultData) {
        this.valDefaultData = valDefaultData;
    }

    /**
     * @return the pk
     */
    public MenuRedirectionPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(MenuRedirectionPK pk) {
        this.pk = pk;
    }

    /**
     * @return the methodInvocation
     */
    public String getMethodInvocation() {
        return methodInvocation;
    }

    /**
     * @param methodInvocation the methodInvocation to set
     */
    public void setMethodInvocation(String methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    @Override
    public String toString() {
        return "MenuRedirect{" + "pk=" + pk + ", sourceName=" + sourceName + ", strutsAction=" + strutsAction + ", destMenu=" + destMenu + ", inQuiry=" + inQuiry + ", defaultData=" + defaultData + ", valDefaultData=" + valDefaultData + ", methodInvocation=" + methodInvocation + '}';
    }
    
}
