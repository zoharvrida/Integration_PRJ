<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="20201LimMonthly_list" namespace="/json" escapeAmp="false">
    <s:param name="flgAcct"><%=request.getParameter("flgAcct")%></s:param>
    <s:param name="noAccount"><%=request.getParameter("noAccount")%></s:param>
    <s:param name="period"><%=request.getParameter("period")%></s:param>
</s:url>
<s:actionerror name="actionError" /> 
<sjg:grid 
    id="gridtable"
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
    inqSL: {
    title: 'Inquire Statement Letter',
    caption: 'Inquire SL',
    icon: 'ui-icon-flag',
    topic: 'inqSL'
    },
    addSL: {
    title: 'Add Statement Letter',
    caption: 'Add SL',
    icon: 'ui-icon-document',
    topic: 'addSL'
    },
    addUD: {
    title: 'Add Underlying Document',
    caption: 'Add UD',
    icon: 'ui-icon-suitcase',
    topic: 'addUD'
    }
    }"
    onSelectRowTopics="selectRow" onGridCompleteTopics="gridComplete"
    >
    <sjg:gridColumn name="COD_CUST" index="COD_CUST" title="CIF" sortable="false" width="100"/>
    <sjg:gridColumn name="NAM_CUST_FULL" index="NAM_CUST_FULL" title="Name" sortable="false"/>
    <sjg:gridColumn name="SELECTED_CUSTOMER" index="SELECTED_CUSTOMER" title="Selected Customer" align="center" sortable="false"/>
    <sjg:gridColumn name="COD_ACCT_CUST_REL" index="COD_ACCT_CUST_REL" title="Relation" align="center" sortable="false" width="50"/>
    <sjg:gridColumn name="AMTAVAILUSD" index="AMTAVAILUSD" title="Available Limit" align="right" formatter="currency" sortable="false"/>
    <sjg:gridColumn name="ACTION" index="ACTION" title="Action" hidden="true"/>
</sjg:grid>
<script type="text/javascript">
    jQuery(document).ready(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#gridtable").data("noCif", $(data).jqGrid("getCell", event.originalEvent.id, "COD_CUST"));
            $("#gridtable").data("sl", $(data).jqGrid("getCell", event.originalEvent.id, "ACTION"));
            var flgSl = $(data).jqGrid("getCell", event.originalEvent.id, "ACTION");
            if (flgSl == 'I') {
                $("#gridtable_pager_left div.ui-pg-div:contains('Inquire SL')").show();
                $("#gridtable_pager_left div.ui-pg-div:contains('Add SL')").hide();
            } else if (flgSl == 'A') {
                $("#gridtable_pager_left div.ui-pg-div:contains('Inquire SL')").hide();
                $("#gridtable_pager_left div.ui-pg-div:contains('Add SL')").show();
            }
            $("#gridtable_pager_left div.ui-pg-div:contains('Add UD')").show();
            $("#gridtable_pager_left #note").hide();
        });

        $("#gridtable").unsubscribe("inqSL");
        $("#gridtable").subscribe("inqSL", function(event, data) {
            var noCif = $("#gridtable").data("noCif");
            var flgSL = $("#gridtable").data("sl");
            if (noCif == null) {
                alert("Please select one of the record to inquire Statement Letter");
            } else if (flgSL == 'I') {
                $("#frmGo_idMenu").val("20301");
                $("#frmGo_noCif").val(noCif);
                $("#frmGo_goAction").val("inquiry");
                $("#btnGo").click();
            }
        });

        $("#gridtable").unsubscribe("addSL");
        $("#gridtable").subscribe("addSL", function(event, data) {
            var noCif = $("#gridtable").data("noCif");
            var flgSL = $("#gridtable").data("sl");
            if (noCif == null) {
                alert("Please select one of the record to add Statement Letter");
            } else if (flgSL == 'A') {
                $("#frmGo_idMenu").val("20301");
                $("#frmGo_noCif").val(noCif);
                $("#frmGo_goAction").val("add");
                $("#btnGo").click();
            }
        });

        $("#gridtable").unsubscribe("addUD");
        $("#gridtable").subscribe("addUD", function(event, data) {
            var noCif = $("#gridtable").data("noCif");
            if (noCif == null) {
                alert("Please select one of the record to add Underlying Document");
            } else {
                $("#frmGo_idMenu").val("20302");
                $("#frmGo_noCif").val(noCif);
                $("#btnGo").click();
            }
        });

        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
            $("#gridtable_pager_left div.ui-pg-div:contains('Inquire SL')").hide();
            $("#gridtable_pager_left div.ui-pg-div:contains('Add SL')").hide();
            $("#gridtable_pager_left div.ui-pg-div:contains('Add UD')").hide();
            if ($("#gridtable_pager_left #note").length == 0) {
                if ($('#gridtable').jqGrid('getGridParam', 'records') == 0)
                    $("#gridtable_pager_left").append("<span id='note' style='color:red'>No records found</span>");
                else
                    $("#gridtable_pager_left").append("<span id='note' style='color:red'>Please select a record to activate buttons</span>");
            }
            $("#gridtable").data("noCif", "");
        });
    });
</script>
