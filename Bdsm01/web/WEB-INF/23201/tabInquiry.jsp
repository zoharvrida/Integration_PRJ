<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="codeNo_" scope="request">${param.codeNo}</s:set>

<s:url var="urlGrid" action="23201_list" namespace="/json" escapeAmp="false" />

<s:actionerror name="actionError" />

<script type="text/javascript">
	Number.prototype.formatMoney = function(c, d, t) {
		var n = this;
		c = isNaN(c = Math.abs(c)) ? 2 : c;
		d = d == undefined ? "." : d;
		t = t == undefined ? "," : t;
		s = n < 0 ? "-" : "";
		i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "";
		j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "")
				+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t)
				+ (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};
	
	function customCurrency(cellValue, options, rowObjects) {
		var result = cellValue;
		result = cellValue.replace(',', '.');
		result = parseFloat(result);

		return result.formatMoney(2);
	}
	
	function customApproved(cellValue, options, rowObjects) {
		var result = " ";
		if (cellValue == true)
			result = "Y"; 
		else if (cellValue == false)
			result = "N";
		
		return result;
	}
</script>

<div id="inquiryGrid" style="overflow-x: auto; display: inline-block;" class=".ui-widget-header">
	<table>
		<tr>
			<td>Filename</td>
			<td>:</td>
			<td><s:label id="txtFilenameInq" theme="css_xhtml" /></td>
		</tr>
	</table>
	
	<sjg:grid 
		id="inqGridTable"
		caption=""
		dataType="local"
		href="%{urlGrid}"
		requestType="POST"
		pager="true"
		pagerPosition="right"
		gridModel="gridTemplate" 
		rowNum="15"
		rownumbers="true"
		shrinkToFit="false"
		width="900"
		altRows="true"
		navigator="true"
		navigatorAdd="false"
		navigatorDelete="false" 
		navigatorEdit="false" 
		navigatorRefresh="false"
		navigatorSearch="false"
		onGridCompleteTopics="inqGridCompleted"
		
	>
		<sjg:gridColumn hidden="true" id="filename" name="0" title="Filename" align="left" sortable="false" />
		<sjg:gridColumn name="1.compositeId.recordId" title="Record Row No." width="50" align="right" sortable="false" />
		<sjg:gridColumn name="1.batchId" title="Batch ID" width="70" align="center" sortable="false" />
		<sjg:gridColumn name="1.identitasPesertaPengirim" title="Sender ID" width="80" align="left" sortable="false" />
		<sjg:gridColumn name="1.sandiKotaPengirim" title="Sender Sector Code" width="70" align="left" sortable="false" />
		<sjg:gridColumn name="1.jumlahRecords" title="Total Records" width="50" align="right" formatter="integer" sortable="false" />
		<sjg:gridColumn name="1.totalNominal" title="Total Nominal" width="120" align="right" formatter="customCurrency" sortable="false" />
		<sjg:gridColumn name="1.tanggalBatch" title="Batch Date" width="100" align="center" sortable="false" />
		<sjg:gridColumn name="1.approved" title="Is Approved" width="60" align="center" formatter="customApproved" sortable="false" />
		<sjg:gridColumn name="1.comments" title="Reason" width="600" align="left" sortable="false" cssClass="fontRed" />
	</sjg:grid>
</div>

<style type="text/css">
	.fontRed { color: red }
</style>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#inqGridTable").unsubscribe("inqGridCompleted");
		$("#inqGridTable").subscribe("inqGridCompleted", function(event, data) {
			if ($("#inqGridTable_pager_left #note").length == 0) {
				if ($('#inqGridTable').jqGrid('getGridParam', 'records') == 0) {
					$("#inqGridTable_pager_left").append("<span id='note' style='color:red'>No records found</span>");
				}
				else {
					$("th.ui-th-column div").css({"white-space": "normal", "height" : "auto"});
					$("td[role='gridcell']").css("vertical-align", "middle");
					$("#txtFilenameInq").text($(data).jqGrid("getCell", 1, 1));
					$("#gview_inqGridTable div.ui-jqgrid-bdiv").css("height", $('#inqGridTable').jqGrid('getGridParam', 'records') * 23 + 20);
				}
			}
		});
		
		$("#inqGridTable").jqGrid('setGridParam', {
			datatype: 'json',
			postData: {
				'action': 'grid',
				'codeNo': function() {return '<s:property value="%{#request.codeNo_}" />';},
				'mode' : '1'
			}
		});
		$("#inqGridTable").trigger('reloadGrid');
	});
</script>
