<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url var="urlGrid" action="23202_list" namespace="/json" escapeAmp="false">
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
	
    function customApproval(cellValue, options, rowObjects) {
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
        rowNum="15"
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
        <sjg:gridColumn hidden="true" name="1.compositeId.parentRecordId" id="parentRecordId" title="ParentId" width="50" align="center" sortable="false" />
		<sjg:gridColumn name="1.batchId" title="BATCH ID" width="50" align="center" sortable="false" />
		<sjg:gridColumn name="1.extractStatus" title="STATUS" width="60" align="center" formatter="customApproval" sortable="false" />
        <sjg:gridColumn name="1.tanggalBatch" title="Tanggal Batch" width="60" align="center" sortable="false" />
		<sjg:gridColumn name="1.identitasPesertaPengirim" index="1.identitasPesertaPengirim" title="Identitas Pengirim" width="150" align="left" sortable="false" />
		<sjg:gridColumn name="1.sandiKotaPengirim" index="1.sandiKotaPengirim" title="Sandi Kota" width="100" align="left" sortable="false" /> 
        <sjg:gridColumn name="1.jumlahRecords" index="1.jumlahRecords" title="Total Records" width="50" align="center" sortable="false" />
        <sjg:gridColumn name="1.totalNominal" index="1.totalNominal" title="Total Nominal" width="150" align="left" formatter="customCurrency" sortable="false" />
        <sjg:gridColumn name="1.message" index="1.message" title="Reason" width="600" align="left" sortable="false" />
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
					
					//$("td[role='gridcell']").css("vertical-align", "middle");
					//$("#gview_inqGridTable div.ui-jqgrid-bdiv").css("height", $('#inqGridTable').jqGrid('getGridParam', 'records') * 23 + 20);
				}
			}
        });
    });
</script>