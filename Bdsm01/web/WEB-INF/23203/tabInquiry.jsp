<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url var="urlGrid" action="23203_list" namespace="/json" escapeAmp="false">
    <s:param name="IDBATCH"><%=request.getParameter("IDBATCH")%></s:param>
	<s:param name="mode" value="1" />
</s:url>
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
	
    function customApprovalSMO(cellValue, options, rowObjects) {
        var result = " ";
        if (cellValue == "R")
            result = "Reject"; 
        else if (cellValue == "A")
            result = "Approved";
		else if (cellValue == "N")
            result = "N/A";	
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
        <tr>
            <td>Account No</td>
            <td>:</td>
            <td><s:label id="txtAcctInq" theme="css_xhtml" /></td>
        </tr>
    </table>

    <sjg:grid 
        id="inqGridTable"
        caption=""
        dataType="json" 
        href="%{urlGrid}"
        requestType="POST"
        pager="true"
        pagerPosition="right"
        gridModel="gridTemplate" 
        rowNum="10"
        rownumbers="true"
        shrinkToFit="false"
        width="800"
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
        <sjg:gridColumn hidden="true" id="AccountNo" name="2.acctNo" title="AccountNo" align="left" sortable="false" />
        <sjg:gridColumn hidden="true" name="2.compositeId.parentId" id="parentRecordId" title="ParentId" width="50" align="center" sortable="false" />
        <sjg:gridColumn name="2.batchNo" title="BATCH ID" width="50" align="center" sortable="false" />
        <sjg:gridColumn name="2.extractStatus" title="STATUS" width="60" align="center" formatter="customApprovalSMO" sortable="false" />
        <sjg:gridColumn name="2.transactionDate" title="Tanggal Batch" width="60" align="center" sortable="false" />
        <sjg:gridColumn name="2.sandiPenyelenggaraPengirim" index="2.sandiPenyelenggaraPengirim" title="Sandi Pengirim" width="150" align="left" sortable="false" />
        <sjg:gridColumn name="2.sandiKBI" index="2.sandiKBI" title="Sandi Kliring BI" width="100" align="left" sortable="false" /> 
        <sjg:gridColumn name="2.totalRecord" index="2.totalRecord" title="Total Records" width="50" align="center" sortable="false" />
        <sjg:gridColumn name="realNominal" index="realNominal" title="Total Nominal" width="150" align="left" formatter="customCurrency" sortable="false" />
        <sjg:gridColumn name="2.message" index="2.message" title="Reason" width="600" align="left" sortable="false" />
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
                    $("#txtFilenameInq").text($(data).jqGrid("getCell", 1, 1));
                    $("#txtAcctInq").text($(data).jqGrid("getCell", 1, 2));
				}
			}
        });
    });
</script>