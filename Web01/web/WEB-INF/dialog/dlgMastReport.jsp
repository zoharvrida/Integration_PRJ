<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="10304_list" namespace="/json" escapeAmp="false">
    <s:param name="reportName"><%=request.getParameter("reportName")%></s:param>
    <s:param name="idTemplate"><%=request.getParameter("idTemplate")%></s:param>  
</s:url>
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
    navigator="false"
    navigatorAdd="false"
    navigatorDelete="false"
    navigatorEdit="false"
    navigatorRefresh="false"
    navigatorSearch="false"
    onSelectRowTopics="selectRow" onGridCompleteTopics="gridComplete"
>
    <sjg:gridColumn name="IDREPORT" index="IDREPORT" title="Report Id" sortable="false" width="50" hidden="true"/>
    <sjg:gridColumn name="REPORTNAME" index="REPORTNAME" title="Report File Name" sortable="false"/>
<sjg:gridColumn name="NAMSCHEDULER" index="NAMSCHEDULER" title="Report Name" sortable="false"/>
    
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.idReport).val($(data).jqGrid("getCell", event.originalEvent.id, "IDREPORT"));
            $("#" + dlgParams.reportName).val($(data).jqGrid("getCell", event.originalEvent.id, "REPORTNAME"));
            
            if (dlgParams.onClose != undefined) {
                dlgParams.onClose();
            }
            $("#ph-dlg").dialog("close");
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 650), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 650)-22);
        });
    });
</script>
