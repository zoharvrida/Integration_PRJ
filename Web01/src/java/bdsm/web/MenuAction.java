package bdsm.web;

import bdsm.model.MenuTree;
import bdsm.model.ProTimePutLog;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import com.jgeppert.struts2.jquery.tree.result.TreeNode;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * @author bdsm
 */
public class MenuAction extends BaseContentAction {
    private static final String ACTION_MENUACCESS_LIST = "menuaccess_list.action";
    private static final String ACTION_MENUACCESS_GET = "menuaccess_get.action";
    private MenuTree menuTree;
    private List<TreeNode> nodes = new ArrayList<TreeNode>();
    private String idMenu;
    private String noCif;
    private String noAcct;
    private String refTxn;
    private String typMsg;
    private String goAction;
    private String typTrx;
    private String typAcct;

    /**
     * 
     * @return
     */
    @Override
    public String doInput() {
        return ActionSupport.INPUT;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
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
    @Override
    public String exec() {
        String idTemplate = (String) session.get(Constant.C_IDTEMPLATE);

        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;

        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        map.put("idTemplate", idTemplate);

        result = HttpUtil.request(getBdsmHost() + ACTION_MENUACCESS_LIST, map);

        try {
            resultMap = (HashMap)JSONUtil.deserialize(result);
            //Gson gson = new Gson();
            /*
            HashMap gMap = (HashMap) resultMap.get("menuTree");
            String s = gson.toJson(gMap);
            menuTree = (MenuTree) gson.fromJson(s, MenuTree.class);*/
            //HashMap gMap = (HashMap) resultMap.get("treeNode");

            //String s = gson.toJson(gMap);
            nodes = (List<TreeNode>) resultMap.get("treeNode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        return ActionSupport.SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String click() {
        getLogger().info("[Begin] click()");
        try {
            if (isValidSession()) {
                return doClick();
            } else {
                return logout();
            }
        } catch (Exception e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] click()");
        }
    }
    
    private String doClick() {
        String idTemplate = (String) session.get(Constant.C_IDTEMPLATE);

        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        map.put("idTemplate", idTemplate);
        map.put("idMenu", idMenu);

        result = HttpUtil.request(getBdsmHost() + ACTION_MENUACCESS_GET, map);

        String accessRight = null;
        String titleMenu = null;
        
        try {
            resultMap = (HashMap)JSONUtil.deserialize(result);
            accessRight = (String) resultMap.get("accessRight");
            titleMenu = (String) resultMap.get("titleMenu");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
            setErrorMessage(ex.getLocalizedMessage());
            return ERROR;
        }
        
        if (accessRight == null || "".equals(accessRight) || "00000".equals(accessRight)) {
            setErrorMessage("Invalid task code");
            return ERROR;
        }
        if (noCif != null && !"".equals(noCif)) {
            session.put(Constant.C_NOCIF, noCif);
        }
        if (noAcct != null && !"".equals(noAcct)) {
            session.put(Constant.C_NOACCT, noAcct);
        }
        if (refTxn != null && !"".equals(refTxn)) {
            session.put(Constant.C_REFTXN, refTxn);
        }
        if (typMsg != null && !"".equals(typMsg)) {
            session.put(Constant.C_TYPMSG, typMsg);
        }
        if (goAction != null && !"".equals(goAction)) {
            session.put(Constant.C_GOACT, goAction);
        }
        
        if (typTrx != null && !"".equals(typTrx)) {
            session.put(Constant.C_TYPTRX, typTrx);
        }
        
        if (typAcct != null && !"".equals(typAcct)) {
            session.put(Constant.C_TYPACCT, typAcct);
        }
        
        session.put(Constant.C_IDMENU, this.idMenu);
        session.put(Constant.C_TITLEMENU, titleMenu);
        session.put(Constant.C_ACCESSRIGHT, accessRight);
        
        setSession(session);
        getLogger().debug("SESSION : "+ session);
        Map sess = session;
        sess.put("MESSAGE","START TRANSACTION");
        ProTimePutLogAct times = new ProTimePutLogAct(sess, getTokenKey(), getTzToken(), getBdsmHost());
        return idMenu;
    }

    /**
     * @return the menuTree
     */
    @JSON(serialize=false)
    public MenuTree getMenuTree() {
        return menuTree;
    }

    /**
     * @return the nodes
     */
    @JSON(serialize=true)
    public List<TreeNode> getNodes() {
        return nodes;
    }

    private void buildChildNodes(TreeNode parentNode, MenuTree parentMenu) {
        List<MenuTree> l = parentMenu.getChildList();
        if (l != null) {
            Collection<TreeNode> c = new ArrayList<TreeNode>();
            for (MenuTree m: l) {
                TreeNode node = new TreeNode();
                node.setId(m.getIdMenu());
                node.setTitle(m.getNamMenu());
                node.setState(TreeNode.NODE_STATE_OPEN);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("url", m.getUrl());
                if (m.getUrl()==null || "".equals(m.getUrl())) {
                    node.setType("folder");
                    buildChildNodes(node, m);
                } else {
                    node.setType("file");
                }
                c.add(node);
            }
            parentNode.setChildren(c);
        }
    }

    /**
     * @param idMenu the idMenu to set
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * @return the noCif
     */
    public String getNoCif() {
        return noCif;
    }

    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(String noCif) {
        this.noCif = noCif;
    }

    /**
     * @return the noAcct
     */
    public String getNoAcct() {
        return noAcct;
    }

    /**
     * @param noAcct the noAcct to set
     */
    public void setNoAcct(String noAcct) {
        this.noAcct = noAcct;
    }
    
    /**
     * @return the refTxn
     */
    public String getRefTxn() {
        return refTxn;
    }

    /**
     * @param refTxn the refTxn to set
     */
    public void setRefTxn(String refTxn) {
        this.refTxn = refTxn;
    }
    
    /**
     * @return the typMsg
     */
    public String getTypMsg() {
        return typMsg;
    }

    /**
     * @param typMsg the typMsg to set
     */
    public void setTypMsg(String typMsg) {
        this.typMsg = typMsg;
    }
    
    /**
     * @return the goAction
     */
    public String getGoAction() {
        return goAction;
    }

    /**
     * @param goAction the goAction to set
     */
    public void setGoAction(String goAction) {
        this.goAction = goAction;
    }

    /**
     * @return the typTrx
     */
    public String getTypTrx() {
        return typTrx;
    }

    /**
     * @param typTrx the typTrx to set
     */
    public void setTypTrx(String typTrx) {
        this.typTrx = typTrx;
    }

    /**
     * @return the typAcct
     */
    public String getTypAcct() {
        return typAcct;
    }

    /**
     * @param typAcct the typAcct to set
     */
    public void setTypAcct(String typAcct) {
        this.typAcct = typAcct;
    }
}
