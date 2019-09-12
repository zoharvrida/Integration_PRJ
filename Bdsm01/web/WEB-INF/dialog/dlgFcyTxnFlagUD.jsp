<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="20401UD_list" namespace="/json" escapeAmp="false">
    <s:param name="noCif"><%=request.getParameter("noCif")%></s:param>
    <s:param name="ccyUd"><%=request.getParameter("ccyUd")%></s:param>
    <s:param name="noUd"><%=request.getParameter("noUd")%></s:param>
    <s:param name="amtTxn"><%=request.getParameter("amtTxn")%></s:param>
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
    <sjg:gridColumn name="NOUD" index="NOUD" title="UD No" sortable="true"/>
    <sjg:gridColumn name="CCYUD" index="CCYUD" title="CCY UD" sortable="true" width="30" hidden="true"/>
    <sjg:gridColumn name="AMTLIMIT" index="AMTLIMIT" title="Amount Limit" formatter="currency" sortable="true" align="right"/>
    <sjg:gridColumn name="AMTAVAIL" index="AMTAVAIL" title="Available Limit" formatter="currency" sortable="true" align="right" hidden="true"/>
    <sjg:gridColumn name="AMTLIMITUSD" index="AMTLIMITUSD" title="Amount Limit USD" formatter="currency" sortable="true" align="right"/>
    <sjg:gridColumn name="AMTAVAILUSD" index="AMTAVAILUSD" title="Available Limit USD" formatter="currency" sortable="true" align="right"/>    
    <sjg:gridColumn name="CDBRANCH" index="CDBRANCH" title="Branch" sortable="true" width="50"/>
    <sjg:gridColumn name="DTEXPIRY" formatter="date" width="100" 
                    formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d H:i:s'}" 
                    index="DTEXPIRY" title="Expiry Date" sortable="true"/>
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.noUd).val($(data).jqGrid("getCell", event.originalEvent.id, "NOUD"));
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
