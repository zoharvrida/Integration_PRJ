/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.CifOpening;
import bdsm.model.ScreenOpening;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.lang.reflect.Field;

/**
 *
 * @author v00019722
 */
public class CifOpeningAction extends BaseHostAction {

    private CifOpening cif = new CifOpening();
    private ScreenOpening so = new ScreenOpening();
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
     * @return
     */
    public String getFieldforScreen(){
        Field[] screenField = this.cif.getClass().getDeclaredFields();
        StringBuilder fieldname = new StringBuilder();
        for(int i=0;i<screenField.length;i++){
            fieldname.append(screenField[i].getName()).append(",");
        }
        try {
            fieldname.deleteCharAt(fieldname.length());
        } catch (Exception e) {
            fieldname.deleteCharAt(fieldname.length()-1);
        }
        this.so.setScreenField(fieldname.toString());
        return SUCCESS;
    }
 
    /**
     * @return the so
     */
    public ScreenOpening getSo() {     
        return so;
    }

    /**
     * @param so the so to set
     */
    public void setSo(ScreenOpening so) {
        this.so = so;
    }
}
