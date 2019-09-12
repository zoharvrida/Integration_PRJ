<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjt" uri="/struts-jquery-tree-tags"%>
<script type="text/javascript">
    function treeOnClick(menu) {
        $("#ph-main").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:40px; height:40px; z-index:99' /></span>" + $("#ph-main").html());
        $("#ph-title").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:20px; height:20px; z-index:99' /></span>" + $("#ph-title").html());
        $("#ph-buttons").html("<span style='position:relative; float:left'><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:20px; height:20px; z-index:99' /></span>" + $("#ph-buttons").html());
        $("#frmMenu_idMenu").val(menu);
        $("#btnMenu").click();
    }
    
    $(function() {
    });
</script>
<sjt:tree 
    id="treeStatic" 
    jstreetheme="default" 
    openAllOnLoad="true" 
    title="BDSM Menu" 
    cssClass="ui-widget ui-widget-content" 
    cssStyle="border: 0px solid white; background-color:transparent"
>
    <sjt:treeItem title="10000 - Common File">
        <sjt:treeItem title="10300 - Maintenances">
            <sjt:treeItem id="10301" title="10301 - Template Registration" onclick="treeOnClick('10301');" />
            <sjt:treeItem id="10302" title="10302 - Menu Access Registration" onclick="treeOnClick('10302');" />
            <sjt:treeItem id="10303" title="10303 - User Registration" onclick="treeOnClick('10303');" />
        </sjt:treeItem>
    </sjt:treeItem>
    <sjt:treeItem title="20000 - Monitoring Foreign Currency Transactions">
        <sjt:treeItem title="20200 - Summary">
            <sjt:treeItem id="20201" title="20201- Transaction Monitoring Summary" onclick="treeOnClick('20201');" />
        </sjt:treeItem>
        <sjt:treeItem title="20300 - Maintenances">
            <sjt:treeItem id="20301" title="20301 - Statement Letter Maintenance" onclick="treeOnClick('20301');" />
            <sjt:treeItem id="20302" title="20302 - Underlying Document Maintenance" onclick="treeOnClick('20302');" />
        </sjt:treeItem>
        <sjt:treeItem title="20400 - Transaction Input">
            <sjt:treeItem id="20401" title="20401 - Flag Unflag" onclick="treeOnClick('20401');" />
            <sjt:treeItem id="20402" title="20402 - Manual Monitoring Input" onclick="treeOnClick('20402');" />
        </sjt:treeItem>
    </sjt:treeItem>
</sjt:tree>
<s:form id="frmMenu" action="menu_click" cssStyle="border: 0px solid white; background-color:transparent" cssClass="ui-helper-hidden">
<s:hidden name="idMenu" />
<sj:submit id="btnMenu" formIds="frmMenu" targets="ph-main" cssClass="ui-helper-hidden"/>
</s:form>