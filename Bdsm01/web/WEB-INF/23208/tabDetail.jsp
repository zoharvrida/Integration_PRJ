<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="batchNo_" scope="request">${param.batchNo}</s:set>
<s:set var="recordId_" scope="request">${param.recordId}</s:set>

<s:actionerror name="actionError" />


<script>
	function trimmer(cellValue, options, rowObject) {
		return cellValue.trim();
	}
	
	function moneyFormat(cellValue, options, rowObject) {
		var result;
		
		$('#tmpMoney').attr("value", trimmer(String(cellValue)));
		$('#tmpMoney').autoNumeric('init');
		result = $('#tmpMoney').attr("value");
		$('#tmpMoney').autoNumeric('destroy');
		
		return result;
	}
	
	function elementPublisherSector(elem) {
		$(elem).attr('name', 'data.sandiKotaAsal');
		
		$(elem).autocomplete({
			source: 'json/SknNgFinInstRoutingAutoCompleter_list.action?BIC=' + $("#BIC").attr("value"),
			minLength: 2,
			select: function(event, ui) {
						$("#idSectorCode").attr("value", ui.item.id);
					}
		});
	}
</script>


<div id="detailGrid" class=".ui-widget-header">
	<s:url var="urlGrid" action="23208_list" namespace="/json" escapeAmp="false" />
	<s:url var="urlGridEdit" action="23208_editDetail" namespace="/json" escapeAmp="false" />
	
	<s:hidden id="BIC" value="" />
	<s:hidden id="idSectorCode" value="" />
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
		<sjg:gridColumn name="1.identitasPesertaPenerima" title="\"Identitas Peserta Penerima\"" width="65" align="center" />
		<sjg:gridColumn name="1.sandiKotaAsal" title="\"Sandi Kota Asal\"" width="55" align="center" editable="true"
			editoptions="{dataInit: elementPublisherSector, maxlength: 4}"
		/>
		<sjg:gridColumn name="1.namaNasabahPemegang" title="\"Nama Nasabah Pemegang\"" width="150" formatter="trimmer" align="left" />
		<sjg:gridColumn name="1.noRekeningNasabahPemegang" title="Credit Acct." width="110" formatter="trimmer" align="right" />
		<sjg:gridColumn name="1.noRekeningNasabahTertarik" title="Debit Acct." width="110" formatter="trimmer" align="right" />
		<sjg:gridColumn name="1.noWarkat" title="\"No. Warkat\"" width="50" formatter="trimmer" align="center" />
		<sjg:gridColumn name="1.nominal" title="Nominal" width="110" formatter="moneyFormat" align="right" />
		<sjg:gridColumn name="0.rejectCode" title="Reject Code" width="50" align="center" sortable="false" />
		<sjg:gridColumn name="2" title="Reject Desc." width="150" sortable="false" />
		<sjg:gridColumn name="1.jenisTransaksi" title="\"Jenis Transaksi\"" width="60" formatter="trimmer" align="center" 
			editable="true" editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.jenisTransaksi');}, maxlength: 4}" />
		<sjg:gridColumn name="1.NPWP" title="\"NPWP\"" width="110" formatter="trimmer" 
			editable="true" editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.NPWP');}, maxlength: 35}" />
		<sjg:gridColumn name="1.kota" title="\"Kota\"" width="150" formatter="trimmer" 
			editable="true" editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.kota');}, maxlength: 35}" />
		<sjg:gridColumn name="1.propinsi" title="\"Propinsi\"" width="110" formatter="trimmer" 
			editable="true" editoptions="{dataInit: function(elem) {$(elem).attr('name', 'data.propinsi');}, maxlength: 35}" />
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
			
			$("#BIC").attr("value", grid.jqGrid("getCell", rowNo, "1.identitasPesertaPenerima"));
			$("#idSectorCode").attr("value", "");
			
			$elem = $("input[name='data.sandiKotaAsal']");
			$elem
				.autocomplete("destroy")
				.autocomplete({
					source: 'json/SknNgFinInstRoutingAutoCompleter_list.action?BIC=' + $("#BIC").attr("value"),
					minLength: 2,
					select: function(event, ui) {
								$("#idSectorCode").attr("value", ui.item.id);
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
								postData['data.sandiKotaAsal'] = ($("#idSectorCode").attr("value") == "" ? $elem.attr("value"): $("#idSectorCode").attr("value"));
								$("#detailGridTable").jqGrid("setCell", $("#detailGridTable").jqGrid("getGridParam", "selrow"), "1.sandiKotaAsal", $("#idSectorCode").attr("value"));
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
