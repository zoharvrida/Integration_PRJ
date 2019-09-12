<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="batchNo_" scope="request">${param.batchNo}</s:set>
<s:set var="recordId_" scope="request">${param.recordId}</s:set>

<s:actionerror name="actionError" />


<script>
	function trimmer(cellValue, options, rowObject) {
		if (cellValue === undefined)
			cellValue = "";
			
		return cellValue.trim();
	};
	
	function moneyFormat(cellValue, options, rowObject) {
		var result;
		
		$('#tmpMoney').attr("value", trimmer(String(cellValue)));
		$('#tmpMoney').autoNumeric('init');
		result = $('#tmpMoney').attr("value");
		$('#tmpMoney').autoNumeric('destroy');
		
		return result;
	}
	
	function elementCityOrigin(elem) {
		$(elem).attr('name', 'data.sandiKotaAsal');
		
		$(elem).autocomplete({
			source: 'json/SknNgFinInstRoutingAutoCompleter_list.action?mode=2&BIC=' + $("#BICOrigin").attr("value"),
			minLength: 2,
			select: function(event, ui) {
						$("#idCityOrigin").attr("value", ui.item.id);
					}
		});
	}
	
	function elementCityDestination(elem) {
		$(elem).attr('name', 'data.sandiKotaTujuan');
		
		$(elem).autocomplete({
			source: 'json/SknNgFinInstRoutingAutoCompleter_list.action?mode=2&BIC=' + $("#BICDestination").attr("value"),
			minLength: 2,
			select: function(event, ui) {
						$("#idCityDestination").attr("value", ui.item.id);
					}
		});
	}
	
	
</script>


<div id="detailGrid" class=".ui-widget-header">
	<s:url var="urlGrid" action="23207_list" namespace="/json" escapeAmp="false" />
	<s:url var="urlGridEdit" action="23207_editDetail" namespace="/json" escapeAmp="false" />
	
	<s:hidden id="BICOrigin" value="" />
	<s:hidden id="BICDestination" value="" />
	<s:hidden id="idCityOrigin" value="" />
	<s:hidden id="idCityDestination" value="" />
	<s:hidden id="tmpMoney" value="" />
	
	<sjg:grid 
		id="detailGridTable"
		caption=""
		dataType="local"
		href="%{urlGrid}"
		requestType="POST"
		pager="true"
		pagerPosition="right"
		gridModel="gridTemplate" 
		rowNum="10"
		rownumbers="true"
		shrinkToFit="false"
		width="870"
		altRows="true"
		navigator="true"
		navigatorAdd="false"
		navigatorDelete="false" 
		navigatorEdit="false" 
		navigatorRefresh="false"
		navigatorSearch="false"
		editurl="%{urlGridEdit}"
		onGridCompleteTopics="detailGridCompleted"
		onDblClickRowTopics="detailGridRowDoubleClicked"
	>
		<sjg:gridColumn name="1.nomorReferensi" title="Ref. No." width="120" formatter="trimmer" align="center" />
		<sjg:gridColumn name="1.identitasPesertaPengirimAsal" title="\"Identitas Peserta Pengirim Asal\"" width="65" align="center" />
		<sjg:gridColumn name="1.sandiKotaAsal" title="\"Sandi Kota Asal\"" width="50" align="center" editable="false"
			editoptions="{dataInit: elementCityOrigin, maxlength: 4}"
		/>
		<sjg:gridColumn name="1.identitasPesertaPenerimaAkhir" title="\"Identitas Peserta Penerima Akhir\"" width="65" align="center" />
		<sjg:gridColumn name="1.sandiKotaTujuan" title="\"Sandi Kota Tujuan\"" width="50" align="center" editable="true"
			editoptions="{dataInit: elementCityDestination, maxlength: 4}"
		/>
		<sjg:gridColumn name="1.destinationAccount" title="Credit Acct." width="100" formatter="trimmer" align="right" />
		<sjg:gridColumn name="1.sourceAccount" title="Debit Acct." width="100" formatter="trimmer" align="right" />
		<sjg:gridColumn name="1.jenisTransaksi" title="\"Jenis Transaksi\"" width="60" formatter="trimmer" align="center" 
			editable="true" editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.jenisTransaksi');}, maxlength: 4}" />
		<sjg:gridColumn name="1.nominal" title="Nominal" width="110" formatter="moneyFormat" align="right" />
		<sjg:gridColumn name="0.rejectCode" title="Reject Code" width="50" align="center" sortable="false" />
		<sjg:gridColumn name="2" title="Reject Desc." width="150" sortable="false" />
		<sjg:gridColumn name="1.jenisNasabahPengirim" title="\"Jenis Nasabah Pengirim\"" width="60" formatter="trimmer" align="center" 
			editable="true" editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.jenisNasabahPengirim');}, maxlength: 1}" />
		<sjg:gridColumn name="1.statusKependudukanNasabahPengirim" title="\"Status Penduduk Pengirim\"" width="70" formatter="trimmer" 
			align="center" editable="true" 
			editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.statusKependudukanNasabahPengirim');}, maxlength: 1}" />
		<sjg:gridColumn name="1.jenisNasabahPenerima" title="\"Jenis Nasabah Penerima\"" width="60" formatter="trimmer" align="center" 
			editable="true" editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.jenisNasabahPenerima');}, maxlength: 1}" />
		<sjg:gridColumn name="1.statusKependudukanNasabahPenerima" title="\"Status Penduduk Penerima\"" width="70" formatter="trimmer" 
			align="center" editable="true" 
			editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.statusKependudukanNasabahPenerima');}, maxlength: 1}" />
		<sjg:gridColumn name="0.compositeId.recordId" title="Row No." width="30" align="right" sortable="false" />
	</sjg:grid>
</div>


<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#detailGridTable").unsubscribe("detailGridCompleted");
		$("#detailGridTable").subscribe("detailGridCompleted", function(event, data) {
			if ($("#detailGridTable_pager_left #note").length == 0) {
				var totalRecords = $('#detailGridTable').jqGrid('getGridParam', 'records'); 
				if (totalRecords == 0) {
					$("#detailGridTable_pager_left").append("<span id='note' style='color:red'>No records found</span>");
				}
				else {
					$("th.ui-th-column div").css({"white-space": "normal", "height" : "auto"});
					$("td[role='gridcell']").css("vertical-align", "middle");
					$("#gview_detailGridTable div.ui-jqgrid-bdiv").css("height", $('#detailGridTable').jqGrid('getGridParam', 'records') * 23 + 20);
				}
				
				if (totalRecords == 0)
					$("#btnSubmit").hide();
				else
					$("#btnSubmit").show();
				
				/* Set total detail records in tabHeader */
				$("#headerGridTable").jqGrid("setCell", $("#headerGridTable").jqGrid("getGridParam", "selrow"), "2.totalError", totalRecords);
			}
		});
		
		$("#detailGridTable").unsubscribe("detailGridRowDoubleClicked");
		$("#detailGridTable").subscribe("detailGridRowDoubleClicked", function(event, data) {
			var rowNo = event.originalEvent.iRow;
			var grid = $("#" + event.originalEvent.e.currentTarget.id);
			
			$("#BICOrigin").attr("value", grid.jqGrid("getCell", rowNo, "1.identitasPesertaPengirimAsal"));
			$("#BICDestination").attr("value", grid.jqGrid("getCell", rowNo, "1.identitasPesertaPenerimaAkhir"));
			$("#idCityOrigin").attr("value", "");
			$("#idCityDestination").attr("value", "");
			
			$elem1 = $("input[name='data.sandiKotaAsal']");
			$elem1
				.autocomplete("destroy")
				.autocomplete({
					source: 'json/SknNgFinInstRoutingAutoCompleter_list.action?mode=2&BIC=' + $("#BICOrigin").attr("value"),
					minLength: 2,
					select: function(event, ui) {
								$("#idCityOrigin").attr("value", ui.item.id);
							}
				});
			
			$elem2 = $("input[name='data.sandiKotaTujuan']");
			$elem2
				.autocomplete("destroy")
				.autocomplete({
					source: 'json/SknNgFinInstRoutingAutoCompleter_list.action?mode=2&BIC=' + $("#BICDestination").attr("value"),
					minLength: 2,
					select: function(event, ui) {
								$("#idCityDestination").attr("value", ui.item.id);
							}
				});
				
			
			$("#detailGridTable").jqGrid("editGridRow", rowNo, {
				bSubmit: 'Save',
				editData: {
					'batchNo'  : '<s:property value="%{#request.batchNo_}" />',
					'recordId' : grid.jqGrid("getCell", rowNo, "0.compositeId.recordId"),
					'idUser'   : '<s:property value="%{#session.idUser}" />'
				},
				viewPagerButtons: false,
				closeAfterEdit: true,
				beforeSubmit: function(postData, formId) {
								postData['data.sandiKotaAsal'] = ($("#idCityOrigin").attr("value") == "" ? $elem1.attr("value"): $("#idCityOrigin").attr("value"));
								$("#detailGridTable").jqGrid("setCell", $("#detailGridTable").jqGrid("getGridParam", "selrow"), "1.sandiKotaAsal", $("#idCityOrigin").attr("value"));
								
								postData['data.sandiKotaTujuan'] = ($("#idCityDestination").attr("value") == "" ? $elem2.attr("value"): $("#idCityDestination").attr("value"));
								$("#detailGridTable").jqGrid("setCell", $("#detailGridTable").jqGrid("getGridParam", "selrow"), "1.sandiKotaTujuan", $("#idCityDestination").attr("value"));
								
								return [true, ''];
							},
				reloadAfterSubmit:true
			});
		});
		
		$("#detailGridTable").jqGrid('setGridParam', {
			datatype: 'json',
			postData: {
				'action'  : 'detail',
				'batchNo' : function() {return '<s:property value="%{#request.batchNo_}" />';},
				'recordId': function() {return '<s:property value="%{#request.recordId_}" />';} 
			}
		});
		$("#detailGridTable").trigger('reloadGrid');
	});
</script>
