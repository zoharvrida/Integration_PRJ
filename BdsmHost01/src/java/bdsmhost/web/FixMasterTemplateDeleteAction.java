/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.rpt.model.TemplateDelInd;
import bdsm.rpt.dao.TemplateDelDao;
import bdsm.rpt.model.TemplateDelComp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author v00019722
 */
public class FixMasterTemplateDeleteAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;
    private List modelList;
    private List modelRep;
    
    private TemplateDelInd model;

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
    public String doList() {
        getLogger().info("ModelList = " + getModelList());
        TemplateDelDao dao = new TemplateDelDao(getHSession());
        modelList = dao.list(getModel().getNamTemplate());
        //modelList = dao.list();
        List<TemplateDelInd> objFixMAstList = new ArrayList<TemplateDelInd>();
        Set<Object> model = new HashSet<Object>();
        int c = 0;
        for (int i = 0; i < modelList.size(); i++) {

            TemplateDelComp objFixMaster = (TemplateDelComp) modelList.get(i);
            if (objFixMaster != null) {
                TemplateDelInd fixMasterPK = new TemplateDelInd();
                getLogger().info("id Template :" + objFixMaster.getCompositeId().getIdTemplate());
                fixMasterPK.setNamTemplate(objFixMaster.getNamTemplate());
                fixMasterPK.setIdTemplate(objFixMaster.getCompositeId().getIdTemplate());
                if (i != 0) {
                    if (fixMasterPK.getIdTemplate().equals(objFixMAstList.get(c - 1).getIdTemplate())) {
                        objFixMAstList.add(fixMasterPK);
                        objFixMAstList.remove(c);
                        if (c != 0) {//c--;
                            getLogger().info(" Cek Remove " + objFixMAstList.get(c - 1));
                        }
                    } else {
                        getLogger().info("else C " + i);
                        objFixMAstList.add(fixMasterPK);
                        c++;
                        getLogger().info(" Cek Object " + objFixMAstList.get(c - 1));
                    }
                } else {
                    objFixMAstList.add(fixMasterPK);
                    c++;
                    getLogger().info(" cek First Object :" + c);
                }
            }
        }
        modelRep = objFixMAstList;
        getLogger().info("ModelList = " + getModelList());
        getLogger().info("list Suceess :" + String.valueOf(getModelList().size()));
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
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
     * @return the modelList
     */
    public List getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the modelRep
     */
    public List getModelRep() {
        return modelRep;
    }

    /**
     * @param modelRep the modelRep to set
     */
    public void setModelRep(List modelRep) {
        this.modelRep = modelRep;
    }

    /**
     * @return the model
     */
    public TemplateDelInd getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(TemplateDelInd model) {
        this.model = model;
    }
}
