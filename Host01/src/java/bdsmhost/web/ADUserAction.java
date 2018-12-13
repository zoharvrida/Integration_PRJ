/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.ADUser;
import bdsm.util.BdsmUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

/**
 *
 * @author Roby
 */
public class ADUserAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;

    private List<ADUser> modelList;
    private ADUser model;

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
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        DirContext ctx = BdsmUtil.getDirContext(getadUser(), getadPassword(), getadServer(), getadRootDN());
        model.setDisplayName(BdsmUtil.getNameFromAD(ctx, getadSearchBase(), model.getSAMAccountName()));
        try {
            ctx.close();
        } catch (NamingException ex) {
            Logger.getLogger(ADUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return SUCCESS;
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
     * @return the modelList
     */
    public List<ADUser> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<ADUser> modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the model
     */
    public ADUser getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(ADUser model) {
        this.model = model;
    }

}
