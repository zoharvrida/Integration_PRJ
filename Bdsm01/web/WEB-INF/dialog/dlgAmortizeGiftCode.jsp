<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>


<script type="text/javascript">
	function customProduct(cellValue, options, rowObjects) {
		return rowObjects[0].compositeId.productCode + " - " + rowObjects[1].namProduct;
	}
	
	function customBooleanValue(cellValue, options, rowObjects) {
		return cellValue? "1": "0";
	}
</script>

<s:url var="urlGrid" action="25301PickList_list" namespace="/json" />
<sjg:grid 
	id="gridTable"
	caption=""
	dataType="local"
	href="%{urlGrid}"
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
	onSelectRowTopics="selectRow"
	onGridCompleteTopics="gridCompleted"
	>
		<sjg:gridColumn 
			name="0.compositeId.giftCode"
			index="0.compositeId.giftCode"
			title="GIFT CODE"
			width="35"
			align="center"
			sortable="false"
		/>
		<sjg:gridColumn
			name="0.giftName"
			index="0.giftName"
			title="GIFT NAME"
			width="60"
			sortable="false"
		/>
		<sjg:gridColumn
			name="productCodeName"
			index="productCodeName"
			title="PRODUCT"
			formatter="customProduct"
			width="100"
			sortable="false"
		/>
		<sjg:gridColumn name="0.type" title="TYPE" hidden="true" />
		<sjg:gridColumn name="0.status" title="STATUS" formatter="customBooleanValue" hidden="true" />
		<sjg:gridColumn name="0.programId" title="PROGRAM ID" hidden="true" />
		<sjg:gridColumn name="0.programName" title="PROGRAM NAME" hidden="true" />
		<sjg:gridColumn name="0.idAccrual" title="ID ACCRUAL" hidden="true" />
		<sjg:gridColumn name="0.taxPercent" title="TAX" hidden="true" />
		<sjg:gridColumn name="0.amortizeMethod" title="AMORTIZE METHOD" hidden="true" />
		<sjg:gridColumn name="0.voucher" title="VOUCHER" hidden="true" />
		<sjg:gridColumn name="1.compositeId.codProd" title="PRODUCT CODE" hidden="true" />
		<sjg:gridColumn name="1.namProduct" title="PRODUCT NAME" hidden="true" />
		
</sjg:grid>

<script type="text/javascript">
	$(function() {
		var setValueFromGridField = 
			function(event, data, elementName, fieldName) { 
				var value = $(data).jqGrid("getCell", event.originalEvent.id, fieldName);
				$("#" + elementName).val(value); 
			};
		
		$("#gridTable").unsubscribe("selectRow");
		$("#gridTable").subscribe("selectRow",
				function(event, data) {
					//var test = $.map(dlgParams, function(value, key) { return key; });
					//console.log(test);
					
					setValueFromGridField(event, data, dlgParams.id, "0.compositeId.giftCode");
					setValueFromGridField(event, data, dlgParams.desc, "0.giftName");
					setValueFromGridField(event, data, dlgParams.type, "0.type");
					setValueFromGridField(event, data, dlgParams.status, "0.status");
					setValueFromGridField(event, data, dlgParams.progId, "0.programId");
					setValueFromGridField(event, data, dlgParams.progDesc, "0.programName");
					setValueFromGridField(event, data, dlgParams.idAccrual, "0.idAccrual");
					setValueFromGridField(event, data, dlgParams.taxPercent, "0.taxPercent");
					setValueFromGridField(event, data, dlgParams.method, "0.amortizeMethod");
					setValueFromGridField(event, data, dlgParams.voucher, "0.voucher");
					setValueFromGridField(event, data, dlgParams.prodId, "1.compositeId.codProd");
					setValueFromGridField(event, data, dlgParams.prodDesc, "productCodeName");
					dlgParams.selected = true;
					
					$("#ph-dlg").dialog("close");
				});
		
		$("#gridTable").unsubscribe("gridCompleted");
		$("#gridTable").subscribe("gridCompleted",
				function(event, data) {
					$("#ph-dlg").dialog({
						width : Math.min(screen.availWidth - 20, 500),
						height : $("#gbox_gridTable").height() + 
									$("div.ui-dialog-titlebar.ui-widget-header").height() + 44
					});
					$("#ph-dlg").dialog({
						position : "center"
					});
					$(data).jqGrid("setGridWidth", Math.min(screen.availWidth - 20, 500) - 22);
				});
				
		
		$("#gridTable").jqGrid('setGridParam', {
			datatype: 'json',
			postData: {
				'giftCode': function() {return '<s:property value="%{#parameters.giftCode}" />';}
			}
		});
		$("#gridTable").trigger('reloadGrid');
	});
</script>
