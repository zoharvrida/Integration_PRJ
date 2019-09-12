<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

    
<s:url var="urlGrid" action="29201Tab_list" namespace="/json" escapeAmp="false">
	 
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
	rowNum="10"
	rownumbers="true"
	altRows="true" 
	autowidth="false"
	navigator="true"
	navigatorAdd="false"
	navigatorDelete="false"
	navigatorEdit="false"
	navigatorRefresh="false"
	navigatorSearch="false"
	onGridCompleteTopics="gridCompleted"
	>
		<sjg:gridColumn name="currencyCode" index="currencyCode" title="Currency Code" width="120" align="center" sortable="false" />
		<sjg:gridColumn name="currencyName" index="currencyName" title="Currency Name" width="120" align="center" sortable="false"/>
		<sjg:gridColumn name="currencyDestination" index="currencyDestination" title="Destination Country" width="120" align="center" sortable="false" />
		<sjg:gridColumn name="sellRate" index="sellRate" title="Sell Rate" width="120" align="center" sortable="false" />
		<sjg:gridColumn name="valueDate" index="valueDate" title="Value Date" width="120" align="center" sortable="false" />
        <sjg:gridColumn name="flag" index="flag" title="Keterangan" width="120" align="center" sortable="false" />
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
        
        $("#gridtable").jqGrid('setGridParam', {
				datatype: 'json',
				postData: {
					'currencyCode': '${param.currencyCode}',
                    'valueDate': '${param.valueDate}'
                    
				}
			});
        $("#gridtable").trigger('reloadGrid');
	});
</script>
