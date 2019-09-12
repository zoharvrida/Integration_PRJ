<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="cardNo_" scope="request">${param.cardNo}</s:set>
<s:set var="flagCard_" scope="request">${param.flagCard}</s:set>

<s:url var="urlGrid" action="22201_list" namespace="/json" escapeAmp="false">
	<%-- <s:param name="cardNo">${param.cardNo}</s:param> --%> <%-- request parameter cardNo --%>
</s:url>

<s:actionerror name="actionError" />

<script>
	function statusFormatter(cellValue, options, rowObject) {
		var result = "-";
		var status = rowObject.tpinStatus;
		
		/*
		if (status == '0')
			result = "Not active";
		else if (status == '1')
			result = "Open";
		else if (status == '9')
			result = "Closed";
		else
		*/
			result = status;
		
		return result;
	};
</script>

<sjg:grid 
	id="gridtable" 
	caption=""
	dataType="local" 
	href="%{urlGrid}"
	requestType="POST"
	pager="true"
	pagerPosition="right"
	gridModel="gridTemplate" 
	rowNum="15"
	rownumbers="false"
	altRows="true" 
	autowidth="true"
	navigator="true"
	navigatorAdd="false"
	navigatorDelete="false"
	navigatorEdit="false"
	navigatorRefresh="false"
	navigatorSearch="false"
	onGridCompleteTopics="gridCompleted"
	>
		<sjg:gridColumn name="cardNo" index="cardNo" title="Card No" width="110" align="center" sortable="false" />
		<sjg:gridColumn name="tpinStatus" index="tpinStatus" title="TPIN Status" width="50" align="center" sortable="false" formatter="statusFormatter" />
		<sjg:gridColumn name="tpinUpdatedDate" index="tpinUpdatedDate" title="Date" width="50" align="center" sortable="false" />
		<sjg:gridColumn name="tpinUpdatedTime" index="tpinUpdatedTime" title="Time" width="50" align="center" sortable="false" />
		<sjg:gridColumn name="cifNo" index="cifNo" title="CIF No" width="50" align="center" sortable="false" />
		<sjg:gridColumn name="accountNo" index="accountNo" title="Account No" width="70" align="center" sortable="false" />
		<sjg:gridColumn name="cifName" index="cifName" title="Customer Name" sortable="false" />
</sjg:grid>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#gridtable").unsubscribe("gridCompleted");
		$("#gridtable").subscribe("gridCompleted", function(event, data) {
			if ($("#gridtable_pager_left #note").length == 0) {
				if ($('#gridtable').jqGrid('getGridParam', 'records') == 0)
					$("#gridtable_pager_left").append("<span id='note' style='color:red'>No records found</span>");
			}
		});
		
		if ("<s:property value='%{#request.cardNo_}' />" == '-1')
			;
		else {
			$("#gridtable").jqGrid('setGridParam', {
				datatype: 'json',
				postData: {
					'cardNo': function() {return '<s:property value="%{#request.cardNo_}" />';}
				}
			});
			$("#gridtable").trigger('reloadGrid');
			//$("#gridtable").trigger('reloadGrid', [{current: true}]);
		}
	});
</script>
