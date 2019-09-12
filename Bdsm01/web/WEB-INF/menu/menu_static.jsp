<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjt" uri="/struts-jquery-tree-tags"%>
<script type="text/javascript">
    $(function() {
        <%--alert("hello menu");--%>
    });
</script>
<sjt:tree 
    id="treeStatic" 
    jstreetheme="classic" 
    openAllOnLoad="true" 
    title="BDSM Menu" 
    cssClass="ui-widget ui-widget-content" 
    cssStyle="border: 0px solid white; background-color:transparent"
>
    <sjt:treeItem title="10000 - Common File">
        <sjt:treeItem title="10300 - Maintenances">
            <s:url var="url10301" action='10301_input'/>
            <sjt:treeItem title="10301 - Template Registration" href="%{url10301}" targets="ph-main" />
            <s:url var="url10302" action='10302_input'/>
            <sjt:treeItem title="10302 - Menu Access Registration" href="%{url10302}" targets="ph-main" />
            <s:url var="url10303" action='10303_input'/>
            <sjt:treeItem title="10303 - User Registration" href="%{url10303}" targets="ph-main" />
        </sjt:treeItem>
    </sjt:treeItem>
    <sjt:treeItem title="20000 - Monitoring Foreign Currency Transactions">
        <sjt:treeItem title="20200 - Summary">
            <s:url var="url20201" action='20201_input'/>
            <sjt:treeItem title="20201- Transaction Monitoring Summary" href="%{url20201}" targets="ph-main" />
        </sjt:treeItem>
        <sjt:treeItem title="20300 - Maintenances">
            <s:url var="url20301" action='20301_input'/>
            <sjt:treeItem title="20301 - Statement Letter Maintenance" href="%{url20301}" targets="ph-main"/>
            <sjt:treeItem title="20302 - Underlying Document Maintenance" href="#"/>
        </sjt:treeItem>
        <sjt:treeItem title="20400 - Transaction Input">
            <sjt:treeItem title="20401 - Flag Unflag" href="#"/>
            <sjt:treeItem title="20402 - Manual Monitoring Input" href="#"/>
        </sjt:treeItem>
    </sjt:treeItem>
</sjt:tree>
