/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author 00030663
 */
public class XrefTemplateMenu extends BaseModel {
    private XrefTemplateMenuPK compositeId;
    private String accessRight;

    /**
     * @return the compositeId
     */
    public XrefTemplateMenuPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(XrefTemplateMenuPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the accessRight
     */
    public String getAccessRight() {
        return accessRight;
    }

    /**
     * @param accessRight the accessRight to set
     */
    public void setAccessRight(String accessRight) {
        this.accessRight = accessRight;
    }
}
