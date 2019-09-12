<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="20401CIF_list" namespace="/json" escapeAmp="false">
    <s:param name="acctNo"><%=request.getParameter("acctNo")%></s:param>
    <s:param name="period"><%=request.getParameter("period")%></s:param>
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
    <sjg:gridColumn name="COD_CUST" index="COD_CUST" title="CIF No" sortable="true" width="50"/>
    <sjg:gridColumn name="NAM_CUST_FULL" index="NAM_CUST_FULL" title="Customer Name" sortable="true"/>
    <sjg:gridColumn name="COD_ACCT_CUST_REL" index="COD_ACCT_CUST_REL" title="Customer Relationship" sortable="true" width="30"/>
    <sjg:gridColumn name="AMTAVAILUSD" index="AMTAVAILUSD" title="Available Limit" formatter="currency" hidden="true"/>
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.noCif).val($(data).jqGrid("getCell", event.originalEvent.id, "COD_CUST"));
            $("#" + dlgParams.namCustFull).val($(data).jqGrid("getCell", event.originalEvent.id, "NAM_CUST_FULL"));
            $("#" + dlgParams.codAcctCustRel).val($(data).jqGrid("getCell", event.originalEvent.id, "COD_ACCT_CUST_REL"));
            $("#" + dlgParams.amtAvailUsd).val($(data).jqGrid("getCell", event.originalEvent.id, "AMTAVAILUSD"));
            if (dlgParams.onClose != undefined) {
                dlgParams.onClose();
            }
            $("#ph-dlg").dialog("close");
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 400), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 400)-22);
        });
    });
</script>
