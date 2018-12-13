<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:set var="cifNo_" scope="request">${param.cifNo}</s:set>

<s:url var="urlGridExist" action="26301_list" namespace="/json" escapeAmp="false">
    <s:param name="cifNo"><%=request.getParameter("dlgcifNo")%></s:param>    
</s:url>
<sjg:grid
    id="gridtableExist"
    caption="Existing Account by CIF"
    dataType="json"
    href="%{urlGridExist}"
    requestType="POST"
    pager="true"
    pagerPosition="right"
    gridModel="gridTemplate"
    rowNum="15"
    rownumbers="true"
    altRows="true"
    autowidth="true"
    navigator="false"
    navigatorAdd="false"
    navigatorDelete="false"
    navigatorEdit="false"
    navigatorRefresh="false"
    navigatorSearch="false"
    onSelectRowTopics="selectRow" onGridCompleteTopics="gridComplete"
    >
    <%-- Revision : Change Field Mapping 
             Change Date : 23-Februari-2016   
             Changer : v00019722 
			 Begin 1 --%>
    <sjg:gridColumn name="accountid" index="accountid" title="Account No" sortable="false" width="50"/>
    <sjg:gridColumn name="customerId" index="customerId" title="Customer ID" sortable="false" hidden="true"/>   
    <sjg:gridColumn name="productCode" index="productCode" title="Product Code" sortable="false" width="30"/>
    <sjg:gridColumn name="productName" index="productName" title="Product Name" sortable="false" width="120"/>
    <sjg:gridColumn name="branchCode" index="branchCode" title="Home Branch" sortable="false" width="30"/>
    <sjg:gridColumn name="currency" index="currency" title="Currency" sortable="false" width="60"/>
    <sjg:gridColumn name="accountStatus" index="accountStatus" title="Account Status" sortable="false" width="80"/>
    <sjg:gridColumn name="acctType" index="acctType" title="Account Type" sortable="true" width="30"/>
    <sjg:gridColumn name="balance2" index="balance2" title="Original Balance " sortable="false" width="40"/>
    <sjg:gridColumn name="balance1" index="balance1" title="Balance Available" sortable="false" width="40"/>
    <sjg:gridColumn name="balance3" index="balance3" title="balance3" sortable="false" width="100" hidden="true"/>
    <sjg:gridColumn name="balance4" index="balance4" title="balance4" sortable="false" hidden="true"/>
    <sjg:gridColumn name="dat_next_due" index="dat_next_due" title="Due Date" sortable="false" width="100" hidden="true"/>
    <sjg:gridColumn name="flg_statement" index="flg_statement" title="Flag Statement" sortable="false" hidden="true"/>
	<%-- End 1 --%>
</sjg:grid>
<script type="text/javascript">
    $(function() {
        //alert("TESTING");
        $("#gridtableExist").unsubscribe("selectRow");
        $("#gridtableExist").subscribe("selectRow", function(event, data) {

        });
		
        $("#gridtableExist").unsubscribe("gridComplete");
        $("#gridtableExist").subscribe("gridComplete", function(event, data) {
            //$("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 400), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({width: Math.min(screen.availWidth - 20, 850), height: $("#gbox_gridtableExist").height() + $("div.ui-dialog-titlebar.ui-widget-header").height() + 44});
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth - 20, 850) - 22);
            /*$("#ph-dlg").dialog({
                        width: Math.min(screen.availWidth - 20, 400),
                        height: $("#gbox_gridTable").height() +
                                $("div.ui-dialog-titlebar.ui-widget-header").height() + 44
                    });
                    $("#ph-dlg").dialog({
                        position: "center"
                    });
             $(data).jqGrid("setGridWidth", Math.min(screen.availWidth - 20, 400) - 22);*/
	        });
        //$("#gridtableExist").trigger('reloadGrid');
    });
</script>
<%
    }%>