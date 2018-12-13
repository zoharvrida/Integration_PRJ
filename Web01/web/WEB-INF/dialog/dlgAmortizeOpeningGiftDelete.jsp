<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="25501_list" namespace="/json" escapeAmp="false">
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
    <sjg:gridColumn name="ACCT_NO" index="ACCT_NO" title="Account No" sortable="true" width="100"/>
    <sjg:gridColumn name="COD_PROD" title="Product Code" sortable="false" width="100"/>
    <sjg:gridColumn name="NAM_PRODUCT" title="Product Name" sortable="false" width="200"/>

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
			
            setValueFromGrid(event, data, dlgParams.AcctNo, "ACCT_NO");
            setValueFromGrid(event, data, dlgParams.ProductCode, "COD_PROD");
            setValueFromGrid(event, data, dlgParams.NamProduct, "NAM_PRODUCT");
            //$('#OpenGiftDetails').attr('style','display:block');
			$("#frmMain_codProd").val($("#codProds").val() + "-" + $("#namProducts").val());
			$("#frmMain_Term").attr("value", null);
            $("#frmMain_Status").attr("value", null);
            $("#frmMain_giftPrices").attr("value", null);
            $("#frmMain_giftCode").attr("value", null);
			$("#frmMain_programID").attr("value", null);
            $("#frmMain_RangeholdAmount").attr("value", null);
            $("#frmMain_holdAmounts").attr("value", null);
            $("#frmMain_tax").attr("value", null);
            $("#frmMain_Opendate").attr("value", null);
            $("#frmMain_Canceldate").attr("value", null);
            $("#ph-dlg").dialog("close");
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
		if ($("#gridtable_pager_left #note").length == 0) {
				$("#divButtons").width($("#aprvGridTable").width());
				if ($('#gridtable').jqGrid('getGridParam', 'records') == 0) {
					$("#gridtable_pager_left").append("<span id='note' style='color:red'>Account not found</span>");
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
