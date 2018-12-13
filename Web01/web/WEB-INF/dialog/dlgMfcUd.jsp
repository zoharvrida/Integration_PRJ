<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="20302_list" namespace="/json" escapeAmp="false">
    <s:param name="noCif"><%=request.getParameter("noCif")%></s:param>
    <s:param name="noUd"><%=request.getParameter("noUd")%></s:param>
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
    <sjg:gridColumn name="NOCIF" index="NOCIF" title="CIF No" sortable="true" width="100" hidden="true"/>
    <sjg:gridColumn name="NOUD" index="NOUD" title="UD No" sortable="true"/>
    <sjg:gridColumn name="TYPE_UD" index="TYPE_UD" title="Type" sortable="true" hidden="true" />
    <sjg:gridColumn name="PAYEE_NAME" index="PAYEE_NAME" title="Payee Name" sortable="true" hidden="true" />
    <sjg:gridColumn name="PAYEE_COUNTRY" index="PAYEE_COUNTRY" title="Payee Country" sortable="true" hidden="true" />
    <sjg:gridColumn name="TRADING_PRODUCT" index="TRADING_PRODUCT" title="Trading Product" sortable="true" hidden="true" />
    <sjg:gridColumn name="DT_ISSUED" formatter="date" width="100" 
                    formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d H:i:s'}"
                    index="DT_ISSUED" title="Issued Date" sortable="true" hidden="true" />
    <sjg:gridColumn name="DTEXPIRY" formatter="date" width="100" 
                    formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d H:i:s'}" 
                    index="DTEXPIRY" title="Expiry Date" sortable="true"/>
    <sjg:gridColumn name="CCYUD" index="CCYUD" title="CCY" sortable="true" width="30"/>
    <sjg:gridColumn name="AMTLIMIT" index="AMTLIMIT" title="Limit Amount" sortable="true"/>
    <sjg:gridColumn name="AMTLIMITUSD" index="AMTLIMITUSD" title="Limit Amount USD" sortable="true"/>
    <sjg:gridColumn name="REMARKS" index="REMARKS" title="Remarks" sortable="true" hidden="true"/>
    <sjg:gridColumn name="CDBRANCH" index="CDBRANCH" title="Branch Code" sortable="true" width="30"/>
    <sjg:gridColumn name="STATUS" index="STATUS" title="Status" sortable="true" width="30"/>
    <sjg:gridColumn name="RATFCYLIM" index="RATFCYLIM" title="RATFCYLIM" sortable="true" width="30" hidden="true"/>
    <sjg:gridColumn name="RATUSDLIM" index="RATUSDLIM" title="RATUSDLIM" sortable="true" width="30" hidden="true"/>
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.noCif).val($(data).jqGrid("getCell", event.originalEvent.id, "NOCIF"));
            $("#" + dlgParams.noUd).val($(data).jqGrid("getCell", event.originalEvent.id, "NOUD"));
            $("#" + dlgParams.typeUD).val($(data).jqGrid("getCell", event.originalEvent.id, "TYPE_UD"));
            $("#" + dlgParams.payeeName).val($(data).jqGrid("getCell", event.originalEvent.id, "PAYEE_NAME"));
            $("#" + dlgParams.payeeCountry).val($(data).jqGrid("getCell", event.originalEvent.id, "PAYEE_COUNTRY"));
            $("#" + dlgParams.tradingProduct).val($(data).jqGrid("getCell", event.originalEvent.id, "TRADING_PRODUCT"));
            $("#" + dlgParams.dtIssued).val($(data).jqGrid("getCell", event.originalEvent.id, "DT_ISSUED"));
            $("#" + dlgParams.dtExpiry).val($(data).jqGrid("getCell", event.originalEvent.id, "DTEXPIRY"));
            $("#" + dlgParams.ccyUd).val($(data).jqGrid("getCell", event.originalEvent.id, "CCYUD"));
            $("#" + dlgParams.amtLimit).val($(data).jqGrid("getCell", event.originalEvent.id, "AMTLIMIT"));
            $("#" + dlgParams.remarks).val($(data).jqGrid("getCell", event.originalEvent.id, "REMARKS"));
            $("#" + dlgParams.cdBranch).val($(data).jqGrid("getCell", event.originalEvent.id, "CDBRANCH"));
            $("#" + dlgParams.status).val($(data).jqGrid("getCell", event.originalEvent.id, "STATUS"));
            $("#" + dlgParams.amtLimitUSD).val($(data).jqGrid("getCell", event.originalEvent.id, "AMTLIMITUSD"));
            $("#" + dlgParams.ratFcyIdr).val($(data).jqGrid("getCell", event.originalEvent.id, "RATFCYLIM"));
            $("#" + dlgParams.usdIdrRate).val($(data).jqGrid("getCell", event.originalEvent.id, "RATUSDLIM"));
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
