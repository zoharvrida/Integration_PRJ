<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="25501_list" namespace="/json" escapeAmp="false">
    <s:param name="giftCode"><%=request.getParameter("giftCode")%></s:param>
    <s:param name="codProd"><%=request.getParameter("codProd")%></s:param>
    <s:param name="mode"><%=request.getParameter("mode")%></s:param>
    <s:param name="AccountNo"><%=request.getParameter("AccountNo")%></s:param>
    <s:param name="state"><%=request.getParameter("state")%></s:param>  
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
    <sjg:gridColumn name="DATEEFF" index="DATEEFF" title="Effective Date" sortable="true" width="100" hidden="true"/>
    <sjg:gridColumn name="GIFT_CODE" title="Gift Code" sortable="false" width="100"/>
    <sjg:gridColumn name="GIFT_NAME" title="Gift Name" sortable="false" width="100"/>
    <sjg:gridColumn name="TAX_PCT" title="Gift Tax" sortable="false" hidden="true"/>
    <sjg:gridColumn name="PROGRAM_ID" title="programId" sortable="false" hidden="true"/>
    <sjg:gridColumn name="PROGRAM_NAME" title="programName" sortable="false" hidden="true"/>
    <sjg:gridColumn name="ID_ACCRUAL" title="accrualID" sortable="false" hidden="true"/>
    <sjg:gridColumn name="STATUS" title="Status" sortable="false" hidden="true"/>
    <sjg:gridColumn name="ID" title="id" sortable="false" hidden="true"/>
    <sjg:gridColumn name="PROGRAM_DETAIL_ID" title="program detail" sortable="false" hidden="true"/>
    <sjg:gridColumn name="TYPE" title="type" sortable="false" hidden="true"/>
    <sjg:gridColumn name="PRODUCT" title="type" sortable="false" hidden="true"/>
	<sjg:gridColumn name="VOUCHER" title="type" sortable="false" hidden="true"/>

</sjg:grid>
<script type="text/javascript">
    $(function() {
        var setValueFromGrid = 
            function(event, data, elementName, fieldName) { 
            var value = $(data).jqGrid("getCell", event.originalEvent.id, fieldName);
            $("#" + elementName).val(value); 
        };
			
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
				
            var test = $.map(dlgParams, function(value, key) { return key; });
			
            setValueFromGrid(event, data, dlgParams.giftCode, "GIFT_CODE");
            setValueFromGrid(event, data, dlgParams.giftName, "GIFT_NAME");

            setValueFromGrid(event, data, dlgParams.taxPct, "TAX_PCT");
            setValueFromGrid(event, data, dlgParams.programID, "PROGRAM_ID");
            setValueFromGrid(event, data, dlgParams.programName, "PROGRAM_NAME");
            setValueFromGrid(event, data, dlgParams.accrualID, "ID_ACCRUAL");

            setValueFromGrid(event, data, dlgParams.status, "STATUS");
            setValueFromGrid(event, data, dlgParams.id, "ID");
            setValueFromGrid(event, data, dlgParams.programDetailId, "PROGRAM_DETAIL_ID");
            setValueFromGrid(event, data, dlgParams.type, "TYPE");
            setValueFromGrid(event, data, dlgParams.codProd, "PRODUCT");
            
			setValueFromGrid(event, data, dlgParams.voucherType, "VOUCHER");
            
			
			$("#frmMain_giftCode").val($("#giftCodes").val() +"-"+$("#giftName").val());
			$("#frmMain_programID").val($("#programIds").val() +"-"+$("#programName").val());
			$("#frmMain_Term").attr("value", null);
            $("#frmMain_Status").attr("value", null);
			$("#frmMain_giftPrices").attr("value", null);
            $("#frmMain_RangeholdAmount").attr("value", null);
            $("#frmMain_holdAmount").attr("value", null);
            $("#frmMain_tax").attr("value", null);
            $("#frmMain_Opendate").attr("value", null);
            $("#frmMain_Canceldate").attr("value", null);
			if(document.getElementById("errorpas0")){                    
						removesRow3("errorpas0");
					}
            $("#ph-dlg").dialog("close");
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
		if ($("#gridtable_pager_left #note").length == 0) {
				$("#divButtons").width($("#aprvGridTable").width());
				if ($('#gridtable').jqGrid('getGridParam', 'records') == 0) {
					$("#gridtable_pager_left").append("<span id='note' style='color:red'>Gift Record not found</span>");
				}
				else {
					$("th.ui-th-column div").css({"white-space": "normal", "height" : "auto"});
					$("input.cbox").css("vertical-align", "middle");
					$("td[role='gridcell']").css("vertical-align", "middle");
					//$("#gview_aprvGridTable div.ui-jqgrid-bdiv").css("height", $('#aprvGridTable').jqGrid('getGridParam', 'records') * 23);				
				}
			}
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 400), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 400)-22);
        });
    });
</script>