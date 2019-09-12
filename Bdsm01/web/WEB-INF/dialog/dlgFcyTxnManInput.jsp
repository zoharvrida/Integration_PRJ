<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="20402_list" namespace="/json" escapeAmp="false">
    <s:param name="noAcct"><%=request.getParameter("noAcct")%></s:param>
    <s:param name="typMsg"><%=request.getParameter("typMsg")%></s:param>
    <s:param name="typAcct"><%=request.getParameter("typAcct")%></s:param>
    <s:param name="refTxn"><%=request.getParameter("refTxn")%></s:param>
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
    <sjg:gridColumn name="REFTXN" index="REFTXN" title="Txn Ref No" sortable="true"/>
    <sjg:gridColumn name="DTMTXN" formatter="date" width="100" 
                    formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d H:i:s'}" 
                    index="DTMTXN" title="Txn Date Time" sortable="true"/>
    <sjg:gridColumn name="STRCCYTXN" index="STRCCYTXN" title="CCY Txn" sortable="true" width="30"/>
    <sjg:gridColumn name="AMTTXN" index="AMTTXN" title="Txn Amount" sortable="true" width="100"/>
    <sjg:gridColumn name="RATFCYIDR" index="RATFCYIDR" title="FCY/IDR Rate" sortable="true" width="50"/>
    <sjg:gridColumn name="AMTTXNLCY" index="AMTTXNLCY" title="LCY Amount" sortable="true" hidden="true"/>
    <sjg:gridColumn name="RATUSDIDR" index="RATUSDIDR" title="USD/IDR Rate" sortable="true" hidden="true"/>
    <sjg:gridColumn name="AMTTXNUSD" index="AMTTXNUSD" title="USD Amount" sortable="true" hidden="true"/>
    <sjg:gridColumn name="TXNDESC" index="TXNDESC" title="Txn Description" sortable="true" hidden="true"/>
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.refTxn).val($(data).jqGrid("getCell", event.originalEvent.id, "REFTXN"));
            $("#" + dlgParams.dtmTxn).val($(data).jqGrid("getCell", event.originalEvent.id, "DTMTXN"));
            $("#" + dlgParams.ccyTxn).val($(data).jqGrid("getCell", event.originalEvent.id, "STRCCYTXN"));
            $("#" + dlgParams.amtTxn).val($(data).jqGrid("getCell", event.originalEvent.id, "AMTTXN"));
            $("#" + dlgParams.ratFcyIdr).val($(data).jqGrid("getCell", event.originalEvent.id, "RATFCYIDR"));
            $("#" + dlgParams.amtTxnLcy).val($(data).jqGrid("getCell", event.originalEvent.id, "AMTTXNLCY"));
            $("#" + dlgParams.ratUsdIdr).val($(data).jqGrid("getCell", event.originalEvent.id, "RATUSDIDR"));
            $("#" + dlgParams.amtTxnUsd).val($(data).jqGrid("getCell", event.originalEvent.id, "AMTTXNUSD"));
            $("#" + dlgParams.txnDesc).val($(data).jqGrid("getCell", event.originalEvent.id, "TXNDESC"));
            if (dlgParams.onClose != undefined) {
                dlgParams.onClose();
            }
            $("#ph-dlg").dialog("close");
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 700), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 700)-22);
        });
    });
</script>
