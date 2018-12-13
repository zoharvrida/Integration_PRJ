/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.MasterMenu;
import bdsm.model.MenuTree;
import bdsm.model.XrefTemplateMenu;
import bdsm.model.XrefTemplateMenuPK;
import bdsmhost.dao.MasterMenuDao;
import bdsmhost.dao.XrefTemplateMenuDao;
import com.jgeppert.struts2.jquery.tree.result.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 00030663
 */
public class MenuAccessAction extends BaseHostAction {

    private String idTemplate;
    private String idMenu;
    private String titleMenu;
    private MenuTree menuTree;
    private String accessRight;
    private List<TreeNode> treeNode;

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        /*
         XrefTemplateMenuDao xdao = new XrefTemplateMenuDao(getHSession());
         List<XrefTemplateMenu> xtmList = xdao.list(idTemplate);
         MasterMenuDao dao = new MasterMenuDao(getHSession());
         List<MasterMenu> menuList = dao.list("");
         menuTree = new MenuTree(null);
         menuTree.setIdMenu("root");
         menuTree.setNamMenu("Root Menu");
         for (MasterMenu m: menuList) {
         doRecursiveList(dao, m, menuTree, xtmList);
         }
         menuTree.compact();*/
        XrefTemplateMenuDao xdao = new XrefTemplateMenuDao(getHSession());
        List<XrefTemplateMenu> xtmList = xdao.listAvailable(idTemplate);
        MasterMenuDao dao = new MasterMenuDao(getHSession());
        List<MasterMenu> menuList = dao.listComplete("");
        treeNode = new ArrayList<TreeNode>();
        constructTree(xtmList, menuList, treeNode);
        return SUCCESS;
    }

    private int findByIdMenu(String id, List<MasterMenu> menuList) {
        int result = -1;
        int max = menuList.size();
        for (int i = 0; i < max; i++) {
            if (menuList.get(i).getIdMenu().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }

    private void constructTree(List<XrefTemplateMenu> xtmList, List<MasterMenu> menuList, List<TreeNode> treeNode) {
        TreeNode base = null;
        TreeNode pbase = null;
        TreeNode gbase = null;
        int x=-1;
        int xg=-1;
        String parent = "";
        String tparent = "1";
        String gparent = "";
        String tgparent = "2";

        for (XrefTemplateMenu xtm : xtmList) {
            base = new TreeNode();
            x = findByIdMenu(xtm.getCompositeId().getIdMenu(), menuList);
            base.setId(xtm.getCompositeId().getIdMenu());
            base.setTitle(xtm.getCompositeId().getIdMenu()
                    + " - "
                    + menuList.get(x).getNamMenu());

            base.setState(TreeNode.NODE_STATE_LEAF);
            base.getAttr().put("href", menuList.get(x).getUrl());

            if (!parent.equals(tparent = menuList.get(x).getIdMenuParent())) {
                if(pbase!=null){
                    gbase.getChildren().add(pbase);
                }
                pbase = new TreeNode();
                pbase.setId(tparent);
                xg = findByIdMenu(tparent, menuList);
                pbase.setTitle(tparent + " - " + menuList.get(xg).getNamMenu());
                pbase.setState(TreeNode.NODE_STATE_OPEN);
                pbase.setChildren(new ArrayList<TreeNode>());
                pbase.getAttr().put("href", "#");
                parent = tparent;
                pbase.getChildren().add(base);
            }else{
                pbase.getChildren().add(base);
            }

            if(!gparent.equals(tgparent = menuList.get(xg).getIdMenuParent())){
                if(gbase!=null){
                    treeNode.add(gbase);
                }
                gbase = new TreeNode();
                gparent = tgparent;
                gbase.setId(gparent);
                x = findByIdMenu(gparent, menuList);
                gbase.setTitle(gparent + " - " + menuList.get(x).getNamMenu());
                gbase.setState(TreeNode.NODE_STATE_OPEN);
                gbase.getAttr().put("href", "#");
                gbase.setChildren(new ArrayList<TreeNode>());
            }
        }
        gbase.getChildren().add(pbase);
        treeNode.add(gbase);

    }

    private void buildTree(MasterMenuDao dao, List<XrefTemplateMenu> xtmList) {
        int max = xtmList.size();
        TreeNode tn;
        for (int i = 0; i < max; i++) {
            tn = new TreeNode();
        }

    }

    private void doRecursiveList(MasterMenuDao dao, MasterMenu menu, MenuTree parent, List<XrefTemplateMenu> xtmList) {
        List<MasterMenu> menuList = dao.list(menu.getIdMenu());
        for (MasterMenu m : menuList) {
            String url = m.getUrl();
            if (url == null) {
                MenuTree child = new MenuTree(parent);
                child.setIdMenu(m.getIdMenu());
                child.setNamMenu(m.getNamMenu());
                child.setUrl(m.getUrl());
                child.setAvailAccess(m.getAvailAccess());
                doRecursiveList(dao, m, child, xtmList);
            } else {
                boolean hasAccess = false;
                if (xtmList != null) {
                    for (XrefTemplateMenu xtm : xtmList) {
                        String xtmAccessRight = xtm.getAccessRight();
                        if (xtm != null
                                && xtm.getCompositeId().getIdMenu().equals(m.getIdMenu())
                                && xtmAccessRight != null && !"".equals(xtmAccessRight) && !"00000".equals(xtmAccessRight)) {
                            getLogger().debug(xtm.getCompositeId().getIdMenu() + "," + m.getIdMenu() + "," + xtmAccessRight);
                            hasAccess = true;
                            break;
                        }
                    }
                }

                if (hasAccess || true) {
                    MenuTree child = new MenuTree(parent);
                    child.setIdMenu(m.getIdMenu());
                    child.setNamMenu(m.getNamMenu());
                    child.setUrl(m.getUrl());
                    child.setAvailAccess(m.getAvailAccess());
                }
            }
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        /* Access Right */
        XrefTemplateMenuPK pk = new XrefTemplateMenuPK();
        pk.setIdMenu(idMenu);
        pk.setIdTemplate(idTemplate);
        XrefTemplateMenuDao dao = new XrefTemplateMenuDao(getHSession());
        XrefTemplateMenu xtp = dao.get(pk);
        accessRight = xtp.getAccessRight();
        getLogger().debug("accessRight: " + accessRight);
        
        /* Menu Title */
        MasterMenuDao menuDAO = new MasterMenuDao(this.getHSession());
        MasterMenu menu = menuDAO.get(this.idMenu);
        this.titleMenu = this.idMenu + " - " + menu.getNamMenu();
        
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
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the menuTree
     */
    public MenuTree getMenuTree() {
        return menuTree;
    }

    /**
     * @param menuTree the menuTree to set
     */
    public void setMenuTree(MenuTree menuTree) {
        this.menuTree = menuTree;
    }

    /**
     * @return the idMenu
     */
    public String getIdMenu() {
        return idMenu;
    }

    /**
     * @param idMenu the idMenu to set
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }
    
    /**
     * @return the titleMenu
     */
    public String getTitleMenu() {
        return titleMenu;
    }
    
    /**
     * @param titleMenu the titleMenu to set
     */
    public void setTitleMenu(String titleMenu) {
        this.titleMenu = titleMenu;
    }

    /**
     * @return the accessRight
     */
    public String getAccessRight() {
        return accessRight;
    }

    /**
     * 
     * @return
     */
    public List getTreeNode(){
        return treeNode;
    }

    /**
     * 
     * @param treeNode
     */
    public void setTreeNode(List<TreeNode> treeNode){
        this.treeNode = treeNode;
    }
}
