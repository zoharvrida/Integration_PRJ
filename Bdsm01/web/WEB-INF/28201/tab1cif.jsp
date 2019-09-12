<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url var="urlGrid" action="20201LimUD_list" namespace="/json" escapeAmp="false">
	<s:param name="flgAcct">${param.flgAcct}</s:param>
	<s:param name="noAccount">${param.noAccount}</s:param>
</s:url>

<sjg:grid 
	id="gridtable2" 
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
	navigator="true"
	navigatorAdd="false" 
	navigatorDelete="false" 
	navigatorEdit="false"
	navigatorRefresh="false" 
	navigatorSearch="false"
	onGridCompleteTopics="gridComplete"
>
	<sjg:gridColumn name="COD_CUST_ID" index="COD_CUST_ID" title="CIF" sortable="false" width="100" />
	<sjg:gridColumn name="NAM_CUST_FULL" index="NAM_CUST_FULL" title="Name" sortable="false" />
	<sjg:gridColumn name="NOUD" index="NOUD" title="UD No" sortable="false" />
	<sjg:gridColumn name="CCYUD" index="CCYUD" title="CCY" sortable="false" align="center" width="50" />
	<sjg:gridColumn name="AMTLIMIT" index="AMTLIMIT" title="Limit Amount" align="right" formatter="currency" sortable="false" />
	<sjg:gridColumn name="AMTAVAIL" index="AMTAVAIL" title="Avail Amount" align="right" formatter="currency" sortable="false" hidden="true" />
	<sjg:gridColumn name="AMTLIMITUSD" index="AMTLIMITUSD" title="Limit Amount USD" align="right" formatter="currency" sortable="false" />
	<sjg:gridColumn name="AMTAVAILUSD" index="AMTAVAILUSD" title="Avail Amount USD" align="right" formatter="currency" sortable="false" />
	<sjg:gridColumn name="CDBRANCH" index="CDBRANCH" title="UD Branch" sortable="false" align="center" width="80" />
	<sjg:gridColumn name="DTEXPIRY" formatter="date" width="100" align="center" 
					formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d H:i:s'}" index="DTEXPIRY" title="Expiry Date" sortable="false" />
</sjg:grid>


<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#gridtable2").unsubscribe('gridComplete');
		$("#gridtable2").subscribe("gridComplete", function(event, data) {
			if ($("#gridtable2_pager_left #note3").length == 0) {
				if ($('#gridtable2').jqGrid('getGridParam', 'records') == 0)
					$("#gridtable2_pager_left").append("<span id='note3' style='color:red'>No records found</span>");
			}
		});
	});
</script>
