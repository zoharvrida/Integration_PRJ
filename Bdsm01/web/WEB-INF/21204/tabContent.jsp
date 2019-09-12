<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="status_" scope="request">${param.status}</s:set>
<s:set var="currency_" scope="request">${param.currency}</s:set>
<s:set var="branch_" scope="request">${param.branch}</s:set>
<s:set var="datTxn_" scope="request">${param.datTxn}</s:set>

<s:url var="urlGrid" action="21204_list" namespace="/json" escapeAmp="false"></s:url>

<s:actionerror name="actionError" />

<sjg:grid 
    id="gridtablecdBranch" 
    caption="Search Result for Denom Transaction"
    title="Search Result for Denom Transaction"
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
    navigatorExtraButtons="{
    inqDT: {
    title: 'Inquire Denom Transaction',
    caption: 'Inquire DT',
    icon: 'ui-icon-flag',
    topic: 'inqDT'
    },
    addDT: {
    title: 'Add Denom Transaction',
    caption: 'Add DT',
    icon: 'ui-icon-document',
    topic: 'addDT'
    },
	editDT: {
    title: 'Edit Denom Transaction',
    caption: 'Edit DT',
    icon: 'ui-icon-document',
    topic: 'editDT'
    },
    confirmDT: {
    title: 'Confirm Denom Transaction',
    caption: 'Confirm DT',
    icon: 'ui-icon-suitcase',
    topic: 'confirmDT'
    }
    }"
    onSelectRowTopics="selectRowDenom" 
    onGridCompleteTopics="gridCompletedDenom"
    >
    <sjg:gridColumn name="no" index="no" title="No" width="50" align="center" sortable="false" />
    <sjg:gridColumn name="dtmRequest" title="Date" width="100" align="center" sortable="false"  />
    <sjg:gridColumn name="txnId" index="txnId" title="Transaction ID" width="50" align="center" sortable="false" />
    <sjg:gridColumn name="idBranch" title="Branch" width="100" align="center" sortable="false" />
    <sjg:gridColumn name="codCcy" title="Currency" width="100" align="center" sortable="false" />
    <sjg:gridColumn name="statusDesc" title="Status" width="100" align="center" sortable="false" />
    <sjg:gridColumn name="status" title="StatusVal" width="100" align="center" sortable="false" hidden="true"/>
    <sjg:gridColumn name="totalAmtReq" title="Amount" width="100" align="center" sortable="false" />
    <sjg:gridColumn name="idVendor" title="Vendor Name" width="100" align="center" sortable="false" />
    <sjg:gridColumn name="customer" title="Customer" width="100" align="center" sortable="false" />

</sjg:grid>
<script type="text/javascript">
    jQuery(document).ready(function() {
        $("#gridtablecdBranch").jqGrid('setGridParam', {
            datatype: 'json',
            postData: {
                'status': function() {
                    return '<s:property value="%{#request.status_}" />';
                },
                'currency': function() {
                    return '<s:property value="%{#request.currency_}" />';
                },
                'branch': function() {
                    return '<s:property value="%{#request.branch_}" />';
                },
                'dateTxn': function() {
                    return '${param.datTxn}';
                }
            }
        });
        $("#gridtablecdBranch").trigger('reloadGrid');

        $("#gridtablecdBranch").unsubscribe("selectRowDenom");
        $("#gridtablecdBranch").subscribe("selectRowDenom", function(event, data) {
            //$("#gridtable").data("noCif", $(data).jqGrid("getCell", event.originalEvent.id, "COD_CUST"));
            //$("#gridtable").data("sl", $(data).jqGrid("getCell", event.originalEvent.id, "ACTION"));
            var status = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "status");
            if (status === "2") {
                $("#gridtablecdBranch_left div.ui-pg-div:contains('Confirm DT')").show();
                $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Add DT')").show();
                $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Inquire DT')").show();
			} else if(status === "6") {
				$("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Confirm DT')").hide();
                $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Add DT')").hide();
                $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Inquire DT')").show();
				$("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Edit DT')").show();
            } else {
                $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Confirm DT')").hide();
                $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Add DT')").show();
                $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Inquire DT')").show();
				$("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Edit DT')").hide();
            }
        });

        $("#gridtablecdBranch").unsubscribe("gridCompletedDenom");
        $("#gridtablecdBranch").subscribe("gridCompletedDenom", function(event, data) {
            $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Inquire DT')").hide();
            $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Add DT')").show();
            $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Confirm DT')").hide();
            $("#gridtablecdBranch_pager_left div.ui-pg-div:contains('Edit DT')").hide();
            if ($("#gridtablecdBranch_pager_left #note").length == 0) {
                if ($('#gridtablecdBranch').jqGrid('getGridParam', 'records') == 0)
                    $("#gridtablecdBranch_pager_left").append("<span id='note' style='color:red'> | No records found</span>");
                else
                    $("#gridtablecdBranch_pager_left").append("<span id='note' style='color:red'>Please select a record to activate buttons</span>");
            }
        });

        $("#gridtablecdBranch").unsubscribe("inqDT");
        $("#gridtablecdBranch").subscribe("inqDT", function(event, data) {
            //var txnId = $("#gridtablecdBranch").data("noCif");
            var txnId = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "txnId");
            var statusDesc = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "statusDesc");
            var status = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "status");
            if (txnId == null) {
                alert("Please select one of the record to inquire Denom Transaction");
            } else {
                $("#frmGo_idMenu").val("21506");
                //$("#frmGo_noCif").val(noCif);
                $("#frmGo_statusCOM").val(status);
                $("#frmGo_statusDescCOM").val(statusDesc);
                $("#frmGo_goAction").val("inquiry");
                $("#btnGo").click();
            }
        });

        $("#gridtablecdBranch").unsubscribe("addDT");
        $("#gridtablecdBranch").subscribe("addDT", function(event, data) {
            var statusDesc = "Request";
            var status = "1";
            $("#frmGo_idMenu").val("21506");
            //$("#frmGo_noCif").val(noCif);
			$("#frmGo_datTxnCOM").val('${param.datTxn}');
            $("#frmGo_statusCOM").val(status);
            $("#frmGo_statusDescCOM").val(statusDesc);
            $("#frmGo_goAction").val("add");
            $("#btnGo").click();

        });

		$("#gridtablecdBranch").unsubscribe("editDT");
        $("#gridtablecdBranch").subscribe("editDT", function(event, data) {
			var txnId = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "txnId");
            var statusDesc = "Request";
            var status = "1";

			if (txnId == null) {
                alert("Please select one of the record to inquire Denom Transaction");
            } else {
				$("#frmGo_idMenu").val("21506");
            //$("#frmGo_noCif").val(noCif);
				$("#frmGo_statusCOM").val(status);
				$("#frmGo_statusDescCOM").val(statusDesc);
				$("#frmGo_goAction").val("edit");
				$("#btnGo").click();
			}
			

        });
		
        $("#gridtablecdBranch").unsubscribe("confirmDT");
        $("#gridtablecdBranch").subscribe("confirmDT", function(event, data) {
            //var noCif = $("#gridtable").data("noCif");
            var txnId = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "txnId");
            var statusDesc = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "statusDesc");
            var status = $("#gridtablecdBranch").jqGrid("getCell", $("#gridtablecdBranch").jqGrid("getGridParam", "selrow"), "status");
            if (txnId == null) {
                alert("Please select one of the record to Confirm Denom Transaction");
            } else {
                $("#frmGo_idMenu").val("21509");
                //$("#frmGo_noCif").val(noCif);
                $("#frmGo_statusCOM").val(status);
                $("#frmGo_statusDescCOM").val(statusDesc);
                $("#btnGo").click();
            }
        });
    });
</script>    