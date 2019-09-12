<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="batchNo_" scope="request">${param.batchNo}</s:set>
<s:url var="urlGrid" action="23206_list" namespace="/json" escapeAmp="false" />
<s:actionerror name="actionError" />


<div id="headerGrid" style="overflow-x: auto; display: inline-block;" class=".ui-widget-header">
	<sjg:grid 
		id="headerGridTable"
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
		width="500"
		altRows="true"
		navigator="true"
		navigatorAdd="false"
		navigatorDelete="false" 
		navigatorEdit="false" 
		navigatorRefresh="false"
		navigatorSearch="false"
		onGridCompleteTopics="headerGridCompleted"
		onSelectRowTopics="headerGridRowSelected"
	>
		<sjg:gridColumn name="0.compositeId.recordId" title="Record Row No." width="50" align="right" sortable="false" />
		<sjg:gridColumn name="0.batchId" title="Batch ID" width="70" align="center" sortable="false" />
		<sjg:gridColumn name="1.tanggalBatch" title="Batch Date" width="100" align="center" sortable="false" />
		<sjg:gridColumn name="0.identitasPesertaPengirim" title="Sender Code" width="80" align="center" sortable="false" />
		<sjg:gridColumn name="1.sandiKotaPengirim" title="Sender Sector Code" width="70" align="center" sortable="false" />
		<sjg:gridColumn name="2.totalError" title="#Error" width="50" align="right" sortable="false" />
		<sjg:gridColumn name="0.compositeId.batchNo" title="" hidden="true" />
		<sjg:gridColumn name="1.compositeId.batchNo" title="" hidden="true" />
		<sjg:gridColumn name="1.jamTanggalMessage" title="" hidden="true" />
		<sjg:gridColumn name="0.rejectCode" title="" hidden="true" />
		<sjg:gridColumn name="0.flagError" title="" hidden="true" />
		<sjg:gridColumn name="2.errorDescription" title="" hidden="true" />
	</sjg:grid>
</div>

<style type="text/css">
	.fontRed { color: red }
</style>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#headerGridTable").unsubscribe("headerGridCompleted");
		$("#headerGridTable").subscribe("headerGridCompleted", function(event, data) {
			if ($("#headerGridTable_pager_left #note").length == 0) {
				if ($('#headerGridTable').jqGrid('getGridParam', 'records') == 0) {
					$("#headerGridTable_pager_left").append("<span id='note' style='color:red'>No records found</span>");
				}
				else {
					$("th.ui-th-column div").css({"white-space": "normal", "height" : "auto"});
					$("td[role='gridcell']").css("vertical-align", "middle");
					$("#gview_headerGridTable div.ui-jqgrid-bdiv").css("height", $('#headerGridTable').jqGrid('getGridParam', 'records') * 23 + 20);
				}
			}
		});
		
		$("#headerGridTable").unsubscribe("headerGridRowSelected");
		$("#headerGridTable").subscribe("headerGridRowSelected", function(event, data) {
			var grid = event.originalEvent.grid;
			var selectedNo = grid.jqGrid("getGridParam", "selrow");
			var isHeaderError = (grid.jqGrid("getCell", selectedNo, "0.flagError") == 'Y');
			
			$("#frmData_batchNo").val(grid.jqGrid("getCell", selectedNo, "1.compositeId.batchNo"));
			$("#frmData_recordId").val(grid.jqGrid("getCell", selectedNo, "0.compositeId.recordId"));
			$("#frmData_batchNoWS").val(grid.jqGrid("getCell", selectedNo, "0.compositeId.batchNo"));
			
			$("#frmData_batchId").val(grid.jqGrid("getCell", selectedNo, "0.batchId"));
			$("#frmData_dateTimeMessage").val(grid.jqGrid("getCell", selectedNo, "1.jamTanggalMessage"));
			$("#frmData_batchDate").val(grid.jqGrid("getCell", selectedNo, "1.tanggalBatch"));
			$("#frmData_senderCode").val(grid.jqGrid("getCell", selectedNo, "0.identitasPesertaPengirim"));
			$("#frmData_senderSectorCode").val(grid.jqGrid("getCell", selectedNo, "1.sandiKotaPengirim"));
			
			var rejectCode = grid.jqGrid("getCell", selectedNo, "0.rejectCode");
			$("label[for='lblRejectCode']")
				.html(rejectCode + ((rejectCode == "0000")? "": (" - " + grid.jqGrid("getCell", selectedNo, "2.errorDescription"))))
				.css('color', ((isHeaderError)? 'red': 'green'));
			
			
			$("#fsData").show();
			if (isHeaderError == false) {
				$("#tabbedPanelDetail").tabs({
					ajaxOptions: {
						type: 'POST',
						data: {
							'batchNo' : $("#frmData_batchNo").val(),
							'recordId': $("#frmData_recordId").val()
						}
					}
				});
				$("#tabbedPanelDetail").tabs({selected : 0});
				$("#tabbedPanelDetail").tabs("url", 0, "23206Tab_input.action");
				$("#tabbedPanelDetail").tabs("load", 0);
				$("#tabbedPanelDetail").show();
				
				$("#btnSubmit").show();
			}
			else {
				$("#tabbedPanelDetail").hide();
				$("#btnSubmit").hide();
			}
		});
		
		$("#headerGridTable").jqGrid('setGridParam', {
			datatype: 'json',
			postData: {
				'action' : 'header',
				'batchNo': function() {return '<s:property value="%{#request.batchNo_}" />';}
			}
		});
		$("#headerGridTable").trigger('reloadGrid');
	});
</script>
