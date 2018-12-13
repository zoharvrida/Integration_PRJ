<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="urlGrid" action="10303_list" namespace="/json">
    <s:param name="namUser"><%=request.getParameter("namUser")%></s:param>
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
    <sjg:gridColumn name="idUser" index="idUser" title="User ID" sortable="false" width="50"/>
    <sjg:gridColumn name="namUser" index="namUser" title="User Name" sortable="false"/>
    <sjg:gridColumn name="cdBranch" index="cdBranch" title="Branch Code" sortable="false" align="center" width="30"/>
    <sjg:gridColumn name="idTemplate" index="idTemplate" title="Template ID" sortable="false" width="50"/>
    <sjg:gridColumn name="namTemplate" index="namTemplate" title="Template Name" sortable="false"/>
</sjg:grid>
<script type="text/javascript">
    $(function() {
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            $("#" + dlgParams.idUser).val($(data).jqGrid("getCell", event.originalEvent.id, "idUser"));
            $("#" + dlgParams.namUser).val($(data).jqGrid("getCell", event.originalEvent.id, "namUser"));
            $("#" + dlgParams.cdBranch).val($(data).jqGrid("getCell", event.originalEvent.id, "cdBranch"));
            $("#" + dlgParams.idTemplate).val($(data).jqGrid("getCell", event.originalEvent.id, "idTemplate"));
            $("#" + dlgParams.namTemplate).val($(data).jqGrid("getCell", event.originalEvent.id, "namTemplate"));
            
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
