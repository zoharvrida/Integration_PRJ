<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>


<s:url var="urlGrid" action="20201PendingTxn_list" namespace="/json" escapeAmp="false">
	<s:param name="noAccount">${param.noAccount}</s:param>
</s:url>

<sjg:grid 
	id="gridtable3" 
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
	navigatorExtraButtons="{
        flag: {
            title: 'Flag Pending Trx',
            caption: 'Flag',
            icon: 'ui-icon-flag',
            topic: 'flagTrx'
        }
    }"
	onSelectRowTopics="selectRow" 
	onGridCompleteTopics="gridComplete"
>
	<sjg:gridColumn name="DTMTXN" formatter="date" width="120" align="right" 
					formatoptions="{newformat : 'd/m/Y h:i:s', srcformat : 'Y-m-d H:i:s'}" index="DTMTXN" title="Txn Date Time" sortable="false" />
	<sjg:gridColumn name="DTPOST" formatter="date" width="100" align="center" 
					formatoptions="{newformat : 'd/m/Y', srcformat : 'Y-m-d H:i:s'}" index="DTPOST" title="Posting Date" sortable="false" />
	<sjg:gridColumn name="REFTXN" index="REFTXN" title="Txn Ref No" sortable="false" />
	<sjg:gridColumn name="CCYTXN" index="CCYTXN" title="Txn Ccy" sortable="false" align="center" width="50" />
	<sjg:gridColumn name="AMTTXN" index="AMTTXN" title="Txn Amount" align="right" formatter="currency" sortable="false" width="100" />
	<sjg:gridColumn name="IDTXN" index="IDTXN" title="User ID" sortable="false" align="center" width="100" />
	<sjg:gridColumn name="TXNBRANCH" index="TXNBRANCH" title="Txn Branch" sortable="false" align="center" width="70" />
	<sjg:gridColumn name="TYPMSG" index="TYPMSG" title="Txn Type" hidden="true" width="50" />
	<sjg:gridColumn name="NOACCT" index="NOACCT" title="No Account" sortable="false" hidden="false" width="100" />
	<sjg:gridColumn name="TYPACCT" index="TYPACCT" title="Type Acct" hidden="true" width="50" />
	<sjg:gridColumn name="TYPTRX" index="TYPTRX" title="Type Trx" hidden="true" width="50" />
</sjg:grid>


<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#gridtable3").unsubscribe("selectRow");
		$("#gridtable3").subscribe("selectRow", function(event, data) {
			$("#gridtable3").data("noAcct", $(data).jqGrid("getCell", event.originalEvent.id, "NOACCT"));
			$("#gridtable3").data("refTxn", $.trim($(data).jqGrid("getCell", event.originalEvent.id, "REFTXN")));
			$("#gridtable3").data("typMsg", $(data).jqGrid("getCell", event.originalEvent.id, "TYPMSG"));
			$("#gridtable3").data("typAcct", $(data).jqGrid("getCell", event.originalEvent.id, "TYPACCT"));
			$("#gridtable3").data("typTrx", $(data).jqGrid("getCell", event.originalEvent.id, "TYPTRX"));

			var flagUnflag = $(data).jqGrid("getCell", event.originalEvent.id, "TYPMSG");

			if (flagUnflag == 'O') {
				$("#gridtable3_pager_left div.ui-pg-div:contains('Flag')").show();
				//  $("#gridtable3_pager_left div.ui-pg-div:contains('Unflag')").hide();
			}
			else {
				$("#gridtable3_pager_left div.ui-pg-div:contains('Flag')").hide();
				// $("#gridtable3_pager_left div.ui-pg-div:contains('Unflag')").show();
			}
			$("#gridtable3_pager_left #note2").hide();
		});

		$("#gridtable3").unsubscribe("flagTrx");
		$("#gridtable3").subscribe("flagTrx", function(event, data) {
			var noAcct = $("#gridtable3").data("noAcct");
			var refTxn = $("#gridtable3").data("refTxn");
			var typMsg = $("#gridtable3").data("typMsg");
			var typAcct = $("#gridtable3").data("typAcct");
			var typTrx = $("#gridtable3").data("typTrx");

			if (noAcct == null || refTxn == null) {
				alert("Please select one of the record to inquire Statement Letter");
			}
			else if (typMsg == 'O') {
				$("#frmGo_idMenu").val("28401");
				$("#frmGo_noAcct").val(noAcct);
				$("#frmGo_refTxn").val(refTxn);
				$("#frmGo_typMsg").val(typMsg);
				$("#frmGo_typAcct").val(typAcct);
				$("#frmGo_typTrx").val(typTrx);
				$("#frmGo_goAction").val("flag");
				$("#btnGo").click();
			}
		});

		/*   $("#gridtable3").unsubscribe("unflagTrx");
		   $("#gridtable3").subscribe("unflagTrx", function(event, data) {
		       var noAcct = $("#gridtable3").data("noAcct");
		       var refTxn = $("#gridtable3").data("refTxn");
		       var typMsg = $("#gridtable3").data("typMsg");
		       if (noAcct == null || refTxn == null) {
		           alert("Please select one of the record to inquire Statement Letter");
		       } else if (typMsg=='R') {
		           $("#frmGo_idMenu").val("20401");
		           $("#frmGo_noAcct").val(noAcct);
		           $("#frmGo_refTxn").val(refTxn);
		           $("#frmGo_typMsg").val(typMsg);
		           $("#frmGo_goAction").val("unflag");
		           $("#btnGo").click();
		       }
		   });*/

		$("#gridtable3").unsubscribe('gridComplete');
		$("#gridtable3").subscribe("gridComplete", function(event, data) {
			$("#gridtable3_pager_left div.ui-pg-div:contains('Flag')").hide();
			//     $("#gridtable3_pager_left div.ui-pg-div:contains('Unflag')").hide();
			if ($("#gridtable3_pager_left #note2").length == 0) {
				if ($('#gridtable3').jqGrid('getGridParam', 'records') == 0)
					$("#gridtable3_pager_left").append("<span id='note2' style='color:red'>No records found</span>");
				else
					$("#gridtable3_pager_left").append("<span id='note2' style='color:red'>Please select a record to activate button</span>");
			}
			$("#gridtable3").data("noAcct", "");
			$("#gridtable3").data("refTxn", "");
		});
	});
</script>
