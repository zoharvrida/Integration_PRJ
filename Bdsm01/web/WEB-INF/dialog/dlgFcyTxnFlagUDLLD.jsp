<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="20401UD_list" namespace="/json" escapeAmp="false">
    <s:param name="noCif"><%=request.getParameter("noCif")%></s:param>
    <s:param name="ccyUd"><%=request.getParameter("ccyUd")%></s:param>
    <s:param name="noUd"><%=request.getParameter("noUd")%></s:param>
    <s:param name="amtTxn"><%=request.getParameter("amtTxn")%></s:param>
    <s:param name="noAcct"><%=request.getParameter("noAcct")%></s:param>
</s:url>
<sjg:grid
    id="gridtable"
    caption=""
    dataType="json"
    href="%{urlGrid}"
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
    <sjg:gridColumn name="compositeId.noUd" index="compositeId.noUd" title="UD No" sortable="true"/>
    <sjg:gridColumn name="ccyUd" index="ccyUd" title="CCY UD" sortable="true" width="30" hidden="true"/>
    <sjg:gridColumn name="amtLimit" index="amtLimit" title="Amount Limit" formatter="currency" sortable="true" align="right"/>
    <sjg:gridColumn name="amtAvail" index="amtAvail" title="Available Limit" formatter="currency" sortable="true" align="right" hidden="true"/>
    <sjg:gridColumn name="amtLimitUsd" index="amtLimitUsd" title="Amount Limit USD" formatter="currency" sortable="true" align="right"/>
    <sjg:gridColumn name="amtAvailUsd" index="amtAvailUsd" title="Available Limit USD" formatter="currency" sortable="true" align="right"/>    
    <sjg:gridColumn name="udTypeCategory" index="udTypeCategory" title="UD Category" sortable="true" hidden="true"/>
    <sjg:gridColumn name="udTypeValue" index="udTypeValue" title="UD Type" sortable="true" hidden="true"/>
    <sjg:gridColumn name="cdBranch" index="cdBranch" title="Branch" sortable="true" width="50"/>
    <sjg:gridColumn name="dtExpiry" formatter="date" width="100" 
                    formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d H:i:s'}" 
                    index="dtExpiry" title="Expiry Date" sortable="true"/>
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.noUd).val($(data).jqGrid("getCell", event.originalEvent.id, "compositeId.noUd"));
            $("#" + dlgParams.udTypeCategory).val($(data).jqGrid("getCell", event.originalEvent.id, "udTypeCategory"));
            $("#" + dlgParams.udTypeValue).val($(data).jqGrid("getCell", event.originalEvent.id, "udTypeValue"));

            if (dlgParams.onClose != undefined) {
                dlgParams.onClose();
            }
            $("#ph-dlg").dialog("close");
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 800), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 800)-22);
        });
    });
</script>
